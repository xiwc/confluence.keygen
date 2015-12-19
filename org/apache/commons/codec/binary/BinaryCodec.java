/*   1:    */ package org.apache.commons.codec.binary;
/*   2:    */ 
/*   3:    */ import org.apache.commons.codec.BinaryDecoder;
/*   4:    */ import org.apache.commons.codec.BinaryEncoder;
/*   5:    */ import org.apache.commons.codec.DecoderException;
/*   6:    */ import org.apache.commons.codec.EncoderException;
/*   7:    */ 
/*   8:    */ public class BinaryCodec
/*   9:    */   implements BinaryDecoder, BinaryEncoder
/*  10:    */ {
/*  11: 41 */   private static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*  12: 44 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*  13:    */   private static final int BIT_0 = 1;
/*  14:    */   private static final int BIT_1 = 2;
/*  15:    */   private static final int BIT_2 = 4;
/*  16:    */   private static final int BIT_3 = 8;
/*  17:    */   private static final int BIT_4 = 16;
/*  18:    */   private static final int BIT_5 = 32;
/*  19:    */   private static final int BIT_6 = 64;
/*  20:    */   private static final int BIT_7 = 128;
/*  21: 70 */   private static final int[] BITS = { 1, 2, 4, 8, 16, 32, 64, 128 };
/*  22:    */   
/*  23:    */   public byte[] encode(byte[] raw)
/*  24:    */   {
/*  25: 81 */     return toAsciiBytes(raw);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object encode(Object raw)
/*  29:    */     throws EncoderException
/*  30:    */   {
/*  31: 95 */     if (!(raw instanceof byte[])) {
/*  32: 96 */       throw new EncoderException("argument not a byte array");
/*  33:    */     }
/*  34: 98 */     return toAsciiChars((byte[])raw);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object decode(Object ascii)
/*  38:    */     throws DecoderException
/*  39:    */   {
/*  40:112 */     if (ascii == null) {
/*  41:113 */       return EMPTY_BYTE_ARRAY;
/*  42:    */     }
/*  43:115 */     if ((ascii instanceof byte[])) {
/*  44:116 */       return fromAscii((byte[])ascii);
/*  45:    */     }
/*  46:118 */     if ((ascii instanceof char[])) {
/*  47:119 */       return fromAscii((char[])ascii);
/*  48:    */     }
/*  49:121 */     if ((ascii instanceof String)) {
/*  50:122 */       return fromAscii(((String)ascii).toCharArray());
/*  51:    */     }
/*  52:124 */     throw new DecoderException("argument not a byte array");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public byte[] decode(byte[] ascii)
/*  56:    */   {
/*  57:136 */     return fromAscii(ascii);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public byte[] toByteArray(String ascii)
/*  61:    */   {
/*  62:148 */     if (ascii == null) {
/*  63:149 */       return EMPTY_BYTE_ARRAY;
/*  64:    */     }
/*  65:151 */     return fromAscii(ascii.toCharArray());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static byte[] fromAscii(char[] ascii)
/*  69:    */   {
/*  70:167 */     if ((ascii == null) || (ascii.length == 0)) {
/*  71:168 */       return EMPTY_BYTE_ARRAY;
/*  72:    */     }
/*  73:171 */     byte[] l_raw = new byte[ascii.length >> 3];
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:176 */     int ii = 0;
/*  79:176 */     for (int jj = ascii.length - 1; ii < l_raw.length; jj -= 8)
/*  80:    */     {
/*  81:177 */       for (int bits = 0; bits < BITS.length; bits++) {
/*  82:178 */         if (ascii[(jj - bits)] == '1')
/*  83:    */         {
/*  84:179 */           int tmp58_57 = ii; byte[] tmp58_56 = l_raw;tmp58_56[tmp58_57] = ((byte)(tmp58_56[tmp58_57] | BITS[bits]));
/*  85:    */         }
/*  86:    */       }
/*  87:176 */       ii++;
/*  88:    */     }
/*  89:183 */     return l_raw;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static byte[] fromAscii(byte[] ascii)
/*  93:    */   {
/*  94:194 */     if (isEmpty(ascii)) {
/*  95:195 */       return EMPTY_BYTE_ARRAY;
/*  96:    */     }
/*  97:198 */     byte[] l_raw = new byte[ascii.length >> 3];
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:203 */     int ii = 0;
/* 103:203 */     for (int jj = ascii.length - 1; ii < l_raw.length; jj -= 8)
/* 104:    */     {
/* 105:204 */       for (int bits = 0; bits < BITS.length; bits++) {
/* 106:205 */         if (ascii[(jj - bits)] == 49)
/* 107:    */         {
/* 108:206 */           int tmp56_55 = ii; byte[] tmp56_54 = l_raw;tmp56_54[tmp56_55] = ((byte)(tmp56_54[tmp56_55] | BITS[bits]));
/* 109:    */         }
/* 110:    */       }
/* 111:203 */       ii++;
/* 112:    */     }
/* 113:210 */     return l_raw;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static boolean isEmpty(byte[] array)
/* 117:    */   {
/* 118:221 */     return (array == null) || (array.length == 0);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static byte[] toAsciiBytes(byte[] raw)
/* 122:    */   {
/* 123:234 */     if (isEmpty(raw)) {
/* 124:235 */       return EMPTY_BYTE_ARRAY;
/* 125:    */     }
/* 126:238 */     byte[] l_ascii = new byte[raw.length << 3];
/* 127:    */     
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:243 */     int ii = 0;
/* 132:243 */     for (int jj = l_ascii.length - 1; ii < raw.length; jj -= 8)
/* 133:    */     {
/* 134:244 */       for (int bits = 0; bits < BITS.length; bits++) {
/* 135:245 */         if ((raw[ii] & BITS[bits]) == 0) {
/* 136:246 */           l_ascii[(jj - bits)] = 48;
/* 137:    */         } else {
/* 138:248 */           l_ascii[(jj - bits)] = 49;
/* 139:    */         }
/* 140:    */       }
/* 141:243 */       ii++;
/* 142:    */     }
/* 143:252 */     return l_ascii;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static char[] toAsciiChars(byte[] raw)
/* 147:    */   {
/* 148:264 */     if (isEmpty(raw)) {
/* 149:265 */       return EMPTY_CHAR_ARRAY;
/* 150:    */     }
/* 151:268 */     char[] l_ascii = new char[raw.length << 3];
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:273 */     int ii = 0;
/* 157:273 */     for (int jj = l_ascii.length - 1; ii < raw.length; jj -= 8)
/* 158:    */     {
/* 159:274 */       for (int bits = 0; bits < BITS.length; bits++) {
/* 160:275 */         if ((raw[ii] & BITS[bits]) == 0) {
/* 161:276 */           l_ascii[(jj - bits)] = '0';
/* 162:    */         } else {
/* 163:278 */           l_ascii[(jj - bits)] = '1';
/* 164:    */         }
/* 165:    */       }
/* 166:273 */       ii++;
/* 167:    */     }
/* 168:282 */     return l_ascii;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static String toAsciiString(byte[] raw)
/* 172:    */   {
/* 173:294 */     return new String(toAsciiChars(raw));
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.BinaryCodec
 * JD-Core Version:    0.7.0.1
 */