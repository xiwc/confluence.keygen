/*     */ package com.jgoodies.looks.plastic.theme;
/*     */ 
/*     */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*     */ import com.jgoodies.looks.plastic.PlasticTheme;
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
/*     */ public class SkyBluer
/*     */   extends PlasticTheme
/*     */ {
/*     */   public String getName()
/*     */   {
/*  49 */     return "Sky Bluer";
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary1() {
/*  53 */     return Colors.BLUE_MEDIUM_DARKEST;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/*  57 */     return Colors.BLUE_MEDIUM_MEDIUM;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/*  61 */     return Colors.BLUE_MEDIUM_LIGHTEST;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/*  65 */     return Colors.GRAY_MEDIUMDARK;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/*  69 */     return Colors.GRAY_LIGHT;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/*  73 */     return Colors.GRAY_LIGHTER;
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedBackground() {
/*  77 */     return getPrimary2();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedForeground() {
/*  81 */     return getWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuSelectedBackground() {
/*  85 */     return getSecondary2();
/*     */   }
/*     */   
/*     */   public ColorUIResource getFocusColor() {
/*  89 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : super.getFocusColor();
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
/*     */   public void addCustomEntriesToTable(UIDefaults table)
/*     */   {
/* 102 */     super.addCustomEntriesToTable(table);
/* 103 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
/*     */     
/* 105 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\SkyBluer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */