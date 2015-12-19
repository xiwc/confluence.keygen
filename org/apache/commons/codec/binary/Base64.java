/*    1:     */ package org.apache.commons.codec.binary;
/*    2:     */ 
/*    3:     */ import java.math.BigInteger;
/*    4:     */ import org.apache.commons.codec.BinaryDecoder;
/*    5:     */ import org.apache.commons.codec.BinaryEncoder;
/*    6:     */ import org.apache.commons.codec.DecoderException;
/*    7:     */ import org.apache.commons.codec.EncoderException;
/*    8:     */ 
/*    9:     */ public class Base64
/*   10:     */   implements BinaryEncoder, BinaryDecoder
/*   11:     */ {
/*   12:     */   private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
/*   13:     */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*   14:     */   static final int CHUNK_SIZE = 76;
/*   15:  79 */   static final byte[] CHUNK_SEPARATOR = { 13, 10 };
/*   16:  88 */   private static final byte[] STANDARD_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*   17: 101 */   private static final byte[] URL_SAFE_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*   18:     */   private static final byte PAD = 61;
/*   19: 125 */   private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*   20:     */   private static final int MASK_6BITS = 63;
/*   21:     */   private static final int MASK_8BITS = 255;
/*   22:     */   private final byte[] encodeTable;
/*   23:     */   private final int lineLength;
/*   24:     */   private final byte[] lineSeparator;
/*   25:     */   private final int decodeSize;
/*   26:     */   private final int encodeSize;
/*   27:     */   private byte[] buffer;
/*   28:     */   private int pos;
/*   29:     */   private int readPos;
/*   30:     */   private int currentLinePos;
/*   31:     */   private int modulus;
/*   32:     */   private boolean eof;
/*   33:     */   private int x;
/*   34:     */   
/*   35:     */   public Base64()
/*   36:     */   {
/*   37: 225 */     this(false);
/*   38:     */   }
/*   39:     */   
/*   40:     */   public Base64(boolean urlSafe)
/*   41:     */   {
/*   42: 244 */     this(76, CHUNK_SEPARATOR, urlSafe);
/*   43:     */   }
/*   44:     */   
/*   45:     */   public Base64(int lineLength)
/*   46:     */   {
/*   47: 266 */     this(lineLength, CHUNK_SEPARATOR);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public Base64(int lineLength, byte[] lineSeparator)
/*   51:     */   {
/*   52: 292 */     this(lineLength, lineSeparator, false);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe)
/*   56:     */   {
/*   57: 321 */     if (lineSeparator == null)
/*   58:     */     {
/*   59: 322 */       lineLength = 0;
/*   60: 323 */       lineSeparator = CHUNK_SEPARATOR;
/*   61:     */     }
/*   62: 325 */     this.lineLength = (lineLength > 0 ? lineLength / 4 * 4 : 0);
/*   63: 326 */     this.lineSeparator = new byte[lineSeparator.length];
/*   64: 327 */     System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
/*   65: 328 */     if (lineLength > 0) {
/*   66: 329 */       this.encodeSize = (4 + lineSeparator.length);
/*   67:     */     } else {
/*   68: 331 */       this.encodeSize = 4;
/*   69:     */     }
/*   70: 333 */     this.decodeSize = (this.encodeSize - 1);
/*   71: 334 */     if (containsBase64Byte(lineSeparator))
/*   72:     */     {
/*   73: 335 */       String sep = StringUtils.newStringUtf8(lineSeparator);
/*   74: 336 */       throw new IllegalArgumentException("lineSeperator must not contain base64 characters: [" + sep + "]");
/*   75:     */     }
/*   76: 338 */     this.encodeTable = (urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE);
/*   77:     */   }
/*   78:     */   
/*   79:     */   public boolean isUrlSafe()
/*   80:     */   {
/*   81: 348 */     return this.encodeTable == URL_SAFE_ENCODE_TABLE;
/*   82:     */   }
/*   83:     */   
/*   84:     */   boolean hasData()
/*   85:     */   {
/*   86: 357 */     return this.buffer != null;
/*   87:     */   }
/*   88:     */   
/*   89:     */   int avail()
/*   90:     */   {
/*   91: 366 */     return this.buffer != null ? this.pos - this.readPos : 0;
/*   92:     */   }
/*   93:     */   
/*   94:     */   private void resizeBuffer()
/*   95:     */   {
/*   96: 371 */     if (this.buffer == null)
/*   97:     */     {
/*   98: 372 */       this.buffer = new byte[8192];
/*   99: 373 */       this.pos = 0;
/*  100: 374 */       this.readPos = 0;
/*  101:     */     }
/*  102:     */     else
/*  103:     */     {
/*  104: 376 */       byte[] b = new byte[this.buffer.length * 2];
/*  105: 377 */       System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
/*  106: 378 */       this.buffer = b;
/*  107:     */     }
/*  108:     */   }
/*  109:     */   
/*  110:     */   int readResults(byte[] b, int bPos, int bAvail)
/*  111:     */   {
/*  112: 395 */     if (this.buffer != null)
/*  113:     */     {
/*  114: 396 */       int len = Math.min(avail(), bAvail);
/*  115: 397 */       if (this.buffer != b)
/*  116:     */       {
/*  117: 398 */         System.arraycopy(this.buffer, this.readPos, b, bPos, len);
/*  118: 399 */         this.readPos += len;
/*  119: 400 */         if (this.readPos >= this.pos) {
/*  120: 401 */           this.buffer = null;
/*  121:     */         }
/*  122:     */       }
/*  123:     */       else
/*  124:     */       {
/*  125: 406 */         this.buffer = null;
/*  126:     */       }
/*  127: 408 */       return len;
/*  128:     */     }
/*  129: 410 */     return this.eof ? -1 : 0;
/*  130:     */   }
/*  131:     */   
/*  132:     */   void setInitialBuffer(byte[] out, int outPos, int outAvail)
/*  133:     */   {
/*  134: 427 */     if ((out != null) && (out.length == outAvail))
/*  135:     */     {
/*  136: 428 */       this.buffer = out;
/*  137: 429 */       this.pos = outPos;
/*  138: 430 */       this.readPos = outPos;
/*  139:     */     }
/*  140:     */   }
/*  141:     */   
/*  142:     */   void encode(byte[] in, int inPos, int inAvail)
/*  143:     */   {
/*  144: 453 */     if (this.eof) {
/*  145: 454 */       return;
/*  146:     */     }
/*  147: 458 */     if (inAvail < 0)
/*  148:     */     {
/*  149: 459 */       this.eof = true;
/*  150: 460 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  151: 461 */         resizeBuffer();
/*  152:     */       }
/*  153: 463 */       switch (this.modulus)
/*  154:     */       {
/*  155:     */       case 1: 
/*  156: 465 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 2 & 0x3F)];
/*  157: 466 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 4 & 0x3F)];
/*  158: 468 */         if (this.encodeTable == STANDARD_ENCODE_TABLE)
/*  159:     */         {
/*  160: 469 */           this.buffer[(this.pos++)] = 61;
/*  161: 470 */           this.buffer[(this.pos++)] = 61;
/*  162:     */         }
/*  163:     */         break;
/*  164:     */       case 2: 
/*  165: 475 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 10 & 0x3F)];
/*  166: 476 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 4 & 0x3F)];
/*  167: 477 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 2 & 0x3F)];
/*  168: 479 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/*  169: 480 */           this.buffer[(this.pos++)] = 61;
/*  170:     */         }
/*  171:     */         break;
/*  172:     */       }
/*  173: 484 */       if ((this.lineLength > 0) && (this.pos > 0))
/*  174:     */       {
/*  175: 485 */         System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  176: 486 */         this.pos += this.lineSeparator.length;
/*  177:     */       }
/*  178:     */     }
/*  179:     */     else
/*  180:     */     {
/*  181: 489 */       for (int i = 0; i < inAvail; i++)
/*  182:     */       {
/*  183: 490 */         if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  184: 491 */           resizeBuffer();
/*  185:     */         }
/*  186: 493 */         this.modulus = (++this.modulus % 3);
/*  187: 494 */         int b = in[(inPos++)];
/*  188: 495 */         if (b < 0) {
/*  189: 496 */           b += 256;
/*  190:     */         }
/*  191: 498 */         this.x = ((this.x << 8) + b);
/*  192: 499 */         if (0 == this.modulus)
/*  193:     */         {
/*  194: 500 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 18 & 0x3F)];
/*  195: 501 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 12 & 0x3F)];
/*  196: 502 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 6 & 0x3F)];
/*  197: 503 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x & 0x3F)];
/*  198: 504 */           this.currentLinePos += 4;
/*  199: 505 */           if ((this.lineLength > 0) && (this.lineLength <= this.currentLinePos))
/*  200:     */           {
/*  201: 506 */             System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  202: 507 */             this.pos += this.lineSeparator.length;
/*  203: 508 */             this.currentLinePos = 0;
/*  204:     */           }
/*  205:     */         }
/*  206:     */       }
/*  207:     */     }
/*  208:     */   }
/*  209:     */   
/*  210:     */   void decode(byte[] in, int inPos, int inAvail)
/*  211:     */   {
/*  212: 539 */     if (this.eof) {
/*  213: 540 */       return;
/*  214:     */     }
/*  215: 542 */     if (inAvail < 0) {
/*  216: 543 */       this.eof = true;
/*  217:     */     }
/*  218: 545 */     for (int i = 0; i < inAvail; i++)
/*  219:     */     {
/*  220: 546 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.decodeSize)) {
/*  221: 547 */         resizeBuffer();
/*  222:     */       }
/*  223: 549 */       byte b = in[(inPos++)];
/*  224: 550 */       if (b == 61)
/*  225:     */       {
/*  226: 552 */         this.eof = true;
/*  227: 553 */         break;
/*  228:     */       }
/*  229: 555 */       if ((b >= 0) && (b < DECODE_TABLE.length))
/*  230:     */       {
/*  231: 556 */         int result = DECODE_TABLE[b];
/*  232: 557 */         if (result >= 0)
/*  233:     */         {
/*  234: 558 */           this.modulus = (++this.modulus % 4);
/*  235: 559 */           this.x = ((this.x << 6) + result);
/*  236: 560 */           if (this.modulus == 0)
/*  237:     */           {
/*  238: 561 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  239: 562 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*  240: 563 */             this.buffer[(this.pos++)] = ((byte)(this.x & 0xFF));
/*  241:     */           }
/*  242:     */         }
/*  243:     */       }
/*  244:     */     }
/*  245: 573 */     if ((this.eof) && (this.modulus != 0))
/*  246:     */     {
/*  247: 574 */       this.x <<= 6;
/*  248: 575 */       switch (this.modulus)
/*  249:     */       {
/*  250:     */       case 2: 
/*  251: 577 */         this.x <<= 6;
/*  252: 578 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  253: 579 */         break;
/*  254:     */       case 3: 
/*  255: 581 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  256: 582 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*  257:     */       }
/*  258:     */     }
/*  259:     */   }
/*  260:     */   
/*  261:     */   public static boolean isBase64(byte octet)
/*  262:     */   {
/*  263: 597 */     return (octet == 61) || ((octet >= 0) && (octet < DECODE_TABLE.length) && (DECODE_TABLE[octet] != -1));
/*  264:     */   }
/*  265:     */   
/*  266:     */   public static boolean isArrayByteBase64(byte[] arrayOctet)
/*  267:     */   {
/*  268: 610 */     for (int i = 0; i < arrayOctet.length; i++) {
/*  269: 611 */       if ((!isBase64(arrayOctet[i])) && (!isWhiteSpace(arrayOctet[i]))) {
/*  270: 612 */         return false;
/*  271:     */       }
/*  272:     */     }
/*  273: 615 */     return true;
/*  274:     */   }
/*  275:     */   
/*  276:     */   private static boolean containsBase64Byte(byte[] arrayOctet)
/*  277:     */   {
/*  278: 626 */     for (int i = 0; i < arrayOctet.length; i++) {
/*  279: 627 */       if (isBase64(arrayOctet[i])) {
/*  280: 628 */         return true;
/*  281:     */       }
/*  282:     */     }
/*  283: 631 */     return false;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public static byte[] encodeBase64(byte[] binaryData)
/*  287:     */   {
/*  288: 642 */     return encodeBase64(binaryData, false);
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static String encodeBase64String(byte[] binaryData)
/*  292:     */   {
/*  293: 654 */     return StringUtils.newStringUtf8(encodeBase64(binaryData, true));
/*  294:     */   }
/*  295:     */   
/*  296:     */   public static byte[] encodeBase64URLSafe(byte[] binaryData)
/*  297:     */   {
/*  298: 667 */     return encodeBase64(binaryData, false, true);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public static String encodeBase64URLSafeString(byte[] binaryData)
/*  302:     */   {
/*  303: 680 */     return StringUtils.newStringUtf8(encodeBase64(binaryData, false, true));
/*  304:     */   }
/*  305:     */   
/*  306:     */   public static byte[] encodeBase64Chunked(byte[] binaryData)
/*  307:     */   {
/*  308: 691 */     return encodeBase64(binaryData, true);
/*  309:     */   }
/*  310:     */   
/*  311:     */   public Object decode(Object pObject)
/*  312:     */     throws DecoderException
/*  313:     */   {
/*  314: 705 */     if ((pObject instanceof byte[])) {
/*  315: 706 */       return decode((byte[])pObject);
/*  316:     */     }
/*  317: 707 */     if ((pObject instanceof String)) {
/*  318: 708 */       return decode((String)pObject);
/*  319:     */     }
/*  320: 710 */     throw new DecoderException("Parameter supplied to Base64 decode is not a byte[] or a String");
/*  321:     */   }
/*  322:     */   
/*  323:     */   public byte[] decode(String pArray)
/*  324:     */   {
/*  325: 723 */     return decode(StringUtils.getBytesUtf8(pArray));
/*  326:     */   }
/*  327:     */   
/*  328:     */   public byte[] decode(byte[] pArray)
/*  329:     */   {
/*  330: 734 */     reset();
/*  331: 735 */     if ((pArray == null) || (pArray.length == 0)) {
/*  332: 736 */       return pArray;
/*  333:     */     }
/*  334: 738 */     long len = pArray.length * 3 / 4;
/*  335: 739 */     byte[] buf = new byte[(int)len];
/*  336: 740 */     setInitialBuffer(buf, 0, buf.length);
/*  337: 741 */     decode(pArray, 0, pArray.length);
/*  338: 742 */     decode(pArray, 0, -1);
/*  339:     */     
/*  340:     */ 
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346: 750 */     byte[] result = new byte[this.pos];
/*  347: 751 */     readResults(result, 0, result.length);
/*  348: 752 */     return result;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked)
/*  352:     */   {
/*  353: 767 */     return encodeBase64(binaryData, isChunked, false);
/*  354:     */   }
/*  355:     */   
/*  356:     */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe)
/*  357:     */   {
/*  358: 785 */     return encodeBase64(binaryData, isChunked, urlSafe, 2147483647);
/*  359:     */   }
/*  360:     */   
/*  361:     */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize)
/*  362:     */   {
/*  363: 805 */     if ((binaryData == null) || (binaryData.length == 0)) {
/*  364: 806 */       return binaryData;
/*  365:     */     }
/*  366: 809 */     long len = getEncodeLength(binaryData, 76, CHUNK_SEPARATOR);
/*  367: 810 */     if (len > maxResultSize) {
/*  368: 811 */       throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maxium size of " + maxResultSize);
/*  369:     */     }
/*  370: 817 */     Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
/*  371: 818 */     return b64.encode(binaryData);
/*  372:     */   }
/*  373:     */   
/*  374:     */   public static byte[] decodeBase64(String base64String)
/*  375:     */   {
/*  376: 830 */     return new Base64().decode(base64String);
/*  377:     */   }
/*  378:     */   
/*  379:     */   public static byte[] decodeBase64(byte[] base64Data)
/*  380:     */   {
/*  381: 841 */     return new Base64().decode(base64Data);
/*  382:     */   }
/*  383:     */   
/*  384:     */   /**
/*  385:     */    * @deprecated
/*  386:     */    */
/*  387:     */   static byte[] discardWhitespace(byte[] data)
/*  388:     */   {
/*  389: 853 */     byte[] groomedData = new byte[data.length];
/*  390: 854 */     int bytesCopied = 0;
/*  391: 855 */     for (int i = 0; i < data.length; i++) {
/*  392: 856 */       switch (data[i])
/*  393:     */       {
/*  394:     */       case 9: 
/*  395:     */       case 10: 
/*  396:     */       case 13: 
/*  397:     */       case 32: 
/*  398:     */         break;
/*  399:     */       default: 
/*  400: 863 */         groomedData[(bytesCopied++)] = data[i];
/*  401:     */       }
/*  402:     */     }
/*  403: 866 */     byte[] packedData = new byte[bytesCopied];
/*  404: 867 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*  405: 868 */     return packedData;
/*  406:     */   }
/*  407:     */   
/*  408:     */   private static boolean isWhiteSpace(byte byteToCheck)
/*  409:     */   {
/*  410: 879 */     switch (byteToCheck)
/*  411:     */     {
/*  412:     */     case 9: 
/*  413:     */     case 10: 
/*  414:     */     case 13: 
/*  415:     */     case 32: 
/*  416: 884 */       return true;
/*  417:     */     }
/*  418: 886 */     return false;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public Object encode(Object pObject)
/*  422:     */     throws EncoderException
/*  423:     */   {
/*  424: 903 */     if (!(pObject instanceof byte[])) {
/*  425: 904 */       throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
/*  426:     */     }
/*  427: 906 */     return encode((byte[])pObject);
/*  428:     */   }
/*  429:     */   
/*  430:     */   public String encodeToString(byte[] pArray)
/*  431:     */   {
/*  432: 918 */     return StringUtils.newStringUtf8(encode(pArray));
/*  433:     */   }
/*  434:     */   
/*  435:     */   public byte[] encode(byte[] pArray)
/*  436:     */   {
/*  437: 929 */     reset();
/*  438: 930 */     if ((pArray == null) || (pArray.length == 0)) {
/*  439: 931 */       return pArray;
/*  440:     */     }
/*  441: 933 */     long len = getEncodeLength(pArray, this.lineLength, this.lineSeparator);
/*  442: 934 */     byte[] buf = new byte[(int)len];
/*  443: 935 */     setInitialBuffer(buf, 0, buf.length);
/*  444: 936 */     encode(pArray, 0, pArray.length);
/*  445: 937 */     encode(pArray, 0, -1);
/*  446: 939 */     if (this.buffer != buf) {
/*  447: 940 */       readResults(buf, 0, buf.length);
/*  448:     */     }
/*  449: 944 */     if ((isUrlSafe()) && (this.pos < buf.length))
/*  450:     */     {
/*  451: 945 */       byte[] smallerBuf = new byte[this.pos];
/*  452: 946 */       System.arraycopy(buf, 0, smallerBuf, 0, this.pos);
/*  453: 947 */       buf = smallerBuf;
/*  454:     */     }
/*  455: 949 */     return buf;
/*  456:     */   }
/*  457:     */   
/*  458:     */   private static long getEncodeLength(byte[] pArray, int chunkSize, byte[] chunkSeparator)
/*  459:     */   {
/*  460: 965 */     chunkSize = chunkSize / 4 * 4;
/*  461:     */     
/*  462: 967 */     long len = pArray.length * 4 / 3;
/*  463: 968 */     long mod = len % 4L;
/*  464: 969 */     if (mod != 0L) {
/*  465: 970 */       len += 4L - mod;
/*  466:     */     }
/*  467: 972 */     if (chunkSize > 0)
/*  468:     */     {
/*  469: 973 */       boolean lenChunksPerfectly = len % chunkSize == 0L;
/*  470: 974 */       len += len / chunkSize * chunkSeparator.length;
/*  471: 975 */       if (!lenChunksPerfectly) {
/*  472: 976 */         len += chunkSeparator.length;
/*  473:     */       }
/*  474:     */     }
/*  475: 979 */     return len;
/*  476:     */   }
/*  477:     */   
/*  478:     */   public static BigInteger decodeInteger(byte[] pArray)
/*  479:     */   {
/*  480: 992 */     return new BigInteger(1, decodeBase64(pArray));
/*  481:     */   }
/*  482:     */   
/*  483:     */   public static byte[] encodeInteger(BigInteger bigInt)
/*  484:     */   {
/*  485:1006 */     if (bigInt == null) {
/*  486:1007 */       throw new NullPointerException("encodeInteger called with null parameter");
/*  487:     */     }
/*  488:1009 */     return encodeBase64(toIntegerBytes(bigInt), false);
/*  489:     */   }
/*  490:     */   
/*  491:     */   static byte[] toIntegerBytes(BigInteger bigInt)
/*  492:     */   {
/*  493:1020 */     int bitlen = bigInt.bitLength();
/*  494:     */     
/*  495:1022 */     bitlen = bitlen + 7 >> 3 << 3;
/*  496:1023 */     byte[] bigBytes = bigInt.toByteArray();
/*  497:1025 */     if ((bigInt.bitLength() % 8 != 0) && (bigInt.bitLength() / 8 + 1 == bitlen / 8)) {
/*  498:1026 */       return bigBytes;
/*  499:     */     }
/*  500:1029 */     int startSrc = 0;
/*  501:1030 */     int len = bigBytes.length;
/*  502:1033 */     if (bigInt.bitLength() % 8 == 0)
/*  503:     */     {
/*  504:1034 */       startSrc = 1;
/*  505:1035 */       len--;
/*  506:     */     }
/*  507:1037 */     int startDst = bitlen / 8 - len;
/*  508:1038 */     byte[] resizedBytes = new byte[bitlen / 8];
/*  509:1039 */     System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
/*  510:1040 */     return resizedBytes;
/*  511:     */   }
/*  512:     */   
/*  513:     */   private void reset()
/*  514:     */   {
/*  515:1047 */     this.buffer = null;
/*  516:1048 */     this.pos = 0;
/*  517:1049 */     this.readPos = 0;
/*  518:1050 */     this.currentLinePos = 0;
/*  519:1051 */     this.modulus = 0;
/*  520:1052 */     this.eof = false;
/*  521:     */   }
/*  522:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.Base64
 * JD-Core Version:    0.7.0.1
 */