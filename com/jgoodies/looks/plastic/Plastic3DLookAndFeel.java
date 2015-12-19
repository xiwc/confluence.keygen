/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import javax.swing.UIDefaults;
/*  4:   */ 
/*  5:   */ public class Plastic3DLookAndFeel
/*  6:   */   extends PlasticLookAndFeel
/*  7:   */ {
/*  8:   */   public String getID()
/*  9:   */   {
/* 10:51 */     return "JGoodies Plastic 3D";
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String getName()
/* 14:   */   {
/* 15:55 */     return "JGoodies Plastic 3D";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getDescription()
/* 19:   */   {
/* 20:59 */     return "The JGoodies Plastic 3D Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected boolean is3DEnabled()
/* 24:   */   {
/* 25:64 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected void initComponentDefaults(UIDefaults table)
/* 29:   */   {
/* 30:73 */     super.initComponentDefaults(table);
/* 31:   */     
/* 32:75 */     Object menuBarBorder = PlasticBorders.getThinRaisedBorder();
/* 33:76 */     Object toolBarBorder = PlasticBorders.getThinRaisedBorder();
/* 34:   */     
/* 35:78 */     Object[] defaults = { "MenuBar.border", menuBarBorder, "ToolBar.border", toolBarBorder };
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:82 */     table.putDefaults(defaults);
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected static void installDefaultThemes() {}
/* 43:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.Plastic3DLookAndFeel
 * JD-Core Version:    0.7.0.1
 */