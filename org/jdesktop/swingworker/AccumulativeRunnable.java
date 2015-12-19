/*     */ package org.jdesktop.swingworker;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.SwingUtilities;
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
/*     */ abstract class AccumulativeRunnable<T>
/*     */   implements Runnable
/*     */ {
/*  76 */   private List<T> arguments = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void run(List<T> paramList);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void run()
/*     */   {
/*  94 */     run(flush());
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
/*     */   public final synchronized void add(boolean isPrepend, T... args)
/*     */   {
/* 107 */     boolean isSubmitted = true;
/* 108 */     if (this.arguments == null) {
/* 109 */       isSubmitted = false;
/* 110 */       this.arguments = new ArrayList();
/*     */     }
/* 112 */     if (isPrepend) {
/* 113 */       this.arguments.addAll(0, Arrays.asList(args));
/*     */     } else {
/* 115 */       Collections.addAll(this.arguments, args);
/*     */     }
/* 117 */     if (!isSubmitted) {
/* 118 */       submit();
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
/*     */   public final void add(T... args)
/*     */   {
/* 131 */     add(false, args);
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
/*     */   protected void submit()
/*     */   {
/* 144 */     SwingUtilities.invokeLater(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final synchronized List<T> flush()
/*     */   {
/* 153 */     List<T> list = this.arguments;
/* 154 */     this.arguments = null;
/* 155 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\swingworker\AccumulativeRunnable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */