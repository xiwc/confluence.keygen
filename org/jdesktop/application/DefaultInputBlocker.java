/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.InputVerifier;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.RootPaneContainer;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultInputBlocker
/*     */   extends Task.InputBlocker
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(DefaultInputBlocker.class.getName());
/*  44 */   private JDialog modalDialog = null;
/*     */   
/*     */   DefaultInputBlocker(Task task, Task.BlockingScope scope, Object target, ApplicationAction action) {
/*  47 */     super(task, scope, target, action);
/*     */   }
/*     */   
/*     */   private void setActionTargetBlocked(boolean f) {
/*  51 */     Action action = (Action)getTarget();
/*  52 */     action.setEnabled(!f);
/*     */   }
/*     */   
/*     */   private void setComponentTargetBlocked(boolean f) {
/*  56 */     Component c = (Component)getTarget();
/*  57 */     c.setEnabled(!f);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void blockingDialogComponents(Component root, List<Component> rv)
/*     */   {
/*  65 */     String rootName = root.getName();
/*  66 */     if ((rootName != null) && (rootName.startsWith("BlockingDialog"))) {
/*  67 */       rv.add(root);
/*     */     }
/*  69 */     if ((root instanceof Container)) {
/*  70 */       for (Component child : ((Container)root).getComponents())
/*  71 */         blockingDialogComponents(child, rv);
/*     */     }
/*     */   }
/*     */   
/*     */   private List<Component> blockingDialogComponents(Component root) {
/*  76 */     List<Component> rv = new ArrayList();
/*  77 */     blockingDialogComponents(root, rv);
/*  78 */     return rv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void injectBlockingDialogComponents(Component root)
/*     */   {
/*  86 */     ResourceMap taskResourceMap = getTask().getResourceMap();
/*  87 */     if (taskResourceMap != null) {
/*  88 */       taskResourceMap.injectComponents(root);
/*     */     }
/*  90 */     ApplicationAction action = getAction();
/*  91 */     if (action != null) {
/*  92 */       ResourceMap actionResourceMap = action.getResourceMap();
/*  93 */       String actionName = action.getName();
/*  94 */       for (Component c : blockingDialogComponents(root)) {
/*  95 */         c.setName(actionName + "." + c.getName());
/*     */       }
/*  97 */       actionResourceMap.injectComponents(root);
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
/*     */   private JDialog createBlockingDialog()
/*     */   {
/* 121 */     JOptionPane optionPane = new JOptionPane();
/*     */     
/*     */ 
/*     */ 
/* 125 */     if (getTask().getUserCanCancel()) {
/* 126 */       JButton cancelButton = new JButton();
/* 127 */       cancelButton.setName("BlockingDialog.cancelButton");
/* 128 */       ActionListener doCancelTask = new ActionListener() {
/*     */         public void actionPerformed(ActionEvent ignore) {
/* 130 */           DefaultInputBlocker.this.getTask().cancel(true);
/*     */         }
/* 132 */       };
/* 133 */       cancelButton.addActionListener(doCancelTask);
/* 134 */       optionPane.setOptions(new Object[] { cancelButton });
/*     */     }
/*     */     else {
/* 137 */       optionPane.setOptions(new Object[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 142 */     Component dialogOwner = (Component)getTarget();
/* 143 */     String taskTitle = getTask().getTitle();
/* 144 */     String dialogTitle = taskTitle == null ? "BlockingDialog" : taskTitle;
/* 145 */     final JDialog dialog = optionPane.createDialog(dialogOwner, dialogTitle);
/* 146 */     dialog.setModal(true);
/* 147 */     dialog.setName("BlockingDialog");
/* 148 */     dialog.setDefaultCloseOperation(0);
/* 149 */     WindowListener dialogCloseListener = new WindowAdapter() {
/*     */       public void windowClosing(WindowEvent e) {
/* 151 */         if (DefaultInputBlocker.this.getTask().getUserCanCancel()) {
/* 152 */           DefaultInputBlocker.this.getTask().cancel(true);
/* 153 */           dialog.setVisible(false);
/*     */         }
/*     */       }
/* 156 */     };
/* 157 */     dialog.addWindowListener(dialogCloseListener);
/* 158 */     optionPane.setName("BlockingDialog.optionPane");
/* 159 */     injectBlockingDialogComponents(dialog);
/*     */     
/*     */ 
/*     */ 
/* 163 */     recreateOptionPaneMessage(optionPane);
/* 164 */     dialog.pack();
/* 165 */     return dialog;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void recreateOptionPaneMessage(JOptionPane optionPane)
/*     */   {
/* 175 */     Object message = optionPane.getMessage();
/* 176 */     if ((message instanceof String)) {
/* 177 */       Font font = optionPane.getFont();
/* 178 */       final JTextArea textArea = new JTextArea((String)message);
/* 179 */       textArea.setFont(font);
/* 180 */       int lh = textArea.getFontMetrics(font).getHeight();
/* 181 */       Insets margin = new Insets(0, 0, lh, 24);
/* 182 */       textArea.setMargin(margin);
/* 183 */       textArea.setEditable(false);
/* 184 */       textArea.setWrapStyleWord(true);
/* 185 */       textArea.setBackground(optionPane.getBackground());
/* 186 */       JPanel panel = new JPanel(new BorderLayout());
/* 187 */       panel.add(textArea, "Center");
/* 188 */       final JProgressBar progressBar = new JProgressBar();
/* 189 */       progressBar.setName("BlockingDialog.progressBar");
/* 190 */       progressBar.setIndeterminate(true);
/* 191 */       PropertyChangeListener taskPCL = new PropertyChangeListener() {
/*     */         public void propertyChange(PropertyChangeEvent e) {
/* 193 */           if ("progress".equals(e.getPropertyName())) {
/* 194 */             progressBar.setIndeterminate(false);
/* 195 */             progressBar.setValue(((Integer)e.getNewValue()).intValue());
/* 196 */             DefaultInputBlocker.this.updateStatusBarString(progressBar);
/*     */           }
/* 198 */           else if ("message".equals(e.getPropertyName())) {
/* 199 */             textArea.setText((String)e.getNewValue());
/*     */           }
/*     */         }
/* 202 */       };
/* 203 */       getTask().addPropertyChangeListener(taskPCL);
/* 204 */       panel.add(progressBar, "South");
/* 205 */       injectBlockingDialogComponents(panel);
/* 206 */       optionPane.setMessage(panel);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateStatusBarString(JProgressBar progressBar) {
/* 211 */     if (!progressBar.isStringPainted()) {
/* 212 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 220 */     String key = "progressBarStringFormat";
/* 221 */     if (progressBar.getClientProperty(key) == null) {
/* 222 */       progressBar.putClientProperty(key, progressBar.getString());
/*     */     }
/* 224 */     String fmt = (String)progressBar.getClientProperty(key);
/* 225 */     if (progressBar.getValue() <= 0) {
/* 226 */       progressBar.setString("");
/*     */     }
/* 228 */     else if (fmt == null) {
/* 229 */       progressBar.setString(null);
/*     */     }
/*     */     else {
/* 232 */       double pctComplete = progressBar.getValue() / 100.0D;
/* 233 */       long durSeconds = getTask().getExecutionDuration(TimeUnit.SECONDS);
/* 234 */       long durMinutes = durSeconds / 60L;
/* 235 */       long remSeconds = (0.5D + durSeconds / pctComplete) - durSeconds;
/* 236 */       long remMinutes = remSeconds / 60L;
/* 237 */       String s = String.format(fmt, new Object[] { Long.valueOf(durMinutes), Long.valueOf(durSeconds - durMinutes * 60L), Long.valueOf(remMinutes), Long.valueOf(remSeconds - remMinutes * 60L) });
/*     */       
/* 239 */       progressBar.setString(s);
/*     */     }
/*     */   }
/*     */   
/*     */   private void showBusyGlassPane(boolean f)
/*     */   {
/* 245 */     RootPaneContainer rpc = null;
/* 246 */     Component root = (Component)getTarget();
/* 247 */     while (root != null) {
/* 248 */       if ((root instanceof RootPaneContainer)) {
/* 249 */         rpc = (RootPaneContainer)root;
/* 250 */         break;
/*     */       }
/* 252 */       root = root.getParent();
/*     */     }
/* 254 */     if (rpc != null) {
/* 255 */       if (f) {
/* 256 */         JMenuBar menuBar = rpc.getRootPane().getJMenuBar();
/* 257 */         if (menuBar != null) {
/* 258 */           menuBar.putClientProperty(this, Boolean.valueOf(menuBar.isEnabled()));
/* 259 */           menuBar.setEnabled(false);
/*     */         }
/* 261 */         JComponent glassPane = new BusyGlassPane();
/* 262 */         InputVerifier retainFocusWhileVisible = new InputVerifier() {
/*     */           public boolean verify(JComponent c) {
/* 264 */             return !c.isVisible();
/*     */           }
/* 266 */         };
/* 267 */         glassPane.setInputVerifier(retainFocusWhileVisible);
/* 268 */         Component oldGlassPane = rpc.getGlassPane();
/* 269 */         rpc.getRootPane().putClientProperty(this, oldGlassPane);
/* 270 */         rpc.setGlassPane(glassPane);
/* 271 */         glassPane.setVisible(true);
/* 272 */         glassPane.revalidate();
/*     */       }
/*     */       else {
/* 275 */         JMenuBar menuBar = rpc.getRootPane().getJMenuBar();
/* 276 */         if (menuBar != null) {
/* 277 */           boolean enabled = ((Boolean)menuBar.getClientProperty(this)).booleanValue();
/* 278 */           menuBar.putClientProperty(this, null);
/* 279 */           menuBar.setEnabled(enabled);
/*     */         }
/* 281 */         Component oldGlassPane = (Component)rpc.getRootPane().getClientProperty(this);
/* 282 */         rpc.getRootPane().putClientProperty(this, null);
/* 283 */         if (!oldGlassPane.isVisible()) {
/* 284 */           rpc.getGlassPane().setVisible(false);
/*     */         }
/* 286 */         rpc.setGlassPane(oldGlassPane);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BusyGlassPane
/*     */     extends JPanel
/*     */   {
/*     */     BusyGlassPane()
/*     */     {
/* 296 */       super(false);
/* 297 */       setVisible(false);
/* 298 */       setOpaque(false);
/* 299 */       setCursor(Cursor.getPredefinedCursor(3));
/* 300 */       MouseInputListener blockMouseEvents = new MouseInputAdapter() {};
/* 301 */       addMouseMotionListener(blockMouseEvents);
/* 302 */       addMouseListener(blockMouseEvents);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int blockingDialogDelay()
/*     */   {
/* 313 */     Integer delay = null;
/* 314 */     String key = "BlockingDialogTimer.delay";
/* 315 */     ApplicationAction action = getAction();
/* 316 */     if (action != null) {
/* 317 */       ResourceMap actionResourceMap = action.getResourceMap();
/* 318 */       String actionName = action.getName();
/* 319 */       delay = actionResourceMap.getInteger(actionName + "." + key);
/*     */     }
/* 321 */     ResourceMap taskResourceMap = getTask().getResourceMap();
/* 322 */     if ((delay == null) && (taskResourceMap != null)) {
/* 323 */       delay = taskResourceMap.getInteger(key);
/*     */     }
/* 325 */     return delay == null ? 0 : delay.intValue();
/*     */   }
/*     */   
/*     */   private void showBlockingDialog(boolean f) {
/* 329 */     if (f) {
/* 330 */       if (this.modalDialog != null) {
/* 331 */         String msg = String.format("unexpected InputBlocker state [%s] %s", new Object[] { Boolean.valueOf(f), this });
/* 332 */         logger.warning(msg);
/* 333 */         this.modalDialog.dispose();
/*     */       }
/* 335 */       this.modalDialog = createBlockingDialog();
/* 336 */       ActionListener showModalDialog = new ActionListener() {
/*     */         public void actionPerformed(ActionEvent e) {
/* 338 */           if (DefaultInputBlocker.this.modalDialog != null) {
/* 339 */             DefaultInputBlocker.this.modalDialog.setVisible(true);
/*     */           }
/*     */         }
/* 342 */       };
/* 343 */       Timer showModalDialogTimer = new Timer(blockingDialogDelay(), showModalDialog);
/* 344 */       showModalDialogTimer.setRepeats(false);
/* 345 */       showModalDialogTimer.start();
/*     */ 
/*     */     }
/* 348 */     else if (this.modalDialog != null) {
/* 349 */       this.modalDialog.dispose();
/* 350 */       this.modalDialog = null;
/*     */     }
/*     */     else {
/* 353 */       String msg = String.format("unexpected InputBlocker state [%s] %s", new Object[] { Boolean.valueOf(f), this });
/* 354 */       logger.warning(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void block()
/*     */   {
/* 360 */     switch (getScope()) {
/*     */     case ACTION: 
/* 362 */       setActionTargetBlocked(true);
/* 363 */       break;
/*     */     case COMPONENT: 
/* 365 */       setComponentTargetBlocked(true);
/* 366 */       break;
/*     */     case WINDOW: 
/*     */     case APPLICATION: 
/* 369 */       showBusyGlassPane(true);
/* 370 */       showBlockingDialog(true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void unblock()
/*     */   {
/* 376 */     switch (getScope()) {
/*     */     case ACTION: 
/* 378 */       setActionTargetBlocked(false);
/* 379 */       break;
/*     */     case COMPONENT: 
/* 381 */       setComponentTargetBlocked(false);
/* 382 */       break;
/*     */     case WINDOW: 
/*     */     case APPLICATION: 
/* 385 */       showBusyGlassPane(false);
/* 386 */       showBlockingDialog(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\DefaultInputBlocker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */