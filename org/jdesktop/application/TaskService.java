/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ public class TaskService extends AbstractBean
/*     */ {
/*     */   private final String name;
/*     */   private final ExecutorService executorService;
/*     */   private final List<Task> tasks;
/*     */   private final PropertyChangeListener taskPCL;
/*     */   
/*     */   public TaskService(String name, ExecutorService executorService)
/*     */   {
/*  23 */     if (name == null) {
/*  24 */       throw new IllegalArgumentException("null name");
/*     */     }
/*  26 */     if (executorService == null) {
/*  27 */       throw new IllegalArgumentException("null executorService");
/*     */     }
/*  29 */     this.name = name;
/*  30 */     this.executorService = executorService;
/*  31 */     this.tasks = new ArrayList();
/*  32 */     this.taskPCL = new TaskPCL(null);
/*     */   }
/*     */   
/*     */   public TaskService(String name) {
/*  36 */     this(name, new ThreadPoolExecutor(3, 10, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getName()
/*     */   {
/*  44 */     return this.name;
/*     */   }
/*     */   
/*     */   private List<Task> copyTasksList() {
/*  48 */     synchronized (this.tasks) {
/*  49 */       if (this.tasks.isEmpty()) {
/*  50 */         return Collections.emptyList();
/*     */       }
/*     */       
/*  53 */       return new ArrayList(this.tasks);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TaskPCL implements PropertyChangeListener {
/*     */     private TaskPCL() {}
/*     */     
/*  60 */     public void propertyChange(PropertyChangeEvent e) { String propertyName = e.getPropertyName();
/*  61 */       if ("done".equals(propertyName)) {
/*  62 */         Task task = (Task)e.getSource();
/*  63 */         if (task.isDone()) { List<Task> oldTaskList;
/*     */           List<Task> newTaskList;
/*  65 */           synchronized (TaskService.this.tasks) {
/*  66 */             oldTaskList = TaskService.this.copyTasksList();
/*  67 */             TaskService.this.tasks.remove(task);
/*  68 */             task.removePropertyChangeListener(TaskService.this.taskPCL);
/*  69 */             newTaskList = TaskService.this.copyTasksList();
/*     */           }
/*  71 */           TaskService.this.firePropertyChange("tasks", oldTaskList, newTaskList);
/*  72 */           Task.InputBlocker inputBlocker = task.getInputBlocker();
/*  73 */           if (inputBlocker != null) {
/*  74 */             inputBlocker.unblock();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void maybeBlockTask(Task task) {
/*  82 */     final Task.InputBlocker inputBlocker = task.getInputBlocker();
/*  83 */     if (inputBlocker == null) {
/*  84 */       return;
/*     */     }
/*  86 */     if (inputBlocker.getScope() != Task.BlockingScope.NONE) {
/*  87 */       if (SwingUtilities.isEventDispatchThread()) {
/*  88 */         inputBlocker.block();
/*     */       }
/*     */       else {
/*  91 */         Runnable doBlockTask = new Runnable() {
/*     */           public void run() {
/*  93 */             inputBlocker.block();
/*     */           }
/*  95 */         };
/*  96 */         SwingUtilities.invokeLater(doBlockTask);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void execute(Task task) {
/* 102 */     if (task == null) {
/* 103 */       throw new IllegalArgumentException("null task");
/*     */     }
/* 105 */     if ((!task.isPending()) || (task.getTaskService() != null)) {
/* 106 */       throw new IllegalArgumentException("task has already been executed");
/*     */     }
/* 108 */     task.setTaskService(this);
/*     */     List<Task> oldTaskList;
/*     */     List<Task> newTaskList;
/* 111 */     synchronized (this.tasks) {
/* 112 */       oldTaskList = copyTasksList();
/* 113 */       this.tasks.add(task);
/* 114 */       newTaskList = copyTasksList();
/* 115 */       task.addPropertyChangeListener(this.taskPCL);
/*     */     }
/* 117 */     firePropertyChange("tasks", oldTaskList, newTaskList);
/* 118 */     maybeBlockTask(task);
/* 119 */     this.executorService.execute(task);
/*     */   }
/*     */   
/*     */   public List<Task> getTasks() {
/* 123 */     return copyTasksList();
/*     */   }
/*     */   
/*     */   public final void shutdown() {
/* 127 */     this.executorService.shutdown();
/*     */   }
/*     */   
/*     */   public final List<Runnable> shutdownNow() {
/* 131 */     return this.executorService.shutdownNow();
/*     */   }
/*     */   
/*     */   public final boolean isShutdown() {
/* 135 */     return this.executorService.isShutdown();
/*     */   }
/*     */   
/*     */   public final boolean isTerminated() {
/* 139 */     return this.executorService.isTerminated();
/*     */   }
/*     */   
/*     */   public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 143 */     return this.executorService.awaitTermination(timeout, unit);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\TaskService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */