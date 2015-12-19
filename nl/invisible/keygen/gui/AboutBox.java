/*    */ package nl.invisible.keygen.gui;
/*    */ 
/*    */ import javax.swing.GroupLayout;
/*    */ import javax.swing.GroupLayout.SequentialGroup;
/*    */ 
/*    */ public class AboutBox extends javax.swing.JDialog
/*    */ {
/*    */   private javax.swing.JButton closeButton;
/*    */   private javax.swing.JLabel invisibleImage;
/*    */   
/*    */   public AboutBox(java.awt.Frame parent)
/*    */   {
/* 13 */     super(parent);
/* 14 */     initComponents();
/* 15 */     getRootPane().setDefaultButton(this.closeButton);
/* 16 */     setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/nl/invisible/keygen/gui/resources/invblue-icon.png")));
/*    */   }
/*    */   
/*    */ 
/*    */   @org.jdesktop.application.Action
/*    */   public void closeAboutBox()
/*    */   {
/* 23 */     dispose();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void initComponents()
/*    */   {
/* 34 */     this.closeButton = new javax.swing.JButton();
/* 35 */     this.invisibleImage = new javax.swing.JLabel();
/*    */     
/* 37 */     setDefaultCloseOperation(2);
/* 38 */     org.jdesktop.application.ResourceMap resourceMap = ((MainApp)org.jdesktop.application.Application.getInstance(MainApp.class)).getContext().getResourceMap(AboutBox.class);
/* 39 */     setTitle(resourceMap.getString("title", new Object[0]));
/* 40 */     setModal(true);
/* 41 */     setName("aboutBox");
/* 42 */     setResizable(false);
/*    */     
/* 44 */     javax.swing.ActionMap actionMap = ((MainApp)org.jdesktop.application.Application.getInstance(MainApp.class)).getContext().getActionMap(AboutBox.class, this);
/* 45 */     this.closeButton.setAction(actionMap.get("closeAboutBox"));
/* 46 */     this.closeButton.setFont(resourceMap.getFont("closeButton.font"));
/* 47 */     this.closeButton.setText(resourceMap.getString("closeButton.text", new Object[0]));
/* 48 */     this.closeButton.setName("closeButton");
/*    */     
/* 50 */     this.invisibleImage.setIcon(resourceMap.getIcon("invisibleImage.icon"));
/* 51 */     this.invisibleImage.setText(resourceMap.getString("invisibleImage.text", new Object[0]));
/* 52 */     this.invisibleImage.setToolTipText(resourceMap.getString("invisibleImage.toolTipText", new Object[0]));
/* 53 */     this.invisibleImage.setName("invisibleImage");
/*    */     
/* 55 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 56 */     getContentPane().setLayout(layout);
/* 57 */     layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(this.closeButton).addComponent(this.invisibleImage)).addContainerGap(-1, 32767)));
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 66 */     layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.invisibleImage).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.closeButton).addContainerGap(-1, 32767)));
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 76 */     pack();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\gui\AboutBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */