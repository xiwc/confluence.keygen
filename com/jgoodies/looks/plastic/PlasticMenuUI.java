/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import com.jgoodies.looks.common.ExtBasicMenuUI;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.Icon;
/*   9:    */ import javax.swing.JComponent;
/*  10:    */ import javax.swing.JMenu;
/*  11:    */ import javax.swing.JMenuItem;
/*  12:    */ import javax.swing.plaf.ComponentUI;
/*  13:    */ 
/*  14:    */ public final class PlasticMenuUI
/*  15:    */   extends ExtBasicMenuUI
/*  16:    */ {
/*  17:    */   private boolean oldOpaque;
/*  18:    */   
/*  19:    */   public static ComponentUI createUI(JComponent b)
/*  20:    */   {
/*  21: 63 */     return new PlasticMenuUI();
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void installDefaults()
/*  25:    */   {
/*  26: 67 */     super.installDefaults();
/*  27: 68 */     this.oldOpaque = this.menuItem.isOpaque();
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void uninstallDefaults()
/*  31:    */   {
/*  32: 72 */     super.uninstallDefaults();
/*  33: 75 */     if ((!LookUtils.IS_OS_WINDOWS_VISTA) && (!LookUtils.IS_JAVA_6_OR_LATER)) {
/*  34: 76 */       this.menuItem.setOpaque(this.oldOpaque);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*  39:    */   {
/*  40: 87 */     JMenuItem b = (JMenuItem)c;
/*  41: 89 */     if (((JMenu)this.menuItem).isTopLevelMenu())
/*  42:    */     {
/*  43: 90 */       b.setOpaque(false);
/*  44: 91 */       if (b.getModel().isSelected())
/*  45:    */       {
/*  46: 92 */         int menuWidth = this.menuItem.getWidth();
/*  47: 93 */         int menuHeight = this.menuItem.getHeight();
/*  48: 94 */         Color oldColor = g.getColor();
/*  49: 95 */         g.setColor(background);
/*  50: 96 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*  51: 97 */         g.setColor(oldColor);
/*  52:    */       }
/*  53:    */     }
/*  54:100 */     super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*  55:    */   }
/*  56:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticMenuUI
 * JD-Core Version:    0.7.0.1
 */