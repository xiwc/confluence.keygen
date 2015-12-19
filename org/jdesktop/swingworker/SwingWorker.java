/*   1:    */ package org.jdesktop.swingworker;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.awt.event.ActionListener;
/*   5:    */ import java.beans.PropertyChangeEvent;
/*   6:    */ import java.beans.PropertyChangeListener;
/*   7:    */ import java.beans.PropertyChangeSupport;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.concurrent.BlockingQueue;
/*  10:    */ import java.util.concurrent.Callable;
/*  11:    */ import java.util.concurrent.ExecutionException;
/*  12:    */ import java.util.concurrent.ExecutorService;
/*  13:    */ import java.util.concurrent.Future;
/*  14:    */ import java.util.concurrent.FutureTask;
/*  15:    */ import java.util.concurrent.LinkedBlockingQueue;
/*  16:    */ import java.util.concurrent.ThreadFactory;
/*  17:    */ import java.util.concurrent.ThreadPoolExecutor;
/*  18:    */ import java.util.concurrent.TimeUnit;
/*  19:    */ import java.util.concurrent.TimeoutException;
/*  20:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  21:    */ import java.util.concurrent.locks.Condition;
/*  22:    */ import java.util.concurrent.locks.ReentrantLock;
/*  23:    */ import javax.swing.SwingUtilities;
/*  24:    */ import javax.swing.Timer;
/*  25:    */ 
/*  26:    */ public abstract class SwingWorker<T, V>
/*  27:    */   implements Future<T>, Runnable
/*  28:    */ {
/*  29:    */   private static final int MAX_WORKER_THREADS = 10;
/*  30:    */   private volatile int progress;
/*  31:    */   private volatile StateValue state;
/*  32:    */   private final FutureTask<T> future;
/*  33:    */   private final PropertyChangeSupport propertyChangeSupport;
/*  34:    */   private AccumulativeRunnable<V> doProcess;
/*  35:    */   private AccumulativeRunnable<Integer> doNotifyProgressChange;
/*  36:241 */   private static final AccumulativeRunnable<Runnable> doSubmit = new DoSubmitAccumulativeRunnable(null);
/*  37:244 */   private static ExecutorService executorService = null;
/*  38:    */   
/*  39:    */   public static enum StateValue
/*  40:    */   {
/*  41:253 */     PENDING,  STARTED,  DONE;
/*  42:    */     
/*  43:    */     private StateValue() {}
/*  44:    */   }
/*  45:    */   
/*  46:    */   public SwingWorker()
/*  47:    */   {
/*  48:272 */     Callable<T> callable = new Callable()
/*  49:    */     {
/*  50:    */       public T call()
/*  51:    */         throws Exception
/*  52:    */       {
/*  53:275 */         SwingWorker.this.setState(SwingWorker.StateValue.STARTED);
/*  54:276 */         return SwingWorker.this.doInBackground();
/*  55:    */       }
/*  56:279 */     };
/*  57:280 */     this.future = new FutureTask(callable)
/*  58:    */     {
/*  59:    */       protected void done()
/*  60:    */       {
/*  61:283 */         SwingWorker.this.doneEDT();
/*  62:284 */         SwingWorker.this.setState(SwingWorker.StateValue.DONE);
/*  63:    */       }
/*  64:287 */     };
/*  65:288 */     this.state = StateValue.PENDING;
/*  66:289 */     this.propertyChangeSupport = new SwingWorkerPropertyChangeSupport(this);
/*  67:290 */     this.doProcess = null;
/*  68:291 */     this.doNotifyProgressChange = null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected abstract T doInBackground()
/*  72:    */     throws Exception;
/*  73:    */   
/*  74:    */   public final void run()
/*  75:    */   {
/*  76:315 */     this.future.run();
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected final void publish(V... chunks)
/*  80:    */   {
/*  81:388 */     synchronized (this)
/*  82:    */     {
/*  83:389 */       if (this.doProcess == null) {
/*  84:390 */         this.doProcess = new AccumulativeRunnable()
/*  85:    */         {
/*  86:    */           public void run(List<V> args)
/*  87:    */           {
/*  88:393 */             SwingWorker.this.process(args);
/*  89:    */           }
/*  90:    */           
/*  91:    */           protected void submit()
/*  92:    */           {
/*  93:397 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/*  94:    */           }
/*  95:    */         };
/*  96:    */       }
/*  97:    */     }
/*  98:402 */     this.doProcess.add(chunks);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void process(List<V> chunks) {}
/* 102:    */   
/* 103:    */   protected void done() {}
/* 104:    */   
/* 105:    */   protected final void setProgress(int progress)
/* 106:    */   {
/* 107:463 */     if ((progress < 0) || (progress > 100)) {
/* 108:464 */       throw new IllegalArgumentException("the value should be from 0 to 100");
/* 109:    */     }
/* 110:466 */     if (this.progress == progress) {
/* 111:467 */       return;
/* 112:    */     }
/* 113:469 */     int oldProgress = this.progress;
/* 114:470 */     this.progress = progress;
/* 115:471 */     if (!getPropertyChangeSupport().hasListeners("progress")) {
/* 116:472 */       return;
/* 117:    */     }
/* 118:474 */     synchronized (this)
/* 119:    */     {
/* 120:475 */       if (this.doNotifyProgressChange == null) {
/* 121:476 */         this.doNotifyProgressChange = new AccumulativeRunnable()
/* 122:    */         {
/* 123:    */           public void run(List<Integer> args)
/* 124:    */           {
/* 125:480 */             SwingWorker.this.firePropertyChange("progress", args.get(0), args.get(args.size() - 1));
/* 126:    */           }
/* 127:    */           
/* 128:    */           protected void submit()
/* 129:    */           {
/* 130:486 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/* 131:    */           }
/* 132:    */         };
/* 133:    */       }
/* 134:    */     }
/* 135:491 */     this.doNotifyProgressChange.add(new Integer[] { Integer.valueOf(oldProgress), Integer.valueOf(progress) });
/* 136:    */   }
/* 137:    */   
/* 138:    */   public final int getProgress()
/* 139:    */   {
/* 140:500 */     return this.progress;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public final void execute()
/* 144:    */   {
/* 145:517 */     getWorkersExecutorService().execute(this);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final boolean cancel(boolean mayInterruptIfRunning)
/* 149:    */   {
/* 150:525 */     return this.future.cancel(mayInterruptIfRunning);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final boolean isCancelled()
/* 154:    */   {
/* 155:532 */     return this.future.isCancelled();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final boolean isDone()
/* 159:    */   {
/* 160:539 */     return this.future.isDone();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final T get()
/* 164:    */     throws InterruptedException, ExecutionException
/* 165:    */   {
/* 166:581 */     return this.future.get();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public final T get(long timeout, TimeUnit unit)
/* 170:    */     throws InterruptedException, ExecutionException, TimeoutException
/* 171:    */   {
/* 172:591 */     return this.future.get(timeout, unit);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public final void addPropertyChangeListener(PropertyChangeListener listener)
/* 176:    */   {
/* 177:610 */     getPropertyChangeSupport().addPropertyChangeListener(listener);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public final void removePropertyChangeListener(PropertyChangeListener listener)
/* 181:    */   {
/* 182:628 */     getPropertyChangeSupport().removePropertyChangeListener(listener);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/* 186:    */   {
/* 187:655 */     getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public final PropertyChangeSupport getPropertyChangeSupport()
/* 191:    */   {
/* 192:677 */     return this.propertyChangeSupport;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public final StateValue getState()
/* 196:    */   {
/* 197:692 */     if (isDone()) {
/* 198:693 */       return StateValue.DONE;
/* 199:    */     }
/* 200:695 */     return this.state;
/* 201:    */   }
/* 202:    */   
/* 203:    */   private void setState(StateValue state)
/* 204:    */   {
/* 205:704 */     StateValue old = this.state;
/* 206:705 */     this.state = state;
/* 207:706 */     firePropertyChange("state", old, state);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private void doneEDT()
/* 211:    */   {
/* 212:713 */     Runnable doDone = new Runnable()
/* 213:    */     {
/* 214:    */       public void run()
/* 215:    */       {
/* 216:716 */         SwingWorker.this.done();
/* 217:    */       }
/* 218:    */     };
/* 219:719 */     if (SwingUtilities.isEventDispatchThread()) {
/* 220:720 */       doDone.run();
/* 221:    */     } else {
/* 222:722 */       doSubmit.add(new Runnable[] { doDone });
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private static synchronized ExecutorService getWorkersExecutorService()
/* 227:    */   {
/* 228:738 */     if (executorService == null)
/* 229:    */     {
/* 230:740 */       ThreadFactory threadFactory = new ThreadFactory()
/* 231:    */       {
/* 232:742 */         final AtomicInteger threadNumber = new AtomicInteger(1);
/* 233:    */         
/* 234:    */         public Thread newThread(Runnable r)
/* 235:    */         {
/* 236:744 */           StringBuilder name = new StringBuilder("SwingWorker-pool-");
/* 237:    */           
/* 238:746 */           name.append(System.identityHashCode(this));
/* 239:747 */           name.append("-thread-");
/* 240:748 */           name.append(this.threadNumber.getAndIncrement());
/* 241:    */           
/* 242:750 */           Thread t = new Thread(r, name.toString());
/* 243:751 */           if (t.isDaemon()) {
/* 244:752 */             t.setDaemon(false);
/* 245:    */           }
/* 246:753 */           if (t.getPriority() != 5) {
/* 247:754 */             t.setPriority(5);
/* 248:    */           }
/* 249:755 */           return t;
/* 250:    */         }
/* 251:765 */       };
/* 252:766 */       executorService = new ThreadPoolExecutor(0, 10, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory)
/* 253:    */       {
/* 254:771 */         private final ReentrantLock pauseLock = new ReentrantLock();
/* 255:772 */         private final Condition unpaused = this.pauseLock.newCondition();
/* 256:773 */         private boolean isPaused = false;
/* 257:774 */         private final ReentrantLock executeLock = new ReentrantLock();
/* 258:    */         
/* 259:    */         public void execute(Runnable command)
/* 260:    */         {
/* 261:803 */           this.executeLock.lock();
/* 262:    */           try
/* 263:    */           {
/* 264:806 */             this.pauseLock.lock();
/* 265:    */             try
/* 266:    */             {
/* 267:808 */               this.isPaused = true;
/* 268:    */             }
/* 269:    */             finally
/* 270:    */             {
/* 271:810 */               this.pauseLock.unlock();
/* 272:    */             }
/* 273:813 */             setCorePoolSize(10);
/* 274:814 */             super.execute(command);
/* 275:815 */             setCorePoolSize(0);
/* 276:    */             
/* 277:817 */             this.pauseLock.lock();
/* 278:    */             try
/* 279:    */             {
/* 280:819 */               this.isPaused = false;
/* 281:820 */               this.unpaused.signalAll();
/* 282:    */             }
/* 283:    */             finally
/* 284:    */             {
/* 285:822 */               this.pauseLock.unlock();
/* 286:    */             }
/* 287:    */           }
/* 288:    */           finally
/* 289:    */           {
/* 290:825 */             this.executeLock.unlock();
/* 291:    */           }
/* 292:    */         }
/* 293:    */         
/* 294:    */         protected void afterExecute(Runnable r, Throwable t)
/* 295:    */         {
/* 296:830 */           super.afterExecute(r, t);
/* 297:831 */           this.pauseLock.lock();
/* 298:    */           try
/* 299:    */           {
/* 300:833 */             while (this.isPaused) {
/* 301:834 */               this.unpaused.await();
/* 302:    */             }
/* 303:    */           }
/* 304:    */           catch (InterruptedException ignore) {}finally
/* 305:    */           {
/* 306:839 */             this.pauseLock.unlock();
/* 307:    */           }
/* 308:    */         }
/* 309:    */       };
/* 310:    */     }
/* 311:844 */     return executorService;
/* 312:    */   }
/* 313:    */   
/* 314:    */   private static class DoSubmitAccumulativeRunnable
/* 315:    */     extends AccumulativeRunnable<Runnable>
/* 316:    */     implements ActionListener
/* 317:    */   {
/* 318:    */     private static final int DELAY = 33;
/* 319:    */     
/* 320:    */     protected void run(List<Runnable> args)
/* 321:    */     {
/* 322:852 */       int i = 0;
/* 323:    */       try
/* 324:    */       {
/* 325:854 */         for (Runnable runnable : args)
/* 326:    */         {
/* 327:855 */           i++;
/* 328:856 */           runnable.run();
/* 329:    */         }
/* 330:859 */         if (i < args.size())
/* 331:    */         {
/* 332:863 */           Runnable[] argsTail = new Runnable[args.size() - i];
/* 333:864 */           for (int j = 0; j < argsTail.length; j++) {
/* 334:865 */             argsTail[j] = ((Runnable)args.get(i + j));
/* 335:    */           }
/* 336:867 */           add(true, argsTail);
/* 337:    */         }
/* 338:    */       }
/* 339:    */       finally
/* 340:    */       {
/* 341:859 */         if (i < args.size())
/* 342:    */         {
/* 343:863 */           Runnable[] argsTail = new Runnable[args.size() - i];
/* 344:864 */           for (int j = 0; j < argsTail.length; j++) {
/* 345:865 */             argsTail[j] = ((Runnable)args.get(i + j));
/* 346:    */           }
/* 347:867 */           add(true, argsTail);
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:    */     
/* 352:    */     protected void submit()
/* 353:    */     {
/* 354:873 */       Timer timer = new Timer(33, this);
/* 355:874 */       timer.setRepeats(false);
/* 356:875 */       timer.start();
/* 357:    */     }
/* 358:    */     
/* 359:    */     public void actionPerformed(ActionEvent event)
/* 360:    */     {
/* 361:878 */       run();
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   private class SwingWorkerPropertyChangeSupport
/* 366:    */     extends PropertyChangeSupport
/* 367:    */   {
/* 368:    */     SwingWorkerPropertyChangeSupport(Object source)
/* 369:    */     {
/* 370:885 */       super();
/* 371:    */     }
/* 372:    */     
/* 373:    */     public void firePropertyChange(final PropertyChangeEvent evt)
/* 374:    */     {
/* 375:889 */       if (SwingUtilities.isEventDispatchThread()) {
/* 376:890 */         super.firePropertyChange(evt);
/* 377:    */       } else {
/* 378:892 */         SwingWorker.doSubmit.add(new Runnable[] { new Runnable()
/* 379:    */         {
/* 380:    */           public void run()
/* 381:    */           {
/* 382:895 */             SwingWorker.SwingWorkerPropertyChangeSupport.this.firePropertyChange(evt);
/* 383:    */           }
/* 384:    */         } });
/* 385:    */       }
/* 386:    */     }
/* 387:    */   }
/* 388:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.swingworker.SwingWorker
 * JD-Core Version:    0.7.0.1
 */