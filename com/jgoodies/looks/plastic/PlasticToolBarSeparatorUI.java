/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.plaf.ComponentUI;
/*  5:   */ import javax.swing.plaf.basic.BasicToolBarSeparatorUI;
/*  6:   */ 
/*  7:   */ public final class PlasticToolBarSeparatorUI
/*  8:   */   extends BasicToolBarSeparatorUI
/*  9:   */ {
/* 10:   */   private static ComponentUI toolBarSeparatorUI;
/* 11:   */   
/* 12:   */   public static ComponentUI createUI(JComponent c)
/* 13:   */   {
/* 14:50 */     if (toolBarSeparatorUI == null) {
/* 15:51 */       toolBarSeparatorUI = new PlasticToolBarSeparatorUI();
/* 16:   */     }
/* 17:53 */     return toolBarSeparatorUI;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticToolBarSeparatorUI
 * JD-Core Version:    0.7.0.1
 */