/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import javax.swing.Icon;
/*  5:   */ import javax.swing.JComponent;
/*  6:   */ import javax.swing.JFileChooser;
/*  7:   */ import javax.swing.UIManager;
/*  8:   */ import javax.swing.filechooser.FileSystemView;
/*  9:   */ import javax.swing.filechooser.FileView;
/* 10:   */ import javax.swing.plaf.ComponentUI;
/* 11:   */ import javax.swing.plaf.basic.BasicFileChooserUI;
/* 12:   */ import javax.swing.plaf.basic.BasicFileChooserUI.BasicFileView;
/* 13:   */ import javax.swing.plaf.metal.MetalFileChooserUI;
/* 14:   */ 
/* 15:   */ public final class PlasticFileChooserUI
/* 16:   */   extends MetalFileChooserUI
/* 17:   */ {
/* 18:57 */   private final BasicFileChooserUI.BasicFileView fileView = new SystemIconFileView(null);
/* 19:   */   
/* 20:   */   public static ComponentUI createUI(JComponent c)
/* 21:   */   {
/* 22:61 */     return new PlasticFileChooserUI((JFileChooser)c);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public PlasticFileChooserUI(JFileChooser fileChooser)
/* 26:   */   {
/* 27:66 */     super(fileChooser);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void clearIconCache()
/* 31:   */   {
/* 32:71 */     this.fileView.clearIconCache();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public FileView getFileView(JFileChooser fc)
/* 36:   */   {
/* 37:76 */     return this.fileView;
/* 38:   */   }
/* 39:   */   
/* 40:   */   private final class SystemIconFileView
/* 41:   */     extends BasicFileChooserUI.BasicFileView
/* 42:   */   {
/* 43:   */     private SystemIconFileView()
/* 44:   */     {
/* 45:84 */       super();
/* 46:   */     }
/* 47:   */     
/* 48:   */     public Icon getIcon(File f)
/* 49:   */     {
/* 50:87 */       Icon icon = getCachedIcon(f);
/* 51:88 */       if (icon != null) {
/* 52:89 */         return icon;
/* 53:   */       }
/* 54:91 */       if ((f != null) && (UIManager.getBoolean("FileChooser.useSystemIcons"))) {
/* 55:92 */         icon = PlasticFileChooserUI.this.getFileChooser().getFileSystemView().getSystemIcon(f);
/* 56:   */       }
/* 57:94 */       if (icon == null) {
/* 58:95 */         return super.getIcon(f);
/* 59:   */       }
/* 60:97 */       cacheIcon(f, icon);
/* 61:98 */       return icon;
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticFileChooserUI
 * JD-Core Version:    0.7.0.1
 */