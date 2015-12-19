/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import com.jgoodies.looks.common.ShadowPopup;
/*     */ import java.awt.Dimension;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Options
/*     */ {
/*     */   public static final String PLASTIC_NAME = "com.jgoodies.looks.plastic.PlasticLookAndFeel";
/*     */   public static final String PLASTIC3D_NAME = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";
/*     */   public static final String PLASTICXP_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/*     */   public static final String JGOODIES_WINDOWS_NAME = "com.jgoodies.looks.windows.WindowsLookAndFeel";
/*     */   public static final String DEFAULT_LOOK_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/* 103 */   private static final Map LAF_REPLACEMENTS = new HashMap();
/* 104 */   static { initializeDefaultReplacements(); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String PLASTIC_FONT_POLICY_KEY = "Plastic.fontPolicy";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String PLASTIC_CONTROL_FONT_KEY = "Plastic.controlFont";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String PLASTIC_MENU_FONT_KEY = "Plastic.menuFont";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String WINDOWS_FONT_POLICY_KEY = "Windows.fontPolicy";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String WINDOWS_CONTROL_FONT_KEY = "Windows.controlFont";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String WINDOWS_MENU_FONT_KEY = "Windows.menuFont";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String USE_SYSTEM_FONTS_KEY = "swing.useSystemFontSettings";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String USE_SYSTEM_FONTS_APP_KEY = "Application.useSystemFontSettings";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String PLASTIC_MICRO_LAYOUT_POLICY_KEY = "Plastic.MicroLayoutPolicy";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String WINDOWS_MICRO_LAYOUT_POLICY_KEY = "Windows.MicroLayoutPolicy";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String DEFAULT_ICON_SIZE_KEY = "jgoodies.defaultIconSize";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String USE_NARROW_BUTTONS_KEY = "jgoodies.useNarrowButtons";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TAB_ICONS_ENABLED_KEY = "jgoodies.tabIconsEnabled";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String POPUP_DROP_SHADOW_ENABLED_KEY = "jgoodies.popupDropShadowEnabled";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String HI_RES_GRAY_FILTER_ENABLED_KEY = "HiResGrayFilterEnabled";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String SELECT_ON_FOCUS_GAIN_KEY = "JGoodies.selectAllOnFocusGain";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String IS_ETCHED_KEY = "jgoodies.isEtched";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String HEADER_STYLE_KEY = "jgoodies.headerStyle";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String NO_ICONS_KEY = "jgoodies.noIcons";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String NO_MARGIN_KEY = "JPopupMenu.noMargin";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TREE_LINE_STYLE_KEY = "JTree.lineStyle";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TREE_LINE_STYLE_ANGLED_VALUE = "Angled";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TREE_LINE_STYLE_NONE_VALUE = "None";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String NO_CONTENT_BORDER_KEY = "jgoodies.noContentBorder";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String EMBEDDED_TABS_KEY = "jgoodies.embeddedTabs";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY = "ComboBox.popupPrototypeDisplayValue";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String COMBO_RENDERER_IS_BORDER_REMOVABLE = "isBorderRemovable";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String HI_RES_DISABLED_ICON_CLIENT_KEY = "generateHiResDisabledIcon";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String SELECT_ON_FOCUS_GAIN_CLIENT_KEY = "JGoodies.selectAllOnFocusGain";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String INVERT_SELECTION_CLIENT_KEY = "JGoodies.invertSelection";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String SET_CARET_TO_START_ON_FOCUS_LOST_CLIENT_KEY = "JGoodies.setCaretToStartOnFocusLost";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 439 */   private static final Boolean USE_SYSTEM_FONTS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("swing.useSystemFontSettings", "Use system fonts");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 451 */   private static final Boolean USE_NARROW_BUTTONS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.useNarrowButtons", "Use narrow buttons");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 463 */   private static final Boolean TAB_ICONS_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.tabIconsEnabled", "Icons in tabbed panes");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 481 */   private static final Boolean POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.popupDropShadowEnabled", "Popup drop shadows");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 488 */   private static final Dimension DEFAULT_ICON_SIZE = new Dimension(20, 20);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String NO_REPLACEMENT = "none";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean getUseSystemFonts()
/*     */   {
/* 509 */     return !Boolean.FALSE.equals(UIManager.get("Application.useSystemFontSettings")) ? true : USE_SYSTEM_FONTS_SYSTEM_VALUE != null ? USE_SYSTEM_FONTS_SYSTEM_VALUE.booleanValue() : false;
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
/*     */   public static void setUseSystemFonts(boolean useSystemFonts)
/*     */   {
/* 525 */     UIManager.put("Application.useSystemFontSettings", Boolean.valueOf(useSystemFonts));
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
/*     */   public static Dimension getDefaultIconSize()
/*     */   {
/* 538 */     Dimension size = UIManager.getDimension("jgoodies.defaultIconSize");
/* 539 */     return size == null ? DEFAULT_ICON_SIZE : size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setDefaultIconSize(Dimension defaultIconSize)
/*     */   {
/* 550 */     UIManager.put("jgoodies.defaultIconSize", defaultIconSize);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean getUseNarrowButtons()
/*     */   {
/* 589 */     return !Boolean.FALSE.equals(UIManager.get("jgoodies.useNarrowButtons")) ? true : USE_NARROW_BUTTONS_SYSTEM_VALUE != null ? USE_NARROW_BUTTONS_SYSTEM_VALUE.booleanValue() : false;
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
/*     */   public static void setUseNarrowButtons(boolean b)
/*     */   {
/* 607 */     UIManager.put("jgoodies.useNarrowButtons", Boolean.valueOf(b));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isTabIconsEnabled()
/*     */   {
/* 619 */     return !Boolean.FALSE.equals(UIManager.get("jgoodies.tabIconsEnabled")) ? true : TAB_ICONS_ENABLED_SYSTEM_VALUE != null ? TAB_ICONS_ENABLED_SYSTEM_VALUE.booleanValue() : false;
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
/*     */   public static void setTabIconsEnabled(boolean b)
/*     */   {
/* 632 */     UIManager.put("jgoodies.tabIconsEnabled", Boolean.valueOf(b));
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
/*     */   public static boolean isPopupDropShadowActive()
/*     */   {
/* 653 */     return (!LookUtils.getToolkitUsesNativeDropShadows()) && (ShadowPopup.canSnapshot()) && (isPopupDropShadowEnabled());
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
/*     */   public static boolean isPopupDropShadowEnabled()
/*     */   {
/* 669 */     if (POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE != null) {
/* 670 */       return POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE.booleanValue();
/*     */     }
/* 672 */     Object value = UIManager.get("jgoodies.popupDropShadowEnabled");
/* 673 */     return value == null ? isPopupDropShadowEnabledDefault() : Boolean.TRUE.equals(value);
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
/*     */   public static void setPopupDropShadowEnabled(boolean b)
/*     */   {
/* 692 */     UIManager.put("jgoodies.popupDropShadowEnabled", Boolean.valueOf(b));
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
/*     */   private static boolean isPopupDropShadowEnabledDefault()
/*     */   {
/* 710 */     return LookUtils.IS_OS_WINDOWS_MODERN;
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
/*     */   public static boolean isHiResGrayFilterEnabled()
/*     */   {
/* 727 */     return !Boolean.FALSE.equals(UIManager.get("HiResGrayFilterEnabled"));
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
/*     */   public static void setHiResGrayFilterEnabled(boolean b)
/*     */   {
/* 743 */     UIManager.put("HiResGrayFilterEnabled", Boolean.valueOf(b));
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
/*     */   public static boolean isSelectOnFocusGainEnabled()
/*     */   {
/* 764 */     return !Boolean.FALSE.equals(UIManager.get("JGoodies.selectAllOnFocusGain"));
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
/*     */   public static void setSelectOnFocusGainEnabled(boolean b)
/*     */   {
/* 786 */     UIManager.put("JGoodies.selectAllOnFocusGain", Boolean.valueOf(b));
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
/*     */   public static boolean isSelectOnFocusGainActive(JTextComponent c)
/*     */   {
/* 803 */     Boolean enabled = getSelectOnFocusGainEnabled(c);
/* 804 */     if (enabled != null) {
/* 805 */       return enabled.booleanValue();
/*     */     }
/* 807 */     return isSelectOnFocusGainEnabled();
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
/*     */   public static Boolean getSelectOnFocusGainEnabled(JTextComponent c)
/*     */   {
/* 824 */     return (Boolean)c.getClientProperty("JGoodies.selectAllOnFocusGain");
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
/*     */   public static void setSelectOnFocusGainEnabled(JTextField field, Boolean b)
/*     */   {
/* 839 */     field.putClientProperty("JGoodies.selectAllOnFocusGain", b);
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
/*     */   public static void putLookAndFeelReplacement(String original, String replacement)
/*     */   {
/* 857 */     LAF_REPLACEMENTS.put(original, replacement);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeLookAndFeelReplacement(String original)
/*     */   {
/* 869 */     LAF_REPLACEMENTS.remove(original);
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
/*     */   private static void initializeDefaultReplacements()
/*     */   {
/* 894 */     putLookAndFeelReplacement("javax.swing.plaf.metal.MetalLookAndFeel", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
/*     */     
/*     */ 
/* 897 */     putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", "com.jgoodies.looks.windows.WindowsLookAndFeel");
/*     */     
/*     */ 
/* 900 */     putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", "none");
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
/*     */   public static String getReplacementClassNameFor(String className)
/*     */   {
/* 916 */     String replacement = (String)LAF_REPLACEMENTS.get(className);
/* 917 */     if (replacement == null)
/* 918 */       return className;
/* 919 */     if (replacement.equals("none")) {
/* 920 */       return null;
/*     */     }
/* 922 */     return replacement;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getCrossPlatformLookAndFeelClassName()
/*     */   {
/* 933 */     return "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getSystemLookAndFeelClassName()
/*     */   {
/* 943 */     if (LookUtils.IS_OS_WINDOWS)
/* 944 */       return "com.jgoodies.looks.windows.WindowsLookAndFeel";
/* 945 */     if (LookUtils.IS_OS_MAC) {
/* 946 */       return UIManager.getSystemLookAndFeelClassName();
/*     */     }
/* 948 */     return getCrossPlatformLookAndFeelClassName();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\Options.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */