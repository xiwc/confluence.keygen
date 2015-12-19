/*   1:    */ package org.apache.commons.codec.language;
/*   2:    */ 
/*   3:    */ import org.apache.commons.codec.EncoderException;
/*   4:    */ import org.apache.commons.codec.StringEncoder;
/*   5:    */ 
/*   6:    */ public class RefinedSoundex
/*   7:    */   implements StringEncoder
/*   8:    */ {
/*   9:    */   public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";
/*  10: 43 */   public static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
/*  11:    */   private final char[] soundexMapping;
/*  12: 56 */   public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();
/*  13:    */   
/*  14:    */   public RefinedSoundex()
/*  15:    */   {
/*  16: 63 */     this.soundexMapping = US_ENGLISH_MAPPING;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public RefinedSoundex(char[] mapping)
/*  20:    */   {
/*  21: 76 */     this.soundexMapping = new char[mapping.length];
/*  22: 77 */     System.arraycopy(mapping, 0, this.soundexMapping, 0, mapping.length);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public RefinedSoundex(String mapping)
/*  26:    */   {
/*  27: 89 */     this.soundexMapping = mapping.toCharArray();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int difference(String s1, String s2)
/*  31:    */     throws EncoderException
/*  32:    */   {
/*  33:115 */     return SoundexUtils.difference(this, s1, s2);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object encode(Object pObject)
/*  37:    */     throws EncoderException
/*  38:    */   {
/*  39:132 */     if (!(pObject instanceof String)) {
/*  40:133 */       throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
/*  41:    */     }
/*  42:135 */     return soundex((String)pObject);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String encode(String pString)
/*  46:    */   {
/*  47:146 */     return soundex(pString);
/*  48:    */   }
/*  49:    */   
/*  50:    */   char getMappingCode(char c)
/*  51:    */   {
/*  52:159 */     if (!Character.isLetter(c)) {
/*  53:160 */       return '\000';
/*  54:    */     }
/*  55:162 */     return this.soundexMapping[(Character.toUpperCase(c) - 'A')];
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String soundex(String str)
/*  59:    */   {
/*  60:173 */     if (str == null) {
/*  61:174 */       return null;
/*  62:    */     }
/*  63:176 */     str = SoundexUtils.clean(str);
/*  64:177 */     if (str.length() == 0) {
/*  65:178 */       return str;
/*  66:    */     }
/*  67:181 */     StringBuffer sBuf = new StringBuffer();
/*  68:182 */     sBuf.append(str.charAt(0));
/*  69:    */     
/*  70:    */ 
/*  71:185 */     char last = '*';
/*  72:187 */     for (int i = 0; i < str.length(); i++)
/*  73:    */     {
/*  74:189 */       char current = getMappingCode(str.charAt(i));
/*  75:190 */       if (current != last)
/*  76:    */       {
/*  77:192 */         if (current != 0) {
/*  78:193 */           sBuf.append(current);
/*  79:    */         }
/*  80:196 */         last = current;
/*  81:    */       }
/*  82:    */     }
/*  83:200 */     return sBuf.toString();
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.RefinedSoundex
 * JD-Core Version:    0.7.0.1
 */