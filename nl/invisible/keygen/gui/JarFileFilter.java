/*    */ package nl.invisible.keygen.gui;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JarFileFilter
/*    */   extends FileFilter
/*    */ {
/*    */   public boolean accept(File f)
/*    */   {
/* 19 */     return (f.getName().equals("atlassian-extras-2.4.jar")) || (f.isDirectory());
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 25 */     return "Patchable Java Archive (atlassian-extras-2.4.jar)";
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\gui\JarFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */