/*   1:    */ package com.jgoodies.looks.plastic.theme;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*   4:    */ import javax.swing.UIDefaults;
/*   5:    */ import javax.swing.plaf.ColorUIResource;
/*   6:    */ 
/*   7:    */ public class LightGray
/*   8:    */   extends ExperienceBlue
/*   9:    */ {
/*  10: 49 */   private static final ColorUIResource GRAY_VERY_LIGHT = new ColorUIResource(244, 244, 244);
/*  11:    */   
/*  12:    */   public String getName()
/*  13:    */   {
/*  14: 53 */     return "Light Gray";
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected ColorUIResource getPrimary1()
/*  18:    */   {
/*  19: 57 */     return new ColorUIResource(51, 153, 255);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected ColorUIResource getPrimary2()
/*  23:    */   {
/*  24: 61 */     return Colors.GRAY_MEDIUMLIGHT;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected ColorUIResource getPrimary3()
/*  28:    */   {
/*  29: 65 */     return new ColorUIResource(225, 240, 255);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected ColorUIResource getSecondary1()
/*  33:    */   {
/*  34: 69 */     return Colors.GRAY_MEDIUM;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected ColorUIResource getSecondary2()
/*  38:    */   {
/*  39: 73 */     return getPrimary2();
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected ColorUIResource getSecondary3()
/*  43:    */   {
/*  44: 77 */     return GRAY_VERY_LIGHT;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ColorUIResource getFocusColor()
/*  48:    */   {
/*  49: 81 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ColorUIResource getTitleTextColor()
/*  53:    */   {
/*  54: 87 */     return Colors.GRAY_DARKEST;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ColorUIResource getSimpleInternalFrameBackground()
/*  58:    */   {
/*  59: 91 */     return Colors.GRAY_MEDIUMDARK;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void addCustomEntriesToTable(UIDefaults table)
/*  63:    */   {
/*  64: 95 */     super.addCustomEntriesToTable(table);
/*  65: 96 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30), "TabbedPane.selected", getWhite(), "TabbedPane.selectHighlight", Colors.GRAY_MEDIUM };
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:101 */     table.putDefaults(uiDefaults);
/*  71:    */   }
/*  72:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.LightGray
 * JD-Core Version:    0.7.0.1
 */