/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
/*    */ import javax.swing.text.Caret;
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
/*    */ public final class PlasticFormattedTextFieldUI
/*    */   extends BasicFormattedTextFieldUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 57 */     return new PlasticFormattedTextFieldUI();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Caret createCaret()
/*    */   {
/* 67 */     return new PlasticFieldCaret();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticFormattedTextFieldUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */