/*   1:    */ package com.jgoodies.looks.plastic.theme;
/*   2:    */ 
/*   3:    */ import javax.swing.UIDefaults;
/*   4:    */ import javax.swing.plaf.ColorUIResource;
/*   5:    */ 
/*   6:    */ public class ExperienceBlue
/*   7:    */   extends DesertBluer
/*   8:    */ {
/*   9:    */   public String getName()
/*  10:    */   {
/*  11: 50 */     return "Experience Blue";
/*  12:    */   }
/*  13:    */   
/*  14: 53 */   protected static final ColorUIResource LUNA_BACKGROUND = new ColorUIResource(236, 233, 216);
/*  15: 56 */   protected static final ColorUIResource LUNA_BACKGROUND_DARKER = new ColorUIResource(189, 190, 176);
/*  16: 60 */   private static final ColorUIResource SECONDARY1 = Colors.GRAY_MEDIUM;
/*  17: 63 */   private static final ColorUIResource SECONDARY2 = LUNA_BACKGROUND_DARKER;
/*  18: 66 */   private static final ColorUIResource SECONDARY3 = LUNA_BACKGROUND;
/*  19:    */   
/*  20:    */   protected ColorUIResource getPrimary1()
/*  21:    */   {
/*  22: 70 */     return Colors.BLUE_MEDIUM_DARK;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected ColorUIResource getPrimary2()
/*  26:    */   {
/*  27: 74 */     return Colors.BLUE_LOW_MEDIUM;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected ColorUIResource getPrimary3()
/*  31:    */   {
/*  32: 78 */     return Colors.BLUE_LOW_LIGHTEST;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected ColorUIResource getSecondary1()
/*  36:    */   {
/*  37: 82 */     return SECONDARY1;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected ColorUIResource getSecondary2()
/*  41:    */   {
/*  42: 86 */     return SECONDARY2;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected ColorUIResource getSecondary3()
/*  46:    */   {
/*  47: 90 */     return SECONDARY3;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ColorUIResource getFocusColor()
/*  51:    */   {
/*  52: 94 */     return Colors.ORANGE_FOCUS;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ColorUIResource getPrimaryControlShadow()
/*  56:    */   {
/*  57: 98 */     return getPrimary3();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ColorUIResource getMenuSelectedBackground()
/*  61:    */   {
/*  62:102 */     return getPrimary1();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ColorUIResource getMenuSelectedForeground()
/*  66:    */   {
/*  67:105 */     return WHITE;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ColorUIResource getMenuItemBackground()
/*  71:    */   {
/*  72:109 */     return WHITE;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public ColorUIResource getToggleButtonCheckColor()
/*  76:    */   {
/*  77:113 */     return Colors.GREEN_CHECK;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void addCustomEntriesToTable(UIDefaults table)
/*  81:    */   {
/*  82:117 */     super.addCustomEntriesToTable(table);
/*  83:118 */     Object[] uiDefaults = { "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", new Integer(22) };
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:125 */     table.putDefaults(uiDefaults);
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.ExperienceBlue
 * JD-Core Version:    0.7.0.1
 */