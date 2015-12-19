/*   1:    */ package org.apache.commons.codec.net;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.BitSet;
/*   5:    */ import org.apache.commons.codec.DecoderException;
/*   6:    */ import org.apache.commons.codec.EncoderException;
/*   7:    */ import org.apache.commons.codec.StringDecoder;
/*   8:    */ import org.apache.commons.codec.StringEncoder;
/*   9:    */ 
/*  10:    */ public class QCodec
/*  11:    */   extends RFC1522Codec
/*  12:    */   implements StringEncoder, StringDecoder
/*  13:    */ {
/*  14:    */   private final String charset;
/*  15: 58 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*  16:    */   private static final byte BLANK = 32;
/*  17:    */   private static final byte UNDERSCORE = 95;
/*  18:    */   
/*  19:    */   static
/*  20:    */   {
/*  21: 62 */     PRINTABLE_CHARS.set(32);
/*  22: 63 */     PRINTABLE_CHARS.set(33);
/*  23: 64 */     PRINTABLE_CHARS.set(34);
/*  24: 65 */     PRINTABLE_CHARS.set(35);
/*  25: 66 */     PRINTABLE_CHARS.set(36);
/*  26: 67 */     PRINTABLE_CHARS.set(37);
/*  27: 68 */     PRINTABLE_CHARS.set(38);
/*  28: 69 */     PRINTABLE_CHARS.set(39);
/*  29: 70 */     PRINTABLE_CHARS.set(40);
/*  30: 71 */     PRINTABLE_CHARS.set(41);
/*  31: 72 */     PRINTABLE_CHARS.set(42);
/*  32: 73 */     PRINTABLE_CHARS.set(43);
/*  33: 74 */     PRINTABLE_CHARS.set(44);
/*  34: 75 */     PRINTABLE_CHARS.set(45);
/*  35: 76 */     PRINTABLE_CHARS.set(46);
/*  36: 77 */     PRINTABLE_CHARS.set(47);
/*  37: 78 */     for (int i = 48; i <= 57; i++) {
/*  38: 79 */       PRINTABLE_CHARS.set(i);
/*  39:    */     }
/*  40: 81 */     PRINTABLE_CHARS.set(58);
/*  41: 82 */     PRINTABLE_CHARS.set(59);
/*  42: 83 */     PRINTABLE_CHARS.set(60);
/*  43: 84 */     PRINTABLE_CHARS.set(62);
/*  44: 85 */     PRINTABLE_CHARS.set(64);
/*  45: 86 */     for (int i = 65; i <= 90; i++) {
/*  46: 87 */       PRINTABLE_CHARS.set(i);
/*  47:    */     }
/*  48: 89 */     PRINTABLE_CHARS.set(91);
/*  49: 90 */     PRINTABLE_CHARS.set(92);
/*  50: 91 */     PRINTABLE_CHARS.set(93);
/*  51: 92 */     PRINTABLE_CHARS.set(94);
/*  52: 93 */     PRINTABLE_CHARS.set(96);
/*  53: 94 */     for (int i = 97; i <= 122; i++) {
/*  54: 95 */       PRINTABLE_CHARS.set(i);
/*  55:    */     }
/*  56: 97 */     PRINTABLE_CHARS.set(123);
/*  57: 98 */     PRINTABLE_CHARS.set(124);
/*  58: 99 */     PRINTABLE_CHARS.set(125);
/*  59:100 */     PRINTABLE_CHARS.set(126);
/*  60:    */   }
/*  61:    */   
/*  62:107 */   private boolean encodeBlanks = false;
/*  63:    */   
/*  64:    */   public QCodec()
/*  65:    */   {
/*  66:113 */     this("UTF-8");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public QCodec(String charset)
/*  70:    */   {
/*  71:126 */     this.charset = charset;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getEncoding()
/*  75:    */   {
/*  76:130 */     return "Q";
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected byte[] doEncoding(byte[] bytes)
/*  80:    */   {
/*  81:134 */     if (bytes == null) {
/*  82:135 */       return null;
/*  83:    */     }
/*  84:137 */     byte[] data = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
/*  85:138 */     if (this.encodeBlanks) {
/*  86:139 */       for (int i = 0; i < data.length; i++) {
/*  87:140 */         if (data[i] == 32) {
/*  88:141 */           data[i] = 95;
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:145 */     return data;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected byte[] doDecoding(byte[] bytes)
/*  96:    */     throws DecoderException
/*  97:    */   {
/*  98:149 */     if (bytes == null) {
/*  99:150 */       return null;
/* 100:    */     }
/* 101:152 */     boolean hasUnderscores = false;
/* 102:153 */     for (int i = 0; i < bytes.length; i++) {
/* 103:154 */       if (bytes[i] == 95)
/* 104:    */       {
/* 105:155 */         hasUnderscores = true;
/* 106:156 */         break;
/* 107:    */       }
/* 108:    */     }
/* 109:159 */     if (hasUnderscores)
/* 110:    */     {
/* 111:160 */       byte[] tmp = new byte[bytes.length];
/* 112:161 */       for (int i = 0; i < bytes.length; i++)
/* 113:    */       {
/* 114:162 */         byte b = bytes[i];
/* 115:163 */         if (b != 95) {
/* 116:164 */           tmp[i] = b;
/* 117:    */         } else {
/* 118:166 */           tmp[i] = 32;
/* 119:    */         }
/* 120:    */       }
/* 121:169 */       return QuotedPrintableCodec.decodeQuotedPrintable(tmp);
/* 122:    */     }
/* 123:171 */     return QuotedPrintableCodec.decodeQuotedPrintable(bytes);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String encode(String pString, String charset)
/* 127:    */     throws EncoderException
/* 128:    */   {
/* 129:187 */     if (pString == null) {
/* 130:188 */       return null;
/* 131:    */     }
/* 132:    */     try
/* 133:    */     {
/* 134:191 */       return encodeText(pString, charset);
/* 135:    */     }
/* 136:    */     catch (UnsupportedEncodingException e)
/* 137:    */     {
/* 138:193 */       throw new EncoderException(e.getMessage(), e);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String encode(String pString)
/* 143:    */     throws EncoderException
/* 144:    */   {
/* 145:208 */     if (pString == null) {
/* 146:209 */       return null;
/* 147:    */     }
/* 148:211 */     return encode(pString, getDefaultCharset());
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String decode(String pString)
/* 152:    */     throws DecoderException
/* 153:    */   {
/* 154:227 */     if (pString == null) {
/* 155:228 */       return null;
/* 156:    */     }
/* 157:    */     try
/* 158:    */     {
/* 159:231 */       return decodeText(pString);
/* 160:    */     }
/* 161:    */     catch (UnsupportedEncodingException e)
/* 162:    */     {
/* 163:233 */       throw new DecoderException(e.getMessage(), e);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Object encode(Object pObject)
/* 168:    */     throws EncoderException
/* 169:    */   {
/* 170:248 */     if (pObject == null) {
/* 171:249 */       return null;
/* 172:    */     }
/* 173:250 */     if ((pObject instanceof String)) {
/* 174:251 */       return encode((String)pObject);
/* 175:    */     }
/* 176:253 */     throw new EncoderException("Objects of type " + pObject.getClass().getName() + " cannot be encoded using Q codec");
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Object decode(Object pObject)
/* 180:    */     throws DecoderException
/* 181:    */   {
/* 182:273 */     if (pObject == null) {
/* 183:274 */       return null;
/* 184:    */     }
/* 185:275 */     if ((pObject instanceof String)) {
/* 186:276 */       return decode((String)pObject);
/* 187:    */     }
/* 188:278 */     throw new DecoderException("Objects of type " + pObject.getClass().getName() + " cannot be decoded using Q codec");
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getDefaultCharset()
/* 192:    */   {
/* 193:290 */     return this.charset;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isEncodeBlanks()
/* 197:    */   {
/* 198:299 */     return this.encodeBlanks;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setEncodeBlanks(boolean b)
/* 202:    */   {
/* 203:309 */     this.encodeBlanks = b;
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.net.QCodec
 * JD-Core Version:    0.7.0.1
 */