/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
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
/*     */ public class ActionManager
/*     */   extends AbstractBean
/*     */ {
/*  34 */   private static final Logger logger = Logger.getLogger(ActionManager.class.getName());
/*     */   private final ApplicationContext context;
/*     */   private final WeakHashMap<Object, WeakReference<ApplicationActionMap>> actionMaps;
/*  37 */   private ApplicationActionMap globalActionMap = null;
/*     */   
/*     */   protected ActionManager(ApplicationContext context) {
/*  40 */     if (context == null) {
/*  41 */       throw new IllegalArgumentException("null context");
/*     */     }
/*  43 */     this.context = context;
/*  44 */     this.actionMaps = new WeakHashMap();
/*     */   }
/*     */   
/*     */   protected final ApplicationContext getContext() {
/*  48 */     return this.context;
/*     */   }
/*     */   
/*     */ 
/*     */   private ApplicationActionMap createActionMapChain(Class startClass, Class stopClass, Object actionsObject, ResourceMap resourceMap)
/*     */   {
/*  54 */     List<Class> classes = new ArrayList();
/*  55 */     for (Class c = startClass;; c = c.getSuperclass()) {
/*  56 */       classes.add(c);
/*  57 */       if (c.equals(stopClass)) break;
/*     */     }
/*  59 */     Collections.reverse(classes);
/*     */     
/*  61 */     ApplicationContext ctx = getContext();
/*  62 */     ApplicationActionMap parent = null;
/*  63 */     for (Class cls : classes) {
/*  64 */       ApplicationActionMap appAM = new ApplicationActionMap(ctx, cls, actionsObject, resourceMap);
/*  65 */       appAM.setParent(parent);
/*  66 */       parent = appAM;
/*     */     }
/*  68 */     return parent;
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
/*     */   public ApplicationActionMap getActionMap()
/*     */   {
/*  96 */     if (this.globalActionMap == null) {
/*  97 */       ApplicationContext ctx = getContext();
/*  98 */       Object appObject = ctx.getApplication();
/*  99 */       Class appClass = ctx.getApplicationClass();
/* 100 */       ResourceMap resourceMap = ctx.getResourceMap();
/* 101 */       this.globalActionMap = createActionMapChain(appClass, Application.class, appObject, resourceMap);
/* 102 */       initProxyActionSupport();
/*     */     }
/* 104 */     return this.globalActionMap;
/*     */   }
/*     */   
/*     */   private void initProxyActionSupport() {
/* 108 */     KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 109 */     kfm.addPropertyChangeListener(new KeyboardFocusPCL());
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
/*     */   public ApplicationActionMap getActionMap(Class actionsClass, Object actionsObject)
/*     */   {
/* 153 */     if (actionsClass == null) {
/* 154 */       throw new IllegalArgumentException("null actionsClass");
/*     */     }
/* 156 */     if (actionsObject == null) {
/* 157 */       throw new IllegalArgumentException("null actionsObject");
/*     */     }
/* 159 */     if (!actionsClass.isAssignableFrom(actionsObject.getClass())) {
/* 160 */       throw new IllegalArgumentException("actionsObject not instanceof actionsClass");
/*     */     }
/* 162 */     synchronized (this.actionMaps) {
/* 163 */       WeakReference<ApplicationActionMap> ref = (WeakReference)this.actionMaps.get(actionsObject);
/* 164 */       ApplicationActionMap classActionMap = ref != null ? (ApplicationActionMap)ref.get() : null;
/* 165 */       if ((classActionMap == null) || (classActionMap.getActionsClass() != actionsClass)) {
/* 166 */         ApplicationContext ctx = getContext();
/* 167 */         Class actionsObjectClass = actionsObject.getClass();
/* 168 */         ResourceMap resourceMap = ctx.getResourceMap(actionsObjectClass, actionsClass);
/* 169 */         classActionMap = createActionMapChain(actionsObjectClass, actionsClass, actionsObject, resourceMap);
/* 170 */         ActionMap lastActionMap = classActionMap;
/* 171 */         while (lastActionMap.getParent() != null) {
/* 172 */           lastActionMap = lastActionMap.getParent();
/*     */         }
/* 174 */         lastActionMap.setParent(getActionMap());
/* 175 */         this.actionMaps.put(actionsObject, new WeakReference(classActionMap));
/*     */       }
/* 177 */       return classActionMap;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class KeyboardFocusPCL implements PropertyChangeListener {
/*     */     private final TextActions textActions;
/*     */     
/* 184 */     KeyboardFocusPCL() { this.textActions = new TextActions(ActionManager.this.getContext()); }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 187 */       if (e.getPropertyName() == "permanentFocusOwner") {
/* 188 */         JComponent oldOwner = ActionManager.this.getContext().getFocusOwner();
/* 189 */         Object newValue = e.getNewValue();
/* 190 */         JComponent newOwner = (newValue instanceof JComponent) ? (JComponent)newValue : null;
/* 191 */         this.textActions.updateFocusOwner(oldOwner, newOwner);
/* 192 */         ActionManager.this.getContext().setFocusOwner(newOwner);
/* 193 */         ActionManager.this.updateAllProxyActions(oldOwner, newOwner);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updateAllProxyActions(JComponent oldFocusOwner, JComponent newFocusOwner)
/*     */   {
/*     */     ActionMap ownerActionMap;
/*     */     
/* 204 */     if (newFocusOwner != null) {
/* 205 */       ownerActionMap = newFocusOwner.getActionMap();
/* 206 */       if (ownerActionMap != null) {
/* 207 */         updateProxyActions(getActionMap(), ownerActionMap, newFocusOwner);
/* 208 */         for (WeakReference<ApplicationActionMap> appAMRef : this.actionMaps.values()) {
/* 209 */           ApplicationActionMap appAM = (ApplicationActionMap)appAMRef.get();
/* 210 */           if (appAM != null)
/*     */           {
/*     */ 
/* 213 */             updateProxyActions(appAM, ownerActionMap, newFocusOwner);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateProxyActions(ApplicationActionMap appAM, ActionMap ownerActionMap, JComponent focusOwner)
/*     */   {
/* 225 */     for (ApplicationAction proxyAction : appAM.getProxyActions()) {
/* 226 */       String proxyActionName = proxyAction.getName();
/* 227 */       Action proxy = ownerActionMap.get(proxyActionName);
/* 228 */       if (proxy != null) {
/* 229 */         proxyAction.setProxy(proxy);
/* 230 */         proxyAction.setProxySource(focusOwner);
/*     */       }
/*     */       else {
/* 233 */         proxyAction.setProxy(null);
/* 234 */         proxyAction.setProxySource(null);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ActionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */