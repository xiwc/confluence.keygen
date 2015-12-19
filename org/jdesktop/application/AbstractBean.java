/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.beans.PropertyChangeEvent;
/*   4:    */ import java.beans.PropertyChangeListener;
/*   5:    */ import java.beans.PropertyChangeSupport;
/*   6:    */ import javax.swing.SwingUtilities;
/*   7:    */ 
/*   8:    */ public class AbstractBean
/*   9:    */ {
/*  10:    */   private final PropertyChangeSupport pcs;
/*  11:    */   
/*  12:    */   public AbstractBean()
/*  13:    */   {
/*  14: 26 */     this.pcs = new EDTPropertyChangeSupport(this);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void addPropertyChangeListener(PropertyChangeListener listener)
/*  18:    */   {
/*  19: 43 */     this.pcs.addPropertyChangeListener(listener);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void removePropertyChangeListener(PropertyChangeListener listener)
/*  23:    */   {
/*  24: 57 */     this.pcs.removePropertyChangeListener(listener);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
/*  28:    */   {
/*  29: 75 */     this.pcs.addPropertyChangeListener(propertyName, listener);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
/*  33:    */   {
/*  34: 93 */     this.pcs.removePropertyChangeListener(propertyName, listener);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PropertyChangeListener[] getPropertyChangeListeners()
/*  38:    */   {
/*  39:103 */     return this.pcs.getPropertyChangeListeners();
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
/*  43:    */   {
/*  44:119 */     if ((oldValue != null) && (newValue != null) && (oldValue.equals(newValue))) {
/*  45:120 */       return;
/*  46:    */     }
/*  47:122 */     this.pcs.firePropertyChange(propertyName, oldValue, newValue);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void firePropertyChange(PropertyChangeEvent e)
/*  51:    */   {
/*  52:138 */     this.pcs.firePropertyChange(e);
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static class EDTPropertyChangeSupport
/*  56:    */     extends PropertyChangeSupport
/*  57:    */   {
/*  58:    */     EDTPropertyChangeSupport(Object source)
/*  59:    */     {
/*  60:143 */       super();
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void firePropertyChange(final PropertyChangeEvent e)
/*  64:    */     {
/*  65:146 */       if (SwingUtilities.isEventDispatchThread())
/*  66:    */       {
/*  67:147 */         super.firePropertyChange(e);
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71:150 */         Runnable doFirePropertyChange = new Runnable()
/*  72:    */         {
/*  73:    */           public void run()
/*  74:    */           {
/*  75:152 */             AbstractBean.EDTPropertyChangeSupport.this.firePropertyChange(e);
/*  76:    */           }
/*  77:154 */         };
/*  78:155 */         SwingUtilities.invokeLater(doFirePropertyChange);
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.AbstractBean
 * JD-Core Version:    0.7.0.1
 */