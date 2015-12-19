/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.ActiveEvent;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.EventQueue;
/*   7:    */ import java.awt.Toolkit;
/*   8:    */ import java.awt.Window;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.PaintEvent;
/*  11:    */ import java.beans.Beans;
/*  12:    */ import java.lang.reflect.Constructor;
/*  13:    */ import java.util.EventListener;
/*  14:    */ import java.util.EventObject;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*  17:    */ import java.util.logging.Level;
/*  18:    */ import java.util.logging.Logger;
/*  19:    */ import javax.swing.JPanel;
/*  20:    */ import javax.swing.JRootPane;
/*  21:    */ import javax.swing.SwingUtilities;
/*  22:    */ import javax.swing.UIManager;
/*  23:    */ 
/*  24:    */ @ProxyActions({"cut", "copy", "paste", "delete"})
/*  25:    */ public abstract class Application
/*  26:    */   extends AbstractBean
/*  27:    */ {
/*  28:127 */   private static final Logger logger = Logger.getLogger(Application.class.getName());
/*  29:128 */   private static Application application = null;
/*  30:    */   private final List<ExitListener> exitListeners;
/*  31:    */   private final ApplicationContext context;
/*  32:    */   
/*  33:    */   protected Application()
/*  34:    */   {
/*  35:142 */     this.exitListeners = new CopyOnWriteArrayList();
/*  36:143 */     this.context = new ApplicationContext();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static synchronized <T extends Application> void launch(Class<T> applicationClass, final String[] args)
/*  40:    */   {
/*  41:166 */     Runnable doCreateAndShowGUI = new Runnable()
/*  42:    */     {
/*  43:    */       public void run()
/*  44:    */       {
/*  45:    */         try
/*  46:    */         {
/*  47:169 */           Application.access$002(Application.create(this.val$applicationClass));
/*  48:170 */           Application.application.initialize(args);
/*  49:171 */           Application.application.startup();
/*  50:172 */           Application.application.waitForReady();
/*  51:    */         }
/*  52:    */         catch (Exception e)
/*  53:    */         {
/*  54:175 */           String msg = String.format("Application %s failed to launch", new Object[] { this.val$applicationClass });
/*  55:176 */           Application.logger.log(Level.SEVERE, msg, e);
/*  56:177 */           throw new Error(msg, e);
/*  57:    */         }
/*  58:    */       }
/*  59:180 */     };
/*  60:181 */     SwingUtilities.invokeLater(doCreateAndShowGUI);
/*  61:    */   }
/*  62:    */   
/*  63:    */   static <T extends Application> T create(Class<T> applicationClass)
/*  64:    */     throws Exception
/*  65:    */   {
/*  66:195 */     if (!Beans.isDesignTime()) {
/*  67:    */       try
/*  68:    */       {
/*  69:202 */         System.setProperty("java.net.useSystemProxies", "true");
/*  70:    */       }
/*  71:    */       catch (SecurityException ignoreException) {}
/*  72:    */     }
/*  73:214 */     Constructor<T> ctor = applicationClass.getDeclaredConstructor(new Class[0]);
/*  74:215 */     if (!ctor.isAccessible()) {
/*  75:    */       try
/*  76:    */       {
/*  77:217 */         ctor.setAccessible(true);
/*  78:    */       }
/*  79:    */       catch (SecurityException ignore) {}
/*  80:    */     }
/*  81:223 */     T application = (Application)ctor.newInstance(new Object[0]);
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:227 */     ApplicationContext ctx = application.getContext();
/*  86:228 */     ctx.setApplicationClass(applicationClass);
/*  87:229 */     ctx.setApplication(application);
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:234 */     ResourceMap appResourceMap = ctx.getResourceMap();
/*  93:    */     
/*  94:236 */     appResourceMap.putResource("platform", platform());
/*  95:238 */     if (!Beans.isDesignTime())
/*  96:    */     {
/*  97:243 */       String key = "Application.lookAndFeel";
/*  98:244 */       String lnfResource = appResourceMap.getString(key, new Object[0]);
/*  99:245 */       String lnf = lnfResource == null ? "system" : lnfResource;
/* 100:    */       try
/* 101:    */       {
/* 102:247 */         if (lnf.equalsIgnoreCase("system"))
/* 103:    */         {
/* 104:248 */           String name = UIManager.getSystemLookAndFeelClassName();
/* 105:249 */           UIManager.setLookAndFeel(name);
/* 106:    */         }
/* 107:251 */         else if (!lnf.equalsIgnoreCase("default"))
/* 108:    */         {
/* 109:252 */           UIManager.setLookAndFeel(lnf);
/* 110:    */         }
/* 111:    */       }
/* 112:    */       catch (Exception e)
/* 113:    */       {
/* 114:256 */         String s = "Couldn't set LookandFeel " + key + " = \"" + lnfResource + "\"";
/* 115:257 */         logger.log(Level.WARNING, s, e);
/* 116:    */       }
/* 117:    */     }
/* 118:261 */     return application;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static String platform()
/* 122:    */   {
/* 123:268 */     String platform = "default";
/* 124:    */     try
/* 125:    */     {
/* 126:270 */       String osName = System.getProperty("os.name");
/* 127:271 */       if ((osName != null) && (osName.toLowerCase().startsWith("mac os x"))) {
/* 128:272 */         platform = "osx";
/* 129:    */       }
/* 130:    */     }
/* 131:    */     catch (SecurityException ignore) {}
/* 132:277 */     return platform;
/* 133:    */   }
/* 134:    */   
/* 135:    */   void waitForReady()
/* 136:    */   {
/* 137:283 */     new DoWaitForEmptyEventQ().execute();
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected void initialize(String[] args) {}
/* 141:    */   
/* 142:    */   protected abstract void startup();
/* 143:    */   
/* 144:    */   protected void ready() {}
/* 145:    */   
/* 146:    */   protected void shutdown() {}
/* 147:    */   
/* 148:    */   private static class NotifyingEvent
/* 149:    */     extends PaintEvent
/* 150:    */     implements ActiveEvent
/* 151:    */   {
/* 152:360 */     private boolean dispatched = false;
/* 153:361 */     private boolean qEmpty = false;
/* 154:    */     
/* 155:    */     NotifyingEvent(Component c)
/* 156:    */     {
/* 157:363 */       super(801, null);
/* 158:    */     }
/* 159:    */     
/* 160:    */     synchronized boolean isDispatched()
/* 161:    */     {
/* 162:365 */       return this.dispatched;
/* 163:    */     }
/* 164:    */     
/* 165:    */     synchronized boolean isEventQEmpty()
/* 166:    */     {
/* 167:366 */       return this.qEmpty;
/* 168:    */     }
/* 169:    */     
/* 170:    */     public void dispatch()
/* 171:    */     {
/* 172:368 */       EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
/* 173:369 */       synchronized (this)
/* 174:    */       {
/* 175:370 */         this.qEmpty = (q.peekEvent() == null);
/* 176:371 */         this.dispatched = true;
/* 177:372 */         notifyAll();
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void waitForEmptyEventQ()
/* 183:    */   {
/* 184:381 */     boolean qEmpty = false;
/* 185:382 */     JPanel placeHolder = new JPanel();
/* 186:383 */     EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
/* 187:384 */     while (!qEmpty)
/* 188:    */     {
/* 189:385 */       NotifyingEvent e = new NotifyingEvent(placeHolder);
/* 190:386 */       q.postEvent(e);
/* 191:387 */       synchronized (e)
/* 192:    */       {
/* 193:388 */         while (!e.isDispatched()) {
/* 194:    */           try
/* 195:    */           {
/* 196:390 */             e.wait();
/* 197:    */           }
/* 198:    */           catch (InterruptedException ie) {}
/* 199:    */         }
/* 200:395 */         qEmpty = e.isEventQEmpty();
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private class DoWaitForEmptyEventQ
/* 206:    */     extends Task<Void, Void>
/* 207:    */   {
/* 208:    */     DoWaitForEmptyEventQ()
/* 209:    */     {
/* 210:404 */       super();
/* 211:    */     }
/* 212:    */     
/* 213:    */     protected Void doInBackground()
/* 214:    */     {
/* 215:406 */       Application.this.waitForEmptyEventQ();
/* 216:407 */       return null;
/* 217:    */     }
/* 218:    */     
/* 219:    */     protected void finished()
/* 220:    */     {
/* 221:410 */       Application.this.ready();
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public final void exit()
/* 226:    */   {
/* 227:423 */     exit(null);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void exit(EventObject event)
/* 231:    */   {
/* 232:463 */     for (ExitListener listener : this.exitListeners) {
/* 233:464 */       if (!listener.canExit(event)) {
/* 234:465 */         return;
/* 235:    */       }
/* 236:    */     }
/* 237:    */     try
/* 238:    */     {
/* 239:469 */       for (ExitListener listener : this.exitListeners) {
/* 240:    */         try
/* 241:    */         {
/* 242:471 */           listener.willExit(event);
/* 243:    */         }
/* 244:    */         catch (Exception e)
/* 245:    */         {
/* 246:474 */           logger.log(Level.WARNING, "ExitListener.willExit() failed", e);
/* 247:    */         }
/* 248:    */       }
/* 249:477 */       shutdown();
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:480 */       logger.log(Level.WARNING, "unexpected error in Application.shutdown()", e);
/* 254:    */     }
/* 255:    */     finally
/* 256:    */     {
/* 257:483 */       end();
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   protected void end()
/* 262:    */   {
/* 263:494 */     Runtime.getRuntime().exit(0);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void addExitListener(ExitListener listener)
/* 267:    */   {
/* 268:531 */     this.exitListeners.add(listener);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void removeExitListener(ExitListener listener)
/* 272:    */   {
/* 273:542 */     this.exitListeners.remove(listener);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public ExitListener[] getExitListeners()
/* 277:    */   {
/* 278:551 */     int size = this.exitListeners.size();
/* 279:552 */     return (ExitListener[])this.exitListeners.toArray(new ExitListener[size]);
/* 280:    */   }
/* 281:    */   
/* 282:    */   @Action
/* 283:    */   public void quit(ActionEvent e)
/* 284:    */   {
/* 285:563 */     exit(e);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public final ApplicationContext getContext()
/* 289:    */   {
/* 290:572 */     return this.context;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static synchronized <T extends Application> T getInstance(Class<T> applicationClass)
/* 294:    */   {
/* 295:591 */     if (application == null) {
/* 296:    */       try
/* 297:    */       {
/* 298:598 */         application = create(applicationClass);
/* 299:    */       }
/* 300:    */       catch (Exception e)
/* 301:    */       {
/* 302:601 */         String msg = String.format("Couldn't construct %s", new Object[] { applicationClass });
/* 303:602 */         throw new Error(msg, e);
/* 304:    */       }
/* 305:    */     }
/* 306:605 */     return (Application)applicationClass.cast(application);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static synchronized Application getInstance()
/* 310:    */   {
/* 311:630 */     if (application == null) {
/* 312:631 */       application = new NoApplication();
/* 313:    */     }
/* 314:633 */     return application;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public static abstract interface ExitListener
/* 318:    */     extends EventListener
/* 319:    */   {
/* 320:    */     public abstract boolean canExit(EventObject paramEventObject);
/* 321:    */     
/* 322:    */     public abstract void willExit(EventObject paramEventObject);
/* 323:    */   }
/* 324:    */   
/* 325:    */   private static class NoApplication
/* 326:    */     extends Application
/* 327:    */   {
/* 328:    */     protected NoApplication()
/* 329:    */     {
/* 330:638 */       ApplicationContext ctx = getContext();
/* 331:639 */       ctx.setApplicationClass(getClass());
/* 332:640 */       ctx.setApplication(this);
/* 333:641 */       ResourceMap appResourceMap = ctx.getResourceMap();
/* 334:642 */       appResourceMap.putResource("platform", Application.access$300());
/* 335:    */     }
/* 336:    */     
/* 337:    */     protected void startup() {}
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void show(View view)
/* 341:    */   {
/* 342:651 */     Window window = (Window)view.getRootPane().getParent();
/* 343:652 */     if (window != null)
/* 344:    */     {
/* 345:653 */       window.pack();
/* 346:654 */       window.setVisible(true);
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void hide(View view)
/* 351:    */   {
/* 352:659 */     view.getRootPane().getParent().setVisible(false);
/* 353:    */   }
/* 354:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.Application
 * JD-Core Version:    0.7.0.1
 */