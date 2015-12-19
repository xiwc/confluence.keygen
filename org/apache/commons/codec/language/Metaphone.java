/*   1:    */ package org.apache.commons.codec.language;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.commons.codec.EncoderException;
/*   5:    */ import org.apache.commons.codec.StringEncoder;
/*   6:    */ 
/*   7:    */ public class Metaphone
/*   8:    */   implements StringEncoder
/*   9:    */ {
/*  10:    */   private static final String VOWELS = "AEIOU";
/*  11:    */   private static final String FRONTV = "EIY";
/*  12:    */   private static final String VARSON = "CSPTG";
/*  13: 63 */   private int maxCodeLen = 4;
/*  14:    */   
/*  15:    */   public String metaphone(String txt)
/*  16:    */   {
/*  17: 83 */     boolean hard = false;
/*  18: 84 */     if ((txt == null) || (txt.length() == 0)) {
/*  19: 85 */       return "";
/*  20:    */     }
/*  21: 88 */     if (txt.length() == 1) {
/*  22: 89 */       return txt.toUpperCase(Locale.ENGLISH);
/*  23:    */     }
/*  24: 92 */     char[] inwd = txt.toUpperCase(Locale.ENGLISH).toCharArray();
/*  25:    */     
/*  26: 94 */     StringBuffer local = new StringBuffer(40);
/*  27: 95 */     StringBuffer code = new StringBuffer(10);
/*  28: 97 */     switch (inwd[0])
/*  29:    */     {
/*  30:    */     case 'G': 
/*  31:    */     case 'K': 
/*  32:    */     case 'P': 
/*  33:101 */       if (inwd[1] == 'N') {
/*  34:102 */         local.append(inwd, 1, inwd.length - 1);
/*  35:    */       } else {
/*  36:104 */         local.append(inwd);
/*  37:    */       }
/*  38:106 */       break;
/*  39:    */     case 'A': 
/*  40:108 */       if (inwd[1] == 'E') {
/*  41:109 */         local.append(inwd, 1, inwd.length - 1);
/*  42:    */       } else {
/*  43:111 */         local.append(inwd);
/*  44:    */       }
/*  45:113 */       break;
/*  46:    */     case 'W': 
/*  47:115 */       if (inwd[1] == 'R')
/*  48:    */       {
/*  49:116 */         local.append(inwd, 1, inwd.length - 1);
/*  50:    */       }
/*  51:119 */       else if (inwd[1] == 'H')
/*  52:    */       {
/*  53:120 */         local.append(inwd, 1, inwd.length - 1);
/*  54:121 */         local.setCharAt(0, 'W');
/*  55:    */       }
/*  56:    */       else
/*  57:    */       {
/*  58:123 */         local.append(inwd);
/*  59:    */       }
/*  60:125 */       break;
/*  61:    */     case 'X': 
/*  62:127 */       inwd[0] = 'S';
/*  63:128 */       local.append(inwd);
/*  64:129 */       break;
/*  65:    */     default: 
/*  66:131 */       local.append(inwd);
/*  67:    */     }
/*  68:134 */     int wdsz = local.length();
/*  69:135 */     int n = 0;
/*  70:137 */     while ((code.length() < getMaxCodeLen()) && (n < wdsz))
/*  71:    */     {
/*  72:139 */       char symb = local.charAt(n);
/*  73:141 */       if ((symb != 'C') && (isPreviousChar(local, n, symb)))
/*  74:    */       {
/*  75:142 */         n++;
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:144 */         switch (symb)
/*  80:    */         {
/*  81:    */         case 'A': 
/*  82:    */         case 'E': 
/*  83:    */         case 'I': 
/*  84:    */         case 'O': 
/*  85:    */         case 'U': 
/*  86:146 */           if (n == 0) {
/*  87:147 */             code.append(symb);
/*  88:    */           }
/*  89:    */           break;
/*  90:    */         case 'B': 
/*  91:151 */           if ((!isPreviousChar(local, n, 'M')) || (!isLastChar(wdsz, n))) {
/*  92:155 */             code.append(symb);
/*  93:    */           }
/*  94:156 */           break;
/*  95:    */         case 'C': 
/*  96:159 */           if ((!isPreviousChar(local, n, 'S')) || (isLastChar(wdsz, n)) || ("EIY".indexOf(local.charAt(n + 1)) < 0)) {
/*  97:164 */             if (regionMatch(local, n, "CIA")) {
/*  98:165 */               code.append('X');
/*  99:168 */             } else if ((!isLastChar(wdsz, n)) && ("EIY".indexOf(local.charAt(n + 1)) >= 0)) {
/* 100:170 */               code.append('S');
/* 101:173 */             } else if ((isPreviousChar(local, n, 'S')) && (isNextChar(local, n, 'H'))) {
/* 102:175 */               code.append('K');
/* 103:178 */             } else if (isNextChar(local, n, 'H'))
/* 104:    */             {
/* 105:179 */               if ((n == 0) && (wdsz >= 3) && (isVowel(local, 2))) {
/* 106:182 */                 code.append('K');
/* 107:    */               } else {
/* 108:184 */                 code.append('X');
/* 109:    */               }
/* 110:    */             }
/* 111:    */             else {
/* 112:187 */               code.append('K');
/* 113:    */             }
/* 114:    */           }
/* 115:189 */           break;
/* 116:    */         case 'D': 
/* 117:191 */           if ((!isLastChar(wdsz, n + 1)) && (isNextChar(local, n, 'G')) && ("EIY".indexOf(local.charAt(n + 2)) >= 0))
/* 118:    */           {
/* 119:194 */             code.append('J');n += 2;
/* 120:    */           }
/* 121:    */           else
/* 122:    */           {
/* 123:196 */             code.append('T');
/* 124:    */           }
/* 125:198 */           break;
/* 126:    */         case 'G': 
/* 127:200 */           if ((!isLastChar(wdsz, n + 1)) || (!isNextChar(local, n, 'H'))) {
/* 128:204 */             if ((isLastChar(wdsz, n + 1)) || (!isNextChar(local, n, 'H')) || (isVowel(local, n + 2))) {
/* 129:209 */               if ((n <= 0) || ((!regionMatch(local, n, "GN")) && (!regionMatch(local, n, "GNED"))))
/* 130:    */               {
/* 131:214 */                 if (isPreviousChar(local, n, 'G')) {
/* 132:216 */                   hard = true;
/* 133:    */                 } else {
/* 134:218 */                   hard = false;
/* 135:    */                 }
/* 136:220 */                 if ((!isLastChar(wdsz, n)) && ("EIY".indexOf(local.charAt(n + 1)) >= 0) && (!hard)) {
/* 137:223 */                   code.append('J');
/* 138:    */                 } else {
/* 139:225 */                   code.append('K');
/* 140:    */                 }
/* 141:    */               }
/* 142:    */             }
/* 143:    */           }
/* 144:227 */           break;
/* 145:    */         case 'H': 
/* 146:229 */           if (!isLastChar(wdsz, n)) {
/* 147:232 */             if ((n <= 0) || ("CSPTG".indexOf(local.charAt(n - 1)) < 0)) {
/* 148:236 */               if (isVowel(local, n + 1)) {
/* 149:237 */                 code.append('H');
/* 150:    */               }
/* 151:    */             }
/* 152:    */           }
/* 153:    */           break;
/* 154:    */         case 'F': 
/* 155:    */         case 'J': 
/* 156:    */         case 'L': 
/* 157:    */         case 'M': 
/* 158:    */         case 'N': 
/* 159:    */         case 'R': 
/* 160:246 */           code.append(symb);
/* 161:247 */           break;
/* 162:    */         case 'K': 
/* 163:249 */           if (n > 0)
/* 164:    */           {
/* 165:250 */             if (!isPreviousChar(local, n, 'C')) {
/* 166:251 */               code.append(symb);
/* 167:    */             }
/* 168:    */           }
/* 169:    */           else {
/* 170:254 */             code.append(symb);
/* 171:    */           }
/* 172:256 */           break;
/* 173:    */         case 'P': 
/* 174:258 */           if (isNextChar(local, n, 'H')) {
/* 175:260 */             code.append('F');
/* 176:    */           } else {
/* 177:262 */             code.append(symb);
/* 178:    */           }
/* 179:264 */           break;
/* 180:    */         case 'Q': 
/* 181:266 */           code.append('K');
/* 182:267 */           break;
/* 183:    */         case 'S': 
/* 184:269 */           if ((regionMatch(local, n, "SH")) || (regionMatch(local, n, "SIO")) || (regionMatch(local, n, "SIA"))) {
/* 185:272 */             code.append('X');
/* 186:    */           } else {
/* 187:274 */             code.append('S');
/* 188:    */           }
/* 189:276 */           break;
/* 190:    */         case 'T': 
/* 191:278 */           if ((regionMatch(local, n, "TIA")) || (regionMatch(local, n, "TIO"))) {
/* 192:280 */             code.append('X');
/* 193:283 */           } else if (!regionMatch(local, n, "TCH")) {
/* 194:288 */             if (regionMatch(local, n, "TH")) {
/* 195:289 */               code.append('0');
/* 196:    */             } else {
/* 197:291 */               code.append('T');
/* 198:    */             }
/* 199:    */           }
/* 200:293 */           break;
/* 201:    */         case 'V': 
/* 202:295 */           code.append('F'); break;
/* 203:    */         case 'W': 
/* 204:    */         case 'Y': 
/* 205:297 */           if ((!isLastChar(wdsz, n)) && (isVowel(local, n + 1))) {
/* 206:299 */             code.append(symb);
/* 207:    */           }
/* 208:    */           break;
/* 209:    */         case 'X': 
/* 210:303 */           code.append('K');code.append('S');
/* 211:304 */           break;
/* 212:    */         case 'Z': 
/* 213:306 */           code.append('S');
/* 214:    */         }
/* 215:308 */         n++;
/* 216:    */       }
/* 217:310 */       if (code.length() > getMaxCodeLen()) {
/* 218:311 */         code.setLength(getMaxCodeLen());
/* 219:    */       }
/* 220:    */     }
/* 221:314 */     return code.toString();
/* 222:    */   }
/* 223:    */   
/* 224:    */   private boolean isVowel(StringBuffer string, int index)
/* 225:    */   {
/* 226:318 */     return "AEIOU".indexOf(string.charAt(index)) >= 0;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private boolean isPreviousChar(StringBuffer string, int index, char c)
/* 230:    */   {
/* 231:322 */     boolean matches = false;
/* 232:323 */     if ((index > 0) && (index < string.length())) {
/* 233:325 */       matches = string.charAt(index - 1) == c;
/* 234:    */     }
/* 235:327 */     return matches;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private boolean isNextChar(StringBuffer string, int index, char c)
/* 239:    */   {
/* 240:331 */     boolean matches = false;
/* 241:332 */     if ((index >= 0) && (index < string.length() - 1)) {
/* 242:334 */       matches = string.charAt(index + 1) == c;
/* 243:    */     }
/* 244:336 */     return matches;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private boolean regionMatch(StringBuffer string, int index, String test)
/* 248:    */   {
/* 249:340 */     boolean matches = false;
/* 250:341 */     if ((index >= 0) && (index + test.length() - 1 < string.length()))
/* 251:    */     {
/* 252:343 */       String substring = string.substring(index, index + test.length());
/* 253:344 */       matches = substring.equals(test);
/* 254:    */     }
/* 255:346 */     return matches;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private boolean isLastChar(int wdsz, int n)
/* 259:    */   {
/* 260:350 */     return n + 1 == wdsz;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Object encode(Object pObject)
/* 264:    */     throws EncoderException
/* 265:    */   {
/* 266:367 */     if (!(pObject instanceof String)) {
/* 267:368 */       throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
/* 268:    */     }
/* 269:370 */     return metaphone((String)pObject);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String encode(String pString)
/* 273:    */   {
/* 274:380 */     return metaphone(pString);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean isMetaphoneEqual(String str1, String str2)
/* 278:    */   {
/* 279:392 */     return metaphone(str1).equals(metaphone(str2));
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int getMaxCodeLen()
/* 283:    */   {
/* 284:399 */     return this.maxCodeLen;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setMaxCodeLen(int maxCodeLen)
/* 288:    */   {
/* 289:405 */     this.maxCodeLen = maxCodeLen;
/* 290:    */   }
/* 291:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.Metaphone
 * JD-Core Version:    0.7.0.1
 */