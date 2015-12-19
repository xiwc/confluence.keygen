/*    */ package com.jgoodies.looks.plastic.theme;
/*    */ 
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
/*    */ public class SkyYellow
/*    */   extends AbstractSkyTheme
/*    */ {
/*    */   public String getName()
/*    */   {
/* 48 */     return "Sky Yellow";
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary1() {
/* 52 */     return Colors.GRAY_DARK;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 56 */     return Colors.YELLOW_LOW_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 60 */     return Colors.YELLOW_LOW_LIGHTEST;
/*    */   }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 64 */     return Colors.ORANGE_FOCUS;
/*    */   }
/*    */   
/*    */   public ColorUIResource getPrimaryControlShadow() {
/* 68 */     return getPrimary3();
/*    */   }
/*    */   
/*    */   public ColorUIResource getMenuItemSelectedBackground() {
/* 72 */     return Colors.YELLOW_LOW_MEDIUMDARK;
/*    */   }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 76 */     super.addCustomEntriesToTable(table);
/* 77 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/*    */     
/* 79 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\SkyYellow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */