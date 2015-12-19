/*    1:     */ package org.jdesktop.application;
/*    2:     */ 
/*    3:     */ import java.applet.Applet;
/*    4:     */ import java.awt.Component;
/*    5:     */ import java.awt.Container;
/*    6:     */ import java.awt.Dialog;
/*    7:     */ import java.awt.Frame;
/*    8:     */ import java.awt.GraphicsConfiguration;
/*    9:     */ import java.awt.GraphicsEnvironment;
/*   10:     */ import java.awt.Rectangle;
/*   11:     */ import java.awt.Window;
/*   12:     */ import java.io.IOException;
/*   13:     */ import java.util.ArrayList;
/*   14:     */ import java.util.Collections;
/*   15:     */ import java.util.HashMap;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.Map;
/*   18:     */ import java.util.logging.Logger;
/*   19:     */ import javax.swing.JComponent;
/*   20:     */ import javax.swing.JFrame;
/*   21:     */ import javax.swing.JRootPane;
/*   22:     */ import javax.swing.JSplitPane;
/*   23:     */ import javax.swing.JTabbedPane;
/*   24:     */ import javax.swing.JTable;
/*   25:     */ import javax.swing.plaf.SplitPaneUI;
/*   26:     */ import javax.swing.table.TableColumn;
/*   27:     */ import javax.swing.table.TableColumnModel;
/*   28:     */ 
/*   29:     */ public class SessionStorage
/*   30:     */ {
/*   31: 100 */   private static Logger logger = Logger.getLogger(SessionStorage.class.getName());
/*   32:     */   private final Map<Class, Property> propertyMap;
/*   33:     */   private final ApplicationContext context;
/*   34:     */   
/*   35:     */   protected SessionStorage(ApplicationContext context)
/*   36:     */   {
/*   37: 149 */     if (context == null) {
/*   38: 150 */       throw new IllegalArgumentException("null context");
/*   39:     */     }
/*   40: 152 */     this.context = context;
/*   41: 153 */     this.propertyMap = new HashMap();
/*   42: 154 */     this.propertyMap.put(Window.class, new WindowProperty());
/*   43: 155 */     this.propertyMap.put(JTabbedPane.class, new TabbedPaneProperty());
/*   44: 156 */     this.propertyMap.put(JSplitPane.class, new SplitPaneProperty());
/*   45: 157 */     this.propertyMap.put(JTable.class, new TableProperty());
/*   46:     */   }
/*   47:     */   
/*   48:     */   protected final ApplicationContext getContext()
/*   49:     */   {
/*   50: 162 */     return this.context;
/*   51:     */   }
/*   52:     */   
/*   53:     */   private void checkSaveRestoreArgs(Component root, String fileName)
/*   54:     */   {
/*   55: 166 */     if (root == null) {
/*   56: 167 */       throw new IllegalArgumentException("null root");
/*   57:     */     }
/*   58: 169 */     if (fileName == null) {
/*   59: 170 */       throw new IllegalArgumentException("null fileName");
/*   60:     */     }
/*   61:     */   }
/*   62:     */   
/*   63:     */   private String getComponentName(Component c)
/*   64:     */   {
/*   65: 177 */     return c.getName();
/*   66:     */   }
/*   67:     */   
/*   68:     */   private String getComponentPathname(Component c)
/*   69:     */   {
/*   70: 206 */     String name = getComponentName(c);
/*   71: 207 */     if (name == null) {
/*   72: 208 */       return null;
/*   73:     */     }
/*   74: 210 */     StringBuilder path = new StringBuilder(name);
/*   75: 211 */     while ((c.getParent() != null) && (!(c instanceof Window)) && (!(c instanceof Applet)))
/*   76:     */     {
/*   77: 212 */       c = c.getParent();
/*   78: 213 */       name = getComponentName(c);
/*   79: 214 */       if (name == null)
/*   80:     */       {
/*   81: 215 */         int n = c.getParent().getComponentZOrder(c);
/*   82: 216 */         if (n >= 0)
/*   83:     */         {
/*   84: 217 */           Class cls = c.getClass();
/*   85: 218 */           name = cls.getSimpleName();
/*   86: 219 */           if (name.length() == 0) {
/*   87: 220 */             name = "Anonymous" + cls.getSuperclass().getSimpleName();
/*   88:     */           }
/*   89: 222 */           name = name + n;
/*   90:     */         }
/*   91:     */         else
/*   92:     */         {
/*   93: 227 */           logger.warning("Couldn't compute pathname for " + c);
/*   94: 228 */           return null;
/*   95:     */         }
/*   96:     */       }
/*   97: 231 */       path.append("/").append(name);
/*   98:     */     }
/*   99: 233 */     return path.toString();
/*  100:     */   }
/*  101:     */   
/*  102:     */   private void saveTree(List<Component> roots, Map<String, Object> stateMap)
/*  103:     */   {
/*  104: 244 */     List<Component> allChildren = new ArrayList();
/*  105: 245 */     for (Component root : roots)
/*  106:     */     {
/*  107: 246 */       if (root != null)
/*  108:     */       {
/*  109: 247 */         Property p = getProperty(root);
/*  110: 248 */         if (p != null)
/*  111:     */         {
/*  112: 249 */           String pathname = getComponentPathname(root);
/*  113: 250 */           if (pathname != null)
/*  114:     */           {
/*  115: 251 */             Object state = p.getSessionState(root);
/*  116: 252 */             if (state != null) {
/*  117: 253 */               stateMap.put(pathname, state);
/*  118:     */             }
/*  119:     */           }
/*  120:     */         }
/*  121:     */       }
/*  122: 258 */       if ((root instanceof Container))
/*  123:     */       {
/*  124: 259 */         Component[] children = ((Container)root).getComponents();
/*  125: 260 */         if ((children != null) && (children.length > 0)) {
/*  126: 261 */           Collections.addAll(allChildren, children);
/*  127:     */         }
/*  128:     */       }
/*  129:     */     }
/*  130: 265 */     if (allChildren.size() > 0) {
/*  131: 266 */       saveTree(allChildren, stateMap);
/*  132:     */     }
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void save(Component root, String fileName)
/*  136:     */     throws IOException
/*  137:     */   {
/*  138: 318 */     checkSaveRestoreArgs(root, fileName);
/*  139: 319 */     Map<String, Object> stateMap = new HashMap();
/*  140: 320 */     saveTree(Collections.singletonList(root), stateMap);
/*  141: 321 */     LocalStorage lst = getContext().getLocalStorage();
/*  142: 322 */     lst.save(stateMap, fileName);
/*  143:     */   }
/*  144:     */   
/*  145:     */   private void restoreTree(List<Component> roots, Map<String, Object> stateMap)
/*  146:     */   {
/*  147: 334 */     List<Component> allChildren = new ArrayList();
/*  148: 335 */     for (Component root : roots)
/*  149:     */     {
/*  150: 336 */       if (root != null)
/*  151:     */       {
/*  152: 337 */         Property p = getProperty(root);
/*  153: 338 */         if (p != null)
/*  154:     */         {
/*  155: 339 */           String pathname = getComponentPathname(root);
/*  156: 340 */           if (pathname != null)
/*  157:     */           {
/*  158: 341 */             Object state = stateMap.get(pathname);
/*  159: 342 */             if (state != null) {
/*  160: 343 */               p.setSessionState(root, state);
/*  161:     */             } else {
/*  162: 346 */               logger.warning("No saved state for " + root);
/*  163:     */             }
/*  164:     */           }
/*  165:     */         }
/*  166:     */       }
/*  167: 351 */       if ((root instanceof Container))
/*  168:     */       {
/*  169: 352 */         Component[] children = ((Container)root).getComponents();
/*  170: 353 */         if ((children != null) && (children.length > 0)) {
/*  171: 354 */           Collections.addAll(allChildren, children);
/*  172:     */         }
/*  173:     */       }
/*  174:     */     }
/*  175: 358 */     if (allChildren.size() > 0) {
/*  176: 359 */       restoreTree(allChildren, stateMap);
/*  177:     */     }
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void restore(Component root, String fileName)
/*  181:     */     throws IOException
/*  182:     */   {
/*  183: 380 */     checkSaveRestoreArgs(root, fileName);
/*  184: 381 */     LocalStorage lst = getContext().getLocalStorage();
/*  185: 382 */     Map<String, Object> stateMap = (Map)lst.load(fileName);
/*  186: 383 */     if (stateMap != null) {
/*  187: 384 */       restoreTree(Collections.singletonList(root), stateMap);
/*  188:     */     }
/*  189:     */   }
/*  190:     */   
/*  191:     */   public static abstract interface Property
/*  192:     */   {
/*  193:     */     public abstract Object getSessionState(Component paramComponent);
/*  194:     */     
/*  195:     */     public abstract void setSessionState(Component paramComponent, Object paramObject);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public static class WindowState
/*  199:     */   {
/*  200:     */     private final Rectangle bounds;
/*  201: 444 */     private Rectangle gcBounds = null;
/*  202:     */     private int screenCount;
/*  203: 446 */     private int frameState = 0;
/*  204:     */     
/*  205:     */     public WindowState()
/*  206:     */     {
/*  207: 448 */       this.bounds = new Rectangle();
/*  208:     */     }
/*  209:     */     
/*  210:     */     public WindowState(Rectangle bounds, Rectangle gcBounds, int screenCount, int frameState)
/*  211:     */     {
/*  212: 451 */       if (bounds == null) {
/*  213: 452 */         throw new IllegalArgumentException("null bounds");
/*  214:     */       }
/*  215: 454 */       if (screenCount < 1) {
/*  216: 455 */         throw new IllegalArgumentException("invalid screenCount");
/*  217:     */       }
/*  218: 457 */       this.bounds = bounds;
/*  219: 458 */       this.gcBounds = gcBounds;
/*  220: 459 */       this.screenCount = screenCount;
/*  221: 460 */       this.frameState = frameState;
/*  222:     */     }
/*  223:     */     
/*  224:     */     public Rectangle getBounds()
/*  225:     */     {
/*  226: 463 */       return new Rectangle(this.bounds);
/*  227:     */     }
/*  228:     */     
/*  229:     */     public void setBounds(Rectangle bounds)
/*  230:     */     {
/*  231: 466 */       this.bounds.setBounds(bounds);
/*  232:     */     }
/*  233:     */     
/*  234:     */     public int getScreenCount()
/*  235:     */     {
/*  236: 469 */       return this.screenCount;
/*  237:     */     }
/*  238:     */     
/*  239:     */     public void setScreenCount(int screenCount)
/*  240:     */     {
/*  241: 472 */       this.screenCount = screenCount;
/*  242:     */     }
/*  243:     */     
/*  244:     */     public int getFrameState()
/*  245:     */     {
/*  246: 475 */       return this.frameState;
/*  247:     */     }
/*  248:     */     
/*  249:     */     public void setFrameState(int frameState)
/*  250:     */     {
/*  251: 478 */       this.frameState = frameState;
/*  252:     */     }
/*  253:     */     
/*  254:     */     public Rectangle getGraphicsConfigurationBounds()
/*  255:     */     {
/*  256: 481 */       return this.gcBounds == null ? null : new Rectangle(this.gcBounds);
/*  257:     */     }
/*  258:     */     
/*  259:     */     public void setGraphicsConfigurationBounds(Rectangle gcBounds)
/*  260:     */     {
/*  261: 484 */       this.gcBounds = (gcBounds == null ? null : new Rectangle(gcBounds));
/*  262:     */     }
/*  263:     */   }
/*  264:     */   
/*  265:     */   public static class WindowProperty
/*  266:     */     implements SessionStorage.Property
/*  267:     */   {
/*  268:     */     private void checkComponent(Component component)
/*  269:     */     {
/*  270: 510 */       if (component == null) {
/*  271: 511 */         throw new IllegalArgumentException("null component");
/*  272:     */       }
/*  273: 513 */       if (!(component instanceof Window)) {
/*  274: 514 */         throw new IllegalArgumentException("invalid component");
/*  275:     */       }
/*  276:     */     }
/*  277:     */     
/*  278:     */     private int getScreenCount()
/*  279:     */     {
/*  280: 519 */       return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
/*  281:     */     }
/*  282:     */     
/*  283:     */     public Object getSessionState(Component c)
/*  284:     */     {
/*  285: 536 */       checkComponent(c);
/*  286: 537 */       int frameState = 0;
/*  287: 538 */       if ((c instanceof Frame)) {
/*  288: 539 */         frameState = ((Frame)c).getExtendedState();
/*  289:     */       }
/*  290: 541 */       GraphicsConfiguration gc = c.getGraphicsConfiguration();
/*  291: 542 */       Rectangle gcBounds = gc == null ? null : gc.getBounds();
/*  292: 543 */       Rectangle frameBounds = c.getBounds();
/*  293: 548 */       if (((c instanceof JFrame)) && (0 != (frameState & 0x6)))
/*  294:     */       {
/*  295: 549 */         String clientPropertyKey = "WindowState.normalBounds";
/*  296: 550 */         Object r = ((JFrame)c).getRootPane().getClientProperty(clientPropertyKey);
/*  297: 551 */         if ((r instanceof Rectangle)) {
/*  298: 552 */           frameBounds = (Rectangle)r;
/*  299:     */         }
/*  300:     */       }
/*  301: 555 */       return new SessionStorage.WindowState(frameBounds, gcBounds, getScreenCount(), frameState);
/*  302:     */     }
/*  303:     */     
/*  304:     */     public void setSessionState(Component c, Object state)
/*  305:     */     {
/*  306: 585 */       checkComponent(c);
/*  307: 586 */       if ((state != null) && (!(state instanceof SessionStorage.WindowState))) {
/*  308: 587 */         throw new IllegalArgumentException("invalid state");
/*  309:     */       }
/*  310: 589 */       Window w = (Window)c;
/*  311: 590 */       if ((!w.isLocationByPlatform()) && (state != null))
/*  312:     */       {
/*  313: 591 */         SessionStorage.WindowState windowState = (SessionStorage.WindowState)state;
/*  314: 592 */         Rectangle gcBounds0 = windowState.getGraphicsConfigurationBounds();
/*  315: 593 */         int sc0 = windowState.getScreenCount();
/*  316: 594 */         GraphicsConfiguration gc = c.getGraphicsConfiguration();
/*  317: 595 */         Rectangle gcBounds1 = gc == null ? null : gc.getBounds();
/*  318: 596 */         int sc1 = getScreenCount();
/*  319: 597 */         if ((gcBounds0 != null) && (gcBounds0.equals(gcBounds1)) && (sc0 == sc1))
/*  320:     */         {
/*  321: 598 */           boolean resizable = true;
/*  322: 599 */           if ((w instanceof Frame)) {
/*  323: 600 */             resizable = ((Frame)w).isResizable();
/*  324: 602 */           } else if ((w instanceof Dialog)) {
/*  325: 603 */             resizable = ((Dialog)w).isResizable();
/*  326:     */           }
/*  327: 605 */           if (resizable) {
/*  328: 606 */             w.setBounds(windowState.getBounds());
/*  329:     */           }
/*  330:     */         }
/*  331: 609 */         if ((w instanceof Frame)) {
/*  332: 610 */           ((Frame)w).setExtendedState(windowState.getFrameState());
/*  333:     */         }
/*  334:     */       }
/*  335:     */     }
/*  336:     */   }
/*  337:     */   
/*  338:     */   public static class TabbedPaneState
/*  339:     */   {
/*  340:     */     private int selectedIndex;
/*  341:     */     private int tabCount;
/*  342:     */     
/*  343:     */     public TabbedPaneState()
/*  344:     */     {
/*  345: 631 */       this.selectedIndex = -1;
/*  346: 632 */       this.tabCount = 0;
/*  347:     */     }
/*  348:     */     
/*  349:     */     public TabbedPaneState(int selectedIndex, int tabCount)
/*  350:     */     {
/*  351: 635 */       if (tabCount < 0) {
/*  352: 636 */         throw new IllegalArgumentException("invalid tabCount");
/*  353:     */       }
/*  354: 638 */       if ((selectedIndex < -1) || (selectedIndex > tabCount)) {
/*  355: 639 */         throw new IllegalArgumentException("invalid selectedIndex");
/*  356:     */       }
/*  357: 641 */       this.selectedIndex = selectedIndex;
/*  358: 642 */       this.tabCount = tabCount;
/*  359:     */     }
/*  360:     */     
/*  361:     */     public int getSelectedIndex()
/*  362:     */     {
/*  363: 644 */       return this.selectedIndex;
/*  364:     */     }
/*  365:     */     
/*  366:     */     public void setSelectedIndex(int selectedIndex)
/*  367:     */     {
/*  368: 646 */       if (selectedIndex < -1) {
/*  369: 647 */         throw new IllegalArgumentException("invalid selectedIndex");
/*  370:     */       }
/*  371: 649 */       this.selectedIndex = selectedIndex;
/*  372:     */     }
/*  373:     */     
/*  374:     */     public int getTabCount()
/*  375:     */     {
/*  376: 651 */       return this.tabCount;
/*  377:     */     }
/*  378:     */     
/*  379:     */     public void setTabCount(int tabCount)
/*  380:     */     {
/*  381: 653 */       if (tabCount < 0) {
/*  382: 654 */         throw new IllegalArgumentException("invalid tabCount");
/*  383:     */       }
/*  384: 656 */       this.tabCount = tabCount;
/*  385:     */     }
/*  386:     */   }
/*  387:     */   
/*  388:     */   public static class TabbedPaneProperty
/*  389:     */     implements SessionStorage.Property
/*  390:     */   {
/*  391:     */     private void checkComponent(Component component)
/*  392:     */     {
/*  393: 682 */       if (component == null) {
/*  394: 683 */         throw new IllegalArgumentException("null component");
/*  395:     */       }
/*  396: 685 */       if (!(component instanceof JTabbedPane)) {
/*  397: 686 */         throw new IllegalArgumentException("invalid component");
/*  398:     */       }
/*  399:     */     }
/*  400:     */     
/*  401:     */     public Object getSessionState(Component c)
/*  402:     */     {
/*  403: 704 */       checkComponent(c);
/*  404: 705 */       JTabbedPane p = (JTabbedPane)c;
/*  405: 706 */       return new SessionStorage.TabbedPaneState(p.getSelectedIndex(), p.getTabCount());
/*  406:     */     }
/*  407:     */     
/*  408:     */     public void setSessionState(Component c, Object state)
/*  409:     */     {
/*  410: 724 */       checkComponent(c);
/*  411: 725 */       if ((state != null) && (!(state instanceof SessionStorage.TabbedPaneState))) {
/*  412: 726 */         throw new IllegalArgumentException("invalid state");
/*  413:     */       }
/*  414: 728 */       JTabbedPane p = (JTabbedPane)c;
/*  415: 729 */       SessionStorage.TabbedPaneState tps = (SessionStorage.TabbedPaneState)state;
/*  416: 730 */       if (p.getTabCount() == tps.getTabCount()) {
/*  417: 731 */         p.setSelectedIndex(tps.getSelectedIndex());
/*  418:     */       }
/*  419:     */     }
/*  420:     */   }
/*  421:     */   
/*  422:     */   public static class SplitPaneState
/*  423:     */   {
/*  424: 749 */     private int dividerLocation = -1;
/*  425: 750 */     private int orientation = 1;
/*  426:     */     
/*  427:     */     private void checkOrientation(int orientation)
/*  428:     */     {
/*  429: 752 */       if ((orientation != 1) && (orientation != 0)) {
/*  430: 754 */         throw new IllegalArgumentException("invalid orientation");
/*  431:     */       }
/*  432:     */     }
/*  433:     */     
/*  434:     */     public SplitPaneState() {}
/*  435:     */     
/*  436:     */     public SplitPaneState(int dividerLocation, int orientation)
/*  437:     */     {
/*  438: 759 */       checkOrientation(orientation);
/*  439: 760 */       if (dividerLocation < -1) {
/*  440: 761 */         throw new IllegalArgumentException("invalid dividerLocation");
/*  441:     */       }
/*  442: 763 */       this.dividerLocation = dividerLocation;
/*  443: 764 */       this.orientation = orientation;
/*  444:     */     }
/*  445:     */     
/*  446:     */     public int getDividerLocation()
/*  447:     */     {
/*  448: 766 */       return this.dividerLocation;
/*  449:     */     }
/*  450:     */     
/*  451:     */     public void setDividerLocation(int dividerLocation)
/*  452:     */     {
/*  453: 768 */       if (dividerLocation < -1) {
/*  454: 769 */         throw new IllegalArgumentException("invalid dividerLocation");
/*  455:     */       }
/*  456: 771 */       this.dividerLocation = dividerLocation;
/*  457:     */     }
/*  458:     */     
/*  459:     */     public int getOrientation()
/*  460:     */     {
/*  461: 773 */       return this.orientation;
/*  462:     */     }
/*  463:     */     
/*  464:     */     public void setOrientation(int orientation)
/*  465:     */     {
/*  466: 775 */       checkOrientation(orientation);
/*  467: 776 */       this.orientation = orientation;
/*  468:     */     }
/*  469:     */   }
/*  470:     */   
/*  471:     */   public static class SplitPaneProperty
/*  472:     */     implements SessionStorage.Property
/*  473:     */   {
/*  474:     */     private void checkComponent(Component component)
/*  475:     */     {
/*  476: 802 */       if (component == null) {
/*  477: 803 */         throw new IllegalArgumentException("null component");
/*  478:     */       }
/*  479: 805 */       if (!(component instanceof JSplitPane)) {
/*  480: 806 */         throw new IllegalArgumentException("invalid component");
/*  481:     */       }
/*  482:     */     }
/*  483:     */     
/*  484:     */     public Object getSessionState(Component c)
/*  485:     */     {
/*  486: 827 */       checkComponent(c);
/*  487: 828 */       JSplitPane p = (JSplitPane)c;
/*  488: 829 */       return new SessionStorage.SplitPaneState(p.getUI().getDividerLocation(p), p.getOrientation());
/*  489:     */     }
/*  490:     */     
/*  491:     */     public void setSessionState(Component c, Object state)
/*  492:     */     {
/*  493: 847 */       checkComponent(c);
/*  494: 848 */       if ((state != null) && (!(state instanceof SessionStorage.SplitPaneState))) {
/*  495: 849 */         throw new IllegalArgumentException("invalid state");
/*  496:     */       }
/*  497: 851 */       JSplitPane p = (JSplitPane)c;
/*  498: 852 */       SessionStorage.SplitPaneState sps = (SessionStorage.SplitPaneState)state;
/*  499: 853 */       if (p.getOrientation() == sps.getOrientation()) {
/*  500: 854 */         p.setDividerLocation(sps.getDividerLocation());
/*  501:     */       }
/*  502:     */     }
/*  503:     */   }
/*  504:     */   
/*  505:     */   public static class TableState
/*  506:     */   {
/*  507: 869 */     private int[] columnWidths = new int[0];
/*  508:     */     
/*  509:     */     private int[] copyColumnWidths(int[] columnWidths)
/*  510:     */     {
/*  511: 871 */       if (columnWidths == null) {
/*  512: 872 */         throw new IllegalArgumentException("invalid columnWidths");
/*  513:     */       }
/*  514: 874 */       int[] copy = new int[columnWidths.length];
/*  515: 875 */       System.arraycopy(columnWidths, 0, copy, 0, columnWidths.length);
/*  516: 876 */       return copy;
/*  517:     */     }
/*  518:     */     
/*  519:     */     public TableState() {}
/*  520:     */     
/*  521:     */     public TableState(int[] columnWidths)
/*  522:     */     {
/*  523: 880 */       this.columnWidths = copyColumnWidths(columnWidths);
/*  524:     */     }
/*  525:     */     
/*  526:     */     public int[] getColumnWidths()
/*  527:     */     {
/*  528: 882 */       return copyColumnWidths(this.columnWidths);
/*  529:     */     }
/*  530:     */     
/*  531:     */     public void setColumnWidths(int[] columnWidths)
/*  532:     */     {
/*  533: 884 */       this.columnWidths = copyColumnWidths(columnWidths);
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public static class TableProperty
/*  538:     */     implements SessionStorage.Property
/*  539:     */   {
/*  540:     */     private void checkComponent(Component component)
/*  541:     */     {
/*  542: 912 */       if (component == null) {
/*  543: 913 */         throw new IllegalArgumentException("null component");
/*  544:     */       }
/*  545: 915 */       if (!(component instanceof JTable)) {
/*  546: 916 */         throw new IllegalArgumentException("invalid component");
/*  547:     */       }
/*  548:     */     }
/*  549:     */     
/*  550:     */     public Object getSessionState(Component c)
/*  551:     */     {
/*  552: 937 */       checkComponent(c);
/*  553: 938 */       JTable table = (JTable)c;
/*  554: 939 */       int[] columnWidths = new int[table.getColumnCount()];
/*  555: 940 */       boolean resizableColumnExists = false;
/*  556: 941 */       for (int i = 0; i < columnWidths.length; i++)
/*  557:     */       {
/*  558: 942 */         TableColumn tc = table.getColumnModel().getColumn(i);
/*  559: 943 */         columnWidths[i] = (tc.getResizable() ? tc.getWidth() : -1);
/*  560: 944 */         if (tc.getResizable()) {
/*  561: 945 */           resizableColumnExists = true;
/*  562:     */         }
/*  563:     */       }
/*  564: 948 */       return resizableColumnExists ? new SessionStorage.TableState(columnWidths) : null;
/*  565:     */     }
/*  566:     */     
/*  567:     */     public void setSessionState(Component c, Object state)
/*  568:     */     {
/*  569: 965 */       checkComponent(c);
/*  570: 966 */       if (!(state instanceof SessionStorage.TableState)) {
/*  571: 967 */         throw new IllegalArgumentException("invalid state");
/*  572:     */       }
/*  573: 969 */       JTable table = (JTable)c;
/*  574: 970 */       int[] columnWidths = ((SessionStorage.TableState)state).getColumnWidths();
/*  575: 971 */       if (table.getColumnCount() == columnWidths.length) {
/*  576: 972 */         for (int i = 0; i < columnWidths.length; i++) {
/*  577: 973 */           if (columnWidths[i] != -1)
/*  578:     */           {
/*  579: 974 */             TableColumn tc = table.getColumnModel().getColumn(i);
/*  580: 975 */             if (tc.getResizable()) {
/*  581: 976 */               tc.setPreferredWidth(columnWidths[i]);
/*  582:     */             }
/*  583:     */           }
/*  584:     */         }
/*  585:     */       }
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   private void checkClassArg(Class cls)
/*  590:     */   {
/*  591: 985 */     if (cls == null) {
/*  592: 986 */       throw new IllegalArgumentException("null class");
/*  593:     */     }
/*  594:     */   }
/*  595:     */   
/*  596:     */   public Property getProperty(Class cls)
/*  597:     */   {
/*  598:1009 */     checkClassArg(cls);
/*  599:1010 */     while (cls != null)
/*  600:     */     {
/*  601:1011 */       Property p = (Property)this.propertyMap.get(cls);
/*  602:1012 */       if (p != null) {
/*  603:1012 */         return p;
/*  604:     */       }
/*  605:1013 */       cls = cls.getSuperclass();
/*  606:     */     }
/*  607:1015 */     return null;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public void putProperty(Class cls, Property property)
/*  611:     */   {
/*  612:1036 */     checkClassArg(cls);
/*  613:1037 */     this.propertyMap.put(cls, property);
/*  614:     */   }
/*  615:     */   
/*  616:     */   public final Property getProperty(Component c)
/*  617:     */   {
/*  618:1073 */     if (c == null) {
/*  619:1074 */       throw new IllegalArgumentException("null component");
/*  620:     */     }
/*  621:1076 */     if ((c instanceof Property)) {
/*  622:1077 */       return (Property)c;
/*  623:     */     }
/*  624:1080 */     Property p = null;
/*  625:1081 */     if ((c instanceof JComponent))
/*  626:     */     {
/*  627:1082 */       Object v = ((JComponent)c).getClientProperty(Property.class);
/*  628:1083 */       p = (v instanceof Property) ? (Property)v : null;
/*  629:     */     }
/*  630:1085 */     return p != null ? p : getProperty(c.getClass());
/*  631:     */   }
/*  632:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.SessionStorage
 * JD-Core Version:    0.7.0.1
 */