/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.Icon;
/*   9:    */ import javax.swing.JCheckBox;
/*  10:    */ import javax.swing.JComponent;
/*  11:    */ import javax.swing.JMenuItem;
/*  12:    */ import javax.swing.plaf.UIResource;
/*  13:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*  14:    */ 
/*  15:    */ final class PlasticIconFactory
/*  16:    */ {
/*  17:    */   private static Icon checkBoxIcon;
/*  18:    */   private static Icon checkBoxMenuItemIcon;
/*  19:    */   private static Icon radioButtonMenuItemIcon;
/*  20:    */   private static Icon menuArrowIcon;
/*  21:    */   private static Icon expandedTreeIcon;
/*  22:    */   private static Icon collapsedTreeIcon;
/*  23:    */   private static Icon comboBoxButtonIcon;
/*  24:    */   
/*  25:    */   private static void drawCheck(Graphics g, int x, int y)
/*  26:    */   {
/*  27: 66 */     g.translate(x, y);
/*  28: 67 */     g.drawLine(3, 5, 3, 5);
/*  29: 68 */     g.fillRect(3, 6, 2, 2);
/*  30: 69 */     g.drawLine(4, 8, 9, 3);
/*  31: 70 */     g.drawLine(5, 8, 9, 4);
/*  32: 71 */     g.drawLine(5, 9, 9, 5);
/*  33: 72 */     g.translate(-x, -y);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static class CheckBoxIcon
/*  37:    */     implements Icon, UIResource, Serializable
/*  38:    */   {
/*  39:    */     private static final int SIZE = 13;
/*  40:    */     
/*  41:    */     public int getIconWidth()
/*  42:    */     {
/*  43: 80 */       return 13;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public int getIconHeight()
/*  47:    */     {
/*  48: 81 */       return 13;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/*  52:    */     {
/*  53: 84 */       JCheckBox cb = (JCheckBox)c;
/*  54: 85 */       ButtonModel model = cb.getModel();
/*  55: 87 */       if (model.isEnabled())
/*  56:    */       {
/*  57: 88 */         if (cb.isBorderPaintedFlat())
/*  58:    */         {
/*  59: 89 */           g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/*  60: 90 */           g.drawRect(x, y, 11, 11);
/*  61:    */           
/*  62: 92 */           g.setColor(PlasticLookAndFeel.getControlHighlight());
/*  63: 93 */           g.fillRect(x + 1, y + 1, 10, 10);
/*  64:    */         }
/*  65: 94 */         else if ((model.isPressed()) && (model.isArmed()))
/*  66:    */         {
/*  67: 95 */           g.setColor(MetalLookAndFeel.getControlShadow());
/*  68: 96 */           g.fillRect(x, y, 12, 12);
/*  69: 97 */           PlasticUtils.drawPressed3DBorder(g, x, y, 13, 13);
/*  70:    */         }
/*  71:    */         else
/*  72:    */         {
/*  73: 99 */           PlasticUtils.drawFlush3DBorder(g, x, y, 13, 13);
/*  74:    */         }
/*  75:101 */         g.setColor(MetalLookAndFeel.getControlInfo());
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:103 */         g.setColor(MetalLookAndFeel.getControlShadow());
/*  80:104 */         g.drawRect(x, y, 11, 11);
/*  81:    */       }
/*  82:107 */       if (model.isSelected()) {
/*  83:108 */         PlasticIconFactory.drawCheck(g, x, y);
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static class CheckBoxMenuItemIcon
/*  89:    */     implements Icon, UIResource, Serializable
/*  90:    */   {
/*  91:    */     private static final int SIZE = 13;
/*  92:    */     
/*  93:    */     public int getIconWidth()
/*  94:    */     {
/*  95:119 */       return 13;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public int getIconHeight()
/*  99:    */     {
/* 100:120 */       return 13;
/* 101:    */     }
/* 102:    */     
/* 103:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 104:    */     {
/* 105:123 */       JMenuItem b = (JMenuItem)c;
/* 106:124 */       if (b.isSelected()) {
/* 107:125 */         PlasticIconFactory.drawCheck(g, x, y + 1);
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static class RadioButtonMenuItemIcon
/* 113:    */     implements Icon, UIResource, Serializable
/* 114:    */   {
/* 115:    */     private static final int SIZE = 13;
/* 116:    */     
/* 117:    */     public int getIconWidth()
/* 118:    */     {
/* 119:135 */       return 13;
/* 120:    */     }
/* 121:    */     
/* 122:    */     public int getIconHeight()
/* 123:    */     {
/* 124:136 */       return 13;
/* 125:    */     }
/* 126:    */     
/* 127:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 128:    */     {
/* 129:139 */       JMenuItem b = (JMenuItem)c;
/* 130:140 */       if (b.isSelected()) {
/* 131:141 */         drawDot(g, x, y);
/* 132:    */       }
/* 133:    */     }
/* 134:    */     
/* 135:    */     private void drawDot(Graphics g, int x, int y)
/* 136:    */     {
/* 137:146 */       g.translate(x, y);
/* 138:147 */       g.drawLine(5, 4, 8, 4);
/* 139:148 */       g.fillRect(4, 5, 6, 4);
/* 140:149 */       g.drawLine(5, 9, 8, 9);
/* 141:150 */       g.translate(-x, -y);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   private static class MenuArrowIcon
/* 146:    */     implements Icon, UIResource, Serializable
/* 147:    */   {
/* 148:    */     private static final int WIDTH = 4;
/* 149:    */     private static final int HEIGHT = 8;
/* 150:    */     
/* 151:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 152:    */     {
/* 153:161 */       JMenuItem b = (JMenuItem)c;
/* 154:    */       
/* 155:163 */       g.translate(x, y);
/* 156:164 */       if (PlasticUtils.isLeftToRight(b))
/* 157:    */       {
/* 158:165 */         g.drawLine(0, 0, 0, 7);
/* 159:166 */         g.drawLine(1, 1, 1, 6);
/* 160:167 */         g.drawLine(2, 2, 2, 5);
/* 161:168 */         g.drawLine(3, 3, 3, 4);
/* 162:    */       }
/* 163:    */       else
/* 164:    */       {
/* 165:170 */         g.drawLine(4, 0, 4, 7);
/* 166:171 */         g.drawLine(3, 1, 3, 6);
/* 167:172 */         g.drawLine(2, 2, 2, 5);
/* 168:173 */         g.drawLine(1, 3, 1, 4);
/* 169:    */       }
/* 170:175 */       g.translate(-x, -y);
/* 171:    */     }
/* 172:    */     
/* 173:    */     public int getIconWidth()
/* 174:    */     {
/* 175:178 */       return 4;
/* 176:    */     }
/* 177:    */     
/* 178:    */     public int getIconHeight()
/* 179:    */     {
/* 180:179 */       return 8;
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   private static class ExpandedTreeIcon
/* 185:    */     implements Icon, Serializable
/* 186:    */   {
/* 187:    */     protected static final int SIZE = 9;
/* 188:    */     protected static final int HALF_SIZE = 4;
/* 189:    */     
/* 190:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 191:    */     {
/* 192:194 */       g.setColor(Color.WHITE);
/* 193:195 */       g.fillRect(x, y, 8, 8);
/* 194:196 */       g.setColor(Color.GRAY);
/* 195:197 */       g.drawRect(x, y, 8, 8);
/* 196:198 */       g.setColor(Color.BLACK);
/* 197:199 */       g.drawLine(x + 2, y + 4, x + 6, y + 4);
/* 198:    */     }
/* 199:    */     
/* 200:    */     public int getIconWidth()
/* 201:    */     {
/* 202:202 */       return 9;
/* 203:    */     }
/* 204:    */     
/* 205:    */     public int getIconHeight()
/* 206:    */     {
/* 207:203 */       return 9;
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   private static class CollapsedTreeIcon
/* 212:    */     extends PlasticIconFactory.ExpandedTreeIcon
/* 213:    */   {
/* 214:    */     private CollapsedTreeIcon()
/* 215:    */     {
/* 216:210 */       super();
/* 217:    */     }
/* 218:    */     
/* 219:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 220:    */     {
/* 221:212 */       super.paintIcon(c, g, x, y);
/* 222:213 */       g.drawLine(x + 4, y + 2, x + 4, y + 6);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   private static class ComboBoxButtonIcon
/* 227:    */     implements Icon, Serializable
/* 228:    */   {
/* 229:    */     public void paintIcon(Component c, Graphics g, int x, int y)
/* 230:    */     {
/* 231:224 */       JComponent component = (JComponent)c;
/* 232:225 */       int iconWidth = getIconWidth();
/* 233:    */       
/* 234:227 */       g.translate(x, y);
/* 235:    */       
/* 236:229 */       g.setColor(component.isEnabled() ? MetalLookAndFeel.getControlInfo() : MetalLookAndFeel.getControlShadow());
/* 237:    */       
/* 238:    */ 
/* 239:232 */       g.drawLine(0, 0, iconWidth - 1, 0);
/* 240:233 */       g.drawLine(1, 1, 1 + (iconWidth - 3), 1);
/* 241:234 */       g.drawLine(2, 2, 2 + (iconWidth - 5), 2);
/* 242:235 */       g.drawLine(3, 3, 3 + (iconWidth - 7), 3);
/* 243:    */       
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:250 */       g.translate(-x, -y);
/* 258:    */     }
/* 259:    */     
/* 260:    */     public int getIconWidth()
/* 261:    */     {
/* 262:253 */       return 8;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public int getIconHeight()
/* 266:    */     {
/* 267:254 */       return 4;
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   static Icon getCheckBoxIcon()
/* 272:    */   {
/* 273:273 */     if (checkBoxIcon == null) {
/* 274:274 */       checkBoxIcon = new CheckBoxIcon(null);
/* 275:    */     }
/* 276:276 */     return checkBoxIcon;
/* 277:    */   }
/* 278:    */   
/* 279:    */   static Icon getCheckBoxMenuItemIcon()
/* 280:    */   {
/* 281:284 */     if (checkBoxMenuItemIcon == null) {
/* 282:285 */       checkBoxMenuItemIcon = new CheckBoxMenuItemIcon(null);
/* 283:    */     }
/* 284:287 */     return checkBoxMenuItemIcon;
/* 285:    */   }
/* 286:    */   
/* 287:    */   static Icon getRadioButtonMenuItemIcon()
/* 288:    */   {
/* 289:295 */     if (radioButtonMenuItemIcon == null) {
/* 290:296 */       radioButtonMenuItemIcon = new RadioButtonMenuItemIcon(null);
/* 291:    */     }
/* 292:298 */     return radioButtonMenuItemIcon;
/* 293:    */   }
/* 294:    */   
/* 295:    */   static Icon getMenuArrowIcon()
/* 296:    */   {
/* 297:306 */     if (menuArrowIcon == null) {
/* 298:307 */       menuArrowIcon = new MenuArrowIcon(null);
/* 299:    */     }
/* 300:309 */     return menuArrowIcon;
/* 301:    */   }
/* 302:    */   
/* 303:    */   static Icon getExpandedTreeIcon()
/* 304:    */   {
/* 305:317 */     if (expandedTreeIcon == null) {
/* 306:318 */       expandedTreeIcon = new ExpandedTreeIcon(null);
/* 307:    */     }
/* 308:320 */     return expandedTreeIcon;
/* 309:    */   }
/* 310:    */   
/* 311:    */   static Icon getCollapsedTreeIcon()
/* 312:    */   {
/* 313:327 */     if (collapsedTreeIcon == null) {
/* 314:328 */       collapsedTreeIcon = new CollapsedTreeIcon(null);
/* 315:    */     }
/* 316:330 */     return collapsedTreeIcon;
/* 317:    */   }
/* 318:    */   
/* 319:    */   static Icon getComboBoxButtonIcon()
/* 320:    */   {
/* 321:337 */     if (comboBoxButtonIcon == null) {
/* 322:338 */       comboBoxButtonIcon = new ComboBoxButtonIcon(null);
/* 323:    */     }
/* 324:340 */     return comboBoxButtonIcon;
/* 325:    */   }
/* 326:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticIconFactory
 * JD-Core Version:    0.7.0.1
 */