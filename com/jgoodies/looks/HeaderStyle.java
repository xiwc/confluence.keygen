/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JToolBar;
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
/*     */ public final class HeaderStyle
/*     */ {
/*  49 */   public static final HeaderStyle SINGLE = new HeaderStyle("Single");
/*  50 */   public static final HeaderStyle BOTH = new HeaderStyle("Both");
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private HeaderStyle(String name)
/*     */   {
/*  56 */     this.name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HeaderStyle from(JMenuBar menuBar)
/*     */   {
/*  68 */     return from0(menuBar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HeaderStyle from(JToolBar toolBar)
/*     */   {
/*  80 */     return from0(toolBar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static HeaderStyle from0(JComponent c)
/*     */   {
/*  92 */     Object value = c.getClientProperty("jgoodies.headerStyle");
/*  93 */     if ((value instanceof HeaderStyle)) {
/*  94 */       return (HeaderStyle)value;
/*     */     }
/*  96 */     if ((value instanceof String)) {
/*  97 */       return valueOf((String)value);
/*     */     }
/*     */     
/* 100 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static HeaderStyle valueOf(String name)
/*     */   {
/* 111 */     if (name.equalsIgnoreCase(SINGLE.name))
/* 112 */       return SINGLE;
/* 113 */     if (name.equalsIgnoreCase(BOTH.name)) {
/* 114 */       return BOTH;
/*     */     }
/* 116 */     throw new IllegalArgumentException("Invalid HeaderStyle name " + name);
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 122 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\HeaderStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */