/*  1:   */ package nl.invisible.keygen.gui;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.Frame;
/*  5:   */ import java.awt.Toolkit;
/*  6:   */ import javax.swing.ActionMap;
/*  7:   */ import javax.swing.GroupLayout;
/*  8:   */ import javax.swing.GroupLayout.Alignment;
/*  9:   */ import javax.swing.GroupLayout.ParallelGroup;
/* 10:   */ import javax.swing.GroupLayout.SequentialGroup;
/* 11:   */ import javax.swing.JButton;
/* 12:   */ import javax.swing.JDialog;
/* 13:   */ import javax.swing.JLabel;
/* 14:   */ import javax.swing.JRootPane;
/* 15:   */ import javax.swing.LayoutStyle.ComponentPlacement;
/* 16:   */ import org.jdesktop.application.Action;
/* 17:   */ import org.jdesktop.application.Application;
/* 18:   */ import org.jdesktop.application.ApplicationContext;
/* 19:   */ import org.jdesktop.application.ResourceMap;
/* 20:   */ 
/* 21:   */ public class AboutBox
/* 22:   */   extends JDialog
/* 23:   */ {
/* 24:   */   private JButton closeButton;
/* 25:   */   private JLabel invisibleImage;
/* 26:   */   
/* 27:   */   public AboutBox(Frame parent)
/* 28:   */   {
/* 29:13 */     super(parent);
/* 30:14 */     initComponents();
/* 31:15 */     getRootPane().setDefaultButton(this.closeButton);
/* 32:16 */     setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/nl/invisible/keygen/gui/resources/invblue-icon.png")));
/* 33:   */   }
/* 34:   */   
/* 35:   */   @Action
/* 36:   */   public void closeAboutBox()
/* 37:   */   {
/* 38:23 */     dispose();
/* 39:   */   }
/* 40:   */   
/* 41:   */   private void initComponents()
/* 42:   */   {
/* 43:34 */     this.closeButton = new JButton();
/* 44:35 */     this.invisibleImage = new JLabel();
/* 45:   */     
/* 46:37 */     setDefaultCloseOperation(2);
/* 47:38 */     ResourceMap resourceMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getResourceMap(AboutBox.class);
/* 48:39 */     setTitle(resourceMap.getString("title", new Object[0]));
/* 49:40 */     setModal(true);
/* 50:41 */     setName("aboutBox");
/* 51:42 */     setResizable(false);
/* 52:   */     
/* 53:44 */     ActionMap actionMap = ((MainApp)Application.getInstance(MainApp.class)).getContext().getActionMap(AboutBox.class, this);
/* 54:45 */     this.closeButton.setAction(actionMap.get("closeAboutBox"));
/* 55:46 */     this.closeButton.setFont(resourceMap.getFont("closeButton.font"));
/* 56:47 */     this.closeButton.setText(resourceMap.getString("closeButton.text", new Object[0]));
/* 57:48 */     this.closeButton.setName("closeButton");
/* 58:   */     
/* 59:50 */     this.invisibleImage.setIcon(resourceMap.getIcon("invisibleImage.icon"));
/* 60:51 */     this.invisibleImage.setText(resourceMap.getString("invisibleImage.text", new Object[0]));
/* 61:52 */     this.invisibleImage.setToolTipText(resourceMap.getString("invisibleImage.toolTipText", new Object[0]));
/* 62:53 */     this.invisibleImage.setName("invisibleImage");
/* 63:   */     
/* 64:55 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 65:56 */     getContentPane().setLayout(layout);
/* 66:57 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.closeButton).addComponent(this.invisibleImage)).addContainerGap(-1, 32767)));
/* 67:   */     
/* 68:   */ 
/* 69:   */ 
/* 70:   */ 
/* 71:   */ 
/* 72:   */ 
/* 73:   */ 
/* 74:   */ 
/* 75:66 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.invisibleImage).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.closeButton).addContainerGap(-1, 32767)));
/* 76:   */     
/* 77:   */ 
/* 78:   */ 
/* 79:   */ 
/* 80:   */ 
/* 81:   */ 
/* 82:   */ 
/* 83:   */ 
/* 84:   */ 
/* 85:76 */     pack();
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.gui.AboutBox
 * JD-Core Version:    0.7.0.1
 */