/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.logging.Logger;
/*   8:    */ import javax.swing.JComponent;
/*   9:    */ import javax.swing.JMenuBar;
/*  10:    */ import javax.swing.JPanel;
/*  11:    */ import javax.swing.JRootPane;
/*  12:    */ import javax.swing.JToolBar;
/*  13:    */ 
/*  14:    */ public class View
/*  15:    */   extends AbstractBean
/*  16:    */ {
/*  17: 60 */   private static final Logger logger = Logger.getLogger(View.class.getName());
/*  18:    */   private final Application application;
/*  19: 62 */   private ResourceMap resourceMap = null;
/*  20: 63 */   private JRootPane rootPane = null;
/*  21: 64 */   private JComponent component = null;
/*  22: 65 */   private JMenuBar menuBar = null;
/*  23: 66 */   private List<JToolBar> toolBars = Collections.emptyList();
/*  24: 67 */   private JComponent toolBarsPanel = null;
/*  25: 68 */   private JComponent statusBar = null;
/*  26:    */   
/*  27:    */   public View(Application application)
/*  28:    */   {
/*  29: 78 */     if (application == null) {
/*  30: 79 */       throw new IllegalArgumentException("null application");
/*  31:    */     }
/*  32: 81 */     this.application = application;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final Application getApplication()
/*  36:    */   {
/*  37: 93 */     return this.application;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final ApplicationContext getContext()
/*  41:    */   {
/*  42:107 */     return getApplication().getContext();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ResourceMap getResourceMap()
/*  46:    */   {
/*  47:118 */     if (this.resourceMap == null) {
/*  48:119 */       this.resourceMap = getContext().getResourceMap(getClass(), View.class);
/*  49:    */     }
/*  50:121 */     return this.resourceMap;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public JRootPane getRootPane()
/*  54:    */   {
/*  55:137 */     if (this.rootPane == null)
/*  56:    */     {
/*  57:138 */       this.rootPane = new JRootPane();
/*  58:139 */       this.rootPane.setOpaque(true);
/*  59:    */     }
/*  60:141 */     return this.rootPane;
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void replaceContentPaneChild(JComponent oldChild, JComponent newChild, String constraint)
/*  64:    */   {
/*  65:145 */     Container contentPane = getRootPane().getContentPane();
/*  66:146 */     if (oldChild != null) {
/*  67:147 */       contentPane.remove(oldChild);
/*  68:    */     }
/*  69:149 */     if (newChild != null) {
/*  70:150 */       contentPane.add(newChild, constraint);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public JComponent getComponent()
/*  75:    */   {
/*  76:161 */     return this.component;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setComponent(JComponent component)
/*  80:    */   {
/*  81:176 */     JComponent oldValue = this.component;
/*  82:177 */     this.component = component;
/*  83:178 */     replaceContentPaneChild(oldValue, this.component, "Center");
/*  84:179 */     firePropertyChange("component", oldValue, this.component);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public JMenuBar getMenuBar()
/*  88:    */   {
/*  89:189 */     return this.menuBar;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setMenuBar(JMenuBar menuBar)
/*  93:    */   {
/*  94:193 */     JMenuBar oldValue = getMenuBar();
/*  95:194 */     this.menuBar = menuBar;
/*  96:195 */     getRootPane().setJMenuBar(menuBar);
/*  97:196 */     firePropertyChange("menuBar", oldValue, menuBar);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<JToolBar> getToolBars()
/* 101:    */   {
/* 102:200 */     return this.toolBars;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setToolBars(List<JToolBar> toolBars)
/* 106:    */   {
/* 107:204 */     if (toolBars == null) {
/* 108:205 */       throw new IllegalArgumentException("null toolbars");
/* 109:    */     }
/* 110:207 */     List<JToolBar> oldValue = getToolBars();
/* 111:208 */     this.toolBars = Collections.unmodifiableList(new ArrayList(toolBars));
/* 112:209 */     JComponent oldToolBarsPanel = this.toolBarsPanel;
/* 113:210 */     JComponent newToolBarsPanel = null;
/* 114:211 */     if (this.toolBars.size() == 1)
/* 115:    */     {
/* 116:212 */       newToolBarsPanel = (JComponent)toolBars.get(0);
/* 117:    */     }
/* 118:214 */     else if (this.toolBars.size() > 1)
/* 119:    */     {
/* 120:215 */       newToolBarsPanel = new JPanel();
/* 121:216 */       for (JComponent toolBar : this.toolBars) {
/* 122:217 */         newToolBarsPanel.add(toolBar);
/* 123:    */       }
/* 124:    */     }
/* 125:220 */     replaceContentPaneChild(oldToolBarsPanel, newToolBarsPanel, "North");
/* 126:221 */     firePropertyChange("toolBars", oldValue, this.toolBars);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final JToolBar getToolBar()
/* 130:    */   {
/* 131:225 */     List<JToolBar> toolBars = getToolBars();
/* 132:226 */     return toolBars.size() == 0 ? null : (JToolBar)toolBars.get(0);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public final void setToolBar(JToolBar toolBar)
/* 136:    */   {
/* 137:230 */     JToolBar oldValue = getToolBar();
/* 138:231 */     List<JToolBar> toolBars = Collections.emptyList();
/* 139:232 */     if (toolBar != null) {
/* 140:233 */       toolBars = Collections.singletonList(toolBar);
/* 141:    */     }
/* 142:235 */     setToolBars(toolBars);
/* 143:236 */     firePropertyChange("toolBar", oldValue, toolBar);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public JComponent getStatusBar()
/* 147:    */   {
/* 148:240 */     return this.statusBar;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setStatusBar(JComponent statusBar)
/* 152:    */   {
/* 153:244 */     JComponent oldValue = this.statusBar;
/* 154:245 */     this.statusBar = statusBar;
/* 155:246 */     replaceContentPaneChild(oldValue, this.statusBar, "South");
/* 156:247 */     firePropertyChange("statusBar", oldValue, this.statusBar);
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.View
 * JD-Core Version:    0.7.0.1
 */