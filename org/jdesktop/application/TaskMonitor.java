/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.jdesktop.swingworker.SwingWorker.StateValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskMonitor
/*     */   extends AbstractBean
/*     */ {
/*     */   private final PropertyChangeListener applicationPCL;
/*     */   private final PropertyChangeListener taskServicePCL;
/*     */   private final PropertyChangeListener taskPCL;
/*     */   private final LinkedList<Task> taskQueue;
/*  73 */   private boolean autoUpdateForegroundTask = true;
/*  74 */   private Task foregroundTask = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public TaskMonitor(ApplicationContext context)
/*     */   {
/*  80 */     this.applicationPCL = new ApplicationPCL(null);
/*  81 */     this.taskServicePCL = new TaskServicePCL(null);
/*  82 */     this.taskPCL = new TaskPCL(null);
/*  83 */     this.taskQueue = new LinkedList();
/*  84 */     context.addPropertyChangeListener(this.applicationPCL);
/*  85 */     for (TaskService taskService : context.getTaskServices()) {
/*  86 */       taskService.addPropertyChangeListener(this.taskServicePCL);
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
/*     */ 
/*     */ 
/*     */   public void setForegroundTask(Task foregroundTask)
/*     */   {
/* 104 */     Task oldTask = this.foregroundTask;
/* 105 */     if (oldTask != null) {
/* 106 */       oldTask.removePropertyChangeListener(this.taskPCL);
/*     */     }
/* 108 */     this.foregroundTask = foregroundTask;
/* 109 */     Task newTask = this.foregroundTask;
/* 110 */     if (newTask != null) {
/* 111 */       newTask.addPropertyChangeListener(this.taskPCL);
/*     */     }
/* 113 */     firePropertyChange("foregroundTask", oldTask, newTask);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Task getForegroundTask()
/*     */   {
/* 125 */     return this.foregroundTask;
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
/*     */   public boolean getAutoUpdateForegroundTask()
/*     */   {
/* 140 */     return this.autoUpdateForegroundTask;
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
/*     */   public void setAutoUpdateForegroundTask(boolean autoUpdateForegroundTask)
/*     */   {
/* 155 */     boolean oldValue = this.autoUpdateForegroundTask;
/* 156 */     this.autoUpdateForegroundTask = autoUpdateForegroundTask;
/* 157 */     firePropertyChange("autoUpdateForegroundTask", Boolean.valueOf(oldValue), Boolean.valueOf(this.autoUpdateForegroundTask));
/*     */   }
/*     */   
/*     */   private List<Task> copyTaskQueue() {
/* 161 */     synchronized (this.taskQueue) {
/* 162 */       if (this.taskQueue.isEmpty()) {
/* 163 */         return Collections.emptyList();
/*     */       }
/*     */       
/* 166 */       return new ArrayList(this.taskQueue);
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
/*     */   public List<Task> getTasks()
/*     */   {
/* 181 */     return copyTaskQueue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateTasks(List<Task> oldTasks, List<Task> newTasks)
/*     */   {
/* 189 */     boolean tasksChanged = false;
/* 190 */     List<Task> oldTaskQueue = copyTaskQueue();
/*     */     
/* 192 */     for (Task oldTask : oldTasks) {
/* 193 */       if ((!newTasks.contains(oldTask)) && 
/* 194 */         (this.taskQueue.remove(oldTask))) {
/* 195 */         tasksChanged = true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 200 */     for (Task newTask : newTasks) {
/* 201 */       if (!this.taskQueue.contains(newTask)) {
/* 202 */         this.taskQueue.addLast(newTask);
/* 203 */         tasksChanged = true;
/*     */       }
/*     */     }
/*     */     
/* 207 */     Iterator<Task> tasks = this.taskQueue.iterator();
/* 208 */     while (tasks.hasNext()) {
/* 209 */       Task task = (Task)tasks.next();
/* 210 */       if (task.isDone()) {
/* 211 */         tasks.remove();
/* 212 */         tasksChanged = true;
/*     */       }
/*     */     }
/*     */     
/* 216 */     if (tasksChanged) {
/* 217 */       List<Task> newTaskQueue = copyTaskQueue();
/* 218 */       firePropertyChange("tasks", oldTaskQueue, newTaskQueue);
/*     */     }
/*     */     
/* 221 */     if ((this.autoUpdateForegroundTask) && (getForegroundTask() == null)) {
/* 222 */       setForegroundTask(this.taskQueue.isEmpty() ? null : (Task)this.taskQueue.getLast());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class ApplicationPCL
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private ApplicationPCL() {}
/*     */     
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent e)
/*     */     {
/* 235 */       String propertyName = e.getPropertyName();
/* 236 */       if ("taskServices".equals(propertyName)) {
/* 237 */         List<TaskService> oldList = (List)e.getOldValue();
/* 238 */         List<TaskService> newList = (List)e.getNewValue();
/* 239 */         for (TaskService oldTaskService : oldList) {
/* 240 */           oldTaskService.removePropertyChangeListener(TaskMonitor.this.taskServicePCL);
/*     */         }
/* 242 */         for (TaskService newTaskService : newList) {
/* 243 */           newTaskService.addPropertyChangeListener(TaskMonitor.this.taskServicePCL);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private class TaskServicePCL
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private TaskServicePCL() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e)
/*     */     {
/* 256 */       String propertyName = e.getPropertyName();
/* 257 */       if ("tasks".equals(propertyName)) {
/* 258 */         List<Task> oldList = (List)e.getOldValue();
/* 259 */         List<Task> newList = (List)e.getNewValue();
/* 260 */         TaskMonitor.this.updateTasks(oldList, newList);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class TaskPCL
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private TaskPCL() {}
/*     */     
/*     */ 
/* 272 */     private void fireStateChange(Task task, String propertyName) { TaskMonitor.this.firePropertyChange(new PropertyChangeEvent(task, propertyName, Boolean.valueOf(false), Boolean.valueOf(true))); }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 275 */       String propertyName = e.getPropertyName();
/* 276 */       Task task = (Task)e.getSource();
/* 277 */       Object newValue = e.getNewValue();
/* 278 */       if ((task != null) && (task == TaskMonitor.this.getForegroundTask())) {
/* 279 */         TaskMonitor.this.firePropertyChange(e);
/* 280 */         if ("state".equals(propertyName)) {
/* 281 */           SwingWorker.StateValue newState = (SwingWorker.StateValue)e.getNewValue();
/* 282 */           switch (TaskMonitor.1.$SwitchMap$org$jdesktop$swingworker$SwingWorker$StateValue[newState.ordinal()]) {
/* 283 */           case 1:  fireStateChange(task, "pending"); break;
/* 284 */           case 2:  fireStateChange(task, "started"); break;
/*     */           case 3: 
/* 286 */             fireStateChange(task, "done");
/* 287 */             TaskMonitor.this.setForegroundTask(null);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\TaskMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */