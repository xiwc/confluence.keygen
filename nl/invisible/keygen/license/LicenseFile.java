/*   1:    */ package nl.invisible.keygen.license;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.DataOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.security.InvalidKeyException;
/*   8:    */ import java.security.KeyFactory;
/*   9:    */ import java.security.NoSuchAlgorithmException;
/*  10:    */ import java.security.PrivateKey;
/*  11:    */ import java.security.Signature;
/*  12:    */ import java.security.SignatureException;
/*  13:    */ import java.security.spec.EncodedKeySpec;
/*  14:    */ import java.security.spec.InvalidKeySpecException;
/*  15:    */ import java.security.spec.PKCS8EncodedKeySpec;
/*  16:    */ import java.text.MessageFormat;
/*  17:    */ import java.text.SimpleDateFormat;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.zip.Deflater;
/*  20:    */ import java.util.zip.DeflaterInputStream;
/*  21:    */ import org.apache.commons.codec.binary.Base64;
/*  22:    */ 
/*  23:    */ public final class LicenseFile
/*  24:    */   implements KeyConstants
/*  25:    */ {
/*  26:    */   private final byte[] zipLicense(byte[] lic)
/*  27:    */   {
/*  28: 41 */     zLic = null;
/*  29: 42 */     byte[] buf = new byte[64];
/*  30:    */     
/*  31: 44 */     ByteArrayInputStream bis = new ByteArrayInputStream(lic);
/*  32: 45 */     DeflaterInputStream dis = new DeflaterInputStream(bis, new Deflater());
/*  33: 46 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  34:    */     try
/*  35:    */     {
/*  36:    */       int len;
/*  37: 50 */       while ((len = dis.read(buf)) > 0) {
/*  38: 51 */         bos.write(buf, 0, len);
/*  39:    */       }
/*  40: 54 */       return bos.toByteArray();
/*  41:    */     }
/*  42:    */     catch (IOException ex)
/*  43:    */     {
/*  44: 56 */       ex.printStackTrace();
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48:    */       try
/*  49:    */       {
/*  50: 59 */         bis.close();
/*  51: 60 */         dis.close();
/*  52: 61 */         bos.close();
/*  53:    */       }
/*  54:    */       catch (IOException ex)
/*  55:    */       {
/*  56: 63 */         ex.printStackTrace();
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private final String split(String licenseData)
/*  62:    */   {
/*  63: 71 */     if ((licenseData == null) || (licenseData.length() == 0)) {
/*  64: 72 */       return licenseData;
/*  65:    */     }
/*  66: 74 */     char[] chars = licenseData.toCharArray();
/*  67: 75 */     StringBuffer buf = new StringBuffer(chars.length + chars.length / 76);
/*  68: 76 */     for (int i = 0; i < chars.length; i++)
/*  69:    */     {
/*  70: 77 */       buf.append(chars[i]);
/*  71: 78 */       if ((i > 0) && (i % 76 == 0)) {
/*  72: 79 */         buf.append('\n');
/*  73:    */       }
/*  74:    */     }
/*  75: 83 */     return buf.toString();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final String genLicense(String username, String email, String org, String sid)
/*  79:    */   {
/*  80: 89 */     byte[] signature = null;
/*  81:    */     
/*  82: 91 */     String licStr = null;String date = new Date().toString();
/*  83: 92 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  84: 93 */     DataOutputStream dOut = new DataOutputStream(out);
/*  85:    */     
/*  86:    */ 
/*  87: 96 */     byte[] lic = MessageFormat.format("#{0}\nDescription=Confluence\nNumberOfUsers=-1\nCreationDate={1}\nContactName={2}\nconf.active=true\nContactEMail={3}\nEvaluation=false\nconf.LicenseTypeName=COMMERCIAL\nMaintenanceExpiryDate=2337-12-25\nconf.NumberOfClusterNodes=0\nSEN=YOU MAKE ME A SAD PANDA.\nOrganisation={4}\nServerID={5}\nLicenseID=LID\nLicenseExpiryDate=2337-12-25\nPurchaseDate={6}", new Object[] { date, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), username, email, org, sid, new SimpleDateFormat("yyyy-MM-dd").format(new Date()) }).getBytes();
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:108 */     byte[] zlic = zipLicense(lic);
/* 100:    */     
/* 101:    */ 
/* 102:111 */     byte[] text = new byte[zlic.length + 5];
/* 103:    */     
/* 104:    */ 
/* 105:114 */     text[0] = 13;
/* 106:115 */     text[1] = 14;
/* 107:116 */     text[2] = 12;
/* 108:117 */     text[3] = 10;
/* 109:118 */     text[4] = 15;
/* 110:    */     
/* 111:    */ 
/* 112:121 */     System.arraycopy(zlic, 0, text, 5, zlic.length);
/* 113:    */     try
/* 114:    */     {
/* 115:125 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");
/* 116:126 */       byte[] rawPrivateKey = Base64.decodeBase64(DSA_KEY[1]);
/* 117:    */       
/* 118:128 */       EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(rawPrivateKey);
/* 119:129 */       PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
/* 120:    */       
/* 121:131 */       Signature sig = Signature.getInstance("SHA1withDSA");
/* 122:132 */       sig.initSign(privateKey);
/* 123:133 */       sig.update(text);
/* 124:    */       
/* 125:135 */       signature = sig.sign();
/* 126:    */     }
/* 127:    */     catch (SignatureException ex)
/* 128:    */     {
/* 129:137 */       ex.printStackTrace();
/* 130:    */     }
/* 131:    */     catch (InvalidKeyException ex)
/* 132:    */     {
/* 133:139 */       ex.printStackTrace();
/* 134:    */     }
/* 135:    */     catch (InvalidKeySpecException ex)
/* 136:    */     {
/* 137:141 */       ex.printStackTrace();
/* 138:    */     }
/* 139:    */     catch (NoSuchAlgorithmException ex)
/* 140:    */     {
/* 141:143 */       ex.printStackTrace();
/* 142:    */     }
/* 143:    */     try
/* 144:    */     {
/* 145:147 */       dOut.writeInt(text.length);
/* 146:148 */       dOut.write(text);
/* 147:149 */       dOut.write(signature);
/* 148:    */     }
/* 149:    */     catch (IOException ex)
/* 150:    */     {
/* 151:151 */       ex.printStackTrace();
/* 152:    */     }
/* 153:154 */     byte[] fullLic = out.toByteArray();
/* 154:    */     
/* 155:156 */     licStr = new String(Base64.encodeBase64(fullLic));
/* 156:157 */     int len = licStr.length();
/* 157:158 */     licStr = licStr + "X02" + Integer.toString(len, 31);
/* 158:    */     
/* 159:160 */     return split(licStr);
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.license.LicenseFile
 * JD-Core Version:    0.7.0.1
 */