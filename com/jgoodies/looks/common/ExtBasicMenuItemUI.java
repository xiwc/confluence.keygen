/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuItemUI;
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
/*     */ public class ExtBasicMenuItemUI
/*     */   extends BasicMenuItemUI
/*     */ {
/*     */   private static final int MINIMUM_WIDTH = 80;
/*     */   private MenuItemRenderer renderer;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  63 */     return new ExtBasicMenuItemUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  67 */     super.installDefaults();
/*  68 */     this.renderer = createRenderer(this.menuItem, iconBorderEnabled(), this.acceleratorFont, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  76 */     Integer gap = (Integer)UIManager.get(getPropertyPrefix() + ".textIconGap");
/*     */     
/*  78 */     this.defaultTextIconGap = (gap != null ? gap.intValue() : 2);
/*     */   }
/*     */   
/*     */   protected boolean iconBorderEnabled()
/*     */   {
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/*  87 */     super.uninstallDefaults();
/*  88 */     this.renderer = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon aCheckIcon, Icon anArrowIcon, int textIconGap)
/*     */   {
/*  97 */     Dimension size = this.renderer.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */     int width = Math.max(80, size.width);
/* 104 */     int height = size.height;
/* 105 */     return new Dimension(width, height);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*     */   {
/* 116 */     this.renderer.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MenuItemRenderer createRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground)
/*     */   {
/* 134 */     return new MenuItemRenderer(menuItem, iconBorderEnabled(), acceleratorFont, selectionForeground, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicMenuItemUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */