/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*  4:   */ import javax.swing.UIDefaults;
/*  5:   */ import javax.swing.plaf.ColorUIResource;
/*  6:   */ 
/*  7:   */ public class DesertBlue
/*  8:   */   extends DesertBluer
/*  9:   */ {
/* 10:   */   public String getName()
/* 11:   */   {
/* 12:49 */     return "Desert Blue";
/* 13:   */   }
/* 14:   */   
/* 15:52 */   private static final ColorUIResource SECONDARY2 = new ColorUIResource(148, 144, 140);
/* 16:53 */   private static final ColorUIResource SECONDARY3 = new ColorUIResource(211, 210, 204);
/* 17:   */   
/* 18:   */   protected ColorUIResource getPrimary1()
/* 19:   */   {
/* 20:57 */     return Colors.GRAY_DARK;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected ColorUIResource getPrimary2()
/* 24:   */   {
/* 25:58 */     return Colors.BLUE_LOW_MEDIUM;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected ColorUIResource getPrimary3()
/* 29:   */   {
/* 30:59 */     return Colors.BLUE_LOW_LIGHTEST;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected ColorUIResource getSecondary1()
/* 34:   */   {
/* 35:61 */     return Colors.GRAY_MEDIUM;
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected ColorUIResource getSecondary2()
/* 39:   */   {
/* 40:62 */     return SECONDARY2;
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected ColorUIResource getSecondary3()
/* 44:   */   {
/* 45:63 */     return SECONDARY3;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public ColorUIResource getTitleTextColor()
/* 49:   */   {
/* 50:65 */     return Colors.BLUE_MEDIUM_DARKEST;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public ColorUIResource getFocusColor()
/* 54:   */   {
/* 55:68 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : Colors.BLUE_MEDIUM_DARK;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public ColorUIResource getPrimaryControlShadow()
/* 59:   */   {
/* 60:73 */     return getPrimary3();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 64:   */   {
/* 65:74 */     return getPrimary1();
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void addCustomEntriesToTable(UIDefaults table)
/* 69:   */   {
/* 70:77 */     super.addCustomEntriesToTable(table);
/* 71:78 */     Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.FALSE, "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", null };
/* 72:   */     
/* 73:   */ 
/* 74:   */ 
/* 75:   */ 
/* 76:83 */     table.putDefaults(uiDefaults);
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.DesertBlue
 * JD-Core Version:    0.7.0.1
 */