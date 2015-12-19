/*     */ package nl.invisible.keygen.patch;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
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
/*     */ public final class Patch
/*     */ {
/*  25 */   private final int BUFFER = 4096;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean patchFile(String jarFilePath)
/*     */   {
/*     */     try
/*     */     {
/*  38 */       File jarFile = new File(jarFilePath);
/*  39 */       File temp = File.createTempFile("patch", null);
/*     */       
/*  41 */       ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));
/*  42 */       ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(temp));
/*     */       
/*  44 */       zos.setLevel(9);
/*  45 */       zos.setMethod(8);
/*     */       
/*  47 */       ZipEntry next = null;
/*  48 */       while ((next = zis.getNextEntry()) != null)
/*     */       {
/*     */ 
/*  51 */         ZipEntry current = new ZipEntry(next.getName());
/*  52 */         current.setComment(next.getComment());
/*  53 */         current.setTime(next.getTime());
/*  54 */         current.setExtra(next.getExtra());
/*     */         
/*  56 */         zos.putNextEntry(current);
/*     */         
/*  58 */         if (next.getName().equals("com/atlassian/extras/decoder/v2/Version2LicenseDecoder.class")) {
/*  59 */           writeToStream(getClass().getResourceAsStream("/nl/invisible/keygen/patch/resources/Version2LicenseDecoder.class"), zos);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*  65 */           writeToStream(zis, zos);
/*     */         }
/*     */         
/*  68 */         zis.closeEntry();
/*  69 */         zos.closeEntry();
/*     */       }
/*     */       
/*  72 */       zis.close();
/*  73 */       zos.flush();
/*  74 */       zos.close();
/*     */       
/*  76 */       temp.setLastModified(jarFile.lastModified());
/*     */       
/*  78 */       if (!jarFile.renameTo(new File(jarFile.getPath().substring(0, jarFile.getPath().lastIndexOf('.') + 1) + "bak"))) {
/*  79 */         return false;
/*     */       }
/*  81 */       if (!temp.renameTo(jarFile)) {
/*  82 */         return false;
/*     */       }
/*     */     } catch (FileNotFoundException ex) {
/*  85 */       ex.printStackTrace();
/*  86 */       return false;
/*     */     } catch (IOException ex) {
/*  88 */       ex.printStackTrace();
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   private final void writeToStream(InputStream is, OutputStream os) {
/*     */     try {
/*  97 */       byte[] buffer = new byte['က'];
/*     */       
/*     */       int currentByte;
/* 100 */       while ((currentByte = is.read(buffer)) != -1) {
/* 101 */         os.write(buffer, 0, currentByte);
/*     */       }
/*     */     } catch (IOException ex) {
/* 104 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\patch\Patch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */