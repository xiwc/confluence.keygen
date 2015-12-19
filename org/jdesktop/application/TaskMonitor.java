/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.beans.PropertyChangeEvent;
/*   4:    */ import java.beans.PropertyChangeListener;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import org.jdesktop.swingworker.SwingWorker.StateValue;
/*  11:    */ 
/*  12:    */ public class TaskMonitor
/*  13:    */   extends AbstractBean
/*  14:    */ {
/*  15:    */   private final PropertyChangeListener applicationPCL;
/*  16:    */   private final PropertyChangeListener taskServicePCL;
/*  17:    */   private final PropertyChangeListener taskPCL;
/*  18:    */   private final LinkedList<Task> taskQueue;
/*  19: 73 */   private boolean autoUpdateForegroundTask = true;
/*  20: 74 */   private Task foregroundTask = null;
/*  21:    */   
/*  22:    */   public TaskMonitor(ApplicationContext context)
/*  23:    */   {
/*  24: 80 */     this.applicationPCL = new ApplicationPCL(null);
/*  25: 81 */     this.taskServicePCL = new TaskServicePCL(null);
/*  26: 82 */     this.taskPCL = new TaskPCL(null);
/*  27: 83 */     this.taskQueue = new LinkedList();
/*  28: 84 */     context.addPropertyChangeListener(this.applicationPCL);
/*  29: 85 */     for (TaskService taskService : context.getTaskServices()) {
/*  30: 86 */       taskService.addPropertyChangeListener(this.taskServicePCL);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setForegroundTask(Task foregroundTask)
/*  35:    */   {
/*  36:104 */     Task oldTask = this.foregroundTask;
/*  37:105 */     if (oldTask != null) {
/*  38:106 */       oldTask.removePropertyChangeListener(this.taskPCL);
/*  39:    */     }
/*  40:108 */     this.foregroundTask = foregroundTask;
/*  41:109 */     Task newTask = this.foregroundTask;
/*  42:110 */     if (newTask != null) {
/*  43:111 */       newTask.addPropertyChangeListener(this.taskPCL);
/*  44:    */     }
/*  45:113 */     firePropertyChange("foregroundTask", oldTask, newTask);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Task getForegroundTask()
/*  49:    */   {
/*  50:125 */     return this.foregroundTask;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean getAutoUpdateForegroundTask()
/*  54:    */   {
/*  55:140 */     return this.autoUpdateForegroundTask;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setAutoUpdateForegroundTask(boolean autoUpdateForegroundTask)
/*  59:    */   {
/*  60:155 */     boolean oldValue = this.autoUpdateForegroundTask;
/*  61:156 */     this.autoUpdateForegroundTask = autoUpdateForegroundTask;
/*  62:157 */     firePropertyChange("autoUpdateForegroundTask", Boolean.valueOf(oldValue), Boolean.valueOf(this.autoUpdateForegroundTask));
/*  63:    */   }
/*  64:    */   
/*  65:    */   private List<Task> copyTaskQueue()
/*  66:    */   {
/*  67:161 */     synchronized (this.taskQueue)
/*  68:    */     {
/*  69:162 */       if (this.taskQueue.isEmpty()) {
/*  70:163 */         return Collections.emptyList();
/*  71:    */       }
/*  72:166 */       return new ArrayList(this.taskQueue);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public List<Task> getTasks()
/*  77:    */   {
/*  78:181 */     return copyTaskQueue();
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void updateTasks(List<Task> oldTasks, List<Task> newTasks)
/*  82:    */   {
/*  83:189 */     boolean tasksChanged = false;
/*  84:190 */     List<Task> oldTaskQueue = copyTaskQueue();
/*  85:192 */     for (Task oldTask : oldTasks) {
/*  86:193 */       if ((!newTasks.contains(oldTask)) && 
/*  87:194 */         (this.taskQueue.remove(oldTask))) {
/*  88:195 */         tasksChanged = true;
/*  89:    */       }
/*  90:    */     }
/*  91:200 */     for (Task newTask : newTasks) {
/*  92:201 */       if (!this.taskQueue.contains(newTask))
/*  93:    */       {
/*  94:202 */         this.taskQueue.addLast(newTask);
/*  95:203 */         tasksChanged = true;
/*  96:    */       }
/*  97:    */     }
/*  98:207 */     Iterator<Task> tasks = this.taskQueue.iterator();
/*  99:208 */     while (tasks.hasNext())
/* 100:    */     {
/* 101:209 */       Task task = (Task)tasks.next();
/* 102:210 */       if (task.isDone())
/* 103:    */       {
/* 104:211 */         tasks.remove();
/* 105:212 */         tasksChanged = true;
/* 106:    */       }
/* 107:    */     }
/* 108:216 */     if (tasksChanged)
/* 109:    */     {
/* 110:217 */       List<Task> newTaskQueue = copyTaskQueue();
/* 111:218 */       firePropertyChange("tasks", oldTaskQueue, newTaskQueue);
/* 112:    */     }
/* 113:221 */     if ((this.autoUpdateForegroundTask) && (getForegroundTask() == null)) {
/* 114:222 */       setForegroundTask(this.taskQueue.isEmpty() ? null : (Task)this.taskQueue.getLast());
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private class ApplicationPCL
/* 119:    */     implements PropertyChangeListener
/* 120:    */   {
/* 121:    */     private ApplicationPCL() {}
/* 122:    */     
/* 123:    */     public void propertyChange(PropertyChangeEvent e)
/* 124:    */     {
/* 125:235 */       String propertyName = e.getPropertyName();
/* 126:236 */       if ("taskServices".equals(propertyName))
/* 127:    */       {
/* 128:237 */         List<TaskService> oldList = (List)e.getOldValue();
/* 129:238 */         List<TaskService> newList = (List)e.getNewValue();
/* 130:239 */         for (TaskService oldTaskService : oldList) {
/* 131:240 */           oldTaskService.removePropertyChangeListener(TaskMonitor.this.taskServicePCL);
/* 132:    */         }
/* 133:242 */         for (TaskService newTaskService : newList) {
/* 134:243 */           newTaskService.addPropertyChangeListener(TaskMonitor.this.taskServicePCL);
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private class TaskServicePCL
/* 141:    */     implements PropertyChangeListener
/* 142:    */   {
/* 143:    */     private TaskServicePCL() {}
/* 144:    */     
/* 145:    */     public void propertyChange(PropertyChangeEvent e)
/* 146:    */     {
/* 147:256 */       String propertyName = e.getPropertyName();
/* 148:257 */       if ("tasks".equals(propertyName))
/* 149:    */       {
/* 150:258 */         List<Task> oldList = (List)e.getOldValue();
/* 151:259 */         List<Task> newList = (List)e.getNewValue();
/* 152:260 */         TaskMonitor.this.updateTasks(oldList, newList);
/* 153:    */       }
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   private class TaskPCL
/* 158:    */     implements PropertyChangeListener
/* 159:    */   {
/* 160:    */     private TaskPCL() {}
/* 161:    */     
/* 162:    */     private void fireStateChange(Task task, String propertyName)
/* 163:    */     {
/* 164:272 */       TaskMonitor.this.firePropertyChange(new PropertyChangeEvent(task, propertyName, Boolean.valueOf(false), Boolean.valueOf(true)));
/* 165:    */     }
/* 166:    */     
/* 167:    */     public void propertyChange(PropertyChangeEvent e)
/* 168:    */     {
/* 169:275 */       String propertyName = e.getPropertyName();
/* 170:276 */       Task task = (Task)e.getSource();
/* 171:277 */       Object newValue = e.getNewValue();
/* 172:278 */       if ((task != null) && (task == TaskMonitor.this.getForegroundTask()))
/* 173:    */       {
/* 174:279 */         TaskMonitor.this.firePropertyChange(e);
/* 175:280 */         if ("state".equals(propertyName))
/* 176:    */         {
/* 177:281 */           SwingWorker.StateValue newState = (SwingWorker.StateValue)e.getNewValue();
/* 178:282 */           switch (TaskMonitor.1.$SwitchMap$org$jdesktop$swingworker$SwingWorker$StateValue[newState.ordinal()])
/* 179:    */           {
/* 180:    */           case 1: 
/* 181:283 */             fireStateChange(task, "pending"); break;
/* 182:    */           case 2: 
/* 183:284 */             fireStateChange(task, "started"); break;
/* 184:    */           case 3: 
/* 185:286 */             fireStateChange(task, "done");
/* 186:287 */             TaskMonitor.this.setForegroundTask(null);
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.TaskMonitor
 * JD-Core Version:    0.7.0.1
 */