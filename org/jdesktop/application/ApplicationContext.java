/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationContext
/*     */   extends AbstractBean
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(ApplicationContext.class.getName());
/*     */   private final List<TaskService> taskServices;
/*     */   private final List<TaskService> taskServicesReadOnly;
/*     */   private ResourceManager resourceManager;
/*     */   private ActionManager actionManager;
/*     */   private LocalStorage localStorage;
/*     */   private SessionStorage sessionStorage;
/*  55 */   private Application application = null;
/*  56 */   private Class applicationClass = null;
/*  57 */   private JComponent focusOwner = null;
/*  58 */   private Clipboard clipboard = null;
/*  59 */   private Throwable uncaughtException = null;
/*  60 */   private TaskMonitor taskMonitor = null;
/*     */   
/*     */   protected ApplicationContext()
/*     */   {
/*  64 */     this.resourceManager = new ResourceManager(this);
/*  65 */     this.actionManager = new ActionManager(this);
/*  66 */     this.localStorage = new LocalStorage(this);
/*  67 */     this.sessionStorage = new SessionStorage(this);
/*  68 */     this.taskServices = new CopyOnWriteArrayList();
/*  69 */     this.taskServices.add(new TaskService("default"));
/*  70 */     this.taskServicesReadOnly = Collections.unmodifiableList(this.taskServices);
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
/*     */   public final synchronized Class getApplicationClass()
/*     */   {
/*  84 */     return this.applicationClass;
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
/*     */   public final synchronized void setApplicationClass(Class applicationClass)
/*     */   {
/*  99 */     if (this.application != null) {
/* 100 */       throw new IllegalStateException("application has been launched");
/*     */     }
/* 102 */     this.applicationClass = applicationClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized Application getApplication()
/*     */   {
/* 113 */     return this.application;
/*     */   }
/*     */   
/*     */ 
/*     */   synchronized void setApplication(Application application)
/*     */   {
/* 119 */     if (this.application != null) {
/* 120 */       throw new IllegalStateException("application has already been launched");
/*     */     }
/* 122 */     this.application = application;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ResourceManager getResourceManager()
/*     */   {
/* 134 */     return this.resourceManager;
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
/*     */   protected void setResourceManager(ResourceManager resourceManager)
/*     */   {
/* 150 */     if (resourceManager == null) {
/* 151 */       throw new IllegalArgumentException("null resourceManager");
/*     */     }
/* 153 */     Object oldValue = this.resourceManager;
/* 154 */     this.resourceManager = resourceManager;
/* 155 */     firePropertyChange("resourceManager", oldValue, this.resourceManager);
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
/*     */   public final ResourceMap getResourceMap(Class cls)
/*     */   {
/* 178 */     return getResourceManager().getResourceMap(cls, cls);
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
/*     */   public final ResourceMap getResourceMap(Class startClass, Class stopClass)
/*     */   {
/* 203 */     return getResourceManager().getResourceMap(startClass, stopClass);
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
/*     */   public final ResourceMap getResourceMap()
/*     */   {
/* 224 */     return getResourceManager().getResourceMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ActionManager getActionManager()
/*     */   {
/* 233 */     return this.actionManager;
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
/*     */   protected void setActionManager(ActionManager actionManager)
/*     */   {
/* 249 */     if (actionManager == null) {
/* 250 */       throw new IllegalArgumentException("null actionManager");
/*     */     }
/* 252 */     Object oldValue = this.actionManager;
/* 253 */     this.actionManager = actionManager;
/* 254 */     firePropertyChange("actionManager", oldValue, this.actionManager);
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
/*     */   public final ApplicationActionMap getActionMap()
/*     */   {
/* 272 */     return getActionManager().getActionMap();
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
/*     */   public final ApplicationActionMap getActionMap(Class actionsClass, Object actionsObject)
/*     */   {
/* 290 */     return getActionManager().getActionMap(actionsClass, actionsObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ApplicationActionMap getActionMap(Object actionsObject)
/*     */   {
/* 300 */     if (actionsObject == null) {
/* 301 */       throw new IllegalArgumentException("null actionsObject");
/*     */     }
/* 303 */     return getActionManager().getActionMap(actionsObject.getClass(), actionsObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final LocalStorage getLocalStorage()
/*     */   {
/* 312 */     return this.localStorage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setLocalStorage(LocalStorage localStorage)
/*     */   {
/* 321 */     if (localStorage == null) {
/* 322 */       throw new IllegalArgumentException("null localStorage");
/*     */     }
/* 324 */     Object oldValue = this.localStorage;
/* 325 */     this.localStorage = localStorage;
/* 326 */     firePropertyChange("localStorage", oldValue, this.localStorage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SessionStorage getSessionStorage()
/*     */   {
/* 336 */     return this.sessionStorage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setSessionStorage(SessionStorage sessionStorage)
/*     */   {
/* 345 */     if (sessionStorage == null) {
/* 346 */       throw new IllegalArgumentException("null sessionStorage");
/*     */     }
/* 348 */     Object oldValue = this.sessionStorage;
/* 349 */     this.sessionStorage = sessionStorage;
/* 350 */     firePropertyChange("sessionStorage", oldValue, this.sessionStorage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Clipboard getClipboard()
/*     */   {
/* 357 */     if (this.clipboard == null) {
/*     */       try {
/* 359 */         this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*     */       }
/*     */       catch (SecurityException e) {
/* 362 */         this.clipboard = new Clipboard("sandbox");
/*     */       }
/*     */     }
/* 365 */     return this.clipboard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 371 */   public JComponent getFocusOwner() { return this.focusOwner; }
/*     */   
/*     */   void setFocusOwner(JComponent focusOwner) {
/* 374 */     Object oldValue = this.focusOwner;
/* 375 */     this.focusOwner = focusOwner;
/* 376 */     firePropertyChange("focusOwner", oldValue, this.focusOwner);
/*     */   }
/*     */   
/*     */   private List<TaskService> copyTaskServices() {
/* 380 */     return new ArrayList(this.taskServices);
/*     */   }
/*     */   
/*     */   public void addTaskService(TaskService taskService) {
/* 384 */     if (taskService == null) {
/* 385 */       throw new IllegalArgumentException("null taskService");
/*     */     }
/* 387 */     List<TaskService> oldValue = null;List<TaskService> newValue = null;
/* 388 */     boolean changed = false;
/* 389 */     synchronized (this.taskServices) {
/* 390 */       if (!this.taskServices.contains(taskService)) {
/* 391 */         oldValue = copyTaskServices();
/* 392 */         this.taskServices.add(taskService);
/* 393 */         newValue = copyTaskServices();
/* 394 */         changed = true;
/*     */       }
/*     */     }
/* 397 */     if (changed) {
/* 398 */       firePropertyChange("taskServices", oldValue, newValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeTaskService(TaskService taskService) {
/* 403 */     if (taskService == null) {
/* 404 */       throw new IllegalArgumentException("null taskService");
/*     */     }
/* 406 */     List<TaskService> oldValue = null;List<TaskService> newValue = null;
/* 407 */     boolean changed = false;
/* 408 */     synchronized (this.taskServices) {
/* 409 */       if (this.taskServices.contains(taskService)) {
/* 410 */         oldValue = copyTaskServices();
/* 411 */         this.taskServices.remove(taskService);
/* 412 */         newValue = copyTaskServices();
/* 413 */         changed = true;
/*     */       }
/*     */     }
/* 416 */     if (changed) {
/* 417 */       firePropertyChange("taskServices", oldValue, newValue);
/*     */     }
/*     */   }
/*     */   
/*     */   public TaskService getTaskService(String name) {
/* 422 */     if (name == null) {
/* 423 */       throw new IllegalArgumentException("null name");
/*     */     }
/* 425 */     for (TaskService taskService : this.taskServices) {
/* 426 */       if (name.equals(taskService.getName())) {
/* 427 */         return taskService;
/*     */       }
/*     */     }
/* 430 */     return null;
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
/*     */   public final TaskService getTaskService()
/*     */   {
/* 448 */     return getTaskService("default");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<TaskService> getTaskServices()
/*     */   {
/* 459 */     return this.taskServicesReadOnly;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final TaskMonitor getTaskMonitor()
/*     */   {
/* 470 */     if (this.taskMonitor == null) {
/* 471 */       this.taskMonitor = new TaskMonitor(this);
/*     */     }
/* 473 */     return this.taskMonitor;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ApplicationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */