/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.BorderStyle;
/*     */ import com.jgoodies.looks.HeaderStyle;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuBarUI;
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
/*     */ 
/*     */ public final class PlasticMenuBarUI
/*     */   extends BasicMenuBarUI
/*     */ {
/*     */   private PropertyChangeListener listener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  63 */     return new PlasticMenuBarUI();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  70 */     super.installDefaults();
/*  71 */     installSpecialBorder();
/*     */   }
/*     */   
/*     */   protected void installListeners()
/*     */   {
/*  76 */     super.installListeners();
/*  77 */     this.listener = createBorderStyleListener();
/*  78 */     this.menuBar.addPropertyChangeListener(this.listener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners()
/*     */   {
/*  83 */     this.menuBar.removePropertyChangeListener(this.listener);
/*  84 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   private PropertyChangeListener createBorderStyleListener()
/*     */   {
/*  89 */     new PropertyChangeListener()
/*     */     {
/*     */       public void propertyChange(PropertyChangeEvent e) {
/*  92 */         String prop = e.getPropertyName();
/*  93 */         if ((prop.equals("jgoodies.headerStyle")) || (prop.equals("Plastic.borderStyle")))
/*     */         {
/*  95 */           PlasticMenuBarUI.this.installSpecialBorder();
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
/*     */   public void installSpecialBorder()
/*     */   {
/* 112 */     BorderStyle borderStyle = BorderStyle.from(this.menuBar, "Plastic.borderStyle");
/*     */     String suffix;
/* 114 */     if (borderStyle == BorderStyle.EMPTY) {
/* 115 */       suffix = "emptyBorder"; } else { String suffix;
/* 116 */       if (borderStyle == BorderStyle.ETCHED) {
/* 117 */         suffix = "etchedBorder"; } else { String suffix;
/* 118 */         if (borderStyle == BorderStyle.SEPARATOR) {
/* 119 */           suffix = "separatorBorder";
/*     */         } else {
/* 121 */           HeaderStyle headerStyle = HeaderStyle.from(this.menuBar);
/* 122 */           String suffix; if (headerStyle == HeaderStyle.BOTH) {
/* 123 */             suffix = "headerBorder"; } else { String suffix;
/* 124 */             if ((headerStyle == HeaderStyle.SINGLE) && (is3D()))
/* 125 */               suffix = "etchedBorder"; else return;
/*     */           }
/*     */         }
/*     */       } }
/*     */     String suffix;
/* 130 */     LookAndFeel.installBorder(this.menuBar, "MenuBar." + suffix);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void update(Graphics g, JComponent c)
/*     */   {
/* 137 */     if (c.isOpaque()) {
/* 138 */       g.setColor(c.getBackground());
/* 139 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 140 */       if (is3D()) {
/* 141 */         Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/*     */         
/* 143 */         PlasticUtils.addLight3DEffekt(g, bounds, true);
/*     */       }
/*     */     }
/*     */     
/* 147 */     paint(g, c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean is3D()
/*     */   {
/* 155 */     if (PlasticUtils.force3D(this.menuBar))
/* 156 */       return true;
/* 157 */     if (PlasticUtils.forceFlat(this.menuBar))
/* 158 */       return false;
/* 159 */     return (PlasticUtils.is3D("MenuBar.")) && (HeaderStyle.from(this.menuBar) != null) && (BorderStyle.from(this.menuBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticMenuBarUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */