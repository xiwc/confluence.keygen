/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalToggleButtonUI;
/*     */ import javax.swing.text.View;
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
/*     */ public class PlasticToggleButtonUI
/*     */   extends MetalToggleButtonUI
/*     */ {
/*  51 */   private static final PlasticToggleButtonUI INSTANCE = new PlasticToggleButtonUI();
/*     */   
/*     */ 
/*     */ 
/*     */   protected static final String HTML_KEY = "html";
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean borderPaintsFocus;
/*     */   
/*     */ 
/*     */ 
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  65 */     return INSTANCE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void installDefaults(AbstractButton b)
/*     */   {
/*  72 */     super.installDefaults(b);
/*  73 */     this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("ToggleButton.borderPaintsFocus"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g, JComponent c)
/*     */   {
/*  82 */     AbstractButton b = (AbstractButton)c;
/*  83 */     if (c.isOpaque()) {
/*  84 */       if (isToolBarButton(b)) {
/*  85 */         c.setOpaque(false);
/*  86 */       } else if (b.isContentAreaFilled()) {
/*  87 */         g.setColor(c.getBackground());
/*  88 */         g.fillRect(0, 0, c.getWidth(), c.getHeight());
/*     */         
/*  90 */         if (is3D(b)) {
/*  91 */           Rectangle r = new Rectangle(1, 1, c.getWidth() - 2, c.getHeight() - 1);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */           PlasticUtils.add3DEffekt(g, r);
/*     */         }
/*     */       }
/*     */     }
/* 101 */     paint(g, c);
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
/*     */   protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
/*     */   {
/* 114 */     if (this.borderPaintsFocus) {
/* 115 */       return;
/*     */     }
/* 117 */     boolean isDefault = false;
/* 118 */     int topLeftInset = isDefault ? 3 : 2;
/* 119 */     int width = b.getWidth() - 1 - topLeftInset * 2;
/* 120 */     int height = b.getHeight() - 1 - topLeftInset * 2;
/*     */     
/* 122 */     g.setColor(getFocusColor());
/* 123 */     g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g, JComponent c)
/*     */   {
/* 131 */     AbstractButton b = (AbstractButton)c;
/* 132 */     ButtonModel model = b.getModel();
/*     */     
/* 134 */     Dimension size = b.getSize();
/* 135 */     FontMetrics fm = g.getFontMetrics();
/*     */     
/* 137 */     Insets i = c.getInsets();
/*     */     
/* 139 */     Rectangle viewRect = new Rectangle(size);
/*     */     
/* 141 */     viewRect.x += i.left;
/* 142 */     viewRect.y += i.top;
/* 143 */     viewRect.width -= i.right + viewRect.x;
/* 144 */     viewRect.height -= i.bottom + viewRect.y;
/*     */     
/* 146 */     Rectangle iconRect = new Rectangle();
/* 147 */     Rectangle textRect = new Rectangle();
/*     */     
/* 149 */     Font f = c.getFont();
/* 150 */     g.setFont(f);
/*     */     
/*     */ 
/* 153 */     String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap());
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
/* 168 */     g.setColor(b.getBackground());
/*     */     
/* 170 */     if (((model.isArmed()) && (model.isPressed())) || (model.isSelected())) {
/* 171 */       paintButtonPressed(g, b);
/*     */     }
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
/* 185 */     if (b.getIcon() != null) {
/* 186 */       paintIcon(g, b, iconRect);
/*     */     }
/*     */     
/*     */ 
/* 190 */     if ((text != null) && (!text.equals(""))) {
/* 191 */       View v = (View)c.getClientProperty("html");
/* 192 */       if (v != null) {
/* 193 */         v.paint(g, textRect);
/*     */       } else {
/* 195 */         paintText(g, c, textRect, text);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 200 */     if ((b.isFocusPainted()) && (b.hasFocus())) {
/* 201 */       paintFocus(g, b, viewRect, textRect, iconRect);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isToolBarButton(AbstractButton b)
/*     */   {
/* 214 */     Container parent = b.getParent();
/* 215 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean is3D(AbstractButton b)
/*     */   {
/* 227 */     if (PlasticUtils.force3D(b))
/* 228 */       return true;
/* 229 */     if (PlasticUtils.forceFlat(b))
/* 230 */       return false;
/* 231 */     ButtonModel model = b.getModel();
/* 232 */     return (PlasticUtils.is3D("ToggleButton.")) && (b.isBorderPainted()) && (model.isEnabled()) && (!model.isPressed());
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticToggleButtonUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */