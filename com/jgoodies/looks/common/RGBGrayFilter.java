/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import com.jgoodies.looks.Options;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.FilteredImageSource;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.RGBImageFilter;
/*     */ import javax.swing.GrayFilter;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JComponent;
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
/*     */ public final class RGBGrayFilter
/*     */   extends RGBImageFilter
/*     */ {
/*     */   private RGBGrayFilter()
/*     */   {
/*  64 */     this.canFilterIndexColorModel = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Icon getDisabledIcon(JComponent component, Icon icon)
/*     */   {
/*  77 */     if ((icon == null) || (component == null) || (icon.getIconWidth() == 0) || (icon.getIconHeight() == 0))
/*     */     {
/*     */ 
/*     */ 
/*  81 */       return null; }
/*     */     Image img;
/*     */     Image img;
/*  84 */     if ((icon instanceof ImageIcon)) {
/*  85 */       img = ((ImageIcon)icon).getImage();
/*     */     } else {
/*  87 */       img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
/*     */       
/*     */ 
/*     */ 
/*  91 */       icon.paintIcon(component, img.getGraphics(), 0, 0);
/*     */     }
/*  93 */     if ((!Options.isHiResGrayFilterEnabled()) || (Boolean.FALSE.equals(component.getClientProperty("generateHiResDisabledIcon"))))
/*     */     {
/*  95 */       return new ImageIcon(GrayFilter.createDisabledImage(img));
/*     */     }
/*     */     
/*  98 */     ImageProducer producer = new FilteredImageSource(img.getSource(), new RGBGrayFilter());
/*     */     
/*     */ 
/* 101 */     return new ImageIcon(component.createImage(producer));
/*     */   }
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
/*     */   public int filterRGB(int x, int y, int rgb)
/*     */   {
/* 119 */     float avg = ((rgb >> 16 & 0xFF) / 255.0F + (rgb >> 8 & 0xFF) / 255.0F + (rgb & 0xFF) / 255.0F) / 3.0F;
/*     */     
/*     */ 
/*     */ 
/* 123 */     float alpha = (rgb >> 24 & 0xFF) / 255.0F;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 128 */     avg = Math.min(1.0F, 0.35F + 0.65F * avg);
/*     */     
/* 130 */     return (int)(alpha * 255.0F) << 24 | (int)(avg * 255.0F) << 16 | (int)(avg * 255.0F) << 8 | (int)(avg * 255.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\RGBGrayFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */