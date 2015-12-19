/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.LookAndFeel;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.metal.MetalScrollPaneUI;
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
/*    */ 
/*    */ public final class PlasticScrollPaneUI
/*    */   extends MetalScrollPaneUI
/*    */ {
/*    */   private PropertyChangeListener borderStyleChangeHandler;
/*    */   
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 62 */     return new PlasticScrollPaneUI();
/*    */   }
/*    */   
/*    */   protected void installDefaults(JScrollPane scrollPane) {
/* 66 */     super.installDefaults(scrollPane);
/* 67 */     installEtchedBorder(scrollPane);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void installListeners(JScrollPane scrollPane)
/*    */   {
/* 74 */     super.installListeners(scrollPane);
/* 75 */     this.borderStyleChangeHandler = new BorderStyleChangeHandler(null);
/* 76 */     scrollPane.addPropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
/*    */   }
/*    */   
/*    */   protected void uninstallListeners(JComponent c) {
/* 80 */     ((JScrollPane)c).removePropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
/*    */     
/* 82 */     super.uninstallListeners(c);
/*    */   }
/*    */   
/*    */   protected void installEtchedBorder(JScrollPane scrollPane) {
/* 86 */     Object value = scrollPane.getClientProperty("jgoodies.isEtched");
/* 87 */     boolean hasEtchedBorder = Boolean.TRUE.equals(value);
/* 88 */     LookAndFeel.installBorder(scrollPane, hasEtchedBorder ? "ScrollPane.etchedBorder" : "ScrollPane.border");
/*    */   }
/*    */   
/*    */   private class BorderStyleChangeHandler implements PropertyChangeListener
/*    */   {
/*    */     private BorderStyleChangeHandler() {}
/*    */     
/*    */     public void propertyChange(PropertyChangeEvent evt)
/*    */     {
/* 97 */       JScrollPane scrollPane = (JScrollPane)evt.getSource();
/* 98 */       PlasticScrollPaneUI.this.installEtchedBorder(scrollPane);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticScrollPaneUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */