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
/*  14:    */ public class URLCodec
/*  15:    */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*  16:    */ {
/*  17:    */   static final int RADIX = 16;
/*  18:    */   protected String charset;
/*  19: 69 */   protected static byte ESCAPE_CHAR = 37;
/*  20: 73 */   protected static final BitSet WWW_FORM_URL = new BitSet(256);
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 78 */     for (int i = 97; i <= 122; i++) {
/*  25: 79 */       WWW_FORM_URL.set(i);
/*  26:    */     }
/*  27: 81 */     for (int i = 65; i <= 90; i++) {
/*  28: 82 */       WWW_FORM_URL.set(i);
/*  29:    */     }
/*  30: 85 */     for (int i = 48; i <= 57; i++) {
/*  31: 86 */       WWW_FORM_URL.set(i);
/*  32:    */     }
/*  33: 89 */     WWW_FORM_URL.set(45);
/*  34: 90 */     WWW_FORM_URL.set(95);
/*  35: 91 */     WWW_FORM_URL.set(46);
/*  36: 92 */     WWW_FORM_URL.set(42);
/*  37:    */     
/*  38: 94 */     WWW_FORM_URL.set(32);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public URLCodec()
/*  42:    */   {
/*  43:102 */     this("UTF-8");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public URLCodec(String charset)
/*  47:    */   {
/*  48:112 */     this.charset = charset;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static final byte[] encodeUrl(BitSet urlsafe, byte[] bytes)
/*  52:    */   {
/*  53:125 */     if (bytes == null) {
/*  54:126 */       return null;
/*  55:    */     }
/*  56:128 */     if (urlsafe == null) {
/*  57:129 */       urlsafe = WWW_FORM_URL;
/*  58:    */     }
/*  59:132 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*  60:133 */     for (int i = 0; i < bytes.length; i++)
/*  61:    */     {
/*  62:134 */       int b = bytes[i];
/*  63:135 */       if (b < 0) {
/*  64:136 */         b = 256 + b;
/*  65:    */       }
/*  66:138 */       if (urlsafe.get(b))
/*  67:    */       {
/*  68:139 */         if (b == 32) {
/*  69:140 */           b = 43;
/*  70:    */         }
/*  71:142 */         buffer.write(b);
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75:144 */         buffer.write(ESCAPE_CHAR);
/*  76:145 */         char hex1 = Character.toUpperCase(Character.forDigit(b >> 4 & 0xF, 16));
/*  77:146 */         char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
/*  78:147 */         buffer.write(hex1);
/*  79:148 */         buffer.write(hex2);
/*  80:    */       }
/*  81:    */     }
/*  82:151 */     return buffer.toByteArray();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static final byte[] decodeUrl(byte[] bytes)
/*  86:    */     throws DecoderException
/*  87:    */   {
/*  88:164 */     if (bytes == null) {
/*  89:165 */       return null;
/*  90:    */     }
/*  91:167 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*  92:168 */     for (int i = 0; i < bytes.length; i++)
/*  93:    */     {
/*  94:169 */       int b = bytes[i];
/*  95:170 */       if (b == 43) {
/*  96:171 */         buffer.write(32);
/*  97:172 */       } else if (b == ESCAPE_CHAR) {
/*  98:    */         try
/*  99:    */         {
/* 100:174 */           int u = Utils.digit16(bytes[(++i)]);
/* 101:175 */           int l = Utils.digit16(bytes[(++i)]);
/* 102:176 */           buffer.write((char)((u << 4) + l));
/* 103:    */         }
/* 104:    */         catch (ArrayIndexOutOfBoundsException e)
/* 105:    */         {
/* 106:178 */           throw new DecoderException("Invalid URL encoding: ", e);
/* 107:    */         }
/* 108:    */       } else {
/* 109:181 */         buffer.write(b);
/* 110:    */       }
/* 111:    */     }
/* 112:184 */     return buffer.toByteArray();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public byte[] encode(byte[] bytes)
/* 116:    */   {
/* 117:195 */     return encodeUrl(WWW_FORM_URL, bytes);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public byte[] decode(byte[] bytes)
/* 121:    */     throws DecoderException
/* 122:    */   {
/* 123:209 */     return decodeUrl(bytes);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String encode(String pString, String charset)
/* 127:    */     throws UnsupportedEncodingException
/* 128:    */   {
/* 129:224 */     if (pString == null) {
/* 130:225 */       return null;
/* 131:    */     }
/* 132:227 */     return StringUtils.newStringUsAscii(encode(pString.getBytes(charset)));
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String encode(String pString)
/* 136:    */     throws EncoderException
/* 137:    */   {
/* 138:241 */     if (pString == null) {
/* 139:242 */       return null;
/* 140:    */     }
/* 141:    */     try
/* 142:    */     {
/* 143:245 */       return encode(pString, getDefaultCharset());
/* 144:    */     }
/* 145:    */     catch (UnsupportedEncodingException e)
/* 146:    */     {
/* 147:247 */       throw new EncoderException(e.getMessage(), e);
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String decode(String pString, String charset)
/* 152:    */     throws DecoderException, UnsupportedEncodingException
/* 153:    */   {
/* 154:265 */     if (pString == null) {
/* 155:266 */       return null;
/* 156:    */     }
/* 157:268 */     return new String(decode(StringUtils.getBytesUsAscii(pString)), charset);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String decode(String pString)
/* 161:    */     throws DecoderException
/* 162:    */   {
/* 163:283 */     if (pString == null) {
/* 164:284 */       return null;
/* 165:    */     }
/* 166:    */     try
/* 167:    */     {
/* 168:287 */       return decode(pString, getDefaultCharset());
/* 169:    */     }
/* 170:    */     catch (UnsupportedEncodingException e)
/* 171:    */     {
/* 172:289 */       throw new DecoderException(e.getMessage(), e);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Object encode(Object pObject)
/* 177:    */     throws EncoderException
/* 178:    */   {
/* 179:304 */     if (pObject == null) {
/* 180:305 */       return null;
/* 181:    */     }
/* 182:306 */     if ((pObject instanceof byte[])) {
/* 183:307 */       return encode((byte[])pObject);
/* 184:    */     }
/* 185:308 */     if ((pObject instanceof String)) {
/* 186:309 */       return encode((String)pObject);
/* 187:    */     }
/* 188:311 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL encoded");
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Object decode(Object pObject)
/* 192:    */     throws DecoderException
/* 193:    */   {
/* 194:329 */     if (pObject == null) {
/* 195:330 */       return null;
/* 196:    */     }
/* 197:331 */     if ((pObject instanceof byte[])) {
/* 198:332 */       return decode((byte[])pObject);
/* 199:    */     }
/* 200:333 */     if ((pObject instanceof String)) {
/* 201:334 */       return decode((String)pObject);
/* 202:    */     }
/* 203:336 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be URL decoded");
/* 204:    */   }
/* 205:    */   
/* 206:    */   /**
/* 207:    */    * @deprecated
/* 208:    */    */
/* 209:    */   public String getEncoding()
/* 210:    */   {
/* 211:349 */     return this.charset;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getDefaultCharset()
/* 215:    */   {
/* 216:358 */     return this.charset;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.URLCodec
 * JD-Core Version:    0.7.0.1
 */