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
/*    */ 
/*    */ public abstract class AbstractSkyTheme
/*    */   extends SkyBluer
/*    */ {
/* 47 */   private static final ColorUIResource SECONDARY2 = new ColorUIResource(164, 164, 164);
/*    */   
/*    */ 
/* 50 */   private static final ColorUIResource SECONDARY3 = new ColorUIResource(225, 225, 225);
/*    */   
/*    */   protected ColorUIResource getPrimary1()
/*    */   {
/* 54 */     return Colors.GRAY_DARK;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 58 */     return Colors.BLUE_LOW_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 62 */     return Colors.BLUE_LOW_LIGHTEST;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getSecondary1() {
/* 66 */     return Colors.GRAY_MEDIUM;
/*    */   }
/*    */   
/* 69 */   protected ColorUIResource getSecondary2() { return SECONDARY2; }
/*    */   
/*    */   protected ColorUIResource getSecondary3()
/*    */   {
/* 73 */     return SECONDARY3;
/*    */   }
/*    */   
/*    */   public ColorUIResource getPrimaryControlShadow()
/*    */   {
/* 78 */     return getPrimary3();
/*    */   }
/*    */   
/*    */   public ColorUIResource getMenuItemSelectedBackground() {
/* 82 */     return getPrimary1();
/*    */   }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 86 */     super.addCustomEntriesToTable(table);
/* 87 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", null, "ScrollBar.thumbHighlight", getPrimaryControlHighlight() };
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 94 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\AbstractSkyTheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */