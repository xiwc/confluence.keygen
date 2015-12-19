/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Font;
/*   5:    */ import java.awt.FontMetrics;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.Insets;
/*   8:    */ import java.awt.Rectangle;
/*   9:    */ import javax.swing.Icon;
/*  10:    */ import javax.swing.JButton;
/*  11:    */ import javax.swing.JInternalFrame;
/*  12:    */ import javax.swing.SwingUtilities;
/*  13:    */ import javax.swing.plaf.metal.MetalInternalFrameTitlePane;
/*  14:    */ 
/*  15:    */ public final class PlasticInternalFrameTitlePane
/*  16:    */   extends MetalInternalFrameTitlePane
/*  17:    */ {
/*  18:    */   private PlasticBumps paletteBumps;
/*  19: 51 */   private final PlasticBumps activeBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlDarkShadow(), PlasticLookAndFeel.getPrimaryControl());
/*  20: 59 */   private final PlasticBumps inactiveBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getControlHighlight(), PlasticLookAndFeel.getControlDarkShadow(), PlasticLookAndFeel.getControl());
/*  21:    */   
/*  22:    */   public PlasticInternalFrameTitlePane(JInternalFrame frame)
/*  23:    */   {
/*  24: 68 */     super(frame);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void paintPalette(Graphics g)
/*  28:    */   {
/*  29: 72 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/*  30:    */     
/*  31: 74 */     int width = getWidth();
/*  32: 75 */     int height = getHeight();
/*  33: 77 */     if (this.paletteBumps == null) {
/*  34: 78 */       this.paletteBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlInfo(), PlasticLookAndFeel.getPrimaryControlShadow());
/*  35:    */     }
/*  36: 87 */     Color background = PlasticLookAndFeel.getPrimaryControlShadow();
/*  37: 88 */     Color darkShadow = PlasticLookAndFeel.getControlDarkShadow();
/*  38:    */     
/*  39: 90 */     g.setColor(background);
/*  40: 91 */     g.fillRect(0, 0, width, height);
/*  41:    */     
/*  42: 93 */     g.setColor(darkShadow);
/*  43: 94 */     g.drawLine(0, height - 1, width, height - 1);
/*  44:    */     
/*  45: 96 */     int buttonsWidth = getButtonsWidth();
/*  46: 97 */     int xOffset = leftToRight ? 4 : buttonsWidth + 4;
/*  47: 98 */     int bumpLength = width - buttonsWidth - 8;
/*  48: 99 */     int bumpHeight = getHeight() - 4;
/*  49:100 */     this.paletteBumps.setBumpArea(bumpLength, bumpHeight);
/*  50:101 */     this.paletteBumps.paintIcon(this, g, xOffset, 2);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void paintComponent(Graphics g)
/*  54:    */   {
/*  55:105 */     if (this.isPalette)
/*  56:    */     {
/*  57:106 */       paintPalette(g);
/*  58:107 */       return;
/*  59:    */     }
/*  60:110 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/*  61:111 */     boolean isSelected = this.frame.isSelected();
/*  62:    */     
/*  63:113 */     int width = getWidth();
/*  64:114 */     int height = getHeight();
/*  65:    */     
/*  66:116 */     Color background = null;
/*  67:117 */     Color foreground = null;
/*  68:118 */     Color shadow = null;
/*  69:    */     PlasticBumps bumps;
/*  70:    */     PlasticBumps bumps;
/*  71:122 */     if (isSelected)
/*  72:    */     {
/*  73:123 */       background = PlasticLookAndFeel.getWindowTitleBackground();
/*  74:124 */       foreground = PlasticLookAndFeel.getWindowTitleForeground();
/*  75:125 */       bumps = this.activeBumps;
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79:127 */       background = PlasticLookAndFeel.getWindowTitleInactiveBackground();
/*  80:128 */       foreground = PlasticLookAndFeel.getWindowTitleInactiveForeground();
/*  81:129 */       bumps = this.inactiveBumps;
/*  82:    */     }
/*  83:132 */     shadow = PlasticLookAndFeel.getControlDarkShadow();
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:165 */     g.setColor(background);
/* 117:166 */     g.fillRect(0, 0, width, height);
/* 118:    */     
/* 119:168 */     g.setColor(shadow);
/* 120:169 */     g.drawLine(0, height - 1, width, height - 1);
/* 121:170 */     g.drawLine(0, 0, 0, 0);
/* 122:171 */     g.drawLine(width - 1, 0, width - 1, 0);
/* 123:    */     
/* 124:173 */     int titleLength = 0;
/* 125:174 */     int xOffset = leftToRight ? 5 : width - 5;
/* 126:175 */     String frameTitle = this.frame.getTitle();
/* 127:    */     
/* 128:177 */     Icon icon = this.frame.getFrameIcon();
/* 129:178 */     if (icon != null)
/* 130:    */     {
/* 131:179 */       if (!leftToRight) {
/* 132:180 */         xOffset -= icon.getIconWidth();
/* 133:    */       }
/* 134:181 */       int iconY = height / 2 - icon.getIconHeight() / 2;
/* 135:182 */       icon.paintIcon(this.frame, g, xOffset, iconY);
/* 136:183 */       xOffset += (leftToRight ? icon.getIconWidth() + 5 : -5);
/* 137:    */     }
/* 138:186 */     if (frameTitle != null)
/* 139:    */     {
/* 140:187 */       Font f = getFont();
/* 141:188 */       g.setFont(f);
/* 142:189 */       FontMetrics fm = g.getFontMetrics();
/* 143:    */       
/* 144:    */ 
/* 145:192 */       g.setColor(foreground);
/* 146:    */       
/* 147:194 */       int yOffset = (height - fm.getHeight()) / 2 + fm.getAscent();
/* 148:    */       
/* 149:196 */       Rectangle rect = new Rectangle(0, 0, 0, 0);
/* 150:197 */       if (this.frame.isIconifiable()) {
/* 151:198 */         rect = this.iconButton.getBounds();
/* 152:199 */       } else if (this.frame.isMaximizable()) {
/* 153:200 */         rect = this.maxButton.getBounds();
/* 154:201 */       } else if (this.frame.isClosable()) {
/* 155:202 */         rect = this.closeButton.getBounds();
/* 156:    */       }
/* 157:206 */       if (leftToRight)
/* 158:    */       {
/* 159:207 */         if (rect.x == 0) {
/* 160:208 */           rect.x = (this.frame.getWidth() - this.frame.getInsets().right - 2);
/* 161:    */         }
/* 162:210 */         int titleW = rect.x - xOffset - 4;
/* 163:211 */         frameTitle = getTitle(frameTitle, fm, titleW);
/* 164:    */       }
/* 165:    */       else
/* 166:    */       {
/* 167:213 */         int titleW = xOffset - rect.x - rect.width - 4;
/* 168:214 */         frameTitle = getTitle(frameTitle, fm, titleW);
/* 169:215 */         xOffset -= SwingUtilities.computeStringWidth(fm, frameTitle);
/* 170:    */       }
/* 171:218 */       titleLength = SwingUtilities.computeStringWidth(fm, frameTitle);
/* 172:219 */       g.drawString(frameTitle, xOffset, yOffset);
/* 173:220 */       xOffset += (leftToRight ? titleLength + 5 : -5);
/* 174:    */     }
/* 175:225 */     int buttonsWidth = getButtonsWidth();
/* 176:    */     int bumpXOffset;
/* 177:    */     int bumpLength;
/* 178:    */     int bumpXOffset;
/* 179:226 */     if (leftToRight)
/* 180:    */     {
/* 181:227 */       int bumpLength = width - buttonsWidth - xOffset - 5;
/* 182:228 */       bumpXOffset = xOffset;
/* 183:    */     }
/* 184:    */     else
/* 185:    */     {
/* 186:230 */       bumpLength = xOffset - buttonsWidth - 5;
/* 187:231 */       bumpXOffset = buttonsWidth + 5;
/* 188:    */     }
/* 189:233 */     int bumpYOffset = 3;
/* 190:234 */     int bumpHeight = getHeight() - 2 * bumpYOffset;
/* 191:235 */     bumps.setBumpArea(bumpLength, bumpHeight);
/* 192:236 */     bumps.paintIcon(this, g, bumpXOffset, bumpYOffset);
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected String getTitle(String text, FontMetrics fm, int availTextWidth)
/* 196:    */   {
/* 197:243 */     if ((text == null) || (text.equals(""))) {
/* 198:244 */       return "";
/* 199:    */     }
/* 200:245 */     int textWidth = SwingUtilities.computeStringWidth(fm, text);
/* 201:246 */     String clipString = "…";
/* 202:247 */     if (textWidth > availTextWidth)
/* 203:    */     {
/* 204:248 */       int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
/* 205:250 */       for (int nChars = 0; nChars < text.length(); nChars++)
/* 206:    */       {
/* 207:251 */         totalWidth += fm.charWidth(text.charAt(nChars));
/* 208:252 */         if (totalWidth > availTextWidth) {
/* 209:    */           break;
/* 210:    */         }
/* 211:    */       }
/* 212:256 */       text = text.substring(0, nChars) + clipString;
/* 213:    */     }
/* 214:258 */     return text;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private int getButtonsWidth()
/* 218:    */   {
/* 219:262 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/* 220:    */     
/* 221:264 */     int w = getWidth();
/* 222:265 */     int x = leftToRight ? w : 0;
/* 223:    */     
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:270 */     int buttonWidth = this.closeButton.getIcon().getIconWidth();
/* 228:272 */     if (this.frame.isClosable()) {
/* 229:273 */       if (this.isPalette)
/* 230:    */       {
/* 231:274 */         int spacing = 3;
/* 232:275 */         x += (leftToRight ? -spacing - (buttonWidth + 2) : spacing);
/* 233:276 */         if (!leftToRight) {
/* 234:277 */           x += buttonWidth + 2;
/* 235:    */         }
/* 236:    */       }
/* 237:    */       else
/* 238:    */       {
/* 239:279 */         int spacing = 4;
/* 240:280 */         x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 241:281 */         if (!leftToRight) {
/* 242:282 */           x += buttonWidth;
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:286 */     if ((this.frame.isMaximizable()) && (!this.isPalette))
/* 247:    */     {
/* 248:287 */       int spacing = this.frame.isClosable() ? 10 : 4;
/* 249:288 */       x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 250:289 */       if (!leftToRight) {
/* 251:290 */         x += buttonWidth;
/* 252:    */       }
/* 253:    */     }
/* 254:293 */     if ((this.frame.isIconifiable()) && (!this.isPalette))
/* 255:    */     {
/* 256:294 */       int spacing = this.frame.isClosable() ? 10 : this.frame.isMaximizable() ? 2 : 4;
/* 257:    */       
/* 258:296 */       x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 259:297 */       if (!leftToRight) {
/* 260:298 */         x += buttonWidth;
/* 261:    */       }
/* 262:    */     }
/* 263:301 */     return leftToRight ? w - x : x;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticInternalFrameTitlePane
 * JD-Core Version:    0.7.0.1
 */