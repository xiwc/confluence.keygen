/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
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
/*     */ public final class PlasticUtils
/*     */ {
/*     */   private static final float FRACTION_3D = 0.5F;
/*     */   
/*     */   static void drawDark3DBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  53 */     drawFlush3DBorder(g, x, y, w, h);
/*  54 */     g.setColor(PlasticLookAndFeel.getControl());
/*  55 */     g.drawLine(x + 1, y + 1, 1, h - 3);
/*  56 */     g.drawLine(y + 1, y + 1, w - 3, 1);
/*     */   }
/*     */   
/*     */   static void drawDisabledBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  61 */     g.setColor(PlasticLookAndFeel.getControlShadow());
/*  62 */     drawRect(g, x, y, w - 1, h - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawFlush3DBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  70 */     g.translate(x, y);
/*  71 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/*  72 */     drawRect(g, 1, 1, w - 2, h - 2);
/*  73 */     g.drawLine(0, h - 1, 0, h - 1);
/*  74 */     g.drawLine(w - 1, 0, w - 1, 0);
/*  75 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  76 */     drawRect(g, 0, 0, w - 2, h - 2);
/*  77 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawPressed3DBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  85 */     g.translate(x, y);
/*  86 */     drawFlush3DBorder(g, 0, 0, w, h);
/*  87 */     g.setColor(PlasticLookAndFeel.getControlShadow());
/*  88 */     g.drawLine(1, 1, 1, h - 3);
/*  89 */     g.drawLine(1, 1, w - 3, 1);
/*  90 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
/*     */   {
/*  98 */     if (active) {
/*  99 */       drawActiveButtonBorder(g, x, y, w, h);
/*     */     } else {
/* 101 */       drawFlush3DBorder(g, x, y, w, h);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void drawActiveButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 109 */     drawFlush3DBorder(g, x, y, w, h);
/* 110 */     g.setColor(PlasticLookAndFeel.getPrimaryControl());
/* 111 */     g.drawLine(x + 1, y + 1, x + 1, h - 3);
/* 112 */     g.drawLine(x + 1, y + 1, w - 3, x + 1);
/* 113 */     g.setColor(PlasticLookAndFeel.getPrimaryControlDarkShadow());
/* 114 */     g.drawLine(x + 2, h - 2, w - 2, h - 2);
/* 115 */     g.drawLine(w - 2, y + 2, w - 2, h - 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
/*     */   {
/* 122 */     drawButtonBorder(g, x + 1, y + 1, w - 1, h - 1, active);
/* 123 */     g.translate(x, y);
/* 124 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 125 */     drawRect(g, 0, 0, w - 3, h - 3);
/* 126 */     g.drawLine(w - 2, 0, w - 2, 0);
/* 127 */     g.drawLine(0, h - 2, 0, h - 2);
/* 128 */     g.setColor(PlasticLookAndFeel.getControl());
/* 129 */     g.drawLine(w - 1, 0, w - 1, 0);
/* 130 */     g.drawLine(0, h - 1, 0, h - 1);
/* 131 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */   static void drawDefaultButtonPressedBorder(Graphics g, int x, int y, int w, int h) {
/* 135 */     drawPressed3DBorder(g, x + 1, y + 1, w - 1, h - 1);
/* 136 */     g.translate(x, y);
/* 137 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 138 */     drawRect(g, 0, 0, w - 3, h - 3);
/* 139 */     g.drawLine(w - 2, 0, w - 2, 0);
/* 140 */     g.drawLine(0, h - 2, 0, h - 2);
/* 141 */     g.setColor(PlasticLookAndFeel.getControl());
/* 142 */     g.drawLine(w - 1, 0, w - 1, 0);
/* 143 */     g.drawLine(0, h - 1, 0, h - 1);
/* 144 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */   static void drawThinFlush3DBorder(Graphics g, int x, int y, int w, int h) {
/* 148 */     g.translate(x, y);
/* 149 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 150 */     g.drawLine(0, 0, w - 2, 0);
/* 151 */     g.drawLine(0, 0, 0, h - 2);
/* 152 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 153 */     g.drawLine(w - 1, 0, w - 1, h - 1);
/* 154 */     g.drawLine(0, h - 1, w - 1, h - 1);
/* 155 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */   static void drawThinPressed3DBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 160 */     g.translate(x, y);
/* 161 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 162 */     g.drawLine(0, 0, w - 2, 0);
/* 163 */     g.drawLine(0, 0, 0, h - 2);
/* 164 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 165 */     g.drawLine(w - 1, 0, w - 1, h - 1);
/* 166 */     g.drawLine(0, h - 1, w - 1, h - 1);
/* 167 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isLeftToRight(Component c)
/*     */   {
/* 175 */     return c.getComponentOrientation().isLeftToRight();
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
/*     */   static boolean is3D(String keyPrefix)
/*     */   {
/* 190 */     Object value = UIManager.get(keyPrefix + "is3DEnabled");
/* 191 */     return Boolean.TRUE.equals(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean force3D(JComponent c)
/*     */   {
/* 203 */     Object value = c.getClientProperty("Plastic.is3D");
/* 204 */     return Boolean.TRUE.equals(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean forceFlat(JComponent c)
/*     */   {
/* 216 */     Object value = c.getClientProperty("Plastic.is3D");
/* 217 */     return Boolean.FALSE.equals(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void add3DEffekt(Graphics g, Rectangle r, boolean isHorizontal, Color startC0, Color stopC0, Color startC1, Color stopC1)
/*     */   {
/* 229 */     Graphics2D g2 = (Graphics2D)g;
/*     */     int yd1;
/* 231 */     int width; int height; int xb0; int yb0; int xb1; int yb1; int xd0; int yd0; int xd1; int yd1; if (isHorizontal) {
/* 232 */       int width = r.width;
/* 233 */       int height = (int)(r.height * 0.5F);
/* 234 */       int xb0 = r.x;
/* 235 */       int yb0 = r.y;
/* 236 */       int xb1 = xb0;
/* 237 */       int yb1 = yb0 + height;
/* 238 */       int xd0 = xb1;
/* 239 */       int yd0 = yb1;
/* 240 */       int xd1 = xd0;
/* 241 */       yd1 = r.y + r.height;
/*     */     } else {
/* 243 */       width = (int)(r.width * 0.5F);
/* 244 */       height = r.height;
/* 245 */       xb0 = r.x;
/* 246 */       yb0 = r.y;
/* 247 */       xb1 = xb0 + width;
/* 248 */       yb1 = yb0;
/* 249 */       xd0 = xb1;
/* 250 */       yd0 = yb0;
/* 251 */       xd1 = r.x + r.width;
/* 252 */       yd1 = yd0;
/*     */     }
/* 254 */     g2.setPaint(new GradientPaint(xb0, yb0, stopC0, xb1, yb1, startC0));
/* 255 */     g2.fillRect(r.x, r.y, width, height);
/* 256 */     g2.setPaint(new GradientPaint(xd0, yd0, startC1, xd1, yd1, stopC1));
/* 257 */     g2.fillRect(xd0, yd0, width, height);
/*     */   }
/*     */   
/*     */   static void add3DEffekt(Graphics g, Rectangle r)
/*     */   {
/* 262 */     Color brightenStop = UIManager.getColor("Plastic.brightenStop");
/* 263 */     if (null == brightenStop) {
/* 264 */       brightenStop = PlasticTheme.BRIGHTEN_STOP;
/*     */     }
/*     */     
/* 267 */     Graphics2D g2 = (Graphics2D)g;
/* 268 */     int border = 10;
/* 269 */     g2.setPaint(new GradientPaint(r.x, r.y, brightenStop, r.x + border, r.y, PlasticTheme.BRIGHTEN_START));
/* 270 */     g2.fillRect(r.x, r.y, border, r.height);
/* 271 */     int x = r.x + r.width - border;
/* 272 */     int y = r.y;
/* 273 */     g2.setPaint(new GradientPaint(x, y, PlasticTheme.DARKEN_START, x + border, y, PlasticTheme.LT_DARKEN_STOP));
/* 274 */     g2.fillRect(x, y, border, r.height);
/*     */     
/* 276 */     add3DEffekt(g, r, true, PlasticTheme.BRIGHTEN_START, brightenStop, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP);
/*     */   }
/*     */   
/*     */   static void addLight3DEffekt(Graphics g, Rectangle r, boolean isHorizontal)
/*     */   {
/* 281 */     Color ltBrightenStop = UIManager.getColor("Plastic.ltBrightenStop");
/* 282 */     if (null == ltBrightenStop) {
/* 283 */       ltBrightenStop = PlasticTheme.LT_BRIGHTEN_STOP;
/*     */     }
/* 285 */     add3DEffekt(g, r, isHorizontal, PlasticTheme.BRIGHTEN_START, ltBrightenStop, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addLight3DEffekt(Graphics g, Rectangle r)
/*     */   {
/* 294 */     Color ltBrightenStop = UIManager.getColor("Plastic.ltBrightenStop");
/* 295 */     if (null == ltBrightenStop) {
/* 296 */       ltBrightenStop = PlasticTheme.LT_BRIGHTEN_STOP;
/*     */     }
/* 298 */     add3DEffekt(g, r, true, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP, PlasticTheme.BRIGHTEN_START, ltBrightenStop);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void drawRect(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 308 */     g.fillRect(x, y, w + 1, 1);
/* 309 */     g.fillRect(x, y + 1, 1, h);
/* 310 */     g.fillRect(x + 1, y + h, w, 1);
/* 311 */     g.fillRect(x + w, y + 1, 1, h);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */