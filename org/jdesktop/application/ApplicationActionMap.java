/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.ActionMap;
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
/*     */ public class ApplicationActionMap
/*     */   extends ActionMap
/*     */ {
/*     */   private final ApplicationContext context;
/*     */   private final ResourceMap resourceMap;
/*     */   private final Class actionsClass;
/*     */   private final Object actionsObject;
/*     */   private final List<ApplicationAction> proxyActions;
/*     */   
/*     */   public ApplicationActionMap(ApplicationContext context, Class actionsClass, Object actionsObject, ResourceMap resourceMap)
/*     */   {
/*  64 */     if (context == null) {
/*  65 */       throw new IllegalArgumentException("null context");
/*     */     }
/*  67 */     if (actionsClass == null) {
/*  68 */       throw new IllegalArgumentException("null actionsClass");
/*     */     }
/*  70 */     if (actionsObject == null) {
/*  71 */       throw new IllegalArgumentException("null actionsObject");
/*     */     }
/*  73 */     if (!actionsClass.isInstance(actionsObject)) {
/*  74 */       throw new IllegalArgumentException("actionsObject not an instanceof actionsClass");
/*     */     }
/*  76 */     this.context = context;
/*  77 */     this.actionsClass = actionsClass;
/*  78 */     this.actionsObject = actionsObject;
/*  79 */     this.resourceMap = resourceMap;
/*  80 */     this.proxyActions = new ArrayList();
/*  81 */     addAnnotationActions(resourceMap);
/*  82 */     maybeAddActionsPCL();
/*     */   }
/*     */   
/*     */   public final ApplicationContext getContext() {
/*  86 */     return this.context;
/*     */   }
/*     */   
/*     */   public final Class getActionsClass() {
/*  90 */     return this.actionsClass;
/*     */   }
/*     */   
/*     */   public final Object getActionsObject() {
/*  94 */     return this.actionsObject;
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
/*     */   public List<ApplicationAction> getProxyActions()
/*     */   {
/* 110 */     ArrayList<ApplicationAction> allProxyActions = new ArrayList(this.proxyActions);
/* 111 */     ActionMap parent = getParent();
/* 112 */     while (parent != null) {
/* 113 */       if ((parent instanceof ApplicationActionMap)) {
/* 114 */         allProxyActions.addAll(((ApplicationActionMap)parent).proxyActions);
/*     */       }
/* 116 */       parent = parent.getParent();
/*     */     }
/* 118 */     return Collections.unmodifiableList(allProxyActions);
/*     */   }
/*     */   
/*     */   private String aString(String s, String emptyValue) {
/* 122 */     return s.length() == 0 ? emptyValue : s;
/*     */   }
/*     */   
/*     */   private void putAction(String key, ApplicationAction action) {
/* 126 */     if (get(key) != null) {}
/*     */     
/*     */ 
/* 129 */     put(key, action);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addAnnotationActions(ResourceMap resourceMap)
/*     */   {
/* 137 */     Class<?> actionsClass = getActionsClass();
/*     */     
/* 139 */     for (Method m : actionsClass.getDeclaredMethods()) {
/* 140 */       Action action = (Action)m.getAnnotation(Action.class);
/* 141 */       if (action != null) {
/* 142 */         String methodName = m.getName();
/* 143 */         String enabledProperty = aString(action.enabledProperty(), null);
/* 144 */         String selectedProperty = aString(action.selectedProperty(), null);
/* 145 */         String actionName = aString(action.name(), methodName);
/* 146 */         Task.BlockingScope block = action.block();
/* 147 */         ApplicationAction appAction = new ApplicationAction(this, resourceMap, actionName, m, enabledProperty, selectedProperty, block);
/*     */         
/* 149 */         putAction(actionName, appAction);
/*     */       }
/*     */     }
/*     */     
/* 153 */     ProxyActions proxyActionsAnnotation = (ProxyActions)actionsClass.getAnnotation(ProxyActions.class);
/* 154 */     if (proxyActionsAnnotation != null) {
/* 155 */       for (String actionName : proxyActionsAnnotation.value()) {
/* 156 */         ApplicationAction appAction = new ApplicationAction(this, resourceMap, actionName);
/* 157 */         appAction.setEnabled(false);
/* 158 */         putAction(actionName, appAction);
/* 159 */         this.proxyActions.add(appAction);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void maybeAddActionsPCL()
/*     */   {
/* 171 */     boolean needsPCL = false;
/* 172 */     Object[] keys = keys();
/* 173 */     if (keys != null) {
/* 174 */       for (Object key : keys) {
/* 175 */         javax.swing.Action value = get(key);
/* 176 */         if ((value instanceof ApplicationAction)) {
/* 177 */           ApplicationAction actionAdapter = (ApplicationAction)value;
/* 178 */           if ((actionAdapter.getEnabledProperty() != null) || (actionAdapter.getSelectedProperty() != null))
/*     */           {
/* 180 */             needsPCL = true;
/* 181 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 185 */       if (needsPCL) {
/*     */         try {
/* 187 */           Class actionsClass = getActionsClass();
/* 188 */           Method m = actionsClass.getMethod("addPropertyChangeListener", new Class[] { PropertyChangeListener.class });
/* 189 */           m.invoke(getActionsObject(), new Object[] { new ActionsPCL(null) });
/*     */         }
/*     */         catch (Exception e) {
/* 192 */           String s = "addPropertyChangeListener undefined " + this.actionsClass;
/* 193 */           throw new Error(s, e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private class ActionsPCL implements PropertyChangeListener
/*     */   {
/*     */     private ActionsPCL() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent event)
/*     */     {
/* 205 */       String propertyName = event.getPropertyName();
/* 206 */       Object[] keys = ApplicationActionMap.this.keys();
/* 207 */       if (keys != null) {
/* 208 */         for (Object key : keys) {
/* 209 */           javax.swing.Action value = ApplicationActionMap.this.get(key);
/* 210 */           if ((value instanceof ApplicationAction)) {
/* 211 */             ApplicationAction appAction = (ApplicationAction)value;
/* 212 */             if (propertyName.equals(appAction.getEnabledProperty())) {
/* 213 */               appAction.forwardPropertyChangeEvent(event, "enabled");
/*     */             }
/* 215 */             else if (propertyName.equals(appAction.getSelectedProperty())) {
/* 216 */               appAction.forwardPropertyChangeEvent(event, "selected");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ApplicationActionMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */