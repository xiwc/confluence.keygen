/*     */ package org.jdesktop.swingworker;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SwingWorker<T, V>
/*     */   implements Future<T>, Runnable
/*     */ {
/*     */   private static final int MAX_WORKER_THREADS = 10;
/*     */   private volatile int progress;
/*     */   private volatile StateValue state;
/*     */   private final FutureTask<T> future;
/*     */   private final PropertyChangeSupport propertyChangeSupport;
/*     */   private AccumulativeRunnable<V> doProcess;
/*     */   private AccumulativeRunnable<Integer> doNotifyProgressChange;
/* 241 */   private static final AccumulativeRunnable<Runnable> doSubmit = new DoSubmitAccumulativeRunnable(null);
/*     */   
/*     */ 
/* 244 */   private static ExecutorService executorService = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum StateValue
/*     */   {
/* 253 */     PENDING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 258 */     STARTED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 265 */     DONE;
/*     */     
/*     */     private StateValue() {}
/*     */   }
/*     */   
/*     */   public SwingWorker()
/*     */   {
/* 272 */     Callable<T> callable = new Callable()
/*     */     {
/*     */       public T call() throws Exception {
/* 275 */         SwingWorker.this.setState(SwingWorker.StateValue.STARTED);
/* 276 */         return (T)SwingWorker.this.doInBackground();
/*     */       }
/*     */       
/* 279 */     };
/* 280 */     this.future = new FutureTask(callable)
/*     */     {
/*     */       protected void done() {
/* 283 */         SwingWorker.this.doneEDT();
/* 284 */         SwingWorker.this.setState(SwingWorker.StateValue.DONE);
/*     */       }
/*     */       
/* 287 */     };
/* 288 */     this.state = StateValue.PENDING;
/* 289 */     this.propertyChangeSupport = new SwingWorkerPropertyChangeSupport(this);
/* 290 */     this.doProcess = null;
/* 291 */     this.doNotifyProgressChange = null;
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
/*     */   protected abstract T doInBackground()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void run()
/*     */   {
/* 315 */     this.future.run();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void publish(V... chunks)
/*     */   {
/* 388 */     synchronized (this) {
/* 389 */       if (this.doProcess == null) {
/* 390 */         this.doProcess = new AccumulativeRunnable()
/*     */         {
/*     */           public void run(List<V> args) {
/* 393 */             SwingWorker.this.process(args);
/*     */           }
/*     */           
/*     */           protected void submit() {
/* 397 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/*     */           }
/*     */         };
/*     */       }
/*     */     }
/* 402 */     this.doProcess.add(chunks);
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
/*     */   protected void process(List<V> chunks) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void done() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void setProgress(int progress)
/*     */   {
/* 463 */     if ((progress < 0) || (progress > 100)) {
/* 464 */       throw new IllegalArgumentException("the value should be from 0 to 100");
/*     */     }
/* 466 */     if (this.progress == progress) {
/* 467 */       return;
/*     */     }
/* 469 */     int oldProgress = this.progress;
/* 470 */     this.progress = progress;
/* 471 */     if (!getPropertyChangeSupport().hasListeners("progress")) {
/* 472 */       return;
/*     */     }
/* 474 */     synchronized (this) {
/* 475 */       if (this.doNotifyProgressChange == null) {
/* 476 */         this.doNotifyProgressChange = new AccumulativeRunnable()
/*     */         {
/*     */           public void run(List<Integer> args)
/*     */           {
/* 480 */             SwingWorker.this.firePropertyChange("progress", args.get(0), args.get(args.size() - 1));
/*     */           }
/*     */           
/*     */ 
/*     */           protected void submit()
/*     */           {
/* 486 */             SwingWorker.doSubmit.add(new Runnable[] { this });
/*     */           }
/*     */         };
/*     */       }
/*     */     }
/* 491 */     this.doNotifyProgressChange.add(new Integer[] { Integer.valueOf(oldProgress), Integer.valueOf(progress) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getProgress()
/*     */   {
/* 500 */     return this.progress;
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
/*     */   public final void execute()
/*     */   {
/* 517 */     getWorkersExecutorService().execute(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean cancel(boolean mayInterruptIfRunning)
/*     */   {
/* 525 */     return this.future.cancel(mayInterruptIfRunning);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isCancelled()
/*     */   {
/* 532 */     return this.future.isCancelled();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isDone()
/*     */   {
/* 539 */     return this.future.isDone();
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
/*     */   public final T get()
/*     */     throws InterruptedException, ExecutionException
/*     */   {
/* 581 */     return (T)this.future.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final T get(long timeout, TimeUnit unit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 591 */     return (T)this.future.get(timeout, unit);
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
/*     */   public final void addPropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 610 */     getPropertyChangeSupport().addPropertyChangeListener(listener);
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
/*     */   public final void removePropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/* 628 */     getPropertyChangeSupport().removePropertyChangeListener(listener);
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
/*     */   public final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*     */   {
/* 655 */     getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
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
/*     */   public final PropertyChangeSupport getPropertyChangeSupport()
/*     */   {
/* 677 */     return this.propertyChangeSupport;
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
/*     */   public final StateValue getState()
/*     */   {
/* 692 */     if (isDone()) {
/* 693 */       return StateValue.DONE;
/*     */     }
/* 695 */     return this.state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setState(StateValue state)
/*     */   {
/* 704 */     StateValue old = this.state;
/* 705 */     this.state = state;
/* 706 */     firePropertyChange("state", old, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void doneEDT()
/*     */   {
/* 713 */     Runnable doDone = new Runnable()
/*     */     {
/*     */       public void run() {
/* 716 */         SwingWorker.this.done();
/*     */       }
/*     */     };
/* 719 */     if (SwingUtilities.isEventDispatchThread()) {
/* 720 */       doDone.run();
/*     */     } else {
/* 722 */       doSubmit.add(new Runnable[] { doDone });
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
/*     */   private static synchronized ExecutorService getWorkersExecutorService()
/*     */   {
/* 738 */     if (executorService == null)
/*     */     {
/* 740 */       ThreadFactory threadFactory = new ThreadFactory()
/*     */       {
/* 742 */         final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */         
/* 744 */         public Thread newThread(Runnable r) { StringBuilder name = new StringBuilder("SwingWorker-pool-");
/*     */           
/* 746 */           name.append(System.identityHashCode(this));
/* 747 */           name.append("-thread-");
/* 748 */           name.append(this.threadNumber.getAndIncrement());
/*     */           
/* 750 */           Thread t = new Thread(r, name.toString());
/* 751 */           if (t.isDaemon())
/* 752 */             t.setDaemon(false);
/* 753 */           if (t.getPriority() != 5)
/* 754 */             t.setPriority(5);
/* 755 */           return t;
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 765 */       };
/* 766 */       executorService = new ThreadPoolExecutor(0, 10, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 771 */         private final ReentrantLock pauseLock = new ReentrantLock();
/* 772 */         private final Condition unpaused = this.pauseLock.newCondition();
/* 773 */         private boolean isPaused = false;
/* 774 */         private final ReentrantLock executeLock = new ReentrantLock();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         public void execute(Runnable command)
/*     */         {
/* 803 */           this.executeLock.lock();
/*     */           try
/*     */           {
/* 806 */             this.pauseLock.lock();
/*     */             try {
/* 808 */               this.isPaused = true;
/*     */             } finally {
/* 810 */               this.pauseLock.unlock();
/*     */             }
/*     */             
/* 813 */             setCorePoolSize(10);
/* 814 */             super.execute(command);
/* 815 */             setCorePoolSize(0);
/*     */             
/* 817 */             this.pauseLock.lock();
/*     */             try {
/* 819 */               this.isPaused = false;
/* 820 */               this.unpaused.signalAll();
/*     */             } finally {
/* 822 */               this.pauseLock.unlock();
/*     */             }
/*     */           } finally {
/* 825 */             this.executeLock.unlock();
/*     */           }
/*     */         }
/*     */         
/*     */         protected void afterExecute(Runnable r, Throwable t) {
/* 830 */           super.afterExecute(r, t);
/* 831 */           this.pauseLock.lock();
/*     */           try {
/* 833 */             while (this.isPaused) {
/* 834 */               this.unpaused.await();
/*     */             }
/*     */           }
/*     */           catch (InterruptedException ignore) {}finally
/*     */           {
/* 839 */             this.pauseLock.unlock();
/*     */           }
/*     */         }
/*     */       };
/*     */     }
/* 844 */     return executorService;
/*     */   }
/*     */   
/*     */   private static class DoSubmitAccumulativeRunnable
/*     */     extends AccumulativeRunnable<Runnable>
/*     */     implements ActionListener
/*     */   {
/*     */     private static final int DELAY = 33;
/*     */     
/*     */     /* Error */
/*     */     protected void run(List<Runnable> args)
/*     */     {
/*     */       // Byte code:
/*     */       //   0: iconst_0
/*     */       //   1: istore_2
/*     */       //   2: aload_1
/*     */       //   3: invokeinterface 3 1 0
/*     */       //   8: astore_3
/*     */       //   9: aload_3
/*     */       //   10: invokeinterface 4 1 0
/*     */       //   15: ifeq +27 -> 42
/*     */       //   18: aload_3
/*     */       //   19: invokeinterface 5 1 0
/*     */       //   24: checkcast 6	java/lang/Runnable
/*     */       //   27: astore 4
/*     */       //   29: iinc 2 1
/*     */       //   32: aload 4
/*     */       //   34: invokeinterface 7 1 0
/*     */       //   39: goto -30 -> 9
/*     */       //   42: iload_2
/*     */       //   43: aload_1
/*     */       //   44: invokeinterface 8 1 0
/*     */       //   49: if_icmpge +127 -> 176
/*     */       //   52: aload_1
/*     */       //   53: invokeinterface 8 1 0
/*     */       //   58: iload_2
/*     */       //   59: isub
/*     */       //   60: anewarray 6	java/lang/Runnable
/*     */       //   63: astore_3
/*     */       //   64: iconst_0
/*     */       //   65: istore 4
/*     */       //   67: iload 4
/*     */       //   69: aload_3
/*     */       //   70: arraylength
/*     */       //   71: if_icmpge +26 -> 97
/*     */       //   74: aload_3
/*     */       //   75: iload 4
/*     */       //   77: aload_1
/*     */       //   78: iload_2
/*     */       //   79: iload 4
/*     */       //   81: iadd
/*     */       //   82: invokeinterface 9 2 0
/*     */       //   87: checkcast 6	java/lang/Runnable
/*     */       //   90: aastore
/*     */       //   91: iinc 4 1
/*     */       //   94: goto -27 -> 67
/*     */       //   97: aload_0
/*     */       //   98: iconst_1
/*     */       //   99: aload_3
/*     */       //   100: invokevirtual 10	org/jdesktop/swingworker/SwingWorker$DoSubmitAccumulativeRunnable:add	(Z[Ljava/lang/Object;)V
/*     */       //   103: goto +73 -> 176
/*     */       //   106: astore 5
/*     */       //   108: iload_2
/*     */       //   109: aload_1
/*     */       //   110: invokeinterface 8 1 0
/*     */       //   115: if_icmpge +58 -> 173
/*     */       //   118: aload_1
/*     */       //   119: invokeinterface 8 1 0
/*     */       //   124: iload_2
/*     */       //   125: isub
/*     */       //   126: anewarray 6	java/lang/Runnable
/*     */       //   129: astore 6
/*     */       //   131: iconst_0
/*     */       //   132: istore 7
/*     */       //   134: iload 7
/*     */       //   136: aload 6
/*     */       //   138: arraylength
/*     */       //   139: if_icmpge +27 -> 166
/*     */       //   142: aload 6
/*     */       //   144: iload 7
/*     */       //   146: aload_1
/*     */       //   147: iload_2
/*     */       //   148: iload 7
/*     */       //   150: iadd
/*     */       //   151: invokeinterface 9 2 0
/*     */       //   156: checkcast 6	java/lang/Runnable
/*     */       //   159: aastore
/*     */       //   160: iinc 7 1
/*     */       //   163: goto -29 -> 134
/*     */       //   166: aload_0
/*     */       //   167: iconst_1
/*     */       //   168: aload 6
/*     */       //   170: invokevirtual 10	org/jdesktop/swingworker/SwingWorker$DoSubmitAccumulativeRunnable:add	(Z[Ljava/lang/Object;)V
/*     */       //   173: aload 5
/*     */       //   175: athrow
/*     */       //   176: return
/*     */       // Line number table:
/*     */       //   Java source line #852	-> byte code offset #0
/*     */       //   Java source line #854	-> byte code offset #2
/*     */       //   Java source line #855	-> byte code offset #29
/*     */       //   Java source line #856	-> byte code offset #32
/*     */       //   Java source line #859	-> byte code offset #42
/*     */       //   Java source line #863	-> byte code offset #52
/*     */       //   Java source line #864	-> byte code offset #64
/*     */       //   Java source line #865	-> byte code offset #74
/*     */       //   Java source line #864	-> byte code offset #91
/*     */       //   Java source line #867	-> byte code offset #97
/*     */       //   Java source line #868	-> byte code offset #103
/*     */       //   Java source line #859	-> byte code offset #106
/*     */       //   Java source line #863	-> byte code offset #118
/*     */       //   Java source line #864	-> byte code offset #131
/*     */       //   Java source line #865	-> byte code offset #142
/*     */       //   Java source line #864	-> byte code offset #160
/*     */       //   Java source line #867	-> byte code offset #166
/*     */       //   Java source line #868	-> byte code offset #173
/*     */       //   Java source line #870	-> byte code offset #176
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	177	0	this	DoSubmitAccumulativeRunnable
/*     */       //   0	177	1	args	List<Runnable>
/*     */       //   1	147	2	i	int
/*     */       //   8	11	3	i$	java.util.Iterator
/*     */       //   63	37	3	argsTail	Runnable[]
/*     */       //   27	6	4	runnable	Runnable
/*     */       //   65	27	4	j	int
/*     */       //   106	68	5	localObject	Object
/*     */       //   129	40	6	argsTail	Runnable[]
/*     */       //   132	29	7	j	int
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   2	42	106	finally
/*     */       //   106	108	106	finally
/*     */     }
/*     */     
/*     */     protected void submit()
/*     */     {
/* 873 */       Timer timer = new Timer(33, this);
/* 874 */       timer.setRepeats(false);
/* 875 */       timer.start();
/*     */     }
/*     */     
/* 878 */     public void actionPerformed(ActionEvent event) { run(); }
/*     */   }
/*     */   
/*     */   private class SwingWorkerPropertyChangeSupport extends PropertyChangeSupport
/*     */   {
/*     */     SwingWorkerPropertyChangeSupport(Object source)
/*     */     {
/* 885 */       super();
/*     */     }
/*     */     
/*     */     public void firePropertyChange(final PropertyChangeEvent evt) {
/* 889 */       if (SwingUtilities.isEventDispatchThread()) {
/* 890 */         super.firePropertyChange(evt);
/*     */       } else {
/* 892 */         SwingWorker.doSubmit.add(new Runnable[] { new Runnable()
/*     */         {
/*     */           public void run() {
/* 895 */             SwingWorker.SwingWorkerPropertyChangeSupport.this.firePropertyChange(evt);
/*     */           }
/*     */         } });
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\swingworker\SwingWorker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */