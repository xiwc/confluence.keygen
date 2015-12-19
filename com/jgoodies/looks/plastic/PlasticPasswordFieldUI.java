/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.LookUtils;
/*  4:   */ import com.jgoodies.looks.common.ExtPasswordView;
/*  5:   */ import javax.swing.JComponent;
/*  6:   */ import javax.swing.plaf.ComponentUI;
/*  7:   */ import javax.swing.plaf.basic.BasicPasswordFieldUI;
/*  8:   */ import javax.swing.text.Caret;
/*  9:   */ import javax.swing.text.Element;
/* 10:   */ import javax.swing.text.View;
/* 11:   */ 
/* 12:   */ public final class PlasticPasswordFieldUI
/* 13:   */   extends BasicPasswordFieldUI
/* 14:   */ {
/* 15:   */   public static ComponentUI createUI(JComponent c)
/* 16:   */   {
/* 17:64 */     return new PlasticPasswordFieldUI();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public View create(Element elem)
/* 21:   */   {
/* 22:74 */     return LookUtils.IS_JAVA_1_4_OR_5 ? new ExtPasswordView(elem) : super.create(elem);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected Caret createCaret()
/* 26:   */   {
/* 27:86 */     return PlasticLookAndFeel.isSelectTextOnKeyboardFocusGained() ? new PlasticFieldCaret() : super.createCaret();
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticPasswordFieldUI
 * JD-Core Version:    0.7.0.1
 */