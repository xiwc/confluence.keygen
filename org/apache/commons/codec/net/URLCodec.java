/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
/*     */ import org.apache.commons.codec.StringEncoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*     */   static final int RADIX = 16;
/*     */   protected String charset;
/*  69 */   protected static byte ESCAPE_CHAR = 37;
/*     */   
/*     */ 
/*     */ 
/*  73 */   protected static final BitSet WWW_FORM_URL = new BitSet(256);
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*  78 */     for (int i = 97; i <= 122; i++) {
/*  79 */       WWW_FORM_URL.set(i);
/*     */     }
/*  81 */     for (int i = 65; i <= 90; i++) {
/*  82 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  85 */     for (int i = 48; i <= 57; i++) {
/*  86 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  89 */     WWW_FORM_URL.set(45);
/*  90 */     WWW_FORM_URL.set(95);
/*  91 */     WWW_FORM_URL.set(46);
/*  92 */     WWW_FORM_URL.set(42);
/*     */     
/*  94 */     WWW_FORM_URL.set(32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public URLCodec()
/*     */   {
/* 102 */     this("UTF-8");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URLCodec(String charset)
/*     */   {
/* 112 */     this.charset = charset;
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
/*     */   public static final byte[] encodeUrl(BitSet urlsafe, byte[] bytes)
/*     */   {
/* 125 */     if (bytes == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     if (urlsafe == null) {
/* 129 */       urlsafe = WWW_FORM_URL;
/*     */     }
/*     */     
/* 132 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 133 */     for (int i = 0; i < bytes.length; i++) {
/* 134 */       int b = bytes[i];
/* 135 */       if (b < 0) {
/* 136 */         b = 256 + b;
/*     */       }
/* 138 */       if (urlsafe.get(b)) {
/* 139 */         if (b == 32) {
/* 140 */           b = 43;
/*     */         }
/* 142 */         buffer.write(b);
/*     */       } else {
/* 144 */         buffer.write(ESCAPE_CHAR);
/* 145 */         char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/* 146 */         char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/* 147 */         buffer.write(hex1);
/* 148 */         buffer.write(hex2);
/*     */       }
/*     */     }
/* 151 */     return buffer.toByteArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final byte[] decodeUrl(byte[] bytes)
/*     */     throws DecoderException
/*     */   {
/* 164 */     if (bytes == null) {
/* 165 */       return null;
/*     */     }
/* 167 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 168 */     for (int i = 0; i < bytes.length; i++) {
/* 169 */       int b = bytes[i];
/* 170 */       if (b == 43) {
/* 171 */         buffer.write(32);
/* 172 */       } else if (b == ESCAPE_CHAR) {
/*     */         try {
/* 174 */           int u = Utils.digit16(bytes[(++i)]);
/* 175 */           int l = Utils.digit16(bytes[(++i)]);
/* 176 */           buffer.write((char)((u << 4) + l));
/*     */         } catch (ArrayIndexOutOfBoundsException e) {
/* 178 */           throw new DecoderException("Invalid URL encoding: ", e);
/*     */         }
/*     */       } else {
/* 181 */         buffer.write(b);
/*     */       }
/*     */     }
/* 184 */     return buffer.toByteArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] encode(byte[] bytes)
/*     */   {
/* 195 */     return encodeUrl(WWW_FORM_URL, bytes);
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
/*     */   public byte[] decode(byte[] bytes)
/*     */     throws DecoderException
/*     */   {
/* 209 */     return decodeUrl(bytes);
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
/*     */   public String encode(String pString, String charset)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 224 */     if (pString == null) {
/* 225 */       return null;
/*     */     }
/* 227 */     return StringUtils.newStringUsAscii(encode(pString.getBytes(charset)));
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
/*     */   public String encode(String pString)
/*     */     throws EncoderException
/*     */   {
/* 241 */     if (pString == null) {
/* 242 */       return null;
/*     */     }
/*     */     try {
/* 245 */       return encode(pString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException e) {
/* 247 */       throw new EncoderException(e.getMessage(), e);
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
/*     */   public String decode(String pString, String charset)
/*     */     throws DecoderException, UnsupportedEncodingException
/*     */   {
/* 265 */     if (pString == null) {
/* 266 */       return null;
/*     */     }
/* 268 */     return new String(decode(StringUtils.getBytesUsAscii(pString)), charset);
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
/*     */   public String decode(String pString)
/*     */     throws DecoderException
/*     */   {
/* 283 */     if (pString == null) {
/* 284 */       return null;
/*     */     }
/*     */     try {
/* 287 */       return decode(pString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException e) {
/* 289 */       throw new DecoderException(e.getMessage(), e);
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
/*     */   public Object encode(Object pObject)
/*     */     throws EncoderException
/*     */   {
/* 304 */     if (pObject == null)
/* 305 */       return null;
/* 306 */     if ((pObject instanceof byte[]))
/* 307 */       return encode((byte[])pObject);
/* 308 */     if ((pObject instanceof String)) {
/* 309 */       return encode((String)pObject);
/*     */     }
/* 311 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL encoded");
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
/*     */   public Object decode(Object pObject)
/*     */     throws DecoderException
/*     */   {
/* 329 */     if (pObject == null)
/* 330 */       return null;
/* 331 */     if ((pObject instanceof byte[]))
/* 332 */       return decode((byte[])pObject);
/* 333 */     if ((pObject instanceof String)) {
/* 334 */       return decode((String)pObject);
/*     */     }
/* 336 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL decoded");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public String getEncoding()
/*     */   {
/* 349 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 358 */     return this.charset;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\URLCodec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */