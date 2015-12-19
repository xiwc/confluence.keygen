/*    1:     */ package org.apache.commons.codec.language;
/*    2:     */ 
/*    3:     */ import java.util.Locale;
/*    4:     */ import org.apache.commons.codec.EncoderException;
/*    5:     */ import org.apache.commons.codec.StringEncoder;
/*    6:     */ 
/*    7:     */ public class DoubleMetaphone
/*    8:     */   implements StringEncoder
/*    9:     */ {
/*   10:     */   private static final String VOWELS = "AEIOUY";
/*   11:  47 */   private static final String[] SILENT_START = { "GN", "KN", "PN", "WR", "PS" };
/*   12:  49 */   private static final String[] L_R_N_M_B_H_F_V_W_SPACE = { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
/*   13:  51 */   private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
/*   14:  53 */   private static final String[] L_T_K_S_N_M_B_Z = { "L", "T", "K", "S", "N", "M", "B", "Z" };
/*   15:  59 */   protected int maxCodeLen = 4;
/*   16:     */   
/*   17:     */   public String doubleMetaphone(String value)
/*   18:     */   {
/*   19:  75 */     return doubleMetaphone(value, false);
/*   20:     */   }
/*   21:     */   
/*   22:     */   public String doubleMetaphone(String value, boolean alternate)
/*   23:     */   {
/*   24:  87 */     value = cleanInput(value);
/*   25:  88 */     if (value == null) {
/*   26:  89 */       return null;
/*   27:     */     }
/*   28:  92 */     boolean slavoGermanic = isSlavoGermanic(value);
/*   29:  93 */     int index = isSilentStart(value) ? 1 : 0;
/*   30:     */     
/*   31:  95 */     DoubleMetaphoneResult result = new DoubleMetaphoneResult(getMaxCodeLen());
/*   32:  97 */     while ((!result.isComplete()) && (index <= value.length() - 1)) {
/*   33:  98 */       switch (value.charAt(index))
/*   34:     */       {
/*   35:     */       case 'A': 
/*   36:     */       case 'E': 
/*   37:     */       case 'I': 
/*   38:     */       case 'O': 
/*   39:     */       case 'U': 
/*   40:     */       case 'Y': 
/*   41: 105 */         index = handleAEIOUY(value, result, index);
/*   42: 106 */         break;
/*   43:     */       case 'B': 
/*   44: 108 */         result.append('P');
/*   45: 109 */         index = charAt(value, index + 1) == 'B' ? index + 2 : index + 1;
/*   46: 110 */         break;
/*   47:     */       case 'Ç': 
/*   48: 113 */         result.append('S');
/*   49: 114 */         index++;
/*   50: 115 */         break;
/*   51:     */       case 'C': 
/*   52: 117 */         index = handleC(value, result, index);
/*   53: 118 */         break;
/*   54:     */       case 'D': 
/*   55: 120 */         index = handleD(value, result, index);
/*   56: 121 */         break;
/*   57:     */       case 'F': 
/*   58: 123 */         result.append('F');
/*   59: 124 */         index = charAt(value, index + 1) == 'F' ? index + 2 : index + 1;
/*   60: 125 */         break;
/*   61:     */       case 'G': 
/*   62: 127 */         index = handleG(value, result, index, slavoGermanic);
/*   63: 128 */         break;
/*   64:     */       case 'H': 
/*   65: 130 */         index = handleH(value, result, index);
/*   66: 131 */         break;
/*   67:     */       case 'J': 
/*   68: 133 */         index = handleJ(value, result, index, slavoGermanic);
/*   69: 134 */         break;
/*   70:     */       case 'K': 
/*   71: 136 */         result.append('K');
/*   72: 137 */         index = charAt(value, index + 1) == 'K' ? index + 2 : index + 1;
/*   73: 138 */         break;
/*   74:     */       case 'L': 
/*   75: 140 */         index = handleL(value, result, index);
/*   76: 141 */         break;
/*   77:     */       case 'M': 
/*   78: 143 */         result.append('M');
/*   79: 144 */         index = conditionM0(value, index) ? index + 2 : index + 1;
/*   80: 145 */         break;
/*   81:     */       case 'N': 
/*   82: 147 */         result.append('N');
/*   83: 148 */         index = charAt(value, index + 1) == 'N' ? index + 2 : index + 1;
/*   84: 149 */         break;
/*   85:     */       case 'Ñ': 
/*   86: 152 */         result.append('N');
/*   87: 153 */         index++;
/*   88: 154 */         break;
/*   89:     */       case 'P': 
/*   90: 156 */         index = handleP(value, result, index);
/*   91: 157 */         break;
/*   92:     */       case 'Q': 
/*   93: 159 */         result.append('K');
/*   94: 160 */         index = charAt(value, index + 1) == 'Q' ? index + 2 : index + 1;
/*   95: 161 */         break;
/*   96:     */       case 'R': 
/*   97: 163 */         index = handleR(value, result, index, slavoGermanic);
/*   98: 164 */         break;
/*   99:     */       case 'S': 
/*  100: 166 */         index = handleS(value, result, index, slavoGermanic);
/*  101: 167 */         break;
/*  102:     */       case 'T': 
/*  103: 169 */         index = handleT(value, result, index);
/*  104: 170 */         break;
/*  105:     */       case 'V': 
/*  106: 172 */         result.append('F');
/*  107: 173 */         index = charAt(value, index + 1) == 'V' ? index + 2 : index + 1;
/*  108: 174 */         break;
/*  109:     */       case 'W': 
/*  110: 176 */         index = handleW(value, result, index);
/*  111: 177 */         break;
/*  112:     */       case 'X': 
/*  113: 179 */         index = handleX(value, result, index);
/*  114: 180 */         break;
/*  115:     */       case 'Z': 
/*  116: 182 */         index = handleZ(value, result, index, slavoGermanic);
/*  117: 183 */         break;
/*  118:     */       default: 
/*  119: 185 */         index++;
/*  120:     */       }
/*  121:     */     }
/*  122: 190 */     return alternate ? result.getAlternate() : result.getPrimary();
/*  123:     */   }
/*  124:     */   
/*  125:     */   public Object encode(Object obj)
/*  126:     */     throws EncoderException
/*  127:     */   {
/*  128: 202 */     if (!(obj instanceof String)) {
/*  129: 203 */       throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
/*  130:     */     }
/*  131: 205 */     return doubleMetaphone((String)obj);
/*  132:     */   }
/*  133:     */   
/*  134:     */   public String encode(String value)
/*  135:     */   {
/*  136: 215 */     return doubleMetaphone(value);
/*  137:     */   }
/*  138:     */   
/*  139:     */   public boolean isDoubleMetaphoneEqual(String value1, String value2)
/*  140:     */   {
/*  141: 229 */     return isDoubleMetaphoneEqual(value1, value2, false);
/*  142:     */   }
/*  143:     */   
/*  144:     */   public boolean isDoubleMetaphoneEqual(String value1, String value2, boolean alternate)
/*  145:     */   {
/*  146: 245 */     return doubleMetaphone(value1, alternate).equals(doubleMetaphone(value2, alternate));
/*  147:     */   }
/*  148:     */   
/*  149:     */   public int getMaxCodeLen()
/*  150:     */   {
/*  151: 254 */     return this.maxCodeLen;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void setMaxCodeLen(int maxCodeLen)
/*  155:     */   {
/*  156: 262 */     this.maxCodeLen = maxCodeLen;
/*  157:     */   }
/*  158:     */   
/*  159:     */   private int handleAEIOUY(String value, DoubleMetaphoneResult result, int index)
/*  160:     */   {
/*  161: 272 */     if (index == 0) {
/*  162: 273 */       result.append('A');
/*  163:     */     }
/*  164: 275 */     return index + 1;
/*  165:     */   }
/*  166:     */   
/*  167:     */   private int handleC(String value, DoubleMetaphoneResult result, int index)
/*  168:     */   {
/*  169: 284 */     if (conditionC0(value, index))
/*  170:     */     {
/*  171: 285 */       result.append('K');
/*  172: 286 */       index += 2;
/*  173:     */     }
/*  174: 287 */     else if ((index == 0) && (contains(value, index, 6, "CAESAR")))
/*  175:     */     {
/*  176: 288 */       result.append('S');
/*  177: 289 */       index += 2;
/*  178:     */     }
/*  179: 290 */     else if (contains(value, index, 2, "CH"))
/*  180:     */     {
/*  181: 291 */       index = handleCH(value, result, index);
/*  182:     */     }
/*  183: 292 */     else if ((contains(value, index, 2, "CZ")) && (!contains(value, index - 2, 4, "WICZ")))
/*  184:     */     {
/*  185: 295 */       result.append('S', 'X');
/*  186: 296 */       index += 2;
/*  187:     */     }
/*  188: 297 */     else if (contains(value, index + 1, 3, "CIA"))
/*  189:     */     {
/*  190: 299 */       result.append('X');
/*  191: 300 */       index += 3;
/*  192:     */     }
/*  193:     */     else
/*  194:     */     {
/*  195: 301 */       if ((contains(value, index, 2, "CC")) && ((index != 1) || (charAt(value, 0) != 'M'))) {
/*  196: 304 */         return handleCC(value, result, index);
/*  197:     */       }
/*  198: 305 */       if (contains(value, index, 2, "CK", "CG", "CQ"))
/*  199:     */       {
/*  200: 306 */         result.append('K');
/*  201: 307 */         index += 2;
/*  202:     */       }
/*  203: 308 */       else if (contains(value, index, 2, "CI", "CE", "CY"))
/*  204:     */       {
/*  205: 310 */         if (contains(value, index, 3, "CIO", "CIE", "CIA")) {
/*  206: 311 */           result.append('S', 'X');
/*  207:     */         } else {
/*  208: 313 */           result.append('S');
/*  209:     */         }
/*  210: 315 */         index += 2;
/*  211:     */       }
/*  212:     */       else
/*  213:     */       {
/*  214: 317 */         result.append('K');
/*  215: 318 */         if (contains(value, index + 1, 2, " C", " Q", " G")) {
/*  216: 320 */           index += 3;
/*  217: 321 */         } else if ((contains(value, index + 1, 1, "C", "K", "Q")) && (!contains(value, index + 1, 2, "CE", "CI"))) {
/*  218: 323 */           index += 2;
/*  219:     */         } else {
/*  220: 325 */           index++;
/*  221:     */         }
/*  222:     */       }
/*  223:     */     }
/*  224: 329 */     return index;
/*  225:     */   }
/*  226:     */   
/*  227:     */   private int handleCC(String value, DoubleMetaphoneResult result, int index)
/*  228:     */   {
/*  229: 338 */     if ((contains(value, index + 2, 1, "I", "E", "H")) && (!contains(value, index + 2, 2, "HU")))
/*  230:     */     {
/*  231: 341 */       if (((index == 1) && (charAt(value, index - 1) == 'A')) || (contains(value, index - 1, 5, "UCCEE", "UCCES"))) {
/*  232: 344 */         result.append("KS");
/*  233:     */       } else {
/*  234: 347 */         result.append('X');
/*  235:     */       }
/*  236: 349 */       index += 3;
/*  237:     */     }
/*  238:     */     else
/*  239:     */     {
/*  240: 351 */       result.append('K');
/*  241: 352 */       index += 2;
/*  242:     */     }
/*  243: 355 */     return index;
/*  244:     */   }
/*  245:     */   
/*  246:     */   private int handleCH(String value, DoubleMetaphoneResult result, int index)
/*  247:     */   {
/*  248: 364 */     if ((index > 0) && (contains(value, index, 4, "CHAE")))
/*  249:     */     {
/*  250: 365 */       result.append('K', 'X');
/*  251: 366 */       return index + 2;
/*  252:     */     }
/*  253: 367 */     if (conditionCH0(value, index))
/*  254:     */     {
/*  255: 369 */       result.append('K');
/*  256: 370 */       return index + 2;
/*  257:     */     }
/*  258: 371 */     if (conditionCH1(value, index))
/*  259:     */     {
/*  260: 373 */       result.append('K');
/*  261: 374 */       return index + 2;
/*  262:     */     }
/*  263: 376 */     if (index > 0)
/*  264:     */     {
/*  265: 377 */       if (contains(value, 0, 2, "MC")) {
/*  266: 378 */         result.append('K');
/*  267:     */       } else {
/*  268: 380 */         result.append('X', 'K');
/*  269:     */       }
/*  270:     */     }
/*  271:     */     else {
/*  272: 383 */       result.append('X');
/*  273:     */     }
/*  274: 385 */     return index + 2;
/*  275:     */   }
/*  276:     */   
/*  277:     */   private int handleD(String value, DoubleMetaphoneResult result, int index)
/*  278:     */   {
/*  279: 395 */     if (contains(value, index, 2, "DG"))
/*  280:     */     {
/*  281: 397 */       if (contains(value, index + 2, 1, "I", "E", "Y"))
/*  282:     */       {
/*  283: 398 */         result.append('J');
/*  284: 399 */         index += 3;
/*  285:     */       }
/*  286:     */       else
/*  287:     */       {
/*  288: 402 */         result.append("TK");
/*  289: 403 */         index += 2;
/*  290:     */       }
/*  291:     */     }
/*  292: 405 */     else if (contains(value, index, 2, "DT", "DD"))
/*  293:     */     {
/*  294: 406 */       result.append('T');
/*  295: 407 */       index += 2;
/*  296:     */     }
/*  297:     */     else
/*  298:     */     {
/*  299: 409 */       result.append('T');
/*  300: 410 */       index++;
/*  301:     */     }
/*  302: 412 */     return index;
/*  303:     */   }
/*  304:     */   
/*  305:     */   private int handleG(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*  306:     */   {
/*  307: 422 */     if (charAt(value, index + 1) == 'H')
/*  308:     */     {
/*  309: 423 */       index = handleGH(value, result, index);
/*  310:     */     }
/*  311: 424 */     else if (charAt(value, index + 1) == 'N')
/*  312:     */     {
/*  313: 425 */       if ((index == 1) && (isVowel(charAt(value, 0))) && (!slavoGermanic)) {
/*  314: 426 */         result.append("KN", "N");
/*  315: 427 */       } else if ((!contains(value, index + 2, 2, "EY")) && (charAt(value, index + 1) != 'Y') && (!slavoGermanic)) {
/*  316: 429 */         result.append("N", "KN");
/*  317:     */       } else {
/*  318: 431 */         result.append("KN");
/*  319:     */       }
/*  320: 433 */       index += 2;
/*  321:     */     }
/*  322: 434 */     else if ((contains(value, index + 1, 2, "LI")) && (!slavoGermanic))
/*  323:     */     {
/*  324: 435 */       result.append("KL", "L");
/*  325: 436 */       index += 2;
/*  326:     */     }
/*  327: 437 */     else if ((index == 0) && ((charAt(value, index + 1) == 'Y') || (contains(value, index + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))))
/*  328:     */     {
/*  329: 439 */       result.append('K', 'J');
/*  330: 440 */       index += 2;
/*  331:     */     }
/*  332: 441 */     else if (((contains(value, index + 1, 2, "ER")) || (charAt(value, index + 1) == 'Y')) && (!contains(value, 0, 6, "DANGER", "RANGER", "MANGER")) && (!contains(value, index - 1, 1, "E", "I")) && (!contains(value, index - 1, 3, "RGY", "OGY")))
/*  333:     */     {
/*  334: 447 */       result.append('K', 'J');
/*  335: 448 */       index += 2;
/*  336:     */     }
/*  337: 449 */     else if ((contains(value, index + 1, 1, "E", "I", "Y")) || (contains(value, index - 1, 4, "AGGI", "OGGI")))
/*  338:     */     {
/*  339: 452 */       if ((contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH")) || (contains(value, index + 1, 2, "ET"))) {
/*  340: 454 */         result.append('K');
/*  341: 455 */       } else if (contains(value, index + 1, 3, "IER")) {
/*  342: 456 */         result.append('J');
/*  343:     */       } else {
/*  344: 458 */         result.append('J', 'K');
/*  345:     */       }
/*  346: 460 */       index += 2;
/*  347:     */     }
/*  348: 461 */     else if (charAt(value, index + 1) == 'G')
/*  349:     */     {
/*  350: 462 */       index += 2;
/*  351: 463 */       result.append('K');
/*  352:     */     }
/*  353:     */     else
/*  354:     */     {
/*  355: 465 */       index++;
/*  356: 466 */       result.append('K');
/*  357:     */     }
/*  358: 468 */     return index;
/*  359:     */   }
/*  360:     */   
/*  361:     */   private int handleGH(String value, DoubleMetaphoneResult result, int index)
/*  362:     */   {
/*  363: 477 */     if ((index > 0) && (!isVowel(charAt(value, index - 1))))
/*  364:     */     {
/*  365: 478 */       result.append('K');
/*  366: 479 */       index += 2;
/*  367:     */     }
/*  368: 480 */     else if (index == 0)
/*  369:     */     {
/*  370: 481 */       if (charAt(value, index + 2) == 'I') {
/*  371: 482 */         result.append('J');
/*  372:     */       } else {
/*  373: 484 */         result.append('K');
/*  374:     */       }
/*  375: 486 */       index += 2;
/*  376:     */     }
/*  377: 487 */     else if (((index > 1) && (contains(value, index - 2, 1, "B", "H", "D"))) || ((index > 2) && (contains(value, index - 3, 1, "B", "H", "D"))) || ((index > 3) && (contains(value, index - 4, 1, "B", "H"))))
/*  378:     */     {
/*  379: 491 */       index += 2;
/*  380:     */     }
/*  381:     */     else
/*  382:     */     {
/*  383: 493 */       if ((index > 2) && (charAt(value, index - 1) == 'U') && (contains(value, index - 3, 1, "C", "G", "L", "R", "T"))) {
/*  384: 496 */         result.append('F');
/*  385: 497 */       } else if ((index > 0) && (charAt(value, index - 1) != 'I')) {
/*  386: 498 */         result.append('K');
/*  387:     */       }
/*  388: 500 */       index += 2;
/*  389:     */     }
/*  390: 502 */     return index;
/*  391:     */   }
/*  392:     */   
/*  393:     */   private int handleH(String value, DoubleMetaphoneResult result, int index)
/*  394:     */   {
/*  395: 512 */     if (((index == 0) || (isVowel(charAt(value, index - 1)))) && (isVowel(charAt(value, index + 1))))
/*  396:     */     {
/*  397: 514 */       result.append('H');
/*  398: 515 */       index += 2;
/*  399:     */     }
/*  400:     */     else
/*  401:     */     {
/*  402: 518 */       index++;
/*  403:     */     }
/*  404: 520 */     return index;
/*  405:     */   }
/*  406:     */   
/*  407:     */   private int handleJ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*  408:     */   {
/*  409: 528 */     if ((contains(value, index, 4, "JOSE")) || (contains(value, 0, 4, "SAN ")))
/*  410:     */     {
/*  411: 530 */       if (((index == 0) && (charAt(value, index + 4) == ' ')) || (value.length() == 4) || (contains(value, 0, 4, "SAN "))) {
/*  412: 532 */         result.append('H');
/*  413:     */       } else {
/*  414: 534 */         result.append('J', 'H');
/*  415:     */       }
/*  416: 536 */       index++;
/*  417:     */     }
/*  418:     */     else
/*  419:     */     {
/*  420: 538 */       if ((index == 0) && (!contains(value, index, 4, "JOSE"))) {
/*  421: 539 */         result.append('J', 'A');
/*  422: 540 */       } else if ((isVowel(charAt(value, index - 1))) && (!slavoGermanic) && ((charAt(value, index + 1) == 'A') || (charAt(value, index + 1) == 'O'))) {
/*  423: 542 */         result.append('J', 'H');
/*  424: 543 */       } else if (index == value.length() - 1) {
/*  425: 544 */         result.append('J', ' ');
/*  426: 545 */       } else if ((!contains(value, index + 1, 1, L_T_K_S_N_M_B_Z)) && (!contains(value, index - 1, 1, "S", "K", "L"))) {
/*  427: 546 */         result.append('J');
/*  428:     */       }
/*  429: 549 */       if (charAt(value, index + 1) == 'J') {
/*  430: 550 */         index += 2;
/*  431:     */       } else {
/*  432: 552 */         index++;
/*  433:     */       }
/*  434:     */     }
/*  435: 555 */     return index;
/*  436:     */   }
/*  437:     */   
/*  438:     */   private int handleL(String value, DoubleMetaphoneResult result, int index)
/*  439:     */   {
/*  440: 564 */     if (charAt(value, index + 1) == 'L')
/*  441:     */     {
/*  442: 565 */       if (conditionL0(value, index)) {
/*  443: 566 */         result.appendPrimary('L');
/*  444:     */       } else {
/*  445: 568 */         result.append('L');
/*  446:     */       }
/*  447: 570 */       index += 2;
/*  448:     */     }
/*  449:     */     else
/*  450:     */     {
/*  451: 572 */       index++;
/*  452: 573 */       result.append('L');
/*  453:     */     }
/*  454: 575 */     return index;
/*  455:     */   }
/*  456:     */   
/*  457:     */   private int handleP(String value, DoubleMetaphoneResult result, int index)
/*  458:     */   {
/*  459: 584 */     if (charAt(value, index + 1) == 'H')
/*  460:     */     {
/*  461: 585 */       result.append('F');
/*  462: 586 */       index += 2;
/*  463:     */     }
/*  464:     */     else
/*  465:     */     {
/*  466: 588 */       result.append('P');
/*  467: 589 */       index = contains(value, index + 1, 1, "P", "B") ? index + 2 : index + 1;
/*  468:     */     }
/*  469: 591 */     return index;
/*  470:     */   }
/*  471:     */   
/*  472:     */   private int handleR(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*  473:     */   {
/*  474: 601 */     if ((index == value.length() - 1) && (!slavoGermanic) && (contains(value, index - 2, 2, "IE")) && (!contains(value, index - 4, 2, "ME", "MA"))) {
/*  475: 604 */       result.appendAlternate('R');
/*  476:     */     } else {
/*  477: 606 */       result.append('R');
/*  478:     */     }
/*  479: 608 */     return charAt(value, index + 1) == 'R' ? index + 2 : index + 1;
/*  480:     */   }
/*  481:     */   
/*  482:     */   private int handleS(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*  483:     */   {
/*  484: 618 */     if (contains(value, index - 1, 3, "ISL", "YSL"))
/*  485:     */     {
/*  486: 620 */       index++;
/*  487:     */     }
/*  488: 621 */     else if ((index == 0) && (contains(value, index, 5, "SUGAR")))
/*  489:     */     {
/*  490: 623 */       result.append('X', 'S');
/*  491: 624 */       index++;
/*  492:     */     }
/*  493: 625 */     else if (contains(value, index, 2, "SH"))
/*  494:     */     {
/*  495: 626 */       if (contains(value, index + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
/*  496: 629 */         result.append('S');
/*  497:     */       } else {
/*  498: 631 */         result.append('X');
/*  499:     */       }
/*  500: 633 */       index += 2;
/*  501:     */     }
/*  502: 634 */     else if ((contains(value, index, 3, "SIO", "SIA")) || (contains(value, index, 4, "SIAN")))
/*  503:     */     {
/*  504: 636 */       if (slavoGermanic) {
/*  505: 637 */         result.append('S');
/*  506:     */       } else {
/*  507: 639 */         result.append('S', 'X');
/*  508:     */       }
/*  509: 641 */       index += 3;
/*  510:     */     }
/*  511: 642 */     else if (((index == 0) && (contains(value, index + 1, 1, "M", "N", "L", "W"))) || (contains(value, index + 1, 1, "Z")))
/*  512:     */     {
/*  513: 647 */       result.append('S', 'X');
/*  514: 648 */       index = contains(value, index + 1, 1, "Z") ? index + 2 : index + 1;
/*  515:     */     }
/*  516: 649 */     else if (contains(value, index, 2, "SC"))
/*  517:     */     {
/*  518: 650 */       index = handleSC(value, result, index);
/*  519:     */     }
/*  520:     */     else
/*  521:     */     {
/*  522: 652 */       if ((index == value.length() - 1) && (contains(value, index - 2, 2, "AI", "OI"))) {
/*  523: 655 */         result.appendAlternate('S');
/*  524:     */       } else {
/*  525: 657 */         result.append('S');
/*  526:     */       }
/*  527: 659 */       index = contains(value, index + 1, 1, "S", "Z") ? index + 2 : index + 1;
/*  528:     */     }
/*  529: 661 */     return index;
/*  530:     */   }
/*  531:     */   
/*  532:     */   private int handleSC(String value, DoubleMetaphoneResult result, int index)
/*  533:     */   {
/*  534: 670 */     if (charAt(value, index + 2) == 'H')
/*  535:     */     {
/*  536: 672 */       if (contains(value, index + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM"))
/*  537:     */       {
/*  538: 675 */         if (contains(value, index + 3, 2, "ER", "EN")) {
/*  539: 677 */           result.append("X", "SK");
/*  540:     */         } else {
/*  541: 679 */           result.append("SK");
/*  542:     */         }
/*  543:     */       }
/*  544: 682 */       else if ((index == 0) && (!isVowel(charAt(value, 3))) && (charAt(value, 3) != 'W')) {
/*  545: 683 */         result.append('X', 'S');
/*  546:     */       } else {
/*  547: 685 */         result.append('X');
/*  548:     */       }
/*  549:     */     }
/*  550: 688 */     else if (contains(value, index + 2, 1, "I", "E", "Y")) {
/*  551: 689 */       result.append('S');
/*  552:     */     } else {
/*  553: 691 */       result.append("SK");
/*  554:     */     }
/*  555: 693 */     return index + 3;
/*  556:     */   }
/*  557:     */   
/*  558:     */   private int handleT(String value, DoubleMetaphoneResult result, int index)
/*  559:     */   {
/*  560: 702 */     if (contains(value, index, 4, "TION"))
/*  561:     */     {
/*  562: 703 */       result.append('X');
/*  563: 704 */       index += 3;
/*  564:     */     }
/*  565: 705 */     else if (contains(value, index, 3, "TIA", "TCH"))
/*  566:     */     {
/*  567: 706 */       result.append('X');
/*  568: 707 */       index += 3;
/*  569:     */     }
/*  570: 708 */     else if ((contains(value, index, 2, "TH")) || (contains(value, index, 3, "TTH")))
/*  571:     */     {
/*  572: 710 */       if ((contains(value, index + 2, 2, "OM", "AM")) || (contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH"))) {
/*  573: 714 */         result.append('T');
/*  574:     */       } else {
/*  575: 716 */         result.append('0', 'T');
/*  576:     */       }
/*  577: 718 */       index += 2;
/*  578:     */     }
/*  579:     */     else
/*  580:     */     {
/*  581: 720 */       result.append('T');
/*  582: 721 */       index = contains(value, index + 1, 1, "T", "D") ? index + 2 : index + 1;
/*  583:     */     }
/*  584: 723 */     return index;
/*  585:     */   }
/*  586:     */   
/*  587:     */   private int handleW(String value, DoubleMetaphoneResult result, int index)
/*  588:     */   {
/*  589: 732 */     if (contains(value, index, 2, "WR"))
/*  590:     */     {
/*  591: 734 */       result.append('R');
/*  592: 735 */       index += 2;
/*  593:     */     }
/*  594: 737 */     else if ((index == 0) && ((isVowel(charAt(value, index + 1))) || (contains(value, index, 2, "WH"))))
/*  595:     */     {
/*  596: 739 */       if (isVowel(charAt(value, index + 1))) {
/*  597: 741 */         result.append('A', 'F');
/*  598:     */       } else {
/*  599: 744 */         result.append('A');
/*  600:     */       }
/*  601: 746 */       index++;
/*  602:     */     }
/*  603: 747 */     else if (((index == value.length() - 1) && (isVowel(charAt(value, index - 1)))) || (contains(value, index - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY")) || (contains(value, 0, 3, "SCH")))
/*  604:     */     {
/*  605: 752 */       result.appendAlternate('F');
/*  606: 753 */       index++;
/*  607:     */     }
/*  608: 754 */     else if (contains(value, index, 4, "WICZ", "WITZ"))
/*  609:     */     {
/*  610: 756 */       result.append("TS", "FX");
/*  611: 757 */       index += 4;
/*  612:     */     }
/*  613:     */     else
/*  614:     */     {
/*  615: 759 */       index++;
/*  616:     */     }
/*  617: 762 */     return index;
/*  618:     */   }
/*  619:     */   
/*  620:     */   private int handleX(String value, DoubleMetaphoneResult result, int index)
/*  621:     */   {
/*  622: 771 */     if (index == 0)
/*  623:     */     {
/*  624: 772 */       result.append('S');
/*  625: 773 */       index++;
/*  626:     */     }
/*  627:     */     else
/*  628:     */     {
/*  629: 775 */       if ((index != value.length() - 1) || ((!contains(value, index - 3, 3, "IAU", "EAU")) && (!contains(value, index - 2, 2, "AU", "OU")))) {
/*  630: 779 */         result.append("KS");
/*  631:     */       }
/*  632: 781 */       index = contains(value, index + 1, 1, "C", "X") ? index + 2 : index + 1;
/*  633:     */     }
/*  634: 783 */     return index;
/*  635:     */   }
/*  636:     */   
/*  637:     */   private int handleZ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*  638:     */   {
/*  639: 791 */     if (charAt(value, index + 1) == 'H')
/*  640:     */     {
/*  641: 793 */       result.append('J');
/*  642: 794 */       index += 2;
/*  643:     */     }
/*  644:     */     else
/*  645:     */     {
/*  646: 796 */       if ((contains(value, index + 1, 2, "ZO", "ZI", "ZA")) || ((slavoGermanic) && (index > 0) && (charAt(value, index - 1) != 'T'))) {
/*  647: 797 */         result.append("S", "TS");
/*  648:     */       } else {
/*  649: 799 */         result.append('S');
/*  650:     */       }
/*  651: 801 */       index = charAt(value, index + 1) == 'Z' ? index + 2 : index + 1;
/*  652:     */     }
/*  653: 803 */     return index;
/*  654:     */   }
/*  655:     */   
/*  656:     */   private boolean conditionC0(String value, int index)
/*  657:     */   {
/*  658: 812 */     if (contains(value, index, 4, "CHIA")) {
/*  659: 813 */       return true;
/*  660:     */     }
/*  661: 814 */     if (index <= 1) {
/*  662: 815 */       return false;
/*  663:     */     }
/*  664: 816 */     if (isVowel(charAt(value, index - 2))) {
/*  665: 817 */       return false;
/*  666:     */     }
/*  667: 818 */     if (!contains(value, index - 1, 3, "ACH")) {
/*  668: 819 */       return false;
/*  669:     */     }
/*  670: 821 */     char c = charAt(value, index + 2);
/*  671: 822 */     return ((c != 'I') && (c != 'E')) || (contains(value, index - 2, 6, "BACHER", "MACHER"));
/*  672:     */   }
/*  673:     */   
/*  674:     */   private boolean conditionCH0(String value, int index)
/*  675:     */   {
/*  676: 831 */     if (index != 0) {
/*  677: 832 */       return false;
/*  678:     */     }
/*  679: 833 */     if ((!contains(value, index + 1, 5, "HARAC", "HARIS")) && (!contains(value, index + 1, 3, "HOR", "HYM", "HIA", "HEM"))) {
/*  680: 835 */       return false;
/*  681:     */     }
/*  682: 836 */     if (contains(value, 0, 5, "CHORE")) {
/*  683: 837 */       return false;
/*  684:     */     }
/*  685: 839 */     return true;
/*  686:     */   }
/*  687:     */   
/*  688:     */   private boolean conditionCH1(String value, int index)
/*  689:     */   {
/*  690: 847 */     return (contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH")) || (contains(value, index - 2, 6, "ORCHES", "ARCHIT", "ORCHID")) || (contains(value, index + 2, 1, "T", "S")) || (((contains(value, index - 1, 1, "A", "O", "U", "E")) || (index == 0)) && ((contains(value, index + 2, 1, L_R_N_M_B_H_F_V_W_SPACE)) || (index + 1 == value.length() - 1)));
/*  691:     */   }
/*  692:     */   
/*  693:     */   private boolean conditionL0(String value, int index)
/*  694:     */   {
/*  695: 859 */     if ((index == value.length() - 3) && (contains(value, index - 1, 4, "ILLO", "ILLA", "ALLE"))) {
/*  696: 861 */       return true;
/*  697:     */     }
/*  698: 862 */     if (((contains(value, value.length() - 2, 2, "AS", "OS")) || (contains(value, value.length() - 1, 1, "A", "O"))) && (contains(value, index - 1, 4, "ALLE"))) {
/*  699: 865 */       return true;
/*  700:     */     }
/*  701: 867 */     return false;
/*  702:     */   }
/*  703:     */   
/*  704:     */   private boolean conditionM0(String value, int index)
/*  705:     */   {
/*  706: 875 */     if (charAt(value, index + 1) == 'M') {
/*  707: 876 */       return true;
/*  708:     */     }
/*  709: 878 */     return (contains(value, index - 1, 3, "UMB")) && ((index + 1 == value.length() - 1) || (contains(value, index + 2, 2, "ER")));
/*  710:     */   }
/*  711:     */   
/*  712:     */   private boolean isSlavoGermanic(String value)
/*  713:     */   {
/*  714: 890 */     return (value.indexOf('W') > -1) || (value.indexOf('K') > -1) || (value.indexOf("CZ") > -1) || (value.indexOf("WITZ") > -1);
/*  715:     */   }
/*  716:     */   
/*  717:     */   private boolean isVowel(char ch)
/*  718:     */   {
/*  719: 898 */     return "AEIOUY".indexOf(ch) != -1;
/*  720:     */   }
/*  721:     */   
/*  722:     */   private boolean isSilentStart(String value)
/*  723:     */   {
/*  724: 907 */     boolean result = false;
/*  725: 908 */     for (int i = 0; i < SILENT_START.length; i++) {
/*  726: 909 */       if (value.startsWith(SILENT_START[i]))
/*  727:     */       {
/*  728: 910 */         result = true;
/*  729: 911 */         break;
/*  730:     */       }
/*  731:     */     }
/*  732: 914 */     return result;
/*  733:     */   }
/*  734:     */   
/*  735:     */   private String cleanInput(String input)
/*  736:     */   {
/*  737: 921 */     if (input == null) {
/*  738: 922 */       return null;
/*  739:     */     }
/*  740: 924 */     input = input.trim();
/*  741: 925 */     if (input.length() == 0) {
/*  742: 926 */       return null;
/*  743:     */     }
/*  744: 928 */     return input.toUpperCase(Locale.ENGLISH);
/*  745:     */   }
/*  746:     */   
/*  747:     */   protected char charAt(String value, int index)
/*  748:     */   {
/*  749: 937 */     if ((index < 0) || (index >= value.length())) {
/*  750: 938 */       return '\000';
/*  751:     */     }
/*  752: 940 */     return value.charAt(index);
/*  753:     */   }
/*  754:     */   
/*  755:     */   private static boolean contains(String value, int start, int length, String criteria)
/*  756:     */   {
/*  757: 948 */     return contains(value, start, length, new String[] { criteria });
/*  758:     */   }
/*  759:     */   
/*  760:     */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2)
/*  761:     */   {
/*  762: 957 */     return contains(value, start, length, new String[] { criteria1, criteria2 });
/*  763:     */   }
/*  764:     */   
/*  765:     */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3)
/*  766:     */   {
/*  767: 967 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3 });
/*  768:     */   }
/*  769:     */   
/*  770:     */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4)
/*  771:     */   {
/*  772: 977 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4 });
/*  773:     */   }
/*  774:     */   
/*  775:     */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5)
/*  776:     */   {
/*  777: 989 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5 });
/*  778:     */   }
/*  779:     */   
/*  780:     */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5, String criteria6)
/*  781:     */   {
/*  782:1001 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5, criteria6 });
/*  783:     */   }
/*  784:     */   
/*  785:     */   protected static boolean contains(String value, int start, int length, String[] criteria)
/*  786:     */   {
/*  787:1012 */     boolean result = false;
/*  788:1013 */     if ((start >= 0) && (start + length <= value.length()))
/*  789:     */     {
/*  790:1014 */       String target = value.substring(start, start + length);
/*  791:1016 */       for (int i = 0; i < criteria.length; i++) {
/*  792:1017 */         if (target.equals(criteria[i]))
/*  793:     */         {
/*  794:1018 */           result = true;
/*  795:1019 */           break;
/*  796:     */         }
/*  797:     */       }
/*  798:     */     }
/*  799:1023 */     return result;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public class DoubleMetaphoneResult
/*  803:     */   {
/*  804:1034 */     private StringBuffer primary = new StringBuffer(DoubleMetaphone.this.getMaxCodeLen());
/*  805:1035 */     private StringBuffer alternate = new StringBuffer(DoubleMetaphone.this.getMaxCodeLen());
/*  806:     */     private int maxLength;
/*  807:     */     
/*  808:     */     public DoubleMetaphoneResult(int maxLength)
/*  809:     */     {
/*  810:1039 */       this.maxLength = maxLength;
/*  811:     */     }
/*  812:     */     
/*  813:     */     public void append(char value)
/*  814:     */     {
/*  815:1043 */       appendPrimary(value);
/*  816:1044 */       appendAlternate(value);
/*  817:     */     }
/*  818:     */     
/*  819:     */     public void append(char primary, char alternate)
/*  820:     */     {
/*  821:1048 */       appendPrimary(primary);
/*  822:1049 */       appendAlternate(alternate);
/*  823:     */     }
/*  824:     */     
/*  825:     */     public void appendPrimary(char value)
/*  826:     */     {
/*  827:1053 */       if (this.primary.length() < this.maxLength) {
/*  828:1054 */         this.primary.append(value);
/*  829:     */       }
/*  830:     */     }
/*  831:     */     
/*  832:     */     public void appendAlternate(char value)
/*  833:     */     {
/*  834:1059 */       if (this.alternate.length() < this.maxLength) {
/*  835:1060 */         this.alternate.append(value);
/*  836:     */       }
/*  837:     */     }
/*  838:     */     
/*  839:     */     public void append(String value)
/*  840:     */     {
/*  841:1065 */       appendPrimary(value);
/*  842:1066 */       appendAlternate(value);
/*  843:     */     }
/*  844:     */     
/*  845:     */     public void append(String primary, String alternate)
/*  846:     */     {
/*  847:1070 */       appendPrimary(primary);
/*  848:1071 */       appendAlternate(alternate);
/*  849:     */     }
/*  850:     */     
/*  851:     */     public void appendPrimary(String value)
/*  852:     */     {
/*  853:1075 */       int addChars = this.maxLength - this.primary.length();
/*  854:1076 */       if (value.length() <= addChars) {
/*  855:1077 */         this.primary.append(value);
/*  856:     */       } else {
/*  857:1079 */         this.primary.append(value.substring(0, addChars));
/*  858:     */       }
/*  859:     */     }
/*  860:     */     
/*  861:     */     public void appendAlternate(String value)
/*  862:     */     {
/*  863:1084 */       int addChars = this.maxLength - this.alternate.length();
/*  864:1085 */       if (value.length() <= addChars) {
/*  865:1086 */         this.alternate.append(value);
/*  866:     */       } else {
/*  867:1088 */         this.alternate.append(value.substring(0, addChars));
/*  868:     */       }
/*  869:     */     }
/*  870:     */     
/*  871:     */     public String getPrimary()
/*  872:     */     {
/*  873:1093 */       return this.primary.toString();
/*  874:     */     }
/*  875:     */     
/*  876:     */     public String getAlternate()
/*  877:     */     {
/*  878:1097 */       return this.alternate.toString();
/*  879:     */     }
/*  880:     */     
/*  881:     */     public boolean isComplete()
/*  882:     */     {
/*  883:1101 */       return (this.primary.length() >= this.maxLength) && (this.alternate.length() >= this.maxLength);
/*  884:     */     }
/*  885:     */   }
/*  886:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.language.DoubleMetaphone
 * JD-Core Version:    0.7.0.1
 */