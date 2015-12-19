/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.filechooser.FileSystemView;
/*    */ import javax.swing.filechooser.FileView;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicFileChooserUI.BasicFileView;
/*    */ import javax.swing.plaf.metal.MetalFileChooserUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PlasticFileChooserUI
/*    */   extends MetalFileChooserUI
/*    */ {
/* 57 */   private final BasicFileChooserUI.BasicFileView fileView = new SystemIconFileView(null);
/*    */   
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 61 */     return new PlasticFileChooserUI((JFileChooser)c);
/*    */   }
/*    */   
/*    */   public PlasticFileChooserUI(JFileChooser fileChooser)
/*    */   {
/* 66 */     super(fileChooser);
/*    */   }
/*    */   
/*    */   public void clearIconCache()
/*    */   {
/* 71 */     this.fileView.clearIconCache();
/*    */   }
/*    */   
/*    */   public FileView getFileView(JFileChooser fc)
/*    */   {
/* 76 */     return this.fileView;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private final class SystemIconFileView
/*    */     extends BasicFileChooserUI.BasicFileView
/*    */   {
/* 84 */     private SystemIconFileView() { super(); }
/*    */     
/*    */     public Icon getIcon(File f) {
/* 87 */       Icon icon = getCachedIcon(f);
/* 88 */       if (icon != null) {
/* 89 */         return icon;
/*    */       }
/* 91 */       if ((f != null) && (UIManager.getBoolean("FileChooser.useSystemIcons"))) {
/* 92 */         icon = PlasticFileChooserUI.this.getFileChooser().getFileSystemView().getSystemIcon(f);
/*    */       }
/* 94 */       if (icon == null) {
/* 95 */         return super.getIcon(f);
/*    */       }
/* 97 */       cacheIcon(f, icon);
/* 98 */       return icon;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticFileChooserUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */