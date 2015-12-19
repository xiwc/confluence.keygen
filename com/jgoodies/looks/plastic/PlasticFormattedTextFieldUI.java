/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.plaf.ComponentUI;
/*  5:   */ import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
/*  6:   */ import javax.swing.text.Caret;
/*  7:   */ 
/*  8:   */ public final class PlasticFormattedTextFieldUI
/*  9:   */   extends BasicFormattedTextFieldUI
/* 10:   */ {
/* 11:   */   public static ComponentUI createUI(JComponent c)
/* 12:   */   {
/* 13:57 */     return new PlasticFormattedTextFieldUI();
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected Caret createCaret()
/* 17:   */   {
/* 18:67 */     return new PlasticFieldCaret();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticFormattedTextFieldUI
 * JD-Core Version:    0.7.0.1
 */