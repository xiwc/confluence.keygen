/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ public class Caverphone
/*     */   implements StringEncoder
/*     */ {
/*     */   public String caverphone(String txt)
/*     */   {
/*  54 */     if ((txt == null) || (txt.length() == 0)) {
/*  55 */       return "1111111111";
/*     */     }
/*     */     
/*     */ 
/*  59 */     txt = txt.toLowerCase(Locale.ENGLISH);
/*     */     
/*     */ 
/*  62 */     txt = txt.replaceAll("[^a-z]", "");
/*     */     
/*     */ 
/*  65 */     txt = txt.replaceAll("e$", "");
/*     */     
/*     */ 
/*  68 */     txt = txt.replaceAll("^cough", "cou2f");
/*  69 */     txt = txt.replaceAll("^rough", "rou2f");
/*  70 */     txt = txt.replaceAll("^tough", "tou2f");
/*  71 */     txt = txt.replaceAll("^enough", "enou2f");
/*  72 */     txt = txt.replaceAll("^trough", "trou2f");
/*  73 */     txt = txt.replaceAll("^gn", "2n");
/*  74 */     txt = txt.replaceAll("^mb", "m2");
/*     */     
/*     */ 
/*  77 */     txt = txt.replaceAll("cq", "2q");
/*  78 */     txt = txt.replaceAll("ci", "si");
/*  79 */     txt = txt.replaceAll("ce", "se");
/*  80 */     txt = txt.replaceAll("cy", "sy");
/*  81 */     txt = txt.replaceAll("tch", "2ch");
/*  82 */     txt = txt.replaceAll("c", "k");
/*  83 */     txt = txt.replaceAll("q", "k");
/*  84 */     txt = txt.replaceAll("x", "k");
/*  85 */     txt = txt.replaceAll("v", "f");
/*  86 */     txt = txt.replaceAll("dg", "2g");
/*  87 */     txt = txt.replaceAll("tio", "sio");
/*  88 */     txt = txt.replaceAll("tia", "sia");
/*  89 */     txt = txt.replaceAll("d", "t");
/*  90 */     txt = txt.replaceAll("ph", "fh");
/*  91 */     txt = txt.replaceAll("b", "p");
/*  92 */     txt = txt.replaceAll("sh", "s2");
/*  93 */     txt = txt.replaceAll("z", "s");
/*  94 */     txt = txt.replaceAll("^[aeiou]", "A");
/*  95 */     txt = txt.replaceAll("[aeiou]", "3");
/*  96 */     txt = txt.replaceAll("j", "y");
/*  97 */     txt = txt.replaceAll("^y3", "Y3");
/*  98 */     txt = txt.replaceAll("^y", "A");
/*  99 */     txt = txt.replaceAll("y", "3");
/* 100 */     txt = txt.replaceAll("3gh3", "3kh3");
/* 101 */     txt = txt.replaceAll("gh", "22");
/* 102 */     txt = txt.replaceAll("g", "k");
/* 103 */     txt = txt.replaceAll("s+", "S");
/* 104 */     txt = txt.replaceAll("t+", "T");
/* 105 */     txt = txt.replaceAll("p+", "P");
/* 106 */     txt = txt.replaceAll("k+", "K");
/* 107 */     txt = txt.replaceAll("f+", "F");
/* 108 */     txt = txt.replaceAll("m+", "M");
/* 109 */     txt = txt.replaceAll("n+", "N");
/* 110 */     txt = txt.replaceAll("w3", "W3");
/*     */     
/* 112 */     txt = txt.replaceAll("wh3", "Wh3");
/* 113 */     txt = txt.replaceAll("w$", "3");
/*     */     
/* 115 */     txt = txt.replaceAll("w", "2");
/* 116 */     txt = txt.replaceAll("^h", "A");
/* 117 */     txt = txt.replaceAll("h", "2");
/* 118 */     txt = txt.replaceAll("r3", "R3");
/* 119 */     txt = txt.replaceAll("r$", "3");
/*     */     
/* 121 */     txt = txt.replaceAll("r", "2");
/* 122 */     txt = txt.replaceAll("l3", "L3");
/* 123 */     txt = txt.replaceAll("l$", "3");
/*     */     
/* 125 */     txt = txt.replaceAll("l", "2");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */     txt = txt.replaceAll("2", "");
/* 132 */     txt = txt.replaceAll("3$", "A");
/* 133 */     txt = txt.replaceAll("3", "");
/*     */     
/*     */ 
/* 136 */     txt = txt + "111111" + "1111";
/*     */     
/*     */ 
/* 139 */     return txt.substring(0, 10);
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
/*     */   public Object encode(Object pObject)
/*     */     throws EncoderException
/*     */   {
/* 155 */     if (!(pObject instanceof String)) {
/* 156 */       throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
/*     */     }
/* 158 */     return caverphone((String)pObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encode(String pString)
/*     */   {
/* 168 */     return caverphone(pString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCaverphoneEqual(String str1, String str2)
/*     */   {
/* 180 */     return caverphone(str1).equals(caverphone(str2));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\language\Caverphone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */