/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExtBasicPopupMenuSeparatorUI
/*     */   extends BasicPopupMenuSeparatorUI
/*     */ {
/*     */   private static final int SEPARATOR_HEIGHT = 2;
/*     */   private Insets insets;
/*     */   private static ComponentUI popupMenuSeparatorUI;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  61 */     if (popupMenuSeparatorUI == null) {
/*  62 */       popupMenuSeparatorUI = new ExtBasicPopupMenuSeparatorUI();
/*     */     }
/*  64 */     return popupMenuSeparatorUI;
/*     */   }
/*     */   
/*     */   protected void installDefaults(JSeparator s)
/*     */   {
/*  69 */     super.installDefaults(s);
/*  70 */     this.insets = UIManager.getInsets("PopupMenuSeparator.margin");
/*     */   }
/*     */   
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/*  75 */     Dimension s = c.getSize();
/*     */     
/*  77 */     int topInset = this.insets.top;
/*  78 */     int leftInset = this.insets.left;
/*  79 */     int rightInset = this.insets.right;
/*     */     
/*     */ 
/*  82 */     g.setColor(UIManager.getColor("MenuItem.background"));
/*  83 */     g.fillRect(0, 0, s.width, s.height);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  92 */     g.translate(0, topInset);
/*  93 */     g.setColor(c.getForeground());
/*  94 */     g.drawLine(leftInset, 0, s.width - rightInset, 0);
/*     */     
/*  96 */     g.setColor(c.getBackground());
/*  97 */     g.drawLine(leftInset, 1, s.width - rightInset, 1);
/*  98 */     g.translate(0, -topInset);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 103 */     return new Dimension(0, this.insets.top + 2 + this.insets.bottom);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicPopupMenuSeparatorUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */