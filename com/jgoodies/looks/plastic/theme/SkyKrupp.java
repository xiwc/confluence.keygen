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
/*    */ public class SkyKrupp
/*    */   extends AbstractSkyTheme
/*    */ {
/*    */   public String getName()
/*    */   {
/* 47 */     return "Sky Krupp";
/*    */   }
/*    */   
/* 50 */   private final ColorUIResource primary1 = new ColorUIResource(54, 54, 90);
/* 51 */   private final ColorUIResource primary2 = new ColorUIResource(156, 156, 178);
/* 52 */   private final ColorUIResource primary3 = new ColorUIResource(197, 197, 221);
/*    */   
/*    */   protected ColorUIResource getPrimary1() {
/* 55 */     return this.primary1;
/*    */   }
/*    */   
/* 58 */   protected ColorUIResource getPrimary2() { return this.primary2; }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 61 */     return this.primary3;
/*    */   }
/*    */   
/*    */   public ColorUIResource getMenuItemSelectedBackground() {
/* 65 */     return getPrimary1();
/*    */   }
/*    */   
/* 68 */   public ColorUIResource getMenuItemSelectedForeground() { return getWhite(); }
/*    */   
/*    */   public ColorUIResource getMenuSelectedBackground() {
/* 71 */     return getSecondary2();
/*    */   }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 75 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.GRAY_DARK;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\SkyKrupp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */