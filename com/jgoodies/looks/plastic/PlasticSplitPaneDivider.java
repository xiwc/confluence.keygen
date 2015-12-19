/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Graphics;
/*  5:   */ import javax.swing.JButton;
/*  6:   */ import javax.swing.JSplitPane;
/*  7:   */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*  8:   */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*  9:   */ 
/* 10:   */ final class PlasticSplitPaneDivider
/* 11:   */   extends BasicSplitPaneDivider
/* 12:   */ {
/* 13:   */   PlasticSplitPaneDivider(BasicSplitPaneUI ui)
/* 14:   */   {
/* 15:53 */     super(ui);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected JButton createLeftOneTouchButton()
/* 19:   */   {
/* 20:57 */     JButton btn = super.createLeftOneTouchButton();
/* 21:58 */     btn.setOpaque(false);
/* 22:59 */     return btn;
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected JButton createRightOneTouchButton()
/* 26:   */   {
/* 27:63 */     JButton btn = super.createRightOneTouchButton();
/* 28:64 */     btn.setOpaque(false);
/* 29:65 */     return btn;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void paint(Graphics g)
/* 33:   */   {
/* 34:69 */     if (this.splitPane.isOpaque())
/* 35:   */     {
/* 36:70 */       Color bgColor = getBackground();
/* 37:71 */       if (bgColor != null)
/* 38:   */       {
/* 39:72 */         g.setColor(bgColor);
/* 40:73 */         g.fillRect(0, 0, getWidth(), getHeight());
/* 41:   */       }
/* 42:   */     }
/* 43:86 */     super.paint(g);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticSplitPaneDivider
 * JD-Core Version:    0.7.0.1
 */