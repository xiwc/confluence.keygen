/*     */ package nl.invisible.keygen.license;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterInputStream;
/*     */ import org.apache.commons.codec.binary.Base64;
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
/*     */ public final class LicenseFile
/*     */   implements KeyConstants
/*     */ {
/*     */   private final byte[] zipLicense(byte[] lic)
/*     */   {
/*  41 */     zLic = null;
/*  42 */     byte[] buf = new byte[64];
/*     */     
/*  44 */     ByteArrayInputStream bis = new ByteArrayInputStream(lic);
/*  45 */     DeflaterInputStream dis = new DeflaterInputStream(bis, new Deflater());
/*  46 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */     try
/*     */     {
/*     */       int len;
/*  50 */       while ((len = dis.read(buf)) > 0) {
/*  51 */         bos.write(buf, 0, len);
/*     */       }
/*     */       
/*  54 */       return bos.toByteArray();
/*     */     } catch (IOException ex) {
/*  56 */       ex.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  59 */         bis.close();
/*  60 */         dis.close();
/*  61 */         bos.close();
/*     */       } catch (IOException ex) {
/*  63 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final String split(String licenseData)
/*     */   {
/*  71 */     if ((licenseData == null) || (licenseData.length() == 0)) {
/*  72 */       return licenseData;
/*     */     }
/*  74 */     char[] chars = licenseData.toCharArray();
/*  75 */     StringBuffer buf = new StringBuffer(chars.length + chars.length / 76);
/*  76 */     for (int i = 0; i < chars.length; i++) {
/*  77 */       buf.append(chars[i]);
/*  78 */       if ((i > 0) && (i % 76 == 0)) {
/*  79 */         buf.append('\n');
/*     */       }
/*     */     }
/*     */     
/*  83 */     return buf.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public final String genLicense(String username, String email, String org, String sid)
/*     */   {
/*  89 */     byte[] signature = null;
/*     */     
/*  91 */     String licStr = null;String date = new Date().toString();
/*  92 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  93 */     DataOutputStream dOut = new DataOutputStream(out);
/*     */     
/*     */ 
/*  96 */     byte[] lic = MessageFormat.format("#{0}\nDescription=Confluence\nNumberOfUsers=-1\nCreationDate={1}\nContactName={2}\nconf.active=true\nContactEMail={3}\nEvaluation=false\nconf.LicenseTypeName=COMMERCIAL\nMaintenanceExpiryDate=2337-12-25\nconf.NumberOfClusterNodes=0\nSEN=YOU MAKE ME A SAD PANDA.\nOrganisation={4}\nServerID={5}\nLicenseID=LID\nLicenseExpiryDate=2337-12-25\nPurchaseDate={6}", new Object[] { date, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), username, email, org, sid, new SimpleDateFormat("yyyy-MM-dd").format(new Date()) }).getBytes();
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
/* 108 */     byte[] zlic = zipLicense(lic);
/*     */     
/*     */ 
/* 111 */     byte[] text = new byte[zlic.length + 5];
/*     */     
/*     */ 
/* 114 */     text[0] = 13;
/* 115 */     text[1] = 14;
/* 116 */     text[2] = 12;
/* 117 */     text[3] = 10;
/* 118 */     text[4] = 15;
/*     */     
/*     */ 
/* 121 */     System.arraycopy(zlic, 0, text, 5, zlic.length);
/*     */     
/*     */     try
/*     */     {
/* 125 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/* 126 */       byte[] rawPrivateKey = Base64.decodeBase64(DSA_KEY[1]);
/*     */       
/* 128 */       EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(rawPrivateKey);
/* 129 */       PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
/*     */       
/* 131 */       Signature sig = Signature.getInstance("SHA1withDSA");
/* 132 */       sig.initSign(privateKey);
/* 133 */       sig.update(text);
/*     */       
/* 135 */       signature = sig.sign();
/*     */     } catch (SignatureException ex) {
/* 137 */       ex.printStackTrace();
/*     */     } catch (InvalidKeyException ex) {
/* 139 */       ex.printStackTrace();
/*     */     } catch (InvalidKeySpecException ex) {
/* 141 */       ex.printStackTrace();
/*     */     } catch (NoSuchAlgorithmException ex) {
/* 143 */       ex.printStackTrace();
/*     */     }
/*     */     try
/*     */     {
/* 147 */       dOut.writeInt(text.length);
/* 148 */       dOut.write(text);
/* 149 */       dOut.write(signature);
/*     */     } catch (IOException ex) {
/* 151 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 154 */     byte[] fullLic = out.toByteArray();
/*     */     
/* 156 */     licStr = new String(Base64.encodeBase64(fullLic));
/* 157 */     int len = licStr.length();
/* 158 */     licStr = licStr + "X02" + Integer.toString(len, 31);
/*     */     
/* 160 */     return split(licStr);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\license\LicenseFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */