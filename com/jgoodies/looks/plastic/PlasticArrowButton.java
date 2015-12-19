/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.UIManager;
/*   9:    */ import javax.swing.plaf.metal.MetalScrollButton;
/*  10:    */ 
/*  11:    */ class PlasticArrowButton
/*  12:    */   extends MetalScrollButton
/*  13:    */ {
/*  14:    */   private final Color shadowColor;
/*  15:    */   private final Color highlightColor;
/*  16:    */   protected boolean isFreeStanding;
/*  17:    */   
/*  18:    */   public PlasticArrowButton(int direction, int width, boolean freeStanding)
/*  19:    */   {
/*  20: 56 */     super(direction, width, freeStanding);
/*  21: 57 */     this.shadowColor = UIManager.getColor("ScrollBar.darkShadow");
/*  22: 58 */     this.highlightColor = UIManager.getColor("ScrollBar.highlight");
/*  23: 59 */     this.isFreeStanding = freeStanding;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setFreeStanding(boolean freeStanding)
/*  27:    */   {
/*  28: 64 */     super.setFreeStanding(freeStanding);
/*  29: 65 */     this.isFreeStanding = freeStanding;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void paint(Graphics g)
/*  33:    */   {
/*  34: 70 */     boolean leftToRight = PlasticUtils.isLeftToRight(this);
/*  35: 71 */     boolean isEnabled = getParent().isEnabled();
/*  36: 72 */     boolean isPressed = getModel().isPressed();
/*  37:    */     
/*  38: 74 */     Color arrowColor = isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled();
/*  39:    */     
/*  40:    */ 
/*  41: 77 */     int width = getWidth();
/*  42: 78 */     int height = getHeight();
/*  43: 79 */     int w = width;
/*  44: 80 */     int h = height;
/*  45: 81 */     int arrowHeight = calculateArrowHeight(height, width);
/*  46: 82 */     int arrowOffset = calculateArrowOffset();
/*  47: 83 */     boolean paintNorthBottom = isPaintingNorthBottom();
/*  48:    */     
/*  49: 85 */     g.setColor(isPressed ? PlasticLookAndFeel.getControlShadow() : getBackground());
/*  50: 86 */     g.fillRect(0, 0, width, height);
/*  51: 88 */     if (getDirection() == 1) {
/*  52: 89 */       paintNorth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset, paintNorthBottom);
/*  53: 91 */     } else if (getDirection() == 5) {
/*  54: 92 */       paintSouth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset);
/*  55: 94 */     } else if (getDirection() == 3) {
/*  56: 95 */       paintEast(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
/*  57: 97 */     } else if (getDirection() == 7) {
/*  58: 98 */       paintWest(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
/*  59:    */     }
/*  60:101 */     if (PlasticUtils.is3D("ScrollBar.")) {
/*  61:102 */       paint3D(g);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected int calculateArrowHeight(int height, int width)
/*  66:    */   {
/*  67:114 */     return (height + 1) / 4;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected int calculateArrowOffset()
/*  71:    */   {
/*  72:118 */     return 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected boolean isPaintingNorthBottom()
/*  76:    */   {
/*  77:122 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void paintWest(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight)
/*  81:    */   {
/*  82:129 */     if (!this.isFreeStanding)
/*  83:    */     {
/*  84:130 */       height += 2;
/*  85:131 */       width++;
/*  86:132 */       g.translate(-1, 0);
/*  87:    */     }
/*  88:136 */     g.setColor(arrowColor);
/*  89:    */     
/*  90:138 */     int startX = (w + 1 - arrowHeight) / 2;
/*  91:139 */     int startY = h / 2;
/*  92:141 */     for (int line = 0; line < arrowHeight; line++) {
/*  93:142 */       g.drawLine(startX + line, startY - line, startX + line, startY + line + 1);
/*  94:    */     }
/*  95:149 */     if (isEnabled)
/*  96:    */     {
/*  97:150 */       g.setColor(this.highlightColor);
/*  98:152 */       if (!isPressed)
/*  99:    */       {
/* 100:153 */         g.drawLine(1, 1, width - 1, 1);
/* 101:154 */         g.drawLine(1, 1, 1, height - 3);
/* 102:    */       }
/* 103:156 */       g.drawLine(1, height - 1, width - 1, height - 1);
/* 104:    */       
/* 105:158 */       g.setColor(this.shadowColor);
/* 106:159 */       g.drawLine(0, 0, width - 1, 0);
/* 107:160 */       g.drawLine(0, 0, 0, height - 2);
/* 108:161 */       g.drawLine(1, height - 2, width - 1, height - 2);
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:163 */       PlasticUtils.drawDisabledBorder(g, 0, 0, width + 1, height);
/* 113:    */     }
/* 114:166 */     if (!this.isFreeStanding)
/* 115:    */     {
/* 116:167 */       height -= 2;
/* 117:168 */       width--;
/* 118:169 */       g.translate(1, 0);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void paintEast(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight)
/* 123:    */   {
/* 124:176 */     if (!this.isFreeStanding)
/* 125:    */     {
/* 126:177 */       height += 2;
/* 127:178 */       width++;
/* 128:    */     }
/* 129:182 */     g.setColor(arrowColor);
/* 130:    */     
/* 131:184 */     int startX = (w + 1 - arrowHeight) / 2 + arrowHeight - 1;
/* 132:185 */     int startY = h / 2;
/* 133:186 */     for (int line = 0; line < arrowHeight; line++) {
/* 134:187 */       g.drawLine(startX - line, startY - line, startX - line, startY + line + 1);
/* 135:    */     }
/* 136:194 */     if (isEnabled)
/* 137:    */     {
/* 138:195 */       g.setColor(this.highlightColor);
/* 139:196 */       if (!isPressed)
/* 140:    */       {
/* 141:197 */         g.drawLine(0, 1, width - 3, 1);
/* 142:198 */         g.drawLine(0, 1, 0, height - 3);
/* 143:    */       }
/* 144:200 */       g.drawLine(width - 1, 1, width - 1, height - 1);
/* 145:201 */       g.drawLine(0, height - 1, width - 1, height - 1);
/* 146:    */       
/* 147:203 */       g.setColor(this.shadowColor);
/* 148:204 */       g.drawLine(0, 0, width - 2, 0);
/* 149:205 */       g.drawLine(width - 2, 1, width - 2, height - 2);
/* 150:206 */       g.drawLine(0, height - 2, width - 2, height - 2);
/* 151:    */     }
/* 152:    */     else
/* 153:    */     {
/* 154:208 */       PlasticUtils.drawDisabledBorder(g, -1, 0, width + 1, height);
/* 155:    */     }
/* 156:210 */     if (!this.isFreeStanding)
/* 157:    */     {
/* 158:211 */       height -= 2;
/* 159:212 */       width--;
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected void paintSouth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset)
/* 164:    */   {
/* 165:221 */     if (!this.isFreeStanding)
/* 166:    */     {
/* 167:222 */       height++;
/* 168:223 */       if (!leftToRight)
/* 169:    */       {
/* 170:224 */         width++;
/* 171:225 */         g.translate(-1, 0);
/* 172:    */       }
/* 173:    */       else
/* 174:    */       {
/* 175:227 */         width += 2;
/* 176:    */       }
/* 177:    */     }
/* 178:232 */     g.setColor(arrowColor);
/* 179:    */     
/* 180:234 */     int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 1;
/* 181:235 */     int startX = w / 2;
/* 182:239 */     for (int line = 0; line < arrowHeight; line++) {
/* 183:240 */       g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1);
/* 184:    */     }
/* 185:243 */     if (isEnabled)
/* 186:    */     {
/* 187:244 */       g.setColor(this.highlightColor);
/* 188:245 */       if (!isPressed)
/* 189:    */       {
/* 190:246 */         g.drawLine(1, 0, width - 3, 0);
/* 191:247 */         g.drawLine(1, 0, 1, height - 3);
/* 192:    */       }
/* 193:249 */       g.drawLine(0, height - 1, width - 1, height - 1);
/* 194:250 */       g.drawLine(width - 1, 0, width - 1, height - 1);
/* 195:    */       
/* 196:252 */       g.setColor(this.shadowColor);
/* 197:253 */       g.drawLine(0, 0, 0, height - 2);
/* 198:254 */       g.drawLine(width - 2, 0, width - 2, height - 2);
/* 199:255 */       g.drawLine(1, height - 2, width - 2, height - 2);
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:257 */       PlasticUtils.drawDisabledBorder(g, 0, -1, width, height + 1);
/* 204:    */     }
/* 205:260 */     if (!this.isFreeStanding)
/* 206:    */     {
/* 207:261 */       height--;
/* 208:262 */       if (!leftToRight)
/* 209:    */       {
/* 210:263 */         width--;
/* 211:264 */         g.translate(1, 0);
/* 212:    */       }
/* 213:    */       else
/* 214:    */       {
/* 215:266 */         width -= 2;
/* 216:    */       }
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected void paintNorth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset, boolean paintBottom)
/* 221:    */   {
/* 222:276 */     if (!this.isFreeStanding)
/* 223:    */     {
/* 224:277 */       height++;
/* 225:278 */       g.translate(0, -1);
/* 226:279 */       if (!leftToRight)
/* 227:    */       {
/* 228:280 */         width++;
/* 229:281 */         g.translate(-1, 0);
/* 230:    */       }
/* 231:    */       else
/* 232:    */       {
/* 233:283 */         width += 2;
/* 234:    */       }
/* 235:    */     }
/* 236:288 */     g.setColor(arrowColor);
/* 237:289 */     int startY = (h + 1 - arrowHeight) / 2;
/* 238:290 */     int startX = w / 2;
/* 239:292 */     for (int line = 0; line < arrowHeight; line++) {
/* 240:293 */       g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1);
/* 241:    */     }
/* 242:296 */     if (isEnabled)
/* 243:    */     {
/* 244:297 */       g.setColor(this.highlightColor);
/* 245:299 */       if (!isPressed)
/* 246:    */       {
/* 247:300 */         g.drawLine(1, 1, width - 3, 1);
/* 248:301 */         g.drawLine(1, 1, 1, height - 1);
/* 249:    */       }
/* 250:304 */       g.drawLine(width - 1, 1, width - 1, height - 1);
/* 251:    */       
/* 252:306 */       g.setColor(this.shadowColor);
/* 253:307 */       g.drawLine(0, 0, width - 2, 0);
/* 254:308 */       g.drawLine(0, 0, 0, height - 1);
/* 255:309 */       g.drawLine(width - 2, 1, width - 2, height - 1);
/* 256:310 */       if (paintBottom) {
/* 257:311 */         g.fillRect(0, height - 1, width - 1, 1);
/* 258:    */       }
/* 259:    */     }
/* 260:    */     else
/* 261:    */     {
/* 262:314 */       PlasticUtils.drawDisabledBorder(g, 0, 0, width, height + 1);
/* 263:315 */       if (paintBottom)
/* 264:    */       {
/* 265:316 */         g.setColor(PlasticLookAndFeel.getControlShadow());
/* 266:317 */         g.fillRect(0, height - 1, width - 1, 1);
/* 267:    */       }
/* 268:    */     }
/* 269:320 */     if (!this.isFreeStanding)
/* 270:    */     {
/* 271:321 */       height--;
/* 272:322 */       g.translate(0, 1);
/* 273:323 */       if (!leftToRight)
/* 274:    */       {
/* 275:324 */         width--;
/* 276:325 */         g.translate(1, 0);
/* 277:    */       }
/* 278:    */       else
/* 279:    */       {
/* 280:327 */         width -= 2;
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   private void paint3D(Graphics g)
/* 286:    */   {
/* 287:334 */     ButtonModel buttonModel = getModel();
/* 288:335 */     if (((buttonModel.isArmed()) && (buttonModel.isPressed())) || (buttonModel.isSelected())) {
/* 289:336 */       return;
/* 290:    */     }
/* 291:338 */     int width = getWidth();
/* 292:339 */     int height = getHeight();
/* 293:340 */     if (getDirection() == 3) {
/* 294:341 */       width -= 2;
/* 295:342 */     } else if (getDirection() == 5) {
/* 296:343 */       height -= 2;
/* 297:    */     }
/* 298:345 */     Rectangle r = new Rectangle(1, 1, width, height);
/* 299:346 */     boolean isHorizontal = (getDirection() == 3) || (getDirection() == 7);
/* 300:347 */     PlasticUtils.addLight3DEffekt(g, r, isHorizontal);
/* 301:    */   }
/* 302:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticArrowButton
 * JD-Core Version:    0.7.0.1
 */