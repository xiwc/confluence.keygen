/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.common.ExtButtonAreaLayout;
/*  4:   */ import java.awt.Container;
/*  5:   */ import javax.swing.JComponent;
/*  6:   */ import javax.swing.JPanel;
/*  7:   */ import javax.swing.UIManager;
/*  8:   */ import javax.swing.plaf.ComponentUI;
/*  9:   */ import javax.swing.plaf.basic.BasicOptionPaneUI;
/* 10:   */ 
/* 11:   */ public final class PlasticOptionPaneUI
/* 12:   */   extends BasicOptionPaneUI
/* 13:   */ {
/* 14:   */   public static ComponentUI createUI(JComponent b)
/* 15:   */   {
/* 16:55 */     return new PlasticOptionPaneUI();
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected Container createButtonArea()
/* 20:   */   {
/* 21:63 */     JPanel bottom = new JPanel(new ExtButtonAreaLayout(true, 6));
/* 22:64 */     bottom.setBorder(UIManager.getBorder("OptionPane.buttonAreaBorder"));
/* 23:65 */     addButtonComponents(bottom, getButtons(), getInitialValueIndex());
/* 24:66 */     return bottom;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticOptionPaneUI
 * JD-Core Version:    0.7.0.1
 */