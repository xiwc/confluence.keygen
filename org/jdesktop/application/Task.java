/*      */ package org.jdesktop.application;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.swing.Action;
/*      */ import org.jdesktop.swingworker.SwingWorker;
/*      */ import org.jdesktop.swingworker.SwingWorker.StateValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Task<T, V>
/*      */   extends SwingWorker<T, V>
/*      */ {
/*  128 */   private static final Logger logger = Logger.getLogger(Task.class.getName());
/*      */   private final Application application;
/*      */   private String resourcePrefix;
/*      */   private ResourceMap resourceMap;
/*      */   private List<TaskListener<T, V>> taskListeners;
/*      */   private InputBlocker inputBlocker;
/*  134 */   private String name = null;
/*  135 */   private String title = null;
/*  136 */   private String description = null;
/*  137 */   private long messageTime = -1L;
/*  138 */   private String message = null;
/*  139 */   private long startTime = -1L;
/*  140 */   private long doneTime = -1L;
/*  141 */   private boolean userCanCancel = true;
/*  142 */   private boolean progressPropertyIsValid = false;
/*  143 */   private TaskService taskService = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static enum BlockingScope
/*      */   {
/*  157 */     NONE, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  162 */     ACTION, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  167 */     COMPONENT, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  172 */     WINDOW, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  177 */     APPLICATION;
/*      */     
/*      */     private BlockingScope() {} }
/*      */   
/*  181 */   private void initTask(ResourceMap resourceMap, String prefix) { this.resourceMap = resourceMap;
/*  182 */     if ((prefix == null) || (prefix.length() == 0)) {
/*  183 */       this.resourcePrefix = "";
/*      */     }
/*  185 */     else if (prefix.endsWith(".")) {
/*  186 */       this.resourcePrefix = prefix;
/*      */     }
/*      */     else {
/*  189 */       this.resourcePrefix = (prefix + ".");
/*      */     }
/*  191 */     if (resourceMap != null) {
/*  192 */       this.title = resourceMap.getString(resourceName("title"), new Object[0]);
/*  193 */       this.description = resourceMap.getString(resourceName("description"), new Object[0]);
/*  194 */       this.message = resourceMap.getString(resourceName("message"), new Object[0]);
/*  195 */       if (this.message != null) {
/*  196 */         this.messageTime = System.currentTimeMillis();
/*      */       }
/*      */     }
/*  199 */     addPropertyChangeListener(new StatePCL(null));
/*  200 */     this.taskListeners = new CopyOnWriteArrayList();
/*      */   }
/*      */   
/*      */   private ResourceMap defaultResourceMap(Application application) {
/*  204 */     return application.getContext().getResourceMap(getClass(), Task.class);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Task(Application application, ResourceMap resourceMap, String resourcePrefix)
/*      */   {
/*  236 */     this.application = application;
/*  237 */     initTask(resourceMap, resourcePrefix);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public Task(Application application, String resourcePrefix)
/*      */   {
/*  266 */     this.application = application;
/*  267 */     initTask(defaultResourceMap(application), resourcePrefix);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Task(Application application)
/*      */   {
/*  277 */     this.application = application;
/*  278 */     initTask(defaultResourceMap(application), "");
/*      */   }
/*      */   
/*      */   public final Application getApplication()
/*      */   {
/*  283 */     return this.application;
/*      */   }
/*      */   
/*      */   public final ApplicationContext getContext() {
/*  287 */     return getApplication().getContext();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized TaskService getTaskService()
/*      */   {
/*  303 */     return this.taskService;
/*      */   }
/*      */   
/*      */ 
/*      */   synchronized void setTaskService(TaskService taskService)
/*      */   {
/*      */     TaskService oldTaskService;
/*      */     
/*      */     TaskService newTaskService;
/*  312 */     synchronized (this) {
/*  313 */       oldTaskService = this.taskService;
/*  314 */       this.taskService = taskService;
/*  315 */       newTaskService = this.taskService;
/*      */     }
/*  317 */     firePropertyChange("taskService", oldTaskService, newTaskService);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final String resourceName(String suffix)
/*      */   {
/*  335 */     return this.resourcePrefix + suffix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ResourceMap getResourceMap()
/*      */   {
/*  348 */     return this.resourceMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized String getTitle()
/*      */   {
/*  366 */     return this.title;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setTitle(String title)
/*      */   {
/*      */     String oldTitle;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     String newTitle;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  389 */     synchronized (this) {
/*  390 */       oldTitle = this.title;
/*  391 */       this.title = title;
/*  392 */       newTitle = this.title;
/*      */     }
/*  394 */     firePropertyChange("title", oldTitle, newTitle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized String getDescription()
/*      */   {
/*  412 */     return this.description;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setDescription(String description)
/*      */   {
/*      */     String oldDescription;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     String newDescription;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  431 */     synchronized (this) {
/*  432 */       oldDescription = this.description;
/*  433 */       this.description = description;
/*  434 */       newDescription = this.description;
/*      */     }
/*  436 */     firePropertyChange("description", oldDescription, newDescription);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getExecutionDuration(TimeUnit unit)
/*      */   {
/*      */     long startTime;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     long doneTime;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  456 */     synchronized (this) {
/*  457 */       startTime = this.startTime;
/*  458 */       doneTime = this.doneTime; }
/*      */     long dt;
/*  460 */     long dt; if (startTime == -1L) {
/*  461 */       dt = 0L;
/*      */     } else { long dt;
/*  463 */       if (doneTime == -1L) {
/*  464 */         dt = System.currentTimeMillis() - startTime;
/*      */       }
/*      */       else
/*  467 */         dt = doneTime - startTime;
/*      */     }
/*  469 */     return unit.convert(Math.max(0L, dt), TimeUnit.MILLISECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMessage()
/*      */   {
/*  485 */     return this.message;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setMessage(String message)
/*      */   {
/*      */     String oldMessage;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     String newMessage;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  528 */     synchronized (this) {
/*  529 */       oldMessage = this.message;
/*  530 */       this.message = message;
/*  531 */       newMessage = this.message;
/*  532 */       this.messageTime = System.currentTimeMillis();
/*      */     }
/*  534 */     firePropertyChange("message", oldMessage, newMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void message(String formatResourceKey, Object... args)
/*      */   {
/*  559 */     ResourceMap resourceMap = getResourceMap();
/*  560 */     if (resourceMap != null) {
/*  561 */       setMessage(resourceMap.getString(resourceName(formatResourceKey), args));
/*      */     }
/*      */     else {
/*  564 */       setMessage(formatResourceKey);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getMessageDuration(TimeUnit unit)
/*      */   {
/*      */     long messageTime;
/*      */     
/*      */ 
/*      */ 
/*  578 */     synchronized (this) {
/*  579 */       messageTime = this.messageTime;
/*      */     }
/*  581 */     long dt = messageTime == -1L ? 0L : Math.max(0L, System.currentTimeMillis() - messageTime);
/*  582 */     return unit.convert(dt, TimeUnit.MILLISECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean getUserCanCancel()
/*      */   {
/*  597 */     return this.userCanCancel;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setUserCanCancel(boolean userCanCancel)
/*      */   {
/*      */     boolean oldValue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean newValue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  619 */     synchronized (this) {
/*  620 */       oldValue = this.userCanCancel;
/*  621 */       this.userCanCancel = userCanCancel;
/*  622 */       newValue = this.userCanCancel;
/*      */     }
/*  624 */     firePropertyChange("userCanCancel", Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isProgressPropertyValid()
/*      */   {
/*  644 */     return this.progressPropertyIsValid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setProgress(int value, int min, int max)
/*      */   {
/*  660 */     if (min >= max) {
/*  661 */       throw new IllegalArgumentException("invalid range: min >= max");
/*      */     }
/*  663 */     if ((value < min) || (value > max)) {
/*  664 */       throw new IllegalArgumentException("invalid value");
/*      */     }
/*  666 */     float percentage = (value - min) / (max - min);
/*  667 */     setProgress(Math.round(percentage * 100.0F));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setProgress(float percentage)
/*      */   {
/*  678 */     if ((percentage < 0.0D) || (percentage > 1.0D)) {
/*  679 */       throw new IllegalArgumentException("invalid percentage");
/*      */     }
/*  681 */     setProgress(Math.round(percentage * 100.0F));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setProgress(float value, float min, float max)
/*      */   {
/*  697 */     if (min >= max) {
/*  698 */       throw new IllegalArgumentException("invalid range: min >= max");
/*      */     }
/*  700 */     if ((value < min) || (value > max)) {
/*  701 */       throw new IllegalArgumentException("invalid value");
/*      */     }
/*  703 */     float percentage = (value - min) / (max - min);
/*  704 */     setProgress(Math.round(percentage * 100.0F));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isPending()
/*      */   {
/*  716 */     return getState() == SwingWorker.StateValue.PENDING;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isStarted()
/*      */   {
/*  728 */     return getState() == SwingWorker.StateValue.STARTED;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void process(List<V> values)
/*      */   {
/*  741 */     fireProcessListeners(values);
/*      */   }
/*      */   
/*      */   protected final void done() {
/*      */     try {
/*  746 */       if (isCancelled()) {
/*  747 */         cancelled();
/*      */       } else {
/*      */         try
/*      */         {
/*  751 */           succeeded(get());
/*      */         }
/*      */         catch (InterruptedException e) {
/*  754 */           interrupted(e);
/*      */         }
/*      */         catch (ExecutionException e) {
/*  757 */           failed(e.getCause());
/*      */         }
/*      */       }
/*      */     }
/*      */     finally {
/*      */       try {
/*  763 */         finished();
/*      */       }
/*      */       finally {
/*  766 */         setTaskService(null);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void cancelled() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void succeeded(T result) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void interrupted(InterruptedException e) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void failed(Throwable cause)
/*      */   {
/*  823 */     String msg = String.format("%s failed: %s", new Object[] { this, cause });
/*  824 */     logger.log(Level.SEVERE, msg, cause);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finished() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addTaskListener(TaskListener<T, V> listener)
/*      */   {
/*  854 */     if (listener == null) {
/*  855 */       throw new IllegalArgumentException("null listener");
/*      */     }
/*  857 */     this.taskListeners.add(listener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeTaskListener(TaskListener<T, V> listener)
/*      */   {
/*  868 */     if (listener == null) {
/*  869 */       throw new IllegalArgumentException("null listener");
/*      */     }
/*  871 */     this.taskListeners.remove(listener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TaskListener<T, V>[] getTaskListeners()
/*      */   {
/*  882 */     return (TaskListener[])this.taskListeners.toArray(new TaskListener[this.taskListeners.size()]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireProcessListeners(List<V> values)
/*      */   {
/*  889 */     TaskEvent<List<V>> event = new TaskEvent(this, values);
/*  890 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  891 */       listener.process(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireDoInBackgroundListeners()
/*      */   {
/*  899 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  900 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  901 */       listener.doInBackground(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireSucceededListeners(T result)
/*      */   {
/*  909 */     TaskEvent<T> event = new TaskEvent(this, result);
/*  910 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  911 */       listener.succeeded(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireCancelledListeners()
/*      */   {
/*  919 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  920 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  921 */       listener.cancelled(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireInterruptedListeners(InterruptedException e)
/*      */   {
/*  929 */     TaskEvent<InterruptedException> event = new TaskEvent(this, e);
/*  930 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  931 */       listener.interrupted(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireFailedListeners(Throwable e)
/*      */   {
/*  939 */     TaskEvent<Throwable> event = new TaskEvent(this, e);
/*  940 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  941 */       listener.failed(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void fireFinishedListeners()
/*      */   {
/*  949 */     TaskEvent<Void> event = new TaskEvent(this, null);
/*  950 */     for (TaskListener<T, V> listener : this.taskListeners) {
/*  951 */       listener.finished(event);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void fireCompletionListeners()
/*      */   {
/*      */     try
/*      */     {
/*  960 */       if (isCancelled()) {
/*  961 */         fireCancelledListeners();
/*      */       } else {
/*      */         try
/*      */         {
/*  965 */           fireSucceededListeners(get());
/*      */         }
/*      */         catch (InterruptedException e) {
/*  968 */           fireInterruptedListeners(e);
/*      */         }
/*      */         catch (ExecutionException e) {
/*  971 */           fireFailedListeners(e.getCause());
/*      */         }
/*      */       }
/*      */     }
/*      */     finally {
/*  976 */       fireFinishedListeners();
/*      */     }
/*      */   }
/*      */   
/*      */   private class StatePCL implements PropertyChangeListener { private StatePCL() {}
/*      */     
/*  982 */     public void propertyChange(PropertyChangeEvent e) { String propertyName = e.getPropertyName();
/*  983 */       if ("state".equals(propertyName)) {
/*  984 */         SwingWorker.StateValue state = (SwingWorker.StateValue)e.getNewValue();
/*  985 */         Task task = (Task)e.getSource();
/*  986 */         switch (Task.1.$SwitchMap$org$jdesktop$swingworker$SwingWorker$StateValue[state.ordinal()]) {
/*  987 */         case 1:  taskStarted(task); break;
/*  988 */         case 2:  taskDone(task);
/*      */         }
/*      */       }
/*  991 */       else if ("progress".equals(propertyName)) {
/*  992 */         synchronized (Task.this) {
/*  993 */           Task.this.progressPropertyIsValid = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  998 */     private void taskStarted(Task task) { synchronized (Task.this) {
/*  999 */         Task.this.startTime = System.currentTimeMillis();
/*      */       }
/* 1001 */       Task.this.firePropertyChange("started", Boolean.valueOf(false), Boolean.valueOf(true));
/* 1002 */       Task.this.fireDoInBackgroundListeners();
/*      */     }
/*      */     
/* 1005 */     private void taskDone(Task task) { synchronized (Task.this) {
/* 1006 */         Task.this.doneTime = System.currentTimeMillis();
/*      */       }
/*      */       try {
/* 1009 */         task.removePropertyChangeListener(this);
/* 1010 */         Task.this.firePropertyChange("done", Boolean.valueOf(false), Boolean.valueOf(true));
/* 1011 */         Task.this.fireCompletionListeners();
/*      */       }
/*      */       finally {
/* 1014 */         Task.this.firePropertyChange("completed", Boolean.valueOf(false), Boolean.valueOf(true));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final InputBlocker getInputBlocker()
/*      */   {
/* 1027 */     return this.inputBlocker;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void setInputBlocker(InputBlocker inputBlocker)
/*      */   {
/* 1046 */     if (getTaskService() != null)
/* 1047 */       throw new IllegalStateException("task already being executed");
/*      */     InputBlocker oldInputBlocker;
/*      */     InputBlocker newInputBlocker;
/* 1050 */     synchronized (this) {
/* 1051 */       oldInputBlocker = this.inputBlocker;
/* 1052 */       this.inputBlocker = inputBlocker;
/* 1053 */       newInputBlocker = this.inputBlocker;
/*      */     }
/* 1055 */     firePropertyChange("inputBlocker", oldInputBlocker, newInputBlocker);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static abstract class InputBlocker
/*      */     extends AbstractBean
/*      */   {
/*      */     private final Task task;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Task.BlockingScope scope;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final Object target;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final ApplicationAction action;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public InputBlocker(Task task, Task.BlockingScope scope, Object target, ApplicationAction action)
/*      */     {
/* 1115 */       if (task == null) {
/* 1116 */         throw new IllegalArgumentException("null task");
/*      */       }
/* 1118 */       if (task.getTaskService() != null) {
/* 1119 */         throw new IllegalStateException("task already being executed");
/*      */       }
/* 1121 */       switch (Task.1.$SwitchMap$org$jdesktop$application$Task$BlockingScope[scope.ordinal()]) {
/*      */       case 1: 
/* 1123 */         if (!(target instanceof Action)) {
/* 1124 */           throw new IllegalArgumentException("target not an Action");
/*      */         }
/*      */         break;
/*      */       case 2: 
/*      */       case 3: 
/* 1129 */         if (!(target instanceof Component)) {
/* 1130 */           throw new IllegalArgumentException("target not a Component");
/*      */         }
/*      */         break;
/*      */       }
/* 1134 */       this.task = task;
/* 1135 */       this.scope = scope;
/* 1136 */       this.target = target;
/* 1137 */       this.action = action;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public InputBlocker(Task task, Task.BlockingScope scope, Object target)
/*      */     {
/* 1152 */       this(task, scope, target, (target instanceof ApplicationAction) ? (ApplicationAction)target : null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final Task getTask()
/*      */     {
/* 1164 */       return this.task;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final Task.BlockingScope getScope()
/*      */     {
/* 1174 */       return this.scope;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final Object getTarget()
/*      */     {
/* 1187 */       return this.target;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public final ApplicationAction getAction()
/*      */     {
/* 1205 */       return this.action;
/*      */     }
/*      */     
/*      */     protected abstract void block();
/*      */     
/*      */     protected abstract void unblock();
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\Task.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */