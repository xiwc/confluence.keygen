/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*   7:    */ 
/*   8:    */ public final class PlasticXPUtils
/*   9:    */ {
/*  10:    */   static void drawPlainButtonBorder(Graphics g, int x, int y, int w, int h)
/*  11:    */   {
/*  12: 58 */     drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), PlasticLookAndFeel.getControlDarkShadow(), LookUtils.getSlightlyBrighter(PlasticLookAndFeel.getControlDarkShadow(), 1.25F));
/*  13:    */   }
/*  14:    */   
/*  15:    */   static void drawPressedButtonBorder(Graphics g, int x, int y, int w, int h)
/*  16:    */   {
/*  17: 71 */     drawPlainButtonBorder(g, x, y, w, h);
/*  18: 72 */     Color darkColor = translucentColor(PlasticLookAndFeel.getControlDarkShadow(), 128);
/*  19:    */     
/*  20:    */ 
/*  21: 75 */     Color lightColor = translucentColor(PlasticLookAndFeel.getControlHighlight(), 80);
/*  22:    */     
/*  23:    */ 
/*  24: 78 */     g.translate(x, y);
/*  25: 79 */     g.setColor(darkColor);
/*  26: 80 */     g.fillRect(2, 1, w - 4, 1);
/*  27:    */     
/*  28: 82 */     g.setColor(lightColor);
/*  29: 83 */     g.fillRect(2, h - 2, w - 4, 1);
/*  30: 84 */     g.translate(-x, -y);
/*  31:    */   }
/*  32:    */   
/*  33:    */   static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h)
/*  34:    */   {
/*  35: 91 */     drawPlainButtonBorder(g, x, y, w, h);
/*  36: 92 */     drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getPrimaryControlDarkShadow());
/*  37:    */   }
/*  38:    */   
/*  39:    */   static void drawFocusedButtonBorder(Graphics g, int x, int y, int w, int h)
/*  40:    */   {
/*  41:100 */     drawPlainButtonBorder(g, x, y, w, h);
/*  42:101 */     drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getFocusColor());
/*  43:    */   }
/*  44:    */   
/*  45:    */   static void drawDisabledButtonBorder(Graphics g, int x, int y, int w, int h)
/*  46:    */   {
/*  47:109 */     drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), MetalLookAndFeel.getControlShadow(), LookUtils.getSlightlyBrighter(MetalLookAndFeel.getControlShadow()));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void drawButtonBorder(Graphics g, int x, int y, int w, int h, Color backgroundColor, Color edgeColor, Color cornerColor)
/*  51:    */   {
/*  52:127 */     g.translate(x, y);
/*  53:    */     
/*  54:129 */     g.setColor(edgeColor);
/*  55:130 */     drawRect(g, 0, 0, w - 1, h - 1);
/*  56:    */     
/*  57:    */ 
/*  58:133 */     g.setColor(cornerColor);
/*  59:134 */     g.fillRect(0, 0, 2, 2);
/*  60:135 */     g.fillRect(0, h - 2, 2, 2);
/*  61:136 */     g.fillRect(w - 2, 0, 2, 2);
/*  62:137 */     g.fillRect(w - 2, h - 2, 2, 2);
/*  63:    */     
/*  64:    */ 
/*  65:140 */     g.setColor(backgroundColor);
/*  66:141 */     g.fillRect(0, 0, 1, 1);
/*  67:142 */     g.fillRect(0, h - 1, 1, 1);
/*  68:143 */     g.fillRect(w - 1, 0, 1, 1);
/*  69:144 */     g.fillRect(w - 1, h - 1, 1, 1);
/*  70:    */     
/*  71:146 */     g.translate(-x, -y);
/*  72:    */   }
/*  73:    */   
/*  74:    */   private static void drawInnerButtonDecoration(Graphics g, int x, int y, int w, int h, Color baseColor)
/*  75:    */   {
/*  76:158 */     Color lightColor = translucentColor(baseColor, 90);
/*  77:159 */     Color mediumColor = translucentColor(baseColor, 120);
/*  78:160 */     Color darkColor = translucentColor(baseColor, 200);
/*  79:    */     
/*  80:162 */     g.translate(x, y);
/*  81:163 */     g.setColor(lightColor);
/*  82:164 */     g.fillRect(2, 1, w - 4, 1);
/*  83:    */     
/*  84:166 */     g.setColor(mediumColor);
/*  85:167 */     g.fillRect(1, 2, 1, h - 4);
/*  86:168 */     g.fillRect(w - 2, 2, 1, h - 4);
/*  87:169 */     drawRect(g, 2, 2, w - 5, h - 5);
/*  88:    */     
/*  89:171 */     g.setColor(darkColor);
/*  90:172 */     g.fillRect(2, h - 2, w - 4, 1);
/*  91:173 */     g.translate(-x, -y);
/*  92:    */   }
/*  93:    */   
/*  94:    */   static void drawRect(Graphics g, int x, int y, int w, int h)
/*  95:    */   {
/*  96:181 */     g.fillRect(x, y, w + 1, 1);
/*  97:182 */     g.fillRect(x, y + 1, 1, h);
/*  98:183 */     g.fillRect(x + 1, y + h, w, 1);
/*  99:184 */     g.fillRect(x + w, y + 1, 1, h);
/* 100:    */   }
/* 101:    */   
/* 102:    */   private static Color translucentColor(Color baseColor, int alpha)
/* 103:    */   {
/* 104:196 */     return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPUtils
 * JD-Core Version:    0.7.0.1
 */