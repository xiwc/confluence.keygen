/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.event.MouseAdapter;
/*   7:    */ import java.awt.event.MouseEvent;
/*   8:    */ import java.awt.event.MouseListener;
/*   9:    */ import javax.swing.AbstractButton;
/*  10:    */ import javax.swing.ButtonModel;
/*  11:    */ import javax.swing.Icon;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JMenu;
/*  14:    */ import javax.swing.JMenuItem;
/*  15:    */ import javax.swing.LookAndFeel;
/*  16:    */ import javax.swing.UIManager;
/*  17:    */ import javax.swing.plaf.ComponentUI;
/*  18:    */ import javax.swing.plaf.UIResource;
/*  19:    */ import javax.swing.plaf.basic.BasicMenuUI;
/*  20:    */ 
/*  21:    */ public class ExtBasicMenuUI
/*  22:    */   extends BasicMenuUI
/*  23:    */ {
/*  24:    */   private static final String MENU_PROPERTY_PREFIX = "Menu";
/*  25:    */   private static final String SUBMENU_PROPERTY_PREFIX = "MenuItem";
/*  26:    */   private String propertyPrefix;
/*  27:    */   private MenuItemRenderer renderer;
/*  28:    */   private MouseListener mouseListener;
/*  29:    */   
/*  30:    */   public ExtBasicMenuUI()
/*  31:    */   {
/*  32: 59 */     this.propertyPrefix = "Menu";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static ComponentUI createUI(JComponent b)
/*  36:    */   {
/*  37: 66 */     return new ExtBasicMenuUI();
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void installDefaults()
/*  41:    */   {
/*  42: 73 */     super.installDefaults();
/*  43: 74 */     if ((this.arrowIcon == null) || ((this.arrowIcon instanceof UIResource))) {
/*  44: 75 */       this.arrowIcon = UIManager.getIcon("Menu.arrowIcon");
/*  45:    */     }
/*  46: 77 */     this.renderer = new MenuItemRenderer(this.menuItem, false, this.acceleratorFont, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 86 */     Integer gap = (Integer)UIManager.get(getPropertyPrefix() + ".textIconGap");
/*  56:    */     
/*  57: 88 */     this.defaultTextIconGap = (gap != null ? gap.intValue() : 2);
/*  58: 89 */     LookAndFeel.installBorder(this.menuItem, getPropertyPrefix() + ".border");
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void uninstallDefaults()
/*  62:    */   {
/*  63: 93 */     super.uninstallDefaults();
/*  64: 94 */     this.renderer = null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected String getPropertyPrefix()
/*  68:    */   {
/*  69: 98 */     return this.propertyPrefix;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected Dimension getPreferredMenuItemSize(JComponent c, Icon aCheckIcon, Icon anArrowIcon, int textIconGap)
/*  73:    */   {
/*  74:107 */     if (isSubMenu(this.menuItem))
/*  75:    */     {
/*  76:108 */       ensureSubMenuInstalled();
/*  77:109 */       return this.renderer.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
/*  78:    */     }
/*  79:115 */     return super.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap)
/*  83:    */   {
/*  84:130 */     if (isSubMenu(this.menuItem)) {
/*  85:131 */       this.renderer.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*  86:    */     } else {
/*  87:140 */       super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void ensureSubMenuInstalled()
/*  92:    */   {
/*  93:156 */     if (this.propertyPrefix.equals("MenuItem")) {
/*  94:157 */       return;
/*  95:    */     }
/*  96:159 */     ButtonModel model = this.menuItem.getModel();
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:163 */     boolean oldArmed = model.isArmed();
/* 101:164 */     boolean oldSelected = model.isSelected();
/* 102:    */     
/* 103:166 */     uninstallRolloverListener();
/* 104:167 */     uninstallDefaults();
/* 105:168 */     this.propertyPrefix = "MenuItem";
/* 106:169 */     installDefaults();
/* 107:    */     
/* 108:    */ 
/* 109:172 */     model.setArmed(oldArmed);
/* 110:173 */     model.setSelected(oldSelected);
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void installListeners()
/* 114:    */   {
/* 115:180 */     super.installListeners();
/* 116:181 */     this.mouseListener = new RolloverHandler(null);
/* 117:182 */     this.menuItem.addMouseListener(this.mouseListener);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected void uninstallListeners()
/* 121:    */   {
/* 122:186 */     super.uninstallListeners();
/* 123:187 */     uninstallRolloverListener();
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void uninstallRolloverListener()
/* 127:    */   {
/* 128:191 */     if (this.mouseListener != null)
/* 129:    */     {
/* 130:192 */       this.menuItem.removeMouseListener(this.mouseListener);
/* 131:193 */       this.mouseListener = null;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private boolean isSubMenu(JMenuItem aMenuItem)
/* 136:    */   {
/* 137:201 */     return !((JMenu)aMenuItem).isTopLevelMenu();
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static final class RolloverHandler
/* 141:    */     extends MouseAdapter
/* 142:    */   {
/* 143:    */     public void mouseEntered(MouseEvent e)
/* 144:    */     {
/* 145:208 */       AbstractButton b = (AbstractButton)e.getSource();
/* 146:209 */       b.getModel().setRollover(true);
/* 147:    */     }
/* 148:    */     
/* 149:    */     public void mouseExited(MouseEvent e)
/* 150:    */     {
/* 151:213 */       AbstractButton b = (AbstractButton)e.getSource();
/* 152:214 */       b.getModel().setRollover(false);
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicMenuUI
 * JD-Core Version:    0.7.0.1
 */