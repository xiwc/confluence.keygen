/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public class SkyKrupp
/*  7:   */   extends AbstractSkyTheme
/*  8:   */ {
/*  9:   */   public String getName()
/* 10:   */   {
/* 11:47 */     return "Sky Krupp";
/* 12:   */   }
/* 13:   */   
/* 14:50 */   private final ColorUIResource primary1 = new ColorUIResource(54, 54, 90);
/* 15:51 */   private final ColorUIResource primary2 = new ColorUIResource(156, 156, 178);
/* 16:52 */   private final ColorUIResource primary3 = new ColorUIResource(197, 197, 221);
/* 17:   */   
/* 18:   */   protected ColorUIResource getPrimary1()
/* 19:   */   {
/* 20:55 */     return this.primary1;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected ColorUIResource getPrimary2()
/* 24:   */   {
/* 25:58 */     return this.primary2;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected ColorUIResource getPrimary3()
/* 29:   */   {
/* 30:61 */     return this.primary3;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 34:   */   {
/* 35:65 */     return getPrimary1();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public ColorUIResource getMenuItemSelectedForeground()
/* 39:   */   {
/* 40:68 */     return getWhite();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public ColorUIResource getMenuSelectedBackground()
/* 44:   */   {
/* 45:71 */     return getSecondary2();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public ColorUIResource getFocusColor()
/* 49:   */   {
/* 50:75 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.GRAY_DARK;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.SkyKrupp
 * JD-Core Version:    0.7.0.1
 */