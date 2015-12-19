/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.AWTException;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Panel;
/*   9:    */ import java.awt.Point;
/*  10:    */ import java.awt.Rectangle;
/*  11:    */ import java.awt.Robot;
/*  12:    */ import java.awt.Window;
/*  13:    */ import java.awt.image.BufferedImage;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.swing.JApplet;
/*  17:    */ import javax.swing.JComboBox;
/*  18:    */ import javax.swing.JComponent;
/*  19:    */ import javax.swing.JInternalFrame;
/*  20:    */ import javax.swing.JRootPane;
/*  21:    */ import javax.swing.JWindow;
/*  22:    */ import javax.swing.Popup;
/*  23:    */ import javax.swing.SwingUtilities;
/*  24:    */ import javax.swing.border.Border;
/*  25:    */ 
/*  26:    */ public final class ShadowPopup
/*  27:    */   extends Popup
/*  28:    */ {
/*  29:    */   private static final int MAX_CACHE_SIZE = 5;
/*  30:    */   private static List cache;
/*  31: 68 */   private static final Border SHADOW_BORDER = ;
/*  32:    */   private static final int SHADOW_SIZE = 5;
/*  33: 78 */   private static boolean canSnapshot = true;
/*  34:    */   private Component owner;
/*  35:    */   private Component contents;
/*  36:    */   private int x;
/*  37:    */   private int y;
/*  38:    */   private Popup popup;
/*  39:    */   private Border oldBorder;
/*  40:    */   private boolean oldOpaque;
/*  41:    */   private Container heavyWeightContainer;
/*  42:    */   
/*  43:    */   static Popup getInstance(Component owner, Component contents, int x, int y, Popup delegate)
/*  44:    */   {
/*  45:    */     ShadowPopup result;
/*  46:123 */     synchronized (ShadowPopup.class)
/*  47:    */     {
/*  48:124 */       if (cache == null) {
/*  49:125 */         cache = new ArrayList(5);
/*  50:    */       }
/*  51:    */       ShadowPopup result;
/*  52:127 */       if (cache.size() > 0) {
/*  53:128 */         result = (ShadowPopup)cache.remove(0);
/*  54:    */       } else {
/*  55:130 */         result = new ShadowPopup();
/*  56:    */       }
/*  57:    */     }
/*  58:133 */     result.reset(owner, contents, x, y, delegate);
/*  59:134 */     return result;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static void recycle(ShadowPopup popup)
/*  63:    */   {
/*  64:141 */     synchronized (ShadowPopup.class)
/*  65:    */     {
/*  66:142 */       if (cache.size() < 5) {
/*  67:143 */         cache.add(popup);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static boolean canSnapshot()
/*  73:    */   {
/*  74:149 */     return canSnapshot;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void hide()
/*  78:    */   {
/*  79:164 */     if (this.contents == null) {
/*  80:165 */       return;
/*  81:    */     }
/*  82:167 */     JComponent parent = (JComponent)this.contents.getParent();
/*  83:168 */     this.popup.hide();
/*  84:169 */     if ((parent != null) && (parent.getBorder() == SHADOW_BORDER))
/*  85:    */     {
/*  86:170 */       parent.setBorder(this.oldBorder);
/*  87:171 */       parent.setOpaque(this.oldOpaque);
/*  88:172 */       this.oldBorder = null;
/*  89:173 */       if (this.heavyWeightContainer != null)
/*  90:    */       {
/*  91:174 */         parent.putClientProperty("jgoodies.hShadowBg", null);
/*  92:175 */         parent.putClientProperty("jgoodies.vShadowBg", null);
/*  93:176 */         this.heavyWeightContainer = null;
/*  94:    */       }
/*  95:    */     }
/*  96:179 */     this.owner = null;
/*  97:180 */     this.contents = null;
/*  98:181 */     this.popup = null;
/*  99:182 */     recycle(this);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void show()
/* 103:    */   {
/* 104:191 */     if (this.heavyWeightContainer != null) {
/* 105:192 */       snapshot();
/* 106:    */     }
/* 107:194 */     this.popup.show();
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void reset(Component owner, Component contents, int x, int y, Popup popup)
/* 111:    */   {
/* 112:208 */     this.owner = owner;
/* 113:209 */     this.contents = contents;
/* 114:210 */     this.popup = popup;
/* 115:211 */     this.x = x;
/* 116:212 */     this.y = y;
/* 117:213 */     if ((owner instanceof JComboBox)) {
/* 118:214 */       return;
/* 119:    */     }
/* 120:219 */     Dimension contentsPrefSize = contents.getPreferredSize();
/* 121:220 */     if ((contentsPrefSize.width <= 0) || (contentsPrefSize.height <= 0)) {
/* 122:221 */       return;
/* 123:    */     }
/* 124:223 */     for (Container p = contents.getParent(); p != null; p = p.getParent()) {
/* 125:224 */       if (((p instanceof JWindow)) || ((p instanceof Panel)))
/* 126:    */       {
/* 127:226 */         p.setBackground(contents.getBackground());
/* 128:227 */         this.heavyWeightContainer = p;
/* 129:228 */         break;
/* 130:    */       }
/* 131:    */     }
/* 132:231 */     JComponent parent = (JComponent)contents.getParent();
/* 133:232 */     this.oldOpaque = parent.isOpaque();
/* 134:233 */     this.oldBorder = parent.getBorder();
/* 135:234 */     parent.setOpaque(false);
/* 136:235 */     parent.setBorder(SHADOW_BORDER);
/* 137:237 */     if (this.heavyWeightContainer != null) {
/* 138:238 */       this.heavyWeightContainer.setSize(this.heavyWeightContainer.getPreferredSize());
/* 139:    */     } else {
/* 140:241 */       parent.setSize(parent.getPreferredSize());
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:251 */   private static final Point POINT = new Point();
/* 145:252 */   private static final Rectangle RECT = new Rectangle();
/* 146:    */   
/* 147:    */   private void snapshot()
/* 148:    */   {
/* 149:    */     try
/* 150:    */     {
/* 151:271 */       Dimension size = this.heavyWeightContainer.getPreferredSize();
/* 152:272 */       int width = size.width;
/* 153:273 */       int height = size.height;
/* 154:277 */       if ((width <= 0) || (height <= 5)) {
/* 155:278 */         return;
/* 156:    */       }
/* 157:281 */       Robot robot = new Robot();
/* 158:    */       
/* 159:283 */       RECT.setBounds(this.x, this.y + height - 5, width, 5);
/* 160:284 */       BufferedImage hShadowBg = robot.createScreenCapture(RECT);
/* 161:    */       
/* 162:286 */       RECT.setBounds(this.x + width - 5, this.y, 5, height - 5);
/* 163:    */       
/* 164:288 */       BufferedImage vShadowBg = robot.createScreenCapture(RECT);
/* 165:    */       
/* 166:290 */       JComponent parent = (JComponent)this.contents.getParent();
/* 167:291 */       parent.putClientProperty("jgoodies.hShadowBg", hShadowBg);
/* 168:292 */       parent.putClientProperty("jgoodies.vShadowBg", vShadowBg);
/* 169:    */       
/* 170:294 */       Container layeredPane = getLayeredPane();
/* 171:295 */       if (layeredPane == null) {
/* 172:297 */         return;
/* 173:    */       }
/* 174:300 */       int layeredPaneWidth = layeredPane.getWidth();
/* 175:301 */       int layeredPaneHeight = layeredPane.getHeight();
/* 176:    */       
/* 177:303 */       POINT.x = this.x;
/* 178:304 */       POINT.y = this.y;
/* 179:305 */       SwingUtilities.convertPointFromScreen(POINT, layeredPane);
/* 180:    */       
/* 181:    */ 
/* 182:308 */       RECT.x = POINT.x;
/* 183:309 */       RECT.y = (POINT.y + height - 5);
/* 184:310 */       RECT.width = width;
/* 185:311 */       RECT.height = 5;
/* 186:313 */       if (RECT.x + RECT.width > layeredPaneWidth) {
/* 187:314 */         RECT.width = (layeredPaneWidth - RECT.x);
/* 188:    */       }
/* 189:316 */       if (RECT.y + RECT.height > layeredPaneHeight) {
/* 190:317 */         RECT.height = (layeredPaneHeight - RECT.y);
/* 191:    */       }
/* 192:319 */       if (!RECT.isEmpty())
/* 193:    */       {
/* 194:320 */         Graphics g = hShadowBg.createGraphics();
/* 195:321 */         g.translate(-RECT.x, -RECT.y);
/* 196:322 */         g.setClip(RECT);
/* 197:323 */         if ((layeredPane instanceof JComponent))
/* 198:    */         {
/* 199:324 */           JComponent c = (JComponent)layeredPane;
/* 200:325 */           boolean doubleBuffered = c.isDoubleBuffered();
/* 201:326 */           c.setDoubleBuffered(false);
/* 202:327 */           c.paintAll(g);
/* 203:328 */           c.setDoubleBuffered(doubleBuffered);
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:330 */           layeredPane.paintAll(g);
/* 208:    */         }
/* 209:332 */         g.dispose();
/* 210:    */       }
/* 211:336 */       RECT.x = (POINT.x + width - 5);
/* 212:337 */       RECT.y = POINT.y;
/* 213:338 */       RECT.width = 5;
/* 214:339 */       RECT.height = (height - 5);
/* 215:341 */       if (RECT.x + RECT.width > layeredPaneWidth) {
/* 216:342 */         RECT.width = (layeredPaneWidth - RECT.x);
/* 217:    */       }
/* 218:344 */       if (RECT.y + RECT.height > layeredPaneHeight) {
/* 219:345 */         RECT.height = (layeredPaneHeight - RECT.y);
/* 220:    */       }
/* 221:347 */       if (!RECT.isEmpty())
/* 222:    */       {
/* 223:348 */         Graphics g = vShadowBg.createGraphics();
/* 224:349 */         g.translate(-RECT.x, -RECT.y);
/* 225:350 */         g.setClip(RECT);
/* 226:351 */         if ((layeredPane instanceof JComponent))
/* 227:    */         {
/* 228:352 */           JComponent c = (JComponent)layeredPane;
/* 229:353 */           boolean doubleBuffered = c.isDoubleBuffered();
/* 230:354 */           c.setDoubleBuffered(false);
/* 231:355 */           c.paintAll(g);
/* 232:356 */           c.setDoubleBuffered(doubleBuffered);
/* 233:    */         }
/* 234:    */         else
/* 235:    */         {
/* 236:358 */           layeredPane.paintAll(g);
/* 237:    */         }
/* 238:360 */         g.dispose();
/* 239:    */       }
/* 240:    */     }
/* 241:    */     catch (AWTException e)
/* 242:    */     {
/* 243:363 */       canSnapshot = false;
/* 244:    */     }
/* 245:    */     catch (SecurityException e)
/* 246:    */     {
/* 247:365 */       canSnapshot = false;
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   private Container getLayeredPane()
/* 252:    */   {
/* 253:374 */     Container parent = null;
/* 254:375 */     if (this.owner != null) {
/* 255:376 */       parent = (this.owner instanceof Container) ? (Container)this.owner : this.owner.getParent();
/* 256:    */     }
/* 257:381 */     for (Container p = parent; p != null; p = p.getParent()) {
/* 258:382 */       if ((p instanceof JRootPane))
/* 259:    */       {
/* 260:383 */         if (!(p.getParent() instanceof JInternalFrame)) {
/* 261:386 */           parent = ((JRootPane)p).getLayeredPane();
/* 262:    */         }
/* 263:    */       }
/* 264:389 */       else if ((p instanceof Window))
/* 265:    */       {
/* 266:390 */         if (parent == null) {
/* 267:391 */           parent = p;
/* 268:    */         }
/* 269:    */       }
/* 270:    */       else {
/* 271:394 */         if ((p instanceof JApplet)) {
/* 272:    */           break;
/* 273:    */         }
/* 274:    */       }
/* 275:    */     }
/* 276:401 */     return parent;
/* 277:    */   }
/* 278:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ShadowPopup
 * JD-Core Version:    0.7.0.1
 */