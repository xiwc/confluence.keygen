/*    */ package org.apache.commons.codec.net;
/*    */ 
/*    */ import org.apache.commons.codec.DecoderException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Utils
/*    */ {
/*    */   static int digit16(byte b)
/*    */     throws DecoderException
/*    */   {
/* 42 */     int i = Character.digit((char)b, 16);
/* 43 */     if (i == -1) {
/* 44 */       throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
/*    */     }
/* 46 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\Utils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */