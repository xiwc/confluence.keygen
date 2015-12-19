/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.border.Border;
/*  5:   */ import javax.swing.plaf.ComponentUI;
/*  6:   */ 
/*  7:   */ public final class PlasticXPToolBarUI
/*  8:   */   extends PlasticToolBarUI
/*  9:   */ {
/* 10:   */   public static ComponentUI createUI(JComponent b)
/* 11:   */   {
/* 12:48 */     return new PlasticXPToolBarUI();
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected Border createRolloverBorder()
/* 16:   */   {
/* 17:55 */     return PlasticXPBorders.getRolloverButtonBorder();
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPToolBarUI
 * JD-Core Version:    0.7.0.1
 */