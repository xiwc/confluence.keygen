/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.event.HierarchyEvent;
/*     */ import java.awt.event.HierarchyListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.RootPaneContainer;
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
/*     */ public abstract class SingleFrameApplication
/*     */   extends Application
/*     */ {
/*  94 */   private static final Logger logger = Logger.getLogger(SingleFrameApplication.class.getName());
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
/*     */   public final JFrame getMainFrame()
/*     */   {
/* 122 */     return getMainView().getFrame();
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
/*     */ 
/*     */   protected final void setMainFrame(JFrame mainFrame)
/*     */   {
/* 146 */     getMainView().setFrame(mainFrame);
/*     */   }
/*     */   
/*     */   private String sessionFilename(Window window) {
/* 150 */     if (window == null) {
/* 151 */       return null;
/*     */     }
/*     */     
/* 154 */     String name = window.getName();
/* 155 */     return name + ".session.xml";
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
/*     */ 
/*     */   protected void configureWindow(Window root)
/*     */   {
/* 179 */     getContext().getResourceMap().injectComponents(root);
/*     */   }
/*     */   
/*     */   private void initRootPaneContainer(RootPaneContainer c) {
/* 183 */     JComponent rootPane = c.getRootPane();
/*     */     
/* 185 */     Object k = "SingleFrameApplication.initRootPaneContainer";
/* 186 */     if (rootPane.getClientProperty(k) != null) {
/* 187 */       return;
/*     */     }
/* 189 */     rootPane.putClientProperty(k, Boolean.TRUE);
/*     */     
/* 191 */     Container root = rootPane.getParent();
/* 192 */     if ((root instanceof Window)) {
/* 193 */       configureWindow((Window)root);
/*     */     }
/*     */     
/* 196 */     JFrame mainFrame = getMainFrame();
/* 197 */     if (c == mainFrame) {
/* 198 */       mainFrame.addWindowListener(new MainFrameListener(null));
/* 199 */       mainFrame.setDefaultCloseOperation(0);
/*     */     }
/* 201 */     else if ((root instanceof Window)) {
/* 202 */       Window window = (Window)root;
/* 203 */       window.addHierarchyListener(new SecondaryWindowListener(null));
/*     */     }
/*     */     
/* 206 */     if ((root instanceof JFrame)) {
/* 207 */       root.addComponentListener(new FrameBoundsListener(null));
/*     */     }
/*     */     
/* 210 */     if ((root instanceof Window)) {
/* 211 */       Window window = (Window)root;
/* 212 */       if ((!root.isValid()) || (root.getWidth() == 0) || (root.getHeight() == 0)) {
/* 213 */         window.pack();
/*     */       }
/* 215 */       if ((!window.isLocationByPlatform()) && (root.getX() == 0) && (root.getY() == 0)) {
/* 216 */         Component owner = window.getOwner();
/* 217 */         if (owner == null) {
/* 218 */           owner = window != mainFrame ? mainFrame : null;
/*     */         }
/* 220 */         window.setLocationRelativeTo(owner);
/*     */       }
/*     */     }
/*     */     
/* 224 */     if ((root instanceof Window)) {
/* 225 */       String filename = sessionFilename((Window)root);
/* 226 */       if (filename != null) {
/*     */         try {
/* 228 */           getContext().getSessionStorage().restore(root, filename);
/*     */         }
/*     */         catch (Exception e) {
/* 231 */           String msg = String.format("couldn't restore sesssion [%s]", new Object[] { filename });
/* 232 */           logger.log(Level.WARNING, msg, e);
/*     */         }
/*     */       }
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
/*     */   protected void show(JComponent c)
/*     */   {
/* 261 */     if (c == null) {
/* 262 */       throw new IllegalArgumentException("null JComponent");
/*     */     }
/* 264 */     JFrame f = getMainFrame();
/* 265 */     f.getContentPane().add(c, "Center");
/* 266 */     initRootPaneContainer(f);
/* 267 */     f.setVisible(true);
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
/*     */   public void show(JDialog c)
/*     */   {
/* 289 */     if (c == null) {
/* 290 */       throw new IllegalArgumentException("null JDialog");
/*     */     }
/* 292 */     initRootPaneContainer(c);
/* 293 */     c.setVisible(true);
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
/*     */   public void show(JFrame c)
/*     */   {
/* 314 */     if (c == null) {
/* 315 */       throw new IllegalArgumentException("null JFrame");
/*     */     }
/* 317 */     initRootPaneContainer(c);
/* 318 */     c.setVisible(true);
/*     */   }
/*     */   
/*     */   private void saveSession(Window window) {}
/*     */   
/*     */   private boolean isVisibleWindow(Window w)
/*     */   {
/* 325 */     return (w.isVisible()) && (((w instanceof JFrame)) || ((w instanceof JDialog)) || ((w instanceof JWindow)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<Window> getVisibleSecondaryWindows()
/*     */   {
/* 335 */     List<Window> rv = new ArrayList();
/* 336 */     Method getWindowsM = null;
/*     */     try {
/* 338 */       getWindowsM = Window.class.getMethod("getWindows", new Class[0]);
/*     */     }
/*     */     catch (Exception ignore) {}
/*     */     
/* 342 */     if (getWindowsM != null) {
/* 343 */       Window[] windows = null;
/*     */       try {
/* 345 */         windows = (Window[])getWindowsM.invoke(null, new Object[0]);
/*     */       }
/*     */       catch (Exception e) {
/* 348 */         throw new Error("HCTB - can't get top level windows list", e);
/*     */       }
/* 350 */       if (windows != null) {
/* 351 */         for (Window window : windows) {
/* 352 */           if (isVisibleWindow(window)) {
/* 353 */             rv.add(window);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 359 */       Frame[] frames = Frame.getFrames();
/* 360 */       if (frames != null) {
/* 361 */         for (Frame frame : frames) {
/* 362 */           if (isVisibleWindow(frame)) {
/* 363 */             rv.add(frame);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 368 */     return rv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void shutdown()
/*     */   {
/* 377 */     saveSession(getMainFrame());
/* 378 */     for (Window window : getVisibleSecondaryWindows())
/* 379 */       saveSession(window);
/*     */   }
/*     */   
/*     */   private class MainFrameListener extends WindowAdapter {
/*     */     private MainFrameListener() {}
/*     */     
/*     */     public void windowClosing(WindowEvent e) {
/* 386 */       SingleFrameApplication.this.exit(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class SecondaryWindowListener
/*     */     implements HierarchyListener
/*     */   {
/*     */     private SecondaryWindowListener() {}
/*     */     
/*     */ 
/*     */     public void hierarchyChanged(HierarchyEvent e)
/*     */     {
/* 400 */       if (((e.getChangeFlags() & 0x4) != 0L) && 
/* 401 */         ((e.getSource() instanceof Window))) {
/* 402 */         Window secondaryWindow = (Window)e.getSource();
/* 403 */         if (!secondaryWindow.isShowing()) {
/* 404 */           SingleFrameApplication.this.saveSession(secondaryWindow);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class FrameBoundsListener
/*     */     implements ComponentListener
/*     */   {
/*     */     private void maybeSaveFrameSize(ComponentEvent e)
/*     */     {
/* 418 */       if ((e.getComponent() instanceof JFrame)) {
/* 419 */         JFrame f = (JFrame)e.getComponent();
/* 420 */         if ((f.getExtendedState() & 0x6) == 0) {
/* 421 */           String clientPropertyKey = "WindowState.normalBounds";
/* 422 */           f.getRootPane().putClientProperty(clientPropertyKey, f.getBounds());
/*     */         }
/*     */       } }
/*     */     
/* 426 */     public void componentResized(ComponentEvent e) { maybeSaveFrameSize(e); }
/*     */     
/*     */ 
/*     */     public void componentMoved(ComponentEvent e) {}
/*     */     
/*     */ 
/*     */     public void componentHidden(ComponentEvent e) {}
/*     */     
/*     */ 
/*     */     public void componentShown(ComponentEvent e) {}
/*     */   }
/*     */   
/* 438 */   private FrameView mainView = null;
/*     */   
/*     */   public FrameView getMainView() {
/* 441 */     if (this.mainView == null) {
/* 442 */       this.mainView = new FrameView(this);
/*     */     }
/* 444 */     return this.mainView;
/*     */   }
/*     */   
/*     */   public void show(View view)
/*     */   {
/* 449 */     if ((this.mainView == null) && ((view instanceof FrameView))) {
/* 450 */       this.mainView = ((FrameView)view);
/*     */     }
/* 452 */     RootPaneContainer c = (RootPaneContainer)view.getRootPane().getParent();
/* 453 */     initRootPaneContainer(c);
/* 454 */     ((Window)c).setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\SingleFrameApplication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */