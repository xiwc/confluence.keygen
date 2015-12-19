/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.ComponentOrientation;
/*   6:    */ import java.awt.GradientPaint;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.Rectangle;
/*  10:    */ import javax.swing.JComponent;
/*  11:    */ import javax.swing.UIManager;
/*  12:    */ 
/*  13:    */ public final class PlasticUtils
/*  14:    */ {
/*  15:    */   private static final float FRACTION_3D = 0.5F;
/*  16:    */   
/*  17:    */   static void drawDark3DBorder(Graphics g, int x, int y, int w, int h)
/*  18:    */   {
/*  19: 53 */     drawFlush3DBorder(g, x, y, w, h);
/*  20: 54 */     g.setColor(PlasticLookAndFeel.getControl());
/*  21: 55 */     g.drawLine(x + 1, y + 1, 1, h - 3);
/*  22: 56 */     g.drawLine(y + 1, y + 1, w - 3, 1);
/*  23:    */   }
/*  24:    */   
/*  25:    */   static void drawDisabledBorder(Graphics g, int x, int y, int w, int h)
/*  26:    */   {
/*  27: 61 */     g.setColor(PlasticLookAndFeel.getControlShadow());
/*  28: 62 */     drawRect(g, x, y, w - 1, h - 1);
/*  29:    */   }
/*  30:    */   
/*  31:    */   static void drawFlush3DBorder(Graphics g, int x, int y, int w, int h)
/*  32:    */   {
/*  33: 70 */     g.translate(x, y);
/*  34: 71 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/*  35: 72 */     drawRect(g, 1, 1, w - 2, h - 2);
/*  36: 73 */     g.drawLine(0, h - 1, 0, h - 1);
/*  37: 74 */     g.drawLine(w - 1, 0, w - 1, 0);
/*  38: 75 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  39: 76 */     drawRect(g, 0, 0, w - 2, h - 2);
/*  40: 77 */     g.translate(-x, -y);
/*  41:    */   }
/*  42:    */   
/*  43:    */   static void drawPressed3DBorder(Graphics g, int x, int y, int w, int h)
/*  44:    */   {
/*  45: 85 */     g.translate(x, y);
/*  46: 86 */     drawFlush3DBorder(g, 0, 0, w, h);
/*  47: 87 */     g.setColor(PlasticLookAndFeel.getControlShadow());
/*  48: 88 */     g.drawLine(1, 1, 1, h - 3);
/*  49: 89 */     g.drawLine(1, 1, w - 3, 1);
/*  50: 90 */     g.translate(-x, -y);
/*  51:    */   }
/*  52:    */   
/*  53:    */   static void drawButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
/*  54:    */   {
/*  55: 98 */     if (active) {
/*  56: 99 */       drawActiveButtonBorder(g, x, y, w, h);
/*  57:    */     } else {
/*  58:101 */       drawFlush3DBorder(g, x, y, w, h);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   static void drawActiveButtonBorder(Graphics g, int x, int y, int w, int h)
/*  63:    */   {
/*  64:109 */     drawFlush3DBorder(g, x, y, w, h);
/*  65:110 */     g.setColor(PlasticLookAndFeel.getPrimaryControl());
/*  66:111 */     g.drawLine(x + 1, y + 1, x + 1, h - 3);
/*  67:112 */     g.drawLine(x + 1, y + 1, w - 3, x + 1);
/*  68:113 */     g.setColor(PlasticLookAndFeel.getPrimaryControlDarkShadow());
/*  69:114 */     g.drawLine(x + 2, h - 2, w - 2, h - 2);
/*  70:115 */     g.drawLine(w - 2, y + 2, w - 2, h - 2);
/*  71:    */   }
/*  72:    */   
/*  73:    */   static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
/*  74:    */   {
/*  75:122 */     drawButtonBorder(g, x + 1, y + 1, w - 1, h - 1, active);
/*  76:123 */     g.translate(x, y);
/*  77:124 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  78:125 */     drawRect(g, 0, 0, w - 3, h - 3);
/*  79:126 */     g.drawLine(w - 2, 0, w - 2, 0);
/*  80:127 */     g.drawLine(0, h - 2, 0, h - 2);
/*  81:128 */     g.setColor(PlasticLookAndFeel.getControl());
/*  82:129 */     g.drawLine(w - 1, 0, w - 1, 0);
/*  83:130 */     g.drawLine(0, h - 1, 0, h - 1);
/*  84:131 */     g.translate(-x, -y);
/*  85:    */   }
/*  86:    */   
/*  87:    */   static void drawDefaultButtonPressedBorder(Graphics g, int x, int y, int w, int h)
/*  88:    */   {
/*  89:135 */     drawPressed3DBorder(g, x + 1, y + 1, w - 1, h - 1);
/*  90:136 */     g.translate(x, y);
/*  91:137 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  92:138 */     drawRect(g, 0, 0, w - 3, h - 3);
/*  93:139 */     g.drawLine(w - 2, 0, w - 2, 0);
/*  94:140 */     g.drawLine(0, h - 2, 0, h - 2);
/*  95:141 */     g.setColor(PlasticLookAndFeel.getControl());
/*  96:142 */     g.drawLine(w - 1, 0, w - 1, 0);
/*  97:143 */     g.drawLine(0, h - 1, 0, h - 1);
/*  98:144 */     g.translate(-x, -y);
/*  99:    */   }
/* 100:    */   
/* 101:    */   static void drawThinFlush3DBorder(Graphics g, int x, int y, int w, int h)
/* 102:    */   {
/* 103:148 */     g.translate(x, y);
/* 104:149 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 105:150 */     g.drawLine(0, 0, w - 2, 0);
/* 106:151 */     g.drawLine(0, 0, 0, h - 2);
/* 107:152 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 108:153 */     g.drawLine(w - 1, 0, w - 1, h - 1);
/* 109:154 */     g.drawLine(0, h - 1, w - 1, h - 1);
/* 110:155 */     g.translate(-x, -y);
/* 111:    */   }
/* 112:    */   
/* 113:    */   static void drawThinPressed3DBorder(Graphics g, int x, int y, int w, int h)
/* 114:    */   {
/* 115:160 */     g.translate(x, y);
/* 116:161 */     g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 117:162 */     g.drawLine(0, 0, w - 2, 0);
/* 118:163 */     g.drawLine(0, 0, 0, h - 2);
/* 119:164 */     g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 120:165 */     g.drawLine(w - 1, 0, w - 1, h - 1);
/* 121:166 */     g.drawLine(0, h - 1, w - 1, h - 1);
/* 122:167 */     g.translate(-x, -y);
/* 123:    */   }
/* 124:    */   
/* 125:    */   static boolean isLeftToRight(Component c)
/* 126:    */   {
/* 127:175 */     return c.getComponentOrientation().isLeftToRight();
/* 128:    */   }
/* 129:    */   
/* 130:    */   static boolean is3D(String keyPrefix)
/* 131:    */   {
/* 132:190 */     Object value = UIManager.get(keyPrefix + "is3DEnabled");
/* 133:191 */     return Boolean.TRUE.equals(value);
/* 134:    */   }
/* 135:    */   
/* 136:    */   static boolean force3D(JComponent c)
/* 137:    */   {
/* 138:203 */     Object value = c.getClientProperty("Plastic.is3D");
/* 139:204 */     return Boolean.TRUE.equals(value);
/* 140:    */   }
/* 141:    */   
/* 142:    */   static boolean forceFlat(JComponent c)
/* 143:    */   {
/* 144:216 */     Object value = c.getClientProperty("Plastic.is3D");
/* 145:217 */     return Boolean.FALSE.equals(value);
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static void add3DEffekt(Graphics g, Rectangle r, boolean isHorizontal, Color startC0, Color stopC0, Color startC1, Color stopC1)
/* 149:    */   {
/* 150:229 */     Graphics2D g2 = (Graphics2D)g;
/* 151:    */     int yd1;
/* 152:    */     int width;
/* 153:    */     int height;
/* 154:    */     int xb0;
/* 155:    */     int yb0;
/* 156:    */     int xb1;
/* 157:    */     int yb1;
/* 158:    */     int xd0;
/* 159:    */     int yd0;
/* 160:    */     int xd1;
/* 161:    */     int yd1;
/* 162:231 */     if (isHorizontal)
/* 163:    */     {
/* 164:232 */       int width = r.width;
/* 165:233 */       int height = (int)(r.height * 0.5F);
/* 166:234 */       int xb0 = r.x;
/* 167:235 */       int yb0 = r.y;
/* 168:236 */       int xb1 = xb0;
/* 169:237 */       int yb1 = yb0 + height;
/* 170:238 */       int xd0 = xb1;
/* 171:239 */       int yd0 = yb1;
/* 172:240 */       int xd1 = xd0;
/* 173:241 */       yd1 = r.y + r.height;
/* 174:    */     }
/* 175:    */     else
/* 176:    */     {
/* 177:243 */       width = (int)(r.width * 0.5F);
/* 178:244 */       height = r.height;
/* 179:245 */       xb0 = r.x;
/* 180:246 */       yb0 = r.y;
/* 181:247 */       xb1 = xb0 + width;
/* 182:248 */       yb1 = yb0;
/* 183:249 */       xd0 = xb1;
/* 184:250 */       yd0 = yb0;
/* 185:251 */       xd1 = r.x + r.width;
/* 186:252 */       yd1 = yd0;
/* 187:    */     }
/* 188:254 */     g2.setPaint(new GradientPaint(xb0, yb0, stopC0, xb1, yb1, startC0));
/* 189:255 */     g2.fillRect(r.x, r.y, width, height);
/* 190:256 */     g2.setPaint(new GradientPaint(xd0, yd0, startC1, xd1, yd1, stopC1));
/* 191:257 */     g2.fillRect(xd0, yd0, width, height);
/* 192:    */   }
/* 193:    */   
/* 194:    */   static void add3DEffekt(Graphics g, Rectangle r)
/* 195:    */   {
/* 196:262 */     Color brightenStop = UIManager.getColor("Plastic.brightenStop");
/* 197:263 */     if (null == brightenStop) {
/* 198:264 */       brightenStop = PlasticTheme.BRIGHTEN_STOP;
/* 199:    */     }
/* 200:267 */     Graphics2D g2 = (Graphics2D)g;
/* 201:268 */     int border = 10;
/* 202:269 */     g2.setPaint(new GradientPaint(r.x, r.y, brightenStop, r.x + border, r.y, PlasticTheme.BRIGHTEN_START));
/* 203:270 */     g2.fillRect(r.x, r.y, border, r.height);
/* 204:271 */     int x = r.x + r.width - border;
/* 205:272 */     int y = r.y;
/* 206:273 */     g2.setPaint(new GradientPaint(x, y, PlasticTheme.DARKEN_START, x + border, y, PlasticTheme.LT_DARKEN_STOP));
/* 207:274 */     g2.fillRect(x, y, border, r.height);
/* 208:    */     
/* 209:276 */     add3DEffekt(g, r, true, PlasticTheme.BRIGHTEN_START, brightenStop, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP);
/* 210:    */   }
/* 211:    */   
/* 212:    */   static void addLight3DEffekt(Graphics g, Rectangle r, boolean isHorizontal)
/* 213:    */   {
/* 214:281 */     Color ltBrightenStop = UIManager.getColor("Plastic.ltBrightenStop");
/* 215:282 */     if (null == ltBrightenStop) {
/* 216:283 */       ltBrightenStop = PlasticTheme.LT_BRIGHTEN_STOP;
/* 217:    */     }
/* 218:285 */     add3DEffekt(g, r, isHorizontal, PlasticTheme.BRIGHTEN_START, ltBrightenStop, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static void addLight3DEffekt(Graphics g, Rectangle r)
/* 222:    */   {
/* 223:294 */     Color ltBrightenStop = UIManager.getColor("Plastic.ltBrightenStop");
/* 224:295 */     if (null == ltBrightenStop) {
/* 225:296 */       ltBrightenStop = PlasticTheme.LT_BRIGHTEN_STOP;
/* 226:    */     }
/* 227:298 */     add3DEffekt(g, r, true, PlasticTheme.DARKEN_START, PlasticTheme.LT_DARKEN_STOP, PlasticTheme.BRIGHTEN_START, ltBrightenStop);
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static void drawRect(Graphics g, int x, int y, int w, int h)
/* 231:    */   {
/* 232:308 */     g.fillRect(x, y, w + 1, 1);
/* 233:309 */     g.fillRect(x, y + 1, 1, h);
/* 234:310 */     g.fillRect(x + 1, y + h, w, 1);
/* 235:311 */     g.fillRect(x + w, y + 1, 1, h);
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticUtils
 * JD-Core Version:    0.7.0.1
 */