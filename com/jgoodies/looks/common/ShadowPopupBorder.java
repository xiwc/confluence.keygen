/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.border.AbstractBorder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ShadowPopupBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   private static final int SHADOW_SIZE = 5;
/*  64 */   private static ShadowPopupBorder instance = new ShadowPopupBorder();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  69 */   private static Image shadow = new ImageIcon(ShadowPopupBorder.class.getResource("shadow.png")).getImage();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ShadowPopupBorder getInstance()
/*     */   {
/*  79 */     return instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*     */   {
/*  89 */     JComponent popup = (JComponent)c;
/*  90 */     Image hShadowBg = (Image)popup.getClientProperty("jgoodies.hShadowBg");
/*  91 */     if (hShadowBg != null) {
/*  92 */       g.drawImage(hShadowBg, x, y + height - 5, c);
/*     */     }
/*  94 */     Image vShadowBg = (Image)popup.getClientProperty("jgoodies.vShadowBg");
/*  95 */     if (vShadowBg != null) {
/*  96 */       g.drawImage(vShadowBg, x + width - 5, y, c);
/*     */     }
/*     */     
/*     */ 
/* 100 */     g.drawImage(shadow, x + 5, y + height - 5, x + 10, y + height, 0, 6, 5, 11, null, c);
/* 101 */     g.drawImage(shadow, x + 10, y + height - 5, x + width - 5, y + height, 5, 6, 6, 11, null, c);
/* 102 */     g.drawImage(shadow, x + width - 5, y + 5, x + width, y + 10, 6, 0, 11, 5, null, c);
/* 103 */     g.drawImage(shadow, x + width - 5, y + 10, x + width, y + height - 5, 6, 5, 11, 6, null, c);
/* 104 */     g.drawImage(shadow, x + width - 5, y + height - 5, x + width, y + height, 6, 6, 11, 11, null, c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Insets getBorderInsets(Component c)
/*     */   {
/* 112 */     return new Insets(0, 0, 5, 5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Insets getBorderInsets(Component c, Insets insets)
/*     */   {
/* 123 */     insets.left = (insets.top = 0);
/* 124 */     insets.right = (insets.bottom = 5);
/* 125 */     return insets;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ShadowPopupBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */