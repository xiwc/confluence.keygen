/*    */ package com.jgoodies.looks.plastic.theme;
/*    */ 
/*    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*    */ import javax.swing.plaf.ColorUIResource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DesertBluer
/*    */   extends SkyBluer
/*    */ {
/* 46 */   private final ColorUIResource primary1 = new ColorUIResource(10, 36, 106);
/* 47 */   private final ColorUIResource primary2 = new ColorUIResource(85, 115, 170);
/* 48 */   private final ColorUIResource primary3 = new ColorUIResource(172, 210, 248);
/*    */   
/* 50 */   private final ColorUIResource secondary2 = new ColorUIResource(148, 144, 140);
/* 51 */   private final ColorUIResource secondary3 = new ColorUIResource(212, 208, 200);
/*    */   
/*    */   public String getName() {
/* 54 */     return "Desert Bluer";
/*    */   }
/*    */   
/* 57 */   protected ColorUIResource getPrimary1() { return this.primary1; }
/* 58 */   protected ColorUIResource getPrimary2() { return this.primary2; }
/* 59 */   protected ColorUIResource getPrimary3() { return this.primary3; }
/* 60 */   protected ColorUIResource getSecondary1() { return Colors.GRAY_MEDIUM; }
/* 61 */   protected ColorUIResource getSecondary2() { return this.secondary2; }
/* 62 */   protected ColorUIResource getSecondary3() { return this.secondary3; }
/*    */   
/* 64 */   public ColorUIResource getTextHighlightColor() { return getPrimary1(); }
/* 65 */   public ColorUIResource getHighlightedTextColor() { return getWhite(); }
/* 66 */   public ColorUIResource getMenuItemSelectedBackground() { return getPrimary1(); }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 69 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : super.getFocusColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\DesertBluer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */