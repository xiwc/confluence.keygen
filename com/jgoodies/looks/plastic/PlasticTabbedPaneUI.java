/*    1:     */ package com.jgoodies.looks.plastic;
/*    2:     */ 
/*    3:     */ import com.jgoodies.looks.LookUtils;
/*    4:     */ import com.jgoodies.looks.Options;
/*    5:     */ import java.awt.Color;
/*    6:     */ import java.awt.Component;
/*    7:     */ import java.awt.Container;
/*    8:     */ import java.awt.Dimension;
/*    9:     */ import java.awt.Font;
/*   10:     */ import java.awt.FontMetrics;
/*   11:     */ import java.awt.Graphics;
/*   12:     */ import java.awt.Graphics2D;
/*   13:     */ import java.awt.Insets;
/*   14:     */ import java.awt.LayoutManager;
/*   15:     */ import java.awt.Point;
/*   16:     */ import java.awt.Polygon;
/*   17:     */ import java.awt.Rectangle;
/*   18:     */ import java.awt.Shape;
/*   19:     */ import java.awt.event.ActionEvent;
/*   20:     */ import java.awt.event.ActionListener;
/*   21:     */ import java.awt.event.MouseEvent;
/*   22:     */ import java.beans.PropertyChangeEvent;
/*   23:     */ import java.beans.PropertyChangeListener;
/*   24:     */ import javax.swing.AbstractAction;
/*   25:     */ import javax.swing.Action;
/*   26:     */ import javax.swing.ActionMap;
/*   27:     */ import javax.swing.Icon;
/*   28:     */ import javax.swing.JButton;
/*   29:     */ import javax.swing.JComponent;
/*   30:     */ import javax.swing.JPanel;
/*   31:     */ import javax.swing.JTabbedPane;
/*   32:     */ import javax.swing.JViewport;
/*   33:     */ import javax.swing.SwingUtilities;
/*   34:     */ import javax.swing.UIManager;
/*   35:     */ import javax.swing.event.ChangeEvent;
/*   36:     */ import javax.swing.event.ChangeListener;
/*   37:     */ import javax.swing.plaf.ComponentUI;
/*   38:     */ import javax.swing.plaf.UIResource;
/*   39:     */ import javax.swing.plaf.basic.BasicTabbedPaneUI;
/*   40:     */ import javax.swing.plaf.basic.BasicTabbedPaneUI.PropertyChangeHandler;
/*   41:     */ import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
/*   42:     */ import javax.swing.plaf.metal.MetalTabbedPaneUI;
/*   43:     */ import javax.swing.text.View;
/*   44:     */ 
/*   45:     */ public final class PlasticTabbedPaneUI
/*   46:     */   extends MetalTabbedPaneUI
/*   47:     */ {
/*   48:  96 */   private static boolean isTabIconsEnabled = ;
/*   49:     */   private Boolean noContentBorder;
/*   50:     */   private Boolean embeddedTabs;
/*   51:     */   private AbstractRenderer renderer;
/*   52:     */   private ScrollableTabSupport tabScroller;
/*   53:     */   private final int[] xCropLen;
/*   54:     */   private final int[] yCropLen;
/*   55:     */   private static final int CROP_SEGMENT = 12;
/*   56:     */   
/*   57:     */   public static ComponentUI createUI(JComponent tabPane)
/*   58:     */   {
/*   59: 128 */     return new PlasticTabbedPaneUI();
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void installUI(JComponent c)
/*   63:     */   {
/*   64: 137 */     super.installUI(c);
/*   65: 138 */     this.embeddedTabs = ((Boolean)c.getClientProperty("jgoodies.embeddedTabs"));
/*   66: 139 */     this.noContentBorder = ((Boolean)c.getClientProperty("jgoodies.noContentBorder"));
/*   67: 140 */     this.renderer = createRenderer(this.tabPane);
/*   68:     */   }
/*   69:     */   
/*   70:     */   public void uninstallUI(JComponent c)
/*   71:     */   {
/*   72: 148 */     this.renderer = null;
/*   73: 149 */     super.uninstallUI(c);
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected void installComponents()
/*   77:     */   {
/*   78: 158 */     if ((scrollableTabLayoutEnabled()) && 
/*   79: 159 */       (this.tabScroller == null))
/*   80:     */     {
/*   81: 160 */       this.tabScroller = new ScrollableTabSupport(this.tabPane.getTabPlacement());
/*   82: 161 */       this.tabPane.add(this.tabScroller.viewport);
/*   83:     */     }
/*   84:     */   }
/*   85:     */   
/*   86:     */   protected void uninstallComponents()
/*   87:     */   {
/*   88: 172 */     if (scrollableTabLayoutEnabled())
/*   89:     */     {
/*   90: 173 */       this.tabPane.remove(this.tabScroller.viewport);
/*   91: 174 */       this.tabPane.remove(this.tabScroller.scrollForwardButton);
/*   92: 175 */       this.tabPane.remove(this.tabScroller.scrollBackwardButton);
/*   93: 176 */       this.tabScroller = null;
/*   94:     */     }
/*   95:     */   }
/*   96:     */   
/*   97:     */   protected void installListeners()
/*   98:     */   {
/*   99: 181 */     super.installListeners();
/*  100: 188 */     if ((this.mouseListener != null) && (LookUtils.IS_JAVA_1_4) && 
/*  101: 189 */       (scrollableTabLayoutEnabled()))
/*  102:     */     {
/*  103: 190 */       this.tabPane.removeMouseListener(this.mouseListener);
/*  104: 191 */       this.tabScroller.tabPanel.addMouseListener(this.mouseListener);
/*  105:     */     }
/*  106:     */   }
/*  107:     */   
/*  108:     */   protected void uninstallListeners()
/*  109:     */   {
/*  110: 197 */     if ((this.mouseListener != null) && (LookUtils.IS_JAVA_1_4))
/*  111:     */     {
/*  112: 198 */       if (scrollableTabLayoutEnabled()) {
/*  113: 199 */         this.tabScroller.tabPanel.removeMouseListener(this.mouseListener);
/*  114:     */       } else {
/*  115: 201 */         this.tabPane.removeMouseListener(this.mouseListener);
/*  116:     */       }
/*  117: 203 */       this.mouseListener = null;
/*  118:     */     }
/*  119: 205 */     super.uninstallListeners();
/*  120:     */   }
/*  121:     */   
/*  122:     */   protected void installKeyboardActions()
/*  123:     */   {
/*  124: 209 */     super.installKeyboardActions();
/*  125: 213 */     if (scrollableTabLayoutEnabled())
/*  126:     */     {
/*  127: 214 */       Action forwardAction = new ScrollTabsForwardAction(null);
/*  128: 215 */       Action backwardAction = new ScrollTabsBackwardAction(null);
/*  129: 216 */       ActionMap am = SwingUtilities.getUIActionMap(this.tabPane);
/*  130: 217 */       am.put("scrollTabsForwardAction", forwardAction);
/*  131: 218 */       am.put("scrollTabsBackwardAction", backwardAction);
/*  132: 219 */       this.tabScroller.scrollForwardButton.setAction(forwardAction);
/*  133: 220 */       this.tabScroller.scrollBackwardButton.setAction(backwardAction);
/*  134:     */     }
/*  135:     */   }
/*  136:     */   
/*  137:     */   private boolean hasNoContentBorder()
/*  138:     */   {
/*  139: 230 */     return Boolean.TRUE.equals(this.noContentBorder);
/*  140:     */   }
/*  141:     */   
/*  142:     */   private boolean hasEmbeddedTabs()
/*  143:     */   {
/*  144: 237 */     return Boolean.TRUE.equals(this.embeddedTabs);
/*  145:     */   }
/*  146:     */   
/*  147:     */   private AbstractRenderer createRenderer(JTabbedPane tabbedPane)
/*  148:     */   {
/*  149: 246 */     return hasEmbeddedTabs() ? AbstractRenderer.createEmbeddedRenderer(tabbedPane) : AbstractRenderer.createRenderer(this.tabPane);
/*  150:     */   }
/*  151:     */   
/*  152:     */   protected PropertyChangeListener createPropertyChangeListener()
/*  153:     */   {
/*  154: 257 */     return new MyPropertyChangeHandler(null);
/*  155:     */   }
/*  156:     */   
/*  157:     */   protected ChangeListener createChangeListener()
/*  158:     */   {
/*  159: 261 */     return new TabSelectionHandler(null);
/*  160:     */   }
/*  161:     */   
/*  162:     */   private void doLayout()
/*  163:     */   {
/*  164: 268 */     this.tabPane.revalidate();
/*  165: 269 */     this.tabPane.repaint();
/*  166:     */   }
/*  167:     */   
/*  168:     */   private void tabPlacementChanged()
/*  169:     */   {
/*  170: 277 */     this.renderer = createRenderer(this.tabPane);
/*  171: 278 */     if (scrollableTabLayoutEnabled()) {
/*  172: 279 */       this.tabScroller.createButtons();
/*  173:     */     }
/*  174: 281 */     doLayout();
/*  175:     */   }
/*  176:     */   
/*  177:     */   private void embeddedTabsPropertyChanged(Boolean newValue)
/*  178:     */   {
/*  179: 289 */     this.embeddedTabs = newValue;
/*  180: 290 */     this.renderer = createRenderer(this.tabPane);
/*  181: 291 */     doLayout();
/*  182:     */   }
/*  183:     */   
/*  184:     */   private void noContentBorderPropertyChanged(Boolean newValue)
/*  185:     */   {
/*  186: 300 */     this.noContentBorder = newValue;
/*  187: 301 */     this.tabPane.repaint();
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void paint(Graphics g, JComponent c)
/*  191:     */   {
/*  192: 305 */     int selectedIndex = this.tabPane.getSelectedIndex();
/*  193: 306 */     int tabPlacement = this.tabPane.getTabPlacement();
/*  194:     */     
/*  195: 308 */     ensureCurrentLayout();
/*  196: 314 */     if (!scrollableTabLayoutEnabled()) {
/*  197: 315 */       paintTabArea(g, tabPlacement, selectedIndex);
/*  198:     */     }
/*  199: 319 */     paintContentBorder(g, tabPlacement, selectedIndex);
/*  200:     */   }
/*  201:     */   
/*  202:     */   protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect)
/*  203:     */   {
/*  204: 324 */     Rectangle tabRect = rects[tabIndex];
/*  205: 325 */     int selectedIndex = this.tabPane.getSelectedIndex();
/*  206: 326 */     boolean isSelected = selectedIndex == tabIndex;
/*  207: 327 */     Graphics2D g2 = null;
/*  208: 328 */     Polygon cropShape = null;
/*  209: 329 */     Shape save = null;
/*  210: 330 */     int cropx = 0;
/*  211: 331 */     int cropy = 0;
/*  212: 333 */     if ((scrollableTabLayoutEnabled()) && 
/*  213: 334 */       ((g instanceof Graphics2D)))
/*  214:     */     {
/*  215: 335 */       g2 = (Graphics2D)g;
/*  216:     */       
/*  217:     */ 
/*  218: 338 */       Rectangle viewRect = this.tabScroller.viewport.getViewRect();
/*  219:     */       int cropline;
/*  220: 340 */       switch (tabPlacement)
/*  221:     */       {
/*  222:     */       case 2: 
/*  223:     */       case 4: 
/*  224: 343 */         cropline = viewRect.y + viewRect.height;
/*  225: 344 */         if ((tabRect.y < cropline) && (tabRect.y + tabRect.height > cropline))
/*  226:     */         {
/*  227: 346 */           cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
/*  228:     */           
/*  229: 348 */           cropx = tabRect.x;
/*  230: 349 */           cropy = cropline - 1;
/*  231:     */         }
/*  232:     */         break;
/*  233:     */       case 1: 
/*  234:     */       case 3: 
/*  235:     */       default: 
/*  236: 355 */         cropline = viewRect.x + viewRect.width;
/*  237: 356 */         if ((tabRect.x < cropline) && (tabRect.x + tabRect.width > cropline))
/*  238:     */         {
/*  239: 358 */           cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
/*  240:     */           
/*  241: 360 */           cropx = cropline - 1;
/*  242: 361 */           cropy = tabRect.y;
/*  243:     */         }
/*  244:     */         break;
/*  245:     */       }
/*  246: 364 */       if (cropShape != null)
/*  247:     */       {
/*  248: 365 */         save = g.getClip();
/*  249: 366 */         g2.clip(cropShape);
/*  250:     */       }
/*  251:     */     }
/*  252: 371 */     paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*  253:     */     
/*  254:     */ 
/*  255: 374 */     paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*  256:     */     
/*  257:     */ 
/*  258: 377 */     String title = this.tabPane.getTitleAt(tabIndex);
/*  259: 378 */     Font font = this.tabPane.getFont();
/*  260: 379 */     FontMetrics metrics = g.getFontMetrics(font);
/*  261: 380 */     Icon icon = getIconForTab(tabIndex);
/*  262:     */     
/*  263: 382 */     layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
/*  264:     */     
/*  265:     */ 
/*  266: 385 */     paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
/*  267:     */     
/*  268:     */ 
/*  269: 388 */     paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
/*  270:     */     
/*  271: 390 */     paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
/*  272: 393 */     if (cropShape != null)
/*  273:     */     {
/*  274: 394 */       paintCroppedTabEdge(g, tabPlacement, tabIndex, isSelected, cropx, cropy);
/*  275:     */       
/*  276: 396 */       g.setClip(save);
/*  277:     */     }
/*  278:     */   }
/*  279:     */   
/*  280:     */   public PlasticTabbedPaneUI()
/*  281:     */   {
/*  282: 422 */     this.xCropLen = new int[] { 1, 1, 0, 0, 1, 1, 2, 2 };
/*  283:     */     
/*  284: 424 */     this.yCropLen = new int[] { 0, 3, 3, 6, 6, 9, 9, 12 };
/*  285:     */   }
/*  286:     */   
/*  287:     */   private Polygon createCroppedTabClip(int tabPlacement, Rectangle tabRect, int cropline)
/*  288:     */   {
/*  289: 430 */     int rlen = 0;
/*  290: 431 */     int start = 0;
/*  291: 432 */     int end = 0;
/*  292: 433 */     int ostart = 0;
/*  293: 435 */     switch (tabPlacement)
/*  294:     */     {
/*  295:     */     case 2: 
/*  296:     */     case 4: 
/*  297: 438 */       rlen = tabRect.width;
/*  298: 439 */       start = tabRect.x;
/*  299: 440 */       end = tabRect.x + tabRect.width;
/*  300: 441 */       ostart = tabRect.y;
/*  301: 442 */       break;
/*  302:     */     case 1: 
/*  303:     */     case 3: 
/*  304:     */     default: 
/*  305: 446 */       rlen = tabRect.height;
/*  306: 447 */       start = tabRect.y;
/*  307: 448 */       end = tabRect.y + tabRect.height;
/*  308: 449 */       ostart = tabRect.x;
/*  309:     */     }
/*  310: 451 */     int rcnt = rlen / 12;
/*  311: 452 */     if (rlen % 12 > 0) {
/*  312: 453 */       rcnt++;
/*  313:     */     }
/*  314: 455 */     int npts = 2 + rcnt * 8;
/*  315: 456 */     int[] xp = new int[npts];
/*  316: 457 */     int[] yp = new int[npts];
/*  317: 458 */     int pcnt = 0;
/*  318:     */     
/*  319: 460 */     xp[pcnt] = ostart;
/*  320: 461 */     yp[(pcnt++)] = end;
/*  321: 462 */     xp[pcnt] = ostart;
/*  322: 463 */     yp[(pcnt++)] = start;
/*  323: 464 */     for (int i = 0; i < rcnt; i++) {
/*  324: 465 */       for (int j = 0; j < this.xCropLen.length; j++)
/*  325:     */       {
/*  326: 466 */         xp[pcnt] = (cropline - this.xCropLen[j]);
/*  327: 467 */         yp[pcnt] = (start + i * 12 + this.yCropLen[j]);
/*  328: 468 */         if (yp[pcnt] >= end)
/*  329:     */         {
/*  330: 469 */           yp[pcnt] = end;
/*  331: 470 */           pcnt++;
/*  332: 471 */           break;
/*  333:     */         }
/*  334: 473 */         pcnt++;
/*  335:     */       }
/*  336:     */     }
/*  337: 476 */     if ((tabPlacement == 1) || (tabPlacement == 3)) {
/*  338: 478 */       return new Polygon(xp, yp, pcnt);
/*  339:     */     }
/*  340: 482 */     return new Polygon(yp, xp, pcnt);
/*  341:     */   }
/*  342:     */   
/*  343:     */   private void paintCroppedTabEdge(Graphics g, int tabPlacement, int tabIndex, boolean isSelected, int x, int y)
/*  344:     */   {
/*  345:     */     int xx;
/*  346: 490 */     switch (tabPlacement)
/*  347:     */     {
/*  348:     */     case 2: 
/*  349:     */     case 4: 
/*  350: 493 */       xx = x;
/*  351: 494 */       g.setColor(this.shadow);
/*  352:     */     }
/*  353: 495 */     while (xx <= x + this.rects[tabIndex].width)
/*  354:     */     {
/*  355: 496 */       for (int i = 0; i < this.xCropLen.length; i += 2) {
/*  356: 497 */         g.drawLine(xx + this.yCropLen[i], y - this.xCropLen[i], xx + this.yCropLen[(i + 1)] - 1, y - this.xCropLen[(i + 1)]);
/*  357:     */       }
/*  358: 500 */       xx += 12; continue;
/*  359:     */       
/*  360:     */ 
/*  361:     */ 
/*  362:     */ 
/*  363:     */ 
/*  364: 506 */       int yy = y;
/*  365: 507 */       g.setColor(this.shadow);
/*  366: 508 */       while (yy <= y + this.rects[tabIndex].height)
/*  367:     */       {
/*  368: 509 */         for (int i = 0; i < this.xCropLen.length; i += 2) {
/*  369: 510 */           g.drawLine(x - this.xCropLen[i], yy + this.yCropLen[i], x - this.xCropLen[(i + 1)], yy + this.yCropLen[(i + 1)] - 1);
/*  370:     */         }
/*  371: 513 */         yy += 12;
/*  372:     */       }
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   private void ensureCurrentLayout()
/*  377:     */   {
/*  378: 519 */     if (!this.tabPane.isValid()) {
/*  379: 520 */       this.tabPane.validate();
/*  380:     */     }
/*  381: 526 */     if (!this.tabPane.isValid())
/*  382:     */     {
/*  383: 527 */       TabbedPaneLayout layout = (TabbedPaneLayout)this.tabPane.getLayout();
/*  384: 528 */       layout.calculateLayoutInfo();
/*  385:     */     }
/*  386:     */   }
/*  387:     */   
/*  388:     */   public int tabForCoordinate(JTabbedPane pane, int x, int y)
/*  389:     */   {
/*  390: 537 */     ensureCurrentLayout();
/*  391: 538 */     Point p = new Point(x, y);
/*  392: 540 */     if (scrollableTabLayoutEnabled())
/*  393:     */     {
/*  394: 541 */       translatePointToTabPanel(x, y, p);
/*  395: 542 */       Rectangle viewRect = this.tabScroller.viewport.getViewRect();
/*  396: 543 */       if (!viewRect.contains(p)) {
/*  397: 544 */         return -1;
/*  398:     */       }
/*  399:     */     }
/*  400: 547 */     int tabCount = this.tabPane.getTabCount();
/*  401: 548 */     for (int i = 0; i < tabCount; i++) {
/*  402: 549 */       if (this.rects[i].contains(p.x, p.y)) {
/*  403: 550 */         return i;
/*  404:     */       }
/*  405:     */     }
/*  406: 553 */     return -1;
/*  407:     */   }
/*  408:     */   
/*  409:     */   protected Rectangle getTabBounds(int tabIndex, Rectangle dest)
/*  410:     */   {
/*  411: 557 */     dest.width = this.rects[tabIndex].width;
/*  412: 558 */     dest.height = this.rects[tabIndex].height;
/*  413: 559 */     if (scrollableTabLayoutEnabled())
/*  414:     */     {
/*  415: 562 */       Point vpp = this.tabScroller.viewport.getLocation();
/*  416: 563 */       Point viewp = this.tabScroller.viewport.getViewPosition();
/*  417: 564 */       dest.x = (this.rects[tabIndex].x + vpp.x - viewp.x);
/*  418: 565 */       dest.y = (this.rects[tabIndex].y + vpp.y - viewp.y);
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422: 567 */       dest.x = this.rects[tabIndex].x;
/*  423: 568 */       dest.y = this.rects[tabIndex].y;
/*  424:     */     }
/*  425: 570 */     return dest;
/*  426:     */   }
/*  427:     */   
/*  428:     */   private int getClosestTab(int x, int y)
/*  429:     */   {
/*  430: 578 */     int min = 0;
/*  431: 579 */     int tabCount = Math.min(this.rects.length, this.tabPane.getTabCount());
/*  432: 580 */     int max = tabCount;
/*  433: 581 */     int tabPlacement = this.tabPane.getTabPlacement();
/*  434: 582 */     boolean useX = (tabPlacement == 1) || (tabPlacement == 3);
/*  435: 583 */     int want = useX ? x : y;
/*  436: 585 */     while (min != max)
/*  437:     */     {
/*  438: 586 */       int current = (max + min) / 2;
/*  439:     */       int maxLoc;
/*  440:     */       int minLoc;
/*  441:     */       int maxLoc;
/*  442: 590 */       if (useX)
/*  443:     */       {
/*  444: 591 */         int minLoc = this.rects[current].x;
/*  445: 592 */         maxLoc = minLoc + this.rects[current].width;
/*  446:     */       }
/*  447:     */       else
/*  448:     */       {
/*  449: 594 */         minLoc = this.rects[current].y;
/*  450: 595 */         maxLoc = minLoc + this.rects[current].height;
/*  451:     */       }
/*  452: 597 */       if (want < minLoc)
/*  453:     */       {
/*  454: 598 */         max = current;
/*  455: 599 */         if (min == max) {
/*  456: 600 */           return Math.max(0, current - 1);
/*  457:     */         }
/*  458:     */       }
/*  459: 602 */       else if (want >= maxLoc)
/*  460:     */       {
/*  461: 603 */         min = current;
/*  462: 604 */         if (max - min <= 1) {
/*  463: 605 */           return Math.max(current + 1, tabCount - 1);
/*  464:     */         }
/*  465:     */       }
/*  466:     */       else
/*  467:     */       {
/*  468: 608 */         return current;
/*  469:     */       }
/*  470:     */     }
/*  471: 611 */     return min;
/*  472:     */   }
/*  473:     */   
/*  474:     */   private Point translatePointToTabPanel(int srcx, int srcy, Point dest)
/*  475:     */   {
/*  476: 620 */     Point vpp = this.tabScroller.viewport.getLocation();
/*  477: 621 */     Point viewp = this.tabScroller.viewport.getViewPosition();
/*  478: 622 */     dest.x = (srcx - vpp.x + viewp.x);
/*  479: 623 */     dest.y = (srcy - vpp.y + viewp.y);
/*  480: 624 */     return dest;
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
/*  484:     */   {
/*  485: 628 */     int tabCount = this.tabPane.getTabCount();
/*  486:     */     
/*  487: 630 */     Rectangle iconRect = new Rectangle();
/*  488: 631 */     Rectangle textRect = new Rectangle();
/*  489: 632 */     Rectangle clipRect = g.getClipBounds();
/*  490: 635 */     for (int i = this.runCount - 1; i >= 0; i--)
/*  491:     */     {
/*  492: 636 */       int start = this.tabRuns[i];
/*  493: 637 */       int next = this.tabRuns[(i + 1)];
/*  494: 638 */       int end = next != 0 ? next - 1 : tabCount - 1;
/*  495: 639 */       for (int j = end; j >= start; j--) {
/*  496: 640 */         if ((j != selectedIndex) && (this.rects[j].intersects(clipRect))) {
/*  497: 641 */           paintTab(g, tabPlacement, this.rects, j, iconRect, textRect);
/*  498:     */         }
/*  499:     */       }
/*  500:     */     }
/*  501: 648 */     if ((selectedIndex >= 0) && (this.rects[selectedIndex].intersects(clipRect))) {
/*  502: 649 */       paintTab(g, tabPlacement, this.rects, selectedIndex, iconRect, textRect);
/*  503:     */     }
/*  504:     */   }
/*  505:     */   
/*  506:     */   protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*  507:     */   {
/*  508: 667 */     textRect.x = (textRect.y = iconRect.x = iconRect.y = 0);
/*  509:     */     
/*  510: 669 */     View v = getTextViewForTab(tabIndex);
/*  511: 670 */     if (v != null) {
/*  512: 671 */       this.tabPane.putClientProperty("html", v);
/*  513:     */     }
/*  514: 674 */     Rectangle calcRectangle = new Rectangle(tabRect);
/*  515: 675 */     if (isSelected)
/*  516:     */     {
/*  517: 676 */       Insets calcInsets = getSelectedTabPadInsets(tabPlacement);
/*  518: 677 */       calcRectangle.x += calcInsets.left;
/*  519: 678 */       calcRectangle.y += calcInsets.top;
/*  520: 679 */       calcRectangle.width -= calcInsets.left + calcInsets.right;
/*  521: 680 */       calcRectangle.height -= calcInsets.bottom + calcInsets.top;
/*  522:     */     }
/*  523: 682 */     int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
/*  524: 683 */     int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
/*  525: 684 */     if (((tabPlacement == 4) || (tabPlacement == 2)) && (icon != null) && (title != null) && (!title.equals("")))
/*  526:     */     {
/*  527: 685 */       SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 2, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
/*  528:     */       
/*  529:     */ 
/*  530:     */ 
/*  531:     */ 
/*  532:     */ 
/*  533:     */ 
/*  534:     */ 
/*  535:     */ 
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539:     */ 
/*  540: 698 */       xNudge += 4;
/*  541:     */     }
/*  542:     */     else
/*  543:     */     {
/*  544: 700 */       SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 0, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
/*  545:     */       
/*  546:     */ 
/*  547:     */ 
/*  548:     */ 
/*  549:     */ 
/*  550:     */ 
/*  551:     */ 
/*  552:     */ 
/*  553:     */ 
/*  554:     */ 
/*  555:     */ 
/*  556:     */ 
/*  557: 713 */       iconRect.y += calcRectangle.height % 2;
/*  558:     */     }
/*  559: 717 */     this.tabPane.putClientProperty("html", null);
/*  560:     */     
/*  561: 719 */     iconRect.x += xNudge;
/*  562: 720 */     iconRect.y += yNudge;
/*  563: 721 */     textRect.x += xNudge;
/*  564: 722 */     textRect.y += yNudge;
/*  565:     */   }
/*  566:     */   
/*  567:     */   protected Icon getIconForTab(int tabIndex)
/*  568:     */   {
/*  569: 731 */     String title = this.tabPane.getTitleAt(tabIndex);
/*  570: 732 */     boolean hasTitle = (title != null) && (title.length() > 0);
/*  571: 733 */     return (!isTabIconsEnabled) && (hasTitle) ? null : super.getIconForTab(tabIndex);
/*  572:     */   }
/*  573:     */   
/*  574:     */   protected LayoutManager createLayoutManager()
/*  575:     */   {
/*  576: 742 */     if (this.tabPane.getTabLayoutPolicy() == 1) {
/*  577: 743 */       return new TabbedPaneScrollLayout(null);
/*  578:     */     }
/*  579: 746 */     return new TabbedPaneLayout(null);
/*  580:     */   }
/*  581:     */   
/*  582:     */   private boolean scrollableTabLayoutEnabled()
/*  583:     */   {
/*  584: 755 */     return this.tabPane.getLayout() instanceof TabbedPaneScrollLayout;
/*  585:     */   }
/*  586:     */   
/*  587:     */   protected boolean isTabInFirstRun(int tabIndex)
/*  588:     */   {
/*  589: 759 */     return getRunForTab(this.tabPane.getTabCount(), tabIndex) == 0;
/*  590:     */   }
/*  591:     */   
/*  592:     */   protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
/*  593:     */   {
/*  594: 763 */     int width = this.tabPane.getWidth();
/*  595: 764 */     int height = this.tabPane.getHeight();
/*  596: 765 */     Insets insets = this.tabPane.getInsets();
/*  597:     */     
/*  598: 767 */     int x = insets.left;
/*  599: 768 */     int y = insets.top;
/*  600: 769 */     int w = width - insets.right - insets.left;
/*  601: 770 */     int h = height - insets.top - insets.bottom;
/*  602: 772 */     switch (tabPlacement)
/*  603:     */     {
/*  604:     */     case 2: 
/*  605: 774 */       x += calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
/*  606: 775 */       w -= x - insets.left;
/*  607: 776 */       break;
/*  608:     */     case 4: 
/*  609: 778 */       w -= calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
/*  610: 779 */       break;
/*  611:     */     case 3: 
/*  612: 781 */       h -= calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
/*  613: 782 */       break;
/*  614:     */     case 1: 
/*  615:     */     default: 
/*  616: 785 */       y += calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
/*  617: 786 */       h -= y - insets.top;
/*  618:     */     }
/*  619: 789 */     g.setColor(this.selectColor == null ? this.tabPane.getBackground() : this.selectColor);
/*  620:     */     
/*  621:     */ 
/*  622: 792 */     g.fillRect(x, y, w, h);
/*  623:     */     
/*  624:     */ 
/*  625: 795 */     Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, this.calcRect);
/*  626: 796 */     boolean drawBroken = (selectedIndex >= 0) && (isTabInFirstRun(selectedIndex));
/*  627: 797 */     boolean isContentBorderPainted = !hasNoContentBorder();
/*  628:     */     
/*  629:     */ 
/*  630:     */ 
/*  631:     */ 
/*  632: 802 */     this.renderer.paintContentBorderTopEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  633: 803 */     this.renderer.paintContentBorderLeftEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  634: 804 */     this.renderer.paintContentBorderBottomEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  635: 805 */     this.renderer.paintContentBorderRightEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  636:     */   }
/*  637:     */   
/*  638:     */   protected Insets getContentBorderInsets(int tabPlacement)
/*  639:     */   {
/*  640: 816 */     return this.renderer.getContentBorderInsets(super.getContentBorderInsets(tabPlacement));
/*  641:     */   }
/*  642:     */   
/*  643:     */   protected Insets getTabAreaInsets(int tabPlacement)
/*  644:     */   {
/*  645: 823 */     return this.renderer.getTabAreaInsets(super.getTabAreaInsets(tabPlacement));
/*  646:     */   }
/*  647:     */   
/*  648:     */   protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected)
/*  649:     */   {
/*  650: 830 */     return this.renderer.getTabLabelShiftX(tabIndex, isSelected);
/*  651:     */   }
/*  652:     */   
/*  653:     */   protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected)
/*  654:     */   {
/*  655: 837 */     return this.renderer.getTabLabelShiftY(tabIndex, isSelected);
/*  656:     */   }
/*  657:     */   
/*  658:     */   protected int getTabRunOverlay(int tabPlacement)
/*  659:     */   {
/*  660: 844 */     return this.renderer.getTabRunOverlay(this.tabRunOverlay);
/*  661:     */   }
/*  662:     */   
/*  663:     */   protected boolean shouldPadTabRun(int tabPlacement, int run)
/*  664:     */   {
/*  665: 852 */     return this.renderer.shouldPadTabRun(run, super.shouldPadTabRun(tabPlacement, run));
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected int getTabRunIndent(int tabPlacement, int run)
/*  669:     */   {
/*  670: 861 */     return this.renderer.getTabRunIndent(run);
/*  671:     */   }
/*  672:     */   
/*  673:     */   protected Insets getTabInsets(int tabPlacement, int tabIndex)
/*  674:     */   {
/*  675: 868 */     return this.renderer.getTabInsets(tabIndex, this.tabInsets);
/*  676:     */   }
/*  677:     */   
/*  678:     */   protected Insets getSelectedTabPadInsets(int tabPlacement)
/*  679:     */   {
/*  680: 875 */     return this.renderer.getSelectedTabPadInsets();
/*  681:     */   }
/*  682:     */   
/*  683:     */   protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rectangles, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*  684:     */   {
/*  685: 889 */     this.renderer.paintFocusIndicator(g, rectangles, tabIndex, iconRect, textRect, isSelected);
/*  686:     */   }
/*  687:     */   
/*  688:     */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*  689:     */   {
/*  690: 898 */     this.renderer.paintTabBackground(g, tabIndex, x, y, w, h, isSelected);
/*  691:     */   }
/*  692:     */   
/*  693:     */   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*  694:     */   {
/*  695: 907 */     this.renderer.paintTabBorder(g, tabIndex, x, y, w, h, isSelected);
/*  696:     */   }
/*  697:     */   
/*  698:     */   protected boolean shouldRotateTabRuns(int tabPlacement)
/*  699:     */   {
/*  700: 916 */     return false;
/*  701:     */   }
/*  702:     */   
/*  703:     */   private final class TabSelectionHandler
/*  704:     */     implements ChangeListener
/*  705:     */   {
/*  706: 921 */     private final Rectangle rect = new Rectangle();
/*  707:     */     
/*  708:     */     private TabSelectionHandler() {}
/*  709:     */     
/*  710:     */     public void stateChanged(ChangeEvent e)
/*  711:     */     {
/*  712: 924 */       JTabbedPane tabPane = (JTabbedPane)e.getSource();
/*  713: 925 */       tabPane.revalidate();
/*  714: 926 */       tabPane.repaint();
/*  715: 928 */       if (tabPane.getTabLayoutPolicy() == 1)
/*  716:     */       {
/*  717: 929 */         int index = tabPane.getSelectedIndex();
/*  718: 930 */         if ((index < PlasticTabbedPaneUI.this.rects.length) && (index != -1))
/*  719:     */         {
/*  720: 931 */           this.rect.setBounds(PlasticTabbedPaneUI.this.rects[index]);
/*  721: 932 */           Point viewPosition = PlasticTabbedPaneUI.this.tabScroller.viewport.getViewPosition();
/*  722: 933 */           if (this.rect.x < viewPosition.x) {
/*  723: 934 */             this.rect.x -= PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*  724:     */           } else {
/*  725: 936 */             this.rect.x += PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*  726:     */           }
/*  727: 938 */           PlasticTabbedPaneUI.this.tabScroller.tabPanel.scrollRectToVisible(this.rect);
/*  728:     */         }
/*  729:     */       }
/*  730:     */     }
/*  731:     */   }
/*  732:     */   
/*  733:     */   private final class MyPropertyChangeHandler
/*  734:     */     extends BasicTabbedPaneUI.PropertyChangeHandler
/*  735:     */   {
/*  736:     */     private MyPropertyChangeHandler()
/*  737:     */     {
/*  738: 949 */       super();
/*  739:     */     }
/*  740:     */     
/*  741:     */     public void propertyChange(PropertyChangeEvent e)
/*  742:     */     {
/*  743: 952 */       String pName = e.getPropertyName();
/*  744: 954 */       if (null == pName) {
/*  745: 955 */         return;
/*  746:     */       }
/*  747: 958 */       super.propertyChange(e);
/*  748: 960 */       if (pName.equals("tabPlacement"))
/*  749:     */       {
/*  750: 961 */         PlasticTabbedPaneUI.this.tabPlacementChanged();
/*  751: 962 */         return;
/*  752:     */       }
/*  753: 964 */       if (pName.equals("jgoodies.embeddedTabs"))
/*  754:     */       {
/*  755: 965 */         PlasticTabbedPaneUI.this.embeddedTabsPropertyChanged((Boolean)e.getNewValue());
/*  756: 966 */         return;
/*  757:     */       }
/*  758: 968 */       if (pName.equals("jgoodies.noContentBorder"))
/*  759:     */       {
/*  760: 969 */         PlasticTabbedPaneUI.this.noContentBorderPropertyChanged((Boolean)e.getNewValue());
/*  761: 970 */         return;
/*  762:     */       }
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   private class TabbedPaneLayout
/*  767:     */     extends BasicTabbedPaneUI.TabbedPaneLayout
/*  768:     */   {
/*  769:     */     private TabbedPaneLayout()
/*  770:     */     {
/*  771: 979 */       super();
/*  772:     */     }
/*  773:     */     
/*  774:     */     protected void calculateTabRects(int tabPlacement, int tabCount)
/*  775:     */     {
/*  776: 982 */       FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
/*  777: 983 */       Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
/*  778: 984 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/*  779: 985 */       Insets theTabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/*  780: 986 */       int fontHeight = metrics.getHeight();
/*  781: 987 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/*  782:     */       
/*  783:     */ 
/*  784:     */ 
/*  785:     */ 
/*  786: 992 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/*  787: 993 */       boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
/*  788:     */       int x;
/*  789:     */       int y;
/*  790:     */       int returnAt;
/*  791: 998 */       switch (tabPlacement)
/*  792:     */       {
/*  793:     */       case 2: 
/*  794:1000 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*  795:1001 */         x = insets.left + theTabAreaInsets.left;
/*  796:1002 */         y = insets.top + theTabAreaInsets.top;
/*  797:1003 */         returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
/*  798:1004 */         break;
/*  799:     */       case 4: 
/*  800:1006 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*  801:1007 */         x = size.width - insets.right - theTabAreaInsets.right - PlasticTabbedPaneUI.this.maxTabWidth;
/*  802:1008 */         y = insets.top + theTabAreaInsets.top;
/*  803:1009 */         returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
/*  804:1010 */         break;
/*  805:     */       case 3: 
/*  806:1012 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*  807:1013 */         x = insets.left + theTabAreaInsets.left;
/*  808:1014 */         y = size.height - insets.bottom - theTabAreaInsets.bottom - PlasticTabbedPaneUI.this.maxTabHeight;
/*  809:1015 */         returnAt = size.width - (insets.right + theTabAreaInsets.right);
/*  810:1016 */         break;
/*  811:     */       case 1: 
/*  812:     */       default: 
/*  813:1019 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*  814:1020 */         x = insets.left + theTabAreaInsets.left;
/*  815:1021 */         y = insets.top + theTabAreaInsets.top;
/*  816:1022 */         returnAt = size.width - (insets.right + theTabAreaInsets.right);
/*  817:     */       }
/*  818:1026 */       int theTabRunOverlay = PlasticTabbedPaneUI.this.getTabRunOverlay(tabPlacement);
/*  819:     */       
/*  820:1028 */       PlasticTabbedPaneUI.this.runCount = 0;
/*  821:1029 */       PlasticTabbedPaneUI.this.selectedRun = -1;
/*  822:     */       
/*  823:     */ 
/*  824:     */ 
/*  825:     */ 
/*  826:1034 */       int tabInRun = -1;
/*  827:     */       
/*  828:     */ 
/*  829:1037 */       int runReturnAt = returnAt;
/*  830:1039 */       if (tabCount == 0) {
/*  831:1040 */         return;
/*  832:     */       }
/*  833:1045 */       for (int i = 0; i < tabCount; i++)
/*  834:     */       {
/*  835:1046 */         Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
/*  836:1047 */         tabInRun++;
/*  837:1049 */         if (!verticalTabRuns)
/*  838:     */         {
/*  839:1051 */           if (i > 0)
/*  840:     */           {
/*  841:1052 */             rect.x = (PlasticTabbedPaneUI.this.rects[(i - 1)].x + PlasticTabbedPaneUI.this.rects[(i - 1)].width);
/*  842:     */           }
/*  843:     */           else
/*  844:     */           {
/*  845:1054 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/*  846:1055 */             PlasticTabbedPaneUI.this.runCount = 1;
/*  847:1056 */             PlasticTabbedPaneUI.this.maxTabWidth = 0;
/*  848:1057 */             rect.x = x;
/*  849:     */           }
/*  850:1060 */           rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/*  851:1061 */           PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
/*  852:1069 */           if ((tabInRun != 0) && (rect.x + rect.width > runReturnAt))
/*  853:     */           {
/*  854:1070 */             if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1) {
/*  855:1071 */               PlasticTabbedPaneUI.this.expandTabRunsArray();
/*  856:     */             }
/*  857:1074 */             tabInRun = 0;
/*  858:1075 */             PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
/*  859:1076 */             PlasticTabbedPaneUI.access$4608(PlasticTabbedPaneUI.this);
/*  860:1077 */             rect.x = x;
/*  861:1078 */             runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
/*  862:     */           }
/*  863:1081 */           rect.y = y;
/*  864:1082 */           rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
/*  865:     */         }
/*  866:     */         else
/*  867:     */         {
/*  868:1086 */           if (i > 0)
/*  869:     */           {
/*  870:1087 */             rect.y = (PlasticTabbedPaneUI.this.rects[(i - 1)].y + PlasticTabbedPaneUI.this.rects[(i - 1)].height);
/*  871:     */           }
/*  872:     */           else
/*  873:     */           {
/*  874:1089 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/*  875:1090 */             PlasticTabbedPaneUI.this.runCount = 1;
/*  876:1091 */             PlasticTabbedPaneUI.this.maxTabHeight = 0;
/*  877:1092 */             rect.y = y;
/*  878:     */           }
/*  879:1095 */           rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
/*  880:1096 */           PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
/*  881:1102 */           if ((tabInRun != 0) && (rect.y + rect.height > runReturnAt))
/*  882:     */           {
/*  883:1103 */             if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1) {
/*  884:1104 */               PlasticTabbedPaneUI.this.expandTabRunsArray();
/*  885:     */             }
/*  886:1106 */             PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
/*  887:1107 */             PlasticTabbedPaneUI.access$6208(PlasticTabbedPaneUI.this);
/*  888:1108 */             rect.y = y;
/*  889:1109 */             tabInRun = 0;
/*  890:1110 */             runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
/*  891:     */           }
/*  892:1113 */           rect.x = x;
/*  893:1114 */           rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
/*  894:     */         }
/*  895:1117 */         if (i == selectedIndex) {
/*  896:1118 */           PlasticTabbedPaneUI.this.selectedRun = (PlasticTabbedPaneUI.this.runCount - 1);
/*  897:     */         }
/*  898:     */       }
/*  899:1122 */       if (PlasticTabbedPaneUI.this.runCount > 1) {
/*  900:1130 */         if (PlasticTabbedPaneUI.this.shouldRotateTabRuns(tabPlacement)) {
/*  901:1131 */           rotateTabRuns(tabPlacement, PlasticTabbedPaneUI.this.selectedRun);
/*  902:     */         }
/*  903:     */       }
/*  904:1137 */       for (i = PlasticTabbedPaneUI.this.runCount - 1; i >= 0; i--)
/*  905:     */       {
/*  906:1138 */         int start = PlasticTabbedPaneUI.this.tabRuns[i];
/*  907:1139 */         int next = PlasticTabbedPaneUI.this.tabRuns[(i + 1)];
/*  908:1140 */         int end = next != 0 ? next - 1 : tabCount - 1;
/*  909:1141 */         int indent = PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, i);
/*  910:1142 */         if (!verticalTabRuns)
/*  911:     */         {
/*  912:1143 */           for (int j = start; j <= end; j++)
/*  913:     */           {
/*  914:1144 */             Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
/*  915:1145 */             rect.y = y;
/*  916:1146 */             rect.x += indent;
/*  917:     */           }
/*  918:1150 */           if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/*  919:1151 */             padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
/*  920:     */           }
/*  921:1153 */           if (tabPlacement == 3) {
/*  922:1154 */             y -= PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
/*  923:     */           } else {
/*  924:1156 */             y += PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
/*  925:     */           }
/*  926:     */         }
/*  927:     */         else
/*  928:     */         {
/*  929:1159 */           for (int j = start; j <= end; j++)
/*  930:     */           {
/*  931:1160 */             Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
/*  932:1161 */             rect.x = x;
/*  933:1162 */             rect.y += indent;
/*  934:     */           }
/*  935:1164 */           if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/*  936:1165 */             padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
/*  937:     */           }
/*  938:1167 */           if (tabPlacement == 4) {
/*  939:1168 */             x -= PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
/*  940:     */           } else {
/*  941:1170 */             x += PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
/*  942:     */           }
/*  943:     */         }
/*  944:     */       }
/*  945:1176 */       padSelectedTab(tabPlacement, selectedIndex);
/*  946:1180 */       if ((!leftToRight) && (!verticalTabRuns))
/*  947:     */       {
/*  948:1181 */         int rightMargin = size.width - (insets.right + theTabAreaInsets.right);
/*  949:1182 */         for (i = 0; i < tabCount; i++) {
/*  950:1183 */           PlasticTabbedPaneUI.this.rects[i].x = (rightMargin - PlasticTabbedPaneUI.this.rects[i].x - PlasticTabbedPaneUI.this.rects[i].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay());
/*  951:     */         }
/*  952:     */       }
/*  953:     */     }
/*  954:     */     
/*  955:     */     protected void padSelectedTab(int tabPlacement, int selectedIndex)
/*  956:     */     {
/*  957:1193 */       if (selectedIndex >= 0)
/*  958:     */       {
/*  959:1194 */         Rectangle selRect = PlasticTabbedPaneUI.this.rects[selectedIndex];
/*  960:1195 */         Insets padInsets = PlasticTabbedPaneUI.this.getSelectedTabPadInsets(tabPlacement);
/*  961:1196 */         selRect.x -= padInsets.left;
/*  962:1197 */         selRect.width += padInsets.left + padInsets.right;
/*  963:1198 */         selRect.y -= padInsets.top;
/*  964:1199 */         selRect.height += padInsets.top + padInsets.bottom;
/*  965:     */       }
/*  966:     */     }
/*  967:     */   }
/*  968:     */   
/*  969:     */   private boolean requestFocusForVisibleComponent()
/*  970:     */   {
/*  971:1207 */     Component visibleComponent = getVisibleComponent();
/*  972:1208 */     if (visibleComponent.isFocusable())
/*  973:     */     {
/*  974:1209 */       visibleComponent.requestFocus();
/*  975:1210 */       return true;
/*  976:     */     }
/*  977:1212 */     if (((visibleComponent instanceof JComponent)) && 
/*  978:1213 */       (((JComponent)visibleComponent).requestDefaultFocus())) {
/*  979:1214 */       return true;
/*  980:     */     }
/*  981:1217 */     return false;
/*  982:     */   }
/*  983:     */   
/*  984:     */   private static class ScrollTabsForwardAction
/*  985:     */     extends AbstractAction
/*  986:     */   {
/*  987:     */     public void actionPerformed(ActionEvent e)
/*  988:     */     {
/*  989:1223 */       JTabbedPane pane = null;
/*  990:1224 */       Object src = e.getSource();
/*  991:1225 */       if ((src instanceof JTabbedPane)) {
/*  992:1226 */         pane = (JTabbedPane)src;
/*  993:1227 */       } else if ((src instanceof PlasticArrowButton)) {
/*  994:1228 */         pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
/*  995:     */       } else {
/*  996:1230 */         return;
/*  997:     */       }
/*  998:1232 */       PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
/*  999:1234 */       if (ui.scrollableTabLayoutEnabled()) {
/* 1000:1235 */         ui.tabScroller.scrollForward(pane.getTabPlacement());
/* 1001:     */       }
/* 1002:     */     }
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   private static class ScrollTabsBackwardAction
/* 1006:     */     extends AbstractAction
/* 1007:     */   {
/* 1008:     */     public void actionPerformed(ActionEvent e)
/* 1009:     */     {
/* 1010:1243 */       JTabbedPane pane = null;
/* 1011:1244 */       Object src = e.getSource();
/* 1012:1245 */       if ((src instanceof JTabbedPane)) {
/* 1013:1246 */         pane = (JTabbedPane)src;
/* 1014:1247 */       } else if ((src instanceof PlasticArrowButton)) {
/* 1015:1248 */         pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
/* 1016:     */       } else {
/* 1017:1250 */         return;
/* 1018:     */       }
/* 1019:1252 */       PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
/* 1020:1254 */       if (ui.scrollableTabLayoutEnabled()) {
/* 1021:1255 */         ui.tabScroller.scrollBackward(pane.getTabPlacement());
/* 1022:     */       }
/* 1023:     */     }
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   private final class TabbedPaneScrollLayout
/* 1027:     */     extends PlasticTabbedPaneUI.TabbedPaneLayout
/* 1028:     */   {
/* 1029:     */     private TabbedPaneScrollLayout()
/* 1030:     */     {
/* 1031:1260 */       super(null);
/* 1032:     */     }
/* 1033:     */     
/* 1034:     */     protected int preferredTabAreaHeight(int tabPlacement, int width)
/* 1035:     */     {
/* 1036:1263 */       return PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 1037:     */     }
/* 1038:     */     
/* 1039:     */     protected int preferredTabAreaWidth(int tabPlacement, int height)
/* 1040:     */     {
/* 1041:1267 */       return PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 1042:     */     }
/* 1043:     */     
/* 1044:     */     public void layoutContainer(Container parent)
/* 1045:     */     {
/* 1046:1271 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1047:1272 */       int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
/* 1048:1273 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/* 1049:1274 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 1050:1275 */       Component visibleComponent = PlasticTabbedPaneUI.this.getVisibleComponent();
/* 1051:     */       
/* 1052:1277 */       calculateLayoutInfo();
/* 1053:1279 */       if (selectedIndex < 0)
/* 1054:     */       {
/* 1055:1280 */         if (visibleComponent != null) {
/* 1056:1282 */           PlasticTabbedPaneUI.this.setVisibleComponent(null);
/* 1057:     */         }
/* 1058:     */       }
/* 1059:     */       else
/* 1060:     */       {
/* 1061:1285 */         Component selectedComponent = PlasticTabbedPaneUI.this.tabPane.getComponentAt(selectedIndex);
/* 1062:1286 */         boolean shouldChangeFocus = false;
/* 1063:1295 */         if (selectedComponent != null)
/* 1064:     */         {
/* 1065:1296 */           if ((selectedComponent != visibleComponent) && (visibleComponent != null)) {
/* 1066:1298 */             if (SwingUtilities.findFocusOwner(visibleComponent) != null) {
/* 1067:1299 */               shouldChangeFocus = true;
/* 1068:     */             }
/* 1069:     */           }
/* 1070:1302 */           PlasticTabbedPaneUI.this.setVisibleComponent(selectedComponent);
/* 1071:     */         }
/* 1072:1306 */         Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 1073:1307 */         Rectangle bounds = PlasticTabbedPaneUI.this.tabPane.getBounds();
/* 1074:1308 */         int numChildren = PlasticTabbedPaneUI.this.tabPane.getComponentCount();
/* 1075:1310 */         if (numChildren > 0)
/* 1076:     */         {
/* 1077:     */           int tw;
/* 1078:     */           int th;
/* 1079:     */           int tx;
/* 1080:     */           int ty;
/* 1081:     */           int cx;
/* 1082:     */           int cy;
/* 1083:     */           int cw;
/* 1084:     */           int ch;
/* 1085:1311 */           switch (tabPlacement)
/* 1086:     */           {
/* 1087:     */           case 2: 
/* 1088:1314 */             tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
/* 1089:     */             
/* 1090:1316 */             th = bounds.height - insets.top - insets.bottom;
/* 1091:1317 */             tx = insets.left;
/* 1092:1318 */             ty = insets.top;
/* 1093:     */             
/* 1094:     */ 
/* 1095:1321 */             cx = tx + tw + contentInsets.left;
/* 1096:1322 */             cy = ty + contentInsets.top;
/* 1097:1323 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/* 1098:     */             
/* 1099:1325 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/* 1100:     */             
/* 1101:1327 */             break;
/* 1102:     */           case 4: 
/* 1103:1330 */             tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
/* 1104:     */             
/* 1105:1332 */             th = bounds.height - insets.top - insets.bottom;
/* 1106:1333 */             tx = bounds.width - insets.right - tw;
/* 1107:1334 */             ty = insets.top;
/* 1108:     */             
/* 1109:     */ 
/* 1110:1337 */             cx = insets.left + contentInsets.left;
/* 1111:1338 */             cy = insets.top + contentInsets.top;
/* 1112:1339 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/* 1113:     */             
/* 1114:1341 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/* 1115:     */             
/* 1116:1343 */             break;
/* 1117:     */           case 3: 
/* 1118:1346 */             tw = bounds.width - insets.left - insets.right;
/* 1119:1347 */             th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
/* 1120:     */             
/* 1121:1349 */             tx = insets.left;
/* 1122:1350 */             ty = bounds.height - insets.bottom - th;
/* 1123:     */             
/* 1124:     */ 
/* 1125:1353 */             cx = insets.left + contentInsets.left;
/* 1126:1354 */             cy = insets.top + contentInsets.top;
/* 1127:1355 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/* 1128:     */             
/* 1129:1357 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/* 1130:     */             
/* 1131:1359 */             break;
/* 1132:     */           case 1: 
/* 1133:     */           default: 
/* 1134:1363 */             tw = bounds.width - insets.left - insets.right;
/* 1135:1364 */             th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
/* 1136:     */             
/* 1137:1366 */             tx = insets.left;
/* 1138:1367 */             ty = insets.top;
/* 1139:     */             
/* 1140:     */ 
/* 1141:1370 */             cx = tx + contentInsets.left;
/* 1142:1371 */             cy = ty + th + contentInsets.top;
/* 1143:1372 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/* 1144:     */             
/* 1145:1374 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/* 1146:     */           }
/* 1147:1378 */           for (int i = 0; i < numChildren; i++)
/* 1148:     */           {
/* 1149:1379 */             Component child = PlasticTabbedPaneUI.this.tabPane.getComponent(i);
/* 1150:1381 */             if ((PlasticTabbedPaneUI.this.tabScroller != null) && (child == PlasticTabbedPaneUI.this.tabScroller.viewport))
/* 1151:     */             {
/* 1152:1382 */               JViewport viewport = (JViewport)child;
/* 1153:1383 */               Rectangle viewRect = viewport.getViewRect();
/* 1154:1384 */               int vw = tw;
/* 1155:1385 */               int vh = th;
/* 1156:1386 */               Dimension butSize = PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton.getPreferredSize();
/* 1157:1387 */               switch (tabPlacement)
/* 1158:     */               {
/* 1159:     */               case 2: 
/* 1160:     */               case 4: 
/* 1161:1390 */                 int totalTabHeight = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].y + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].height;
/* 1162:1392 */                 if (totalTabHeight > th)
/* 1163:     */                 {
/* 1164:1394 */                   vh = th > 2 * butSize.height ? th - 2 * butSize.height : 0;
/* 1165:1396 */                   if (totalTabHeight - viewRect.y <= vh) {
/* 1166:1401 */                     vh = totalTabHeight - viewRect.y;
/* 1167:     */                   }
/* 1168:     */                 }
/* 1169:     */                 break;
/* 1170:     */               case 1: 
/* 1171:     */               case 3: 
/* 1172:     */               default: 
/* 1173:1408 */                 int totalTabWidth = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].x + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/* 1174:1410 */                 if (totalTabWidth > tw)
/* 1175:     */                 {
/* 1176:1412 */                   vw = tw > 2 * butSize.width ? tw - 2 * butSize.width : 0;
/* 1177:1414 */                   if (totalTabWidth - viewRect.x <= vw) {
/* 1178:1419 */                     vw = totalTabWidth - viewRect.x;
/* 1179:     */                   }
/* 1180:     */                 }
/* 1181:     */                 break;
/* 1182:     */               }
/* 1183:1423 */               child.setBounds(tx, ty, vw, vh);
/* 1184:     */             }
/* 1185:1425 */             else if ((PlasticTabbedPaneUI.this.tabScroller != null) && ((child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton) || (child == PlasticTabbedPaneUI.this.tabScroller.scrollBackwardButton)))
/* 1186:     */             {
/* 1187:1428 */               Component scrollbutton = child;
/* 1188:1429 */               Dimension bsize = scrollbutton.getPreferredSize();
/* 1189:1430 */               int bx = 0;
/* 1190:1431 */               int by = 0;
/* 1191:1432 */               int bw = bsize.width;
/* 1192:1433 */               int bh = bsize.height;
/* 1193:1434 */               boolean visible = false;
/* 1194:1436 */               switch (tabPlacement)
/* 1195:     */               {
/* 1196:     */               case 2: 
/* 1197:     */               case 4: 
/* 1198:1439 */                 int totalTabHeight = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].y + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].height + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/* 1199:1442 */                 if (totalTabHeight > th)
/* 1200:     */                 {
/* 1201:1443 */                   visible = true;
/* 1202:1444 */                   bx = tabPlacement == 2 ? tx + tw - bsize.width : tx;
/* 1203:     */                   
/* 1204:1446 */                   by = child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton ? bounds.height - insets.bottom - bsize.height : bounds.height - insets.bottom - 2 * bsize.height;
/* 1205:     */                 }
/* 1206:     */                 break;
/* 1207:     */               case 1: 
/* 1208:     */               case 3: 
/* 1209:     */               default: 
/* 1210:1456 */                 int totalTabWidth = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].x + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/* 1211:1460 */                 if (totalTabWidth > tw)
/* 1212:     */                 {
/* 1213:1461 */                   visible = true;
/* 1214:1462 */                   bx = child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton ? bounds.width - insets.left - bsize.width : bounds.width - insets.left - 2 * bsize.width;
/* 1215:     */                   
/* 1216:     */ 
/* 1217:     */ 
/* 1218:1466 */                   by = tabPlacement == 1 ? ty + th - bsize.height : ty;
/* 1219:     */                 }
/* 1220:     */                 break;
/* 1221:     */               }
/* 1222:1470 */               child.setVisible(visible);
/* 1223:1471 */               if (visible) {
/* 1224:1472 */                 child.setBounds(bx, by, bw, bh);
/* 1225:     */               }
/* 1226:     */             }
/* 1227:     */             else
/* 1228:     */             {
/* 1229:1477 */               child.setBounds(cx, cy, cw, ch);
/* 1230:     */             }
/* 1231:     */           }
/* 1232:1480 */           if ((shouldChangeFocus) && 
/* 1233:1481 */             (!PlasticTabbedPaneUI.this.requestFocusForVisibleComponent())) {
/* 1234:1482 */             PlasticTabbedPaneUI.this.tabPane.requestFocus();
/* 1235:     */           }
/* 1236:     */         }
/* 1237:     */       }
/* 1238:     */     }
/* 1239:     */     
/* 1240:     */     protected void calculateTabRects(int tabPlacement, int tabCount)
/* 1241:     */     {
/* 1242:1490 */       FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
/* 1243:1491 */       Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
/* 1244:1492 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/* 1245:1493 */       Insets tabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/* 1246:1494 */       int fontHeight = metrics.getHeight();
/* 1247:1495 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 1248:1496 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/* 1249:1497 */       boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
/* 1250:1498 */       int x = tabAreaInsets.left;
/* 1251:1499 */       int y = tabAreaInsets.top;
/* 1252:1500 */       int totalWidth = 0;
/* 1253:1501 */       int totalHeight = 0;
/* 1254:1506 */       switch (tabPlacement)
/* 1255:     */       {
/* 1256:     */       case 2: 
/* 1257:     */       case 4: 
/* 1258:1509 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 1259:1510 */         break;
/* 1260:     */       case 1: 
/* 1261:     */       case 3: 
/* 1262:     */       default: 
/* 1263:1514 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 1264:     */       }
/* 1265:1517 */       PlasticTabbedPaneUI.this.runCount = 0;
/* 1266:1518 */       PlasticTabbedPaneUI.this.selectedRun = -1;
/* 1267:1520 */       if (tabCount == 0) {
/* 1268:1521 */         return;
/* 1269:     */       }
/* 1270:1524 */       PlasticTabbedPaneUI.this.selectedRun = 0;
/* 1271:1525 */       PlasticTabbedPaneUI.this.runCount = 1;
/* 1272:1529 */       for (int i = 0; i < tabCount; i++)
/* 1273:     */       {
/* 1274:1530 */         Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
/* 1275:1532 */         if (!verticalTabRuns)
/* 1276:     */         {
/* 1277:1534 */           if (i > 0)
/* 1278:     */           {
/* 1279:1535 */             rect.x = (PlasticTabbedPaneUI.this.rects[(i - 1)].x + PlasticTabbedPaneUI.this.rects[(i - 1)].width);
/* 1280:     */           }
/* 1281:     */           else
/* 1282:     */           {
/* 1283:1537 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1284:1538 */             PlasticTabbedPaneUI.this.maxTabWidth = 0;
/* 1285:1539 */             totalHeight += PlasticTabbedPaneUI.this.maxTabHeight;
/* 1286:1540 */             rect.x = x;
/* 1287:     */           }
/* 1288:1542 */           rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/* 1289:1543 */           totalWidth = rect.x + rect.width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/* 1290:1544 */           PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
/* 1291:     */           
/* 1292:1546 */           rect.y = y;
/* 1293:1547 */           rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
/* 1294:     */         }
/* 1295:     */         else
/* 1296:     */         {
/* 1297:1551 */           if (i > 0)
/* 1298:     */           {
/* 1299:1552 */             rect.y = (PlasticTabbedPaneUI.this.rects[(i - 1)].y + PlasticTabbedPaneUI.this.rects[(i - 1)].height);
/* 1300:     */           }
/* 1301:     */           else
/* 1302:     */           {
/* 1303:1554 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1304:1555 */             PlasticTabbedPaneUI.this.maxTabHeight = 0;
/* 1305:1556 */             totalWidth = PlasticTabbedPaneUI.this.maxTabWidth;
/* 1306:1557 */             rect.y = y;
/* 1307:     */           }
/* 1308:1559 */           rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
/* 1309:1560 */           totalHeight = rect.y + rect.height;
/* 1310:1561 */           PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
/* 1311:     */           
/* 1312:1563 */           rect.x = x;
/* 1313:1564 */           rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
/* 1314:     */         }
/* 1315:     */       }
/* 1316:1570 */       padSelectedTab(tabPlacement, selectedIndex);
/* 1317:1574 */       if ((!leftToRight) && (!verticalTabRuns))
/* 1318:     */       {
/* 1319:1575 */         int rightMargin = size.width - (insets.right + tabAreaInsets.right);
/* 1320:1577 */         for (int i = 0; i < tabCount; i++) {
/* 1321:1578 */           PlasticTabbedPaneUI.this.rects[i].x = (rightMargin - PlasticTabbedPaneUI.this.rects[i].x - PlasticTabbedPaneUI.this.rects[i].width);
/* 1322:     */         }
/* 1323:     */       }
/* 1324:1581 */       PlasticTabbedPaneUI.this.tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
/* 1325:     */     }
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   private final class ScrollableTabSupport
/* 1329:     */     implements ActionListener, ChangeListener
/* 1330:     */   {
/* 1331:     */     public PlasticTabbedPaneUI.ScrollableTabViewport viewport;
/* 1332:     */     public PlasticTabbedPaneUI.ScrollableTabPanel tabPanel;
/* 1333:     */     public JButton scrollForwardButton;
/* 1334:     */     public JButton scrollBackwardButton;
/* 1335:     */     public int leadingTabIndex;
/* 1336:1593 */     private final Point tabViewPosition = new Point(0, 0);
/* 1337:     */     
/* 1338:     */     ScrollableTabSupport(int tabPlacement)
/* 1339:     */     {
/* 1340:1596 */       this.viewport = new PlasticTabbedPaneUI.ScrollableTabViewport(PlasticTabbedPaneUI.this);
/* 1341:1597 */       this.tabPanel = new PlasticTabbedPaneUI.ScrollableTabPanel(PlasticTabbedPaneUI.this);
/* 1342:1598 */       this.viewport.setView(this.tabPanel);
/* 1343:1599 */       this.viewport.addChangeListener(this);
/* 1344:1600 */       createButtons();
/* 1345:     */     }
/* 1346:     */     
/* 1347:     */     void createButtons()
/* 1348:     */     {
/* 1349:1607 */       if (this.scrollForwardButton != null)
/* 1350:     */       {
/* 1351:1608 */         PlasticTabbedPaneUI.this.tabPane.remove(this.scrollForwardButton);
/* 1352:1609 */         this.scrollForwardButton.removeActionListener(this);
/* 1353:1610 */         PlasticTabbedPaneUI.this.tabPane.remove(this.scrollBackwardButton);
/* 1354:1611 */         this.scrollBackwardButton.removeActionListener(this);
/* 1355:     */       }
/* 1356:1613 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1357:1614 */       int width = UIManager.getInt("ScrollBar.width");
/* 1358:1615 */       if ((tabPlacement == 1) || (tabPlacement == 3))
/* 1359:     */       {
/* 1360:1616 */         this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(3, width);
/* 1361:1617 */         this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(7, width);
/* 1362:     */       }
/* 1363:     */       else
/* 1364:     */       {
/* 1365:1619 */         this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(5, width);
/* 1366:1620 */         this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(1, width);
/* 1367:     */       }
/* 1368:1622 */       this.scrollForwardButton.addActionListener(this);
/* 1369:1623 */       this.scrollBackwardButton.addActionListener(this);
/* 1370:1624 */       PlasticTabbedPaneUI.this.tabPane.add(this.scrollForwardButton);
/* 1371:1625 */       PlasticTabbedPaneUI.this.tabPane.add(this.scrollBackwardButton);
/* 1372:     */     }
/* 1373:     */     
/* 1374:     */     public void scrollForward(int tabPlacement)
/* 1375:     */     {
/* 1376:1629 */       Dimension viewSize = this.viewport.getViewSize();
/* 1377:1630 */       Rectangle viewRect = this.viewport.getViewRect();
/* 1378:1632 */       if ((tabPlacement == 1) || (tabPlacement == 3))
/* 1379:     */       {
/* 1380:1633 */         if (viewRect.width < viewSize.width - viewRect.x) {}
/* 1381:     */       }
/* 1382:1637 */       else if (viewRect.height >= viewSize.height - viewRect.y) {
/* 1383:1638 */         return;
/* 1384:     */       }
/* 1385:1641 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex + 1);
/* 1386:     */     }
/* 1387:     */     
/* 1388:     */     public void scrollBackward(int tabPlacement)
/* 1389:     */     {
/* 1390:1645 */       if (this.leadingTabIndex == 0) {
/* 1391:1646 */         return;
/* 1392:     */       }
/* 1393:1648 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex - 1);
/* 1394:     */     }
/* 1395:     */     
/* 1396:     */     public void setLeadingTabIndex(int tabPlacement, int index)
/* 1397:     */     {
/* 1398:1652 */       this.leadingTabIndex = index;
/* 1399:1653 */       Dimension viewSize = this.viewport.getViewSize();
/* 1400:1654 */       Rectangle viewRect = this.viewport.getViewRect();
/* 1401:1656 */       switch (tabPlacement)
/* 1402:     */       {
/* 1403:     */       case 1: 
/* 1404:     */       case 3: 
/* 1405:1659 */         this.tabViewPosition.x = (this.leadingTabIndex == 0 ? 0 : PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].x - PlasticTabbedPaneUI.this.renderer.getTabsOverlay());
/* 1406:1662 */         if (viewSize.width - this.tabViewPosition.x < viewRect.width)
/* 1407:     */         {
/* 1408:1666 */           Dimension extentSize = new Dimension(viewSize.width - this.tabViewPosition.x, viewRect.height);
/* 1409:     */           
/* 1410:1668 */           this.viewport.setExtentSize(extentSize);
/* 1411:     */         }
/* 1412:1669 */         break;
/* 1413:     */       case 2: 
/* 1414:     */       case 4: 
/* 1415:1673 */         this.tabViewPosition.y = (this.leadingTabIndex == 0 ? 0 : PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].y);
/* 1416:1676 */         if (viewSize.height - this.tabViewPosition.y < viewRect.height)
/* 1417:     */         {
/* 1418:1680 */           Dimension extentSize = new Dimension(viewRect.width, viewSize.height - this.tabViewPosition.y);
/* 1419:     */           
/* 1420:1682 */           this.viewport.setExtentSize(extentSize);
/* 1421:     */         }
/* 1422:     */         break;
/* 1423:     */       }
/* 1424:1685 */       this.viewport.setViewPosition(this.tabViewPosition);
/* 1425:     */     }
/* 1426:     */     
/* 1427:     */     public void stateChanged(ChangeEvent e)
/* 1428:     */     {
/* 1429:1689 */       JViewport viewport = (JViewport)e.getSource();
/* 1430:1690 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1431:1691 */       int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
/* 1432:1692 */       Rectangle vpRect = viewport.getBounds();
/* 1433:1693 */       Dimension viewSize = viewport.getViewSize();
/* 1434:1694 */       Rectangle viewRect = viewport.getViewRect();
/* 1435:     */       
/* 1436:1696 */       this.leadingTabIndex = PlasticTabbedPaneUI.this.getClosestTab(viewRect.x, viewRect.y);
/* 1437:1699 */       if (this.leadingTabIndex + 1 < tabCount) {
/* 1438:1700 */         switch (tabPlacement)
/* 1439:     */         {
/* 1440:     */         case 1: 
/* 1441:     */         case 3: 
/* 1442:1703 */           if (PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].x < viewRect.x) {
/* 1443:1704 */             this.leadingTabIndex += 1;
/* 1444:     */           }
/* 1445:     */           break;
/* 1446:     */         case 2: 
/* 1447:     */         case 4: 
/* 1448:1709 */           if (PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].y < viewRect.y) {
/* 1449:1710 */             this.leadingTabIndex += 1;
/* 1450:     */           }
/* 1451:     */           break;
/* 1452:     */         }
/* 1453:     */       }
/* 1454:1715 */       Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 1455:1716 */       switch (tabPlacement)
/* 1456:     */       {
/* 1457:     */       case 2: 
/* 1458:1718 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x + vpRect.width, vpRect.y, contentInsets.left, vpRect.height);
/* 1459:     */         
/* 1460:1720 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) && (this.leadingTabIndex > 0));
/* 1461:     */         
/* 1462:1722 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/* 1463:     */         
/* 1464:1724 */         break;
/* 1465:     */       case 4: 
/* 1466:1726 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y, contentInsets.right, vpRect.height);
/* 1467:     */         
/* 1468:1728 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) && (this.leadingTabIndex > 0));
/* 1469:     */         
/* 1470:1730 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/* 1471:     */         
/* 1472:1732 */         break;
/* 1473:     */       case 3: 
/* 1474:1734 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom, vpRect.width, contentInsets.bottom);
/* 1475:     */         
/* 1476:1736 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) && (this.leadingTabIndex > 0));
/* 1477:     */         
/* 1478:1738 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - viewRect.x > viewRect.width));
/* 1479:     */         
/* 1480:1740 */         break;
/* 1481:     */       case 1: 
/* 1482:     */       default: 
/* 1483:1743 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y + vpRect.height, vpRect.width, contentInsets.top);
/* 1484:     */         
/* 1485:1745 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) && (this.leadingTabIndex > 0));
/* 1486:     */         
/* 1487:1747 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - viewRect.x > viewRect.width));
/* 1488:     */       }
/* 1489:     */     }
/* 1490:     */     
/* 1491:     */     public void actionPerformed(ActionEvent e)
/* 1492:     */     {
/* 1493:1756 */       ActionMap map = PlasticTabbedPaneUI.this.tabPane.getActionMap();
/* 1494:1758 */       if (map != null)
/* 1495:     */       {
/* 1496:     */         String actionKey;
/* 1497:     */         String actionKey;
/* 1498:1761 */         if (e.getSource() == this.scrollForwardButton) {
/* 1499:1762 */           actionKey = "scrollTabsForwardAction";
/* 1500:     */         } else {
/* 1501:1764 */           actionKey = "scrollTabsBackwardAction";
/* 1502:     */         }
/* 1503:1766 */         Action action = map.get(actionKey);
/* 1504:1768 */         if ((action != null) && (action.isEnabled())) {
/* 1505:1769 */           action.actionPerformed(new ActionEvent(PlasticTabbedPaneUI.this.tabPane, 1001, null, e.getWhen(), e.getModifiers()));
/* 1506:     */         }
/* 1507:     */       }
/* 1508:     */     }
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   private final class ScrollableTabViewport
/* 1512:     */     extends JViewport
/* 1513:     */     implements UIResource
/* 1514:     */   {
/* 1515:     */     public ScrollableTabViewport()
/* 1516:     */     {
/* 1517:1782 */       setName("TabbedPane.scrollableViewport");
/* 1518:1783 */       setScrollMode(0);
/* 1519:1784 */       setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
/* 1520:1785 */       Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 1521:1786 */       if (bgColor == null) {
/* 1522:1787 */         bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground();
/* 1523:     */       }
/* 1524:1789 */       setBackground(bgColor);
/* 1525:     */     }
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   private final class ScrollableTabPanel
/* 1529:     */     extends JPanel
/* 1530:     */     implements UIResource
/* 1531:     */   {
/* 1532:     */     public ScrollableTabPanel()
/* 1533:     */     {
/* 1534:1796 */       super();
/* 1535:1797 */       setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
/* 1536:1798 */       Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 1537:1799 */       if (bgColor == null) {
/* 1538:1800 */         bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground();
/* 1539:     */       }
/* 1540:1802 */       setBackground(bgColor);
/* 1541:     */     }
/* 1542:     */     
/* 1543:     */     public void paintComponent(Graphics g)
/* 1544:     */     {
/* 1545:1806 */       super.paintComponent(g);
/* 1546:1807 */       PlasticTabbedPaneUI.this.paintTabArea(g, PlasticTabbedPaneUI.this.tabPane.getTabPlacement(), PlasticTabbedPaneUI.this.tabPane.getSelectedIndex());
/* 1547:     */     }
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   private static final class ArrowButton
/* 1551:     */     extends JButton
/* 1552:     */     implements UIResource
/* 1553:     */   {
/* 1554:     */     private final int buttonWidth;
/* 1555:     */     private final int direction;
/* 1556:     */     private boolean mouseIsOver;
/* 1557:     */     
/* 1558:     */     ArrowButton(int direction, int buttonWidth)
/* 1559:     */     {
/* 1560:1820 */       this.direction = direction;
/* 1561:1821 */       this.buttonWidth = buttonWidth;
/* 1562:1822 */       setRequestFocusEnabled(false);
/* 1563:     */     }
/* 1564:     */     
/* 1565:     */     protected void processMouseEvent(MouseEvent e)
/* 1566:     */     {
/* 1567:1826 */       super.processMouseEvent(e);
/* 1568:1827 */       switch (e.getID())
/* 1569:     */       {
/* 1570:     */       case 504: 
/* 1571:1829 */         this.mouseIsOver = true;
/* 1572:1830 */         revalidate();
/* 1573:1831 */         repaint();
/* 1574:1832 */         break;
/* 1575:     */       case 505: 
/* 1576:1834 */         this.mouseIsOver = false;
/* 1577:1835 */         revalidate();
/* 1578:1836 */         repaint();
/* 1579:     */       }
/* 1580:     */     }
/* 1581:     */     
/* 1582:     */     protected void paintBorder(Graphics g)
/* 1583:     */     {
/* 1584:1842 */       if ((this.mouseIsOver) && (isEnabled())) {
/* 1585:1843 */         super.paintBorder(g);
/* 1586:     */       }
/* 1587:     */     }
/* 1588:     */     
/* 1589:     */     protected void paintComponent(Graphics g)
/* 1590:     */     {
/* 1591:1848 */       if (this.mouseIsOver)
/* 1592:     */       {
/* 1593:1849 */         super.paintComponent(g);
/* 1594:     */       }
/* 1595:     */       else
/* 1596:     */       {
/* 1597:1851 */         g.setColor(getBackground());
/* 1598:1852 */         g.fillRect(0, 0, getWidth(), getHeight());
/* 1599:     */       }
/* 1600:1854 */       paintArrow(g);
/* 1601:     */     }
/* 1602:     */     
/* 1603:     */     private void paintArrow(Graphics g)
/* 1604:     */     {
/* 1605:1858 */       Color oldColor = g.getColor();
/* 1606:     */       
/* 1607:1860 */       boolean isEnabled = isEnabled();
/* 1608:1861 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled());
/* 1609:     */       int arrowWidth;
/* 1610:     */       int arrowHeight;
/* 1611:1865 */       switch (this.direction)
/* 1612:     */       {
/* 1613:     */       case 1: 
/* 1614:     */       case 5: 
/* 1615:1868 */         arrowWidth = 9;
/* 1616:1869 */         arrowHeight = 5;
/* 1617:1870 */         break;
/* 1618:     */       case 2: 
/* 1619:     */       case 3: 
/* 1620:     */       case 4: 
/* 1621:     */       case 6: 
/* 1622:     */       case 7: 
/* 1623:     */       default: 
/* 1624:1874 */         arrowWidth = 5;
/* 1625:1875 */         arrowHeight = 9;
/* 1626:     */       }
/* 1627:1878 */       int x = (getWidth() - arrowWidth) / 2;
/* 1628:1879 */       int y = (getHeight() - arrowHeight) / 2;
/* 1629:1880 */       g.translate(x, y);
/* 1630:     */       
/* 1631:1882 */       boolean paintShadow = (!this.mouseIsOver) || (!isEnabled);
/* 1632:1883 */       Color shadow = isEnabled ? PlasticLookAndFeel.getControlShadow() : UIManager.getColor("ScrollBar.highlight");
/* 1633:1886 */       switch (this.direction)
/* 1634:     */       {
/* 1635:     */       case 1: 
/* 1636:1888 */         g.fillRect(0, 4, 9, 1);
/* 1637:1889 */         g.fillRect(1, 3, 7, 1);
/* 1638:1890 */         g.fillRect(2, 2, 5, 1);
/* 1639:1891 */         g.fillRect(3, 1, 3, 1);
/* 1640:1892 */         g.fillRect(4, 0, 1, 1);
/* 1641:1893 */         if (paintShadow)
/* 1642:     */         {
/* 1643:1894 */           g.setColor(shadow);
/* 1644:1895 */           g.fillRect(1, 5, 9, 1);
/* 1645:     */         }
/* 1646:     */         break;
/* 1647:     */       case 5: 
/* 1648:1899 */         g.fillRect(0, 0, 9, 1);
/* 1649:1900 */         g.fillRect(1, 1, 7, 1);
/* 1650:1901 */         g.fillRect(2, 2, 5, 1);
/* 1651:1902 */         g.fillRect(3, 3, 3, 1);
/* 1652:1903 */         g.fillRect(4, 4, 1, 1);
/* 1653:1904 */         if (paintShadow)
/* 1654:     */         {
/* 1655:1905 */           g.setColor(shadow);
/* 1656:1906 */           g.drawLine(5, 4, 8, 1);
/* 1657:1907 */           g.drawLine(5, 5, 9, 1);
/* 1658:     */         }
/* 1659:     */         break;
/* 1660:     */       case 7: 
/* 1661:1911 */         g.fillRect(0, 4, 1, 1);
/* 1662:1912 */         g.fillRect(1, 3, 1, 3);
/* 1663:1913 */         g.fillRect(2, 2, 1, 5);
/* 1664:1914 */         g.fillRect(3, 1, 1, 7);
/* 1665:1915 */         g.fillRect(4, 0, 1, 9);
/* 1666:1916 */         if (paintShadow)
/* 1667:     */         {
/* 1668:1917 */           g.setColor(shadow);
/* 1669:1918 */           g.fillRect(5, 1, 1, 9);
/* 1670:     */         }
/* 1671:     */         break;
/* 1672:     */       case 3: 
/* 1673:1922 */         g.fillRect(0, 0, 1, 9);
/* 1674:1923 */         g.fillRect(1, 1, 1, 7);
/* 1675:1924 */         g.fillRect(2, 2, 1, 5);
/* 1676:1925 */         g.fillRect(3, 3, 1, 3);
/* 1677:1926 */         g.fillRect(4, 4, 1, 1);
/* 1678:1927 */         if (paintShadow)
/* 1679:     */         {
/* 1680:1928 */           g.setColor(shadow);
/* 1681:1929 */           g.drawLine(1, 8, 4, 5);
/* 1682:1930 */           g.drawLine(1, 9, 5, 5);
/* 1683:     */         }
/* 1684:     */         break;
/* 1685:     */       }
/* 1686:1935 */       g.translate(-x, -y);
/* 1687:1936 */       g.setColor(oldColor);
/* 1688:     */     }
/* 1689:     */     
/* 1690:     */     public Dimension getPreferredSize()
/* 1691:     */     {
/* 1692:1940 */       return new Dimension(this.buttonWidth, this.buttonWidth);
/* 1693:     */     }
/* 1694:     */     
/* 1695:     */     public Dimension getMinimumSize()
/* 1696:     */     {
/* 1697:1944 */       return getPreferredSize();
/* 1698:     */     }
/* 1699:     */     
/* 1700:     */     public Dimension getMaximumSize()
/* 1701:     */     {
/* 1702:1948 */       return new Dimension(2147483647, 2147483647);
/* 1703:     */     }
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   private static abstract class AbstractRenderer
/* 1707:     */   {
/* 1708:1958 */     protected static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/* 1709:1959 */     protected static final Insets NORTH_INSETS = new Insets(1, 0, 0, 0);
/* 1710:1960 */     protected static final Insets WEST_INSETS = new Insets(0, 1, 0, 0);
/* 1711:1961 */     protected static final Insets SOUTH_INSETS = new Insets(0, 0, 1, 0);
/* 1712:1962 */     protected static final Insets EAST_INSETS = new Insets(0, 0, 0, 1);
/* 1713:     */     protected final JTabbedPane tabPane;
/* 1714:     */     protected final int tabPlacement;
/* 1715:     */     protected Color shadowColor;
/* 1716:     */     protected Color darkShadow;
/* 1717:     */     protected Color selectColor;
/* 1718:     */     protected Color selectLight;
/* 1719:     */     protected Color selectHighlight;
/* 1720:     */     protected Color lightHighlight;
/* 1721:     */     protected Color focus;
/* 1722:     */     
/* 1723:     */     private AbstractRenderer(JTabbedPane tabPane)
/* 1724:     */     {
/* 1725:1975 */       initColors();
/* 1726:1976 */       this.tabPane = tabPane;
/* 1727:1977 */       this.tabPlacement = tabPane.getTabPlacement();
/* 1728:     */     }
/* 1729:     */     
/* 1730:     */     private static AbstractRenderer createRenderer(JTabbedPane tabPane)
/* 1731:     */     {
/* 1732:1981 */       switch (tabPane.getTabPlacement())
/* 1733:     */       {
/* 1734:     */       case 1: 
/* 1735:1983 */         return new PlasticTabbedPaneUI.TopRenderer(tabPane, null);
/* 1736:     */       case 3: 
/* 1737:1985 */         return new PlasticTabbedPaneUI.BottomRenderer(tabPane, null);
/* 1738:     */       case 2: 
/* 1739:1987 */         return new PlasticTabbedPaneUI.LeftRenderer(tabPane, null);
/* 1740:     */       case 4: 
/* 1741:1989 */         return new PlasticTabbedPaneUI.RightRenderer(tabPane, null);
/* 1742:     */       }
/* 1743:1991 */       return new PlasticTabbedPaneUI.TopRenderer(tabPane, null);
/* 1744:     */     }
/* 1745:     */     
/* 1746:     */     private static AbstractRenderer createEmbeddedRenderer(JTabbedPane tabPane)
/* 1747:     */     {
/* 1748:1996 */       switch (tabPane.getTabPlacement())
/* 1749:     */       {
/* 1750:     */       case 1: 
/* 1751:1998 */         return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane, null);
/* 1752:     */       case 3: 
/* 1753:2000 */         return new PlasticTabbedPaneUI.BottomEmbeddedRenderer(tabPane, null);
/* 1754:     */       case 2: 
/* 1755:2002 */         return new PlasticTabbedPaneUI.LeftEmbeddedRenderer(tabPane, null);
/* 1756:     */       case 4: 
/* 1757:2004 */         return new PlasticTabbedPaneUI.RightEmbeddedRenderer(tabPane, null);
/* 1758:     */       }
/* 1759:2006 */       return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane, null);
/* 1760:     */     }
/* 1761:     */     
/* 1762:     */     private void initColors()
/* 1763:     */     {
/* 1764:2011 */       this.shadowColor = UIManager.getColor("TabbedPane.shadow");
/* 1765:2012 */       this.darkShadow = UIManager.getColor("TabbedPane.darkShadow");
/* 1766:2013 */       this.selectColor = UIManager.getColor("TabbedPane.selected");
/* 1767:2014 */       this.focus = UIManager.getColor("TabbedPane.focus");
/* 1768:2015 */       this.selectHighlight = UIManager.getColor("TabbedPane.selectHighlight");
/* 1769:2016 */       this.lightHighlight = UIManager.getColor("TabbedPane.highlight");
/* 1770:2017 */       this.selectLight = new Color((2 * this.selectColor.getRed() + this.selectHighlight.getRed()) / 3, (2 * this.selectColor.getGreen() + this.selectHighlight.getGreen()) / 3, (2 * this.selectColor.getBlue() + this.selectHighlight.getBlue()) / 3);
/* 1771:     */     }
/* 1772:     */     
/* 1773:     */     protected boolean isFirstDisplayedTab(int tabIndex, int position, int paneBorder)
/* 1774:     */     {
/* 1775:2025 */       return tabIndex == 0;
/* 1776:     */     }
/* 1777:     */     
/* 1778:     */     protected Insets getTabAreaInsets(Insets defaultInsets)
/* 1779:     */     {
/* 1780:2030 */       return defaultInsets;
/* 1781:     */     }
/* 1782:     */     
/* 1783:     */     protected Insets getContentBorderInsets(Insets defaultInsets)
/* 1784:     */     {
/* 1785:2034 */       return defaultInsets;
/* 1786:     */     }
/* 1787:     */     
/* 1788:     */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
/* 1789:     */     {
/* 1790:2041 */       return 0;
/* 1791:     */     }
/* 1792:     */     
/* 1793:     */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
/* 1794:     */     {
/* 1795:2048 */       return 0;
/* 1796:     */     }
/* 1797:     */     
/* 1798:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 1799:     */     {
/* 1800:2055 */       return tabRunOverlay;
/* 1801:     */     }
/* 1802:     */     
/* 1803:     */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/* 1804:     */     {
/* 1805:2063 */       return aPriori;
/* 1806:     */     }
/* 1807:     */     
/* 1808:     */     protected int getTabRunIndent(int run)
/* 1809:     */     {
/* 1810:2072 */       return 0;
/* 1811:     */     }
/* 1812:     */     
/* 1813:     */     protected abstract Insets getTabInsets(int paramInt, Insets paramInsets);
/* 1814:     */     
/* 1815:     */     protected abstract void paintFocusIndicator(Graphics paramGraphics, Rectangle[] paramArrayOfRectangle, int paramInt, Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean);
/* 1816:     */     
/* 1817:     */     protected abstract void paintTabBackground(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean);
/* 1818:     */     
/* 1819:     */     protected abstract void paintTabBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean);
/* 1820:     */     
/* 1821:     */     protected Insets getSelectedTabPadInsets()
/* 1822:     */     {
/* 1823:2107 */       return EMPTY_INSETS;
/* 1824:     */     }
/* 1825:     */     
/* 1826:     */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 1827:     */     {
/* 1828:2124 */       if (isContentBorderPainted)
/* 1829:     */       {
/* 1830:2125 */         g.setColor(this.selectHighlight);
/* 1831:2126 */         g.fillRect(x, y, w - 1, 1);
/* 1832:     */       }
/* 1833:     */     }
/* 1834:     */     
/* 1835:     */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 1836:     */     {
/* 1837:2144 */       if (isContentBorderPainted)
/* 1838:     */       {
/* 1839:2145 */         g.setColor(this.darkShadow);
/* 1840:2146 */         g.fillRect(x, y + h - 1, w - 1, 1);
/* 1841:     */       }
/* 1842:     */     }
/* 1843:     */     
/* 1844:     */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 1845:     */     {
/* 1846:2164 */       if (isContentBorderPainted)
/* 1847:     */       {
/* 1848:2165 */         g.setColor(this.selectHighlight);
/* 1849:2166 */         g.fillRect(x, y, 1, h - 1);
/* 1850:     */       }
/* 1851:     */     }
/* 1852:     */     
/* 1853:     */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 1854:     */     {
/* 1855:2184 */       if (isContentBorderPainted)
/* 1856:     */       {
/* 1857:2185 */         g.setColor(this.darkShadow);
/* 1858:2186 */         g.fillRect(x + w - 1, y, 1, h);
/* 1859:     */       }
/* 1860:     */     }
/* 1861:     */     
/* 1862:     */     protected int getTabsOverlay()
/* 1863:     */     {
/* 1864:2194 */       return 0;
/* 1865:     */     }
/* 1866:     */   }
/* 1867:     */   
/* 1868:     */   private static final class BottomEmbeddedRenderer
/* 1869:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 1870:     */   {
/* 1871:     */     private BottomEmbeddedRenderer(JTabbedPane tabPane)
/* 1872:     */     {
/* 1873:2205 */       super(null);
/* 1874:     */     }
/* 1875:     */     
/* 1876:     */     protected Insets getTabAreaInsets(Insets insets)
/* 1877:     */     {
/* 1878:2209 */       return EMPTY_INSETS;
/* 1879:     */     }
/* 1880:     */     
/* 1881:     */     protected Insets getContentBorderInsets(Insets defaultInsets)
/* 1882:     */     {
/* 1883:2213 */       return SOUTH_INSETS;
/* 1884:     */     }
/* 1885:     */     
/* 1886:     */     protected Insets getSelectedTabPadInsets()
/* 1887:     */     {
/* 1888:2217 */       return EMPTY_INSETS;
/* 1889:     */     }
/* 1890:     */     
/* 1891:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 1892:     */     {
/* 1893:2221 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/* 1894:     */     }
/* 1895:     */     
/* 1896:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/* 1897:     */     
/* 1898:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 1899:     */     {
/* 1900:2239 */       g.setColor(this.selectColor);
/* 1901:2240 */       g.fillRect(x, y, w + 1, h);
/* 1902:     */     }
/* 1903:     */     
/* 1904:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 1905:     */     {
/* 1906:2245 */       int bottom = h;
/* 1907:2246 */       int right = w + 1;
/* 1908:     */       
/* 1909:2248 */       g.translate(x, y);
/* 1910:2249 */       if (isFirstDisplayedTab(tabIndex, x, this.tabPane.getBounds().x))
/* 1911:     */       {
/* 1912:2250 */         if (isSelected)
/* 1913:     */         {
/* 1914:2252 */           g.setColor(this.shadowColor);
/* 1915:2253 */           g.fillRect(right, 0, 1, bottom - 1);
/* 1916:2254 */           g.fillRect(right - 1, bottom - 1, 1, 1);
/* 1917:     */           
/* 1918:     */ 
/* 1919:     */ 
/* 1920:     */ 
/* 1921:     */ 
/* 1922:2260 */           g.setColor(this.selectHighlight);
/* 1923:2261 */           g.fillRect(0, 0, 1, bottom);
/* 1924:2262 */           g.fillRect(right - 1, 0, 1, bottom - 1);
/* 1925:2263 */           g.fillRect(1, bottom - 1, right - 2, 1);
/* 1926:     */         }
/* 1927:     */       }
/* 1928:2268 */       else if (isSelected)
/* 1929:     */       {
/* 1930:2270 */         g.setColor(this.shadowColor);
/* 1931:2271 */         g.fillRect(0, 0, 1, bottom - 1);
/* 1932:2272 */         g.fillRect(1, bottom - 1, 1, 1);
/* 1933:2273 */         g.fillRect(right, 0, 1, bottom - 1);
/* 1934:2274 */         g.fillRect(right - 1, bottom - 1, 1, 1);
/* 1935:     */         
/* 1936:     */ 
/* 1937:2277 */         g.setColor(this.selectHighlight);
/* 1938:2278 */         g.fillRect(1, 0, 1, bottom - 1);
/* 1939:2279 */         g.fillRect(right - 1, 0, 1, bottom - 1);
/* 1940:2280 */         g.fillRect(2, bottom - 1, right - 3, 1);
/* 1941:     */       }
/* 1942:     */       else
/* 1943:     */       {
/* 1944:2282 */         g.setColor(this.shadowColor);
/* 1945:2283 */         g.fillRect(1, h / 2, 1, h - h / 2);
/* 1946:     */       }
/* 1947:2286 */       g.translate(-x, -y);
/* 1948:     */     }
/* 1949:     */     
/* 1950:     */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 1951:     */     {
/* 1952:2299 */       g.setColor(this.shadowColor);
/* 1953:2300 */       g.fillRect(x, y + h - 1, w, 1);
/* 1954:     */     }
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   private static final class BottomRenderer
/* 1958:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 1959:     */   {
/* 1960:     */     private BottomRenderer(JTabbedPane tabPane)
/* 1961:     */     {
/* 1962:2312 */       super(null);
/* 1963:     */     }
/* 1964:     */     
/* 1965:     */     protected Insets getTabAreaInsets(Insets defaultInsets)
/* 1966:     */     {
/* 1967:2316 */       return new Insets(defaultInsets.top, defaultInsets.left + 5, defaultInsets.bottom, defaultInsets.right);
/* 1968:     */     }
/* 1969:     */     
/* 1970:     */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
/* 1971:     */     {
/* 1972:2320 */       return isSelected ? 0 : -1;
/* 1973:     */     }
/* 1974:     */     
/* 1975:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 1976:     */     {
/* 1977:2324 */       return tabRunOverlay - 2;
/* 1978:     */     }
/* 1979:     */     
/* 1980:     */     protected int getTabRunIndent(int run)
/* 1981:     */     {
/* 1982:2328 */       return 6 * run;
/* 1983:     */     }
/* 1984:     */     
/* 1985:     */     protected Insets getSelectedTabPadInsets()
/* 1986:     */     {
/* 1987:2332 */       return SOUTH_INSETS;
/* 1988:     */     }
/* 1989:     */     
/* 1990:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 1991:     */     {
/* 1992:2336 */       return new Insets(tabInsets.top, tabInsets.left - 2, tabInsets.bottom, tabInsets.right - 2);
/* 1993:     */     }
/* 1994:     */     
/* 1995:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/* 1996:     */     {
/* 1997:2347 */       if ((!this.tabPane.hasFocus()) || (!isSelected)) {
/* 1998:2348 */         return;
/* 1999:     */       }
/* 2000:2349 */       Rectangle tabRect = rects[tabIndex];
/* 2001:2350 */       int top = tabRect.y;
/* 2002:2351 */       int left = tabRect.x + 6;
/* 2003:2352 */       int height = tabRect.height - 3;
/* 2004:2353 */       int width = tabRect.width - 12;
/* 2005:2354 */       g.setColor(this.focus);
/* 2006:2355 */       g.drawRect(left, top, width, height);
/* 2007:     */     }
/* 2008:     */     
/* 2009:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2010:     */     {
/* 2011:2360 */       g.setColor(this.selectColor);
/* 2012:2361 */       g.fillRect(x, y, w, h);
/* 2013:     */     }
/* 2014:     */     
/* 2015:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2016:     */     {
/* 2017:2366 */       int bottom = h - 1;
/* 2018:2367 */       int right = w + 4;
/* 2019:     */       
/* 2020:2369 */       g.translate(x - 3, y);
/* 2021:     */       
/* 2022:     */ 
/* 2023:2372 */       g.setColor(this.selectHighlight);
/* 2024:     */       
/* 2025:     */ 
/* 2026:2375 */       g.fillRect(0, 0, 1, 2);
/* 2027:2376 */       g.drawLine(0, 2, 4, bottom - 4);
/* 2028:2377 */       g.fillRect(5, bottom - 3, 1, 2);
/* 2029:2378 */       g.fillRect(6, bottom - 1, 1, 1);
/* 2030:     */       
/* 2031:     */ 
/* 2032:2381 */       g.fillRect(7, bottom, 1, 1);
/* 2033:2382 */       g.setColor(this.darkShadow);
/* 2034:2383 */       g.fillRect(8, bottom, right - 13, 1);
/* 2035:     */       
/* 2036:     */ 
/* 2037:2386 */       g.drawLine(right + 1, 0, right - 3, bottom - 4);
/* 2038:2387 */       g.fillRect(right - 4, bottom - 3, 1, 2);
/* 2039:2388 */       g.fillRect(right - 5, bottom - 1, 1, 1);
/* 2040:     */       
/* 2041:2390 */       g.translate(-x + 3, -y);
/* 2042:     */     }
/* 2043:     */     
/* 2044:     */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2045:     */     {
/* 2046:2402 */       int bottom = y + h - 1;
/* 2047:2403 */       int right = x + w - 1;
/* 2048:2404 */       g.translate(x, bottom);
/* 2049:2405 */       if ((drawBroken) && (selRect.x >= x) && (selRect.x <= x + w))
/* 2050:     */       {
/* 2051:2407 */         g.setColor(this.darkShadow);
/* 2052:2408 */         g.fillRect(0, 0, selRect.x - x - 2, 1);
/* 2053:2409 */         if (selRect.x + selRect.width < x + w - 2)
/* 2054:     */         {
/* 2055:2410 */           g.setColor(this.darkShadow);
/* 2056:2411 */           g.fillRect(selRect.x + selRect.width + 2 - x, 0, right - selRect.x - selRect.width - 2, 1);
/* 2057:     */         }
/* 2058:     */       }
/* 2059:     */       else
/* 2060:     */       {
/* 2061:2414 */         g.setColor(this.darkShadow);
/* 2062:2415 */         g.fillRect(0, 0, w - 1, 1);
/* 2063:     */       }
/* 2064:2417 */       g.translate(-x, -bottom);
/* 2065:     */     }
/* 2066:     */     
/* 2067:     */     protected int getTabsOverlay()
/* 2068:     */     {
/* 2069:2421 */       return 4;
/* 2070:     */     }
/* 2071:     */   }
/* 2072:     */   
/* 2073:     */   private static final class LeftEmbeddedRenderer
/* 2074:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2075:     */   {
/* 2076:     */     private LeftEmbeddedRenderer(JTabbedPane tabPane)
/* 2077:     */     {
/* 2078:2432 */       super(null);
/* 2079:     */     }
/* 2080:     */     
/* 2081:     */     protected Insets getTabAreaInsets(Insets insets)
/* 2082:     */     {
/* 2083:2436 */       return EMPTY_INSETS;
/* 2084:     */     }
/* 2085:     */     
/* 2086:     */     protected Insets getContentBorderInsets(Insets defaultInsets)
/* 2087:     */     {
/* 2088:2440 */       return WEST_INSETS;
/* 2089:     */     }
/* 2090:     */     
/* 2091:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 2092:     */     {
/* 2093:2444 */       return 0;
/* 2094:     */     }
/* 2095:     */     
/* 2096:     */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/* 2097:     */     {
/* 2098:2448 */       return false;
/* 2099:     */     }
/* 2100:     */     
/* 2101:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2102:     */     {
/* 2103:2452 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/* 2104:     */     }
/* 2105:     */     
/* 2106:     */     protected Insets getSelectedTabPadInsets()
/* 2107:     */     {
/* 2108:2456 */       return EMPTY_INSETS;
/* 2109:     */     }
/* 2110:     */     
/* 2111:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/* 2112:     */     
/* 2113:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2114:     */     {
/* 2115:2473 */       g.setColor(this.selectColor);
/* 2116:2474 */       g.fillRect(x, y, w, h);
/* 2117:     */     }
/* 2118:     */     
/* 2119:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2120:     */     {
/* 2121:2479 */       int bottom = h;
/* 2122:2480 */       int right = w;
/* 2123:     */       
/* 2124:2482 */       g.translate(x, y);
/* 2125:2484 */       if (isFirstDisplayedTab(tabIndex, y, this.tabPane.getBounds().y))
/* 2126:     */       {
/* 2127:2485 */         if (isSelected)
/* 2128:     */         {
/* 2129:2487 */           g.setColor(this.selectHighlight);
/* 2130:2488 */           g.fillRect(0, 0, right, 1);
/* 2131:2489 */           g.fillRect(0, 0, 1, bottom - 1);
/* 2132:2490 */           g.fillRect(1, bottom - 1, right - 1, 1);
/* 2133:2491 */           g.setColor(this.shadowColor);
/* 2134:2492 */           g.fillRect(0, bottom - 1, 1, 1);
/* 2135:2493 */           g.fillRect(1, bottom, right - 1, 1);
/* 2136:     */         }
/* 2137:     */       }
/* 2138:2500 */       else if (isSelected)
/* 2139:     */       {
/* 2140:2502 */         g.setColor(this.selectHighlight);
/* 2141:2503 */         g.fillRect(1, 1, right - 1, 1);
/* 2142:2504 */         g.fillRect(0, 2, 1, bottom - 2);
/* 2143:2505 */         g.fillRect(1, bottom - 1, right - 1, 1);
/* 2144:2506 */         g.setColor(this.shadowColor);
/* 2145:2507 */         g.fillRect(1, 0, right - 1, 1);
/* 2146:2508 */         g.fillRect(0, 1, 1, 1);
/* 2147:2509 */         g.fillRect(0, bottom - 1, 1, 1);
/* 2148:2510 */         g.fillRect(1, bottom, right - 1, 1);
/* 2149:     */       }
/* 2150:     */       else
/* 2151:     */       {
/* 2152:2514 */         g.setColor(this.shadowColor);
/* 2153:2515 */         g.fillRect(0, 0, right / 3, 1);
/* 2154:     */       }
/* 2155:2519 */       g.translate(-x, -y);
/* 2156:     */     }
/* 2157:     */     
/* 2158:     */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2159:     */     {
/* 2160:2531 */       g.setColor(this.shadowColor);
/* 2161:2532 */       g.fillRect(x, y, 1, h);
/* 2162:     */     }
/* 2163:     */   }
/* 2164:     */   
/* 2165:     */   private static final class LeftRenderer
/* 2166:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2167:     */   {
/* 2168:     */     private LeftRenderer(JTabbedPane tabPane)
/* 2169:     */     {
/* 2170:2542 */       super(null);
/* 2171:     */     }
/* 2172:     */     
/* 2173:     */     protected Insets getTabAreaInsets(Insets defaultInsets)
/* 2174:     */     {
/* 2175:2546 */       return new Insets(defaultInsets.top + 4, defaultInsets.left, defaultInsets.bottom, defaultInsets.right);
/* 2176:     */     }
/* 2177:     */     
/* 2178:     */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
/* 2179:     */     {
/* 2180:2550 */       return 1;
/* 2181:     */     }
/* 2182:     */     
/* 2183:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 2184:     */     {
/* 2185:2554 */       return 1;
/* 2186:     */     }
/* 2187:     */     
/* 2188:     */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/* 2189:     */     {
/* 2190:2558 */       return false;
/* 2191:     */     }
/* 2192:     */     
/* 2193:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2194:     */     {
/* 2195:2562 */       return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
/* 2196:     */     }
/* 2197:     */     
/* 2198:     */     protected Insets getSelectedTabPadInsets()
/* 2199:     */     {
/* 2200:2566 */       return WEST_INSETS;
/* 2201:     */     }
/* 2202:     */     
/* 2203:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/* 2204:     */     {
/* 2205:2577 */       if ((!this.tabPane.hasFocus()) || (!isSelected)) {
/* 2206:2578 */         return;
/* 2207:     */       }
/* 2208:2579 */       Rectangle tabRect = rects[tabIndex];
/* 2209:2580 */       int top = tabRect.y + 2;
/* 2210:2581 */       int left = tabRect.x + 3;
/* 2211:2582 */       int height = tabRect.height - 5;
/* 2212:2583 */       int width = tabRect.width - 6;
/* 2213:2584 */       g.setColor(this.focus);
/* 2214:2585 */       g.drawRect(left, top, width, height);
/* 2215:     */     }
/* 2216:     */     
/* 2217:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2218:     */     {
/* 2219:2589 */       if (!isSelected)
/* 2220:     */       {
/* 2221:2590 */         g.setColor(this.selectLight);
/* 2222:2591 */         g.fillRect(x + 1, y + 1, w - 1, h - 2);
/* 2223:     */       }
/* 2224:     */       else
/* 2225:     */       {
/* 2226:2593 */         g.setColor(this.selectColor);
/* 2227:2594 */         g.fillRect(x + 1, y + 1, w - 3, h - 2);
/* 2228:     */       }
/* 2229:     */     }
/* 2230:     */     
/* 2231:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2232:     */     {
/* 2233:2600 */       int bottom = h - 1;
/* 2234:2601 */       int left = 0;
/* 2235:2602 */       g.translate(x, y);
/* 2236:     */       
/* 2237:     */ 
/* 2238:2605 */       g.setColor(this.selectHighlight);
/* 2239:     */       
/* 2240:2607 */       g.fillRect(left + 2, 0, w - 2 - left, 1);
/* 2241:     */       
/* 2242:     */ 
/* 2243:2610 */       g.fillRect(left + 1, 1, 1, 1);
/* 2244:2611 */       g.fillRect(left, 2, 1, bottom - 3);
/* 2245:2612 */       g.setColor(this.darkShadow);
/* 2246:2613 */       g.fillRect(left + 1, bottom - 1, 1, 1);
/* 2247:     */       
/* 2248:     */ 
/* 2249:2616 */       g.fillRect(left + 2, bottom, w - 2 - left, 1);
/* 2250:     */       
/* 2251:2618 */       g.translate(-x, -y);
/* 2252:     */     }
/* 2253:     */     
/* 2254:     */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2255:     */     {
/* 2256:2630 */       g.setColor(this.selectHighlight);
/* 2257:2631 */       if ((drawBroken) && (selRect.y >= y) && (selRect.y <= y + h))
/* 2258:     */       {
/* 2259:2633 */         g.fillRect(x, y, 1, selRect.y + 1 - y);
/* 2260:2634 */         if (selRect.y + selRect.height < y + h - 2) {
/* 2261:2635 */           g.fillRect(x, selRect.y + selRect.height - 1, 1, y + h - selRect.y - selRect.height);
/* 2262:     */         }
/* 2263:     */       }
/* 2264:     */       else
/* 2265:     */       {
/* 2266:2638 */         g.fillRect(x, y, 1, h - 1);
/* 2267:     */       }
/* 2268:     */     }
/* 2269:     */   }
/* 2270:     */   
/* 2271:     */   private static final class RightEmbeddedRenderer
/* 2272:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2273:     */   {
/* 2274:     */     private RightEmbeddedRenderer(JTabbedPane tabPane)
/* 2275:     */     {
/* 2276:2650 */       super(null);
/* 2277:     */     }
/* 2278:     */     
/* 2279:     */     protected Insets getTabAreaInsets(Insets insets)
/* 2280:     */     {
/* 2281:2654 */       return EMPTY_INSETS;
/* 2282:     */     }
/* 2283:     */     
/* 2284:     */     protected Insets getContentBorderInsets(Insets defaultInsets)
/* 2285:     */     {
/* 2286:2658 */       return EAST_INSETS;
/* 2287:     */     }
/* 2288:     */     
/* 2289:     */     protected int getTabRunIndent(int run)
/* 2290:     */     {
/* 2291:2662 */       return 4 * run;
/* 2292:     */     }
/* 2293:     */     
/* 2294:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 2295:     */     {
/* 2296:2666 */       return 0;
/* 2297:     */     }
/* 2298:     */     
/* 2299:     */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/* 2300:     */     {
/* 2301:2670 */       return false;
/* 2302:     */     }
/* 2303:     */     
/* 2304:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2305:     */     {
/* 2306:2674 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/* 2307:     */     }
/* 2308:     */     
/* 2309:     */     protected Insets getSelectedTabPadInsets()
/* 2310:     */     {
/* 2311:2678 */       return EMPTY_INSETS;
/* 2312:     */     }
/* 2313:     */     
/* 2314:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/* 2315:     */     
/* 2316:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2317:     */     {
/* 2318:2696 */       g.setColor(this.selectColor);
/* 2319:2697 */       g.fillRect(x, y, w, h);
/* 2320:     */     }
/* 2321:     */     
/* 2322:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2323:     */     {
/* 2324:2702 */       int bottom = h;
/* 2325:2703 */       int right = w - 1;
/* 2326:     */       
/* 2327:2705 */       g.translate(x + 1, y);
/* 2328:2707 */       if (isFirstDisplayedTab(tabIndex, y, this.tabPane.getBounds().y))
/* 2329:     */       {
/* 2330:2708 */         if (isSelected)
/* 2331:     */         {
/* 2332:2710 */           g.setColor(this.shadowColor);
/* 2333:     */           
/* 2334:     */ 
/* 2335:     */ 
/* 2336:2714 */           g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2337:2715 */           g.fillRect(0, bottom, right - 1, 1);
/* 2338:2716 */           g.setColor(this.selectHighlight);
/* 2339:2717 */           g.fillRect(0, 0, right - 1, 1);
/* 2340:2718 */           g.fillRect(right - 1, 0, 1, bottom - 1);
/* 2341:2719 */           g.fillRect(0, bottom - 1, right - 1, 1);
/* 2342:     */         }
/* 2343:     */       }
/* 2344:2722 */       else if (isSelected)
/* 2345:     */       {
/* 2346:2724 */         g.setColor(this.shadowColor);
/* 2347:2725 */         g.fillRect(0, -1, right - 1, 1);
/* 2348:2726 */         g.fillRect(right - 1, 0, 1, 1);
/* 2349:     */         
/* 2350:     */ 
/* 2351:2729 */         g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2352:2730 */         g.fillRect(0, bottom, right - 1, 1);
/* 2353:2731 */         g.setColor(this.selectHighlight);
/* 2354:2732 */         g.fillRect(0, 0, right - 1, 1);
/* 2355:2733 */         g.fillRect(right - 1, 1, 1, bottom - 2);
/* 2356:2734 */         g.fillRect(0, bottom - 1, right - 1, 1);
/* 2357:     */       }
/* 2358:     */       else
/* 2359:     */       {
/* 2360:2737 */         g.setColor(this.shadowColor);
/* 2361:2738 */         g.fillRect(2 * right / 3, 0, right / 3, 1);
/* 2362:     */       }
/* 2363:2741 */       g.translate(-x - 1, -y);
/* 2364:     */     }
/* 2365:     */     
/* 2366:     */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2367:     */     {
/* 2368:2753 */       g.setColor(this.shadowColor);
/* 2369:2754 */       g.fillRect(x + w - 1, y, 1, h);
/* 2370:     */     }
/* 2371:     */   }
/* 2372:     */   
/* 2373:     */   private static final class RightRenderer
/* 2374:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2375:     */   {
/* 2376:     */     private RightRenderer(JTabbedPane tabPane)
/* 2377:     */     {
/* 2378:2765 */       super(null);
/* 2379:     */     }
/* 2380:     */     
/* 2381:     */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
/* 2382:     */     {
/* 2383:2769 */       return 1;
/* 2384:     */     }
/* 2385:     */     
/* 2386:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 2387:     */     {
/* 2388:2773 */       return 1;
/* 2389:     */     }
/* 2390:     */     
/* 2391:     */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/* 2392:     */     {
/* 2393:2777 */       return false;
/* 2394:     */     }
/* 2395:     */     
/* 2396:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2397:     */     {
/* 2398:2781 */       return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
/* 2399:     */     }
/* 2400:     */     
/* 2401:     */     protected Insets getSelectedTabPadInsets()
/* 2402:     */     {
/* 2403:2785 */       return EAST_INSETS;
/* 2404:     */     }
/* 2405:     */     
/* 2406:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/* 2407:     */     {
/* 2408:2796 */       if ((!this.tabPane.hasFocus()) || (!isSelected)) {
/* 2409:2797 */         return;
/* 2410:     */       }
/* 2411:2798 */       Rectangle tabRect = rects[tabIndex];
/* 2412:2799 */       int top = tabRect.y + 2;
/* 2413:2800 */       int left = tabRect.x + 3;
/* 2414:2801 */       int height = tabRect.height - 5;
/* 2415:2802 */       int width = tabRect.width - 6;
/* 2416:2803 */       g.setColor(this.focus);
/* 2417:2804 */       g.drawRect(left, top, width, height);
/* 2418:     */     }
/* 2419:     */     
/* 2420:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2421:     */     {
/* 2422:2808 */       if (!isSelected)
/* 2423:     */       {
/* 2424:2809 */         g.setColor(this.selectLight);
/* 2425:2810 */         g.fillRect(x, y, w, h);
/* 2426:     */       }
/* 2427:     */       else
/* 2428:     */       {
/* 2429:2812 */         g.setColor(this.selectColor);
/* 2430:2813 */         g.fillRect(x + 2, y, w - 2, h);
/* 2431:     */       }
/* 2432:     */     }
/* 2433:     */     
/* 2434:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2435:     */     {
/* 2436:2819 */       int bottom = h - 1;
/* 2437:2820 */       int right = w;
/* 2438:     */       
/* 2439:2822 */       g.translate(x, y);
/* 2440:     */       
/* 2441:     */ 
/* 2442:     */ 
/* 2443:2826 */       g.setColor(this.selectHighlight);
/* 2444:2827 */       g.fillRect(0, 0, right - 1, 1);
/* 2445:     */       
/* 2446:2829 */       g.setColor(this.darkShadow);
/* 2447:2830 */       g.fillRect(right - 1, 1, 1, 1);
/* 2448:2831 */       g.fillRect(right, 2, 1, bottom - 3);
/* 2449:     */       
/* 2450:2833 */       g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2451:2834 */       g.fillRect(0, bottom, right - 1, 1);
/* 2452:     */       
/* 2453:2836 */       g.translate(-x, -y);
/* 2454:     */     }
/* 2455:     */     
/* 2456:     */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2457:     */     {
/* 2458:2848 */       g.setColor(this.darkShadow);
/* 2459:2849 */       if ((drawBroken) && (selRect.y >= y) && (selRect.y <= y + h))
/* 2460:     */       {
/* 2461:2851 */         g.fillRect(x + w - 1, y, 1, selRect.y - y);
/* 2462:2852 */         if (selRect.y + selRect.height < y + h - 2) {
/* 2463:2853 */           g.fillRect(x + w - 1, selRect.y + selRect.height, 1, y + h - selRect.y - selRect.height);
/* 2464:     */         }
/* 2465:     */       }
/* 2466:     */       else
/* 2467:     */       {
/* 2468:2856 */         g.fillRect(x + w - 1, y, 1, h - 1);
/* 2469:     */       }
/* 2470:     */     }
/* 2471:     */   }
/* 2472:     */   
/* 2473:     */   private static final class TopEmbeddedRenderer
/* 2474:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2475:     */   {
/* 2476:     */     private TopEmbeddedRenderer(JTabbedPane tabPane)
/* 2477:     */     {
/* 2478:2867 */       super(null);
/* 2479:     */     }
/* 2480:     */     
/* 2481:     */     protected Insets getTabAreaInsets(Insets insets)
/* 2482:     */     {
/* 2483:2871 */       return EMPTY_INSETS;
/* 2484:     */     }
/* 2485:     */     
/* 2486:     */     protected Insets getContentBorderInsets(Insets defaultInsets)
/* 2487:     */     {
/* 2488:2875 */       return NORTH_INSETS;
/* 2489:     */     }
/* 2490:     */     
/* 2491:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2492:     */     {
/* 2493:2879 */       return new Insets(tabInsets.top, tabInsets.left + 1, tabInsets.bottom, tabInsets.right);
/* 2494:     */     }
/* 2495:     */     
/* 2496:     */     protected Insets getSelectedTabPadInsets()
/* 2497:     */     {
/* 2498:2883 */       return EMPTY_INSETS;
/* 2499:     */     }
/* 2500:     */     
/* 2501:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/* 2502:     */     
/* 2503:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2504:     */     {
/* 2505:2901 */       g.setColor(this.selectColor);
/* 2506:2902 */       g.fillRect(x, y, w, h);
/* 2507:     */     }
/* 2508:     */     
/* 2509:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2510:     */     {
/* 2511:2907 */       g.translate(x, y);
/* 2512:     */       
/* 2513:2909 */       int right = w;
/* 2514:2910 */       int bottom = h;
/* 2515:2912 */       if (isFirstDisplayedTab(tabIndex, x, this.tabPane.getBounds().x))
/* 2516:     */       {
/* 2517:2913 */         if (isSelected)
/* 2518:     */         {
/* 2519:2914 */           g.setColor(this.selectHighlight);
/* 2520:     */           
/* 2521:2916 */           g.fillRect(0, 0, 1, bottom);
/* 2522:     */           
/* 2523:2918 */           g.fillRect(0, 0, right - 1, 1);
/* 2524:     */           
/* 2525:2920 */           g.fillRect(right - 1, 0, 1, bottom);
/* 2526:2921 */           g.setColor(this.shadowColor);
/* 2527:     */           
/* 2528:2923 */           g.fillRect(right - 1, 0, 1, 1);
/* 2529:     */           
/* 2530:2925 */           g.fillRect(right, 1, 1, bottom);
/* 2531:     */         }
/* 2532:     */       }
/* 2533:2928 */       else if (isSelected)
/* 2534:     */       {
/* 2535:2929 */         g.setColor(this.selectHighlight);
/* 2536:     */         
/* 2537:2931 */         g.fillRect(1, 1, 1, bottom - 1);
/* 2538:     */         
/* 2539:2933 */         g.fillRect(2, 0, right - 3, 1);
/* 2540:     */         
/* 2541:2935 */         g.fillRect(right - 1, 1, 1, bottom - 1);
/* 2542:2936 */         g.setColor(this.shadowColor);
/* 2543:     */         
/* 2544:2938 */         g.fillRect(0, 1, 1, bottom - 1);
/* 2545:     */         
/* 2546:2940 */         g.fillRect(1, 0, 1, 1);
/* 2547:     */         
/* 2548:2942 */         g.fillRect(right - 1, 0, 1, 1);
/* 2549:     */         
/* 2550:2944 */         g.fillRect(right, 1, 1, bottom);
/* 2551:     */       }
/* 2552:     */       else
/* 2553:     */       {
/* 2554:2946 */         g.setColor(this.shadowColor);
/* 2555:2947 */         g.fillRect(0, 0, 1, bottom + 2 - bottom / 2);
/* 2556:     */       }
/* 2557:2950 */       g.translate(-x, -y);
/* 2558:     */     }
/* 2559:     */     
/* 2560:     */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2561:     */     {
/* 2562:2962 */       g.setColor(this.shadowColor);
/* 2563:2963 */       g.fillRect(x, y, w, 1);
/* 2564:     */     }
/* 2565:     */   }
/* 2566:     */   
/* 2567:     */   private static final class TopRenderer
/* 2568:     */     extends PlasticTabbedPaneUI.AbstractRenderer
/* 2569:     */   {
/* 2570:     */     private TopRenderer(JTabbedPane tabPane)
/* 2571:     */     {
/* 2572:2974 */       super(null);
/* 2573:     */     }
/* 2574:     */     
/* 2575:     */     protected Insets getTabAreaInsets(Insets defaultInsets)
/* 2576:     */     {
/* 2577:2978 */       return new Insets(defaultInsets.top, defaultInsets.left + 4, defaultInsets.bottom, defaultInsets.right);
/* 2578:     */     }
/* 2579:     */     
/* 2580:     */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
/* 2581:     */     {
/* 2582:2982 */       return isSelected ? -1 : 0;
/* 2583:     */     }
/* 2584:     */     
/* 2585:     */     protected int getTabRunOverlay(int tabRunOverlay)
/* 2586:     */     {
/* 2587:2986 */       return tabRunOverlay - 2;
/* 2588:     */     }
/* 2589:     */     
/* 2590:     */     protected int getTabRunIndent(int run)
/* 2591:     */     {
/* 2592:2990 */       return 6 * run;
/* 2593:     */     }
/* 2594:     */     
/* 2595:     */     protected Insets getSelectedTabPadInsets()
/* 2596:     */     {
/* 2597:2994 */       return NORTH_INSETS;
/* 2598:     */     }
/* 2599:     */     
/* 2600:     */     protected Insets getTabInsets(int tabIndex, Insets tabInsets)
/* 2601:     */     {
/* 2602:2998 */       return new Insets(tabInsets.top - 1, tabInsets.left - 4, tabInsets.bottom, tabInsets.right - 4);
/* 2603:     */     }
/* 2604:     */     
/* 2605:     */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/* 2606:     */     {
/* 2607:3009 */       if ((!this.tabPane.hasFocus()) || (!isSelected)) {
/* 2608:3010 */         return;
/* 2609:     */       }
/* 2610:3011 */       Rectangle tabRect = rects[tabIndex];
/* 2611:3012 */       int top = tabRect.y + 1;
/* 2612:3013 */       int left = tabRect.x + 4;
/* 2613:3014 */       int height = tabRect.height - 3;
/* 2614:3015 */       int width = tabRect.width - 9;
/* 2615:3016 */       g.setColor(this.focus);
/* 2616:3017 */       g.drawRect(left, top, width, height);
/* 2617:     */     }
/* 2618:     */     
/* 2619:     */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2620:     */     {
/* 2621:3022 */       int sel = isSelected ? 0 : 1;
/* 2622:3023 */       g.setColor(this.selectColor);
/* 2623:3024 */       g.fillRect(x, y + sel, w, h / 2);
/* 2624:3025 */       g.fillRect(x - 1, y + sel + h / 2, w + 2, h - h / 2);
/* 2625:     */     }
/* 2626:     */     
/* 2627:     */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/* 2628:     */     {
/* 2629:3030 */       g.translate(x - 4, y);
/* 2630:     */       
/* 2631:3032 */       int top = 0;
/* 2632:3033 */       int right = w + 6;
/* 2633:     */       
/* 2634:     */ 
/* 2635:3036 */       g.setColor(this.selectHighlight);
/* 2636:     */       
/* 2637:     */ 
/* 2638:3039 */       g.drawLine(1, h - 1, 4, top + 4);
/* 2639:3040 */       g.fillRect(5, top + 2, 1, 2);
/* 2640:3041 */       g.fillRect(6, top + 1, 1, 1);
/* 2641:     */       
/* 2642:     */ 
/* 2643:3044 */       g.fillRect(7, top, right - 12, 1);
/* 2644:     */       
/* 2645:     */ 
/* 2646:3047 */       g.setColor(this.darkShadow);
/* 2647:3048 */       g.drawLine(right, h - 1, right - 3, top + 4);
/* 2648:3049 */       g.fillRect(right - 4, top + 2, 1, 2);
/* 2649:3050 */       g.fillRect(right - 5, top + 1, 1, 1);
/* 2650:     */       
/* 2651:3052 */       g.translate(-x + 4, -y);
/* 2652:     */     }
/* 2653:     */     
/* 2654:     */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/* 2655:     */     {
/* 2656:3064 */       int right = x + w - 1;
/* 2657:3065 */       int top = y;
/* 2658:3066 */       g.setColor(this.selectHighlight);
/* 2659:3068 */       if ((drawBroken) && (selRect.x >= x) && (selRect.x <= x + w))
/* 2660:     */       {
/* 2661:3070 */         g.fillRect(x, top, selRect.x - 2 - x, 1);
/* 2662:3071 */         if (selRect.x + selRect.width < x + w - 2) {
/* 2663:3072 */           g.fillRect(selRect.x + selRect.width + 2, top, right - 2 - selRect.x - selRect.width, 1);
/* 2664:     */         } else {
/* 2665:3074 */           g.fillRect(x + w - 2, top, 1, 1);
/* 2666:     */         }
/* 2667:     */       }
/* 2668:     */       else
/* 2669:     */       {
/* 2670:3077 */         g.fillRect(x, top, w - 1, 1);
/* 2671:     */       }
/* 2672:     */     }
/* 2673:     */     
/* 2674:     */     protected int getTabsOverlay()
/* 2675:     */     {
/* 2676:3082 */       return 6;
/* 2677:     */     }
/* 2678:     */   }
/* 2679:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticTabbedPaneUI
 * JD-Core Version:    0.7.0.1
 */