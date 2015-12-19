/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.metal.MetalInternalFrameTitlePane;
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
/*     */ public final class PlasticInternalFrameTitlePane
/*     */   extends MetalInternalFrameTitlePane
/*     */ {
/*     */   private PlasticBumps paletteBumps;
/*  51 */   private final PlasticBumps activeBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlDarkShadow(), PlasticLookAndFeel.getPrimaryControl());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  59 */   private final PlasticBumps inactiveBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getControlHighlight(), PlasticLookAndFeel.getControlDarkShadow(), PlasticLookAndFeel.getControl());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PlasticInternalFrameTitlePane(JInternalFrame frame)
/*     */   {
/*  68 */     super(frame);
/*     */   }
/*     */   
/*     */   public void paintPalette(Graphics g) {
/*  72 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/*     */     
/*  74 */     int width = getWidth();
/*  75 */     int height = getHeight();
/*     */     
/*  77 */     if (this.paletteBumps == null) {
/*  78 */       this.paletteBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlInfo(), PlasticLookAndFeel.getPrimaryControlShadow());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */     Color background = PlasticLookAndFeel.getPrimaryControlShadow();
/*  88 */     Color darkShadow = PlasticLookAndFeel.getControlDarkShadow();
/*     */     
/*  90 */     g.setColor(background);
/*  91 */     g.fillRect(0, 0, width, height);
/*     */     
/*  93 */     g.setColor(darkShadow);
/*  94 */     g.drawLine(0, height - 1, width, height - 1);
/*     */     
/*  96 */     int buttonsWidth = getButtonsWidth();
/*  97 */     int xOffset = leftToRight ? 4 : buttonsWidth + 4;
/*  98 */     int bumpLength = width - buttonsWidth - 8;
/*  99 */     int bumpHeight = getHeight() - 4;
/* 100 */     this.paletteBumps.setBumpArea(bumpLength, bumpHeight);
/* 101 */     this.paletteBumps.paintIcon(this, g, xOffset, 2);
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 105 */     if (this.isPalette) {
/* 106 */       paintPalette(g);
/* 107 */       return;
/*     */     }
/*     */     
/* 110 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/* 111 */     boolean isSelected = this.frame.isSelected();
/*     */     
/* 113 */     int width = getWidth();
/* 114 */     int height = getHeight();
/*     */     
/* 116 */     Color background = null;
/* 117 */     Color foreground = null;
/* 118 */     Color shadow = null;
/*     */     
/*     */     PlasticBumps bumps;
/*     */     PlasticBumps bumps;
/* 122 */     if (isSelected) {
/* 123 */       background = PlasticLookAndFeel.getWindowTitleBackground();
/* 124 */       foreground = PlasticLookAndFeel.getWindowTitleForeground();
/* 125 */       bumps = this.activeBumps;
/*     */     } else {
/* 127 */       background = PlasticLookAndFeel.getWindowTitleInactiveBackground();
/* 128 */       foreground = PlasticLookAndFeel.getWindowTitleInactiveForeground();
/* 129 */       bumps = this.inactiveBumps;
/*     */     }
/*     */     
/* 132 */     shadow = PlasticLookAndFeel.getControlDarkShadow();
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
/* 165 */     g.setColor(background);
/* 166 */     g.fillRect(0, 0, width, height);
/*     */     
/* 168 */     g.setColor(shadow);
/* 169 */     g.drawLine(0, height - 1, width, height - 1);
/* 170 */     g.drawLine(0, 0, 0, 0);
/* 171 */     g.drawLine(width - 1, 0, width - 1, 0);
/*     */     
/* 173 */     int titleLength = 0;
/* 174 */     int xOffset = leftToRight ? 5 : width - 5;
/* 175 */     String frameTitle = this.frame.getTitle();
/*     */     
/* 177 */     Icon icon = this.frame.getFrameIcon();
/* 178 */     if (icon != null) {
/* 179 */       if (!leftToRight)
/* 180 */         xOffset -= icon.getIconWidth();
/* 181 */       int iconY = height / 2 - icon.getIconHeight() / 2;
/* 182 */       icon.paintIcon(this.frame, g, xOffset, iconY);
/* 183 */       xOffset += (leftToRight ? icon.getIconWidth() + 5 : -5);
/*     */     }
/*     */     
/* 186 */     if (frameTitle != null) {
/* 187 */       Font f = getFont();
/* 188 */       g.setFont(f);
/* 189 */       FontMetrics fm = g.getFontMetrics();
/*     */       
/*     */ 
/* 192 */       g.setColor(foreground);
/*     */       
/* 194 */       int yOffset = (height - fm.getHeight()) / 2 + fm.getAscent();
/*     */       
/* 196 */       Rectangle rect = new Rectangle(0, 0, 0, 0);
/* 197 */       if (this.frame.isIconifiable()) {
/* 198 */         rect = this.iconButton.getBounds();
/* 199 */       } else if (this.frame.isMaximizable()) {
/* 200 */         rect = this.maxButton.getBounds();
/* 201 */       } else if (this.frame.isClosable()) {
/* 202 */         rect = this.closeButton.getBounds();
/*     */       }
/*     */       
/*     */ 
/* 206 */       if (leftToRight) {
/* 207 */         if (rect.x == 0) {
/* 208 */           rect.x = (this.frame.getWidth() - this.frame.getInsets().right - 2);
/*     */         }
/* 210 */         int titleW = rect.x - xOffset - 4;
/* 211 */         frameTitle = getTitle(frameTitle, fm, titleW);
/*     */       } else {
/* 213 */         int titleW = xOffset - rect.x - rect.width - 4;
/* 214 */         frameTitle = getTitle(frameTitle, fm, titleW);
/* 215 */         xOffset -= SwingUtilities.computeStringWidth(fm, frameTitle);
/*     */       }
/*     */       
/* 218 */       titleLength = SwingUtilities.computeStringWidth(fm, frameTitle);
/* 219 */       g.drawString(frameTitle, xOffset, yOffset);
/* 220 */       xOffset += (leftToRight ? titleLength + 5 : -5);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 225 */     int buttonsWidth = getButtonsWidth();
/* 226 */     int bumpXOffset; int bumpLength; int bumpXOffset; if (leftToRight) {
/* 227 */       int bumpLength = width - buttonsWidth - xOffset - 5;
/* 228 */       bumpXOffset = xOffset;
/*     */     } else {
/* 230 */       bumpLength = xOffset - buttonsWidth - 5;
/* 231 */       bumpXOffset = buttonsWidth + 5;
/*     */     }
/* 233 */     int bumpYOffset = 3;
/* 234 */     int bumpHeight = getHeight() - 2 * bumpYOffset;
/* 235 */     bumps.setBumpArea(bumpLength, bumpHeight);
/* 236 */     bumps.paintIcon(this, g, bumpXOffset, bumpYOffset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getTitle(String text, FontMetrics fm, int availTextWidth)
/*     */   {
/* 243 */     if ((text == null) || (text.equals("")))
/* 244 */       return "";
/* 245 */     int textWidth = SwingUtilities.computeStringWidth(fm, text);
/* 246 */     String clipString = "…";
/* 247 */     if (textWidth > availTextWidth) {
/* 248 */       int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
/*     */       
/* 250 */       for (int nChars = 0; nChars < text.length(); nChars++) {
/* 251 */         totalWidth += fm.charWidth(text.charAt(nChars));
/* 252 */         if (totalWidth > availTextWidth) {
/*     */           break;
/*     */         }
/*     */       }
/* 256 */       text = text.substring(0, nChars) + clipString;
/*     */     }
/* 258 */     return text;
/*     */   }
/*     */   
/*     */   private int getButtonsWidth() {
/* 262 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
/*     */     
/* 264 */     int w = getWidth();
/* 265 */     int x = leftToRight ? w : 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 270 */     int buttonWidth = this.closeButton.getIcon().getIconWidth();
/*     */     
/* 272 */     if (this.frame.isClosable()) {
/* 273 */       if (this.isPalette) {
/* 274 */         int spacing = 3;
/* 275 */         x += (leftToRight ? -spacing - (buttonWidth + 2) : spacing);
/* 276 */         if (!leftToRight)
/* 277 */           x += buttonWidth + 2;
/*     */       } else {
/* 279 */         int spacing = 4;
/* 280 */         x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 281 */         if (!leftToRight) {
/* 282 */           x += buttonWidth;
/*     */         }
/*     */       }
/*     */     }
/* 286 */     if ((this.frame.isMaximizable()) && (!this.isPalette)) {
/* 287 */       int spacing = this.frame.isClosable() ? 10 : 4;
/* 288 */       x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 289 */       if (!leftToRight) {
/* 290 */         x += buttonWidth;
/*     */       }
/*     */     }
/* 293 */     if ((this.frame.isIconifiable()) && (!this.isPalette)) {
/* 294 */       int spacing = this.frame.isClosable() ? 10 : this.frame.isMaximizable() ? 2 : 4;
/*     */       
/* 296 */       x += (leftToRight ? -spacing - buttonWidth : spacing);
/* 297 */       if (!leftToRight) {
/* 298 */         x += buttonWidth;
/*     */       }
/*     */     }
/* 301 */     return leftToRight ? w - x : x;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticInternalFrameTitlePane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */