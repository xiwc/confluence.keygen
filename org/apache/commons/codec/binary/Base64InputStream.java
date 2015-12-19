/*   1:    */ package org.apache.commons.codec.binary;
/*   2:    */ 
/*   3:    */ import java.io.FilterInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ 
/*   7:    */ public class Base64InputStream
/*   8:    */   extends FilterInputStream
/*   9:    */ {
/*  10:    */   private final boolean doEncode;
/*  11:    */   private final Base64 base64;
/*  12: 52 */   private final byte[] singleByte = new byte[1];
/*  13:    */   
/*  14:    */   public Base64InputStream(InputStream in)
/*  15:    */   {
/*  16: 61 */     this(in, false);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Base64InputStream(InputStream in, boolean doEncode)
/*  20:    */   {
/*  21: 74 */     super(in);
/*  22: 75 */     this.doEncode = doEncode;
/*  23: 76 */     this.base64 = new Base64();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Base64InputStream(InputStream in, boolean doEncode, int lineLength, byte[] lineSeparator)
/*  27:    */   {
/*  28: 96 */     super(in);
/*  29: 97 */     this.doEncode = doEncode;
/*  30: 98 */     this.base64 = new Base64(lineLength, lineSeparator);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int read()
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:109 */     int r = read(this.singleByte, 0, 1);
/*  37:110 */     while (r == 0) {
/*  38:111 */       r = read(this.singleByte, 0, 1);
/*  39:    */     }
/*  40:113 */     if (r > 0) {
/*  41:114 */       return this.singleByte[0] < 0 ? 256 + this.singleByte[0] : this.singleByte[0];
/*  42:    */     }
/*  43:116 */     return -1;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int read(byte[] b, int offset, int len)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:139 */     if (b == null) {
/*  50:140 */       throw new NullPointerException();
/*  51:    */     }
/*  52:141 */     if ((offset < 0) || (len < 0)) {
/*  53:142 */       throw new IndexOutOfBoundsException();
/*  54:    */     }
/*  55:143 */     if ((offset > b.length) || (offset + len > b.length)) {
/*  56:144 */       throw new IndexOutOfBoundsException();
/*  57:    */     }
/*  58:145 */     if (len == 0) {
/*  59:146 */       return 0;
/*  60:    */     }
/*  61:148 */     if (!this.base64.hasData())
/*  62:    */     {
/*  63:149 */       byte[] buf = new byte[this.doEncode ? 4096 : 8192];
/*  64:150 */       int c = this.in.read(buf);
/*  65:153 */       if ((c > 0) && (b.length == len)) {
/*  66:154 */         this.base64.setInitialBuffer(b, offset, len);
/*  67:    */       }
/*  68:156 */       if (this.doEncode) {
/*  69:157 */         this.base64.encode(buf, 0, c);
/*  70:    */       } else {
/*  71:159 */         this.base64.decode(buf, 0, c);
/*  72:    */       }
/*  73:    */     }
/*  74:162 */     return this.base64.readResults(b, offset, len);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean markSupported()
/*  78:    */   {
/*  79:172 */     return false;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.Base64InputStream
 * JD-Core Version:    0.7.0.1
 */