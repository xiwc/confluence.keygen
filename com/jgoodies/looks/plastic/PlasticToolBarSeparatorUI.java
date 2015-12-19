/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicToolBarSeparatorUI;
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
/*    */ public final class PlasticToolBarSeparatorUI
/*    */   extends BasicToolBarSeparatorUI
/*    */ {
/*    */   private static ComponentUI toolBarSeparatorUI;
/*    */   
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 50 */     if (toolBarSeparatorUI == null) {
/* 51 */       toolBarSeparatorUI = new PlasticToolBarSeparatorUI();
/*    */     }
/* 53 */     return toolBarSeparatorUI;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticToolBarSeparatorUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */