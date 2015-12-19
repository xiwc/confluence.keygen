/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.Options;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Dimension;
/*  6:   */ import java.awt.Graphics;
/*  7:   */ import javax.swing.Icon;
/*  8:   */ 
/*  9:   */ public class MinimumSizedIcon
/* 10:   */   implements Icon
/* 11:   */ {
/* 12:   */   private final Icon icon;
/* 13:   */   private final int width;
/* 14:   */   private final int height;
/* 15:   */   private final int xOffset;
/* 16:   */   private final int yOffset;
/* 17:   */   
/* 18:   */   public MinimumSizedIcon()
/* 19:   */   {
/* 20:59 */     this(null);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MinimumSizedIcon(Icon icon)
/* 24:   */   {
/* 25:63 */     Dimension minimumSize = Options.getDefaultIconSize();
/* 26:64 */     this.icon = icon;
/* 27:65 */     int iconWidth = icon == null ? 0 : icon.getIconWidth();
/* 28:66 */     int iconHeight = icon == null ? 0 : icon.getIconHeight();
/* 29:67 */     this.width = Math.max(iconWidth, Math.max(20, minimumSize.width));
/* 30:68 */     this.height = Math.max(iconHeight, Math.max(20, minimumSize.height));
/* 31:69 */     this.xOffset = Math.max(0, (this.width - iconWidth) / 2);
/* 32:70 */     this.yOffset = Math.max(0, (this.height - iconHeight) / 2);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getIconHeight()
/* 36:   */   {
/* 37:74 */     return this.height;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int getIconWidth()
/* 41:   */   {
/* 42:75 */     return this.width;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void paintIcon(Component c, Graphics g, int x, int y)
/* 46:   */   {
/* 47:79 */     if (this.icon != null) {
/* 48:80 */       this.icon.paintIcon(c, g, x + this.xOffset, y + this.yOffset);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.MinimumSizedIcon
 * JD-Core Version:    0.7.0.1
 */