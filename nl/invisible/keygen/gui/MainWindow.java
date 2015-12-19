/*     */ package nl.invisible.keygen.gui;
/*     */ 
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class MainWindow extends org.jdesktop.application.FrameView implements nl.invisible.keygen.license.KeyConstants {
/*     */   private javax.swing.JButton aboutButton;
/*     */   private JLabel appNameLabel;
/*     */   private javax.swing.JButton cancelButton;
/*     */   private JLabel emailLabel;
/*     */   private javax.swing.JTextField emailText;
/*     */   private javax.swing.JButton genButton;
/*     */   private javax.swing.JTextArea keyBox;
/*     */   private javax.swing.JScrollPane keyBoxScrollPane;
/*     */   private JLabel keyLabel;
/*     */   private javax.swing.JPanel mainPanel;
/*     */   private JLabel nameLabel;
/*     */   private javax.swing.JTextField nameText;
/*     */   private JLabel orgLabel;
/*     */   private javax.swing.JTextField orgText;
/*     */   private javax.swing.JButton patchButton;
/*     */   private JLabel serverIDLabel;
/*     */   private javax.swing.JTextField serverIDText;
/*     */   private JLabel statusLabel;
/*     */   private javax.swing.JDialog aboutBox;
/*     */   
/*     */   public MainWindow(org.jdesktop.application.SingleFrameApplication app) {
/*  27 */     super(app);
/*  28 */     initComponents();
/*  29 */     getFrame().setResizable(false);
/*  30 */     getFrame().setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/nl/invisible/keygen/gui/resources/invblue-icon.png")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @org.jdesktop.application.Action
/*     */   public void showAboutBox()
/*     */   {
/*  38 */     if (this.aboutBox == null) {
/*  39 */       javax.swing.JFrame mainFrame = MainApp.getApplication().getMainFrame();
/*  40 */       this.aboutBox = new AboutBox(mainFrame);
/*  41 */       this.aboutBox.setLocationRelativeTo(mainFrame);
/*     */     }
/*  43 */     MainApp.getApplication().show(this.aboutBox);
/*     */   }
/*     */   
/*     */   @org.jdesktop.application.Action
/*     */   public void patchApp() {
/*  48 */     nl.invisible.keygen.patch.Patch patch = new nl.invisible.keygen.patch.Patch();
/*     */     
/*  50 */     this.patchButton.setEnabled(false);
/*  51 */     this.statusLabel.setText("Patching...");
/*     */     
/*  53 */     if ((showFileChooser()) && (patch.patchFile(this.jarFilePath))) {
/*  54 */       this.statusLabel.setText("Jar successfully patched.");
/*     */     } else {
/*  56 */       this.statusLabel.setText("Error patching jar.");
/*  57 */       this.patchButton.setEnabled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @org.jdesktop.application.Action
/*     */   public void genLicense() {
/*  63 */     String name = this.nameText.getText().trim();
/*  64 */     String email = this.emailText.getText().trim();
/*  65 */     String org = this.orgText.getText().trim();
/*  66 */     String sid = this.serverIDText.getText().trim();
/*     */     
/*  68 */     if ((name.equals("")) || (name == null)) {
/*  69 */       this.statusLabel.setText("Enter your name.");
/*  70 */     } else if (name.length() < 5) {
/*  71 */       this.statusLabel.setText("Name too short.");
/*  72 */     } else if (name.length() > 100) {
/*  73 */       this.statusLabel.setText("Name too long.");
/*  74 */     } else if ((email.equals("")) || (email == null)) {
/*  75 */       this.statusLabel.setText("Enter your email.");
/*  76 */     } else if (!email.contains("@")) {
/*  77 */       this.statusLabel.setText("Enter a real email.");
/*  78 */     } else if (email.length() < 5) {
/*  79 */       this.statusLabel.setText("Email too short.");
/*  80 */     } else if (email.length() > 100) {
/*  81 */       this.statusLabel.setText("Email too long.");
/*  82 */     } else if ((org.equals("")) || (org == null)) {
/*  83 */       this.statusLabel.setText("Enter your org.");
/*  84 */     } else if (org.length() < 5) {
/*  85 */       this.statusLabel.setText("Org too short.");
/*  86 */     } else if (org.length() > 100) {
/*  87 */       this.statusLabel.setText("Org too long.");
/*  88 */     } else if ((sid.equals("")) || (sid == null)) {
/*  89 */       this.statusLabel.setText("Enter your SID.");
/*  90 */     } else if (sid.length() != 19) {
/*  91 */       this.statusLabel.setText("Enter a real SID.");
/*     */     } else {
/*  93 */       nl.invisible.keygen.license.LicenseFile key = new nl.invisible.keygen.license.LicenseFile();
/*     */       
/*  95 */       this.keyBox.setText(key.genLicense(name, email, org, sid));
/*  96 */       this.statusLabel.setText("Key successfully generated.");
/*     */     }
/*     */   }
/*     */   
/*     */   private final boolean showFileChooser() {
/* 101 */     javax.swing.JFrame mainFrame = MainApp.getApplication().getMainFrame();
/* 102 */     javax.swing.filechooser.FileFilter filter = new JarFileFilter();
/* 103 */     boolean ret = false;
/* 104 */     java.io.File file = null;
/*     */     
/* 106 */     if (this.firstTimeCallingFChooser) {
/* 107 */       this.fileChooser = new javax.swing.JFileChooser();
/* 108 */       this.fileChooser.setFileFilter(filter);
/* 109 */       this.firstTimeCallingFChooser = false;
/*     */     }
/*     */     
/* 112 */     this.fileChooser.setDialogTitle("Select the Jar File to Patch");
/* 113 */     this.fileChooser.setFileSelectionMode(2);
/*     */     
/* 115 */     int returnVal = this.fileChooser.showOpenDialog(mainFrame);
/*     */     
/* 117 */     if (returnVal == 1) {
/* 118 */       return false;
/*     */     }
/*     */     do
/*     */     {
/* 122 */       file = this.fileChooser.getSelectedFile();
/*     */       
/* 124 */       if (file.isDirectory()) {
/* 125 */         this.fileChooser.setCurrentDirectory(file);
/* 126 */         returnVal = this.fileChooser.showOpenDialog(mainFrame);
/*     */       }
/*     */       else {
/* 129 */         if (!file.getName().endsWith(".jar")) {
/* 130 */           this.statusLabel.setText("Select a '.jar' file.");
/* 131 */           break; }
/* 132 */         if (!file.getName().equals("atlassian-extras-2.4.jar")) {
/* 133 */           this.statusLabel.setText("Select 'atlassian-extras-2.4.jar'.");
/* 134 */           break;
/*     */         }
/* 136 */         this.jarFilePath = file.getAbsolutePath();
/* 137 */         ret = true;
/*     */         
/* 139 */         break;
/*     */       }
/* 141 */     } while (returnVal == 0);
/*     */     
/* 143 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 155 */     this.mainPanel = new javax.swing.JPanel();
/* 156 */     this.appNameLabel = new JLabel();
/* 157 */     this.nameLabel = new JLabel();
/* 158 */     this.nameText = new javax.swing.JTextField();
/* 159 */     this.cancelButton = new javax.swing.JButton();
/* 160 */     this.genButton = new javax.swing.JButton();
/* 161 */     this.statusLabel = new JLabel();
/* 162 */     this.keyLabel = new JLabel();
/* 163 */     this.keyBoxScrollPane = new javax.swing.JScrollPane();
/* 164 */     this.keyBox = new javax.swing.JTextArea();
/* 165 */     this.aboutButton = new javax.swing.JButton();
/* 166 */     this.patchButton = new javax.swing.JButton();
/* 167 */     this.emailText = new javax.swing.JTextField();
/* 168 */     this.emailLabel = new JLabel();
/* 169 */     this.orgLabel = new JLabel();
/* 170 */     this.orgText = new javax.swing.JTextField();
/* 171 */     this.serverIDText = new javax.swing.JTextField();
/* 172 */     this.serverIDLabel = new JLabel();
/*     */     
/* 174 */     this.mainPanel.setName("mainPanel");
/*     */     
/* 176 */     org.jdesktop.application.ResourceMap resourceMap = ((MainApp)org.jdesktop.application.Application.getInstance(MainApp.class)).getContext().getResourceMap(MainWindow.class);
/* 177 */     this.appNameLabel.setFont(resourceMap.getFont("appNameLabel.font"));
/* 178 */     this.appNameLabel.setText(resourceMap.getString("appNameLabel.text", new Object[0]));
/* 179 */     this.appNameLabel.setName("appNameLabel");
/*     */     
/* 181 */     this.nameLabel.setFont(resourceMap.getFont("nameLabel.font"));
/* 182 */     this.nameLabel.setText(resourceMap.getString("nameLabel.text", new Object[0]));
/* 183 */     this.nameLabel.setName("nameLabel");
/*     */     
/* 185 */     this.nameText.setFont(resourceMap.getFont("nameText.font"));
/* 186 */     this.nameText.setText(resourceMap.getString("nameText.text", new Object[0]));
/* 187 */     this.nameText.setToolTipText(resourceMap.getString("nameText.toolTipText", new Object[0]));
/* 188 */     this.nameText.setName(System.getProperty("user.name"));
/* 189 */     this.nameText.addMouseListener(new java.awt.event.MouseAdapter() {
/*     */       public void mouseClicked(java.awt.event.MouseEvent evt) {
/* 191 */         MainWindow.this.nameTextMouseClicked(evt);
/*     */       }
/*     */       
/* 194 */     });
/* 195 */     javax.swing.ActionMap actionMap = ((MainApp)org.jdesktop.application.Application.getInstance(MainApp.class)).getContext().getActionMap(MainWindow.class, this);
/* 196 */     this.cancelButton.setAction(actionMap.get("quit"));
/* 197 */     this.cancelButton.setFont(resourceMap.getFont("cancelButton.font"));
/* 198 */     this.cancelButton.setText(resourceMap.getString("cancelButton.text", new Object[0]));
/* 199 */     this.cancelButton.setToolTipText(resourceMap.getString("cancelButton.toolTipText", new Object[0]));
/* 200 */     this.cancelButton.setName("cancelButton");
/*     */     
/* 202 */     this.genButton.setAction(actionMap.get("genLicense"));
/* 203 */     this.genButton.setFont(resourceMap.getFont("genButton.font"));
/* 204 */     this.genButton.setText(resourceMap.getString("genButton.text", new Object[0]));
/* 205 */     this.genButton.setToolTipText(resourceMap.getString("genButton.toolTipText", new Object[0]));
/* 206 */     this.genButton.setName("genButton");
/*     */     
/* 208 */     this.statusLabel.setFont(resourceMap.getFont("statusLabel.font"));
/* 209 */     this.statusLabel.setText(resourceMap.getString("statusLabel.text", new Object[0]));
/* 210 */     this.statusLabel.setName("statusLabel");
/*     */     
/* 212 */     this.keyLabel.setFont(resourceMap.getFont("keyLabel.font"));
/* 213 */     this.keyLabel.setText(resourceMap.getString("keyLabel.text", new Object[0]));
/* 214 */     this.keyLabel.setName("keyLabel");
/*     */     
/* 216 */     this.keyBoxScrollPane.setName("keyBoxScrollPane");
/*     */     
/* 218 */     this.keyBox.setColumns(20);
/* 219 */     this.keyBox.setEditable(false);
/* 220 */     this.keyBox.setFont(resourceMap.getFont("keyBox.font"));
/* 221 */     this.keyBox.setLineWrap(true);
/* 222 */     this.keyBox.setRows(5);
/* 223 */     this.keyBox.setToolTipText(resourceMap.getString("keyBox.toolTipText", new Object[0]));
/* 224 */     this.keyBox.setName("keyBox");
/* 225 */     this.keyBox.addMouseListener(new java.awt.event.MouseAdapter() {
/*     */       public void mouseClicked(java.awt.event.MouseEvent evt) {
/* 227 */         MainWindow.this.keyBoxMouseClicked(evt);
/*     */       }
/* 229 */     });
/* 230 */     this.keyBoxScrollPane.setViewportView(this.keyBox);
/*     */     
/* 232 */     this.aboutButton.setAction(actionMap.get("showAboutBox"));
/* 233 */     this.aboutButton.setFont(resourceMap.getFont("aboutButton.font"));
/* 234 */     this.aboutButton.setText(resourceMap.getString("aboutButton.text", new Object[0]));
/* 235 */     this.aboutButton.setToolTipText(resourceMap.getString("aboutButton.toolTipText", new Object[0]));
/* 236 */     this.aboutButton.setName("aboutButton");
/*     */     
/* 238 */     this.patchButton.setAction(actionMap.get("patchApp"));
/* 239 */     this.patchButton.setFont(resourceMap.getFont("patchButton.font"));
/* 240 */     this.patchButton.setText(resourceMap.getString("patchButton.text", new Object[0]));
/* 241 */     this.patchButton.setToolTipText(resourceMap.getString("patchButton.toolTipText", new Object[0]));
/* 242 */     this.patchButton.setName("patchButton");
/*     */     
/* 244 */     this.emailText.setFont(resourceMap.getFont("emailText.font"));
/* 245 */     this.emailText.setText(resourceMap.getString("emailText.text", new Object[0]));
/* 246 */     this.emailText.setToolTipText(resourceMap.getString("emailText.toolTipText", new Object[0]));
/* 247 */     this.emailText.setName("emailText");
/* 248 */     this.emailText.addMouseListener(new java.awt.event.MouseAdapter() {
/*     */       public void mouseClicked(java.awt.event.MouseEvent evt) {
/* 250 */         MainWindow.this.emailTextMouseClicked(evt);
/*     */       }
/*     */       
/* 253 */     });
/* 254 */     this.emailLabel.setFont(resourceMap.getFont("emailLabel.font"));
/* 255 */     this.emailLabel.setText(resourceMap.getString("emailLabel.text", new Object[0]));
/* 256 */     this.emailLabel.setName("emailLabel");
/*     */     
/* 258 */     this.orgLabel.setFont(resourceMap.getFont("orgLabel.font"));
/* 259 */     this.orgLabel.setText(resourceMap.getString("orgLabel.text", new Object[0]));
/* 260 */     this.orgLabel.setName("orgLabel");
/*     */     
/* 262 */     this.orgText.setFont(resourceMap.getFont("orgText.font"));
/* 263 */     this.orgText.setText(resourceMap.getString("orgText.text", new Object[0]));
/* 264 */     this.orgText.setToolTipText(resourceMap.getString("orgText.toolTipText", new Object[0]));
/* 265 */     this.orgText.setName("orgText");
/* 266 */     this.orgText.addMouseListener(new java.awt.event.MouseAdapter() {
/*     */       public void mouseClicked(java.awt.event.MouseEvent evt) {
/* 268 */         MainWindow.this.orgTextMouseClicked(evt);
/*     */       }
/*     */       
/* 271 */     });
/* 272 */     this.serverIDText.setFont(resourceMap.getFont("serverIDText.font"));
/* 273 */     this.serverIDText.setToolTipText(resourceMap.getString("serverIDText.toolTipText", new Object[0]));
/* 274 */     this.serverIDText.setName("serverIDText");
/*     */     
/* 276 */     this.serverIDLabel.setFont(resourceMap.getFont("serverIDLabel.font"));
/* 277 */     this.serverIDLabel.setText(resourceMap.getString("serverIDLabel.text", new Object[0]));
/* 278 */     this.serverIDLabel.setName("serverIDLabel");
/*     */     
/* 280 */     javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(this.mainPanel);
/* 281 */     this.mainPanel.setLayout(mainPanelLayout);
/* 282 */     mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(this.appNameLabel, -1, 432, 32767).addGroup(mainPanelLayout.createSequentialGroup().addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(this.nameLabel).addComponent(this.emailLabel).addComponent(this.orgLabel).addComponent(this.serverIDLabel).addComponent(this.keyLabel)).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(this.serverIDText, javax.swing.GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.orgText, javax.swing.GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.emailText, javax.swing.GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.nameText, javax.swing.GroupLayout.Alignment.LEADING, -1, 349, 32767).addComponent(this.keyBoxScrollPane, javax.swing.GroupLayout.Alignment.LEADING, -1, 349, 32767))).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup().addComponent(this.statusLabel, -1, 126, 32767).addGap(18, 18, 18).addComponent(this.genButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.patchButton).addGap(18, 18, 18).addComponent(this.aboutButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton))).addContainerGap()));
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
/* 314 */     mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.appNameLabel).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(this.nameLabel).addComponent(this.nameText, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(this.emailLabel).addComponent(this.emailText, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(this.orgLabel).addComponent(this.orgText, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(this.serverIDLabel).addComponent(this.serverIDText, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, 32767).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(this.keyLabel).addComponent(this.keyBoxScrollPane, -2, -1, -2)).addGap(18, 18, 18).addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.aboutButton).addComponent(this.patchButton).addComponent(this.genButton).addComponent(this.statusLabel)).addContainerGap()));
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
/* 349 */     setComponent(this.mainPanel);
/*     */   }
/*     */   
/*     */   private void keyBoxMouseClicked(java.awt.event.MouseEvent evt) {
/* 353 */     if (evt.getButton() == 1) {
/*     */       String key;
/* 355 */       if (((key = this.keyBox.getText().trim()).equals("")) || (key == null)) {
/* 356 */         return;
/*     */       }
/*     */       
/* 359 */       this.keyBox.requestFocus();
/* 360 */       this.keyBox.selectAll();
/* 361 */       this.keyBox.copy();
/* 362 */       this.keyBox.select(0, 0);
/*     */       
/* 364 */       this.statusLabel.setText("Key copied to clipboard.");
/*     */     }
/*     */   }
/*     */   
/*     */   private void orgTextMouseClicked(java.awt.event.MouseEvent evt) {
/* 369 */     if ((evt.getButton() == 1) && 
/* 370 */       (this.orgText.getText().equals("iNViSiBLE TEAM"))) {
/* 371 */       this.orgText.setText("");
/*     */     }
/*     */   }
/*     */   
/*     */   private void emailTextMouseClicked(java.awt.event.MouseEvent evt) {
/* 376 */     if ((evt.getButton() == 1) && 
/* 377 */       (this.emailText.getText().equals("invisible@scene.nl"))) {
/* 378 */       this.emailText.setText("");
/*     */     }
/*     */   }
/*     */   
/*     */   private void nameTextMouseClicked(java.awt.event.MouseEvent evt) {
/* 383 */     if ((evt.getButton() == 1) && 
/* 384 */       (this.nameText.getText().equals(System.getProperty("user.name")))) {
/* 385 */       this.nameText.setText("");
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
/* 410 */   private boolean firstTimeCallingFChooser = true;
/*     */   private javax.swing.JFileChooser fileChooser;
/*     */   private String jarFilePath;
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\gui\MainWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */