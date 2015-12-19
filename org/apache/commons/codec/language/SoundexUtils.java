/*   1:    */ package org.apache.commons.codec.language;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.commons.codec.EncoderException;
/*   5:    */ import org.apache.commons.codec.StringEncoder;
/*   6:    */ 
/*   7:    */ final class SoundexUtils
/*   8:    */ {
/*   9:    */   static String clean(String str)
/*  10:    */   {
/*  11: 41 */     if ((str == null) || (str.length() == 0)) {
/*  12: 42 */       return str;
/*  13:    */     }
/*  14: 44 */     int len = str.length();
/*  15: 45 */     char[] chars = new char[len];
/*  16: 46 */     int count = 0;
/*  17: 47 */     for (int i = 0; i < len; i++) {
/*  18: 48 */       if (Character.isLetter(str.charAt(i))) {
/*  19: 49 */         chars[(count++)] = str.charAt(i);
/*  20:    */       }
/*  21:    */     }
/*  22: 52 */     if (count == len) {
/*  23: 53 */       return str.toUpperCase(Locale.ENGLISH);
/*  24:    */     }
/*  25: 55 */     return new String(chars, 0, count).toUpperCase(Locale.ENGLISH);
/*  26:    */   }
/*  27:    */   
/*  28:    */   static int difference(StringEncoder encoder, String s1, String s2)
/*  29:    */     throws EncoderException
/*  30:    */   {
/*  31: 85 */     return differenceEncoded(encoder.encode(s1), encoder.encode(s2));
/*  32:    */   }
/*  33:    */   
/*  34:    */   static int differenceEncoded(String es1, String es2)
/*  35:    */   {
/*  36:110 */     if ((es1 == null) || (es2 == null)) {
/*  37:111 */       return 0;
/*  38:    */     }
/*  39:113 */     int lengthToMatch = Math.min(es1.length(), es2.length());
/*  40:114 */     int diff = 0;
/*  41:115 */     for (int i = 0; i < lengthToMatch; i++) {
/*  42:116 */       if (es1.charAt(i) == es2.charAt(i)) {
/*  43:117 */         diff++;
/*  44:    */       }
/*  45:    */     }
/*  46:120 */     return diff;
/*  47:    */   }
/*  48:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.SoundexUtils
 * JD-Core Version:    0.7.0.1
 */