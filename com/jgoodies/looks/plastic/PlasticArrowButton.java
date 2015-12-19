/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.metal.MetalScrollButton;
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
/*     */ class PlasticArrowButton
/*     */   extends MetalScrollButton
/*     */ {
/*     */   private final Color shadowColor;
/*     */   private final Color highlightColor;
/*     */   protected boolean isFreeStanding;
/*     */   
/*     */   public PlasticArrowButton(int direction, int width, boolean freeStanding)
/*     */   {
/*  56 */     super(direction, width, freeStanding);
/*  57 */     this.shadowColor = UIManager.getColor("ScrollBar.darkShadow");
/*  58 */     this.highlightColor = UIManager.getColor("ScrollBar.highlight");
/*  59 */     this.isFreeStanding = freeStanding;
/*     */   }
/*     */   
/*     */   public void setFreeStanding(boolean freeStanding)
/*     */   {
/*  64 */     super.setFreeStanding(freeStanding);
/*  65 */     this.isFreeStanding = freeStanding;
/*     */   }
/*     */   
/*     */   public void paint(Graphics g)
/*     */   {
/*  70 */     boolean leftToRight = PlasticUtils.isLeftToRight(this);
/*  71 */     boolean isEnabled = getParent().isEnabled();
/*  72 */     boolean isPressed = getModel().isPressed();
/*     */     
/*  74 */     Color arrowColor = isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled();
/*     */     
/*     */ 
/*  77 */     int width = getWidth();
/*  78 */     int height = getHeight();
/*  79 */     int w = width;
/*  80 */     int h = height;
/*  81 */     int arrowHeight = calculateArrowHeight(height, width);
/*  82 */     int arrowOffset = calculateArrowOffset();
/*  83 */     boolean paintNorthBottom = isPaintingNorthBottom();
/*     */     
/*  85 */     g.setColor(isPressed ? PlasticLookAndFeel.getControlShadow() : getBackground());
/*  86 */     g.fillRect(0, 0, width, height);
/*     */     
/*  88 */     if (getDirection() == 1) {
/*  89 */       paintNorth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset, paintNorthBottom);
/*     */     }
/*  91 */     else if (getDirection() == 5) {
/*  92 */       paintSouth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset);
/*     */     }
/*  94 */     else if (getDirection() == 3) {
/*  95 */       paintEast(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
/*     */     }
/*  97 */     else if (getDirection() == 7) {
/*  98 */       paintWest(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
/*     */     }
/*     */     
/* 101 */     if (PlasticUtils.is3D("ScrollBar.")) {
/* 102 */       paint3D(g);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int calculateArrowHeight(int height, int width)
/*     */   {
/* 114 */     return (height + 1) / 4;
/*     */   }
/*     */   
/*     */   protected int calculateArrowOffset() {
/* 118 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean isPaintingNorthBottom() {
/* 122 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void paintWest(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight)
/*     */   {
/* 129 */     if (!this.isFreeStanding) {
/* 130 */       height += 2;
/* 131 */       width++;
/* 132 */       g.translate(-1, 0);
/*     */     }
/*     */     
/*     */ 
/* 136 */     g.setColor(arrowColor);
/*     */     
/* 138 */     int startX = (w + 1 - arrowHeight) / 2;
/* 139 */     int startY = h / 2;
/*     */     
/* 141 */     for (int line = 0; line < arrowHeight; line++) {
/* 142 */       g.drawLine(startX + line, startY - line, startX + line, startY + line + 1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 149 */     if (isEnabled) {
/* 150 */       g.setColor(this.highlightColor);
/*     */       
/* 152 */       if (!isPressed) {
/* 153 */         g.drawLine(1, 1, width - 1, 1);
/* 154 */         g.drawLine(1, 1, 1, height - 3);
/*     */       }
/* 156 */       g.drawLine(1, height - 1, width - 1, height - 1);
/*     */       
/* 158 */       g.setColor(this.shadowColor);
/* 159 */       g.drawLine(0, 0, width - 1, 0);
/* 160 */       g.drawLine(0, 0, 0, height - 2);
/* 161 */       g.drawLine(1, height - 2, width - 1, height - 2);
/*     */     } else {
/* 163 */       PlasticUtils.drawDisabledBorder(g, 0, 0, width + 1, height);
/*     */     }
/*     */     
/* 166 */     if (!this.isFreeStanding) {
/* 167 */       height -= 2;
/* 168 */       width--;
/* 169 */       g.translate(1, 0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void paintEast(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight)
/*     */   {
/* 176 */     if (!this.isFreeStanding) {
/* 177 */       height += 2;
/* 178 */       width++;
/*     */     }
/*     */     
/*     */ 
/* 182 */     g.setColor(arrowColor);
/*     */     
/* 184 */     int startX = (w + 1 - arrowHeight) / 2 + arrowHeight - 1;
/* 185 */     int startY = h / 2;
/* 186 */     for (int line = 0; line < arrowHeight; line++) {
/* 187 */       g.drawLine(startX - line, startY - line, startX - line, startY + line + 1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */     if (isEnabled) {
/* 195 */       g.setColor(this.highlightColor);
/* 196 */       if (!isPressed) {
/* 197 */         g.drawLine(0, 1, width - 3, 1);
/* 198 */         g.drawLine(0, 1, 0, height - 3);
/*     */       }
/* 200 */       g.drawLine(width - 1, 1, width - 1, height - 1);
/* 201 */       g.drawLine(0, height - 1, width - 1, height - 1);
/*     */       
/* 203 */       g.setColor(this.shadowColor);
/* 204 */       g.drawLine(0, 0, width - 2, 0);
/* 205 */       g.drawLine(width - 2, 1, width - 2, height - 2);
/* 206 */       g.drawLine(0, height - 2, width - 2, height - 2);
/*     */     } else {
/* 208 */       PlasticUtils.drawDisabledBorder(g, -1, 0, width + 1, height);
/*     */     }
/* 210 */     if (!this.isFreeStanding) {
/* 211 */       height -= 2;
/* 212 */       width--;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void paintSouth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset)
/*     */   {
/* 221 */     if (!this.isFreeStanding) {
/* 222 */       height++;
/* 223 */       if (!leftToRight) {
/* 224 */         width++;
/* 225 */         g.translate(-1, 0);
/*     */       } else {
/* 227 */         width += 2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 232 */     g.setColor(arrowColor);
/*     */     
/* 234 */     int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 1;
/* 235 */     int startX = w / 2;
/*     */     
/*     */ 
/*     */ 
/* 239 */     for (int line = 0; line < arrowHeight; line++) {
/* 240 */       g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1);
/*     */     }
/*     */     
/* 243 */     if (isEnabled) {
/* 244 */       g.setColor(this.highlightColor);
/* 245 */       if (!isPressed) {
/* 246 */         g.drawLine(1, 0, width - 3, 0);
/* 247 */         g.drawLine(1, 0, 1, height - 3);
/*     */       }
/* 249 */       g.drawLine(0, height - 1, width - 1, height - 1);
/* 250 */       g.drawLine(width - 1, 0, width - 1, height - 1);
/*     */       
/* 252 */       g.setColor(this.shadowColor);
/* 253 */       g.drawLine(0, 0, 0, height - 2);
/* 254 */       g.drawLine(width - 2, 0, width - 2, height - 2);
/* 255 */       g.drawLine(1, height - 2, width - 2, height - 2);
/*     */     } else {
/* 257 */       PlasticUtils.drawDisabledBorder(g, 0, -1, width, height + 1);
/*     */     }
/*     */     
/* 260 */     if (!this.isFreeStanding) {
/* 261 */       height--;
/* 262 */       if (!leftToRight) {
/* 263 */         width--;
/* 264 */         g.translate(1, 0);
/*     */       } else {
/* 266 */         width -= 2;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void paintNorth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset, boolean paintBottom)
/*     */   {
/* 276 */     if (!this.isFreeStanding) {
/* 277 */       height++;
/* 278 */       g.translate(0, -1);
/* 279 */       if (!leftToRight) {
/* 280 */         width++;
/* 281 */         g.translate(-1, 0);
/*     */       } else {
/* 283 */         width += 2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 288 */     g.setColor(arrowColor);
/* 289 */     int startY = (h + 1 - arrowHeight) / 2;
/* 290 */     int startX = w / 2;
/*     */     
/* 292 */     for (int line = 0; line < arrowHeight; line++) {
/* 293 */       g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1);
/*     */     }
/*     */     
/* 296 */     if (isEnabled) {
/* 297 */       g.setColor(this.highlightColor);
/*     */       
/* 299 */       if (!isPressed) {
/* 300 */         g.drawLine(1, 1, width - 3, 1);
/* 301 */         g.drawLine(1, 1, 1, height - 1);
/*     */       }
/*     */       
/* 304 */       g.drawLine(width - 1, 1, width - 1, height - 1);
/*     */       
/* 306 */       g.setColor(this.shadowColor);
/* 307 */       g.drawLine(0, 0, width - 2, 0);
/* 308 */       g.drawLine(0, 0, 0, height - 1);
/* 309 */       g.drawLine(width - 2, 1, width - 2, height - 1);
/* 310 */       if (paintBottom) {
/* 311 */         g.fillRect(0, height - 1, width - 1, 1);
/*     */       }
/*     */     } else {
/* 314 */       PlasticUtils.drawDisabledBorder(g, 0, 0, width, height + 1);
/* 315 */       if (paintBottom) {
/* 316 */         g.setColor(PlasticLookAndFeel.getControlShadow());
/* 317 */         g.fillRect(0, height - 1, width - 1, 1);
/*     */       }
/*     */     }
/* 320 */     if (!this.isFreeStanding) {
/* 321 */       height--;
/* 322 */       g.translate(0, 1);
/* 323 */       if (!leftToRight) {
/* 324 */         width--;
/* 325 */         g.translate(1, 0);
/*     */       } else {
/* 327 */         width -= 2;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void paint3D(Graphics g)
/*     */   {
/* 334 */     ButtonModel buttonModel = getModel();
/* 335 */     if (((buttonModel.isArmed()) && (buttonModel.isPressed())) || (buttonModel.isSelected())) {
/* 336 */       return;
/*     */     }
/* 338 */     int width = getWidth();
/* 339 */     int height = getHeight();
/* 340 */     if (getDirection() == 3) {
/* 341 */       width -= 2;
/* 342 */     } else if (getDirection() == 5) {
/* 343 */       height -= 2;
/*     */     }
/* 345 */     Rectangle r = new Rectangle(1, 1, width, height);
/* 346 */     boolean isHorizontal = (getDirection() == 3) || (getDirection() == 7);
/* 347 */     PlasticUtils.addLight3DEffekt(g, r, isHorizontal);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticArrowButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */