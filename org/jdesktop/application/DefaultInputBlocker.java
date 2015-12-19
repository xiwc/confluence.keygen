/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Cursor;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.FontMetrics;
/*   9:    */ import java.awt.Insets;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.awt.event.WindowAdapter;
/*  13:    */ import java.awt.event.WindowEvent;
/*  14:    */ import java.awt.event.WindowListener;
/*  15:    */ import java.beans.PropertyChangeEvent;
/*  16:    */ import java.beans.PropertyChangeListener;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.concurrent.TimeUnit;
/*  20:    */ import java.util.logging.Logger;
/*  21:    */ import javax.swing.Action;
/*  22:    */ import javax.swing.InputVerifier;
/*  23:    */ import javax.swing.JButton;
/*  24:    */ import javax.swing.JComponent;
/*  25:    */ import javax.swing.JDialog;
/*  26:    */ import javax.swing.JMenuBar;
/*  27:    */ import javax.swing.JOptionPane;
/*  28:    */ import javax.swing.JPanel;
/*  29:    */ import javax.swing.JProgressBar;
/*  30:    */ import javax.swing.JRootPane;
/*  31:    */ import javax.swing.JTextArea;
/*  32:    */ import javax.swing.RootPaneContainer;
/*  33:    */ import javax.swing.Timer;
/*  34:    */ import javax.swing.event.MouseInputAdapter;
/*  35:    */ import javax.swing.event.MouseInputListener;
/*  36:    */ 
/*  37:    */ final class DefaultInputBlocker
/*  38:    */   extends Task.InputBlocker
/*  39:    */ {
/*  40: 43 */   private static final Logger logger = Logger.getLogger(DefaultInputBlocker.class.getName());
/*  41: 44 */   private JDialog modalDialog = null;
/*  42:    */   
/*  43:    */   DefaultInputBlocker(Task task, Task.BlockingScope scope, Object target, ApplicationAction action)
/*  44:    */   {
/*  45: 47 */     super(task, scope, target, action);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void setActionTargetBlocked(boolean f)
/*  49:    */   {
/*  50: 51 */     Action action = (Action)getTarget();
/*  51: 52 */     action.setEnabled(!f);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void setComponentTargetBlocked(boolean f)
/*  55:    */   {
/*  56: 56 */     Component c = (Component)getTarget();
/*  57: 57 */     c.setEnabled(!f);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void blockingDialogComponents(Component root, List<Component> rv)
/*  61:    */   {
/*  62: 65 */     String rootName = root.getName();
/*  63: 66 */     if ((rootName != null) && (rootName.startsWith("BlockingDialog"))) {
/*  64: 67 */       rv.add(root);
/*  65:    */     }
/*  66: 69 */     if ((root instanceof Container)) {
/*  67: 70 */       for (Component child : ((Container)root).getComponents()) {
/*  68: 71 */         blockingDialogComponents(child, rv);
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private List<Component> blockingDialogComponents(Component root)
/*  74:    */   {
/*  75: 76 */     List<Component> rv = new ArrayList();
/*  76: 77 */     blockingDialogComponents(root, rv);
/*  77: 78 */     return rv;
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void injectBlockingDialogComponents(Component root)
/*  81:    */   {
/*  82: 86 */     ResourceMap taskResourceMap = getTask().getResourceMap();
/*  83: 87 */     if (taskResourceMap != null) {
/*  84: 88 */       taskResourceMap.injectComponents(root);
/*  85:    */     }
/*  86: 90 */     ApplicationAction action = getAction();
/*  87: 91 */     if (action != null)
/*  88:    */     {
/*  89: 92 */       ResourceMap actionResourceMap = action.getResourceMap();
/*  90: 93 */       String actionName = action.getName();
/*  91: 94 */       for (Component c : blockingDialogComponents(root)) {
/*  92: 95 */         c.setName(actionName + "." + c.getName());
/*  93:    */       }
/*  94: 97 */       actionResourceMap.injectComponents(root);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private JDialog createBlockingDialog()
/*  99:    */   {
/* 100:121 */     JOptionPane optionPane = new JOptionPane();
/* 101:125 */     if (getTask().getUserCanCancel())
/* 102:    */     {
/* 103:126 */       JButton cancelButton = new JButton();
/* 104:127 */       cancelButton.setName("BlockingDialog.cancelButton");
/* 105:128 */       ActionListener doCancelTask = new ActionListener()
/* 106:    */       {
/* 107:    */         public void actionPerformed(ActionEvent ignore)
/* 108:    */         {
/* 109:130 */           DefaultInputBlocker.this.getTask().cancel(true);
/* 110:    */         }
/* 111:132 */       };
/* 112:133 */       cancelButton.addActionListener(doCancelTask);
/* 113:134 */       optionPane.setOptions(new Object[] { cancelButton });
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:137 */       optionPane.setOptions(new Object[0]);
/* 118:    */     }
/* 119:142 */     Component dialogOwner = (Component)getTarget();
/* 120:143 */     String taskTitle = getTask().getTitle();
/* 121:144 */     String dialogTitle = taskTitle == null ? "BlockingDialog" : taskTitle;
/* 122:145 */     final JDialog dialog = optionPane.createDialog(dialogOwner, dialogTitle);
/* 123:146 */     dialog.setModal(true);
/* 124:147 */     dialog.setName("BlockingDialog");
/* 125:148 */     dialog.setDefaultCloseOperation(0);
/* 126:149 */     WindowListener dialogCloseListener = new WindowAdapter()
/* 127:    */     {
/* 128:    */       public void windowClosing(WindowEvent e)
/* 129:    */       {
/* 130:151 */         if (DefaultInputBlocker.this.getTask().getUserCanCancel())
/* 131:    */         {
/* 132:152 */           DefaultInputBlocker.this.getTask().cancel(true);
/* 133:153 */           dialog.setVisible(false);
/* 134:    */         }
/* 135:    */       }
/* 136:156 */     };
/* 137:157 */     dialog.addWindowListener(dialogCloseListener);
/* 138:158 */     optionPane.setName("BlockingDialog.optionPane");
/* 139:159 */     injectBlockingDialogComponents(dialog);
/* 140:    */     
/* 141:    */ 
/* 142:    */ 
/* 143:163 */     recreateOptionPaneMessage(optionPane);
/* 144:164 */     dialog.pack();
/* 145:165 */     return dialog;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void recreateOptionPaneMessage(JOptionPane optionPane)
/* 149:    */   {
/* 150:175 */     Object message = optionPane.getMessage();
/* 151:176 */     if ((message instanceof String))
/* 152:    */     {
/* 153:177 */       Font font = optionPane.getFont();
/* 154:178 */       final JTextArea textArea = new JTextArea((String)message);
/* 155:179 */       textArea.setFont(font);
/* 156:180 */       int lh = textArea.getFontMetrics(font).getHeight();
/* 157:181 */       Insets margin = new Insets(0, 0, lh, 24);
/* 158:182 */       textArea.setMargin(margin);
/* 159:183 */       textArea.setEditable(false);
/* 160:184 */       textArea.setWrapStyleWord(true);
/* 161:185 */       textArea.setBackground(optionPane.getBackground());
/* 162:186 */       JPanel panel = new JPanel(new BorderLayout());
/* 163:187 */       panel.add(textArea, "Center");
/* 164:188 */       final JProgressBar progressBar = new JProgressBar();
/* 165:189 */       progressBar.setName("BlockingDialog.progressBar");
/* 166:190 */       progressBar.setIndeterminate(true);
/* 167:191 */       PropertyChangeListener taskPCL = new PropertyChangeListener()
/* 168:    */       {
/* 169:    */         public void propertyChange(PropertyChangeEvent e)
/* 170:    */         {
/* 171:193 */           if ("progress".equals(e.getPropertyName()))
/* 172:    */           {
/* 173:194 */             progressBar.setIndeterminate(false);
/* 174:195 */             progressBar.setValue(((Integer)e.getNewValue()).intValue());
/* 175:196 */             DefaultInputBlocker.this.updateStatusBarString(progressBar);
/* 176:    */           }
/* 177:198 */           else if ("message".equals(e.getPropertyName()))
/* 178:    */           {
/* 179:199 */             textArea.setText((String)e.getNewValue());
/* 180:    */           }
/* 181:    */         }
/* 182:202 */       };
/* 183:203 */       getTask().addPropertyChangeListener(taskPCL);
/* 184:204 */       panel.add(progressBar, "South");
/* 185:205 */       injectBlockingDialogComponents(panel);
/* 186:206 */       optionPane.setMessage(panel);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private void updateStatusBarString(JProgressBar progressBar)
/* 191:    */   {
/* 192:211 */     if (!progressBar.isStringPainted()) {
/* 193:212 */       return;
/* 194:    */     }
/* 195:220 */     String key = "progressBarStringFormat";
/* 196:221 */     if (progressBar.getClientProperty(key) == null) {
/* 197:222 */       progressBar.putClientProperty(key, progressBar.getString());
/* 198:    */     }
/* 199:224 */     String fmt = (String)progressBar.getClientProperty(key);
/* 200:225 */     if (progressBar.getValue() <= 0)
/* 201:    */     {
/* 202:226 */       progressBar.setString("");
/* 203:    */     }
/* 204:228 */     else if (fmt == null)
/* 205:    */     {
/* 206:229 */       progressBar.setString(null);
/* 207:    */     }
/* 208:    */     else
/* 209:    */     {
/* 210:232 */       double pctComplete = progressBar.getValue() / 100.0D;
/* 211:233 */       long durSeconds = getTask().getExecutionDuration(TimeUnit.SECONDS);
/* 212:234 */       long durMinutes = durSeconds / 60L;
/* 213:235 */       long remSeconds = (0.5D + durSeconds / pctComplete) - durSeconds;
/* 214:236 */       long remMinutes = remSeconds / 60L;
/* 215:237 */       String s = String.format(fmt, new Object[] { Long.valueOf(durMinutes), Long.valueOf(durSeconds - durMinutes * 60L), Long.valueOf(remMinutes), Long.valueOf(remSeconds - remMinutes * 60L) });
/* 216:    */       
/* 217:239 */       progressBar.setString(s);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   private void showBusyGlassPane(boolean f)
/* 222:    */   {
/* 223:245 */     RootPaneContainer rpc = null;
/* 224:246 */     Component root = (Component)getTarget();
/* 225:247 */     while (root != null)
/* 226:    */     {
/* 227:248 */       if ((root instanceof RootPaneContainer))
/* 228:    */       {
/* 229:249 */         rpc = (RootPaneContainer)root;
/* 230:250 */         break;
/* 231:    */       }
/* 232:252 */       root = root.getParent();
/* 233:    */     }
/* 234:254 */     if (rpc != null) {
/* 235:255 */       if (f)
/* 236:    */       {
/* 237:256 */         JMenuBar menuBar = rpc.getRootPane().getJMenuBar();
/* 238:257 */         if (menuBar != null)
/* 239:    */         {
/* 240:258 */           menuBar.putClientProperty(this, Boolean.valueOf(menuBar.isEnabled()));
/* 241:259 */           menuBar.setEnabled(false);
/* 242:    */         }
/* 243:261 */         JComponent glassPane = new BusyGlassPane();
/* 244:262 */         InputVerifier retainFocusWhileVisible = new InputVerifier()
/* 245:    */         {
/* 246:    */           public boolean verify(JComponent c)
/* 247:    */           {
/* 248:264 */             return !c.isVisible();
/* 249:    */           }
/* 250:266 */         };
/* 251:267 */         glassPane.setInputVerifier(retainFocusWhileVisible);
/* 252:268 */         Component oldGlassPane = rpc.getGlassPane();
/* 253:269 */         rpc.getRootPane().putClientProperty(this, oldGlassPane);
/* 254:270 */         rpc.setGlassPane(glassPane);
/* 255:271 */         glassPane.setVisible(true);
/* 256:272 */         glassPane.revalidate();
/* 257:    */       }
/* 258:    */       else
/* 259:    */       {
/* 260:275 */         JMenuBar menuBar = rpc.getRootPane().getJMenuBar();
/* 261:276 */         if (menuBar != null)
/* 262:    */         {
/* 263:277 */           boolean enabled = ((Boolean)menuBar.getClientProperty(this)).booleanValue();
/* 264:278 */           menuBar.putClientProperty(this, null);
/* 265:279 */           menuBar.setEnabled(enabled);
/* 266:    */         }
/* 267:281 */         Component oldGlassPane = (Component)rpc.getRootPane().getClientProperty(this);
/* 268:282 */         rpc.getRootPane().putClientProperty(this, null);
/* 269:283 */         if (!oldGlassPane.isVisible()) {
/* 270:284 */           rpc.getGlassPane().setVisible(false);
/* 271:    */         }
/* 272:286 */         rpc.setGlassPane(oldGlassPane);
/* 273:    */       }
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private static class BusyGlassPane
/* 278:    */     extends JPanel
/* 279:    */   {
/* 280:    */     BusyGlassPane()
/* 281:    */     {
/* 282:296 */       super(false);
/* 283:297 */       setVisible(false);
/* 284:298 */       setOpaque(false);
/* 285:299 */       setCursor(Cursor.getPredefinedCursor(3));
/* 286:300 */       MouseInputListener blockMouseEvents = new MouseInputAdapter() {};
/* 287:301 */       addMouseMotionListener(blockMouseEvents);
/* 288:302 */       addMouseListener(blockMouseEvents);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   private int blockingDialogDelay()
/* 293:    */   {
/* 294:313 */     Integer delay = null;
/* 295:314 */     String key = "BlockingDialogTimer.delay";
/* 296:315 */     ApplicationAction action = getAction();
/* 297:316 */     if (action != null)
/* 298:    */     {
/* 299:317 */       ResourceMap actionResourceMap = action.getResourceMap();
/* 300:318 */       String actionName = action.getName();
/* 301:319 */       delay = actionResourceMap.getInteger(actionName + "." + key);
/* 302:    */     }
/* 303:321 */     ResourceMap taskResourceMap = getTask().getResourceMap();
/* 304:322 */     if ((delay == null) && (taskResourceMap != null)) {
/* 305:323 */       delay = taskResourceMap.getInteger(key);
/* 306:    */     }
/* 307:325 */     return delay == null ? 0 : delay.intValue();
/* 308:    */   }
/* 309:    */   
/* 310:    */   private void showBlockingDialog(boolean f)
/* 311:    */   {
/* 312:329 */     if (f)
/* 313:    */     {
/* 314:330 */       if (this.modalDialog != null)
/* 315:    */       {
/* 316:331 */         String msg = String.format("unexpected InputBlocker state [%s] %s", new Object[] { Boolean.valueOf(f), this });
/* 317:332 */         logger.warning(msg);
/* 318:333 */         this.modalDialog.dispose();
/* 319:    */       }
/* 320:335 */       this.modalDialog = createBlockingDialog();
/* 321:336 */       ActionListener showModalDialog = new ActionListener()
/* 322:    */       {
/* 323:    */         public void actionPerformed(ActionEvent e)
/* 324:    */         {
/* 325:338 */           if (DefaultInputBlocker.this.modalDialog != null) {
/* 326:339 */             DefaultInputBlocker.this.modalDialog.setVisible(true);
/* 327:    */           }
/* 328:    */         }
/* 329:342 */       };
/* 330:343 */       Timer showModalDialogTimer = new Timer(blockingDialogDelay(), showModalDialog);
/* 331:344 */       showModalDialogTimer.setRepeats(false);
/* 332:345 */       showModalDialogTimer.start();
/* 333:    */     }
/* 334:348 */     else if (this.modalDialog != null)
/* 335:    */     {
/* 336:349 */       this.modalDialog.dispose();
/* 337:350 */       this.modalDialog = null;
/* 338:    */     }
/* 339:    */     else
/* 340:    */     {
/* 341:353 */       String msg = String.format("unexpected InputBlocker state [%s] %s", new Object[] { Boolean.valueOf(f), this });
/* 342:354 */       logger.warning(msg);
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   protected void block()
/* 347:    */   {
/* 348:360 */     switch (6.$SwitchMap$org$jdesktop$application$Task$BlockingScope[getScope().ordinal()])
/* 349:    */     {
/* 350:    */     case 1: 
/* 351:362 */       setActionTargetBlocked(true);
/* 352:363 */       break;
/* 353:    */     case 2: 
/* 354:365 */       setComponentTargetBlocked(true);
/* 355:366 */       break;
/* 356:    */     case 3: 
/* 357:    */     case 4: 
/* 358:369 */       showBusyGlassPane(true);
/* 359:370 */       showBlockingDialog(true);
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   protected void unblock()
/* 364:    */   {
/* 365:376 */     switch (6.$SwitchMap$org$jdesktop$application$Task$BlockingScope[getScope().ordinal()])
/* 366:    */     {
/* 367:    */     case 1: 
/* 368:378 */       setActionTargetBlocked(false);
/* 369:379 */       break;
/* 370:    */     case 2: 
/* 371:381 */       setComponentTargetBlocked(false);
/* 372:382 */       break;
/* 373:    */     case 3: 
/* 374:    */     case 4: 
/* 375:385 */       showBusyGlassPane(false);
/* 376:386 */       showBlockingDialog(false);
/* 377:    */     }
/* 378:    */   }
/* 379:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.DefaultInputBlocker
 * JD-Core Version:    0.7.0.1
 */