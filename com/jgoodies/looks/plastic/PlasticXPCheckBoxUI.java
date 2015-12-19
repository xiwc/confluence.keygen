/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicButtonListener;
/*    */ import javax.swing.plaf.metal.MetalCheckBoxUI;
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
/*    */ public final class PlasticXPCheckBoxUI
/*    */   extends MetalCheckBoxUI
/*    */ {
/* 49 */   private static final PlasticXPCheckBoxUI INSTANCE = new PlasticXPCheckBoxUI();
/*    */   
/*    */   public static ComponentUI createUI(JComponent b) {
/* 52 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   protected BasicButtonListener createButtonListener(AbstractButton b) {
/* 56 */     return new ActiveBasicButtonListener(b);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPCheckBoxUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */