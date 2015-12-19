/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import javax.swing.Icon;
/*   8:    */ import javax.swing.JComponent;
/*   9:    */ import javax.swing.JMenuItem;
/*  10:    */ import javax.swing.UIManager;
/*  11:    */ import javax.swing.plaf.ComponentUI;
/*  12:    */ import javax.swing.plaf.basic.BasicMenuItemUI;
/*  13:    */ 
/*  14:    */ public class ExtBasicMenuItemUI
/*  15:    */   extends BasicMenuItemUI
/*  16:    */ {
/*  17:    */   private static final int MINIMUM_WIDTH = 80;
/*  18:    */   private MenuItemRenderer renderer;
/*  19:    */   
/*  20:    */   public static ComponentUI createUI(JComponent b)
/*  21:    */   {
/*  22: 63 */     return new ExtBasicMenuItemUI();
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected void installDefaults()
/*  26:    */   {
/*  27: 67 */     super.installDefaults();
/*  28: 68 */     this.renderer = createRenderer(this.menuItem, iconBorderEnabled(), this.acceleratorFont, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36: 76 */     Integer gap = (Integer)UIManager.get(getPropertyPrefix() + ".textIconGap");
/*  37:    */     
/*  38: 78 */     this.defaultTextIconGap = (gap != null ? gap.intValue() : 2);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected boolean iconBorderEnabled()
/*  42:    */   {
/*  43: 83 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void uninstallDefaults()
/*  47:    */   {
/*  48: 87 */     super.uninstallDefaults();
/*  49: 88 */     this.renderer = null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon aCheckIcon, Icon anArrowIcon, int textIconGap)
/*  53:    */   {
/*  54: 97 */     Dimension size = this.renderer.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:103 */     int width = Math.max(80, size.width);
/*  61:104 */     int height = size.height;
/*  62:105 */     return new Dimension(width, height);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*  66:    */   {
/*  67:116 */     this.renderer.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected MenuItemRenderer createRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground)
/*  71:    */   {
/*  72:134 */     return new MenuItemRenderer(menuItem, iconBorderEnabled(), acceleratorFont, selectionForeground, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicMenuItemUI
 * JD-Core Version:    0.7.0.1
 */