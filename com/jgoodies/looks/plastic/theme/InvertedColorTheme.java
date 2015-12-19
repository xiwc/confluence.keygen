/*     */ package com.jgoodies.looks.plastic.theme;
/*     */ 
/*     */ import com.jgoodies.looks.plastic.PlasticTheme;
/*     */ import java.awt.Color;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.plaf.ColorUIResource;
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
/*     */ public abstract class InvertedColorTheme
/*     */   extends PlasticTheme
/*     */ {
/*  49 */   private final ColorUIResource softWhite = new ColorUIResource(154, 154, 154);
/*     */   
/*     */ 
/*  52 */   private final ColorUIResource primary1 = new ColorUIResource(83, 83, 61);
/*     */   
/*     */ 
/*  55 */   private final ColorUIResource primary2 = new ColorUIResource(115, 107, 82);
/*     */   
/*     */ 
/*  58 */   private final ColorUIResource primary3 = new ColorUIResource(156, 156, 123);
/*     */   
/*     */ 
/*  61 */   private final ColorUIResource secondary1 = new ColorUIResource(32, 32, 32);
/*     */   
/*     */ 
/*  64 */   private final ColorUIResource secondary2 = new ColorUIResource(96, 96, 96);
/*     */   
/*     */ 
/*  67 */   private final ColorUIResource secondary3 = new ColorUIResource(84, 84, 84);
/*     */   
/*     */   public ColorUIResource getSimpleInternalFrameBackground()
/*     */   {
/*  71 */     return getWhite();
/*     */   }
/*     */   
/*     */   public void addCustomEntriesToTable(UIDefaults table) {
/*  75 */     super.addCustomEntriesToTable(table);
/*  76 */     Object[] uiDefaults = { "TextField.ineditableForeground", getSoftWhite(), "Plastic.brightenStop", new Color(255, 255, 255, 20), "Plastic.ltBrightenStop", new Color(255, 255, 255, 16), "SimpleInternalFrame.activeTitleBackground", getPrimary2() };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */   
/*     */   public ColorUIResource getControlDisabled() {
/*  90 */     return getSoftWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getControlHighlight() {
/*  94 */     return getSoftWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getControlInfo() {
/*  98 */     return getWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getInactiveSystemTextColor() {
/* 102 */     return getSoftWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuDisabledForeground() {
/* 106 */     return getSoftWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedBackground() {
/* 110 */     return getPrimary3();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedForeground() {
/* 114 */     return getBlack();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuSelectedBackground() {
/* 118 */     return getPrimary2();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuSelectedForeground() {
/* 122 */     return getWhite();
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary1() {
/* 126 */     return this.primary1;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/* 130 */     return this.primary2;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/* 134 */     return this.primary3;
/*     */   }
/*     */   
/* 137 */   public ColorUIResource getPrimaryControlHighlight() { return getSoftWhite(); }
/*     */   
/*     */   protected ColorUIResource getSecondary1()
/*     */   {
/* 141 */     return this.secondary1;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/* 145 */     return this.secondary2;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/* 149 */     return this.secondary3;
/*     */   }
/*     */   
/*     */   public ColorUIResource getSeparatorBackground() {
/* 153 */     return getSoftWhite();
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSoftWhite() {
/* 157 */     return this.softWhite;
/*     */   }
/*     */   
/*     */   public ColorUIResource getTitleTextColor() {
/* 161 */     return getControlInfo();
/*     */   }
/*     */   
/*     */   public ColorUIResource getToggleButtonCheckColor() {
/* 165 */     return getWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getFocusColor() {
/* 169 */     return Colors.GRAY_FOCUS;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\InvertedColorTheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */