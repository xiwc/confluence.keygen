/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.beans.PropertyChangeEvent;
/*   6:    */ import java.beans.PropertyChangeListener;
/*   7:    */ import javax.swing.Icon;
/*   8:    */ import javax.swing.JComponent;
/*   9:    */ import javax.swing.plaf.ComponentUI;
/*  10:    */ import javax.swing.plaf.basic.BasicTreeUI;
/*  11:    */ 
/*  12:    */ public final class PlasticTreeUI
/*  13:    */   extends BasicTreeUI
/*  14:    */ {
/*  15:    */   private boolean linesEnabled;
/*  16:    */   private PropertyChangeListener lineStyleHandler;
/*  17:    */   
/*  18:    */   public PlasticTreeUI()
/*  19:    */   {
/*  20: 78 */     this.linesEnabled = true;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static ComponentUI createUI(JComponent b)
/*  24:    */   {
/*  25: 83 */     return new PlasticTreeUI();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void installUI(JComponent c)
/*  29:    */   {
/*  30: 89 */     super.installUI(c);
/*  31: 90 */     updateLineStyle(c.getClientProperty("JTree.lineStyle"));
/*  32: 91 */     this.lineStyleHandler = new LineStyleHandler(null);
/*  33: 92 */     c.addPropertyChangeListener(this.lineStyleHandler);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void uninstallUI(JComponent c)
/*  37:    */   {
/*  38: 96 */     c.removePropertyChangeListener(this.lineStyleHandler);
/*  39: 97 */     super.uninstallUI(c);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom)
/*  43:    */   {
/*  44:104 */     if (this.linesEnabled) {
/*  45:105 */       drawDashedVerticalLine(g, x, top, bottom);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right)
/*  50:    */   {
/*  51:110 */     if (this.linesEnabled) {
/*  52:111 */       drawDashedHorizontalLine(g, y, left, right);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected void drawCentered(Component c, Graphics graphics, Icon icon, int x, int y)
/*  57:    */   {
/*  58:117 */     icon.paintIcon(c, graphics, x - icon.getIconWidth() / 2 - 1, y - icon.getIconHeight() / 2);
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void updateLineStyle(Object lineStyle)
/*  62:    */   {
/*  63:127 */     this.linesEnabled = (!"None".equals(lineStyle));
/*  64:    */   }
/*  65:    */   
/*  66:    */   private class LineStyleHandler
/*  67:    */     implements PropertyChangeListener
/*  68:    */   {
/*  69:    */     private LineStyleHandler() {}
/*  70:    */     
/*  71:    */     public void propertyChange(PropertyChangeEvent e)
/*  72:    */     {
/*  73:133 */       String name = e.getPropertyName();
/*  74:134 */       Object value = e.getNewValue();
/*  75:135 */       if (name.equals("JTree.lineStyle")) {
/*  76:136 */         PlasticTreeUI.this.updateLineStyle(value);
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticTreeUI
 * JD-Core Version:    0.7.0.1
 */