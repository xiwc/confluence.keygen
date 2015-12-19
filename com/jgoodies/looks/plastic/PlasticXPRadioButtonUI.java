/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.AbstractButton;
/*  4:   */ import javax.swing.JComponent;
/*  5:   */ import javax.swing.plaf.ComponentUI;
/*  6:   */ import javax.swing.plaf.basic.BasicButtonListener;
/*  7:   */ import javax.swing.plaf.metal.MetalRadioButtonUI;
/*  8:   */ 
/*  9:   */ public final class PlasticXPRadioButtonUI
/* 10:   */   extends MetalRadioButtonUI
/* 11:   */ {
/* 12:49 */   private static final PlasticXPRadioButtonUI INSTANCE = new PlasticXPRadioButtonUI();
/* 13:   */   
/* 14:   */   public static ComponentUI createUI(JComponent b)
/* 15:   */   {
/* 16:53 */     return INSTANCE;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected BasicButtonListener createButtonListener(AbstractButton b)
/* 20:   */   {
/* 21:57 */     return new ActiveBasicButtonListener(b);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPRadioButtonUI
 * JD-Core Version:    0.7.0.1
 */