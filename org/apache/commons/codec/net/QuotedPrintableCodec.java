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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuotedPrintableCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*     */   private final String charset;
/*  72 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */   
/*     */   private static final byte ESCAPE_CHAR = 61;
/*     */   
/*     */   private static final byte TAB = 9;
/*     */   
/*     */   private static final byte SPACE = 32;
/*     */   
/*     */   static
/*     */   {
/*  82 */     for (int i = 33; i <= 60; i++) {
/*  83 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  85 */     for (int i = 62; i <= 126; i++) {
/*  86 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  88 */     PRINTABLE_CHARS.set(9);
/*  89 */     PRINTABLE_CHARS.set(32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public QuotedPrintableCodec()
/*     */   {
/*  96 */     this("UTF-8");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public QuotedPrintableCodec(String charset)
/*     */   {
/* 107 */     this.charset = charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final void encodeQuotedPrintable(int b, ByteArrayOutputStream buffer)
/*     */   {
/* 119 */     buffer.write(61);
/* 120 */     char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/* 121 */     char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/* 122 */     buffer.write(hex1);
/* 123 */     buffer.write(hex2);
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
/*     */   public static final byte[] encodeQuotedPrintable(BitSet printable, byte[] bytes)
/*     */   {
/* 141 */     if (bytes == null) {
/* 142 */       return null;
/*     */     }
/* 144 */     if (printable == null) {
/* 145 */       printable = PRINTABLE_CHARS;
/*     */     }
/* 147 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 148 */     for (int i = 0; i < bytes.length; i++) {
/* 149 */       int b = bytes[i];
/* 150 */       if (b < 0) {
/* 151 */         b = 256 + b;
/*     */       }
/* 153 */       if (printable.get(b)) {
/* 154 */         buffer.write(b);
/*     */       } else {
/* 156 */         encodeQuotedPrintable(b, buffer);
/*     */       }
/*     */     }
/* 159 */     return buffer.toByteArray();
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
/*     */   public static final byte[] decodeQuotedPrintable(byte[] bytes)
/*     */     throws DecoderException
/*     */   {
/* 178 */     if (bytes == null) {
/* 179 */       return null;
/*     */     }
/* 181 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 182 */     for (int i = 0; i < bytes.length; i++) {
/* 183 */       int b = bytes[i];
/* 184 */       if (b == 61) {
/*     */         try {
/* 186 */           int u = Utils.digit16(bytes[(++i)]);
/* 187 */           int l = Utils.digit16(bytes[(++i)]);
/* 188 */           buffer.write((char)((u << 4) + l));
/*     */         } catch (ArrayIndexOutOfBoundsException e) {
/* 190 */           throw new DecoderException("Invalid quoted-printable encoding", e);
/*     */         }
/*     */       } else {
/* 193 */         buffer.write(b);
/*     */       }
/*     */     }
/* 196 */     return buffer.toByteArray();
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
/*     */   public byte[] encode(byte[] bytes)
/*     */   {
/* 212 */     return encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
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
/*     */   public byte[] decode(byte[] bytes)
/*     */     throws DecoderException
/*     */   {
/* 231 */     return decodeQuotedPrintable(bytes);
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
/*     */   public String encode(String pString)
/*     */     throws EncoderException
/*     */   {
/* 252 */     if (pString == null) {
/* 253 */       return null;
/*     */     }
/*     */     try {
/* 256 */       return encode(pString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException e) {
/* 258 */       throw new EncoderException(e.getMessage(), e);
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
/*     */ 
/*     */   public String decode(String pString, String charset)
/*     */     throws DecoderException, UnsupportedEncodingException
/*     */   {
/* 277 */     if (pString == null) {
/* 278 */       return null;
/*     */     }
/* 280 */     return new String(decode(StringUtils.getBytesUsAscii(pString)), charset);
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
/*     */   public String decode(String pString)
/*     */     throws DecoderException
/*     */   {
/* 296 */     if (pString == null) {
/* 297 */       return null;
/*     */     }
/*     */     try {
/* 300 */       return decode(pString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException e) {
/* 302 */       throw new DecoderException(e.getMessage(), e);
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
/* 317 */     if (pObject == null)
/* 318 */       return null;
/* 319 */     if ((pObject instanceof byte[]))
/* 320 */       return encode((byte[])pObject);
/* 321 */     if ((pObject instanceof String)) {
/* 322 */       return encode((String)pObject);
/*     */     }
/* 324 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable encoded");
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
/* 342 */     if (pObject == null)
/* 343 */       return null;
/* 344 */     if ((pObject instanceof byte[]))
/* 345 */       return decode((byte[])pObject);
/* 346 */     if ((pObject instanceof String)) {
/* 347 */       return decode((String)pObject);
/*     */     }
/* 349 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable decoded");
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
/* 361 */     return this.charset;
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
/*     */   public String encode(String pString, String charset)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 382 */     if (pString == null) {
/* 383 */       return null;
/*     */     }
/* 385 */     return StringUtils.newStringUsAscii(encode(pString.getBytes(charset)));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\QuotedPrintableCodec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */