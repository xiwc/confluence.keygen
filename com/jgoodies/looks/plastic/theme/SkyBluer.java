/*   1:    */ package com.jgoodies.looks.plastic.theme;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*   4:    */ import com.jgoodies.looks.plastic.PlasticTheme;
/*   5:    */ import javax.swing.UIDefaults;
/*   6:    */ import javax.swing.plaf.ColorUIResource;
/*   7:    */ 
/*   8:    */ public class SkyBluer
/*   9:    */   extends PlasticTheme
/*  10:    */ {
/*  11:    */   public String getName()
/*  12:    */   {
/*  13: 49 */     return "Sky Bluer";
/*  14:    */   }
/*  15:    */   
/*  16:    */   protected ColorUIResource getPrimary1()
/*  17:    */   {
/*  18: 53 */     return Colors.BLUE_MEDIUM_DARKEST;
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected ColorUIResource getPrimary2()
/*  22:    */   {
/*  23: 57 */     return Colors.BLUE_MEDIUM_MEDIUM;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected ColorUIResource getPrimary3()
/*  27:    */   {
/*  28: 61 */     return Colors.BLUE_MEDIUM_LIGHTEST;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected ColorUIResource getSecondary1()
/*  32:    */   {
/*  33: 65 */     return Colors.GRAY_MEDIUMDARK;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected ColorUIResource getSecondary2()
/*  37:    */   {
/*  38: 69 */     return Colors.GRAY_LIGHT;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected ColorUIResource getSecondary3()
/*  42:    */   {
/*  43: 73 */     return Colors.GRAY_LIGHTER;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ColorUIResource getMenuItemSelectedBackground()
/*  47:    */   {
/*  48: 77 */     return getPrimary2();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ColorUIResource getMenuItemSelectedForeground()
/*  52:    */   {
/*  53: 81 */     return getWhite();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ColorUIResource getMenuSelectedBackground()
/*  57:    */   {
/*  58: 85 */     return getSecondary2();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ColorUIResource getFocusColor()
/*  62:    */   {
/*  63: 89 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : super.getFocusColor();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void addCustomEntriesToTable(UIDefaults table)
/*  67:    */   {
/*  68:102 */     super.addCustomEntriesToTable(table);
/*  69:103 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/*  70:    */     
/*  71:105 */     table.putDefaults(uiDefaults);
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.SkyBluer
 * JD-Core Version:    0.7.0.1
 */