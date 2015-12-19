/*      */ package org.apache.commons.codec.binary;
/*      */ 
/*      */ import java.math.BigInteger;
/*      */ import org.apache.commons.codec.BinaryDecoder;
/*      */ import org.apache.commons.codec.BinaryEncoder;
/*      */ import org.apache.commons.codec.DecoderException;
/*      */ import org.apache.commons.codec.EncoderException;
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
/*      */ public class Base64
/*      */   implements BinaryEncoder, BinaryDecoder
/*      */ {
/*      */   private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
/*      */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*      */   static final int CHUNK_SIZE = 76;
/*   79 */   static final byte[] CHUNK_SEPARATOR = { 13, 10 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   88 */   private static final byte[] STANDARD_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
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
/*  101 */   private static final byte[] URL_SAFE_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
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
/*      */   private static final byte PAD = 61;
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
/*  125 */   private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MASK_6BITS = 63;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MASK_8BITS = 255;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final byte[] encodeTable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int lineLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final byte[] lineSeparator;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int decodeSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int encodeSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte[] buffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int pos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int readPos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int currentLinePos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int modulus;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean eof;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int x;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64()
/*      */   {
/*  225 */     this(false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64(boolean urlSafe)
/*      */   {
/*  244 */     this(76, CHUNK_SEPARATOR, urlSafe);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64(int lineLength)
/*      */   {
/*  266 */     this(lineLength, CHUNK_SEPARATOR);
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
/*      */   public Base64(int lineLength, byte[] lineSeparator)
/*      */   {
/*  292 */     this(lineLength, lineSeparator, false);
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
/*      */   public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe)
/*      */   {
/*  321 */     if (lineSeparator == null) {
/*  322 */       lineLength = 0;
/*  323 */       lineSeparator = CHUNK_SEPARATOR;
/*      */     }
/*  325 */     this.lineLength = (lineLength > 0 ? lineLength / 4 * 4 : 0);
/*  326 */     this.lineSeparator = new byte[lineSeparator.length];
/*  327 */     System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
/*  328 */     if (lineLength > 0) {
/*  329 */       this.encodeSize = (4 + lineSeparator.length);
/*      */     } else {
/*  331 */       this.encodeSize = 4;
/*      */     }
/*  333 */     this.decodeSize = (this.encodeSize - 1);
/*  334 */     if (containsBase64Byte(lineSeparator)) {
/*  335 */       String sep = StringUtils.newStringUtf8(lineSeparator);
/*  336 */       throw new IllegalArgumentException("lineSeperator must not contain base64 characters: [" + sep + "]");
/*      */     }
/*  338 */     this.encodeTable = (urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isUrlSafe()
/*      */   {
/*  348 */     return this.encodeTable == URL_SAFE_ENCODE_TABLE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean hasData()
/*      */   {
/*  357 */     return this.buffer != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int avail()
/*      */   {
/*  366 */     return this.buffer != null ? this.pos - this.readPos : 0;
/*      */   }
/*      */   
/*      */   private void resizeBuffer()
/*      */   {
/*  371 */     if (this.buffer == null) {
/*  372 */       this.buffer = new byte[' '];
/*  373 */       this.pos = 0;
/*  374 */       this.readPos = 0;
/*      */     } else {
/*  376 */       byte[] b = new byte[this.buffer.length * 2];
/*  377 */       System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
/*  378 */       this.buffer = b;
/*      */     }
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
/*      */   int readResults(byte[] b, int bPos, int bAvail)
/*      */   {
/*  395 */     if (this.buffer != null) {
/*  396 */       int len = Math.min(avail(), bAvail);
/*  397 */       if (this.buffer != b) {
/*  398 */         System.arraycopy(this.buffer, this.readPos, b, bPos, len);
/*  399 */         this.readPos += len;
/*  400 */         if (this.readPos >= this.pos) {
/*  401 */           this.buffer = null;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  406 */         this.buffer = null;
/*      */       }
/*  408 */       return len;
/*      */     }
/*  410 */     return this.eof ? -1 : 0;
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
/*      */ 
/*      */   void setInitialBuffer(byte[] out, int outPos, int outAvail)
/*      */   {
/*  427 */     if ((out != null) && (out.length == outAvail)) {
/*  428 */       this.buffer = out;
/*  429 */       this.pos = outPos;
/*  430 */       this.readPos = outPos;
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void encode(byte[] in, int inPos, int inAvail)
/*      */   {
/*  453 */     if (this.eof) {
/*  454 */       return;
/*      */     }
/*      */     
/*      */ 
/*  458 */     if (inAvail < 0) {
/*  459 */       this.eof = true;
/*  460 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  461 */         resizeBuffer();
/*      */       }
/*  463 */       switch (this.modulus) {
/*      */       case 1: 
/*  465 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 2 & 0x3F)];
/*  466 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 4 & 0x3F)];
/*      */         
/*  468 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/*  469 */           this.buffer[(this.pos++)] = 61;
/*  470 */           this.buffer[(this.pos++)] = 61;
/*      */         }
/*      */         
/*      */         break;
/*      */       case 2: 
/*  475 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 10 & 0x3F)];
/*  476 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 4 & 0x3F)];
/*  477 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 2 & 0x3F)];
/*      */         
/*  479 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/*  480 */           this.buffer[(this.pos++)] = 61;
/*      */         }
/*      */         break;
/*      */       }
/*  484 */       if ((this.lineLength > 0) && (this.pos > 0)) {
/*  485 */         System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  486 */         this.pos += this.lineSeparator.length;
/*      */       }
/*      */     } else {
/*  489 */       for (int i = 0; i < inAvail; i++) {
/*  490 */         if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  491 */           resizeBuffer();
/*      */         }
/*  493 */         this.modulus = (++this.modulus % 3);
/*  494 */         int b = in[(inPos++)];
/*  495 */         if (b < 0) {
/*  496 */           b += 256;
/*      */         }
/*  498 */         this.x = ((this.x << 8) + b);
/*  499 */         if (0 == this.modulus) {
/*  500 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 18 & 0x3F)];
/*  501 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 12 & 0x3F)];
/*  502 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 6 & 0x3F)];
/*  503 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x & 0x3F)];
/*  504 */           this.currentLinePos += 4;
/*  505 */           if ((this.lineLength > 0) && (this.lineLength <= this.currentLinePos)) {
/*  506 */             System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  507 */             this.pos += this.lineSeparator.length;
/*  508 */             this.currentLinePos = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/*      */   void decode(byte[] in, int inPos, int inAvail)
/*      */   {
/*  539 */     if (this.eof) {
/*  540 */       return;
/*      */     }
/*  542 */     if (inAvail < 0) {
/*  543 */       this.eof = true;
/*      */     }
/*  545 */     for (int i = 0; i < inAvail; i++) {
/*  546 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.decodeSize)) {
/*  547 */         resizeBuffer();
/*      */       }
/*  549 */       byte b = in[(inPos++)];
/*  550 */       if (b == 61)
/*      */       {
/*  552 */         this.eof = true;
/*  553 */         break;
/*      */       }
/*  555 */       if ((b >= 0) && (b < DECODE_TABLE.length)) {
/*  556 */         int result = DECODE_TABLE[b];
/*  557 */         if (result >= 0) {
/*  558 */           this.modulus = (++this.modulus % 4);
/*  559 */           this.x = ((this.x << 6) + result);
/*  560 */           if (this.modulus == 0) {
/*  561 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  562 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*  563 */             this.buffer[(this.pos++)] = ((byte)(this.x & 0xFF));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  573 */     if ((this.eof) && (this.modulus != 0)) {
/*  574 */       this.x <<= 6;
/*  575 */       switch (this.modulus) {
/*      */       case 2: 
/*  577 */         this.x <<= 6;
/*  578 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  579 */         break;
/*      */       case 3: 
/*  581 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  582 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isBase64(byte octet)
/*      */   {
/*  597 */     return (octet == 61) || ((octet >= 0) && (octet < DECODE_TABLE.length) && (DECODE_TABLE[octet] != -1));
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
/*      */   public static boolean isArrayByteBase64(byte[] arrayOctet)
/*      */   {
/*  610 */     for (int i = 0; i < arrayOctet.length; i++) {
/*  611 */       if ((!isBase64(arrayOctet[i])) && (!isWhiteSpace(arrayOctet[i]))) {
/*  612 */         return false;
/*      */       }
/*      */     }
/*  615 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean containsBase64Byte(byte[] arrayOctet)
/*      */   {
/*  626 */     for (int i = 0; i < arrayOctet.length; i++) {
/*  627 */       if (isBase64(arrayOctet[i])) {
/*  628 */         return true;
/*      */       }
/*      */     }
/*  631 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData)
/*      */   {
/*  642 */     return encodeBase64(binaryData, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String encodeBase64String(byte[] binaryData)
/*      */   {
/*  654 */     return StringUtils.newStringUtf8(encodeBase64(binaryData, true));
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
/*      */   public static byte[] encodeBase64URLSafe(byte[] binaryData)
/*      */   {
/*  667 */     return encodeBase64(binaryData, false, true);
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
/*      */   public static String encodeBase64URLSafeString(byte[] binaryData)
/*      */   {
/*  680 */     return StringUtils.newStringUtf8(encodeBase64(binaryData, false, true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64Chunked(byte[] binaryData)
/*      */   {
/*  691 */     return encodeBase64(binaryData, true);
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
/*      */   public Object decode(Object pObject)
/*      */     throws DecoderException
/*      */   {
/*  705 */     if ((pObject instanceof byte[]))
/*  706 */       return decode((byte[])pObject);
/*  707 */     if ((pObject instanceof String)) {
/*  708 */       return decode((String)pObject);
/*      */     }
/*  710 */     throw new DecoderException("Parameter supplied to Base64 decode is not a byte[] or a String");
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
/*      */   public byte[] decode(String pArray)
/*      */   {
/*  723 */     return decode(StringUtils.getBytesUtf8(pArray));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] decode(byte[] pArray)
/*      */   {
/*  734 */     reset();
/*  735 */     if ((pArray == null) || (pArray.length == 0)) {
/*  736 */       return pArray;
/*      */     }
/*  738 */     long len = pArray.length * 3 / 4;
/*  739 */     byte[] buf = new byte[(int)len];
/*  740 */     setInitialBuffer(buf, 0, buf.length);
/*  741 */     decode(pArray, 0, pArray.length);
/*  742 */     decode(pArray, 0, -1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  750 */     byte[] result = new byte[this.pos];
/*  751 */     readResults(result, 0, result.length);
/*  752 */     return result;
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
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked)
/*      */   {
/*  767 */     return encodeBase64(binaryData, isChunked, false);
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
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe)
/*      */   {
/*  785 */     return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize)
/*      */   {
/*  805 */     if ((binaryData == null) || (binaryData.length == 0)) {
/*  806 */       return binaryData;
/*      */     }
/*      */     
/*  809 */     long len = getEncodeLength(binaryData, 76, CHUNK_SEPARATOR);
/*  810 */     if (len > maxResultSize) {
/*  811 */       throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maxium size of " + maxResultSize);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  817 */     Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
/*  818 */     return b64.encode(binaryData);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decodeBase64(String base64String)
/*      */   {
/*  830 */     return new Base64().decode(base64String);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decodeBase64(byte[] base64Data)
/*      */   {
/*  841 */     return new Base64().decode(base64Data);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   static byte[] discardWhitespace(byte[] data)
/*      */   {
/*  853 */     byte[] groomedData = new byte[data.length];
/*  854 */     int bytesCopied = 0;
/*  855 */     for (int i = 0; i < data.length; i++) {
/*  856 */       switch (data[i]) {
/*      */       case 9: 
/*      */       case 10: 
/*      */       case 13: 
/*      */       case 32: 
/*      */         break;
/*      */       default: 
/*  863 */         groomedData[(bytesCopied++)] = data[i];
/*      */       }
/*      */     }
/*  866 */     byte[] packedData = new byte[bytesCopied];
/*  867 */     System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
/*  868 */     return packedData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isWhiteSpace(byte byteToCheck)
/*      */   {
/*  879 */     switch (byteToCheck) {
/*      */     case 9: 
/*      */     case 10: 
/*      */     case 13: 
/*      */     case 32: 
/*  884 */       return true;
/*      */     }
/*  886 */     return false;
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
/*      */   public Object encode(Object pObject)
/*      */     throws EncoderException
/*      */   {
/*  903 */     if (!(pObject instanceof byte[])) {
/*  904 */       throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
/*      */     }
/*  906 */     return encode((byte[])pObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String encodeToString(byte[] pArray)
/*      */   {
/*  918 */     return StringUtils.newStringUtf8(encode(pArray));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] encode(byte[] pArray)
/*      */   {
/*  929 */     reset();
/*  930 */     if ((pArray == null) || (pArray.length == 0)) {
/*  931 */       return pArray;
/*      */     }
/*  933 */     long len = getEncodeLength(pArray, this.lineLength, this.lineSeparator);
/*  934 */     byte[] buf = new byte[(int)len];
/*  935 */     setInitialBuffer(buf, 0, buf.length);
/*  936 */     encode(pArray, 0, pArray.length);
/*  937 */     encode(pArray, 0, -1);
/*      */     
/*  939 */     if (this.buffer != buf) {
/*  940 */       readResults(buf, 0, buf.length);
/*      */     }
/*      */     
/*      */ 
/*  944 */     if ((isUrlSafe()) && (this.pos < buf.length)) {
/*  945 */       byte[] smallerBuf = new byte[this.pos];
/*  946 */       System.arraycopy(buf, 0, smallerBuf, 0, this.pos);
/*  947 */       buf = smallerBuf;
/*      */     }
/*  949 */     return buf;
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
/*      */   private static long getEncodeLength(byte[] pArray, int chunkSize, byte[] chunkSeparator)
/*      */   {
/*  965 */     chunkSize = chunkSize / 4 * 4;
/*      */     
/*  967 */     long len = pArray.length * 4 / 3;
/*  968 */     long mod = len % 4L;
/*  969 */     if (mod != 0L) {
/*  970 */       len += 4L - mod;
/*      */     }
/*  972 */     if (chunkSize > 0) {
/*  973 */       boolean lenChunksPerfectly = len % chunkSize == 0L;
/*  974 */       len += len / chunkSize * chunkSeparator.length;
/*  975 */       if (!lenChunksPerfectly) {
/*  976 */         len += chunkSeparator.length;
/*      */       }
/*      */     }
/*  979 */     return len;
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
/*      */   public static BigInteger decodeInteger(byte[] pArray)
/*      */   {
/*  992 */     return new BigInteger(1, decodeBase64(pArray));
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
/*      */   public static byte[] encodeInteger(BigInteger bigInt)
/*      */   {
/* 1006 */     if (bigInt == null) {
/* 1007 */       throw new NullPointerException("encodeInteger called with null parameter");
/*      */     }
/* 1009 */     return encodeBase64(toIntegerBytes(bigInt), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte[] toIntegerBytes(BigInteger bigInt)
/*      */   {
/* 1020 */     int bitlen = bigInt.bitLength();
/*      */     
/* 1022 */     bitlen = bitlen + 7 >> 3 << 3;
/* 1023 */     byte[] bigBytes = bigInt.toByteArray();
/*      */     
/* 1025 */     if ((bigInt.bitLength() % 8 != 0) && (bigInt.bitLength() / 8 + 1 == bitlen / 8)) {
/* 1026 */       return bigBytes;
/*      */     }
/*      */     
/* 1029 */     int startSrc = 0;
/* 1030 */     int len = bigBytes.length;
/*      */     
/*      */ 
/* 1033 */     if (bigInt.bitLength() % 8 == 0) {
/* 1034 */       startSrc = 1;
/* 1035 */       len--;
/*      */     }
/* 1037 */     int startDst = bitlen / 8 - len;
/* 1038 */     byte[] resizedBytes = new byte[bitlen / 8];
/* 1039 */     System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
/* 1040 */     return resizedBytes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void reset()
/*      */   {
/* 1047 */     this.buffer = null;
/* 1048 */     this.pos = 0;
/* 1049 */     this.readPos = 0;
/* 1050 */     this.currentLinePos = 0;
/* 1051 */     this.modulus = 0;
/* 1052 */     this.eof = false;
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\binary\Base64.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */