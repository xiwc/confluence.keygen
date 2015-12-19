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
/*     */ public class RefinedSoundex
/*     */   implements StringEncoder
/*     */ {
/*     */   public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
/*  43 */   public static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
/*     */   
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
/*  56 */   public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public RefinedSoundex()
/*     */   {
/*  63 */     this.soundexMapping = US_ENGLISH_MAPPING;
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
/*     */   public RefinedSoundex(char[] mapping)
/*     */   {
/*  76 */     this.soundexMapping = new char[mapping.length];
/*  77 */     System.arraycopy(mapping, 0, this.soundexMapping, 0, mapping.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RefinedSoundex(String mapping)
/*     */   {
/*  89 */     this.soundexMapping = mapping.toCharArray();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int difference(String s1, String s2)
/*     */     throws EncoderException
/*     */   {
/* 115 */     return SoundexUtils.difference(this, s1, s2);
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
/* 132 */     if (!(pObject instanceof String)) {
/* 133 */       throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
/*     */     }
/* 135 */     return soundex((String)pObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encode(String pString)
/*     */   {
/* 146 */     return soundex(pString);
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
/*     */   char getMappingCode(char c)
/*     */   {
/* 159 */     if (!Character.isLetter(c)) {
/* 160 */       return '\000';
/*     */     }
/* 162 */     return this.soundexMapping[(Character.toUpperCase(c) - 'A')];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String soundex(String str)
/*     */   {
/* 173 */     if (str == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     str = SoundexUtils.clean(str);
/* 177 */     if (str.length() == 0) {
/* 178 */       return str;
/*     */     }
/*     */     
/* 181 */     StringBuffer sBuf = new StringBuffer();
/* 182 */     sBuf.append(str.charAt(0));
/*     */     
/*     */ 
/* 185 */     char last = '*';
/*     */     
/* 187 */     for (int i = 0; i < str.length(); i++)
/*     */     {
/* 189 */       char current = getMappingCode(str.charAt(i));
/* 190 */       if (current != last)
/*     */       {
/* 192 */         if (current != 0) {
/* 193 */           sBuf.append(current);
/*     */         }
/*     */         
/* 196 */         last = current;
/*     */       }
/*     */     }
/*     */     
/* 200 */     return sBuf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\language\RefinedSoundex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */