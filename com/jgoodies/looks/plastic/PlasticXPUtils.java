/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
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
/*     */ public final class PlasticXPUtils
/*     */ {
/*     */   static void drawPlainButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  58 */     drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), PlasticLookAndFeel.getControlDarkShadow(), LookUtils.getSlightlyBrighter(PlasticLookAndFeel.getControlDarkShadow(), 1.25F));
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
/*     */   static void drawPressedButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  71 */     drawPlainButtonBorder(g, x, y, w, h);
/*  72 */     Color darkColor = translucentColor(PlasticLookAndFeel.getControlDarkShadow(), 128);
/*     */     
/*     */ 
/*  75 */     Color lightColor = translucentColor(PlasticLookAndFeel.getControlHighlight(), 80);
/*     */     
/*     */ 
/*  78 */     g.translate(x, y);
/*  79 */     g.setColor(darkColor);
/*  80 */     g.fillRect(2, 1, w - 4, 1);
/*     */     
/*  82 */     g.setColor(lightColor);
/*  83 */     g.fillRect(2, h - 2, w - 4, 1);
/*  84 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/*  91 */     drawPlainButtonBorder(g, x, y, w, h);
/*  92 */     drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getPrimaryControlDarkShadow());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawFocusedButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 100 */     drawPlainButtonBorder(g, x, y, w, h);
/* 101 */     drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getFocusColor());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawDisabledButtonBorder(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 109 */     drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), MetalLookAndFeel.getControlShadow(), LookUtils.getSlightlyBrighter(MetalLookAndFeel.getControlShadow()));
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
/*     */   public static void drawButtonBorder(Graphics g, int x, int y, int w, int h, Color backgroundColor, Color edgeColor, Color cornerColor)
/*     */   {
/* 127 */     g.translate(x, y);
/*     */     
/* 129 */     g.setColor(edgeColor);
/* 130 */     drawRect(g, 0, 0, w - 1, h - 1);
/*     */     
/*     */ 
/* 133 */     g.setColor(cornerColor);
/* 134 */     g.fillRect(0, 0, 2, 2);
/* 135 */     g.fillRect(0, h - 2, 2, 2);
/* 136 */     g.fillRect(w - 2, 0, 2, 2);
/* 137 */     g.fillRect(w - 2, h - 2, 2, 2);
/*     */     
/*     */ 
/* 140 */     g.setColor(backgroundColor);
/* 141 */     g.fillRect(0, 0, 1, 1);
/* 142 */     g.fillRect(0, h - 1, 1, 1);
/* 143 */     g.fillRect(w - 1, 0, 1, 1);
/* 144 */     g.fillRect(w - 1, h - 1, 1, 1);
/*     */     
/* 146 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void drawInnerButtonDecoration(Graphics g, int x, int y, int w, int h, Color baseColor)
/*     */   {
/* 158 */     Color lightColor = translucentColor(baseColor, 90);
/* 159 */     Color mediumColor = translucentColor(baseColor, 120);
/* 160 */     Color darkColor = translucentColor(baseColor, 200);
/*     */     
/* 162 */     g.translate(x, y);
/* 163 */     g.setColor(lightColor);
/* 164 */     g.fillRect(2, 1, w - 4, 1);
/*     */     
/* 166 */     g.setColor(mediumColor);
/* 167 */     g.fillRect(1, 2, 1, h - 4);
/* 168 */     g.fillRect(w - 2, 2, 1, h - 4);
/* 169 */     drawRect(g, 2, 2, w - 5, h - 5);
/*     */     
/* 171 */     g.setColor(darkColor);
/* 172 */     g.fillRect(2, h - 2, w - 4, 1);
/* 173 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static void drawRect(Graphics g, int x, int y, int w, int h)
/*     */   {
/* 181 */     g.fillRect(x, y, w + 1, 1);
/* 182 */     g.fillRect(x, y + 1, 1, h);
/* 183 */     g.fillRect(x + 1, y + h, w, 1);
/* 184 */     g.fillRect(x + w, y + 1, 1, h);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Color translucentColor(Color baseColor, int alpha)
/*     */   {
/* 196 */     return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */