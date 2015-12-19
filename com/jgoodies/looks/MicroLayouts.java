/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import java.awt.Insets;
/*   4:    */ import javax.swing.plaf.InsetsUIResource;
/*   5:    */ 
/*   6:    */ public final class MicroLayouts
/*   7:    */ {
/*   8: 60 */   private static final InsetsUIResource PLASTIC_MENU_ITEM_MARGIN = new InsetsUIResource(3, 0, 3, 0);
/*   9: 63 */   private static final InsetsUIResource PLASTIC_MENU_MARGIN = new InsetsUIResource(2, 4, 2, 4);
/*  10: 66 */   private static final InsetsUIResource PLASTIC_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 1);
/*  11:    */   
/*  12:    */   public static MicroLayout createPlasticLowResMicroLayout()
/*  13:    */   {
/*  14: 71 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(1, 1), getButtonMargin(1, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static MicroLayout createPlasticHiResMicroLayout()
/*  18:    */   {
/*  19: 89 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(1, 3, 1, 3), getButtonMargin(2, 3), getButtonMargin(2, 3), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static MicroLayout createPlasticVistaMicroLayout()
/*  23:    */   {
/*  24:107 */     return new MicroLayout(new InsetsUIResource(1, 1, 1, 1), new InsetsUIResource(1, 2, 1, 1), new InsetsUIResource(1, 1, 1, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static MicroLayout createPlasticVistaClassicMicroLayout()
/*  28:    */   {
/*  29:125 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(3, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static MicroLayout createPlasticXPLowResMicroLayout()
/*  33:    */   {
/*  34:145 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 3, 2), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static MicroLayout createPlasticXPHiResMicroLayout()
/*  38:    */   {
/*  39:163 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(2, 2, 2, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static MicroLayout createPlasticXPVistaMicroLayout()
/*  43:    */   {
/*  44:181 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), -1, 1, new Insets(2, 2, 3, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static MicroLayout createPlasticXPVistaClassicMicroLayout()
/*  48:    */   {
/*  49:199 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 4, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*  50:    */   }
/*  51:    */   
/*  52:218 */   private static final InsetsUIResource WINDOWS_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 0);
/*  53:    */   
/*  54:    */   public static MicroLayout createWindowsClassicLowResMicroLayout()
/*  55:    */   {
/*  56:223 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 3), new InsetsUIResource(2, 0, 3, 0));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static MicroLayout createWindowsClassicHiResMicroLayout()
/*  60:    */   {
/*  61:241 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 4, 2, 4), new InsetsUIResource(3, 0, 4, 0));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static MicroLayout createWindowsXPLowResMicroLayout()
/*  65:    */   {
/*  66:259 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static MicroLayout createWindowsXPHiResMicroLayout()
/*  70:    */   {
/*  71:277 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static MicroLayout createWindowsVistaLowResMicroLayout()
/*  75:    */   {
/*  76:295 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static MicroLayout createWindowsVistaHiResMicroLayout()
/*  80:    */   {
/*  81:313 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static InsetsUIResource getButtonMargin(int top, int bottom)
/*  85:    */   {
/*  86:333 */     int pad = Options.getUseNarrowButtons() ? 4 : 14;
/*  87:334 */     return new InsetsUIResource(top, pad, bottom, pad);
/*  88:    */   }
/*  89:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.MicroLayouts
 * JD-Core Version:    0.7.0.1
 */