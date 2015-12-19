/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Rectangle;
/*   6:    */ import javax.swing.AbstractButton;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.JButton;
/*   9:    */ import javax.swing.JComponent;
/*  10:    */ import javax.swing.JToolBar;
/*  11:    */ import javax.swing.UIManager;
/*  12:    */ import javax.swing.plaf.ComponentUI;
/*  13:    */ import javax.swing.plaf.metal.MetalButtonUI;
/*  14:    */ 
/*  15:    */ public class PlasticButtonUI
/*  16:    */   extends MetalButtonUI
/*  17:    */ {
/*  18: 51 */   private static final PlasticButtonUI INSTANCE = new PlasticButtonUI();
/*  19:    */   private boolean borderPaintsFocus;
/*  20:    */   
/*  21:    */   public static ComponentUI createUI(JComponent b)
/*  22:    */   {
/*  23: 56 */     return INSTANCE;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void installDefaults(AbstractButton b)
/*  27:    */   {
/*  28: 63 */     super.installDefaults(b);
/*  29: 64 */     this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("Button.borderPaintsFocus"));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void update(Graphics g, JComponent c)
/*  33:    */   {
/*  34: 72 */     if (c.isOpaque())
/*  35:    */     {
/*  36: 73 */       AbstractButton b = (AbstractButton)c;
/*  37: 74 */       if (isToolBarButton(b))
/*  38:    */       {
/*  39: 75 */         c.setOpaque(false);
/*  40:    */       }
/*  41: 76 */       else if (b.isContentAreaFilled())
/*  42:    */       {
/*  43: 77 */         g.setColor(c.getBackground());
/*  44: 78 */         g.fillRect(0, 0, c.getWidth(), c.getHeight());
/*  45: 80 */         if (is3D(b))
/*  46:    */         {
/*  47: 81 */           Rectangle r = new Rectangle(1, 1, c.getWidth() - 2, c.getHeight() - 1);
/*  48:    */           
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 87 */           PlasticUtils.add3DEffekt(g, r);
/*  54:    */         }
/*  55:    */       }
/*  56:    */     }
/*  57: 91 */     paint(g, c);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
/*  61:    */   {
/*  62:104 */     if (this.borderPaintsFocus) {
/*  63:105 */       return;
/*  64:    */     }
/*  65:108 */     boolean isDefault = ((b instanceof JButton)) && (((JButton)b).isDefaultButton());
/*  66:    */     
/*  67:110 */     int topLeftInset = isDefault ? 3 : 2;
/*  68:111 */     int width = b.getWidth() - 1 - topLeftInset * 2;
/*  69:112 */     int height = b.getHeight() - 1 - topLeftInset * 2;
/*  70:    */     
/*  71:114 */     g.setColor(getFocusColor());
/*  72:115 */     g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected boolean isToolBarButton(AbstractButton b)
/*  76:    */   {
/*  77:127 */     Container parent = b.getParent();
/*  78:128 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected boolean is3D(AbstractButton b)
/*  82:    */   {
/*  83:140 */     if (PlasticUtils.force3D(b)) {
/*  84:141 */       return true;
/*  85:    */     }
/*  86:142 */     if (PlasticUtils.forceFlat(b)) {
/*  87:143 */       return false;
/*  88:    */     }
/*  89:144 */     ButtonModel model = b.getModel();
/*  90:145 */     return (PlasticUtils.is3D("Button.")) && (b.isBorderPainted()) && (model.isEnabled()) && ((!model.isPressed()) || (!model.isArmed()));
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticButtonUI
 * JD-Core Version:    0.7.0.1
 */