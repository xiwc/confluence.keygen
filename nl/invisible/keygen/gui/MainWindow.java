/*   1:    */ package nl.invisible.keygen.gui;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.event.MouseAdapter;
/*   5:    */ import java.awt.event.MouseEvent;
/*   6:    */ import java.io.File;
/*   7:    */ import javax.swing.ActionMap;
/*   8:    */ import javax.swing.GroupLayout;
/*   9:    */ import javax.swing.GroupLayout.Alignment;
/*  10:    */ import javax.swing.GroupLayout.ParallelGroup;
/*  11:    */ import javax.swing.GroupLayout.SequentialGroup;
/*  12:    */ import javax.swing.JButton;
/*  13:    */ import javax.swing.JDialog;
/*  14:    */ import javax.swing.JFileChooser;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JScrollPane;
/*  19:    */ import javax.swing.JTextArea;
/*  20:    */ import javax.swing.JTextField;
/*  21:    */ import javax.swing.LayoutStyle.ComponentPlacement;
/*  22:    */ import javax.swing.filechooser.FileFilter;
/*  23:    */ import nl.invisible.keygen.license.KeyConstants;
/*  24:    */ import nl.invisible.keygen.license.LicenseFile;
/*  25:    */ import nl.invisible.keygen.patch.Patch;
/*  26:    */ import org.jdesktop.application.Action;
/*  27:    */ import org.jdesktop.application.Application;
/*  28:    */ import org.jdesktop.application.ApplicationContext;
/*  29:    */ import org.jdesktop.application.FrameView;
/*  30:    */ import org.jdesktop.application.ResourceMap;
/*  31:    */ import org.jdesktop.application.SingleFrameApplication;
/*  32:    */ 
/*  33:    */ public class MainWindow
/*  34:    */   extends FrameView
/*  35:    */   implements KeyConstants
/*  36:    */ {
/*  37:    */   private JButton aboutButton;
/*  38:    */   private JLabel appNameLabel;
/*  39:    */   private JButton cancelButton;
/*  40:    */   private JLabel emailLabel;
/*  41:    */   private JTextField emailText;
/*  42:    */   private JButton genButton;
/*  43:    */   private JTextArea keyBox;
/*  44:    */   private JScrollPane keyBoxScrollPane;
/*  45:    */   private JLabel keyLabel;
/*  46:    */   private JPanel mainPanel;
/*  47:    */   private JLabel nameLabel;
/*  48:    */   private JTextField nameText;
/*  49:    */   private JLabel orgLabel;
/*  50:    */   private JTextField orgText;
/*  51:    */   private JButton patchButton;
/*  52:    */   private JLabel serverIDLabel;
/*  53:    */   private JTextField serverIDText;
/*  54:    */   private JLabel statusLabel;
/*  55:    */   private JDialog aboutBox;
/*  56:    */   
/*  57:    */   public MainWindow(SingleFrameApplication app)
/*  58:    */   {
/*  59: 27 */     super(app);
/*  60: 28 */     initComponents();
/*  61: 29 */     getFrame().setResizable(false);
/*  62: 30 */     getFrame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/nl/invisible/keygen/gui/resources/invblue-icon.png")));
/*  63:    */   }
/*  64:    */   
/*  65:    */   @Action
/*  66:    */   public void showAboutBox()
/*  67:    */   {
/*  68: 38 */     if (this.aboutBox == null)
/*  69:    */     {
/*  70: 39 */       JFrame mainFrame = MainApp.getApplication().getMainFrame();
/*  71: 40 */       this.aboutBox = new AboutBox(mainFrame);
/*  72: 41 */       this.aboutBox.setLocationRelativeTo(mainFrame);
/*  73:    */     }
/*  74: 43 */     MainApp.getApplication().show(this.aboutBox);
/*  75:    */   }
/*  76:    */   
/*  77:    */   @Action
/*  78:    */   public void patchApp()
/*  79:    */   {
/*  80: 48 */     Patch patch = new Patch();
/*  81:    */     
/*  82: 50 */     this.patchButton.setEnabled(false);
/*  83: 51 */     this.statusLabel.setText("Patching...");
/*  84: 53 */     if ((showFileChooser()) && (patch.patchFile(this.jarFilePath)))
/*  85:    */     {
/*  86: 54 */       this.statusLabel.setText("Jar successfully patched.");
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90: 56 */       this.statusLabel.setText("Error patching jar.");
/*  91: 57 */       this.patchButton.setEnabled(true);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   @Action
/*  96:    */   public void genLicense()
/*  97:    */   {
/*  98: 63 */     String name = this.nameText.getText().trim();
/*  99: 64 */     String email = this.emailText.getText().trim();
/* 100: 65 */     String org = this.orgText.getText().trim();
/* 101: 66 */     String sid = this.serverIDText.getText().trim();
/* 102: 68 */     if ((name.equals("")) || (name == null))
/* 103:    */     {
/* 104: 69 */       this.statusLabel.setText("Enter your name.");
/* 105:    */     }
/* 106: 70 */     else if (name.length() < 5)
/* 107:    */     {
/* 108: 71 */       this.statusLabel.setText("Name too short.");
/* 109:    */     }
/* 110: 72 */     else if (name.length() > 100)
/* 111:    */     {
/* 112: 73 */       this.statusLabel.setText("Name too long.");
/* 113:    */     }
/* 114: 74 */     else if ((email.equals("")) || (email == null))
/* 115:    */     {
/* 116: 75 */       this.statusLabel.setText("Enter your email.");
/* 117:    */     }
/* 118: 76 */     else if (!email.contains("@"))
/* 119:    */     {
/* 120: 77 */       this.statusLabel.setText("Enter a real email.");
/* 121:    */     }
/* 122: 78 */     else if (email.length() < 5)
/* 123:    */     {
/* 124: 79 */       this.statusLabel.setText("Email too short.");
/* 125:    */     }
/* 126: 80 */     else if (email.length() > 100)
/* 127:    */     {
/* 128: 81 */       this.statusLabel.setText("Email too long.");
/* 129:    */     }
/* 130: 82 */     else if ((org.equals("")) || (org == null))
/* 131:    */     {
/* 132: 83 */       this.statusLabel.setText("Enter your org.");
/* 133:    */     }
/* 134: 84 */     else if (org.length() < 5)
/* 135:    */     {
/* 136: 85 */       this.statusLabel.setText("Org too short.");
/* 137:    */     }
/* 138: 86 */     else if (org.length() > 100)
/* 139:    */     {
/* 140: 87 */       this.statusLabel.setText("Org too long.");
/* 141:    */     }
/* 142: 88 */     else if ((sid.equals("")) || (sid == null))
/* 143:    */     {
/* 144: 89 */       this.statusLabel.setText("Enter your SID.");
/* 145:    */     }
/* 146: 90 */     else if (sid.length() != 19)
/* 147:    */     {
/* 148: 91 */       this.statusLabel.setText("Enter a real SID.");
/* 149:    */     }
/* 150:    */     else
/* 151:    */     {
/* 152: 93 */       LicenseFile key = new LicenseFile();
/* 153:    */       
/* 154: 95 */       this.keyBox.setText(key.genLicense(name, email, org, sid));
/* 155: 96 */       this.statusLabel.setText("Key successfully generated.");
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private final boolean showFileChooser()
/* 160:    */   {
/* 161:101 */     JFrame mainFrame = MainApp.getApplication().getMainFrame();
/* 162:102 */     FileFilter filter = new JarFileFilter();
/* 163:103 */     boolean ret = false;
/* 164:104 */     File file = null;
/* 165:106 */     if (this.firstTimeCallingFChooser)
/* 166:    */     {
/* 167:107 */       this.fileChooser = new JFileChooser();
/* 168:108 */       this.fileChooser.setFileFilter(filter);
/* 169:109 */       this.firstTimeCallingFChooser = false;
/* 170:    */     }
/* 171:112 */     this.fileChooser.setDialogTitle("Select the Jar File to Patch");
/* 172:113 */     this.fileChooser.setFileSelectionMode(2);
/* 173:    */     
/* 174:115 */     int returnVal = this.fileChooser.showOpenDialog(mainFrame);
/* 175:117 */     if (returnVal == 1) {
/* 176:118 */       return false;
/* 177:    */     }
/* 178:    */     do
/* 179:    */     {
/* 180:122 */       file = this.fileChooser.getSelectedFile();
/* 181:124 */       if (file.isDirectory())
/* 182:    */       {
/* 183:125 */         this.fileChooser.setCurrentDirectory(file);
/* 184:126 */         returnVal = this.fileChooser.showOpenDialog(mainFrame);
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:129 */         if (!file.getName().endsWith(".jar"))
/* 189:    */         {
/* 190:130 */           this.statusLabel.setText("Select a '.jar' file.");
/* 191:131 */           break;
/* 192:    */         }
/* 193:132 */         if (!file.getName().equals("atlassian-extras-2.4.jar"))
/* 194:    */         {
/* 195:133 */           this.statusLabel.setText("Select 'atlassian-extras-2.4.jar'.");
/* 196:134 */           break;
/* 197:    */         }
/* 198:136 */         this.jarFilePath = file.getAbsolutePath();
/* 199:137 */         ret = true;
/* 200:    */         
/* 201:139 */         break;
/* 202:    */       }
/* 203:141 */     } while (returnVal == 0);
/* 204:143 */     return ret;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private void initComponents()
/* 208:    */   {
/* 209:155 */     this.mainPanel = new JPanel();
/* 210:156 */     this.appNameLabel = new JLabel();
/* 211:157 */     this.nameLabel = new JLabel();
/* 212:158 */     this.nameText = new JTextField();
/* 213:159 */     this.cancelButton = new JButton();
/* 214:160 */     this.genButton = new JButton();
/* 215:161 */     this.statusLabel = new JLabel();
/* 216:162 */     this.keyLabel = new JLabel();
/* 217:163 */     this.keyBoxScrollPane = new JScrollPane();
/* 218:164 */     this.keyBox = new JTextArea();
/* 219:165 */     this.aboutButton = new JButton();
/* 220:166 */     this.patchButton = new JButton();
/* 221:167 */     this.emailText = new JTextField();
/* 222:168 */     this.emailLabel = new JLabel();
/* 223:169 */     this.orgLabel = new JLabel();
/* 224:170 */     this.orgText = new JTextField();
/* 225:171 */     this.serverIDText = new JTextField();
/* 226:172 */     this.serverIDLabel = new JLabel();
/* 227:    */     
/* 228:174 */     this.mainPanel.setName("mainPanel");
/* 229:    */     
/* 230:176 */     ResourceMap resourceMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getResourceMap(MainWindow.class);
/* 231:177 */     this.appNameLabel.setFont(resourceMap.getFont("appNameLabel.font"));
/* 232:178 */     this.appNameLabel.setText(resourceMap.getString("appNameLabel.text", new Object[0]));
/* 233:179 */     this.appNameLabel.setName("appNameLabel");
/* 234:    */     
/* 235:181 */     this.nameLabel.setFont(resourceMap.getFont("nameLabel.font"));
/* 236:182 */     this.nameLabel.setText(resourceMap.getString("nameLabel.text", new Object[0]));
/* 237:183 */     this.nameLabel.setName("nameLabel");
/* 238:    */     
/* 239:185 */     this.nameText.setFont(resourceMap.getFont("nameText.font"));
/* 240:186 */     this.nameText.setText(resourceMap.getString("nameText.text", new Object[0]));
/* 241:187 */     this.nameText.setToolTipText(resourceMap.getString("nameText.toolTipText", new Object[0]));
/* 242:188 */     this.nameText.setName(System.getProperty("user.name"));
/* 243:189 */     this.nameText.addMouseListener(new MouseAdapter()
/* 244:    */     {
/* 245:    */       public void mouseClicked(MouseEvent evt)
/* 246:    */       {
/* 247:191 */         MainWindow.this.nameTextMouseClicked(evt);
/* 248:    */       }
/* 249:194 */     });
/* 250:195 */     ActionMap actionMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getActionMap(MainWindow.class, this);
/* 251:196 */     this.cancelButton.setAction(actionMap.get("quit"));
/* 252:197 */     this.cancelButton.setFont(resourceMap.getFont("cancelButton.font"));
/* 253:198 */     this.cancelButton.setText(resourceMap.getString("cancelButton.text", new Object[0]));
/* 254:199 */     this.cancelButton.setToolTipText(resourceMap.getString("cancelButton.toolTipText", new Object[0]));
/* 255:200 */     this.cancelButton.setName("cancelButton");
/* 256:    */     
/* 257:202 */     this.genButton.setAction(actionMap.get("genLicense"));
/* 258:203 */     this.genButton.setFont(resourceMap.getFont("genButton.font"));
/* 259:204 */     this.genButton.setText(resourceMap.getString("genButton.text", new Object[0]));
/* 260:205 */     this.genButton.setToolTipText(resourceMap.getString("genButton.toolTipText", new Object[0]));
/* 261:206 */     this.genButton.setName("genButton");
/* 262:    */     
/* 263:208 */     this.statusLabel.setFont(resourceMap.getFont("statusLabel.font"));
/* 264:209 */     this.statusLabel.setText(resourceMap.getString("statusLabel.text", new Object[0]));
/* 265:210 */     this.statusLabel.setName("statusLabel");
/* 266:    */     
/* 267:212 */     this.keyLabel.setFont(resourceMap.getFont("keyLabel.font"));
/* 268:213 */     this.keyLabel.setText(resourceMap.getString("keyLabel.text", new Object[0]));
/* 269:214 */     this.keyLabel.setName("keyLabel");
/* 270:    */     
/* 271:216 */     this.keyBoxScrollPane.setName("keyBoxScrollPane");
/* 272:    */     
/* 273:218 */     this.keyBox.setColumns(20);
/* 274:219 */     this.keyBox.setEditable(false);
/* 275:220 */     this.keyBox.setFont(resourceMap.getFont("keyBox.font"));
/* 276:221 */     this.keyBox.setLineWrap(true);
/* 277:222 */     this.keyBox.setRows(5);
/* 278:223 */     this.keyBox.setToolTipText(resourceMap.getString("keyBox.toolTipText", new Object[0]));
/* 279:224 */     this.keyBox.setName("keyBox");
/* 280:225 */     this.keyBox.addMouseListener(new MouseAdapter()
/* 281:    */     {
/* 282:    */       public void mouseClicked(MouseEvent evt)
/* 283:    */       {
/* 284:227 */         MainWindow.this.keyBoxMouseClicked(evt);
/* 285:    */       }
/* 286:229 */     });
/* 287:230 */     this.keyBoxScrollPane.setViewportView(this.keyBox);
/* 288:    */     
/* 289:232 */     this.aboutButton.setAction(actionMap.get("showAboutBox"));
/* 290:233 */     this.aboutButton.setFont(resourceMap.getFont("aboutButton.font"));
/* 291:234 */     this.aboutButton.setText(resourceMap.getString("aboutButton.text", new Object[0]));
/* 292:235 */     this.aboutButton.setToolTipText(resourceMap.getString("aboutButton.toolTipText", new Object[0]));
/* 293:236 */     this.aboutButton.setName("aboutButton");
/* 294:    */     
/* 295:238 */     this.patchButton.setAction(actionMap.get("patchApp"));
/* 296:239 */     this.patchButton.setFont(resourceMap.getFont("patchButton.font"));
/* 297:240 */     this.patchButton.setText(resourceMap.getString("patchButton.text", new Object[0]));
/* 298:241 */     this.patchButton.setToolTipText(resourceMap.getString("patchButton.toolTipText", new Object[0]));
/* 299:242 */     this.patchButton.setName("patchButton");
/* 300:    */     
/* 301:244 */     this.emailText.setFont(resourceMap.getFont("emailText.font"));
/* 302:245 */     this.emailText.setText(resourceMap.getString("emailText.text", new Object[0]));
/* 303:246 */     this.emailText.setToolTipText(resourceMap.getString("emailText.toolTipText", new Object[0]));
/* 304:247 */     this.emailText.setName("emailText");
/* 305:248 */     this.emailText.addMouseListener(new MouseAdapter()
/* 306:    */     {
/* 307:    */       public void mouseClicked(MouseEvent evt)
/* 308:    */       {
/* 309:250 */         MainWindow.this.emailTextMouseClicked(evt);
/* 310:    */       }
/* 311:253 */     });
/* 312:254 */     this.emailLabel.setFont(resourceMap.getFont("emailLabel.font"));
/* 313:255 */     this.emailLabel.setText(resourceMap.getString("emailLabel.text", new Object[0]));
/* 314:256 */     this.emailLabel.setName("emailLabel");
/* 315:    */     
/* 316:258 */     this.orgLabel.setFont(resourceMap.getFont("orgLabel.font"));
/* 317:259 */     this.orgLabel.setText(resourceMap.getString("orgLabel.text", new Object[0]));
/* 318:260 */     this.orgLabel.setName("orgLabel");
/* 319:    */     
/* 320:262 */     this.orgText.setFont(resourceMap.getFont("orgText.font"));
/* 321:263 */     this.orgText.setText(resourceMap.getString("orgText.text", new Object[0]));
/* 322:264 */     this.orgText.setToolTipText(resourceMap.getString("orgText.toolTipText", new Object[0]));
/* 323:265 */     this.orgText.setName("orgText");
/* 324:266 */     this.orgText.addMouseListener(new MouseAdapter()
/* 325:    */     {
/* 326:    */       public void mouseClicked(MouseEvent evt)
/* 327:    */       {
/* 328:268 */         MainWindow.this.orgTextMouseClicked(evt);
/* 329:    */       }
/* 330:271 */     });
/* 331:272 */     this.serverIDText.setFont(resourceMap.getFont("serverIDText.font"));
/* 332:273 */     this.serverIDText.setToolTipText(resourceMap.getString("serverIDText.toolTipText", new Object[0]));
/* 333:274 */     this.serverIDText.setName("serverIDText");
/* 334:    */     
/* 335:276 */     this.serverIDLabel.setFont(resourceMap.getFont("serverIDLabel.font"));
/* 336:277 */     this.serverIDLabel.setText(resourceMap.getString("serverIDLabel.text", new Object[0]));
/* 337:278 */     this.serverIDLabel.setName("serverIDLabel");
/* 338:    */     
/* 339:280 */     GroupLayout mainPanelLayout = new GroupLayout(this.mainPanel);
/* 340:281 */     this.mainPanel.setLayout(mainPanelLayout);
/* 341:282 */     mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.appNameLabel, -1, 432, 32767).addGroup(mainPanelLayout.createSequentialGroup().addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.nameLabel).addComponent(this.emailLabel).addComponent(this.orgLabel).addComponent(this.serverIDLabel).addComponent(this.keyLabel)).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.serverIDText, GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.orgText, GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.emailText, GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.nameText, GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.keyBoxScrollPane, GroupLayout.Alignment.LEADING, -1, 349, 32767))).addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup().addComponent(this.statusLabel, -1, 126, 32767).addGap(18, 18, 18).addComponent(this.genButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.patchButton).addGap(18, 18, 18).addComponent(this.aboutButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton))).addContainerGap()));
/* 342:    */     
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:314 */     mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.appNameLabel).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.nameLabel).addComponent(this.nameText, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.emailLabel).addComponent(this.emailText, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.orgLabel).addComponent(this.orgText, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.serverIDLabel).addComponent(this.serverIDText, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, 32767).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.keyLabel).addComponent(this.keyBoxScrollPane, -2, -1, -2)).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.aboutButton).addComponent(this.patchButton).addComponent(this.genButton).addComponent(this.statusLabel)).addContainerGap()));
/* 374:    */     
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:    */ 
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:349 */     setComponent(this.mainPanel);
/* 409:    */   }
/* 410:    */   
/* 411:    */   private void keyBoxMouseClicked(MouseEvent evt)
/* 412:    */   {
/* 413:353 */     if (evt.getButton() == 1)
/* 414:    */     {
/* 415:    */       String key;
/* 416:355 */       if (((key = this.keyBox.getText().trim()).equals("")) || (key == null)) {
/* 417:356 */         return;
/* 418:    */       }
/* 419:359 */       this.keyBox.requestFocus();
/* 420:360 */       this.keyBox.selectAll();
/* 421:361 */       this.keyBox.copy();
/* 422:362 */       this.keyBox.select(0, 0);
/* 423:    */       
/* 424:364 */       this.statusLabel.setText("Key copied to clipboard.");
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   private void orgTextMouseClicked(MouseEvent evt)
/* 429:    */   {
/* 430:369 */     if ((evt.getButton() == 1) && 
/* 431:370 */       (this.orgText.getText().equals("iNViSiBLE TEAM"))) {
/* 432:371 */       this.orgText.setText("");
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   private void emailTextMouseClicked(MouseEvent evt)
/* 437:    */   {
/* 438:376 */     if ((evt.getButton() == 1) && 
/* 439:377 */       (this.emailText.getText().equals("invisible@scene.nl"))) {
/* 440:378 */       this.emailText.setText("");
/* 441:    */     }
/* 442:    */   }
/* 443:    */   
/* 444:    */   private void nameTextMouseClicked(MouseEvent evt)
/* 445:    */   {
/* 446:383 */     if ((evt.getButton() == 1) && 
/* 447:384 */       (this.nameText.getText().equals(System.getProperty("user.name")))) {
/* 448:385 */       this.nameText.setText("");
/* 449:    */     }
/* 450:    */   }
/* 451:    */   
/* 452:410 */   private boolean firstTimeCallingFChooser = true;
/* 453:    */   private JFileChooser fileChooser;
/* 454:    */   private String jarFilePath;
/* 455:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.gui.MainWindow
 * JD-Core Version:    0.7.0.1
 */