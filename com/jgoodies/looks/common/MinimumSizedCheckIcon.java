/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.ButtonModel;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.UIManager;
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
/*    */ 
/*    */ 
/*    */ public final class MinimumSizedCheckIcon
/*    */   extends MinimumSizedIcon
/*    */ {
/*    */   private final JMenuItem menuItem;
/*    */   
/*    */   public MinimumSizedCheckIcon(Icon icon, JMenuItem menuItem)
/*    */   {
/* 58 */     super(icon);
/* 59 */     this.menuItem = menuItem;
/*    */   }
/*    */   
/*    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*    */   {
/* 64 */     paintState(g, x, y);
/* 65 */     super.paintIcon(c, g, x, y);
/*    */   }
/*    */   
/*    */   private void paintState(Graphics g, int x, int y) {
/* 69 */     ButtonModel model = this.menuItem.getModel();
/*    */     
/*    */ 
/* 72 */     int w = getIconWidth();
/* 73 */     int h = getIconHeight();
/*    */     
/* 75 */     g.translate(x, y);
/* 76 */     if ((model.isSelected()) || (model.isArmed())) {
/* 77 */       Color background = model.isArmed() ? UIManager.getColor("MenuItem.background") : UIManager.getColor("ScrollBar.track");
/*    */       
/*    */ 
/* 80 */       Color upColor = UIManager.getColor("controlLtHighlight");
/* 81 */       Color downColor = UIManager.getColor("controlDkShadow");
/*    */       
/*    */ 
/* 84 */       g.setColor(background);
/* 85 */       g.fillRect(0, 0, w, h);
/*    */       
/* 87 */       g.setColor(model.isSelected() ? downColor : upColor);
/* 88 */       g.drawLine(0, 0, w - 2, 0);
/* 89 */       g.drawLine(0, 0, 0, h - 2);
/*    */       
/* 91 */       g.setColor(model.isSelected() ? upColor : downColor);
/* 92 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 93 */       g.drawLine(w - 1, 0, w - 1, h - 1);
/*    */     }
/* 95 */     g.translate(-x, -y);
/* 96 */     g.setColor(UIManager.getColor("textText"));
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\MinimumSizedCheckIcon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */