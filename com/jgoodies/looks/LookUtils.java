/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*     */ import com.jgoodies.looks.plastic.PlasticTheme;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LookUtils
/*     */ {
/*  63 */   private static final String JAVA_VERSION = getSystemProperty("java.version");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */   private static final String OS_NAME = getSystemProperty("os.name");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   private static final String OS_VERSION = getSystemProperty("os.version");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */   public static final boolean IS_JAVA_1_4 = startsWith(JAVA_VERSION, "1.4");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  93 */   public static final boolean IS_JAVA_1_4_0 = startsWith(JAVA_VERSION, "1.4.0");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */   public static final boolean IS_JAVA_1_4_2_OR_LATER = (!startsWith(JAVA_VERSION, "1.4.0")) && (!startsWith(JAVA_VERSION, "1.4.1"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */   public static final boolean IS_JAVA_5 = startsWith(JAVA_VERSION, "1.5");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */   public static final boolean IS_JAVA_5_OR_LATER = !IS_JAVA_1_4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */   public static final boolean IS_JAVA_6 = startsWith(JAVA_VERSION, "1.6");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */   public static final boolean IS_JAVA_6_OR_LATER = (!IS_JAVA_1_4) && (!IS_JAVA_5);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */   public static final boolean IS_JAVA_1_4_OR_5 = (IS_JAVA_1_4) || (IS_JAVA_5);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 143 */   public static final boolean IS_OS_FREEBSD = startsWithIgnoreCase(OS_NAME, "FreeBSD");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 149 */   public static final boolean IS_OS_LINUX = startsWithIgnoreCase(OS_NAME, "Linux");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 155 */   public static final boolean IS_OS_OS2 = startsWith(OS_NAME, "OS/2");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 161 */   public static final boolean IS_OS_MAC = startsWith(OS_NAME, "Mac");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 167 */   public static final boolean IS_OS_WINDOWS = startsWith(OS_NAME, "Windows");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 173 */   public static final boolean IS_OS_WINDOWS_MODERN = (startsWith(OS_NAME, "Windows")) && (!startsWith(OS_VERSION, "4.0"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 181 */   public static final boolean IS_OS_WINDOWS_95 = (startsWith(OS_NAME, "Windows 9")) && (startsWith(OS_VERSION, "4.0"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 189 */   public static final boolean IS_OS_WINDOWS_98 = (startsWith(OS_NAME, "Windows 9")) && (startsWith(OS_VERSION, "4.1"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 197 */   public static final boolean IS_OS_WINDOWS_NT = startsWith(OS_NAME, "Windows NT");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 205 */   public static final boolean IS_OS_WINDOWS_ME = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "4.9"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */   public static final boolean IS_OS_WINDOWS_2000 = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "5.0"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 219 */   public static final boolean IS_OS_WINDOWS_XP = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "5.1"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */   public static final boolean IS_OS_WINDOWS_VISTA = (startsWith(OS_NAME, "Windows")) && (startsWith(OS_VERSION, "6.0"));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 233 */   public static final boolean IS_OS_SOLARIS = startsWith(OS_NAME, "Solaris");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 242 */   public static final boolean IS_LAF_WINDOWS_XP_ENABLED = isWindowsXPLafEnabled();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 249 */   public static final boolean IS_LOW_RESOLUTION = isLowResolution();
/*     */   
/* 251 */   private static boolean loggingEnabled = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getSystemProperty(String key)
/*     */   {
/*     */     try
/*     */     {
/* 272 */       return System.getProperty(key);
/*     */     } catch (SecurityException e) {
/* 274 */       log("Can't read the System property " + key + "."); }
/* 275 */     return null;
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
/*     */   public static String getSystemProperty(String key, String defaultValue)
/*     */   {
/*     */     try
/*     */     {
/* 291 */       return System.getProperty(key, defaultValue);
/*     */     } catch (SecurityException e) {
/* 293 */       log("Can't read the System property " + key + "."); }
/* 294 */     return defaultValue;
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
/*     */   public static Boolean getBooleanSystemProperty(String key, String logMessage)
/*     */   {
/* 312 */     String value = getSystemProperty(key, "");
/*     */     Boolean result;
/* 314 */     Boolean result; if (value.equalsIgnoreCase("false")) {
/* 315 */       result = Boolean.FALSE; } else { Boolean result;
/* 316 */       if (value.equalsIgnoreCase("true")) {
/* 317 */         result = Boolean.TRUE;
/*     */       } else
/* 319 */         result = null; }
/* 320 */     if (result != null) {
/* 321 */       log(logMessage + " have been " + (result.booleanValue() ? "en" : "dis") + "abled in the system properties.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 327 */     return result;
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
/*     */   private static boolean isWindowsXPLafEnabled()
/*     */   {
/* 347 */     return ((IS_OS_WINDOWS_XP) || (IS_OS_WINDOWS_VISTA)) && (IS_JAVA_1_4_2_OR_LATER) && (Boolean.TRUE.equals(Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive"))) && (getSystemProperty("swing.noxp") == null);
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
/*     */   public static boolean isTrueColor(Component c)
/*     */   {
/* 362 */     return c.getToolkit().getColorModel().getPixelSize() >= 24;
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
/*     */   public static boolean getToolkitUsesNativeDropShadows()
/*     */   {
/* 377 */     return IS_OS_MAC;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Color getSlightlyBrighter(Color color)
/*     */   {
/* 389 */     return getSlightlyBrighter(color, 1.1F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Color getSlightlyBrighter(Color color, float factor)
/*     */   {
/* 401 */     float[] hsbValues = new float[3];
/* 402 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 407 */     float hue = hsbValues[0];
/* 408 */     float saturation = hsbValues[1];
/* 409 */     float brightness = hsbValues[2];
/* 410 */     float newBrightness = Math.min(brightness * factor, 1.0F);
/* 411 */     return Color.getHSBColor(hue, saturation, newBrightness);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setLookAndTheme(LookAndFeel laf, Object theme)
/*     */     throws UnsupportedLookAndFeelException
/*     */   {
/* 419 */     if (((laf instanceof PlasticLookAndFeel)) && (theme != null) && ((theme instanceof PlasticTheme)))
/*     */     {
/*     */ 
/* 422 */       PlasticLookAndFeel.setPlasticTheme((PlasticTheme)theme);
/*     */     }
/* 424 */     UIManager.setLookAndFeel(laf);
/*     */   }
/*     */   
/*     */   public static Object getDefaultTheme(LookAndFeel laf) {
/* 428 */     return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.createMyDefaultTheme() : null;
/*     */   }
/*     */   
/*     */ 
/*     */   public static List getInstalledThemes(LookAndFeel laf)
/*     */   {
/* 434 */     return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.getInstalledThemes() : Collections.EMPTY_LIST;
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
/*     */   public static void setLoggingEnabled(boolean enabled)
/*     */   {
/* 448 */     loggingEnabled = enabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void log()
/*     */   {
/* 455 */     if (loggingEnabled) {
/* 456 */       System.out.println();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void log(String message)
/*     */   {
/* 466 */     if (loggingEnabled) {
/* 467 */       System.out.println("JGoodies Looks: " + message);
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
/*     */   private static boolean isLowResolution()
/*     */   {
/* 480 */     return Toolkit.getDefaultToolkit().getScreenResolution() < 120;
/*     */   }
/*     */   
/*     */   private static boolean startsWith(String str, String prefix) {
/* 484 */     return (str != null) && (str.startsWith(prefix));
/*     */   }
/*     */   
/*     */   private static boolean startsWithIgnoreCase(String str, String prefix) {
/* 488 */     return (str != null) && (str.toUpperCase(Locale.ENGLISH).startsWith(prefix.toUpperCase(Locale.ENGLISH)));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\LookUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */