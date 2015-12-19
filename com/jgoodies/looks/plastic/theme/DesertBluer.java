/*  1:   */ package com.jgoodies.looks.plastic.theme;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*  4:   */ import javax.swing.plaf.ColorUIResource;
/*  5:   */ 
/*  6:   */ public class DesertBluer
/*  7:   */   extends SkyBluer
/*  8:   */ {
/*  9:46 */   private final ColorUIResource primary1 = new ColorUIResource(10, 36, 106);
/* 10:47 */   private final ColorUIResource primary2 = new ColorUIResource(85, 115, 170);
/* 11:48 */   private final ColorUIResource primary3 = new ColorUIResource(172, 210, 248);
/* 12:50 */   private final ColorUIResource secondary2 = new ColorUIResource(148, 144, 140);
/* 13:51 */   private final ColorUIResource secondary3 = new ColorUIResource(212, 208, 200);
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:54 */     return "Desert Bluer";
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected ColorUIResource getPrimary1()
/* 21:   */   {
/* 22:57 */     return this.primary1;
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected ColorUIResource getPrimary2()
/* 26:   */   {
/* 27:58 */     return this.primary2;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected ColorUIResource getPrimary3()
/* 31:   */   {
/* 32:59 */     return this.primary3;
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ColorUIResource getSecondary1()
/* 36:   */   {
/* 37:60 */     return Colors.GRAY_MEDIUM;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected ColorUIResource getSecondary2()
/* 41:   */   {
/* 42:61 */     return this.secondary2;
/* 43:   */   }
/* 44:   */   
/* 45:   */   protected ColorUIResource getSecondary3()
/* 46:   */   {
/* 47:62 */     return this.secondary3;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public ColorUIResource getTextHighlightColor()
/* 51:   */   {
/* 52:64 */     return getPrimary1();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public ColorUIResource getHighlightedTextColor()
/* 56:   */   {
/* 57:65 */     return getWhite();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public ColorUIResource getMenuItemSelectedBackground()
/* 61:   */   {
/* 62:66 */     return getPrimary1();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public ColorUIResource getFocusColor()
/* 66:   */   {
/* 67:69 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : super.getFocusColor();
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.DesertBluer
 * JD-Core Version:    0.7.0.1
 */