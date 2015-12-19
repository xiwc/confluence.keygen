/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import java.awt.Font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontPolicies
/*     */ {
/*     */   public static FontPolicy createFixedPolicy(FontSet fontSet)
/*     */   {
/*  81 */     return new FixedPolicy(fontSet);
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
/*     */   public static FontPolicy customSettingsPolicy(FontPolicy defaultPolicy)
/*     */   {
/*  96 */     return new CustomSettingsPolicy(defaultPolicy);
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
/*     */   public static FontPolicy getDefaultPlasticOnWindowsPolicy()
/*     */   {
/* 122 */     return new DefaultPlasticOnWindowsPolicy(null);
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
/*     */   public static FontPolicy getDefaultPlasticPolicy()
/*     */   {
/* 138 */     if (LookUtils.IS_OS_WINDOWS) {
/* 139 */       return getDefaultPlasticOnWindowsPolicy();
/*     */     }
/* 141 */     return getLogicalFontsPolicy();
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
/*     */   public static FontPolicy getDefaultWindowsPolicy()
/*     */   {
/* 158 */     return new DefaultWindowsPolicy(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FontPolicy getLogicalFontsPolicy()
/*     */   {
/* 169 */     return createFixedPolicy(FontSets.getLogicalFontSet());
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
/*     */   public static FontPolicy getLooks1xPlasticPolicy()
/*     */   {
/* 183 */     Font controlFont = Fonts.getDefaultGUIFontWesternModernWindowsNormal();
/* 184 */     Font menuFont = controlFont;
/* 185 */     Font titleFont = controlFont.deriveFont(1);
/* 186 */     FontSet fontSet = FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
/* 187 */     return createFixedPolicy(fontSet);
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
/*     */   public static FontPolicy getLooks1xWindowsPolicy()
/*     */   {
/* 201 */     return new Looks1xWindowsPolicy(null);
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
/*     */   public static FontPolicy getTransitionalPlasticPolicy()
/*     */   {
/* 215 */     return LookUtils.IS_OS_WINDOWS ? getDefaultPlasticOnWindowsPolicy() : getLooks1xPlasticPolicy();
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
/*     */   private static FontSet getCustomFontSet(String lafName)
/*     */   {
/* 233 */     String controlFontKey = lafName + ".controlFont";
/* 234 */     String menuFontKey = lafName + ".menuFont";
/* 235 */     String decodedControlFont = LookUtils.getSystemProperty(controlFontKey);
/* 236 */     if (decodedControlFont == null)
/* 237 */       return null;
/* 238 */     Font controlFont = Font.decode(decodedControlFont);
/* 239 */     String decodedMenuFont = LookUtils.getSystemProperty(menuFontKey);
/* 240 */     Font menuFont = decodedMenuFont != null ? Font.decode(decodedMenuFont) : null;
/*     */     
/*     */ 
/* 243 */     Font titleFont = "Plastic".equals(lafName) ? controlFont.deriveFont(1) : controlFont;
/*     */     
/*     */ 
/* 246 */     return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
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
/*     */   private static FontPolicy getCustomPolicy(String lafName)
/*     */   {
/* 261 */     return null;
/*     */   }
/*     */   
/*     */   private static final class CustomSettingsPolicy implements FontPolicy
/*     */   {
/*     */     private final FontPolicy wrappedPolicy;
/*     */     
/*     */     CustomSettingsPolicy(FontPolicy wrappedPolicy)
/*     */     {
/* 270 */       this.wrappedPolicy = wrappedPolicy;
/*     */     }
/*     */     
/*     */     public FontSet getFontSet(String lafName, UIDefaults table) {
/* 274 */       FontPolicy customPolicy = FontPolicies.getCustomPolicy(lafName);
/* 275 */       if (customPolicy != null) {
/* 276 */         return customPolicy.getFontSet(null, table);
/*     */       }
/* 278 */       FontSet customFontSet = FontPolicies.getCustomFontSet(lafName);
/* 279 */       if (customFontSet != null) {
/* 280 */         return customFontSet;
/*     */       }
/* 282 */       return this.wrappedPolicy.getFontSet(lafName, table);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class DefaultPlasticOnWindowsPolicy
/*     */     implements FontPolicy
/*     */   {
/*     */     public FontSet getFontSet(String lafName, UIDefaults table)
/*     */     {
/* 305 */       Font windowsControlFont = Fonts.getWindowsControlFont();
/*     */       Font controlFont;
/* 307 */       Font controlFont; if (windowsControlFont != null) {
/* 308 */         controlFont = windowsControlFont; } else { Font controlFont;
/* 309 */         if (table != null) {
/* 310 */           controlFont = table.getFont("Button.font");
/*     */         } else {
/* 312 */           controlFont = new Font("Dialog", 0, 12);
/*     */         }
/*     */       }
/* 315 */       Font menuFont = table == null ? controlFont : table.getFont("Menu.font");
/*     */       
/*     */ 
/* 318 */       Font titleFont = controlFont.deriveFont(1);
/*     */       
/* 320 */       return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class DefaultWindowsPolicy
/*     */     implements FontPolicy
/*     */   {
/*     */     public FontSet getFontSet(String lafName, UIDefaults table)
/*     */     {
/* 331 */       Font windowsControlFont = Fonts.getWindowsControlFont();
/*     */       Font controlFont;
/* 333 */       Font controlFont; if (windowsControlFont != null) {
/* 334 */         controlFont = windowsControlFont; } else { Font controlFont;
/* 335 */         if (table != null) {
/* 336 */           controlFont = table.getFont("Button.font");
/*     */         } else
/* 338 */           controlFont = new Font("Dialog", 0, 12);
/*     */       }
/* 340 */       Font menuFont = table == null ? controlFont : table.getFont("Menu.font");
/*     */       
/*     */ 
/* 343 */       Font titleFont = controlFont;
/* 344 */       Font messageFont = table == null ? controlFont : table.getFont("OptionPane.font");
/*     */       
/*     */ 
/* 347 */       Font smallFont = table == null ? controlFont.deriveFont(controlFont.getSize2D() - 2.0F) : table.getFont("ToolTip.font");
/*     */       
/*     */ 
/* 350 */       Font windowTitleFont = table == null ? controlFont : table.getFont("InternalFrame.titleFont");
/*     */       
/*     */ 
/* 353 */       return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class FixedPolicy
/*     */     implements FontPolicy
/*     */   {
/*     */     private final FontSet fontSet;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     FixedPolicy(FontSet fontSet)
/*     */     {
/* 373 */       this.fontSet = fontSet;
/*     */     }
/*     */     
/*     */     public FontSet getFontSet(String lafName, UIDefaults table) {
/* 377 */       return this.fontSet;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Looks1xWindowsPolicy
/*     */     implements FontPolicy
/*     */   {
/*     */     public FontSet getFontSet(String lafName, UIDefaults table)
/*     */     {
/* 388 */       Font windowsControlFont = Fonts.getLooks1xWindowsControlFont();
/*     */       Font controlFont;
/* 390 */       Font controlFont; if (windowsControlFont != null) {
/* 391 */         controlFont = windowsControlFont; } else { Font controlFont;
/* 392 */         if (table != null) {
/* 393 */           controlFont = table.getFont("Button.font");
/*     */         } else
/* 395 */           controlFont = new Font("Dialog", 0, 12);
/*     */       }
/* 397 */       return FontSets.createDefaultFontSet(controlFont);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\FontPolicies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */