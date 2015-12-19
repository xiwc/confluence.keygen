/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneUI;
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
/*    */ public final class PlasticSplitPaneUI
/*    */   extends BasicSplitPaneUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent x)
/*    */   {
/* 52 */     return new PlasticSplitPaneUI();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public BasicSplitPaneDivider createDefaultDivider()
/*    */   {
/* 60 */     return new PlasticSplitPaneDivider(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticSplitPaneUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */