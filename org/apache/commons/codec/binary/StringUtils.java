/*   1:    */ package org.apache.commons.codec.binary;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ 
/*   5:    */ public class StringUtils
/*   6:    */ {
/*   7:    */   public static byte[] getBytesIso8859_1(String string)
/*   8:    */   {
/*   9: 49 */     return getBytesUnchecked(string, "ISO-8859-1");
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static byte[] getBytesUsAscii(String string)
/*  13:    */   {
/*  14: 65 */     return getBytesUnchecked(string, "US-ASCII");
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static byte[] getBytesUtf16(String string)
/*  18:    */   {
/*  19: 81 */     return getBytesUnchecked(string, "UTF-16");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static byte[] getBytesUtf16Be(String string)
/*  23:    */   {
/*  24: 97 */     return getBytesUnchecked(string, "UTF-16BE");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static byte[] getBytesUtf16Le(String string)
/*  28:    */   {
/*  29:113 */     return getBytesUnchecked(string, "UTF-16LE");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static byte[] getBytesUtf8(String string)
/*  33:    */   {
/*  34:129 */     return getBytesUnchecked(string, "UTF-8");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static byte[] getBytesUnchecked(String string, String charsetName)
/*  38:    */   {
/*  39:152 */     if (string == null) {
/*  40:153 */       return null;
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44:156 */       return string.getBytes(charsetName);
/*  45:    */     }
/*  46:    */     catch (UnsupportedEncodingException e)
/*  47:    */     {
/*  48:158 */       throw newIllegalStateException(charsetName, e);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e)
/*  53:    */   {
/*  54:163 */     return new IllegalStateException(charsetName + ": " + e);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String newString(byte[] bytes, String charsetName)
/*  58:    */   {
/*  59:185 */     if (bytes == null) {
/*  60:186 */       return null;
/*  61:    */     }
/*  62:    */     try
/*  63:    */     {
/*  64:189 */       return new String(bytes, charsetName);
/*  65:    */     }
/*  66:    */     catch (UnsupportedEncodingException e)
/*  67:    */     {
/*  68:191 */       throw newIllegalStateException(charsetName, e);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String newStringIso8859_1(byte[] bytes)
/*  73:    */   {
/*  74:206 */     return newString(bytes, "ISO-8859-1");
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static String newStringUsAscii(byte[] bytes)
/*  78:    */   {
/*  79:220 */     return newString(bytes, "US-ASCII");
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static String newStringUtf16(byte[] bytes)
/*  83:    */   {
/*  84:234 */     return newString(bytes, "UTF-16");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String newStringUtf16Be(byte[] bytes)
/*  88:    */   {
/*  89:248 */     return newString(bytes, "UTF-16BE");
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String newStringUtf16Le(byte[] bytes)
/*  93:    */   {
/*  94:262 */     return newString(bytes, "UTF-16LE");
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static String newStringUtf8(byte[] bytes)
/*  98:    */   {
/*  99:276 */     return newString(bytes, "UTF-8");
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.StringUtils
 * JD-Core Version:    0.7.0.1
 */