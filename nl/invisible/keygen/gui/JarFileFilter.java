/*  1:   */ package nl.invisible.keygen.gui;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import javax.swing.filechooser.FileFilter;
/*  5:   */ 
/*  6:   */ public class JarFileFilter
/*  7:   */   extends FileFilter
/*  8:   */ {
/*  9:   */   public boolean accept(File f)
/* 10:   */   {
/* 11:19 */     return (f.getName().equals("atlassian-extras-2.4.jar")) || (f.isDirectory());
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getDescription()
/* 15:   */   {
/* 16:25 */     return "Patchable Java Archive (atlassian-extras-2.4.jar)";
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.gui.JarFileFilter
 * JD-Core Version:    0.7.0.1
 */