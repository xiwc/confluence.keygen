/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.plaf.ComponentUI;
/*  5:   */ import javax.swing.plaf.metal.MetalSeparatorUI;
/*  6:   */ 
/*  7:   */ public final class PlasticSeparatorUI
/*  8:   */   extends MetalSeparatorUI
/*  9:   */ {
/* 10:   */   private static ComponentUI separatorUI;
/* 11:   */   
/* 12:   */   public static ComponentUI createUI(JComponent c)
/* 13:   */   {
/* 14:51 */     if (separatorUI == null) {
/* 15:52 */       separatorUI = new PlasticSeparatorUI();
/* 16:   */     }
/* 17:54 */     return separatorUI;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticSeparatorUI
 * JD-Core Version:    0.7.0.1
 */