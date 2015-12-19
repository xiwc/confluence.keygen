/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.KeyboardFocusManager;
/*   4:    */ import java.beans.PropertyChangeEvent;
/*   5:    */ import java.beans.PropertyChangeListener;
/*   6:    */ import java.lang.ref.WeakReference;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.WeakHashMap;
/*  11:    */ import java.util.logging.Logger;
/*  12:    */ import javax.swing.Action;
/*  13:    */ import javax.swing.ActionMap;
/*  14:    */ import javax.swing.JComponent;
/*  15:    */ 
/*  16:    */ public class ActionManager
/*  17:    */   extends AbstractBean
/*  18:    */ {
/*  19: 34 */   private static final Logger logger = Logger.getLogger(ActionManager.class.getName());
/*  20:    */   private final ApplicationContext context;
/*  21:    */   private final WeakHashMap<Object, WeakReference<ApplicationActionMap>> actionMaps;
/*  22: 37 */   private ApplicationActionMap globalActionMap = null;
/*  23:    */   
/*  24:    */   protected ActionManager(ApplicationContext context)
/*  25:    */   {
/*  26: 40 */     if (context == null) {
/*  27: 41 */       throw new IllegalArgumentException("null context");
/*  28:    */     }
/*  29: 43 */     this.context = context;
/*  30: 44 */     this.actionMaps = new WeakHashMap();
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected final ApplicationContext getContext()
/*  34:    */   {
/*  35: 48 */     return this.context;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private ApplicationActionMap createActionMapChain(Class startClass, Class stopClass, Object actionsObject, ResourceMap resourceMap)
/*  39:    */   {
/*  40: 54 */     List<Class> classes = new ArrayList();
/*  41: 55 */     for (Class c = startClass;; c = c.getSuperclass())
/*  42:    */     {
/*  43: 56 */       classes.add(c);
/*  44: 57 */       if (c.equals(stopClass)) {
/*  45:    */         break;
/*  46:    */       }
/*  47:    */     }
/*  48: 59 */     Collections.reverse(classes);
/*  49:    */     
/*  50: 61 */     ApplicationContext ctx = getContext();
/*  51: 62 */     ApplicationActionMap parent = null;
/*  52: 63 */     for (Class cls : classes)
/*  53:    */     {
/*  54: 64 */       ApplicationActionMap appAM = new ApplicationActionMap(ctx, cls, actionsObject, resourceMap);
/*  55: 65 */       appAM.setParent(parent);
/*  56: 66 */       parent = appAM;
/*  57:    */     }
/*  58: 68 */     return parent;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ApplicationActionMap getActionMap()
/*  62:    */   {
/*  63: 96 */     if (this.globalActionMap == null)
/*  64:    */     {
/*  65: 97 */       ApplicationContext ctx = getContext();
/*  66: 98 */       Object appObject = ctx.getApplication();
/*  67: 99 */       Class appClass = ctx.getApplicationClass();
/*  68:100 */       ResourceMap resourceMap = ctx.getResourceMap();
/*  69:101 */       this.globalActionMap = createActionMapChain(appClass, Application.class, appObject, resourceMap);
/*  70:102 */       initProxyActionSupport();
/*  71:    */     }
/*  72:104 */     return this.globalActionMap;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void initProxyActionSupport()
/*  76:    */   {
/*  77:108 */     KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  78:109 */     kfm.addPropertyChangeListener(new KeyboardFocusPCL());
/*  79:    */   }
/*  80:    */   
/*  81:    */   public ApplicationActionMap getActionMap(Class actionsClass, Object actionsObject)
/*  82:    */   {
/*  83:153 */     if (actionsClass == null) {
/*  84:154 */       throw new IllegalArgumentException("null actionsClass");
/*  85:    */     }
/*  86:156 */     if (actionsObject == null) {
/*  87:157 */       throw new IllegalArgumentException("null actionsObject");
/*  88:    */     }
/*  89:159 */     if (!actionsClass.isAssignableFrom(actionsObject.getClass())) {
/*  90:160 */       throw new IllegalArgumentException("actionsObject not instanceof actionsClass");
/*  91:    */     }
/*  92:162 */     synchronized (this.actionMaps)
/*  93:    */     {
/*  94:163 */       WeakReference<ApplicationActionMap> ref = (WeakReference)this.actionMaps.get(actionsObject);
/*  95:164 */       ApplicationActionMap classActionMap = ref != null ? (ApplicationActionMap)ref.get() : null;
/*  96:165 */       if ((classActionMap == null) || (classActionMap.getActionsClass() != actionsClass))
/*  97:    */       {
/*  98:166 */         ApplicationContext ctx = getContext();
/*  99:167 */         Class actionsObjectClass = actionsObject.getClass();
/* 100:168 */         ResourceMap resourceMap = ctx.getResourceMap(actionsObjectClass, actionsClass);
/* 101:169 */         classActionMap = createActionMapChain(actionsObjectClass, actionsClass, actionsObject, resourceMap);
/* 102:170 */         ActionMap lastActionMap = classActionMap;
/* 103:171 */         while (lastActionMap.getParent() != null) {
/* 104:172 */           lastActionMap = lastActionMap.getParent();
/* 105:    */         }
/* 106:174 */         lastActionMap.setParent(getActionMap());
/* 107:175 */         this.actionMaps.put(actionsObject, new WeakReference(classActionMap));
/* 108:    */       }
/* 109:177 */       return classActionMap;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private final class KeyboardFocusPCL
/* 114:    */     implements PropertyChangeListener
/* 115:    */   {
/* 116:    */     private final TextActions textActions;
/* 117:    */     
/* 118:    */     KeyboardFocusPCL()
/* 119:    */     {
/* 120:184 */       this.textActions = new TextActions(ActionManager.this.getContext());
/* 121:    */     }
/* 122:    */     
/* 123:    */     public void propertyChange(PropertyChangeEvent e)
/* 124:    */     {
/* 125:187 */       if (e.getPropertyName() == "permanentFocusOwner")
/* 126:    */       {
/* 127:188 */         JComponent oldOwner = ActionManager.this.getContext().getFocusOwner();
/* 128:189 */         Object newValue = e.getNewValue();
/* 129:190 */         JComponent newOwner = (newValue instanceof JComponent) ? (JComponent)newValue : null;
/* 130:191 */         this.textActions.updateFocusOwner(oldOwner, newOwner);
/* 131:192 */         ActionManager.this.getContext().setFocusOwner(newOwner);
/* 132:193 */         ActionManager.this.updateAllProxyActions(oldOwner, newOwner);
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void updateAllProxyActions(JComponent oldFocusOwner, JComponent newFocusOwner)
/* 138:    */   {
/* 139:    */     ActionMap ownerActionMap;
/* 140:204 */     if (newFocusOwner != null)
/* 141:    */     {
/* 142:205 */       ownerActionMap = newFocusOwner.getActionMap();
/* 143:206 */       if (ownerActionMap != null)
/* 144:    */       {
/* 145:207 */         updateProxyActions(getActionMap(), ownerActionMap, newFocusOwner);
/* 146:208 */         for (WeakReference<ApplicationActionMap> appAMRef : this.actionMaps.values())
/* 147:    */         {
/* 148:209 */           ApplicationActionMap appAM = (ApplicationActionMap)appAMRef.get();
/* 149:210 */           if (appAM != null) {
/* 150:213 */             updateProxyActions(appAM, ownerActionMap, newFocusOwner);
/* 151:    */           }
/* 152:    */         }
/* 153:    */       }
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   private void updateProxyActions(ApplicationActionMap appAM, ActionMap ownerActionMap, JComponent focusOwner)
/* 158:    */   {
/* 159:225 */     for (ApplicationAction proxyAction : appAM.getProxyActions())
/* 160:    */     {
/* 161:226 */       String proxyActionName = proxyAction.getName();
/* 162:227 */       Action proxy = ownerActionMap.get(proxyActionName);
/* 163:228 */       if (proxy != null)
/* 164:    */       {
/* 165:229 */         proxyAction.setProxy(proxy);
/* 166:230 */         proxyAction.setProxySource(focusOwner);
/* 167:    */       }
/* 168:    */       else
/* 169:    */       {
/* 170:233 */         proxyAction.setProxy(null);
/* 171:234 */         proxyAction.setProxySource(null);
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ActionManager
 * JD-Core Version:    0.7.0.1
 */