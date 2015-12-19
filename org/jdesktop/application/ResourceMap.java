/*      */ package org.jdesktop.application;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.beans.BeanInfo;
/*      */ import java.beans.IntrospectionException;
/*      */ import java.beans.Introspector;
/*      */ import java.beans.PropertyDescriptor;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ResourceMap
/*      */ {
/*   94 */   private static Logger logger = Logger.getLogger(ResourceMap.class.getName());
/*   95 */   private static final Object nullResource = new String("null resource");
/*      */   private final ClassLoader classLoader;
/*      */   private final ResourceMap parent;
/*      */   private final List<String> bundleNames;
/*      */   private final String resourcesDir;
/*  100 */   private Map<String, Object> bundlesMapP = null;
/*  101 */   private Locale locale = Locale.getDefault();
/*  102 */   private Set<String> bundlesMapKeysP = null;
/*  103 */   private boolean bundlesLoaded = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ResourceMap(ResourceMap parent, ClassLoader classLoader, List<String> bundleNames)
/*      */   {
/*  151 */     if (classLoader == null) {
/*  152 */       throw new IllegalArgumentException("null ClassLoader");
/*      */     }
/*  154 */     if ((bundleNames == null) || (bundleNames.size() == 0)) {
/*  155 */       throw new IllegalArgumentException("no bundle specified");
/*      */     }
/*  157 */     for (String bn : bundleNames) {
/*  158 */       if ((bn == null) || (bn.length() == 0)) {
/*  159 */         throw new IllegalArgumentException("invalid bundleName: \"" + bn + "\"");
/*      */       }
/*      */     }
/*  162 */     String bpn = bundlePackageName((String)bundleNames.get(0));
/*  163 */     for (String bn : bundleNames) {
/*  164 */       if (!bpn.equals(bundlePackageName(bn))) {
/*  165 */         throw new IllegalArgumentException("bundles not colocated: \"" + bn + "\" != \"" + bpn + "\"");
/*      */       }
/*      */     }
/*  168 */     this.parent = parent;
/*  169 */     this.classLoader = classLoader;
/*  170 */     this.bundleNames = Collections.unmodifiableList(new ArrayList(bundleNames));
/*  171 */     this.resourcesDir = (bpn.replace(".", "/") + "/");
/*      */   }
/*      */   
/*      */   private String bundlePackageName(String bundleName) {
/*  175 */     int i = bundleName.lastIndexOf(".");
/*  176 */     return i == -1 ? "" : bundleName.substring(0, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ResourceMap(ResourceMap parent, ClassLoader classLoader, String... bundleNames)
/*      */   {
/*  185 */     this(parent, classLoader, Arrays.asList(bundleNames));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ResourceMap getParent()
/*      */   {
/*  196 */     return this.parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getBundleNames()
/*      */   {
/*  206 */     return this.bundleNames;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ClassLoader getClassLoader()
/*      */   {
/*  216 */     return this.classLoader;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getResourcesDir()
/*      */   {
/*  232 */     return this.resourcesDir;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized Map<String, Object> getBundlesMap()
/*      */   {
/*  241 */     Locale defaultLocale = Locale.getDefault();
/*  242 */     if (this.locale != defaultLocale) {
/*  243 */       this.bundlesLoaded = false;
/*  244 */       this.locale = defaultLocale;
/*      */     }
/*  246 */     if (!this.bundlesLoaded) {
/*  247 */       Map<String, Object> bundlesMap = new ConcurrentHashMap();
/*  248 */       for (int i = this.bundleNames.size() - 1; i >= 0; i--) {
/*      */         try {
/*  250 */           String bundleName = (String)this.bundleNames.get(i);
/*  251 */           ResourceBundle bundle = ResourceBundle.getBundle(bundleName, this.locale, this.classLoader);
/*  252 */           Enumeration<String> keys = bundle.getKeys();
/*  253 */           while (keys.hasMoreElements()) {
/*  254 */             String key = (String)keys.nextElement();
/*  255 */             bundlesMap.put(key, bundle.getObject(key));
/*      */           }
/*      */         }
/*      */         catch (MissingResourceException ignore) {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  264 */       this.bundlesMapP = bundlesMap;
/*  265 */       this.bundlesLoaded = true;
/*      */     }
/*  267 */     return this.bundlesMapP;
/*      */   }
/*      */   
/*      */   private void checkNullKey(String key) {
/*  271 */     if (key == null) {
/*  272 */       throw new IllegalArgumentException("null key");
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized Set<String> getBundlesMapKeys() {
/*  277 */     if (this.bundlesMapKeysP == null) {
/*  278 */       Set<String> allKeys = new HashSet(getResourceKeySet());
/*  279 */       ResourceMap parent = getParent();
/*  280 */       if (parent != null) {
/*  281 */         allKeys.addAll(parent.keySet());
/*      */       }
/*  283 */       this.bundlesMapKeysP = Collections.unmodifiableSet(allKeys);
/*      */     }
/*  285 */     return this.bundlesMapKeysP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<String> keySet()
/*      */   {
/*  296 */     return getBundlesMapKeys();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsKey(String key)
/*      */   {
/*  308 */     checkNullKey(key);
/*  309 */     if (containsResourceKey(key)) {
/*  310 */       return true;
/*      */     }
/*      */     
/*  313 */     ResourceMap parent = getParent();
/*  314 */     return parent != null ? parent.containsKey(key) : false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class LookupException
/*      */     extends RuntimeException
/*      */   {
/*      */     private final Class type;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final String key;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public LookupException(String msg, String key, Class type)
/*      */     {
/*  339 */       super();
/*  340 */       this.key = key;
/*  341 */       this.type = type;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Class getType()
/*      */     {
/*  349 */       return this.type;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getKey()
/*      */     {
/*  357 */       return this.key;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Set<String> getResourceKeySet()
/*      */   {
/*  378 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  379 */     if (bundlesMap == null) {
/*  380 */       return Collections.emptySet();
/*      */     }
/*      */     
/*  383 */     return bundlesMap.keySet();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean containsResourceKey(String key)
/*      */   {
/*  407 */     checkNullKey(key);
/*  408 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  409 */     return (bundlesMap != null) && (bundlesMap.containsKey(key));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object getResource(String key)
/*      */   {
/*  435 */     checkNullKey(key);
/*  436 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  437 */     Object value = bundlesMap != null ? bundlesMap.get(key) : null;
/*  438 */     return value == nullResource ? null : value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void putResource(String key, Object value)
/*      */   {
/*  462 */     checkNullKey(key);
/*  463 */     Map<String, Object> bundlesMap = getBundlesMap();
/*  464 */     if (bundlesMap != null) {
/*  465 */       bundlesMap.put(key, value == null ? nullResource : value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getObject(String key, Class type)
/*      */   {
/*  521 */     checkNullKey(key);
/*  522 */     if (type == null) {
/*  523 */       throw new IllegalArgumentException("null type");
/*      */     }
/*  525 */     if (type.isPrimitive()) {
/*  526 */       if (type == Boolean.TYPE) { type = Boolean.class;
/*  527 */       } else if (type == Character.TYPE) { type = Character.class;
/*  528 */       } else if (type == Byte.TYPE) { type = Byte.class;
/*  529 */       } else if (type == Short.TYPE) { type = Short.class;
/*  530 */       } else if (type == Integer.TYPE) { type = Integer.class;
/*  531 */       } else if (type == Long.TYPE) { type = Long.class;
/*  532 */       } else if (type == Float.TYPE) { type = Float.class;
/*  533 */       } else if (type == Double.TYPE) type = Double.class;
/*      */     }
/*  535 */     Object value = null;
/*  536 */     ResourceMap resourceMapNode = this;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  541 */     while (resourceMapNode != null) {
/*  542 */       if (resourceMapNode.containsResourceKey(key)) {
/*  543 */         value = resourceMapNode.getResource(key);
/*  544 */         break;
/*      */       }
/*  546 */       resourceMapNode = resourceMapNode.getParent();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  552 */     if (((value instanceof String)) && (((String)value).contains("${"))) {
/*  553 */       value = evaluateStringExpression((String)value);
/*  554 */       resourceMapNode.putResource(key, value);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  565 */     if (value != null) {
/*  566 */       Class valueClass = value.getClass();
/*  567 */       if (!type.isAssignableFrom(valueClass)) {
/*  568 */         if ((value instanceof String)) {
/*  569 */           ResourceConverter stringConverter = ResourceConverter.forType(type);
/*  570 */           if (stringConverter != null) {
/*  571 */             String sValue = (String)value;
/*      */             try {
/*  573 */               value = stringConverter.parseString(sValue, resourceMapNode);
/*  574 */               resourceMapNode.putResource(key, value);
/*      */             }
/*      */             catch (ResourceConverter.ResourceConverterException e) {
/*  577 */               String msg = "string conversion failed";
/*  578 */               LookupException lfe = new LookupException(msg, key, type);
/*  579 */               lfe.initCause(e);
/*  580 */               throw lfe;
/*      */             }
/*      */           }
/*      */           else {
/*  584 */             String msg = "no StringConverter for required type";
/*  585 */             throw new LookupException(msg, key, type);
/*      */           }
/*      */         }
/*      */         else {
/*  589 */           String msg = "named resource has wrong type";
/*  590 */           throw new LookupException(msg, key, type);
/*      */         }
/*      */       }
/*      */     }
/*  594 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String evaluateStringExpression(String expr)
/*      */   {
/*  607 */     if (expr.trim().equals("${null}")) {
/*  608 */       return null;
/*      */     }
/*  610 */     StringBuffer value = new StringBuffer();
/*  611 */     int i0 = 0;int i1 = 0;
/*  612 */     while ((i1 = expr.indexOf("${", i0)) != -1) {
/*  613 */       if ((i1 == 0) || ((i1 > 0) && (expr.charAt(i1 - 1) != '\\'))) {
/*  614 */         int i2 = expr.indexOf("}", i1);
/*  615 */         if ((i2 != -1) && (i2 > i1 + 2)) {
/*  616 */           String k = expr.substring(i1 + 2, i2);
/*  617 */           String v = getString(k, new Object[0]);
/*  618 */           value.append(expr.substring(i0, i1));
/*  619 */           if (v != null) {
/*  620 */             value.append(v);
/*      */           }
/*      */           else {
/*  623 */             String msg = String.format("no value for \"%s\" in \"%s\"", new Object[] { k, expr });
/*  624 */             throw new LookupException(msg, k, String.class);
/*      */           }
/*  626 */           i0 = i2 + 1;
/*      */         }
/*      */         else {
/*  629 */           String msg = String.format("no closing brace in \"%s\"", new Object[] { expr });
/*  630 */           throw new LookupException(msg, "<not found>", String.class);
/*      */         }
/*      */       }
/*      */       else {
/*  634 */         value.append(expr.substring(i0, i1 - 1));
/*  635 */         value.append("${");
/*  636 */         i0 = i1 + 2;
/*      */       }
/*      */     }
/*  639 */     value.append(expr.substring(i0));
/*  640 */     return value.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getString(String key, Object... args)
/*      */   {
/*  665 */     if (args.length == 0) {
/*  666 */       return (String)getObject(key, String.class);
/*      */     }
/*      */     
/*  669 */     String format = (String)getObject(key, String.class);
/*  670 */     return format == null ? null : String.format(format, args);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Boolean getBoolean(String key)
/*      */   {
/*  685 */     return (Boolean)getObject(key, Boolean.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Integer getInteger(String key)
/*      */   {
/*  699 */     return (Integer)getObject(key, Integer.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Long getLong(String key)
/*      */   {
/*  713 */     return (Long)getObject(key, Long.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Short getShort(String key)
/*      */   {
/*  727 */     return (Short)getObject(key, Short.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Byte getByte(String key)
/*      */   {
/*  741 */     return (Byte)getObject(key, Byte.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Float getFloat(String key)
/*      */   {
/*  755 */     return (Float)getObject(key, Float.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Double getDouble(String key)
/*      */   {
/*  769 */     return (Double)getObject(key, Double.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Icon getIcon(String key)
/*      */   {
/*  787 */     return (Icon)getObject(key, Icon.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ImageIcon getImageIcon(String key)
/*      */   {
/*  826 */     return (ImageIcon)getObject(key, ImageIcon.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Font getFont(String key)
/*      */   {
/*  852 */     return (Font)getObject(key, Font.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Color getColor(String key)
/*      */   {
/*  883 */     return (Color)getObject(key, Color.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final KeyStroke getKeyStroke(String key)
/*      */   {
/*  902 */     return (KeyStroke)getObject(key, KeyStroke.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Integer getKeyCode(String key)
/*      */   {
/*  917 */     KeyStroke ks = getKeyStroke(key);
/*  918 */     return ks != null ? new Integer(ks.getKeyCode()) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class PropertyInjectionException
/*      */     extends RuntimeException
/*      */   {
/*      */     private final String key;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Component component;
/*      */     
/*      */ 
/*      */ 
/*      */     private final String propertyName;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public PropertyInjectionException(String msg, String key, Component component, String propertyName)
/*      */     {
/*  944 */       super();
/*  945 */       this.key = key;
/*  946 */       this.component = component;
/*  947 */       this.propertyName = propertyName;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getKey()
/*      */     {
/*  955 */       return this.key;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Component getComponent()
/*      */     {
/*  963 */       return this.component;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getPropertyName()
/*      */     {
/*  971 */       return this.propertyName;
/*      */     }
/*      */   }
/*      */   
/*      */   private void injectComponentProperty(Component component, PropertyDescriptor pd, String key) {
/*  976 */     Method setter = pd.getWriteMethod();
/*  977 */     Class type = pd.getPropertyType();
/*  978 */     if ((setter != null) && (type != null) && (containsKey(key))) {
/*  979 */       Object value = getObject(key, type);
/*  980 */       String propertyName = pd.getName();
/*      */       
/*      */       try
/*      */       {
/*  984 */         if (("text".equals(propertyName)) && ((component instanceof AbstractButton))) {
/*  985 */           MnemonicText.configure(component, (String)value);
/*      */         }
/*  987 */         else if (("text".equals(propertyName)) && ((component instanceof JLabel))) {
/*  988 */           MnemonicText.configure(component, (String)value);
/*      */         }
/*      */         else {
/*  991 */           setter.invoke(component, new Object[] { value });
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  995 */         String pdn = pd.getName();
/*  996 */         String msg = "property setter failed";
/*  997 */         RuntimeException re = new PropertyInjectionException(msg, key, component, pdn);
/*  998 */         re.initCause(e);
/*  999 */         throw re;
/*      */       }
/*      */     } else {
/* 1002 */       if (type != null) {
/* 1003 */         String pdn = pd.getName();
/* 1004 */         String msg = "no value specified for resource";
/* 1005 */         throw new PropertyInjectionException(msg, key, component, pdn);
/*      */       }
/* 1007 */       if (setter == null) {
/* 1008 */         String pdn = pd.getName();
/* 1009 */         String msg = "can't set read-only property";
/* 1010 */         throw new PropertyInjectionException(msg, key, component, pdn);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1015 */   private void injectComponentProperties(Component component) { String componentName = component.getName();
/* 1016 */     PropertyDescriptor[] pds; if (componentName != null)
/*      */     {
/*      */ 
/*      */ 
/* 1020 */       boolean matchingResourceFound = false;
/* 1021 */       for (String key : keySet()) {
/* 1022 */         int i = key.lastIndexOf(".");
/* 1023 */         if ((i != -1) && (componentName.equals(key.substring(0, i)))) {
/* 1024 */           matchingResourceFound = true;
/* 1025 */           break;
/*      */         }
/*      */       }
/* 1028 */       if (!matchingResourceFound) {
/* 1029 */         return;
/*      */       }
/* 1031 */       BeanInfo beanInfo = null;
/*      */       try {
/* 1033 */         beanInfo = Introspector.getBeanInfo(component.getClass());
/*      */       }
/*      */       catch (IntrospectionException e) {
/* 1036 */         String msg = "introspection failed";
/* 1037 */         RuntimeException re = new PropertyInjectionException(msg, null, component, null);
/* 1038 */         re.initCause(e);
/* 1039 */         throw re;
/*      */       }
/* 1041 */       pds = beanInfo.getPropertyDescriptors();
/* 1042 */       if ((pds != null) && (pds.length > 0)) {
/* 1043 */         for (String key : keySet()) {
/* 1044 */           int i = key.lastIndexOf(".");
/* 1045 */           String keyComponentName = i == -1 ? null : key.substring(0, i);
/* 1046 */           if (componentName.equals(keyComponentName)) {
/* 1047 */             if (i + 1 == key.length())
/*      */             {
/*      */ 
/*      */ 
/* 1051 */               String msg = "component resource lacks property name suffix";
/* 1052 */               logger.warning(msg);
/* 1053 */               break;
/*      */             }
/* 1055 */             String propertyName = key.substring(i + 1);
/* 1056 */             boolean matchingPropertyFound = false;
/* 1057 */             for (PropertyDescriptor pd : pds) {
/* 1058 */               if (pd.getName().equals(propertyName)) {
/* 1059 */                 injectComponentProperty(component, pd, key);
/* 1060 */                 matchingPropertyFound = true;
/* 1061 */                 break;
/*      */               }
/*      */             }
/* 1064 */             if (!matchingPropertyFound) {
/* 1065 */               String msg = String.format("[resource %s] component named %s doesn't have a property named %s", new Object[] { key, componentName, propertyName });
/*      */               
/*      */ 
/* 1068 */               logger.warning(msg);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void injectComponent(Component target)
/*      */   {
/* 1122 */     if (target == null) {
/* 1123 */       throw new IllegalArgumentException("null target");
/*      */     }
/* 1125 */     injectComponentProperties(target);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void injectComponents(Component root)
/*      */   {
/* 1139 */     injectComponent(root);
/* 1140 */     if ((root instanceof JMenu))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1147 */       JMenu menu = (JMenu)root;
/* 1148 */       for (Component child : menu.getMenuComponents()) {
/* 1149 */         injectComponents(child);
/*      */       }
/*      */     }
/* 1152 */     else if ((root instanceof Container)) {
/* 1153 */       Container container = (Container)root;
/* 1154 */       for (Component child : container.getComponents()) {
/* 1155 */         injectComponents(child);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class InjectFieldException
/*      */     extends RuntimeException
/*      */   {
/*      */     private final Field field;
/*      */     
/*      */ 
/*      */ 
/*      */     private final Object target;
/*      */     
/*      */ 
/*      */ 
/*      */     private final String key;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public InjectFieldException(String msg, Field field, Object target, String key)
/*      */     {
/* 1182 */       super();
/* 1183 */       this.field = field;
/* 1184 */       this.target = target;
/* 1185 */       this.key = key;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Field getField()
/*      */     {
/* 1193 */       return this.field;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Object getTarget()
/*      */     {
/* 1201 */       return this.target;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getKey()
/*      */     {
/* 1209 */       return this.key;
/*      */     }
/*      */   }
/*      */   
/*      */   private void injectField(Field field, Object target, String key) {
/* 1214 */     Class type = field.getType();
/* 1215 */     Pattern p; if (type.isArray()) {
/* 1216 */       type = type.getComponentType();
/* 1217 */       p = Pattern.compile(key + "\\[([\\d]+)\\]");
/* 1218 */       List<String> arrayKeys = new ArrayList();
/* 1219 */       for (String arrayElementKey : keySet()) {
/* 1220 */         Matcher m = p.matcher(arrayElementKey);
/* 1221 */         if (m.matches())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1227 */           Object value = getObject(arrayElementKey, type);
/* 1228 */           if (!field.isAccessible()) {
/* 1229 */             field.setAccessible(true);
/*      */           }
/*      */           try {
/* 1232 */             int index = Integer.parseInt(m.group(1));
/* 1233 */             Array.set(field.get(target), index, value);
/*      */ 
/*      */ 
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*      */ 
/* 1240 */             String msg = "unable to set array element";
/* 1241 */             InjectFieldException ife = new InjectFieldException(msg, field, target, key);
/* 1242 */             ife.initCause(e);
/* 1243 */             throw ife;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 1249 */       Object value = getObject(key, type);
/* 1250 */       if (value != null) {
/* 1251 */         if (!field.isAccessible()) {
/* 1252 */           field.setAccessible(true);
/*      */         }
/*      */         try {
/* 1255 */           field.set(target, value);
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/* 1261 */           String msg = "unable to set field's value";
/* 1262 */           InjectFieldException ife = new InjectFieldException(msg, field, target, key);
/* 1263 */           ife.initCause(e);
/* 1264 */           throw ife;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void injectFields(Object target)
/*      */   {
/* 1308 */     if (target == null) {
/* 1309 */       throw new IllegalArgumentException("null target");
/*      */     }
/* 1311 */     Class targetType = target.getClass();
/* 1312 */     if (targetType.isArray()) {
/* 1313 */       throw new IllegalArgumentException("array target");
/*      */     }
/* 1315 */     String keyPrefix = targetType.getSimpleName() + ".";
/* 1316 */     for (Field field : targetType.getDeclaredFields()) {
/* 1317 */       Resource resource = (Resource)field.getAnnotation(Resource.class);
/* 1318 */       if (resource != null) {
/* 1319 */         String rKey = resource.key();
/* 1320 */         String key = keyPrefix + field.getName();
/* 1321 */         injectField(field, target, key);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/* 1330 */     ResourceConverter[] stringConverters = { new ColorStringConverter(), new IconStringConverter(), new ImageStringConverter(), new FontStringConverter(), new KeyStrokeStringConverter(), new DimensionStringConverter(), new PointStringConverter(), new RectangleStringConverter(), new InsetsStringConverter(), new EmptyBorderStringConverter() };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1342 */     for (ResourceConverter sc : stringConverters) {
/* 1343 */       ResourceConverter.register(sc);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String resourcePath(String path, ResourceMap resourceMap)
/*      */   {
/* 1351 */     String rPath = path;
/* 1352 */     if (path == null) {
/* 1353 */       rPath = null;
/*      */     }
/* 1355 */     else if (path.startsWith("/")) {
/* 1356 */       rPath = path.length() > 1 ? path.substring(1) : null;
/*      */     }
/*      */     else {
/* 1359 */       rPath = resourceMap.getResourcesDir() + path;
/*      */     }
/* 1361 */     return rPath;
/*      */   }
/*      */   
/*      */   private static ImageIcon loadImageIcon(String s, ResourceMap resourceMap)
/*      */     throws ResourceConverter.ResourceConverterException
/*      */   {
/* 1367 */     String rPath = resourcePath(s, resourceMap);
/* 1368 */     if (rPath == null) {
/* 1369 */       String msg = String.format("invalid image/icon path \"%s\"", new Object[] { s });
/* 1370 */       throw new ResourceConverter.ResourceConverterException(msg, s);
/*      */     }
/* 1372 */     URL url = resourceMap.getClassLoader().getResource(rPath);
/* 1373 */     if (url != null) {
/* 1374 */       return new ImageIcon(url);
/*      */     }
/*      */     
/* 1377 */     String msg = String.format("couldn't find Icon resource \"%s\"", new Object[] { s });
/* 1378 */     throw new ResourceConverter.ResourceConverterException(msg, s);
/*      */   }
/*      */   
/*      */   private static class FontStringConverter extends ResourceConverter
/*      */   {
/*      */     FontStringConverter() {
/* 1384 */       super();
/*      */     }
/*      */     
/*      */ 
/*      */     public Object parseString(String s, ResourceMap ignore)
/*      */       throws ResourceConverter.ResourceConverterException
/*      */     {
/* 1391 */       return Font.decode(s);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ColorStringConverter extends ResourceConverter {
/*      */     ColorStringConverter() {
/* 1397 */       super();
/*      */     }
/*      */     
/* 1400 */     private void error(String msg, String s, Exception e) throws ResourceConverter.ResourceConverterException { throw new ResourceConverter.ResourceConverterException(msg, s, e); }
/*      */     
/*      */     private void error(String msg, String s) throws ResourceConverter.ResourceConverterException
/*      */     {
/* 1404 */       error(msg, s, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Object parseString(String s, ResourceMap ignore)
/*      */       throws ResourceConverter.ResourceConverterException
/*      */     {
/* 1414 */       Color color = null;
/* 1415 */       if (s.startsWith("#")) {
/* 1416 */         switch (s.length())
/*      */         {
/*      */         case 7: 
/* 1419 */           color = Color.decode(s);
/* 1420 */           break;
/*      */         
/*      */         case 9: 
/* 1423 */           int alpha = Integer.decode(s.substring(0, 3)).intValue();
/* 1424 */           int rgb = Integer.decode("#" + s.substring(3)).intValue();
/* 1425 */           color = new Color(alpha << 24 | rgb, true);
/* 1426 */           break;
/*      */         default: 
/* 1428 */           throw new ResourceConverter.ResourceConverterException("invalid #RRGGBB or #AARRGGBB color string", s);
/*      */         }
/*      */       }
/*      */       else {
/* 1432 */         String[] parts = s.split(",");
/* 1433 */         if ((parts.length < 3) || (parts.length > 4)) {
/* 1434 */           throw new ResourceConverter.ResourceConverterException("invalid R, G, B[, A] color string", s);
/*      */         }
/*      */         try
/*      */         {
/* 1438 */           if (parts.length == 4) {
/* 1439 */             int r = Integer.parseInt(parts[0].trim());
/* 1440 */             int g = Integer.parseInt(parts[1].trim());
/* 1441 */             int b = Integer.parseInt(parts[2].trim());
/* 1442 */             int a = Integer.parseInt(parts[3].trim());
/* 1443 */             color = new Color(r, g, b, a);
/*      */           } else {
/* 1445 */             int r = Integer.parseInt(parts[0].trim());
/* 1446 */             int g = Integer.parseInt(parts[1].trim());
/* 1447 */             int b = Integer.parseInt(parts[2].trim());
/* 1448 */             color = new Color(r, g, b);
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException e) {
/* 1452 */           throw new ResourceConverter.ResourceConverterException("invalid R, G, B[, A] color string", s, e);
/*      */         }
/*      */       }
/* 1455 */       return color;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class IconStringConverter extends ResourceConverter {
/*      */     IconStringConverter() {
/* 1461 */       super();
/*      */     }
/*      */     
/*      */     public Object parseString(String s, ResourceMap resourceMap) throws ResourceConverter.ResourceConverterException {
/* 1465 */       return ResourceMap.loadImageIcon(s, resourceMap);
/*      */     }
/*      */     
/*      */     public boolean supportsType(Class testType) {
/* 1469 */       return (testType.equals(Icon.class)) || (testType.equals(ImageIcon.class));
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImageStringConverter extends ResourceConverter {
/*      */     ImageStringConverter() {
/* 1475 */       super();
/*      */     }
/*      */     
/*      */     public Object parseString(String s, ResourceMap resourceMap) throws ResourceConverter.ResourceConverterException {
/* 1479 */       return ResourceMap.loadImageIcon(s, resourceMap).getImage();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class KeyStrokeStringConverter extends ResourceConverter {
/*      */     KeyStrokeStringConverter() {
/* 1485 */       super();
/*      */     }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) {
/* 1489 */       if (s.contains("shortcut")) {
/* 1490 */         int k = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/* 1491 */         s = s.replaceAll("shortcut", k == 4 ? "meta" : "control");
/*      */       }
/* 1493 */       return KeyStroke.getKeyStroke(s);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List<Double> parseDoubles(String s, int n, String errorMsg)
/*      */     throws ResourceConverter.ResourceConverterException
/*      */   {
/* 1503 */     String[] doubleStrings = s.split(",", n + 1);
/* 1504 */     if (doubleStrings.length != n) {
/* 1505 */       throw new ResourceConverter.ResourceConverterException(errorMsg, s);
/*      */     }
/*      */     
/* 1508 */     List<Double> doubles = new ArrayList(n);
/* 1509 */     for (String doubleString : doubleStrings) {
/*      */       try {
/* 1511 */         doubles.add(Double.valueOf(doubleString));
/*      */       }
/*      */       catch (NumberFormatException e) {
/* 1514 */         throw new ResourceConverter.ResourceConverterException(errorMsg, s, e);
/*      */       }
/*      */     }
/* 1517 */     return doubles;
/*      */   }
/*      */   
/*      */   private static class DimensionStringConverter
/*      */     extends ResourceConverter
/*      */   {
/* 1523 */     DimensionStringConverter() { super(); }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/* 1526 */       List<Double> xy = ResourceMap.parseDoubles(s, 2, "invalid x,y Dimension string");
/* 1527 */       Dimension d = new Dimension();
/* 1528 */       d.setSize(((Double)xy.get(0)).doubleValue(), ((Double)xy.get(1)).doubleValue());
/* 1529 */       return d;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PointStringConverter
/*      */     extends ResourceConverter {
/* 1535 */     PointStringConverter() { super(); }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/* 1538 */       List<Double> xy = ResourceMap.parseDoubles(s, 2, "invalid x,y Point string");
/* 1539 */       Point p = new Point();
/* 1540 */       p.setLocation(((Double)xy.get(0)).doubleValue(), ((Double)xy.get(1)).doubleValue());
/* 1541 */       return p;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RectangleStringConverter
/*      */     extends ResourceConverter {
/* 1547 */     RectangleStringConverter() { super(); }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/* 1550 */       List<Double> xywh = ResourceMap.parseDoubles(s, 4, "invalid x,y,width,height Rectangle string");
/* 1551 */       Rectangle r = new Rectangle();
/* 1552 */       r.setFrame(((Double)xywh.get(0)).doubleValue(), ((Double)xywh.get(1)).doubleValue(), ((Double)xywh.get(2)).doubleValue(), ((Double)xywh.get(3)).doubleValue());
/* 1553 */       return r;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InsetsStringConverter
/*      */     extends ResourceConverter {
/* 1559 */     InsetsStringConverter() { super(); }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/* 1562 */       List<Double> tlbr = ResourceMap.parseDoubles(s, 4, "invalid top,left,bottom,right Insets string");
/* 1563 */       return new Insets(((Double)tlbr.get(0)).intValue(), ((Double)tlbr.get(1)).intValue(), ((Double)tlbr.get(2)).intValue(), ((Double)tlbr.get(3)).intValue());
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EmptyBorderStringConverter
/*      */     extends ResourceConverter {
/* 1569 */     EmptyBorderStringConverter() { super(); }
/*      */     
/*      */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/* 1572 */       List<Double> tlbr = ResourceMap.parseDoubles(s, 4, "invalid top,left,bottom,right EmptyBorder string");
/* 1573 */       return new EmptyBorder(((Double)tlbr.get(0)).intValue(), ((Double)tlbr.get(1)).intValue(), ((Double)tlbr.get(2)).intValue(), ((Double)tlbr.get(3)).intValue());
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ResourceMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */