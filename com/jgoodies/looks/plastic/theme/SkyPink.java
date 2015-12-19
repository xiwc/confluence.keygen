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
/*    */ public class SkyPink
/*    */   extends AbstractSkyTheme
/*    */ {
/*    */   public String getName()
/*    */   {
/* 47 */     return "Sky Pink";
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary1() {
/* 51 */     return Colors.PINK_LOW_DARK;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 55 */     return Colors.PINK_LOW_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 59 */     return Colors.PINK_LOW_LIGHTER;
/*    */   }
/*    */   
/*    */   public ColorUIResource getHighlightedTextColor() {
/* 63 */     return getControlTextColor();
/*    */   }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 67 */     super.addCustomEntriesToTable(table);
/* 68 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/*    */     
/* 70 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\SkyPink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */