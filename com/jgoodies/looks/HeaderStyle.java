/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import javax.swing.JComponent;
/*   4:    */ import javax.swing.JMenuBar;
/*   5:    */ import javax.swing.JToolBar;
/*   6:    */ 
/*   7:    */ public final class HeaderStyle
/*   8:    */ {
/*   9: 49 */   public static final HeaderStyle SINGLE = new HeaderStyle("Single");
/*  10: 50 */   public static final HeaderStyle BOTH = new HeaderStyle("Both");
/*  11:    */   private final String name;
/*  12:    */   
/*  13:    */   private HeaderStyle(String name)
/*  14:    */   {
/*  15: 56 */     this.name = name;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static HeaderStyle from(JMenuBar menuBar)
/*  19:    */   {
/*  20: 68 */     return from0(menuBar);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static HeaderStyle from(JToolBar toolBar)
/*  24:    */   {
/*  25: 80 */     return from0(toolBar);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private static HeaderStyle from0(JComponent c)
/*  29:    */   {
/*  30: 92 */     Object value = c.getClientProperty("jgoodies.headerStyle");
/*  31: 93 */     if ((value instanceof HeaderStyle)) {
/*  32: 94 */       return (HeaderStyle)value;
/*  33:    */     }
/*  34: 96 */     if ((value instanceof String)) {
/*  35: 97 */       return valueOf((String)value);
/*  36:    */     }
/*  37:100 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private static HeaderStyle valueOf(String name)
/*  41:    */   {
/*  42:111 */     if (name.equalsIgnoreCase(SINGLE.name)) {
/*  43:112 */       return SINGLE;
/*  44:    */     }
/*  45:113 */     if (name.equalsIgnoreCase(BOTH.name)) {
/*  46:114 */       return BOTH;
/*  47:    */     }
/*  48:116 */     throw new IllegalArgumentException("Invalid HeaderStyle name " + name);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53:122 */     return this.name;
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.HeaderStyle
 * JD-Core Version:    0.7.0.1
 */