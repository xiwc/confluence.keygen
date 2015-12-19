/*    */ package com.jgoodies.looks.plastic.theme;
/*    */ 
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
/*    */ public class ExperienceGreen
/*    */   extends ExperienceBlue
/*    */ {
/*    */   public String getName()
/*    */   {
/* 46 */     return "Experience Green";
/*    */   }
/*    */   
/* 49 */   private static final ColorUIResource FOCUS = new ColorUIResource(245, 165, 16);
/*    */   
/*    */   protected ColorUIResource getPrimary1()
/*    */   {
/* 53 */     return Colors.GREEN_LOW_DARK;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary2() {
/* 57 */     return Colors.GREEN_LOW_MEDIUM;
/*    */   }
/*    */   
/*    */   protected ColorUIResource getPrimary3() {
/* 61 */     return Colors.GREEN_LOW_LIGHTEST;
/*    */   }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 65 */     return FOCUS;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\ExperienceGreen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */