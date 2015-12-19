/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.beans.PropertyChangeEvent;
/*   4:    */ import java.beans.PropertyChangeListener;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.swing.ActionMap;
/*  10:    */ 
/*  11:    */ public class ApplicationActionMap
/*  12:    */   extends ActionMap
/*  13:    */ {
/*  14:    */   private final ApplicationContext context;
/*  15:    */   private final ResourceMap resourceMap;
/*  16:    */   private final Class actionsClass;
/*  17:    */   private final Object actionsObject;
/*  18:    */   private final List<ApplicationAction> proxyActions;
/*  19:    */   
/*  20:    */   public ApplicationActionMap(ApplicationContext context, Class actionsClass, Object actionsObject, ResourceMap resourceMap)
/*  21:    */   {
/*  22: 64 */     if (context == null) {
/*  23: 65 */       throw new IllegalArgumentException("null context");
/*  24:    */     }
/*  25: 67 */     if (actionsClass == null) {
/*  26: 68 */       throw new IllegalArgumentException("null actionsClass");
/*  27:    */     }
/*  28: 70 */     if (actionsObject == null) {
/*  29: 71 */       throw new IllegalArgumentException("null actionsObject");
/*  30:    */     }
/*  31: 73 */     if (!actionsClass.isInstance(actionsObject)) {
/*  32: 74 */       throw new IllegalArgumentException("actionsObject not an instanceof actionsClass");
/*  33:    */     }
/*  34: 76 */     this.context = context;
/*  35: 77 */     this.actionsClass = actionsClass;
/*  36: 78 */     this.actionsObject = actionsObject;
/*  37: 79 */     this.resourceMap = resourceMap;
/*  38: 80 */     this.proxyActions = new ArrayList();
/*  39: 81 */     addAnnotationActions(resourceMap);
/*  40: 82 */     maybeAddActionsPCL();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final ApplicationContext getContext()
/*  44:    */   {
/*  45: 86 */     return this.context;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final Class getActionsClass()
/*  49:    */   {
/*  50: 90 */     return this.actionsClass;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final Object getActionsObject()
/*  54:    */   {
/*  55: 94 */     return this.actionsObject;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<ApplicationAction> getProxyActions()
/*  59:    */   {
/*  60:110 */     ArrayList<ApplicationAction> allProxyActions = new ArrayList(this.proxyActions);
/*  61:111 */     ActionMap parent = getParent();
/*  62:112 */     while (parent != null)
/*  63:    */     {
/*  64:113 */       if ((parent instanceof ApplicationActionMap)) {
/*  65:114 */         allProxyActions.addAll(((ApplicationActionMap)parent).proxyActions);
/*  66:    */       }
/*  67:116 */       parent = parent.getParent();
/*  68:    */     }
/*  69:118 */     return Collections.unmodifiableList(allProxyActions);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private String aString(String s, String emptyValue)
/*  73:    */   {
/*  74:122 */     return s.length() == 0 ? emptyValue : s;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void putAction(String key, ApplicationAction action)
/*  78:    */   {
/*  79:126 */     if (get(key) != null) {}
/*  80:129 */     put(key, action);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void addAnnotationActions(ResourceMap resourceMap)
/*  84:    */   {
/*  85:137 */     Class<?> actionsClass = getActionsClass();
/*  86:139 */     for (Method m : actionsClass.getDeclaredMethods())
/*  87:    */     {
/*  88:140 */       Action action = (Action)m.getAnnotation(Action.class);
/*  89:141 */       if (action != null)
/*  90:    */       {
/*  91:142 */         String methodName = m.getName();
/*  92:143 */         String enabledProperty = aString(action.enabledProperty(), null);
/*  93:144 */         String selectedProperty = aString(action.selectedProperty(), null);
/*  94:145 */         String actionName = aString(action.name(), methodName);
/*  95:146 */         Task.BlockingScope block = action.block();
/*  96:147 */         ApplicationAction appAction = new ApplicationAction(this, resourceMap, actionName, m, enabledProperty, selectedProperty, block);
/*  97:    */         
/*  98:149 */         putAction(actionName, appAction);
/*  99:    */       }
/* 100:    */     }
/* 101:153 */     ProxyActions proxyActionsAnnotation = (ProxyActions)actionsClass.getAnnotation(ProxyActions.class);
/* 102:154 */     if (proxyActionsAnnotation != null) {
/* 103:155 */       for (String actionName : proxyActionsAnnotation.value())
/* 104:    */       {
/* 105:156 */         ApplicationAction appAction = new ApplicationAction(this, resourceMap, actionName);
/* 106:157 */         appAction.setEnabled(false);
/* 107:158 */         putAction(actionName, appAction);
/* 108:159 */         this.proxyActions.add(appAction);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void maybeAddActionsPCL()
/* 114:    */   {
/* 115:171 */     boolean needsPCL = false;
/* 116:172 */     Object[] keys = keys();
/* 117:173 */     if (keys != null)
/* 118:    */     {
/* 119:174 */       for (Object key : keys)
/* 120:    */       {
/* 121:175 */         javax.swing.Action value = get(key);
/* 122:176 */         if ((value instanceof ApplicationAction))
/* 123:    */         {
/* 124:177 */           ApplicationAction actionAdapter = (ApplicationAction)value;
/* 125:178 */           if ((actionAdapter.getEnabledProperty() != null) || (actionAdapter.getSelectedProperty() != null))
/* 126:    */           {
/* 127:180 */             needsPCL = true;
/* 128:181 */             break;
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:185 */       if (needsPCL) {
/* 133:    */         try
/* 134:    */         {
/* 135:187 */           Class actionsClass = getActionsClass();
/* 136:188 */           Method m = actionsClass.getMethod("addPropertyChangeListener", new Class[] { PropertyChangeListener.class });
/* 137:189 */           m.invoke(getActionsObject(), new Object[] { new ActionsPCL(null) });
/* 138:    */         }
/* 139:    */         catch (Exception e)
/* 140:    */         {
/* 141:192 */           String s = "addPropertyChangeListener undefined " + this.actionsClass;
/* 142:193 */           throw new Error(s, e);
/* 143:    */         }
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   private class ActionsPCL
/* 149:    */     implements PropertyChangeListener
/* 150:    */   {
/* 151:    */     private ActionsPCL() {}
/* 152:    */     
/* 153:    */     public void propertyChange(PropertyChangeEvent event)
/* 154:    */     {
/* 155:205 */       String propertyName = event.getPropertyName();
/* 156:206 */       Object[] keys = ApplicationActionMap.this.keys();
/* 157:207 */       if (keys != null) {
/* 158:208 */         for (Object key : keys)
/* 159:    */         {
/* 160:209 */           javax.swing.Action value = ApplicationActionMap.this.get(key);
/* 161:210 */           if ((value instanceof ApplicationAction))
/* 162:    */           {
/* 163:211 */             ApplicationAction appAction = (ApplicationAction)value;
/* 164:212 */             if (propertyName.equals(appAction.getEnabledProperty())) {
/* 165:213 */               appAction.forwardPropertyChangeEvent(event, "enabled");
/* 166:215 */             } else if (propertyName.equals(appAction.getSelectedProperty())) {
/* 167:216 */               appAction.forwardPropertyChangeEvent(event, "selected");
/* 168:    */             }
/* 169:    */           }
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ApplicationActionMap
 * JD-Core Version:    0.7.0.1
 */