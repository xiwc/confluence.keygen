/*     */ package com.jgoodies.looks.plastic.theme;
/*     */ 
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
/*     */ 
/*     */ public class ExperienceRoyale
/*     */   extends DesertBluer
/*     */ {
/*     */   public String getName()
/*     */   {
/*  51 */     return "Experience Royale";
/*     */   }
/*     */   
/*  54 */   protected static final ColorUIResource ROYALE_BACKGROUND = new ColorUIResource(235, 233, 237);
/*     */   
/*     */ 
/*  57 */   protected static final ColorUIResource ROYALE_BACKGROUND_DARKER = new ColorUIResource(167, 166, 170);
/*     */   
/*     */ 
/*  60 */   protected static final ColorUIResource ROYALE_SELECTION = new ColorUIResource(51, 94, 168);
/*     */   
/*     */ 
/*  63 */   private static final ColorUIResource SECONDARY1 = Colors.GRAY_MEDIUM;
/*     */   
/*     */ 
/*  66 */   private static final ColorUIResource SECONDARY2 = ROYALE_BACKGROUND_DARKER;
/*     */   
/*     */ 
/*  69 */   private static final ColorUIResource SECONDARY3 = ROYALE_BACKGROUND;
/*     */   
/*     */   protected ColorUIResource getPrimary1()
/*     */   {
/*  73 */     return ROYALE_SELECTION;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/*  77 */     return Colors.BLUE_LOW_MEDIUM;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/*  81 */     return Colors.BLUE_LOW_LIGHTEST;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/*  85 */     return SECONDARY1;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/*  89 */     return SECONDARY2;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/*  93 */     return SECONDARY3;
/*     */   }
/*     */   
/*     */   public ColorUIResource getFocusColor() {
/*  97 */     return Colors.ORANGE_FOCUS;
/*     */   }
/*     */   
/*     */   public ColorUIResource getPrimaryControlShadow() {
/* 101 */     return getPrimary3();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuSelectedBackground() {
/* 105 */     return getPrimary1();
/*     */   }
/*     */   
/* 108 */   public ColorUIResource getMenuSelectedForeground() { return WHITE; }
/*     */   
/*     */   public ColorUIResource getMenuItemBackground()
/*     */   {
/* 112 */     return WHITE;
/*     */   }
/*     */   
/*     */   public ColorUIResource getToggleButtonCheckColor() {
/* 116 */     return Colors.GREEN_CHECK;
/*     */   }
/*     */   
/*     */   public void addCustomEntriesToTable(UIDefaults table) {
/* 120 */     super.addCustomEntriesToTable(table);
/* 121 */     Object[] uiDefaults = { "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", new Integer(22) };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 128 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\ExperienceRoyale.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */