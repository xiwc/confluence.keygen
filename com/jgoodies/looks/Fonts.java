/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import java.awt.Toolkit;
/*   5:    */ import java.util.Locale;
/*   6:    */ 
/*   7:    */ public final class Fonts
/*   8:    */ {
/*   9:    */   public static final String TAHOMA_NAME = "Tahoma";
/*  10:    */   public static final String SEGOE_UI_NAME = "Segoe UI";
/*  11: 78 */   public static final Font TAHOMA_11PT = new Font("Tahoma", 0, 11);
/*  12: 83 */   public static final Font TAHOMA_13PT = new Font("Tahoma", 0, 13);
/*  13: 88 */   public static final Font TAHOMA_14PT = new Font("Tahoma", 0, 14);
/*  14: 94 */   public static final Font SEGOE_UI_12PT = new Font("Segoe UI", 0, 12);
/*  15: 99 */   public static final Font SEGOE_UI_13PT = new Font("Segoe UI", 0, 13);
/*  16:104 */   public static final Font SEGOE_UI_15PT = new Font("Segoe UI", 0, 15);
/*  17:113 */   public static final Font WINDOWS_XP_96DPI_NORMAL = TAHOMA_11PT;
/*  18:119 */   public static final Font WINDOWS_XP_96DPI_DEFAULT_GUI = TAHOMA_11PT;
/*  19:125 */   public static final Font WINDOWS_XP_96DPI_LARGE = TAHOMA_13PT;
/*  20:131 */   public static final Font WINDOWS_XP_120DPI_NORMAL = TAHOMA_14PT;
/*  21:137 */   public static final Font WINDOWS_XP_120DPI_DEFAULT_GUI = TAHOMA_13PT;
/*  22:143 */   public static final Font WINDOWS_VISTA_96DPI_NORMAL = SEGOE_UI_12PT;
/*  23:149 */   public static final Font WINDOWS_VISTA_96DPI_LARGE = SEGOE_UI_15PT;
/*  24:157 */   static final Font WINDOWS_VISTA_101DPI_NORMAL = SEGOE_UI_13PT;
/*  25:163 */   public static final Font WINDOWS_VISTA_120DPI_NORMAL = SEGOE_UI_15PT;
/*  26:    */   static final String WINDOWS_DEFAULT_GUI_FONT_KEY = "win.defaultGUI.font";
/*  27:    */   static final String WINDOWS_ICON_FONT_KEY = "win.icon.font";
/*  28:    */   
/*  29:    */   static Font getDefaultGUIFontWesternModernWindowsNormal()
/*  30:    */   {
/*  31:203 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_DEFAULT_GUI : WINDOWS_XP_120DPI_DEFAULT_GUI;
/*  32:    */   }
/*  33:    */   
/*  34:    */   static Font getDefaultIconFontWesternModernWindowsNormal()
/*  35:    */   {
/*  36:209 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_NORMAL : WINDOWS_XP_120DPI_NORMAL;
/*  37:    */   }
/*  38:    */   
/*  39:    */   static Font getDefaultIconFontWesternWindowsVistaNormal()
/*  40:    */   {
/*  41:215 */     return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_VISTA_96DPI_NORMAL : WINDOWS_VISTA_120DPI_NORMAL;
/*  42:    */   }
/*  43:    */   
/*  44:    */   static Font getLooks1xWindowsControlFont()
/*  45:    */   {
/*  46:236 */     if (!LookUtils.IS_OS_WINDOWS) {
/*  47:237 */       throw new UnsupportedOperationException();
/*  48:    */     }
/*  49:239 */     return getDesktopFont("win.defaultGUI.font");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Font getWindowsControlFont()
/*  53:    */   {
/*  54:264 */     if (!LookUtils.IS_OS_WINDOWS) {
/*  55:265 */       throw new UnsupportedOperationException();
/*  56:    */     }
/*  57:267 */     Font defaultGUIFont = getDefaultGUIFont();
/*  58:269 */     if ((LookUtils.IS_OS_WINDOWS_95) || (LookUtils.IS_OS_WINDOWS_98) || (LookUtils.IS_OS_WINDOWS_NT) || (LookUtils.IS_OS_WINDOWS_ME)) {
/*  59:273 */       return defaultGUIFont;
/*  60:    */     }
/*  61:277 */     if ((LookUtils.IS_OS_WINDOWS_VISTA) && (LookUtils.IS_JAVA_1_4_OR_5))
/*  62:    */     {
/*  63:278 */       Font tahoma = getDefaultGUIFontWesternModernWindowsNormal();
/*  64:279 */       return Boolean.TRUE.equals(canDisplayLocalizedText(tahoma, Locale.getDefault())) ? tahoma : defaultGUIFont;
/*  65:    */     }
/*  66:284 */     Font iconFont = getDesktopFont("win.icon.font");
/*  67:285 */     return Boolean.TRUE.equals(canDisplayLocalizedText(iconFont, Locale.getDefault())) ? iconFont : defaultGUIFont;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static Font getDefaultGUIFont()
/*  71:    */   {
/*  72:301 */     Font font = getDesktopFont("win.defaultGUI.font");
/*  73:302 */     if (font != null) {
/*  74:303 */       return font;
/*  75:    */     }
/*  76:305 */     return new Font("Dialog", 0, 12);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static Boolean canDisplayLocalizedText(Font font, Locale locale)
/*  80:    */   {
/*  81:333 */     if (localeHasLocalizedDisplayLanguage(locale)) {
/*  82:334 */       return Boolean.valueOf(canDisplayLocalizedDisplayLanguage(font, locale));
/*  83:    */     }
/*  84:336 */     String fontName = font.getName();
/*  85:337 */     String language = locale.getLanguage();
/*  86:338 */     if ("Tahoma".equals(fontName))
/*  87:    */     {
/*  88:339 */       if ("hi".equals(language)) {
/*  89:340 */         return Boolean.FALSE;
/*  90:    */       }
/*  91:341 */       if ("ja".equals(language)) {
/*  92:342 */         return Boolean.FALSE;
/*  93:    */       }
/*  94:343 */       if ("ko".equals(language)) {
/*  95:344 */         return Boolean.FALSE;
/*  96:    */       }
/*  97:345 */       if ("zh".equals(language)) {
/*  98:346 */         return Boolean.FALSE;
/*  99:    */       }
/* 100:    */     }
/* 101:348 */     if ("Microsoft Sans Serif".equals(fontName))
/* 102:    */     {
/* 103:349 */       if ("ja".equals(language)) {
/* 104:350 */         return Boolean.FALSE;
/* 105:    */       }
/* 106:351 */       if ("ko".equals(language)) {
/* 107:352 */         return Boolean.FALSE;
/* 108:    */       }
/* 109:353 */       if ("zh".equals(language)) {
/* 110:354 */         return Boolean.FALSE;
/* 111:    */       }
/* 112:    */     }
/* 113:356 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static boolean canDisplayLocalizedDisplayLanguage(Font font, Locale locale)
/* 117:    */   {
/* 118:374 */     String testString = locale.getDisplayLanguage(locale);
/* 119:375 */     int index = font.canDisplayUpTo(testString);
/* 120:376 */     return index == -1;
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static boolean localeHasLocalizedDisplayLanguage(Locale locale)
/* 124:    */   {
/* 125:389 */     if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
/* 126:390 */       return true;
/* 127:    */     }
/* 128:391 */     String englishDisplayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
/* 129:392 */     String localizedDisplayLanguage = locale.getDisplayLanguage(locale);
/* 130:393 */     return !englishDisplayLanguage.equals(localizedDisplayLanguage);
/* 131:    */   }
/* 132:    */   
/* 133:    */   private static Font getDesktopFont(String fontName)
/* 134:    */   {
/* 135:405 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 136:406 */     return (Font)toolkit.getDesktopProperty(fontName);
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.Fonts
 * JD-Core Version:    0.7.0.1
 */