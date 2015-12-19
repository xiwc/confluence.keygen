/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.metal.MetalSeparatorUI;
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
/*    */ public final class PlasticSeparatorUI
/*    */   extends MetalSeparatorUI
/*    */ {
/*    */   private static ComponentUI separatorUI;
/*    */   
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 51 */     if (separatorUI == null) {
/* 52 */       separatorUI = new PlasticSeparatorUI();
/*    */     }
/* 54 */     return separatorUI;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticSeparatorUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */