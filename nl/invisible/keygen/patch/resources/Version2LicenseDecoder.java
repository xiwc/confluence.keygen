/*     */ package com.atlassian.extras.decoder.v2;
/*     */ 
/*     */ import com.atlassian.extras.common.LicenseException;
/*     */ import com.atlassian.extras.common.org.springframework.util.DefaultPropertiesPersister;
/*     */ import com.atlassian.extras.decoder.api.AbstractLicenseDecoder;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Properties;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version2LicenseDecoder
/*     */   extends AbstractLicenseDecoder
/*     */ {
/*     */   public static final int VERSION_NUMBER_1 = 1;
/*     */   public static final int VERSION_NUMBER_2 = 2;
/*     */   public static final int VERSION_LENGTH = 3;
/*     */   public static final int ENCODED_LICENSE_LENGTH_BASE = 31;
/*  55 */   public static final byte[] LICENSE_PREFIX = { 13, 14, 12, 10, 15 }; static { try { String pubKeyEncoded = "MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkBrKJN92XEUFWggagAhhhNtFVc/Nh/JTnB3xsQ5azfHq7UcFtPEq0ohc3vGZ7OGEQS7Ym08DB6B1DtD93CwaNdX";
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
/*     */ 
/*     */ 
/*  72 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");PUBLIC_KEY = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64("MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkBrKJN92XEUFWggagAhhhNtFVc/Nh/JTnB3xsQ5azfHq7UcFtPEq0ohc3vGZ7OGEQS7Ym08DB6B1DtD93CwaNdX".getBytes()))); } catch (NoSuchAlgorithmException e) { throw new Error(e); } catch (InvalidKeySpecException e) { throw new Error(e);
/*     */     }
/*     */   }
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
/*     */   public boolean canDecode(String licenseString)
/*     */   {
/*  89 */     licenseString = removeWhiteSpaces(licenseString);int pos = licenseString.lastIndexOf('X'); if ((pos == -1) || (pos + 3 >= licenseString.length())) return false; try { int version = Integer.parseInt(licenseString.substring(pos + 1, pos + 3)); if ((version != 1) && (version != 2)) return false; String lengthStr = licenseString.substring(pos + 3);
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
/*     */ 
/*     */ 
/*     */ 
/* 107 */       int encodedLicenseLength = Integer.valueOf(lengthStr, 31).intValue();return pos == encodedLicenseLength; } catch (NumberFormatException e) {} return false;
/*     */   }
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
/*     */   public Properties doDecode(String licenseString)
/*     */   {
/* 123 */     String encodedLicenseTextAndHash = getLicenseContent(removeWhiteSpaces(licenseString));byte[] zippedLicenseBytes = checkAndGetLicenseText(encodedLicenseTextAndHash);Reader licenseText = unzipText(zippedLicenseBytes);
/*     */     
/*     */ 
/*     */ 
/* 127 */     return loadLicenseConfiguration(licenseText);
/*     */   }
/*     */   
/*     */   protected int getLicenseVersion()
/*     */   {
/* 132 */     return 2;
/*     */   }
/*     */   
/*     */   private Reader unzipText(byte[] licenseText)
/*     */   {
/* 137 */     ByteArrayInputStream in = new ByteArrayInputStream(licenseText);in.skip(LICENSE_PREFIX.length);InflaterInputStream zipIn = new InflaterInputStream(in, new Inflater());
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 142 */       return new InputStreamReader(zipIn, "UTF-8"); } catch (UnsupportedEncodingException e) { throw new LicenseException(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getLicenseContent(String licenseString)
/*     */   {
/* 153 */     String lengthStr = licenseString.substring(licenseString.lastIndexOf('X') + 3); try { int encodedLicenseLength = Integer.valueOf(lengthStr, 31).intValue();
/*     */       
/*     */ 
/*     */ 
/* 157 */       return licenseString.substring(0, encodedLicenseLength); } catch (NumberFormatException e) { throw new LicenseException("Could NOT decode license length <" + lengthStr + ">", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private byte[] checkAndGetLicenseText(String licenseContent)
/*     */   {
/*     */     byte[] licenseText;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 170 */       byte[] decodedBytes = Base64.decodeBase64(licenseContent.getBytes());ByteArrayInputStream in = new ByteArrayInputStream(decodedBytes);DataInputStream dIn = new DataInputStream(in);int textLength = dIn.readInt();licenseText = new byte[textLength];dIn.read(licenseText);byte[] hash = new byte[dIn.available()];dIn.read(hash);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 181 */         Signature signature = Signature.getInstance("SHA1withDSA");signature.initVerify(PUBLIC_KEY);signature.update(licenseText); if (!signature.verify(hash)) throw new LicenseException("Failed to verify the license."); } catch (InvalidKeyException e) { throw new LicenseException(e); } catch (SignatureException e) { throw new LicenseException(e); } catch (NoSuchAlgorithmException e) { throw new LicenseException(e); } } catch (IOException e) { throw new LicenseException(e); } return licenseText;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final char SEPARATOR = 'X';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final PublicKey PUBLIC_KEY;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int ENCODED_LICENSE_LINE_LENGTH = 76;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Properties loadLicenseConfiguration(Reader text)
/*     */   {
/*     */     try
/*     */     {
/* 217 */       Properties props = new Properties();new DefaultPropertiesPersister().load(props, text);
/*     */       
/* 219 */       return props;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 223 */       throw new LicenseException("Could NOT load properties from reader", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String removeWhiteSpaces(String licenseData)
/*     */   {
/* 232 */     if ((licenseData == null) || (licenseData.length() == 0)) return licenseData; char[] chars = licenseData.toCharArray();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 238 */     StringBuffer buf = new StringBuffer(chars.length); for (int i = 0; i < chars.length; 
/* 239 */         i++)
/*     */     {
/* 241 */       if (!Character.isWhitespace(chars[i])) { buf.append(chars[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 247 */     return buf.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String packLicense(byte[] text, byte[] hash)
/*     */     throws LicenseException
/*     */   {
/*     */     try
/*     */     {
/* 259 */       ByteArrayOutputStream out = new ByteArrayOutputStream();DataOutputStream dOut = new DataOutputStream(out);
/*     */       
/* 261 */       dOut.writeInt(text.length);dOut.write(text);dOut.write(hash);byte[] allData = out.toByteArray();String result = new String(Base64.encodeBase64(allData)).trim();result = result + 'X' + "0" + 2 + Integer.toString(result.length(), 31);
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
/* 272 */       return split(result); } catch (IOException e) { throw new LicenseException(e);
/*     */     }
/*     */   }
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
/*     */   private static String split(String licenseData)
/*     */   {
/* 287 */     if ((licenseData == null) || (licenseData.length() == 0)) return licenseData; char[] chars = licenseData.toCharArray();StringBuffer buf = new StringBuffer(chars.length + chars.length / 76);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 294 */     for (int i = 0; i < chars.length; i++) { buf.append(chars[i]); if ((i > 0) && (i % 76 == 0)) buf.append('\n'); } return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\patch\resources\Version2LicenseDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */