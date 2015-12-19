/*   1:    */ package org.jdesktop.swingworker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.swing.SwingUtilities;
/*   8:    */ 
/*   9:    */ abstract class AccumulativeRunnable<T>
/*  10:    */   implements Runnable
/*  11:    */ {
/*  12: 76 */   private List<T> arguments = null;
/*  13:    */   
/*  14:    */   protected abstract void run(List<T> paramList);
/*  15:    */   
/*  16:    */   public final void run()
/*  17:    */   {
/*  18: 94 */     run(flush());
/*  19:    */   }
/*  20:    */   
/*  21:    */   public final synchronized void add(boolean isPrepend, T... args)
/*  22:    */   {
/*  23:107 */     boolean isSubmitted = true;
/*  24:108 */     if (this.arguments == null)
/*  25:    */     {
/*  26:109 */       isSubmitted = false;
/*  27:110 */       this.arguments = new ArrayList();
/*  28:    */     }
/*  29:112 */     if (isPrepend) {
/*  30:113 */       this.arguments.addAll(0, Arrays.asList(args));
/*  31:    */     } else {
/*  32:115 */       Collections.addAll(this.arguments, args);
/*  33:    */     }
/*  34:117 */     if (!isSubmitted) {
/*  35:118 */       submit();
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final void add(T... args)
/*  40:    */   {
/*  41:131 */     add(false, args);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void submit()
/*  45:    */   {
/*  46:144 */     SwingUtilities.invokeLater(this);
/*  47:    */   }
/*  48:    */   
/*  49:    */   private final synchronized List<T> flush()
/*  50:    */   {
/*  51:153 */     List<T> list = this.arguments;
/*  52:154 */     this.arguments = null;
/*  53:155 */     return list;
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.swingworker.AccumulativeRunnable
 * JD-Core Version:    0.7.0.1
 */