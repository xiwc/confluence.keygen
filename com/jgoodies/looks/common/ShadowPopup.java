/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Robot;
/*     */ import java.awt.Window;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.Popup;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
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
/*     */ public final class ShadowPopup
/*     */   extends Popup
/*     */ {
/*     */   private static final int MAX_CACHE_SIZE = 5;
/*     */   private static List cache;
/*  68 */   private static final Border SHADOW_BORDER = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int SHADOW_SIZE = 5;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   private static boolean canSnapshot = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Component owner;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Component contents;
/*     */   
/*     */ 
/*     */ 
/*     */   private int x;
/*     */   
/*     */ 
/*     */ 
/*     */   private int y;
/*     */   
/*     */ 
/*     */ 
/*     */   private Popup popup;
/*     */   
/*     */ 
/*     */ 
/*     */   private Border oldBorder;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean oldOpaque;
/*     */   
/*     */ 
/*     */ 
/*     */   private Container heavyWeightContainer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Popup getInstance(Component owner, Component contents, int x, int y, Popup delegate)
/*     */   {
/*     */     ShadowPopup result;
/*     */     
/*     */ 
/*     */ 
/* 123 */     synchronized (ShadowPopup.class) {
/* 124 */       if (cache == null)
/* 125 */         cache = new ArrayList(5);
/*     */       ShadowPopup result;
/* 127 */       if (cache.size() > 0) {
/* 128 */         result = (ShadowPopup)cache.remove(0);
/*     */       } else {
/* 130 */         result = new ShadowPopup();
/*     */       }
/*     */     }
/* 133 */     result.reset(owner, contents, x, y, delegate);
/* 134 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void recycle(ShadowPopup popup)
/*     */   {
/* 141 */     synchronized (ShadowPopup.class) {
/* 142 */       if (cache.size() < 5) {
/* 143 */         cache.add(popup);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean canSnapshot() {
/* 149 */     return canSnapshot;
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
/*     */   public void hide()
/*     */   {
/* 164 */     if (this.contents == null) {
/* 165 */       return;
/*     */     }
/* 167 */     JComponent parent = (JComponent)this.contents.getParent();
/* 168 */     this.popup.hide();
/* 169 */     if ((parent != null) && (parent.getBorder() == SHADOW_BORDER)) {
/* 170 */       parent.setBorder(this.oldBorder);
/* 171 */       parent.setOpaque(this.oldOpaque);
/* 172 */       this.oldBorder = null;
/* 173 */       if (this.heavyWeightContainer != null) {
/* 174 */         parent.putClientProperty("jgoodies.hShadowBg", null);
/* 175 */         parent.putClientProperty("jgoodies.vShadowBg", null);
/* 176 */         this.heavyWeightContainer = null;
/*     */       }
/*     */     }
/* 179 */     this.owner = null;
/* 180 */     this.contents = null;
/* 181 */     this.popup = null;
/* 182 */     recycle(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void show()
/*     */   {
/* 191 */     if (this.heavyWeightContainer != null) {
/* 192 */       snapshot();
/*     */     }
/* 194 */     this.popup.show();
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
/*     */   private void reset(Component owner, Component contents, int x, int y, Popup popup)
/*     */   {
/* 208 */     this.owner = owner;
/* 209 */     this.contents = contents;
/* 210 */     this.popup = popup;
/* 211 */     this.x = x;
/* 212 */     this.y = y;
/* 213 */     if ((owner instanceof JComboBox)) {
/* 214 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 219 */     Dimension contentsPrefSize = contents.getPreferredSize();
/* 220 */     if ((contentsPrefSize.width <= 0) || (contentsPrefSize.height <= 0)) {
/* 221 */       return;
/*     */     }
/* 223 */     for (Container p = contents.getParent(); p != null; p = p.getParent()) {
/* 224 */       if (((p instanceof JWindow)) || ((p instanceof Panel)))
/*     */       {
/* 226 */         p.setBackground(contents.getBackground());
/* 227 */         this.heavyWeightContainer = p;
/* 228 */         break;
/*     */       }
/*     */     }
/* 231 */     JComponent parent = (JComponent)contents.getParent();
/* 232 */     this.oldOpaque = parent.isOpaque();
/* 233 */     this.oldBorder = parent.getBorder();
/* 234 */     parent.setOpaque(false);
/* 235 */     parent.setBorder(SHADOW_BORDER);
/*     */     
/* 237 */     if (this.heavyWeightContainer != null) {
/* 238 */       this.heavyWeightContainer.setSize(this.heavyWeightContainer.getPreferredSize());
/*     */     }
/*     */     else {
/* 241 */       parent.setSize(parent.getPreferredSize());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 251 */   private static final Point POINT = new Point();
/* 252 */   private static final Rectangle RECT = new Rectangle();
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
/*     */   private void snapshot()
/*     */   {
/*     */     try
/*     */     {
/* 271 */       Dimension size = this.heavyWeightContainer.getPreferredSize();
/* 272 */       int width = size.width;
/* 273 */       int height = size.height;
/*     */       
/*     */ 
/*     */ 
/* 277 */       if ((width <= 0) || (height <= 5)) {
/* 278 */         return;
/*     */       }
/*     */       
/* 281 */       Robot robot = new Robot();
/*     */       
/* 283 */       RECT.setBounds(this.x, this.y + height - 5, width, 5);
/* 284 */       BufferedImage hShadowBg = robot.createScreenCapture(RECT);
/*     */       
/* 286 */       RECT.setBounds(this.x + width - 5, this.y, 5, height - 5);
/*     */       
/* 288 */       BufferedImage vShadowBg = robot.createScreenCapture(RECT);
/*     */       
/* 290 */       JComponent parent = (JComponent)this.contents.getParent();
/* 291 */       parent.putClientProperty("jgoodies.hShadowBg", hShadowBg);
/* 292 */       parent.putClientProperty("jgoodies.vShadowBg", vShadowBg);
/*     */       
/* 294 */       Container layeredPane = getLayeredPane();
/* 295 */       if (layeredPane == null)
/*     */       {
/* 297 */         return;
/*     */       }
/*     */       
/* 300 */       int layeredPaneWidth = layeredPane.getWidth();
/* 301 */       int layeredPaneHeight = layeredPane.getHeight();
/*     */       
/* 303 */       POINT.x = this.x;
/* 304 */       POINT.y = this.y;
/* 305 */       SwingUtilities.convertPointFromScreen(POINT, layeredPane);
/*     */       
/*     */ 
/* 308 */       RECT.x = POINT.x;
/* 309 */       RECT.y = (POINT.y + height - 5);
/* 310 */       RECT.width = width;
/* 311 */       RECT.height = 5;
/*     */       
/* 313 */       if (RECT.x + RECT.width > layeredPaneWidth) {
/* 314 */         RECT.width = (layeredPaneWidth - RECT.x);
/*     */       }
/* 316 */       if (RECT.y + RECT.height > layeredPaneHeight) {
/* 317 */         RECT.height = (layeredPaneHeight - RECT.y);
/*     */       }
/* 319 */       if (!RECT.isEmpty()) {
/* 320 */         Graphics g = hShadowBg.createGraphics();
/* 321 */         g.translate(-RECT.x, -RECT.y);
/* 322 */         g.setClip(RECT);
/* 323 */         if ((layeredPane instanceof JComponent)) {
/* 324 */           JComponent c = (JComponent)layeredPane;
/* 325 */           boolean doubleBuffered = c.isDoubleBuffered();
/* 326 */           c.setDoubleBuffered(false);
/* 327 */           c.paintAll(g);
/* 328 */           c.setDoubleBuffered(doubleBuffered);
/*     */         } else {
/* 330 */           layeredPane.paintAll(g);
/*     */         }
/* 332 */         g.dispose();
/*     */       }
/*     */       
/*     */ 
/* 336 */       RECT.x = (POINT.x + width - 5);
/* 337 */       RECT.y = POINT.y;
/* 338 */       RECT.width = 5;
/* 339 */       RECT.height = (height - 5);
/*     */       
/* 341 */       if (RECT.x + RECT.width > layeredPaneWidth) {
/* 342 */         RECT.width = (layeredPaneWidth - RECT.x);
/*     */       }
/* 344 */       if (RECT.y + RECT.height > layeredPaneHeight) {
/* 345 */         RECT.height = (layeredPaneHeight - RECT.y);
/*     */       }
/* 347 */       if (!RECT.isEmpty()) {
/* 348 */         Graphics g = vShadowBg.createGraphics();
/* 349 */         g.translate(-RECT.x, -RECT.y);
/* 350 */         g.setClip(RECT);
/* 351 */         if ((layeredPane instanceof JComponent)) {
/* 352 */           JComponent c = (JComponent)layeredPane;
/* 353 */           boolean doubleBuffered = c.isDoubleBuffered();
/* 354 */           c.setDoubleBuffered(false);
/* 355 */           c.paintAll(g);
/* 356 */           c.setDoubleBuffered(doubleBuffered);
/*     */         } else {
/* 358 */           layeredPane.paintAll(g);
/*     */         }
/* 360 */         g.dispose();
/*     */       }
/*     */     } catch (AWTException e) {
/* 363 */       canSnapshot = false;
/*     */     } catch (SecurityException e) {
/* 365 */       canSnapshot = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Container getLayeredPane()
/*     */   {
/* 374 */     Container parent = null;
/* 375 */     if (this.owner != null) {
/* 376 */       parent = (this.owner instanceof Container) ? (Container)this.owner : this.owner.getParent();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 381 */     for (Container p = parent; p != null; p = p.getParent()) {
/* 382 */       if ((p instanceof JRootPane)) {
/* 383 */         if (!(p.getParent() instanceof JInternalFrame))
/*     */         {
/*     */ 
/* 386 */           parent = ((JRootPane)p).getLayeredPane();
/*     */         }
/*     */       }
/* 389 */       else if ((p instanceof Window)) {
/* 390 */         if (parent == null) {
/* 391 */           parent = p;
/*     */         }
/*     */       } else {
/* 394 */         if ((p instanceof JApplet)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 401 */     return parent;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ShadowPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */