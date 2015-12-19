/*     */ package org.apache.commons.codec.digest;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.codec.binary.StringUtils;
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
/*     */ public class DigestUtils
/*     */ {
/*     */   private static final int STREAM_BUFFER_LENGTH = 1024;
/*     */   
/*     */   private static byte[] digest(MessageDigest digest, InputStream data)
/*     */     throws IOException
/*     */   {
/*  50 */     byte[] buffer = new byte['Ѐ'];
/*  51 */     int read = data.read(buffer, 0, 1024);
/*     */     
/*  53 */     while (read > -1) {
/*  54 */       digest.update(buffer, 0, read);
/*  55 */       read = data.read(buffer, 0, 1024);
/*     */     }
/*     */     
/*  58 */     return digest.digest();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static byte[] getBytesUtf8(String data)
/*     */   {
/*  69 */     return StringUtils.getBytesUtf8(data);
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
/*     */   static MessageDigest getDigest(String algorithm)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       return MessageDigest.getInstance(algorithm);
/*     */     } catch (NoSuchAlgorithmException e) {
/*  89 */       throw new RuntimeException(e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static MessageDigest getMd5Digest()
/*     */   {
/* 101 */     return getDigest("MD5");
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
/*     */   private static MessageDigest getSha256Digest()
/*     */   {
/* 115 */     return getDigest("SHA-256");
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
/*     */   private static MessageDigest getSha384Digest()
/*     */   {
/* 129 */     return getDigest("SHA-384");
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
/*     */   private static MessageDigest getSha512Digest()
/*     */   {
/* 143 */     return getDigest("SHA-512");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static MessageDigest getShaDigest()
/*     */   {
/* 154 */     return getDigest("SHA");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md5(byte[] data)
/*     */   {
/* 165 */     return getMd5Digest().digest(data);
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
/*     */   public static byte[] md5(InputStream data)
/*     */     throws IOException
/*     */   {
/* 179 */     return digest(getMd5Digest(), data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md5(String data)
/*     */   {
/* 190 */     return md5(getBytesUtf8(data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Hex(byte[] data)
/*     */   {
/* 201 */     return Hex.encodeHexString(md5(data));
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
/*     */   public static String md5Hex(InputStream data)
/*     */     throws IOException
/*     */   {
/* 215 */     return Hex.encodeHexString(md5(data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Hex(String data)
/*     */   {
/* 226 */     return Hex.encodeHexString(md5(data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] sha(byte[] data)
/*     */   {
/* 237 */     return getShaDigest().digest(data);
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
/*     */   public static byte[] sha(InputStream data)
/*     */     throws IOException
/*     */   {
/* 251 */     return digest(getShaDigest(), data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] sha(String data)
/*     */   {
/* 262 */     return sha(getBytesUtf8(data));
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
/*     */   public static byte[] sha256(byte[] data)
/*     */   {
/* 277 */     return getSha256Digest().digest(data);
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
/*     */   public static byte[] sha256(InputStream data)
/*     */     throws IOException
/*     */   {
/* 294 */     return digest(getSha256Digest(), data);
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
/*     */   public static byte[] sha256(String data)
/*     */   {
/* 309 */     return sha256(getBytesUtf8(data));
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
/*     */   public static String sha256Hex(byte[] data)
/*     */   {
/* 324 */     return Hex.encodeHexString(sha256(data));
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
/*     */   public static String sha256Hex(InputStream data)
/*     */     throws IOException
/*     */   {
/* 341 */     return Hex.encodeHexString(sha256(data));
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
/*     */   public static String sha256Hex(String data)
/*     */   {
/* 356 */     return Hex.encodeHexString(sha256(data));
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
/*     */   public static byte[] sha384(byte[] data)
/*     */   {
/* 371 */     return getSha384Digest().digest(data);
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
/*     */   public static byte[] sha384(InputStream data)
/*     */     throws IOException
/*     */   {
/* 388 */     return digest(getSha384Digest(), data);
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
/*     */   public static byte[] sha384(String data)
/*     */   {
/* 403 */     return sha384(getBytesUtf8(data));
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
/*     */   public static String sha384Hex(byte[] data)
/*     */   {
/* 418 */     return Hex.encodeHexString(sha384(data));
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
/*     */   public static String sha384Hex(InputStream data)
/*     */     throws IOException
/*     */   {
/* 435 */     return Hex.encodeHexString(sha384(data));
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
/*     */   public static String sha384Hex(String data)
/*     */   {
/* 450 */     return Hex.encodeHexString(sha384(data));
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
/*     */   public static byte[] sha512(byte[] data)
/*     */   {
/* 465 */     return getSha512Digest().digest(data);
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
/*     */   public static byte[] sha512(InputStream data)
/*     */     throws IOException
/*     */   {
/* 482 */     return digest(getSha512Digest(), data);
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
/*     */   public static byte[] sha512(String data)
/*     */   {
/* 497 */     return sha512(getBytesUtf8(data));
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
/*     */   public static String sha512Hex(byte[] data)
/*     */   {
/* 512 */     return Hex.encodeHexString(sha512(data));
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
/*     */   public static String sha512Hex(InputStream data)
/*     */     throws IOException
/*     */   {
/* 529 */     return Hex.encodeHexString(sha512(data));
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
/*     */   public static String sha512Hex(String data)
/*     */   {
/* 544 */     return Hex.encodeHexString(sha512(data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String shaHex(byte[] data)
/*     */   {
/* 555 */     return Hex.encodeHexString(sha(data));
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
/*     */   public static String shaHex(InputStream data)
/*     */     throws IOException
/*     */   {
/* 569 */     return Hex.encodeHexString(sha(data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String shaHex(String data)
/*     */   {
/* 580 */     return Hex.encodeHexString(sha(data));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\digest\DigestUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */