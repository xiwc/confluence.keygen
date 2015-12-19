/*  1:   */ package org.jdesktop.swingworker;
/*  2:   */ 
/*  3:   */ import java.beans.PropertyChangeEvent;
/*  4:   */ import java.beans.PropertyChangeSupport;
/*  5:   */ import javax.swing.SwingUtilities;
/*  6:   */ 
/*  7:   */ public final class SwingPropertyChangeSupport
/*  8:   */   extends PropertyChangeSupport
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = 7162625831330845068L;
/* 11:   */   private final boolean notifyOnEDT;
/* 12:   */   
/* 13:   */   public SwingPropertyChangeSupport(Object sourceBean)
/* 14:   */   {
/* 15:36 */     this(sourceBean, false);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT)
/* 19:   */   {
/* 20:51 */     super(sourceBean);
/* 21:52 */     this.notifyOnEDT = notifyOnEDT;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void firePropertyChange(final PropertyChangeEvent evt)
/* 25:   */   {
/* 26:70 */     if (evt == null) {
/* 27:71 */       throw new NullPointerException();
/* 28:   */     }
/* 29:73 */     if ((!isNotifyOnEDT()) || (SwingUtilities.isEventDispatchThread())) {
/* 30:75 */       super.firePropertyChange(evt);
/* 31:   */     } else {
/* 32:77 */       SwingUtilities.invokeLater(new Runnable()
/* 33:   */       {
/* 34:   */         public void run()
/* 35:   */         {
/* 36:80 */           SwingPropertyChangeSupport.this.firePropertyChange(evt);
/* 37:   */         }
/* 38:   */       });
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public final boolean isNotifyOnEDT()
/* 43:   */   {
/* 44:94 */     return this.notifyOnEDT;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.swingworker.SwingPropertyChangeSupport
 * JD-Core Version:    0.7.0.1
 */