/*    1:     */ package org.jdesktop.application;
/*    2:     */ 
/*    3:     */ import java.awt.Component;
/*    4:     */ import java.beans.PropertyChangeEvent;
/*    5:     */ import java.beans.PropertyChangeListener;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.concurrent.CopyOnWriteArrayList;
/*    8:     */ import java.util.concurrent.ExecutionException;
/*    9:     */ import java.util.concurrent.TimeUnit;
/*   10:     */ import java.util.logging.Level;
/*   11:     */ import java.util.logging.Logger;
/*   12:     */ import javax.swing.Action;
/*   13:     */ import org.jdesktop.swingworker.SwingWorker;
/*   14:     */ import org.jdesktop.swingworker.SwingWorker.StateValue;
/*   15:     */ 
/*   16:     */ public abstract class Task<T, V>
/*   17:     */   extends SwingWorker<T, V>
/*   18:     */ {
/*   19: 128 */   private static final Logger logger = Logger.getLogger(Task.class.getName());
/*   20:     */   private final Application application;
/*   21:     */   private String resourcePrefix;
/*   22:     */   private ResourceMap resourceMap;
/*   23:     */   private List<TaskListener<T, V>> taskListeners;
/*   24:     */   private InputBlocker inputBlocker;
/*   25: 134 */   private String name = null;
/*   26: 135 */   private String title = null;
/*   27: 136 */   private String description = null;
/*   28: 137 */   private long messageTime = -1L;
/*   29: 138 */   private String message = null;
/*   30: 139 */   private long startTime = -1L;
/*   31: 140 */   private long doneTime = -1L;
/*   32: 141 */   private boolean userCanCancel = true;
/*   33: 142 */   private boolean progressPropertyIsValid = false;
/*   34: 143 */   private TaskService taskService = null;
/*   35:     */   
/*   36:     */   public static enum BlockingScope
/*   37:     */   {
/*   38: 157 */     NONE,  ACTION,  COMPONENT,  WINDOW,  APPLICATION;
/*   39:     */     
/*   40:     */     private BlockingScope() {}
/*   41:     */   }
/*   42:     */   
/*   43:     */   private void initTask(ResourceMap resourceMap, String prefix)
/*   44:     */   {
/*   45: 181 */     this.resourceMap = resourceMap;
/*   46: 182 */     if ((prefix == null) || (prefix.length() == 0)) {
/*   47: 183 */       this.resourcePrefix = "";
/*   48: 185 */     } else if (prefix.endsWith(".")) {
/*   49: 186 */       this.resourcePrefix = prefix;
/*   50:     */     } else {
/*   51: 189 */       this.resourcePrefix = (prefix + ".");
/*   52:     */     }
/*   53: 191 */     if (resourceMap != null)
/*   54:     */     {
/*   55: 192 */       this.title = resourceMap.getString(resourceName("title"), new Object[0]);
/*   56: 193 */       this.description = resourceMap.getString(resourceName("description"), new Object[0]);
/*   57: 194 */       this.message = resourceMap.getString(resourceName("message"), new Object[0]);
/*   58: 195 */       if (this.message != null) {
/*   59: 196 */         this.messageTime = System.currentTimeMillis();
/*   60:     */       }
/*   61:     */     }
/*   62: 199 */     addPropertyChangeListener(new StatePCL(null));
/*   63: 200 */     this.taskListeners = new CopyOnWriteArrayList();
/*   64:     */   }
/*   65:     */   
/*   66:     */   private ResourceMap defaultResourceMap(Application application)
/*   67:     */   {
/*   68: 204 */     return application.getContext().getResourceMap(getClass(), Task.class);
/*   69:     */   }
/*   70:     */   
/*   71:     */   @Deprecated
/*   72:     */   public Task(Application application, ResourceMap resourceMap, String resourcePrefix)
/*   73:     */   {
/*   74: 236 */     this.application = application;
/*   75: 237 */     initTask(resourceMap, resourcePrefix);
/*   76:     */   }
/*   77:     */   
/*   78:     */   @Deprecated
/*   79:     */   public Task(Application application, String resourcePrefix)
/*   80:     */   {
/*   81: 266 */     this.application = application;
/*   82: 267 */     initTask(defaultResourceMap(application), resourcePrefix);
/*   83:     */   }
/*   84:     */   
/*   85:     */   public Task(Application application)
/*   86:     */   {
/*   87: 277 */     this.application = application;
/*   88: 278 */     initTask(defaultResourceMap(application), "");
/*   89:     */   }
/*   90:     */   
/*   91:     */   public final Application getApplication()
/*   92:     */   {
/*   93: 283 */     return this.application;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public final ApplicationContext getContext()
/*   97:     */   {
/*   98: 287 */     return getApplication().getContext();
/*   99:     */   }
/*  100:     */   
/*  101:     */   public synchronized TaskService getTaskService()
/*  102:     */   {
/*  103: 303 */     return this.taskService;
/*  104:     */   }
/*  105:     */   
/*  106:     */   synchronized void setTaskService(TaskService taskService)
/*  107:     */   {
/*  108:     */     TaskService oldTaskService;
/*  109:     */     TaskService newTaskService;
/*  110: 312 */     synchronized (this)
/*  111:     */     {
/*  112: 313 */       oldTaskService = this.taskService;
/*  113: 314 */       this.taskService = taskService;
/*  114: 315 */       newTaskService = this.taskService;
/*  115:     */     }
/*  116: 317 */     firePropertyChange("taskService", oldTaskService, newTaskService);
/*  117:     */   }
/*  118:     */   
/*  119:     */   protected final String resourceName(String suffix)
/*  120:     */   {
/*  121: 335 */     return this.resourcePrefix + suffix;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public final ResourceMap getResourceMap()
/*  125:     */   {
/*  126: 348 */     return this.resourceMap;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public synchronized String getTitle()
/*  130:     */   {
/*  131: 366 */     return this.title;
/*  132:     */   }
/*  133:     */   
/*  134:     */   protected void setTitle(String title)
/*  135:     */   {
/*  136:     */     String oldTitle;
/*  137:     */     String newTitle;
/*  138: 389 */     synchronized (this)
/*  139:     */     {
/*  140: 390 */       oldTitle = this.title;
/*  141: 391 */       this.title = title;
/*  142: 392 */       newTitle = this.title;
/*  143:     */     }
/*  144: 394 */     firePropertyChange("title", oldTitle, newTitle);
/*  145:     */   }
/*  146:     */   
/*  147:     */   public synchronized String getDescription()
/*  148:     */   {
/*  149: 412 */     return this.description;
/*  150:     */   }
/*  151:     */   
/*  152:     */   protected void setDescription(String description)
/*  153:     */   {
/*  154:     */     String oldDescription;
/*  155:     */     String newDescription;
/*  156: 431 */     synchronized (this)
/*  157:     */     {
/*  158: 432 */       oldDescription = this.description;
/*  159: 433 */       this.description = description;
/*  160: 434 */       newDescription = this.description;
/*  161:     */     }
/*  162: 436 */     firePropertyChange("description", oldDescription, newDescription);
/*  163:     */   }
/*  164:     */   
/*  165:     */   public long getExecutionDuration(TimeUnit unit)
/*  166:     */   {
/*  167:     */     long startTime;
/*  168:     */     long doneTime;
/*  169: 456 */     synchronized (this)
/*  170:     */     {
/*  171: 457 */       startTime = this.startTime;
/*  172: 458 */       doneTime = this.doneTime;
/*  173:     */     }
/*  174:     */     long dt;
/*  175:     */     long dt;
/*  176: 460 */     if (startTime == -1L)
/*  177:     */     {
/*  178: 461 */       dt = 0L;
/*  179:     */     }
/*  180:     */     else
/*  181:     */     {
/*  182:     */       long dt;
/*  183: 463 */       if (doneTime == -1L) {
/*  184: 464 */         dt = System.currentTimeMillis() - startTime;
/*  185:     */       } else {
/*  186: 467 */         dt = doneTime - startTime;
/*  187:     */       }
/*  188:     */     }
/*  189: 469 */     return unit.convert(Math.max(0L, dt), TimeUnit.MILLISECONDS);
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String getMessage()
/*  193:     */   {
/*  194: 485 */     return this.message;
/*  195:     */   }
/*  196:     */   
/*  197:     */   protected void setMessage(String message)
/*  198:     */   {
/*  199:     */     String oldMessage;
/*  200:     */     String newMessage;
/*  201: 528 */     synchronized (this)
/*  202:     */     {
/*  203: 529 */       oldMessage = this.message;
/*  204: 530 */       this.message = message;
/*  205: 531 */       newMessage = this.message;
/*  206: 532 */       this.messageTime = System.currentTimeMillis();
/*  207:     */     }
/*  208: 534 */     firePropertyChange("message", oldMessage, newMessage);
/*  209:     */   }
/*  210:     */   
/*  211:     */   protected final void message(String formatResourceKey, Object... args)
/*  212:     */   {
/*  213: 559 */     ResourceMap resourceMap = getResourceMap();
/*  214: 560 */     if (resourceMap != null) {
/*  215: 561 */       setMessage(resourceMap.getString(resourceName(formatResourceKey), args));
/*  216:     */     } else {
/*  217: 564 */       setMessage(formatResourceKey);
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public long getMessageDuration(TimeUnit unit)
/*  222:     */   {
/*  223:     */     long messageTime;
/*  224: 578 */     synchronized (this)
/*  225:     */     {
/*  226: 579 */       messageTime = this.messageTime;
/*  227:     */     }
/*  228: 581 */     long dt = messageTime == -1L ? 0L : Math.max(0L, System.currentTimeMillis() - messageTime);
/*  229: 582 */     return unit.convert(dt, TimeUnit.MILLISECONDS);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public synchronized boolean getUserCanCancel()
/*  233:     */   {
/*  234: 597 */     return this.userCanCancel;
/*  235:     */   }
/*  236:     */   
/*  237:     */   protected void setUserCanCancel(boolean userCanCancel)
/*  238:     */   {
/*  239:     */     boolean oldValue;
/*  240:     */     boolean newValue;
/*  241: 619 */     synchronized (this)
/*  242:     */     {
/*  243: 620 */       oldValue = this.userCanCancel;
/*  244: 621 */       this.userCanCancel = userCanCancel;
/*  245: 622 */       newValue = this.userCanCancel;
/*  246:     */     }
/*  247: 624 */     firePropertyChange("userCanCancel", Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
/*  248:     */   }
/*  249:     */   
/*  250:     */   public synchronized boolean isProgressPropertyValid()
/*  251:     */   {
/*  252: 644 */     return this.progressPropertyIsValid;
/*  253:     */   }
/*  254:     */   
/*  255:     */   protected final void setProgress(int value, int min, int max)
/*  256:     */   {
/*  257: 660 */     if (min >= max) {
/*  258: 661 */       throw new IllegalArgumentException("invalid range: min >= max");
/*  259:     */     }
/*  260: 663 */     if ((value < min) || (value > max)) {
/*  261: 664 */       throw new IllegalArgumentException("invalid value");
/*  262:     */     }
/*  263: 666 */     float percentage = (value - min) / (max - min);
/*  264: 667 */     setProgress(Math.round(percentage * 100.0F));
/*  265:     */   }
/*  266:     */   
/*  267:     */   protected final void setProgress(float percentage)
/*  268:     */   {
/*  269: 678 */     if ((percentage < 0.0D) || (percentage > 1.0D)) {
/*  270: 679 */       throw new IllegalArgumentException("invalid percentage");
/*  271:     */     }
/*  272: 681 */     setProgress(Math.round(percentage * 100.0F));
/*  273:     */   }
/*  274:     */   
/*  275:     */   protected final void setProgress(float value, float min, float max)
/*  276:     */   {
/*  277: 697 */     if (min >= max) {
/*  278: 698 */       throw new IllegalArgumentException("invalid range: min >= max");
/*  279:     */     }
/*  280: 700 */     if ((value < min) || (value > max)) {
/*  281: 701 */       throw new IllegalArgumentException("invalid value");
/*  282:     */     }
/*  283: 703 */     float percentage = (value - min) / (max - min);
/*  284: 704 */     setProgress(Math.round(percentage * 100.0F));
/*  285:     */   }
/*  286:     */   
/*  287:     */   public final boolean isPending()
/*  288:     */   {
/*  289: 716 */     return getState() == SwingWorker.StateValue.PENDING;
/*  290:     */   }
/*  291:     */   
/*  292:     */   public final boolean isStarted()
/*  293:     */   {
/*  294: 728 */     return getState() == SwingWorker.StateValue.STARTED;
/*  295:     */   }
/*  296:     */   
/*  297:     */   protected void process(List<V> values)
/*  298:     */   {
/*  299: 741 */     fireProcessListeners(values);
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected final void done()
/*  303:     */   {
/*  304:     */     try
/*  305:     */     {
/*  306: 746 */       if (isCancelled()) {
/*  307: 747 */         cancelled();
/*  308:     */       } else {
/*  309:     */         try
/*  310:     */         {
/*  311: 751 */           succeeded(get());
/*  312:     */         }
/*  313:     */         catch (InterruptedException e)
/*  314:     */         {
/*  315: 754 */           interrupted(e);
/*  316:     */         }
/*  317:     */         catch (ExecutionException e)
/*  318:     */         {
/*  319: 757 */           failed(e.getCause());
/*  320:     */         }
/*  321:     */       }
/*  322:     */     }
/*  323:     */     finally
/*  324:     */     {
/*  325:     */       try
/*  326:     */       {
/*  327: 763 */         finished();
/*  328:     */       }
/*  329:     */       finally
/*  330:     */       {
/*  331: 766 */         setTaskService(null);
/*  332:     */       }
/*  333:     */     }
/*  334:     */   }
/*  335:     */   
/*  336:     */   protected void cancelled() {}
/*  337:     */   
/*  338:     */   protected void succeeded(T result) {}
/*  339:     */   
/*  340:     */   protected void interrupted(InterruptedException e) {}
/*  341:     */   
/*  342:     */   protected void failed(Throwable cause)
/*  343:     */   {
/*  344: 823 */     String msg = String.format("%s failed: %s", new Object[] { this, cause });
/*  345: 824 */     logger.log(Level.SEVERE, msg, cause);
/*  346:     */   }
/*  347:     */   
/*  348:     */   protected void finished() {}
/*  349:     */   
/*  350:     */   public void addTaskListener(TaskListener<T, V> listener)
/*  351:     */   {
/*  352: 854 */     if (listener == null) {
/*  353: 855 */       throw new IllegalArgumentException("null listener");
/*  354:     */     }
/*  355: 857 */     this.taskListeners.add(listener);
/*  356:     */   }
/*  357:     */   
/*  358:     */   public void removeTaskListener(TaskListener<T, V> listener)
/*  359:     */   {
/*  360: 868 */     if (listener == null) {
/*  361: 869 */       throw new IllegalArgumentException("null listener");
/*  362:     */     }
/*  363: 871 */     this.taskListeners.remove(listener);
/*  364:     */   }
/*  365:     */   
/*  366:     */   public TaskListener<T, V>[] getTaskListeners()
/*  367:     */   {
/*  368: 882 */     return (TaskListener[])this.taskListeners.toArray(new TaskListener[this.taskListeners.size()]);
/*  369:     */   }
/*  370:     */   
/*  371:     */   private void fireProcessListeners(List<V> values)
/*  372:     */   {
/*  373: 889 */     TaskEvent<List<V>> event = new TaskEvent(this, values);
/*  374: 890 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  375: 891 */       listener.process(event);
/*  376:     */     }
/*  377:     */   }
/*  378:     */   
/*  379:     */   private void fireDoInBackgroundListeners()
/*  380:     */   {
/*  381: 899 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  382: 900 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  383: 901 */       listener.doInBackground(event);
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   private void fireSucceededListeners(T result)
/*  388:     */   {
/*  389: 909 */     TaskEvent<T> event = new TaskEvent(this, result);
/*  390: 910 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  391: 911 */       listener.succeeded(event);
/*  392:     */     }
/*  393:     */   }
/*  394:     */   
/*  395:     */   private void fireCancelledListeners()
/*  396:     */   {
/*  397: 919 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  398: 920 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  399: 921 */       listener.cancelled(event);
/*  400:     */     }
/*  401:     */   }
/*  402:     */   
/*  403:     */   private void fireInterruptedListeners(InterruptedException e)
/*  404:     */   {
/*  405: 929 */     TaskEvent<InterruptedException> event = new TaskEvent(this, e);
/*  406: 930 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  407: 931 */       listener.interrupted(event);
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   private void fireFailedListeners(Throwable e)
/*  412:     */   {
/*  413: 939 */     TaskEvent<Throwable> event = new TaskEvent(this, e);
/*  414: 940 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  415: 941 */       listener.failed(event);
/*  416:     */     }
/*  417:     */   }
/*  418:     */   
/*  419:     */   private void fireFinishedListeners()
/*  420:     */   {
/*  421: 949 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  422: 950 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  423: 951 */       listener.finished(event);
/*  424:     */     }
/*  425:     */   }
/*  426:     */   
/*  427:     */   private void fireCompletionListeners()
/*  428:     */   {
/*  429:     */     try
/*  430:     */     {
/*  431: 960 */       if (isCancelled()) {
/*  432: 961 */         fireCancelledListeners();
/*  433:     */       } else {
/*  434:     */         try
/*  435:     */         {
/*  436: 965 */           fireSucceededListeners(get());
/*  437:     */         }
/*  438:     */         catch (InterruptedException e)
/*  439:     */         {
/*  440: 968 */           fireInterruptedListeners(e);
/*  441:     */         }
/*  442:     */         catch (ExecutionException e)
/*  443:     */         {
/*  444: 971 */           fireFailedListeners(e.getCause());
/*  445:     */         }
/*  446:     */       }
/*  447:     */     }
/*  448:     */     finally
/*  449:     */     {
/*  450: 976 */       fireFinishedListeners();
/*  451:     */     }
/*  452:     */   }
/*  453:     */   
/*  454:     */   private class StatePCL
/*  455:     */     implements PropertyChangeListener
/*  456:     */   {
/*  457:     */     private StatePCL() {}
/*  458:     */     
/*  459:     */     public void propertyChange(PropertyChangeEvent e)
/*  460:     */     {
/*  461: 982 */       String propertyName = e.getPropertyName();
/*  462: 983 */       if ("state".equals(propertyName))
/*  463:     */       {
/*  464: 984 */         SwingWorker.StateValue state = (SwingWorker.StateValue)e.getNewValue();
/*  465: 985 */         Task task = (Task)e.getSource();
/*  466: 986 */         switch (Task.1.$SwitchMap$org$jdesktop$swingworker$SwingWorker$StateValue[state.ordinal()])
/*  467:     */         {
/*  468:     */         case 1: 
/*  469: 987 */           taskStarted(task); break;
/*  470:     */         case 2: 
/*  471: 988 */           taskDone(task);
/*  472:     */         }
/*  473:     */       }
/*  474: 991 */       else if ("progress".equals(propertyName))
/*  475:     */       {
/*  476: 992 */         synchronized (Task.this)
/*  477:     */         {
/*  478: 993 */           Task.this.progressPropertyIsValid = true;
/*  479:     */         }
/*  480:     */       }
/*  481:     */     }
/*  482:     */     
/*  483:     */     private void taskStarted(Task task)
/*  484:     */     {
/*  485: 998 */       synchronized (Task.this)
/*  486:     */       {
/*  487: 999 */         Task.this.startTime = System.currentTimeMillis();
/*  488:     */       }
/*  489:1001 */       Task.this.firePropertyChange("started", Boolean.valueOf(false), Boolean.valueOf(true));
/*  490:1002 */       Task.this.fireDoInBackgroundListeners();
/*  491:     */     }
/*  492:     */     
/*  493:     */     private void taskDone(Task task)
/*  494:     */     {
/*  495:1005 */       synchronized (Task.this)
/*  496:     */       {
/*  497:1006 */         Task.this.doneTime = System.currentTimeMillis();
/*  498:     */       }
/*  499:     */       try
/*  500:     */       {
/*  501:1009 */         task.removePropertyChangeListener(this);
/*  502:1010 */         Task.this.firePropertyChange("done", Boolean.valueOf(false), Boolean.valueOf(true));
/*  503:1011 */         Task.this.fireCompletionListeners();
/*  504:     */       }
/*  505:     */       finally
/*  506:     */       {
/*  507:1014 */         Task.this.firePropertyChange("completed", Boolean.valueOf(false), Boolean.valueOf(true));
/*  508:     */       }
/*  509:     */     }
/*  510:     */   }
/*  511:     */   
/*  512:     */   public final InputBlocker getInputBlocker()
/*  513:     */   {
/*  514:1027 */     return this.inputBlocker;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public final void setInputBlocker(InputBlocker inputBlocker)
/*  518:     */   {
/*  519:1046 */     if (getTaskService() != null) {
/*  520:1047 */       throw new IllegalStateException("task already being executed");
/*  521:     */     }
/*  522:     */     InputBlocker oldInputBlocker;
/*  523:     */     InputBlocker newInputBlocker;
/*  524:1050 */     synchronized (this)
/*  525:     */     {
/*  526:1051 */       oldInputBlocker = this.inputBlocker;
/*  527:1052 */       this.inputBlocker = inputBlocker;
/*  528:1053 */       newInputBlocker = this.inputBlocker;
/*  529:     */     }
/*  530:1055 */     firePropertyChange("inputBlocker", oldInputBlocker, newInputBlocker);
/*  531:     */   }
/*  532:     */   
/*  533:     */   public static abstract class InputBlocker
/*  534:     */     extends AbstractBean
/*  535:     */   {
/*  536:     */     private final Task task;
/*  537:     */     private final Task.BlockingScope scope;
/*  538:     */     private final Object target;
/*  539:     */     private final ApplicationAction action;
/*  540:     */     
/*  541:     */     public InputBlocker(Task task, Task.BlockingScope scope, Object target, ApplicationAction action)
/*  542:     */     {
/*  543:1115 */       if (task == null) {
/*  544:1116 */         throw new IllegalArgumentException("null task");
/*  545:     */       }
/*  546:1118 */       if (task.getTaskService() != null) {
/*  547:1119 */         throw new IllegalStateException("task already being executed");
/*  548:     */       }
/*  549:1121 */       switch (Task.1.$SwitchMap$org$jdesktop$application$Task$BlockingScope[scope.ordinal()])
/*  550:     */       {
/*  551:     */       case 1: 
/*  552:1123 */         if (!(target instanceof Action)) {
/*  553:1124 */           throw new IllegalArgumentException("target not an Action");
/*  554:     */         }
/*  555:     */         break;
/*  556:     */       case 2: 
/*  557:     */       case 3: 
/*  558:1129 */         if (!(target instanceof Component)) {
/*  559:1130 */           throw new IllegalArgumentException("target not a Component");
/*  560:     */         }
/*  561:     */         break;
/*  562:     */       }
/*  563:1134 */       this.task = task;
/*  564:1135 */       this.scope = scope;
/*  565:1136 */       this.target = target;
/*  566:1137 */       this.action = action;
/*  567:     */     }
/*  568:     */     
/*  569:     */     public InputBlocker(Task task, Task.BlockingScope scope, Object target)
/*  570:     */     {
/*  571:1152 */       this(task, scope, target, (target instanceof ApplicationAction) ? (ApplicationAction)target : null);
/*  572:     */     }
/*  573:     */     
/*  574:     */     public final Task getTask()
/*  575:     */     {
/*  576:1164 */       return this.task;
/*  577:     */     }
/*  578:     */     
/*  579:     */     public final Task.BlockingScope getScope()
/*  580:     */     {
/*  581:1174 */       return this.scope;
/*  582:     */     }
/*  583:     */     
/*  584:     */     public final Object getTarget()
/*  585:     */     {
/*  586:1187 */       return this.target;
/*  587:     */     }
/*  588:     */     
/*  589:     */     public final ApplicationAction getAction()
/*  590:     */     {
/*  591:1205 */       return this.action;
/*  592:     */     }
/*  593:     */     
/*  594:     */     protected abstract void block();
/*  595:     */     
/*  596:     */     protected abstract void unblock();
/*  597:     */   }
/*  598:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.Task
 * JD-Core Version:    0.7.0.1
 */