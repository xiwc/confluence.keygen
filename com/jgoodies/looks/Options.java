/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.common.ShadowPopup;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.swing.JTextField;
/*   8:    */ import javax.swing.UIManager;
/*   9:    */ import javax.swing.text.JTextComponent;
/*  10:    */ 
/*  11:    */ public final class Options
/*  12:    */ {
/*  13:    */   public static final String PLASTIC_NAME = "com.jgoodies.looks.plastic.PlasticLookAndFeel";
/*  14:    */   public static final String PLASTIC3D_NAME = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";
/*  15:    */   public static final String PLASTICXP_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/*  16:    */   public static final String JGOODIES_WINDOWS_NAME = "com.jgoodies.looks.windows.WindowsLookAndFeel";
/*  17:    */   public static final String DEFAULT_LOOK_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/*  18:103 */   private static final Map LAF_REPLACEMENTS = new HashMap();
/*  19:    */   public static final String PLASTIC_FONT_POLICY_KEY = "Plastic.fontPolicy";
/*  20:    */   public static final String PLASTIC_CONTROL_FONT_KEY = "Plastic.controlFont";
/*  21:    */   public static final String PLASTIC_MENU_FONT_KEY = "Plastic.menuFont";
/*  22:    */   public static final String WINDOWS_FONT_POLICY_KEY = "Windows.fontPolicy";
/*  23:    */   public static final String WINDOWS_CONTROL_FONT_KEY = "Windows.controlFont";
/*  24:    */   public static final String WINDOWS_MENU_FONT_KEY = "Windows.menuFont";
/*  25:    */   public static final String USE_SYSTEM_FONTS_KEY = "swing.useSystemFontSettings";
/*  26:    */   public static final String USE_SYSTEM_FONTS_APP_KEY = "Application.useSystemFontSettings";
/*  27:    */   public static final String PLASTIC_MICRO_LAYOUT_POLICY_KEY = "Plastic.MicroLayoutPolicy";
/*  28:    */   public static final String WINDOWS_MICRO_LAYOUT_POLICY_KEY = "Windows.MicroLayoutPolicy";
/*  29:    */   public static final String DEFAULT_ICON_SIZE_KEY = "jgoodies.defaultIconSize";
/*  30:    */   public static final String USE_NARROW_BUTTONS_KEY = "jgoodies.useNarrowButtons";
/*  31:    */   public static final String TAB_ICONS_ENABLED_KEY = "jgoodies.tabIconsEnabled";
/*  32:    */   public static final String POPUP_DROP_SHADOW_ENABLED_KEY = "jgoodies.popupDropShadowEnabled";
/*  33:    */   public static final String HI_RES_GRAY_FILTER_ENABLED_KEY = "HiResGrayFilterEnabled";
/*  34:    */   public static final String SELECT_ON_FOCUS_GAIN_KEY = "JGoodies.selectAllOnFocusGain";
/*  35:    */   public static final String IS_ETCHED_KEY = "jgoodies.isEtched";
/*  36:    */   public static final String HEADER_STYLE_KEY = "jgoodies.headerStyle";
/*  37:    */   public static final String NO_ICONS_KEY = "jgoodies.noIcons";
/*  38:    */   public static final String NO_MARGIN_KEY = "JPopupMenu.noMargin";
/*  39:    */   public static final String TREE_LINE_STYLE_KEY = "JTree.lineStyle";
/*  40:    */   public static final String TREE_LINE_STYLE_ANGLED_VALUE = "Angled";
/*  41:    */   public static final String TREE_LINE_STYLE_NONE_VALUE = "None";
/*  42:    */   public static final String NO_CONTENT_BORDER_KEY = "jgoodies.noContentBorder";
/*  43:    */   public static final String EMBEDDED_TABS_KEY = "jgoodies.embeddedTabs";
/*  44:    */   public static final String COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY = "ComboBox.popupPrototypeDisplayValue";
/*  45:    */   public static final String COMBO_RENDERER_IS_BORDER_REMOVABLE = "isBorderRemovable";
/*  46:    */   public static final String HI_RES_DISABLED_ICON_CLIENT_KEY = "generateHiResDisabledIcon";
/*  47:    */   public static final String SELECT_ON_FOCUS_GAIN_CLIENT_KEY = "JGoodies.selectAllOnFocusGain";
/*  48:    */   public static final String INVERT_SELECTION_CLIENT_KEY = "JGoodies.invertSelection";
/*  49:    */   public static final String SET_CARET_TO_START_ON_FOCUS_LOST_CLIENT_KEY = "JGoodies.setCaretToStartOnFocusLost";
/*  50:    */   
/*  51:    */   static
/*  52:    */   {
/*  53:104 */     initializeDefaultReplacements();
/*  54:    */   }
/*  55:    */   
/*  56:439 */   private static final Boolean USE_SYSTEM_FONTS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("swing.useSystemFontSettings", "Use system fonts");
/*  57:451 */   private static final Boolean USE_NARROW_BUTTONS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.useNarrowButtons", "Use narrow buttons");
/*  58:463 */   private static final Boolean TAB_ICONS_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.tabIconsEnabled", "Icons in tabbed panes");
/*  59:481 */   private static final Boolean POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.popupDropShadowEnabled", "Popup drop shadows");
/*  60:488 */   private static final Dimension DEFAULT_ICON_SIZE = new Dimension(20, 20);
/*  61:    */   public static final String NO_REPLACEMENT = "none";
/*  62:    */   
/*  63:    */   public static boolean getUseSystemFonts()
/*  64:    */   {
/*  65:509 */     return !Boolean.FALSE.equals(UIManager.get("Application.useSystemFontSettings")) ? true : USE_SYSTEM_FONTS_SYSTEM_VALUE != null ? USE_SYSTEM_FONTS_SYSTEM_VALUE.booleanValue() : false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void setUseSystemFonts(boolean useSystemFonts)
/*  69:    */   {
/*  70:525 */     UIManager.put("Application.useSystemFontSettings", Boolean.valueOf(useSystemFonts));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Dimension getDefaultIconSize()
/*  74:    */   {
/*  75:538 */     Dimension size = UIManager.getDimension("jgoodies.defaultIconSize");
/*  76:539 */     return size == null ? DEFAULT_ICON_SIZE : size;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void setDefaultIconSize(Dimension defaultIconSize)
/*  80:    */   {
/*  81:550 */     UIManager.put("jgoodies.defaultIconSize", defaultIconSize);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static boolean getUseNarrowButtons()
/*  85:    */   {
/*  86:589 */     return !Boolean.FALSE.equals(UIManager.get("jgoodies.useNarrowButtons")) ? true : USE_NARROW_BUTTONS_SYSTEM_VALUE != null ? USE_NARROW_BUTTONS_SYSTEM_VALUE.booleanValue() : false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void setUseNarrowButtons(boolean b)
/*  90:    */   {
/*  91:607 */     UIManager.put("jgoodies.useNarrowButtons", Boolean.valueOf(b));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static boolean isTabIconsEnabled()
/*  95:    */   {
/*  96:619 */     return !Boolean.FALSE.equals(UIManager.get("jgoodies.tabIconsEnabled")) ? true : TAB_ICONS_ENABLED_SYSTEM_VALUE != null ? TAB_ICONS_ENABLED_SYSTEM_VALUE.booleanValue() : false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void setTabIconsEnabled(boolean b)
/* 100:    */   {
/* 101:632 */     UIManager.put("jgoodies.tabIconsEnabled", Boolean.valueOf(b));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static boolean isPopupDropShadowActive()
/* 105:    */   {
/* 106:653 */     return (!LookUtils.getToolkitUsesNativeDropShadows()) && (ShadowPopup.canSnapshot()) && (isPopupDropShadowEnabled());
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static boolean isPopupDropShadowEnabled()
/* 110:    */   {
/* 111:669 */     if (POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE != null) {
/* 112:670 */       return POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE.booleanValue();
/* 113:    */     }
/* 114:672 */     Object value = UIManager.get("jgoodies.popupDropShadowEnabled");
/* 115:673 */     return value == null ? isPopupDropShadowEnabledDefault() : Boolean.TRUE.equals(value);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void setPopupDropShadowEnabled(boolean b)
/* 119:    */   {
/* 120:692 */     UIManager.put("jgoodies.popupDropShadowEnabled", Boolean.valueOf(b));
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static boolean isPopupDropShadowEnabledDefault()
/* 124:    */   {
/* 125:710 */     return LookUtils.IS_OS_WINDOWS_MODERN;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static boolean isHiResGrayFilterEnabled()
/* 129:    */   {
/* 130:727 */     return !Boolean.FALSE.equals(UIManager.get("HiResGrayFilterEnabled"));
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static void setHiResGrayFilterEnabled(boolean b)
/* 134:    */   {
/* 135:743 */     UIManager.put("HiResGrayFilterEnabled", Boolean.valueOf(b));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static boolean isSelectOnFocusGainEnabled()
/* 139:    */   {
/* 140:764 */     return !Boolean.FALSE.equals(UIManager.get("JGoodies.selectAllOnFocusGain"));
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static void setSelectOnFocusGainEnabled(boolean b)
/* 144:    */   {
/* 145:786 */     UIManager.put("JGoodies.selectAllOnFocusGain", Boolean.valueOf(b));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static boolean isSelectOnFocusGainActive(JTextComponent c)
/* 149:    */   {
/* 150:803 */     Boolean enabled = getSelectOnFocusGainEnabled(c);
/* 151:804 */     if (enabled != null) {
/* 152:805 */       return enabled.booleanValue();
/* 153:    */     }
/* 154:807 */     return isSelectOnFocusGainEnabled();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static Boolean getSelectOnFocusGainEnabled(JTextComponent c)
/* 158:    */   {
/* 159:824 */     return (Boolean)c.getClientProperty("JGoodies.selectAllOnFocusGain");
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void setSelectOnFocusGainEnabled(JTextField field, Boolean b)
/* 163:    */   {
/* 164:839 */     field.putClientProperty("JGoodies.selectAllOnFocusGain", b);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void putLookAndFeelReplacement(String original, String replacement)
/* 168:    */   {
/* 169:857 */     LAF_REPLACEMENTS.put(original, replacement);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void removeLookAndFeelReplacement(String original)
/* 173:    */   {
/* 174:869 */     LAF_REPLACEMENTS.remove(original);
/* 175:    */   }
/* 176:    */   
/* 177:    */   private static void initializeDefaultReplacements()
/* 178:    */   {
/* 179:894 */     putLookAndFeelReplacement("javax.swing.plaf.metal.MetalLookAndFeel", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
/* 180:    */     
/* 181:    */ 
/* 182:897 */     putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", "com.jgoodies.looks.windows.WindowsLookAndFeel");
/* 183:    */     
/* 184:    */ 
/* 185:900 */     putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", "none");
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static String getReplacementClassNameFor(String className)
/* 189:    */   {
/* 190:916 */     String replacement = (String)LAF_REPLACEMENTS.get(className);
/* 191:917 */     if (replacement == null) {
/* 192:918 */       return className;
/* 193:    */     }
/* 194:919 */     if (replacement.equals("none")) {
/* 195:920 */       return null;
/* 196:    */     }
/* 197:922 */     return replacement;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static String getCrossPlatformLookAndFeelClassName()
/* 201:    */   {
/* 202:933 */     return "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static String getSystemLookAndFeelClassName()
/* 206:    */   {
/* 207:943 */     if (LookUtils.IS_OS_WINDOWS) {
/* 208:944 */       return "com.jgoodies.looks.windows.WindowsLookAndFeel";
/* 209:    */     }
/* 210:945 */     if (LookUtils.IS_OS_MAC) {
/* 211:946 */       return UIManager.getSystemLookAndFeelClassName();
/* 212:    */     }
/* 213:948 */     return getCrossPlatformLookAndFeelClassName();
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.Options
 * JD-Core Version:    0.7.0.1
 */