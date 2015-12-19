/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontSets
/*     */ {
/*     */   private static FontSet logicalFontSet;
/*     */   
/*     */   public static FontSet createDefaultFontSet(Font controlFont)
/*     */   {
/*  75 */     return createDefaultFontSet(controlFont, null);
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
/*     */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont)
/*     */   {
/*  92 */     return createDefaultFontSet(controlFont, menuFont, null, null, null, null);
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
/*     */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont)
/*     */   {
/* 110 */     return createDefaultFontSet(controlFont, menuFont, titleFont, null, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont)
/*     */   {
/* 138 */     return new DefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
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
/*     */   public static FontSet getLogicalFontSet()
/*     */   {
/* 152 */     if (logicalFontSet == null) {
/* 153 */       logicalFontSet = new LogicalFontSet(null);
/*     */     }
/* 155 */     return logicalFontSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class DefaultFontSet
/*     */     implements FontSet
/*     */   {
/*     */     private final FontUIResource controlFont;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final FontUIResource menuFont;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final FontUIResource titleFont;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final FontUIResource messageFont;
/*     */     
/*     */ 
/*     */ 
/*     */     private final FontUIResource smallFont;
/*     */     
/*     */ 
/*     */ 
/*     */     private final FontUIResource windowTitleFont;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public DefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont)
/*     */     {
/* 195 */       this.controlFont = new FontUIResource(controlFont);
/* 196 */       this.menuFont = (menuFont != null ? new FontUIResource(menuFont) : this.controlFont);
/*     */       
/*     */ 
/* 199 */       this.titleFont = (titleFont != null ? new FontUIResource(titleFont) : this.controlFont);
/*     */       
/*     */ 
/* 202 */       this.messageFont = (messageFont != null ? new FontUIResource(messageFont) : this.controlFont);
/*     */       
/*     */ 
/* 205 */       this.smallFont = new FontUIResource(smallFont != null ? smallFont : controlFont.deriveFont(controlFont.getSize2D() - 2.0F));
/*     */       
/*     */ 
/* 208 */       this.windowTitleFont = (windowTitleFont != null ? new FontUIResource(windowTitleFont) : this.titleFont);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public FontUIResource getControlFont()
/*     */     {
/* 217 */       return this.controlFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getMenuFont() {
/* 221 */       return this.menuFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getTitleFont() {
/* 225 */       return this.titleFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getWindowTitleFont() {
/* 229 */       return this.windowTitleFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getSmallFont() {
/* 233 */       return this.smallFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getMessageFont() {
/* 237 */       return this.messageFont;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class LogicalFontSet
/*     */     implements FontSet
/*     */   {
/*     */     private FontUIResource controlFont;
/*     */     
/*     */     private FontUIResource titleFont;
/*     */     
/*     */     private FontUIResource systemFont;
/*     */     
/*     */     private FontUIResource smallFont;
/*     */     
/*     */ 
/*     */     public FontUIResource getControlFont()
/*     */     {
/* 256 */       if (this.controlFont == null) {
/* 257 */         this.controlFont = new FontUIResource(Font.getFont("swing.plaf.metal.controlFont", new Font("Dialog", 0, 12)));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 263 */       return this.controlFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getMenuFont() {
/* 267 */       return getControlFont();
/*     */     }
/*     */     
/*     */     public FontUIResource getTitleFont() {
/* 271 */       if (this.titleFont == null) {
/* 272 */         this.titleFont = new FontUIResource(getControlFont().deriveFont(1));
/*     */       }
/*     */       
/*     */ 
/* 276 */       return this.titleFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getSmallFont() {
/* 280 */       if (this.smallFont == null) {
/* 281 */         this.smallFont = new FontUIResource(Font.getFont("swing.plaf.metal.smallFont", new Font("Dialog", 0, 10)));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 287 */       return this.smallFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getMessageFont() {
/* 291 */       if (this.systemFont == null) {
/* 292 */         this.systemFont = new FontUIResource(Font.getFont("swing.plaf.metal.systemFont", new Font("Dialog", 0, 12)));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 298 */       return this.systemFont;
/*     */     }
/*     */     
/*     */     public FontUIResource getWindowTitleFont() {
/* 302 */       return getTitleFont();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\FontSets.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */