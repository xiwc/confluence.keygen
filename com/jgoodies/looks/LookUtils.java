/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*   4:    */ import com.jgoodies.looks.plastic.PlasticTheme;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Component;
/*   7:    */ import java.awt.Toolkit;
/*   8:    */ import java.awt.image.ColorModel;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Locale;
/*  13:    */ import javax.swing.LookAndFeel;
/*  14:    */ import javax.swing.UIManager;
/*  15:    */ import javax.swing.UnsupportedLookAndFeelException;
/*  16:    */ 
/*  17:    */ public final class LookUtils
/*  18:    */ {
/*  19: 63 */   private static final String JAVA_VERSION = getSystemProperty("java.version");
/*  20: 71 */   private static final String OS_NAME = getSystemProperty("os.name");
/*  21: 79 */   private static final String OS_VERSION = getSystemProperty("os.version");
/*  22: 87 */   public static final boolean IS_JAVA_1_4 = startsWith(JAVA_VERSION, "1.4");
/*  23: 93 */   public static final boolean IS_JAVA_1_4_0 = startsWith(JAVA_VERSION, "1.4.0");
/*  24: 99 */   public static final boolean IS_JAVA_1_4_2_OR_LATER = (!startsWith(JAVA_VERSION, "1.4.0")) && (!startsWith(JAVA_VERSION, "1.4.1"));
/*  25:106 */   public static final boolean IS_JAVA_5 = startsWith(JAVA_VERSION, "1.5");
/*  26:113 */   public static final boolean IS_JAVA_5_OR_LATER = !IS_JAVA_1_4;
/*  27:119 */   public static final boolean IS_JAVA_6 = startsWith(JAVA_VERSION, "1.6");
/*  28:126 */   public static final boolean IS_JAVA_6_OR_LATER = (!IS_JAVA_1_4) && (!IS_JAVA_5);
/*  29:134 */   public static final boolean IS_JAVA_1_4_OR_5 = (IS_JAVA_1_4) || (IS_JAVA_5);
/*  30:143 */   public static final boolean IS_OS_FREEBSD = startsWithIgnoreCase(OS_NAME, "FreeBSD");
/*  31:149 */   public static final boolean IS_OS_LINUX = startsWithIgnoreCase(OS_NAME, "Linux");
/*  32:155 */   public static final boolean IS_OS_OS2 = startsWith(OS_NAME, "OS/2");
/*  33:161 */   public static final boolean IS_OS_MAC = startsWith(OS_NAME, "Mac");
/*  34:167 */   public static final boolean IS_OS_WINDOWS = startsWith(OS_NAME, "Windows");
/*  35:173 */   public static final boolean IS_OS_WINDOWS_MODERN = (startsWith(OS_NAME, "Windows")) && (!startsWith(OS_VERSION, "4.0"));
/*  36:181 */   public static final boolean IS_OS_WINDOWS_95 = (startsWith(OS_NAME, "Windows 9")) && (startsWith(OS_VERSION, "4.0"));
/*  37:189 */   public static final boolean IS_OS_WINDOWS_98 = (startsWith(OS_NAME, "Windows 9")) && (startsWith(OS_VERSION, "4.1"));
/*  38:197 */   public static final boolean IS_OS_WINDOWS_NT = startsWith(OS_NAME, "Windows NT");
/*  39:205 */   public static final boolean IS_OS_WINDOWS_ME = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "4.9"));
/*  40:213 */   public static final boolean IS_OS_WINDOWS_2000 = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "5.0"));
/*  41:219 */   public static final boolean IS_OS_WINDOWS_XP = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "5.1"));
/*  42:227 */   public static final boolean IS_OS_WINDOWS_VISTA = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "6.0"));
/*  43:233 */   public static final boolean IS_OS_SOLARIS = startsWith(OS_NAME, "Solaris");
/*  44:242 */   public static final boolean IS_LAF_WINDOWS_XP_ENABLED = isWindowsXPLafEnabled();
/*  45:249 */   public static final boolean IS_LOW_RESOLUTION = isLowResolution();
/*  46:251 */   private static boolean loggingEnabled = true;
/*  47:    */   
/*  48:    */   public static String getSystemProperty(String key)
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52:272 */       return System.getProperty(key);
/*  53:    */     }
/*  54:    */     catch (SecurityException e)
/*  55:    */     {
/*  56:274 */       log("Can't read the System property " + key + ".");
/*  57:    */     }
/*  58:275 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static String getSystemProperty(String key, String defaultValue)
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65:291 */       return System.getProperty(key, defaultValue);
/*  66:    */     }
/*  67:    */     catch (SecurityException e)
/*  68:    */     {
/*  69:293 */       log("Can't read the System property " + key + ".");
/*  70:    */     }
/*  71:294 */     return defaultValue;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static Boolean getBooleanSystemProperty(String key, String logMessage)
/*  75:    */   {
/*  76:312 */     String value = getSystemProperty(key, "");
/*  77:    */     Boolean result;
/*  78:    */     Boolean result;
/*  79:314 */     if (value.equalsIgnoreCase("false"))
/*  80:    */     {
/*  81:315 */       result = Boolean.FALSE;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:    */       Boolean result;
/*  86:316 */       if (value.equalsIgnoreCase("true")) {
/*  87:317 */         result = Boolean.TRUE;
/*  88:    */       } else {
/*  89:319 */         result = null;
/*  90:    */       }
/*  91:    */     }
/*  92:320 */     if (result != null) {
/*  93:321 */       log(logMessage + " have been " + (result.booleanValue() ? "en" : "dis") + "abled in the system properties.");
/*  94:    */     }
/*  95:327 */     return result;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private static boolean isWindowsXPLafEnabled()
/*  99:    */   {
/* 100:347 */     return ((IS_OS_WINDOWS_XP) || (IS_OS_WINDOWS_VISTA)) && (IS_JAVA_1_4_2_OR_LATER) && (Boolean.TRUE.equals(Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive"))) && (getSystemProperty("swing.noxp") == null);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static boolean isTrueColor(Component c)
/* 104:    */   {
/* 105:362 */     return c.getToolkit().getColorModel().getPixelSize() >= 24;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static boolean getToolkitUsesNativeDropShadows()
/* 109:    */   {
/* 110:377 */     return IS_OS_MAC;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static Color getSlightlyBrighter(Color color)
/* 114:    */   {
/* 115:389 */     return getSlightlyBrighter(color, 1.1F);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static Color getSlightlyBrighter(Color color, float factor)
/* 119:    */   {
/* 120:401 */     float[] hsbValues = new float[3];
/* 121:402 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);
/* 122:    */     
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:407 */     float hue = hsbValues[0];
/* 127:408 */     float saturation = hsbValues[1];
/* 128:409 */     float brightness = hsbValues[2];
/* 129:410 */     float newBrightness = Math.min(brightness * factor, 1.0F);
/* 130:411 */     return Color.getHSBColor(hue, saturation, newBrightness);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void setLookAndTheme(LookAndFeel laf, Object theme)
/* 134:    */     throws UnsupportedLookAndFeelException
/* 135:    */   {
/* 136:419 */     if (((laf instanceof PlasticLookAndFeel)) && (theme != null) && ((theme instanceof PlasticTheme))) {
/* 137:422 */       PlasticLookAndFeel.setPlasticTheme((PlasticTheme)theme);
/* 138:    */     }
/* 139:424 */     UIManager.setLookAndFeel(laf);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Object getDefaultTheme(LookAndFeel laf)
/* 143:    */   {
/* 144:428 */     return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.createMyDefaultTheme() : null;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static List getInstalledThemes(LookAndFeel laf)
/* 148:    */   {
/* 149:434 */     return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.getInstalledThemes() : Collections.EMPTY_LIST;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static void setLoggingEnabled(boolean enabled)
/* 153:    */   {
/* 154:448 */     loggingEnabled = enabled;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static void log()
/* 158:    */   {
/* 159:455 */     if (loggingEnabled) {
/* 160:456 */       System.out.println();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static void log(String message)
/* 165:    */   {
/* 166:466 */     if (loggingEnabled) {
/* 167:467 */       System.out.println("JGoodies Looks: " + message);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static boolean isLowResolution()
/* 172:    */   {
/* 173:480 */     return Toolkit.getDefaultToolkit().getScreenResolution() < 120;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static boolean startsWith(String str, String prefix)
/* 177:    */   {
/* 178:484 */     return (str != null) && (str.startsWith(prefix));
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static boolean startsWithIgnoreCase(String str, String prefix)
/* 182:    */   {
/* 183:488 */     return (str != null) && (str.toUpperCase(Locale.ENGLISH).startsWith(prefix.toUpperCase(Locale.ENGLISH)));
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.LookUtils
 * JD-Core Version:    0.7.0.1
 */