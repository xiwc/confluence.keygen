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
/*     */ public final class BorderStyle
/*     */ {
/*  48 */   public static final BorderStyle EMPTY = new BorderStyle("Empty");
/*  49 */   public static final BorderStyle SEPARATOR = new BorderStyle("Separator");
/*  50 */   public static final BorderStyle ETCHED = new BorderStyle("Etched");
/*     */   
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */   private BorderStyle(String name)
/*     */   {
/*  58 */     this.name = name;
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
/*     */   public static BorderStyle from(JToolBar toolBar, String clientPropertyKey)
/*     */   {
/*  72 */     return from0(toolBar, clientPropertyKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BorderStyle from(JMenuBar menuBar, String clientPropertyKey)
/*     */   {
/*  83 */     return from0(menuBar, clientPropertyKey);
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
/*     */   private static BorderStyle from0(JComponent c, String clientPropertyKey)
/*     */   {
/*  96 */     Object value = c.getClientProperty(clientPropertyKey);
/*  97 */     if ((value instanceof BorderStyle)) {
/*  98 */       return (BorderStyle)value;
/*     */     }
/* 100 */     if ((value instanceof String)) {
/* 101 */       return valueOf((String)value);
/*     */     }
/*     */     
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   private static BorderStyle valueOf(String name) {
/* 108 */     if (name.equalsIgnoreCase(EMPTY.name))
/* 109 */       return EMPTY;
/* 110 */     if (name.equalsIgnoreCase(SEPARATOR.name))
/* 111 */       return SEPARATOR;
/* 112 */     if (name.equalsIgnoreCase(ETCHED.name)) {
/* 113 */       return ETCHED;
/*     */     }
/* 115 */     throw new IllegalArgumentException("Invalid BorderStyle name " + name);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 120 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\BorderStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */