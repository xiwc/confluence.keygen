/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
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
/*     */ public final class PlasticXPSpinnerUI
/*     */   extends PlasticSpinnerUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  54 */     return new PlasticXPSpinnerUI();
/*     */   }
/*     */   
/*     */   protected Component createArrowButton(int direction)
/*     */   {
/*  59 */     return new SpinnerXPArrowButton(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class SpinnerXPArrowButton
/*     */     extends PlasticArrowButton
/*     */   {
/*     */     SpinnerXPArrowButton(int direction)
/*     */     {
/*  72 */       super(UIManager.getInt("ScrollBar.width") - 1, false);
/*     */     }
/*     */     
/*     */     protected int calculateArrowHeight(int height, int width) {
/*  76 */       int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
/*  77 */       return Math.max(arrowHeight, 3);
/*     */     }
/*     */     
/*     */     protected boolean isPaintingNorthBottom() {
/*  81 */       return true;
/*     */     }
/*     */     
/*     */     protected int calculateArrowOffset() {
/*  85 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void paintNorth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset, boolean paintBottom)
/*     */     {
/*  92 */       if (!this.isFreeStanding) {
/*  93 */         height++;
/*  94 */         g.translate(0, -1);
/*  95 */         if (!leftToRight) {
/*  96 */           width++;
/*  97 */           g.translate(-1, 0);
/*     */         } else {
/*  99 */           width += 2;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 104 */       g.setColor(arrowColor);
/* 105 */       int startY = 1 + (h + 1 - arrowHeight) / 2;
/* 106 */       int startX = w / 2;
/*     */       
/* 108 */       for (int line = 0; line < arrowHeight; line++) {
/* 109 */         g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1);
/*     */       }
/*     */       
/*     */ 
/* 113 */       paintNorthBorder(g, isEnabled, width, height, paintBottom);
/*     */       
/* 115 */       if (!this.isFreeStanding) {
/* 116 */         height--;
/* 117 */         g.translate(0, 1);
/* 118 */         if (!leftToRight) {
/* 119 */           width--;
/* 120 */           g.translate(1, 0);
/*     */         } else {
/* 122 */           width -= 2;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void paintNorthBorder(Graphics g, boolean isEnabled, int w, int h, boolean paintBottom) {
/* 128 */       if (isEnabled) {
/* 129 */         boolean isPressed = (this.model.isPressed()) && (this.model.isArmed());
/* 130 */         if (isPressed) {
/* 131 */           PlasticXPUtils.drawPressedButtonBorder(g, 0, 1, w - 2, h);
/*     */         } else {
/* 133 */           PlasticXPUtils.drawPlainButtonBorder(g, 0, 1, w - 2, h);
/*     */         }
/*     */       } else {
/* 136 */         PlasticXPUtils.drawDisabledButtonBorder(g, 0, 1, w - 2, h + 1);
/*     */       }
/*     */       
/* 139 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 142 */       g.fillRect(0, 1, 1, 1);
/*     */       
/* 144 */       if (paintBottom) {
/* 145 */         g.fillRect(0, h - 1, w - 1, 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void paintSouth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset)
/*     */     {
/* 154 */       if (!this.isFreeStanding) {
/* 155 */         height++;
/* 156 */         if (!leftToRight) {
/* 157 */           width++;
/* 158 */           g.translate(-1, 0);
/*     */         } else {
/* 160 */           width += 2;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 165 */       g.setColor(arrowColor);
/*     */       
/* 167 */       int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 2;
/* 168 */       int startX = w / 2;
/*     */       
/*     */ 
/*     */ 
/* 172 */       for (int line = 0; line < arrowHeight; line++) {
/* 173 */         g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1);
/*     */       }
/*     */       
/*     */ 
/* 177 */       paintSouthBorder(g, isEnabled, width, height);
/*     */       
/* 179 */       if (!this.isFreeStanding) {
/* 180 */         height--;
/* 181 */         if (!leftToRight) {
/* 182 */           width--;
/* 183 */           g.translate(1, 0);
/*     */         } else {
/* 185 */           width -= 2;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void paintSouthBorder(Graphics g, boolean isEnabled, int w, int h) {
/* 191 */       if (isEnabled) {
/* 192 */         boolean isPressed = (this.model.isPressed()) && (this.model.isArmed());
/* 193 */         if (isPressed) {
/* 194 */           PlasticXPUtils.drawPressedButtonBorder(g, 0, -2, w - 2, h + 1);
/*     */         } else {
/* 196 */           PlasticXPUtils.drawPlainButtonBorder(g, 0, -2, w - 2, h + 1);
/*     */         }
/*     */       } else {
/* 199 */         PlasticXPUtils.drawDisabledButtonBorder(g, 0, -2, w - 2, h + 1);
/*     */       }
/*     */       
/* 202 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 205 */       g.fillRect(0, h - 2, 1, 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPSpinnerUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */