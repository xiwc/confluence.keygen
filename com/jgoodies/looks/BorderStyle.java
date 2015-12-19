/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import javax.swing.JComponent;
/*   4:    */ import javax.swing.JMenuBar;
/*   5:    */ import javax.swing.JToolBar;
/*   6:    */ 
/*   7:    */ public final class BorderStyle
/*   8:    */ {
/*   9: 48 */   public static final BorderStyle EMPTY = new BorderStyle("Empty");
/*  10: 49 */   public static final BorderStyle SEPARATOR = new BorderStyle("Separator");
/*  11: 50 */   public static final BorderStyle ETCHED = new BorderStyle("Etched");
/*  12:    */   private final String name;
/*  13:    */   
/*  14:    */   private BorderStyle(String name)
/*  15:    */   {
/*  16: 58 */     this.name = name;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static BorderStyle from(JToolBar toolBar, String clientPropertyKey)
/*  20:    */   {
/*  21: 72 */     return from0(toolBar, clientPropertyKey);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static BorderStyle from(JMenuBar menuBar, String clientPropertyKey)
/*  25:    */   {
/*  26: 83 */     return from0(menuBar, clientPropertyKey);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static BorderStyle from0(JComponent c, String clientPropertyKey)
/*  30:    */   {
/*  31: 96 */     Object value = c.getClientProperty(clientPropertyKey);
/*  32: 97 */     if ((value instanceof BorderStyle)) {
/*  33: 98 */       return (BorderStyle)value;
/*  34:    */     }
/*  35:100 */     if ((value instanceof String)) {
/*  36:101 */       return valueOf((String)value);
/*  37:    */     }
/*  38:104 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private static BorderStyle valueOf(String name)
/*  42:    */   {
/*  43:108 */     if (name.equalsIgnoreCase(EMPTY.name)) {
/*  44:109 */       return EMPTY;
/*  45:    */     }
/*  46:110 */     if (name.equalsIgnoreCase(SEPARATOR.name)) {
/*  47:111 */       return SEPARATOR;
/*  48:    */     }
/*  49:112 */     if (name.equalsIgnoreCase(ETCHED.name)) {
/*  50:113 */       return ETCHED;
/*  51:    */     }
/*  52:115 */     throw new IllegalArgumentException("Invalid BorderStyle name " + name);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57:120 */     return this.name;
/*  58:    */   }
/*  59:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.BorderStyle
 * JD-Core Version:    0.7.0.1
 */