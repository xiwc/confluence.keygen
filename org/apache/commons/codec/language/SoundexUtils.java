/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringEncoder;
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
/*     */ final class SoundexUtils
/*     */ {
/*     */   static String clean(String str)
/*     */   {
/*  41 */     if ((str == null) || (str.length() == 0)) {
/*  42 */       return str;
/*     */     }
/*  44 */     int len = str.length();
/*  45 */     char[] chars = new char[len];
/*  46 */     int count = 0;
/*  47 */     for (int i = 0; i < len; i++) {
/*  48 */       if (Character.isLetter(str.charAt(i))) {
/*  49 */         chars[(count++)] = str.charAt(i);
/*     */       }
/*     */     }
/*  52 */     if (count == len) {
/*  53 */       return str.toUpperCase(Locale.ENGLISH);
/*     */     }
/*  55 */     return new String(chars, 0, count).toUpperCase(Locale.ENGLISH);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int difference(StringEncoder encoder, String s1, String s2)
/*     */     throws EncoderException
/*     */   {
/*  85 */     return differenceEncoded(encoder.encode(s1), encoder.encode(s2));
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
/*     */ 
/*     */ 
/*     */   static int differenceEncoded(String es1, String es2)
/*     */   {
/* 110 */     if ((es1 == null) || (es2 == null)) {
/* 111 */       return 0;
/*     */     }
/* 113 */     int lengthToMatch = Math.min(es1.length(), es2.length());
/* 114 */     int diff = 0;
/* 115 */     for (int i = 0; i < lengthToMatch; i++) {
/* 116 */       if (es1.charAt(i) == es2.charAt(i)) {
/* 117 */         diff++;
/*     */       }
/*     */     }
/* 120 */     return diff;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\language\SoundexUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */