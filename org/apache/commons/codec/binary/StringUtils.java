/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringUtils
/*     */ {
/*     */   public static byte[] getBytesIso8859_1(String string)
/*     */   {
/*  49 */     return getBytesUnchecked(string, "ISO-8859-1");
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
/*     */   public static byte[] getBytesUsAscii(String string)
/*     */   {
/*  65 */     return getBytesUnchecked(string, "US-ASCII");
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
/*     */   public static byte[] getBytesUtf16(String string)
/*     */   {
/*  81 */     return getBytesUnchecked(string, "UTF-16");
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
/*     */   public static byte[] getBytesUtf16Be(String string)
/*     */   {
/*  97 */     return getBytesUnchecked(string, "UTF-16BE");
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
/*     */   public static byte[] getBytesUtf16Le(String string)
/*     */   {
/* 113 */     return getBytesUnchecked(string, "UTF-16LE");
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
/*     */   public static byte[] getBytesUtf8(String string)
/*     */   {
/* 129 */     return getBytesUnchecked(string, "UTF-8");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] getBytesUnchecked(String string, String charsetName)
/*     */   {
/* 152 */     if (string == null) {
/* 153 */       return null;
/*     */     }
/*     */     try {
/* 156 */       return string.getBytes(charsetName);
/*     */     } catch (UnsupportedEncodingException e) {
/* 158 */       throw newIllegalStateException(charsetName, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
/* 163 */     return new IllegalStateException(charsetName + ": " + e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String newString(byte[] bytes, String charsetName)
/*     */   {
/* 185 */     if (bytes == null) {
/* 186 */       return null;
/*     */     }
/*     */     try {
/* 189 */       return new String(bytes, charsetName);
/*     */     } catch (UnsupportedEncodingException e) {
/* 191 */       throw newIllegalStateException(charsetName, e);
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
/*     */   public static String newStringIso8859_1(byte[] bytes)
/*     */   {
/* 206 */     return newString(bytes, "ISO-8859-1");
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
/*     */   public static String newStringUsAscii(byte[] bytes)
/*     */   {
/* 220 */     return newString(bytes, "US-ASCII");
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
/*     */   public static String newStringUtf16(byte[] bytes)
/*     */   {
/* 234 */     return newString(bytes, "UTF-16");
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
/*     */   public static String newStringUtf16Be(byte[] bytes)
/*     */   {
/* 248 */     return newString(bytes, "UTF-16BE");
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
/*     */   public static String newStringUtf16Le(byte[] bytes)
/*     */   {
/* 262 */     return newString(bytes, "UTF-16LE");
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
/*     */   public static String newStringUtf8(byte[] bytes)
/*     */   {
/* 276 */     return newString(bytes, "UTF-8");
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\binary\StringUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */