/*   1:    */ package org.apache.commons.codec.binary;
/*   2:    */ 
/*   3:    */ import java.io.FilterOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class Base64OutputStream
/*   8:    */   extends FilterOutputStream
/*   9:    */ {
/*  10:    */   private final boolean doEncode;
/*  11:    */   private final Base64 base64;
/*  12: 51 */   private final byte[] singleByte = new byte[1];
/*  13:    */   
/*  14:    */   public Base64OutputStream(OutputStream out)
/*  15:    */   {
/*  16: 60 */     this(out, true);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Base64OutputStream(OutputStream out, boolean doEncode)
/*  20:    */   {
/*  21: 73 */     super(out);
/*  22: 74 */     this.doEncode = doEncode;
/*  23: 75 */     this.base64 = new Base64();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Base64OutputStream(OutputStream out, boolean doEncode, int lineLength, byte[] lineSeparator)
/*  27:    */   {
/*  28: 95 */     super(out);
/*  29: 96 */     this.doEncode = doEncode;
/*  30: 97 */     this.base64 = new Base64(lineLength, lineSeparator);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void write(int i)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:109 */     this.singleByte[0] = ((byte)i);
/*  37:110 */     write(this.singleByte, 0, 1);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void write(byte[] b, int offset, int len)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:132 */     if (b == null) {
/*  44:133 */       throw new NullPointerException();
/*  45:    */     }
/*  46:134 */     if ((offset < 0) || (len < 0)) {
/*  47:135 */       throw new IndexOutOfBoundsException();
/*  48:    */     }
/*  49:136 */     if ((offset > b.length) || (offset + len > b.length)) {
/*  50:137 */       throw new IndexOutOfBoundsException();
/*  51:    */     }
/*  52:138 */     if (len > 0)
/*  53:    */     {
/*  54:139 */       if (this.doEncode) {
/*  55:140 */         this.base64.encode(b, offset, len);
/*  56:    */       } else {
/*  57:142 */         this.base64.decode(b, offset, len);
/*  58:    */       }
/*  59:144 */       flush(false);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void flush(boolean propogate)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66:158 */     int avail = this.base64.avail();
/*  67:159 */     if (avail > 0)
/*  68:    */     {
/*  69:160 */       byte[] buf = new byte[avail];
/*  70:161 */       int c = this.base64.readResults(buf, 0, avail);
/*  71:162 */       if (c > 0) {
/*  72:163 */         this.out.write(buf, 0, c);
/*  73:    */       }
/*  74:    */     }
/*  75:166 */     if (propogate) {
/*  76:167 */       this.out.flush();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void flush()
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:178 */     flush(true);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void close()
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:189 */     if (this.doEncode) {
/*  90:190 */       this.base64.encode(this.singleByte, 0, -1);
/*  91:    */     } else {
/*  92:192 */       this.base64.decode(this.singleByte, 0, -1);
/*  93:    */     }
/*  94:194 */     flush();
/*  95:195 */     this.out.close();
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.binary.Base64OutputStream
 * JD-Core Version:    0.7.0.1
 */