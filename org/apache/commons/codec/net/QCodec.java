/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*     */   private final String charset;
/*  58 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */   private static final byte BLANK = 32;
/*     */   private static final byte UNDERSCORE = 95;
/*     */   
/*  62 */   static { PRINTABLE_CHARS.set(32);
/*  63 */     PRINTABLE_CHARS.set(33);
/*  64 */     PRINTABLE_CHARS.set(34);
/*  65 */     PRINTABLE_CHARS.set(35);
/*  66 */     PRINTABLE_CHARS.set(36);
/*  67 */     PRINTABLE_CHARS.set(37);
/*  68 */     PRINTABLE_CHARS.set(38);
/*  69 */     PRINTABLE_CHARS.set(39);
/*  70 */     PRINTABLE_CHARS.set(40);
/*  71 */     PRINTABLE_CHARS.set(41);
/*  72 */     PRINTABLE_CHARS.set(42);
/*  73 */     PRINTABLE_CHARS.set(43);
/*  74 */     PRINTABLE_CHARS.set(44);
/*  75 */     PRINTABLE_CHARS.set(45);
/*  76 */     PRINTABLE_CHARS.set(46);
/*  77 */     PRINTABLE_CHARS.set(47);
/*  78 */     for (int i = 48; i <= 57; i++) {
/*  79 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  81 */     PRINTABLE_CHARS.set(58);
/*  82 */     PRINTABLE_CHARS.set(59);
/*  83 */     PRINTABLE_CHARS.set(60);
/*  84 */     PRINTABLE_CHARS.set(62);
/*  85 */     PRINTABLE_CHARS.set(64);
/*  86 */     for (int i = 65; i <= 90; i++) {
/*  87 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  89 */     PRINTABLE_CHARS.set(91);
/*  90 */     PRINTABLE_CHARS.set(92);
/*  91 */     PRINTABLE_CHARS.set(93);
/*  92 */     PRINTABLE_CHARS.set(94);
/*  93 */     PRINTABLE_CHARS.set(96);
/*  94 */     for (int i = 97; i <= 122; i++) {
/*  95 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  97 */     PRINTABLE_CHARS.set(123);
/*  98 */     PRINTABLE_CHARS.set(124);
/*  99 */     PRINTABLE_CHARS.set(125);
/* 100 */     PRINTABLE_CHARS.set(126);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */   private boolean encodeBlanks = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public QCodec()
/*     */   {
/* 113 */     this("UTF-8");
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
/*     */   public QCodec(String charset)
/*     */   {
/* 126 */     this.charset = charset;
/*     */   }
/*     */   
/*     */   protected String getEncoding() {
/* 130 */     return "Q";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] bytes) {
/* 134 */     if (bytes == null) {
/* 135 */       return null;
/*     */     }
/* 137 */     byte[] data = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
/* 138 */     if (this.encodeBlanks) {
/* 139 */       for (int i = 0; i < data.length; i++) {
/* 140 */         if (data[i] == 32) {
/* 141 */           data[i] = 95;
/*     */         }
/*     */       }
/*     */     }
/* 145 */     return data;
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] bytes) throws DecoderException {
/* 149 */     if (bytes == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     boolean hasUnderscores = false;
/* 153 */     for (int i = 0; i < bytes.length; i++) {
/* 154 */       if (bytes[i] == 95) {
/* 155 */         hasUnderscores = true;
/* 156 */         break;
/*     */       }
/*     */     }
/* 159 */     if (hasUnderscores) {
/* 160 */       byte[] tmp = new byte[bytes.length];
/* 161 */       for (int i = 0; i < bytes.length; i++) {
/* 162 */         byte b = bytes[i];
/* 163 */         if (b != 95) {
/* 164 */           tmp[i] = b;
/*     */         } else {
/* 166 */           tmp[i] = 32;
/*     */         }
/*     */       }
/* 169 */       return QuotedPrintableCodec.decodeQuotedPrintable(tmp);
/*     */     }
/* 171 */     return QuotedPrintableCodec.decodeQuotedPrintable(bytes);
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
/*     */   public String encode(String pString, String charset)
/*     */     throws EncoderException
/*     */   {
/* 187 */     if (pString == null) {
/* 188 */       return null;
/*     */     }
/*     */     try {
/* 191 */       return encodeText(pString, charset);
/*     */     } catch (UnsupportedEncodingException e) {
/* 193 */       throw new EncoderException(e.getMessage(), e);
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
/*     */   public String encode(String pString)
/*     */     throws EncoderException
/*     */   {
/* 208 */     if (pString == null) {
/* 209 */       return null;
/*     */     }
/* 211 */     return encode(pString, getDefaultCharset());
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
/* 227 */     if (pString == null) {
/* 228 */       return null;
/*     */     }
/*     */     try {
/* 231 */       return decodeText(pString);
/*     */     } catch (UnsupportedEncodingException e) {
/* 233 */       throw new DecoderException(e.getMessage(), e);
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
/* 248 */     if (pObject == null)
/* 249 */       return null;
/* 250 */     if ((pObject instanceof String)) {
/* 251 */       return encode((String)pObject);
/*     */     }
/* 253 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be encoded using Q codec");
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
/*     */   public Object decode(Object pObject)
/*     */     throws DecoderException
/*     */   {
/* 273 */     if (pObject == null)
/* 274 */       return null;
/* 275 */     if ((pObject instanceof String)) {
/* 276 */       return decode((String)pObject);
/*     */     }
/* 278 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be decoded using Q codec");
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
/* 290 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEncodeBlanks()
/*     */   {
/* 299 */     return this.encodeBlanks;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEncodeBlanks(boolean b)
/*     */   {
/* 309 */     this.encodeBlanks = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\QCodec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */