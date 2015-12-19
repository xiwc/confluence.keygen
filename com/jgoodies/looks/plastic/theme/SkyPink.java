/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import javax.swing.UIDefaults;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public class SkyPink
/*  7:   */   extends AbstractSkyTheme
/*  8:   */ {
/*  9:   */   public String getName()
/* 10:   */   {
/* 11:47 */     return "Sky Pink";
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected ColorUIResource getPrimary1()
/* 15:   */   {
/* 16:51 */     return Colors.PINK_LOW_DARK;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ColorUIResource getPrimary2()
/* 20:   */   {
/* 21:55 */     return Colors.PINK_LOW_MEDIUM;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected ColorUIResource getPrimary3()
/* 25:   */   {
/* 26:59 */     return Colors.PINK_LOW_LIGHTER;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ColorUIResource getHighlightedTextColor()
/* 30:   */   {
/* 31:63 */     return getControlTextColor();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 35:   */   {
/* 36:67 */     super.addCustomEntriesToTable(table);
/* 37:68 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/* 38:   */     
/* 39:70 */     table.putDefaults(uiDefaults);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.SkyPink
 * JD-Core Version:    0.7.0.1
 */