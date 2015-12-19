/*      */ package org.jdesktop.application;
/*      */ 
/*      */ import java.applet.Applet;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Logger;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.plaf.SplitPaneUI;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
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
/*      */ public class SessionStorage
/*      */ {
/*  100 */   private static Logger logger = Logger.getLogger(SessionStorage.class.getName());
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
/*      */   private final Map<Class, Property> propertyMap;
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
/*      */   private final ApplicationContext context;
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
/*      */   protected SessionStorage(ApplicationContext context)
/*      */   {
/*  149 */     if (context == null) {
/*  150 */       throw new IllegalArgumentException("null context");
/*      */     }
/*  152 */     this.context = context;
/*  153 */     this.propertyMap = new HashMap();
/*  154 */     this.propertyMap.put(Window.class, new WindowProperty());
/*  155 */     this.propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
/*  156 */     this.propertyMap.put(JSplitPane.class, new SplitPaneProperty());
/*  157 */     this.propertyMap.put(JTable.class, new TableProperty());
/*      */   }
/*      */   
/*      */   protected final ApplicationContext getContext()
/*      */   {
/*  162 */     return this.context;
/*      */   }
/*      */   
/*      */   private void checkSaveRestoreArgs(Component root, String fileName) {
/*  166 */     if (root == null) {
/*  167 */       throw new IllegalArgumentException("null root");
/*      */     }
/*  169 */     if (fileName == null) {
/*  170 */       throw new IllegalArgumentException("null fileName");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private String getComponentName(Component c)
/*      */   {
/*  177 */     return c.getName();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getComponentPathname(Component c)
/*      */   {
/*  206 */     String name = getComponentName(c);
/*  207 */     if (name == null) {
/*  208 */       return null;
/*      */     }
/*  210 */     StringBuilder path = new StringBuilder(name);
/*  211 */     while ((c.getParent() != null) && (!(c instanceof Window)) && (!(c instanceof Applet))) {
/*  212 */       c = c.getParent();
/*  213 */       name = getComponentName(c);
/*  214 */       if (name == null) {
/*  215 */         int n = c.getParent().getComponentZOrder(c);
/*  216 */         if (n >= 0) {
/*  217 */           Class cls = c.getClass();
/*  218 */           name = cls.getSimpleName();
/*  219 */           if (name.length() == 0) {
/*  220 */             name = "Anonymous" + cls.getSuperclass().getSimpleName();
/*      */           }
/*  222 */           name = name + n;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  227 */           logger.warning("Couldn't compute pathname for " + c);
/*  228 */           return null;
/*      */         }
/*      */       }
/*  231 */       path.append("/").append(name);
/*      */     }
/*  233 */     return path.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void saveTree(List<Component> roots, Map<String, Object> stateMap)
/*      */   {
/*  244 */     List<Component> allChildren = new ArrayList();
/*  245 */     for (Component root : roots) {
/*  246 */       if (root != null) {
/*  247 */         Property p = getProperty(root);
/*  248 */         if (p != null) {
/*  249 */           String pathname = getComponentPathname(root);
/*  250 */           if (pathname != null) {
/*  251 */             Object state = p.getSessionState(root);
/*  252 */             if (state != null) {
/*  253 */               stateMap.put(pathname, state);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  258 */       if ((root instanceof Container)) {
/*  259 */         Component[] children = ((Container)root).getComponents();
/*  260 */         if ((children != null) && (children.length > 0)) {
/*  261 */           Collections.addAll(allChildren, children);
/*      */         }
/*      */       }
/*      */     }
/*  265 */     if (allChildren.size() > 0) {
/*  266 */       saveTree(allChildren, stateMap);
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
/*      */   public void save(Component root, String fileName)
/*      */     throws IOException
/*      */   {
/*  318 */     checkSaveRestoreArgs(root, fileName);
/*  319 */     Map<String, Object> stateMap = new HashMap();
/*  320 */     saveTree(Collections.singletonList(root), stateMap);
/*  321 */     LocalStorage lst = getContext().getLocalStorage();
/*  322 */     lst.save(stateMap, fileName);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void restoreTree(List<Component> roots, Map<String, Object> stateMap)
/*      */   {
/*  334 */     List<Component> allChildren = new ArrayList();
/*  335 */     for (Component root : roots) {
/*  336 */       if (root != null) {
/*  337 */         Property p = getProperty(root);
/*  338 */         if (p != null) {
/*  339 */           String pathname = getComponentPathname(root);
/*  340 */           if (pathname != null) {
/*  341 */             Object state = stateMap.get(pathname);
/*  342 */             if (state != null) {
/*  343 */               p.setSessionState(root, state);
/*      */             }
/*      */             else {
/*  346 */               logger.warning("No saved state for " + root);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  351 */       if ((root instanceof Container)) {
/*  352 */         Component[] children = ((Container)root).getComponents();
/*  353 */         if ((children != null) && (children.length > 0)) {
/*  354 */           Collections.addAll(allChildren, children);
/*      */         }
/*      */       }
/*      */     }
/*  358 */     if (allChildren.size() > 0) {
/*  359 */       restoreTree(allChildren, stateMap);
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
/*      */   public void restore(Component root, String fileName)
/*      */     throws IOException
/*      */   {
/*  380 */     checkSaveRestoreArgs(root, fileName);
/*  381 */     LocalStorage lst = getContext().getLocalStorage();
/*  382 */     Map<String, Object> stateMap = (Map)lst.load(fileName);
/*  383 */     if (stateMap != null) {
/*  384 */       restoreTree(Collections.singletonList(root), stateMap);
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
/*      */   public static abstract interface Property
/*      */   {
/*      */     public abstract Object getSessionState(Component paramComponent);
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
/*      */     public abstract void setSessionState(Component paramComponent, Object paramObject);
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
/*      */   public static class WindowState
/*      */   {
/*      */     private final Rectangle bounds;
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
/*  444 */     private Rectangle gcBounds = null;
/*      */     private int screenCount;
/*  446 */     private int frameState = 0;
/*      */     
/*  448 */     public WindowState() { this.bounds = new Rectangle(); }
/*      */     
/*      */     public WindowState(Rectangle bounds, Rectangle gcBounds, int screenCount, int frameState) {
/*  451 */       if (bounds == null) {
/*  452 */         throw new IllegalArgumentException("null bounds");
/*      */       }
/*  454 */       if (screenCount < 1) {
/*  455 */         throw new IllegalArgumentException("invalid screenCount");
/*      */       }
/*  457 */       this.bounds = bounds;
/*  458 */       this.gcBounds = gcBounds;
/*  459 */       this.screenCount = screenCount;
/*  460 */       this.frameState = frameState;
/*      */     }
/*      */     
/*  463 */     public Rectangle getBounds() { return new Rectangle(this.bounds); }
/*      */     
/*      */     public void setBounds(Rectangle bounds) {
/*  466 */       this.bounds.setBounds(bounds);
/*      */     }
/*      */     
/*  469 */     public int getScreenCount() { return this.screenCount; }
/*      */     
/*      */     public void setScreenCount(int screenCount) {
/*  472 */       this.screenCount = screenCount;
/*      */     }
/*      */     
/*  475 */     public int getFrameState() { return this.frameState; }
/*      */     
/*      */     public void setFrameState(int frameState) {
/*  478 */       this.frameState = frameState;
/*      */     }
/*      */     
/*  481 */     public Rectangle getGraphicsConfigurationBounds() { return this.gcBounds == null ? null : new Rectangle(this.gcBounds); }
/*      */     
/*      */     public void setGraphicsConfigurationBounds(Rectangle gcBounds) {
/*  484 */       this.gcBounds = (gcBounds == null ? null : new Rectangle(gcBounds));
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
/*      */   public static class WindowProperty
/*      */     implements SessionStorage.Property
/*      */   {
/*      */     private void checkComponent(Component component)
/*      */     {
/*  510 */       if (component == null) {
/*  511 */         throw new IllegalArgumentException("null component");
/*      */       }
/*  513 */       if (!(component instanceof Window)) {
/*  514 */         throw new IllegalArgumentException("invalid component");
/*      */       }
/*      */     }
/*      */     
/*      */     private int getScreenCount() {
/*  519 */       return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
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
/*      */     public Object getSessionState(Component c)
/*      */     {
/*  536 */       checkComponent(c);
/*  537 */       int frameState = 0;
/*  538 */       if ((c instanceof Frame)) {
/*  539 */         frameState = ((Frame)c).getExtendedState();
/*      */       }
/*  541 */       GraphicsConfiguration gc = c.getGraphicsConfiguration();
/*  542 */       Rectangle gcBounds = gc == null ? null : gc.getBounds();
/*  543 */       Rectangle frameBounds = c.getBounds();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  548 */       if (((c instanceof JFrame)) && (0 != (frameState & 0x6))) {
/*  549 */         String clientPropertyKey = "WindowState.normalBounds";
/*  550 */         Object r = ((JFrame)c).getRootPane().getClientProperty(clientPropertyKey);
/*  551 */         if ((r instanceof Rectangle)) {
/*  552 */           frameBounds = (Rectangle)r;
/*      */         }
/*      */       }
/*  555 */       return new SessionStorage.WindowState(frameBounds, gcBounds, getScreenCount(), frameState);
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
/*      */     public void setSessionState(Component c, Object state)
/*      */     {
/*  585 */       checkComponent(c);
/*  586 */       if ((state != null) && (!(state instanceof SessionStorage.WindowState))) {
/*  587 */         throw new IllegalArgumentException("invalid state");
/*      */       }
/*  589 */       Window w = (Window)c;
/*  590 */       if ((!w.isLocationByPlatform()) && (state != null)) {
/*  591 */         SessionStorage.WindowState windowState = (SessionStorage.WindowState)state;
/*  592 */         Rectangle gcBounds0 = windowState.getGraphicsConfigurationBounds();
/*  593 */         int sc0 = windowState.getScreenCount();
/*  594 */         GraphicsConfiguration gc = c.getGraphicsConfiguration();
/*  595 */         Rectangle gcBounds1 = gc == null ? null : gc.getBounds();
/*  596 */         int sc1 = getScreenCount();
/*  597 */         if ((gcBounds0 != null) && (gcBounds0.equals(gcBounds1)) && (sc0 == sc1)) {
/*  598 */           boolean resizable = true;
/*  599 */           if ((w instanceof Frame)) {
/*  600 */             resizable = ((Frame)w).isResizable();
/*      */           }
/*  602 */           else if ((w instanceof Dialog)) {
/*  603 */             resizable = ((Dialog)w).isResizable();
/*      */           }
/*  605 */           if (resizable) {
/*  606 */             w.setBounds(windowState.getBounds());
/*      */           }
/*      */         }
/*  609 */         if ((w instanceof Frame)) {
/*  610 */           ((Frame)w).setExtendedState(windowState.getFrameState());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class TabbedPaneState
/*      */   {
/*      */     private int selectedIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     private int tabCount;
/*      */     
/*      */ 
/*      */ 
/*      */     public TabbedPaneState()
/*      */     {
/*  631 */       this.selectedIndex = -1;
/*  632 */       this.tabCount = 0;
/*      */     }
/*      */     
/*  635 */     public TabbedPaneState(int selectedIndex, int tabCount) { if (tabCount < 0) {
/*  636 */         throw new IllegalArgumentException("invalid tabCount");
/*      */       }
/*  638 */       if ((selectedIndex < -1) || (selectedIndex > tabCount)) {
/*  639 */         throw new IllegalArgumentException("invalid selectedIndex");
/*      */       }
/*  641 */       this.selectedIndex = selectedIndex;
/*  642 */       this.tabCount = tabCount; }
/*      */     
/*  644 */     public int getSelectedIndex() { return this.selectedIndex; }
/*      */     
/*  646 */     public void setSelectedIndex(int selectedIndex) { if (selectedIndex < -1) {
/*  647 */         throw new IllegalArgumentException("invalid selectedIndex");
/*      */       }
/*  649 */       this.selectedIndex = selectedIndex; }
/*      */     
/*  651 */     public int getTabCount() { return this.tabCount; }
/*      */     
/*  653 */     public void setTabCount(int tabCount) { if (tabCount < 0) {
/*  654 */         throw new IllegalArgumentException("invalid tabCount");
/*      */       }
/*  656 */       this.tabCount = tabCount;
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
/*      */   public static class TabbedPaneProperty
/*      */     implements SessionStorage.Property
/*      */   {
/*      */     private void checkComponent(Component component)
/*      */     {
/*  682 */       if (component == null) {
/*  683 */         throw new IllegalArgumentException("null component");
/*      */       }
/*  685 */       if (!(component instanceof JTabbedPane)) {
/*  686 */         throw new IllegalArgumentException("invalid component");
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
/*      */     public Object getSessionState(Component c)
/*      */     {
/*  704 */       checkComponent(c);
/*  705 */       JTabbedPane p = (JTabbedPane)c;
/*  706 */       return new SessionStorage.TabbedPaneState(p.getSelectedIndex(), p.getTabCount());
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
/*      */ 
/*      */     public void setSessionState(Component c, Object state)
/*      */     {
/*  724 */       checkComponent(c);
/*  725 */       if ((state != null) && (!(state instanceof SessionStorage.TabbedPaneState))) {
/*  726 */         throw new IllegalArgumentException("invalid state");
/*      */       }
/*  728 */       JTabbedPane p = (JTabbedPane)c;
/*  729 */       SessionStorage.TabbedPaneState tps = (SessionStorage.TabbedPaneState)state;
/*  730 */       if (p.getTabCount() == tps.getTabCount()) {
/*  731 */         p.setSelectedIndex(tps.getSelectedIndex());
/*      */       }
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
/*      */   public static class SplitPaneState
/*      */   {
/*  749 */     private int dividerLocation = -1;
/*  750 */     private int orientation = 1;
/*      */     
/*  752 */     private void checkOrientation(int orientation) { if ((orientation != 1) && (orientation != 0))
/*      */       {
/*  754 */         throw new IllegalArgumentException("invalid orientation"); }
/*      */     }
/*      */     
/*      */     public SplitPaneState() {}
/*      */     
/*  759 */     public SplitPaneState(int dividerLocation, int orientation) { checkOrientation(orientation);
/*  760 */       if (dividerLocation < -1) {
/*  761 */         throw new IllegalArgumentException("invalid dividerLocation");
/*      */       }
/*  763 */       this.dividerLocation = dividerLocation;
/*  764 */       this.orientation = orientation; }
/*      */     
/*  766 */     public int getDividerLocation() { return this.dividerLocation; }
/*      */     
/*  768 */     public void setDividerLocation(int dividerLocation) { if (dividerLocation < -1) {
/*  769 */         throw new IllegalArgumentException("invalid dividerLocation");
/*      */       }
/*  771 */       this.dividerLocation = dividerLocation; }
/*      */     
/*  773 */     public int getOrientation() { return this.orientation; }
/*      */     
/*  775 */     public void setOrientation(int orientation) { checkOrientation(orientation);
/*  776 */       this.orientation = orientation;
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
/*      */   public static class SplitPaneProperty
/*      */     implements SessionStorage.Property
/*      */   {
/*      */     private void checkComponent(Component component)
/*      */     {
/*  802 */       if (component == null) {
/*  803 */         throw new IllegalArgumentException("null component");
/*      */       }
/*  805 */       if (!(component instanceof JSplitPane)) {
/*  806 */         throw new IllegalArgumentException("invalid component");
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
/*      */ 
/*      */ 
/*      */ 
/*      */     public Object getSessionState(Component c)
/*      */     {
/*  827 */       checkComponent(c);
/*  828 */       JSplitPane p = (JSplitPane)c;
/*  829 */       return new SessionStorage.SplitPaneState(p.getUI().getDividerLocation(p), p.getOrientation());
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
/*      */ 
/*      */     public void setSessionState(Component c, Object state)
/*      */     {
/*  847 */       checkComponent(c);
/*  848 */       if ((state != null) && (!(state instanceof SessionStorage.SplitPaneState))) {
/*  849 */         throw new IllegalArgumentException("invalid state");
/*      */       }
/*  851 */       JSplitPane p = (JSplitPane)c;
/*  852 */       SessionStorage.SplitPaneState sps = (SessionStorage.SplitPaneState)state;
/*  853 */       if (p.getOrientation() == sps.getOrientation()) {
/*  854 */         p.setDividerLocation(sps.getDividerLocation());
/*      */       }
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
/*      */   public static class TableState
/*      */   {
/*  869 */     private int[] columnWidths = new int[0];
/*      */     
/*  871 */     private int[] copyColumnWidths(int[] columnWidths) { if (columnWidths == null) {
/*  872 */         throw new IllegalArgumentException("invalid columnWidths");
/*      */       }
/*  874 */       int[] copy = new int[columnWidths.length];
/*  875 */       System.arraycopy(columnWidths, 0, copy, 0, columnWidths.length);
/*  876 */       return copy; }
/*      */     
/*      */     public TableState() {}
/*      */     
/*  880 */     public TableState(int[] columnWidths) { this.columnWidths = copyColumnWidths(columnWidths); }
/*      */     
/*  882 */     public int[] getColumnWidths() { return copyColumnWidths(this.columnWidths); }
/*      */     
/*  884 */     public void setColumnWidths(int[] columnWidths) { this.columnWidths = copyColumnWidths(columnWidths); }
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
/*      */   public static class TableProperty
/*      */     implements SessionStorage.Property
/*      */   {
/*      */     private void checkComponent(Component component)
/*      */     {
/*  912 */       if (component == null) {
/*  913 */         throw new IllegalArgumentException("null component");
/*      */       }
/*  915 */       if (!(component instanceof JTable)) {
/*  916 */         throw new IllegalArgumentException("invalid component");
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
/*      */ 
/*      */ 
/*      */ 
/*      */     public Object getSessionState(Component c)
/*      */     {
/*  937 */       checkComponent(c);
/*  938 */       JTable table = (JTable)c;
/*  939 */       int[] columnWidths = new int[table.getColumnCount()];
/*  940 */       boolean resizableColumnExists = false;
/*  941 */       for (int i = 0; i < columnWidths.length; i++) {
/*  942 */         TableColumn tc = table.getColumnModel().getColumn(i);
/*  943 */         columnWidths[i] = (tc.getResizable() ? tc.getWidth() : -1);
/*  944 */         if (tc.getResizable()) {
/*  945 */           resizableColumnExists = true;
/*      */         }
/*      */       }
/*  948 */       return resizableColumnExists ? new SessionStorage.TableState(columnWidths) : null;
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
/*      */     public void setSessionState(Component c, Object state)
/*      */     {
/*  965 */       checkComponent(c);
/*  966 */       if (!(state instanceof SessionStorage.TableState)) {
/*  967 */         throw new IllegalArgumentException("invalid state");
/*      */       }
/*  969 */       JTable table = (JTable)c;
/*  970 */       int[] columnWidths = ((SessionStorage.TableState)state).getColumnWidths();
/*  971 */       if (table.getColumnCount() == columnWidths.length) {
/*  972 */         for (int i = 0; i < columnWidths.length; i++) {
/*  973 */           if (columnWidths[i] != -1) {
/*  974 */             TableColumn tc = table.getColumnModel().getColumn(i);
/*  975 */             if (tc.getResizable()) {
/*  976 */               tc.setPreferredWidth(columnWidths[i]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkClassArg(Class cls) {
/*  985 */     if (cls == null) {
/*  986 */       throw new IllegalArgumentException("null class");
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
/*      */   public Property getProperty(Class cls)
/*      */   {
/* 1009 */     checkClassArg(cls);
/* 1010 */     while (cls != null) {
/* 1011 */       Property p = (Property)this.propertyMap.get(cls);
/* 1012 */       if (p != null) return p;
/* 1013 */       cls = cls.getSuperclass();
/*      */     }
/* 1015 */     return null;
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
/*      */   public void putProperty(Class cls, Property property)
/*      */   {
/* 1036 */     checkClassArg(cls);
/* 1037 */     this.propertyMap.put(cls, property);
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
/*      */   public final Property getProperty(Component c)
/*      */   {
/* 1073 */     if (c == null) {
/* 1074 */       throw new IllegalArgumentException("null component");
/*      */     }
/* 1076 */     if ((c instanceof Property)) {
/* 1077 */       return (Property)c;
/*      */     }
/*      */     
/* 1080 */     Property p = null;
/* 1081 */     if ((c instanceof JComponent)) {
/* 1082 */       Object v = ((JComponent)c).getClientProperty(Property.class);
/* 1083 */       p = (v instanceof Property) ? (Property)v : null;
/*      */     }
/* 1085 */     return p != null ? p : getProperty(c.getClass());
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\SessionStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */