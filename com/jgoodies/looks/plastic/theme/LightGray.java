/*     */ package com.jgoodies.looks.plastic.theme;
/*     */ 
/*     */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
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
/*     */ 
/*     */ public class LightGray
/*     */   extends ExperienceBlue
/*     */ {
/*  49 */   private static final ColorUIResource GRAY_VERY_LIGHT = new ColorUIResource(244, 244, 244);
/*     */   
/*     */   public String getName()
/*     */   {
/*  53 */     return "Light Gray";
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary1() {
/*  57 */     return new ColorUIResource(51, 153, 255);
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/*  61 */     return Colors.GRAY_MEDIUMLIGHT;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/*  65 */     return new ColorUIResource(225, 240, 255);
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/*  69 */     return Colors.GRAY_MEDIUM;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/*  73 */     return getPrimary2();
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/*  77 */     return GRAY_VERY_LIGHT;
/*     */   }
/*     */   
/*     */   public ColorUIResource getFocusColor() {
/*  81 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
/*     */   }
/*     */   
/*     */ 
/*     */   public ColorUIResource getTitleTextColor()
/*     */   {
/*  87 */     return Colors.GRAY_DARKEST;
/*     */   }
/*     */   
/*     */   public ColorUIResource getSimpleInternalFrameBackground() {
/*  91 */     return Colors.GRAY_MEDIUMDARK;
/*     */   }
/*     */   
/*     */   public void addCustomEntriesToTable(UIDefaults table) {
/*  95 */     super.addCustomEntriesToTable(table);
/*  96 */     Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30), "TabbedPane.selected", getWhite(), "TabbedPane.selectHighlight", Colors.GRAY_MEDIUM };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 101 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\LightGray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */