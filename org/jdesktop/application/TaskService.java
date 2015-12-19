/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.beans.PropertyChangeEvent;
/*   4:    */ import java.beans.PropertyChangeListener;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.concurrent.ExecutorService;
/*   9:    */ import java.util.concurrent.LinkedBlockingQueue;
/*  10:    */ import java.util.concurrent.ThreadPoolExecutor;
/*  11:    */ import java.util.concurrent.TimeUnit;
/*  12:    */ import javax.swing.SwingUtilities;
/*  13:    */ 
/*  14:    */ public class TaskService
/*  15:    */   extends AbstractBean
/*  16:    */ {
/*  17:    */   private final String name;
/*  18:    */   private final ExecutorService executorService;
/*  19:    */   private final List<Task> tasks;
/*  20:    */   private final PropertyChangeListener taskPCL;
/*  21:    */   
/*  22:    */   public TaskService(String name, ExecutorService executorService)
/*  23:    */   {
/*  24: 23 */     if (name == null) {
/*  25: 24 */       throw new IllegalArgumentException("null name");
/*  26:    */     }
/*  27: 26 */     if (executorService == null) {
/*  28: 27 */       throw new IllegalArgumentException("null executorService");
/*  29:    */     }
/*  30: 29 */     this.name = name;
/*  31: 30 */     this.executorService = executorService;
/*  32: 31 */     this.tasks = new ArrayList();
/*  33: 32 */     this.taskPCL = new TaskPCL(null);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public TaskService(String name)
/*  37:    */   {
/*  38: 36 */     this(name, new ThreadPoolExecutor(3, 10, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue()));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final String getName()
/*  42:    */   {
/*  43: 44 */     return this.name;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private List<Task> copyTasksList()
/*  47:    */   {
/*  48: 48 */     synchronized (this.tasks)
/*  49:    */     {
/*  50: 49 */       if (this.tasks.isEmpty()) {
/*  51: 50 */         return Collections.emptyList();
/*  52:    */       }
/*  53: 53 */       return new ArrayList(this.tasks);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private class TaskPCL
/*  58:    */     implements PropertyChangeListener
/*  59:    */   {
/*  60:    */     private TaskPCL() {}
/*  61:    */     
/*  62:    */     public void propertyChange(PropertyChangeEvent e)
/*  63:    */     {
/*  64: 60 */       String propertyName = e.getPropertyName();
/*  65: 61 */       if ("done".equals(propertyName))
/*  66:    */       {
/*  67: 62 */         Task task = (Task)e.getSource();
/*  68: 63 */         if (task.isDone())
/*  69:    */         {
/*  70:    */           List<Task> oldTaskList;
/*  71:    */           List<Task> newTaskList;
/*  72: 65 */           synchronized (TaskService.this.tasks)
/*  73:    */           {
/*  74: 66 */             oldTaskList = TaskService.this.copyTasksList();
/*  75: 67 */             TaskService.this.tasks.remove(task);
/*  76: 68 */             task.removePropertyChangeListener(TaskService.this.taskPCL);
/*  77: 69 */             newTaskList = TaskService.this.copyTasksList();
/*  78:    */           }
/*  79: 71 */           TaskService.this.firePropertyChange("tasks", oldTaskList, newTaskList);
/*  80: 72 */           Task.InputBlocker inputBlocker = task.getInputBlocker();
/*  81: 73 */           if (inputBlocker != null) {
/*  82: 74 */             inputBlocker.unblock();
/*  83:    */           }
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void maybeBlockTask(Task task)
/*  90:    */   {
/*  91: 82 */     final Task.InputBlocker inputBlocker = task.getInputBlocker();
/*  92: 83 */     if (inputBlocker == null) {
/*  93: 84 */       return;
/*  94:    */     }
/*  95: 86 */     if (inputBlocker.getScope() != Task.BlockingScope.NONE) {
/*  96: 87 */       if (SwingUtilities.isEventDispatchThread())
/*  97:    */       {
/*  98: 88 */         inputBlocker.block();
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102: 91 */         Runnable doBlockTask = new Runnable()
/* 103:    */         {
/* 104:    */           public void run()
/* 105:    */           {
/* 106: 93 */             inputBlocker.block();
/* 107:    */           }
/* 108: 95 */         };
/* 109: 96 */         SwingUtilities.invokeLater(doBlockTask);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void execute(Task task)
/* 115:    */   {
/* 116:102 */     if (task == null) {
/* 117:103 */       throw new IllegalArgumentException("null task");
/* 118:    */     }
/* 119:105 */     if ((!task.isPending()) || (task.getTaskService() != null)) {
/* 120:106 */       throw new IllegalArgumentException("task has already been executed");
/* 121:    */     }
/* 122:108 */     task.setTaskService(this);
/* 123:    */     List<Task> oldTaskList;
/* 124:    */     List<Task> newTaskList;
/* 125:111 */     synchronized (this.tasks)
/* 126:    */     {
/* 127:112 */       oldTaskList = copyTasksList();
/* 128:113 */       this.tasks.add(task);
/* 129:114 */       newTaskList = copyTasksList();
/* 130:115 */       task.addPropertyChangeListener(this.taskPCL);
/* 131:    */     }
/* 132:117 */     firePropertyChange("tasks", oldTaskList, newTaskList);
/* 133:118 */     maybeBlockTask(task);
/* 134:119 */     this.executorService.execute(task);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<Task> getTasks()
/* 138:    */   {
/* 139:123 */     return copyTasksList();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public final void shutdown()
/* 143:    */   {
/* 144:127 */     this.executorService.shutdown();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public final List<Runnable> shutdownNow()
/* 148:    */   {
/* 149:131 */     return this.executorService.shutdownNow();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final boolean isShutdown()
/* 153:    */   {
/* 154:135 */     return this.executorService.isShutdown();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public final boolean isTerminated()
/* 158:    */   {
/* 159:139 */     return this.executorService.isTerminated();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public final boolean awaitTermination(long timeout, TimeUnit unit)
/* 163:    */     throws InterruptedException
/* 164:    */   {
/* 165:143 */     return this.executorService.awaitTermination(timeout, unit);
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.TaskService
 * JD-Core Version:    0.7.0.1
 */