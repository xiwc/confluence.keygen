/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import javax.swing.UIDefaults;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MicroLayoutPolicies
/*     */ {
/*     */   public static MicroLayoutPolicy getDefaultPlasticPolicy()
/*     */   {
/*  66 */     return new DefaultPlasticPolicy(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static MicroLayoutPolicy getDefaultWindowsPolicy()
/*     */   {
/*  77 */     return new DefaultWindowsPolicy(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class DefaultPlasticPolicy
/*     */     implements MicroLayoutPolicy
/*     */   {
/*     */     public MicroLayout getMicroLayout(String lafName, UIDefaults table)
/*     */     {
/*  90 */       boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
/*  91 */       boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
/*  92 */       boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
/*  93 */       boolean isPlasticXP = lafName.equals("JGoodies Plastic XP");
/*  94 */       if (isPlasticXP) {
/*  95 */         if (isVista) {
/*  96 */           return isClassic ? MicroLayouts.createPlasticXPVistaClassicMicroLayout() : MicroLayouts.createPlasticXPVistaMicroLayout();
/*     */         }
/*     */         
/*     */ 
/* 100 */         return isLowRes ? MicroLayouts.createPlasticXPLowResMicroLayout() : MicroLayouts.createPlasticXPHiResMicroLayout();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 105 */       if (isVista) {
/* 106 */         return isClassic ? MicroLayouts.createPlasticVistaClassicMicroLayout() : MicroLayouts.createPlasticVistaMicroLayout();
/*     */       }
/*     */       
/*     */ 
/* 110 */       return isLowRes ? MicroLayouts.createPlasticLowResMicroLayout() : MicroLayouts.createPlasticHiResMicroLayout();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class DefaultWindowsPolicy
/*     */     implements MicroLayoutPolicy
/*     */   {
/*     */     public MicroLayout getMicroLayout(String lafName, UIDefaults table)
/*     */     {
/* 126 */       boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
/* 127 */       boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
/* 128 */       boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
/* 129 */       if (isClassic) {
/* 130 */         return isLowRes ? MicroLayouts.createWindowsClassicLowResMicroLayout() : MicroLayouts.createWindowsClassicHiResMicroLayout();
/*     */       }
/*     */       
/* 133 */       if (isVista) {
/* 134 */         return isLowRes ? MicroLayouts.createWindowsVistaLowResMicroLayout() : MicroLayouts.createWindowsVistaHiResMicroLayout();
/*     */       }
/*     */       
/*     */ 
/* 138 */       return isLowRes ? MicroLayouts.createWindowsXPLowResMicroLayout() : MicroLayouts.createWindowsXPHiResMicroLayout();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\MicroLayoutPolicies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */