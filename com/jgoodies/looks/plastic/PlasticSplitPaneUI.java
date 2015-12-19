/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.plaf.ComponentUI;
/*  5:   */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*  6:   */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*  7:   */ 
/*  8:   */ public final class PlasticSplitPaneUI
/*  9:   */   extends BasicSplitPaneUI
/* 10:   */ {
/* 11:   */   public static ComponentUI createUI(JComponent x)
/* 12:   */   {
/* 13:52 */     return new PlasticSplitPaneUI();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public BasicSplitPaneDivider createDefaultDivider()
/* 17:   */   {
/* 18:60 */     return new PlasticSplitPaneDivider(this);
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticSplitPaneUI
 * JD-Core Version:    0.7.0.1
 */