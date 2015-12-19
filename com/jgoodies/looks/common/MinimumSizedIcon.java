/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import com.jgoodies.looks.Options;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
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
/*    */ public class MinimumSizedIcon
/*    */   implements Icon
/*    */ {
/*    */   private final Icon icon;
/*    */   private final int width;
/*    */   private final int height;
/*    */   private final int xOffset;
/*    */   private final int yOffset;
/*    */   
/*    */   public MinimumSizedIcon()
/*    */   {
/* 59 */     this(null);
/*    */   }
/*    */   
/*    */   public MinimumSizedIcon(Icon icon) {
/* 63 */     Dimension minimumSize = Options.getDefaultIconSize();
/* 64 */     this.icon = icon;
/* 65 */     int iconWidth = icon == null ? 0 : icon.getIconWidth();
/* 66 */     int iconHeight = icon == null ? 0 : icon.getIconHeight();
/* 67 */     this.width = Math.max(iconWidth, Math.max(20, minimumSize.width));
/* 68 */     this.height = Math.max(iconHeight, Math.max(20, minimumSize.height));
/* 69 */     this.xOffset = Math.max(0, (this.width - iconWidth) / 2);
/* 70 */     this.yOffset = Math.max(0, (this.height - iconHeight) / 2);
/*    */   }
/*    */   
/*    */ 
/* 74 */   public int getIconHeight() { return this.height; }
/* 75 */   public int getIconWidth() { return this.width; }
/*    */   
/*    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*    */   {
/* 79 */     if (this.icon != null) {
/* 80 */       this.icon.paintIcon(c, g, x + this.xOffset, y + this.yOffset);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\MinimumSizedIcon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */