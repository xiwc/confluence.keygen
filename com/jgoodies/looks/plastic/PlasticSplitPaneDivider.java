/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JSplitPane;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*    */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PlasticSplitPaneDivider
/*    */   extends BasicSplitPaneDivider
/*    */ {
/*    */   PlasticSplitPaneDivider(BasicSplitPaneUI ui)
/*    */   {
/* 53 */     super(ui);
/*    */   }
/*    */   
/*    */   protected JButton createLeftOneTouchButton() {
/* 57 */     JButton btn = super.createLeftOneTouchButton();
/* 58 */     btn.setOpaque(false);
/* 59 */     return btn;
/*    */   }
/*    */   
/*    */   protected JButton createRightOneTouchButton() {
/* 63 */     JButton btn = super.createRightOneTouchButton();
/* 64 */     btn.setOpaque(false);
/* 65 */     return btn;
/*    */   }
/*    */   
/*    */   public void paint(Graphics g) {
/* 69 */     if (this.splitPane.isOpaque()) {
/* 70 */       Color bgColor = getBackground();
/* 71 */       if (bgColor != null) {
/* 72 */         g.setColor(bgColor);
/* 73 */         g.fillRect(0, 0, getWidth(), getHeight());
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 86 */     super.paint(g);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticSplitPaneDivider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */