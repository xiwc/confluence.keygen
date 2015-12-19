/*      */ package com.jgoodies.looks.plastic;
/*      */ 
/*      */ import com.jgoodies.looks.LookUtils;
/*      */ import com.jgoodies.looks.Options;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.plaf.basic.BasicTabbedPaneUI.PropertyChangeHandler;
/*      */ import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
/*      */ import javax.swing.plaf.metal.MetalTabbedPaneUI;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class PlasticTabbedPaneUI
/*      */   extends MetalTabbedPaneUI
/*      */ {
/*   96 */   private static boolean isTabIconsEnabled = ;
/*      */   
/*      */ 
/*      */ 
/*      */   private Boolean noContentBorder;
/*      */   
/*      */ 
/*      */ 
/*      */   private Boolean embeddedTabs;
/*      */   
/*      */ 
/*      */ 
/*      */   private AbstractRenderer renderer;
/*      */   
/*      */ 
/*      */ 
/*      */   private ScrollableTabSupport tabScroller;
/*      */   
/*      */ 
/*      */ 
/*      */   private final int[] xCropLen;
/*      */   
/*      */ 
/*      */   private final int[] yCropLen;
/*      */   
/*      */ 
/*      */   private static final int CROP_SEGMENT = 12;
/*      */   
/*      */ 
/*      */ 
/*      */   public static ComponentUI createUI(JComponent tabPane)
/*      */   {
/*  128 */     return new PlasticTabbedPaneUI();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void installUI(JComponent c)
/*      */   {
/*  137 */     super.installUI(c);
/*  138 */     this.embeddedTabs = ((Boolean)c.getClientProperty("jgoodies.embeddedTabs"));
/*  139 */     this.noContentBorder = ((Boolean)c.getClientProperty("jgoodies.noContentBorder"));
/*  140 */     this.renderer = createRenderer(this.tabPane);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void uninstallUI(JComponent c)
/*      */   {
/*  148 */     this.renderer = null;
/*  149 */     super.uninstallUI(c);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void installComponents()
/*      */   {
/*  158 */     if ((scrollableTabLayoutEnabled()) && 
/*  159 */       (this.tabScroller == null)) {
/*  160 */       this.tabScroller = new ScrollableTabSupport(this.tabPane.getTabPlacement());
/*  161 */       this.tabPane.add(this.tabScroller.viewport);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void uninstallComponents()
/*      */   {
/*  172 */     if (scrollableTabLayoutEnabled()) {
/*  173 */       this.tabPane.remove(this.tabScroller.viewport);
/*  174 */       this.tabPane.remove(this.tabScroller.scrollForwardButton);
/*  175 */       this.tabPane.remove(this.tabScroller.scrollBackwardButton);
/*  176 */       this.tabScroller = null;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void installListeners() {
/*  181 */     super.installListeners();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  188 */     if ((this.mouseListener != null) && (LookUtils.IS_JAVA_1_4) && 
/*  189 */       (scrollableTabLayoutEnabled())) {
/*  190 */       this.tabPane.removeMouseListener(this.mouseListener);
/*  191 */       this.tabScroller.tabPanel.addMouseListener(this.mouseListener);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallListeners()
/*      */   {
/*  197 */     if ((this.mouseListener != null) && (LookUtils.IS_JAVA_1_4)) {
/*  198 */       if (scrollableTabLayoutEnabled()) {
/*  199 */         this.tabScroller.tabPanel.removeMouseListener(this.mouseListener);
/*      */       } else {
/*  201 */         this.tabPane.removeMouseListener(this.mouseListener);
/*      */       }
/*  203 */       this.mouseListener = null;
/*      */     }
/*  205 */     super.uninstallListeners();
/*      */   }
/*      */   
/*      */   protected void installKeyboardActions() {
/*  209 */     super.installKeyboardActions();
/*      */     
/*      */ 
/*      */ 
/*  213 */     if (scrollableTabLayoutEnabled()) {
/*  214 */       Action forwardAction = new ScrollTabsForwardAction(null);
/*  215 */       Action backwardAction = new ScrollTabsBackwardAction(null);
/*  216 */       ActionMap am = SwingUtilities.getUIActionMap(this.tabPane);
/*  217 */       am.put("scrollTabsForwardAction", forwardAction);
/*  218 */       am.put("scrollTabsBackwardAction", backwardAction);
/*  219 */       this.tabScroller.scrollForwardButton.setAction(forwardAction);
/*  220 */       this.tabScroller.scrollBackwardButton.setAction(backwardAction);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean hasNoContentBorder()
/*      */   {
/*  230 */     return Boolean.TRUE.equals(this.noContentBorder);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean hasEmbeddedTabs()
/*      */   {
/*  237 */     return Boolean.TRUE.equals(this.embeddedTabs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private AbstractRenderer createRenderer(JTabbedPane tabbedPane)
/*      */   {
/*  246 */     return hasEmbeddedTabs() ? AbstractRenderer.createEmbeddedRenderer(tabbedPane) : AbstractRenderer.createRenderer(this.tabPane);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PropertyChangeListener createPropertyChangeListener()
/*      */   {
/*  257 */     return new MyPropertyChangeHandler(null);
/*      */   }
/*      */   
/*      */   protected ChangeListener createChangeListener() {
/*  261 */     return new TabSelectionHandler(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void doLayout()
/*      */   {
/*  268 */     this.tabPane.revalidate();
/*  269 */     this.tabPane.repaint();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void tabPlacementChanged()
/*      */   {
/*  277 */     this.renderer = createRenderer(this.tabPane);
/*  278 */     if (scrollableTabLayoutEnabled()) {
/*  279 */       this.tabScroller.createButtons();
/*      */     }
/*  281 */     doLayout();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void embeddedTabsPropertyChanged(Boolean newValue)
/*      */   {
/*  289 */     this.embeddedTabs = newValue;
/*  290 */     this.renderer = createRenderer(this.tabPane);
/*  291 */     doLayout();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void noContentBorderPropertyChanged(Boolean newValue)
/*      */   {
/*  300 */     this.noContentBorder = newValue;
/*  301 */     this.tabPane.repaint();
/*      */   }
/*      */   
/*      */   public void paint(Graphics g, JComponent c) {
/*  305 */     int selectedIndex = this.tabPane.getSelectedIndex();
/*  306 */     int tabPlacement = this.tabPane.getTabPlacement();
/*      */     
/*  308 */     ensureCurrentLayout();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  314 */     if (!scrollableTabLayoutEnabled()) {
/*  315 */       paintTabArea(g, tabPlacement, selectedIndex);
/*      */     }
/*      */     
/*      */ 
/*  319 */     paintContentBorder(g, tabPlacement, selectedIndex);
/*      */   }
/*      */   
/*      */   protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect)
/*      */   {
/*  324 */     Rectangle tabRect = rects[tabIndex];
/*  325 */     int selectedIndex = this.tabPane.getSelectedIndex();
/*  326 */     boolean isSelected = selectedIndex == tabIndex;
/*  327 */     Graphics2D g2 = null;
/*  328 */     Polygon cropShape = null;
/*  329 */     Shape save = null;
/*  330 */     int cropx = 0;
/*  331 */     int cropy = 0;
/*      */     
/*  333 */     if ((scrollableTabLayoutEnabled()) && 
/*  334 */       ((g instanceof Graphics2D))) {
/*  335 */       g2 = (Graphics2D)g;
/*      */       
/*      */ 
/*  338 */       Rectangle viewRect = this.tabScroller.viewport.getViewRect();
/*      */       int cropline;
/*  340 */       switch (tabPlacement) {
/*      */       case 2: 
/*      */       case 4: 
/*  343 */         cropline = viewRect.y + viewRect.height;
/*  344 */         if ((tabRect.y < cropline) && (tabRect.y + tabRect.height > cropline))
/*      */         {
/*  346 */           cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
/*      */           
/*  348 */           cropx = tabRect.x;
/*  349 */           cropy = cropline - 1;
/*      */         }
/*      */         break;
/*      */       case 1: 
/*      */       case 3: 
/*      */       default: 
/*  355 */         cropline = viewRect.x + viewRect.width;
/*  356 */         if ((tabRect.x < cropline) && (tabRect.x + tabRect.width > cropline))
/*      */         {
/*  358 */           cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
/*      */           
/*  360 */           cropx = cropline - 1;
/*  361 */           cropy = tabRect.y;
/*      */         }
/*      */         break; }
/*  364 */       if (cropShape != null) {
/*  365 */         save = g.getClip();
/*  366 */         g2.clip(cropShape);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  371 */     paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*      */     
/*      */ 
/*  374 */     paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
/*      */     
/*      */ 
/*  377 */     String title = this.tabPane.getTitleAt(tabIndex);
/*  378 */     Font font = this.tabPane.getFont();
/*  379 */     FontMetrics metrics = g.getFontMetrics(font);
/*  380 */     Icon icon = getIconForTab(tabIndex);
/*      */     
/*  382 */     layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
/*      */     
/*      */ 
/*  385 */     paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
/*      */     
/*      */ 
/*  388 */     paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
/*      */     
/*  390 */     paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
/*      */     
/*      */ 
/*  393 */     if (cropShape != null) {
/*  394 */       paintCroppedTabEdge(g, tabPlacement, tabIndex, isSelected, cropx, cropy);
/*      */       
/*  396 */       g.setClip(save);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PlasticTabbedPaneUI()
/*      */   {
/*  422 */     this.xCropLen = new int[] { 1, 1, 0, 0, 1, 1, 2, 2 };
/*      */     
/*  424 */     this.yCropLen = new int[] { 0, 3, 3, 6, 6, 9, 9, 12 };
/*      */   }
/*      */   
/*      */ 
/*      */   private Polygon createCroppedTabClip(int tabPlacement, Rectangle tabRect, int cropline)
/*      */   {
/*  430 */     int rlen = 0;
/*  431 */     int start = 0;
/*  432 */     int end = 0;
/*  433 */     int ostart = 0;
/*      */     
/*  435 */     switch (tabPlacement) {
/*      */     case 2: 
/*      */     case 4: 
/*  438 */       rlen = tabRect.width;
/*  439 */       start = tabRect.x;
/*  440 */       end = tabRect.x + tabRect.width;
/*  441 */       ostart = tabRect.y;
/*  442 */       break;
/*      */     case 1: 
/*      */     case 3: 
/*      */     default: 
/*  446 */       rlen = tabRect.height;
/*  447 */       start = tabRect.y;
/*  448 */       end = tabRect.y + tabRect.height;
/*  449 */       ostart = tabRect.x;
/*      */     }
/*  451 */     int rcnt = rlen / 12;
/*  452 */     if (rlen % 12 > 0) {
/*  453 */       rcnt++;
/*      */     }
/*  455 */     int npts = 2 + rcnt * 8;
/*  456 */     int[] xp = new int[npts];
/*  457 */     int[] yp = new int[npts];
/*  458 */     int pcnt = 0;
/*      */     
/*  460 */     xp[pcnt] = ostart;
/*  461 */     yp[(pcnt++)] = end;
/*  462 */     xp[pcnt] = ostart;
/*  463 */     yp[(pcnt++)] = start;
/*  464 */     for (int i = 0; i < rcnt; i++) {
/*  465 */       for (int j = 0; j < this.xCropLen.length; j++) {
/*  466 */         xp[pcnt] = (cropline - this.xCropLen[j]);
/*  467 */         yp[pcnt] = (start + i * 12 + this.yCropLen[j]);
/*  468 */         if (yp[pcnt] >= end) {
/*  469 */           yp[pcnt] = end;
/*  470 */           pcnt++;
/*  471 */           break;
/*      */         }
/*  473 */         pcnt++;
/*      */       }
/*      */     }
/*  476 */     if ((tabPlacement == 1) || (tabPlacement == 3))
/*      */     {
/*  478 */       return new Polygon(xp, yp, pcnt);
/*      */     }
/*      */     
/*      */ 
/*  482 */     return new Polygon(yp, xp, pcnt);
/*      */   }
/*      */   
/*      */ 
/*      */   private void paintCroppedTabEdge(Graphics g, int tabPlacement, int tabIndex, boolean isSelected, int x, int y)
/*      */   {
/*      */     int xx;
/*      */     
/*  490 */     switch (tabPlacement) {
/*      */     case 2: 
/*      */     case 4: 
/*  493 */       xx = x;
/*  494 */       g.setColor(this.shadow); }
/*  495 */     while (xx <= x + this.rects[tabIndex].width) {
/*  496 */       for (int i = 0; i < this.xCropLen.length; i += 2) {
/*  497 */         g.drawLine(xx + this.yCropLen[i], y - this.xCropLen[i], xx + this.yCropLen[(i + 1)] - 1, y - this.xCropLen[(i + 1)]);
/*      */       }
/*      */       
/*  500 */       xx += 12; continue;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  506 */       int yy = y;
/*  507 */       g.setColor(this.shadow);
/*  508 */       while (yy <= y + this.rects[tabIndex].height) {
/*  509 */         for (int i = 0; i < this.xCropLen.length; i += 2) {
/*  510 */           g.drawLine(x - this.xCropLen[i], yy + this.yCropLen[i], x - this.xCropLen[(i + 1)], yy + this.yCropLen[(i + 1)] - 1);
/*      */         }
/*      */         
/*  513 */         yy += 12;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void ensureCurrentLayout() {
/*  519 */     if (!this.tabPane.isValid()) {
/*  520 */       this.tabPane.validate();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  526 */     if (!this.tabPane.isValid()) {
/*  527 */       TabbedPaneLayout layout = (TabbedPaneLayout)this.tabPane.getLayout();
/*  528 */       layout.calculateLayoutInfo();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int tabForCoordinate(JTabbedPane pane, int x, int y)
/*      */   {
/*  537 */     ensureCurrentLayout();
/*  538 */     Point p = new Point(x, y);
/*      */     
/*  540 */     if (scrollableTabLayoutEnabled()) {
/*  541 */       translatePointToTabPanel(x, y, p);
/*  542 */       Rectangle viewRect = this.tabScroller.viewport.getViewRect();
/*  543 */       if (!viewRect.contains(p)) {
/*  544 */         return -1;
/*      */       }
/*      */     }
/*  547 */     int tabCount = this.tabPane.getTabCount();
/*  548 */     for (int i = 0; i < tabCount; i++) {
/*  549 */       if (this.rects[i].contains(p.x, p.y)) {
/*  550 */         return i;
/*      */       }
/*      */     }
/*  553 */     return -1;
/*      */   }
/*      */   
/*      */   protected Rectangle getTabBounds(int tabIndex, Rectangle dest) {
/*  557 */     dest.width = this.rects[tabIndex].width;
/*  558 */     dest.height = this.rects[tabIndex].height;
/*  559 */     if (scrollableTabLayoutEnabled())
/*      */     {
/*      */ 
/*  562 */       Point vpp = this.tabScroller.viewport.getLocation();
/*  563 */       Point viewp = this.tabScroller.viewport.getViewPosition();
/*  564 */       dest.x = (this.rects[tabIndex].x + vpp.x - viewp.x);
/*  565 */       dest.y = (this.rects[tabIndex].y + vpp.y - viewp.y);
/*      */     } else {
/*  567 */       dest.x = this.rects[tabIndex].x;
/*  568 */       dest.y = this.rects[tabIndex].y;
/*      */     }
/*  570 */     return dest;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getClosestTab(int x, int y)
/*      */   {
/*  578 */     int min = 0;
/*  579 */     int tabCount = Math.min(this.rects.length, this.tabPane.getTabCount());
/*  580 */     int max = tabCount;
/*  581 */     int tabPlacement = this.tabPane.getTabPlacement();
/*  582 */     boolean useX = (tabPlacement == 1) || (tabPlacement == 3);
/*  583 */     int want = useX ? x : y;
/*      */     
/*  585 */     while (min != max) {
/*  586 */       int current = (max + min) / 2;
/*      */       int maxLoc;
/*      */       int minLoc;
/*      */       int maxLoc;
/*  590 */       if (useX) {
/*  591 */         int minLoc = this.rects[current].x;
/*  592 */         maxLoc = minLoc + this.rects[current].width;
/*      */       } else {
/*  594 */         minLoc = this.rects[current].y;
/*  595 */         maxLoc = minLoc + this.rects[current].height;
/*      */       }
/*  597 */       if (want < minLoc) {
/*  598 */         max = current;
/*  599 */         if (min == max) {
/*  600 */           return Math.max(0, current - 1);
/*      */         }
/*  602 */       } else if (want >= maxLoc) {
/*  603 */         min = current;
/*  604 */         if (max - min <= 1) {
/*  605 */           return Math.max(current + 1, tabCount - 1);
/*      */         }
/*      */       } else {
/*  608 */         return current;
/*      */       }
/*      */     }
/*  611 */     return min;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Point translatePointToTabPanel(int srcx, int srcy, Point dest)
/*      */   {
/*  620 */     Point vpp = this.tabScroller.viewport.getLocation();
/*  621 */     Point viewp = this.tabScroller.viewport.getViewPosition();
/*  622 */     dest.x = (srcx - vpp.x + viewp.x);
/*  623 */     dest.y = (srcy - vpp.y + viewp.y);
/*  624 */     return dest;
/*      */   }
/*      */   
/*      */   protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
/*  628 */     int tabCount = this.tabPane.getTabCount();
/*      */     
/*  630 */     Rectangle iconRect = new Rectangle();
/*  631 */     Rectangle textRect = new Rectangle();
/*  632 */     Rectangle clipRect = g.getClipBounds();
/*      */     
/*      */ 
/*  635 */     for (int i = this.runCount - 1; i >= 0; i--) {
/*  636 */       int start = this.tabRuns[i];
/*  637 */       int next = this.tabRuns[(i + 1)];
/*  638 */       int end = next != 0 ? next - 1 : tabCount - 1;
/*  639 */       for (int j = end; j >= start; j--) {
/*  640 */         if ((j != selectedIndex) && (this.rects[j].intersects(clipRect))) {
/*  641 */           paintTab(g, tabPlacement, this.rects, j, iconRect, textRect);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  648 */     if ((selectedIndex >= 0) && (this.rects[selectedIndex].intersects(clipRect))) {
/*  649 */       paintTab(g, tabPlacement, this.rects, selectedIndex, iconRect, textRect);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/*  667 */     textRect.x = (textRect.y = iconRect.x = iconRect.y = 0);
/*      */     
/*  669 */     View v = getTextViewForTab(tabIndex);
/*  670 */     if (v != null) {
/*  671 */       this.tabPane.putClientProperty("html", v);
/*      */     }
/*      */     
/*  674 */     Rectangle calcRectangle = new Rectangle(tabRect);
/*  675 */     if (isSelected) {
/*  676 */       Insets calcInsets = getSelectedTabPadInsets(tabPlacement);
/*  677 */       calcRectangle.x += calcInsets.left;
/*  678 */       calcRectangle.y += calcInsets.top;
/*  679 */       calcRectangle.width -= calcInsets.left + calcInsets.right;
/*  680 */       calcRectangle.height -= calcInsets.bottom + calcInsets.top;
/*      */     }
/*  682 */     int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
/*  683 */     int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
/*  684 */     if (((tabPlacement == 4) || (tabPlacement == 2)) && (icon != null) && (title != null) && (!title.equals(""))) {
/*  685 */       SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 2, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  698 */       xNudge += 4;
/*      */     } else {
/*  700 */       SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 0, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  713 */       iconRect.y += calcRectangle.height % 2;
/*      */     }
/*      */     
/*      */ 
/*  717 */     this.tabPane.putClientProperty("html", null);
/*      */     
/*  719 */     iconRect.x += xNudge;
/*  720 */     iconRect.y += yNudge;
/*  721 */     textRect.x += xNudge;
/*  722 */     textRect.y += yNudge;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Icon getIconForTab(int tabIndex)
/*      */   {
/*  731 */     String title = this.tabPane.getTitleAt(tabIndex);
/*  732 */     boolean hasTitle = (title != null) && (title.length() > 0);
/*  733 */     return (!isTabIconsEnabled) && (hasTitle) ? null : super.getIconForTab(tabIndex);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected LayoutManager createLayoutManager()
/*      */   {
/*  742 */     if (this.tabPane.getTabLayoutPolicy() == 1) {
/*  743 */       return new TabbedPaneScrollLayout(null);
/*      */     }
/*      */     
/*  746 */     return new TabbedPaneLayout(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean scrollableTabLayoutEnabled()
/*      */   {
/*  755 */     return this.tabPane.getLayout() instanceof TabbedPaneScrollLayout;
/*      */   }
/*      */   
/*      */   protected boolean isTabInFirstRun(int tabIndex) {
/*  759 */     return getRunForTab(this.tabPane.getTabCount(), tabIndex) == 0;
/*      */   }
/*      */   
/*      */   protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
/*  763 */     int width = this.tabPane.getWidth();
/*  764 */     int height = this.tabPane.getHeight();
/*  765 */     Insets insets = this.tabPane.getInsets();
/*      */     
/*  767 */     int x = insets.left;
/*  768 */     int y = insets.top;
/*  769 */     int w = width - insets.right - insets.left;
/*  770 */     int h = height - insets.top - insets.bottom;
/*      */     
/*  772 */     switch (tabPlacement) {
/*      */     case 2: 
/*  774 */       x += calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
/*  775 */       w -= x - insets.left;
/*  776 */       break;
/*      */     case 4: 
/*  778 */       w -= calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
/*  779 */       break;
/*      */     case 3: 
/*  781 */       h -= calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
/*  782 */       break;
/*      */     case 1: 
/*      */     default: 
/*  785 */       y += calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
/*  786 */       h -= y - insets.top;
/*      */     }
/*      */     
/*  789 */     g.setColor(this.selectColor == null ? this.tabPane.getBackground() : this.selectColor);
/*      */     
/*      */ 
/*  792 */     g.fillRect(x, y, w, h);
/*      */     
/*      */ 
/*  795 */     Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, this.calcRect);
/*  796 */     boolean drawBroken = (selectedIndex >= 0) && (isTabInFirstRun(selectedIndex));
/*  797 */     boolean isContentBorderPainted = !hasNoContentBorder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  802 */     this.renderer.paintContentBorderTopEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  803 */     this.renderer.paintContentBorderLeftEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  804 */     this.renderer.paintContentBorderBottomEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*  805 */     this.renderer.paintContentBorderRightEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Insets getContentBorderInsets(int tabPlacement)
/*      */   {
/*  816 */     return this.renderer.getContentBorderInsets(super.getContentBorderInsets(tabPlacement));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Insets getTabAreaInsets(int tabPlacement)
/*      */   {
/*  823 */     return this.renderer.getTabAreaInsets(super.getTabAreaInsets(tabPlacement));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected)
/*      */   {
/*  830 */     return this.renderer.getTabLabelShiftX(tabIndex, isSelected);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected)
/*      */   {
/*  837 */     return this.renderer.getTabLabelShiftY(tabIndex, isSelected);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected int getTabRunOverlay(int tabPlacement)
/*      */   {
/*  844 */     return this.renderer.getTabRunOverlay(this.tabRunOverlay);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean shouldPadTabRun(int tabPlacement, int run)
/*      */   {
/*  852 */     return this.renderer.shouldPadTabRun(run, super.shouldPadTabRun(tabPlacement, run));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int getTabRunIndent(int tabPlacement, int run)
/*      */   {
/*  861 */     return this.renderer.getTabRunIndent(run);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Insets getTabInsets(int tabPlacement, int tabIndex)
/*      */   {
/*  868 */     return this.renderer.getTabInsets(tabIndex, this.tabInsets);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Insets getSelectedTabPadInsets(int tabPlacement)
/*      */   {
/*  875 */     return this.renderer.getSelectedTabPadInsets();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rectangles, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */   {
/*  889 */     this.renderer.paintFocusIndicator(g, rectangles, tabIndex, iconRect, textRect, isSelected);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*  898 */     this.renderer.paintTabBackground(g, tabIndex, x, y, w, h, isSelected);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */   {
/*  907 */     this.renderer.paintTabBorder(g, tabIndex, x, y, w, h, isSelected);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  916 */   protected boolean shouldRotateTabRuns(int tabPlacement) { return false; }
/*      */   
/*      */   private final class TabSelectionHandler implements ChangeListener {
/*      */     private TabSelectionHandler() {}
/*      */     
/*  921 */     private final Rectangle rect = new Rectangle();
/*      */     
/*      */     public void stateChanged(ChangeEvent e) {
/*  924 */       JTabbedPane tabPane = (JTabbedPane)e.getSource();
/*  925 */       tabPane.revalidate();
/*  926 */       tabPane.repaint();
/*      */       
/*  928 */       if (tabPane.getTabLayoutPolicy() == 1) {
/*  929 */         int index = tabPane.getSelectedIndex();
/*  930 */         if ((index < PlasticTabbedPaneUI.this.rects.length) && (index != -1)) {
/*  931 */           this.rect.setBounds(PlasticTabbedPaneUI.this.rects[index]);
/*  932 */           Point viewPosition = PlasticTabbedPaneUI.this.tabScroller.viewport.getViewPosition();
/*  933 */           if (this.rect.x < viewPosition.x) {
/*  934 */             this.rect.x -= PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*      */           } else {
/*  936 */             this.rect.x += PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*      */           }
/*  938 */           PlasticTabbedPaneUI.this.tabScroller.tabPanel.scrollRectToVisible(this.rect);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final class MyPropertyChangeHandler
/*      */     extends BasicTabbedPaneUI.PropertyChangeHandler
/*      */   {
/*  949 */     private MyPropertyChangeHandler() { super(); }
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent e) {
/*  952 */       String pName = e.getPropertyName();
/*      */       
/*  954 */       if (null == pName) {
/*  955 */         return;
/*      */       }
/*      */       
/*  958 */       super.propertyChange(e);
/*      */       
/*  960 */       if (pName.equals("tabPlacement")) {
/*  961 */         PlasticTabbedPaneUI.this.tabPlacementChanged();
/*  962 */         return;
/*      */       }
/*  964 */       if (pName.equals("jgoodies.embeddedTabs")) {
/*  965 */         PlasticTabbedPaneUI.this.embeddedTabsPropertyChanged((Boolean)e.getNewValue());
/*  966 */         return;
/*      */       }
/*  968 */       if (pName.equals("jgoodies.noContentBorder")) {
/*  969 */         PlasticTabbedPaneUI.this.noContentBorderPropertyChanged((Boolean)e.getNewValue());
/*  970 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private class TabbedPaneLayout
/*      */     extends BasicTabbedPaneUI.TabbedPaneLayout
/*      */   {
/*  979 */     private TabbedPaneLayout() { super(); }
/*      */     
/*      */     protected void calculateTabRects(int tabPlacement, int tabCount) {
/*  982 */       FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
/*  983 */       Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
/*  984 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/*  985 */       Insets theTabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/*  986 */       int fontHeight = metrics.getHeight();
/*  987 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  992 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/*  993 */       boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
/*      */       
/*      */       int x;
/*      */       int y;
/*      */       int returnAt;
/*  998 */       switch (tabPlacement) {
/*      */       case 2: 
/* 1000 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 1001 */         x = insets.left + theTabAreaInsets.left;
/* 1002 */         y = insets.top + theTabAreaInsets.top;
/* 1003 */         returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
/* 1004 */         break;
/*      */       case 4: 
/* 1006 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 1007 */         x = size.width - insets.right - theTabAreaInsets.right - PlasticTabbedPaneUI.this.maxTabWidth;
/* 1008 */         y = insets.top + theTabAreaInsets.top;
/* 1009 */         returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
/* 1010 */         break;
/*      */       case 3: 
/* 1012 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 1013 */         x = insets.left + theTabAreaInsets.left;
/* 1014 */         y = size.height - insets.bottom - theTabAreaInsets.bottom - PlasticTabbedPaneUI.this.maxTabHeight;
/* 1015 */         returnAt = size.width - (insets.right + theTabAreaInsets.right);
/* 1016 */         break;
/*      */       case 1: 
/*      */       default: 
/* 1019 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/* 1020 */         x = insets.left + theTabAreaInsets.left;
/* 1021 */         y = insets.top + theTabAreaInsets.top;
/* 1022 */         returnAt = size.width - (insets.right + theTabAreaInsets.right);
/*      */       }
/*      */       
/*      */       
/* 1026 */       int theTabRunOverlay = PlasticTabbedPaneUI.this.getTabRunOverlay(tabPlacement);
/*      */       
/* 1028 */       PlasticTabbedPaneUI.this.runCount = 0;
/* 1029 */       PlasticTabbedPaneUI.this.selectedRun = -1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1034 */       int tabInRun = -1;
/*      */       
/*      */ 
/* 1037 */       int runReturnAt = returnAt;
/*      */       
/* 1039 */       if (tabCount == 0) {
/* 1040 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1045 */       for (int i = 0; i < tabCount; i++) {
/* 1046 */         Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
/* 1047 */         tabInRun++;
/*      */         
/* 1049 */         if (!verticalTabRuns)
/*      */         {
/* 1051 */           if (i > 0) {
/* 1052 */             rect.x = (PlasticTabbedPaneUI.this.rects[(i - 1)].x + PlasticTabbedPaneUI.this.rects[(i - 1)].width);
/*      */           } else {
/* 1054 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1055 */             PlasticTabbedPaneUI.this.runCount = 1;
/* 1056 */             PlasticTabbedPaneUI.this.maxTabWidth = 0;
/* 1057 */             rect.x = x;
/*      */           }
/*      */           
/* 1060 */           rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/* 1061 */           PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1069 */           if ((tabInRun != 0) && (rect.x + rect.width > runReturnAt)) {
/* 1070 */             if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1) {
/* 1071 */               PlasticTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/*      */             
/* 1074 */             tabInRun = 0;
/* 1075 */             PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
/* 1076 */             PlasticTabbedPaneUI.access$4608(PlasticTabbedPaneUI.this);
/* 1077 */             rect.x = x;
/* 1078 */             runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
/*      */           }
/*      */           
/* 1081 */           rect.y = y;
/* 1082 */           rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
/*      */         }
/*      */         else
/*      */         {
/* 1086 */           if (i > 0) {
/* 1087 */             rect.y = (PlasticTabbedPaneUI.this.rects[(i - 1)].y + PlasticTabbedPaneUI.this.rects[(i - 1)].height);
/*      */           } else {
/* 1089 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1090 */             PlasticTabbedPaneUI.this.runCount = 1;
/* 1091 */             PlasticTabbedPaneUI.this.maxTabHeight = 0;
/* 1092 */             rect.y = y;
/*      */           }
/*      */           
/* 1095 */           rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
/* 1096 */           PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1102 */           if ((tabInRun != 0) && (rect.y + rect.height > runReturnAt)) {
/* 1103 */             if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1) {
/* 1104 */               PlasticTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/* 1106 */             PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
/* 1107 */             PlasticTabbedPaneUI.access$6208(PlasticTabbedPaneUI.this);
/* 1108 */             rect.y = y;
/* 1109 */             tabInRun = 0;
/* 1110 */             runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
/*      */           }
/*      */           
/* 1113 */           rect.x = x;
/* 1114 */           rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
/*      */         }
/*      */         
/* 1117 */         if (i == selectedIndex) {
/* 1118 */           PlasticTabbedPaneUI.this.selectedRun = (PlasticTabbedPaneUI.this.runCount - 1);
/*      */         }
/*      */       }
/*      */       
/* 1122 */       if (PlasticTabbedPaneUI.this.runCount > 1)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1130 */         if (PlasticTabbedPaneUI.this.shouldRotateTabRuns(tabPlacement)) {
/* 1131 */           rotateTabRuns(tabPlacement, PlasticTabbedPaneUI.this.selectedRun);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1137 */       for (i = PlasticTabbedPaneUI.this.runCount - 1; i >= 0; i--) {
/* 1138 */         int start = PlasticTabbedPaneUI.this.tabRuns[i];
/* 1139 */         int next = PlasticTabbedPaneUI.this.tabRuns[(i + 1)];
/* 1140 */         int end = next != 0 ? next - 1 : tabCount - 1;
/* 1141 */         int indent = PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, i);
/* 1142 */         if (!verticalTabRuns) {
/* 1143 */           for (int j = start; j <= end; j++) {
/* 1144 */             Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
/* 1145 */             rect.y = y;
/* 1146 */             rect.x += indent;
/*      */           }
/*      */           
/*      */ 
/* 1150 */           if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/* 1151 */             padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
/*      */           }
/* 1153 */           if (tabPlacement == 3) {
/* 1154 */             y -= PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
/*      */           } else {
/* 1156 */             y += PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
/*      */           }
/*      */         } else {
/* 1159 */           for (int j = start; j <= end; j++) {
/* 1160 */             Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
/* 1161 */             rect.x = x;
/* 1162 */             rect.y += indent;
/*      */           }
/* 1164 */           if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i)) {
/* 1165 */             padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
/*      */           }
/* 1167 */           if (tabPlacement == 4) {
/* 1168 */             x -= PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
/*      */           } else {
/* 1170 */             x += PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1176 */       padSelectedTab(tabPlacement, selectedIndex);
/*      */       
/*      */ 
/*      */ 
/* 1180 */       if ((!leftToRight) && (!verticalTabRuns)) {
/* 1181 */         int rightMargin = size.width - (insets.right + theTabAreaInsets.right);
/* 1182 */         for (i = 0; i < tabCount; i++) {
/* 1183 */           PlasticTabbedPaneUI.this.rects[i].x = (rightMargin - PlasticTabbedPaneUI.this.rects[i].x - PlasticTabbedPaneUI.this.rects[i].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void padSelectedTab(int tabPlacement, int selectedIndex)
/*      */     {
/* 1193 */       if (selectedIndex >= 0) {
/* 1194 */         Rectangle selRect = PlasticTabbedPaneUI.this.rects[selectedIndex];
/* 1195 */         Insets padInsets = PlasticTabbedPaneUI.this.getSelectedTabPadInsets(tabPlacement);
/* 1196 */         selRect.x -= padInsets.left;
/* 1197 */         selRect.width += padInsets.left + padInsets.right;
/* 1198 */         selRect.y -= padInsets.top;
/* 1199 */         selRect.height += padInsets.top + padInsets.bottom;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean requestFocusForVisibleComponent()
/*      */   {
/* 1207 */     Component visibleComponent = getVisibleComponent();
/* 1208 */     if (visibleComponent.isFocusable()) {
/* 1209 */       visibleComponent.requestFocus();
/* 1210 */       return true;
/*      */     }
/* 1212 */     if (((visibleComponent instanceof JComponent)) && 
/* 1213 */       (((JComponent)visibleComponent).requestDefaultFocus())) {
/* 1214 */       return true;
/*      */     }
/*      */     
/* 1217 */     return false;
/*      */   }
/*      */   
/*      */   private static class ScrollTabsForwardAction extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent e) {
/* 1223 */       JTabbedPane pane = null;
/* 1224 */       Object src = e.getSource();
/* 1225 */       if ((src instanceof JTabbedPane)) {
/* 1226 */         pane = (JTabbedPane)src;
/* 1227 */       } else if ((src instanceof PlasticArrowButton)) {
/* 1228 */         pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
/*      */       } else {
/* 1230 */         return;
/*      */       }
/* 1232 */       PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
/*      */       
/* 1234 */       if (ui.scrollableTabLayoutEnabled()) {
/* 1235 */         ui.tabScroller.scrollForward(pane.getTabPlacement());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ScrollTabsBackwardAction extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent e) {
/* 1243 */       JTabbedPane pane = null;
/* 1244 */       Object src = e.getSource();
/* 1245 */       if ((src instanceof JTabbedPane)) {
/* 1246 */         pane = (JTabbedPane)src;
/* 1247 */       } else if ((src instanceof PlasticArrowButton)) {
/* 1248 */         pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
/*      */       } else {
/* 1250 */         return;
/*      */       }
/* 1252 */       PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
/*      */       
/* 1254 */       if (ui.scrollableTabLayoutEnabled())
/* 1255 */         ui.tabScroller.scrollBackward(pane.getTabPlacement());
/*      */     }
/*      */   }
/*      */   
/*      */   private final class TabbedPaneScrollLayout extends PlasticTabbedPaneUI.TabbedPaneLayout {
/* 1260 */     private TabbedPaneScrollLayout() { super(null); }
/*      */     
/*      */     protected int preferredTabAreaHeight(int tabPlacement, int width) {
/* 1263 */       return PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*      */     }
/*      */     
/*      */     protected int preferredTabAreaWidth(int tabPlacement, int height) {
/* 1267 */       return PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container parent) {
/* 1271 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1272 */       int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
/* 1273 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/* 1274 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 1275 */       Component visibleComponent = PlasticTabbedPaneUI.this.getVisibleComponent();
/*      */       
/* 1277 */       calculateLayoutInfo();
/*      */       
/* 1279 */       if (selectedIndex < 0) {
/* 1280 */         if (visibleComponent != null)
/*      */         {
/* 1282 */           PlasticTabbedPaneUI.this.setVisibleComponent(null);
/*      */         }
/*      */       } else {
/* 1285 */         Component selectedComponent = PlasticTabbedPaneUI.this.tabPane.getComponentAt(selectedIndex);
/* 1286 */         boolean shouldChangeFocus = false;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1295 */         if (selectedComponent != null) {
/* 1296 */           if ((selectedComponent != visibleComponent) && (visibleComponent != null))
/*      */           {
/* 1298 */             if (SwingUtilities.findFocusOwner(visibleComponent) != null) {
/* 1299 */               shouldChangeFocus = true;
/*      */             }
/*      */           }
/* 1302 */           PlasticTabbedPaneUI.this.setVisibleComponent(selectedComponent);
/*      */         }
/*      */         
/*      */ 
/* 1306 */         Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 1307 */         Rectangle bounds = PlasticTabbedPaneUI.this.tabPane.getBounds();
/* 1308 */         int numChildren = PlasticTabbedPaneUI.this.tabPane.getComponentCount();
/*      */         
/* 1310 */         if (numChildren > 0) { int tw;
/* 1311 */           int th; int tx; int ty; int cx; int cy; int cw; int ch; switch (tabPlacement)
/*      */           {
/*      */           case 2: 
/* 1314 */             tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
/*      */             
/* 1316 */             th = bounds.height - insets.top - insets.bottom;
/* 1317 */             tx = insets.left;
/* 1318 */             ty = insets.top;
/*      */             
/*      */ 
/* 1321 */             cx = tx + tw + contentInsets.left;
/* 1322 */             cy = ty + contentInsets.top;
/* 1323 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/*      */             
/* 1325 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/*      */             
/* 1327 */             break;
/*      */           
/*      */           case 4: 
/* 1330 */             tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
/*      */             
/* 1332 */             th = bounds.height - insets.top - insets.bottom;
/* 1333 */             tx = bounds.width - insets.right - tw;
/* 1334 */             ty = insets.top;
/*      */             
/*      */ 
/* 1337 */             cx = insets.left + contentInsets.left;
/* 1338 */             cy = insets.top + contentInsets.top;
/* 1339 */             cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
/*      */             
/* 1341 */             ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
/*      */             
/* 1343 */             break;
/*      */           
/*      */           case 3: 
/* 1346 */             tw = bounds.width - insets.left - insets.right;
/* 1347 */             th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
/*      */             
/* 1349 */             tx = insets.left;
/* 1350 */             ty = bounds.height - insets.bottom - th;
/*      */             
/*      */ 
/* 1353 */             cx = insets.left + contentInsets.left;
/* 1354 */             cy = insets.top + contentInsets.top;
/* 1355 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/*      */             
/* 1357 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/*      */             
/* 1359 */             break;
/*      */           
/*      */           case 1: 
/*      */           default: 
/* 1363 */             tw = bounds.width - insets.left - insets.right;
/* 1364 */             th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
/*      */             
/* 1366 */             tx = insets.left;
/* 1367 */             ty = insets.top;
/*      */             
/*      */ 
/* 1370 */             cx = tx + contentInsets.left;
/* 1371 */             cy = ty + th + contentInsets.top;
/* 1372 */             cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
/*      */             
/* 1374 */             ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
/*      */           }
/*      */           
/*      */           
/* 1378 */           for (int i = 0; i < numChildren; i++) {
/* 1379 */             Component child = PlasticTabbedPaneUI.this.tabPane.getComponent(i);
/*      */             
/* 1381 */             if ((PlasticTabbedPaneUI.this.tabScroller != null) && (child == PlasticTabbedPaneUI.this.tabScroller.viewport)) {
/* 1382 */               JViewport viewport = (JViewport)child;
/* 1383 */               Rectangle viewRect = viewport.getViewRect();
/* 1384 */               int vw = tw;
/* 1385 */               int vh = th;
/* 1386 */               Dimension butSize = PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton.getPreferredSize();
/* 1387 */               switch (tabPlacement) {
/*      */               case 2: 
/*      */               case 4: 
/* 1390 */                 int totalTabHeight = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].y + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].height;
/*      */                 
/* 1392 */                 if (totalTabHeight > th)
/*      */                 {
/* 1394 */                   vh = th > 2 * butSize.height ? th - 2 * butSize.height : 0;
/*      */                   
/* 1396 */                   if (totalTabHeight - viewRect.y <= vh)
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/* 1401 */                     vh = totalTabHeight - viewRect.y;
/*      */                   }
/*      */                 }
/*      */                 break;
/*      */               case 1: 
/*      */               case 3: 
/*      */               default: 
/* 1408 */                 int totalTabWidth = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].x + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*      */                 
/* 1410 */                 if (totalTabWidth > tw)
/*      */                 {
/* 1412 */                   vw = tw > 2 * butSize.width ? tw - 2 * butSize.width : 0;
/*      */                   
/* 1414 */                   if (totalTabWidth - viewRect.x <= vw)
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/* 1419 */                     vw = totalTabWidth - viewRect.x; }
/*      */                 }
/*      */                 break;
/*      */               }
/* 1423 */               child.setBounds(tx, ty, vw, vh);
/*      */             }
/* 1425 */             else if ((PlasticTabbedPaneUI.this.tabScroller != null) && ((child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton) || (child == PlasticTabbedPaneUI.this.tabScroller.scrollBackwardButton)))
/*      */             {
/*      */ 
/* 1428 */               Component scrollbutton = child;
/* 1429 */               Dimension bsize = scrollbutton.getPreferredSize();
/* 1430 */               int bx = 0;
/* 1431 */               int by = 0;
/* 1432 */               int bw = bsize.width;
/* 1433 */               int bh = bsize.height;
/* 1434 */               boolean visible = false;
/*      */               
/* 1436 */               switch (tabPlacement) {
/*      */               case 2: 
/*      */               case 4: 
/* 1439 */                 int totalTabHeight = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].y + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].height + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*      */                 
/*      */ 
/* 1442 */                 if (totalTabHeight > th) {
/* 1443 */                   visible = true;
/* 1444 */                   bx = tabPlacement == 2 ? tx + tw - bsize.width : tx;
/*      */                   
/* 1446 */                   by = child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton ? bounds.height - insets.bottom - bsize.height : bounds.height - insets.bottom - 2 * bsize.height;
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */                 break;
/*      */               case 1: 
/*      */               case 3: 
/*      */               default: 
/* 1456 */                 int totalTabWidth = PlasticTabbedPaneUI.this.rects[(tabCount - 1)].x + PlasticTabbedPaneUI.this.rects[(tabCount - 1)].width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/*      */                 
/*      */ 
/*      */ 
/* 1460 */                 if (totalTabWidth > tw) {
/* 1461 */                   visible = true;
/* 1462 */                   bx = child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton ? bounds.width - insets.left - bsize.width : bounds.width - insets.left - 2 * bsize.width;
/*      */                   
/*      */ 
/*      */ 
/* 1466 */                   by = tabPlacement == 1 ? ty + th - bsize.height : ty;
/*      */                 }
/*      */                 break;
/*      */               }
/* 1470 */               child.setVisible(visible);
/* 1471 */               if (visible) {
/* 1472 */                 child.setBounds(bx, by, bw, bh);
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 1477 */               child.setBounds(cx, cy, cw, ch);
/*      */             }
/*      */           }
/* 1480 */           if ((shouldChangeFocus) && 
/* 1481 */             (!PlasticTabbedPaneUI.this.requestFocusForVisibleComponent())) {
/* 1482 */             PlasticTabbedPaneUI.this.tabPane.requestFocus();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     protected void calculateTabRects(int tabPlacement, int tabCount)
/*      */     {
/* 1490 */       FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
/* 1491 */       Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
/* 1492 */       Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
/* 1493 */       Insets tabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
/* 1494 */       int fontHeight = metrics.getHeight();
/* 1495 */       int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 1496 */       boolean verticalTabRuns = (tabPlacement == 2) || (tabPlacement == 4);
/* 1497 */       boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
/* 1498 */       int x = tabAreaInsets.left;
/* 1499 */       int y = tabAreaInsets.top;
/* 1500 */       int totalWidth = 0;
/* 1501 */       int totalHeight = 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1506 */       switch (tabPlacement) {
/*      */       case 2: 
/*      */       case 4: 
/* 1509 */         PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
/* 1510 */         break;
/*      */       case 1: 
/*      */       case 3: 
/*      */       default: 
/* 1514 */         PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
/*      */       }
/*      */       
/* 1517 */       PlasticTabbedPaneUI.this.runCount = 0;
/* 1518 */       PlasticTabbedPaneUI.this.selectedRun = -1;
/*      */       
/* 1520 */       if (tabCount == 0) {
/* 1521 */         return;
/*      */       }
/*      */       
/* 1524 */       PlasticTabbedPaneUI.this.selectedRun = 0;
/* 1525 */       PlasticTabbedPaneUI.this.runCount = 1;
/*      */       
/*      */ 
/*      */ 
/* 1529 */       for (int i = 0; i < tabCount; i++) {
/* 1530 */         Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
/*      */         
/* 1532 */         if (!verticalTabRuns)
/*      */         {
/* 1534 */           if (i > 0) {
/* 1535 */             rect.x = (PlasticTabbedPaneUI.this.rects[(i - 1)].x + PlasticTabbedPaneUI.this.rects[(i - 1)].width);
/*      */           } else {
/* 1537 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1538 */             PlasticTabbedPaneUI.this.maxTabWidth = 0;
/* 1539 */             totalHeight += PlasticTabbedPaneUI.this.maxTabHeight;
/* 1540 */             rect.x = x;
/*      */           }
/* 1542 */           rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
/* 1543 */           totalWidth = rect.x + rect.width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
/* 1544 */           PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
/*      */           
/* 1546 */           rect.y = y;
/* 1547 */           rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
/*      */         }
/*      */         else
/*      */         {
/* 1551 */           if (i > 0) {
/* 1552 */             rect.y = (PlasticTabbedPaneUI.this.rects[(i - 1)].y + PlasticTabbedPaneUI.this.rects[(i - 1)].height);
/*      */           } else {
/* 1554 */             PlasticTabbedPaneUI.this.tabRuns[0] = 0;
/* 1555 */             PlasticTabbedPaneUI.this.maxTabHeight = 0;
/* 1556 */             totalWidth = PlasticTabbedPaneUI.this.maxTabWidth;
/* 1557 */             rect.y = y;
/*      */           }
/* 1559 */           rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
/* 1560 */           totalHeight = rect.y + rect.height;
/* 1561 */           PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
/*      */           
/* 1563 */           rect.x = x;
/* 1564 */           rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1570 */       padSelectedTab(tabPlacement, selectedIndex);
/*      */       
/*      */ 
/*      */ 
/* 1574 */       if ((!leftToRight) && (!verticalTabRuns)) {
/* 1575 */         int rightMargin = size.width - (insets.right + tabAreaInsets.right);
/*      */         
/* 1577 */         for (int i = 0; i < tabCount; i++) {
/* 1578 */           PlasticTabbedPaneUI.this.rects[i].x = (rightMargin - PlasticTabbedPaneUI.this.rects[i].x - PlasticTabbedPaneUI.this.rects[i].width);
/*      */         }
/*      */       }
/* 1581 */       PlasticTabbedPaneUI.this.tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ScrollableTabSupport
/*      */     implements ActionListener, ChangeListener
/*      */   {
/*      */     public PlasticTabbedPaneUI.ScrollableTabViewport viewport;
/*      */     public PlasticTabbedPaneUI.ScrollableTabPanel tabPanel;
/*      */     public JButton scrollForwardButton;
/*      */     public JButton scrollBackwardButton;
/*      */     public int leadingTabIndex;
/* 1593 */     private final Point tabViewPosition = new Point(0, 0);
/*      */     
/*      */     ScrollableTabSupport(int tabPlacement) {
/* 1596 */       this.viewport = new PlasticTabbedPaneUI.ScrollableTabViewport(PlasticTabbedPaneUI.this);
/* 1597 */       this.tabPanel = new PlasticTabbedPaneUI.ScrollableTabPanel(PlasticTabbedPaneUI.this);
/* 1598 */       this.viewport.setView(this.tabPanel);
/* 1599 */       this.viewport.addChangeListener(this);
/* 1600 */       createButtons();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void createButtons()
/*      */     {
/* 1607 */       if (this.scrollForwardButton != null) {
/* 1608 */         PlasticTabbedPaneUI.this.tabPane.remove(this.scrollForwardButton);
/* 1609 */         this.scrollForwardButton.removeActionListener(this);
/* 1610 */         PlasticTabbedPaneUI.this.tabPane.remove(this.scrollBackwardButton);
/* 1611 */         this.scrollBackwardButton.removeActionListener(this);
/*      */       }
/* 1613 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1614 */       int width = UIManager.getInt("ScrollBar.width");
/* 1615 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1616 */         this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(3, width);
/* 1617 */         this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(7, width);
/*      */       } else {
/* 1619 */         this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(5, width);
/* 1620 */         this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(1, width);
/*      */       }
/* 1622 */       this.scrollForwardButton.addActionListener(this);
/* 1623 */       this.scrollBackwardButton.addActionListener(this);
/* 1624 */       PlasticTabbedPaneUI.this.tabPane.add(this.scrollForwardButton);
/* 1625 */       PlasticTabbedPaneUI.this.tabPane.add(this.scrollBackwardButton);
/*      */     }
/*      */     
/*      */     public void scrollForward(int tabPlacement) {
/* 1629 */       Dimension viewSize = this.viewport.getViewSize();
/* 1630 */       Rectangle viewRect = this.viewport.getViewRect();
/*      */       
/* 1632 */       if ((tabPlacement == 1) || (tabPlacement == 3)) {
/* 1633 */         if (viewRect.width < viewSize.width - viewRect.x) {}
/*      */ 
/*      */ 
/*      */       }
/* 1637 */       else if (viewRect.height >= viewSize.height - viewRect.y) {
/* 1638 */         return;
/*      */       }
/*      */       
/* 1641 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex + 1);
/*      */     }
/*      */     
/*      */     public void scrollBackward(int tabPlacement) {
/* 1645 */       if (this.leadingTabIndex == 0) {
/* 1646 */         return;
/*      */       }
/* 1648 */       setLeadingTabIndex(tabPlacement, this.leadingTabIndex - 1);
/*      */     }
/*      */     
/*      */     public void setLeadingTabIndex(int tabPlacement, int index) {
/* 1652 */       this.leadingTabIndex = index;
/* 1653 */       Dimension viewSize = this.viewport.getViewSize();
/* 1654 */       Rectangle viewRect = this.viewport.getViewRect();
/*      */       
/* 1656 */       switch (tabPlacement) {
/*      */       case 1: 
/*      */       case 3: 
/* 1659 */         this.tabViewPosition.x = (this.leadingTabIndex == 0 ? 0 : PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].x - PlasticTabbedPaneUI.this.renderer.getTabsOverlay());
/*      */         
/*      */ 
/* 1662 */         if (viewSize.width - this.tabViewPosition.x < viewRect.width)
/*      */         {
/*      */ 
/*      */ 
/* 1666 */           Dimension extentSize = new Dimension(viewSize.width - this.tabViewPosition.x, viewRect.height);
/*      */           
/* 1668 */           this.viewport.setExtentSize(extentSize); }
/* 1669 */         break;
/*      */       
/*      */       case 2: 
/*      */       case 4: 
/* 1673 */         this.tabViewPosition.y = (this.leadingTabIndex == 0 ? 0 : PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].y);
/*      */         
/*      */ 
/* 1676 */         if (viewSize.height - this.tabViewPosition.y < viewRect.height)
/*      */         {
/*      */ 
/*      */ 
/* 1680 */           Dimension extentSize = new Dimension(viewRect.width, viewSize.height - this.tabViewPosition.y);
/*      */           
/* 1682 */           this.viewport.setExtentSize(extentSize);
/*      */         }
/*      */         break; }
/* 1685 */       this.viewport.setViewPosition(this.tabViewPosition);
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent e) {
/* 1689 */       JViewport viewport = (JViewport)e.getSource();
/* 1690 */       int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
/* 1691 */       int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
/* 1692 */       Rectangle vpRect = viewport.getBounds();
/* 1693 */       Dimension viewSize = viewport.getViewSize();
/* 1694 */       Rectangle viewRect = viewport.getViewRect();
/*      */       
/* 1696 */       this.leadingTabIndex = PlasticTabbedPaneUI.this.getClosestTab(viewRect.x, viewRect.y);
/*      */       
/*      */ 
/* 1699 */       if (this.leadingTabIndex + 1 < tabCount) {
/* 1700 */         switch (tabPlacement) {
/*      */         case 1: 
/*      */         case 3: 
/* 1703 */           if (PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].x < viewRect.x) {
/* 1704 */             this.leadingTabIndex += 1;
/*      */           }
/*      */           break;
/*      */         case 2: 
/*      */         case 4: 
/* 1709 */           if (PlasticTabbedPaneUI.this.rects[this.leadingTabIndex].y < viewRect.y) {
/* 1710 */             this.leadingTabIndex += 1;
/*      */           }
/*      */           break;
/*      */         }
/*      */       }
/* 1715 */       Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
/* 1716 */       switch (tabPlacement) {
/*      */       case 2: 
/* 1718 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x + vpRect.width, vpRect.y, contentInsets.left, vpRect.height);
/*      */         
/* 1720 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) && (this.leadingTabIndex > 0));
/*      */         
/* 1722 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/*      */         
/* 1724 */         break;
/*      */       case 4: 
/* 1726 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y, contentInsets.right, vpRect.height);
/*      */         
/* 1728 */         this.scrollBackwardButton.setEnabled((viewRect.y > 0) && (this.leadingTabIndex > 0));
/*      */         
/* 1730 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.height - viewRect.y > viewRect.height));
/*      */         
/* 1732 */         break;
/*      */       case 3: 
/* 1734 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom, vpRect.width, contentInsets.bottom);
/*      */         
/* 1736 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) && (this.leadingTabIndex > 0));
/*      */         
/* 1738 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - viewRect.x > viewRect.width));
/*      */         
/* 1740 */         break;
/*      */       case 1: 
/*      */       default: 
/* 1743 */         PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y + vpRect.height, vpRect.width, contentInsets.top);
/*      */         
/* 1745 */         this.scrollBackwardButton.setEnabled((viewRect.x > 0) && (this.leadingTabIndex > 0));
/*      */         
/* 1747 */         this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1) && (viewSize.width - viewRect.x > viewRect.width));
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void actionPerformed(ActionEvent e)
/*      */     {
/* 1756 */       ActionMap map = PlasticTabbedPaneUI.this.tabPane.getActionMap();
/*      */       
/* 1758 */       if (map != null) {
/*      */         String actionKey;
/*      */         String actionKey;
/* 1761 */         if (e.getSource() == this.scrollForwardButton) {
/* 1762 */           actionKey = "scrollTabsForwardAction";
/*      */         } else {
/* 1764 */           actionKey = "scrollTabsBackwardAction";
/*      */         }
/* 1766 */         Action action = map.get(actionKey);
/*      */         
/* 1768 */         if ((action != null) && (action.isEnabled())) {
/* 1769 */           action.actionPerformed(new ActionEvent(PlasticTabbedPaneUI.this.tabPane, 1001, null, e.getWhen(), e.getModifiers()));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final class ScrollableTabViewport
/*      */     extends JViewport
/*      */     implements UIResource
/*      */   {
/*      */     public ScrollableTabViewport()
/*      */     {
/* 1782 */       setName("TabbedPane.scrollableViewport");
/* 1783 */       setScrollMode(0);
/* 1784 */       setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
/* 1785 */       Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 1786 */       if (bgColor == null) {
/* 1787 */         bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground();
/*      */       }
/* 1789 */       setBackground(bgColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ScrollableTabPanel extends JPanel implements UIResource
/*      */   {
/*      */     public ScrollableTabPanel() {
/* 1796 */       super();
/* 1797 */       setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
/* 1798 */       Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 1799 */       if (bgColor == null) {
/* 1800 */         bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground();
/*      */       }
/* 1802 */       setBackground(bgColor);
/*      */     }
/*      */     
/*      */     public void paintComponent(Graphics g) {
/* 1806 */       super.paintComponent(g);
/* 1807 */       PlasticTabbedPaneUI.this.paintTabArea(g, PlasticTabbedPaneUI.this.tabPane.getTabPlacement(), PlasticTabbedPaneUI.this.tabPane.getSelectedIndex());
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class ArrowButton
/*      */     extends JButton implements UIResource
/*      */   {
/*      */     private final int buttonWidth;
/*      */     private final int direction;
/*      */     private boolean mouseIsOver;
/*      */     
/*      */     ArrowButton(int direction, int buttonWidth)
/*      */     {
/* 1820 */       this.direction = direction;
/* 1821 */       this.buttonWidth = buttonWidth;
/* 1822 */       setRequestFocusEnabled(false);
/*      */     }
/*      */     
/*      */     protected void processMouseEvent(MouseEvent e) {
/* 1826 */       super.processMouseEvent(e);
/* 1827 */       switch (e.getID()) {
/*      */       case 504: 
/* 1829 */         this.mouseIsOver = true;
/* 1830 */         revalidate();
/* 1831 */         repaint();
/* 1832 */         break;
/*      */       case 505: 
/* 1834 */         this.mouseIsOver = false;
/* 1835 */         revalidate();
/* 1836 */         repaint();
/*      */       }
/*      */     }
/*      */     
/*      */     protected void paintBorder(Graphics g)
/*      */     {
/* 1842 */       if ((this.mouseIsOver) && (isEnabled())) {
/* 1843 */         super.paintBorder(g);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void paintComponent(Graphics g) {
/* 1848 */       if (this.mouseIsOver) {
/* 1849 */         super.paintComponent(g);
/*      */       } else {
/* 1851 */         g.setColor(getBackground());
/* 1852 */         g.fillRect(0, 0, getWidth(), getHeight());
/*      */       }
/* 1854 */       paintArrow(g);
/*      */     }
/*      */     
/*      */     private void paintArrow(Graphics g) {
/* 1858 */       Color oldColor = g.getColor();
/*      */       
/* 1860 */       boolean isEnabled = isEnabled();
/* 1861 */       g.setColor(isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled());
/*      */       
/*      */       int arrowWidth;
/*      */       int arrowHeight;
/* 1865 */       switch (this.direction) {
/*      */       case 1: 
/*      */       case 5: 
/* 1868 */         arrowWidth = 9;
/* 1869 */         arrowHeight = 5;
/* 1870 */         break;
/*      */       case 2: case 3: 
/*      */       case 4: case 6: 
/*      */       case 7: default: 
/* 1874 */         arrowWidth = 5;
/* 1875 */         arrowHeight = 9;
/*      */       }
/*      */       
/* 1878 */       int x = (getWidth() - arrowWidth) / 2;
/* 1879 */       int y = (getHeight() - arrowHeight) / 2;
/* 1880 */       g.translate(x, y);
/*      */       
/* 1882 */       boolean paintShadow = (!this.mouseIsOver) || (!isEnabled);
/* 1883 */       Color shadow = isEnabled ? PlasticLookAndFeel.getControlShadow() : UIManager.getColor("ScrollBar.highlight");
/*      */       
/*      */ 
/* 1886 */       switch (this.direction) {
/*      */       case 1: 
/* 1888 */         g.fillRect(0, 4, 9, 1);
/* 1889 */         g.fillRect(1, 3, 7, 1);
/* 1890 */         g.fillRect(2, 2, 5, 1);
/* 1891 */         g.fillRect(3, 1, 3, 1);
/* 1892 */         g.fillRect(4, 0, 1, 1);
/* 1893 */         if (paintShadow) {
/* 1894 */           g.setColor(shadow);
/* 1895 */           g.fillRect(1, 5, 9, 1);
/*      */         }
/*      */         break;
/*      */       case 5: 
/* 1899 */         g.fillRect(0, 0, 9, 1);
/* 1900 */         g.fillRect(1, 1, 7, 1);
/* 1901 */         g.fillRect(2, 2, 5, 1);
/* 1902 */         g.fillRect(3, 3, 3, 1);
/* 1903 */         g.fillRect(4, 4, 1, 1);
/* 1904 */         if (paintShadow) {
/* 1905 */           g.setColor(shadow);
/* 1906 */           g.drawLine(5, 4, 8, 1);
/* 1907 */           g.drawLine(5, 5, 9, 1);
/*      */         }
/*      */         break;
/*      */       case 7: 
/* 1911 */         g.fillRect(0, 4, 1, 1);
/* 1912 */         g.fillRect(1, 3, 1, 3);
/* 1913 */         g.fillRect(2, 2, 1, 5);
/* 1914 */         g.fillRect(3, 1, 1, 7);
/* 1915 */         g.fillRect(4, 0, 1, 9);
/* 1916 */         if (paintShadow) {
/* 1917 */           g.setColor(shadow);
/* 1918 */           g.fillRect(5, 1, 1, 9);
/*      */         }
/*      */         break;
/*      */       case 3: 
/* 1922 */         g.fillRect(0, 0, 1, 9);
/* 1923 */         g.fillRect(1, 1, 1, 7);
/* 1924 */         g.fillRect(2, 2, 1, 5);
/* 1925 */         g.fillRect(3, 3, 1, 3);
/* 1926 */         g.fillRect(4, 4, 1, 1);
/* 1927 */         if (paintShadow) {
/* 1928 */           g.setColor(shadow);
/* 1929 */           g.drawLine(1, 8, 4, 5);
/* 1930 */           g.drawLine(1, 9, 5, 5);
/*      */         }
/*      */         break;
/*      */       }
/*      */       
/* 1935 */       g.translate(-x, -y);
/* 1936 */       g.setColor(oldColor);
/*      */     }
/*      */     
/*      */     public Dimension getPreferredSize() {
/* 1940 */       return new Dimension(this.buttonWidth, this.buttonWidth);
/*      */     }
/*      */     
/*      */     public Dimension getMinimumSize() {
/* 1944 */       return getPreferredSize();
/*      */     }
/*      */     
/*      */     public Dimension getMaximumSize() {
/* 1948 */       return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static abstract class AbstractRenderer
/*      */   {
/* 1958 */     protected static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/* 1959 */     protected static final Insets NORTH_INSETS = new Insets(1, 0, 0, 0);
/* 1960 */     protected static final Insets WEST_INSETS = new Insets(0, 1, 0, 0);
/* 1961 */     protected static final Insets SOUTH_INSETS = new Insets(0, 0, 1, 0);
/* 1962 */     protected static final Insets EAST_INSETS = new Insets(0, 0, 0, 1);
/*      */     protected final JTabbedPane tabPane;
/*      */     protected final int tabPlacement;
/*      */     protected Color shadowColor;
/*      */     protected Color darkShadow;
/*      */     protected Color selectColor;
/*      */     protected Color selectLight;
/*      */     protected Color selectHighlight;
/*      */     protected Color lightHighlight;
/*      */     protected Color focus;
/*      */     
/*      */     private AbstractRenderer(JTabbedPane tabPane)
/*      */     {
/* 1975 */       initColors();
/* 1976 */       this.tabPane = tabPane;
/* 1977 */       this.tabPlacement = tabPane.getTabPlacement();
/*      */     }
/*      */     
/*      */     private static AbstractRenderer createRenderer(JTabbedPane tabPane) {
/* 1981 */       switch (tabPane.getTabPlacement()) {
/*      */       case 1: 
/* 1983 */         return new PlasticTabbedPaneUI.TopRenderer(tabPane, null);
/*      */       case 3: 
/* 1985 */         return new PlasticTabbedPaneUI.BottomRenderer(tabPane, null);
/*      */       case 2: 
/* 1987 */         return new PlasticTabbedPaneUI.LeftRenderer(tabPane, null);
/*      */       case 4: 
/* 1989 */         return new PlasticTabbedPaneUI.RightRenderer(tabPane, null);
/*      */       }
/* 1991 */       return new PlasticTabbedPaneUI.TopRenderer(tabPane, null);
/*      */     }
/*      */     
/*      */     private static AbstractRenderer createEmbeddedRenderer(JTabbedPane tabPane)
/*      */     {
/* 1996 */       switch (tabPane.getTabPlacement()) {
/*      */       case 1: 
/* 1998 */         return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane, null);
/*      */       case 3: 
/* 2000 */         return new PlasticTabbedPaneUI.BottomEmbeddedRenderer(tabPane, null);
/*      */       case 2: 
/* 2002 */         return new PlasticTabbedPaneUI.LeftEmbeddedRenderer(tabPane, null);
/*      */       case 4: 
/* 2004 */         return new PlasticTabbedPaneUI.RightEmbeddedRenderer(tabPane, null);
/*      */       }
/* 2006 */       return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane, null);
/*      */     }
/*      */     
/*      */     private void initColors()
/*      */     {
/* 2011 */       this.shadowColor = UIManager.getColor("TabbedPane.shadow");
/* 2012 */       this.darkShadow = UIManager.getColor("TabbedPane.darkShadow");
/* 2013 */       this.selectColor = UIManager.getColor("TabbedPane.selected");
/* 2014 */       this.focus = UIManager.getColor("TabbedPane.focus");
/* 2015 */       this.selectHighlight = UIManager.getColor("TabbedPane.selectHighlight");
/* 2016 */       this.lightHighlight = UIManager.getColor("TabbedPane.highlight");
/* 2017 */       this.selectLight = new Color((2 * this.selectColor.getRed() + this.selectHighlight.getRed()) / 3, (2 * this.selectColor.getGreen() + this.selectHighlight.getGreen()) / 3, (2 * this.selectColor.getBlue() + this.selectHighlight.getBlue()) / 3);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected boolean isFirstDisplayedTab(int tabIndex, int position, int paneBorder)
/*      */     {
/* 2025 */       return tabIndex == 0;
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets defaultInsets)
/*      */     {
/* 2030 */       return defaultInsets;
/*      */     }
/*      */     
/*      */     protected Insets getContentBorderInsets(Insets defaultInsets) {
/* 2034 */       return defaultInsets;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
/*      */     {
/* 2041 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
/*      */     {
/* 2048 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     protected int getTabRunOverlay(int tabRunOverlay)
/*      */     {
/* 2055 */       return tabRunOverlay;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected boolean shouldPadTabRun(int run, boolean aPriori)
/*      */     {
/* 2063 */       return aPriori;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected int getTabRunIndent(int run)
/*      */     {
/* 2072 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected abstract Insets getTabInsets(int paramInt, Insets paramInsets);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected abstract void paintFocusIndicator(Graphics paramGraphics, Rectangle[] paramArrayOfRectangle, int paramInt, Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected abstract void paintTabBackground(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected abstract void paintTabBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Insets getSelectedTabPadInsets()
/*      */     {
/* 2107 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2124 */       if (isContentBorderPainted) {
/* 2125 */         g.setColor(this.selectHighlight);
/* 2126 */         g.fillRect(x, y, w - 1, 1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2144 */       if (isContentBorderPainted) {
/* 2145 */         g.setColor(this.darkShadow);
/* 2146 */         g.fillRect(x, y + h - 1, w - 1, 1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2164 */       if (isContentBorderPainted) {
/* 2165 */         g.setColor(this.selectHighlight);
/* 2166 */         g.fillRect(x, y, 1, h - 1);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2184 */       if (isContentBorderPainted) {
/* 2185 */         g.setColor(this.darkShadow);
/* 2186 */         g.fillRect(x + w - 1, y, 1, h);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     protected int getTabsOverlay()
/*      */     {
/* 2194 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class BottomEmbeddedRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private BottomEmbeddedRenderer(JTabbedPane tabPane)
/*      */     {
/* 2205 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets insets) {
/* 2209 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getContentBorderInsets(Insets defaultInsets) {
/* 2213 */       return SOUTH_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2217 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2221 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2239 */       g.setColor(this.selectColor);
/* 2240 */       g.fillRect(x, y, w + 1, h);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2245 */       int bottom = h;
/* 2246 */       int right = w + 1;
/*      */       
/* 2248 */       g.translate(x, y);
/* 2249 */       if (isFirstDisplayedTab(tabIndex, x, this.tabPane.getBounds().x)) {
/* 2250 */         if (isSelected)
/*      */         {
/* 2252 */           g.setColor(this.shadowColor);
/* 2253 */           g.fillRect(right, 0, 1, bottom - 1);
/* 2254 */           g.fillRect(right - 1, bottom - 1, 1, 1);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2260 */           g.setColor(this.selectHighlight);
/* 2261 */           g.fillRect(0, 0, 1, bottom);
/* 2262 */           g.fillRect(right - 1, 0, 1, bottom - 1);
/* 2263 */           g.fillRect(1, bottom - 1, right - 2, 1);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2268 */       else if (isSelected)
/*      */       {
/* 2270 */         g.setColor(this.shadowColor);
/* 2271 */         g.fillRect(0, 0, 1, bottom - 1);
/* 2272 */         g.fillRect(1, bottom - 1, 1, 1);
/* 2273 */         g.fillRect(right, 0, 1, bottom - 1);
/* 2274 */         g.fillRect(right - 1, bottom - 1, 1, 1);
/*      */         
/*      */ 
/* 2277 */         g.setColor(this.selectHighlight);
/* 2278 */         g.fillRect(1, 0, 1, bottom - 1);
/* 2279 */         g.fillRect(right - 1, 0, 1, bottom - 1);
/* 2280 */         g.fillRect(2, bottom - 1, right - 3, 1);
/*      */       } else {
/* 2282 */         g.setColor(this.shadowColor);
/* 2283 */         g.fillRect(1, h / 2, 1, h - h / 2);
/*      */       }
/*      */       
/* 2286 */       g.translate(-x, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2299 */       g.setColor(this.shadowColor);
/* 2300 */       g.fillRect(x, y + h - 1, w, 1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final class BottomRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private BottomRenderer(JTabbedPane tabPane)
/*      */     {
/* 2312 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets defaultInsets) {
/* 2316 */       return new Insets(defaultInsets.top, defaultInsets.left + 5, defaultInsets.bottom, defaultInsets.right);
/*      */     }
/*      */     
/*      */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected) {
/* 2320 */       return isSelected ? 0 : -1;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2324 */       return tabRunOverlay - 2;
/*      */     }
/*      */     
/*      */     protected int getTabRunIndent(int run) {
/* 2328 */       return 6 * run;
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2332 */       return SOUTH_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2336 */       return new Insets(tabInsets.top, tabInsets.left - 2, tabInsets.bottom, tabInsets.right - 2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */     {
/* 2347 */       if ((!this.tabPane.hasFocus()) || (!isSelected))
/* 2348 */         return;
/* 2349 */       Rectangle tabRect = rects[tabIndex];
/* 2350 */       int top = tabRect.y;
/* 2351 */       int left = tabRect.x + 6;
/* 2352 */       int height = tabRect.height - 3;
/* 2353 */       int width = tabRect.width - 12;
/* 2354 */       g.setColor(this.focus);
/* 2355 */       g.drawRect(left, top, width, height);
/*      */     }
/*      */     
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2360 */       g.setColor(this.selectColor);
/* 2361 */       g.fillRect(x, y, w, h);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2366 */       int bottom = h - 1;
/* 2367 */       int right = w + 4;
/*      */       
/* 2369 */       g.translate(x - 3, y);
/*      */       
/*      */ 
/* 2372 */       g.setColor(this.selectHighlight);
/*      */       
/*      */ 
/* 2375 */       g.fillRect(0, 0, 1, 2);
/* 2376 */       g.drawLine(0, 2, 4, bottom - 4);
/* 2377 */       g.fillRect(5, bottom - 3, 1, 2);
/* 2378 */       g.fillRect(6, bottom - 1, 1, 1);
/*      */       
/*      */ 
/* 2381 */       g.fillRect(7, bottom, 1, 1);
/* 2382 */       g.setColor(this.darkShadow);
/* 2383 */       g.fillRect(8, bottom, right - 13, 1);
/*      */       
/*      */ 
/* 2386 */       g.drawLine(right + 1, 0, right - 3, bottom - 4);
/* 2387 */       g.fillRect(right - 4, bottom - 3, 1, 2);
/* 2388 */       g.fillRect(right - 5, bottom - 1, 1, 1);
/*      */       
/* 2390 */       g.translate(-x + 3, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2402 */       int bottom = y + h - 1;
/* 2403 */       int right = x + w - 1;
/* 2404 */       g.translate(x, bottom);
/* 2405 */       if ((drawBroken) && (selRect.x >= x) && (selRect.x <= x + w))
/*      */       {
/* 2407 */         g.setColor(this.darkShadow);
/* 2408 */         g.fillRect(0, 0, selRect.x - x - 2, 1);
/* 2409 */         if (selRect.x + selRect.width < x + w - 2) {
/* 2410 */           g.setColor(this.darkShadow);
/* 2411 */           g.fillRect(selRect.x + selRect.width + 2 - x, 0, right - selRect.x - selRect.width - 2, 1);
/*      */         }
/*      */       } else {
/* 2414 */         g.setColor(this.darkShadow);
/* 2415 */         g.fillRect(0, 0, w - 1, 1);
/*      */       }
/* 2417 */       g.translate(-x, -bottom);
/*      */     }
/*      */     
/*      */     protected int getTabsOverlay() {
/* 2421 */       return 4;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class LeftEmbeddedRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private LeftEmbeddedRenderer(JTabbedPane tabPane)
/*      */     {
/* 2432 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets insets) {
/* 2436 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getContentBorderInsets(Insets defaultInsets) {
/* 2440 */       return WEST_INSETS;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2444 */       return 0;
/*      */     }
/*      */     
/*      */     protected boolean shouldPadTabRun(int run, boolean aPriori) {
/* 2448 */       return false;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2452 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2456 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2473 */       g.setColor(this.selectColor);
/* 2474 */       g.fillRect(x, y, w, h);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2479 */       int bottom = h;
/* 2480 */       int right = w;
/*      */       
/* 2482 */       g.translate(x, y);
/*      */       
/* 2484 */       if (isFirstDisplayedTab(tabIndex, y, this.tabPane.getBounds().y)) {
/* 2485 */         if (isSelected)
/*      */         {
/* 2487 */           g.setColor(this.selectHighlight);
/* 2488 */           g.fillRect(0, 0, right, 1);
/* 2489 */           g.fillRect(0, 0, 1, bottom - 1);
/* 2490 */           g.fillRect(1, bottom - 1, right - 1, 1);
/* 2491 */           g.setColor(this.shadowColor);
/* 2492 */           g.fillRect(0, bottom - 1, 1, 1);
/* 2493 */           g.fillRect(1, bottom, right - 1, 1);
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/* 2500 */       else if (isSelected)
/*      */       {
/* 2502 */         g.setColor(this.selectHighlight);
/* 2503 */         g.fillRect(1, 1, right - 1, 1);
/* 2504 */         g.fillRect(0, 2, 1, bottom - 2);
/* 2505 */         g.fillRect(1, bottom - 1, right - 1, 1);
/* 2506 */         g.setColor(this.shadowColor);
/* 2507 */         g.fillRect(1, 0, right - 1, 1);
/* 2508 */         g.fillRect(0, 1, 1, 1);
/* 2509 */         g.fillRect(0, bottom - 1, 1, 1);
/* 2510 */         g.fillRect(1, bottom, right - 1, 1);
/*      */       }
/*      */       else
/*      */       {
/* 2514 */         g.setColor(this.shadowColor);
/* 2515 */         g.fillRect(0, 0, right / 3, 1);
/*      */       }
/*      */       
/*      */ 
/* 2519 */       g.translate(-x, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2531 */       g.setColor(this.shadowColor);
/* 2532 */       g.fillRect(x, y, 1, h);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static final class LeftRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private LeftRenderer(JTabbedPane tabPane)
/*      */     {
/* 2542 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets defaultInsets) {
/* 2546 */       return new Insets(defaultInsets.top + 4, defaultInsets.left, defaultInsets.bottom, defaultInsets.right);
/*      */     }
/*      */     
/*      */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected) {
/* 2550 */       return 1;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2554 */       return 1;
/*      */     }
/*      */     
/*      */     protected boolean shouldPadTabRun(int run, boolean aPriori) {
/* 2558 */       return false;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2562 */       return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2566 */       return WEST_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */     {
/* 2577 */       if ((!this.tabPane.hasFocus()) || (!isSelected))
/* 2578 */         return;
/* 2579 */       Rectangle tabRect = rects[tabIndex];
/* 2580 */       int top = tabRect.y + 2;
/* 2581 */       int left = tabRect.x + 3;
/* 2582 */       int height = tabRect.height - 5;
/* 2583 */       int width = tabRect.width - 6;
/* 2584 */       g.setColor(this.focus);
/* 2585 */       g.drawRect(left, top, width, height);
/*      */     }
/*      */     
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
/* 2589 */       if (!isSelected) {
/* 2590 */         g.setColor(this.selectLight);
/* 2591 */         g.fillRect(x + 1, y + 1, w - 1, h - 2);
/*      */       } else {
/* 2593 */         g.setColor(this.selectColor);
/* 2594 */         g.fillRect(x + 1, y + 1, w - 3, h - 2);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2600 */       int bottom = h - 1;
/* 2601 */       int left = 0;
/* 2602 */       g.translate(x, y);
/*      */       
/*      */ 
/* 2605 */       g.setColor(this.selectHighlight);
/*      */       
/* 2607 */       g.fillRect(left + 2, 0, w - 2 - left, 1);
/*      */       
/*      */ 
/* 2610 */       g.fillRect(left + 1, 1, 1, 1);
/* 2611 */       g.fillRect(left, 2, 1, bottom - 3);
/* 2612 */       g.setColor(this.darkShadow);
/* 2613 */       g.fillRect(left + 1, bottom - 1, 1, 1);
/*      */       
/*      */ 
/* 2616 */       g.fillRect(left + 2, bottom, w - 2 - left, 1);
/*      */       
/* 2618 */       g.translate(-x, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2630 */       g.setColor(this.selectHighlight);
/* 2631 */       if ((drawBroken) && (selRect.y >= y) && (selRect.y <= y + h))
/*      */       {
/* 2633 */         g.fillRect(x, y, 1, selRect.y + 1 - y);
/* 2634 */         if (selRect.y + selRect.height < y + h - 2) {
/* 2635 */           g.fillRect(x, selRect.y + selRect.height - 1, 1, y + h - selRect.y - selRect.height);
/*      */         }
/*      */       } else {
/* 2638 */         g.fillRect(x, y, 1, h - 1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class RightEmbeddedRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private RightEmbeddedRenderer(JTabbedPane tabPane)
/*      */     {
/* 2650 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets insets) {
/* 2654 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getContentBorderInsets(Insets defaultInsets) {
/* 2658 */       return EAST_INSETS;
/*      */     }
/*      */     
/*      */     protected int getTabRunIndent(int run) {
/* 2662 */       return 4 * run;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2666 */       return 0;
/*      */     }
/*      */     
/*      */     protected boolean shouldPadTabRun(int run, boolean aPriori) {
/* 2670 */       return false;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2674 */       return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2678 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2696 */       g.setColor(this.selectColor);
/* 2697 */       g.fillRect(x, y, w, h);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2702 */       int bottom = h;
/* 2703 */       int right = w - 1;
/*      */       
/* 2705 */       g.translate(x + 1, y);
/*      */       
/* 2707 */       if (isFirstDisplayedTab(tabIndex, y, this.tabPane.getBounds().y)) {
/* 2708 */         if (isSelected)
/*      */         {
/* 2710 */           g.setColor(this.shadowColor);
/*      */           
/*      */ 
/*      */ 
/* 2714 */           g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2715 */           g.fillRect(0, bottom, right - 1, 1);
/* 2716 */           g.setColor(this.selectHighlight);
/* 2717 */           g.fillRect(0, 0, right - 1, 1);
/* 2718 */           g.fillRect(right - 1, 0, 1, bottom - 1);
/* 2719 */           g.fillRect(0, bottom - 1, right - 1, 1);
/*      */         }
/*      */       }
/* 2722 */       else if (isSelected)
/*      */       {
/* 2724 */         g.setColor(this.shadowColor);
/* 2725 */         g.fillRect(0, -1, right - 1, 1);
/* 2726 */         g.fillRect(right - 1, 0, 1, 1);
/*      */         
/*      */ 
/* 2729 */         g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2730 */         g.fillRect(0, bottom, right - 1, 1);
/* 2731 */         g.setColor(this.selectHighlight);
/* 2732 */         g.fillRect(0, 0, right - 1, 1);
/* 2733 */         g.fillRect(right - 1, 1, 1, bottom - 2);
/* 2734 */         g.fillRect(0, bottom - 1, right - 1, 1);
/*      */       }
/*      */       else {
/* 2737 */         g.setColor(this.shadowColor);
/* 2738 */         g.fillRect(2 * right / 3, 0, right / 3, 1);
/*      */       }
/*      */       
/* 2741 */       g.translate(-x - 1, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2753 */       g.setColor(this.shadowColor);
/* 2754 */       g.fillRect(x + w - 1, y, 1, h);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class RightRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private RightRenderer(JTabbedPane tabPane)
/*      */     {
/* 2765 */       super(null);
/*      */     }
/*      */     
/*      */     protected int getTabLabelShiftX(int tabIndex, boolean isSelected) {
/* 2769 */       return 1;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2773 */       return 1;
/*      */     }
/*      */     
/*      */     protected boolean shouldPadTabRun(int run, boolean aPriori) {
/* 2777 */       return false;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2781 */       return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2785 */       return EAST_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */     {
/* 2796 */       if ((!this.tabPane.hasFocus()) || (!isSelected))
/* 2797 */         return;
/* 2798 */       Rectangle tabRect = rects[tabIndex];
/* 2799 */       int top = tabRect.y + 2;
/* 2800 */       int left = tabRect.x + 3;
/* 2801 */       int height = tabRect.height - 5;
/* 2802 */       int width = tabRect.width - 6;
/* 2803 */       g.setColor(this.focus);
/* 2804 */       g.drawRect(left, top, width, height);
/*      */     }
/*      */     
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
/* 2808 */       if (!isSelected) {
/* 2809 */         g.setColor(this.selectLight);
/* 2810 */         g.fillRect(x, y, w, h);
/*      */       } else {
/* 2812 */         g.setColor(this.selectColor);
/* 2813 */         g.fillRect(x + 2, y, w - 2, h);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2819 */       int bottom = h - 1;
/* 2820 */       int right = w;
/*      */       
/* 2822 */       g.translate(x, y);
/*      */       
/*      */ 
/*      */ 
/* 2826 */       g.setColor(this.selectHighlight);
/* 2827 */       g.fillRect(0, 0, right - 1, 1);
/*      */       
/* 2829 */       g.setColor(this.darkShadow);
/* 2830 */       g.fillRect(right - 1, 1, 1, 1);
/* 2831 */       g.fillRect(right, 2, 1, bottom - 3);
/*      */       
/* 2833 */       g.fillRect(right - 1, bottom - 1, 1, 1);
/* 2834 */       g.fillRect(0, bottom, right - 1, 1);
/*      */       
/* 2836 */       g.translate(-x, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2848 */       g.setColor(this.darkShadow);
/* 2849 */       if ((drawBroken) && (selRect.y >= y) && (selRect.y <= y + h))
/*      */       {
/* 2851 */         g.fillRect(x + w - 1, y, 1, selRect.y - y);
/* 2852 */         if (selRect.y + selRect.height < y + h - 2) {
/* 2853 */           g.fillRect(x + w - 1, selRect.y + selRect.height, 1, y + h - selRect.y - selRect.height);
/*      */         }
/*      */       } else {
/* 2856 */         g.fillRect(x + w - 1, y, 1, h - 1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static final class TopEmbeddedRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private TopEmbeddedRenderer(JTabbedPane tabPane)
/*      */     {
/* 2867 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets insets) {
/* 2871 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getContentBorderInsets(Insets defaultInsets) {
/* 2875 */       return NORTH_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2879 */       return new Insets(tabInsets.top, tabInsets.left + 1, tabInsets.bottom, tabInsets.right);
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2883 */       return EMPTY_INSETS;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2901 */       g.setColor(this.selectColor);
/* 2902 */       g.fillRect(x, y, w, h);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 2907 */       g.translate(x, y);
/*      */       
/* 2909 */       int right = w;
/* 2910 */       int bottom = h;
/*      */       
/* 2912 */       if (isFirstDisplayedTab(tabIndex, x, this.tabPane.getBounds().x)) {
/* 2913 */         if (isSelected) {
/* 2914 */           g.setColor(this.selectHighlight);
/*      */           
/* 2916 */           g.fillRect(0, 0, 1, bottom);
/*      */           
/* 2918 */           g.fillRect(0, 0, right - 1, 1);
/*      */           
/* 2920 */           g.fillRect(right - 1, 0, 1, bottom);
/* 2921 */           g.setColor(this.shadowColor);
/*      */           
/* 2923 */           g.fillRect(right - 1, 0, 1, 1);
/*      */           
/* 2925 */           g.fillRect(right, 1, 1, bottom);
/*      */         }
/*      */       }
/* 2928 */       else if (isSelected) {
/* 2929 */         g.setColor(this.selectHighlight);
/*      */         
/* 2931 */         g.fillRect(1, 1, 1, bottom - 1);
/*      */         
/* 2933 */         g.fillRect(2, 0, right - 3, 1);
/*      */         
/* 2935 */         g.fillRect(right - 1, 1, 1, bottom - 1);
/* 2936 */         g.setColor(this.shadowColor);
/*      */         
/* 2938 */         g.fillRect(0, 1, 1, bottom - 1);
/*      */         
/* 2940 */         g.fillRect(1, 0, 1, 1);
/*      */         
/* 2942 */         g.fillRect(right - 1, 0, 1, 1);
/*      */         
/* 2944 */         g.fillRect(right, 1, 1, bottom);
/*      */       } else {
/* 2946 */         g.setColor(this.shadowColor);
/* 2947 */         g.fillRect(0, 0, 1, bottom + 2 - bottom / 2);
/*      */       }
/*      */       
/* 2950 */       g.translate(-x, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 2962 */       g.setColor(this.shadowColor);
/* 2963 */       g.fillRect(x, y, w, 1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class TopRenderer
/*      */     extends PlasticTabbedPaneUI.AbstractRenderer
/*      */   {
/*      */     private TopRenderer(JTabbedPane tabPane)
/*      */     {
/* 2974 */       super(null);
/*      */     }
/*      */     
/*      */     protected Insets getTabAreaInsets(Insets defaultInsets) {
/* 2978 */       return new Insets(defaultInsets.top, defaultInsets.left + 4, defaultInsets.bottom, defaultInsets.right);
/*      */     }
/*      */     
/*      */     protected int getTabLabelShiftY(int tabIndex, boolean isSelected) {
/* 2982 */       return isSelected ? -1 : 0;
/*      */     }
/*      */     
/*      */     protected int getTabRunOverlay(int tabRunOverlay) {
/* 2986 */       return tabRunOverlay - 2;
/*      */     }
/*      */     
/*      */     protected int getTabRunIndent(int run) {
/* 2990 */       return 6 * run;
/*      */     }
/*      */     
/*      */     protected Insets getSelectedTabPadInsets() {
/* 2994 */       return NORTH_INSETS;
/*      */     }
/*      */     
/*      */     protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
/* 2998 */       return new Insets(tabInsets.top - 1, tabInsets.left - 4, tabInsets.bottom, tabInsets.right - 4);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
/*      */     {
/* 3009 */       if ((!this.tabPane.hasFocus()) || (!isSelected))
/* 3010 */         return;
/* 3011 */       Rectangle tabRect = rects[tabIndex];
/* 3012 */       int top = tabRect.y + 1;
/* 3013 */       int left = tabRect.x + 4;
/* 3014 */       int height = tabRect.height - 3;
/* 3015 */       int width = tabRect.width - 9;
/* 3016 */       g.setColor(this.focus);
/* 3017 */       g.drawRect(left, top, width, height);
/*      */     }
/*      */     
/*      */     protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 3022 */       int sel = isSelected ? 0 : 1;
/* 3023 */       g.setColor(this.selectColor);
/* 3024 */       g.fillRect(x, y + sel, w, h / 2);
/* 3025 */       g.fillRect(x - 1, y + sel + h / 2, w + 2, h - h / 2);
/*      */     }
/*      */     
/*      */     protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
/*      */     {
/* 3030 */       g.translate(x - 4, y);
/*      */       
/* 3032 */       int top = 0;
/* 3033 */       int right = w + 6;
/*      */       
/*      */ 
/* 3036 */       g.setColor(this.selectHighlight);
/*      */       
/*      */ 
/* 3039 */       g.drawLine(1, h - 1, 4, top + 4);
/* 3040 */       g.fillRect(5, top + 2, 1, 2);
/* 3041 */       g.fillRect(6, top + 1, 1, 1);
/*      */       
/*      */ 
/* 3044 */       g.fillRect(7, top, right - 12, 1);
/*      */       
/*      */ 
/* 3047 */       g.setColor(this.darkShadow);
/* 3048 */       g.drawLine(right, h - 1, right - 3, top + 4);
/* 3049 */       g.fillRect(right - 4, top + 2, 1, 2);
/* 3050 */       g.fillRect(right - 5, top + 1, 1, 1);
/*      */       
/* 3052 */       g.translate(-x + 4, -y);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted)
/*      */     {
/* 3064 */       int right = x + w - 1;
/* 3065 */       int top = y;
/* 3066 */       g.setColor(this.selectHighlight);
/*      */       
/* 3068 */       if ((drawBroken) && (selRect.x >= x) && (selRect.x <= x + w))
/*      */       {
/* 3070 */         g.fillRect(x, top, selRect.x - 2 - x, 1);
/* 3071 */         if (selRect.x + selRect.width < x + w - 2) {
/* 3072 */           g.fillRect(selRect.x + selRect.width + 2, top, right - 2 - selRect.x - selRect.width, 1);
/*      */         } else {
/* 3074 */           g.fillRect(x + w - 2, top, 1, 1);
/*      */         }
/*      */       } else {
/* 3077 */         g.fillRect(x, top, w - 1, 1);
/*      */       }
/*      */     }
/*      */     
/*      */     protected int getTabsOverlay() {
/* 3082 */       return 6;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticTabbedPaneUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */