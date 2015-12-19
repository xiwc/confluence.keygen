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
/*    */ 
/*    */ 
/*    */ public class DarkStar
/*    */   extends InvertedColorTheme
/*    */ {
/* 46 */   private final ColorUIResource softWhite = new ColorUIResource(154, 154, 154);
/*    */   
/* 48 */   private final ColorUIResource primary1 = new ColorUIResource(83, 83, 61);
/* 49 */   private final ColorUIResource primary2 = new ColorUIResource(115, 107, 82);
/* 50 */   private final ColorUIResource primary3 = new ColorUIResource(156, 156, 123);
/*    */   
/* 52 */   private final ColorUIResource secondary1 = new ColorUIResource(32, 32, 32);
/* 53 */   private final ColorUIResource secondary2 = new ColorUIResource(96, 96, 96);
/* 54 */   private final ColorUIResource secondary3 = new ColorUIResource(84, 84, 84);
/*    */   
/*    */   public String getName() {
/* 57 */     return "Dark Star";
/*    */   }
/*    */   
/* 60 */   protected ColorUIResource getPrimary1() { return this.primary1; }
/* 61 */   protected ColorUIResource getPrimary2() { return this.primary2; }
/* 62 */   protected ColorUIResource getPrimary3() { return this.primary3; }
/* 63 */   protected ColorUIResource getSecondary1() { return this.secondary1; }
/* 64 */   protected ColorUIResource getSecondary2() { return this.secondary2; }
/* 65 */   protected ColorUIResource getSecondary3() { return this.secondary3; }
/* 66 */   protected ColorUIResource getSoftWhite() { return this.softWhite; }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\DarkStar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */