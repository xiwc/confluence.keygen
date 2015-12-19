/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import java.awt.Insets;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MicroLayouts
/*     */ {
/*  60 */   private static final InsetsUIResource PLASTIC_MENU_ITEM_MARGIN = new InsetsUIResource(3, 0, 3, 0);
/*     */   
/*     */ 
/*  63 */   private static final InsetsUIResource PLASTIC_MENU_MARGIN = new InsetsUIResource(2, 4, 2, 4);
/*     */   
/*     */ 
/*  66 */   private static final InsetsUIResource PLASTIC_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 1);
/*     */   
/*     */ 
/*     */   public static MicroLayout createPlasticLowResMicroLayout()
/*     */   {
/*  71 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(1, 1), getButtonMargin(1, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticHiResMicroLayout()
/*     */   {
/*  89 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(1, 3, 1, 3), getButtonMargin(2, 3), getButtonMargin(2, 3), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticVistaMicroLayout()
/*     */   {
/* 107 */     return new MicroLayout(new InsetsUIResource(1, 1, 1, 1), new InsetsUIResource(1, 2, 1, 1), new InsetsUIResource(1, 1, 1, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticVistaClassicMicroLayout()
/*     */   {
/* 125 */     return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(3, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticXPLowResMicroLayout()
/*     */   {
/* 145 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 3, 2), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticXPHiResMicroLayout()
/*     */   {
/* 163 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(2, 2, 2, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticXPVistaMicroLayout()
/*     */   {
/* 181 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), -1, 1, new Insets(2, 2, 3, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createPlasticXPVistaClassicMicroLayout()
/*     */   {
/* 199 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 4, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 218 */   private static final InsetsUIResource WINDOWS_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 0);
/*     */   
/*     */ 
/*     */   public static MicroLayout createWindowsClassicLowResMicroLayout()
/*     */   {
/* 223 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 3), new InsetsUIResource(2, 0, 3, 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createWindowsClassicHiResMicroLayout()
/*     */   {
/* 241 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 4, 2, 4), new InsetsUIResource(3, 0, 4, 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createWindowsXPLowResMicroLayout()
/*     */   {
/* 259 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createWindowsXPHiResMicroLayout()
/*     */   {
/* 277 */     return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createWindowsVistaLowResMicroLayout()
/*     */   {
/* 295 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayout createWindowsVistaHiResMicroLayout()
/*     */   {
/* 313 */     return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static InsetsUIResource getButtonMargin(int top, int bottom)
/*     */   {
/* 333 */     int pad = Options.getUseNarrowButtons() ? 4 : 14;
/* 334 */     return new InsetsUIResource(top, pad, bottom, pad);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\MicroLayouts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */