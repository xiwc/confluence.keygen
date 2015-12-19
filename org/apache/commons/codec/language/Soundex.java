/*   1:    */ package org.apache.commons.codec.language;
/*   2:    */ 
/*   3:    */ import org.apache.commons.codec.EncoderException;
/*   4:    */ import org.apache.commons.codec.StringEncoder;
/*   5:    */ 
/*   6:    */ public class Soundex
/*   7:    */   implements StringEncoder
/*   8:    */ {
/*   9:    */   public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
/*  10: 50 */   public static final char[] US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
/*  11: 57 */   public static final Soundex US_ENGLISH = new Soundex();
/*  12:    */   
/*  13:    */   public int difference(String s1, String s2)
/*  14:    */     throws EncoderException
/*  15:    */   {
/*  16: 80 */     return SoundexUtils.difference(this, s1, s2);
/*  17:    */   }
/*  18:    */   
/*  19:    */   /**
/*  20:    */    * @deprecated
/*  21:    */    */
/*  22: 88 */   private int maxLength = 4;
/*  23:    */   private final char[] soundexMapping;
/*  24:    */   
/*  25:    */   public Soundex()
/*  26:    */   {
/*  27:103 */     this.soundexMapping = US_ENGLISH_MAPPING;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Soundex(char[] mapping)
/*  31:    */   {
/*  32:117 */     this.soundexMapping = new char[mapping.length];
/*  33:118 */     System.arraycopy(mapping, 0, this.soundexMapping, 0, mapping.length);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Soundex(String mapping)
/*  37:    */   {
/*  38:130 */     this.soundexMapping = mapping.toCharArray();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object encode(Object pObject)
/*  42:    */     throws EncoderException
/*  43:    */   {
/*  44:147 */     if (!(pObject instanceof String)) {
/*  45:148 */       throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
/*  46:    */     }
/*  47:150 */     return soundex((String)pObject);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String encode(String pString)
/*  51:    */   {
/*  52:163 */     return soundex(pString);
/*  53:    */   }
/*  54:    */   
/*  55:    */   private char getMappingCode(String str, int index)
/*  56:    */   {
/*  57:181 */     char mappedChar = map(str.charAt(index));
/*  58:183 */     if ((index > 1) && (mappedChar != '0'))
/*  59:    */     {
/*  60:184 */       char hwChar = str.charAt(index - 1);
/*  61:185 */       if (('H' == hwChar) || ('W' == hwChar))
/*  62:    */       {
/*  63:186 */         char preHWChar = str.charAt(index - 2);
/*  64:187 */         char firstCode = map(preHWChar);
/*  65:188 */         if ((firstCode == mappedChar) || ('H' == preHWChar) || ('W' == preHWChar)) {
/*  66:189 */           return '\000';
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70:193 */     return mappedChar;
/*  71:    */   }
/*  72:    */   
/*  73:    */   /**
/*  74:    */    * @deprecated
/*  75:    */    */
/*  76:    */   public int getMaxLength()
/*  77:    */   {
/*  78:203 */     return this.maxLength;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private char[] getSoundexMapping()
/*  82:    */   {
/*  83:212 */     return this.soundexMapping;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private char map(char ch)
/*  87:    */   {
/*  88:225 */     int index = ch - 'A';
/*  89:226 */     if ((index < 0) || (index >= getSoundexMapping().length)) {
/*  90:227 */       throw new IllegalArgumentException("The character is not mapped: " + ch);
/*  91:    */     }
/*  92:229 */     return getSoundexMapping()[index];
/*  93:    */   }
/*  94:    */   
/*  95:    */   /**
/*  96:    */    * @deprecated
/*  97:    */    */
/*  98:    */   public void setMaxLength(int maxLength)
/*  99:    */   {
/* 100:240 */     this.maxLength = maxLength;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String soundex(String str)
/* 104:    */   {
/* 105:253 */     if (str == null) {
/* 106:254 */       return null;
/* 107:    */     }
/* 108:256 */     str = SoundexUtils.clean(str);
/* 109:257 */     if (str.length() == 0) {
/* 110:258 */       return str;
/* 111:    */     }
/* 112:260 */     char[] out = { '0', '0', '0', '0' };
/* 113:    */     
/* 114:262 */     int incount = 1;int count = 1;
/* 115:263 */     out[0] = str.charAt(0);
/* 116:    */     
/* 117:265 */     char last = getMappingCode(str, 0);
/* 118:266 */     while ((incount < str.length()) && (count < out.length))
/* 119:    */     {
/* 120:267 */       char mapped = getMappingCode(str, incount++);
/* 121:268 */       if (mapped != 0)
/* 122:    */       {
/* 123:269 */         if ((mapped != '0') && (mapped != last)) {
/* 124:270 */           out[(count++)] = mapped;
/* 125:    */         }
/* 126:272 */         last = mapped;
/* 127:    */       }
/* 128:    */     }
/* 129:275 */     return new String(out);
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.Soundex
 * JD-Core Version:    0.7.0.1
 */