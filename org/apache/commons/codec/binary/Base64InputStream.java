/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class Base64InputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private final boolean doEncode;
/*     */   private final Base64 base64;
/*  52 */   private final byte[] singleByte = new byte[1];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64InputStream(InputStream in)
/*     */   {
/*  61 */     this(in, false);
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
/*     */   public Base64InputStream(InputStream in, boolean doEncode)
/*     */   {
/*  74 */     super(in);
/*  75 */     this.doEncode = doEncode;
/*  76 */     this.base64 = new Base64();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64InputStream(InputStream in, boolean doEncode, int lineLength, byte[] lineSeparator)
/*     */   {
/*  96 */     super(in);
/*  97 */     this.doEncode = doEncode;
/*  98 */     this.base64 = new Base64(lineLength, lineSeparator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 109 */     int r = read(this.singleByte, 0, 1);
/* 110 */     while (r == 0) {
/* 111 */       r = read(this.singleByte, 0, 1);
/*     */     }
/* 113 */     if (r > 0) {
/* 114 */       return this.singleByte[0] < 0 ? 256 + this.singleByte[0] : this.singleByte[0];
/*     */     }
/* 116 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] b, int offset, int len)
/*     */     throws IOException
/*     */   {
/* 139 */     if (b == null)
/* 140 */       throw new NullPointerException();
/* 141 */     if ((offset < 0) || (len < 0))
/* 142 */       throw new IndexOutOfBoundsException();
/* 143 */     if ((offset > b.length) || (offset + len > b.length))
/* 144 */       throw new IndexOutOfBoundsException();
/* 145 */     if (len == 0) {
/* 146 */       return 0;
/*     */     }
/* 148 */     if (!this.base64.hasData()) {
/* 149 */       byte[] buf = new byte[this.doEncode ? 'က' : ' '];
/* 150 */       int c = this.in.read(buf);
/*     */       
/*     */ 
/* 153 */       if ((c > 0) && (b.length == len)) {
/* 154 */         this.base64.setInitialBuffer(b, offset, len);
/*     */       }
/* 156 */       if (this.doEncode) {
/* 157 */         this.base64.encode(buf, 0, c);
/*     */       } else {
/* 159 */         this.base64.decode(buf, 0, c);
/*     */       }
/*     */     }
/* 162 */     return this.base64.readResults(b, offset, len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 172 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\binary\Base64InputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */