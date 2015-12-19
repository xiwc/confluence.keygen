/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import com.jgoodies.looks.common.ExtBasicMenuUI;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.plaf.ComponentUI;
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
/*     */ public final class PlasticMenuUI
/*     */   extends ExtBasicMenuUI
/*     */ {
/*     */   private boolean oldOpaque;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  63 */     return new PlasticMenuUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  67 */     super.installDefaults();
/*  68 */     this.oldOpaque = this.menuItem.isOpaque();
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/*  72 */     super.uninstallDefaults();
/*     */     
/*     */ 
/*  75 */     if ((!LookUtils.IS_OS_WINDOWS_VISTA) && (!LookUtils.IS_JAVA_6_OR_LATER)) {
/*  76 */       this.menuItem.setOpaque(this.oldOpaque);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*     */   {
/*  87 */     JMenuItem b = (JMenuItem)c;
/*     */     
/*  89 */     if (((JMenu)this.menuItem).isTopLevelMenu()) {
/*  90 */       b.setOpaque(false);
/*  91 */       if (b.getModel().isSelected()) {
/*  92 */         int menuWidth = this.menuItem.getWidth();
/*  93 */         int menuHeight = this.menuItem.getHeight();
/*  94 */         Color oldColor = g.getColor();
/*  95 */         g.setColor(background);
/*  96 */         g.fillRect(0, 0, menuWidth, menuHeight);
/*  97 */         g.setColor(oldColor);
/*     */       }
/*     */     }
/* 100 */     super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticMenuUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */