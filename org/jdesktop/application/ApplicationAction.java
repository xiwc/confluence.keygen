/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.beans.PropertyChangeEvent;
/*   5:    */ import java.beans.PropertyChangeListener;
/*   6:    */ import java.lang.annotation.Annotation;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.logging.Logger;
/*   9:    */ import javax.swing.AbstractAction;
/*  10:    */ import javax.swing.Action;
/*  11:    */ import javax.swing.ActionMap;
/*  12:    */ import javax.swing.Icon;
/*  13:    */ import javax.swing.KeyStroke;
/*  14:    */ 
/*  15:    */ public class ApplicationAction
/*  16:    */   extends AbstractAction
/*  17:    */ {
/*  18:113 */   private static final Logger logger = Logger.getLogger(ApplicationAction.class.getName());
/*  19:    */   private final ApplicationActionMap appAM;
/*  20:    */   private final ResourceMap resourceMap;
/*  21:    */   private final String actionName;
/*  22:    */   private final Method actionMethod;
/*  23:    */   private final String enabledProperty;
/*  24:    */   private final Method isEnabledMethod;
/*  25:    */   private final Method setEnabledMethod;
/*  26:    */   private final String selectedProperty;
/*  27:    */   private final Method isSelectedMethod;
/*  28:    */   private final Method setSelectedMethod;
/*  29:    */   private final Task.BlockingScope block;
/*  30:125 */   private Action proxy = null;
/*  31:126 */   private Object proxySource = null;
/*  32:127 */   private PropertyChangeListener proxyPCL = null;
/*  33:    */   private static final String SELECTED_KEY = "SwingSelectedKey";
/*  34:    */   private static final String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
/*  35:    */   private static final String LARGE_ICON_KEY = "SwingLargeIconKey";
/*  36:    */   
/*  37:    */   public ApplicationAction(ApplicationActionMap appAM, ResourceMap resourceMap, String baseName, Method actionMethod, String enabledProperty, String selectedProperty, Task.BlockingScope block)
/*  38:    */   {
/*  39:206 */     if (appAM == null) {
/*  40:207 */       throw new IllegalArgumentException("null appAM");
/*  41:    */     }
/*  42:209 */     if (baseName == null) {
/*  43:210 */       throw new IllegalArgumentException("null baseName");
/*  44:    */     }
/*  45:212 */     this.appAM = appAM;
/*  46:213 */     this.resourceMap = resourceMap;
/*  47:214 */     this.actionName = baseName;
/*  48:215 */     this.actionMethod = actionMethod;
/*  49:216 */     this.enabledProperty = enabledProperty;
/*  50:217 */     this.selectedProperty = selectedProperty;
/*  51:218 */     this.block = block;
/*  52:223 */     if (enabledProperty != null)
/*  53:    */     {
/*  54:224 */       this.setEnabledMethod = propertySetMethod(enabledProperty, Boolean.TYPE);
/*  55:225 */       this.isEnabledMethod = propertyGetMethod(enabledProperty);
/*  56:226 */       if (this.isEnabledMethod == null) {
/*  57:227 */         throw newNoSuchPropertyException(enabledProperty);
/*  58:    */       }
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62:231 */       this.isEnabledMethod = null;
/*  63:232 */       this.setEnabledMethod = null;
/*  64:    */     }
/*  65:238 */     if (selectedProperty != null)
/*  66:    */     {
/*  67:239 */       this.setSelectedMethod = propertySetMethod(selectedProperty, Boolean.TYPE);
/*  68:240 */       this.isSelectedMethod = propertyGetMethod(selectedProperty);
/*  69:241 */       if (this.isSelectedMethod == null) {
/*  70:242 */         throw newNoSuchPropertyException(selectedProperty);
/*  71:    */       }
/*  72:244 */       super.putValue("SwingSelectedKey", Boolean.FALSE);
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:247 */       this.isSelectedMethod = null;
/*  77:248 */       this.setSelectedMethod = null;
/*  78:    */     }
/*  79:251 */     if (resourceMap != null) {
/*  80:252 */       initActionProperties(resourceMap, baseName);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   ApplicationAction(ApplicationActionMap appAM, ResourceMap resourceMap, String actionName)
/*  85:    */   {
/*  86:260 */     this(appAM, resourceMap, actionName, null, null, null, Task.BlockingScope.NONE);
/*  87:    */   }
/*  88:    */   
/*  89:    */   private IllegalArgumentException newNoSuchPropertyException(String propertyName)
/*  90:    */   {
/*  91:264 */     String actionsClassName = this.appAM.getActionsClass().getName();
/*  92:265 */     String msg = String.format("no property named %s in %s", new Object[] { propertyName, actionsClassName });
/*  93:266 */     return new IllegalArgumentException(msg);
/*  94:    */   }
/*  95:    */   
/*  96:    */   String getEnabledProperty()
/*  97:    */   {
/*  98:278 */     return this.enabledProperty;
/*  99:    */   }
/* 100:    */   
/* 101:    */   String getSelectedProperty()
/* 102:    */   {
/* 103:289 */     return this.selectedProperty;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Action getProxy()
/* 107:    */   {
/* 108:302 */     return this.proxy;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setProxy(Action proxy)
/* 112:    */   {
/* 113:330 */     Action oldProxy = this.proxy;
/* 114:331 */     this.proxy = proxy;
/* 115:332 */     if (oldProxy != null)
/* 116:    */     {
/* 117:333 */       oldProxy.removePropertyChangeListener(this.proxyPCL);
/* 118:334 */       this.proxyPCL = null;
/* 119:    */     }
/* 120:336 */     if (this.proxy != null)
/* 121:    */     {
/* 122:337 */       updateProxyProperties();
/* 123:338 */       this.proxyPCL = new ProxyPCL(null);
/* 124:339 */       proxy.addPropertyChangeListener(this.proxyPCL);
/* 125:    */     }
/* 126:341 */     else if (oldProxy != null)
/* 127:    */     {
/* 128:342 */       setEnabled(false);
/* 129:343 */       setSelected(false);
/* 130:    */     }
/* 131:345 */     firePropertyChange("proxy", oldProxy, this.proxy);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Object getProxySource()
/* 135:    */   {
/* 136:358 */     return this.proxySource;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setProxySource(Object source)
/* 140:    */   {
/* 141:371 */     Object oldValue = this.proxySource;
/* 142:372 */     this.proxySource = source;
/* 143:373 */     firePropertyChange("proxySource", oldValue, this.proxySource);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void maybePutDescriptionValue(String key, Action proxy)
/* 147:    */   {
/* 148:377 */     Object s = proxy.getValue(key);
/* 149:378 */     if ((s instanceof String)) {
/* 150:379 */       putValue(key, (String)s);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void updateProxyProperties()
/* 155:    */   {
/* 156:384 */     Action proxy = getProxy();
/* 157:385 */     if (proxy != null)
/* 158:    */     {
/* 159:386 */       setEnabled(proxy.isEnabled());
/* 160:387 */       Object s = proxy.getValue("SwingSelectedKey");
/* 161:388 */       setSelected(((s instanceof Boolean)) && (((Boolean)s).booleanValue()));
/* 162:389 */       maybePutDescriptionValue("ShortDescription", proxy);
/* 163:390 */       maybePutDescriptionValue("LongDescription", proxy);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private class ProxyPCL
/* 168:    */     implements PropertyChangeListener
/* 169:    */   {
/* 170:    */     private ProxyPCL() {}
/* 171:    */     
/* 172:    */     public void propertyChange(PropertyChangeEvent e)
/* 173:    */     {
/* 174:401 */       String propertyName = e.getPropertyName();
/* 175:402 */       if ((propertyName == null) || ("enabled".equals(propertyName)) || ("selected".equals(propertyName)) || ("ShortDescription".equals(propertyName)) || ("LongDescription".equals(propertyName))) {
/* 176:407 */         ApplicationAction.this.updateProxyProperties();
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void initActionProperties(ResourceMap resourceMap, String baseName)
/* 182:    */   {
/* 183:424 */     boolean iconOrNameSpecified = false;
/* 184:425 */     String typedName = null;
/* 185:    */     
/* 186:    */ 
/* 187:428 */     String text = resourceMap.getString(baseName + ".Action.text", new Object[0]);
/* 188:429 */     if (text != null)
/* 189:    */     {
/* 190:430 */       MnemonicText.configure(this, text);
/* 191:431 */       iconOrNameSpecified = true;
/* 192:    */     }
/* 193:434 */     Integer mnemonic = resourceMap.getKeyCode(baseName + ".Action.mnemonic");
/* 194:435 */     if (mnemonic != null) {
/* 195:436 */       putValue("MnemonicKey", mnemonic);
/* 196:    */     }
/* 197:439 */     Integer index = resourceMap.getInteger(baseName + ".Action.displayedMnemonicIndex");
/* 198:440 */     if (index != null) {
/* 199:441 */       putValue("SwingDisplayedMnemonicIndexKey", index);
/* 200:    */     }
/* 201:444 */     KeyStroke key = resourceMap.getKeyStroke(baseName + ".Action.accelerator");
/* 202:445 */     if (key != null) {
/* 203:446 */       putValue("AcceleratorKey", key);
/* 204:    */     }
/* 205:449 */     Icon icon = resourceMap.getIcon(baseName + ".Action.icon");
/* 206:450 */     if (icon != null)
/* 207:    */     {
/* 208:451 */       putValue("SmallIcon", icon);
/* 209:452 */       putValue("SwingLargeIconKey", icon);
/* 210:453 */       iconOrNameSpecified = true;
/* 211:    */     }
/* 212:456 */     Icon smallIcon = resourceMap.getIcon(baseName + ".Action.smallIcon");
/* 213:457 */     if (smallIcon != null)
/* 214:    */     {
/* 215:458 */       putValue("SmallIcon", smallIcon);
/* 216:459 */       iconOrNameSpecified = true;
/* 217:    */     }
/* 218:462 */     Icon largeIcon = resourceMap.getIcon(baseName + ".Action.largeIcon");
/* 219:463 */     if (largeIcon != null)
/* 220:    */     {
/* 221:464 */       putValue("SwingLargeIconKey", largeIcon);
/* 222:465 */       iconOrNameSpecified = true;
/* 223:    */     }
/* 224:468 */     putValue("ShortDescription", resourceMap.getString(baseName + ".Action.shortDescription", new Object[0]));
/* 225:    */     
/* 226:    */ 
/* 227:471 */     putValue("LongDescription", resourceMap.getString(baseName + ".Action.longDescription", new Object[0]));
/* 228:    */     
/* 229:    */ 
/* 230:474 */     putValue("ActionCommandKey", resourceMap.getString(baseName + ".Action.command", new Object[0]));
/* 231:478 */     if (!iconOrNameSpecified) {
/* 232:479 */       putValue("Name", this.actionName);
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   private String propertyMethodName(String prefix, String propertyName)
/* 237:    */   {
/* 238:484 */     return prefix + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
/* 239:    */   }
/* 240:    */   
/* 241:    */   private Method propertyGetMethod(String propertyName)
/* 242:    */   {
/* 243:488 */     String[] getMethodNames = { propertyMethodName("is", propertyName), propertyMethodName("get", propertyName) };
/* 244:    */     
/* 245:    */ 
/* 246:    */ 
/* 247:492 */     Class actionsClass = this.appAM.getActionsClass();
/* 248:493 */     for (String name : getMethodNames) {
/* 249:    */       try
/* 250:    */       {
/* 251:495 */         return actionsClass.getMethod(name, new Class[0]);
/* 252:    */       }
/* 253:    */       catch (NoSuchMethodException ignore) {}
/* 254:    */     }
/* 255:499 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private Method propertySetMethod(String propertyName, Class type)
/* 259:    */   {
/* 260:503 */     Class actionsClass = this.appAM.getActionsClass();
/* 261:    */     try
/* 262:    */     {
/* 263:505 */       return actionsClass.getMethod(propertyMethodName("set", propertyName), new Class[] { type });
/* 264:    */     }
/* 265:    */     catch (NoSuchMethodException ignore) {}
/* 266:508 */     return null;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getName()
/* 270:    */   {
/* 271:537 */     return this.actionName;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public ResourceMap getResourceMap()
/* 275:    */   {
/* 276:546 */     return this.resourceMap;
/* 277:    */   }
/* 278:    */   
/* 279:    */   protected Object getActionArgument(Class pType, String pKey, ActionEvent actionEvent)
/* 280:    */   {
/* 281:602 */     Object argument = null;
/* 282:603 */     if (pType == ActionEvent.class)
/* 283:    */     {
/* 284:604 */       argument = actionEvent;
/* 285:    */     }
/* 286:606 */     else if (pType == Action.class)
/* 287:    */     {
/* 288:607 */       argument = this;
/* 289:    */     }
/* 290:609 */     else if (pType == ActionMap.class)
/* 291:    */     {
/* 292:610 */       argument = this.appAM;
/* 293:    */     }
/* 294:612 */     else if (pType == ResourceMap.class)
/* 295:    */     {
/* 296:613 */       argument = this.resourceMap;
/* 297:    */     }
/* 298:615 */     else if (pType == ApplicationContext.class)
/* 299:    */     {
/* 300:616 */       argument = this.appAM.getContext();
/* 301:    */     }
/* 302:618 */     else if (pType == Application.class)
/* 303:    */     {
/* 304:619 */       argument = this.appAM.getContext().getApplication();
/* 305:    */     }
/* 306:    */     else
/* 307:    */     {
/* 308:622 */       Exception e = new IllegalArgumentException("unrecognized @Action method parameter");
/* 309:623 */       actionFailed(actionEvent, e);
/* 310:    */     }
/* 311:625 */     return argument;
/* 312:    */   }
/* 313:    */   
/* 314:    */   private Task.InputBlocker createInputBlocker(Task task, ActionEvent event)
/* 315:    */   {
/* 316:630 */     Object target = event.getSource();
/* 317:631 */     if (this.block == Task.BlockingScope.ACTION) {
/* 318:632 */       target = this;
/* 319:    */     }
/* 320:634 */     return new DefaultInputBlocker(task, this.block, target, this);
/* 321:    */   }
/* 322:    */   
/* 323:    */   private void noProxyActionPerformed(ActionEvent actionEvent)
/* 324:    */   {
/* 325:638 */     Object taskObject = null;
/* 326:    */     
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:643 */     Annotation[][] allPAnnotations = this.actionMethod.getParameterAnnotations();
/* 331:644 */     Class<?>[] pTypes = this.actionMethod.getParameterTypes();
/* 332:645 */     Object[] arguments = new Object[pTypes.length];
/* 333:646 */     for (int i = 0; i < pTypes.length; i++)
/* 334:    */     {
/* 335:647 */       String pKey = null;
/* 336:648 */       for (Annotation pAnnotation : allPAnnotations[i]) {
/* 337:649 */         if ((pAnnotation instanceof Action.Parameter))
/* 338:    */         {
/* 339:650 */           pKey = ((Action.Parameter)pAnnotation).value();
/* 340:651 */           break;
/* 341:    */         }
/* 342:    */       }
/* 343:654 */       arguments[i] = getActionArgument(pTypes[i], pKey, actionEvent);
/* 344:    */     }
/* 345:    */     try
/* 346:    */     {
/* 347:661 */       Object target = this.appAM.getActionsObject();
/* 348:662 */       taskObject = this.actionMethod.invoke(target, arguments);
/* 349:    */     }
/* 350:    */     catch (Exception e)
/* 351:    */     {
/* 352:665 */       actionFailed(actionEvent, e);
/* 353:    */     }
/* 354:668 */     if ((taskObject instanceof Task))
/* 355:    */     {
/* 356:669 */       Task task = (Task)taskObject;
/* 357:670 */       if (task.getInputBlocker() == null) {
/* 358:671 */         task.setInputBlocker(createInputBlocker(task, actionEvent));
/* 359:    */       }
/* 360:673 */       ApplicationContext ctx = this.appAM.getContext();
/* 361:674 */       ctx.getTaskService().execute(task);
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void actionPerformed(ActionEvent actionEvent)
/* 366:    */   {
/* 367:692 */     Action proxy = getProxy();
/* 368:693 */     if (proxy != null)
/* 369:    */     {
/* 370:694 */       actionEvent.setSource(getProxySource());
/* 371:695 */       proxy.actionPerformed(actionEvent);
/* 372:    */     }
/* 373:697 */     else if (this.actionMethod != null)
/* 374:    */     {
/* 375:698 */       noProxyActionPerformed(actionEvent);
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean isEnabled()
/* 380:    */   {
/* 381:715 */     if ((getProxy() != null) || (this.isEnabledMethod == null)) {
/* 382:716 */       return super.isEnabled();
/* 383:    */     }
/* 384:    */     try
/* 385:    */     {
/* 386:720 */       Object b = this.isEnabledMethod.invoke(this.appAM.getActionsObject(), new Object[0]);
/* 387:721 */       return ((Boolean)b).booleanValue();
/* 388:    */     }
/* 389:    */     catch (Exception e)
/* 390:    */     {
/* 391:724 */       throw newInvokeError(this.isEnabledMethod, e, new Object[0]);
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setEnabled(boolean enabled)
/* 396:    */   {
/* 397:742 */     if ((getProxy() != null) || (this.setEnabledMethod == null)) {
/* 398:743 */       super.setEnabled(enabled);
/* 399:    */     } else {
/* 400:    */       try
/* 401:    */       {
/* 402:747 */         this.setEnabledMethod.invoke(this.appAM.getActionsObject(), new Object[] { Boolean.valueOf(enabled) });
/* 403:    */       }
/* 404:    */       catch (Exception e)
/* 405:    */       {
/* 406:750 */         throw newInvokeError(this.setEnabledMethod, e, new Object[] { Boolean.valueOf(enabled) });
/* 407:    */       }
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   public boolean isSelected()
/* 412:    */   {
/* 413:767 */     if ((getProxy() != null) || (this.isSelectedMethod == null))
/* 414:    */     {
/* 415:768 */       Object v = getValue("SwingSelectedKey");
/* 416:769 */       return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : false;
/* 417:    */     }
/* 418:    */     try
/* 419:    */     {
/* 420:773 */       Object b = this.isSelectedMethod.invoke(this.appAM.getActionsObject(), new Object[0]);
/* 421:774 */       return ((Boolean)b).booleanValue();
/* 422:    */     }
/* 423:    */     catch (Exception e)
/* 424:    */     {
/* 425:777 */       throw newInvokeError(this.isSelectedMethod, e, new Object[0]);
/* 426:    */     }
/* 427:    */   }
/* 428:    */   
/* 429:    */   public void setSelected(boolean selected)
/* 430:    */   {
/* 431:795 */     if ((getProxy() != null) || (this.setSelectedMethod == null)) {
/* 432:796 */       super.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/* 433:    */     } else {
/* 434:    */       try
/* 435:    */       {
/* 436:800 */         super.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/* 437:801 */         if (selected != isSelected()) {
/* 438:802 */           this.setSelectedMethod.invoke(this.appAM.getActionsObject(), new Object[] { Boolean.valueOf(selected) });
/* 439:    */         }
/* 440:    */       }
/* 441:    */       catch (Exception e)
/* 442:    */       {
/* 443:806 */         throw newInvokeError(this.setSelectedMethod, e, new Object[] { Boolean.valueOf(selected) });
/* 444:    */       }
/* 445:    */     }
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void putValue(String key, Object value)
/* 449:    */   {
/* 450:819 */     if (("SwingSelectedKey".equals(key)) && ((value instanceof Boolean))) {
/* 451:820 */       setSelected(((Boolean)value).booleanValue());
/* 452:    */     } else {
/* 453:823 */       super.putValue(key, value);
/* 454:    */     }
/* 455:    */   }
/* 456:    */   
/* 457:    */   private Error newInvokeError(Method m, Exception e, Object... args)
/* 458:    */   {
/* 459:831 */     String argsString = args.length == 0 ? "" : args[0].toString();
/* 460:832 */     for (int i = 1; i < args.length; i++) {
/* 461:833 */       argsString = argsString + ", " + args[i];
/* 462:    */     }
/* 463:835 */     String actionsClassName = this.appAM.getActionsObject().getClass().getName();
/* 464:836 */     String msg = String.format("%s.%s(%s) failed", new Object[] { actionsClassName, m, argsString });
/* 465:837 */     return new Error(msg, e);
/* 466:    */   }
/* 467:    */   
/* 468:    */   void forwardPropertyChangeEvent(PropertyChangeEvent e, String actionPropertyName)
/* 469:    */   {
/* 470:847 */     if (("selected".equals(actionPropertyName)) && ((e.getNewValue() instanceof Boolean))) {
/* 471:848 */       putValue("SwingSelectedKey", (Boolean)e.getNewValue());
/* 472:    */     }
/* 473:850 */     firePropertyChange(actionPropertyName, e.getOldValue(), e.getNewValue());
/* 474:    */   }
/* 475:    */   
/* 476:    */   private void actionFailed(ActionEvent actionEvent, Exception e)
/* 477:    */   {
/* 478:859 */     throw new Error(e);
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String toString()
/* 482:    */   {
/* 483:874 */     StringBuilder sb = new StringBuilder();
/* 484:875 */     sb.append(getClass().getName());
/* 485:876 */     sb.append(" ");
/* 486:877 */     boolean enabled = isEnabled();
/* 487:878 */     if (!enabled) {
/* 488:878 */       sb.append("(");
/* 489:    */     }
/* 490:879 */     sb.append(getName());
/* 491:880 */     Object selectedValue = getValue("SwingSelectedKey");
/* 492:881 */     if (((selectedValue instanceof Boolean)) && 
/* 493:882 */       (((Boolean)selectedValue).booleanValue())) {
/* 494:883 */       sb.append("+");
/* 495:    */     }
/* 496:886 */     if (!enabled) {
/* 497:886 */       sb.append(")");
/* 498:    */     }
/* 499:887 */     Object nameValue = getValue("Name");
/* 500:888 */     if ((nameValue instanceof String))
/* 501:    */     {
/* 502:889 */       sb.append(" \"");
/* 503:890 */       sb.append((String)nameValue);
/* 504:891 */       sb.append("\"");
/* 505:    */     }
/* 506:893 */     this.proxy = getProxy();
/* 507:894 */     if (this.proxy != null)
/* 508:    */     {
/* 509:895 */       sb.append(" Proxy for: ");
/* 510:896 */       sb.append(this.proxy.toString());
/* 511:    */     }
/* 512:898 */     return sb.toString();
/* 513:    */   }
/* 514:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ApplicationAction
 * JD-Core Version:    0.7.0.1
 */