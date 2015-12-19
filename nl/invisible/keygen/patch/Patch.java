/*   1:    */ package nl.invisible.keygen.patch;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.util.zip.ZipEntry;
/*  11:    */ import java.util.zip.ZipInputStream;
/*  12:    */ import java.util.zip.ZipOutputStream;
/*  13:    */ 
/*  14:    */ public final class Patch
/*  15:    */ {
/*  16: 25 */   private final int BUFFER = 4096;
/*  17:    */   
/*  18:    */   public final boolean patchFile(String jarFilePath)
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 38 */       File jarFile = new File(jarFilePath);
/*  23: 39 */       File temp = File.createTempFile("patch", null);
/*  24:    */       
/*  25: 41 */       ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));
/*  26: 42 */       ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(temp));
/*  27:    */       
/*  28: 44 */       zos.setLevel(9);
/*  29: 45 */       zos.setMethod(8);
/*  30:    */       
/*  31: 47 */       ZipEntry next = null;
/*  32: 48 */       while ((next = zis.getNextEntry()) != null)
/*  33:    */       {
/*  34: 51 */         ZipEntry current = new ZipEntry(next.getName());
/*  35: 52 */         current.setComment(next.getComment());
/*  36: 53 */         current.setTime(next.getTime());
/*  37: 54 */         current.setExtra(next.getExtra());
/*  38:    */         
/*  39: 56 */         zos.putNextEntry(current);
/*  40: 58 */         if (next.getName().equals("com/atlassian/extras/decoder/v2/Version2LicenseDecoder.class")) {
/*  41: 59 */           writeToStream(getClass().getResourceAsStream("/nl/invisible/keygen/patch/resources/Version2LicenseDecoder.class"), zos);
/*  42:    */         } else {
/*  43: 65 */           writeToStream(zis, zos);
/*  44:    */         }
/*  45: 68 */         zis.closeEntry();
/*  46: 69 */         zos.closeEntry();
/*  47:    */       }
/*  48: 72 */       zis.close();
/*  49: 73 */       zos.flush();
/*  50: 74 */       zos.close();
/*  51:    */       
/*  52: 76 */       temp.setLastModified(jarFile.lastModified());
/*  53: 78 */       if (!jarFile.renameTo(new File(jarFile.getPath().substring(0, jarFile.getPath().lastIndexOf('.') + 1) + "bak"))) {
/*  54: 79 */         return false;
/*  55:    */       }
/*  56: 81 */       if (!temp.renameTo(jarFile)) {
/*  57: 82 */         return false;
/*  58:    */       }
/*  59:    */     }
/*  60:    */     catch (FileNotFoundException ex)
/*  61:    */     {
/*  62: 85 */       ex.printStackTrace();
/*  63: 86 */       return false;
/*  64:    */     }
/*  65:    */     catch (IOException ex)
/*  66:    */     {
/*  67: 88 */       ex.printStackTrace();
/*  68: 89 */       return false;
/*  69:    */     }
/*  70: 92 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private final void writeToStream(InputStream is, OutputStream os)
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77: 97 */       byte[] buffer = new byte[4096];
/*  78:    */       int currentByte;
/*  79:100 */       while ((currentByte = is.read(buffer)) != -1) {
/*  80:101 */         os.write(buffer, 0, currentByte);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     catch (IOException ex)
/*  84:    */     {
/*  85:104 */       ex.printStackTrace();
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.patch.Patch
 * JD-Core Version:    0.7.0.1
 */