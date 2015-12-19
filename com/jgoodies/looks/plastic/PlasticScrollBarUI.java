/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Rectangle;
/*   6:    */ import javax.swing.JButton;
/*   7:    */ import javax.swing.JComponent;
/*   8:    */ import javax.swing.JScrollBar;
/*   9:    */ import javax.swing.UIManager;
/*  10:    */ import javax.swing.plaf.ComponentUI;
/*  11:    */ import javax.swing.plaf.metal.MetalScrollBarUI;
/*  12:    */ 
/*  13:    */ public final class PlasticScrollBarUI
/*  14:    */   extends MetalScrollBarUI
/*  15:    */ {
/*  16:    */   private static final String PROPERTY_PREFIX = "ScrollBar.";
/*  17:    */   public static final String MAX_BUMPS_WIDTH_KEY = "ScrollBar.maxBumpsWidth";
/*  18:    */   private Color shadowColor;
/*  19:    */   private Color highlightColor;
/*  20:    */   private Color darkShadowColor;
/*  21:    */   private Color thumbColor;
/*  22:    */   private Color thumbShadow;
/*  23:    */   private Color thumbHighlightColor;
/*  24:    */   private PlasticBumps bumps;
/*  25:    */   
/*  26:    */   public static ComponentUI createUI(JComponent b)
/*  27:    */   {
/*  28: 69 */     return new PlasticScrollBarUI();
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void installDefaults()
/*  32:    */   {
/*  33: 74 */     super.installDefaults();
/*  34: 75 */     this.bumps = new PlasticBumps(10, 10, this.thumbHighlightColor, this.thumbShadow, this.thumbColor);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected JButton createDecreaseButton(int orientation)
/*  38:    */   {
/*  39: 80 */     this.decreaseButton = new PlasticArrowButton(orientation, this.scrollBarWidth, this.isFreeStanding);
/*  40: 81 */     return this.decreaseButton;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected JButton createIncreaseButton(int orientation)
/*  44:    */   {
/*  45: 86 */     this.increaseButton = new PlasticArrowButton(orientation, this.scrollBarWidth, this.isFreeStanding);
/*  46: 87 */     return this.increaseButton;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void configureScrollBarColors()
/*  50:    */   {
/*  51: 92 */     super.configureScrollBarColors();
/*  52: 93 */     this.shadowColor = UIManager.getColor("ScrollBar.shadow");
/*  53: 94 */     this.highlightColor = UIManager.getColor("ScrollBar.highlight");
/*  54: 95 */     this.darkShadowColor = UIManager.getColor("ScrollBar.darkShadow");
/*  55: 96 */     this.thumbColor = UIManager.getColor("ScrollBar.thumb");
/*  56: 97 */     this.thumbShadow = UIManager.getColor("ScrollBar.thumbShadow");
/*  57: 98 */     this.thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight");
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
/*  61:    */   {
/*  62:103 */     g.translate(trackBounds.x, trackBounds.y);
/*  63:    */     
/*  64:105 */     boolean leftToRight = PlasticUtils.isLeftToRight(c);
/*  65:107 */     if (this.scrollbar.getOrientation() == 1)
/*  66:    */     {
/*  67:108 */       if (!this.isFreeStanding) {
/*  68:109 */         if (!leftToRight)
/*  69:    */         {
/*  70:110 */           trackBounds.width += 1;
/*  71:111 */           g.translate(-1, 0);
/*  72:    */         }
/*  73:    */         else
/*  74:    */         {
/*  75:113 */           trackBounds.width += 2;
/*  76:    */         }
/*  77:    */       }
/*  78:117 */       if (c.isEnabled())
/*  79:    */       {
/*  80:118 */         g.setColor(this.darkShadowColor);
/*  81:119 */         g.drawLine(0, 0, 0, trackBounds.height - 1);
/*  82:120 */         g.drawLine(trackBounds.width - 2, 0, trackBounds.width - 2, trackBounds.height - 1);
/*  83:121 */         g.drawLine(1, trackBounds.height - 1, trackBounds.width - 1, trackBounds.height - 1);
/*  84:122 */         g.drawLine(1, 0, trackBounds.width - 2, 0);
/*  85:    */         
/*  86:124 */         g.setColor(this.shadowColor);
/*  87:    */         
/*  88:126 */         g.drawLine(1, 1, 1, trackBounds.height - 2);
/*  89:127 */         g.drawLine(1, 1, trackBounds.width - 3, 1);
/*  90:128 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum())
/*  91:    */         {
/*  92:129 */           int y = this.thumbRect.y + this.thumbRect.height - trackBounds.y;
/*  93:130 */           g.drawLine(1, y, trackBounds.width - 1, y);
/*  94:    */         }
/*  95:132 */         g.setColor(this.highlightColor);
/*  96:133 */         g.drawLine(trackBounds.width - 1, 0, trackBounds.width - 1, trackBounds.height - 1);
/*  97:    */       }
/*  98:    */       else
/*  99:    */       {
/* 100:135 */         PlasticUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height);
/* 101:    */       }
/* 102:138 */       if (!this.isFreeStanding) {
/* 103:139 */         if (!leftToRight)
/* 104:    */         {
/* 105:140 */           trackBounds.width -= 1;
/* 106:141 */           g.translate(1, 0);
/* 107:    */         }
/* 108:    */         else
/* 109:    */         {
/* 110:143 */           trackBounds.width -= 2;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:147 */       if (!this.isFreeStanding) {
/* 117:148 */         trackBounds.height += 2;
/* 118:    */       }
/* 119:151 */       if (c.isEnabled())
/* 120:    */       {
/* 121:152 */         g.setColor(this.darkShadowColor);
/* 122:153 */         g.drawLine(0, 0, trackBounds.width - 1, 0);
/* 123:154 */         g.drawLine(0, 1, 0, trackBounds.height - 2);
/* 124:155 */         g.drawLine(0, trackBounds.height - 2, trackBounds.width - 1, trackBounds.height - 2);
/* 125:    */         
/* 126:157 */         g.drawLine(trackBounds.width - 1, 1, trackBounds.width - 1, trackBounds.height - 1);
/* 127:    */         
/* 128:    */ 
/* 129:160 */         g.setColor(this.shadowColor);
/* 130:    */         
/* 131:162 */         g.drawLine(1, 1, trackBounds.width - 2, 1);
/* 132:163 */         g.drawLine(1, 1, 1, trackBounds.height - 3);
/* 133:164 */         g.drawLine(0, trackBounds.height - 1, trackBounds.width - 1, trackBounds.height - 1);
/* 134:166 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum())
/* 135:    */         {
/* 136:167 */           int x = this.thumbRect.x + this.thumbRect.width - trackBounds.x;
/* 137:168 */           g.drawLine(x, 1, x, trackBounds.height - 1);
/* 138:    */         }
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:171 */         PlasticUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height);
/* 143:    */       }
/* 144:174 */       if (!this.isFreeStanding) {
/* 145:175 */         trackBounds.height -= 2;
/* 146:    */       }
/* 147:    */     }
/* 148:178 */     g.translate(-trackBounds.x, -trackBounds.y);
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
/* 152:    */   {
/* 153:183 */     if (!c.isEnabled()) {
/* 154:184 */       return;
/* 155:    */     }
/* 156:187 */     boolean leftToRight = PlasticUtils.isLeftToRight(c);
/* 157:    */     
/* 158:189 */     g.translate(thumbBounds.x, thumbBounds.y);
/* 159:191 */     if (this.scrollbar.getOrientation() == 1)
/* 160:    */     {
/* 161:192 */       if (!this.isFreeStanding) {
/* 162:193 */         if (!leftToRight)
/* 163:    */         {
/* 164:194 */           thumbBounds.width += 1;
/* 165:195 */           g.translate(-1, 0);
/* 166:    */         }
/* 167:    */         else
/* 168:    */         {
/* 169:197 */           thumbBounds.width += 2;
/* 170:    */         }
/* 171:    */       }
/* 172:202 */       g.setColor(this.thumbColor);
/* 173:203 */       g.fillRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
/* 174:    */       
/* 175:205 */       g.setColor(this.thumbShadow);
/* 176:206 */       g.drawRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
/* 177:    */       
/* 178:208 */       g.setColor(this.thumbHighlightColor);
/* 179:209 */       g.drawLine(1, 1, thumbBounds.width - 3, 1);
/* 180:210 */       g.drawLine(1, 1, 1, thumbBounds.height - 2);
/* 181:    */       
/* 182:212 */       paintBumps(g, c, 3, 4, thumbBounds.width - 6, thumbBounds.height - 7);
/* 183:214 */       if (!this.isFreeStanding) {
/* 184:215 */         if (!leftToRight)
/* 185:    */         {
/* 186:216 */           thumbBounds.width -= 1;
/* 187:217 */           g.translate(1, 0);
/* 188:    */         }
/* 189:    */         else
/* 190:    */         {
/* 191:219 */           thumbBounds.width -= 2;
/* 192:    */         }
/* 193:    */       }
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:223 */       if (!this.isFreeStanding) {
/* 198:224 */         thumbBounds.height += 2;
/* 199:    */       }
/* 200:227 */       g.setColor(this.thumbColor);
/* 201:228 */       g.fillRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);
/* 202:    */       
/* 203:230 */       g.setColor(this.thumbShadow);
/* 204:231 */       g.drawRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);
/* 205:    */       
/* 206:233 */       g.setColor(this.thumbHighlightColor);
/* 207:234 */       g.drawLine(1, 1, thumbBounds.width - 2, 1);
/* 208:235 */       g.drawLine(1, 1, 1, thumbBounds.height - 3);
/* 209:    */       
/* 210:237 */       paintBumps(g, c, 4, 3, thumbBounds.width - 7, thumbBounds.height - 6);
/* 211:239 */       if (!this.isFreeStanding) {
/* 212:240 */         thumbBounds.height -= 2;
/* 213:    */       }
/* 214:    */     }
/* 215:243 */     g.translate(-thumbBounds.x, -thumbBounds.y);
/* 216:245 */     if (PlasticUtils.is3D("ScrollBar.")) {
/* 217:246 */       paintThumb3D(g, thumbBounds);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   private void paintBumps(Graphics g, JComponent c, int x, int y, int width, int height)
/* 222:    */   {
/* 223:252 */     if (!useNarrowBumps())
/* 224:    */     {
/* 225:253 */       this.bumps.setBumpArea(width, height);
/* 226:254 */       this.bumps.paintIcon(c, g, x, y);
/* 227:    */     }
/* 228:    */     else
/* 229:    */     {
/* 230:256 */       int maxWidth = UIManager.getInt("ScrollBar.maxBumpsWidth");
/* 231:257 */       int myWidth = Math.min(maxWidth, width);
/* 232:258 */       int myHeight = Math.min(maxWidth, height);
/* 233:259 */       int myX = x + (width - myWidth) / 2;
/* 234:260 */       int myY = y + (height - myHeight) / 2;
/* 235:261 */       this.bumps.setBumpArea(myWidth, myHeight);
/* 236:262 */       this.bumps.paintIcon(c, g, myX, myY);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   private void paintThumb3D(Graphics g, Rectangle thumbBounds)
/* 241:    */   {
/* 242:268 */     boolean isHorizontal = this.scrollbar.getOrientation() == 0;
/* 243:269 */     int width = thumbBounds.width - (isHorizontal ? 3 : 1);
/* 244:270 */     int height = thumbBounds.height - (isHorizontal ? 1 : 3);
/* 245:271 */     Rectangle r = new Rectangle(thumbBounds.x + 2, thumbBounds.y + 2, width, height);
/* 246:272 */     PlasticUtils.addLight3DEffekt(g, r, isHorizontal);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private boolean useNarrowBumps()
/* 250:    */   {
/* 251:279 */     Object value = UIManager.get("ScrollBar.maxBumpsWidth");
/* 252:280 */     return (value != null) && ((value instanceof Integer));
/* 253:    */   }
/* 254:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticScrollBarUI
 * JD-Core Version:    0.7.0.1
 */