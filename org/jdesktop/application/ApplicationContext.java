/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.datatransfer.Clipboard;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*   9:    */ import java.util.logging.Logger;
/*  10:    */ import javax.swing.JComponent;
/*  11:    */ 
/*  12:    */ public class ApplicationContext
/*  13:    */   extends AbstractBean
/*  14:    */ {
/*  15: 48 */   private static final Logger logger = Logger.getLogger(ApplicationContext.class.getName());
/*  16:    */   private final List<TaskService> taskServices;
/*  17:    */   private final List<TaskService> taskServicesReadOnly;
/*  18:    */   private ResourceManager resourceManager;
/*  19:    */   private ActionManager actionManager;
/*  20:    */   private LocalStorage localStorage;
/*  21:    */   private SessionStorage sessionStorage;
/*  22: 55 */   private Application application = null;
/*  23: 56 */   private Class applicationClass = null;
/*  24: 57 */   private JComponent focusOwner = null;
/*  25: 58 */   private Clipboard clipboard = null;
/*  26: 59 */   private Throwable uncaughtException = null;
/*  27: 60 */   private TaskMonitor taskMonitor = null;
/*  28:    */   
/*  29:    */   protected ApplicationContext()
/*  30:    */   {
/*  31: 64 */     this.resourceManager = new ResourceManager(this);
/*  32: 65 */     this.actionManager = new ActionManager(this);
/*  33: 66 */     this.localStorage = new LocalStorage(this);
/*  34: 67 */     this.sessionStorage = new SessionStorage(this);
/*  35: 68 */     this.taskServices = new CopyOnWriteArrayList();
/*  36: 69 */     this.taskServices.add(new TaskService("default"));
/*  37: 70 */     this.taskServicesReadOnly = Collections.unmodifiableList(this.taskServices);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final synchronized Class getApplicationClass()
/*  41:    */   {
/*  42: 84 */     return this.applicationClass;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final synchronized void setApplicationClass(Class applicationClass)
/*  46:    */   {
/*  47: 99 */     if (this.application != null) {
/*  48:100 */       throw new IllegalStateException("application has been launched");
/*  49:    */     }
/*  50:102 */     this.applicationClass = applicationClass;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final synchronized Application getApplication()
/*  54:    */   {
/*  55:113 */     return this.application;
/*  56:    */   }
/*  57:    */   
/*  58:    */   synchronized void setApplication(Application application)
/*  59:    */   {
/*  60:119 */     if (this.application != null) {
/*  61:120 */       throw new IllegalStateException("application has already been launched");
/*  62:    */     }
/*  63:122 */     this.application = application;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final ResourceManager getResourceManager()
/*  67:    */   {
/*  68:134 */     return this.resourceManager;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void setResourceManager(ResourceManager resourceManager)
/*  72:    */   {
/*  73:150 */     if (resourceManager == null) {
/*  74:151 */       throw new IllegalArgumentException("null resourceManager");
/*  75:    */     }
/*  76:153 */     Object oldValue = this.resourceManager;
/*  77:154 */     this.resourceManager = resourceManager;
/*  78:155 */     firePropertyChange("resourceManager", oldValue, this.resourceManager);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public final ResourceMap getResourceMap(Class cls)
/*  82:    */   {
/*  83:178 */     return getResourceManager().getResourceMap(cls, cls);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public final ResourceMap getResourceMap(Class startClass, Class stopClass)
/*  87:    */   {
/*  88:203 */     return getResourceManager().getResourceMap(startClass, stopClass);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public final ResourceMap getResourceMap()
/*  92:    */   {
/*  93:224 */     return getResourceManager().getResourceMap();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public final ActionManager getActionManager()
/*  97:    */   {
/*  98:233 */     return this.actionManager;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void setActionManager(ActionManager actionManager)
/* 102:    */   {
/* 103:249 */     if (actionManager == null) {
/* 104:250 */       throw new IllegalArgumentException("null actionManager");
/* 105:    */     }
/* 106:252 */     Object oldValue = this.actionManager;
/* 107:253 */     this.actionManager = actionManager;
/* 108:254 */     firePropertyChange("actionManager", oldValue, this.actionManager);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public final ApplicationActionMap getActionMap()
/* 112:    */   {
/* 113:272 */     return getActionManager().getActionMap();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final ApplicationActionMap getActionMap(Class actionsClass, Object actionsObject)
/* 117:    */   {
/* 118:290 */     return getActionManager().getActionMap(actionsClass, actionsObject);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public final ApplicationActionMap getActionMap(Object actionsObject)
/* 122:    */   {
/* 123:300 */     if (actionsObject == null) {
/* 124:301 */       throw new IllegalArgumentException("null actionsObject");
/* 125:    */     }
/* 126:303 */     return getActionManager().getActionMap(actionsObject.getClass(), actionsObject);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final LocalStorage getLocalStorage()
/* 130:    */   {
/* 131:312 */     return this.localStorage;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected void setLocalStorage(LocalStorage localStorage)
/* 135:    */   {
/* 136:321 */     if (localStorage == null) {
/* 137:322 */       throw new IllegalArgumentException("null localStorage");
/* 138:    */     }
/* 139:324 */     Object oldValue = this.localStorage;
/* 140:325 */     this.localStorage = localStorage;
/* 141:326 */     firePropertyChange("localStorage", oldValue, this.localStorage);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public final SessionStorage getSessionStorage()
/* 145:    */   {
/* 146:336 */     return this.sessionStorage;
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected void setSessionStorage(SessionStorage sessionStorage)
/* 150:    */   {
/* 151:345 */     if (sessionStorage == null) {
/* 152:346 */       throw new IllegalArgumentException("null sessionStorage");
/* 153:    */     }
/* 154:348 */     Object oldValue = this.sessionStorage;
/* 155:349 */     this.sessionStorage = sessionStorage;
/* 156:350 */     firePropertyChange("sessionStorage", oldValue, this.sessionStorage);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Clipboard getClipboard()
/* 160:    */   {
/* 161:357 */     if (this.clipboard == null) {
/* 162:    */       try
/* 163:    */       {
/* 164:359 */         this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 165:    */       }
/* 166:    */       catch (SecurityException e)
/* 167:    */       {
/* 168:362 */         this.clipboard = new Clipboard("sandbox");
/* 169:    */       }
/* 170:    */     }
/* 171:365 */     return this.clipboard;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public JComponent getFocusOwner()
/* 175:    */   {
/* 176:371 */     return this.focusOwner;
/* 177:    */   }
/* 178:    */   
/* 179:    */   void setFocusOwner(JComponent focusOwner)
/* 180:    */   {
/* 181:374 */     Object oldValue = this.focusOwner;
/* 182:375 */     this.focusOwner = focusOwner;
/* 183:376 */     firePropertyChange("focusOwner", oldValue, this.focusOwner);
/* 184:    */   }
/* 185:    */   
/* 186:    */   private List<TaskService> copyTaskServices()
/* 187:    */   {
/* 188:380 */     return new ArrayList(this.taskServices);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void addTaskService(TaskService taskService)
/* 192:    */   {
/* 193:384 */     if (taskService == null) {
/* 194:385 */       throw new IllegalArgumentException("null taskService");
/* 195:    */     }
/* 196:387 */     List<TaskService> oldValue = null;List<TaskService> newValue = null;
/* 197:388 */     boolean changed = false;
/* 198:389 */     synchronized (this.taskServices)
/* 199:    */     {
/* 200:390 */       if (!this.taskServices.contains(taskService))
/* 201:    */       {
/* 202:391 */         oldValue = copyTaskServices();
/* 203:392 */         this.taskServices.add(taskService);
/* 204:393 */         newValue = copyTaskServices();
/* 205:394 */         changed = true;
/* 206:    */       }
/* 207:    */     }
/* 208:397 */     if (changed) {
/* 209:398 */       firePropertyChange("taskServices", oldValue, newValue);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void removeTaskService(TaskService taskService)
/* 214:    */   {
/* 215:403 */     if (taskService == null) {
/* 216:404 */       throw new IllegalArgumentException("null taskService");
/* 217:    */     }
/* 218:406 */     List<TaskService> oldValue = null;List<TaskService> newValue = null;
/* 219:407 */     boolean changed = false;
/* 220:408 */     synchronized (this.taskServices)
/* 221:    */     {
/* 222:409 */       if (this.taskServices.contains(taskService))
/* 223:    */       {
/* 224:410 */         oldValue = copyTaskServices();
/* 225:411 */         this.taskServices.remove(taskService);
/* 226:412 */         newValue = copyTaskServices();
/* 227:413 */         changed = true;
/* 228:    */       }
/* 229:    */     }
/* 230:416 */     if (changed) {
/* 231:417 */       firePropertyChange("taskServices", oldValue, newValue);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public TaskService getTaskService(String name)
/* 236:    */   {
/* 237:422 */     if (name == null) {
/* 238:423 */       throw new IllegalArgumentException("null name");
/* 239:    */     }
/* 240:425 */     for (TaskService taskService : this.taskServices) {
/* 241:426 */       if (name.equals(taskService.getName())) {
/* 242:427 */         return taskService;
/* 243:    */       }
/* 244:    */     }
/* 245:430 */     return null;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final TaskService getTaskService()
/* 249:    */   {
/* 250:448 */     return getTaskService("default");
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<TaskService> getTaskServices()
/* 254:    */   {
/* 255:459 */     return this.taskServicesReadOnly;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public final TaskMonitor getTaskMonitor()
/* 259:    */   {
/* 260:470 */     if (this.taskMonitor == null) {
/* 261:471 */       this.taskMonitor = new TaskMonitor(this);
/* 262:    */     }
/* 263:473 */     return this.taskMonitor;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ApplicationContext
 * JD-Core Version:    0.7.0.1
 */