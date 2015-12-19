/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import javax.swing.ButtonModel;
/*   7:    */ import javax.swing.JComponent;
/*   8:    */ import javax.swing.UIManager;
/*   9:    */ import javax.swing.plaf.ComponentUI;
/*  10:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*  11:    */ 
/*  12:    */ public final class PlasticXPSpinnerUI
/*  13:    */   extends PlasticSpinnerUI
/*  14:    */ {
/*  15:    */   public static ComponentUI createUI(JComponent b)
/*  16:    */   {
/*  17: 54 */     return new PlasticXPSpinnerUI();
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected Component createArrowButton(int direction)
/*  21:    */   {
/*  22: 59 */     return new SpinnerXPArrowButton(direction);
/*  23:    */   }
/*  24:    */   
/*  25:    */   private static final class SpinnerXPArrowButton
/*  26:    */     extends PlasticArrowButton
/*  27:    */   {
/*  28:    */     SpinnerXPArrowButton(int direction)
/*  29:    */     {
/*  30: 72 */       super(UIManager.getInt("ScrollBar.width") - 1, false);
/*  31:    */     }
/*  32:    */     
/*  33:    */     protected int calculateArrowHeight(int height, int width)
/*  34:    */     {
/*  35: 76 */       int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
/*  36: 77 */       return Math.max(arrowHeight, 3);
/*  37:    */     }
/*  38:    */     
/*  39:    */     protected boolean isPaintingNorthBottom()
/*  40:    */     {
/*  41: 81 */       return true;
/*  42:    */     }
/*  43:    */     
/*  44:    */     protected int calculateArrowOffset()
/*  45:    */     {
/*  46: 85 */       return 1;
/*  47:    */     }
/*  48:    */     
/*  49:    */     protected void paintNorth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset, boolean paintBottom)
/*  50:    */     {
/*  51: 92 */       if (!this.isFreeStanding)
/*  52:    */       {
/*  53: 93 */         height++;
/*  54: 94 */         g.translate(0, -1);
/*  55: 95 */         if (!leftToRight)
/*  56:    */         {
/*  57: 96 */           width++;
/*  58: 97 */           g.translate(-1, 0);
/*  59:    */         }
/*  60:    */         else
/*  61:    */         {
/*  62: 99 */           width += 2;
/*  63:    */         }
/*  64:    */       }
/*  65:104 */       g.setColor(arrowColor);
/*  66:105 */       int startY = 1 + (h + 1 - arrowHeight) / 2;
/*  67:106 */       int startX = w / 2;
/*  68:108 */       for (int line = 0; line < arrowHeight; line++) {
/*  69:109 */         g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1);
/*  70:    */       }
/*  71:113 */       paintNorthBorder(g, isEnabled, width, height, paintBottom);
/*  72:115 */       if (!this.isFreeStanding)
/*  73:    */       {
/*  74:116 */         height--;
/*  75:117 */         g.translate(0, 1);
/*  76:118 */         if (!leftToRight)
/*  77:    */         {
/*  78:119 */           width--;
/*  79:120 */           g.translate(1, 0);
/*  80:    */         }
/*  81:    */         else
/*  82:    */         {
/*  83:122 */           width -= 2;
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */     
/*  88:    */     private void paintNorthBorder(Graphics g, boolean isEnabled, int w, int h, boolean paintBottom)
/*  89:    */     {
/*  90:128 */       if (isEnabled)
/*  91:    */       {
/*  92:129 */         boolean isPressed = (this.model.isPressed()) && (this.model.isArmed());
/*  93:130 */         if (isPressed) {
/*  94:131 */           PlasticXPUtils.drawPressedButtonBorder(g, 0, 1, w - 2, h);
/*  95:    */         } else {
/*  96:133 */           PlasticXPUtils.drawPlainButtonBorder(g, 0, 1, w - 2, h);
/*  97:    */         }
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101:136 */         PlasticXPUtils.drawDisabledButtonBorder(g, 0, 1, w - 2, h + 1);
/* 102:    */       }
/* 103:139 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 104:    */       
/* 105:    */ 
/* 106:142 */       g.fillRect(0, 1, 1, 1);
/* 107:144 */       if (paintBottom) {
/* 108:145 */         g.fillRect(0, h - 1, w - 1, 1);
/* 109:    */       }
/* 110:    */     }
/* 111:    */     
/* 112:    */     protected void paintSouth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset)
/* 113:    */     {
/* 114:154 */       if (!this.isFreeStanding)
/* 115:    */       {
/* 116:155 */         height++;
/* 117:156 */         if (!leftToRight)
/* 118:    */         {
/* 119:157 */           width++;
/* 120:158 */           g.translate(-1, 0);
/* 121:    */         }
/* 122:    */         else
/* 123:    */         {
/* 124:160 */           width += 2;
/* 125:    */         }
/* 126:    */       }
/* 127:165 */       g.setColor(arrowColor);
/* 128:    */       
/* 129:167 */       int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 2;
/* 130:168 */       int startX = w / 2;
/* 131:172 */       for (int line = 0; line < arrowHeight; line++) {
/* 132:173 */         g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1);
/* 133:    */       }
/* 134:177 */       paintSouthBorder(g, isEnabled, width, height);
/* 135:179 */       if (!this.isFreeStanding)
/* 136:    */       {
/* 137:180 */         height--;
/* 138:181 */         if (!leftToRight)
/* 139:    */         {
/* 140:182 */           width--;
/* 141:183 */           g.translate(1, 0);
/* 142:    */         }
/* 143:    */         else
/* 144:    */         {
/* 145:185 */           width -= 2;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */     
/* 150:    */     private void paintSouthBorder(Graphics g, boolean isEnabled, int w, int h)
/* 151:    */     {
/* 152:191 */       if (isEnabled)
/* 153:    */       {
/* 154:192 */         boolean isPressed = (this.model.isPressed()) && (this.model.isArmed());
/* 155:193 */         if (isPressed) {
/* 156:194 */           PlasticXPUtils.drawPressedButtonBorder(g, 0, -2, w - 2, h + 1);
/* 157:    */         } else {
/* 158:196 */           PlasticXPUtils.drawPlainButtonBorder(g, 0, -2, w - 2, h + 1);
/* 159:    */         }
/* 160:    */       }
/* 161:    */       else
/* 162:    */       {
/* 163:199 */         PlasticXPUtils.drawDisabledButtonBorder(g, 0, -2, w - 2, h + 1);
/* 164:    */       }
/* 165:202 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 166:    */       
/* 167:    */ 
/* 168:205 */       g.fillRect(0, h - 2, 1, 1);
/* 169:    */     }
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPSpinnerUI
 * JD-Core Version:    0.7.0.1
 */