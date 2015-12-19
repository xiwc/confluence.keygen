/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
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
/*     */ final class PlasticBumps
/*     */   implements Icon
/*     */ {
/*  51 */   private static final List BUFFERS = new ArrayList();
/*     */   
/*     */   private int xBumps;
/*     */   
/*     */   private int yBumps;
/*     */   
/*     */   private Color topColor;
/*     */   
/*     */   private Color shadowColor;
/*     */   
/*     */   private Color backColor;
/*     */   
/*     */   private BumpBuffer buffer;
/*     */   
/*     */   PlasticBumps(int width, int height, Color newTopColor, Color newShadowColor, Color newBackColor)
/*     */   {
/*  67 */     setBumpArea(width, height);
/*  68 */     setBumpColors(newTopColor, newShadowColor, newBackColor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setBumpArea(int width, int height)
/*     */   {
/*  75 */     this.xBumps = (width / 2);
/*  76 */     this.yBumps = (height / 2);
/*     */   }
/*     */   
/*     */   void setBumpColors(Color newTopColor, Color newShadowColor, Color newBackColor)
/*     */   {
/*  81 */     this.topColor = newTopColor;
/*  82 */     this.shadowColor = newShadowColor;
/*  83 */     this.backColor = newBackColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paintIcon(Component c, Graphics g, int x, int y)
/*     */   {
/*  90 */     GraphicsConfiguration gc = (g instanceof Graphics2D) ? ((Graphics2D)g).getDeviceConfiguration() : null;
/*     */     
/*     */ 
/*     */ 
/*  94 */     this.buffer = getBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/*     */     
/*  96 */     int bufferWidth = this.buffer.getImageSize().width;
/*  97 */     int bufferHeight = this.buffer.getImageSize().height;
/*  98 */     int iconWidth = getIconWidth();
/*  99 */     int iconHeight = getIconHeight();
/* 100 */     int x2 = x + iconWidth;
/* 101 */     int y2 = y + iconHeight;
/* 102 */     int savex = x;
/*     */     
/* 104 */     while (y < y2) {
/* 105 */       int h = Math.min(y2 - y, bufferHeight);
/* 106 */       for (x = savex; x < x2; x += bufferWidth) {
/* 107 */         int w = Math.min(x2 - x, bufferWidth);
/* 108 */         g.drawImage(this.buffer.getImage(), x, y, x + w, y + h, 0, 0, w, h, null);
/*     */       }
/* 110 */       y += bufferHeight;
/*     */     }
/*     */   }
/*     */   
/* 114 */   public int getIconWidth() { return this.xBumps * 2; }
/* 115 */   public int getIconHeight() { return this.yBumps * 2; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private BumpBuffer getBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */   {
/* 122 */     if ((this.buffer != null) && (this.buffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor)))
/*     */     {
/* 124 */       return this.buffer;
/*     */     }
/* 126 */     BumpBuffer result = null;
/* 127 */     for (Iterator iterator = BUFFERS.iterator(); iterator.hasNext();) {
/* 128 */       BumpBuffer aBuffer = (BumpBuffer)iterator.next();
/* 129 */       if (aBuffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor)) {
/* 130 */         result = aBuffer;
/* 131 */         break;
/*     */       }
/*     */     }
/* 134 */     if (result == null) {
/* 135 */       result = new BumpBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/* 136 */       BUFFERS.add(result);
/*     */     }
/* 138 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class BumpBuffer
/*     */   {
/*     */     private static final int IMAGE_SIZE = 64;
/*     */     
/* 147 */     private static Dimension imageSize = new Dimension(64, 64);
/*     */     
/*     */     transient Image image;
/*     */     
/*     */     private final Color topColor;
/*     */     
/*     */     private final Color shadowColor;
/*     */     
/*     */     private final Color backColor;
/*     */     private final GraphicsConfiguration gc;
/*     */     
/*     */     BumpBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */     {
/* 160 */       this.gc = gc;
/* 161 */       this.topColor = aTopColor;
/* 162 */       this.shadowColor = aShadowColor;
/* 163 */       this.backColor = aBackColor;
/* 164 */       createImage();
/* 165 */       fillBumpBuffer();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean hasSameConfiguration(GraphicsConfiguration aGC, Color aTopColor, Color aShadowColor, Color aBackColor)
/*     */     {
/* 174 */       if (this.gc != null) {
/* 175 */         if (!this.gc.equals(aGC)) {
/* 176 */           return false;
/*     */         }
/* 178 */       } else if (aGC != null) {
/* 179 */         return false;
/*     */       }
/* 181 */       return (this.topColor.equals(aTopColor)) && (this.shadowColor.equals(aShadowColor)) && (this.backColor.equals(aBackColor));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Image getImage()
/*     */     {
/* 191 */       return this.image;
/*     */     }
/*     */     
/* 194 */     Dimension getImageSize() { return imageSize; }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private void fillBumpBuffer()
/*     */     {
/* 201 */       Graphics g = this.image.getGraphics();
/*     */       
/* 203 */       g.setColor(this.backColor);
/* 204 */       g.fillRect(0, 0, 64, 64);
/*     */       
/* 206 */       g.setColor(this.topColor);
/* 207 */       for (int x = 0; x < 64; x += 4) {
/* 208 */         for (int y = 0; y < 64; y += 4) {
/* 209 */           g.drawLine(x, y, x, y);
/* 210 */           g.drawLine(x + 2, y + 2, x + 2, y + 2);
/*     */         }
/*     */       }
/*     */       
/* 214 */       g.setColor(this.shadowColor);
/* 215 */       for (int x = 0; x < 64; x += 4) {
/* 216 */         for (int y = 0; y < 64; y += 4) {
/* 217 */           g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 218 */           g.drawLine(x + 3, y + 3, x + 3, y + 3);
/*     */         }
/*     */       }
/* 221 */       g.dispose();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void createImage()
/*     */     {
/* 230 */       if (this.gc != null) {
/* 231 */         this.image = this.gc.createCompatibleImage(64, 64);
/*     */       } else {
/* 233 */         int[] cmap = { this.backColor.getRGB(), this.topColor.getRGB(), this.shadowColor.getRGB() };
/* 234 */         IndexColorModel icm = new IndexColorModel(8, 3, cmap, 0, false, -1, 0);
/*     */         
/* 236 */         this.image = new BufferedImage(64, 64, 13, icm);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticBumps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */