/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringEncoder;
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
/*     */ public class Soundex
/*     */   implements StringEncoder
/*     */ {
/*     */   public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
/*  50 */   public static final char[] US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */   public static final Soundex US_ENGLISH = new Soundex();
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
/*     */   public int difference(String s1, String s2)
/*     */     throws EncoderException
/*     */   {
/*  80 */     return SoundexUtils.difference(this, s1, s2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*  88 */   private int maxLength = 4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final char[] soundexMapping;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Soundex()
/*     */   {
/* 103 */     this.soundexMapping = US_ENGLISH_MAPPING;
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
/*     */   public Soundex(char[] mapping)
/*     */   {
/* 117 */     this.soundexMapping = new char[mapping.length];
/* 118 */     System.arraycopy(mapping, 0, this.soundexMapping, 0, mapping.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Soundex(String mapping)
/*     */   {
/* 130 */     this.soundexMapping = mapping.toCharArray();
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
/*     */   public Object encode(Object pObject)
/*     */     throws EncoderException
/*     */   {
/* 147 */     if (!(pObject instanceof String)) {
/* 148 */       throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
/*     */     }
/* 150 */     return soundex((String)pObject);
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
/*     */   public String encode(String pString)
/*     */   {
/* 163 */     return soundex(pString);
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
/*     */   private char getMappingCode(String str, int index)
/*     */   {
/* 181 */     char mappedChar = map(str.charAt(index));
/*     */     
/* 183 */     if ((index > 1) && (mappedChar != '0')) {
/* 184 */       char hwChar = str.charAt(index - 1);
/* 185 */       if (('H' == hwChar) || ('W' == hwChar)) {
/* 186 */         char preHWChar = str.charAt(index - 2);
/* 187 */         char firstCode = map(preHWChar);
/* 188 */         if ((firstCode == mappedChar) || ('H' == preHWChar) || ('W' == preHWChar)) {
/* 189 */           return '\000';
/*     */         }
/*     */       }
/*     */     }
/* 193 */     return mappedChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public int getMaxLength()
/*     */   {
/* 203 */     return this.maxLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] getSoundexMapping()
/*     */   {
/* 212 */     return this.soundexMapping;
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
/*     */   private char map(char ch)
/*     */   {
/* 225 */     int index = ch - 'A';
/* 226 */     if ((index < 0) || (index >= getSoundexMapping().length)) {
/* 227 */       throw new IllegalArgumentException("The character is not mapped: " + ch);
/*     */     }
/* 229 */     return getSoundexMapping()[index];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   /**
/*     */    * @deprecated
/*     */    */
/*     */   public void setMaxLength(int maxLength)
/*     */   {
/* 240 */     this.maxLength = maxLength;
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
/*     */   public String soundex(String str)
/*     */   {
/* 253 */     if (str == null) {
/* 254 */       return null;
/*     */     }
/* 256 */     str = SoundexUtils.clean(str);
/* 257 */     if (str.length() == 0) {
/* 258 */       return str;
/*     */     }
/* 260 */     char[] out = { '0', '0', '0', '0' };
/*     */     
/* 262 */     int incount = 1;int count = 1;
/* 263 */     out[0] = str.charAt(0);
/*     */     
/* 265 */     char last = getMappingCode(str, 0);
/* 266 */     while ((incount < str.length()) && (count < out.length)) {
/* 267 */       char mapped = getMappingCode(str, incount++);
/* 268 */       if (mapped != 0) {
/* 269 */         if ((mapped != '0') && (mapped != last)) {
/* 270 */           out[(count++)] = mapped;
/*     */         }
/* 272 */         last = mapped;
/*     */       }
/*     */     }
/* 275 */     return new String(out);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\language\Soundex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */