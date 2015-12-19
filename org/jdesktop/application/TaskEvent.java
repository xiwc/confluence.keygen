/*  1:   */ package org.jdesktop.application;
/*  2:   */ 
/*  3:   */ import java.util.EventObject;
/*  4:   */ 
/*  5:   */ public class TaskEvent<T>
/*  6:   */   extends EventObject
/*  7:   */ {
/*  8:   */   private final T value;
/*  9:   */   
/* 10:   */   public final T getValue()
/* 11:   */   {
/* 12:23 */     return this.value;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public TaskEvent(Task source, T value)
/* 16:   */   {
/* 17:33 */     super(source);
/* 18:34 */     this.value = value;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.TaskEvent
 * JD-Core Version:    0.7.0.1
 */