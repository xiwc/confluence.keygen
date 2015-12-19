/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
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
/*     */ public class Hex
/*     */   implements BinaryEncoder, BinaryDecoder
/*     */ {
/*     */   public static final String DEFAULT_CHARSET_NAME = "UTF-8";
/*  46 */   private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */   private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String charsetName;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] decodeHex(char[] data)
/*     */     throws DecoderException
/*     */   {
/*  66 */     int len = data.length;
/*     */     
/*  68 */     if ((len & 0x1) != 0) {
/*  69 */       throw new DecoderException("Odd number of characters.");
/*     */     }
/*     */     
/*  72 */     byte[] out = new byte[len >> 1];
/*     */     
/*     */ 
/*  75 */     int i = 0; for (int j = 0; j < len; i++) {
/*  76 */       int f = toDigit(data[j], j) << 4;
/*  77 */       j++;
/*  78 */       f |= toDigit(data[j], j);
/*  79 */       j++;
/*  80 */       out[i] = ((byte)(f & 0xFF));
/*     */     }
/*     */     
/*  83 */     return out;
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
/*     */   public static char[] encodeHex(byte[] data)
/*     */   {
/*  96 */     return encodeHex(data, true);
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
/*     */   public static char[] encodeHex(byte[] data, boolean toLowerCase)
/*     */   {
/* 112 */     return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
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
/*     */   protected static char[] encodeHex(byte[] data, char[] toDigits)
/*     */   {
/* 128 */     int l = data.length;
/* 129 */     char[] out = new char[l << 1];
/*     */     
/* 131 */     int i = 0; for (int j = 0; i < l; i++) {
/* 132 */       out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
/* 133 */       out[(j++)] = toDigits[(0xF & data[i])];
/*     */     }
/* 135 */     return out;
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
/*     */   public static String encodeHexString(byte[] data)
/*     */   {
/* 148 */     return new String(encodeHex(data));
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
/*     */   protected static int toDigit(char ch, int index)
/*     */     throws DecoderException
/*     */   {
/* 163 */     int digit = Character.digit(ch, 16);
/* 164 */     if (digit == -1) {
/* 165 */       throw new DecoderException("Illegal hexadecimal charcter " + ch + " at index " + index);
/*     */     }
/* 167 */     return digit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Hex()
/*     */   {
/* 177 */     this.charsetName = "UTF-8";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Hex(String csName)
/*     */   {
/* 188 */     this.charsetName = csName;
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
/*     */   public byte[] decode(byte[] array)
/*     */     throws DecoderException
/*     */   {
/*     */     try
/*     */     {
/* 205 */       return decodeHex(new String(array, getCharsetName()).toCharArray());
/*     */     } catch (UnsupportedEncodingException e) {
/* 207 */       throw new DecoderException(e.getMessage(), e);
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
/*     */   public Object decode(Object object)
/*     */     throws DecoderException
/*     */   {
/*     */     try
/*     */     {
/* 226 */       char[] charArray = (object instanceof String) ? ((String)object).toCharArray() : (char[])object;
/* 227 */       return decodeHex(charArray);
/*     */     } catch (ClassCastException e) {
/* 229 */       throw new DecoderException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] encode(byte[] array)
/*     */   {
/* 251 */     return StringUtils.getBytesUnchecked(encodeHexString(array), getCharsetName());
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
/*     */   public Object encode(Object object)
/*     */     throws EncoderException
/*     */   {
/*     */     try
/*     */     {
/* 272 */       byte[] byteArray = (object instanceof String) ? ((String)object).getBytes(getCharsetName()) : (byte[])object;
/* 273 */       return encodeHex(byteArray);
/*     */     } catch (ClassCastException e) {
/* 275 */       throw new EncoderException(e.getMessage(), e);
/*     */     } catch (UnsupportedEncodingException e) {
/* 277 */       throw new EncoderException(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCharsetName()
/*     */   {
/* 288 */     return this.charsetName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 297 */     return super.toString() + "[charsetName=" + this.charsetName + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\binary\Hex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */