/*    */ package com.jgoodies.looks.plastic.theme;
/*    */ 
/*    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*    */ import javax.swing.UIDefaults;
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
/*    */ 
/*    */ public class Silver
/*    */   extends ExperienceBlue
/*    */ {
/* 48 */   private static final ColorUIResource GRAY_LIGHT_LIGHTER = new ColorUIResource(190, 190, 190);
/*    */   
/*    */   public String getName()
/*    */   {
/* 52 */     return "Silver";
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary1() {
/* 56 */     return Colors.GRAY_MEDIUMDARK;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 60 */     return Colors.GRAY_MEDIUMLIGHT;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 64 */     return GRAY_LIGHT_LIGHTER;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getSecondary1() {
/* 68 */     return Colors.GRAY_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getSecondary2() {
/* 72 */     return getPrimary2();
/*    */   }
/*    */   
/*    */   protected ColorUIResource getSecondary3() {
/* 76 */     return Colors.GRAY_LIGHTER2;
/*    */   }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 80 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
/*    */   }
/*    */   
/*    */ 
/*    */   public ColorUIResource getTitleTextColor()
/*    */   {
/* 86 */     return Colors.GRAY_DARKEST;
/*    */   }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 90 */     super.addCustomEntriesToTable(table);
/* 91 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(50) };
/*    */     
/* 93 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\Silver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */