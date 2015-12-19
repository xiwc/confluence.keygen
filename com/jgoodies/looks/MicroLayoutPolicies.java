/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import javax.swing.UIDefaults;
/*   4:    */ 
/*   5:    */ public final class MicroLayoutPolicies
/*   6:    */ {
/*   7:    */   public static MicroLayoutPolicy getDefaultPlasticPolicy()
/*   8:    */   {
/*   9: 66 */     return new DefaultPlasticPolicy(null);
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static MicroLayoutPolicy getDefaultWindowsPolicy()
/*  13:    */   {
/*  14: 77 */     return new DefaultWindowsPolicy(null);
/*  15:    */   }
/*  16:    */   
/*  17:    */   private static final class DefaultPlasticPolicy
/*  18:    */     implements MicroLayoutPolicy
/*  19:    */   {
/*  20:    */     public MicroLayout getMicroLayout(String lafName, UIDefaults table)
/*  21:    */     {
/*  22: 90 */       boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
/*  23: 91 */       boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
/*  24: 92 */       boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
/*  25: 93 */       boolean isPlasticXP = lafName.equals("JGoodies Plastic XP");
/*  26: 94 */       if (isPlasticXP)
/*  27:    */       {
/*  28: 95 */         if (isVista) {
/*  29: 96 */           return isClassic ? MicroLayouts.createPlasticXPVistaClassicMicroLayout() : MicroLayouts.createPlasticXPVistaMicroLayout();
/*  30:    */         }
/*  31:100 */         return isLowRes ? MicroLayouts.createPlasticXPLowResMicroLayout() : MicroLayouts.createPlasticXPHiResMicroLayout();
/*  32:    */       }
/*  33:105 */       if (isVista) {
/*  34:106 */         return isClassic ? MicroLayouts.createPlasticVistaClassicMicroLayout() : MicroLayouts.createPlasticVistaMicroLayout();
/*  35:    */       }
/*  36:110 */       return isLowRes ? MicroLayouts.createPlasticLowResMicroLayout() : MicroLayouts.createPlasticHiResMicroLayout();
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   private static final class DefaultWindowsPolicy
/*  41:    */     implements MicroLayoutPolicy
/*  42:    */   {
/*  43:    */     public MicroLayout getMicroLayout(String lafName, UIDefaults table)
/*  44:    */     {
/*  45:126 */       boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
/*  46:127 */       boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
/*  47:128 */       boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
/*  48:129 */       if (isClassic) {
/*  49:130 */         return isLowRes ? MicroLayouts.createWindowsClassicLowResMicroLayout() : MicroLayouts.createWindowsClassicHiResMicroLayout();
/*  50:    */       }
/*  51:133 */       if (isVista) {
/*  52:134 */         return isLowRes ? MicroLayouts.createWindowsVistaLowResMicroLayout() : MicroLayouts.createWindowsVistaHiResMicroLayout();
/*  53:    */       }
/*  54:138 */       return isLowRes ? MicroLayouts.createWindowsXPLowResMicroLayout() : MicroLayouts.createWindowsXPHiResMicroLayout();
/*  55:    */     }
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.MicroLayoutPolicies
 * JD-Core Version:    0.7.0.1
 */