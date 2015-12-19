/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.plaf.ComponentUI;
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
/*    */ public final class PlasticXPToolBarUI
/*    */   extends PlasticToolBarUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 48 */     return new PlasticXPToolBarUI();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected Border createRolloverBorder()
/*    */   {
/* 55 */     return PlasticXPBorders.getRolloverButtonBorder();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPToolBarUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */