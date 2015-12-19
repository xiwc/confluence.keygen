/*   1:    */ package org.apache.commons.codec.language;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.commons.codec.EncoderException;
/*   5:    */ import org.apache.commons.codec.StringEncoder;
/*   6:    */ 
/*   7:    */ public class Caverphone
/*   8:    */   implements StringEncoder
/*   9:    */ {
/*  10:    */   public String caverphone(String txt)
/*  11:    */   {
/*  12: 54 */     if ((txt == null) || (txt.length() == 0)) {
/*  13: 55 */       return "1111111111";
/*  14:    */     }
/*  15: 59 */     txt = txt.toLowerCase(Locale.ENGLISH);
/*  16:    */     
/*  17:    */ 
/*  18: 62 */     txt = txt.replaceAll("[^a-z]", "");
/*  19:    */     
/*  20:    */ 
/*  21: 65 */     txt = txt.replaceAll("e$", "");
/*  22:    */     
/*  23:    */ 
/*  24: 68 */     txt = txt.replaceAll("^cough", "cou2f");
/*  25: 69 */     txt = txt.replaceAll("^rough", "rou2f");
/*  26: 70 */     txt = txt.replaceAll("^tough", "tou2f");
/*  27: 71 */     txt = txt.replaceAll("^enough", "enou2f");
/*  28: 72 */     txt = txt.replaceAll("^trough", "trou2f");
/*  29: 73 */     txt = txt.replaceAll("^gn", "2n");
/*  30: 74 */     txt = txt.replaceAll("^mb", "m2");
/*  31:    */     
/*  32:    */ 
/*  33: 77 */     txt = txt.replaceAll("cq", "2q");
/*  34: 78 */     txt = txt.replaceAll("ci", "si");
/*  35: 79 */     txt = txt.replaceAll("ce", "se");
/*  36: 80 */     txt = txt.replaceAll("cy", "sy");
/*  37: 81 */     txt = txt.replaceAll("tch", "2ch");
/*  38: 82 */     txt = txt.replaceAll("c", "k");
/*  39: 83 */     txt = txt.replaceAll("q", "k");
/*  40: 84 */     txt = txt.replaceAll("x", "k");
/*  41: 85 */     txt = txt.replaceAll("v", "f");
/*  42: 86 */     txt = txt.replaceAll("dg", "2g");
/*  43: 87 */     txt = txt.replaceAll("tio", "sio");
/*  44: 88 */     txt = txt.replaceAll("tia", "sia");
/*  45: 89 */     txt = txt.replaceAll("d", "t");
/*  46: 90 */     txt = txt.replaceAll("ph", "fh");
/*  47: 91 */     txt = txt.replaceAll("b", "p");
/*  48: 92 */     txt = txt.replaceAll("sh", "s2");
/*  49: 93 */     txt = txt.replaceAll("z", "s");
/*  50: 94 */     txt = txt.replaceAll("^[aeiou]", "A");
/*  51: 95 */     txt = txt.replaceAll("[aeiou]", "3");
/*  52: 96 */     txt = txt.replaceAll("j", "y");
/*  53: 97 */     txt = txt.replaceAll("^y3", "Y3");
/*  54: 98 */     txt = txt.replaceAll("^y", "A");
/*  55: 99 */     txt = txt.replaceAll("y", "3");
/*  56:100 */     txt = txt.replaceAll("3gh3", "3kh3");
/*  57:101 */     txt = txt.replaceAll("gh", "22");
/*  58:102 */     txt = txt.replaceAll("g", "k");
/*  59:103 */     txt = txt.replaceAll("s+", "S");
/*  60:104 */     txt = txt.replaceAll("t+", "T");
/*  61:105 */     txt = txt.replaceAll("p+", "P");
/*  62:106 */     txt = txt.replaceAll("k+", "K");
/*  63:107 */     txt = txt.replaceAll("f+", "F");
/*  64:108 */     txt = txt.replaceAll("m+", "M");
/*  65:109 */     txt = txt.replaceAll("n+", "N");
/*  66:110 */     txt = txt.replaceAll("w3", "W3");
/*  67:    */     
/*  68:112 */     txt = txt.replaceAll("wh3", "Wh3");
/*  69:113 */     txt = txt.replaceAll("w$", "3");
/*  70:    */     
/*  71:115 */     txt = txt.replaceAll("w", "2");
/*  72:116 */     txt = txt.replaceAll("^h", "A");
/*  73:117 */     txt = txt.replaceAll("h", "2");
/*  74:118 */     txt = txt.replaceAll("r3", "R3");
/*  75:119 */     txt = txt.replaceAll("r$", "3");
/*  76:    */     
/*  77:121 */     txt = txt.replaceAll("r", "2");
/*  78:122 */     txt = txt.replaceAll("l3", "L3");
/*  79:123 */     txt = txt.replaceAll("l$", "3");
/*  80:    */     
/*  81:125 */     txt = txt.replaceAll("l", "2");
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:131 */     txt = txt.replaceAll("2", "");
/*  88:132 */     txt = txt.replaceAll("3$", "A");
/*  89:133 */     txt = txt.replaceAll("3", "");
/*  90:    */     
/*  91:    */ 
/*  92:136 */     txt = txt + "111111" + "1111";
/*  93:    */     
/*  94:    */ 
/*  95:139 */     return txt.substring(0, 10);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object encode(Object pObject)
/*  99:    */     throws EncoderException
/* 100:    */   {
/* 101:155 */     if (!(pObject instanceof String)) {
/* 102:156 */       throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
/* 103:    */     }
/* 104:158 */     return caverphone((String)pObject);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String encode(String pString)
/* 108:    */   {
/* 109:168 */     return caverphone(pString);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isCaverphoneEqual(String str1, String str2)
/* 113:    */   {
/* 114:180 */     return caverphone(str1).equals(caverphone(str2));
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.Caverphone
 * JD-Core Version:    0.7.0.1
 */