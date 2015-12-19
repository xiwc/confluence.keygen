/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import javax.swing.JComponent;
/*   7:    */ import javax.swing.JSeparator;
/*   8:    */ import javax.swing.UIManager;
/*   9:    */ import javax.swing.plaf.ComponentUI;
/*  10:    */ import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
/*  11:    */ 
/*  12:    */ public final class ExtBasicPopupMenuSeparatorUI
/*  13:    */   extends BasicPopupMenuSeparatorUI
/*  14:    */ {
/*  15:    */   private static final int SEPARATOR_HEIGHT = 2;
/*  16:    */   private Insets insets;
/*  17:    */   private static ComponentUI popupMenuSeparatorUI;
/*  18:    */   
/*  19:    */   public static ComponentUI createUI(JComponent b)
/*  20:    */   {
/*  21: 61 */     if (popupMenuSeparatorUI == null) {
/*  22: 62 */       popupMenuSeparatorUI = new ExtBasicPopupMenuSeparatorUI();
/*  23:    */     }
/*  24: 64 */     return popupMenuSeparatorUI;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void installDefaults(JSeparator s)
/*  28:    */   {
/*  29: 69 */     super.installDefaults(s);
/*  30: 70 */     this.insets = UIManager.getInsets("PopupMenuSeparator.margin");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void paint(Graphics g, JComponent c)
/*  34:    */   {
/*  35: 75 */     Dimension s = c.getSize();
/*  36:    */     
/*  37: 77 */     int topInset = this.insets.top;
/*  38: 78 */     int leftInset = this.insets.left;
/*  39: 79 */     int rightInset = this.insets.right;
/*  40:    */     
/*  41:    */ 
/*  42: 82 */     g.setColor(UIManager.getColor("MenuItem.background"));
/*  43: 83 */     g.fillRect(0, 0, s.width, s.height);
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 92 */     g.translate(0, topInset);
/*  53: 93 */     g.setColor(c.getForeground());
/*  54: 94 */     g.drawLine(leftInset, 0, s.width - rightInset, 0);
/*  55:    */     
/*  56: 96 */     g.setColor(c.getBackground());
/*  57: 97 */     g.drawLine(leftInset, 1, s.width - rightInset, 1);
/*  58: 98 */     g.translate(0, -topInset);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Dimension getPreferredSize(JComponent c)
/*  62:    */   {
/*  63:103 */     return new Dimension(0, this.insets.top + 2 + this.insets.bottom);
/*  64:    */   }
/*  65:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicPopupMenuSeparatorUI
 * JD-Core Version:    0.7.0.1
 */