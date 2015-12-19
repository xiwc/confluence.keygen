/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.BorderStyle;
/*     */ import com.jgoodies.looks.HeaderStyle;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.metal.MetalToolBarUI;
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
/*     */ 
/*     */ 
/*     */ public class PlasticToolBarUI
/*     */   extends MetalToolBarUI
/*     */ {
/*     */   private static final String PROPERTY_PREFIX = "ToolBar.";
/*     */   private PropertyChangeListener listener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  67 */     return new PlasticToolBarUI();
/*     */   }
/*     */   
/*     */ 
/*     */   protected Border createRolloverBorder()
/*     */   {
/*  73 */     return PlasticBorders.getRolloverButtonBorder();
/*     */   }
/*     */   
/*     */   protected void setBorderToRollover(Component c) {
/*  77 */     if ((c instanceof AbstractButton)) {
/*  78 */       super.setBorderToRollover(c);
/*  79 */     } else if ((c instanceof Container)) {
/*  80 */       Container cont = (Container)c;
/*  81 */       for (int i = 0; i < cont.getComponentCount(); i++) {
/*  82 */         super.setBorderToRollover(cont.getComponent(i));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  92 */     super.installDefaults();
/*  93 */     installSpecialBorder();
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  97 */     super.installListeners();
/*  98 */     this.listener = createBorderStyleListener();
/*  99 */     this.toolBar.addPropertyChangeListener(this.listener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 103 */     this.toolBar.removePropertyChangeListener(this.listener);
/* 104 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   private PropertyChangeListener createBorderStyleListener() {
/* 108 */     new PropertyChangeListener()
/*     */     {
/*     */       public void propertyChange(PropertyChangeEvent e) {
/* 111 */         String prop = e.getPropertyName();
/* 112 */         if ((prop.equals("jgoodies.headerStyle")) || (prop.equals("Plastic.borderStyle")))
/*     */         {
/* 114 */           PlasticToolBarUI.this.installSpecialBorder();
/*     */         }
/*     */       }
/*     */     };
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
/*     */ 
/*     */ 
/*     */   private void installSpecialBorder()
/*     */   {
/* 132 */     BorderStyle borderStyle = BorderStyle.from(this.toolBar, "Plastic.borderStyle");
/*     */     String suffix;
/* 134 */     String suffix; if (borderStyle == BorderStyle.EMPTY) {
/* 135 */       suffix = "emptyBorder"; } else { String suffix;
/* 136 */       if (borderStyle == BorderStyle.ETCHED) {
/* 137 */         suffix = "etchedBorder"; } else { String suffix;
/* 138 */         if (borderStyle == BorderStyle.SEPARATOR) {
/* 139 */           suffix = "separatorBorder";
/*     */         } else {
/* 141 */           HeaderStyle headerStyle = HeaderStyle.from(this.toolBar);
/* 142 */           String suffix; if (headerStyle == HeaderStyle.BOTH) {
/* 143 */             suffix = "headerBorder"; } else { String suffix;
/* 144 */             if ((headerStyle == HeaderStyle.SINGLE) && (is3D())) {
/* 145 */               suffix = "etchedBorder";
/*     */             } else
/* 147 */               suffix = "border";
/*     */           } } } }
/* 149 */     LookAndFeel.installBorder(this.toolBar, "ToolBar." + suffix);
/*     */   }
/*     */   
/*     */ 
/*     */   public void update(Graphics g, JComponent c)
/*     */   {
/* 155 */     if (c.isOpaque()) {
/* 156 */       g.setColor(c.getBackground());
/* 157 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 158 */       if (is3D()) {
/* 159 */         Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/*     */         
/* 161 */         boolean isHorizontal = ((JToolBar)c).getOrientation() == 0;
/*     */         
/* 163 */         PlasticUtils.addLight3DEffekt(g, bounds, isHorizontal);
/*     */       }
/*     */     }
/* 166 */     paint(g, c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean is3D()
/*     */   {
/* 173 */     if (PlasticUtils.force3D(this.toolBar))
/* 174 */       return true;
/* 175 */     if (PlasticUtils.forceFlat(this.toolBar))
/* 176 */       return false;
/* 177 */     return (PlasticUtils.is3D("ToolBar.")) && (HeaderStyle.from(this.toolBar) != null) && (BorderStyle.from(this.toolBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticToolBarUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */