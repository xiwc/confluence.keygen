/*      */ package org.apache.commons.codec.language;
/*      */ 
/*      */ import java.util.Locale;
/*      */ import org.apache.commons.codec.EncoderException;
/*      */ import org.apache.commons.codec.StringEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DoubleMetaphone
/*      */   implements StringEncoder
/*      */ {
/*      */   private static final String VOWELS = "AEIOUY";
/*   47 */   private static final String[] SILENT_START = { "GN", "KN", "PN", "WR", "PS" };
/*      */   
/*   49 */   private static final String[] L_R_N_M_B_H_F_V_W_SPACE = { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
/*      */   
/*   51 */   private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
/*      */   
/*   53 */   private static final String[] L_T_K_S_N_M_B_Z = { "L", "T", "K", "S", "N", "M", "B", "Z" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   59 */   protected int maxCodeLen = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String doubleMetaphone(String value)
/*      */   {
/*   75 */     return doubleMetaphone(value, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String doubleMetaphone(String value, boolean alternate)
/*      */   {
/*   87 */     value = cleanInput(value);
/*   88 */     if (value == null) {
/*   89 */       return null;
/*      */     }
/*      */     
/*   92 */     boolean slavoGermanic = isSlavoGermanic(value);
/*   93 */     int index = isSilentStart(value) ? 1 : 0;
/*      */     
/*   95 */     DoubleMetaphoneResult result = new DoubleMetaphoneResult(getMaxCodeLen());
/*      */     
/*   97 */     while ((!result.isComplete()) && (index <= value.length() - 1)) {
/*   98 */       switch (value.charAt(index)) {
/*      */       case 'A': 
/*      */       case 'E': 
/*      */       case 'I': 
/*      */       case 'O': 
/*      */       case 'U': 
/*      */       case 'Y': 
/*  105 */         index = handleAEIOUY(value, result, index);
/*  106 */         break;
/*      */       case 'B': 
/*  108 */         result.append('P');
/*  109 */         index = charAt(value, index + 1) == 'B' ? index + 2 : index + 1;
/*  110 */         break;
/*      */       
/*      */       case 'Ç': 
/*  113 */         result.append('S');
/*  114 */         index++;
/*  115 */         break;
/*      */       case 'C': 
/*  117 */         index = handleC(value, result, index);
/*  118 */         break;
/*      */       case 'D': 
/*  120 */         index = handleD(value, result, index);
/*  121 */         break;
/*      */       case 'F': 
/*  123 */         result.append('F');
/*  124 */         index = charAt(value, index + 1) == 'F' ? index + 2 : index + 1;
/*  125 */         break;
/*      */       case 'G': 
/*  127 */         index = handleG(value, result, index, slavoGermanic);
/*  128 */         break;
/*      */       case 'H': 
/*  130 */         index = handleH(value, result, index);
/*  131 */         break;
/*      */       case 'J': 
/*  133 */         index = handleJ(value, result, index, slavoGermanic);
/*  134 */         break;
/*      */       case 'K': 
/*  136 */         result.append('K');
/*  137 */         index = charAt(value, index + 1) == 'K' ? index + 2 : index + 1;
/*  138 */         break;
/*      */       case 'L': 
/*  140 */         index = handleL(value, result, index);
/*  141 */         break;
/*      */       case 'M': 
/*  143 */         result.append('M');
/*  144 */         index = conditionM0(value, index) ? index + 2 : index + 1;
/*  145 */         break;
/*      */       case 'N': 
/*  147 */         result.append('N');
/*  148 */         index = charAt(value, index + 1) == 'N' ? index + 2 : index + 1;
/*  149 */         break;
/*      */       
/*      */       case 'Ñ': 
/*  152 */         result.append('N');
/*  153 */         index++;
/*  154 */         break;
/*      */       case 'P': 
/*  156 */         index = handleP(value, result, index);
/*  157 */         break;
/*      */       case 'Q': 
/*  159 */         result.append('K');
/*  160 */         index = charAt(value, index + 1) == 'Q' ? index + 2 : index + 1;
/*  161 */         break;
/*      */       case 'R': 
/*  163 */         index = handleR(value, result, index, slavoGermanic);
/*  164 */         break;
/*      */       case 'S': 
/*  166 */         index = handleS(value, result, index, slavoGermanic);
/*  167 */         break;
/*      */       case 'T': 
/*  169 */         index = handleT(value, result, index);
/*  170 */         break;
/*      */       case 'V': 
/*  172 */         result.append('F');
/*  173 */         index = charAt(value, index + 1) == 'V' ? index + 2 : index + 1;
/*  174 */         break;
/*      */       case 'W': 
/*  176 */         index = handleW(value, result, index);
/*  177 */         break;
/*      */       case 'X': 
/*  179 */         index = handleX(value, result, index);
/*  180 */         break;
/*      */       case 'Z': 
/*  182 */         index = handleZ(value, result, index, slavoGermanic);
/*  183 */         break;
/*      */       default: 
/*  185 */         index++;
/*      */       }
/*      */       
/*      */     }
/*      */     
/*  190 */     return alternate ? result.getAlternate() : result.getPrimary();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object encode(Object obj)
/*      */     throws EncoderException
/*      */   {
/*  202 */     if (!(obj instanceof String)) {
/*  203 */       throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
/*      */     }
/*  205 */     return doubleMetaphone((String)obj);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String encode(String value)
/*      */   {
/*  215 */     return doubleMetaphone(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDoubleMetaphoneEqual(String value1, String value2)
/*      */   {
/*  229 */     return isDoubleMetaphoneEqual(value1, value2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDoubleMetaphoneEqual(String value1, String value2, boolean alternate)
/*      */   {
/*  245 */     return doubleMetaphone(value1, alternate).equals(doubleMetaphone(value2, alternate));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaxCodeLen()
/*      */   {
/*  254 */     return this.maxCodeLen;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMaxCodeLen(int maxCodeLen)
/*      */   {
/*  262 */     this.maxCodeLen = maxCodeLen;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleAEIOUY(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  272 */     if (index == 0) {
/*  273 */       result.append('A');
/*      */     }
/*  275 */     return index + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleC(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  284 */     if (conditionC0(value, index)) {
/*  285 */       result.append('K');
/*  286 */       index += 2;
/*  287 */     } else if ((index == 0) && (contains(value, index, 6, "CAESAR"))) {
/*  288 */       result.append('S');
/*  289 */       index += 2;
/*  290 */     } else if (contains(value, index, 2, "CH")) {
/*  291 */       index = handleCH(value, result, index);
/*  292 */     } else if ((contains(value, index, 2, "CZ")) && (!contains(value, index - 2, 4, "WICZ")))
/*      */     {
/*      */ 
/*  295 */       result.append('S', 'X');
/*  296 */       index += 2;
/*  297 */     } else if (contains(value, index + 1, 3, "CIA"))
/*      */     {
/*  299 */       result.append('X');
/*  300 */       index += 3;
/*  301 */     } else { if ((contains(value, index, 2, "CC")) && ((index != 1) || (charAt(value, 0) != 'M')))
/*      */       {
/*      */ 
/*  304 */         return handleCC(value, result, index); }
/*  305 */       if (contains(value, index, 2, "CK", "CG", "CQ")) {
/*  306 */         result.append('K');
/*  307 */         index += 2;
/*  308 */       } else if (contains(value, index, 2, "CI", "CE", "CY"))
/*      */       {
/*  310 */         if (contains(value, index, 3, "CIO", "CIE", "CIA")) {
/*  311 */           result.append('S', 'X');
/*      */         } else {
/*  313 */           result.append('S');
/*      */         }
/*  315 */         index += 2;
/*      */       } else {
/*  317 */         result.append('K');
/*  318 */         if (contains(value, index + 1, 2, " C", " Q", " G"))
/*      */         {
/*  320 */           index += 3;
/*  321 */         } else if ((contains(value, index + 1, 1, "C", "K", "Q")) && (!contains(value, index + 1, 2, "CE", "CI")))
/*      */         {
/*  323 */           index += 2;
/*      */         } else {
/*  325 */           index++;
/*      */         }
/*      */       }
/*      */     }
/*  329 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleCC(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  338 */     if ((contains(value, index + 2, 1, "I", "E", "H")) && (!contains(value, index + 2, 2, "HU")))
/*      */     {
/*      */ 
/*  341 */       if (((index == 1) && (charAt(value, index - 1) == 'A')) || (contains(value, index - 1, 5, "UCCEE", "UCCES")))
/*      */       {
/*      */ 
/*  344 */         result.append("KS");
/*      */       }
/*      */       else {
/*  347 */         result.append('X');
/*      */       }
/*  349 */       index += 3;
/*      */     } else {
/*  351 */       result.append('K');
/*  352 */       index += 2;
/*      */     }
/*      */     
/*  355 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleCH(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  364 */     if ((index > 0) && (contains(value, index, 4, "CHAE"))) {
/*  365 */       result.append('K', 'X');
/*  366 */       return index + 2; }
/*  367 */     if (conditionCH0(value, index))
/*      */     {
/*  369 */       result.append('K');
/*  370 */       return index + 2; }
/*  371 */     if (conditionCH1(value, index))
/*      */     {
/*  373 */       result.append('K');
/*  374 */       return index + 2;
/*      */     }
/*  376 */     if (index > 0) {
/*  377 */       if (contains(value, 0, 2, "MC")) {
/*  378 */         result.append('K');
/*      */       } else {
/*  380 */         result.append('X', 'K');
/*      */       }
/*      */     } else {
/*  383 */       result.append('X');
/*      */     }
/*  385 */     return index + 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleD(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  395 */     if (contains(value, index, 2, "DG"))
/*      */     {
/*  397 */       if (contains(value, index + 2, 1, "I", "E", "Y")) {
/*  398 */         result.append('J');
/*  399 */         index += 3;
/*      */       }
/*      */       else {
/*  402 */         result.append("TK");
/*  403 */         index += 2;
/*      */       }
/*  405 */     } else if (contains(value, index, 2, "DT", "DD")) {
/*  406 */       result.append('T');
/*  407 */       index += 2;
/*      */     } else {
/*  409 */       result.append('T');
/*  410 */       index++;
/*      */     }
/*  412 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleG(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*      */   {
/*  422 */     if (charAt(value, index + 1) == 'H') {
/*  423 */       index = handleGH(value, result, index);
/*  424 */     } else if (charAt(value, index + 1) == 'N') {
/*  425 */       if ((index == 1) && (isVowel(charAt(value, 0))) && (!slavoGermanic)) {
/*  426 */         result.append("KN", "N");
/*  427 */       } else if ((!contains(value, index + 2, 2, "EY")) && (charAt(value, index + 1) != 'Y') && (!slavoGermanic))
/*      */       {
/*  429 */         result.append("N", "KN");
/*      */       } else {
/*  431 */         result.append("KN");
/*      */       }
/*  433 */       index += 2;
/*  434 */     } else if ((contains(value, index + 1, 2, "LI")) && (!slavoGermanic)) {
/*  435 */       result.append("KL", "L");
/*  436 */       index += 2;
/*  437 */     } else if ((index == 0) && ((charAt(value, index + 1) == 'Y') || (contains(value, index + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))))
/*      */     {
/*  439 */       result.append('K', 'J');
/*  440 */       index += 2;
/*  441 */     } else if (((contains(value, index + 1, 2, "ER")) || (charAt(value, index + 1) == 'Y')) && (!contains(value, 0, 6, "DANGER", "RANGER", "MANGER")) && (!contains(value, index - 1, 1, "E", "I")) && (!contains(value, index - 1, 3, "RGY", "OGY")))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  447 */       result.append('K', 'J');
/*  448 */       index += 2;
/*  449 */     } else if ((contains(value, index + 1, 1, "E", "I", "Y")) || (contains(value, index - 1, 4, "AGGI", "OGGI")))
/*      */     {
/*      */ 
/*  452 */       if ((contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH")) || (contains(value, index + 1, 2, "ET")))
/*      */       {
/*  454 */         result.append('K');
/*  455 */       } else if (contains(value, index + 1, 3, "IER")) {
/*  456 */         result.append('J');
/*      */       } else {
/*  458 */         result.append('J', 'K');
/*      */       }
/*  460 */       index += 2;
/*  461 */     } else if (charAt(value, index + 1) == 'G') {
/*  462 */       index += 2;
/*  463 */       result.append('K');
/*      */     } else {
/*  465 */       index++;
/*  466 */       result.append('K');
/*      */     }
/*  468 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleGH(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  477 */     if ((index > 0) && (!isVowel(charAt(value, index - 1)))) {
/*  478 */       result.append('K');
/*  479 */       index += 2;
/*  480 */     } else if (index == 0) {
/*  481 */       if (charAt(value, index + 2) == 'I') {
/*  482 */         result.append('J');
/*      */       } else {
/*  484 */         result.append('K');
/*      */       }
/*  486 */       index += 2;
/*  487 */     } else if (((index > 1) && (contains(value, index - 2, 1, "B", "H", "D"))) || ((index > 2) && (contains(value, index - 3, 1, "B", "H", "D"))) || ((index > 3) && (contains(value, index - 4, 1, "B", "H"))))
/*      */     {
/*      */ 
/*      */ 
/*  491 */       index += 2;
/*      */     } else {
/*  493 */       if ((index > 2) && (charAt(value, index - 1) == 'U') && (contains(value, index - 3, 1, "C", "G", "L", "R", "T")))
/*      */       {
/*      */ 
/*  496 */         result.append('F');
/*  497 */       } else if ((index > 0) && (charAt(value, index - 1) != 'I')) {
/*  498 */         result.append('K');
/*      */       }
/*  500 */       index += 2;
/*      */     }
/*  502 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleH(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  512 */     if (((index == 0) || (isVowel(charAt(value, index - 1)))) && (isVowel(charAt(value, index + 1))))
/*      */     {
/*  514 */       result.append('H');
/*  515 */       index += 2;
/*      */     }
/*      */     else {
/*  518 */       index++;
/*      */     }
/*  520 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleJ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*      */   {
/*  528 */     if ((contains(value, index, 4, "JOSE")) || (contains(value, 0, 4, "SAN ")))
/*      */     {
/*  530 */       if (((index == 0) && (charAt(value, index + 4) == ' ')) || (value.length() == 4) || (contains(value, 0, 4, "SAN ")))
/*      */       {
/*  532 */         result.append('H');
/*      */       } else {
/*  534 */         result.append('J', 'H');
/*      */       }
/*  536 */       index++;
/*      */     } else {
/*  538 */       if ((index == 0) && (!contains(value, index, 4, "JOSE"))) {
/*  539 */         result.append('J', 'A');
/*  540 */       } else if ((isVowel(charAt(value, index - 1))) && (!slavoGermanic) && ((charAt(value, index + 1) == 'A') || (charAt(value, index + 1) == 'O')))
/*      */       {
/*  542 */         result.append('J', 'H');
/*  543 */       } else if (index == value.length() - 1) {
/*  544 */         result.append('J', ' ');
/*  545 */       } else if ((!contains(value, index + 1, 1, L_T_K_S_N_M_B_Z)) && (!contains(value, index - 1, 1, "S", "K", "L"))) {
/*  546 */         result.append('J');
/*      */       }
/*      */       
/*  549 */       if (charAt(value, index + 1) == 'J') {
/*  550 */         index += 2;
/*      */       } else {
/*  552 */         index++;
/*      */       }
/*      */     }
/*  555 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleL(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  564 */     if (charAt(value, index + 1) == 'L') {
/*  565 */       if (conditionL0(value, index)) {
/*  566 */         result.appendPrimary('L');
/*      */       } else {
/*  568 */         result.append('L');
/*      */       }
/*  570 */       index += 2;
/*      */     } else {
/*  572 */       index++;
/*  573 */       result.append('L');
/*      */     }
/*  575 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleP(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  584 */     if (charAt(value, index + 1) == 'H') {
/*  585 */       result.append('F');
/*  586 */       index += 2;
/*      */     } else {
/*  588 */       result.append('P');
/*  589 */       index = contains(value, index + 1, 1, "P", "B") ? index + 2 : index + 1;
/*      */     }
/*  591 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleR(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*      */   {
/*  601 */     if ((index == value.length() - 1) && (!slavoGermanic) && (contains(value, index - 2, 2, "IE")) && (!contains(value, index - 4, 2, "ME", "MA")))
/*      */     {
/*      */ 
/*  604 */       result.appendAlternate('R');
/*      */     } else {
/*  606 */       result.append('R');
/*      */     }
/*  608 */     return charAt(value, index + 1) == 'R' ? index + 2 : index + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleS(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*      */   {
/*  618 */     if (contains(value, index - 1, 3, "ISL", "YSL"))
/*      */     {
/*  620 */       index++;
/*  621 */     } else if ((index == 0) && (contains(value, index, 5, "SUGAR")))
/*      */     {
/*  623 */       result.append('X', 'S');
/*  624 */       index++;
/*  625 */     } else if (contains(value, index, 2, "SH")) {
/*  626 */       if (contains(value, index + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ"))
/*      */       {
/*      */ 
/*  629 */         result.append('S');
/*      */       } else {
/*  631 */         result.append('X');
/*      */       }
/*  633 */       index += 2;
/*  634 */     } else if ((contains(value, index, 3, "SIO", "SIA")) || (contains(value, index, 4, "SIAN")))
/*      */     {
/*  636 */       if (slavoGermanic) {
/*  637 */         result.append('S');
/*      */       } else {
/*  639 */         result.append('S', 'X');
/*      */       }
/*  641 */       index += 3;
/*  642 */     } else if (((index == 0) && (contains(value, index + 1, 1, "M", "N", "L", "W"))) || (contains(value, index + 1, 1, "Z")))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  647 */       result.append('S', 'X');
/*  648 */       index = contains(value, index + 1, 1, "Z") ? index + 2 : index + 1;
/*  649 */     } else if (contains(value, index, 2, "SC")) {
/*  650 */       index = handleSC(value, result, index);
/*      */     } else {
/*  652 */       if ((index == value.length() - 1) && (contains(value, index - 2, 2, "AI", "OI")))
/*      */       {
/*      */ 
/*  655 */         result.appendAlternate('S');
/*      */       } else {
/*  657 */         result.append('S');
/*      */       }
/*  659 */       index = contains(value, index + 1, 1, "S", "Z") ? index + 2 : index + 1;
/*      */     }
/*  661 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleSC(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  670 */     if (charAt(value, index + 2) == 'H')
/*      */     {
/*  672 */       if (contains(value, index + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM"))
/*      */       {
/*      */ 
/*  675 */         if (contains(value, index + 3, 2, "ER", "EN"))
/*      */         {
/*  677 */           result.append("X", "SK");
/*      */         } else {
/*  679 */           result.append("SK");
/*      */         }
/*      */       }
/*  682 */       else if ((index == 0) && (!isVowel(charAt(value, 3))) && (charAt(value, 3) != 'W')) {
/*  683 */         result.append('X', 'S');
/*      */       } else {
/*  685 */         result.append('X');
/*      */       }
/*      */     }
/*  688 */     else if (contains(value, index + 2, 1, "I", "E", "Y")) {
/*  689 */       result.append('S');
/*      */     } else {
/*  691 */       result.append("SK");
/*      */     }
/*  693 */     return index + 3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleT(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  702 */     if (contains(value, index, 4, "TION")) {
/*  703 */       result.append('X');
/*  704 */       index += 3;
/*  705 */     } else if (contains(value, index, 3, "TIA", "TCH")) {
/*  706 */       result.append('X');
/*  707 */       index += 3;
/*  708 */     } else if ((contains(value, index, 2, "TH")) || (contains(value, index, 3, "TTH")))
/*      */     {
/*  710 */       if ((contains(value, index + 2, 2, "OM", "AM")) || (contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH")))
/*      */       {
/*      */ 
/*      */ 
/*  714 */         result.append('T');
/*      */       } else {
/*  716 */         result.append('0', 'T');
/*      */       }
/*  718 */       index += 2;
/*      */     } else {
/*  720 */       result.append('T');
/*  721 */       index = contains(value, index + 1, 1, "T", "D") ? index + 2 : index + 1;
/*      */     }
/*  723 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleW(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  732 */     if (contains(value, index, 2, "WR"))
/*      */     {
/*  734 */       result.append('R');
/*  735 */       index += 2;
/*      */     }
/*  737 */     else if ((index == 0) && ((isVowel(charAt(value, index + 1))) || (contains(value, index, 2, "WH"))))
/*      */     {
/*  739 */       if (isVowel(charAt(value, index + 1)))
/*      */       {
/*  741 */         result.append('A', 'F');
/*      */       }
/*      */       else {
/*  744 */         result.append('A');
/*      */       }
/*  746 */       index++;
/*  747 */     } else if (((index == value.length() - 1) && (isVowel(charAt(value, index - 1)))) || (contains(value, index - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY")) || (contains(value, 0, 3, "SCH")))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  752 */       result.appendAlternate('F');
/*  753 */       index++;
/*  754 */     } else if (contains(value, index, 4, "WICZ", "WITZ"))
/*      */     {
/*  756 */       result.append("TS", "FX");
/*  757 */       index += 4;
/*      */     } else {
/*  759 */       index++;
/*      */     }
/*      */     
/*  762 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleX(String value, DoubleMetaphoneResult result, int index)
/*      */   {
/*  771 */     if (index == 0) {
/*  772 */       result.append('S');
/*  773 */       index++;
/*      */     } else {
/*  775 */       if ((index != value.length() - 1) || ((!contains(value, index - 3, 3, "IAU", "EAU")) && (!contains(value, index - 2, 2, "AU", "OU"))))
/*      */       {
/*      */ 
/*      */ 
/*  779 */         result.append("KS");
/*      */       }
/*  781 */       index = contains(value, index + 1, 1, "C", "X") ? index + 2 : index + 1;
/*      */     }
/*  783 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int handleZ(String value, DoubleMetaphoneResult result, int index, boolean slavoGermanic)
/*      */   {
/*  791 */     if (charAt(value, index + 1) == 'H')
/*      */     {
/*  793 */       result.append('J');
/*  794 */       index += 2;
/*      */     } else {
/*  796 */       if ((contains(value, index + 1, 2, "ZO", "ZI", "ZA")) || ((slavoGermanic) && (index > 0) && (charAt(value, index - 1) != 'T'))) {
/*  797 */         result.append("S", "TS");
/*      */       } else {
/*  799 */         result.append('S');
/*      */       }
/*  801 */       index = charAt(value, index + 1) == 'Z' ? index + 2 : index + 1;
/*      */     }
/*  803 */     return index;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionC0(String value, int index)
/*      */   {
/*  812 */     if (contains(value, index, 4, "CHIA"))
/*  813 */       return true;
/*  814 */     if (index <= 1)
/*  815 */       return false;
/*  816 */     if (isVowel(charAt(value, index - 2)))
/*  817 */       return false;
/*  818 */     if (!contains(value, index - 1, 3, "ACH")) {
/*  819 */       return false;
/*      */     }
/*  821 */     char c = charAt(value, index + 2);
/*  822 */     return ((c != 'I') && (c != 'E')) || (contains(value, index - 2, 6, "BACHER", "MACHER"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionCH0(String value, int index)
/*      */   {
/*  831 */     if (index != 0)
/*  832 */       return false;
/*  833 */     if ((!contains(value, index + 1, 5, "HARAC", "HARIS")) && (!contains(value, index + 1, 3, "HOR", "HYM", "HIA", "HEM")))
/*      */     {
/*  835 */       return false; }
/*  836 */     if (contains(value, 0, 5, "CHORE")) {
/*  837 */       return false;
/*      */     }
/*  839 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionCH1(String value, int index)
/*      */   {
/*  847 */     return (contains(value, 0, 4, "VAN ", "VON ")) || (contains(value, 0, 3, "SCH")) || (contains(value, index - 2, 6, "ORCHES", "ARCHIT", "ORCHID")) || (contains(value, index + 2, 1, "T", "S")) || (((contains(value, index - 1, 1, "A", "O", "U", "E")) || (index == 0)) && ((contains(value, index + 2, 1, L_R_N_M_B_H_F_V_W_SPACE)) || (index + 1 == value.length() - 1)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionL0(String value, int index)
/*      */   {
/*  859 */     if ((index == value.length() - 3) && (contains(value, index - 1, 4, "ILLO", "ILLA", "ALLE")))
/*      */     {
/*  861 */       return true; }
/*  862 */     if (((contains(value, value.length() - 2, 2, "AS", "OS")) || (contains(value, value.length() - 1, 1, "A", "O"))) && (contains(value, index - 1, 4, "ALLE")))
/*      */     {
/*      */ 
/*  865 */       return true;
/*      */     }
/*  867 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean conditionM0(String value, int index)
/*      */   {
/*  875 */     if (charAt(value, index + 1) == 'M') {
/*  876 */       return true;
/*      */     }
/*  878 */     return (contains(value, index - 1, 3, "UMB")) && ((index + 1 == value.length() - 1) || (contains(value, index + 2, 2, "ER")));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isSlavoGermanic(String value)
/*      */   {
/*  890 */     return (value.indexOf('W') > -1) || (value.indexOf('K') > -1) || (value.indexOf("CZ") > -1) || (value.indexOf("WITZ") > -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isVowel(char ch)
/*      */   {
/*  898 */     return "AEIOUY".indexOf(ch) != -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isSilentStart(String value)
/*      */   {
/*  907 */     boolean result = false;
/*  908 */     for (int i = 0; i < SILENT_START.length; i++) {
/*  909 */       if (value.startsWith(SILENT_START[i])) {
/*  910 */         result = true;
/*  911 */         break;
/*      */       }
/*      */     }
/*  914 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String cleanInput(String input)
/*      */   {
/*  921 */     if (input == null) {
/*  922 */       return null;
/*      */     }
/*  924 */     input = input.trim();
/*  925 */     if (input.length() == 0) {
/*  926 */       return null;
/*      */     }
/*  928 */     return input.toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected char charAt(String value, int index)
/*      */   {
/*  937 */     if ((index < 0) || (index >= value.length())) {
/*  938 */       return '\000';
/*      */     }
/*  940 */     return value.charAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria)
/*      */   {
/*  948 */     return contains(value, start, length, new String[] { criteria });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2)
/*      */   {
/*  957 */     return contains(value, start, length, new String[] { criteria1, criteria2 });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3)
/*      */   {
/*  967 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3 });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4)
/*      */   {
/*  977 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4 });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5)
/*      */   {
/*  989 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5 });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean contains(String value, int start, int length, String criteria1, String criteria2, String criteria3, String criteria4, String criteria5, String criteria6)
/*      */   {
/* 1001 */     return contains(value, start, length, new String[] { criteria1, criteria2, criteria3, criteria4, criteria5, criteria6 });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static boolean contains(String value, int start, int length, String[] criteria)
/*      */   {
/* 1012 */     boolean result = false;
/* 1013 */     if ((start >= 0) && (start + length <= value.length())) {
/* 1014 */       String target = value.substring(start, start + length);
/*      */       
/* 1016 */       for (int i = 0; i < criteria.length; i++) {
/* 1017 */         if (target.equals(criteria[i])) {
/* 1018 */           result = true;
/* 1019 */           break;
/*      */         }
/*      */       }
/*      */     }
/* 1023 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public class DoubleMetaphoneResult
/*      */   {
/* 1034 */     private StringBuffer primary = new StringBuffer(DoubleMetaphone.this.getMaxCodeLen());
/* 1035 */     private StringBuffer alternate = new StringBuffer(DoubleMetaphone.this.getMaxCodeLen());
/*      */     private int maxLength;
/*      */     
/*      */     public DoubleMetaphoneResult(int maxLength) {
/* 1039 */       this.maxLength = maxLength;
/*      */     }
/*      */     
/*      */     public void append(char value) {
/* 1043 */       appendPrimary(value);
/* 1044 */       appendAlternate(value);
/*      */     }
/*      */     
/*      */     public void append(char primary, char alternate) {
/* 1048 */       appendPrimary(primary);
/* 1049 */       appendAlternate(alternate);
/*      */     }
/*      */     
/*      */     public void appendPrimary(char value) {
/* 1053 */       if (this.primary.length() < this.maxLength) {
/* 1054 */         this.primary.append(value);
/*      */       }
/*      */     }
/*      */     
/*      */     public void appendAlternate(char value) {
/* 1059 */       if (this.alternate.length() < this.maxLength) {
/* 1060 */         this.alternate.append(value);
/*      */       }
/*      */     }
/*      */     
/*      */     public void append(String value) {
/* 1065 */       appendPrimary(value);
/* 1066 */       appendAlternate(value);
/*      */     }
/*      */     
/*      */     public void append(String primary, String alternate) {
/* 1070 */       appendPrimary(primary);
/* 1071 */       appendAlternate(alternate);
/*      */     }
/*      */     
/*      */     public void appendPrimary(String value) {
/* 1075 */       int addChars = this.maxLength - this.primary.length();
/* 1076 */       if (value.length() <= addChars) {
/* 1077 */         this.primary.append(value);
/*      */       } else {
/* 1079 */         this.primary.append(value.substring(0, addChars));
/*      */       }
/*      */     }
/*      */     
/*      */     public void appendAlternate(String value) {
/* 1084 */       int addChars = this.maxLength - this.alternate.length();
/* 1085 */       if (value.length() <= addChars) {
/* 1086 */         this.alternate.append(value);
/*      */       } else {
/* 1088 */         this.alternate.append(value.substring(0, addChars));
/*      */       }
/*      */     }
/*      */     
/*      */     public String getPrimary() {
/* 1093 */       return this.primary.toString();
/*      */     }
/*      */     
/*      */     public String getAlternate() {
/* 1097 */       return this.alternate.toString();
/*      */     }
/*      */     
/*      */     public boolean isComplete() {
/* 1101 */       return (this.primary.length() >= this.maxLength) && (this.alternate.length() >= this.maxLength);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\language\DoubleMetaphone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */