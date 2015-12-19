/*   1:    */ package com.jgoodies.looks.plastic.theme;
/*   2:    */ 
/*   3:    */ import javax.swing.UIDefaults;
/*   4:    */ import javax.swing.plaf.ColorUIResource;
/*   5:    */ 
/*   6:    */ public class ExperienceRoyale
/*   7:    */   extends DesertBluer
/*   8:    */ {
/*   9:    */   public String getName()
/*  10:    */   {
/*  11: 51 */     return "Experience Royale";
/*  12:    */   }
/*  13:    */   
/*  14: 54 */   protected static final ColorUIResource ROYALE_BACKGROUND = new ColorUIResource(235, 233, 237);
/*  15: 57 */   protected static final ColorUIResource ROYALE_BACKGROUND_DARKER = new ColorUIResource(167, 166, 170);
/*  16: 60 */   protected static final ColorUIResource ROYALE_SELECTION = new ColorUIResource(51, 94, 168);
/*  17: 63 */   private static final ColorUIResource SECONDARY1 = Colors.GRAY_MEDIUM;
/*  18: 66 */   private static final ColorUIResource SECONDARY2 = ROYALE_BACKGROUND_DARKER;
/*  19: 69 */   private static final ColorUIResource SECONDARY3 = ROYALE_BACKGROUND;
/*  20:    */   
/*  21:    */   protected ColorUIResource getPrimary1()
/*  22:    */   {
/*  23: 73 */     return ROYALE_SELECTION;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected ColorUIResource getPrimary2()
/*  27:    */   {
/*  28: 77 */     return Colors.BLUE_LOW_MEDIUM;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected ColorUIResource getPrimary3()
/*  32:    */   {
/*  33: 81 */     return Colors.BLUE_LOW_LIGHTEST;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected ColorUIResource getSecondary1()
/*  37:    */   {
/*  38: 85 */     return SECONDARY1;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected ColorUIResource getSecondary2()
/*  42:    */   {
/*  43: 89 */     return SECONDARY2;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected ColorUIResource getSecondary3()
/*  47:    */   {
/*  48: 93 */     return SECONDARY3;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ColorUIResource getFocusColor()
/*  52:    */   {
/*  53: 97 */     return Colors.ORANGE_FOCUS;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ColorUIResource getPrimaryControlShadow()
/*  57:    */   {
/*  58:101 */     return getPrimary3();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ColorUIResource getMenuSelectedBackground()
/*  62:    */   {
/*  63:105 */     return getPrimary1();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ColorUIResource getMenuSelectedForeground()
/*  67:    */   {
/*  68:108 */     return WHITE;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ColorUIResource getMenuItemBackground()
/*  72:    */   {
/*  73:112 */     return WHITE;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public ColorUIResource getToggleButtonCheckColor()
/*  77:    */   {
/*  78:116 */     return Colors.GREEN_CHECK;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void addCustomEntriesToTable(UIDefaults table)
/*  82:    */   {
/*  83:120 */     super.addCustomEntriesToTable(table);
/*  84:121 */     Object[] uiDefaults = { "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", new Integer(22) };
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:128 */     table.putDefaults(uiDefaults);
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.ExperienceRoyale
 * JD-Core Version:    0.7.0.1
 */