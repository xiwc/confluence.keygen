/*  1:   */ package org.apache.commons.codec;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ 
/*  5:   */ public class StringEncoderComparator
/*  6:   */   implements Comparator
/*  7:   */ {
/*  8:   */   private final StringEncoder stringEncoder;
/*  9:   */   
/* 10:   */   /**
/* 11:   */    * @deprecated
/* 12:   */    */
/* 13:   */   public StringEncoderComparator()
/* 14:   */   {
/* 15:47 */     this.stringEncoder = null;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public StringEncoderComparator(StringEncoder stringEncoder)
/* 19:   */   {
/* 20:55 */     this.stringEncoder = stringEncoder;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int compare(Object o1, Object o2)
/* 24:   */   {
/* 25:73 */     int compareCode = 0;
/* 26:   */     try
/* 27:   */     {
/* 28:76 */       Comparable s1 = (Comparable)this.stringEncoder.encode(o1);
/* 29:77 */       Comparable s2 = (Comparable)this.stringEncoder.encode(o2);
/* 30:78 */       compareCode = s1.compareTo(s2);
/* 31:   */     }
/* 32:   */     catch (EncoderException ee)
/* 33:   */     {
/* 34:81 */       compareCode = 0;
/* 35:   */     }
/* 36:83 */     return compareCode;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.apache.commons.codec.StringEncoderComparator
 * JD-Core Version:    0.7.0.1
 */