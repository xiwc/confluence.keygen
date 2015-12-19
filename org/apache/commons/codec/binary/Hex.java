/*   1:    */ package org.apache.commons.codec.binary;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import org.apache.commons.codec.BinaryDecoder;
/*   5:    */ import org.apache.commons.codec.BinaryEncoder;
/*   6:    */ import org.apache.commons.codec.DecoderException;
/*   7:    */ import org.apache.commons.codec.EncoderException;
/*   8:    */ 
/*   9:    */ public class Hex
/*  10:    */   implements BinaryEncoder, BinaryDecoder
/*  11:    */ {
/*  12:    */   public static final String DEFAULT_CHARSET_NAME = "UTF-8";
/*  13: 46 */   private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  14: 51 */   private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*  15:    */   private final String charsetName;
/*  16:    */   
/*  17:    */   public static byte[] decodeHex(char[] data)
/*  18:    */     throws DecoderException
/*  19:    */   {
/*  20: 66 */     int len = data.length;
/*  21: 68 */     if ((len & 0x1) != 0) {
/*  22: 69 */       throw new DecoderException("Odd number of characters.");
/*  23:    */     }
/*  24: 72 */     byte[] out = new byte[len >> 1];
/*  25:    */     
/*  26:    */ 
/*  27: 75 */     int i = 0;
/*  28: 75 */     for (int j = 0; j < len; i++)
/*  29:    */     {
/*  30: 76 */       int f = toDigit(data[j], j) << 4;
/*  31: 77 */       j++;
/*  32: 78 */       f |= toDigit(data[j], j);
/*  33: 79 */       j++;
/*  34: 80 */       out[i] = ((byte)(f & 0xFF));
/*  35:    */     }
/*  36: 83 */     return out;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static char[] encodeHex(byte[] data)
/*  40:    */   {
/*  41: 96 */     return encodeHex(data, true);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static char[] encodeHex(byte[] data, boolean toLowerCase)
/*  45:    */   {
/*  46:112 */     return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected static char[] encodeHex(byte[] data, char[] toDigits)
/*  50:    */   {
/*  51:128 */     int l = data.length;
/*  52:129 */     char[] out = new char[l << 1];
/*  53:    */     
/*  54:131 */     int i = 0;
/*  55:131 */     for (int j = 0; i < l; i++)
/*  56:    */     {
/*  57:132 */       out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
/*  58:133 */       out[(j++)] = toDigits[(0xF & data[i])];
/*  59:    */     }
/*  60:135 */     return out;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String encodeHexString(byte[] data)
/*  64:    */   {
/*  65:148 */     return new String(encodeHex(data));
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected static int toDigit(char ch, int index)
/*  69:    */     throws DecoderException
/*  70:    */   {
/*  71:163 */     int digit = Character.digit(ch, 16);
/*  72:164 */     if (digit == -1) {
/*  73:165 */       throw new DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index);
/*  74:    */     }
/*  75:167 */     return digit;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Hex()
/*  79:    */   {
/*  80:177 */     this.charsetName = "UTF-8";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Hex(String csName)
/*  84:    */   {
/*  85:188 */     this.charsetName = csName;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public byte[] decode(byte[] array)
/*  89:    */     throws DecoderException
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:205 */       return decodeHex(new String(array, getCharsetName()).toCharArray());
/*  94:    */     }
/*  95:    */     catch (UnsupportedEncodingException e)
/*  96:    */     {
/*  97:207 */       throw new DecoderException(e.getMessage(), e);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Object decode(Object object)
/* 102:    */     throws DecoderException
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:226 */       char[] charArray = (object instanceof String) ? ((String)object).toCharArray() : (char[])object;
/* 107:227 */       return decodeHex(charArray);
/* 108:    */     }
/* 109:    */     catch (ClassCastException e)
/* 110:    */     {
/* 111:229 */       throw new DecoderException(e.getMessage(), e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public byte[] encode(byte[] array)
/* 116:    */   {
/* 117:251 */     return StringUtils.getBytesUnchecked(encodeHexString(array), getCharsetName());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Object encode(Object object)
/* 121:    */     throws EncoderException
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:272 */       byte[] byteArray = (object instanceof String) ? ((String)object).getBytes(getCharsetName()) : (byte[])object;
/* 126:273 */       return encodeHex(byteArray);
/* 127:    */     }
/* 128:    */     catch (ClassCastException e)
/* 129:    */     {
/* 130:275 */       throw new EncoderException(e.getMessage(), e);
/* 131:    */     }
/* 132:    */     catch (UnsupportedEncodingException e)
/* 133:    */     {
/* 134:277 */       throw new EncoderException(e.getMessage(), e);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getCharsetName()
/* 139:    */   {
/* 140:288 */     return this.charsetName;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String toString()
/* 144:    */   {
/* 145:297 */     return super.toString() + "[charsetName=" + this.charsetName + "]";
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.Hex
 * JD-Core Version:    0.7.0.1
 */