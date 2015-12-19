/*    */ package org.jdesktop.swingworker;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeSupport;
/*    */ import javax.swing.SwingUtilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SwingPropertyChangeSupport
/*    */   extends PropertyChangeSupport
/*    */ {
/*    */   static final long serialVersionUID = 7162625831330845068L;
/*    */   private final boolean notifyOnEDT;
/*    */   
/*    */   public SwingPropertyChangeSupport(Object sourceBean)
/*    */   {
/* 36 */     this(sourceBean, false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT)
/*    */   {
/* 51 */     super(sourceBean);
/* 52 */     this.notifyOnEDT = notifyOnEDT;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void firePropertyChange(final PropertyChangeEvent evt)
/*    */   {
/* 70 */     if (evt == null) {
/* 71 */       throw new NullPointerException();
/*    */     }
/* 73 */     if ((!isNotifyOnEDT()) || (SwingUtilities.isEventDispatchThread()))
/*    */     {
/* 75 */       super.firePropertyChange(evt);
/*    */     } else {
/* 77 */       SwingUtilities.invokeLater(new Runnable()
/*    */       {
/*    */         public void run() {
/* 80 */           SwingPropertyChangeSupport.this.firePropertyChange(evt);
/*    */         }
/*    */       });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final boolean isNotifyOnEDT()
/*    */   {
/* 94 */     return this.notifyOnEDT;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\swingworker\SwingPropertyChangeSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */