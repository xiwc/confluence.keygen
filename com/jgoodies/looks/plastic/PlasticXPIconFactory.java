/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import java.awt.BasicStroke;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Component;
/*   7:    */ import java.awt.GradientPaint;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.awt.RenderingHints;
/*  11:    */ import java.awt.RenderingHints.Key;
/*  12:    */ import java.awt.Stroke;
/*  13:    */ import java.io.Serializable;
/*  14:    */ import javax.swing.AbstractButton;
/*  15:    */ import javax.swing.ButtonModel;
/*  16:    */ import javax.swing.Icon;
/*  17:    */ import javax.swing.JCheckBox;
/*  18:    */ import javax.swing.UIManager;
/*  19:    */ import javax.swing.plaf.ColorUIResource;
/*  20:    */ import javax.swing.plaf.UIResource;
/*  21:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*  22:    */ 
/*  23:    */ public final class PlasticXPIconFactory
/*  24:    */ {
/*  25:    */   private static CheckBoxIcon checkBoxIcon;
/*  26:    */   private static RadioButtonIcon radioButtonIcon;
/*  27:    */   
/*  28:    */   static Icon getCheckBoxIcon()
/*  29:    */   {
/*  30: 73 */     if (checkBoxIcon == null) {
/*  31: 74 */       checkBoxIcon = new CheckBoxIcon(null);
/*  32:    */     }
/*  33: 76 */     return checkBoxIcon;
/*  34:    */   }
/*  35:    */   
/*  36:    */   static Icon getRadioButtonIcon()
/*  37:    */   {
/*  38: 85 */     if (radioButtonIcon == null) {
/*  39: 86 */       radioButtonIcon = new RadioButtonIcon(null);
/*  40:    */     }
/*  41: 88 */     return radioButtonIcon;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static final class CheckBoxIcon
/*  45:    */     implements Icon, UIResource, Serializable
/*  46:    */   {
/*  47: 97 */     private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
/*  48:    */     
/*  49:    */     public int getIconWidth()
/*  50:    */     {
/*  51: 99 */       return SIZE;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public int getIconHeight()
/*  55:    */     {
/*  56:100 */       return SIZE;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/*  60:    */     {
/*  61:103 */       JCheckBox cb = (JCheckBox)c;
/*  62:104 */       ButtonModel model = cb.getModel();
/*  63:105 */       Graphics2D g2 = (Graphics2D)g;
/*  64:106 */       boolean paintFocus = ((model.isArmed()) && (!model.isPressed())) || ((cb.hasFocus()) && (PlasticXPIconFactory.isBlank(cb.getText())));
/*  65:    */       
/*  66:    */ 
/*  67:109 */       RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
/*  68:110 */       Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/*  69:111 */       Object oldAAHint = g2.getRenderingHint(key);
/*  70:112 */       if (newAAHint != oldAAHint) {
/*  71:113 */         g2.setRenderingHint(key, newAAHint);
/*  72:    */       } else {
/*  73:115 */         oldAAHint = null;
/*  74:    */       }
/*  75:118 */       drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
/*  76:119 */       drawFill(g2, model.isPressed(), x + 1, y + 1, SIZE - 2, SIZE - 2);
/*  77:120 */       if (paintFocus) {
/*  78:121 */         drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3);
/*  79:    */       }
/*  80:123 */       if (model.isSelected()) {
/*  81:124 */         drawCheck(g2, model.isEnabled(), x + 3, y + 3, SIZE - 7, SIZE - 7);
/*  82:    */       }
/*  83:127 */       if (oldAAHint != null) {
/*  84:128 */         g2.setRenderingHint(key, oldAAHint);
/*  85:    */       }
/*  86:    */     }
/*  87:    */     
/*  88:    */     private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int width, int height)
/*  89:    */     {
/*  90:133 */       g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
/*  91:    */       
/*  92:    */ 
/*  93:136 */       g2.drawRect(x, y, width, height);
/*  94:    */     }
/*  95:    */     
/*  96:    */     private void drawCheck(Graphics2D g2, boolean enabled, int x, int y, int width, int height)
/*  97:    */     {
/*  98:140 */       g2.setColor(enabled ? UIManager.getColor("CheckBox.check") : MetalLookAndFeel.getControlDisabled());
/*  99:    */       
/* 100:    */ 
/* 101:143 */       int right = x + width;
/* 102:144 */       int bottom = y + height;
/* 103:145 */       int startY = y + height / 3;
/* 104:146 */       int turnX = x + width / 2 - 2;
/* 105:147 */       g2.drawLine(x, startY, turnX, bottom - 3);
/* 106:148 */       g2.drawLine(x, startY + 1, turnX, bottom - 2);
/* 107:149 */       g2.drawLine(x, startY + 2, turnX, bottom - 1);
/* 108:150 */       g2.drawLine(turnX + 1, bottom - 2, right, y);
/* 109:151 */       g2.drawLine(turnX + 1, bottom - 1, right, y + 1);
/* 110:152 */       g2.drawLine(turnX + 1, bottom, right, y + 2);
/* 111:    */     }
/* 112:    */     
/* 113:    */     private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h)
/* 114:    */     {
/* 115:    */       Color lowerRight;
/* 116:    */       Color upperLeft;
/* 117:    */       Color lowerRight;
/* 118:158 */       if (pressed)
/* 119:    */       {
/* 120:159 */         Color upperLeft = MetalLookAndFeel.getControlShadow();
/* 121:160 */         lowerRight = PlasticLookAndFeel.getControlHighlight();
/* 122:    */       }
/* 123:    */       else
/* 124:    */       {
/* 125:162 */         upperLeft = PlasticLookAndFeel.getControl();
/* 126:163 */         lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
/* 127:    */       }
/* 128:165 */       g2.setPaint(new GradientPaint(x, y, upperLeft, x + w, y + h, lowerRight));
/* 129:166 */       g2.fillRect(x, y, w, h);
/* 130:    */     }
/* 131:    */     
/* 132:    */     private void drawFocus(Graphics2D g2, int x, int y, int width, int height)
/* 133:    */     {
/* 134:170 */       g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), width, height, PlasticLookAndFeel.getFocusColor()));
/* 135:    */       
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:178 */       g2.drawRect(x, y, width, height);
/* 143:179 */       g2.drawRect(x + 1, y + 1, width - 2, height - 2);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private static final class RadioButtonIcon
/* 148:    */     implements Icon, UIResource, Serializable
/* 149:    */   {
/* 150:190 */     private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
/* 151:192 */     private static final Stroke FOCUS_STROKE = new BasicStroke(2.0F);
/* 152:    */     
/* 153:    */     public int getIconWidth()
/* 154:    */     {
/* 155:194 */       return SIZE;
/* 156:    */     }
/* 157:    */     
/* 158:    */     public int getIconHeight()
/* 159:    */     {
/* 160:195 */       return SIZE;
/* 161:    */     }
/* 162:    */     
/* 163:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 164:    */     {
/* 165:198 */       Graphics2D g2 = (Graphics2D)g;
/* 166:199 */       AbstractButton rb = (AbstractButton)c;
/* 167:200 */       ButtonModel model = rb.getModel();
/* 168:201 */       boolean paintFocus = ((model.isArmed()) && (!model.isPressed())) || ((rb.hasFocus()) && (PlasticXPIconFactory.isBlank(rb.getText())));
/* 169:    */       
/* 170:    */ 
/* 171:204 */       RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
/* 172:205 */       Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/* 173:206 */       Object oldAAHint = g2.getRenderingHint(key);
/* 174:207 */       if (newAAHint != oldAAHint) {
/* 175:208 */         g2.setRenderingHint(key, newAAHint);
/* 176:    */       } else {
/* 177:210 */         oldAAHint = null;
/* 178:    */       }
/* 179:213 */       drawFill(g2, model.isPressed(), x, y, SIZE - 1, SIZE - 1);
/* 180:214 */       if (paintFocus) {
/* 181:215 */         drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3);
/* 182:    */       }
/* 183:217 */       if (model.isSelected()) {
/* 184:218 */         drawCheck(g2, c, model.isEnabled(), x + 4, y + 4, SIZE - 8, SIZE - 8);
/* 185:    */       }
/* 186:220 */       drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
/* 187:222 */       if (oldAAHint != null) {
/* 188:223 */         g2.setRenderingHint(key, oldAAHint);
/* 189:    */       }
/* 190:    */     }
/* 191:    */     
/* 192:    */     private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int w, int h)
/* 193:    */     {
/* 194:228 */       g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
/* 195:    */       
/* 196:    */ 
/* 197:231 */       g2.drawOval(x, y, w, h);
/* 198:    */     }
/* 199:    */     
/* 200:    */     private void drawCheck(Graphics2D g2, Component c, boolean enabled, int x, int y, int w, int h)
/* 201:    */     {
/* 202:235 */       g2.translate(x, y);
/* 203:236 */       if (enabled)
/* 204:    */       {
/* 205:237 */         g2.setColor(UIManager.getColor("RadioButton.check"));
/* 206:238 */         g2.fillOval(0, 0, w, h);
/* 207:239 */         UIManager.getIcon("RadioButton.checkIcon").paintIcon(c, g2, 0, 0);
/* 208:    */       }
/* 209:    */       else
/* 210:    */       {
/* 211:241 */         g2.setColor(MetalLookAndFeel.getControlDisabled());
/* 212:242 */         g2.fillOval(0, 0, w, h);
/* 213:    */       }
/* 214:244 */       g2.translate(-x, -y);
/* 215:    */     }
/* 216:    */     
/* 217:    */     private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h)
/* 218:    */     {
/* 219:    */       Color lowerRight;
/* 220:    */       Color upperLeft;
/* 221:    */       Color lowerRight;
/* 222:250 */       if (pressed)
/* 223:    */       {
/* 224:251 */         Color upperLeft = MetalLookAndFeel.getControlShadow();
/* 225:252 */         lowerRight = PlasticLookAndFeel.getControlHighlight();
/* 226:    */       }
/* 227:    */       else
/* 228:    */       {
/* 229:254 */         upperLeft = PlasticLookAndFeel.getControl();
/* 230:255 */         lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
/* 231:    */       }
/* 232:257 */       g2.setPaint(new GradientPaint(x, y, upperLeft, x + w, y + h, lowerRight));
/* 233:258 */       g2.fillOval(x, y, w, h);
/* 234:    */     }
/* 235:    */     
/* 236:    */     private void drawFocus(Graphics2D g2, int x, int y, int w, int h)
/* 237:    */     {
/* 238:262 */       g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), w, h, PlasticLookAndFeel.getFocusColor()));
/* 239:    */       
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:270 */       Stroke stroke = g2.getStroke();
/* 247:271 */       g2.setStroke(FOCUS_STROKE);
/* 248:272 */       g2.drawOval(x, y, w, h);
/* 249:273 */       g2.setStroke(stroke);
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   private static boolean isBlank(String str)
/* 254:    */   {
/* 255:    */     int length;
/* 256:300 */     if ((str == null) || ((length = str.length()) == 0)) {
/* 257:301 */       return true;
/* 258:    */     }
/* 259:    */     int length;
/* 260:302 */     for (int i = length - 1; i >= 0; i--) {
/* 261:303 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 262:304 */         return false;
/* 263:    */       }
/* 264:    */     }
/* 265:306 */     return true;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPIconFactory
 * JD-Core Version:    0.7.0.1
 */