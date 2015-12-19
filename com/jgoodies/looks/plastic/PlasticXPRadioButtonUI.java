/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicButtonListener;
/*    */ import javax.swing.plaf.metal.MetalRadioButtonUI;
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
/*    */ public final class PlasticXPRadioButtonUI
/*    */   extends MetalRadioButtonUI
/*    */ {
/* 49 */   private static final PlasticXPRadioButtonUI INSTANCE = new PlasticXPRadioButtonUI();
/*    */   
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 53 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   protected BasicButtonListener createButtonListener(AbstractButton b) {
/* 57 */     return new ActiveBasicButtonListener(b);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPRadioButtonUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */