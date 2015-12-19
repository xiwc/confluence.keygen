/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractBean
/*     */ {
/*     */   private final PropertyChangeSupport pcs;
/*     */   
/*     */   public AbstractBean()
/*     */   {
/*  26 */     this.pcs = new EDTPropertyChangeSupport(this);
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
/*     */   public void addPropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/*  43 */     this.pcs.addPropertyChangeListener(listener);
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
/*     */   public void removePropertyChangeListener(PropertyChangeListener listener)
/*     */   {
/*  57 */     this.pcs.removePropertyChangeListener(listener);
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
/*     */   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
/*     */   {
/*  75 */     this.pcs.addPropertyChangeListener(propertyName, listener);
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
/*     */   public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
/*     */   {
/*  93 */     this.pcs.removePropertyChangeListener(propertyName, listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyChangeListener[] getPropertyChangeListeners()
/*     */   {
/* 103 */     return this.pcs.getPropertyChangeListeners();
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
/*     */   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*     */   {
/* 119 */     if ((oldValue != null) && (newValue != null) && (oldValue.equals(newValue))) {
/* 120 */       return;
/*     */     }
/* 122 */     this.pcs.firePropertyChange(propertyName, oldValue, newValue);
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
/*     */   protected void firePropertyChange(PropertyChangeEvent e)
/*     */   {
/* 138 */     this.pcs.firePropertyChange(e);
/*     */   }
/*     */   
/*     */   private static class EDTPropertyChangeSupport
/*     */     extends PropertyChangeSupport {
/* 143 */     EDTPropertyChangeSupport(Object source) { super(); }
/*     */     
/*     */     public void firePropertyChange(final PropertyChangeEvent e) {
/* 146 */       if (SwingUtilities.isEventDispatchThread()) {
/* 147 */         super.firePropertyChange(e);
/*     */       }
/*     */       else {
/* 150 */         Runnable doFirePropertyChange = new Runnable() {
/*     */           public void run() {
/* 152 */             AbstractBean.EDTPropertyChangeSupport.this.firePropertyChange(e);
/*     */           }
/* 154 */         };
/* 155 */         SwingUtilities.invokeLater(doFirePropertyChange);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\AbstractBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */