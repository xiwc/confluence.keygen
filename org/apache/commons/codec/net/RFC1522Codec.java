/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class RFC1522Codec
/*     */ {
/*     */   protected static final char SEP = '?';
/*     */   protected static final String POSTFIX = "?=";
/*     */   protected static final String PREFIX = "=?";
/*     */   
/*     */   protected String encodeText(String text, String charset)
/*     */     throws EncoderException, UnsupportedEncodingException
/*     */   {
/*  84 */     if (text == null) {
/*  85 */       return null;
/*     */     }
/*  87 */     StringBuffer buffer = new StringBuffer();
/*  88 */     buffer.append("=?");
/*  89 */     buffer.append(charset);
/*  90 */     buffer.append('?');
/*  91 */     buffer.append(getEncoding());
/*  92 */     buffer.append('?');
/*  93 */     byte[] rawdata = doEncoding(text.getBytes(charset));
/*  94 */     buffer.append(StringUtils.newStringUsAscii(rawdata));
/*  95 */     buffer.append("?=");
/*  96 */     return buffer.toString();
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
/*     */   protected String decodeText(String text)
/*     */     throws DecoderException, UnsupportedEncodingException
/*     */   {
/* 115 */     if (text == null) {
/* 116 */       return null;
/*     */     }
/* 118 */     if ((!text.startsWith("=?")) || (!text.endsWith("?="))) {
/* 119 */       throw new DecoderException("RFC 1522 violation: malformed encoded content");
/*     */     }
/* 121 */     int terminator = text.length() - 2;
/* 122 */     int from = 2;
/* 123 */     int to = text.indexOf('?', from);
/* 124 */     if (to == terminator) {
/* 125 */       throw new DecoderException("RFC 1522 violation: charset token not found");
/*     */     }
/* 127 */     String charset = text.substring(from, to);
/* 128 */     if (charset.equals("")) {
/* 129 */       throw new DecoderException("RFC 1522 violation: charset not specified");
/*     */     }
/* 131 */     from = to + 1;
/* 132 */     to = text.indexOf('?', from);
/* 133 */     if (to == terminator) {
/* 134 */       throw new DecoderException("RFC 1522 violation: encoding token not found");
/*     */     }
/* 136 */     String encoding = text.substring(from, to);
/* 137 */     if (!getEncoding().equalsIgnoreCase(encoding)) {
/* 138 */       throw new DecoderException("This codec cannot decode " + encoding + " encoded content");
/*     */     }
/*     */     
/* 141 */     from = to + 1;
/* 142 */     to = text.indexOf('?', from);
/* 143 */     byte[] data = StringUtils.getBytesUsAscii(text.substring(from, to));
/* 144 */     data = doDecoding(data);
/* 145 */     return new String(data, charset);
/*     */   }
/*     */   
/*     */   protected abstract String getEncoding();
/*     */   
/*     */   protected abstract byte[] doEncoding(byte[] paramArrayOfByte)
/*     */     throws EncoderException;
/*     */   
/*     */   protected abstract byte[] doDecoding(byte[] paramArrayOfByte)
/*     */     throws DecoderException;
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\net\RFC1522Codec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */