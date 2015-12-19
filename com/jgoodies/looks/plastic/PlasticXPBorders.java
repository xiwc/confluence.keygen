/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*     */ import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;
/*     */ import javax.swing.plaf.metal.MetalLookAndFeel;
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
/*     */ 
/*     */ final class PlasticXPBorders
/*     */ {
/*     */   private static Border comboBoxArrowButtonBorder;
/*     */   private static Border comboBoxEditorBorder;
/*     */   private static Border scrollPaneBorder;
/*     */   private static Border textFieldBorder;
/*     */   private static Border spinnerBorder;
/*     */   private static Border rolloverButtonBorder;
/*     */   
/*     */   static Border getButtonBorder(Insets buttonMargin)
/*     */   {
/*  79 */     return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getComboBoxArrowButtonBorder()
/*     */   {
/*  88 */     if (comboBoxArrowButtonBorder == null) {
/*  89 */       comboBoxArrowButtonBorder = new CompoundBorder(new XPComboBoxArrowButtonBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/*  93 */     return comboBoxArrowButtonBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Border getComboBoxEditorBorder()
/*     */   {
/* 100 */     if (comboBoxEditorBorder == null) {
/* 101 */       comboBoxEditorBorder = new CompoundBorder(new XPComboBoxEditorBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 105 */     return comboBoxEditorBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Border getScrollPaneBorder()
/*     */   {
/* 112 */     if (scrollPaneBorder == null) {
/* 113 */       scrollPaneBorder = new XPScrollPaneBorder(null);
/*     */     }
/* 115 */     return scrollPaneBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Border getTextFieldBorder()
/*     */   {
/* 122 */     if (textFieldBorder == null) {
/* 123 */       textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new XPTextFieldBorder(null), new BasicBorders.MarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 127 */     return textFieldBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Border getToggleButtonBorder(Insets buttonMargin)
/*     */   {
/* 134 */     return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getSpinnerBorder()
/*     */   {
/* 143 */     if (spinnerBorder == null) {
/* 144 */       spinnerBorder = new XPSpinnerBorder(null);
/*     */     }
/* 146 */     return spinnerBorder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Border getRolloverButtonBorder()
/*     */   {
/* 156 */     if (rolloverButtonBorder == null) {
/* 157 */       rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(null), new PlasticBorders.RolloverMarginBorder());
/*     */     }
/*     */     
/*     */ 
/* 161 */     return rolloverButtonBorder;
/*     */   }
/*     */   
/*     */   private static class XPButtonBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected final Insets insets;
/*     */     
/*     */     protected XPButtonBorder(Insets insets)
/*     */     {
/* 172 */       this.insets = insets;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 176 */       AbstractButton button = (AbstractButton)c;
/* 177 */       ButtonModel model = button.getModel();
/*     */       
/* 179 */       if (!model.isEnabled()) {
/* 180 */         PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
/* 181 */         return;
/*     */       }
/*     */       
/* 184 */       boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 185 */       boolean isDefault = ((button instanceof JButton)) && (((JButton)button).isDefaultButton());
/*     */       
/* 187 */       boolean isFocused = (button.isFocusPainted()) && (button.hasFocus());
/*     */       
/* 189 */       if (isPressed) {
/* 190 */         PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 191 */       } else if (isFocused) {
/* 192 */         PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
/* 193 */       } else if (isDefault) {
/* 194 */         PlasticXPUtils.drawDefaultButtonBorder(g, x, y, w, h);
/*     */       } else
/* 196 */         PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/*     */     }
/*     */     
/* 199 */     public Insets getBorderInsets(Component c) { return this.insets; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 202 */       newInsets.top = this.insets.top;
/* 203 */       newInsets.left = this.insets.left;
/* 204 */       newInsets.bottom = this.insets.bottom;
/* 205 */       newInsets.right = this.insets.right;
/* 206 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class XPComboBoxArrowButtonBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 216 */     protected static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 219 */       PlasticComboBoxButton button = (PlasticComboBoxButton)c;
/* 220 */       JComboBox comboBox = button.getComboBox();
/* 221 */       ButtonModel model = button.getModel();
/*     */       
/* 223 */       if (!model.isEnabled()) {
/* 224 */         PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
/*     */       } else {
/* 226 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 227 */         boolean isFocused = comboBox.hasFocus();
/* 228 */         if (isPressed) {
/* 229 */           PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 230 */         } else if (isFocused) {
/* 231 */           PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
/*     */         } else
/* 233 */           PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/*     */       }
/* 235 */       if (comboBox.isEditable())
/*     */       {
/* 237 */         g.setColor(model.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */         
/*     */ 
/* 240 */         g.fillRect(x, y, 1, 1);
/* 241 */         g.fillRect(x, y + h - 1, 1, 1);
/*     */       }
/*     */     }
/*     */     
/* 245 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class XPComboBoxEditorBorder
/*     */     extends AbstractBorder
/*     */   {
/* 254 */     private static final Insets INSETS = new Insets(1, 1, 1, 0);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 257 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 260 */       PlasticXPUtils.drawRect(g, x, y, w + 1, h - 1);
/*     */     }
/*     */     
/* 263 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class XPTextFieldBorder
/*     */     extends AbstractBorder
/*     */   {
/* 272 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/*     */     {
/* 276 */       boolean enabled = (((c instanceof JTextComponent)) && (c.isEnabled()) && (((JTextComponent)c).isEditable())) || (c.isEnabled());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 281 */       g.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 284 */       PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
/*     */     }
/*     */     
/* 287 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 290 */       newInsets.top = INSETS.top;
/* 291 */       newInsets.left = INSETS.left;
/* 292 */       newInsets.bottom = INSETS.bottom;
/* 293 */       newInsets.right = INSETS.right;
/* 294 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class XPScrollPaneBorder
/*     */     extends MetalBorders.ScrollPaneBorder
/*     */   {
/* 306 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 309 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/* 312 */       PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
/*     */     }
/*     */     
/* 315 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 318 */       newInsets.top = INSETS.top;
/* 319 */       newInsets.left = INSETS.left;
/* 320 */       newInsets.bottom = INSETS.bottom;
/* 321 */       newInsets.right = INSETS.right;
/* 322 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class XPSpinnerBorder
/*     */     extends MetalBorders.ScrollPaneBorder
/*     */   {
/* 332 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 335 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 340 */       int arrowButtonWidth = UIManager.getInt("ScrollBar.width") - 1;
/* 341 */       w -= arrowButtonWidth;
/* 342 */       g.fillRect(x, y, w, 1);
/* 343 */       g.fillRect(x, y + 1, 1, h - 1);
/* 344 */       g.fillRect(x + 1, y + h - 1, w - 1, 1);
/*     */     }
/*     */     
/* 347 */     public Insets getBorderInsets(Component c) { return INSETS; }
/*     */     
/*     */     public Insets getBorderInsets(Component c, Insets newInsets) {
/* 350 */       newInsets.top = INSETS.top;
/* 351 */       newInsets.left = INSETS.left;
/* 352 */       newInsets.bottom = INSETS.bottom;
/* 353 */       newInsets.right = INSETS.right;
/* 354 */       return newInsets;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class RolloverButtonBorder
/*     */     extends PlasticXPBorders.XPButtonBorder
/*     */   {
/*     */     private RolloverButtonBorder()
/*     */     {
/* 365 */       super();
/*     */     }
/*     */     
/*     */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
/* 369 */       AbstractButton b = (AbstractButton)c;
/* 370 */       ButtonModel model = b.getModel();
/*     */       
/* 372 */       if (!model.isEnabled()) {
/* 373 */         return;
/*     */       }
/* 375 */       if (!(c instanceof JToggleButton)) {
/* 376 */         if ((model.isRollover()) && ((!model.isPressed()) || (model.isArmed()))) {
/* 377 */           super.paintBorder(c, g, x, y, w, h);
/*     */         }
/* 379 */         return;
/*     */       }
/*     */       
/* 382 */       if (model.isRollover()) {
/* 383 */         if ((model.isPressed()) && (model.isArmed())) {
/* 384 */           PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/*     */         } else {
/* 386 */           PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/*     */         }
/* 388 */       } else if (model.isSelected()) {
/* 389 */         PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPBorders.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */