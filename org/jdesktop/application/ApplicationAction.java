/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationAction
/*     */   extends AbstractAction
/*     */ {
/* 113 */   private static final Logger logger = Logger.getLogger(ApplicationAction.class.getName());
/*     */   private final ApplicationActionMap appAM;
/*     */   private final ResourceMap resourceMap;
/*     */   private final String actionName;
/*     */   private final Method actionMethod;
/*     */   private final String enabledProperty;
/*     */   private final Method isEnabledMethod;
/*     */   private final Method setEnabledMethod;
/*     */   private final String selectedProperty;
/*     */   private final Method isSelectedMethod;
/*     */   private final Method setSelectedMethod;
/*     */   private final Task.BlockingScope block;
/* 125 */   private Action proxy = null;
/* 126 */   private Object proxySource = null;
/* 127 */   private PropertyChangeListener proxyPCL = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String SELECTED_KEY = "SwingSelectedKey";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String LARGE_ICON_KEY = "SwingLargeIconKey";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ApplicationAction(ApplicationActionMap appAM, ResourceMap resourceMap, String baseName, Method actionMethod, String enabledProperty, String selectedProperty, Task.BlockingScope block)
/*     */   {
/* 206 */     if (appAM == null) {
/* 207 */       throw new IllegalArgumentException("null appAM");
/*     */     }
/* 209 */     if (baseName == null) {
/* 210 */       throw new IllegalArgumentException("null baseName");
/*     */     }
/* 212 */     this.appAM = appAM;
/* 213 */     this.resourceMap = resourceMap;
/* 214 */     this.actionName = baseName;
/* 215 */     this.actionMethod = actionMethod;
/* 216 */     this.enabledProperty = enabledProperty;
/* 217 */     this.selectedProperty = selectedProperty;
/* 218 */     this.block = block;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 223 */     if (enabledProperty != null) {
/* 224 */       this.setEnabledMethod = propertySetMethod(enabledProperty, Boolean.TYPE);
/* 225 */       this.isEnabledMethod = propertyGetMethod(enabledProperty);
/* 226 */       if (this.isEnabledMethod == null) {
/* 227 */         throw newNoSuchPropertyException(enabledProperty);
/*     */       }
/*     */     }
/*     */     else {
/* 231 */       this.isEnabledMethod = null;
/* 232 */       this.setEnabledMethod = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 238 */     if (selectedProperty != null) {
/* 239 */       this.setSelectedMethod = propertySetMethod(selectedProperty, Boolean.TYPE);
/* 240 */       this.isSelectedMethod = propertyGetMethod(selectedProperty);
/* 241 */       if (this.isSelectedMethod == null) {
/* 242 */         throw newNoSuchPropertyException(selectedProperty);
/*     */       }
/* 244 */       super.putValue("SwingSelectedKey", Boolean.FALSE);
/*     */     }
/*     */     else {
/* 247 */       this.isSelectedMethod = null;
/* 248 */       this.setSelectedMethod = null;
/*     */     }
/*     */     
/* 251 */     if (resourceMap != null) {
/* 252 */       initActionProperties(resourceMap, baseName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   ApplicationAction(ApplicationActionMap appAM, ResourceMap resourceMap, String actionName)
/*     */   {
/* 260 */     this(appAM, resourceMap, actionName, null, null, null, Task.BlockingScope.NONE);
/*     */   }
/*     */   
/*     */   private IllegalArgumentException newNoSuchPropertyException(String propertyName) {
/* 264 */     String actionsClassName = this.appAM.getActionsClass().getName();
/* 265 */     String msg = String.format("no property named %s in %s", new Object[] { propertyName, actionsClassName });
/* 266 */     return new IllegalArgumentException(msg);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String getEnabledProperty()
/*     */   {
/* 278 */     return this.enabledProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String getSelectedProperty()
/*     */   {
/* 289 */     return this.selectedProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getProxy()
/*     */   {
/* 302 */     return this.proxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProxy(Action proxy)
/*     */   {
/* 330 */     Action oldProxy = this.proxy;
/* 331 */     this.proxy = proxy;
/* 332 */     if (oldProxy != null) {
/* 333 */       oldProxy.removePropertyChangeListener(this.proxyPCL);
/* 334 */       this.proxyPCL = null;
/*     */     }
/* 336 */     if (this.proxy != null) {
/* 337 */       updateProxyProperties();
/* 338 */       this.proxyPCL = new ProxyPCL(null);
/* 339 */       proxy.addPropertyChangeListener(this.proxyPCL);
/*     */     }
/* 341 */     else if (oldProxy != null) {
/* 342 */       setEnabled(false);
/* 343 */       setSelected(false);
/*     */     }
/* 345 */     firePropertyChange("proxy", oldProxy, this.proxy);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getProxySource()
/*     */   {
/* 358 */     return this.proxySource;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProxySource(Object source)
/*     */   {
/* 371 */     Object oldValue = this.proxySource;
/* 372 */     this.proxySource = source;
/* 373 */     firePropertyChange("proxySource", oldValue, this.proxySource);
/*     */   }
/*     */   
/*     */   private void maybePutDescriptionValue(String key, Action proxy) {
/* 377 */     Object s = proxy.getValue(key);
/* 378 */     if ((s instanceof String)) {
/* 379 */       putValue(key, (String)s);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateProxyProperties() {
/* 384 */     Action proxy = getProxy();
/* 385 */     if (proxy != null) {
/* 386 */       setEnabled(proxy.isEnabled());
/* 387 */       Object s = proxy.getValue("SwingSelectedKey");
/* 388 */       setSelected(((s instanceof Boolean)) && (((Boolean)s).booleanValue()));
/* 389 */       maybePutDescriptionValue("ShortDescription", proxy);
/* 390 */       maybePutDescriptionValue("LongDescription", proxy);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ProxyPCL
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private ProxyPCL() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e)
/*     */     {
/* 401 */       String propertyName = e.getPropertyName();
/* 402 */       if ((propertyName == null) || ("enabled".equals(propertyName)) || ("selected".equals(propertyName)) || ("ShortDescription".equals(propertyName)) || ("LongDescription".equals(propertyName)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 407 */         ApplicationAction.this.updateProxyProperties();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initActionProperties(ResourceMap resourceMap, String baseName)
/*     */   {
/* 424 */     boolean iconOrNameSpecified = false;
/* 425 */     String typedName = null;
/*     */     
/*     */ 
/* 428 */     String text = resourceMap.getString(baseName + ".Action.text", new Object[0]);
/* 429 */     if (text != null) {
/* 430 */       MnemonicText.configure(this, text);
/* 431 */       iconOrNameSpecified = true;
/*     */     }
/*     */     
/* 434 */     Integer mnemonic = resourceMap.getKeyCode(baseName + ".Action.mnemonic");
/* 435 */     if (mnemonic != null) {
/* 436 */       putValue("MnemonicKey", mnemonic);
/*     */     }
/*     */     
/* 439 */     Integer index = resourceMap.getInteger(baseName + ".Action.displayedMnemonicIndex");
/* 440 */     if (index != null) {
/* 441 */       putValue("SwingDisplayedMnemonicIndexKey", index);
/*     */     }
/*     */     
/* 444 */     KeyStroke key = resourceMap.getKeyStroke(baseName + ".Action.accelerator");
/* 445 */     if (key != null) {
/* 446 */       putValue("AcceleratorKey", key);
/*     */     }
/*     */     
/* 449 */     Icon icon = resourceMap.getIcon(baseName + ".Action.icon");
/* 450 */     if (icon != null) {
/* 451 */       putValue("SmallIcon", icon);
/* 452 */       putValue("SwingLargeIconKey", icon);
/* 453 */       iconOrNameSpecified = true;
/*     */     }
/*     */     
/* 456 */     Icon smallIcon = resourceMap.getIcon(baseName + ".Action.smallIcon");
/* 457 */     if (smallIcon != null) {
/* 458 */       putValue("SmallIcon", smallIcon);
/* 459 */       iconOrNameSpecified = true;
/*     */     }
/*     */     
/* 462 */     Icon largeIcon = resourceMap.getIcon(baseName + ".Action.largeIcon");
/* 463 */     if (largeIcon != null) {
/* 464 */       putValue("SwingLargeIconKey", largeIcon);
/* 465 */       iconOrNameSpecified = true;
/*     */     }
/*     */     
/* 468 */     putValue("ShortDescription", resourceMap.getString(baseName + ".Action.shortDescription", new Object[0]));
/*     */     
/*     */ 
/* 471 */     putValue("LongDescription", resourceMap.getString(baseName + ".Action.longDescription", new Object[0]));
/*     */     
/*     */ 
/* 474 */     putValue("ActionCommandKey", resourceMap.getString(baseName + ".Action.command", new Object[0]));
/*     */     
/*     */ 
/*     */ 
/* 478 */     if (!iconOrNameSpecified) {
/* 479 */       putValue("Name", this.actionName);
/*     */     }
/*     */   }
/*     */   
/*     */   private String propertyMethodName(String prefix, String propertyName) {
/* 484 */     return prefix + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
/*     */   }
/*     */   
/*     */   private Method propertyGetMethod(String propertyName) {
/* 488 */     String[] getMethodNames = { propertyMethodName("is", propertyName), propertyMethodName("get", propertyName) };
/*     */     
/*     */ 
/*     */ 
/* 492 */     Class actionsClass = this.appAM.getActionsClass();
/* 493 */     for (String name : getMethodNames) {
/*     */       try {
/* 495 */         return actionsClass.getMethod(name, new Class[0]);
/*     */       }
/*     */       catch (NoSuchMethodException ignore) {}
/*     */     }
/* 499 */     return null;
/*     */   }
/*     */   
/*     */   private Method propertySetMethod(String propertyName, Class type) {
/* 503 */     Class actionsClass = this.appAM.getActionsClass();
/*     */     try {
/* 505 */       return actionsClass.getMethod(propertyMethodName("set", propertyName), new Class[] { type });
/*     */     }
/*     */     catch (NoSuchMethodException ignore) {}
/* 508 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 537 */     return this.actionName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResourceMap getResourceMap()
/*     */   {
/* 546 */     return this.resourceMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object getActionArgument(Class pType, String pKey, ActionEvent actionEvent)
/*     */   {
/* 602 */     Object argument = null;
/* 603 */     if (pType == ActionEvent.class) {
/* 604 */       argument = actionEvent;
/*     */     }
/* 606 */     else if (pType == Action.class) {
/* 607 */       argument = this;
/*     */     }
/* 609 */     else if (pType == ActionMap.class) {
/* 610 */       argument = this.appAM;
/*     */     }
/* 612 */     else if (pType == ResourceMap.class) {
/* 613 */       argument = this.resourceMap;
/*     */     }
/* 615 */     else if (pType == ApplicationContext.class) {
/* 616 */       argument = this.appAM.getContext();
/*     */     }
/* 618 */     else if (pType == Application.class) {
/* 619 */       argument = this.appAM.getContext().getApplication();
/*     */     }
/*     */     else {
/* 622 */       Exception e = new IllegalArgumentException("unrecognized @Action method parameter");
/* 623 */       actionFailed(actionEvent, e);
/*     */     }
/* 625 */     return argument;
/*     */   }
/*     */   
/*     */   private Task.InputBlocker createInputBlocker(Task task, ActionEvent event)
/*     */   {
/* 630 */     Object target = event.getSource();
/* 631 */     if (this.block == Task.BlockingScope.ACTION) {
/* 632 */       target = this;
/*     */     }
/* 634 */     return new DefaultInputBlocker(task, this.block, target, this);
/*     */   }
/*     */   
/*     */   private void noProxyActionPerformed(ActionEvent actionEvent) {
/* 638 */     Object taskObject = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 643 */     Annotation[][] allPAnnotations = this.actionMethod.getParameterAnnotations();
/* 644 */     Class<?>[] pTypes = this.actionMethod.getParameterTypes();
/* 645 */     Object[] arguments = new Object[pTypes.length];
/* 646 */     for (int i = 0; i < pTypes.length; i++) {
/* 647 */       String pKey = null;
/* 648 */       for (Annotation pAnnotation : allPAnnotations[i]) {
/* 649 */         if ((pAnnotation instanceof Action.Parameter)) {
/* 650 */           pKey = ((Action.Parameter)pAnnotation).value();
/* 651 */           break;
/*     */         }
/*     */       }
/* 654 */       arguments[i] = getActionArgument(pTypes[i], pKey, actionEvent);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 661 */       Object target = this.appAM.getActionsObject();
/* 662 */       taskObject = this.actionMethod.invoke(target, arguments);
/*     */     }
/*     */     catch (Exception e) {
/* 665 */       actionFailed(actionEvent, e);
/*     */     }
/*     */     
/* 668 */     if ((taskObject instanceof Task)) {
/* 669 */       Task task = (Task)taskObject;
/* 670 */       if (task.getInputBlocker() == null) {
/* 671 */         task.setInputBlocker(createInputBlocker(task, actionEvent));
/*     */       }
/* 673 */       ApplicationContext ctx = this.appAM.getContext();
/* 674 */       ctx.getTaskService().execute(task);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void actionPerformed(ActionEvent actionEvent)
/*     */   {
/* 692 */     Action proxy = getProxy();
/* 693 */     if (proxy != null) {
/* 694 */       actionEvent.setSource(getProxySource());
/* 695 */       proxy.actionPerformed(actionEvent);
/*     */     }
/* 697 */     else if (this.actionMethod != null) {
/* 698 */       noProxyActionPerformed(actionEvent);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/* 715 */     if ((getProxy() != null) || (this.isEnabledMethod == null)) {
/* 716 */       return super.isEnabled();
/*     */     }
/*     */     try
/*     */     {
/* 720 */       Object b = this.isEnabledMethod.invoke(this.appAM.getActionsObject(), new Object[0]);
/* 721 */       return ((Boolean)b).booleanValue();
/*     */     }
/*     */     catch (Exception e) {
/* 724 */       throw newInvokeError(this.isEnabledMethod, e, new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEnabled(boolean enabled)
/*     */   {
/* 742 */     if ((getProxy() != null) || (this.setEnabledMethod == null)) {
/* 743 */       super.setEnabled(enabled);
/*     */     } else {
/*     */       try
/*     */       {
/* 747 */         this.setEnabledMethod.invoke(this.appAM.getActionsObject(), new Object[] { Boolean.valueOf(enabled) });
/*     */       }
/*     */       catch (Exception e) {
/* 750 */         throw newInvokeError(this.setEnabledMethod, e, new Object[] { Boolean.valueOf(enabled) });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSelected()
/*     */   {
/* 767 */     if ((getProxy() != null) || (this.isSelectedMethod == null)) {
/* 768 */       Object v = getValue("SwingSelectedKey");
/* 769 */       return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : false;
/*     */     }
/*     */     try
/*     */     {
/* 773 */       Object b = this.isSelectedMethod.invoke(this.appAM.getActionsObject(), new Object[0]);
/* 774 */       return ((Boolean)b).booleanValue();
/*     */     }
/*     */     catch (Exception e) {
/* 777 */       throw newInvokeError(this.isSelectedMethod, e, new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSelected(boolean selected)
/*     */   {
/* 795 */     if ((getProxy() != null) || (this.setSelectedMethod == null)) {
/* 796 */       super.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/*     */     } else {
/*     */       try
/*     */       {
/* 800 */         super.putValue("SwingSelectedKey", Boolean.valueOf(selected));
/* 801 */         if (selected != isSelected()) {
/* 802 */           this.setSelectedMethod.invoke(this.appAM.getActionsObject(), new Object[] { Boolean.valueOf(selected) });
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 806 */         throw newInvokeError(this.setSelectedMethod, e, new Object[] { Boolean.valueOf(selected) });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void putValue(String key, Object value)
/*     */   {
/* 819 */     if (("SwingSelectedKey".equals(key)) && ((value instanceof Boolean))) {
/* 820 */       setSelected(((Boolean)value).booleanValue());
/*     */     }
/*     */     else {
/* 823 */       super.putValue(key, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Error newInvokeError(Method m, Exception e, Object... args)
/*     */   {
/* 831 */     String argsString = args.length == 0 ? "" : args[0].toString();
/* 832 */     for (int i = 1; i < args.length; i++) {
/* 833 */       argsString = argsString + ", " + args[i];
/*     */     }
/* 835 */     String actionsClassName = this.appAM.getActionsObject().getClass().getName();
/* 836 */     String msg = String.format("%s.%s(%s) failed", new Object[] { actionsClassName, m, argsString });
/* 837 */     return new Error(msg, e);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void forwardPropertyChangeEvent(PropertyChangeEvent e, String actionPropertyName)
/*     */   {
/* 847 */     if (("selected".equals(actionPropertyName)) && ((e.getNewValue() instanceof Boolean))) {
/* 848 */       putValue("SwingSelectedKey", (Boolean)e.getNewValue());
/*     */     }
/* 850 */     firePropertyChange(actionPropertyName, e.getOldValue(), e.getNewValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void actionFailed(ActionEvent actionEvent, Exception e)
/*     */   {
/* 859 */     throw new Error(e);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 874 */     StringBuilder sb = new StringBuilder();
/* 875 */     sb.append(getClass().getName());
/* 876 */     sb.append(" ");
/* 877 */     boolean enabled = isEnabled();
/* 878 */     if (!enabled) sb.append("(");
/* 879 */     sb.append(getName());
/* 880 */     Object selectedValue = getValue("SwingSelectedKey");
/* 881 */     if (((selectedValue instanceof Boolean)) && 
/* 882 */       (((Boolean)selectedValue).booleanValue())) {
/* 883 */       sb.append("+");
/*     */     }
/*     */     
/* 886 */     if (!enabled) sb.append(")");
/* 887 */     Object nameValue = getValue("Name");
/* 888 */     if ((nameValue instanceof String)) {
/* 889 */       sb.append(" \"");
/* 890 */       sb.append((String)nameValue);
/* 891 */       sb.append("\"");
/*     */     }
/* 893 */     this.proxy = getProxy();
/* 894 */     if (this.proxy != null) {
/* 895 */       sb.append(" Proxy for: ");
/* 896 */       sb.append(this.proxy.toString());
/*     */     }
/* 898 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ApplicationAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */