/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Insets;
/*   9:    */ import java.awt.Rectangle;
/*  10:    */ import javax.swing.AbstractButton;
/*  11:    */ import javax.swing.ButtonModel;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JToolBar;
/*  14:    */ import javax.swing.SwingUtilities;
/*  15:    */ import javax.swing.UIManager;
/*  16:    */ import javax.swing.plaf.ComponentUI;
/*  17:    */ import javax.swing.plaf.metal.MetalToggleButtonUI;
/*  18:    */ import javax.swing.text.View;
/*  19:    */ 
/*  20:    */ public class PlasticToggleButtonUI
/*  21:    */   extends MetalToggleButtonUI
/*  22:    */ {
/*  23: 51 */   private static final PlasticToggleButtonUI INSTANCE = new PlasticToggleButtonUI();
/*  24:    */   protected static final String HTML_KEY = "html";
/*  25:    */   private boolean borderPaintsFocus;
/*  26:    */   
/*  27:    */   public static ComponentUI createUI(JComponent b)
/*  28:    */   {
/*  29: 65 */     return INSTANCE;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void installDefaults(AbstractButton b)
/*  33:    */   {
/*  34: 72 */     super.installDefaults(b);
/*  35: 73 */     this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("ToggleButton.borderPaintsFocus"));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void update(Graphics g, JComponent c)
/*  39:    */   {
/*  40: 82 */     AbstractButton b = (AbstractButton)c;
/*  41: 83 */     if (c.isOpaque()) {
/*  42: 84 */       if (isToolBarButton(b))
/*  43:    */       {
/*  44: 85 */         c.setOpaque(false);
/*  45:    */       }
/*  46: 86 */       else if (b.isContentAreaFilled())
/*  47:    */       {
/*  48: 87 */         g.setColor(c.getBackground());
/*  49: 88 */         g.fillRect(0, 0, c.getWidth(), c.getHeight());
/*  50: 90 */         if (is3D(b))
/*  51:    */         {
/*  52: 91 */           Rectangle r = new Rectangle(1, 1, c.getWidth() - 2, c.getHeight() - 1);
/*  53:    */           
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58: 97 */           PlasticUtils.add3DEffekt(g, r);
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:101 */     paint(g, c);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
/*  66:    */   {
/*  67:114 */     if (this.borderPaintsFocus) {
/*  68:115 */       return;
/*  69:    */     }
/*  70:117 */     boolean isDefault = false;
/*  71:118 */     int topLeftInset = isDefault ? 3 : 2;
/*  72:119 */     int width = b.getWidth() - 1 - topLeftInset * 2;
/*  73:120 */     int height = b.getHeight() - 1 - topLeftInset * 2;
/*  74:    */     
/*  75:122 */     g.setColor(getFocusColor());
/*  76:123 */     g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void paint(Graphics g, JComponent c)
/*  80:    */   {
/*  81:131 */     AbstractButton b = (AbstractButton)c;
/*  82:132 */     ButtonModel model = b.getModel();
/*  83:    */     
/*  84:134 */     Dimension size = b.getSize();
/*  85:135 */     FontMetrics fm = g.getFontMetrics();
/*  86:    */     
/*  87:137 */     Insets i = c.getInsets();
/*  88:    */     
/*  89:139 */     Rectangle viewRect = new Rectangle(size);
/*  90:    */     
/*  91:141 */     viewRect.x += i.left;
/*  92:142 */     viewRect.y += i.top;
/*  93:143 */     viewRect.width -= i.right + viewRect.x;
/*  94:144 */     viewRect.height -= i.bottom + viewRect.y;
/*  95:    */     
/*  96:146 */     Rectangle iconRect = new Rectangle();
/*  97:147 */     Rectangle textRect = new Rectangle();
/*  98:    */     
/*  99:149 */     Font f = c.getFont();
/* 100:150 */     g.setFont(f);
/* 101:    */     
/* 102:    */ 
/* 103:153 */     String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap());
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
/* 116:    */ 
/* 117:    */ 
/* 118:168 */     g.setColor(b.getBackground());
/* 119:170 */     if (((model.isArmed()) && (model.isPressed())) || (model.isSelected())) {
/* 120:171 */       paintButtonPressed(g, b);
/* 121:    */     }
/* 122:185 */     if (b.getIcon() != null) {
/* 123:186 */       paintIcon(g, b, iconRect);
/* 124:    */     }
/* 125:190 */     if ((text != null) && (!text.equals("")))
/* 126:    */     {
/* 127:191 */       View v = (View)c.getClientProperty("html");
/* 128:192 */       if (v != null) {
/* 129:193 */         v.paint(g, textRect);
/* 130:    */       } else {
/* 131:195 */         paintText(g, c, textRect, text);
/* 132:    */       }
/* 133:    */     }
/* 134:200 */     if ((b.isFocusPainted()) && (b.hasFocus())) {
/* 135:201 */       paintFocus(g, b, viewRect, textRect, iconRect);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected boolean isToolBarButton(AbstractButton b)
/* 140:    */   {
/* 141:214 */     Container parent = b.getParent();
/* 142:215 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected boolean is3D(AbstractButton b)
/* 146:    */   {
/* 147:227 */     if (PlasticUtils.force3D(b)) {
/* 148:228 */       return true;
/* 149:    */     }
/* 150:229 */     if (PlasticUtils.forceFlat(b)) {
/* 151:230 */       return false;
/* 152:    */     }
/* 153:231 */     ButtonModel model = b.getModel();
/* 154:232 */     return (PlasticUtils.is3D("ToggleButton.")) && (b.isBorderPainted()) && (model.isEnabled()) && (!model.isPressed());
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticToggleButtonUI
 * JD-Core Version:    0.7.0.1
 */