/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicMenuUI;
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
/*     */ public class ExtBasicMenuUI
/*     */   extends BasicMenuUI
/*     */ {
/*     */   private static final String MENU_PROPERTY_PREFIX = "Menu";
/*     */   private static final String SUBMENU_PROPERTY_PREFIX = "MenuItem";
/*     */   private String propertyPrefix;
/*     */   private MenuItemRenderer renderer;
/*     */   private MouseListener mouseListener;
/*     */   
/*     */   public ExtBasicMenuUI()
/*     */   {
/*  59 */     this.propertyPrefix = "Menu";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  66 */     return new ExtBasicMenuUI();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void installDefaults()
/*     */   {
/*  73 */     super.installDefaults();
/*  74 */     if ((this.arrowIcon == null) || ((this.arrowIcon instanceof UIResource))) {
/*  75 */       this.arrowIcon = UIManager.getIcon("Menu.arrowIcon");
/*     */     }
/*  77 */     this.renderer = new MenuItemRenderer(this.menuItem, false, this.acceleratorFont, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */     Integer gap = (Integer)UIManager.get(getPropertyPrefix() + ".textIconGap");
/*     */     
/*  88 */     this.defaultTextIconGap = (gap != null ? gap.intValue() : 2);
/*  89 */     LookAndFeel.installBorder(this.menuItem, getPropertyPrefix() + ".border");
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/*  93 */     super.uninstallDefaults();
/*  94 */     this.renderer = null;
/*     */   }
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  98 */     return this.propertyPrefix;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon aCheckIcon, Icon anArrowIcon, int textIconGap)
/*     */   {
/* 107 */     if (isSubMenu(this.menuItem)) {
/* 108 */       ensureSubMenuInstalled();
/* 109 */       return this.renderer.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 115 */     return super.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
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
/*     */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*     */   {
/* 130 */     if (isSubMenu(this.menuItem)) {
/* 131 */       this.renderer.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*     */ 
/* 140 */       super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*     */     }
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
/*     */   private void ensureSubMenuInstalled()
/*     */   {
/* 156 */     if (this.propertyPrefix.equals("MenuItem")) {
/* 157 */       return;
/*     */     }
/* 159 */     ButtonModel model = this.menuItem.getModel();
/*     */     
/*     */ 
/*     */ 
/* 163 */     boolean oldArmed = model.isArmed();
/* 164 */     boolean oldSelected = model.isSelected();
/*     */     
/* 166 */     uninstallRolloverListener();
/* 167 */     uninstallDefaults();
/* 168 */     this.propertyPrefix = "MenuItem";
/* 169 */     installDefaults();
/*     */     
/*     */ 
/* 172 */     model.setArmed(oldArmed);
/* 173 */     model.setSelected(oldSelected);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void installListeners()
/*     */   {
/* 180 */     super.installListeners();
/* 181 */     this.mouseListener = new RolloverHandler(null);
/* 182 */     this.menuItem.addMouseListener(this.mouseListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 186 */     super.uninstallListeners();
/* 187 */     uninstallRolloverListener();
/*     */   }
/*     */   
/*     */   private void uninstallRolloverListener() {
/* 191 */     if (this.mouseListener != null) {
/* 192 */       this.menuItem.removeMouseListener(this.mouseListener);
/* 193 */       this.mouseListener = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isSubMenu(JMenuItem aMenuItem)
/*     */   {
/* 201 */     return !((JMenu)aMenuItem).isTopLevelMenu();
/*     */   }
/*     */   
/*     */   private static final class RolloverHandler extends MouseAdapter
/*     */   {
/*     */     public void mouseEntered(MouseEvent e)
/*     */     {
/* 208 */       AbstractButton b = (AbstractButton)e.getSource();
/* 209 */       b.getModel().setRollover(true);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent e) {
/* 213 */       AbstractButton b = (AbstractButton)e.getSource();
/* 214 */       b.getModel().setRollover(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicMenuUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */