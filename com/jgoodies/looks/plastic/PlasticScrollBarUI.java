/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalScrollBarUI;
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
/*     */ public final class PlasticScrollBarUI
/*     */   extends MetalScrollBarUI
/*     */ {
/*     */   private static final String PROPERTY_PREFIX = "ScrollBar.";
/*     */   public static final String MAX_BUMPS_WIDTH_KEY = "ScrollBar.maxBumpsWidth";
/*     */   private Color shadowColor;
/*     */   private Color highlightColor;
/*     */   private Color darkShadowColor;
/*     */   private Color thumbColor;
/*     */   private Color thumbShadow;
/*     */   private Color thumbHighlightColor;
/*     */   private PlasticBumps bumps;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  69 */     return new PlasticScrollBarUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults()
/*     */   {
/*  74 */     super.installDefaults();
/*  75 */     this.bumps = new PlasticBumps(10, 10, this.thumbHighlightColor, this.thumbShadow, this.thumbColor);
/*     */   }
/*     */   
/*     */   protected JButton createDecreaseButton(int orientation)
/*     */   {
/*  80 */     this.decreaseButton = new PlasticArrowButton(orientation, this.scrollBarWidth, this.isFreeStanding);
/*  81 */     return this.decreaseButton;
/*     */   }
/*     */   
/*     */   protected JButton createIncreaseButton(int orientation)
/*     */   {
/*  86 */     this.increaseButton = new PlasticArrowButton(orientation, this.scrollBarWidth, this.isFreeStanding);
/*  87 */     return this.increaseButton;
/*     */   }
/*     */   
/*     */   protected void configureScrollBarColors()
/*     */   {
/*  92 */     super.configureScrollBarColors();
/*  93 */     this.shadowColor = UIManager.getColor("ScrollBar.shadow");
/*  94 */     this.highlightColor = UIManager.getColor("ScrollBar.highlight");
/*  95 */     this.darkShadowColor = UIManager.getColor("ScrollBar.darkShadow");
/*  96 */     this.thumbColor = UIManager.getColor("ScrollBar.thumb");
/*  97 */     this.thumbShadow = UIManager.getColor("ScrollBar.thumbShadow");
/*  98 */     this.thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight");
/*     */   }
/*     */   
/*     */   protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
/*     */   {
/* 103 */     g.translate(trackBounds.x, trackBounds.y);
/*     */     
/* 105 */     boolean leftToRight = PlasticUtils.isLeftToRight(c);
/*     */     
/* 107 */     if (this.scrollbar.getOrientation() == 1) {
/* 108 */       if (!this.isFreeStanding) {
/* 109 */         if (!leftToRight) {
/* 110 */           trackBounds.width += 1;
/* 111 */           g.translate(-1, 0);
/*     */         } else {
/* 113 */           trackBounds.width += 2;
/*     */         }
/*     */       }
/*     */       
/* 117 */       if (c.isEnabled()) {
/* 118 */         g.setColor(this.darkShadowColor);
/* 119 */         g.drawLine(0, 0, 0, trackBounds.height - 1);
/* 120 */         g.drawLine(trackBounds.width - 2, 0, trackBounds.width - 2, trackBounds.height - 1);
/* 121 */         g.drawLine(1, trackBounds.height - 1, trackBounds.width - 1, trackBounds.height - 1);
/* 122 */         g.drawLine(1, 0, trackBounds.width - 2, 0);
/*     */         
/* 124 */         g.setColor(this.shadowColor);
/*     */         
/* 126 */         g.drawLine(1, 1, 1, trackBounds.height - 2);
/* 127 */         g.drawLine(1, 1, trackBounds.width - 3, 1);
/* 128 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum()) {
/* 129 */           int y = this.thumbRect.y + this.thumbRect.height - trackBounds.y;
/* 130 */           g.drawLine(1, y, trackBounds.width - 1, y);
/*     */         }
/* 132 */         g.setColor(this.highlightColor);
/* 133 */         g.drawLine(trackBounds.width - 1, 0, trackBounds.width - 1, trackBounds.height - 1);
/*     */       } else {
/* 135 */         PlasticUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height);
/*     */       }
/*     */       
/* 138 */       if (!this.isFreeStanding) {
/* 139 */         if (!leftToRight) {
/* 140 */           trackBounds.width -= 1;
/* 141 */           g.translate(1, 0);
/*     */         } else {
/* 143 */           trackBounds.width -= 2;
/*     */         }
/*     */       }
/*     */     } else {
/* 147 */       if (!this.isFreeStanding) {
/* 148 */         trackBounds.height += 2;
/*     */       }
/*     */       
/* 151 */       if (c.isEnabled()) {
/* 152 */         g.setColor(this.darkShadowColor);
/* 153 */         g.drawLine(0, 0, trackBounds.width - 1, 0);
/* 154 */         g.drawLine(0, 1, 0, trackBounds.height - 2);
/* 155 */         g.drawLine(0, trackBounds.height - 2, trackBounds.width - 1, trackBounds.height - 2);
/*     */         
/* 157 */         g.drawLine(trackBounds.width - 1, 1, trackBounds.width - 1, trackBounds.height - 1);
/*     */         
/*     */ 
/* 160 */         g.setColor(this.shadowColor);
/*     */         
/* 162 */         g.drawLine(1, 1, trackBounds.width - 2, 1);
/* 163 */         g.drawLine(1, 1, 1, trackBounds.height - 3);
/* 164 */         g.drawLine(0, trackBounds.height - 1, trackBounds.width - 1, trackBounds.height - 1);
/*     */         
/* 166 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum()) {
/* 167 */           int x = this.thumbRect.x + this.thumbRect.width - trackBounds.x;
/* 168 */           g.drawLine(x, 1, x, trackBounds.height - 1);
/*     */         }
/*     */       } else {
/* 171 */         PlasticUtils.drawDisabledBorder(g, 0, 0, trackBounds.width, trackBounds.height);
/*     */       }
/*     */       
/* 174 */       if (!this.isFreeStanding) {
/* 175 */         trackBounds.height -= 2;
/*     */       }
/*     */     }
/* 178 */     g.translate(-trackBounds.x, -trackBounds.y);
/*     */   }
/*     */   
/*     */   protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
/*     */   {
/* 183 */     if (!c.isEnabled()) {
/* 184 */       return;
/*     */     }
/*     */     
/* 187 */     boolean leftToRight = PlasticUtils.isLeftToRight(c);
/*     */     
/* 189 */     g.translate(thumbBounds.x, thumbBounds.y);
/*     */     
/* 191 */     if (this.scrollbar.getOrientation() == 1) {
/* 192 */       if (!this.isFreeStanding) {
/* 193 */         if (!leftToRight) {
/* 194 */           thumbBounds.width += 1;
/* 195 */           g.translate(-1, 0);
/*     */         } else {
/* 197 */           thumbBounds.width += 2;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 202 */       g.setColor(this.thumbColor);
/* 203 */       g.fillRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
/*     */       
/* 205 */       g.setColor(this.thumbShadow);
/* 206 */       g.drawRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
/*     */       
/* 208 */       g.setColor(this.thumbHighlightColor);
/* 209 */       g.drawLine(1, 1, thumbBounds.width - 3, 1);
/* 210 */       g.drawLine(1, 1, 1, thumbBounds.height - 2);
/*     */       
/* 212 */       paintBumps(g, c, 3, 4, thumbBounds.width - 6, thumbBounds.height - 7);
/*     */       
/* 214 */       if (!this.isFreeStanding) {
/* 215 */         if (!leftToRight) {
/* 216 */           thumbBounds.width -= 1;
/* 217 */           g.translate(1, 0);
/*     */         } else {
/* 219 */           thumbBounds.width -= 2;
/*     */         }
/*     */       }
/*     */     } else {
/* 223 */       if (!this.isFreeStanding) {
/* 224 */         thumbBounds.height += 2;
/*     */       }
/*     */       
/* 227 */       g.setColor(this.thumbColor);
/* 228 */       g.fillRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);
/*     */       
/* 230 */       g.setColor(this.thumbShadow);
/* 231 */       g.drawRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);
/*     */       
/* 233 */       g.setColor(this.thumbHighlightColor);
/* 234 */       g.drawLine(1, 1, thumbBounds.width - 2, 1);
/* 235 */       g.drawLine(1, 1, 1, thumbBounds.height - 3);
/*     */       
/* 237 */       paintBumps(g, c, 4, 3, thumbBounds.width - 7, thumbBounds.height - 6);
/*     */       
/* 239 */       if (!this.isFreeStanding) {
/* 240 */         thumbBounds.height -= 2;
/*     */       }
/*     */     }
/* 243 */     g.translate(-thumbBounds.x, -thumbBounds.y);
/*     */     
/* 245 */     if (PlasticUtils.is3D("ScrollBar.")) {
/* 246 */       paintThumb3D(g, thumbBounds);
/*     */     }
/*     */   }
/*     */   
/*     */   private void paintBumps(Graphics g, JComponent c, int x, int y, int width, int height)
/*     */   {
/* 252 */     if (!useNarrowBumps()) {
/* 253 */       this.bumps.setBumpArea(width, height);
/* 254 */       this.bumps.paintIcon(c, g, x, y);
/*     */     } else {
/* 256 */       int maxWidth = UIManager.getInt("ScrollBar.maxBumpsWidth");
/* 257 */       int myWidth = Math.min(maxWidth, width);
/* 258 */       int myHeight = Math.min(maxWidth, height);
/* 259 */       int myX = x + (width - myWidth) / 2;
/* 260 */       int myY = y + (height - myHeight) / 2;
/* 261 */       this.bumps.setBumpArea(myWidth, myHeight);
/* 262 */       this.bumps.paintIcon(c, g, myX, myY);
/*     */     }
/*     */   }
/*     */   
/*     */   private void paintThumb3D(Graphics g, Rectangle thumbBounds)
/*     */   {
/* 268 */     boolean isHorizontal = this.scrollbar.getOrientation() == 0;
/* 269 */     int width = thumbBounds.width - (isHorizontal ? 3 : 1);
/* 270 */     int height = thumbBounds.height - (isHorizontal ? 1 : 3);
/* 271 */     Rectangle r = new Rectangle(thumbBounds.x + 2, thumbBounds.y + 2, width, height);
/* 272 */     PlasticUtils.addLight3DEffekt(g, r, isHorizontal);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean useNarrowBumps()
/*     */   {
/* 279 */     Object value = UIManager.get("ScrollBar.maxBumpsWidth");
/* 280 */     return (value != null) && ((value instanceof Integer));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticScrollBarUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */