/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import javax.swing.UIDefaults;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSkyTheme
/*  7:   */   extends SkyBluer
/*  8:   */ {
/*  9:47 */   private static final ColorUIResource SECONDARY2 = new ColorUIResource(164, 164, 164);
/* 10:50 */   private static final ColorUIResource SECONDARY3 = new ColorUIResource(225, 225, 225);
/* 11:   */   
/* 12:   */   protected ColorUIResource getPrimary1()
/* 13:   */   {
/* 14:54 */     return Colors.GRAY_DARK;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected ColorUIResource getPrimary2()
/* 18:   */   {
/* 19:58 */     return Colors.BLUE_LOW_MEDIUM;
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected ColorUIResource getPrimary3()
/* 23:   */   {
/* 24:62 */     return Colors.BLUE_LOW_LIGHTEST;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected ColorUIResource getSecondary1()
/* 28:   */   {
/* 29:66 */     return Colors.GRAY_MEDIUM;
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected ColorUIResource getSecondary2()
/* 33:   */   {
/* 34:69 */     return SECONDARY2;
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected ColorUIResource getSecondary3()
/* 38:   */   {
/* 39:73 */     return SECONDARY3;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ColorUIResource getPrimaryControlShadow()
/* 43:   */   {
/* 44:78 */     return getPrimary3();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 48:   */   {
/* 49:82 */     return getPrimary1();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 53:   */   {
/* 54:86 */     super.addCustomEntriesToTable(table);
/* 55:87 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", null, "ScrollBar.thumbHighlight", getPrimaryControlHighlight() };
/* 56:   */     
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:94 */     table.putDefaults(uiDefaults);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.AbstractSkyTheme
 * JD-Core Version:    0.7.0.1
 */