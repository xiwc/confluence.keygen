/*   1:    */ package org.apache.commons.codec.net;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import org.apache.commons.codec.DecoderException;
/*   5:    */ import org.apache.commons.codec.EncoderException;
/*   6:    */ import org.apache.commons.codec.binary.StringUtils;
/*   7:    */ 
/*   8:    */ abstract class RFC1522Codec
/*   9:    */ {
/*  10:    */   protected static final char SEP = '?';
/*  11:    */   protected static final String POSTFIX = "?=";
/*  12:    */   protected static final String PREFIX = "=?";
/*  13:    */   
/*  14:    */   protected String encodeText(String text, String charset)
/*  15:    */     throws EncoderException, UnsupportedEncodingException
/*  16:    */   {
/*  17: 84 */     if (text == null) {
/*  18: 85 */       return null;
/*  19:    */     }
/*  20: 87 */     StringBuffer buffer = new StringBuffer();
/*  21: 88 */     buffer.append("=?");
/*  22: 89 */     buffer.append(charset);
/*  23: 90 */     buffer.append('?');
/*  24: 91 */     buffer.append(getEncoding());
/*  25: 92 */     buffer.append('?');
/*  26: 93 */     byte[] rawdata = doEncoding(text.getBytes(charset));
/*  27: 94 */     buffer.append(StringUtils.newStringUsAscii(rawdata));
/*  28: 95 */     buffer.append("?=");
/*  29: 96 */     return buffer.toString();
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected String decodeText(String text)
/*  33:    */     throws DecoderException, UnsupportedEncodingException
/*  34:    */   {
/*  35:115 */     if (text == null) {
/*  36:116 */       return null;
/*  37:    */     }
/*  38:118 */     if ((!text.startsWith("=?")) || (!text.endsWith("?="))) {
/*  39:119 */       throw new DecoderException("RFC 1522 violation: malformed encoded content");
/*  40:    */     }
/*  41:121 */     int terminator = text.length() - 2;
/*  42:122 */     int from = 2;
/*  43:123 */     int to = text.indexOf('?', from);
/*  44:124 */     if (to == terminator) {
/*  45:125 */       throw new DecoderException("RFC 1522 violation: charset token not found");
/*  46:    */     }
/*  47:127 */     String charset = text.substring(from, to);
/*  48:128 */     if (charset.equals("")) {
/*  49:129 */       throw new DecoderException("RFC 1522 violation: charset not specified");
/*  50:    */     }
/*  51:131 */     from = to + 1;
/*  52:132 */     to = text.indexOf('?', from);
/*  53:133 */     if (to == terminator) {
/*  54:134 */       throw new DecoderException("RFC 1522 violation: encoding token not found");
/*  55:    */     }
/*  56:136 */     String encoding = text.substring(from, to);
/*  57:137 */     if (!getEncoding().equalsIgnoreCase(encoding)) {
/*  58:138 */       throw new DecoderException("This codec cannot decode " + encoding + " encoded content");
/*  59:    */     }
/*  60:141 */     from = to + 1;
/*  61:142 */     to = text.indexOf('?', from);
/*  62:143 */     byte[] data = StringUtils.getBytesUsAscii(text.substring(from, to));
/*  63:144 */     data = doDecoding(data);
/*  64:145 */     return new String(data, charset);
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected abstract String getEncoding();
/*  68:    */   
/*  69:    */   protected abstract byte[] doEncoding(byte[] paramArrayOfByte)
/*  70:    */     throws EncoderException;
/*  71:    */   
/*  72:    */   protected abstract byte[] doDecoding(byte[] paramArrayOfByte)
/*  73:    */     throws DecoderException;
/*  74:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.RFC1522Codec
 * JD-Core Version:    0.7.0.1
 */