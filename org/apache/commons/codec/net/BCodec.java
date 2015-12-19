/*   1:    */ package org.apache.commons.codec.net;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import org.apache.commons.codec.DecoderException;
/*   5:    */ import org.apache.commons.codec.EncoderException;
/*   6:    */ import org.apache.commons.codec.StringDecoder;
/*   7:    */ import org.apache.commons.codec.StringEncoder;
/*   8:    */ import org.apache.commons.codec.binary.Base64;
/*   9:    */ 
/*  10:    */ public class BCodec
/*  11:    */   extends RFC1522Codec
/*  12:    */   implements StringEncoder, StringDecoder
/*  13:    */ {
/*  14:    */   private final String charset;
/*  15:    */   
/*  16:    */   public BCodec()
/*  17:    */   {
/*  18: 58 */     this("UTF-8");
/*  19:    */   }
/*  20:    */   
/*  21:    */   public BCodec(String charset)
/*  22:    */   {
/*  23: 71 */     this.charset = charset;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected String getEncoding()
/*  27:    */   {
/*  28: 75 */     return "B";
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected byte[] doEncoding(byte[] bytes)
/*  32:    */   {
/*  33: 79 */     if (bytes == null) {
/*  34: 80 */       return null;
/*  35:    */     }
/*  36: 82 */     return Base64.encodeBase64(bytes);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected byte[] doDecoding(byte[] bytes)
/*  40:    */   {
/*  41: 86 */     if (bytes == null) {
/*  42: 87 */       return null;
/*  43:    */     }
/*  44: 89 */     return Base64.decodeBase64(bytes);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String encode(String value, String charset)
/*  48:    */     throws EncoderException
/*  49:    */   {
/*  50:105 */     if (value == null) {
/*  51:106 */       return null;
/*  52:    */     }
/*  53:    */     try
/*  54:    */     {
/*  55:109 */       return encodeText(value, charset);
/*  56:    */     }
/*  57:    */     catch (UnsupportedEncodingException e)
/*  58:    */     {
/*  59:111 */       throw new EncoderException(e.getMessage(), e);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String encode(String value)
/*  64:    */     throws EncoderException
/*  65:    */   {
/*  66:126 */     if (value == null) {
/*  67:127 */       return null;
/*  68:    */     }
/*  69:129 */     return encode(value, getDefaultCharset());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String decode(String value)
/*  73:    */     throws DecoderException
/*  74:    */   {
/*  75:143 */     if (value == null) {
/*  76:144 */       return null;
/*  77:    */     }
/*  78:    */     try
/*  79:    */     {
/*  80:147 */       return decodeText(value);
/*  81:    */     }
/*  82:    */     catch (UnsupportedEncodingException e)
/*  83:    */     {
/*  84:149 */       throw new DecoderException(e.getMessage(), e);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object encode(Object value)
/*  89:    */     throws EncoderException
/*  90:    */   {
/*  91:164 */     if (value == null) {
/*  92:165 */       return null;
/*  93:    */     }
/*  94:166 */     if ((value instanceof String)) {
/*  95:167 */       return encode((String)value);
/*  96:    */     }
/*  97:169 */     throw new EncoderException("Objects of type " + value.getClass().getName() + " cannot be encoded using BCodec");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Object decode(Object value)
/* 101:    */     throws DecoderException
/* 102:    */   {
/* 103:189 */     if (value == null) {
/* 104:190 */       return null;
/* 105:    */     }
/* 106:191 */     if ((value instanceof String)) {
/* 107:192 */       return decode((String)value);
/* 108:    */     }
/* 109:194 */     throw new DecoderException("Objects of type " + value.getClass().getName() + " cannot be decoded using BCodec");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getDefaultCharset()
/* 113:    */   {
/* 114:206 */     return this.charset;
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.BCodec
 * JD-Core Version:    0.7.0.1
 */