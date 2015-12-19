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
/*     */ public class ExperienceBlue
/*     */   extends DesertBluer
/*     */ {
/*     */   public String getName()
/*     */   {
/*  50 */     return "Experience Blue";
/*     */   }
/*     */   
/*  53 */   protected static final ColorUIResource LUNA_BACKGROUND = new ColorUIResource(236, 233, 216);
/*     */   
/*     */ 
/*  56 */   protected static final ColorUIResource LUNA_BACKGROUND_DARKER = new ColorUIResource(189, 190, 176);
/*     */   
/*     */ 
/*     */ 
/*  60 */   private static final ColorUIResource SECONDARY1 = Colors.GRAY_MEDIUM;
/*     */   
/*     */ 
/*  63 */   private static final ColorUIResource SECONDARY2 = LUNA_BACKGROUND_DARKER;
/*     */   
/*     */ 
/*  66 */   private static final ColorUIResource SECONDARY3 = LUNA_BACKGROUND;
/*     */   
/*     */   protected ColorUIResource getPrimary1()
/*     */   {
/*  70 */     return Colors.BLUE_MEDIUM_DARK;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/*  74 */     return Colors.BLUE_LOW_MEDIUM;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/*  78 */     return Colors.BLUE_LOW_LIGHTEST;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/*  82 */     return SECONDARY1;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/*  86 */     return SECONDARY2;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/*  90 */     return SECONDARY3;
/*     */   }
/*     */   
/*     */   public ColorUIResource getFocusColor() {
/*  94 */     return Colors.ORANGE_FOCUS;
/*     */   }
/*     */   
/*     */   public ColorUIResource getPrimaryControlShadow() {
/*  98 */     return getPrimary3();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuSelectedBackground() {
/* 102 */     return getPrimary1();
/*     */   }
/*     */   
/* 105 */   public ColorUIResource getMenuSelectedForeground() { return WHITE; }
/*     */   
/*     */   public ColorUIResource getMenuItemBackground()
/*     */   {
/* 109 */     return WHITE;
/*     */   }
/*     */   
/*     */   public ColorUIResource getToggleButtonCheckColor() {
/* 113 */     return Colors.GREEN_CHECK;
/*     */   }
/*     */   
/*     */   public void addCustomEntriesToTable(UIDefaults table) {
/* 117 */     super.addCustomEntriesToTable(table);
/* 118 */     Object[] uiDefaults = { "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", new Integer(22) };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 125 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\ExperienceBlue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */