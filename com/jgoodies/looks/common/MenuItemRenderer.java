/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.ComponentOrientation;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.Font;
/*   9:    */ import java.awt.FontMetrics;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.Insets;
/*  12:    */ import java.awt.Rectangle;
/*  13:    */ import java.awt.event.KeyEvent;
/*  14:    */ import javax.swing.ButtonModel;
/*  15:    */ import javax.swing.Icon;
/*  16:    */ import javax.swing.JComponent;
/*  17:    */ import javax.swing.JMenu;
/*  18:    */ import javax.swing.JMenuItem;
/*  19:    */ import javax.swing.JPopupMenu;
/*  20:    */ import javax.swing.KeyStroke;
/*  21:    */ import javax.swing.SwingUtilities;
/*  22:    */ import javax.swing.UIManager;
/*  23:    */ import javax.swing.text.View;
/*  24:    */ 
/*  25:    */ public class MenuItemRenderer
/*  26:    */ {
/*  27:    */   protected static final String HTML_KEY = "html";
/*  28:    */   static final String MAX_TEXT_WIDTH = "maxTextWidth";
/*  29:    */   static final String MAX_ACC_WIDTH = "maxAccWidth";
/*  30: 63 */   private static final Icon NO_ICON = new NullIcon(null);
/*  31: 66 */   static Rectangle zeroRect = new Rectangle(0, 0, 0, 0);
/*  32: 67 */   static Rectangle iconRect = new Rectangle();
/*  33: 68 */   static Rectangle textRect = new Rectangle();
/*  34: 69 */   static Rectangle acceleratorRect = new Rectangle();
/*  35: 70 */   static Rectangle checkIconRect = new Rectangle();
/*  36: 71 */   static Rectangle arrowIconRect = new Rectangle();
/*  37: 72 */   static Rectangle viewRect = new Rectangle(32767, 32767);
/*  38: 73 */   static Rectangle r = new Rectangle();
/*  39:    */   private final JMenuItem menuItem;
/*  40:    */   private final boolean iconBorderEnabled;
/*  41:    */   private final Font acceleratorFont;
/*  42:    */   private final Color selectionForeground;
/*  43:    */   private final Color disabledForeground;
/*  44:    */   private final Color acceleratorForeground;
/*  45:    */   private final Color acceleratorSelectionForeground;
/*  46:    */   private final String acceleratorDelimiter;
/*  47:    */   private final Icon fillerIcon;
/*  48:    */   
/*  49:    */   public MenuItemRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground)
/*  50:    */   {
/*  51: 98 */     this.menuItem = menuItem;
/*  52: 99 */     this.iconBorderEnabled = iconBorderEnabled;
/*  53:100 */     this.acceleratorFont = acceleratorFont;
/*  54:101 */     this.selectionForeground = selectionForeground;
/*  55:102 */     this.disabledForeground = disabledForeground;
/*  56:103 */     this.acceleratorForeground = acceleratorForeground;
/*  57:104 */     this.acceleratorSelectionForeground = acceleratorSelectionForeground;
/*  58:105 */     this.acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
/*  59:106 */     this.fillerIcon = new MinimumSizedIcon();
/*  60:    */   }
/*  61:    */   
/*  62:    */   private Icon getIcon(JMenuItem aMenuItem, Icon defaultIcon)
/*  63:    */   {
/*  64:114 */     Icon icon = aMenuItem.getIcon();
/*  65:115 */     if (icon == null) {
/*  66:116 */       return defaultIcon;
/*  67:    */     }
/*  68:118 */     ButtonModel model = aMenuItem.getModel();
/*  69:119 */     if (!model.isEnabled()) {
/*  70:120 */       return model.isSelected() ? aMenuItem.getDisabledSelectedIcon() : aMenuItem.getDisabledIcon();
/*  71:    */     }
/*  72:123 */     if ((model.isPressed()) && (model.isArmed()))
/*  73:    */     {
/*  74:124 */       Icon pressedIcon = aMenuItem.getPressedIcon();
/*  75:125 */       return pressedIcon != null ? pressedIcon : icon;
/*  76:    */     }
/*  77:126 */     if (model.isSelected())
/*  78:    */     {
/*  79:127 */       Icon selectedIcon = aMenuItem.getSelectedIcon();
/*  80:128 */       return selectedIcon != null ? selectedIcon : icon;
/*  81:    */     }
/*  82:130 */     return icon;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private boolean hasCustomIcon()
/*  86:    */   {
/*  87:138 */     return getIcon(this.menuItem, null) != null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private Icon getWrappedIcon(Icon icon)
/*  91:    */   {
/*  92:146 */     if (hideIcons()) {
/*  93:147 */       return NO_ICON;
/*  94:    */     }
/*  95:148 */     if (icon == null) {
/*  96:149 */       return this.fillerIcon;
/*  97:    */     }
/*  98:150 */     return (this.iconBorderEnabled) && (hasCustomIcon()) ? new MinimumSizedCheckIcon(icon, this.menuItem) : new MinimumSizedIcon(icon);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void resetRects()
/* 102:    */   {
/* 103:157 */     iconRect.setBounds(zeroRect);
/* 104:158 */     textRect.setBounds(zeroRect);
/* 105:159 */     acceleratorRect.setBounds(zeroRect);
/* 106:160 */     checkIconRect.setBounds(zeroRect);
/* 107:161 */     arrowIconRect.setBounds(zeroRect);
/* 108:162 */     viewRect.setBounds(0, 0, 32767, 32767);
/* 109:163 */     r.setBounds(zeroRect);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int defaultTextIconGap)
/* 113:    */   {
/* 114:170 */     JMenuItem b = (JMenuItem)c;
/* 115:171 */     String text = b.getText();
/* 116:172 */     KeyStroke accelerator = b.getAccelerator();
/* 117:173 */     String acceleratorText = "";
/* 118:175 */     if (accelerator != null)
/* 119:    */     {
/* 120:176 */       int modifiers = accelerator.getModifiers();
/* 121:177 */       if (modifiers > 0)
/* 122:    */       {
/* 123:178 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/* 124:179 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/* 125:    */       }
/* 126:181 */       int keyCode = accelerator.getKeyCode();
/* 127:182 */       if (keyCode != 0) {
/* 128:183 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/* 129:    */       } else {
/* 130:185 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/* 131:    */       }
/* 132:    */     }
/* 133:189 */     Font font = b.getFont();
/* 134:190 */     FontMetrics fm = b.getFontMetrics(font);
/* 135:191 */     FontMetrics fmAccel = b.getFontMetrics(this.acceleratorFont);
/* 136:    */     
/* 137:193 */     resetRects();
/* 138:    */     
/* 139:195 */     Icon wrappedIcon = getWrappedIcon(getIcon(this.menuItem, checkIcon));
/* 140:196 */     Icon wrappedArrowIcon = new MinimumSizedIcon(arrowIcon);
/* 141:197 */     Icon icon = wrappedIcon.getIconHeight() > this.fillerIcon.getIconHeight() ? wrappedIcon : null;
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:201 */     layoutMenuItem(fm, text, fmAccel, acceleratorText, icon, wrappedIcon, wrappedArrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, text == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/* 146:    */     
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:211 */     r.setBounds(textRect);
/* 156:212 */     r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width, iconRect.height, r);
/* 157:    */     
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:219 */     Container parent = this.menuItem.getParent();
/* 164:222 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/* 165:    */     {
/* 166:225 */       JComponent p = (JComponent)parent;
/* 167:    */       
/* 168:    */ 
/* 169:228 */       Integer maxTextWidth = (Integer)p.getClientProperty("maxTextWidth");
/* 170:229 */       Integer maxAccWidth = (Integer)p.getClientProperty("maxAccWidth");
/* 171:    */       
/* 172:231 */       int maxTextValue = maxTextWidth != null ? maxTextWidth.intValue() : 0;
/* 173:232 */       int maxAccValue = maxAccWidth != null ? maxAccWidth.intValue() : 0;
/* 174:235 */       if (r.width < maxTextValue) {
/* 175:236 */         r.width = maxTextValue;
/* 176:    */       } else {
/* 177:238 */         p.putClientProperty("maxTextWidth", new Integer(r.width));
/* 178:    */       }
/* 179:242 */       if (acceleratorRect.width > maxAccValue)
/* 180:    */       {
/* 181:243 */         maxAccValue = acceleratorRect.width;
/* 182:244 */         p.putClientProperty("maxAccWidth", new Integer(acceleratorRect.width));
/* 183:    */       }
/* 184:248 */       r.width += maxAccValue;
/* 185:249 */       r.width += 10;
/* 186:    */     }
/* 187:252 */     if (useCheckAndArrow())
/* 188:    */     {
/* 189:254 */       r.width += checkIconRect.width;
/* 190:255 */       r.width += defaultTextIconGap;
/* 191:    */       
/* 192:    */ 
/* 193:258 */       r.width += defaultTextIconGap;
/* 194:259 */       r.width += arrowIconRect.width;
/* 195:    */     }
/* 196:262 */     r.width += 2 * defaultTextIconGap;
/* 197:    */     
/* 198:264 */     Insets insets = b.getInsets();
/* 199:265 */     if (insets != null)
/* 200:    */     {
/* 201:266 */       r.width += insets.left + insets.right;
/* 202:267 */       r.height += insets.top + insets.bottom;
/* 203:    */     }
/* 204:280 */     if (r.height % 2 == 1) {
/* 205:281 */       r.height += 1;
/* 206:    */     }
/* 207:283 */     return r.getSize();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap)
/* 211:    */   {
/* 212:290 */     JMenuItem b = (JMenuItem)c;
/* 213:291 */     ButtonModel model = b.getModel();
/* 214:    */     
/* 215:    */ 
/* 216:294 */     int menuWidth = b.getWidth();
/* 217:295 */     int menuHeight = b.getHeight();
/* 218:296 */     Insets i = c.getInsets();
/* 219:    */     
/* 220:298 */     resetRects();
/* 221:    */     
/* 222:300 */     viewRect.setBounds(0, 0, menuWidth, menuHeight);
/* 223:    */     
/* 224:302 */     viewRect.x += i.left;
/* 225:303 */     viewRect.y += i.top;
/* 226:304 */     viewRect.width -= i.right + viewRect.x;
/* 227:305 */     viewRect.height -= i.bottom + viewRect.y;
/* 228:    */     
/* 229:307 */     Font holdf = g.getFont();
/* 230:308 */     Font f = c.getFont();
/* 231:309 */     g.setFont(f);
/* 232:310 */     FontMetrics fm = g.getFontMetrics(f);
/* 233:311 */     FontMetrics fmAccel = g.getFontMetrics(this.acceleratorFont);
/* 234:    */     
/* 235:    */ 
/* 236:314 */     KeyStroke accelerator = b.getAccelerator();
/* 237:315 */     String acceleratorText = "";
/* 238:316 */     if (accelerator != null)
/* 239:    */     {
/* 240:317 */       int modifiers = accelerator.getModifiers();
/* 241:318 */       if (modifiers > 0)
/* 242:    */       {
/* 243:319 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/* 244:320 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/* 245:    */       }
/* 246:323 */       int keyCode = accelerator.getKeyCode();
/* 247:324 */       if (keyCode != 0) {
/* 248:325 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/* 249:    */       } else {
/* 250:327 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/* 251:    */       }
/* 252:    */     }
/* 253:331 */     Icon wrappedIcon = getWrappedIcon(getIcon(this.menuItem, checkIcon));
/* 254:332 */     Icon wrappedArrowIcon = new MinimumSizedIcon(arrowIcon);
/* 255:    */     
/* 256:    */ 
/* 257:335 */     String text = layoutMenuItem(fm, b.getText(), fmAccel, acceleratorText, null, wrappedIcon, wrappedArrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, b.getText() == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/* 258:    */     
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:346 */     paintBackground(g, b, background);
/* 269:    */     
/* 270:    */ 
/* 271:349 */     Color holdc = g.getColor();
/* 272:350 */     if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/* 273:351 */       g.setColor(foreground);
/* 274:    */     }
/* 275:353 */     wrappedIcon.paintIcon(c, g, checkIconRect.x, checkIconRect.y);
/* 276:354 */     g.setColor(holdc);
/* 277:358 */     if (text != null)
/* 278:    */     {
/* 279:359 */       View v = (View)c.getClientProperty("html");
/* 280:360 */       if (v != null) {
/* 281:361 */         v.paint(g, textRect);
/* 282:    */       } else {
/* 283:363 */         paintText(g, b, textRect, text);
/* 284:    */       }
/* 285:    */     }
/* 286:368 */     if ((acceleratorText != null) && (!acceleratorText.equals("")))
/* 287:    */     {
/* 288:371 */       int accOffset = 0;
/* 289:372 */       Container parent = this.menuItem.getParent();
/* 290:373 */       if ((parent != null) && ((parent instanceof JComponent)))
/* 291:    */       {
/* 292:374 */         JComponent p = (JComponent)parent;
/* 293:375 */         Integer maxValueInt = (Integer)p.getClientProperty("maxAccWidth");
/* 294:376 */         int maxValue = maxValueInt != null ? maxValueInt.intValue() : acceleratorRect.width;
/* 295:    */         
/* 296:    */ 
/* 297:379 */         accOffset = isLeftToRight(this.menuItem) ? maxValue - acceleratorRect.width : acceleratorRect.width - maxValue;
/* 298:    */       }
/* 299:384 */       g.setFont(this.acceleratorFont);
/* 300:385 */       if (!model.isEnabled())
/* 301:    */       {
/* 302:387 */         if (!disabledTextHasShadow())
/* 303:    */         {
/* 304:388 */           g.setColor(this.disabledForeground);
/* 305:389 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/* 306:    */         }
/* 307:    */         else
/* 308:    */         {
/* 309:393 */           g.setColor(b.getBackground().brighter());
/* 310:394 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/* 311:    */           
/* 312:    */ 
/* 313:397 */           g.setColor(b.getBackground().darker());
/* 314:398 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset - 1, acceleratorRect.y + fmAccel.getAscent() - 1);
/* 315:    */         }
/* 316:    */       }
/* 317:    */       else
/* 318:    */       {
/* 319:404 */         if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/* 320:405 */           g.setColor(this.acceleratorSelectionForeground);
/* 321:    */         } else {
/* 322:407 */           g.setColor(this.acceleratorForeground);
/* 323:    */         }
/* 324:409 */         RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/* 325:    */       }
/* 326:    */     }
/* 327:416 */     if (arrowIcon != null)
/* 328:    */     {
/* 329:417 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/* 330:418 */         g.setColor(foreground);
/* 331:    */       }
/* 332:419 */       if (useCheckAndArrow()) {
/* 333:420 */         wrappedArrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
/* 334:    */       }
/* 335:    */     }
/* 336:422 */     g.setColor(holdc);
/* 337:423 */     g.setFont(holdf);
/* 338:    */   }
/* 339:    */   
/* 340:    */   private String layoutMenuItem(FontMetrics fm, String text, FontMetrics fmAccel, String acceleratorText, Icon icon, Icon checkIcon, Icon arrowIcon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewRectangle, Rectangle iconRectangle, Rectangle textRectangle, Rectangle acceleratorRectangle, Rectangle checkIconRectangle, Rectangle arrowIconRectangle, int textIconGap, int menuItemGap)
/* 341:    */   {
/* 342:446 */     SwingUtilities.layoutCompoundLabel(this.menuItem, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewRectangle, iconRectangle, textRectangle, textIconGap);
/* 343:455 */     if ((acceleratorText == null) || (acceleratorText.equals("")))
/* 344:    */     {
/* 345:456 */       acceleratorRectangle.width = (acceleratorRectangle.height = 0);
/* 346:457 */       acceleratorText = "";
/* 347:    */     }
/* 348:    */     else
/* 349:    */     {
/* 350:459 */       acceleratorRectangle.width = SwingUtilities.computeStringWidth(fmAccel, acceleratorText);
/* 351:460 */       acceleratorRectangle.height = fmAccel.getHeight();
/* 352:    */     }
/* 353:463 */     boolean useCheckAndArrow = useCheckAndArrow();
/* 354:467 */     if (useCheckAndArrow)
/* 355:    */     {
/* 356:468 */       if (checkIcon != null)
/* 357:    */       {
/* 358:469 */         checkIconRectangle.width = checkIcon.getIconWidth();
/* 359:470 */         checkIconRectangle.height = checkIcon.getIconHeight();
/* 360:    */       }
/* 361:    */       else
/* 362:    */       {
/* 363:472 */         checkIconRectangle.width = (checkIconRectangle.height = 0);
/* 364:    */       }
/* 365:477 */       if (arrowIcon != null)
/* 366:    */       {
/* 367:478 */         arrowIconRectangle.width = arrowIcon.getIconWidth();
/* 368:479 */         arrowIconRectangle.height = arrowIcon.getIconHeight();
/* 369:    */       }
/* 370:    */       else
/* 371:    */       {
/* 372:481 */         arrowIconRectangle.width = (arrowIconRectangle.height = 0);
/* 373:    */       }
/* 374:    */     }
/* 375:485 */     Rectangle labelRect = iconRectangle.union(textRectangle);
/* 376:486 */     if (isLeftToRight(this.menuItem))
/* 377:    */     {
/* 378:487 */       textRectangle.x += menuItemGap;
/* 379:488 */       iconRectangle.x += menuItemGap;
/* 380:    */       
/* 381:    */ 
/* 382:491 */       acceleratorRectangle.x = (viewRectangle.x + viewRectangle.width - arrowIconRectangle.width - menuItemGap - acceleratorRectangle.width);
/* 383:498 */       if (useCheckAndArrow)
/* 384:    */       {
/* 385:499 */         checkIconRectangle.x = viewRectangle.x;
/* 386:500 */         textRectangle.x += menuItemGap + checkIconRectangle.width;
/* 387:501 */         iconRectangle.x += menuItemGap + checkIconRectangle.width;
/* 388:502 */         arrowIconRectangle.x = (viewRectangle.x + viewRectangle.width - menuItemGap - arrowIconRectangle.width);
/* 389:    */       }
/* 390:    */     }
/* 391:    */     else
/* 392:    */     {
/* 393:505 */       textRectangle.x -= menuItemGap;
/* 394:506 */       iconRectangle.x -= menuItemGap;
/* 395:    */       
/* 396:    */ 
/* 397:509 */       acceleratorRectangle.x = (viewRectangle.x + arrowIconRectangle.width + menuItemGap);
/* 398:512 */       if (useCheckAndArrow)
/* 399:    */       {
/* 400:514 */         checkIconRectangle.x = (viewRectangle.x + viewRectangle.width - checkIconRectangle.width);
/* 401:515 */         textRectangle.x -= menuItemGap + checkIconRectangle.width;
/* 402:516 */         iconRectangle.x -= menuItemGap + checkIconRectangle.width;
/* 403:517 */         viewRectangle.x += menuItemGap;
/* 404:    */       }
/* 405:    */     }
/* 406:523 */     acceleratorRectangle.y = (labelRect.y + labelRect.height / 2 - acceleratorRectangle.height / 2);
/* 407:524 */     if (useCheckAndArrow)
/* 408:    */     {
/* 409:525 */       arrowIconRectangle.y = (labelRect.y + labelRect.height / 2 - arrowIconRectangle.height / 2);
/* 410:526 */       checkIconRectangle.y = (labelRect.y + labelRect.height / 2 - checkIconRectangle.height / 2);
/* 411:    */     }
/* 412:536 */     return text;
/* 413:    */   }
/* 414:    */   
/* 415:    */   private boolean useCheckAndArrow()
/* 416:    */   {
/* 417:544 */     boolean isTopLevelMenu = ((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu());
/* 418:    */     
/* 419:546 */     return !isTopLevelMenu;
/* 420:    */   }
/* 421:    */   
/* 422:    */   private boolean isLeftToRight(Component c)
/* 423:    */   {
/* 424:551 */     return c.getComponentOrientation().isLeftToRight();
/* 425:    */   }
/* 426:    */   
/* 427:    */   private void paintBackground(Graphics g, JMenuItem aMenuItem, Color bgColor)
/* 428:    */   {
/* 429:569 */     ButtonModel model = aMenuItem.getModel();
/* 430:571 */     if (aMenuItem.isOpaque())
/* 431:    */     {
/* 432:572 */       int menuWidth = aMenuItem.getWidth();
/* 433:573 */       int menuHeight = aMenuItem.getHeight();
/* 434:574 */       Color c = (model.isArmed()) || (((aMenuItem instanceof JMenu)) && (model.isSelected())) ? bgColor : aMenuItem.getBackground();
/* 435:    */       
/* 436:    */ 
/* 437:    */ 
/* 438:578 */       Color oldColor = g.getColor();
/* 439:579 */       g.setColor(c);
/* 440:580 */       g.fillRect(0, 0, menuWidth, menuHeight);
/* 441:581 */       g.setColor(oldColor);
/* 442:    */     }
/* 443:    */   }
/* 444:    */   
/* 445:    */   private void paintText(Graphics g, JMenuItem aMenuItem, Rectangle textRectangle, String text)
/* 446:    */   {
/* 447:596 */     ButtonModel model = aMenuItem.getModel();
/* 448:597 */     FontMetrics fm = g.getFontMetrics();
/* 449:598 */     int mnemIndex = aMenuItem.getDisplayedMnemonicIndex();
/* 450:599 */     if (isMnemonicHidden()) {
/* 451:600 */       mnemIndex = -1;
/* 452:    */     }
/* 453:603 */     if (!model.isEnabled())
/* 454:    */     {
/* 455:604 */       if (!disabledTextHasShadow())
/* 456:    */       {
/* 457:606 */         g.setColor(UIManager.getColor("MenuItem.disabledForeground"));
/* 458:607 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/* 459:    */       }
/* 460:    */       else
/* 461:    */       {
/* 462:612 */         g.setColor(aMenuItem.getBackground().brighter());
/* 463:613 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/* 464:    */         
/* 465:    */ 
/* 466:616 */         g.setColor(aMenuItem.getBackground().darker());
/* 467:617 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x - 1, textRectangle.y + fm.getAscent() - 1);
/* 468:    */       }
/* 469:    */     }
/* 470:    */     else
/* 471:    */     {
/* 472:623 */       if ((model.isArmed()) || (((aMenuItem instanceof JMenu)) && (model.isSelected()))) {
/* 473:624 */         g.setColor(this.selectionForeground);
/* 474:    */       }
/* 475:626 */       RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/* 476:    */     }
/* 477:    */   }
/* 478:    */   
/* 479:    */   protected boolean isMnemonicHidden()
/* 480:    */   {
/* 481:634 */     return false;
/* 482:    */   }
/* 483:    */   
/* 484:    */   protected boolean disabledTextHasShadow()
/* 485:    */   {
/* 486:638 */     return false;
/* 487:    */   }
/* 488:    */   
/* 489:    */   private boolean hideIcons()
/* 490:    */   {
/* 491:646 */     Component parent = this.menuItem.getParent();
/* 492:647 */     if (!(parent instanceof JPopupMenu)) {
/* 493:648 */       return false;
/* 494:    */     }
/* 495:650 */     JPopupMenu popupMenu = (JPopupMenu)parent;
/* 496:651 */     Object value = popupMenu.getClientProperty("jgoodies.noIcons");
/* 497:652 */     if (value == null)
/* 498:    */     {
/* 499:653 */       Component invoker = popupMenu.getInvoker();
/* 500:654 */       if ((invoker != null) && ((invoker instanceof JMenu))) {
/* 501:655 */         value = ((JMenu)invoker).getClientProperty("jgoodies.noIcons");
/* 502:    */       }
/* 503:    */     }
/* 504:657 */     return Boolean.TRUE.equals(value);
/* 505:    */   }
/* 506:    */   
/* 507:    */   private static class NullIcon
/* 508:    */     implements Icon
/* 509:    */   {
/* 510:    */     public int getIconWidth()
/* 511:    */     {
/* 512:665 */       return 0;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public int getIconHeight()
/* 516:    */     {
/* 517:666 */       return 0;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public void paintIcon(Component c, Graphics g, int x, int y) {}
/* 521:    */   }
/* 522:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.MenuItemRenderer
 * JD-Core Version:    0.7.0.1
 */