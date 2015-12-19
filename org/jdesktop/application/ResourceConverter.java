/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public abstract class ResourceConverter
/*     */ {
/*     */   protected final Class type;
/*     */   
/*     */   public abstract Object parseString(String paramString, ResourceMap paramResourceMap)
/*     */     throws ResourceConverter.ResourceConverterException;
/*     */   
/*     */   public String toString(Object obj)
/*     */   {
/*  67 */     return obj == null ? "null" : obj.toString();
/*     */   }
/*     */   
/*     */   protected ResourceConverter(Class type) {
/*  71 */     if (type == null) {
/*  72 */       throw new IllegalArgumentException("null type");
/*     */     }
/*  74 */     this.type = type; }
/*     */   
/*  76 */   private ResourceConverter() { this.type = null; }
/*     */   
/*     */ 
/*  79 */   public boolean supportsType(Class testType) { return this.type.equals(testType); }
/*     */   
/*     */   public static class ResourceConverterException extends Exception {
/*     */     private final String badString;
/*     */     
/*     */     private String maybeShorten(String s) {
/*  85 */       int n = s.length();
/*  86 */       return s.substring(0, 128) + "...[" + (n - 128) + " more characters]";
/*     */     }
/*     */     
/*     */     public ResourceConverterException(String message, String badString, Throwable cause) {
/*  90 */       super(cause);
/*  91 */       this.badString = maybeShorten(badString);
/*     */     }
/*     */     
/*     */     public ResourceConverterException(String message, String badString) {
/*  95 */       super();
/*  96 */       this.badString = maybeShorten(badString);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 100 */       StringBuffer sb = new StringBuffer(super.toString());
/* 101 */       sb.append(" string: \"");
/* 102 */       sb.append(this.badString);
/* 103 */       sb.append("\"");
/* 104 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void register(ResourceConverter resourceConverter) {
/* 109 */     if (resourceConverter == null) {
/* 110 */       throw new IllegalArgumentException("null resourceConverter");
/*     */     }
/* 112 */     resourceConverters.add(resourceConverter);
/*     */   }
/*     */   
/*     */   public static ResourceConverter forType(Class type) {
/* 116 */     if (type == null) {
/* 117 */       throw new IllegalArgumentException("null type");
/*     */     }
/* 119 */     for (ResourceConverter sc : resourceConverters) {
/* 120 */       if (sc.supportsType(type)) {
/* 121 */         return sc;
/*     */       }
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/* 127 */   private static ResourceConverter[] resourceConvertersArray = { new BooleanResourceConverter(new String[] { "true", "on", "yes" }), new IntegerResourceConverter(), new MessageFormatResourceConverter(), new FloatResourceConverter(), new DoubleResourceConverter(), new LongResourceConverter(), new ShortResourceConverter(), new ByteResourceConverter(), new URLResourceConverter(), new URIResourceConverter() };
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
/* 139 */   private static List<ResourceConverter> resourceConverters = new ArrayList(Arrays.asList(resourceConvertersArray));
/*     */   
/*     */   private static class BooleanResourceConverter extends ResourceConverter
/*     */   {
/*     */     private final String[] trueStrings;
/*     */     
/*     */     BooleanResourceConverter(String... trueStrings) {
/* 146 */       super();
/* 147 */       this.trueStrings = trueStrings;
/*     */     }
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) {
/* 151 */       s = s.trim();
/* 152 */       for (String trueString : this.trueStrings) {
/* 153 */         if (s.equalsIgnoreCase(trueString)) {
/* 154 */           return Boolean.TRUE;
/*     */         }
/*     */       }
/* 157 */       return Boolean.FALSE;
/*     */     }
/*     */     
/*     */ 
/* 161 */     public boolean supportsType(Class testType) { return (testType.equals(Boolean.class)) || (testType.equals(Boolean.TYPE)); }
/*     */   }
/*     */   
/*     */   private static abstract class NumberResourceConverter extends ResourceConverter {
/*     */     private final Class primitiveType;
/*     */     
/*     */     NumberResourceConverter(Class type, Class primitiveType) {
/* 168 */       super();
/* 169 */       this.primitiveType = primitiveType;
/*     */     }
/*     */     
/*     */     protected abstract Number parseString(String paramString) throws NumberFormatException;
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/*     */       try {
/* 176 */         return parseString(s);
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 179 */         throw new ResourceConverter.ResourceConverterException("invalid " + this.type.getSimpleName(), s, e);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean supportsType(Class testType) {
/* 184 */       return (testType.equals(this.type)) || (testType.equals(this.primitiveType));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FloatResourceConverter extends ResourceConverter.NumberResourceConverter {
/*     */     FloatResourceConverter() {
/* 190 */       super(Float.TYPE);
/*     */     }
/*     */     
/*     */     protected Number parseString(String s) throws NumberFormatException {
/* 194 */       return Float.valueOf(Float.parseFloat(s));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DoubleResourceConverter extends ResourceConverter.NumberResourceConverter {
/*     */     DoubleResourceConverter() {
/* 200 */       super(Double.TYPE);
/*     */     }
/*     */     
/*     */ 
/* 204 */     protected Number parseString(String s) throws NumberFormatException { return Double.valueOf(Double.parseDouble(s)); }
/*     */   }
/*     */   
/*     */   private static abstract class INumberResourceConverter extends ResourceConverter {
/*     */     private final Class primitiveType;
/*     */     
/*     */     INumberResourceConverter(Class type, Class primitiveType) {
/* 211 */       super();
/* 212 */       this.primitiveType = primitiveType;
/*     */     }
/*     */     
/*     */     protected abstract Number parseString(String paramString, int paramInt) throws NumberFormatException;
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/*     */       try {
/* 219 */         String[] nar = s.split("&");
/* 220 */         int radix = nar.length == 2 ? Integer.parseInt(nar[1]) : -1;
/* 221 */         return parseString(nar[0], radix);
/*     */       }
/*     */       catch (NumberFormatException e) {
/* 224 */         throw new ResourceConverter.ResourceConverterException("invalid " + this.type.getSimpleName(), s, e);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean supportsType(Class testType) {
/* 229 */       return (testType.equals(this.type)) || (testType.equals(this.primitiveType));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteResourceConverter extends ResourceConverter.INumberResourceConverter {
/*     */     ByteResourceConverter() {
/* 235 */       super(Byte.TYPE);
/*     */     }
/*     */     
/*     */     protected Number parseString(String s, int radix) throws NumberFormatException {
/* 239 */       return Byte.valueOf(radix == -1 ? Byte.decode(s).byteValue() : Byte.parseByte(s, radix));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IntegerResourceConverter extends ResourceConverter.INumberResourceConverter {
/*     */     IntegerResourceConverter() {
/* 245 */       super(Integer.TYPE);
/*     */     }
/*     */     
/*     */     protected Number parseString(String s, int radix) throws NumberFormatException {
/* 249 */       return Integer.valueOf(radix == -1 ? Integer.decode(s).intValue() : Integer.parseInt(s, radix));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LongResourceConverter extends ResourceConverter.INumberResourceConverter {
/*     */     LongResourceConverter() {
/* 255 */       super(Long.TYPE);
/*     */     }
/*     */     
/*     */     protected Number parseString(String s, int radix) throws NumberFormatException {
/* 259 */       return Long.valueOf(radix == -1 ? Long.decode(s).longValue() : Long.parseLong(s, radix));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ShortResourceConverter extends ResourceConverter.INumberResourceConverter {
/*     */     ShortResourceConverter() {
/* 265 */       super(Short.TYPE);
/*     */     }
/*     */     
/*     */     protected Number parseString(String s, int radix) throws NumberFormatException {
/* 269 */       return Short.valueOf(radix == -1 ? Short.decode(s).shortValue() : Short.parseShort(s, radix));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MessageFormatResourceConverter extends ResourceConverter {
/*     */     MessageFormatResourceConverter() {
/* 275 */       super();
/*     */     }
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) {
/* 279 */       return new MessageFormat(s);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class URLResourceConverter extends ResourceConverter {
/*     */     URLResourceConverter() {
/* 285 */       super();
/*     */     }
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/*     */       try {
/* 290 */         return new URL(s);
/*     */       }
/*     */       catch (MalformedURLException e) {
/* 293 */         throw new ResourceConverter.ResourceConverterException("invalid URL", s, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class URIResourceConverter extends ResourceConverter {
/*     */     URIResourceConverter() {
/* 300 */       super();
/*     */     }
/*     */     
/*     */     public Object parseString(String s, ResourceMap ignore) throws ResourceConverter.ResourceConverterException {
/*     */       try {
/* 305 */         return new URI(s);
/*     */       }
/*     */       catch (URISyntaxException e) {
/* 308 */         throw new ResourceConverter.ResourceConverterException("invalid URI", s, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ResourceConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */