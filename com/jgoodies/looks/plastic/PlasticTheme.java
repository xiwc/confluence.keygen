/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.FontPolicy;
/*     */ import com.jgoodies.looks.FontSet;
/*     */ import java.awt.Color;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import javax.swing.plaf.metal.DefaultMetalTheme;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PlasticTheme
/*     */   extends DefaultMetalTheme
/*     */ {
/*  52 */   public static final Color DARKEN_START = new Color(0, 0, 0, 0);
/*  53 */   public static final Color DARKEN_STOP = new Color(0, 0, 0, 64);
/*  54 */   public static final Color LT_DARKEN_STOP = new Color(0, 0, 0, 32);
/*  55 */   public static final Color BRIGHTEN_START = new Color(255, 255, 255, 0);
/*  56 */   public static final Color BRIGHTEN_STOP = new Color(255, 255, 255, 128);
/*  57 */   public static final Color LT_BRIGHTEN_STOP = new Color(255, 255, 255, 64);
/*     */   
/*  59 */   protected static final ColorUIResource WHITE = new ColorUIResource(255, 255, 255);
/*     */   
/*     */ 
/*  62 */   protected static final ColorUIResource BLACK = new ColorUIResource(0, 0, 0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private FontSet fontSet;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ColorUIResource getBlack()
/*     */   {
/*  81 */     return BLACK;
/*     */   }
/*     */   
/*     */   protected ColorUIResource getWhite() {
/*  85 */     return WHITE;
/*     */   }
/*     */   
/*     */   public ColorUIResource getSystemTextColor() {
/*  89 */     return getControlInfo();
/*     */   }
/*     */   
/*     */   public ColorUIResource getTitleTextColor() {
/*  93 */     return getPrimary1();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuForeground() {
/*  97 */     return getControlInfo();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemBackground() {
/* 101 */     return getMenuBackground();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedBackground() {
/* 105 */     return getMenuSelectedBackground();
/*     */   }
/*     */   
/*     */   public ColorUIResource getMenuItemSelectedForeground() {
/* 109 */     return getMenuSelectedForeground();
/*     */   }
/*     */   
/*     */   public ColorUIResource getSimpleInternalFrameForeground() {
/* 113 */     return getWhite();
/*     */   }
/*     */   
/*     */   public ColorUIResource getSimpleInternalFrameBackground() {
/* 117 */     return getPrimary1();
/*     */   }
/*     */   
/*     */   public ColorUIResource getToggleButtonCheckColor() {
/* 121 */     return getPrimary1();
/*     */   }
/*     */   
/*     */ 
/*     */   public FontUIResource getTitleTextFont()
/*     */   {
/* 127 */     return getFontSet().getTitleFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getControlTextFont() {
/* 131 */     return getFontSet().getControlFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getMenuTextFont() {
/* 135 */     return getFontSet().getMenuFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getSubTextFont() {
/* 139 */     return getFontSet().getSmallFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getSystemTextFont() {
/* 143 */     return getFontSet().getControlFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getUserTextFont() {
/* 147 */     return getFontSet().getControlFont();
/*     */   }
/*     */   
/*     */   public FontUIResource getWindowTitleFont() {
/* 151 */     return getFontSet().getWindowTitleFont();
/*     */   }
/*     */   
/*     */   protected FontSet getFontSet() {
/* 155 */     if (this.fontSet == null) {
/* 156 */       FontPolicy policy = PlasticLookAndFeel.getFontPolicy();
/* 157 */       this.fontSet = policy.getFontSet("Plastic", null);
/*     */     }
/* 159 */     return this.fontSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 171 */     if (this == o)
/* 172 */       return true;
/* 173 */     if (o == null)
/* 174 */       return false;
/* 175 */     return getClass().equals(o.getClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 185 */     return getClass().hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticTheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */