/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTreeUI;
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
/*     */ public final class PlasticTreeUI
/*     */   extends BasicTreeUI
/*     */ {
/*     */   private boolean linesEnabled;
/*     */   private PropertyChangeListener lineStyleHandler;
/*     */   
/*     */   public PlasticTreeUI()
/*     */   {
/*  78 */     this.linesEnabled = true;
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  83 */     return new PlasticTreeUI();
/*     */   }
/*     */   
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  89 */     super.installUI(c);
/*  90 */     updateLineStyle(c.getClientProperty("JTree.lineStyle"));
/*  91 */     this.lineStyleHandler = new LineStyleHandler(null);
/*  92 */     c.addPropertyChangeListener(this.lineStyleHandler);
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent c) {
/*  96 */     c.removePropertyChangeListener(this.lineStyleHandler);
/*  97 */     super.uninstallUI(c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom)
/*     */   {
/* 104 */     if (this.linesEnabled) {
/* 105 */       drawDashedVerticalLine(g, x, top, bottom);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
/* 110 */     if (this.linesEnabled) {
/* 111 */       drawDashedHorizontalLine(g, y, left, right);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void drawCentered(Component c, Graphics graphics, Icon icon, int x, int y)
/*     */   {
/* 117 */     icon.paintIcon(c, graphics, x - icon.getIconWidth() / 2 - 1, y - icon.getIconHeight() / 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 127 */   private void updateLineStyle(Object lineStyle) { this.linesEnabled = (!"None".equals(lineStyle)); }
/*     */   
/*     */   private class LineStyleHandler implements PropertyChangeListener {
/*     */     private LineStyleHandler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 133 */       String name = e.getPropertyName();
/* 134 */       Object value = e.getNewValue();
/* 135 */       if (name.equals("JTree.lineStyle")) {
/* 136 */         PlasticTreeUI.this.updateLineStyle(value);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticTreeUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */