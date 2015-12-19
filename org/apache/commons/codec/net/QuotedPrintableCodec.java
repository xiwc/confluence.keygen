/*   1:    */ package org.apache.commons.codec.net;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.BitSet;
/*   6:    */ import org.apache.commons.codec.BinaryDecoder;
/*   7:    */ import org.apache.commons.codec.BinaryEncoder;
/*   8:    */ import org.apache.commons.codec.DecoderException;
/*   9:    */ import org.apache.commons.codec.EncoderException;
/*  10:    */ import org.apache.commons.codec.StringDecoder;
/*  11:    */ import org.apache.commons.codec.StringEncoder;
/*  12:    */ import org.apache.commons.codec.binary.StringUtils;
/*  13:    */ 
/*  14:    */ public class QuotedPrintableCodec
/*  15:    */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*  16:    */ {
/*  17:    */   private final String charset;
/*  18: 72 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*  19:    */   private static final byte ESCAPE_CHAR = 61;
/*  20:    */   private static final byte TAB = 9;
/*  21:    */   private static final byte SPACE = 32;
/*  22:    */   
/*  23:    */   static
/*  24:    */   {
/*  25: 82 */     for (int i = 33; i <= 60; i++) {
/*  26: 83 */       PRINTABLE_CHARS.set(i);
/*  27:    */     }
/*  28: 85 */     for (int i = 62; i <= 126; i++) {
/*  29: 86 */       PRINTABLE_CHARS.set(i);
/*  30:    */     }
/*  31: 88 */     PRINTABLE_CHARS.set(9);
/*  32: 89 */     PRINTABLE_CHARS.set(32);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public QuotedPrintableCodec()
/*  36:    */   {
/*  37: 96 */     this("UTF-8");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public QuotedPrintableCodec(String charset)
/*  41:    */   {
/*  42:107 */     this.charset = charset;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static final void encodeQuotedPrintable(int b, ByteArrayOutputStream buffer)
/*  46:    */   {
/*  47:119 */     buffer.write(61);
/*  48:120 */     char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/*  49:121 */     char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/*  50:122 */     buffer.write(hex1);
/*  51:123 */     buffer.write(hex2);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static final byte[] encodeQuotedPrintable(BitSet printable, byte[] bytes)
/*  55:    */   {
/*  56:141 */     if (bytes == null) {
/*  57:142 */       return null;
/*  58:    */     }
/*  59:144 */     if (printable == null) {
/*  60:145 */       printable = PRINTABLE_CHARS;
/*  61:    */     }
/*  62:147 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*  63:148 */     for (int i = 0; i < bytes.length; i++)
/*  64:    */     {
/*  65:149 */       int b = bytes[i];
/*  66:150 */       if (b < 0) {
/*  67:151 */         b = 256 + b;
/*  68:    */       }
/*  69:153 */       if (printable.get(b)) {
/*  70:154 */         buffer.write(b);
/*  71:    */       } else {
/*  72:156 */         encodeQuotedPrintable(b, buffer);
/*  73:    */       }
/*  74:    */     }
/*  75:159 */     return buffer.toByteArray();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static final byte[] decodeQuotedPrintable(byte[] bytes)
/*  79:    */     throws DecoderException
/*  80:    */   {
/*  81:178 */     if (bytes == null) {
/*  82:179 */       return null;
/*  83:    */     }
/*  84:181 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*  85:182 */     for (int i = 0; i < bytes.length; i++)
/*  86:    */     {
/*  87:183 */       int b = bytes[i];
/*  88:184 */       if (b == 61) {
/*  89:    */         try
/*  90:    */         {
/*  91:186 */           int u = Utils.digit16(bytes[(++i)]);
/*  92:187 */           int l = Utils.digit16(bytes[(++i)]);
/*  93:188 */           buffer.write((char)((u << 4) + l));
/*  94:    */         }
/*  95:    */         catch (ArrayIndexOutOfBoundsException e)
/*  96:    */         {
/*  97:190 */           throw new DecoderException("Invalid quoted-printable encoding", e);
/*  98:    */         }
/*  99:    */       } else {
/* 100:193 */         buffer.write(b);
/* 101:    */       }
/* 102:    */     }
/* 103:196 */     return buffer.toByteArray();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public byte[] encode(byte[] bytes)
/* 107:    */   {
/* 108:212 */     return encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public byte[] decode(byte[] bytes)
/* 112:    */     throws DecoderException
/* 113:    */   {
/* 114:231 */     return decodeQuotedPrintable(bytes);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String encode(String pString)
/* 118:    */     throws EncoderException
/* 119:    */   {
/* 120:252 */     if (pString == null) {
/* 121:253 */       return null;
/* 122:    */     }
/* 123:    */     try
/* 124:    */     {
/* 125:256 */       return encode(pString, getDefaultCharset());
/* 126:    */     }
/* 127:    */     catch (UnsupportedEncodingException e)
/* 128:    */     {
/* 129:258 */       throw new EncoderException(e.getMessage(), e);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String decode(String pString, String charset)
/* 134:    */     throws DecoderException, UnsupportedEncodingException
/* 135:    */   {
/* 136:277 */     if (pString == null) {
/* 137:278 */       return null;
/* 138:    */     }
/* 139:280 */     return new String(decode(StringUtils.getBytesUsAscii(pString)), charset);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String decode(String pString)
/* 143:    */     throws DecoderException
/* 144:    */   {
/* 145:296 */     if (pString == null) {
/* 146:297 */       return null;
/* 147:    */     }
/* 148:    */     try
/* 149:    */     {
/* 150:300 */       return decode(pString, getDefaultCharset());
/* 151:    */     }
/* 152:    */     catch (UnsupportedEncodingException e)
/* 153:    */     {
/* 154:302 */       throw new DecoderException(e.getMessage(), e);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Object encode(Object pObject)
/* 159:    */     throws EncoderException
/* 160:    */   {
/* 161:317 */     if (pObject == null) {
/* 162:318 */       return null;
/* 163:    */     }
/* 164:319 */     if ((pObject instanceof byte[])) {
/* 165:320 */       return encode((byte[])pObject);
/* 166:    */     }
/* 167:321 */     if ((pObject instanceof String)) {
/* 168:322 */       return encode((String)pObject);
/* 169:    */     }
/* 170:324 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable encoded");
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Object decode(Object pObject)
/* 174:    */     throws DecoderException
/* 175:    */   {
/* 176:342 */     if (pObject == null) {
/* 177:343 */       return null;
/* 178:    */     }
/* 179:344 */     if ((pObject instanceof byte[])) {
/* 180:345 */       return decode((byte[])pObject);
/* 181:    */     }
/* 182:346 */     if ((pObject instanceof String)) {
/* 183:347 */       return decode((String)pObject);
/* 184:    */     }
/* 185:349 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be quoted-printable decoded");
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getDefaultCharset()
/* 189:    */   {
/* 190:361 */     return this.charset;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String encode(String pString, String charset)
/* 194:    */     throws UnsupportedEncodingException
/* 195:    */   {
/* 196:382 */     if (pString == null) {
/* 197:383 */       return null;
/* 198:    */     }
/* 199:385 */     return StringUtils.newStringUsAscii(encode(pString.getBytes(charset)));
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.QuotedPrintableCodec
 * JD-Core Version:    0.7.0.1
 */