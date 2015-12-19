/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyEvent;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.View;
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
/*     */ public class MenuItemRenderer
/*     */ {
/*     */   protected static final String HTML_KEY = "html";
/*     */   static final String MAX_TEXT_WIDTH = "maxTextWidth";
/*     */   static final String MAX_ACC_WIDTH = "maxAccWidth";
/*  63 */   private static final Icon NO_ICON = new NullIcon(null);
/*     */   
/*     */ 
/*  66 */   static Rectangle zeroRect = new Rectangle(0, 0, 0, 0);
/*  67 */   static Rectangle iconRect = new Rectangle();
/*  68 */   static Rectangle textRect = new Rectangle();
/*  69 */   static Rectangle acceleratorRect = new Rectangle();
/*  70 */   static Rectangle checkIconRect = new Rectangle();
/*  71 */   static Rectangle arrowIconRect = new Rectangle();
/*  72 */   static Rectangle viewRect = new Rectangle(32767, 32767);
/*  73 */   static Rectangle r = new Rectangle();
/*     */   
/*     */ 
/*     */   private final JMenuItem menuItem;
/*     */   
/*     */ 
/*     */   private final boolean iconBorderEnabled;
/*     */   
/*     */   private final Font acceleratorFont;
/*     */   
/*     */   private final Color selectionForeground;
/*     */   
/*     */   private final Color disabledForeground;
/*     */   
/*     */   private final Color acceleratorForeground;
/*     */   
/*     */   private final Color acceleratorSelectionForeground;
/*     */   
/*     */   private final String acceleratorDelimiter;
/*     */   
/*     */   private final Icon fillerIcon;
/*     */   
/*     */ 
/*     */   public MenuItemRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground)
/*     */   {
/*  98 */     this.menuItem = menuItem;
/*  99 */     this.iconBorderEnabled = iconBorderEnabled;
/* 100 */     this.acceleratorFont = acceleratorFont;
/* 101 */     this.selectionForeground = selectionForeground;
/* 102 */     this.disabledForeground = disabledForeground;
/* 103 */     this.acceleratorForeground = acceleratorForeground;
/* 104 */     this.acceleratorSelectionForeground = acceleratorSelectionForeground;
/* 105 */     this.acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
/* 106 */     this.fillerIcon = new MinimumSizedIcon();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Icon getIcon(JMenuItem aMenuItem, Icon defaultIcon)
/*     */   {
/* 114 */     Icon icon = aMenuItem.getIcon();
/* 115 */     if (icon == null) {
/* 116 */       return defaultIcon;
/*     */     }
/* 118 */     ButtonModel model = aMenuItem.getModel();
/* 119 */     if (!model.isEnabled()) {
/* 120 */       return model.isSelected() ? aMenuItem.getDisabledSelectedIcon() : aMenuItem.getDisabledIcon();
/*     */     }
/*     */     
/* 123 */     if ((model.isPressed()) && (model.isArmed())) {
/* 124 */       Icon pressedIcon = aMenuItem.getPressedIcon();
/* 125 */       return pressedIcon != null ? pressedIcon : icon; }
/* 126 */     if (model.isSelected()) {
/* 127 */       Icon selectedIcon = aMenuItem.getSelectedIcon();
/* 128 */       return selectedIcon != null ? selectedIcon : icon;
/*     */     }
/* 130 */     return icon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean hasCustomIcon()
/*     */   {
/* 138 */     return getIcon(this.menuItem, null) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Icon getWrappedIcon(Icon icon)
/*     */   {
/* 146 */     if (hideIcons())
/* 147 */       return NO_ICON;
/* 148 */     if (icon == null)
/* 149 */       return this.fillerIcon;
/* 150 */     return (this.iconBorderEnabled) && (hasCustomIcon()) ? new MinimumSizedCheckIcon(icon, this.menuItem) : new MinimumSizedIcon(icon);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void resetRects()
/*     */   {
/* 157 */     iconRect.setBounds(zeroRect);
/* 158 */     textRect.setBounds(zeroRect);
/* 159 */     acceleratorRect.setBounds(zeroRect);
/* 160 */     checkIconRect.setBounds(zeroRect);
/* 161 */     arrowIconRect.setBounds(zeroRect);
/* 162 */     viewRect.setBounds(0, 0, 32767, 32767);
/* 163 */     r.setBounds(zeroRect);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int defaultTextIconGap)
/*     */   {
/* 170 */     JMenuItem b = (JMenuItem)c;
/* 171 */     String text = b.getText();
/* 172 */     KeyStroke accelerator = b.getAccelerator();
/* 173 */     String acceleratorText = "";
/*     */     
/* 175 */     if (accelerator != null) {
/* 176 */       int modifiers = accelerator.getModifiers();
/* 177 */       if (modifiers > 0) {
/* 178 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/* 179 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*     */       }
/* 181 */       int keyCode = accelerator.getKeyCode();
/* 182 */       if (keyCode != 0) {
/* 183 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*     */       } else {
/* 185 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*     */       }
/*     */     }
/*     */     
/* 189 */     Font font = b.getFont();
/* 190 */     FontMetrics fm = b.getFontMetrics(font);
/* 191 */     FontMetrics fmAccel = b.getFontMetrics(this.acceleratorFont);
/*     */     
/* 193 */     resetRects();
/*     */     
/* 195 */     Icon wrappedIcon = getWrappedIcon(getIcon(this.menuItem, checkIcon));
/* 196 */     Icon wrappedArrowIcon = new MinimumSizedIcon(arrowIcon);
/* 197 */     Icon icon = wrappedIcon.getIconHeight() > this.fillerIcon.getIconHeight() ? wrappedIcon : null;
/*     */     
/*     */ 
/*     */ 
/* 201 */     layoutMenuItem(fm, text, fmAccel, acceleratorText, icon, wrappedIcon, wrappedArrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, text == null ? 0 : defaultTextIconGap, defaultTextIconGap);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 211 */     r.setBounds(textRect);
/* 212 */     r = SwingUtilities.computeUnion(iconRect.x, iconRect.y, iconRect.width, iconRect.height, r);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 219 */     Container parent = this.menuItem.getParent();
/*     */     
/*     */ 
/* 222 */     if ((parent != null) && ((parent instanceof JComponent)) && ((!(this.menuItem instanceof JMenu)) || (!((JMenu)this.menuItem).isTopLevelMenu())))
/*     */     {
/*     */ 
/* 225 */       JComponent p = (JComponent)parent;
/*     */       
/*     */ 
/* 228 */       Integer maxTextWidth = (Integer)p.getClientProperty("maxTextWidth");
/* 229 */       Integer maxAccWidth = (Integer)p.getClientProperty("maxAccWidth");
/*     */       
/* 231 */       int maxTextValue = maxTextWidth != null ? maxTextWidth.intValue() : 0;
/* 232 */       int maxAccValue = maxAccWidth != null ? maxAccWidth.intValue() : 0;
/*     */       
/*     */ 
/* 235 */       if (r.width < maxTextValue) {
/* 236 */         r.width = maxTextValue;
/*     */       } else {
/* 238 */         p.putClientProperty("maxTextWidth", new Integer(r.width));
/*     */       }
/*     */       
/*     */ 
/* 242 */       if (acceleratorRect.width > maxAccValue) {
/* 243 */         maxAccValue = acceleratorRect.width;
/* 244 */         p.putClientProperty("maxAccWidth", new Integer(acceleratorRect.width));
/*     */       }
/*     */       
/*     */ 
/* 248 */       r.width += maxAccValue;
/* 249 */       r.width += 10;
/*     */     }
/*     */     
/* 252 */     if (useCheckAndArrow())
/*     */     {
/* 254 */       r.width += checkIconRect.width;
/* 255 */       r.width += defaultTextIconGap;
/*     */       
/*     */ 
/* 258 */       r.width += defaultTextIconGap;
/* 259 */       r.width += arrowIconRect.width;
/*     */     }
/*     */     
/* 262 */     r.width += 2 * defaultTextIconGap;
/*     */     
/* 264 */     Insets insets = b.getInsets();
/* 265 */     if (insets != null) {
/* 266 */       r.width += insets.left + insets.right;
/* 267 */       r.height += insets.top + insets.bottom;
/*     */     }
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
/* 280 */     if (r.height % 2 == 1) {
/* 281 */       r.height += 1;
/*     */     }
/* 283 */     return r.getSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap)
/*     */   {
/* 290 */     JMenuItem b = (JMenuItem)c;
/* 291 */     ButtonModel model = b.getModel();
/*     */     
/*     */ 
/* 294 */     int menuWidth = b.getWidth();
/* 295 */     int menuHeight = b.getHeight();
/* 296 */     Insets i = c.getInsets();
/*     */     
/* 298 */     resetRects();
/*     */     
/* 300 */     viewRect.setBounds(0, 0, menuWidth, menuHeight);
/*     */     
/* 302 */     viewRect.x += i.left;
/* 303 */     viewRect.y += i.top;
/* 304 */     viewRect.width -= i.right + viewRect.x;
/* 305 */     viewRect.height -= i.bottom + viewRect.y;
/*     */     
/* 307 */     Font holdf = g.getFont();
/* 308 */     Font f = c.getFont();
/* 309 */     g.setFont(f);
/* 310 */     FontMetrics fm = g.getFontMetrics(f);
/* 311 */     FontMetrics fmAccel = g.getFontMetrics(this.acceleratorFont);
/*     */     
/*     */ 
/* 314 */     KeyStroke accelerator = b.getAccelerator();
/* 315 */     String acceleratorText = "";
/* 316 */     if (accelerator != null) {
/* 317 */       int modifiers = accelerator.getModifiers();
/* 318 */       if (modifiers > 0) {
/* 319 */         acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
/* 320 */         acceleratorText = acceleratorText + this.acceleratorDelimiter;
/*     */       }
/*     */       
/* 323 */       int keyCode = accelerator.getKeyCode();
/* 324 */       if (keyCode != 0) {
/* 325 */         acceleratorText = acceleratorText + KeyEvent.getKeyText(keyCode);
/*     */       } else {
/* 327 */         acceleratorText = acceleratorText + accelerator.getKeyChar();
/*     */       }
/*     */     }
/*     */     
/* 331 */     Icon wrappedIcon = getWrappedIcon(getIcon(this.menuItem, checkIcon));
/* 332 */     Icon wrappedArrowIcon = new MinimumSizedIcon(arrowIcon);
/*     */     
/*     */ 
/* 335 */     String text = layoutMenuItem(fm, b.getText(), fmAccel, acceleratorText, null, wrappedIcon, wrappedArrowIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect, b.getText() == null ? 0 : defaultTextIconGap, defaultTextIconGap);
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
/* 346 */     paintBackground(g, b, background);
/*     */     
/*     */ 
/* 349 */     Color holdc = g.getColor();
/* 350 */     if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/* 351 */       g.setColor(foreground);
/*     */     }
/* 353 */     wrappedIcon.paintIcon(c, g, checkIconRect.x, checkIconRect.y);
/* 354 */     g.setColor(holdc);
/*     */     
/*     */ 
/*     */ 
/* 358 */     if (text != null) {
/* 359 */       View v = (View)c.getClientProperty("html");
/* 360 */       if (v != null) {
/* 361 */         v.paint(g, textRect);
/*     */       } else {
/* 363 */         paintText(g, b, textRect, text);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 368 */     if ((acceleratorText != null) && (!acceleratorText.equals("")))
/*     */     {
/*     */ 
/* 371 */       int accOffset = 0;
/* 372 */       Container parent = this.menuItem.getParent();
/* 373 */       if ((parent != null) && ((parent instanceof JComponent))) {
/* 374 */         JComponent p = (JComponent)parent;
/* 375 */         Integer maxValueInt = (Integer)p.getClientProperty("maxAccWidth");
/* 376 */         int maxValue = maxValueInt != null ? maxValueInt.intValue() : acceleratorRect.width;
/*     */         
/*     */ 
/* 379 */         accOffset = isLeftToRight(this.menuItem) ? maxValue - acceleratorRect.width : acceleratorRect.width - maxValue;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 384 */       g.setFont(this.acceleratorFont);
/* 385 */       if (!model.isEnabled())
/*     */       {
/* 387 */         if (!disabledTextHasShadow()) {
/* 388 */           g.setColor(this.disabledForeground);
/* 389 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*     */         }
/*     */         else
/*     */         {
/* 393 */           g.setColor(b.getBackground().brighter());
/* 394 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*     */           
/*     */ 
/* 397 */           g.setColor(b.getBackground().darker());
/* 398 */           RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset - 1, acceleratorRect.y + fmAccel.getAscent() - 1);
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 404 */         if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected()))) {
/* 405 */           g.setColor(this.acceleratorSelectionForeground);
/*     */         } else {
/* 407 */           g.setColor(this.acceleratorForeground);
/*     */         }
/* 409 */         RenderingUtils.drawStringUnderlineCharAt(c, g, acceleratorText, -1, acceleratorRect.x - accOffset, acceleratorRect.y + fmAccel.getAscent());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 416 */     if (arrowIcon != null) {
/* 417 */       if ((model.isArmed()) || (((c instanceof JMenu)) && (model.isSelected())))
/* 418 */         g.setColor(foreground);
/* 419 */       if (useCheckAndArrow())
/* 420 */         wrappedArrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
/*     */     }
/* 422 */     g.setColor(holdc);
/* 423 */     g.setFont(holdf);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String layoutMenuItem(FontMetrics fm, String text, FontMetrics fmAccel, String acceleratorText, Icon icon, Icon checkIcon, Icon arrowIcon, int verticalAlignment, int horizontalAlignment, int verticalTextPosition, int horizontalTextPosition, Rectangle viewRectangle, Rectangle iconRectangle, Rectangle textRectangle, Rectangle acceleratorRectangle, Rectangle checkIconRectangle, Rectangle arrowIconRectangle, int textIconGap, int menuItemGap)
/*     */   {
/* 446 */     SwingUtilities.layoutCompoundLabel(this.menuItem, fm, text, icon, verticalAlignment, horizontalAlignment, verticalTextPosition, horizontalTextPosition, viewRectangle, iconRectangle, textRectangle, textIconGap);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 455 */     if ((acceleratorText == null) || (acceleratorText.equals(""))) {
/* 456 */       acceleratorRectangle.width = (acceleratorRectangle.height = 0);
/* 457 */       acceleratorText = "";
/*     */     } else {
/* 459 */       acceleratorRectangle.width = SwingUtilities.computeStringWidth(fmAccel, acceleratorText);
/* 460 */       acceleratorRectangle.height = fmAccel.getHeight();
/*     */     }
/*     */     
/* 463 */     boolean useCheckAndArrow = useCheckAndArrow();
/*     */     
/*     */ 
/*     */ 
/* 467 */     if (useCheckAndArrow) {
/* 468 */       if (checkIcon != null) {
/* 469 */         checkIconRectangle.width = checkIcon.getIconWidth();
/* 470 */         checkIconRectangle.height = checkIcon.getIconHeight();
/*     */       } else {
/* 472 */         checkIconRectangle.width = (checkIconRectangle.height = 0);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 477 */       if (arrowIcon != null) {
/* 478 */         arrowIconRectangle.width = arrowIcon.getIconWidth();
/* 479 */         arrowIconRectangle.height = arrowIcon.getIconHeight();
/*     */       } else {
/* 481 */         arrowIconRectangle.width = (arrowIconRectangle.height = 0);
/*     */       }
/*     */     }
/*     */     
/* 485 */     Rectangle labelRect = iconRectangle.union(textRectangle);
/* 486 */     if (isLeftToRight(this.menuItem)) {
/* 487 */       textRectangle.x += menuItemGap;
/* 488 */       iconRectangle.x += menuItemGap;
/*     */       
/*     */ 
/* 491 */       acceleratorRectangle.x = (viewRectangle.x + viewRectangle.width - arrowIconRectangle.width - menuItemGap - acceleratorRectangle.width);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 498 */       if (useCheckAndArrow) {
/* 499 */         checkIconRectangle.x = viewRectangle.x;
/* 500 */         textRectangle.x += menuItemGap + checkIconRectangle.width;
/* 501 */         iconRectangle.x += menuItemGap + checkIconRectangle.width;
/* 502 */         arrowIconRectangle.x = (viewRectangle.x + viewRectangle.width - menuItemGap - arrowIconRectangle.width);
/*     */       }
/*     */     } else {
/* 505 */       textRectangle.x -= menuItemGap;
/* 506 */       iconRectangle.x -= menuItemGap;
/*     */       
/*     */ 
/* 509 */       acceleratorRectangle.x = (viewRectangle.x + arrowIconRectangle.width + menuItemGap);
/*     */       
/*     */ 
/* 512 */       if (useCheckAndArrow)
/*     */       {
/* 514 */         checkIconRectangle.x = (viewRectangle.x + viewRectangle.width - checkIconRectangle.width);
/* 515 */         textRectangle.x -= menuItemGap + checkIconRectangle.width;
/* 516 */         iconRectangle.x -= menuItemGap + checkIconRectangle.width;
/* 517 */         viewRectangle.x += menuItemGap;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 523 */     acceleratorRectangle.y = (labelRect.y + labelRect.height / 2 - acceleratorRectangle.height / 2);
/* 524 */     if (useCheckAndArrow) {
/* 525 */       arrowIconRectangle.y = (labelRect.y + labelRect.height / 2 - arrowIconRectangle.height / 2);
/* 526 */       checkIconRectangle.y = (labelRect.y + labelRect.height / 2 - checkIconRectangle.height / 2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 536 */     return text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean useCheckAndArrow()
/*     */   {
/* 544 */     boolean isTopLevelMenu = ((this.menuItem instanceof JMenu)) && (((JMenu)this.menuItem).isTopLevelMenu());
/*     */     
/* 546 */     return !isTopLevelMenu;
/*     */   }
/*     */   
/*     */   private boolean isLeftToRight(Component c)
/*     */   {
/* 551 */     return c.getComponentOrientation().isLeftToRight();
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
/*     */   private void paintBackground(Graphics g, JMenuItem aMenuItem, Color bgColor)
/*     */   {
/* 569 */     ButtonModel model = aMenuItem.getModel();
/*     */     
/* 571 */     if (aMenuItem.isOpaque()) {
/* 572 */       int menuWidth = aMenuItem.getWidth();
/* 573 */       int menuHeight = aMenuItem.getHeight();
/* 574 */       Color c = (model.isArmed()) || (((aMenuItem instanceof JMenu)) && (model.isSelected())) ? bgColor : aMenuItem.getBackground();
/*     */       
/*     */ 
/*     */ 
/* 578 */       Color oldColor = g.getColor();
/* 579 */       g.setColor(c);
/* 580 */       g.fillRect(0, 0, menuWidth, menuHeight);
/* 581 */       g.setColor(oldColor);
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
/*     */   private void paintText(Graphics g, JMenuItem aMenuItem, Rectangle textRectangle, String text)
/*     */   {
/* 596 */     ButtonModel model = aMenuItem.getModel();
/* 597 */     FontMetrics fm = g.getFontMetrics();
/* 598 */     int mnemIndex = aMenuItem.getDisplayedMnemonicIndex();
/* 599 */     if (isMnemonicHidden()) {
/* 600 */       mnemIndex = -1;
/*     */     }
/*     */     
/* 603 */     if (!model.isEnabled()) {
/* 604 */       if (!disabledTextHasShadow())
/*     */       {
/* 606 */         g.setColor(UIManager.getColor("MenuItem.disabledForeground"));
/* 607 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 612 */         g.setColor(aMenuItem.getBackground().brighter());
/* 613 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/*     */         
/*     */ 
/* 616 */         g.setColor(aMenuItem.getBackground().darker());
/* 617 */         RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x - 1, textRectangle.y + fm.getAscent() - 1);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 623 */       if ((model.isArmed()) || (((aMenuItem instanceof JMenu)) && (model.isSelected()))) {
/* 624 */         g.setColor(this.selectionForeground);
/*     */       }
/* 626 */       RenderingUtils.drawStringUnderlineCharAt(aMenuItem, g, text, mnemIndex, textRectangle.x, textRectangle.y + fm.getAscent());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isMnemonicHidden()
/*     */   {
/* 634 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean disabledTextHasShadow() {
/* 638 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean hideIcons()
/*     */   {
/* 646 */     Component parent = this.menuItem.getParent();
/* 647 */     if (!(parent instanceof JPopupMenu)) {
/* 648 */       return false;
/*     */     }
/* 650 */     JPopupMenu popupMenu = (JPopupMenu)parent;
/* 651 */     Object value = popupMenu.getClientProperty("jgoodies.noIcons");
/* 652 */     if (value == null) {
/* 653 */       Component invoker = popupMenu.getInvoker();
/* 654 */       if ((invoker != null) && ((invoker instanceof JMenu)))
/* 655 */         value = ((JMenu)invoker).getClientProperty("jgoodies.noIcons");
/*     */     }
/* 657 */     return Boolean.TRUE.equals(value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class NullIcon
/*     */     implements Icon
/*     */   {
/* 665 */     public int getIconWidth() { return 0; }
/* 666 */     public int getIconHeight() { return 0; }
/*     */     
/*     */     public void paintIcon(Component c, Graphics g, int x, int y) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\MenuItemRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */