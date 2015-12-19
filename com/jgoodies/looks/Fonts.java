/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Fonts
/*     */ {
/*     */   public static final String TAHOMA_NAME = "Tahoma";
/*     */   public static final String SEGOE_UI_NAME = "Segoe UI";
/*  78 */   public static final Font TAHOMA_11PT = new Font("Tahoma", 0, 11);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  83 */   public static final Font TAHOMA_13PT = new Font("Tahoma", 0, 13);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  88 */   public static final Font TAHOMA_14PT = new Font("Tahoma", 0, 14);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  94 */   public static final Font SEGOE_UI_12PT = new Font("Segoe UI", 0, 12);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  99 */   public static final Font SEGOE_UI_13PT = new Font("Segoe UI", 0, 13);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 104 */   public static final Font SEGOE_UI_15PT = new Font("Segoe UI", 0, 15);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */   public static final Font WINDOWS_XP_96DPI_NORMAL = TAHOMA_11PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */   public static final Font WINDOWS_XP_96DPI_DEFAULT_GUI = TAHOMA_11PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 125 */   public static final Font WINDOWS_XP_96DPI_LARGE = TAHOMA_13PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */   public static final Font WINDOWS_XP_120DPI_NORMAL = TAHOMA_14PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 137 */   public static final Font WINDOWS_XP_120DPI_DEFAULT_GUI = TAHOMA_13PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 143 */   public static final Font WINDOWS_VISTA_96DPI_NORMAL = SEGOE_UI_12PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 149 */   public static final Font WINDOWS_VISTA_96DPI_LARGE = SEGOE_UI_15PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */   static final Font WINDOWS_VISTA_101DPI_NORMAL = SEGOE_UI_13PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */   public static final Font WINDOWS_VISTA_120DPI_NORMAL = SEGOE_UI_15PT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String WINDOWS_DEFAULT_GUI_FONT_KEY = "win.defaultGUI.font";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String WINDOWS_ICON_FONT_KEY = "win.icon.font";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Font getDefaultGUIFontWesternModernWindowsNormal()
/*     */   {
/* 203 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_DEFAULT_GUI : WINDOWS_XP_120DPI_DEFAULT_GUI;
/*     */   }
/*     */   
/*     */ 
/*     */   static Font getDefaultIconFontWesternModernWindowsNormal()
/*     */   {
/* 209 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_NORMAL : WINDOWS_XP_120DPI_NORMAL;
/*     */   }
/*     */   
/*     */ 
/*     */   static Font getDefaultIconFontWesternWindowsVistaNormal()
/*     */   {
/* 215 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_VISTA_96DPI_NORMAL : WINDOWS_VISTA_120DPI_NORMAL;
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
/*     */   static Font getLooks1xWindowsControlFont()
/*     */   {
/* 236 */     if (!LookUtils.IS_OS_WINDOWS) {
/* 237 */       throw new UnsupportedOperationException();
/*     */     }
/* 239 */     return getDesktopFont("win.defaultGUI.font");
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
/*     */   public static Font getWindowsControlFont()
/*     */   {
/* 264 */     if (!LookUtils.IS_OS_WINDOWS) {
/* 265 */       throw new UnsupportedOperationException();
/*     */     }
/* 267 */     Font defaultGUIFont = getDefaultGUIFont();
/*     */     
/* 269 */     if ((LookUtils.IS_OS_WINDOWS_95) || (LookUtils.IS_OS_WINDOWS_98) || (LookUtils.IS_OS_WINDOWS_NT) || (LookUtils.IS_OS_WINDOWS_ME))
/*     */     {
/*     */ 
/*     */ 
/* 273 */       return defaultGUIFont;
/*     */     }
/*     */     
/*     */ 
/* 277 */     if ((LookUtils.IS_OS_WINDOWS_VISTA) && (LookUtils.IS_JAVA_1_4_OR_5)) {
/* 278 */       Font tahoma = getDefaultGUIFontWesternModernWindowsNormal();
/* 279 */       return Boolean.TRUE.equals(canDisplayLocalizedText(tahoma, Locale.getDefault())) ? tahoma : defaultGUIFont;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 284 */     Font iconFont = getDesktopFont("win.icon.font");
/* 285 */     return Boolean.TRUE.equals(canDisplayLocalizedText(iconFont, Locale.getDefault())) ? iconFont : defaultGUIFont;
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
/*     */   private static Font getDefaultGUIFont()
/*     */   {
/* 301 */     Font font = getDesktopFont("win.defaultGUI.font");
/* 302 */     if (font != null) {
/* 303 */       return font;
/*     */     }
/* 305 */     return new Font("Dialog", 0, 12);
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
/*     */   public static Boolean canDisplayLocalizedText(Font font, Locale locale)
/*     */   {
/* 333 */     if (localeHasLocalizedDisplayLanguage(locale)) {
/* 334 */       return Boolean.valueOf(canDisplayLocalizedDisplayLanguage(font, locale));
/*     */     }
/* 336 */     String fontName = font.getName();
/* 337 */     String language = locale.getLanguage();
/* 338 */     if ("Tahoma".equals(fontName)) {
/* 339 */       if ("hi".equals(language))
/* 340 */         return Boolean.FALSE;
/* 341 */       if ("ja".equals(language))
/* 342 */         return Boolean.FALSE;
/* 343 */       if ("ko".equals(language))
/* 344 */         return Boolean.FALSE;
/* 345 */       if ("zh".equals(language))
/* 346 */         return Boolean.FALSE;
/*     */     }
/* 348 */     if ("Microsoft Sans Serif".equals(fontName)) {
/* 349 */       if ("ja".equals(language))
/* 350 */         return Boolean.FALSE;
/* 351 */       if ("ko".equals(language))
/* 352 */         return Boolean.FALSE;
/* 353 */       if ("zh".equals(language))
/* 354 */         return Boolean.FALSE;
/*     */     }
/* 356 */     return null;
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
/*     */   private static boolean canDisplayLocalizedDisplayLanguage(Font font, Locale locale)
/*     */   {
/* 374 */     String testString = locale.getDisplayLanguage(locale);
/* 375 */     int index = font.canDisplayUpTo(testString);
/* 376 */     return index == -1;
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
/*     */   private static boolean localeHasLocalizedDisplayLanguage(Locale locale)
/*     */   {
/* 389 */     if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage()))
/* 390 */       return true;
/* 391 */     String englishDisplayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
/* 392 */     String localizedDisplayLanguage = locale.getDisplayLanguage(locale);
/* 393 */     return !englishDisplayLanguage.equals(localizedDisplayLanguage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Font getDesktopFont(String fontName)
/*     */   {
/* 405 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 406 */     return (Font)toolkit.getDesktopProperty(fontName);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\Fonts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */