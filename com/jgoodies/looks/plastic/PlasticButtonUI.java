/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalButtonUI;
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
/*     */ public class PlasticButtonUI
/*     */   extends MetalButtonUI
/*     */ {
/*  51 */   private static final PlasticButtonUI INSTANCE = new PlasticButtonUI();
/*     */   private boolean borderPaintsFocus;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  56 */     return INSTANCE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void installDefaults(AbstractButton b)
/*     */   {
/*  63 */     super.installDefaults(b);
/*  64 */     this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("Button.borderPaintsFocus"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g, JComponent c)
/*     */   {
/*  72 */     if (c.isOpaque()) {
/*  73 */       AbstractButton b = (AbstractButton)c;
/*  74 */       if (isToolBarButton(b)) {
/*  75 */         c.setOpaque(false);
/*  76 */       } else if (b.isContentAreaFilled()) {
/*  77 */         g.setColor(c.getBackground());
/*  78 */         g.fillRect(0, 0, c.getWidth(), c.getHeight());
/*     */         
/*  80 */         if (is3D(b)) {
/*  81 */           Rectangle r = new Rectangle(1, 1, c.getWidth() - 2, c.getHeight() - 1);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */           PlasticUtils.add3DEffekt(g, r);
/*     */         }
/*     */       }
/*     */     }
/*  91 */     paint(g, c);
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
/* 104 */     if (this.borderPaintsFocus) {
/* 105 */       return;
/*     */     }
/*     */     
/* 108 */     boolean isDefault = ((b instanceof JButton)) && (((JButton)b).isDefaultButton());
/*     */     
/* 110 */     int topLeftInset = isDefault ? 3 : 2;
/* 111 */     int width = b.getWidth() - 1 - topLeftInset * 2;
/* 112 */     int height = b.getHeight() - 1 - topLeftInset * 2;
/*     */     
/* 114 */     g.setColor(getFocusColor());
/* 115 */     g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
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
/* 127 */     Container parent = b.getParent();
/* 128 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
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
/* 140 */     if (PlasticUtils.force3D(b))
/* 141 */       return true;
/* 142 */     if (PlasticUtils.forceFlat(b))
/* 143 */       return false;
/* 144 */     ButtonModel model = b.getModel();
/* 145 */     return (PlasticUtils.is3D("Button.")) && (b.isBorderPainted()) && (model.isEnabled()) && ((!model.isPressed()) || (!model.isArmed()));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticButtonUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */