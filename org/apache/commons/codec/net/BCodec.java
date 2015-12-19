/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
/*     */ import org.apache.commons.codec.StringEncoder;
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
/*     */ public class BCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*     */   private final String charset;
/*     */   
/*     */   public BCodec()
/*     */   {
/*  58 */     this("UTF-8");
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
/*     */   public BCodec(String charset)
/*     */   {
/*  71 */     this.charset = charset;
/*     */   }
/*     */   
/*     */   protected String getEncoding() {
/*  75 */     return "B";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] bytes) {
/*  79 */     if (bytes == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     return Base64.encodeBase64(bytes);
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] bytes) {
/*  86 */     if (bytes == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     return Base64.decodeBase64(bytes);
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
/*     */   public String encode(String value, String charset)
/*     */     throws EncoderException
/*     */   {
/* 105 */     if (value == null) {
/* 106 */       return null;
/*     */     }
/*     */     try {
/* 109 */       return encodeText(value, charset);
/*     */     } catch (UnsupportedEncodingException e) {
/* 111 */       throw new EncoderException(e.getMessage(), e);
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
/*     */   public String encode(String value)
/*     */     throws EncoderException
/*     */   {
/* 126 */     if (value == null) {
/* 127 */       return null;
/*     */     }
/* 129 */     return encode(value, getDefaultCharset());
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
/*     */   public String decode(String value)
/*     */     throws DecoderException
/*     */   {
/* 143 */     if (value == null) {
/* 144 */       return null;
/*     */     }
/*     */     try {
/* 147 */       return decodeText(value);
/*     */     } catch (UnsupportedEncodingException e) {
/* 149 */       throw new DecoderException(e.getMessage(), e);
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
/*     */   public Object encode(Object value)
/*     */     throws EncoderException
/*     */   {
/* 164 */     if (value == null)
/* 165 */       return null;
/* 166 */     if ((value instanceof String)) {
/* 167 */       return encode((String)value);
/*     */     }
/* 169 */     throw new EncoderException("Objects of type " + value.getClass().getName() + " cannot be encoded using BCodec");
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
/*     */   public Object decode(Object value)
/*     */     throws DecoderException
/*     */   {
/* 189 */     if (value == null)
/* 190 */       return null;
/* 191 */     if ((value instanceof String)) {
/* 192 */       return decode((String)value);
/*     */     }
/* 194 */     throw new DecoderException("Objects of type " + value.getClass().getName() + " cannot be decoded using BCodec");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 206 */     return this.charset;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\BCodec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */