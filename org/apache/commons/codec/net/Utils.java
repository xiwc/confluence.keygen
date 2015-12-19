/*  1:   */ package org.apache.commons.codec.net;
/*  2:   */ 
/*  3:   */ import org.apache.commons.codec.DecoderException;
/*  4:   */ 
/*  5:   */ class Utils
/*  6:   */ {
/*  7:   */   static int digit16(byte b)
/*  8:   */     throws DecoderException
/*  9:   */   {
/* 10:42 */     int i = Character.digit((char)b, 16);
/* 11:43 */     if (i == -1) {
/* 12:44 */       throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
/* 13:   */     }
/* 14:46 */     return i;
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.Utils
 * JD-Core Version:    0.7.0.1
 */