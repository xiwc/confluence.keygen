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
/*    */ public class DesertYellow
/*    */   extends DesertBlue
/*    */ {
/*    */   public String getName()
/*    */   {
/* 48 */     return "Desert Yellow";
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 52 */     return Colors.YELLOW_LOW_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 56 */     return Colors.YELLOW_LOW_LIGHTEST;
/*    */   }
/*    */   
/*    */   public ColorUIResource getTitleTextColor() {
/* 60 */     return Colors.GRAY_DARKER;
/*    */   }
/*    */   
/*    */   public ColorUIResource getMenuItemSelectedBackground() {
/* 64 */     return Colors.YELLOW_LOW_MEDIUMDARK;
/*    */   }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 68 */     super.addCustomEntriesToTable(table);
/* 69 */     Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.TRUE, "ScrollBar.maxBumpsWidth", new Integer(30) };
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 76 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\DesertYellow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */