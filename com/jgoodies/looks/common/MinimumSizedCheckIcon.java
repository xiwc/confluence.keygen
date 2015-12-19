/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Graphics;
/*  6:   */ import javax.swing.ButtonModel;
/*  7:   */ import javax.swing.Icon;
/*  8:   */ import javax.swing.JMenuItem;
/*  9:   */ import javax.swing.UIManager;
/* 10:   */ 
/* 11:   */ public final class MinimumSizedCheckIcon
/* 12:   */   extends MinimumSizedIcon
/* 13:   */ {
/* 14:   */   private final JMenuItem menuItem;
/* 15:   */   
/* 16:   */   public MinimumSizedCheckIcon(Icon icon, JMenuItem menuItem)
/* 17:   */   {
/* 18:58 */     super(icon);
/* 19:59 */     this.menuItem = menuItem;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void paintIcon(Component c, Graphics g, int x, int y)
/* 23:   */   {
/* 24:64 */     paintState(g, x, y);
/* 25:65 */     super.paintIcon(c, g, x, y);
/* 26:   */   }
/* 27:   */   
/* 28:   */   private void paintState(Graphics g, int x, int y)
/* 29:   */   {
/* 30:69 */     ButtonModel model = this.menuItem.getModel();
/* 31:   */     
/* 32:   */ 
/* 33:72 */     int w = getIconWidth();
/* 34:73 */     int h = getIconHeight();
/* 35:   */     
/* 36:75 */     g.translate(x, y);
/* 37:76 */     if ((model.isSelected()) || (model.isArmed()))
/* 38:   */     {
/* 39:77 */       Color background = model.isArmed() ? UIManager.getColor("MenuItem.background") : UIManager.getColor("ScrollBar.track");
/* 40:   */       
/* 41:   */ 
/* 42:80 */       Color upColor = UIManager.getColor("controlLtHighlight");
/* 43:81 */       Color downColor = UIManager.getColor("controlDkShadow");
/* 44:   */       
/* 45:   */ 
/* 46:84 */       g.setColor(background);
/* 47:85 */       g.fillRect(0, 0, w, h);
/* 48:   */       
/* 49:87 */       g.setColor(model.isSelected() ? downColor : upColor);
/* 50:88 */       g.drawLine(0, 0, w - 2, 0);
/* 51:89 */       g.drawLine(0, 0, 0, h - 2);
/* 52:   */       
/* 53:91 */       g.setColor(model.isSelected() ? upColor : downColor);
/* 54:92 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 55:93 */       g.drawLine(w - 1, 0, w - 1, h - 1);
/* 56:   */     }
/* 57:95 */     g.translate(-x, -y);
/* 58:96 */     g.setColor(UIManager.getColor("textText"));
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.MinimumSizedCheckIcon
 * JD-Core Version:    0.7.0.1
 */