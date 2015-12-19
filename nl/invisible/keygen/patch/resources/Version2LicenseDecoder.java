/*   1:    */ package com.atlassian.extras.decoder.v2;
/*   2:    */ 
/*   3:    */ import com.atlassian.extras.common.LicenseException;
/*   4:    */ import com.atlassian.extras.common.org.springframework.util.DefaultPropertiesPersister;
/*   5:    */ import com.atlassian.extras.decoder.api.AbstractLicenseDecoder;
/*   6:    */ import java.io.ByteArrayInputStream;
/*   7:    */ import java.io.ByteArrayOutputStream;
/*   8:    */ import java.io.DataInputStream;
/*   9:    */ import java.io.DataOutputStream;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import java.io.Reader;
/*  13:    */ import java.io.UnsupportedEncodingException;
/*  14:    */ import java.security.InvalidKeyException;
/*  15:    */ import java.security.KeyFactory;
/*  16:    */ import java.security.NoSuchAlgorithmException;
/*  17:    */ import java.security.PublicKey;
/*  18:    */ import java.security.Signature;
/*  19:    */ import java.security.SignatureException;
/*  20:    */ import java.security.spec.InvalidKeySpecException;
/*  21:    */ import java.security.spec.X509EncodedKeySpec;
/*  22:    */ import java.util.Properties;
/*  23:    */ import java.util.zip.Inflater;
/*  24:    */ import java.util.zip.InflaterInputStream;
/*  25:    */ import org.apache.commons.codec.binary.Base64;
/*  26:    */ 
/*  27:    */ public class Version2LicenseDecoder
/*  28:    */   extends AbstractLicenseDecoder
/*  29:    */ {
/*  30:    */   public static final int VERSION_NUMBER_1 = 1;
/*  31:    */   public static final int VERSION_NUMBER_2 = 2;
/*  32:    */   public static final int VERSION_LENGTH = 3;
/*  33:    */   public static final int ENCODED_LICENSE_LENGTH_BASE = 31;
/*  34: 55 */   public static final byte[] LICENSE_PREFIX = { 13, 14, 12, 10, 15 };
/*  35:    */   public static final char SEPARATOR = 'X';
/*  36:    */   private static final PublicKey PUBLIC_KEY;
/*  37:    */   private static final int ENCODED_LICENSE_LINE_LENGTH = 76;
/*  38:    */   
/*  39:    */   static
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 55 */       String pubKeyEncoded = "MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkBrKJN92XEUFWggagAhhhNtFVc/Nh/JTnB3xsQ5azfHq7UcFtPEq0ohc3vGZ7OGEQS7Ym08DB6B1DtD93CwaNdX";
/*  44:    */       
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60: 72 */       KeyFactory keyFactory = KeyFactory.getInstance("DSA");PUBLIC_KEY = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64("MIHwMIGoBgcqhkjOOAQBMIGcAkEA/KaCzo4Syrom78z3EQ5SbbB4sF7ey80etKII864WF64B81uRpH5t9jQTxeEu0ImbzRMqzVDZkVG9xD7nN1kuFwIVAJYu3cw2nLqOuyYO5rahJtk0bjjFAkBnhHGyepz0TukaScUUfbGpqvJE8FpDTWSGkx0tFCcbnjUDC3H9c9oXkGmzLik1Yw4cIGI1TQ2iCmxBblC+eUykA0MAAkBrKJN92XEUFWggagAhhhNtFVc/Nh/JTnB3xsQ5azfHq7UcFtPEq0ohc3vGZ7OGEQS7Ym08DB6B1DtD93CwaNdX".getBytes())));
/*  61:    */     }
/*  62:    */     catch (NoSuchAlgorithmException e)
/*  63:    */     {
/*  64: 72 */       throw new Error(e);
/*  65:    */     }
/*  66:    */     catch (InvalidKeySpecException e)
/*  67:    */     {
/*  68: 72 */       throw new Error(e);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean canDecode(String licenseString)
/*  73:    */   {
/*  74: 89 */     licenseString = removeWhiteSpaces(licenseString);int pos = licenseString.lastIndexOf('X');
/*  75: 89 */     if ((pos == -1) || (pos + 3 >= licenseString.length())) {
/*  76: 89 */       return false;
/*  77:    */     }
/*  78:    */     try
/*  79:    */     {
/*  80: 89 */       int version = Integer.parseInt(licenseString.substring(pos + 1, pos + 3));
/*  81: 89 */       if ((version != 1) && (version != 2)) {
/*  82: 89 */         return false;
/*  83:    */       }
/*  84: 89 */       String lengthStr = licenseString.substring(pos + 3);
/*  85:    */       
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:107 */       int encodedLicenseLength = Integer.valueOf(lengthStr, 31).intValue();return pos == encodedLicenseLength;
/* 103:    */     }
/* 104:    */     catch (NumberFormatException e) {}
/* 105:107 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Properties doDecode(String licenseString)
/* 109:    */   {
/* 110:123 */     String encodedLicenseTextAndHash = getLicenseContent(removeWhiteSpaces(licenseString));byte[] zippedLicenseBytes = checkAndGetLicenseText(encodedLicenseTextAndHash);Reader licenseText = unzipText(zippedLicenseBytes);
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:127 */     return loadLicenseConfiguration(licenseText);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected int getLicenseVersion()
/* 118:    */   {
/* 119:132 */     return 2;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private Reader unzipText(byte[] licenseText)
/* 123:    */   {
/* 124:137 */     ByteArrayInputStream in = new ByteArrayInputStream(licenseText);in.skip(LICENSE_PREFIX.length);InflaterInputStream zipIn = new InflaterInputStream(in, new Inflater());
/* 125:    */     try
/* 126:    */     {
/* 127:142 */       return new InputStreamReader(zipIn, "UTF-8");
/* 128:    */     }
/* 129:    */     catch (UnsupportedEncodingException e)
/* 130:    */     {
/* 131:142 */       throw new LicenseException(e);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private String getLicenseContent(String licenseString)
/* 136:    */   {
/* 137:153 */     String lengthStr = licenseString.substring(licenseString.lastIndexOf('X') + 3);
/* 138:    */     try
/* 139:    */     {
/* 140:153 */       int encodedLicenseLength = Integer.valueOf(lengthStr, 31).intValue();
/* 141:    */       
/* 142:    */ 
/* 143:    */ 
/* 144:157 */       return licenseString.substring(0, encodedLicenseLength);
/* 145:    */     }
/* 146:    */     catch (NumberFormatException e)
/* 147:    */     {
/* 148:157 */       throw new LicenseException("Could NOT decode license length <" + lengthStr + ">", e);
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   private byte[] checkAndGetLicenseText(String licenseContent)
/* 153:    */   {
/* 154:    */     byte[] licenseText;
/* 155:    */     try
/* 156:    */     {
/* 157:170 */       byte[] decodedBytes = Base64.decodeBase64(licenseContent.getBytes());ByteArrayInputStream in = new ByteArrayInputStream(decodedBytes);DataInputStream dIn = new DataInputStream(in);int textLength = dIn.readInt();licenseText = new byte[textLength];dIn.read(licenseText);byte[] hash = new byte[dIn.available()];dIn.read(hash);
/* 158:    */       try
/* 159:    */       {
/* 160:181 */         Signature signature = Signature.getInstance("SHA1withDSA");signature.initVerify(PUBLIC_KEY);signature.update(licenseText);
/* 161:181 */         if (!signature.verify(hash)) {
/* 162:181 */           throw new LicenseException("Failed to verify the license.");
/* 163:    */         }
/* 164:    */       }
/* 165:    */       catch (InvalidKeyException e)
/* 166:    */       {
/* 167:181 */         throw new LicenseException(e);
/* 168:    */       }
/* 169:    */       catch (SignatureException e)
/* 170:    */       {
/* 171:181 */         throw new LicenseException(e);
/* 172:    */       }
/* 173:    */       catch (NoSuchAlgorithmException e)
/* 174:    */       {
/* 175:181 */         throw new LicenseException(e);
/* 176:    */       }
/* 177:    */     }
/* 178:    */     catch (IOException e)
/* 179:    */     {
/* 180:181 */       throw new LicenseException(e);
/* 181:    */     }
/* 182:181 */     return licenseText;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private Properties loadLicenseConfiguration(Reader text)
/* 186:    */   {
/* 187:    */     try
/* 188:    */     {
/* 189:217 */       Properties props = new Properties();new DefaultPropertiesPersister().load(props, text);
/* 190:    */       
/* 191:219 */       return props;
/* 192:    */     }
/* 193:    */     catch (IOException e)
/* 194:    */     {
/* 195:223 */       throw new LicenseException("Could NOT load properties from reader", e);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static String removeWhiteSpaces(String licenseData)
/* 200:    */   {
/* 201:232 */     if ((licenseData == null) || (licenseData.length() == 0)) {
/* 202:232 */       return licenseData;
/* 203:    */     }
/* 204:232 */     char[] chars = licenseData.toCharArray();
/* 205:    */     
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:238 */     StringBuffer buf = new StringBuffer(chars.length);
/* 211:238 */     for (int i = 0; i < chars.length; i++) {
/* 212:241 */       if (!Character.isWhitespace(chars[i])) {
/* 213:241 */         buf.append(chars[i]);
/* 214:    */       }
/* 215:    */     }
/* 216:247 */     return buf.toString();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static String packLicense(byte[] text, byte[] hash)
/* 220:    */     throws LicenseException
/* 221:    */   {
/* 222:    */     try
/* 223:    */     {
/* 224:259 */       ByteArrayOutputStream out = new ByteArrayOutputStream();DataOutputStream dOut = new DataOutputStream(out);
/* 225:    */       
/* 226:261 */       dOut.writeInt(text.length);dOut.write(text);dOut.write(hash);byte[] allData = out.toByteArray();String result = new String(Base64.encodeBase64(allData)).trim();result = result + 'X' + "0" + 2 + Integer.toString(result.length(), 31);
/* 227:    */       
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:272 */       return split(result);
/* 238:    */     }
/* 239:    */     catch (IOException e)
/* 240:    */     {
/* 241:272 */       throw new LicenseException(e);
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   private static String split(String licenseData)
/* 246:    */   {
/* 247:287 */     if ((licenseData == null) || (licenseData.length() == 0)) {
/* 248:287 */       return licenseData;
/* 249:    */     }
/* 250:287 */     char[] chars = licenseData.toCharArray();StringBuffer buf = new StringBuffer(chars.length + chars.length / 76);
/* 251:294 */     for (int i = 0; i < chars.length; i++)
/* 252:    */     {
/* 253:294 */       buf.append(chars[i]);
/* 254:294 */       if ((i > 0) && (i % 76 == 0)) {
/* 255:294 */         buf.append('\n');
/* 256:    */       }
/* 257:    */     }
/* 258:294 */     return buf.toString();
/* 259:    */   }
/* 260:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.patch.resources.Version2LicenseDecoder
 * JD-Core Version:    0.7.0.1
 */