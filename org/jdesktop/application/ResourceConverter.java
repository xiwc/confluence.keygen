/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.net.MalformedURLException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.net.URISyntaxException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.text.MessageFormat;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.List;
/*  11:    */ 
/*  12:    */ public abstract class ResourceConverter
/*  13:    */ {
/*  14:    */   protected final Class type;
/*  15:    */   
/*  16:    */   public abstract Object parseString(String paramString, ResourceMap paramResourceMap)
/*  17:    */     throws ResourceConverter.ResourceConverterException;
/*  18:    */   
/*  19:    */   public String toString(Object obj)
/*  20:    */   {
/*  21: 67 */     return obj == null ? "null" : obj.toString();
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected ResourceConverter(Class type)
/*  25:    */   {
/*  26: 71 */     if (type == null) {
/*  27: 72 */       throw new IllegalArgumentException("null type");
/*  28:    */     }
/*  29: 74 */     this.type = type;
/*  30:    */   }
/*  31:    */   
/*  32:    */   private ResourceConverter()
/*  33:    */   {
/*  34: 76 */     this.type = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean supportsType(Class testType)
/*  38:    */   {
/*  39: 79 */     return this.type.equals(testType);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static class ResourceConverterException
/*  43:    */     extends Exception
/*  44:    */   {
/*  45:    */     private final String badString;
/*  46:    */     
/*  47:    */     private String maybeShorten(String s)
/*  48:    */     {
/*  49: 85 */       int n = s.length();
/*  50: 86 */       return s.substring(0, 128) + "...[" + (n - 128) + " more characters]";
/*  51:    */     }
/*  52:    */     
/*  53:    */     public ResourceConverterException(String message, String badString, Throwable cause)
/*  54:    */     {
/*  55: 90 */       super(cause);
/*  56: 91 */       this.badString = maybeShorten(badString);
/*  57:    */     }
/*  58:    */     
/*  59:    */     public ResourceConverterException(String message, String badString)
/*  60:    */     {
/*  61: 95 */       super();
/*  62: 96 */       this.badString = maybeShorten(badString);
/*  63:    */     }
/*  64:    */     
/*  65:    */     public String toString()
/*  66:    */     {
/*  67:100 */       StringBuffer sb = new StringBuffer(super.toString());
/*  68:101 */       sb.append(" string: \"");
/*  69:102 */       sb.append(this.badString);
/*  70:103 */       sb.append("\"");
/*  71:104 */       return sb.toString();
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void register(ResourceConverter resourceConverter)
/*  76:    */   {
/*  77:109 */     if (resourceConverter == null) {
/*  78:110 */       throw new IllegalArgumentException("null resourceConverter");
/*  79:    */     }
/*  80:112 */     resourceConverters.add(resourceConverter);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static ResourceConverter forType(Class type)
/*  84:    */   {
/*  85:116 */     if (type == null) {
/*  86:117 */       throw new IllegalArgumentException("null type");
/*  87:    */     }
/*  88:119 */     for (ResourceConverter sc : resourceConverters) {
/*  89:120 */       if (sc.supportsType(type)) {
/*  90:121 */         return sc;
/*  91:    */       }
/*  92:    */     }
/*  93:124 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:127 */   private static ResourceConverter[] resourceConvertersArray = { new BooleanResourceConverter(new String[] { "true", "on", "yes" }), new IntegerResourceConverter(), new MessageFormatResourceConverter(), new FloatResourceConverter(), new DoubleResourceConverter(), new LongResourceConverter(), new ShortResourceConverter(), new ByteResourceConverter(), new URLResourceConverter(), new URIResourceConverter() };
/*  97:139 */   private static List<ResourceConverter> resourceConverters = new ArrayList(Arrays.asList(resourceConvertersArray));
/*  98:    */   
/*  99:    */   private static class BooleanResourceConverter
/* 100:    */     extends ResourceConverter
/* 101:    */   {
/* 102:    */     private final String[] trueStrings;
/* 103:    */     
/* 104:    */     BooleanResourceConverter(String... trueStrings)
/* 105:    */     {
/* 106:146 */       super();
/* 107:147 */       this.trueStrings = trueStrings;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public Object parseString(String s, ResourceMap ignore)
/* 111:    */     {
/* 112:151 */       s = s.trim();
/* 113:152 */       for (String trueString : this.trueStrings) {
/* 114:153 */         if (s.equalsIgnoreCase(trueString)) {
/* 115:154 */           return Boolean.TRUE;
/* 116:    */         }
/* 117:    */       }
/* 118:157 */       return Boolean.FALSE;
/* 119:    */     }
/* 120:    */     
/* 121:    */     public boolean supportsType(Class testType)
/* 122:    */     {
/* 123:161 */       return (testType.equals(Boolean.class)) || (testType.equals(Boolean.TYPE));
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private static abstract class NumberResourceConverter
/* 128:    */     extends ResourceConverter
/* 129:    */   {
/* 130:    */     private final Class primitiveType;
/* 131:    */     
/* 132:    */     NumberResourceConverter(Class type, Class primitiveType)
/* 133:    */     {
/* 134:168 */       super();
/* 135:169 */       this.primitiveType = primitiveType;
/* 136:    */     }
/* 137:    */     
/* 138:    */     protected abstract Number parseString(String paramString)
/* 139:    */       throws NumberFormatException;
/* 140:    */     
/* 141:    */     public Object parseString(String s, ResourceMap ignore)
/* 142:    */       throws ResourceConverter.ResourceConverterException
/* 143:    */     {
/* 144:    */       try
/* 145:    */       {
/* 146:176 */         return parseString(s);
/* 147:    */       }
/* 148:    */       catch (NumberFormatException e)
/* 149:    */       {
/* 150:179 */         throw new ResourceConverter.ResourceConverterException("invalid " + this.type.getSimpleName(), s, e);
/* 151:    */       }
/* 152:    */     }
/* 153:    */     
/* 154:    */     public boolean supportsType(Class testType)
/* 155:    */     {
/* 156:184 */       return (testType.equals(this.type)) || (testType.equals(this.primitiveType));
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private static class FloatResourceConverter
/* 161:    */     extends ResourceConverter.NumberResourceConverter
/* 162:    */   {
/* 163:    */     FloatResourceConverter()
/* 164:    */     {
/* 165:190 */       super(Float.TYPE);
/* 166:    */     }
/* 167:    */     
/* 168:    */     protected Number parseString(String s)
/* 169:    */       throws NumberFormatException
/* 170:    */     {
/* 171:194 */       return Float.valueOf(Float.parseFloat(s));
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static class DoubleResourceConverter
/* 176:    */     extends ResourceConverter.NumberResourceConverter
/* 177:    */   {
/* 178:    */     DoubleResourceConverter()
/* 179:    */     {
/* 180:200 */       super(Double.TYPE);
/* 181:    */     }
/* 182:    */     
/* 183:    */     protected Number parseString(String s)
/* 184:    */       throws NumberFormatException
/* 185:    */     {
/* 186:204 */       return Double.valueOf(Double.parseDouble(s));
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   private static abstract class INumberResourceConverter
/* 191:    */     extends ResourceConverter
/* 192:    */   {
/* 193:    */     private final Class primitiveType;
/* 194:    */     
/* 195:    */     INumberResourceConverter(Class type, Class primitiveType)
/* 196:    */     {
/* 197:211 */       super();
/* 198:212 */       this.primitiveType = primitiveType;
/* 199:    */     }
/* 200:    */     
/* 201:    */     protected abstract Number parseString(String paramString, int paramInt)
/* 202:    */       throws NumberFormatException;
/* 203:    */     
/* 204:    */     public Object parseString(String s, ResourceMap ignore)
/* 205:    */       throws ResourceConverter.ResourceConverterException
/* 206:    */     {
/* 207:    */       try
/* 208:    */       {
/* 209:219 */         String[] nar = s.split("&");
/* 210:220 */         int radix = nar.length == 2 ? Integer.parseInt(nar[1]) : -1;
/* 211:221 */         return parseString(nar[0], radix);
/* 212:    */       }
/* 213:    */       catch (NumberFormatException e)
/* 214:    */       {
/* 215:224 */         throw new ResourceConverter.ResourceConverterException("invalid " + this.type.getSimpleName(), s, e);
/* 216:    */       }
/* 217:    */     }
/* 218:    */     
/* 219:    */     public boolean supportsType(Class testType)
/* 220:    */     {
/* 221:229 */       return (testType.equals(this.type)) || (testType.equals(this.primitiveType));
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   private static class ByteResourceConverter
/* 226:    */     extends ResourceConverter.INumberResourceConverter
/* 227:    */   {
/* 228:    */     ByteResourceConverter()
/* 229:    */     {
/* 230:235 */       super(Byte.TYPE);
/* 231:    */     }
/* 232:    */     
/* 233:    */     protected Number parseString(String s, int radix)
/* 234:    */       throws NumberFormatException
/* 235:    */     {
/* 236:239 */       return Byte.valueOf(radix == -1 ? Byte.decode(s).byteValue() : Byte.parseByte(s, radix));
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   private static class IntegerResourceConverter
/* 241:    */     extends ResourceConverter.INumberResourceConverter
/* 242:    */   {
/* 243:    */     IntegerResourceConverter()
/* 244:    */     {
/* 245:245 */       super(Integer.TYPE);
/* 246:    */     }
/* 247:    */     
/* 248:    */     protected Number parseString(String s, int radix)
/* 249:    */       throws NumberFormatException
/* 250:    */     {
/* 251:249 */       return Integer.valueOf(radix == -1 ? Integer.decode(s).intValue() : Integer.parseInt(s, radix));
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   private static class LongResourceConverter
/* 256:    */     extends ResourceConverter.INumberResourceConverter
/* 257:    */   {
/* 258:    */     LongResourceConverter()
/* 259:    */     {
/* 260:255 */       super(Long.TYPE);
/* 261:    */     }
/* 262:    */     
/* 263:    */     protected Number parseString(String s, int radix)
/* 264:    */       throws NumberFormatException
/* 265:    */     {
/* 266:259 */       return Long.valueOf(radix == -1 ? Long.decode(s).longValue() : Long.parseLong(s, radix));
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   private static class ShortResourceConverter
/* 271:    */     extends ResourceConverter.INumberResourceConverter
/* 272:    */   {
/* 273:    */     ShortResourceConverter()
/* 274:    */     {
/* 275:265 */       super(Short.TYPE);
/* 276:    */     }
/* 277:    */     
/* 278:    */     protected Number parseString(String s, int radix)
/* 279:    */       throws NumberFormatException
/* 280:    */     {
/* 281:269 */       return Short.valueOf(radix == -1 ? Short.decode(s).shortValue() : Short.parseShort(s, radix));
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   private static class MessageFormatResourceConverter
/* 286:    */     extends ResourceConverter
/* 287:    */   {
/* 288:    */     MessageFormatResourceConverter()
/* 289:    */     {
/* 290:275 */       super();
/* 291:    */     }
/* 292:    */     
/* 293:    */     public Object parseString(String s, ResourceMap ignore)
/* 294:    */     {
/* 295:279 */       return new MessageFormat(s);
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static class URLResourceConverter
/* 300:    */     extends ResourceConverter
/* 301:    */   {
/* 302:    */     URLResourceConverter()
/* 303:    */     {
/* 304:285 */       super();
/* 305:    */     }
/* 306:    */     
/* 307:    */     public Object parseString(String s, ResourceMap ignore)
/* 308:    */       throws ResourceConverter.ResourceConverterException
/* 309:    */     {
/* 310:    */       try
/* 311:    */       {
/* 312:290 */         return new URL(s);
/* 313:    */       }
/* 314:    */       catch (MalformedURLException e)
/* 315:    */       {
/* 316:293 */         throw new ResourceConverter.ResourceConverterException("invalid URL", s, e);
/* 317:    */       }
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   private static class URIResourceConverter
/* 322:    */     extends ResourceConverter
/* 323:    */   {
/* 324:    */     URIResourceConverter()
/* 325:    */     {
/* 326:300 */       super();
/* 327:    */     }
/* 328:    */     
/* 329:    */     public Object parseString(String s, ResourceMap ignore)
/* 330:    */       throws ResourceConverter.ResourceConverterException
/* 331:    */     {
/* 332:    */       try
/* 333:    */       {
/* 334:305 */         return new URI(s);
/* 335:    */       }
/* 336:    */       catch (URISyntaxException e)
/* 337:    */       {
/* 338:308 */         throw new ResourceConverter.ResourceConverterException("invalid URI", s, e);
/* 339:    */       }
/* 340:    */     }
/* 341:    */   }
/* 342:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ResourceConverter
 * JD-Core Version:    0.7.0.1
 */