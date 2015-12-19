/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import javax.swing.JComponent;
/*  4:   */ import javax.swing.plaf.ComponentUI;
/*  5:   */ 
/*  6:   */ public final class ExtBasicCheckBoxMenuItemUI
/*  7:   */   extends ExtBasicRadioButtonMenuItemUI
/*  8:   */ {
/*  9:   */   protected String getPropertyPrefix()
/* 10:   */   {
/* 11:45 */     return "CheckBoxMenuItem";
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static ComponentUI createUI(JComponent b)
/* 15:   */   {
/* 16:49 */     return new ExtBasicCheckBoxMenuItemUI();
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicCheckBoxMenuItemUI
 * JD-Core Version:    0.7.0.1
 */