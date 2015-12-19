/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;
/*     */ import javax.swing.text.JTextComponent;
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
/*     */ final class PlasticBorders
/*     */ {
/*     */   private static Border comboBoxEditorBorder;
/*     */   private static Border comboBoxArrowButtonBorder;
/*     */   private static Border etchedBorder;
/*     */   private static Border flush3DBorder;
/*     */   private static Border menuBarHeaderBorder;
/*     */   private static Border menuBorder;
/*     */   private static Border menuItemBorder;
/*     */   private static Border popupMenuBorder;
/*     */   private static Border noMarginPopupMenuBorder;
/*     */   private static Border rolloverButtonBorder;
/*     */   private static Border scrollPaneBorder;
/*     */   private static Border separatorBorder;
/*     */   private static Border textFieldBorder;
/*     */   private static Border thinLoweredBorder;
/*     */   private static Border thinRaisedBorder;
/*     */   private static Border toolBarHeaderBorder;
/*     */   
/*     */   static Border getButtonBorder(Insets buttonMargin)
/*     */   {
/*  90 */     return new BorderUIResource.CompoundBorderUIResource(new ButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getComboBoxArrowButtonBorder()
/*     */   {
/* 101 */     if (comboBoxArrowButtonBorder == null) {
/* 102 */       comboBoxArrowButtonBorder = new CompoundBorder(new ComboBoxArrowButtonBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 106 */     return comboBoxArrowButtonBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getComboBoxEditorBorder()
/*     */   {
/* 115 */     if (comboBoxEditorBorder == null) {
/* 116 */       comboBoxEditorBorder = new CompoundBorder(new ComboBoxEditorBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 120 */     return comboBoxEditorBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getEtchedBorder()
/*     */   {
/* 130 */     if (etchedBorder == null) {
/* 131 */       etchedBorder = new BorderUIResource.CompoundBorderUIResource(new EtchedBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 135 */     return etchedBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getFlush3DBorder()
/*     */   {
/* 144 */     if (flush3DBorder == null) {
/* 145 */       flush3DBorder = new Flush3DBorder(null);
/*     */     }
/* 147 */     return flush3DBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getInternalFrameBorder()
/*     */   {
/* 156 */     return new InternalFrameBorder(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getMenuBarHeaderBorder()
/*     */   {
/* 166 */     if (menuBarHeaderBorder == null) {
/* 167 */       menuBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBarHeaderBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 171 */     return menuBarHeaderBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getMenuBorder()
/*     */   {
/* 180 */     if (menuBorder == null) {
/* 181 */       menuBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 185 */     return menuBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getMenuItemBorder()
/*     */   {
/* 194 */     if (menuItemBorder == null) {
/* 195 */       menuItemBorder = new BorderUIResource(new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/* 198 */     return menuItemBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getPopupMenuBorder()
/*     */   {
/* 207 */     if (popupMenuBorder == null) {
/* 208 */       popupMenuBorder = new PopupMenuBorder(null);
/*     */     }
/* 210 */     return popupMenuBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getNoMarginPopupMenuBorder()
/*     */   {
/* 220 */     if (noMarginPopupMenuBorder == null) {
/* 221 */       noMarginPopupMenuBorder = new NoMarginPopupMenuBorder(null);
/*     */     }
/* 223 */     return noMarginPopupMenuBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getPaletteBorder()
/*     */   {
/* 232 */     return new PaletteBorder(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getRolloverButtonBorder()
/*     */   {
/* 241 */     if (rolloverButtonBorder == null) {
/* 242 */       rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(null), new RolloverMarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 246 */     return rolloverButtonBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getScrollPaneBorder()
/*     */   {
/* 255 */     if (scrollPaneBorder == null) {
/* 256 */       scrollPaneBorder = new ScrollPaneBorder(null);
/*     */     }
/* 258 */     return scrollPaneBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getSeparatorBorder()
/*     */   {
/* 268 */     if (separatorBorder == null) {
/* 269 */       separatorBorder = new BorderUIResource.CompoundBorderUIResource(new SeparatorBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 273 */     return separatorBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getTextFieldBorder()
/*     */   {
/* 282 */     if (textFieldBorder == null) {
/* 283 */       textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new TextFieldBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 287 */     return textFieldBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getThinLoweredBorder()
/*     */   {
/* 296 */     if (thinLoweredBorder == null) {
/* 297 */       thinLoweredBorder = new ThinLoweredBorder(null);
/*     */     }
/* 299 */     return thinLoweredBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getThinRaisedBorder()
/*     */   {
/* 308 */     if (thinRaisedBorder == null) {
/* 309 */       thinRaisedBorder = new ThinRaisedBorder(null);
/*     */     }
/* 311 */     return thinRaisedBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getToggleButtonBorder(Insets buttonMargin)
/*     */   {
/* 320 */     return new BorderUIResource.CompoundBorderUIResource(new ToggleButtonBorder(buttonMargin, null), new BasicBorders.MarginBorder());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getToolBarHeaderBorder()
/*     */   {
/* 332 */     if (toolBarHeaderBorder == null) {
/* 333 */       toolBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new ToolBarHeaderBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 337 */     return toolBarHeaderBorder;
/*     */   }
/*     */   
/*     */   private static class Flush3DBorder extends AbstractBorder implements UIResource
/*     */   {
/* 342 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 345 */       if (c.isEnabled()) {
/* 346 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/*     */       } else
/* 348 */         PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/*     */     }
/*     */     
/* 351 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 354 */       newInsets.top = INSETS.top;
/* 355 */       newInsets.left = INSETS.left;
/* 356 */       newInsets.bottom = INSETS.bottom;
/* 357 */       newInsets.right = INSETS.right;
/* 358 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ButtonBorder extends AbstractBorder implements UIResource
/*     */   {
/*     */     protected final Insets insets;
/*     */     
/*     */     protected ButtonBorder(Insets insets)
/*     */     {
/* 368 */       this.insets = insets;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 372 */       AbstractButton button = (AbstractButton)c;
/* 373 */       ButtonModel model = button.getModel();
/*     */       
/* 375 */       if (model.isEnabled()) {
/* 376 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 377 */         boolean isDefault = ((button instanceof JButton)) && (((JButton)button).isDefaultButton());
/*     */         
/*     */ 
/* 380 */         if ((isPressed) && (isDefault)) {
/* 381 */           PlasticUtils.drawDefaultButtonPressedBorder(g, x, y, w, h);
/* 382 */         } else if (isPressed) {
/* 383 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 384 */         } else if (isDefault) {
/* 385 */           PlasticUtils.drawDefaultButtonBorder(g, x, y, w, h, false);
/*     */         } else
/* 387 */           PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
/*     */       } else {
/* 389 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/*     */       }
/*     */     }
/*     */     
/* 393 */     public Insets getBorderInsets(Component c) { return this.insets; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 396 */       newInsets.top = this.insets.top;
/* 397 */       newInsets.left = this.insets.left;
/* 398 */       newInsets.bottom = this.insets.bottom;
/* 399 */       newInsets.right = this.insets.right;
/* 400 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ComboBoxArrowButtonBorder
/*     */     extends AbstractBorder implements UIResource
/*     */   {
/* 407 */     protected static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 410 */       AbstractButton button = (AbstractButton)c;
/* 411 */       ButtonModel model = button.getModel();
/*     */       
/* 413 */       if (model.isEnabled()) {
/* 414 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/*     */         
/* 416 */         if (isPressed) {
/* 417 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/*     */         } else
/* 419 */           PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
/*     */       } else {
/* 421 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/*     */       }
/*     */     }
/*     */     
/* 425 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static final class ComboBoxEditorBorder
/*     */     extends AbstractBorder
/*     */   {
/* 431 */     private static final Insets INSETS = new Insets(2, 2, 2, 0);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 434 */       if (c.isEnabled()) {
/* 435 */         PlasticUtils.drawFlush3DBorder(g, x, y, w + 2, h);
/*     */       } else {
/* 437 */         PlasticUtils.drawDisabledBorder(g, x, y, w + 2, h - 1);
/* 438 */         g.setColor(UIManager.getColor("control"));
/* 439 */         g.drawLine(x, y + h - 1, x + w, y + h - 1);
/*     */       }
/*     */     }
/*     */     
/* 443 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class InternalFrameBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 452 */     private static final Insets NORMAL_INSETS = new Insets(1, 1, 1, 1);
/* 453 */     private static final Insets MAXIMIZED_INSETS = new Insets(1, 1, 0, 0);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/*     */     {
/* 457 */       JInternalFrame frame = (JInternalFrame)c;
/* 458 */       if (frame.isMaximum()) {
/* 459 */         paintMaximizedBorder(g, x, y, w, h);
/*     */       } else
/* 461 */         PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
/*     */     }
/*     */     
/*     */     private void paintMaximizedBorder(Graphics g, int x, int y, int w, int h) {
/* 465 */       g.translate(x, y);
/* 466 */       g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 467 */       g.drawLine(0, 0, w - 2, 0);
/* 468 */       g.drawLine(0, 0, 0, h - 2);
/* 469 */       g.translate(-x, -y);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component c) {
/* 473 */       return ((JInternalFrame)c).isMaximum() ? MAXIMIZED_INSETS : NORMAL_INSETS;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class PaletteBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 483 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 486 */       g.translate(x, y);
/* 487 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 488 */       g.drawRect(0, 0, w - 1, h - 1);
/* 489 */       g.translate(-x, -y);
/*     */     }
/*     */     
/* 492 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class SeparatorBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 502 */     private static final Insets INSETS = new Insets(0, 0, 2, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 505 */       g.translate(x, y);
/* 506 */       g.setColor(UIManager.getColor("Separator.foreground"));
/* 507 */       g.drawLine(0, h - 2, w - 1, h - 2);
/*     */       
/* 509 */       g.setColor(UIManager.getColor("Separator.background"));
/* 510 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 511 */       g.translate(-x, -y); }
/*     */     
/* 513 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static final class ThinRaisedBorder extends AbstractBorder implements UIResource
/*     */   {
/* 518 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 521 */       PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
/*     */     }
/*     */     
/* 524 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static final class ThinLoweredBorder extends AbstractBorder implements UIResource
/*     */   {
/* 529 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 532 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
/*     */     }
/*     */     
/* 535 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class EtchedBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 547 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 550 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
/* 551 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 2);
/*     */     }
/*     */     
/* 554 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class MenuBarHeaderBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 565 */     private static final Insets INSETS = new Insets(2, 2, 1, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 568 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h + 1);
/* 569 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 1);
/*     */     }
/*     */     
/* 572 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ToolBarHeaderBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 583 */     private static final Insets INSETS = new Insets(1, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 586 */       PlasticUtils.drawThinPressed3DBorder(g, x, y - 1, w, h + 1);
/* 587 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y, w - 2, h - 1);
/*     */     }
/*     */     
/* 590 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static final class MenuBorder extends AbstractBorder implements UIResource
/*     */   {
/* 595 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 598 */       JMenuItem b = (JMenuItem)c;
/* 599 */       ButtonModel model = b.getModel();
/*     */       
/* 601 */       if ((model.isArmed()) || (model.isSelected())) {
/* 602 */         g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 603 */         g.drawLine(0, 0, w - 2, 0);
/* 604 */         g.drawLine(0, 0, 0, h - 1);
/*     */         
/*     */ 
/* 607 */         g.setColor(PlasticLookAndFeel.getPrimaryControlHighlight());
/* 608 */         g.drawLine(w - 1, 0, w - 1, h - 1);
/* 609 */       } else if (model.isRollover()) {
/* 610 */         g.translate(x, y);
/* 611 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 612 */         g.translate(-x, -y);
/*     */       }
/*     */     }
/*     */     
/* 616 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 619 */       newInsets.top = INSETS.top;
/* 620 */       newInsets.left = INSETS.left;
/* 621 */       newInsets.bottom = INSETS.bottom;
/* 622 */       newInsets.right = INSETS.right;
/* 623 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class PopupMenuBorder extends AbstractBorder implements UIResource
/*     */   {
/* 629 */     private static final Insets INSETS = new Insets(3, 3, 3, 3);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 632 */       g.translate(x, y);
/* 633 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 634 */       g.drawRect(0, 0, w - 1, h - 1);
/* 635 */       g.setColor(PlasticLookAndFeel.getMenuItemBackground());
/* 636 */       g.drawRect(1, 1, w - 3, h - 3);
/* 637 */       g.drawRect(2, 2, w - 5, h - 5);
/* 638 */       g.translate(-x, -y);
/*     */     }
/*     */     
/* 641 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static final class NoMarginPopupMenuBorder extends AbstractBorder implements UIResource
/*     */   {
/* 646 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 649 */       g.translate(x, y);
/* 650 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 651 */       g.drawRect(0, 0, w - 1, h - 1);
/* 652 */       g.translate(-x, -y);
/*     */     }
/*     */     
/* 655 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */   private static class RolloverButtonBorder extends PlasticBorders.ButtonBorder
/*     */   {
/*     */     private RolloverButtonBorder()
/*     */     {
/* 662 */       super();
/*     */     }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 666 */       AbstractButton b = (AbstractButton)c;
/* 667 */       ButtonModel model = b.getModel();
/*     */       
/* 669 */       if (!model.isEnabled()) {
/* 670 */         return;
/*     */       }
/* 672 */       if (!(c instanceof JToggleButton)) {
/* 673 */         if ((model.isRollover()) && ((!model.isPressed()) || (model.isArmed()))) {
/* 674 */           super.paintBorder(c, g, x, y, w, h);
/*     */         }
/* 676 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 683 */       if (model.isRollover()) {
/* 684 */         if ((model.isPressed()) && (model.isArmed())) {
/* 685 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/*     */         } else {
/* 687 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/*     */         }
/* 689 */       } else if (model.isSelected()) {
/* 690 */         PlasticUtils.drawDark3DBorder(g, x, y, w, h);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class RolloverMarginBorder
/*     */     extends EmptyBorder
/*     */   {
/*     */     RolloverMarginBorder()
/*     */     {
/* 702 */       super(1, 1, 1);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component c)
/*     */     {
/* 707 */       return getBorderInsets(c, new Insets(0, 0, 0, 0));
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets insets)
/*     */     {
/* 712 */       Insets margin = null;
/*     */       
/* 714 */       if ((c instanceof AbstractButton)) {
/* 715 */         margin = ((AbstractButton)c).getMargin();
/*     */       }
/* 717 */       if ((margin == null) || ((margin instanceof UIResource)))
/*     */       {
/* 719 */         insets.left = this.left;
/* 720 */         insets.top = this.top;
/* 721 */         insets.right = this.right;
/* 722 */         insets.bottom = this.bottom;
/*     */       }
/*     */       else {
/* 725 */         insets.left = margin.left;
/* 726 */         insets.top = margin.top;
/* 727 */         insets.right = margin.right;
/* 728 */         insets.bottom = margin.bottom;
/*     */       }
/* 730 */       return insets;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ScrollPaneBorder
/*     */     extends MetalBorders.ScrollPaneBorder
/*     */   {
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/*     */     {
/* 742 */       g.translate(x, y);
/*     */       
/* 744 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 745 */       g.drawRect(0, 0, w - 2, h - 2);
/* 746 */       g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 747 */       g.drawLine(w - 1, 0, w - 1, h - 1);
/* 748 */       g.drawLine(0, h - 1, w - 1, h - 1);
/*     */       
/* 750 */       g.translate(-x, -y);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TextFieldBorder extends PlasticBorders.Flush3DBorder {
/* 755 */     private TextFieldBorder() { super(); }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 758 */       if (!(c instanceof JTextComponent))
/*     */       {
/* 760 */         if (c.isEnabled()) {
/* 761 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/*     */         } else {
/* 763 */           PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/*     */         }
/* 765 */         return;
/*     */       }
/*     */       
/* 768 */       if ((c.isEnabled()) && (((JTextComponent)c).isEditable())) {
/* 769 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/*     */       } else {
/* 771 */         PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ToggleButtonBorder extends PlasticBorders.ButtonBorder
/*     */   {
/*     */     private ToggleButtonBorder(Insets insets) {
/* 779 */       super();
/*     */     }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 783 */       if (!c.isEnabled()) {
/* 784 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/*     */       } else {
/* 786 */         AbstractButton button = (AbstractButton)c;
/* 787 */         ButtonModel model = button.getModel();
/* 788 */         if ((model.isPressed()) && (model.isArmed())) {
/* 789 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 790 */         } else if (model.isSelected()) {
/* 791 */           PlasticUtils.drawDark3DBorder(g, x, y, w, h);
/*     */         } else {
/* 793 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticBorders.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */