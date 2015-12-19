/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import javax.swing.UIDefaults;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public class DesertYellow
/*  7:   */   extends DesertBlue
/*  8:   */ {
/*  9:   */   public String getName()
/* 10:   */   {
/* 11:48 */     return "Desert Yellow";
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected ColorUIResource getPrimary2()
/* 15:   */   {
/* 16:52 */     return Colors.YELLOW_LOW_MEDIUM;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ColorUIResource getPrimary3()
/* 20:   */   {
/* 21:56 */     return Colors.YELLOW_LOW_LIGHTEST;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ColorUIResource getTitleTextColor()
/* 25:   */   {
/* 26:60 */     return Colors.GRAY_DARKER;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 30:   */   {
/* 31:64 */     return Colors.YELLOW_LOW_MEDIUMDARK;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 35:   */   {
/* 36:68 */     super.addCustomEntriesToTable(table);
/* 37:69 */     Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.TRUE, "ScrollBar.maxBumpsWidth", new Integer(30) };
/* 38:   */     
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:76 */     table.putDefaults(uiDefaults);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.DesertYellow
 * JD-Core Version:    0.7.0.1
 */