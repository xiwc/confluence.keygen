/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import javax.swing.UIManager;
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
/*     */ public final class ExtBasicSpinnerLayout
/*     */   implements LayoutManager
/*     */ {
/*  52 */   private static final Dimension ZERO_SIZE = new Dimension(0, 0);
/*     */   
/*     */ 
/*  55 */   private Component nextButton = null;
/*  56 */   private Component previousButton = null;
/*  57 */   private Component editor = null;
/*     */   
/*     */   public void addLayoutComponent(String name, Component c)
/*     */   {
/*  61 */     if ("Next".equals(name)) {
/*  62 */       this.nextButton = c;
/*  63 */     } else if ("Previous".equals(name)) {
/*  64 */       this.previousButton = c;
/*  65 */     } else if ("Editor".equals(name)) {
/*  66 */       this.editor = c;
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeLayoutComponent(Component c)
/*     */   {
/*  72 */     if (c == this.nextButton) {
/*  73 */       c = null;
/*  74 */     } else if (c == this.previousButton) {
/*  75 */       this.previousButton = null;
/*  76 */     } else if (c == this.editor) {
/*  77 */       this.editor = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private Dimension preferredSize(Component c)
/*     */   {
/*  83 */     return c == null ? ZERO_SIZE : c.getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension preferredLayoutSize(Container parent)
/*     */   {
/*  88 */     Dimension nextD = preferredSize(this.nextButton);
/*  89 */     Dimension previousD = preferredSize(this.previousButton);
/*  90 */     Dimension editorD = preferredSize(this.editor);
/*     */     
/*  92 */     Dimension size = new Dimension(editorD.width, editorD.height);
/*  93 */     size.width += Math.max(nextD.width, previousD.width);
/*  94 */     Insets insets = parent.getInsets();
/*  95 */     size.width += insets.left + insets.right;
/*  96 */     size.height += insets.top + insets.bottom;
/*  97 */     return size;
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container parent)
/*     */   {
/* 102 */     return preferredLayoutSize(parent);
/*     */   }
/*     */   
/*     */   private void setBounds(Component c, int x, int y, int width, int height)
/*     */   {
/* 107 */     if (c != null) {
/* 108 */       c.setBounds(x, y, width, height);
/*     */     }
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container parent)
/*     */   {
/* 114 */     int width = parent.getWidth();
/* 115 */     int height = parent.getHeight();
/*     */     
/* 117 */     Insets insets = parent.getInsets();
/* 118 */     Dimension nextD = preferredSize(this.nextButton);
/* 119 */     Dimension previousD = preferredSize(this.previousButton);
/* 120 */     int buttonsWidth = Math.max(nextD.width, previousD.width);
/* 121 */     int editorHeight = height - (insets.top + insets.bottom);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 128 */     Insets buttonInsets = UIManager.getInsets("Spinner.arrowButtonInsets");
/*     */     
/* 130 */     if (buttonInsets == null) {
/* 131 */       buttonInsets = insets;
/*     */     }
/*     */     
/*     */     int buttonsX;
/*     */     int buttonsX;
/*     */     int editorX;
/*     */     int editorWidth;
/* 138 */     if (parent.getComponentOrientation().isLeftToRight()) {
/* 139 */       int editorX = insets.left;
/* 140 */       int editorWidth = width - insets.left - buttonsWidth - buttonInsets.right;
/*     */       
/* 142 */       buttonsX = width - buttonsWidth - buttonInsets.right;
/*     */     } else {
/* 144 */       buttonsX = buttonInsets.left;
/* 145 */       editorX = buttonsX + buttonsWidth;
/* 146 */       editorWidth = width - buttonInsets.left - buttonsWidth - insets.right;
/*     */     }
/*     */     
/*     */ 
/* 150 */     int nextY = buttonInsets.top;
/* 151 */     int nextHeight = height / 2 + height % 2 - nextY;
/* 152 */     int previousY = buttonInsets.top + nextHeight;
/* 153 */     int previousHeight = height - previousY - buttonInsets.bottom;
/*     */     
/* 155 */     setBounds(this.editor, editorX, insets.top, editorWidth, editorHeight);
/* 156 */     setBounds(this.nextButton, buttonsX, nextY, buttonsWidth, nextHeight);
/* 157 */     setBounds(this.previousButton, buttonsX, previousY, buttonsWidth, previousHeight);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicSpinnerLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */