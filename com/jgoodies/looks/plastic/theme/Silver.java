/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*  4:   */ import javax.swing.UIDefaults;
/*  5:   */ import javax.swing.plaf.ColorUIResource;
/*  6:   */ 
/*  7:   */ public class Silver
/*  8:   */   extends ExperienceBlue
/*  9:   */ {
/* 10:48 */   private static final ColorUIResource GRAY_LIGHT_LIGHTER = new ColorUIResource(190, 190, 190);
/* 11:   */   
/* 12:   */   public String getName()
/* 13:   */   {
/* 14:52 */     return "Silver";
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected ColorUIResource getPrimary1()
/* 18:   */   {
/* 19:56 */     return Colors.GRAY_MEDIUMDARK;
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected ColorUIResource getPrimary2()
/* 23:   */   {
/* 24:60 */     return Colors.GRAY_MEDIUMLIGHT;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected ColorUIResource getPrimary3()
/* 28:   */   {
/* 29:64 */     return GRAY_LIGHT_LIGHTER;
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected ColorUIResource getSecondary1()
/* 33:   */   {
/* 34:68 */     return Colors.GRAY_MEDIUM;
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected ColorUIResource getSecondary2()
/* 38:   */   {
/* 39:72 */     return getPrimary2();
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected ColorUIResource getSecondary3()
/* 43:   */   {
/* 44:76 */     return Colors.GRAY_LIGHTER2;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public ColorUIResource getFocusColor()
/* 48:   */   {
/* 49:80 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public ColorUIResource getTitleTextColor()
/* 53:   */   {
/* 54:86 */     return Colors.GRAY_DARKEST;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 58:   */   {
/* 59:90 */     super.addCustomEntriesToTable(table);
/* 60:91 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(50) };
/* 61:   */     
/* 62:93 */     table.putDefaults(uiDefaults);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.Silver
 * JD-Core Version:    0.7.0.1
 */