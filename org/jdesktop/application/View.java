/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JToolBar;
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
/*     */ public class View
/*     */   extends AbstractBean
/*     */ {
/*  60 */   private static final Logger logger = Logger.getLogger(View.class.getName());
/*     */   private final Application application;
/*  62 */   private ResourceMap resourceMap = null;
/*  63 */   private JRootPane rootPane = null;
/*  64 */   private JComponent component = null;
/*  65 */   private JMenuBar menuBar = null;
/*  66 */   private List<JToolBar> toolBars = Collections.emptyList();
/*  67 */   private JComponent toolBarsPanel = null;
/*  68 */   private JComponent statusBar = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public View(Application application)
/*     */   {
/*  78 */     if (application == null) {
/*  79 */       throw new IllegalArgumentException("null application");
/*     */     }
/*  81 */     this.application = application;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Application getApplication()
/*     */   {
/*  93 */     return this.application;
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
/*     */   public final ApplicationContext getContext()
/*     */   {
/* 107 */     return getApplication().getContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResourceMap getResourceMap()
/*     */   {
/* 118 */     if (this.resourceMap == null) {
/* 119 */       this.resourceMap = getContext().getResourceMap(getClass(), View.class);
/*     */     }
/* 121 */     return this.resourceMap;
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
/*     */   public JRootPane getRootPane()
/*     */   {
/* 137 */     if (this.rootPane == null) {
/* 138 */       this.rootPane = new JRootPane();
/* 139 */       this.rootPane.setOpaque(true);
/*     */     }
/* 141 */     return this.rootPane;
/*     */   }
/*     */   
/*     */   private void replaceContentPaneChild(JComponent oldChild, JComponent newChild, String constraint) {
/* 145 */     Container contentPane = getRootPane().getContentPane();
/* 146 */     if (oldChild != null) {
/* 147 */       contentPane.remove(oldChild);
/*     */     }
/* 149 */     if (newChild != null) {
/* 150 */       contentPane.add(newChild, constraint);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JComponent getComponent()
/*     */   {
/* 161 */     return this.component;
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
/*     */   public void setComponent(JComponent component)
/*     */   {
/* 176 */     JComponent oldValue = this.component;
/* 177 */     this.component = component;
/* 178 */     replaceContentPaneChild(oldValue, this.component, "Center");
/* 179 */     firePropertyChange("component", oldValue, this.component);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JMenuBar getMenuBar()
/*     */   {
/* 189 */     return this.menuBar;
/*     */   }
/*     */   
/*     */   public void setMenuBar(JMenuBar menuBar) {
/* 193 */     JMenuBar oldValue = getMenuBar();
/* 194 */     this.menuBar = menuBar;
/* 195 */     getRootPane().setJMenuBar(menuBar);
/* 196 */     firePropertyChange("menuBar", oldValue, menuBar);
/*     */   }
/*     */   
/*     */   public List<JToolBar> getToolBars() {
/* 200 */     return this.toolBars;
/*     */   }
/*     */   
/*     */   public void setToolBars(List<JToolBar> toolBars) {
/* 204 */     if (toolBars == null) {
/* 205 */       throw new IllegalArgumentException("null toolbars");
/*     */     }
/* 207 */     List<JToolBar> oldValue = getToolBars();
/* 208 */     this.toolBars = Collections.unmodifiableList(new ArrayList(toolBars));
/* 209 */     JComponent oldToolBarsPanel = this.toolBarsPanel;
/* 210 */     JComponent newToolBarsPanel = null;
/* 211 */     if (this.toolBars.size() == 1) {
/* 212 */       newToolBarsPanel = (JComponent)toolBars.get(0);
/*     */     }
/* 214 */     else if (this.toolBars.size() > 1) {
/* 215 */       newToolBarsPanel = new JPanel();
/* 216 */       for (JComponent toolBar : this.toolBars) {
/* 217 */         newToolBarsPanel.add(toolBar);
/*     */       }
/*     */     }
/* 220 */     replaceContentPaneChild(oldToolBarsPanel, newToolBarsPanel, "North");
/* 221 */     firePropertyChange("toolBars", oldValue, this.toolBars);
/*     */   }
/*     */   
/*     */   public final JToolBar getToolBar() {
/* 225 */     List<JToolBar> toolBars = getToolBars();
/* 226 */     return toolBars.size() == 0 ? null : (JToolBar)toolBars.get(0);
/*     */   }
/*     */   
/*     */   public final void setToolBar(JToolBar toolBar) {
/* 230 */     JToolBar oldValue = getToolBar();
/* 231 */     List<JToolBar> toolBars = Collections.emptyList();
/* 232 */     if (toolBar != null) {
/* 233 */       toolBars = Collections.singletonList(toolBar);
/*     */     }
/* 235 */     setToolBars(toolBars);
/* 236 */     firePropertyChange("toolBar", oldValue, toolBar);
/*     */   }
/*     */   
/*     */   public JComponent getStatusBar() {
/* 240 */     return this.statusBar;
/*     */   }
/*     */   
/*     */   public void setStatusBar(JComponent statusBar) {
/* 244 */     JComponent oldValue = this.statusBar;
/* 245 */     this.statusBar = statusBar;
/* 246 */     replaceContentPaneChild(oldValue, this.statusBar, "South");
/* 247 */     firePropertyChange("statusBar", oldValue, this.statusBar);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\View.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */