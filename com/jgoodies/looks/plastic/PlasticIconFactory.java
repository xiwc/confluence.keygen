/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
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
/*     */ final class PlasticIconFactory
/*     */ {
/*     */   private static Icon checkBoxIcon;
/*     */   private static Icon checkBoxMenuItemIcon;
/*     */   private static Icon radioButtonMenuItemIcon;
/*     */   private static Icon menuArrowIcon;
/*     */   private static Icon expandedTreeIcon;
/*     */   private static Icon collapsedTreeIcon;
/*     */   private static Icon comboBoxButtonIcon;
/*     */   
/*     */   private static void drawCheck(Graphics g, int x, int y)
/*     */   {
/*  66 */     g.translate(x, y);
/*  67 */     g.drawLine(3, 5, 3, 5);
/*  68 */     g.fillRect(3, 6, 2, 2);
/*  69 */     g.drawLine(4, 8, 9, 3);
/*  70 */     g.drawLine(5, 8, 9, 4);
/*  71 */     g.drawLine(5, 9, 9, 5);
/*  72 */     g.translate(-x, -y);
/*     */   }
/*     */   
/*     */   private static class CheckBoxIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*     */     private static final int SIZE = 13;
/*     */     
/*  80 */     public int getIconWidth() { return 13; }
/*  81 */     public int getIconHeight() { return 13; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/*  84 */       JCheckBox cb = (JCheckBox)c;
/*  85 */       ButtonModel model = cb.getModel();
/*     */       
/*  87 */       if (model.isEnabled()) {
/*  88 */         if (cb.isBorderPaintedFlat()) {
/*  89 */           g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  90 */           g.drawRect(x, y, 11, 11);
/*     */           
/*  92 */           g.setColor(PlasticLookAndFeel.getControlHighlight());
/*  93 */           g.fillRect(x + 1, y + 1, 10, 10);
/*  94 */         } else if ((model.isPressed()) && (model.isArmed())) {
/*  95 */           g.setColor(MetalLookAndFeel.getControlShadow());
/*  96 */           g.fillRect(x, y, 12, 12);
/*  97 */           PlasticUtils.drawPressed3DBorder(g, x, y, 13, 13);
/*     */         } else {
/*  99 */           PlasticUtils.drawFlush3DBorder(g, x, y, 13, 13);
/*     */         }
/* 101 */         g.setColor(MetalLookAndFeel.getControlInfo());
/*     */       } else {
/* 103 */         g.setColor(MetalLookAndFeel.getControlShadow());
/* 104 */         g.drawRect(x, y, 11, 11);
/*     */       }
/*     */       
/* 107 */       if (model.isSelected()) {
/* 108 */         PlasticIconFactory.drawCheck(g, x, y);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class CheckBoxMenuItemIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*     */     private static final int SIZE = 13;
/*     */     
/* 119 */     public int getIconWidth() { return 13; }
/* 120 */     public int getIconHeight() { return 13; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/* 123 */       JMenuItem b = (JMenuItem)c;
/* 124 */       if (b.isSelected()) {
/* 125 */         PlasticIconFactory.drawCheck(g, x, y + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadioButtonMenuItemIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*     */     private static final int SIZE = 13;
/*     */     
/* 135 */     public int getIconWidth() { return 13; }
/* 136 */     public int getIconHeight() { return 13; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/* 139 */       JMenuItem b = (JMenuItem)c;
/* 140 */       if (b.isSelected()) {
/* 141 */         drawDot(g, x, y);
/*     */       }
/*     */     }
/*     */     
/*     */     private void drawDot(Graphics g, int x, int y) {
/* 146 */       g.translate(x, y);
/* 147 */       g.drawLine(5, 4, 8, 4);
/* 148 */       g.fillRect(4, 5, 6, 4);
/* 149 */       g.drawLine(5, 9, 8, 9);
/* 150 */       g.translate(-x, -y);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MenuArrowIcon implements Icon, UIResource, Serializable
/*     */   {
/*     */     private static final int WIDTH = 4;
/*     */     private static final int HEIGHT = 8;
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 161 */       JMenuItem b = (JMenuItem)c;
/*     */       
/* 163 */       g.translate(x, y);
/* 164 */       if (PlasticUtils.isLeftToRight(b)) {
/* 165 */         g.drawLine(0, 0, 0, 7);
/* 166 */         g.drawLine(1, 1, 1, 6);
/* 167 */         g.drawLine(2, 2, 2, 5);
/* 168 */         g.drawLine(3, 3, 3, 4);
/*     */       } else {
/* 170 */         g.drawLine(4, 0, 4, 7);
/* 171 */         g.drawLine(3, 1, 3, 6);
/* 172 */         g.drawLine(2, 2, 2, 5);
/* 173 */         g.drawLine(1, 3, 1, 4);
/*     */       }
/* 175 */       g.translate(-x, -y);
/*     */     }
/*     */     
/* 178 */     public int getIconWidth() { return 4; }
/* 179 */     public int getIconHeight() { return 8; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class ExpandedTreeIcon
/*     */     implements Icon, Serializable
/*     */   {
/*     */     protected static final int SIZE = 9;
/*     */     
/*     */     protected static final int HALF_SIZE = 4;
/*     */     
/*     */ 
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 194 */       g.setColor(Color.WHITE);
/* 195 */       g.fillRect(x, y, 8, 8);
/* 196 */       g.setColor(Color.GRAY);
/* 197 */       g.drawRect(x, y, 8, 8);
/* 198 */       g.setColor(Color.BLACK);
/* 199 */       g.drawLine(x + 2, y + 4, x + 6, y + 4);
/*     */     }
/*     */     
/* 202 */     public int getIconWidth() { return 9; }
/* 203 */     public int getIconHeight() { return 9; }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class CollapsedTreeIcon
/*     */     extends PlasticIconFactory.ExpandedTreeIcon
/*     */   {
/* 210 */     private CollapsedTreeIcon() { super(); }
/*     */     
/* 212 */     public void paintIcon(Component c, Graphics g, int x, int y) { super.paintIcon(c, g, x, y);
/* 213 */       g.drawLine(x + 4, y + 2, x + 4, y + 6);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class ComboBoxButtonIcon
/*     */     implements Icon, Serializable
/*     */   {
/*     */     public void paintIcon(Component c, Graphics g, int x, int y)
/*     */     {
/* 224 */       JComponent component = (JComponent)c;
/* 225 */       int iconWidth = getIconWidth();
/*     */       
/* 227 */       g.translate(x, y);
/*     */       
/* 229 */       g.setColor(component.isEnabled() ? MetalLookAndFeel.getControlInfo() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 232 */       g.drawLine(0, 0, iconWidth - 1, 0);
/* 233 */       g.drawLine(1, 1, 1 + (iconWidth - 3), 1);
/* 234 */       g.drawLine(2, 2, 2 + (iconWidth - 5), 2);
/* 235 */       g.drawLine(3, 3, 3 + (iconWidth - 7), 3);
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
/* 250 */       g.translate(-x, -y);
/*     */     }
/*     */     
/* 253 */     public int getIconWidth() { return 8; }
/* 254 */     public int getIconHeight() { return 4; }
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
/*     */ 
/*     */   static Icon getCheckBoxIcon()
/*     */   {
/* 273 */     if (checkBoxIcon == null) {
/* 274 */       checkBoxIcon = new CheckBoxIcon(null);
/*     */     }
/* 276 */     return checkBoxIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Icon getCheckBoxMenuItemIcon()
/*     */   {
/* 284 */     if (checkBoxMenuItemIcon == null) {
/* 285 */       checkBoxMenuItemIcon = new CheckBoxMenuItemIcon(null);
/*     */     }
/* 287 */     return checkBoxMenuItemIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Icon getRadioButtonMenuItemIcon()
/*     */   {
/* 295 */     if (radioButtonMenuItemIcon == null) {
/* 296 */       radioButtonMenuItemIcon = new RadioButtonMenuItemIcon(null);
/*     */     }
/* 298 */     return radioButtonMenuItemIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Icon getMenuArrowIcon()
/*     */   {
/* 306 */     if (menuArrowIcon == null) {
/* 307 */       menuArrowIcon = new MenuArrowIcon(null);
/*     */     }
/* 309 */     return menuArrowIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static Icon getExpandedTreeIcon()
/*     */   {
/* 317 */     if (expandedTreeIcon == null) {
/* 318 */       expandedTreeIcon = new ExpandedTreeIcon(null);
/*     */     }
/* 320 */     return expandedTreeIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Icon getCollapsedTreeIcon()
/*     */   {
/* 327 */     if (collapsedTreeIcon == null) {
/* 328 */       collapsedTreeIcon = new CollapsedTreeIcon(null);
/*     */     }
/* 330 */     return collapsedTreeIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Icon getComboBoxButtonIcon()
/*     */   {
/* 337 */     if (comboBoxButtonIcon == null) {
/* 338 */       comboBoxButtonIcon = new ComboBoxButtonIcon(null);
/*     */     }
/* 340 */     return comboBoxButtonIcon;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticIconFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */