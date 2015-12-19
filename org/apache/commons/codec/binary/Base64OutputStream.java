/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class Base64OutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private final boolean doEncode;
/*     */   private final Base64 base64;
/*  51 */   private final byte[] singleByte = new byte[1];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base64OutputStream(OutputStream out)
/*     */   {
/*  60 */     this(out, true);
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
/*     */   public Base64OutputStream(OutputStream out, boolean doEncode)
/*     */   {
/*  73 */     super(out);
/*  74 */     this.doEncode = doEncode;
/*  75 */     this.base64 = new Base64();
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
/*     */   public Base64OutputStream(OutputStream out, boolean doEncode, int lineLength, byte[] lineSeparator)
/*     */   {
/*  95 */     super(out);
/*  96 */     this.doEncode = doEncode;
/*  97 */     this.base64 = new Base64(lineLength, lineSeparator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int i)
/*     */     throws IOException
/*     */   {
/* 109 */     this.singleByte[0] = ((byte)i);
/* 110 */     write(this.singleByte, 0, 1);
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
/*     */   public void write(byte[] b, int offset, int len)
/*     */     throws IOException
/*     */   {
/* 132 */     if (b == null)
/* 133 */       throw new NullPointerException();
/* 134 */     if ((offset < 0) || (len < 0))
/* 135 */       throw new IndexOutOfBoundsException();
/* 136 */     if ((offset > b.length) || (offset + len > b.length))
/* 137 */       throw new IndexOutOfBoundsException();
/* 138 */     if (len > 0) {
/* 139 */       if (this.doEncode) {
/* 140 */         this.base64.encode(b, offset, len);
/*     */       } else {
/* 142 */         this.base64.decode(b, offset, len);
/*     */       }
/* 144 */       flush(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void flush(boolean propogate)
/*     */     throws IOException
/*     */   {
/* 158 */     int avail = this.base64.avail();
/* 159 */     if (avail > 0) {
/* 160 */       byte[] buf = new byte[avail];
/* 161 */       int c = this.base64.readResults(buf, 0, avail);
/* 162 */       if (c > 0) {
/* 163 */         this.out.write(buf, 0, c);
/*     */       }
/*     */     }
/* 166 */     if (propogate) {
/* 167 */       this.out.flush();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 178 */     flush(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 189 */     if (this.doEncode) {
/* 190 */       this.base64.encode(this.singleByte, 0, -1);
/*     */     } else {
/* 192 */       this.base64.decode(this.singleByte, 0, -1);
/*     */     }
/* 194 */     flush();
/* 195 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\apache\commons\codec\binary\Base64OutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */