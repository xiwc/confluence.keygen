/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Image;
/*   6:    */ import java.awt.Insets;
/*   7:    */ import javax.swing.ImageIcon;
/*   8:    */ import javax.swing.JComponent;
/*   9:    */ import javax.swing.border.AbstractBorder;
/*  10:    */ 
/*  11:    */ final class ShadowPopupBorder
/*  12:    */   extends AbstractBorder
/*  13:    */ {
/*  14:    */   private static final int SHADOW_SIZE = 5;
/*  15: 64 */   private static ShadowPopupBorder instance = new ShadowPopupBorder();
/*  16: 69 */   private static Image shadow = new ImageIcon(ShadowPopupBorder.class.getResource("shadow.png")).getImage();
/*  17:    */   
/*  18:    */   public static ShadowPopupBorder getInstance()
/*  19:    */   {
/*  20: 79 */     return instance;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
/*  24:    */   {
/*  25: 89 */     JComponent popup = (JComponent)c;
/*  26: 90 */     Image hShadowBg = (Image)popup.getClientProperty("jgoodies.hShadowBg");
/*  27: 91 */     if (hShadowBg != null) {
/*  28: 92 */       g.drawImage(hShadowBg, x, y + height - 5, c);
/*  29:    */     }
/*  30: 94 */     Image vShadowBg = (Image)popup.getClientProperty("jgoodies.vShadowBg");
/*  31: 95 */     if (vShadowBg != null) {
/*  32: 96 */       g.drawImage(vShadowBg, x + width - 5, y, c);
/*  33:    */     }
/*  34:100 */     g.drawImage(shadow, x + 5, y + height - 5, x + 10, y + height, 0, 6, 5, 11, null, c);
/*  35:101 */     g.drawImage(shadow, x + 10, y + height - 5, x + width - 5, y + height, 5, 6, 6, 11, null, c);
/*  36:102 */     g.drawImage(shadow, x + width - 5, y + 5, x + width, y + 10, 6, 0, 11, 5, null, c);
/*  37:103 */     g.drawImage(shadow, x + width - 5, y + 10, x + width, y + height - 5, 6, 5, 11, 6, null, c);
/*  38:104 */     g.drawImage(shadow, x + width - 5, y + height - 5, x + width, y + height, 6, 6, 11, 11, null, c);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Insets getBorderInsets(Component c)
/*  42:    */   {
/*  43:112 */     return new Insets(0, 0, 5, 5);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Insets getBorderInsets(Component c, Insets insets)
/*  47:    */   {
/*  48:123 */     insets.left = (insets.top = 0);
/*  49:124 */     insets.right = (insets.bottom = 5);
/*  50:125 */     return insets;
/*  51:    */   }
/*  52:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ShadowPopupBorder
 * JD-Core Version:    0.7.0.1
 */