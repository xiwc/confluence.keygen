/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.RenderingHints.Key;
/*     */ import java.awt.Stroke;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ColorUIResource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PlasticXPIconFactory
/*     */ {
/*     */   private static CheckBoxIcon checkBoxIcon;
/*     */   private static RadioButtonIcon radioButtonIcon;
/*     */   
/*     */   static Icon getCheckBoxIcon()
/*     */   {
/*  73 */     if (checkBoxIcon == null) {
/*  74 */       checkBoxIcon = new CheckBoxIcon(null);
/*     */     }
/*  76 */     return checkBoxIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Icon getRadioButtonIcon()
/*     */   {
/*  85 */     if (radioButtonIcon == null) {
/*  86 */       radioButtonIcon = new RadioButtonIcon(null);
/*     */     }
/*  88 */     return radioButtonIcon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class CheckBoxIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*  97 */     private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
/*     */     
/*  99 */     public int getIconWidth() { return SIZE; }
/* 100 */     public int getIconHeight() { return SIZE; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/* 103 */       JCheckBox cb = (JCheckBox)c;
/* 104 */       ButtonModel model = cb.getModel();
/* 105 */       Graphics2D g2 = (Graphics2D)g;
/* 106 */       boolean paintFocus = ((model.isArmed()) && (!model.isPressed())) || ((cb.hasFocus()) && (PlasticXPIconFactory.isBlank(cb.getText())));
/*     */       
/*     */ 
/* 109 */       RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
/* 110 */       Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/* 111 */       Object oldAAHint = g2.getRenderingHint(key);
/* 112 */       if (newAAHint != oldAAHint) {
/* 113 */         g2.setRenderingHint(key, newAAHint);
/*     */       } else {
/* 115 */         oldAAHint = null;
/*     */       }
/*     */       
/* 118 */       drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
/* 119 */       drawFill(g2, model.isPressed(), x + 1, y + 1, SIZE - 2, SIZE - 2);
/* 120 */       if (paintFocus) {
/* 121 */         drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3);
/*     */       }
/* 123 */       if (model.isSelected()) {
/* 124 */         drawCheck(g2, model.isEnabled(), x + 3, y + 3, SIZE - 7, SIZE - 7);
/*     */       }
/*     */       
/* 127 */       if (oldAAHint != null) {
/* 128 */         g2.setRenderingHint(key, oldAAHint);
/*     */       }
/*     */     }
/*     */     
/*     */     private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int width, int height) {
/* 133 */       g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
/*     */       
/*     */ 
/* 136 */       g2.drawRect(x, y, width, height);
/*     */     }
/*     */     
/*     */     private void drawCheck(Graphics2D g2, boolean enabled, int x, int y, int width, int height) {
/* 140 */       g2.setColor(enabled ? UIManager.getColor("CheckBox.check") : MetalLookAndFeel.getControlDisabled());
/*     */       
/*     */ 
/* 143 */       int right = x + width;
/* 144 */       int bottom = y + height;
/* 145 */       int startY = y + height / 3;
/* 146 */       int turnX = x + width / 2 - 2;
/* 147 */       g2.drawLine(x, startY, turnX, bottom - 3);
/* 148 */       g2.drawLine(x, startY + 1, turnX, bottom - 2);
/* 149 */       g2.drawLine(x, startY + 2, turnX, bottom - 1);
/* 150 */       g2.drawLine(turnX + 1, bottom - 2, right, y);
/* 151 */       g2.drawLine(turnX + 1, bottom - 1, right, y + 1);
/* 152 */       g2.drawLine(turnX + 1, bottom, right, y + 2);
/*     */     }
/*     */     
/*     */     private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h) { Color lowerRight;
/*     */       Color upperLeft;
/*     */       Color lowerRight;
/* 158 */       if (pressed) {
/* 159 */         Color upperLeft = MetalLookAndFeel.getControlShadow();
/* 160 */         lowerRight = PlasticLookAndFeel.getControlHighlight();
/*     */       } else {
/* 162 */         upperLeft = PlasticLookAndFeel.getControl();
/* 163 */         lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
/*     */       }
/* 165 */       g2.setPaint(new GradientPaint(x, y, upperLeft, x + w, y + h, lowerRight));
/* 166 */       g2.fillRect(x, y, w, h);
/*     */     }
/*     */     
/*     */     private void drawFocus(Graphics2D g2, int x, int y, int width, int height) {
/* 170 */       g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), width, height, PlasticLookAndFeel.getFocusColor()));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 178 */       g2.drawRect(x, y, width, height);
/* 179 */       g2.drawRect(x + 1, y + 1, width - 2, height - 2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class RadioButtonIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/* 190 */     private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
/*     */     
/* 192 */     private static final Stroke FOCUS_STROKE = new BasicStroke(2.0F);
/*     */     
/* 194 */     public int getIconWidth() { return SIZE; }
/* 195 */     public int getIconHeight() { return SIZE; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {
/* 198 */       Graphics2D g2 = (Graphics2D)g;
/* 199 */       AbstractButton rb = (AbstractButton)c;
/* 200 */       ButtonModel model = rb.getModel();
/* 201 */       boolean paintFocus = ((model.isArmed()) && (!model.isPressed())) || ((rb.hasFocus()) && (PlasticXPIconFactory.isBlank(rb.getText())));
/*     */       
/*     */ 
/* 204 */       RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
/* 205 */       Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/* 206 */       Object oldAAHint = g2.getRenderingHint(key);
/* 207 */       if (newAAHint != oldAAHint) {
/* 208 */         g2.setRenderingHint(key, newAAHint);
/*     */       } else {
/* 210 */         oldAAHint = null;
/*     */       }
/*     */       
/* 213 */       drawFill(g2, model.isPressed(), x, y, SIZE - 1, SIZE - 1);
/* 214 */       if (paintFocus) {
/* 215 */         drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3);
/*     */       }
/* 217 */       if (model.isSelected()) {
/* 218 */         drawCheck(g2, c, model.isEnabled(), x + 4, y + 4, SIZE - 8, SIZE - 8);
/*     */       }
/* 220 */       drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
/*     */       
/* 222 */       if (oldAAHint != null) {
/* 223 */         g2.setRenderingHint(key, oldAAHint);
/*     */       }
/*     */     }
/*     */     
/*     */     private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int w, int h) {
/* 228 */       g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
/*     */       
/*     */ 
/* 231 */       g2.drawOval(x, y, w, h);
/*     */     }
/*     */     
/*     */     private void drawCheck(Graphics2D g2, Component c, boolean enabled, int x, int y, int w, int h) {
/* 235 */       g2.translate(x, y);
/* 236 */       if (enabled) {
/* 237 */         g2.setColor(UIManager.getColor("RadioButton.check"));
/* 238 */         g2.fillOval(0, 0, w, h);
/* 239 */         UIManager.getIcon("RadioButton.checkIcon").paintIcon(c, g2, 0, 0);
/*     */       } else {
/* 241 */         g2.setColor(MetalLookAndFeel.getControlDisabled());
/* 242 */         g2.fillOval(0, 0, w, h);
/*     */       }
/* 244 */       g2.translate(-x, -y);
/*     */     }
/*     */     
/*     */     private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h) { Color lowerRight;
/*     */       Color upperLeft;
/*     */       Color lowerRight;
/* 250 */       if (pressed) {
/* 251 */         Color upperLeft = MetalLookAndFeel.getControlShadow();
/* 252 */         lowerRight = PlasticLookAndFeel.getControlHighlight();
/*     */       } else {
/* 254 */         upperLeft = PlasticLookAndFeel.getControl();
/* 255 */         lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
/*     */       }
/* 257 */       g2.setPaint(new GradientPaint(x, y, upperLeft, x + w, y + h, lowerRight));
/* 258 */       g2.fillOval(x, y, w, h);
/*     */     }
/*     */     
/*     */     private void drawFocus(Graphics2D g2, int x, int y, int w, int h) {
/* 262 */       g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), w, h, PlasticLookAndFeel.getFocusColor()));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */       Stroke stroke = g2.getStroke();
/* 271 */       g2.setStroke(FOCUS_STROKE);
/* 272 */       g2.drawOval(x, y, w, h);
/* 273 */       g2.setStroke(stroke);
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
/*     */   private static boolean isBlank(String str)
/*     */   {
/*     */     int length;
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
/* 300 */     if ((str == null) || ((length = str.length()) == 0))
/* 301 */       return true;
/* 302 */     int length; for (int i = length - 1; i >= 0; i--) {
/* 303 */       if (!Character.isWhitespace(str.charAt(i)))
/* 304 */         return false;
/*     */     }
/* 306 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPIconFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */