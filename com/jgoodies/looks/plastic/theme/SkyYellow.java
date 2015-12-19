/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import javax.swing.UIDefaults;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public class SkyYellow
/*  7:   */   extends AbstractSkyTheme
/*  8:   */ {
/*  9:   */   public String getName()
/* 10:   */   {
/* 11:48 */     return "Sky Yellow";
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected ColorUIResource getPrimary1()
/* 15:   */   {
/* 16:52 */     return Colors.GRAY_DARK;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ColorUIResource getPrimary2()
/* 20:   */   {
/* 21:56 */     return Colors.YELLOW_LOW_MEDIUM;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected ColorUIResource getPrimary3()
/* 25:   */   {
/* 26:60 */     return Colors.YELLOW_LOW_LIGHTEST;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ColorUIResource getFocusColor()
/* 30:   */   {
/* 31:64 */     return Colors.ORANGE_FOCUS;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ColorUIResource getPrimaryControlShadow()
/* 35:   */   {
/* 36:68 */     return getPrimary3();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 40:   */   {
/* 41:72 */     return Colors.YELLOW_LOW_MEDIUMDARK;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 45:   */   {
/* 46:76 */     super.addCustomEntriesToTable(table);
/* 47:77 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/* 48:   */     
/* 49:79 */     table.putDefaults(uiDefaults);
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.SkyYellow
 * JD-Core Version:    0.7.0.1
 */