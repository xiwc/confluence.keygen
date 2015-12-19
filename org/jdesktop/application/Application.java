/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.ActiveEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.PaintEvent;
/*     */ import java.beans.Beans;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.EventListener;
/*     */ import java.util.EventObject;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ProxyActions({"cut", "copy", "paste", "delete"})
/*     */ public abstract class Application
/*     */   extends AbstractBean
/*     */ {
/* 127 */   private static final Logger logger = Logger.getLogger(Application.class.getName());
/* 128 */   private static Application application = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private final List<ExitListener> exitListeners;
/*     */   
/*     */ 
/*     */ 
/*     */   private final ApplicationContext context;
/*     */   
/*     */ 
/*     */ 
/*     */   protected Application()
/*     */   {
/* 142 */     this.exitListeners = new CopyOnWriteArrayList();
/* 143 */     this.context = new ApplicationContext();
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
/*     */   public static synchronized <T extends Application> void launch(Class<T> applicationClass, final String[] args)
/*     */   {
/* 166 */     Runnable doCreateAndShowGUI = new Runnable() {
/*     */       public void run() {
/*     */         try {
/* 169 */           Application.access$002(Application.create(this.val$applicationClass));
/* 170 */           Application.application.initialize(args);
/* 171 */           Application.application.startup();
/* 172 */           Application.application.waitForReady();
/*     */         }
/*     */         catch (Exception e) {
/* 175 */           String msg = String.format("Application %s failed to launch", new Object[] { this.val$applicationClass });
/* 176 */           Application.logger.log(Level.SEVERE, msg, e);
/* 177 */           throw new Error(msg, e);
/*     */         }
/*     */       }
/* 180 */     };
/* 181 */     SwingUtilities.invokeLater(doCreateAndShowGUI);
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
/*     */   static <T extends Application> T create(Class<T> applicationClass)
/*     */     throws Exception
/*     */   {
/* 195 */     if (!Beans.isDesignTime())
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/*     */ 
/* 202 */         System.setProperty("java.net.useSystemProxies", "true");
/*     */       }
/*     */       catch (SecurityException ignoreException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */     Constructor<T> ctor = applicationClass.getDeclaredConstructor(new Class[0]);
/* 215 */     if (!ctor.isAccessible()) {
/*     */       try {
/* 217 */         ctor.setAccessible(true);
/*     */       }
/*     */       catch (SecurityException ignore) {}
/*     */     }
/*     */     
/*     */ 
/* 223 */     T application = (Application)ctor.newInstance(new Object[0]);
/*     */     
/*     */ 
/*     */ 
/* 227 */     ApplicationContext ctx = application.getContext();
/* 228 */     ctx.setApplicationClass(applicationClass);
/* 229 */     ctx.setApplication(application);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 234 */     ResourceMap appResourceMap = ctx.getResourceMap();
/*     */     
/* 236 */     appResourceMap.putResource("platform", platform());
/*     */     
/* 238 */     if (!Beans.isDesignTime())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 243 */       String key = "Application.lookAndFeel";
/* 244 */       String lnfResource = appResourceMap.getString(key, new Object[0]);
/* 245 */       String lnf = lnfResource == null ? "system" : lnfResource;
/*     */       try {
/* 247 */         if (lnf.equalsIgnoreCase("system")) {
/* 248 */           String name = UIManager.getSystemLookAndFeelClassName();
/* 249 */           UIManager.setLookAndFeel(name);
/*     */         }
/* 251 */         else if (!lnf.equalsIgnoreCase("default")) {
/* 252 */           UIManager.setLookAndFeel(lnf);
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 256 */         String s = "Couldn't set LookandFeel " + key + " = \"" + lnfResource + "\"";
/* 257 */         logger.log(Level.WARNING, s, e);
/*     */       }
/*     */     }
/*     */     
/* 261 */     return application;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String platform()
/*     */   {
/* 268 */     String platform = "default";
/*     */     try {
/* 270 */       String osName = System.getProperty("os.name");
/* 271 */       if ((osName != null) && (osName.toLowerCase().startsWith("mac os x"))) {
/* 272 */         platform = "osx";
/*     */       }
/*     */     }
/*     */     catch (SecurityException ignore) {}
/*     */     
/* 277 */     return platform;
/*     */   }
/*     */   
/*     */ 
/*     */   void waitForReady()
/*     */   {
/* 283 */     new DoWaitForEmptyEventQ().execute();
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
/*     */   protected void initialize(String[] args) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void startup();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void ready() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void shutdown() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class NotifyingEvent
/*     */     extends PaintEvent
/*     */     implements ActiveEvent
/*     */   {
/* 360 */     private boolean dispatched = false;
/* 361 */     private boolean qEmpty = false;
/*     */     
/* 363 */     NotifyingEvent(Component c) { super(801, null); }
/*     */     
/* 365 */     synchronized boolean isDispatched() { return this.dispatched; }
/* 366 */     synchronized boolean isEventQEmpty() { return this.qEmpty; }
/*     */     
/* 368 */     public void dispatch() { EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
/* 369 */       synchronized (this) {
/* 370 */         this.qEmpty = (q.peekEvent() == null);
/* 371 */         this.dispatched = true;
/* 372 */         notifyAll();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void waitForEmptyEventQ()
/*     */   {
/* 381 */     boolean qEmpty = false;
/* 382 */     JPanel placeHolder = new JPanel();
/* 383 */     EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
/* 384 */     while (!qEmpty) {
/* 385 */       NotifyingEvent e = new NotifyingEvent(placeHolder);
/* 386 */       q.postEvent(e);
/* 387 */       synchronized (e) {
/* 388 */         while (!e.isDispatched()) {
/*     */           try {
/* 390 */             e.wait();
/*     */           }
/*     */           catch (InterruptedException ie) {}
/*     */         }
/*     */         
/* 395 */         qEmpty = e.isEventQEmpty();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class DoWaitForEmptyEventQ
/*     */     extends Task<Void, Void>
/*     */   {
/* 404 */     DoWaitForEmptyEventQ() { super(); }
/*     */     
/* 406 */     protected Void doInBackground() { Application.this.waitForEmptyEventQ();
/* 407 */       return null;
/*     */     }
/*     */     
/* 410 */     protected void finished() { Application.this.ready(); }
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
/*     */   public final void exit()
/*     */   {
/* 423 */     exit(null);
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
/*     */   public static abstract interface ExitListener
/*     */     extends EventListener
/*     */   {
/*     */     public abstract boolean canExit(EventObject paramEventObject);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract void willExit(EventObject paramEventObject);
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
/*     */   public void exit(EventObject event)
/*     */   {
/* 463 */     for (ExitListener listener : this.exitListeners) {
/* 464 */       if (!listener.canExit(event)) {
/* 465 */         return;
/*     */       }
/*     */     }
/*     */     try {
/* 469 */       for (ExitListener listener : this.exitListeners) {
/*     */         try {
/* 471 */           listener.willExit(event);
/*     */         }
/*     */         catch (Exception e) {
/* 474 */           logger.log(Level.WARNING, "ExitListener.willExit() failed", e);
/*     */         }
/*     */       }
/* 477 */       shutdown();
/*     */     }
/*     */     catch (Exception e) {
/* 480 */       logger.log(Level.WARNING, "unexpected error in Application.shutdown()", e);
/*     */     }
/*     */     finally {
/* 483 */       end();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void end()
/*     */   {
/* 494 */     Runtime.getRuntime().exit(0);
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
/*     */   public void addExitListener(ExitListener listener)
/*     */   {
/* 531 */     this.exitListeners.add(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeExitListener(ExitListener listener)
/*     */   {
/* 542 */     this.exitListeners.remove(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExitListener[] getExitListeners()
/*     */   {
/* 551 */     int size = this.exitListeners.size();
/* 552 */     return (ExitListener[])this.exitListeners.toArray(new ExitListener[size]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Action
/*     */   public void quit(ActionEvent e)
/*     */   {
/* 563 */     exit(e);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ApplicationContext getContext()
/*     */   {
/* 572 */     return this.context;
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
/*     */   public static synchronized <T extends Application> T getInstance(Class<T> applicationClass)
/*     */   {
/* 591 */     if (application == null)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/*     */ 
/* 598 */         application = create(applicationClass);
/*     */       }
/*     */       catch (Exception e) {
/* 601 */         String msg = String.format("Couldn't construct %s", new Object[] { applicationClass });
/* 602 */         throw new Error(msg, e);
/*     */       }
/*     */     }
/* 605 */     return (Application)applicationClass.cast(application);
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
/*     */   public static synchronized Application getInstance()
/*     */   {
/* 630 */     if (application == null) {
/* 631 */       application = new NoApplication();
/*     */     }
/* 633 */     return application;
/*     */   }
/*     */   
/*     */   private static class NoApplication extends Application {
/*     */     protected NoApplication() {
/* 638 */       ApplicationContext ctx = getContext();
/* 639 */       ctx.setApplicationClass(getClass());
/* 640 */       ctx.setApplication(this);
/* 641 */       ResourceMap appResourceMap = ctx.getResourceMap();
/* 642 */       appResourceMap.putResource("platform", Application.access$300());
/*     */     }
/*     */     
/*     */ 
/*     */     protected void startup() {}
/*     */   }
/*     */   
/*     */   public void show(View view)
/*     */   {
/* 651 */     Window window = (Window)view.getRootPane().getParent();
/* 652 */     if (window != null) {
/* 653 */       window.pack();
/* 654 */       window.setVisible(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void hide(View view) {
/* 659 */     view.getRootPane().getParent().setVisible(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\Application.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */