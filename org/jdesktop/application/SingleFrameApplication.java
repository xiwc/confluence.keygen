/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Frame;
/*   6:    */ import java.awt.Window;
/*   7:    */ import java.awt.event.ComponentEvent;
/*   8:    */ import java.awt.event.ComponentListener;
/*   9:    */ import java.awt.event.HierarchyEvent;
/*  10:    */ import java.awt.event.HierarchyListener;
/*  11:    */ import java.awt.event.WindowAdapter;
/*  12:    */ import java.awt.event.WindowEvent;
/*  13:    */ import java.lang.reflect.Method;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.logging.Level;
/*  17:    */ import java.util.logging.Logger;
/*  18:    */ import javax.swing.JComponent;
/*  19:    */ import javax.swing.JDialog;
/*  20:    */ import javax.swing.JFrame;
/*  21:    */ import javax.swing.JRootPane;
/*  22:    */ import javax.swing.JWindow;
/*  23:    */ import javax.swing.RootPaneContainer;
/*  24:    */ 
/*  25:    */ public abstract class SingleFrameApplication
/*  26:    */   extends Application
/*  27:    */ {
/*  28: 94 */   private static final Logger logger = Logger.getLogger(SingleFrameApplication.class.getName());
/*  29:    */   
/*  30:    */   public final JFrame getMainFrame()
/*  31:    */   {
/*  32:122 */     return getMainView().getFrame();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected final void setMainFrame(JFrame mainFrame)
/*  36:    */   {
/*  37:146 */     getMainView().setFrame(mainFrame);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private String sessionFilename(Window window)
/*  41:    */   {
/*  42:150 */     if (window == null) {
/*  43:151 */       return null;
/*  44:    */     }
/*  45:154 */     String name = window.getName();
/*  46:155 */     return name + ".session.xml";
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void configureWindow(Window root)
/*  50:    */   {
/*  51:179 */     getContext().getResourceMap().injectComponents(root);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void initRootPaneContainer(RootPaneContainer c)
/*  55:    */   {
/*  56:183 */     JComponent rootPane = c.getRootPane();
/*  57:    */     
/*  58:185 */     Object k = "SingleFrameApplication.initRootPaneContainer";
/*  59:186 */     if (rootPane.getClientProperty(k) != null) {
/*  60:187 */       return;
/*  61:    */     }
/*  62:189 */     rootPane.putClientProperty(k, Boolean.TRUE);
/*  63:    */     
/*  64:191 */     Container root = rootPane.getParent();
/*  65:192 */     if ((root instanceof Window)) {
/*  66:193 */       configureWindow((Window)root);
/*  67:    */     }
/*  68:196 */     JFrame mainFrame = getMainFrame();
/*  69:197 */     if (c == mainFrame)
/*  70:    */     {
/*  71:198 */       mainFrame.addWindowListener(new MainFrameListener(null));
/*  72:199 */       mainFrame.setDefaultCloseOperation(0);
/*  73:    */     }
/*  74:201 */     else if ((root instanceof Window))
/*  75:    */     {
/*  76:202 */       Window window = (Window)root;
/*  77:203 */       window.addHierarchyListener(new SecondaryWindowListener(null));
/*  78:    */     }
/*  79:206 */     if ((root instanceof JFrame)) {
/*  80:207 */       root.addComponentListener(new FrameBoundsListener(null));
/*  81:    */     }
/*  82:210 */     if ((root instanceof Window))
/*  83:    */     {
/*  84:211 */       Window window = (Window)root;
/*  85:212 */       if ((!root.isValid()) || (root.getWidth() == 0) || (root.getHeight() == 0)) {
/*  86:213 */         window.pack();
/*  87:    */       }
/*  88:215 */       if ((!window.isLocationByPlatform()) && (root.getX() == 0) && (root.getY() == 0))
/*  89:    */       {
/*  90:216 */         Component owner = window.getOwner();
/*  91:217 */         if (owner == null) {
/*  92:218 */           owner = window != mainFrame ? mainFrame : null;
/*  93:    */         }
/*  94:220 */         window.setLocationRelativeTo(owner);
/*  95:    */       }
/*  96:    */     }
/*  97:224 */     if ((root instanceof Window))
/*  98:    */     {
/*  99:225 */       String filename = sessionFilename((Window)root);
/* 100:226 */       if (filename != null) {
/* 101:    */         try
/* 102:    */         {
/* 103:228 */           getContext().getSessionStorage().restore(root, filename);
/* 104:    */         }
/* 105:    */         catch (Exception e)
/* 106:    */         {
/* 107:231 */           String msg = String.format("couldn't restore sesssion [%s]", new Object[] { filename });
/* 108:232 */           logger.log(Level.WARNING, msg, e);
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected void show(JComponent c)
/* 115:    */   {
/* 116:261 */     if (c == null) {
/* 117:262 */       throw new IllegalArgumentException("null JComponent");
/* 118:    */     }
/* 119:264 */     JFrame f = getMainFrame();
/* 120:265 */     f.getContentPane().add(c, "Center");
/* 121:266 */     initRootPaneContainer(f);
/* 122:267 */     f.setVisible(true);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void show(JDialog c)
/* 126:    */   {
/* 127:289 */     if (c == null) {
/* 128:290 */       throw new IllegalArgumentException("null JDialog");
/* 129:    */     }
/* 130:292 */     initRootPaneContainer(c);
/* 131:293 */     c.setVisible(true);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void show(JFrame c)
/* 135:    */   {
/* 136:314 */     if (c == null) {
/* 137:315 */       throw new IllegalArgumentException("null JFrame");
/* 138:    */     }
/* 139:317 */     initRootPaneContainer(c);
/* 140:318 */     c.setVisible(true);
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void saveSession(Window window) {}
/* 144:    */   
/* 145:    */   private boolean isVisibleWindow(Window w)
/* 146:    */   {
/* 147:325 */     return (w.isVisible()) && (((w instanceof JFrame)) || ((w instanceof JDialog)) || ((w instanceof JWindow)));
/* 148:    */   }
/* 149:    */   
/* 150:    */   private List<Window> getVisibleSecondaryWindows()
/* 151:    */   {
/* 152:335 */     List<Window> rv = new ArrayList();
/* 153:336 */     Method getWindowsM = null;
/* 154:    */     try
/* 155:    */     {
/* 156:338 */       getWindowsM = Window.class.getMethod("getWindows", new Class[0]);
/* 157:    */     }
/* 158:    */     catch (Exception ignore) {}
/* 159:342 */     if (getWindowsM != null)
/* 160:    */     {
/* 161:343 */       Window[] windows = null;
/* 162:    */       try
/* 163:    */       {
/* 164:345 */         windows = (Window[])getWindowsM.invoke(null, new Object[0]);
/* 165:    */       }
/* 166:    */       catch (Exception e)
/* 167:    */       {
/* 168:348 */         throw new Error("HCTB - can't get top level windows list", e);
/* 169:    */       }
/* 170:350 */       if (windows != null) {
/* 171:351 */         for (Window window : windows) {
/* 172:352 */           if (isVisibleWindow(window)) {
/* 173:353 */             rv.add(window);
/* 174:    */           }
/* 175:    */         }
/* 176:    */       }
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:359 */       Frame[] frames = Frame.getFrames();
/* 181:360 */       if (frames != null) {
/* 182:361 */         for (Frame frame : frames) {
/* 183:362 */           if (isVisibleWindow(frame)) {
/* 184:363 */             rv.add(frame);
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:    */     }
/* 189:368 */     return rv;
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected void shutdown()
/* 193:    */   {
/* 194:377 */     saveSession(getMainFrame());
/* 195:378 */     for (Window window : getVisibleSecondaryWindows()) {
/* 196:379 */       saveSession(window);
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   private class MainFrameListener
/* 201:    */     extends WindowAdapter
/* 202:    */   {
/* 203:    */     private MainFrameListener() {}
/* 204:    */     
/* 205:    */     public void windowClosing(WindowEvent e)
/* 206:    */     {
/* 207:386 */       SingleFrameApplication.this.exit(e);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   private class SecondaryWindowListener
/* 212:    */     implements HierarchyListener
/* 213:    */   {
/* 214:    */     private SecondaryWindowListener() {}
/* 215:    */     
/* 216:    */     public void hierarchyChanged(HierarchyEvent e)
/* 217:    */     {
/* 218:400 */       if (((e.getChangeFlags() & 0x4) != 0L) && 
/* 219:401 */         ((e.getSource() instanceof Window)))
/* 220:    */       {
/* 221:402 */         Window secondaryWindow = (Window)e.getSource();
/* 222:403 */         if (!secondaryWindow.isShowing()) {
/* 223:404 */           SingleFrameApplication.this.saveSession(secondaryWindow);
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private static class FrameBoundsListener
/* 230:    */     implements ComponentListener
/* 231:    */   {
/* 232:    */     private void maybeSaveFrameSize(ComponentEvent e)
/* 233:    */     {
/* 234:418 */       if ((e.getComponent() instanceof JFrame))
/* 235:    */       {
/* 236:419 */         JFrame f = (JFrame)e.getComponent();
/* 237:420 */         if ((f.getExtendedState() & 0x6) == 0)
/* 238:    */         {
/* 239:421 */           String clientPropertyKey = "WindowState.normalBounds";
/* 240:422 */           f.getRootPane().putClientProperty(clientPropertyKey, f.getBounds());
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:    */     
/* 245:    */     public void componentResized(ComponentEvent e)
/* 246:    */     {
/* 247:426 */       maybeSaveFrameSize(e);
/* 248:    */     }
/* 249:    */     
/* 250:    */     public void componentMoved(ComponentEvent e) {}
/* 251:    */     
/* 252:    */     public void componentHidden(ComponentEvent e) {}
/* 253:    */     
/* 254:    */     public void componentShown(ComponentEvent e) {}
/* 255:    */   }
/* 256:    */   
/* 257:438 */   private FrameView mainView = null;
/* 258:    */   
/* 259:    */   public FrameView getMainView()
/* 260:    */   {
/* 261:441 */     if (this.mainView == null) {
/* 262:442 */       this.mainView = new FrameView(this);
/* 263:    */     }
/* 264:444 */     return this.mainView;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void show(View view)
/* 268:    */   {
/* 269:449 */     if ((this.mainView == null) && ((view instanceof FrameView))) {
/* 270:450 */       this.mainView = ((FrameView)view);
/* 271:    */     }
/* 272:452 */     RootPaneContainer c = (RootPaneContainer)view.getRootPane().getParent();
/* 273:453 */     initRootPaneContainer(c);
/* 274:454 */     ((Window)c).setVisible(true);
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.SingleFrameApplication
 * JD-Core Version:    0.7.0.1
 */