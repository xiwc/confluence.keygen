/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.Graphics2D;
/*   8:    */ import java.awt.GraphicsConfiguration;
/*   9:    */ import java.awt.Image;
/*  10:    */ import java.awt.image.BufferedImage;
/*  11:    */ import java.awt.image.IndexColorModel;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.swing.Icon;
/*  16:    */ 
/*  17:    */ final class PlasticBumps
/*  18:    */   implements Icon
/*  19:    */ {
/*  20: 51 */   private static final List BUFFERS = new ArrayList();
/*  21:    */   private int xBumps;
/*  22:    */   private int yBumps;
/*  23:    */   private Color topColor;
/*  24:    */   private Color shadowColor;
/*  25:    */   private Color backColor;
/*  26:    */   private BumpBuffer buffer;
/*  27:    */   
/*  28:    */   PlasticBumps(int width, int height, Color newTopColor, Color newShadowColor, Color newBackColor)
/*  29:    */   {
/*  30: 67 */     setBumpArea(width, height);
/*  31: 68 */     setBumpColors(newTopColor, newShadowColor, newBackColor);
/*  32:    */   }
/*  33:    */   
/*  34:    */   void setBumpArea(int width, int height)
/*  35:    */   {
/*  36: 75 */     this.xBumps = (width / 2);
/*  37: 76 */     this.yBumps = (height / 2);
/*  38:    */   }
/*  39:    */   
/*  40:    */   void setBumpColors(Color newTopColor, Color newShadowColor, Color newBackColor)
/*  41:    */   {
/*  42: 81 */     this.topColor = newTopColor;
/*  43: 82 */     this.shadowColor = newShadowColor;
/*  44: 83 */     this.backColor = newBackColor;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*  48:    */   {
/*  49: 90 */     GraphicsConfiguration gc = (g instanceof Graphics2D) ? ((Graphics2D)g).getDeviceConfiguration() : null;
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53: 94 */     this.buffer = getBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/*  54:    */     
/*  55: 96 */     int bufferWidth = this.buffer.getImageSize().width;
/*  56: 97 */     int bufferHeight = this.buffer.getImageSize().height;
/*  57: 98 */     int iconWidth = getIconWidth();
/*  58: 99 */     int iconHeight = getIconHeight();
/*  59:100 */     int x2 = x + iconWidth;
/*  60:101 */     int y2 = y + iconHeight;
/*  61:102 */     int savex = x;
/*  62:104 */     while (y < y2)
/*  63:    */     {
/*  64:105 */       int h = Math.min(y2 - y, bufferHeight);
/*  65:106 */       for (x = savex; x < x2; x += bufferWidth)
/*  66:    */       {
/*  67:107 */         int w = Math.min(x2 - x, bufferWidth);
/*  68:108 */         g.drawImage(this.buffer.getImage(), x, y, x + w, y + h, 0, 0, w, h, null);
/*  69:    */       }
/*  70:110 */       y += bufferHeight;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIconWidth()
/*  75:    */   {
/*  76:114 */     return this.xBumps * 2;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIconHeight()
/*  80:    */   {
/*  81:115 */     return this.yBumps * 2;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private BumpBuffer getBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/*  85:    */   {
/*  86:122 */     if ((this.buffer != null) && (this.buffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor))) {
/*  87:124 */       return this.buffer;
/*  88:    */     }
/*  89:126 */     BumpBuffer result = null;
/*  90:127 */     for (Iterator iterator = BUFFERS.iterator(); iterator.hasNext();)
/*  91:    */     {
/*  92:128 */       BumpBuffer aBuffer = (BumpBuffer)iterator.next();
/*  93:129 */       if (aBuffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor))
/*  94:    */       {
/*  95:130 */         result = aBuffer;
/*  96:131 */         break;
/*  97:    */       }
/*  98:    */     }
/*  99:134 */     if (result == null)
/* 100:    */     {
/* 101:135 */       result = new BumpBuffer(gc, this.topColor, this.shadowColor, this.backColor);
/* 102:136 */       BUFFERS.add(result);
/* 103:    */     }
/* 104:138 */     return result;
/* 105:    */   }
/* 106:    */   
/* 107:    */   private static final class BumpBuffer
/* 108:    */   {
/* 109:    */     private static final int IMAGE_SIZE = 64;
/* 110:147 */     private static Dimension imageSize = new Dimension(64, 64);
/* 111:    */     transient Image image;
/* 112:    */     private final Color topColor;
/* 113:    */     private final Color shadowColor;
/* 114:    */     private final Color backColor;
/* 115:    */     private final GraphicsConfiguration gc;
/* 116:    */     
/* 117:    */     BumpBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor)
/* 118:    */     {
/* 119:160 */       this.gc = gc;
/* 120:161 */       this.topColor = aTopColor;
/* 121:162 */       this.shadowColor = aShadowColor;
/* 122:163 */       this.backColor = aBackColor;
/* 123:164 */       createImage();
/* 124:165 */       fillBumpBuffer();
/* 125:    */     }
/* 126:    */     
/* 127:    */     boolean hasSameConfiguration(GraphicsConfiguration aGC, Color aTopColor, Color aShadowColor, Color aBackColor)
/* 128:    */     {
/* 129:174 */       if (this.gc != null)
/* 130:    */       {
/* 131:175 */         if (!this.gc.equals(aGC)) {
/* 132:176 */           return false;
/* 133:    */         }
/* 134:    */       }
/* 135:178 */       else if (aGC != null) {
/* 136:179 */         return false;
/* 137:    */       }
/* 138:181 */       return (this.topColor.equals(aTopColor)) && (this.shadowColor.equals(aShadowColor)) && (this.backColor.equals(aBackColor));
/* 139:    */     }
/* 140:    */     
/* 141:    */     Image getImage()
/* 142:    */     {
/* 143:191 */       return this.image;
/* 144:    */     }
/* 145:    */     
/* 146:    */     Dimension getImageSize()
/* 147:    */     {
/* 148:194 */       return imageSize;
/* 149:    */     }
/* 150:    */     
/* 151:    */     private void fillBumpBuffer()
/* 152:    */     {
/* 153:201 */       Graphics g = this.image.getGraphics();
/* 154:    */       
/* 155:203 */       g.setColor(this.backColor);
/* 156:204 */       g.fillRect(0, 0, 64, 64);
/* 157:    */       
/* 158:206 */       g.setColor(this.topColor);
/* 159:207 */       for (int x = 0; x < 64; x += 4) {
/* 160:208 */         for (int y = 0; y < 64; y += 4)
/* 161:    */         {
/* 162:209 */           g.drawLine(x, y, x, y);
/* 163:210 */           g.drawLine(x + 2, y + 2, x + 2, y + 2);
/* 164:    */         }
/* 165:    */       }
/* 166:214 */       g.setColor(this.shadowColor);
/* 167:215 */       for (int x = 0; x < 64; x += 4) {
/* 168:216 */         for (int y = 0; y < 64; y += 4)
/* 169:    */         {
/* 170:217 */           g.drawLine(x + 1, y + 1, x + 1, y + 1);
/* 171:218 */           g.drawLine(x + 3, y + 3, x + 3, y + 3);
/* 172:    */         }
/* 173:    */       }
/* 174:221 */       g.dispose();
/* 175:    */     }
/* 176:    */     
/* 177:    */     private void createImage()
/* 178:    */     {
/* 179:230 */       if (this.gc != null)
/* 180:    */       {
/* 181:231 */         this.image = this.gc.createCompatibleImage(64, 64);
/* 182:    */       }
/* 183:    */       else
/* 184:    */       {
/* 185:233 */         int[] cmap = { this.backColor.getRGB(), this.topColor.getRGB(), this.shadowColor.getRGB() };
/* 186:234 */         IndexColorModel icm = new IndexColorModel(8, 3, cmap, 0, false, -1, 0);
/* 187:    */         
/* 188:236 */         this.image = new BufferedImage(64, 64, 13, icm);
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticBumps
 * JD-Core Version:    0.7.0.1
 */