/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.AbstractButton;
/*  4:   */ import javax.swing.JComponent;
/*  5:   */ import javax.swing.plaf.ComponentUI;
/*  6:   */ import javax.swing.plaf.basic.BasicButtonListener;
/*  7:   */ import javax.swing.plaf.metal.MetalCheckBoxUI;
/*  8:   */ 
/*  9:   */ public final class PlasticXPCheckBoxUI
/* 10:   */   extends MetalCheckBoxUI
/* 11:   */ {
/* 12:49 */   private static final PlasticXPCheckBoxUI INSTANCE = new PlasticXPCheckBoxUI();
/* 13:   */   
/* 14:   */   public static ComponentUI createUI(JComponent b)
/* 15:   */   {
/* 16:52 */     return INSTANCE;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected BasicButtonListener createButtonListener(AbstractButton b)
/* 20:   */   {
/* 21:56 */     return new ActiveBasicButtonListener(b);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPCheckBoxUI
 * JD-Core Version:    0.7.0.1
 */