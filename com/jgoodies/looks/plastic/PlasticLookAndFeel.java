/*    1:     */ package com.jgoodies.looks.plastic;
/*    2:     */ 
/*    3:     */ import com.jgoodies.looks.FontPolicies;
/*    4:     */ import com.jgoodies.looks.FontPolicy;
/*    5:     */ import com.jgoodies.looks.LookUtils;
/*    6:     */ import com.jgoodies.looks.MicroLayout;
/*    7:     */ import com.jgoodies.looks.MicroLayoutPolicies;
/*    8:     */ import com.jgoodies.looks.MicroLayoutPolicy;
/*    9:     */ import com.jgoodies.looks.common.MinimumSizedIcon;
/*   10:     */ import com.jgoodies.looks.common.RGBGrayFilter;
/*   11:     */ import com.jgoodies.looks.common.ShadowPopupFactory;
/*   12:     */ import com.jgoodies.looks.plastic.theme.SkyBluer;
/*   13:     */ import java.awt.Color;
/*   14:     */ import java.awt.Font;
/*   15:     */ import java.awt.Insets;
/*   16:     */ import java.awt.Toolkit;
/*   17:     */ import java.lang.reflect.InvocationTargetException;
/*   18:     */ import java.lang.reflect.Method;
/*   19:     */ import java.util.ArrayList;
/*   20:     */ import java.util.Collections;
/*   21:     */ import java.util.Comparator;
/*   22:     */ import java.util.List;
/*   23:     */ import javax.swing.Icon;
/*   24:     */ import javax.swing.JComponent;
/*   25:     */ import javax.swing.UIDefaults;
/*   26:     */ import javax.swing.UIManager;
/*   27:     */ import javax.swing.border.Border;
/*   28:     */ import javax.swing.border.EmptyBorder;
/*   29:     */ import javax.swing.plaf.BorderUIResource;
/*   30:     */ import javax.swing.plaf.ColorUIResource;
/*   31:     */ import javax.swing.plaf.FontUIResource;
/*   32:     */ import javax.swing.plaf.IconUIResource;
/*   33:     */ import javax.swing.plaf.InsetsUIResource;
/*   34:     */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*   35:     */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*   36:     */ import javax.swing.plaf.metal.MetalTheme;
/*   37:     */ 
/*   38:     */ public class PlasticLookAndFeel
/*   39:     */   extends MetalLookAndFeel
/*   40:     */ {
/*   41:     */   public static final String BORDER_STYLE_KEY = "Plastic.borderStyle";
/*   42:     */   public static final String IS_3D_KEY = "Plastic.is3D";
/*   43:     */   public static final String DEFAULT_THEME_KEY = "Plastic.defaultTheme";
/*   44:     */   public static final String HIGH_CONTRAST_FOCUS_ENABLED_KEY = "Plastic.highContrastFocus";
/*   45:     */   protected static final String TAB_STYLE_KEY = "Plastic.tabStyle";
/*   46:     */   public static final String TAB_STYLE_DEFAULT_VALUE = "default";
/*   47:     */   public static final String TAB_STYLE_METAL_VALUE = "metal";
/*   48: 127 */   private static final Object THEME_KEY = new StringBuffer("Plastic.theme");
/*   49: 135 */   private static boolean useMetalTabs = LookUtils.getSystemProperty("Plastic.tabStyle", "").equalsIgnoreCase("metal");
/*   50: 142 */   private static boolean useHighContrastFocusColors = LookUtils.getSystemProperty("Plastic.highContrastFocus") != null;
/*   51:     */   private static List installedThemes;
/*   52: 151 */   private static boolean is3DEnabled = false;
/*   53: 154 */   private static boolean selectTextOnKeyboardFocusGained = LookUtils.IS_OS_WINDOWS;
/*   54: 161 */   private static Method getCurrentThemeMethod = null;
/*   55:     */   private static final String THEME_CLASSNAME_PREFIX = "com.jgoodies.looks.plastic.theme.";
/*   56:     */   
/*   57:     */   static
/*   58:     */   {
/*   59: 164 */     if (LookUtils.IS_JAVA_5_OR_LATER) {
/*   60: 165 */       getCurrentThemeMethod = getMethodGetCurrentTheme();
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public PlasticLookAndFeel()
/*   65:     */   {
/*   66: 176 */     getPlasticTheme();
/*   67:     */   }
/*   68:     */   
/*   69:     */   public String getID()
/*   70:     */   {
/*   71: 183 */     return "JGoodies Plastic";
/*   72:     */   }
/*   73:     */   
/*   74:     */   public String getName()
/*   75:     */   {
/*   76: 187 */     return "JGoodies Plastic";
/*   77:     */   }
/*   78:     */   
/*   79:     */   public String getDescription()
/*   80:     */   {
/*   81: 191 */     return "The JGoodies Plastic Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/*   82:     */   }
/*   83:     */   
/*   84:     */   public static FontPolicy getFontPolicy()
/*   85:     */   {
/*   86: 219 */     FontPolicy policy = (FontPolicy)UIManager.get("Plastic.fontPolicy");
/*   87: 221 */     if (policy != null) {
/*   88: 222 */       return policy;
/*   89:     */     }
/*   90: 224 */     FontPolicy defaultPolicy = FontPolicies.getDefaultPlasticPolicy();
/*   91: 225 */     return FontPolicies.customSettingsPolicy(defaultPolicy);
/*   92:     */   }
/*   93:     */   
/*   94:     */   public static void setFontPolicy(FontPolicy fontPolicy)
/*   95:     */   {
/*   96: 242 */     UIManager.put("Plastic.fontPolicy", fontPolicy);
/*   97:     */   }
/*   98:     */   
/*   99:     */   public static MicroLayoutPolicy getMicroLayoutPolicy()
/*  100:     */   {
/*  101: 260 */     MicroLayoutPolicy policy = (MicroLayoutPolicy)UIManager.get("Plastic.MicroLayoutPolicy");
/*  102:     */     
/*  103: 262 */     return policy != null ? policy : MicroLayoutPolicies.getDefaultPlasticPolicy();
/*  104:     */   }
/*  105:     */   
/*  106:     */   public static void setMicroLayoutPolicy(MicroLayout microLayoutPolicy)
/*  107:     */   {
/*  108: 280 */     UIManager.put("Plastic.MicroLayoutPolicy", microLayoutPolicy);
/*  109:     */   }
/*  110:     */   
/*  111:     */   protected boolean is3DEnabled()
/*  112:     */   {
/*  113: 285 */     return is3DEnabled;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public static void set3DEnabled(boolean b)
/*  117:     */   {
/*  118: 289 */     is3DEnabled = b;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public static String getTabStyle()
/*  122:     */   {
/*  123: 293 */     return useMetalTabs ? "metal" : "default";
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static void setTabStyle(String tabStyle)
/*  127:     */   {
/*  128: 297 */     useMetalTabs = tabStyle.equalsIgnoreCase("metal");
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static boolean getHighContrastFocusColorsEnabled()
/*  132:     */   {
/*  133: 301 */     return useHighContrastFocusColors;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static void setHighContrastFocusColorsEnabled(boolean b)
/*  137:     */   {
/*  138: 305 */     useHighContrastFocusColors = b;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public static boolean isSelectTextOnKeyboardFocusGained()
/*  142:     */   {
/*  143: 309 */     return selectTextOnKeyboardFocusGained;
/*  144:     */   }
/*  145:     */   
/*  146:     */   public static void setSelectTextOnKeyboardFocusGained(boolean b)
/*  147:     */   {
/*  148: 321 */     selectTextOnKeyboardFocusGained = b;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public void initialize()
/*  152:     */   {
/*  153: 334 */     super.initialize();
/*  154: 335 */     ShadowPopupFactory.install();
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void uninitialize()
/*  158:     */   {
/*  159: 346 */     super.uninitialize();
/*  160: 347 */     ShadowPopupFactory.uninstall();
/*  161:     */   }
/*  162:     */   
/*  163:     */   public Icon getDisabledIcon(JComponent component, Icon icon)
/*  164:     */   {
/*  165: 362 */     Icon disabledIcon = RGBGrayFilter.getDisabledIcon(component, icon);
/*  166: 363 */     return disabledIcon != null ? new IconUIResource(disabledIcon) : null;
/*  167:     */   }
/*  168:     */   
/*  169:     */   protected void initClassDefaults(UIDefaults table)
/*  170:     */   {
/*  171: 377 */     super.initClassDefaults(table);
/*  172:     */     
/*  173: 379 */     String plasticPrefix = "com.jgoodies.looks.plastic.Plastic";
/*  174: 380 */     String commonPrefix = "com.jgoodies.looks.common.ExtBasic";
/*  175:     */     
/*  176:     */ 
/*  177: 383 */     Object[] uiDefaults = { "ButtonUI", "com.jgoodies.looks.plastic.PlasticButtonUI", "ToggleButtonUI", "com.jgoodies.looks.plastic.PlasticToggleButtonUI", "ComboBoxUI", "com.jgoodies.looks.plastic.PlasticComboBoxUI", "ScrollBarUI", "com.jgoodies.looks.plastic.PlasticScrollBarUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticSpinnerUI", "MenuBarUI", "com.jgoodies.looks.plastic.PlasticMenuBarUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticToolBarUI", "MenuUI", "com.jgoodies.looks.plastic.PlasticMenuUI", "MenuItemUI", "com.jgoodies.looks.common.ExtBasicMenuItemUI", "CheckBoxMenuItemUI", "com.jgoodies.looks.common.ExtBasicCheckBoxMenuItemUI", "RadioButtonMenuItemUI", "com.jgoodies.looks.common.ExtBasicRadioButtonMenuItemUI", "PopupMenuUI", "com.jgoodies.looks.plastic.PlasticPopupMenuUI", "PopupMenuSeparatorUI", "com.jgoodies.looks.common.ExtBasicPopupMenuSeparatorUI", "OptionPaneUI", "com.jgoodies.looks.plastic.PlasticOptionPaneUI", "ScrollPaneUI", "com.jgoodies.looks.plastic.PlasticScrollPaneUI", "SplitPaneUI", "com.jgoodies.looks.plastic.PlasticSplitPaneUI", "PasswordFieldUI", "com.jgoodies.looks.plastic.PlasticPasswordFieldUI", "TextAreaUI", "com.jgoodies.looks.plastic.PlasticTextAreaUI", "TreeUI", "com.jgoodies.looks.plastic.PlasticTreeUI", "InternalFrameUI", "com.jgoodies.looks.plastic.PlasticInternalFrameUI", "SeparatorUI", "com.jgoodies.looks.plastic.PlasticSeparatorUI", "ToolBarSeparatorUI", "com.jgoodies.looks.plastic.PlasticToolBarSeparatorUI", "FileChooserUI", "com.jgoodies.looks.plastic.PlasticFileChooserUI" };
/*  178: 439 */     if (!useMetalTabs) {
/*  179: 441 */       uiDefaults = append(uiDefaults, "TabbedPaneUI", "com.jgoodies.looks.plastic.PlasticTabbedPaneUI");
/*  180:     */     }
/*  181: 444 */     if (isSelectTextOnKeyboardFocusGained())
/*  182:     */     {
/*  183: 446 */       uiDefaults = append(uiDefaults, "TextFieldUI", "com.jgoodies.looks.plastic.PlasticTextFieldUI");
/*  184:     */       
/*  185: 448 */       uiDefaults = append(uiDefaults, "FormattedTextFieldUI", "com.jgoodies.looks.plastic.PlasticFormattedTextFieldUI");
/*  186:     */     }
/*  187: 451 */     table.putDefaults(uiDefaults);
/*  188:     */   }
/*  189:     */   
/*  190:     */   protected void initComponentDefaults(UIDefaults table)
/*  191:     */   {
/*  192: 456 */     super.initComponentDefaults(table);
/*  193:     */     
/*  194: 458 */     MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
/*  195: 459 */     Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
/*  196:     */     
/*  197: 461 */     Object marginBorder = new BasicBorders.MarginBorder();
/*  198:     */     
/*  199: 463 */     Object buttonBorder = PlasticBorders.getButtonBorder(buttonBorderInsets);
/*  200: 464 */     Object comboBoxButtonBorder = PlasticBorders.getComboBoxArrowButtonBorder();
/*  201: 465 */     Border comboBoxEditorBorder = PlasticBorders.getComboBoxEditorBorder();
/*  202: 466 */     Object menuItemBorder = PlasticBorders.getMenuItemBorder();
/*  203: 467 */     Object textFieldBorder = PlasticBorders.getTextFieldBorder();
/*  204: 468 */     Object toggleButtonBorder = PlasticBorders.getToggleButtonBorder(buttonBorderInsets);
/*  205:     */     
/*  206: 470 */     Object scrollPaneBorder = PlasticBorders.getScrollPaneBorder();
/*  207: 471 */     Object tableHeaderBorder = new BorderUIResource((Border)table.get("TableHeader.cellBorder"));
/*  208:     */     
/*  209:     */ 
/*  210: 474 */     Object menuBarEmptyBorder = marginBorder;
/*  211: 475 */     Object menuBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
/*  212: 476 */     Object menuBarEtchedBorder = PlasticBorders.getEtchedBorder();
/*  213: 477 */     Object menuBarHeaderBorder = PlasticBorders.getMenuBarHeaderBorder();
/*  214:     */     
/*  215: 479 */     Object toolBarEmptyBorder = marginBorder;
/*  216: 480 */     Object toolBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
/*  217: 481 */     Object toolBarEtchedBorder = PlasticBorders.getEtchedBorder();
/*  218: 482 */     Object toolBarHeaderBorder = PlasticBorders.getToolBarHeaderBorder();
/*  219:     */     
/*  220: 484 */     Object internalFrameBorder = getInternalFrameBorder();
/*  221: 485 */     Object paletteBorder = getPaletteBorder();
/*  222:     */     
/*  223: 487 */     Color controlColor = table.getColor("control");
/*  224:     */     
/*  225: 489 */     Object checkBoxIcon = PlasticIconFactory.getCheckBoxIcon();
/*  226: 490 */     Object checkBoxMargin = microLayout.getCheckBoxMargin();
/*  227:     */     
/*  228: 492 */     Object buttonMargin = microLayout.getButtonMargin();
/*  229: 493 */     Object textInsets = microLayout.getTextInsets();
/*  230: 494 */     Object wrappedTextInsets = microLayout.getWrappedTextInsets();
/*  231: 495 */     Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
/*  232:     */     
/*  233: 497 */     Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
/*  234: 498 */     int comboBorderSize = comboEditorBorderInsets.left;
/*  235: 499 */     int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
/*  236: 500 */     int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
/*  237: 501 */     Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
/*  238: 502 */     Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
/*  239:     */     
/*  240: 504 */     Object menuItemMargin = microLayout.getMenuItemMargin();
/*  241: 505 */     Object menuMargin = microLayout.getMenuMargin();
/*  242:     */     
/*  243: 507 */     Icon menuItemCheckIcon = new MinimumSizedIcon();
/*  244: 508 */     Icon checkBoxMenuItemIcon = PlasticIconFactory.getCheckBoxMenuItemIcon();
/*  245: 509 */     Icon radioButtonMenuItemIcon = PlasticIconFactory.getRadioButtonMenuItemIcon();
/*  246:     */     
/*  247: 511 */     Color menuItemForeground = table.getColor("MenuItem.foreground");
/*  248:     */     
/*  249: 513 */     Color inactiveTextBackground = table.getColor("TextField.inactiveBackground");
/*  250:     */     
/*  251:     */ 
/*  252: 516 */     int treeFontSize = table.getFont("Tree.font").getSize();
/*  253: 517 */     Integer rowHeight = new Integer(treeFontSize + 6);
/*  254: 518 */     Object treeExpandedIcon = PlasticIconFactory.getExpandedTreeIcon();
/*  255: 519 */     Object treeCollapsedIcon = PlasticIconFactory.getCollapsedTreeIcon();
/*  256: 520 */     ColorUIResource gray = new ColorUIResource(Color.GRAY);
/*  257:     */     
/*  258: 522 */     Boolean is3D = Boolean.valueOf(is3DEnabled());
/*  259:     */     
/*  260: 524 */     Character passwordEchoChar = new Character(LookUtils.IS_OS_WINDOWS ? '●' : '•');
/*  261:     */     
/*  262: 526 */     String iconPrefix = "icons/" + (LookUtils.IS_LOW_RESOLUTION ? "32x32/" : "48x48/");
/*  263: 527 */     Object errorIcon = makeIcon(getClass(), iconPrefix + "dialog-error.png");
/*  264: 528 */     Object informationIcon = makeIcon(getClass(), iconPrefix + "dialog-information.png");
/*  265: 529 */     Object questionIcon = makeIcon(getClass(), iconPrefix + "dialog-question.png");
/*  266: 530 */     Object warningIcon = makeIcon(getClass(), iconPrefix + "dialog-warning.png");
/*  267:     */     
/*  268:     */ 
/*  269: 533 */     Object[] defaults = { "Button.border", buttonBorder, "Button.margin", buttonMargin, "CheckBox.margin", checkBoxMargin, "CheckBox.icon", checkBoxIcon, "CheckBoxMenuItem.border", menuItemBorder, "CheckBoxMenuItem.margin", menuItemMargin, "CheckBoxMenuItem.checkIcon", checkBoxMenuItemIcon, "CheckBoxMenuItem.background", getMenuItemBackground(), "CheckBoxMenuItem.selectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.selectionBackground", getMenuItemSelectedBackground(), "CheckBoxMenuItem.acceleratorForeground", menuItemForeground, "CheckBoxMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "ComboBox.selectionForeground", getMenuSelectedForeground(), "ComboBox.selectionBackground", getMenuSelectedBackground(), "ComboBox.arrowButtonBorder", comboBoxButtonBorder, "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.editorColumns", new Integer(5), "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.editorInsets", textInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, "EditorPane.margin", wrappedTextInsets, "InternalFrame.border", internalFrameBorder, "InternalFrame.paletteBorder", paletteBorder, "List.font", getControlTextFont(), "Menu.border", PlasticBorders.getMenuBorder(), "Menu.margin", menuMargin, "Menu.arrowIcon", PlasticIconFactory.getMenuArrowIcon(), "MenuBar.emptyBorder", menuBarEmptyBorder, "MenuBar.separatorBorder", menuBarSeparatorBorder, "MenuBar.etchedBorder", menuBarEtchedBorder, "MenuBar.headerBorder", menuBarHeaderBorder, "MenuItem.border", menuItemBorder, "MenuItem.checkIcon", menuItemCheckIcon, "MenuItem.margin", menuItemMargin, "MenuItem.background", getMenuItemBackground(), "MenuItem.selectionForeground", getMenuItemSelectedForeground(), "MenuItem.selectionBackground", getMenuItemSelectedBackground(), "MenuItem.acceleratorForeground", menuItemForeground, "MenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "MenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "OptionPane.errorIcon", errorIcon, "OptionPane.informationIcon", informationIcon, "OptionPane.questionIcon", questionIcon, "OptionPane.warningIcon", warningIcon, "FileView.computerIcon", makeIcon(getClass(), "icons/Computer.gif"), "FileView.directoryIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), "FileView.fileIcon", makeIcon(getClass(), "icons/File.gif"), "FileView.floppyDriveIcon", makeIcon(getClass(), "icons/FloppyDrive.gif"), "FileView.hardDriveIcon", makeIcon(getClass(), "icons/HardDrive.gif"), "FileChooser.homeFolderIcon", makeIcon(getClass(), "icons/HomeFolder.gif"), "FileChooser.newFolderIcon", makeIcon(getClass(), "icons/NewFolder.gif"), "FileChooser.upFolderIcon", makeIcon(getClass(), "icons/UpFolder.gif"), "Tree.closedIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), "Tree.openIcon", makeIcon(getClass(), "icons/TreeOpen.gif"), "Tree.leafIcon", makeIcon(getClass(), "icons/TreeLeaf.gif"), "FormattedTextField.border", textFieldBorder, "FormattedTextField.margin", textInsets, "PasswordField.border", textFieldBorder, "PasswordField.margin", textInsets, "PasswordField.echoChar", passwordEchoChar, "PopupMenu.border", PlasticBorders.getPopupMenuBorder(), "PopupMenu.noMarginBorder", PlasticBorders.getNoMarginPopupMenuBorder(), "PopupMenuSeparator.margin", new InsetsUIResource(3, 4, 3, 4), "RadioButton.margin", checkBoxMargin, "RadioButtonMenuItem.border", menuItemBorder, "RadioButtonMenuItem.checkIcon", radioButtonMenuItemIcon, "RadioButtonMenuItem.margin", menuItemMargin, "RadioButtonMenuItem.background", getMenuItemBackground(), "RadioButtonMenuItem.selectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.selectionBackground", getMenuItemSelectedBackground(), "RadioButtonMenuItem.acceleratorForeground", menuItemForeground, "RadioButtonMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "Separator.foreground", getControlDarkShadow(), "ScrollPane.border", scrollPaneBorder, "ScrollPane.etchedBorder", scrollPaneBorder, "SimpleInternalFrame.activeTitleForeground", getSimpleInternalFrameForeground(), "SimpleInternalFrame.activeTitleBackground", getSimpleInternalFrameBackground(), "Spinner.border", PlasticBorders.getFlush3DBorder(), "Spinner.defaultEditorInsets", textInsets, "SplitPane.dividerSize", new Integer(7), "TabbedPane.focus", getFocusColor(), "TabbedPane.tabInsets", new InsetsUIResource(1, 9, 1, 8), "Table.foreground", table.get("textText"), "Table.gridColor", controlColor, "Table.scrollPaneBorder", scrollPaneBorder, "TableHeader.cellBorder", tableHeaderBorder, "TextArea.inactiveBackground", inactiveTextBackground, "TextArea.margin", wrappedTextInsets, "TextField.border", textFieldBorder, "TextField.margin", textInsets, "TitledBorder.font", getTitleTextFont(), "TitledBorder.titleColor", getTitleTextColor(), "ToggleButton.border", toggleButtonBorder, "ToggleButton.margin", buttonMargin, "ToolBar.emptyBorder", toolBarEmptyBorder, "ToolBar.separatorBorder", toolBarSeparatorBorder, "ToolBar.etchedBorder", toolBarEtchedBorder, "ToolBar.headerBorder", toolBarHeaderBorder, "ToolTip.hideAccelerator", Boolean.TRUE, "Tree.expandedIcon", treeExpandedIcon, "Tree.collapsedIcon", treeCollapsedIcon, "Tree.line", gray, "Tree.hash", gray, "Tree.rowHeight", rowHeight, "Button.is3DEnabled", is3D, "ComboBox.is3DEnabled", is3D, "MenuBar.is3DEnabled", is3D, "ToolBar.is3DEnabled", is3D, "ScrollBar.is3DEnabled", is3D, "ToggleButton.is3DEnabled", is3D, "CheckBox.border", marginBorder, "RadioButton.border", marginBorder, "ProgressBar.selectionForeground", getSystemTextColor(), "ProgressBar.selectionBackground", getSystemTextColor() };
/*  270:     */     
/*  271:     */ 
/*  272:     */ 
/*  273:     */ 
/*  274:     */ 
/*  275:     */ 
/*  276:     */ 
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280:     */ 
/*  281:     */ 
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289:     */ 
/*  290:     */ 
/*  291:     */ 
/*  292:     */ 
/*  293:     */ 
/*  294:     */ 
/*  295:     */ 
/*  296:     */ 
/*  297:     */ 
/*  298:     */ 
/*  299:     */ 
/*  300:     */ 
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304:     */ 
/*  305:     */ 
/*  306:     */ 
/*  307:     */ 
/*  308:     */ 
/*  309:     */ 
/*  310:     */ 
/*  311:     */ 
/*  312:     */ 
/*  313:     */ 
/*  314:     */ 
/*  315:     */ 
/*  316:     */ 
/*  317:     */ 
/*  318:     */ 
/*  319:     */ 
/*  320:     */ 
/*  321:     */ 
/*  322:     */ 
/*  323:     */ 
/*  324:     */ 
/*  325:     */ 
/*  326:     */ 
/*  327:     */ 
/*  328:     */ 
/*  329:     */ 
/*  330:     */ 
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334:     */ 
/*  335:     */ 
/*  336:     */ 
/*  337:     */ 
/*  338:     */ 
/*  339:     */ 
/*  340:     */ 
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346:     */ 
/*  347:     */ 
/*  348:     */ 
/*  349:     */ 
/*  350:     */ 
/*  351:     */ 
/*  352:     */ 
/*  353:     */ 
/*  354:     */ 
/*  355:     */ 
/*  356:     */ 
/*  357:     */ 
/*  358:     */ 
/*  359:     */ 
/*  360:     */ 
/*  361:     */ 
/*  362:     */ 
/*  363:     */ 
/*  364:     */ 
/*  365:     */ 
/*  366:     */ 
/*  367:     */ 
/*  368:     */ 
/*  369:     */ 
/*  370:     */ 
/*  371:     */ 
/*  372:     */ 
/*  373:     */ 
/*  374:     */ 
/*  375:     */ 
/*  376:     */ 
/*  377:     */ 
/*  378:     */ 
/*  379:     */ 
/*  380:     */ 
/*  381:     */ 
/*  382:     */ 
/*  383:     */ 
/*  384:     */ 
/*  385:     */ 
/*  386:     */ 
/*  387:     */ 
/*  388:     */ 
/*  389:     */ 
/*  390:     */ 
/*  391:     */ 
/*  392:     */ 
/*  393:     */ 
/*  394:     */ 
/*  395:     */ 
/*  396:     */ 
/*  397:     */ 
/*  398:     */ 
/*  399:     */ 
/*  400:     */ 
/*  401:     */ 
/*  402:     */ 
/*  403:     */ 
/*  404:     */ 
/*  405:     */ 
/*  406:     */ 
/*  407:     */ 
/*  408:     */ 
/*  409:     */ 
/*  410:     */ 
/*  411:     */ 
/*  412:     */ 
/*  413:     */ 
/*  414:     */ 
/*  415:     */ 
/*  416:     */ 
/*  417:     */ 
/*  418:     */ 
/*  419:     */ 
/*  420: 684 */     table.putDefaults(defaults);
/*  421:     */     
/*  422:     */ 
/*  423: 687 */     String soundPathPrefix = "/javax/swing/plaf/metal/";
/*  424: 688 */     Object[] auditoryCues = (Object[])table.get("AuditoryCues.allAuditoryCues");
/*  425: 689 */     if (auditoryCues != null)
/*  426:     */     {
/*  427: 690 */       Object[] audioDefaults = new String[auditoryCues.length * 2];
/*  428: 691 */       for (int i = 0; i < auditoryCues.length; i++)
/*  429:     */       {
/*  430: 692 */         Object auditoryCue = auditoryCues[i];
/*  431: 693 */         audioDefaults[(2 * i)] = auditoryCue;
/*  432: 694 */         audioDefaults[(2 * i + 1)] = (soundPathPrefix + table.getString(auditoryCue));
/*  433:     */       }
/*  434: 696 */       table.putDefaults(audioDefaults);
/*  435:     */     }
/*  436:     */   }
/*  437:     */   
/*  438:     */   protected void initSystemColorDefaults(UIDefaults table)
/*  439:     */   {
/*  440: 708 */     super.initSystemColorDefaults(table);
/*  441: 709 */     table.put("unifiedControlShadow", table.getColor("controlDkShadow"));
/*  442: 710 */     table.put("primaryControlHighlight", getPrimaryControlHighlight());
/*  443:     */   }
/*  444:     */   
/*  445:     */   public static PlasticTheme createMyDefaultTheme()
/*  446:     */   {
/*  447:     */     String defaultName;
/*  448:     */     String defaultName;
/*  449: 726 */     if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED)
/*  450:     */     {
/*  451: 727 */       defaultName = getDefaultXPTheme();
/*  452:     */     }
/*  453:     */     else
/*  454:     */     {
/*  455:     */       String defaultName;
/*  456: 728 */       if (LookUtils.IS_OS_WINDOWS_MODERN) {
/*  457: 729 */         defaultName = "DesertBluer";
/*  458:     */       } else {
/*  459: 731 */         defaultName = "SkyBlue";
/*  460:     */       }
/*  461:     */     }
/*  462: 734 */     String userName = LookUtils.getSystemProperty("Plastic.defaultTheme", "");
/*  463: 735 */     boolean overridden = userName.length() > 0;
/*  464: 736 */     String themeName = overridden ? userName : defaultName;
/*  465: 737 */     PlasticTheme theme = createTheme(themeName);
/*  466: 738 */     PlasticTheme result = theme != null ? theme : new SkyBluer();
/*  467: 741 */     if (overridden)
/*  468:     */     {
/*  469: 742 */       String className = result.getClass().getName().substring("com.jgoodies.looks.plastic.theme.".length());
/*  470: 744 */       if (className.equals(userName))
/*  471:     */       {
/*  472: 745 */         LookUtils.log("I have successfully installed the '" + result.getName() + "' theme.");
/*  473:     */       }
/*  474:     */       else
/*  475:     */       {
/*  476: 747 */         LookUtils.log("I could not install the Plastic theme '" + userName + "'.");
/*  477: 748 */         LookUtils.log("I have installed the '" + result.getName() + "' theme, instead.");
/*  478:     */       }
/*  479:     */     }
/*  480: 751 */     return result;
/*  481:     */   }
/*  482:     */   
/*  483:     */   private static String getDefaultXPTheme()
/*  484:     */   {
/*  485: 756 */     String fallbackName = "ExperienceBlue";
/*  486: 757 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  487: 758 */     String xpstyleDll = (String)toolkit.getDesktopProperty("win.xpstyle.dllName");
/*  488: 759 */     if (xpstyleDll == null) {
/*  489: 760 */       return fallbackName;
/*  490:     */     }
/*  491: 762 */     boolean isStyleLuna = xpstyleDll.endsWith("luna.msstyles");
/*  492: 763 */     boolean isStyleRoyale = xpstyleDll.endsWith("Royale.msstyles");
/*  493: 764 */     boolean isStyleAero = xpstyleDll.endsWith("Aero.msstyles");
/*  494: 765 */     if (isStyleRoyale) {
/*  495: 766 */       return "ExperienceRoyale";
/*  496:     */     }
/*  497: 768 */     if (isStyleLuna)
/*  498:     */     {
/*  499: 769 */       String xpstyleColorName = (String)toolkit.getDesktopProperty("win.xpstyle.colorName");
/*  500: 770 */       if (xpstyleColorName == null) {
/*  501: 771 */         return fallbackName;
/*  502:     */       }
/*  503: 773 */       if (xpstyleColorName.equalsIgnoreCase("HomeStead")) {
/*  504: 774 */         return "ExperienceGreen";
/*  505:     */       }
/*  506: 775 */       if (xpstyleColorName.equalsIgnoreCase("Metallic")) {
/*  507: 776 */         return "Silver";
/*  508:     */       }
/*  509: 778 */       return fallbackName;
/*  510:     */     }
/*  511: 781 */     if (isStyleAero) {
/*  512: 782 */       return "LightGray";
/*  513:     */     }
/*  514: 784 */     return fallbackName;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public static List getInstalledThemes()
/*  518:     */   {
/*  519: 795 */     if (null == installedThemes) {
/*  520: 796 */       installDefaultThemes();
/*  521:     */     }
/*  522: 798 */     Collections.sort(installedThemes, new Comparator()
/*  523:     */     {
/*  524:     */       public int compare(Object o1, Object o2)
/*  525:     */       {
/*  526: 800 */         MetalTheme theme1 = (MetalTheme)o1;
/*  527: 801 */         MetalTheme theme2 = (MetalTheme)o2;
/*  528: 802 */         return theme1.getName().compareTo(theme2.getName());
/*  529:     */       }
/*  530: 805 */     });
/*  531: 806 */     return installedThemes;
/*  532:     */   }
/*  533:     */   
/*  534:     */   protected static void installDefaultThemes()
/*  535:     */   {
/*  536: 814 */     installedThemes = new ArrayList();
/*  537: 815 */     String[] themeNames = { "BrownSugar", "DarkStar", "DesertBlue", "DesertBluer", "DesertGreen", "DesertRed", "DesertYellow", "ExperienceBlue", "ExperienceGreen", "ExperienceRoyale", "LightGray", "Silver", "SkyBlue", "SkyBluer", "SkyGreen", "SkyKrupp", "SkyPink", "SkyRed", "SkyYellow" };
/*  538: 835 */     for (int i = themeNames.length - 1; i >= 0; i--) {
/*  539: 836 */       installTheme(createTheme(themeNames[i]));
/*  540:     */     }
/*  541:     */   }
/*  542:     */   
/*  543:     */   protected static PlasticTheme createTheme(String themeName)
/*  544:     */   {
/*  545: 848 */     String className = "com.jgoodies.looks.plastic.theme." + themeName;
/*  546:     */     try
/*  547:     */     {
/*  548: 850 */       Class cl = Class.forName(className);
/*  549: 851 */       return (PlasticTheme)cl.newInstance();
/*  550:     */     }
/*  551:     */     catch (ClassNotFoundException e) {}catch (IllegalAccessException e) {}catch (InstantiationException e) {}
/*  552: 859 */     LookUtils.log("Can't create theme " + className);
/*  553: 860 */     return null;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public static void installTheme(PlasticTheme theme)
/*  557:     */   {
/*  558: 870 */     if (null == installedThemes) {
/*  559: 871 */       installDefaultThemes();
/*  560:     */     }
/*  561: 872 */     installedThemes.add(theme);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public static PlasticTheme getPlasticTheme()
/*  565:     */   {
/*  566: 887 */     if (LookUtils.IS_JAVA_5_OR_LATER)
/*  567:     */     {
/*  568: 888 */       MetalTheme theme = getCurrentTheme0();
/*  569: 889 */       if ((theme instanceof PlasticTheme)) {
/*  570: 890 */         return (PlasticTheme)theme;
/*  571:     */       }
/*  572:     */     }
/*  573: 892 */     PlasticTheme uimanagerTheme = (PlasticTheme)UIManager.get(THEME_KEY);
/*  574: 893 */     if (uimanagerTheme != null) {
/*  575: 894 */       return uimanagerTheme;
/*  576:     */     }
/*  577: 896 */     PlasticTheme initialTheme = createMyDefaultTheme();
/*  578: 897 */     setPlasticTheme(initialTheme);
/*  579: 898 */     return initialTheme;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public static void setPlasticTheme(PlasticTheme theme)
/*  583:     */   {
/*  584: 916 */     if (theme == null) {
/*  585: 917 */       throw new NullPointerException("The theme must not be null.");
/*  586:     */     }
/*  587: 919 */     UIManager.put(THEME_KEY, theme);
/*  588:     */     
/*  589: 921 */     setCurrentTheme(theme);
/*  590:     */   }
/*  591:     */   
/*  592:     */   public static BorderUIResource getInternalFrameBorder()
/*  593:     */   {
/*  594: 928 */     return new BorderUIResource(PlasticBorders.getInternalFrameBorder());
/*  595:     */   }
/*  596:     */   
/*  597:     */   public static BorderUIResource getPaletteBorder()
/*  598:     */   {
/*  599: 932 */     return new BorderUIResource(PlasticBorders.getPaletteBorder());
/*  600:     */   }
/*  601:     */   
/*  602:     */   public static ColorUIResource getPrimaryControlDarkShadow()
/*  603:     */   {
/*  604: 941 */     return getPlasticTheme().getPrimaryControlDarkShadow();
/*  605:     */   }
/*  606:     */   
/*  607:     */   public static ColorUIResource getPrimaryControlHighlight()
/*  608:     */   {
/*  609: 945 */     return getPlasticTheme().getPrimaryControlHighlight();
/*  610:     */   }
/*  611:     */   
/*  612:     */   public static ColorUIResource getPrimaryControlInfo()
/*  613:     */   {
/*  614: 949 */     return getPlasticTheme().getPrimaryControlInfo();
/*  615:     */   }
/*  616:     */   
/*  617:     */   public static ColorUIResource getPrimaryControlShadow()
/*  618:     */   {
/*  619: 953 */     return getPlasticTheme().getPrimaryControlShadow();
/*  620:     */   }
/*  621:     */   
/*  622:     */   public static ColorUIResource getPrimaryControl()
/*  623:     */   {
/*  624: 957 */     return getPlasticTheme().getPrimaryControl();
/*  625:     */   }
/*  626:     */   
/*  627:     */   public static ColorUIResource getControlHighlight()
/*  628:     */   {
/*  629: 961 */     return getPlasticTheme().getControlHighlight();
/*  630:     */   }
/*  631:     */   
/*  632:     */   public static ColorUIResource getControlDarkShadow()
/*  633:     */   {
/*  634: 965 */     return getPlasticTheme().getControlDarkShadow();
/*  635:     */   }
/*  636:     */   
/*  637:     */   public static ColorUIResource getControl()
/*  638:     */   {
/*  639: 969 */     return getPlasticTheme().getControl();
/*  640:     */   }
/*  641:     */   
/*  642:     */   public static ColorUIResource getFocusColor()
/*  643:     */   {
/*  644: 973 */     return getPlasticTheme().getFocusColor();
/*  645:     */   }
/*  646:     */   
/*  647:     */   public static ColorUIResource getMenuItemBackground()
/*  648:     */   {
/*  649: 977 */     return getPlasticTheme().getMenuItemBackground();
/*  650:     */   }
/*  651:     */   
/*  652:     */   public static ColorUIResource getMenuItemSelectedBackground()
/*  653:     */   {
/*  654: 981 */     return getPlasticTheme().getMenuItemSelectedBackground();
/*  655:     */   }
/*  656:     */   
/*  657:     */   public static ColorUIResource getMenuItemSelectedForeground()
/*  658:     */   {
/*  659: 985 */     return getPlasticTheme().getMenuItemSelectedForeground();
/*  660:     */   }
/*  661:     */   
/*  662:     */   public static ColorUIResource getWindowTitleBackground()
/*  663:     */   {
/*  664: 989 */     return getPlasticTheme().getWindowTitleBackground();
/*  665:     */   }
/*  666:     */   
/*  667:     */   public static ColorUIResource getWindowTitleForeground()
/*  668:     */   {
/*  669: 993 */     return getPlasticTheme().getWindowTitleForeground();
/*  670:     */   }
/*  671:     */   
/*  672:     */   public static ColorUIResource getWindowTitleInactiveBackground()
/*  673:     */   {
/*  674: 997 */     return getPlasticTheme().getWindowTitleInactiveBackground();
/*  675:     */   }
/*  676:     */   
/*  677:     */   public static ColorUIResource getWindowTitleInactiveForeground()
/*  678:     */   {
/*  679:1001 */     return getPlasticTheme().getWindowTitleInactiveForeground();
/*  680:     */   }
/*  681:     */   
/*  682:     */   public static ColorUIResource getSimpleInternalFrameForeground()
/*  683:     */   {
/*  684:1005 */     return getPlasticTheme().getSimpleInternalFrameForeground();
/*  685:     */   }
/*  686:     */   
/*  687:     */   public static ColorUIResource getSimpleInternalFrameBackground()
/*  688:     */   {
/*  689:1009 */     return getPlasticTheme().getSimpleInternalFrameBackground();
/*  690:     */   }
/*  691:     */   
/*  692:     */   public static ColorUIResource getTitleTextColor()
/*  693:     */   {
/*  694:1013 */     return getPlasticTheme().getTitleTextColor();
/*  695:     */   }
/*  696:     */   
/*  697:     */   public static FontUIResource getTitleTextFont()
/*  698:     */   {
/*  699:1017 */     return getPlasticTheme().getTitleTextFont();
/*  700:     */   }
/*  701:     */   
/*  702:     */   private static MetalTheme getCurrentTheme0()
/*  703:     */   {
/*  704:1024 */     if (getCurrentThemeMethod != null) {
/*  705:     */       try
/*  706:     */       {
/*  707:1026 */         return (MetalTheme)getCurrentThemeMethod.invoke(null, null);
/*  708:     */       }
/*  709:     */       catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*  710:     */     }
/*  711:1035 */     return null;
/*  712:     */   }
/*  713:     */   
/*  714:     */   private static Method getMethodGetCurrentTheme()
/*  715:     */   {
/*  716:     */     try
/*  717:     */     {
/*  718:1041 */       Class clazz = MetalLookAndFeel.class;
/*  719:1042 */       return clazz.getMethod("getCurrentTheme", new Class[0]);
/*  720:     */     }
/*  721:     */     catch (SecurityException e) {}catch (NoSuchMethodException e) {}
/*  722:1048 */     return null;
/*  723:     */   }
/*  724:     */   
/*  725:     */   private static Object[] append(Object[] source, String key, Object value)
/*  726:     */   {
/*  727:1061 */     int length = source.length;
/*  728:1062 */     Object[] destination = new Object[length + 2];
/*  729:1063 */     System.arraycopy(source, 0, destination, 0, length);
/*  730:1064 */     destination[length] = key;
/*  731:1065 */     destination[(length + 1)] = value;
/*  732:1066 */     return destination;
/*  733:     */   }
/*  734:     */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticLookAndFeel
 * JD-Core Version:    0.7.0.1
 */