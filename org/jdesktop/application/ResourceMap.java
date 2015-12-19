/*    1:     */ package org.jdesktop.application;
/*    2:     */ 
/*    3:     */ import java.awt.Color;
/*    4:     */ import java.awt.Component;
/*    5:     */ import java.awt.Container;
/*    6:     */ import java.awt.Dimension;
/*    7:     */ import java.awt.Font;
/*    8:     */ import java.awt.Image;
/*    9:     */ import java.awt.Insets;
/*   10:     */ import java.awt.Point;
/*   11:     */ import java.awt.Rectangle;
/*   12:     */ import java.awt.Toolkit;
/*   13:     */ import java.beans.BeanInfo;
/*   14:     */ import java.beans.IntrospectionException;
/*   15:     */ import java.beans.Introspector;
/*   16:     */ import java.beans.PropertyDescriptor;
/*   17:     */ import java.lang.reflect.Array;
/*   18:     */ import java.lang.reflect.Field;
/*   19:     */ import java.lang.reflect.Method;
/*   20:     */ import java.net.URL;
/*   21:     */ import java.util.ArrayList;
/*   22:     */ import java.util.Arrays;
/*   23:     */ import java.util.Collections;
/*   24:     */ import java.util.Enumeration;
/*   25:     */ import java.util.HashSet;
/*   26:     */ import java.util.List;
/*   27:     */ import java.util.Locale;
/*   28:     */ import java.util.Map;
/*   29:     */ import java.util.MissingResourceException;
/*   30:     */ import java.util.ResourceBundle;
/*   31:     */ import java.util.Set;
/*   32:     */ import java.util.concurrent.ConcurrentHashMap;
/*   33:     */ import java.util.logging.Logger;
/*   34:     */ import java.util.regex.Matcher;
/*   35:     */ import java.util.regex.Pattern;
/*   36:     */ import javax.swing.AbstractButton;
/*   37:     */ import javax.swing.Icon;
/*   38:     */ import javax.swing.ImageIcon;
/*   39:     */ import javax.swing.JLabel;
/*   40:     */ import javax.swing.JMenu;
/*   41:     */ import javax.swing.KeyStroke;
/*   42:     */ import javax.swing.border.EmptyBorder;
/*   43:     */ 
/*   44:     */ public class ResourceMap
/*   45:     */ {
/*   46:  94 */   private static Logger logger = Logger.getLogger(ResourceMap.class.getName());
/*   47:  95 */   private static final Object nullResource = new String("null resource");
/*   48:     */   private final ClassLoader classLoader;
/*   49:     */   private final ResourceMap parent;
/*   50:     */   private final List<String> bundleNames;
/*   51:     */   private final String resourcesDir;
/*   52: 100 */   private Map<String, Object> bundlesMapP = null;
/*   53: 101 */   private Locale locale = Locale.getDefault();
/*   54: 102 */   private Set<String> bundlesMapKeysP = null;
/*   55: 103 */   private boolean bundlesLoaded = false;
/*   56:     */   
/*   57:     */   public ResourceMap(ResourceMap parent, ClassLoader classLoader, List<String> bundleNames)
/*   58:     */   {
/*   59: 151 */     if (classLoader == null) {
/*   60: 152 */       throw new IllegalArgumentException("null ClassLoader");
/*   61:     */     }
/*   62: 154 */     if ((bundleNames == null) || (bundleNames.size() == 0)) {
/*   63: 155 */       throw new IllegalArgumentException("no bundle specified");
/*   64:     */     }
/*   65: 157 */     for (String bn : bundleNames) {
/*   66: 158 */       if ((bn == null) || (bn.length() == 0)) {
/*   67: 159 */         throw new IllegalArgumentException("invalid bundleName: \"" + bn + "\"");
/*   68:     */       }
/*   69:     */     }
/*   70: 162 */     String bpn = bundlePackageName((String)bundleNames.get(0));
/*   71: 163 */     for (String bn : bundleNames) {
/*   72: 164 */       if (!bpn.equals(bundlePackageName(bn))) {
/*   73: 165 */         throw new IllegalArgumentException("bundles not colocated: \"" + bn + "\" != \"" + bpn + "\"");
/*   74:     */       }
/*   75:     */     }
/*   76: 168 */     this.parent = parent;
/*   77: 169 */     this.classLoader = classLoader;
/*   78: 170 */     this.bundleNames = Collections.unmodifiableList(new ArrayList(bundleNames));
/*   79: 171 */     this.resourcesDir = (bpn.replace(".", "/") + "/");
/*   80:     */   }
/*   81:     */   
/*   82:     */   private String bundlePackageName(String bundleName)
/*   83:     */   {
/*   84: 175 */     int i = bundleName.lastIndexOf(".");
/*   85: 176 */     return i == -1 ? "" : bundleName.substring(0, i);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public ResourceMap(ResourceMap parent, ClassLoader classLoader, String... bundleNames)
/*   89:     */   {
/*   90: 185 */     this(parent, classLoader, Arrays.asList(bundleNames));
/*   91:     */   }
/*   92:     */   
/*   93:     */   public ResourceMap getParent()
/*   94:     */   {
/*   95: 196 */     return this.parent;
/*   96:     */   }
/*   97:     */   
/*   98:     */   public List<String> getBundleNames()
/*   99:     */   {
/*  100: 206 */     return this.bundleNames;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public ClassLoader getClassLoader()
/*  104:     */   {
/*  105: 216 */     return this.classLoader;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public String getResourcesDir()
/*  109:     */   {
/*  110: 232 */     return this.resourcesDir;
/*  111:     */   }
/*  112:     */   
/*  113:     */   private synchronized Map<String, Object> getBundlesMap()
/*  114:     */   {
/*  115: 241 */     Locale defaultLocale = Locale.getDefault();
/*  116: 242 */     if (this.locale != defaultLocale)
/*  117:     */     {
/*  118: 243 */       this.bundlesLoaded = false;
/*  119: 244 */       this.locale = defaultLocale;
/*  120:     */     }
/*  121: 246 */     if (!this.bundlesLoaded)
/*  122:     */     {
/*  123: 247 */       Map<String, Object> bundlesMap = new ConcurrentHashMap();
/*  124: 248 */       for (int i = this.bundleNames.size() - 1; i >= 0; i--) {
/*  125:     */         try
/*  126:     */         {
/*  127: 250 */           String bundleName = (String)this.bundleNames.get(i);
/*  128: 251 */           ResourceBundle bundle = ResourceBundle.getBundle(bundleName, this.locale, this.classLoader);
/*  129: 252 */           Enumeration<String> keys = bundle.getKeys();
/*  130: 253 */           while (keys.hasMoreElements())
/*  131:     */           {
/*  132: 254 */             String key = (String)keys.nextElement();
/*  133: 255 */             bundlesMap.put(key, bundle.getObject(key));
/*  134:     */           }
/*  135:     */         }
/*  136:     */         catch (MissingResourceException ignore) {}
/*  137:     */       }
/*  138: 264 */       this.bundlesMapP = bundlesMap;
/*  139: 265 */       this.bundlesLoaded = true;
/*  140:     */     }
/*  141: 267 */     return this.bundlesMapP;
/*  142:     */   }
/*  143:     */   
/*  144:     */   private void checkNullKey(String key)
/*  145:     */   {
/*  146: 271 */     if (key == null) {
/*  147: 272 */       throw new IllegalArgumentException("null key");
/*  148:     */     }
/*  149:     */   }
/*  150:     */   
/*  151:     */   private synchronized Set<String> getBundlesMapKeys()
/*  152:     */   {
/*  153: 277 */     if (this.bundlesMapKeysP == null)
/*  154:     */     {
/*  155: 278 */       Set<String> allKeys = new HashSet(getResourceKeySet());
/*  156: 279 */       ResourceMap parent = getParent();
/*  157: 280 */       if (parent != null) {
/*  158: 281 */         allKeys.addAll(parent.keySet());
/*  159:     */       }
/*  160: 283 */       this.bundlesMapKeysP = Collections.unmodifiableSet(allKeys);
/*  161:     */     }
/*  162: 285 */     return this.bundlesMapKeysP;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public Set<String> keySet()
/*  166:     */   {
/*  167: 296 */     return getBundlesMapKeys();
/*  168:     */   }
/*  169:     */   
/*  170:     */   public boolean containsKey(String key)
/*  171:     */   {
/*  172: 308 */     checkNullKey(key);
/*  173: 309 */     if (containsResourceKey(key)) {
/*  174: 310 */       return true;
/*  175:     */     }
/*  176: 313 */     ResourceMap parent = getParent();
/*  177: 314 */     return parent != null ? parent.containsKey(key) : false;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public static class LookupException
/*  181:     */     extends RuntimeException
/*  182:     */   {
/*  183:     */     private final Class type;
/*  184:     */     private final String key;
/*  185:     */     
/*  186:     */     public LookupException(String msg, String key, Class type)
/*  187:     */     {
/*  188: 339 */       super();
/*  189: 340 */       this.key = key;
/*  190: 341 */       this.type = type;
/*  191:     */     }
/*  192:     */     
/*  193:     */     public Class getType()
/*  194:     */     {
/*  195: 349 */       return this.type;
/*  196:     */     }
/*  197:     */     
/*  198:     */     public String getKey()
/*  199:     */     {
/*  200: 357 */       return this.key;
/*  201:     */     }
/*  202:     */   }
/*  203:     */   
/*  204:     */   protected Set<String> getResourceKeySet()
/*  205:     */   {
/*  206: 378 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  207: 379 */     if (bundlesMap == null) {
/*  208: 380 */       return Collections.emptySet();
/*  209:     */     }
/*  210: 383 */     return bundlesMap.keySet();
/*  211:     */   }
/*  212:     */   
/*  213:     */   protected boolean containsResourceKey(String key)
/*  214:     */   {
/*  215: 407 */     checkNullKey(key);
/*  216: 408 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  217: 409 */     return (bundlesMap != null) && (bundlesMap.containsKey(key));
/*  218:     */   }
/*  219:     */   
/*  220:     */   protected Object getResource(String key)
/*  221:     */   {
/*  222: 435 */     checkNullKey(key);
/*  223: 436 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  224: 437 */     Object value = bundlesMap != null ? bundlesMap.get(key) : null;
/*  225: 438 */     return value == nullResource ? null : value;
/*  226:     */   }
/*  227:     */   
/*  228:     */   protected void putResource(String key, Object value)
/*  229:     */   {
/*  230: 462 */     checkNullKey(key);
/*  231: 463 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  232: 464 */     if (bundlesMap != null) {
/*  233: 465 */       bundlesMap.put(key, value == null ? nullResource : value);
/*  234:     */     }
/*  235:     */   }
/*  236:     */   
/*  237:     */   public Object getObject(String key, Class type)
/*  238:     */   {
/*  239: 521 */     checkNullKey(key);
/*  240: 522 */     if (type == null) {
/*  241: 523 */       throw new IllegalArgumentException("null type");
/*  242:     */     }
/*  243: 525 */     if (type.isPrimitive()) {
/*  244: 526 */       if (type == Boolean.TYPE) {
/*  245: 526 */         type = Boolean.class;
/*  246: 527 */       } else if (type == Character.TYPE) {
/*  247: 527 */         type = Character.class;
/*  248: 528 */       } else if (type == Byte.TYPE) {
/*  249: 528 */         type = Byte.class;
/*  250: 529 */       } else if (type == Short.TYPE) {
/*  251: 529 */         type = Short.class;
/*  252: 530 */       } else if (type == Integer.TYPE) {
/*  253: 530 */         type = Integer.class;
/*  254: 531 */       } else if (type == Long.TYPE) {
/*  255: 531 */         type = Long.class;
/*  256: 532 */       } else if (type == Float.TYPE) {
/*  257: 532 */         type = Float.class;
/*  258: 533 */       } else if (type == Double.TYPE) {
/*  259: 533 */         type = Double.class;
/*  260:     */       }
/*  261:     */     }
/*  262: 535 */     Object value = null;
/*  263: 536 */     ResourceMap resourceMapNode = this;
/*  264: 541 */     while (resourceMapNode != null)
/*  265:     */     {
/*  266: 542 */       if (resourceMapNode.containsResourceKey(key))
/*  267:     */       {
/*  268: 543 */         value = resourceMapNode.getResource(key);
/*  269: 544 */         break;
/*  270:     */       }
/*  271: 546 */       resourceMapNode = resourceMapNode.getParent();
/*  272:     */     }
/*  273: 552 */     if (((value instanceof String)) && (((String)value).contains("${")))
/*  274:     */     {
/*  275: 553 */       value = evaluateStringExpression((String)value);
/*  276: 554 */       resourceMapNode.putResource(key, value);
/*  277:     */     }
/*  278: 565 */     if (value != null)
/*  279:     */     {
/*  280: 566 */       Class valueClass = value.getClass();
/*  281: 567 */       if (!type.isAssignableFrom(valueClass)) {
/*  282: 568 */         if ((value instanceof String))
/*  283:     */         {
/*  284: 569 */           ResourceConverter stringConverter = ResourceConverter.forType(type);
/*  285: 570 */           if (stringConverter != null)
/*  286:     */           {
/*  287: 571 */             String sValue = (String)value;
/*  288:     */             try
/*  289:     */             {
/*  290: 573 */               value = stringConverter.parseString(sValue, resourceMapNode);
/*  291: 574 */               resourceMapNode.putResource(key, value);
/*  292:     */             }
/*  293:     */             catch (ResourceConverter.ResourceConverterException e)
/*  294:     */             {
/*  295: 577 */               String msg = "string conversion failed";
/*  296: 578 */               LookupException lfe = new LookupException(msg, key, type);
/*  297: 579 */               lfe.initCause(e);
/*  298: 580 */               throw lfe;
/*  299:     */             }
/*  300:     */           }
/*  301:     */           else
/*  302:     */           {
/*  303: 584 */             String msg = "no StringConverter for required type";
/*  304: 585 */             throw new LookupException(msg, key, type);
/*  305:     */           }
/*  306:     */         }
/*  307:     */         else
/*  308:     */         {
/*  309: 589 */           String msg = "named resource has wrong type";
/*  310: 590 */           throw new LookupException(msg, key, type);
/*  311:     */         }
/*  312:     */       }
/*  313:     */     }
/*  314: 594 */     return value;
/*  315:     */   }
/*  316:     */   
/*  317:     */   private String evaluateStringExpression(String expr)
/*  318:     */   {
/*  319: 607 */     if (expr.trim().equals("${null}")) {
/*  320: 608 */       return null;
/*  321:     */     }
/*  322: 610 */     StringBuffer value = new StringBuffer();
/*  323: 611 */     int i0 = 0;int i1 = 0;
/*  324: 612 */     while ((i1 = expr.indexOf("${", i0)) != -1) {
/*  325: 613 */       if ((i1 == 0) || ((i1 > 0) && (expr.charAt(i1 - 1) != '\\')))
/*  326:     */       {
/*  327: 614 */         int i2 = expr.indexOf("}", i1);
/*  328: 615 */         if ((i2 != -1) && (i2 > i1 + 2))
/*  329:     */         {
/*  330: 616 */           String k = expr.substring(i1 + 2, i2);
/*  331: 617 */           String v = getString(k, new Object[0]);
/*  332: 618 */           value.append(expr.substring(i0, i1));
/*  333: 619 */           if (v != null)
/*  334:     */           {
/*  335: 620 */             value.append(v);
/*  336:     */           }
/*  337:     */           else
/*  338:     */           {
/*  339: 623 */             String msg = String.format("no value for \"%s\" in \"%s\"", new Object[] { k, expr });
/*  340: 624 */             throw new LookupException(msg, k, String.class);
/*  341:     */           }
/*  342: 626 */           i0 = i2 + 1;
/*  343:     */         }
/*  344:     */         else
/*  345:     */         {
/*  346: 629 */           String msg = String.format("no closing brace in \"%s\"", new Object[] { expr });
/*  347: 630 */           throw new LookupException(msg, "<not found>", String.class);
/*  348:     */         }
/*  349:     */       }
/*  350:     */       else
/*  351:     */       {
/*  352: 634 */         value.append(expr.substring(i0, i1 - 1));
/*  353: 635 */         value.append("${");
/*  354: 636 */         i0 = i1 + 2;
/*  355:     */       }
/*  356:     */     }
/*  357: 639 */     value.append(expr.substring(i0));
/*  358: 640 */     return value.toString();
/*  359:     */   }
/*  360:     */   
/*  361:     */   public String getString(String key, Object... args)
/*  362:     */   {
/*  363: 665 */     if (args.length == 0) {
/*  364: 666 */       return (String)getObject(key, String.class);
/*  365:     */     }
/*  366: 669 */     String format = (String)getObject(key, String.class);
/*  367: 670 */     return format == null ? null : String.format(format, args);
/*  368:     */   }
/*  369:     */   
/*  370:     */   public final Boolean getBoolean(String key)
/*  371:     */   {
/*  372: 685 */     return (Boolean)getObject(key, Boolean.class);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public final Integer getInteger(String key)
/*  376:     */   {
/*  377: 699 */     return (Integer)getObject(key, Integer.class);
/*  378:     */   }
/*  379:     */   
/*  380:     */   public final Long getLong(String key)
/*  381:     */   {
/*  382: 713 */     return (Long)getObject(key, Long.class);
/*  383:     */   }
/*  384:     */   
/*  385:     */   public final Short getShort(String key)
/*  386:     */   {
/*  387: 727 */     return (Short)getObject(key, Short.class);
/*  388:     */   }
/*  389:     */   
/*  390:     */   public final Byte getByte(String key)
/*  391:     */   {
/*  392: 741 */     return (Byte)getObject(key, Byte.class);
/*  393:     */   }
/*  394:     */   
/*  395:     */   public final Float getFloat(String key)
/*  396:     */   {
/*  397: 755 */     return (Float)getObject(key, Float.class);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public final Double getDouble(String key)
/*  401:     */   {
/*  402: 769 */     return (Double)getObject(key, Double.class);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public final Icon getIcon(String key)
/*  406:     */   {
/*  407: 787 */     return (Icon)getObject(key, Icon.class);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public final ImageIcon getImageIcon(String key)
/*  411:     */   {
/*  412: 826 */     return (ImageIcon)getObject(key, ImageIcon.class);
/*  413:     */   }
/*  414:     */   
/*  415:     */   public final Font getFont(String key)
/*  416:     */   {
/*  417: 852 */     return (Font)getObject(key, Font.class);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public final Color getColor(String key)
/*  421:     */   {
/*  422: 883 */     return (Color)getObject(key, Color.class);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public final KeyStroke getKeyStroke(String key)
/*  426:     */   {
/*  427: 902 */     return (KeyStroke)getObject(key, KeyStroke.class);
/*  428:     */   }
/*  429:     */   
/*  430:     */   public Integer getKeyCode(String key)
/*  431:     */   {
/*  432: 917 */     KeyStroke ks = getKeyStroke(key);
/*  433: 918 */     return ks != null ? new Integer(ks.getKeyCode()) : null;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public static class PropertyInjectionException
/*  437:     */     extends RuntimeException
/*  438:     */   {
/*  439:     */     private final String key;
/*  440:     */     private final Component component;
/*  441:     */     private final String propertyName;
/*  442:     */     
/*  443:     */     public PropertyInjectionException(String msg, String key, Component component, String propertyName)
/*  444:     */     {
/*  445: 944 */       super();
/*  446: 945 */       this.key = key;
/*  447: 946 */       this.component = component;
/*  448: 947 */       this.propertyName = propertyName;
/*  449:     */     }
/*  450:     */     
/*  451:     */     public String getKey()
/*  452:     */     {
/*  453: 955 */       return this.key;
/*  454:     */     }
/*  455:     */     
/*  456:     */     public Component getComponent()
/*  457:     */     {
/*  458: 963 */       return this.component;
/*  459:     */     }
/*  460:     */     
/*  461:     */     public String getPropertyName()
/*  462:     */     {
/*  463: 971 */       return this.propertyName;
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   private void injectComponentProperty(Component component, PropertyDescriptor pd, String key)
/*  468:     */   {
/*  469: 976 */     Method setter = pd.getWriteMethod();
/*  470: 977 */     Class type = pd.getPropertyType();
/*  471: 978 */     if ((setter != null) && (type != null) && (containsKey(key)))
/*  472:     */     {
/*  473: 979 */       Object value = getObject(key, type);
/*  474: 980 */       String propertyName = pd.getName();
/*  475:     */       try
/*  476:     */       {
/*  477: 984 */         if (("text".equals(propertyName)) && ((component instanceof AbstractButton))) {
/*  478: 985 */           MnemonicText.configure(component, (String)value);
/*  479: 987 */         } else if (("text".equals(propertyName)) && ((component instanceof JLabel))) {
/*  480: 988 */           MnemonicText.configure(component, (String)value);
/*  481:     */         } else {
/*  482: 991 */           setter.invoke(component, new Object[] { value });
/*  483:     */         }
/*  484:     */       }
/*  485:     */       catch (Exception e)
/*  486:     */       {
/*  487: 995 */         String pdn = pd.getName();
/*  488: 996 */         String msg = "property setter failed";
/*  489: 997 */         RuntimeException re = new PropertyInjectionException(msg, key, component, pdn);
/*  490: 998 */         re.initCause(e);
/*  491: 999 */         throw re;
/*  492:     */       }
/*  493:     */     }
/*  494:     */     else
/*  495:     */     {
/*  496:1002 */       if (type != null)
/*  497:     */       {
/*  498:1003 */         String pdn = pd.getName();
/*  499:1004 */         String msg = "no value specified for resource";
/*  500:1005 */         throw new PropertyInjectionException(msg, key, component, pdn);
/*  501:     */       }
/*  502:1007 */       if (setter == null)
/*  503:     */       {
/*  504:1008 */         String pdn = pd.getName();
/*  505:1009 */         String msg = "can't set read-only property";
/*  506:1010 */         throw new PropertyInjectionException(msg, key, component, pdn);
/*  507:     */       }
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:     */   private void injectComponentProperties(Component component)
/*  512:     */   {
/*  513:1015 */     String componentName = component.getName();
/*  514:     */     PropertyDescriptor[] pds;
/*  515:1016 */     if (componentName != null)
/*  516:     */     {
/*  517:1020 */       boolean matchingResourceFound = false;
/*  518:1021 */       for (String key : keySet())
/*  519:     */       {
/*  520:1022 */         int i = key.lastIndexOf(".");
/*  521:1023 */         if ((i != -1) && (componentName.equals(key.substring(0, i))))
/*  522:     */         {
/*  523:1024 */           matchingResourceFound = true;
/*  524:1025 */           break;
/*  525:     */         }
/*  526:     */       }
/*  527:1028 */       if (!matchingResourceFound) {
/*  528:1029 */         return;
/*  529:     */       }
/*  530:1031 */       BeanInfo beanInfo = null;
/*  531:     */       try
/*  532:     */       {
/*  533:1033 */         beanInfo = Introspector.getBeanInfo(component.getClass());
/*  534:     */       }
/*  535:     */       catch (IntrospectionException e)
/*  536:     */       {
/*  537:1036 */         String msg = "introspection failed";
/*  538:1037 */         RuntimeException re = new PropertyInjectionException(msg, null, component, null);
/*  539:1038 */         re.initCause(e);
/*  540:1039 */         throw re;
/*  541:     */       }
/*  542:1041 */       pds = beanInfo.getPropertyDescriptors();
/*  543:1042 */       if ((pds != null) && (pds.length > 0)) {
/*  544:1043 */         for (String key : keySet())
/*  545:     */         {
/*  546:1044 */           int i = key.lastIndexOf(".");
/*  547:1045 */           String keyComponentName = i == -1 ? null : key.substring(0, i);
/*  548:1046 */           if (componentName.equals(keyComponentName))
/*  549:     */           {
/*  550:1047 */             if (i + 1 == key.length())
/*  551:     */             {
/*  552:1051 */               String msg = "component resource lacks property name suffix";
/*  553:1052 */               logger.warning(msg);
/*  554:1053 */               break;
/*  555:     */             }
/*  556:1055 */             String propertyName = key.substring(i + 1);
/*  557:1056 */             boolean matchingPropertyFound = false;
/*  558:1057 */             for (PropertyDescriptor pd : pds) {
/*  559:1058 */               if (pd.getName().equals(propertyName))
/*  560:     */               {
/*  561:1059 */                 injectComponentProperty(component, pd, key);
/*  562:1060 */                 matchingPropertyFound = true;
/*  563:1061 */                 break;
/*  564:     */               }
/*  565:     */             }
/*  566:1064 */             if (!matchingPropertyFound)
/*  567:     */             {
/*  568:1065 */               String msg = String.format("[resource %s] component named %s doesn't have a property named %s", new Object[] { key, componentName, propertyName });
/*  569:     */               
/*  570:     */ 
/*  571:1068 */               logger.warning(msg);
/*  572:     */             }
/*  573:     */           }
/*  574:     */         }
/*  575:     */       }
/*  576:     */     }
/*  577:     */   }
/*  578:     */   
/*  579:     */   public void injectComponent(Component target)
/*  580:     */   {
/*  581:1122 */     if (target == null) {
/*  582:1123 */       throw new IllegalArgumentException("null target");
/*  583:     */     }
/*  584:1125 */     injectComponentProperties(target);
/*  585:     */   }
/*  586:     */   
/*  587:     */   public void injectComponents(Component root)
/*  588:     */   {
/*  589:1139 */     injectComponent(root);
/*  590:1140 */     if ((root instanceof JMenu))
/*  591:     */     {
/*  592:1147 */       JMenu menu = (JMenu)root;
/*  593:1148 */       for (Component child : menu.getMenuComponents()) {
/*  594:1149 */         injectComponents(child);
/*  595:     */       }
/*  596:     */     }
/*  597:1152 */     else if ((root instanceof Container))
/*  598:     */     {
/*  599:1153 */       Container container = (Container)root;
/*  600:1154 */       for (Component child : container.getComponents()) {
/*  601:1155 */         injectComponents(child);
/*  602:     */       }
/*  603:     */     }
/*  604:     */   }
/*  605:     */   
/*  606:     */   public static class InjectFieldException
/*  607:     */     extends RuntimeException
/*  608:     */   {
/*  609:     */     private final Field field;
/*  610:     */     private final Object target;
/*  611:     */     private final String key;
/*  612:     */     
/*  613:     */     public InjectFieldException(String msg, Field field, Object target, String key)
/*  614:     */     {
/*  615:1182 */       super();
/*  616:1183 */       this.field = field;
/*  617:1184 */       this.target = target;
/*  618:1185 */       this.key = key;
/*  619:     */     }
/*  620:     */     
/*  621:     */     public Field getField()
/*  622:     */     {
/*  623:1193 */       return this.field;
/*  624:     */     }
/*  625:     */     
/*  626:     */     public Object getTarget()
/*  627:     */     {
/*  628:1201 */       return this.target;
/*  629:     */     }
/*  630:     */     
/*  631:     */     public String getKey()
/*  632:     */     {
/*  633:1209 */       return this.key;
/*  634:     */     }
/*  635:     */   }
/*  636:     */   
/*  637:     */   private void injectField(Field field, Object target, String key)
/*  638:     */   {
/*  639:1214 */     Class type = field.getType();
/*  640:     */     Pattern p;
/*  641:1215 */     if (type.isArray())
/*  642:     */     {
/*  643:1216 */       type = type.getComponentType();
/*  644:1217 */       p = Pattern.compile(key + "\\[([\\d]+)\\]");
/*  645:1218 */       List<String> arrayKeys = new ArrayList();
/*  646:1219 */       for (String arrayElementKey : keySet())
/*  647:     */       {
/*  648:1220 */         Matcher m = p.matcher(arrayElementKey);
/*  649:1221 */         if (m.matches())
/*  650:     */         {
/*  651:1227 */           Object value = getObject(arrayElementKey, type);
/*  652:1228 */           if (!field.isAccessible()) {
/*  653:1229 */             field.setAccessible(true);
/*  654:     */           }
/*  655:     */           try
/*  656:     */           {
/*  657:1232 */             int index = Integer.parseInt(m.group(1));
/*  658:1233 */             Array.set(field.get(target), index, value);
/*  659:     */           }
/*  660:     */           catch (Exception e)
/*  661:     */           {
/*  662:1240 */             String msg = "unable to set array element";
/*  663:1241 */             InjectFieldException ife = new InjectFieldException(msg, field, target, key);
/*  664:1242 */             ife.initCause(e);
/*  665:1243 */             throw ife;
/*  666:     */           }
/*  667:     */         }
/*  668:     */       }
/*  669:     */     }
/*  670:     */     else
/*  671:     */     {
/*  672:1249 */       Object value = getObject(key, type);
/*  673:1250 */       if (value != null)
/*  674:     */       {
/*  675:1251 */         if (!field.isAccessible()) {
/*  676:1252 */           field.setAccessible(true);
/*  677:     */         }
/*  678:     */         try
/*  679:     */         {
/*  680:1255 */           field.set(target, value);
/*  681:     */         }
/*  682:     */         catch (Exception e)
/*  683:     */         {
/*  684:1261 */           String msg = "unable to set field's value";
/*  685:1262 */           InjectFieldException ife = new InjectFieldException(msg, field, target, key);
/*  686:1263 */           ife.initCause(e);
/*  687:1264 */           throw ife;
/*  688:     */         }
/*  689:     */       }
/*  690:     */     }
/*  691:     */   }
/*  692:     */   
/*  693:     */   public void injectFields(Object target)
/*  694:     */   {
/*  695:1308 */     if (target == null) {
/*  696:1309 */       throw new IllegalArgumentException("null target");
/*  697:     */     }
/*  698:1311 */     Class targetType = target.getClass();
/*  699:1312 */     if (targetType.isArray()) {
/*  700:1313 */       throw new IllegalArgumentException("array target");
/*  701:     */     }
/*  702:1315 */     String keyPrefix = targetType.getSimpleName() + ".";
/*  703:1316 */     for (Field field : targetType.getDeclaredFields())
/*  704:     */     {
/*  705:1317 */       Resource resource = (Resource)field.getAnnotation(Resource.class);
/*  706:1318 */       if (resource != null)
/*  707:     */       {
/*  708:1319 */         String rKey = resource.key();
/*  709:1320 */         String key = keyPrefix + field.getName();
/*  710:1321 */         injectField(field, target, key);
/*  711:     */       }
/*  712:     */     }
/*  713:     */   }
/*  714:     */   
/*  715:     */   static
/*  716:     */   {
/*  717:1330 */     ResourceConverter[] stringConverters = { new ColorStringConverter(), new IconStringConverter(), new ImageStringConverter(), new FontStringConverter(), new KeyStrokeStringConverter(), new DimensionStringConverter(), new PointStringConverter(), new RectangleStringConverter(), new InsetsStringConverter(), new EmptyBorderStringConverter() };
/*  718:1342 */     for (ResourceConverter sc : stringConverters) {
/*  719:1343 */       ResourceConverter.register(sc);
/*  720:     */     }
/*  721:     */   }
/*  722:     */   
/*  723:     */   private static String resourcePath(String path, ResourceMap resourceMap)
/*  724:     */   {
/*  725:1351 */     String rPath = path;
/*  726:1352 */     if (path == null) {
/*  727:1353 */       rPath = null;
/*  728:1355 */     } else if (path.startsWith("/")) {
/*  729:1356 */       rPath = path.length() > 1 ? path.substring(1) : null;
/*  730:     */     } else {
/*  731:1359 */       rPath = resourceMap.getResourcesDir() + path;
/*  732:     */     }
/*  733:1361 */     return rPath;
/*  734:     */   }
/*  735:     */   
/*  736:     */   private static ImageIcon loadImageIcon(String s, ResourceMap resourceMap)
/*  737:     */     throws ResourceConverter.ResourceConverterException
/*  738:     */   {
/*  739:1367 */     String rPath = resourcePath(s, resourceMap);
/*  740:1368 */     if (rPath == null)
/*  741:     */     {
/*  742:1369 */       String msg = String.format("invalid image/icon path \"%s\"", new Object[] { s });
/*  743:1370 */       throw new ResourceConverter.ResourceConverterException(msg, s);
/*  744:     */     }
/*  745:1372 */     URL url = resourceMap.getClassLoader().getResource(rPath);
/*  746:1373 */     if (url != null) {
/*  747:1374 */       return new ImageIcon(url);
/*  748:     */     }
/*  749:1377 */     String msg = String.format("couldn't find Icon resource \"%s\"", new Object[] { s });
/*  750:1378 */     throw new ResourceConverter.ResourceConverterException(msg, s);
/*  751:     */   }
/*  752:     */   
/*  753:     */   private static class FontStringConverter
/*  754:     */     extends ResourceConverter
/*  755:     */   {
/*  756:     */     FontStringConverter()
/*  757:     */     {
/*  758:1384 */       super();
/*  759:     */     }
/*  760:     */     
/*  761:     */     public Object parseString(String s, ResourceMap ignore)
/*  762:     */       throws ResourceConverter.ResourceConverterException
/*  763:     */     {
/*  764:1391 */       return Font.decode(s);
/*  765:     */     }
/*  766:     */   }
/*  767:     */   
/*  768:     */   private static class ColorStringConverter
/*  769:     */     extends ResourceConverter
/*  770:     */   {
/*  771:     */     ColorStringConverter()
/*  772:     */     {
/*  773:1397 */       super();
/*  774:     */     }
/*  775:     */     
/*  776:     */     private void error(String msg, String s, Exception e)
/*  777:     */       throws ResourceConverter.ResourceConverterException
/*  778:     */     {
/*  779:1400 */       throw new ResourceConverter.ResourceConverterException(msg, s, e);
/*  780:     */     }
/*  781:     */     
/*  782:     */     private void error(String msg, String s)
/*  783:     */       throws ResourceConverter.ResourceConverterException
/*  784:     */     {
/*  785:1404 */       error(msg, s, null);
/*  786:     */     }
/*  787:     */     
/*  788:     */     public Object parseString(String s, ResourceMap ignore)
/*  789:     */       throws ResourceConverter.ResourceConverterException
/*  790:     */     {
/*  791:1414 */       Color color = null;
/*  792:1415 */       if (s.startsWith("#"))
/*  793:     */       {
/*  794:1416 */         switch (s.length())
/*  795:     */         {
/*  796:     */         case 7: 
/*  797:1419 */           color = Color.decode(s);
/*  798:1420 */           break;
/*  799:     */         case 9: 
/*  800:1423 */           int alpha = Integer.decode(s.substring(0, 3)).intValue();
/*  801:1424 */           int rgb = Integer.decode("#" + s.substring(3)).intValue();
/*  802:1425 */           color = new Color(alpha << 24 | rgb, true);
/*  803:1426 */           break;
/*  804:     */         default: 
/*  805:1428 */           throw new ResourceConverter.ResourceConverterException("invalid #RRGGBB or #AARRGGBB color string", s);
/*  806:     */         }
/*  807:     */       }
/*  808:     */       else
/*  809:     */       {
/*  810:1432 */         String[] parts = s.split(",");
/*  811:1433 */         if ((parts.length < 3) || (parts.length > 4)) {
/*  812:1434 */           throw new ResourceConverter.ResourceConverterException("invalid R, G, B[, A] color string", s);
/*  813:     */         }
/*  814:     */         try
/*  815:     */         {
/*  816:1438 */           if (parts.length == 4)
/*  817:     */           {
/*  818:1439 */             int r = Integer.parseInt(parts[0].trim());
/*  819:1440 */             int g = Integer.parseInt(parts[1].trim());
/*  820:1441 */             int b = Integer.parseInt(parts[2].trim());
/*  821:1442 */             int a = Integer.parseInt(parts[3].trim());
/*  822:1443 */             color = new Color(r, g, b, a);
/*  823:     */           }
/*  824:     */           else
/*  825:     */           {
/*  826:1445 */             int r = Integer.parseInt(parts[0].trim());
/*  827:1446 */             int g = Integer.parseInt(parts[1].trim());
/*  828:1447 */             int b = Integer.parseInt(parts[2].trim());
/*  829:1448 */             color = new Color(r, g, b);
/*  830:     */           }
/*  831:     */         }
/*  832:     */         catch (NumberFormatException e)
/*  833:     */         {
/*  834:1452 */           throw new ResourceConverter.ResourceConverterException("invalid R, G, B[, A] color string", s, e);
/*  835:     */         }
/*  836:     */       }
/*  837:1455 */       return color;
/*  838:     */     }
/*  839:     */   }
/*  840:     */   
/*  841:     */   private static class IconStringConverter
/*  842:     */     extends ResourceConverter
/*  843:     */   {
/*  844:     */     IconStringConverter()
/*  845:     */     {
/*  846:1461 */       super();
/*  847:     */     }
/*  848:     */     
/*  849:     */     public Object parseString(String s, ResourceMap resourceMap)
/*  850:     */       throws ResourceConverter.ResourceConverterException
/*  851:     */     {
/*  852:1465 */       return ResourceMap.loadImageIcon(s, resourceMap);
/*  853:     */     }
/*  854:     */     
/*  855:     */     public boolean supportsType(Class testType)
/*  856:     */     {
/*  857:1469 */       return (testType.equals(Icon.class)) || (testType.equals(ImageIcon.class));
/*  858:     */     }
/*  859:     */   }
/*  860:     */   
/*  861:     */   private static class ImageStringConverter
/*  862:     */     extends ResourceConverter
/*  863:     */   {
/*  864:     */     ImageStringConverter()
/*  865:     */     {
/*  866:1475 */       super();
/*  867:     */     }
/*  868:     */     
/*  869:     */     public Object parseString(String s, ResourceMap resourceMap)
/*  870:     */       throws ResourceConverter.ResourceConverterException
/*  871:     */     {
/*  872:1479 */       return ResourceMap.loadImageIcon(s, resourceMap).getImage();
/*  873:     */     }
/*  874:     */   }
/*  875:     */   
/*  876:     */   private static class KeyStrokeStringConverter
/*  877:     */     extends ResourceConverter
/*  878:     */   {
/*  879:     */     KeyStrokeStringConverter()
/*  880:     */     {
/*  881:1485 */       super();
/*  882:     */     }
/*  883:     */     
/*  884:     */     public Object parseString(String s, ResourceMap ignore)
/*  885:     */     {
/*  886:1489 */       if (s.contains("shortcut"))
/*  887:     */       {
/*  888:1490 */         int k = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/*  889:1491 */         s = s.replaceAll("shortcut", k == 4 ? "meta" : "control");
/*  890:     */       }
/*  891:1493 */       return KeyStroke.getKeyStroke(s);
/*  892:     */     }
/*  893:     */   }
/*  894:     */   
/*  895:     */   private static List<Double> parseDoubles(String s, int n, String errorMsg)
/*  896:     */     throws ResourceConverter.ResourceConverterException
/*  897:     */   {
/*  898:1503 */     String[] doubleStrings = s.split(",", n + 1);
/*  899:1504 */     if (doubleStrings.length != n) {
/*  900:1505 */       throw new ResourceConverter.ResourceConverterException(errorMsg, s);
/*  901:     */     }
/*  902:1508 */     List<Double> doubles = new ArrayList(n);
/*  903:1509 */     for (String doubleString : doubleStrings) {
/*  904:     */       try
/*  905:     */       {
/*  906:1511 */         doubles.add(Double.valueOf(doubleString));
/*  907:     */       }
/*  908:     */       catch (NumberFormatException e)
/*  909:     */       {
/*  910:1514 */         throw new ResourceConverter.ResourceConverterException(errorMsg, s, e);
/*  911:     */       }
/*  912:     */     }
/*  913:1517 */     return doubles;
/*  914:     */   }
/*  915:     */   
/*  916:     */   private static class DimensionStringConverter
/*  917:     */     extends ResourceConverter
/*  918:     */   {
/*  919:     */     DimensionStringConverter()
/*  920:     */     {
/*  921:1523 */       super();
/*  922:     */     }
/*  923:     */     
/*  924:     */     public Object parseString(String s, ResourceMap ignore)
/*  925:     */       throws ResourceConverter.ResourceConverterException
/*  926:     */     {
/*  927:1526 */       List<Double> xy = ResourceMap.parseDoubles(s, 2, "invalid x,y Dimension string");
/*  928:1527 */       Dimension d = new Dimension();
/*  929:1528 */       d.setSize(((Double)xy.get(0)).doubleValue(), ((Double)xy.get(1)).doubleValue());
/*  930:1529 */       return d;
/*  931:     */     }
/*  932:     */   }
/*  933:     */   
/*  934:     */   private static class PointStringConverter
/*  935:     */     extends ResourceConverter
/*  936:     */   {
/*  937:     */     PointStringConverter()
/*  938:     */     {
/*  939:1535 */       super();
/*  940:     */     }
/*  941:     */     
/*  942:     */     public Object parseString(String s, ResourceMap ignore)
/*  943:     */       throws ResourceConverter.ResourceConverterException
/*  944:     */     {
/*  945:1538 */       List<Double> xy = ResourceMap.parseDoubles(s, 2, "invalid x,y Point string");
/*  946:1539 */       Point p = new Point();
/*  947:1540 */       p.setLocation(((Double)xy.get(0)).doubleValue(), ((Double)xy.get(1)).doubleValue());
/*  948:1541 */       return p;
/*  949:     */     }
/*  950:     */   }
/*  951:     */   
/*  952:     */   private static class RectangleStringConverter
/*  953:     */     extends ResourceConverter
/*  954:     */   {
/*  955:     */     RectangleStringConverter()
/*  956:     */     {
/*  957:1547 */       super();
/*  958:     */     }
/*  959:     */     
/*  960:     */     public Object parseString(String s, ResourceMap ignore)
/*  961:     */       throws ResourceConverter.ResourceConverterException
/*  962:     */     {
/*  963:1550 */       List<Double> xywh = ResourceMap.parseDoubles(s, 4, "invalid x,y,width,height Rectangle string");
/*  964:1551 */       Rectangle r = new Rectangle();
/*  965:1552 */       r.setFrame(((Double)xywh.get(0)).doubleValue(), ((Double)xywh.get(1)).doubleValue(), ((Double)xywh.get(2)).doubleValue(), ((Double)xywh.get(3)).doubleValue());
/*  966:1553 */       return r;
/*  967:     */     }
/*  968:     */   }
/*  969:     */   
/*  970:     */   private static class InsetsStringConverter
/*  971:     */     extends ResourceConverter
/*  972:     */   {
/*  973:     */     InsetsStringConverter()
/*  974:     */     {
/*  975:1559 */       super();
/*  976:     */     }
/*  977:     */     
/*  978:     */     public Object parseString(String s, ResourceMap ignore)
/*  979:     */       throws ResourceConverter.ResourceConverterException
/*  980:     */     {
/*  981:1562 */       List<Double> tlbr = ResourceMap.parseDoubles(s, 4, "invalid top,left,bottom,right Insets string");
/*  982:1563 */       return new Insets(((Double)tlbr.get(0)).intValue(), ((Double)tlbr.get(1)).intValue(), ((Double)tlbr.get(2)).intValue(), ((Double)tlbr.get(3)).intValue());
/*  983:     */     }
/*  984:     */   }
/*  985:     */   
/*  986:     */   private static class EmptyBorderStringConverter
/*  987:     */     extends ResourceConverter
/*  988:     */   {
/*  989:     */     EmptyBorderStringConverter()
/*  990:     */     {
/*  991:1569 */       super();
/*  992:     */     }
/*  993:     */     
/*  994:     */     public Object parseString(String s, ResourceMap ignore)
/*  995:     */       throws ResourceConverter.ResourceConverterException
/*  996:     */     {
/*  997:1572 */       List<Double> tlbr = ResourceMap.parseDoubles(s, 4, "invalid top,left,bottom,right EmptyBorder string");
/*  998:1573 */       return new EmptyBorder(((Double)tlbr.get(0)).intValue(), ((Double)tlbr.get(1)).intValue(), ((Double)tlbr.get(2)).intValue(), ((Double)tlbr.get(3)).intValue());
/*  999:     */     }
/* 1000:     */   }
/* 1001:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ResourceMap
 * JD-Core Version:    0.7.0.1
 */