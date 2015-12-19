/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import javax.swing.AbstractButton;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.JButton;
/*   9:    */ import javax.swing.JComboBox;
/*  10:    */ import javax.swing.JToggleButton;
/*  11:    */ import javax.swing.UIManager;
/*  12:    */ import javax.swing.border.AbstractBorder;
/*  13:    */ import javax.swing.border.Border;
/*  14:    */ import javax.swing.border.CompoundBorder;
/*  15:    */ import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;
/*  16:    */ import javax.swing.plaf.UIResource;
/*  17:    */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*  18:    */ import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;
/*  19:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*  20:    */ import javax.swing.text.JTextComponent;
/*  21:    */ 
/*  22:    */ final class PlasticXPBorders
/*  23:    */ {
/*  24:    */   private static Border comboBoxArrowButtonBorder;
/*  25:    */   private static Border comboBoxEditorBorder;
/*  26:    */   private static Border scrollPaneBorder;
/*  27:    */   private static Border textFieldBorder;
/*  28:    */   private static Border spinnerBorder;
/*  29:    */   private static Border rolloverButtonBorder;
/*  30:    */   
/*  31:    */   static Border getButtonBorder(Insets buttonMargin)
/*  32:    */   {
/*  33: 79 */     return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*  34:    */   }
/*  35:    */   
/*  36:    */   static Border getComboBoxArrowButtonBorder()
/*  37:    */   {
/*  38: 88 */     if (comboBoxArrowButtonBorder == null) {
/*  39: 89 */       comboBoxArrowButtonBorder = new CompoundBorder(new XPComboBoxArrowButtonBorder(null), new BasicBorders.MarginBorder());
/*  40:    */     }
/*  41: 93 */     return comboBoxArrowButtonBorder;
/*  42:    */   }
/*  43:    */   
/*  44:    */   static Border getComboBoxEditorBorder()
/*  45:    */   {
/*  46:100 */     if (comboBoxEditorBorder == null) {
/*  47:101 */       comboBoxEditorBorder = new CompoundBorder(new XPComboBoxEditorBorder(null), new BasicBorders.MarginBorder());
/*  48:    */     }
/*  49:105 */     return comboBoxEditorBorder;
/*  50:    */   }
/*  51:    */   
/*  52:    */   static Border getScrollPaneBorder()
/*  53:    */   {
/*  54:112 */     if (scrollPaneBorder == null) {
/*  55:113 */       scrollPaneBorder = new XPScrollPaneBorder(null);
/*  56:    */     }
/*  57:115 */     return scrollPaneBorder;
/*  58:    */   }
/*  59:    */   
/*  60:    */   static Border getTextFieldBorder()
/*  61:    */   {
/*  62:122 */     if (textFieldBorder == null) {
/*  63:123 */       textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new XPTextFieldBorder(null), new BasicBorders.MarginBorder());
/*  64:    */     }
/*  65:127 */     return textFieldBorder;
/*  66:    */   }
/*  67:    */   
/*  68:    */   static Border getToggleButtonBorder(Insets buttonMargin)
/*  69:    */   {
/*  70:134 */     return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*  71:    */   }
/*  72:    */   
/*  73:    */   static Border getSpinnerBorder()
/*  74:    */   {
/*  75:143 */     if (spinnerBorder == null) {
/*  76:144 */       spinnerBorder = new XPSpinnerBorder(null);
/*  77:    */     }
/*  78:146 */     return spinnerBorder;
/*  79:    */   }
/*  80:    */   
/*  81:    */   static Border getRolloverButtonBorder()
/*  82:    */   {
/*  83:156 */     if (rolloverButtonBorder == null) {
/*  84:157 */       rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(null), new PlasticBorders.RolloverMarginBorder());
/*  85:    */     }
/*  86:161 */     return rolloverButtonBorder;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static class XPButtonBorder
/*  90:    */     extends AbstractBorder
/*  91:    */     implements UIResource
/*  92:    */   {
/*  93:    */     protected final Insets insets;
/*  94:    */     
/*  95:    */     protected XPButtonBorder(Insets insets)
/*  96:    */     {
/*  97:172 */       this.insets = insets;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 101:    */     {
/* 102:176 */       AbstractButton button = (AbstractButton)c;
/* 103:177 */       ButtonModel model = button.getModel();
/* 104:179 */       if (!model.isEnabled())
/* 105:    */       {
/* 106:180 */         PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
/* 107:181 */         return;
/* 108:    */       }
/* 109:184 */       boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 110:185 */       boolean isDefault = ((button instanceof JButton)) && (((JButton)button).isDefaultButton());
/* 111:    */       
/* 112:187 */       boolean isFocused = (button.isFocusPainted()) && (button.hasFocus());
/* 113:189 */       if (isPressed) {
/* 114:190 */         PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 115:191 */       } else if (isFocused) {
/* 116:192 */         PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
/* 117:193 */       } else if (isDefault) {
/* 118:194 */         PlasticXPUtils.drawDefaultButtonBorder(g, x, y, w, h);
/* 119:    */       } else {
/* 120:196 */         PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/* 121:    */       }
/* 122:    */     }
/* 123:    */     
/* 124:    */     public Insets getBorderInsets(Component c)
/* 125:    */     {
/* 126:199 */       return this.insets;
/* 127:    */     }
/* 128:    */     
/* 129:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 130:    */     {
/* 131:202 */       newInsets.top = this.insets.top;
/* 132:203 */       newInsets.left = this.insets.left;
/* 133:204 */       newInsets.bottom = this.insets.bottom;
/* 134:205 */       newInsets.right = this.insets.right;
/* 135:206 */       return newInsets;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private static final class XPComboBoxArrowButtonBorder
/* 140:    */     extends AbstractBorder
/* 141:    */     implements UIResource
/* 142:    */   {
/* 143:216 */     protected static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 144:    */     
/* 145:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 146:    */     {
/* 147:219 */       PlasticComboBoxButton button = (PlasticComboBoxButton)c;
/* 148:220 */       JComboBox comboBox = button.getComboBox();
/* 149:221 */       ButtonModel model = button.getModel();
/* 150:223 */       if (!model.isEnabled())
/* 151:    */       {
/* 152:224 */         PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:226 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 157:227 */         boolean isFocused = comboBox.hasFocus();
/* 158:228 */         if (isPressed) {
/* 159:229 */           PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 160:230 */         } else if (isFocused) {
/* 161:231 */           PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
/* 162:    */         } else {
/* 163:233 */           PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/* 164:    */         }
/* 165:    */       }
/* 166:235 */       if (comboBox.isEditable())
/* 167:    */       {
/* 168:237 */         g.setColor(model.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 169:    */         
/* 170:    */ 
/* 171:240 */         g.fillRect(x, y, 1, 1);
/* 172:241 */         g.fillRect(x, y + h - 1, 1, 1);
/* 173:    */       }
/* 174:    */     }
/* 175:    */     
/* 176:    */     public Insets getBorderInsets(Component c)
/* 177:    */     {
/* 178:245 */       return INSETS;
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   private static final class XPComboBoxEditorBorder
/* 183:    */     extends AbstractBorder
/* 184:    */   {
/* 185:254 */     private static final Insets INSETS = new Insets(1, 1, 1, 0);
/* 186:    */     
/* 187:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 188:    */     {
/* 189:257 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 190:    */       
/* 191:    */ 
/* 192:260 */       PlasticXPUtils.drawRect(g, x, y, w + 1, h - 1);
/* 193:    */     }
/* 194:    */     
/* 195:    */     public Insets getBorderInsets(Component c)
/* 196:    */     {
/* 197:263 */       return INSETS;
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   private static final class XPTextFieldBorder
/* 202:    */     extends AbstractBorder
/* 203:    */   {
/* 204:272 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 205:    */     
/* 206:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 207:    */     {
/* 208:276 */       boolean enabled = (((c instanceof JTextComponent)) && (c.isEnabled()) && (((JTextComponent)c).isEditable())) || (c.isEnabled());
/* 209:    */       
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:281 */       g.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 214:    */       
/* 215:    */ 
/* 216:284 */       PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
/* 217:    */     }
/* 218:    */     
/* 219:    */     public Insets getBorderInsets(Component c)
/* 220:    */     {
/* 221:287 */       return INSETS;
/* 222:    */     }
/* 223:    */     
/* 224:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 225:    */     {
/* 226:290 */       newInsets.top = INSETS.top;
/* 227:291 */       newInsets.left = INSETS.left;
/* 228:292 */       newInsets.bottom = INSETS.bottom;
/* 229:293 */       newInsets.right = INSETS.right;
/* 230:294 */       return newInsets;
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   private static final class XPScrollPaneBorder
/* 235:    */     extends MetalBorders.ScrollPaneBorder
/* 236:    */   {
/* 237:306 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 238:    */     
/* 239:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 240:    */     {
/* 241:309 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 242:    */       
/* 243:    */ 
/* 244:312 */       PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
/* 245:    */     }
/* 246:    */     
/* 247:    */     public Insets getBorderInsets(Component c)
/* 248:    */     {
/* 249:315 */       return INSETS;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 253:    */     {
/* 254:318 */       newInsets.top = INSETS.top;
/* 255:319 */       newInsets.left = INSETS.left;
/* 256:320 */       newInsets.bottom = INSETS.bottom;
/* 257:321 */       newInsets.right = INSETS.right;
/* 258:322 */       return newInsets;
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   private static final class XPSpinnerBorder
/* 263:    */     extends MetalBorders.ScrollPaneBorder
/* 264:    */   {
/* 265:332 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 266:    */     
/* 267:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 268:    */     {
/* 269:335 */       g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
/* 270:    */       
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:340 */       int arrowButtonWidth = UIManager.getInt("ScrollBar.width") - 1;
/* 275:341 */       w -= arrowButtonWidth;
/* 276:342 */       g.fillRect(x, y, w, 1);
/* 277:343 */       g.fillRect(x, y + 1, 1, h - 1);
/* 278:344 */       g.fillRect(x + 1, y + h - 1, w - 1, 1);
/* 279:    */     }
/* 280:    */     
/* 281:    */     public Insets getBorderInsets(Component c)
/* 282:    */     {
/* 283:347 */       return INSETS;
/* 284:    */     }
/* 285:    */     
/* 286:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 287:    */     {
/* 288:350 */       newInsets.top = INSETS.top;
/* 289:351 */       newInsets.left = INSETS.left;
/* 290:352 */       newInsets.bottom = INSETS.bottom;
/* 291:353 */       newInsets.right = INSETS.right;
/* 292:354 */       return newInsets;
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   private static final class RolloverButtonBorder
/* 297:    */     extends PlasticXPBorders.XPButtonBorder
/* 298:    */   {
/* 299:    */     private RolloverButtonBorder()
/* 300:    */     {
/* 301:365 */       super();
/* 302:    */     }
/* 303:    */     
/* 304:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 305:    */     {
/* 306:369 */       AbstractButton b = (AbstractButton)c;
/* 307:370 */       ButtonModel model = b.getModel();
/* 308:372 */       if (!model.isEnabled()) {
/* 309:373 */         return;
/* 310:    */       }
/* 311:375 */       if (!(c instanceof JToggleButton))
/* 312:    */       {
/* 313:376 */         if ((model.isRollover()) && ((!model.isPressed()) || (model.isArmed()))) {
/* 314:377 */           super.paintBorder(c, g, x, y, w, h);
/* 315:    */         }
/* 316:379 */         return;
/* 317:    */       }
/* 318:382 */       if (model.isRollover())
/* 319:    */       {
/* 320:383 */         if ((model.isPressed()) && (model.isArmed())) {
/* 321:384 */           PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 322:    */         } else {
/* 323:386 */           PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
/* 324:    */         }
/* 325:    */       }
/* 326:388 */       else if (model.isSelected()) {
/* 327:389 */         PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
/* 328:    */       }
/* 329:    */     }
/* 330:    */   }
/* 331:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPBorders
 * JD-Core Version:    0.7.0.1
 */