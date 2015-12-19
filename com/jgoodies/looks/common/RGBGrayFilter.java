/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.Options;
/*   4:    */ import java.awt.Image;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.awt.image.FilteredImageSource;
/*   7:    */ import java.awt.image.ImageProducer;
/*   8:    */ import java.awt.image.RGBImageFilter;
/*   9:    */ import javax.swing.GrayFilter;
/*  10:    */ import javax.swing.Icon;
/*  11:    */ import javax.swing.ImageIcon;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ 
/*  14:    */ public final class RGBGrayFilter
/*  15:    */   extends RGBImageFilter
/*  16:    */ {
/*  17:    */   private RGBGrayFilter()
/*  18:    */   {
/*  19: 64 */     this.canFilterIndexColorModel = true;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static Icon getDisabledIcon(JComponent component, Icon icon)
/*  23:    */   {
/*  24: 77 */     if ((icon == null) || (component == null) || (icon.getIconWidth() == 0) || (icon.getIconHeight() == 0)) {
/*  25: 81 */       return null;
/*  26:    */     }
/*  27:    */     Image img;
/*  28:    */     Image img;
/*  29: 84 */     if ((icon instanceof ImageIcon))
/*  30:    */     {
/*  31: 85 */       img = ((ImageIcon)icon).getImage();
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35: 87 */       img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/*  36:    */       
/*  37:    */ 
/*  38:    */ 
/*  39: 91 */       icon.paintIcon(component, img.getGraphics(), 0, 0);
/*  40:    */     }
/*  41: 93 */     if ((!Options.isHiResGrayFilterEnabled()) || (Boolean.FALSE.equals(component.getClientProperty("generateHiResDisabledIcon")))) {
/*  42: 95 */       return new ImageIcon(GrayFilter.createDisabledImage(img));
/*  43:    */     }
/*  44: 98 */     ImageProducer producer = new FilteredImageSource(img.getSource(), new RGBGrayFilter());
/*  45:    */     
/*  46:    */ 
/*  47:101 */     return new ImageIcon(component.createImage(producer));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int filterRGB(int x, int y, int rgb)
/*  51:    */   {
/*  52:119 */     float avg = ((rgb >> 16 & 0xFF) / 255.0F + (rgb >> 8 & 0xFF) / 255.0F + (rgb & 0xFF) / 255.0F) / 3.0F;
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
/*  56:123 */     float alpha = (rgb >> 24 & 0xFF) / 255.0F;
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:128 */     avg = Math.min(1.0F, 0.35F + 0.65F * avg);
/*  62:    */     
/*  63:130 */     return (int)(alpha * 255.0F) << 24 | (int)(avg * 255.0F) << 16 | (int)(avg * 255.0F) << 8 | (int)(avg * 255.0F);
/*  64:    */   }
/*  65:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.RGBGrayFilter
 * JD-Core Version:    0.7.0.1
 */