/*   1:    */ package org.apache.commons.codec.digest;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.security.MessageDigest;
/*   6:    */ import java.security.NoSuchAlgorithmException;
/*   7:    */ import org.apache.commons.codec.binary.Hex;
/*   8:    */ import org.apache.commons.codec.binary.StringUtils;
/*   9:    */ 
/*  10:    */ public class DigestUtils
/*  11:    */ {
/*  12:    */   private static final int STREAM_BUFFER_LENGTH = 1024;
/*  13:    */   
/*  14:    */   private static byte[] digest(MessageDigest digest, InputStream data)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 50 */     byte[] buffer = new byte[1024];
/*  18: 51 */     int read = data.read(buffer, 0, 1024);
/*  19: 53 */     while (read > -1)
/*  20:    */     {
/*  21: 54 */       digest.update(buffer, 0, read);
/*  22: 55 */       read = data.read(buffer, 0, 1024);
/*  23:    */     }
/*  24: 58 */     return digest.digest();
/*  25:    */   }
/*  26:    */   
/*  27:    */   private static byte[] getBytesUtf8(String data)
/*  28:    */   {
/*  29: 69 */     return StringUtils.getBytesUtf8(data);
/*  30:    */   }
/*  31:    */   
/*  32:    */   static MessageDigest getDigest(String algorithm)
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 87 */       return MessageDigest.getInstance(algorithm);
/*  37:    */     }
/*  38:    */     catch (NoSuchAlgorithmException e)
/*  39:    */     {
/*  40: 89 */       throw new RuntimeException(e.getMessage());
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static MessageDigest getMd5Digest()
/*  45:    */   {
/*  46:101 */     return getDigest("MD5");
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static MessageDigest getSha256Digest()
/*  50:    */   {
/*  51:115 */     return getDigest("SHA-256");
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static MessageDigest getSha384Digest()
/*  55:    */   {
/*  56:129 */     return getDigest("SHA-384");
/*  57:    */   }
/*  58:    */   
/*  59:    */   private static MessageDigest getSha512Digest()
/*  60:    */   {
/*  61:143 */     return getDigest("SHA-512");
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static MessageDigest getShaDigest()
/*  65:    */   {
/*  66:154 */     return getDigest("SHA");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static byte[] md5(byte[] data)
/*  70:    */   {
/*  71:165 */     return getMd5Digest().digest(data);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static byte[] md5(InputStream data)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:179 */     return digest(getMd5Digest(), data);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static byte[] md5(String data)
/*  81:    */   {
/*  82:190 */     return md5(getBytesUtf8(data));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static String md5Hex(byte[] data)
/*  86:    */   {
/*  87:201 */     return Hex.encodeHexString(md5(data));
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static String md5Hex(InputStream data)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:215 */     return Hex.encodeHexString(md5(data));
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String md5Hex(String data)
/*  97:    */   {
/*  98:226 */     return Hex.encodeHexString(md5(data));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static byte[] sha(byte[] data)
/* 102:    */   {
/* 103:237 */     return getShaDigest().digest(data);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static byte[] sha(InputStream data)
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:251 */     return digest(getShaDigest(), data);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static byte[] sha(String data)
/* 113:    */   {
/* 114:262 */     return sha(getBytesUtf8(data));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static byte[] sha256(byte[] data)
/* 118:    */   {
/* 119:277 */     return getSha256Digest().digest(data);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static byte[] sha256(InputStream data)
/* 123:    */     throws IOException
/* 124:    */   {
/* 125:294 */     return digest(getSha256Digest(), data);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static byte[] sha256(String data)
/* 129:    */   {
/* 130:309 */     return sha256(getBytesUtf8(data));
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static String sha256Hex(byte[] data)
/* 134:    */   {
/* 135:324 */     return Hex.encodeHexString(sha256(data));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static String sha256Hex(InputStream data)
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:341 */     return Hex.encodeHexString(sha256(data));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static String sha256Hex(String data)
/* 145:    */   {
/* 146:356 */     return Hex.encodeHexString(sha256(data));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static byte[] sha384(byte[] data)
/* 150:    */   {
/* 151:371 */     return getSha384Digest().digest(data);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static byte[] sha384(InputStream data)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:388 */     return digest(getSha384Digest(), data);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static byte[] sha384(String data)
/* 161:    */   {
/* 162:403 */     return sha384(getBytesUtf8(data));
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static String sha384Hex(byte[] data)
/* 166:    */   {
/* 167:418 */     return Hex.encodeHexString(sha384(data));
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static String sha384Hex(InputStream data)
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:435 */     return Hex.encodeHexString(sha384(data));
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static String sha384Hex(String data)
/* 177:    */   {
/* 178:450 */     return Hex.encodeHexString(sha384(data));
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static byte[] sha512(byte[] data)
/* 182:    */   {
/* 183:465 */     return getSha512Digest().digest(data);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static byte[] sha512(InputStream data)
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:482 */     return digest(getSha512Digest(), data);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static byte[] sha512(String data)
/* 193:    */   {
/* 194:497 */     return sha512(getBytesUtf8(data));
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static String sha512Hex(byte[] data)
/* 198:    */   {
/* 199:512 */     return Hex.encodeHexString(sha512(data));
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static String sha512Hex(InputStream data)
/* 203:    */     throws IOException
/* 204:    */   {
/* 205:529 */     return Hex.encodeHexString(sha512(data));
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static String sha512Hex(String data)
/* 209:    */   {
/* 210:544 */     return Hex.encodeHexString(sha512(data));
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static String shaHex(byte[] data)
/* 214:    */   {
/* 215:555 */     return Hex.encodeHexString(sha(data));
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static String shaHex(InputStream data)
/* 219:    */     throws IOException
/* 220:    */   {
/* 221:569 */     return Hex.encodeHexString(sha(data));
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static String shaHex(String data)
/* 225:    */   {
/* 226:580 */     return Hex.encodeHexString(sha(data));
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.digest.DigestUtils
 * JD-Core Version:    0.7.0.1
 */