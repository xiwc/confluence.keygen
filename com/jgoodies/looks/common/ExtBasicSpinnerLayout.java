/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.ComponentOrientation;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Insets;
/*   8:    */ import java.awt.LayoutManager;
/*   9:    */ import javax.swing.UIManager;
/*  10:    */ 
/*  11:    */ public final class ExtBasicSpinnerLayout
/*  12:    */   implements LayoutManager
/*  13:    */ {
/*  14: 52 */   private static final Dimension ZERO_SIZE = new Dimension(0, 0);
/*  15: 55 */   private Component nextButton = null;
/*  16: 56 */   private Component previousButton = null;
/*  17: 57 */   private Component editor = null;
/*  18:    */   
/*  19:    */   public void addLayoutComponent(String name, Component c)
/*  20:    */   {
/*  21: 61 */     if ("Next".equals(name)) {
/*  22: 62 */       this.nextButton = c;
/*  23: 63 */     } else if ("Previous".equals(name)) {
/*  24: 64 */       this.previousButton = c;
/*  25: 65 */     } else if ("Editor".equals(name)) {
/*  26: 66 */       this.editor = c;
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void removeLayoutComponent(Component c)
/*  31:    */   {
/*  32: 72 */     if (c == this.nextButton) {
/*  33: 73 */       c = null;
/*  34: 74 */     } else if (c == this.previousButton) {
/*  35: 75 */       this.previousButton = null;
/*  36: 76 */     } else if (c == this.editor) {
/*  37: 77 */       this.editor = null;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   private Dimension preferredSize(Component c)
/*  42:    */   {
/*  43: 83 */     return c == null ? ZERO_SIZE : c.getPreferredSize();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Dimension preferredLayoutSize(Container parent)
/*  47:    */   {
/*  48: 88 */     Dimension nextD = preferredSize(this.nextButton);
/*  49: 89 */     Dimension previousD = preferredSize(this.previousButton);
/*  50: 90 */     Dimension editorD = preferredSize(this.editor);
/*  51:    */     
/*  52: 92 */     Dimension size = new Dimension(editorD.width, editorD.height);
/*  53: 93 */     size.width += Math.max(nextD.width, previousD.width);
/*  54: 94 */     Insets insets = parent.getInsets();
/*  55: 95 */     size.width += insets.left + insets.right;
/*  56: 96 */     size.height += insets.top + insets.bottom;
/*  57: 97 */     return size;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Dimension minimumLayoutSize(Container parent)
/*  61:    */   {
/*  62:102 */     return preferredLayoutSize(parent);
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void setBounds(Component c, int x, int y, int width, int height)
/*  66:    */   {
/*  67:107 */     if (c != null) {
/*  68:108 */       c.setBounds(x, y, width, height);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void layoutContainer(Container parent)
/*  73:    */   {
/*  74:114 */     int width = parent.getWidth();
/*  75:115 */     int height = parent.getHeight();
/*  76:    */     
/*  77:117 */     Insets insets = parent.getInsets();
/*  78:118 */     Dimension nextD = preferredSize(this.nextButton);
/*  79:119 */     Dimension previousD = preferredSize(this.previousButton);
/*  80:120 */     int buttonsWidth = Math.max(nextD.width, previousD.width);
/*  81:121 */     int editorHeight = height - (insets.top + insets.bottom);
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:128 */     Insets buttonInsets = UIManager.getInsets("Spinner.arrowButtonInsets");
/*  89:130 */     if (buttonInsets == null) {
/*  90:131 */       buttonInsets = insets;
/*  91:    */     }
/*  92:    */     int buttonsX;
/*  93:    */     int buttonsX;
/*  94:    */     int editorX;
/*  95:    */     int editorWidth;
/*  96:138 */     if (parent.getComponentOrientation().isLeftToRight())
/*  97:    */     {
/*  98:139 */       int editorX = insets.left;
/*  99:140 */       int editorWidth = width - insets.left - buttonsWidth - buttonInsets.right;
/* 100:    */       
/* 101:142 */       buttonsX = width - buttonsWidth - buttonInsets.right;
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:144 */       buttonsX = buttonInsets.left;
/* 106:145 */       editorX = buttonsX + buttonsWidth;
/* 107:146 */       editorWidth = width - buttonInsets.left - buttonsWidth - insets.right;
/* 108:    */     }
/* 109:150 */     int nextY = buttonInsets.top;
/* 110:151 */     int nextHeight = height / 2 + height % 2 - nextY;
/* 111:152 */     int previousY = buttonInsets.top + nextHeight;
/* 112:153 */     int previousHeight = height - previousY - buttonInsets.bottom;
/* 113:    */     
/* 114:155 */     setBounds(this.editor, editorX, insets.top, editorWidth, editorHeight);
/* 115:156 */     setBounds(this.nextButton, buttonsX, nextY, buttonsWidth, nextHeight);
/* 116:157 */     setBounds(this.previousButton, buttonsX, previousY, buttonsWidth, previousHeight);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicSpinnerLayout
 * JD-Core Version:    0.7.0.1
 */