/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import javax.swing.UIDefaults;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Plastic3DLookAndFeel
/*    */   extends PlasticLookAndFeel
/*    */ {
/*    */   public String getID()
/*    */   {
/* 51 */     return "JGoodies Plastic 3D";
/*    */   }
/*    */   
/*    */   public String getName() {
/* 55 */     return "JGoodies Plastic 3D";
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 59 */     return "The JGoodies Plastic 3D Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/*    */   }
/*    */   
/*    */   protected boolean is3DEnabled()
/*    */   {
/* 64 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void initComponentDefaults(UIDefaults table)
/*    */   {
/* 73 */     super.initComponentDefaults(table);
/*    */     
/* 75 */     Object menuBarBorder = PlasticBorders.getThinRaisedBorder();
/* 76 */     Object toolBarBorder = PlasticBorders.getThinRaisedBorder();
/*    */     
/* 78 */     Object[] defaults = { "MenuBar.border", menuBarBorder, "ToolBar.border", toolBarBorder };
/*    */     
/*    */ 
/*    */ 
/* 82 */     table.putDefaults(defaults);
/*    */   }
/*    */   
/*    */   protected static void installDefaultThemes() {}
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\Plastic3DLookAndFeel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */