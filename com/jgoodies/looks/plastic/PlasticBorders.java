/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import javax.swing.AbstractButton;
/*   7:    */ import javax.swing.ButtonModel;
/*   8:    */ import javax.swing.JButton;
/*   9:    */ import javax.swing.JInternalFrame;
/*  10:    */ import javax.swing.JMenuItem;
/*  11:    */ import javax.swing.JToggleButton;
/*  12:    */ import javax.swing.UIManager;
/*  13:    */ import javax.swing.border.AbstractBorder;
/*  14:    */ import javax.swing.border.Border;
/*  15:    */ import javax.swing.border.CompoundBorder;
/*  16:    */ import javax.swing.border.EmptyBorder;
/*  17:    */ import javax.swing.plaf.BorderUIResource;
/*  18:    */ import javax.swing.plaf.BorderUIResource.CompoundBorderUIResource;
/*  19:    */ import javax.swing.plaf.UIResource;
/*  20:    */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*  21:    */ import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;
/*  22:    */ import javax.swing.text.JTextComponent;
/*  23:    */ 
/*  24:    */ final class PlasticBorders
/*  25:    */ {
/*  26:    */   private static Border comboBoxEditorBorder;
/*  27:    */   private static Border comboBoxArrowButtonBorder;
/*  28:    */   private static Border etchedBorder;
/*  29:    */   private static Border flush3DBorder;
/*  30:    */   private static Border menuBarHeaderBorder;
/*  31:    */   private static Border menuBorder;
/*  32:    */   private static Border menuItemBorder;
/*  33:    */   private static Border popupMenuBorder;
/*  34:    */   private static Border noMarginPopupMenuBorder;
/*  35:    */   private static Border rolloverButtonBorder;
/*  36:    */   private static Border scrollPaneBorder;
/*  37:    */   private static Border separatorBorder;
/*  38:    */   private static Border textFieldBorder;
/*  39:    */   private static Border thinLoweredBorder;
/*  40:    */   private static Border thinRaisedBorder;
/*  41:    */   private static Border toolBarHeaderBorder;
/*  42:    */   
/*  43:    */   static Border getButtonBorder(Insets buttonMargin)
/*  44:    */   {
/*  45: 90 */     return new BorderUIResource.CompoundBorderUIResource(new ButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
/*  46:    */   }
/*  47:    */   
/*  48:    */   static Border getComboBoxArrowButtonBorder()
/*  49:    */   {
/*  50:101 */     if (comboBoxArrowButtonBorder == null) {
/*  51:102 */       comboBoxArrowButtonBorder = new CompoundBorder(new ComboBoxArrowButtonBorder(null), new BasicBorders.MarginBorder());
/*  52:    */     }
/*  53:106 */     return comboBoxArrowButtonBorder;
/*  54:    */   }
/*  55:    */   
/*  56:    */   static Border getComboBoxEditorBorder()
/*  57:    */   {
/*  58:115 */     if (comboBoxEditorBorder == null) {
/*  59:116 */       comboBoxEditorBorder = new CompoundBorder(new ComboBoxEditorBorder(null), new BasicBorders.MarginBorder());
/*  60:    */     }
/*  61:120 */     return comboBoxEditorBorder;
/*  62:    */   }
/*  63:    */   
/*  64:    */   static Border getEtchedBorder()
/*  65:    */   {
/*  66:130 */     if (etchedBorder == null) {
/*  67:131 */       etchedBorder = new BorderUIResource.CompoundBorderUIResource(new EtchedBorder(null), new BasicBorders.MarginBorder());
/*  68:    */     }
/*  69:135 */     return etchedBorder;
/*  70:    */   }
/*  71:    */   
/*  72:    */   static Border getFlush3DBorder()
/*  73:    */   {
/*  74:144 */     if (flush3DBorder == null) {
/*  75:145 */       flush3DBorder = new Flush3DBorder(null);
/*  76:    */     }
/*  77:147 */     return flush3DBorder;
/*  78:    */   }
/*  79:    */   
/*  80:    */   static Border getInternalFrameBorder()
/*  81:    */   {
/*  82:156 */     return new InternalFrameBorder(null);
/*  83:    */   }
/*  84:    */   
/*  85:    */   static Border getMenuBarHeaderBorder()
/*  86:    */   {
/*  87:166 */     if (menuBarHeaderBorder == null) {
/*  88:167 */       menuBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBarHeaderBorder(null), new BasicBorders.MarginBorder());
/*  89:    */     }
/*  90:171 */     return menuBarHeaderBorder;
/*  91:    */   }
/*  92:    */   
/*  93:    */   static Border getMenuBorder()
/*  94:    */   {
/*  95:180 */     if (menuBorder == null) {
/*  96:181 */       menuBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBorder(null), new BasicBorders.MarginBorder());
/*  97:    */     }
/*  98:185 */     return menuBorder;
/*  99:    */   }
/* 100:    */   
/* 101:    */   static Border getMenuItemBorder()
/* 102:    */   {
/* 103:194 */     if (menuItemBorder == null) {
/* 104:195 */       menuItemBorder = new BorderUIResource(new BasicBorders.MarginBorder());
/* 105:    */     }
/* 106:198 */     return menuItemBorder;
/* 107:    */   }
/* 108:    */   
/* 109:    */   static Border getPopupMenuBorder()
/* 110:    */   {
/* 111:207 */     if (popupMenuBorder == null) {
/* 112:208 */       popupMenuBorder = new PopupMenuBorder(null);
/* 113:    */     }
/* 114:210 */     return popupMenuBorder;
/* 115:    */   }
/* 116:    */   
/* 117:    */   static Border getNoMarginPopupMenuBorder()
/* 118:    */   {
/* 119:220 */     if (noMarginPopupMenuBorder == null) {
/* 120:221 */       noMarginPopupMenuBorder = new NoMarginPopupMenuBorder(null);
/* 121:    */     }
/* 122:223 */     return noMarginPopupMenuBorder;
/* 123:    */   }
/* 124:    */   
/* 125:    */   static Border getPaletteBorder()
/* 126:    */   {
/* 127:232 */     return new PaletteBorder(null);
/* 128:    */   }
/* 129:    */   
/* 130:    */   static Border getRolloverButtonBorder()
/* 131:    */   {
/* 132:241 */     if (rolloverButtonBorder == null) {
/* 133:242 */       rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(null), new RolloverMarginBorder());
/* 134:    */     }
/* 135:246 */     return rolloverButtonBorder;
/* 136:    */   }
/* 137:    */   
/* 138:    */   static Border getScrollPaneBorder()
/* 139:    */   {
/* 140:255 */     if (scrollPaneBorder == null) {
/* 141:256 */       scrollPaneBorder = new ScrollPaneBorder(null);
/* 142:    */     }
/* 143:258 */     return scrollPaneBorder;
/* 144:    */   }
/* 145:    */   
/* 146:    */   static Border getSeparatorBorder()
/* 147:    */   {
/* 148:268 */     if (separatorBorder == null) {
/* 149:269 */       separatorBorder = new BorderUIResource.CompoundBorderUIResource(new SeparatorBorder(null), new BasicBorders.MarginBorder());
/* 150:    */     }
/* 151:273 */     return separatorBorder;
/* 152:    */   }
/* 153:    */   
/* 154:    */   static Border getTextFieldBorder()
/* 155:    */   {
/* 156:282 */     if (textFieldBorder == null) {
/* 157:283 */       textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new TextFieldBorder(null), new BasicBorders.MarginBorder());
/* 158:    */     }
/* 159:287 */     return textFieldBorder;
/* 160:    */   }
/* 161:    */   
/* 162:    */   static Border getThinLoweredBorder()
/* 163:    */   {
/* 164:296 */     if (thinLoweredBorder == null) {
/* 165:297 */       thinLoweredBorder = new ThinLoweredBorder(null);
/* 166:    */     }
/* 167:299 */     return thinLoweredBorder;
/* 168:    */   }
/* 169:    */   
/* 170:    */   static Border getThinRaisedBorder()
/* 171:    */   {
/* 172:308 */     if (thinRaisedBorder == null) {
/* 173:309 */       thinRaisedBorder = new ThinRaisedBorder(null);
/* 174:    */     }
/* 175:311 */     return thinRaisedBorder;
/* 176:    */   }
/* 177:    */   
/* 178:    */   static Border getToggleButtonBorder(Insets buttonMargin)
/* 179:    */   {
/* 180:320 */     return new BorderUIResource.CompoundBorderUIResource(new ToggleButtonBorder(buttonMargin, null), new BasicBorders.MarginBorder());
/* 181:    */   }
/* 182:    */   
/* 183:    */   static Border getToolBarHeaderBorder()
/* 184:    */   {
/* 185:332 */     if (toolBarHeaderBorder == null) {
/* 186:333 */       toolBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new ToolBarHeaderBorder(null), new BasicBorders.MarginBorder());
/* 187:    */     }
/* 188:337 */     return toolBarHeaderBorder;
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static class Flush3DBorder
/* 192:    */     extends AbstractBorder
/* 193:    */     implements UIResource
/* 194:    */   {
/* 195:342 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/* 196:    */     
/* 197:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 198:    */     {
/* 199:345 */       if (c.isEnabled()) {
/* 200:346 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 201:    */       } else {
/* 202:348 */         PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/* 203:    */       }
/* 204:    */     }
/* 205:    */     
/* 206:    */     public Insets getBorderInsets(Component c)
/* 207:    */     {
/* 208:351 */       return INSETS;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 212:    */     {
/* 213:354 */       newInsets.top = INSETS.top;
/* 214:355 */       newInsets.left = INSETS.left;
/* 215:356 */       newInsets.bottom = INSETS.bottom;
/* 216:357 */       newInsets.right = INSETS.right;
/* 217:358 */       return newInsets;
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static class ButtonBorder
/* 222:    */     extends AbstractBorder
/* 223:    */     implements UIResource
/* 224:    */   {
/* 225:    */     protected final Insets insets;
/* 226:    */     
/* 227:    */     protected ButtonBorder(Insets insets)
/* 228:    */     {
/* 229:368 */       this.insets = insets;
/* 230:    */     }
/* 231:    */     
/* 232:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 233:    */     {
/* 234:372 */       AbstractButton button = (AbstractButton)c;
/* 235:373 */       ButtonModel model = button.getModel();
/* 236:375 */       if (model.isEnabled())
/* 237:    */       {
/* 238:376 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 239:377 */         boolean isDefault = ((button instanceof JButton)) && (((JButton)button).isDefaultButton());
/* 240:380 */         if ((isPressed) && (isDefault)) {
/* 241:381 */           PlasticUtils.drawDefaultButtonPressedBorder(g, x, y, w, h);
/* 242:382 */         } else if (isPressed) {
/* 243:383 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 244:384 */         } else if (isDefault) {
/* 245:385 */           PlasticUtils.drawDefaultButtonBorder(g, x, y, w, h, false);
/* 246:    */         } else {
/* 247:387 */           PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
/* 248:    */         }
/* 249:    */       }
/* 250:    */       else
/* 251:    */       {
/* 252:389 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/* 253:    */       }
/* 254:    */     }
/* 255:    */     
/* 256:    */     public Insets getBorderInsets(Component c)
/* 257:    */     {
/* 258:393 */       return this.insets;
/* 259:    */     }
/* 260:    */     
/* 261:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 262:    */     {
/* 263:396 */       newInsets.top = this.insets.top;
/* 264:397 */       newInsets.left = this.insets.left;
/* 265:398 */       newInsets.bottom = this.insets.bottom;
/* 266:399 */       newInsets.right = this.insets.right;
/* 267:400 */       return newInsets;
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   private static final class ComboBoxArrowButtonBorder
/* 272:    */     extends AbstractBorder
/* 273:    */     implements UIResource
/* 274:    */   {
/* 275:407 */     protected static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 276:    */     
/* 277:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 278:    */     {
/* 279:410 */       AbstractButton button = (AbstractButton)c;
/* 280:411 */       ButtonModel model = button.getModel();
/* 281:413 */       if (model.isEnabled())
/* 282:    */       {
/* 283:414 */         boolean isPressed = (model.isPressed()) && (model.isArmed());
/* 284:416 */         if (isPressed) {
/* 285:417 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 286:    */         } else {
/* 287:419 */           PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
/* 288:    */         }
/* 289:    */       }
/* 290:    */       else
/* 291:    */       {
/* 292:421 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/* 293:    */       }
/* 294:    */     }
/* 295:    */     
/* 296:    */     public Insets getBorderInsets(Component c)
/* 297:    */     {
/* 298:425 */       return INSETS;
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   private static final class ComboBoxEditorBorder
/* 303:    */     extends AbstractBorder
/* 304:    */   {
/* 305:431 */     private static final Insets INSETS = new Insets(2, 2, 2, 0);
/* 306:    */     
/* 307:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 308:    */     {
/* 309:434 */       if (c.isEnabled())
/* 310:    */       {
/* 311:435 */         PlasticUtils.drawFlush3DBorder(g, x, y, w + 2, h);
/* 312:    */       }
/* 313:    */       else
/* 314:    */       {
/* 315:437 */         PlasticUtils.drawDisabledBorder(g, x, y, w + 2, h - 1);
/* 316:438 */         g.setColor(UIManager.getColor("control"));
/* 317:439 */         g.drawLine(x, y + h - 1, x + w, y + h - 1);
/* 318:    */       }
/* 319:    */     }
/* 320:    */     
/* 321:    */     public Insets getBorderInsets(Component c)
/* 322:    */     {
/* 323:443 */       return INSETS;
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private static final class InternalFrameBorder
/* 328:    */     extends AbstractBorder
/* 329:    */     implements UIResource
/* 330:    */   {
/* 331:452 */     private static final Insets NORMAL_INSETS = new Insets(1, 1, 1, 1);
/* 332:453 */     private static final Insets MAXIMIZED_INSETS = new Insets(1, 1, 0, 0);
/* 333:    */     
/* 334:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 335:    */     {
/* 336:457 */       JInternalFrame frame = (JInternalFrame)c;
/* 337:458 */       if (frame.isMaximum()) {
/* 338:459 */         paintMaximizedBorder(g, x, y, w, h);
/* 339:    */       } else {
/* 340:461 */         PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
/* 341:    */       }
/* 342:    */     }
/* 343:    */     
/* 344:    */     private void paintMaximizedBorder(Graphics g, int x, int y, int w, int h)
/* 345:    */     {
/* 346:465 */       g.translate(x, y);
/* 347:466 */       g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 348:467 */       g.drawLine(0, 0, w - 2, 0);
/* 349:468 */       g.drawLine(0, 0, 0, h - 2);
/* 350:469 */       g.translate(-x, -y);
/* 351:    */     }
/* 352:    */     
/* 353:    */     public Insets getBorderInsets(Component c)
/* 354:    */     {
/* 355:473 */       return ((JInternalFrame)c).isMaximum() ? MAXIMIZED_INSETS : NORMAL_INSETS;
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   private static final class PaletteBorder
/* 360:    */     extends AbstractBorder
/* 361:    */     implements UIResource
/* 362:    */   {
/* 363:483 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 364:    */     
/* 365:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 366:    */     {
/* 367:486 */       g.translate(x, y);
/* 368:487 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 369:488 */       g.drawRect(0, 0, w - 1, h - 1);
/* 370:489 */       g.translate(-x, -y);
/* 371:    */     }
/* 372:    */     
/* 373:    */     public Insets getBorderInsets(Component c)
/* 374:    */     {
/* 375:492 */       return INSETS;
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   private static final class SeparatorBorder
/* 380:    */     extends AbstractBorder
/* 381:    */     implements UIResource
/* 382:    */   {
/* 383:502 */     private static final Insets INSETS = new Insets(0, 0, 2, 1);
/* 384:    */     
/* 385:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 386:    */     {
/* 387:505 */       g.translate(x, y);
/* 388:506 */       g.setColor(UIManager.getColor("Separator.foreground"));
/* 389:507 */       g.drawLine(0, h - 2, w - 1, h - 2);
/* 390:    */       
/* 391:509 */       g.setColor(UIManager.getColor("Separator.background"));
/* 392:510 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 393:511 */       g.translate(-x, -y);
/* 394:    */     }
/* 395:    */     
/* 396:    */     public Insets getBorderInsets(Component c)
/* 397:    */     {
/* 398:513 */       return INSETS;
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private static final class ThinRaisedBorder
/* 403:    */     extends AbstractBorder
/* 404:    */     implements UIResource
/* 405:    */   {
/* 406:518 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/* 407:    */     
/* 408:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 409:    */     {
/* 410:521 */       PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
/* 411:    */     }
/* 412:    */     
/* 413:    */     public Insets getBorderInsets(Component c)
/* 414:    */     {
/* 415:524 */       return INSETS;
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   private static final class ThinLoweredBorder
/* 420:    */     extends AbstractBorder
/* 421:    */     implements UIResource
/* 422:    */   {
/* 423:529 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/* 424:    */     
/* 425:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 426:    */     {
/* 427:532 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
/* 428:    */     }
/* 429:    */     
/* 430:    */     public Insets getBorderInsets(Component c)
/* 431:    */     {
/* 432:535 */       return INSETS;
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   private static final class EtchedBorder
/* 437:    */     extends AbstractBorder
/* 438:    */     implements UIResource
/* 439:    */   {
/* 440:547 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/* 441:    */     
/* 442:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 443:    */     {
/* 444:550 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
/* 445:551 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 2);
/* 446:    */     }
/* 447:    */     
/* 448:    */     public Insets getBorderInsets(Component c)
/* 449:    */     {
/* 450:554 */       return INSETS;
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   private static final class MenuBarHeaderBorder
/* 455:    */     extends AbstractBorder
/* 456:    */     implements UIResource
/* 457:    */   {
/* 458:565 */     private static final Insets INSETS = new Insets(2, 2, 1, 2);
/* 459:    */     
/* 460:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 461:    */     {
/* 462:568 */       PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h + 1);
/* 463:569 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 1);
/* 464:    */     }
/* 465:    */     
/* 466:    */     public Insets getBorderInsets(Component c)
/* 467:    */     {
/* 468:572 */       return INSETS;
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   private static final class ToolBarHeaderBorder
/* 473:    */     extends AbstractBorder
/* 474:    */     implements UIResource
/* 475:    */   {
/* 476:583 */     private static final Insets INSETS = new Insets(1, 2, 2, 2);
/* 477:    */     
/* 478:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 479:    */     {
/* 480:586 */       PlasticUtils.drawThinPressed3DBorder(g, x, y - 1, w, h + 1);
/* 481:587 */       PlasticUtils.drawThinFlush3DBorder(g, x + 1, y, w - 2, h - 1);
/* 482:    */     }
/* 483:    */     
/* 484:    */     public Insets getBorderInsets(Component c)
/* 485:    */     {
/* 486:590 */       return INSETS;
/* 487:    */     }
/* 488:    */   }
/* 489:    */   
/* 490:    */   private static final class MenuBorder
/* 491:    */     extends AbstractBorder
/* 492:    */     implements UIResource
/* 493:    */   {
/* 494:595 */     private static final Insets INSETS = new Insets(2, 2, 2, 2);
/* 495:    */     
/* 496:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 497:    */     {
/* 498:598 */       JMenuItem b = (JMenuItem)c;
/* 499:599 */       ButtonModel model = b.getModel();
/* 500:601 */       if ((model.isArmed()) || (model.isSelected()))
/* 501:    */       {
/* 502:602 */         g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 503:603 */         g.drawLine(0, 0, w - 2, 0);
/* 504:604 */         g.drawLine(0, 0, 0, h - 1);
/* 505:    */         
/* 506:    */ 
/* 507:607 */         g.setColor(PlasticLookAndFeel.getPrimaryControlHighlight());
/* 508:608 */         g.drawLine(w - 1, 0, w - 1, h - 1);
/* 509:    */       }
/* 510:609 */       else if (model.isRollover())
/* 511:    */       {
/* 512:610 */         g.translate(x, y);
/* 513:611 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 514:612 */         g.translate(-x, -y);
/* 515:    */       }
/* 516:    */     }
/* 517:    */     
/* 518:    */     public Insets getBorderInsets(Component c)
/* 519:    */     {
/* 520:616 */       return INSETS;
/* 521:    */     }
/* 522:    */     
/* 523:    */     public Insets getBorderInsets(Component c, Insets newInsets)
/* 524:    */     {
/* 525:619 */       newInsets.top = INSETS.top;
/* 526:620 */       newInsets.left = INSETS.left;
/* 527:621 */       newInsets.bottom = INSETS.bottom;
/* 528:622 */       newInsets.right = INSETS.right;
/* 529:623 */       return newInsets;
/* 530:    */     }
/* 531:    */   }
/* 532:    */   
/* 533:    */   private static final class PopupMenuBorder
/* 534:    */     extends AbstractBorder
/* 535:    */     implements UIResource
/* 536:    */   {
/* 537:629 */     private static final Insets INSETS = new Insets(3, 3, 3, 3);
/* 538:    */     
/* 539:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 540:    */     {
/* 541:632 */       g.translate(x, y);
/* 542:633 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 543:634 */       g.drawRect(0, 0, w - 1, h - 1);
/* 544:635 */       g.setColor(PlasticLookAndFeel.getMenuItemBackground());
/* 545:636 */       g.drawRect(1, 1, w - 3, h - 3);
/* 546:637 */       g.drawRect(2, 2, w - 5, h - 5);
/* 547:638 */       g.translate(-x, -y);
/* 548:    */     }
/* 549:    */     
/* 550:    */     public Insets getBorderInsets(Component c)
/* 551:    */     {
/* 552:641 */       return INSETS;
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   private static final class NoMarginPopupMenuBorder
/* 557:    */     extends AbstractBorder
/* 558:    */     implements UIResource
/* 559:    */   {
/* 560:646 */     private static final Insets INSETS = new Insets(1, 1, 1, 1);
/* 561:    */     
/* 562:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 563:    */     {
/* 564:649 */       g.translate(x, y);
/* 565:650 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 566:651 */       g.drawRect(0, 0, w - 1, h - 1);
/* 567:652 */       g.translate(-x, -y);
/* 568:    */     }
/* 569:    */     
/* 570:    */     public Insets getBorderInsets(Component c)
/* 571:    */     {
/* 572:655 */       return INSETS;
/* 573:    */     }
/* 574:    */   }
/* 575:    */   
/* 576:    */   private static class RolloverButtonBorder
/* 577:    */     extends PlasticBorders.ButtonBorder
/* 578:    */   {
/* 579:    */     private RolloverButtonBorder()
/* 580:    */     {
/* 581:662 */       super();
/* 582:    */     }
/* 583:    */     
/* 584:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 585:    */     {
/* 586:666 */       AbstractButton b = (AbstractButton)c;
/* 587:667 */       ButtonModel model = b.getModel();
/* 588:669 */       if (!model.isEnabled()) {
/* 589:670 */         return;
/* 590:    */       }
/* 591:672 */       if (!(c instanceof JToggleButton))
/* 592:    */       {
/* 593:673 */         if ((model.isRollover()) && ((!model.isPressed()) || (model.isArmed()))) {
/* 594:674 */           super.paintBorder(c, g, x, y, w, h);
/* 595:    */         }
/* 596:676 */         return;
/* 597:    */       }
/* 598:683 */       if (model.isRollover())
/* 599:    */       {
/* 600:684 */         if ((model.isPressed()) && (model.isArmed())) {
/* 601:685 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 602:    */         } else {
/* 603:687 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 604:    */         }
/* 605:    */       }
/* 606:689 */       else if (model.isSelected()) {
/* 607:690 */         PlasticUtils.drawDark3DBorder(g, x, y, w, h);
/* 608:    */       }
/* 609:    */     }
/* 610:    */   }
/* 611:    */   
/* 612:    */   static final class RolloverMarginBorder
/* 613:    */     extends EmptyBorder
/* 614:    */   {
/* 615:    */     RolloverMarginBorder()
/* 616:    */     {
/* 617:702 */       super(1, 1, 1);
/* 618:    */     }
/* 619:    */     
/* 620:    */     public Insets getBorderInsets(Component c)
/* 621:    */     {
/* 622:707 */       return getBorderInsets(c, new Insets(0, 0, 0, 0));
/* 623:    */     }
/* 624:    */     
/* 625:    */     public Insets getBorderInsets(Component c, Insets insets)
/* 626:    */     {
/* 627:712 */       Insets margin = null;
/* 628:714 */       if ((c instanceof AbstractButton)) {
/* 629:715 */         margin = ((AbstractButton)c).getMargin();
/* 630:    */       }
/* 631:717 */       if ((margin == null) || ((margin instanceof UIResource)))
/* 632:    */       {
/* 633:719 */         insets.left = this.left;
/* 634:720 */         insets.top = this.top;
/* 635:721 */         insets.right = this.right;
/* 636:722 */         insets.bottom = this.bottom;
/* 637:    */       }
/* 638:    */       else
/* 639:    */       {
/* 640:725 */         insets.left = margin.left;
/* 641:726 */         insets.top = margin.top;
/* 642:727 */         insets.right = margin.right;
/* 643:728 */         insets.bottom = margin.bottom;
/* 644:    */       }
/* 645:730 */       return insets;
/* 646:    */     }
/* 647:    */   }
/* 648:    */   
/* 649:    */   private static final class ScrollPaneBorder
/* 650:    */     extends MetalBorders.ScrollPaneBorder
/* 651:    */   {
/* 652:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 653:    */     {
/* 654:742 */       g.translate(x, y);
/* 655:    */       
/* 656:744 */       g.setColor(PlasticLookAndFeel.getControlDarkShadow());
/* 657:745 */       g.drawRect(0, 0, w - 2, h - 2);
/* 658:746 */       g.setColor(PlasticLookAndFeel.getControlHighlight());
/* 659:747 */       g.drawLine(w - 1, 0, w - 1, h - 1);
/* 660:748 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 661:    */       
/* 662:750 */       g.translate(-x, -y);
/* 663:    */     }
/* 664:    */   }
/* 665:    */   
/* 666:    */   private static final class TextFieldBorder
/* 667:    */     extends PlasticBorders.Flush3DBorder
/* 668:    */   {
/* 669:    */     private TextFieldBorder()
/* 670:    */     {
/* 671:755 */       super();
/* 672:    */     }
/* 673:    */     
/* 674:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 675:    */     {
/* 676:758 */       if (!(c instanceof JTextComponent))
/* 677:    */       {
/* 678:760 */         if (c.isEnabled()) {
/* 679:761 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 680:    */         } else {
/* 681:763 */           PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/* 682:    */         }
/* 683:765 */         return;
/* 684:    */       }
/* 685:768 */       if ((c.isEnabled()) && (((JTextComponent)c).isEditable())) {
/* 686:769 */         PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 687:    */       } else {
/* 688:771 */         PlasticUtils.drawDisabledBorder(g, x, y, w, h);
/* 689:    */       }
/* 690:    */     }
/* 691:    */   }
/* 692:    */   
/* 693:    */   private static final class ToggleButtonBorder
/* 694:    */     extends PlasticBorders.ButtonBorder
/* 695:    */   {
/* 696:    */     private ToggleButtonBorder(Insets insets)
/* 697:    */     {
/* 698:779 */       super();
/* 699:    */     }
/* 700:    */     
/* 701:    */     public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
/* 702:    */     {
/* 703:783 */       if (!c.isEnabled())
/* 704:    */       {
/* 705:784 */         PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
/* 706:    */       }
/* 707:    */       else
/* 708:    */       {
/* 709:786 */         AbstractButton button = (AbstractButton)c;
/* 710:787 */         ButtonModel model = button.getModel();
/* 711:788 */         if ((model.isPressed()) && (model.isArmed())) {
/* 712:789 */           PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
/* 713:790 */         } else if (model.isSelected()) {
/* 714:791 */           PlasticUtils.drawDark3DBorder(g, x, y, w, h);
/* 715:    */         } else {
/* 716:793 */           PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
/* 717:    */         }
/* 718:    */       }
/* 719:    */     }
/* 720:    */   }
/* 721:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticBorders
 * JD-Core Version:    0.7.0.1
 */