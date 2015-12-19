/*      */ package com.jgoodies.looks.plastic;
/*      */ 
/*      */ import com.jgoodies.looks.FontPolicies;
/*      */ import com.jgoodies.looks.FontPolicy;
/*      */ import com.jgoodies.looks.LookUtils;
/*      */ import com.jgoodies.looks.MicroLayout;
/*      */ import com.jgoodies.looks.MicroLayoutPolicies;
/*      */ import com.jgoodies.looks.MicroLayoutPolicy;
/*      */ import com.jgoodies.looks.common.MinimumSizedIcon;
/*      */ import com.jgoodies.looks.common.RGBGrayFilter;
/*      */ import com.jgoodies.looks.common.ShadowPopupFactory;
/*      */ import com.jgoodies.looks.plastic.theme.SkyBluer;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.plaf.BorderUIResource;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.IconUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.basic.BasicBorders.MarginBorder;
/*      */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*      */ import javax.swing.plaf.metal.MetalTheme;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PlasticLookAndFeel
/*      */   extends MetalLookAndFeel
/*      */ {
/*      */   public static final String BORDER_STYLE_KEY = "Plastic.borderStyle";
/*      */   public static final String IS_3D_KEY = "Plastic.is3D";
/*      */   public static final String DEFAULT_THEME_KEY = "Plastic.defaultTheme";
/*      */   public static final String HIGH_CONTRAST_FOCUS_ENABLED_KEY = "Plastic.highContrastFocus";
/*      */   protected static final String TAB_STYLE_KEY = "Plastic.tabStyle";
/*      */   public static final String TAB_STYLE_DEFAULT_VALUE = "default";
/*      */   public static final String TAB_STYLE_METAL_VALUE = "metal";
/*  127 */   private static final Object THEME_KEY = new StringBuffer("Plastic.theme");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  135 */   private static boolean useMetalTabs = LookUtils.getSystemProperty("Plastic.tabStyle", "").equalsIgnoreCase("metal");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  142 */   private static boolean useHighContrastFocusColors = LookUtils.getSystemProperty("Plastic.highContrastFocus") != null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List installedThemes;
/*      */   
/*      */ 
/*      */ 
/*  151 */   private static boolean is3DEnabled = false;
/*      */   
/*      */ 
/*  154 */   private static boolean selectTextOnKeyboardFocusGained = LookUtils.IS_OS_WINDOWS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  161 */   private static Method getCurrentThemeMethod = null;
/*      */   private static final String THEME_CLASSNAME_PREFIX = "com.jgoodies.looks.plastic.theme.";
/*      */   
/*  164 */   static { if (LookUtils.IS_JAVA_5_OR_LATER) {
/*  165 */       getCurrentThemeMethod = getMethodGetCurrentTheme();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PlasticLookAndFeel()
/*      */   {
/*  176 */     getPlasticTheme();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getID()
/*      */   {
/*  183 */     return "JGoodies Plastic";
/*      */   }
/*      */   
/*      */   public String getName() {
/*  187 */     return "JGoodies Plastic";
/*      */   }
/*      */   
/*      */   public String getDescription() {
/*  191 */     return "The JGoodies Plastic Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static FontPolicy getFontPolicy()
/*      */   {
/*  219 */     FontPolicy policy = (FontPolicy)UIManager.get("Plastic.fontPolicy");
/*      */     
/*  221 */     if (policy != null) {
/*  222 */       return policy;
/*      */     }
/*  224 */     FontPolicy defaultPolicy = FontPolicies.getDefaultPlasticPolicy();
/*  225 */     return FontPolicies.customSettingsPolicy(defaultPolicy);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setFontPolicy(FontPolicy fontPolicy)
/*      */   {
/*  242 */     UIManager.put("Plastic.fontPolicy", fontPolicy);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static MicroLayoutPolicy getMicroLayoutPolicy()
/*      */   {
/*  260 */     MicroLayoutPolicy policy = (MicroLayoutPolicy)UIManager.get("Plastic.MicroLayoutPolicy");
/*      */     
/*  262 */     return policy != null ? policy : MicroLayoutPolicies.getDefaultPlasticPolicy();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setMicroLayoutPolicy(MicroLayout microLayoutPolicy)
/*      */   {
/*  280 */     UIManager.put("Plastic.MicroLayoutPolicy", microLayoutPolicy);
/*      */   }
/*      */   
/*      */   protected boolean is3DEnabled()
/*      */   {
/*  285 */     return is3DEnabled;
/*      */   }
/*      */   
/*      */   public static void set3DEnabled(boolean b) {
/*  289 */     is3DEnabled = b;
/*      */   }
/*      */   
/*      */   public static String getTabStyle() {
/*  293 */     return useMetalTabs ? "metal" : "default";
/*      */   }
/*      */   
/*      */   public static void setTabStyle(String tabStyle) {
/*  297 */     useMetalTabs = tabStyle.equalsIgnoreCase("metal");
/*      */   }
/*      */   
/*      */   public static boolean getHighContrastFocusColorsEnabled() {
/*  301 */     return useHighContrastFocusColors;
/*      */   }
/*      */   
/*      */   public static void setHighContrastFocusColorsEnabled(boolean b) {
/*  305 */     useHighContrastFocusColors = b;
/*      */   }
/*      */   
/*      */   public static boolean isSelectTextOnKeyboardFocusGained() {
/*  309 */     return selectTextOnKeyboardFocusGained;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setSelectTextOnKeyboardFocusGained(boolean b)
/*      */   {
/*  321 */     selectTextOnKeyboardFocusGained = b;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void initialize()
/*      */   {
/*  334 */     super.initialize();
/*  335 */     ShadowPopupFactory.install();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void uninitialize()
/*      */   {
/*  346 */     super.uninitialize();
/*  347 */     ShadowPopupFactory.uninstall();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Icon getDisabledIcon(JComponent component, Icon icon)
/*      */   {
/*  362 */     Icon disabledIcon = RGBGrayFilter.getDisabledIcon(component, icon);
/*  363 */     return disabledIcon != null ? new IconUIResource(disabledIcon) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initClassDefaults(UIDefaults table)
/*      */   {
/*  377 */     super.initClassDefaults(table);
/*      */     
/*  379 */     String plasticPrefix = "com.jgoodies.looks.plastic.Plastic";
/*  380 */     String commonPrefix = "com.jgoodies.looks.common.ExtBasic";
/*      */     
/*      */ 
/*  383 */     Object[] uiDefaults = { "ButtonUI", "com.jgoodies.looks.plastic.PlasticButtonUI", "ToggleButtonUI", "com.jgoodies.looks.plastic.PlasticToggleButtonUI", "ComboBoxUI", "com.jgoodies.looks.plastic.PlasticComboBoxUI", "ScrollBarUI", "com.jgoodies.looks.plastic.PlasticScrollBarUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticSpinnerUI", "MenuBarUI", "com.jgoodies.looks.plastic.PlasticMenuBarUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticToolBarUI", "MenuUI", "com.jgoodies.looks.plastic.PlasticMenuUI", "MenuItemUI", "com.jgoodies.looks.common.ExtBasicMenuItemUI", "CheckBoxMenuItemUI", "com.jgoodies.looks.common.ExtBasicCheckBoxMenuItemUI", "RadioButtonMenuItemUI", "com.jgoodies.looks.common.ExtBasicRadioButtonMenuItemUI", "PopupMenuUI", "com.jgoodies.looks.plastic.PlasticPopupMenuUI", "PopupMenuSeparatorUI", "com.jgoodies.looks.common.ExtBasicPopupMenuSeparatorUI", "OptionPaneUI", "com.jgoodies.looks.plastic.PlasticOptionPaneUI", "ScrollPaneUI", "com.jgoodies.looks.plastic.PlasticScrollPaneUI", "SplitPaneUI", "com.jgoodies.looks.plastic.PlasticSplitPaneUI", "PasswordFieldUI", "com.jgoodies.looks.plastic.PlasticPasswordFieldUI", "TextAreaUI", "com.jgoodies.looks.plastic.PlasticTextAreaUI", "TreeUI", "com.jgoodies.looks.plastic.PlasticTreeUI", "InternalFrameUI", "com.jgoodies.looks.plastic.PlasticInternalFrameUI", "SeparatorUI", "com.jgoodies.looks.plastic.PlasticSeparatorUI", "ToolBarSeparatorUI", "com.jgoodies.looks.plastic.PlasticToolBarSeparatorUI", "FileChooserUI", "com.jgoodies.looks.plastic.PlasticFileChooserUI" };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  439 */     if (!useMetalTabs)
/*      */     {
/*  441 */       uiDefaults = append(uiDefaults, "TabbedPaneUI", "com.jgoodies.looks.plastic.PlasticTabbedPaneUI");
/*      */     }
/*      */     
/*  444 */     if (isSelectTextOnKeyboardFocusGained())
/*      */     {
/*  446 */       uiDefaults = append(uiDefaults, "TextFieldUI", "com.jgoodies.looks.plastic.PlasticTextFieldUI");
/*      */       
/*  448 */       uiDefaults = append(uiDefaults, "FormattedTextFieldUI", "com.jgoodies.looks.plastic.PlasticFormattedTextFieldUI");
/*      */     }
/*      */     
/*  451 */     table.putDefaults(uiDefaults);
/*      */   }
/*      */   
/*      */   protected void initComponentDefaults(UIDefaults table)
/*      */   {
/*  456 */     super.initComponentDefaults(table);
/*      */     
/*  458 */     MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
/*  459 */     Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
/*      */     
/*  461 */     Object marginBorder = new BasicBorders.MarginBorder();
/*      */     
/*  463 */     Object buttonBorder = PlasticBorders.getButtonBorder(buttonBorderInsets);
/*  464 */     Object comboBoxButtonBorder = PlasticBorders.getComboBoxArrowButtonBorder();
/*  465 */     Border comboBoxEditorBorder = PlasticBorders.getComboBoxEditorBorder();
/*  466 */     Object menuItemBorder = PlasticBorders.getMenuItemBorder();
/*  467 */     Object textFieldBorder = PlasticBorders.getTextFieldBorder();
/*  468 */     Object toggleButtonBorder = PlasticBorders.getToggleButtonBorder(buttonBorderInsets);
/*      */     
/*  470 */     Object scrollPaneBorder = PlasticBorders.getScrollPaneBorder();
/*  471 */     Object tableHeaderBorder = new BorderUIResource((Border)table.get("TableHeader.cellBorder"));
/*      */     
/*      */ 
/*  474 */     Object menuBarEmptyBorder = marginBorder;
/*  475 */     Object menuBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
/*  476 */     Object menuBarEtchedBorder = PlasticBorders.getEtchedBorder();
/*  477 */     Object menuBarHeaderBorder = PlasticBorders.getMenuBarHeaderBorder();
/*      */     
/*  479 */     Object toolBarEmptyBorder = marginBorder;
/*  480 */     Object toolBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
/*  481 */     Object toolBarEtchedBorder = PlasticBorders.getEtchedBorder();
/*  482 */     Object toolBarHeaderBorder = PlasticBorders.getToolBarHeaderBorder();
/*      */     
/*  484 */     Object internalFrameBorder = getInternalFrameBorder();
/*  485 */     Object paletteBorder = getPaletteBorder();
/*      */     
/*  487 */     Color controlColor = table.getColor("control");
/*      */     
/*  489 */     Object checkBoxIcon = PlasticIconFactory.getCheckBoxIcon();
/*  490 */     Object checkBoxMargin = microLayout.getCheckBoxMargin();
/*      */     
/*  492 */     Object buttonMargin = microLayout.getButtonMargin();
/*  493 */     Object textInsets = microLayout.getTextInsets();
/*  494 */     Object wrappedTextInsets = microLayout.getWrappedTextInsets();
/*  495 */     Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
/*      */     
/*  497 */     Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
/*  498 */     int comboBorderSize = comboEditorBorderInsets.left;
/*  499 */     int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
/*  500 */     int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
/*  501 */     Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
/*  502 */     Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
/*      */     
/*  504 */     Object menuItemMargin = microLayout.getMenuItemMargin();
/*  505 */     Object menuMargin = microLayout.getMenuMargin();
/*      */     
/*  507 */     Icon menuItemCheckIcon = new MinimumSizedIcon();
/*  508 */     Icon checkBoxMenuItemIcon = PlasticIconFactory.getCheckBoxMenuItemIcon();
/*  509 */     Icon radioButtonMenuItemIcon = PlasticIconFactory.getRadioButtonMenuItemIcon();
/*      */     
/*  511 */     Color menuItemForeground = table.getColor("MenuItem.foreground");
/*      */     
/*  513 */     Color inactiveTextBackground = table.getColor("TextField.inactiveBackground");
/*      */     
/*      */ 
/*  516 */     int treeFontSize = table.getFont("Tree.font").getSize();
/*  517 */     Integer rowHeight = new Integer(treeFontSize + 6);
/*  518 */     Object treeExpandedIcon = PlasticIconFactory.getExpandedTreeIcon();
/*  519 */     Object treeCollapsedIcon = PlasticIconFactory.getCollapsedTreeIcon();
/*  520 */     ColorUIResource gray = new ColorUIResource(Color.GRAY);
/*      */     
/*  522 */     Boolean is3D = Boolean.valueOf(is3DEnabled());
/*      */     
/*  524 */     Character passwordEchoChar = new Character(LookUtils.IS_OS_WINDOWS ? '●' : '•');
/*      */     
/*  526 */     String iconPrefix = "icons/" + (LookUtils.IS_LOW_RESOLUTION ? "32x32/" : "48x48/");
/*  527 */     Object errorIcon = makeIcon(getClass(), iconPrefix + "dialog-error.png");
/*  528 */     Object informationIcon = makeIcon(getClass(), iconPrefix + "dialog-information.png");
/*  529 */     Object questionIcon = makeIcon(getClass(), iconPrefix + "dialog-question.png");
/*  530 */     Object warningIcon = makeIcon(getClass(), iconPrefix + "dialog-warning.png");
/*      */     
/*      */ 
/*  533 */     Object[] defaults = { "Button.border", buttonBorder, "Button.margin", buttonMargin, "CheckBox.margin", checkBoxMargin, "CheckBox.icon", checkBoxIcon, "CheckBoxMenuItem.border", menuItemBorder, "CheckBoxMenuItem.margin", menuItemMargin, "CheckBoxMenuItem.checkIcon", checkBoxMenuItemIcon, "CheckBoxMenuItem.background", getMenuItemBackground(), "CheckBoxMenuItem.selectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.selectionBackground", getMenuItemSelectedBackground(), "CheckBoxMenuItem.acceleratorForeground", menuItemForeground, "CheckBoxMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "ComboBox.selectionForeground", getMenuSelectedForeground(), "ComboBox.selectionBackground", getMenuSelectedBackground(), "ComboBox.arrowButtonBorder", comboBoxButtonBorder, "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.editorColumns", new Integer(5), "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.editorInsets", textInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, "EditorPane.margin", wrappedTextInsets, "InternalFrame.border", internalFrameBorder, "InternalFrame.paletteBorder", paletteBorder, "List.font", getControlTextFont(), "Menu.border", PlasticBorders.getMenuBorder(), "Menu.margin", menuMargin, "Menu.arrowIcon", PlasticIconFactory.getMenuArrowIcon(), "MenuBar.emptyBorder", menuBarEmptyBorder, "MenuBar.separatorBorder", menuBarSeparatorBorder, "MenuBar.etchedBorder", menuBarEtchedBorder, "MenuBar.headerBorder", menuBarHeaderBorder, "MenuItem.border", menuItemBorder, "MenuItem.checkIcon", menuItemCheckIcon, "MenuItem.margin", menuItemMargin, "MenuItem.background", getMenuItemBackground(), "MenuItem.selectionForeground", getMenuItemSelectedForeground(), "MenuItem.selectionBackground", getMenuItemSelectedBackground(), "MenuItem.acceleratorForeground", menuItemForeground, "MenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "MenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "OptionPane.errorIcon", errorIcon, "OptionPane.informationIcon", informationIcon, "OptionPane.questionIcon", questionIcon, "OptionPane.warningIcon", warningIcon, "FileView.computerIcon", makeIcon(getClass(), "icons/Computer.gif"), "FileView.directoryIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), "FileView.fileIcon", makeIcon(getClass(), "icons/File.gif"), "FileView.floppyDriveIcon", makeIcon(getClass(), "icons/FloppyDrive.gif"), "FileView.hardDriveIcon", makeIcon(getClass(), "icons/HardDrive.gif"), "FileChooser.homeFolderIcon", makeIcon(getClass(), "icons/HomeFolder.gif"), "FileChooser.newFolderIcon", makeIcon(getClass(), "icons/NewFolder.gif"), "FileChooser.upFolderIcon", makeIcon(getClass(), "icons/UpFolder.gif"), "Tree.closedIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), "Tree.openIcon", makeIcon(getClass(), "icons/TreeOpen.gif"), "Tree.leafIcon", makeIcon(getClass(), "icons/TreeLeaf.gif"), "FormattedTextField.border", textFieldBorder, "FormattedTextField.margin", textInsets, "PasswordField.border", textFieldBorder, "PasswordField.margin", textInsets, "PasswordField.echoChar", passwordEchoChar, "PopupMenu.border", PlasticBorders.getPopupMenuBorder(), "PopupMenu.noMarginBorder", PlasticBorders.getNoMarginPopupMenuBorder(), "PopupMenuSeparator.margin", new InsetsUIResource(3, 4, 3, 4), "RadioButton.margin", checkBoxMargin, "RadioButtonMenuItem.border", menuItemBorder, "RadioButtonMenuItem.checkIcon", radioButtonMenuItemIcon, "RadioButtonMenuItem.margin", menuItemMargin, "RadioButtonMenuItem.background", getMenuItemBackground(), "RadioButtonMenuItem.selectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.selectionBackground", getMenuItemSelectedBackground(), "RadioButtonMenuItem.acceleratorForeground", menuItemForeground, "RadioButtonMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "Separator.foreground", getControlDarkShadow(), "ScrollPane.border", scrollPaneBorder, "ScrollPane.etchedBorder", scrollPaneBorder, "SimpleInternalFrame.activeTitleForeground", getSimpleInternalFrameForeground(), "SimpleInternalFrame.activeTitleBackground", getSimpleInternalFrameBackground(), "Spinner.border", PlasticBorders.getFlush3DBorder(), "Spinner.defaultEditorInsets", textInsets, "SplitPane.dividerSize", new Integer(7), "TabbedPane.focus", getFocusColor(), "TabbedPane.tabInsets", new InsetsUIResource(1, 9, 1, 8), "Table.foreground", table.get("textText"), "Table.gridColor", controlColor, "Table.scrollPaneBorder", scrollPaneBorder, "TableHeader.cellBorder", tableHeaderBorder, "TextArea.inactiveBackground", inactiveTextBackground, "TextArea.margin", wrappedTextInsets, "TextField.border", textFieldBorder, "TextField.margin", textInsets, "TitledBorder.font", getTitleTextFont(), "TitledBorder.titleColor", getTitleTextColor(), "ToggleButton.border", toggleButtonBorder, "ToggleButton.margin", buttonMargin, "ToolBar.emptyBorder", toolBarEmptyBorder, "ToolBar.separatorBorder", toolBarSeparatorBorder, "ToolBar.etchedBorder", toolBarEtchedBorder, "ToolBar.headerBorder", toolBarHeaderBorder, "ToolTip.hideAccelerator", Boolean.TRUE, "Tree.expandedIcon", treeExpandedIcon, "Tree.collapsedIcon", treeCollapsedIcon, "Tree.line", gray, "Tree.hash", gray, "Tree.rowHeight", rowHeight, "Button.is3DEnabled", is3D, "ComboBox.is3DEnabled", is3D, "MenuBar.is3DEnabled", is3D, "ToolBar.is3DEnabled", is3D, "ScrollBar.is3DEnabled", is3D, "ToggleButton.is3DEnabled", is3D, "CheckBox.border", marginBorder, "RadioButton.border", marginBorder, "ProgressBar.selectionForeground", getSystemTextColor(), "ProgressBar.selectionBackground", getSystemTextColor() };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  684 */     table.putDefaults(defaults);
/*      */     
/*      */ 
/*  687 */     String soundPathPrefix = "/javax/swing/plaf/metal/";
/*  688 */     Object[] auditoryCues = (Object[])table.get("AuditoryCues.allAuditoryCues");
/*  689 */     if (auditoryCues != null) {
/*  690 */       Object[] audioDefaults = new String[auditoryCues.length * 2];
/*  691 */       for (int i = 0; i < auditoryCues.length; i++) {
/*  692 */         Object auditoryCue = auditoryCues[i];
/*  693 */         audioDefaults[(2 * i)] = auditoryCue;
/*  694 */         audioDefaults[(2 * i + 1)] = (soundPathPrefix + table.getString(auditoryCue));
/*      */       }
/*  696 */       table.putDefaults(audioDefaults);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void initSystemColorDefaults(UIDefaults table)
/*      */   {
/*  708 */     super.initSystemColorDefaults(table);
/*  709 */     table.put("unifiedControlShadow", table.getColor("controlDkShadow"));
/*  710 */     table.put("primaryControlHighlight", getPrimaryControlHighlight());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static PlasticTheme createMyDefaultTheme()
/*      */   {
/*      */     String defaultName;
/*      */     
/*      */ 
/*      */ 
/*      */     String defaultName;
/*      */     
/*      */ 
/*      */ 
/*  726 */     if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED) {
/*  727 */       defaultName = getDefaultXPTheme(); } else { String defaultName;
/*  728 */       if (LookUtils.IS_OS_WINDOWS_MODERN) {
/*  729 */         defaultName = "DesertBluer";
/*      */       } else {
/*  731 */         defaultName = "SkyBlue";
/*      */       }
/*      */     }
/*  734 */     String userName = LookUtils.getSystemProperty("Plastic.defaultTheme", "");
/*  735 */     boolean overridden = userName.length() > 0;
/*  736 */     String themeName = overridden ? userName : defaultName;
/*  737 */     PlasticTheme theme = createTheme(themeName);
/*  738 */     PlasticTheme result = theme != null ? theme : new SkyBluer();
/*      */     
/*      */ 
/*  741 */     if (overridden) {
/*  742 */       String className = result.getClass().getName().substring("com.jgoodies.looks.plastic.theme.".length());
/*      */       
/*  744 */       if (className.equals(userName)) {
/*  745 */         LookUtils.log("I have successfully installed the '" + result.getName() + "' theme.");
/*      */       } else {
/*  747 */         LookUtils.log("I could not install the Plastic theme '" + userName + "'.");
/*  748 */         LookUtils.log("I have installed the '" + result.getName() + "' theme, instead.");
/*      */       }
/*      */     }
/*  751 */     return result;
/*      */   }
/*      */   
/*      */   private static String getDefaultXPTheme()
/*      */   {
/*  756 */     String fallbackName = "ExperienceBlue";
/*  757 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  758 */     String xpstyleDll = (String)toolkit.getDesktopProperty("win.xpstyle.dllName");
/*  759 */     if (xpstyleDll == null) {
/*  760 */       return fallbackName;
/*      */     }
/*  762 */     boolean isStyleLuna = xpstyleDll.endsWith("luna.msstyles");
/*  763 */     boolean isStyleRoyale = xpstyleDll.endsWith("Royale.msstyles");
/*  764 */     boolean isStyleAero = xpstyleDll.endsWith("Aero.msstyles");
/*  765 */     if (isStyleRoyale) {
/*  766 */       return "ExperienceRoyale";
/*      */     }
/*  768 */     if (isStyleLuna) {
/*  769 */       String xpstyleColorName = (String)toolkit.getDesktopProperty("win.xpstyle.colorName");
/*  770 */       if (xpstyleColorName == null) {
/*  771 */         return fallbackName;
/*      */       }
/*  773 */       if (xpstyleColorName.equalsIgnoreCase("HomeStead"))
/*  774 */         return "ExperienceGreen";
/*  775 */       if (xpstyleColorName.equalsIgnoreCase("Metallic")) {
/*  776 */         return "Silver";
/*      */       }
/*  778 */       return fallbackName;
/*      */     }
/*      */     
/*  781 */     if (isStyleAero) {
/*  782 */       return "LightGray";
/*      */     }
/*  784 */     return fallbackName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getInstalledThemes()
/*      */   {
/*  795 */     if (null == installedThemes) {
/*  796 */       installDefaultThemes();
/*      */     }
/*  798 */     Collections.sort(installedThemes, new Comparator() {
/*      */       public int compare(Object o1, Object o2) {
/*  800 */         MetalTheme theme1 = (MetalTheme)o1;
/*  801 */         MetalTheme theme2 = (MetalTheme)o2;
/*  802 */         return theme1.getName().compareTo(theme2.getName());
/*      */       }
/*      */       
/*  805 */     });
/*  806 */     return installedThemes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void installDefaultThemes()
/*      */   {
/*  814 */     installedThemes = new ArrayList();
/*  815 */     String[] themeNames = { "BrownSugar", "DarkStar", "DesertBlue", "DesertBluer", "DesertGreen", "DesertRed", "DesertYellow", "ExperienceBlue", "ExperienceGreen", "ExperienceRoyale", "LightGray", "Silver", "SkyBlue", "SkyBluer", "SkyGreen", "SkyKrupp", "SkyPink", "SkyRed", "SkyYellow" };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  835 */     for (int i = themeNames.length - 1; i >= 0; i--) {
/*  836 */       installTheme(createTheme(themeNames[i]));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static PlasticTheme createTheme(String themeName)
/*      */   {
/*  848 */     String className = "com.jgoodies.looks.plastic.theme." + themeName;
/*      */     try {
/*  850 */       Class cl = Class.forName(className);
/*  851 */       return (PlasticTheme)cl.newInstance();
/*      */     }
/*      */     catch (ClassNotFoundException e) {}catch (IllegalAccessException e) {}catch (InstantiationException e) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  859 */     LookUtils.log("Can't create theme " + className);
/*  860 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void installTheme(PlasticTheme theme)
/*      */   {
/*  870 */     if (null == installedThemes)
/*  871 */       installDefaultThemes();
/*  872 */     installedThemes.add(theme);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static PlasticTheme getPlasticTheme()
/*      */   {
/*  887 */     if (LookUtils.IS_JAVA_5_OR_LATER) {
/*  888 */       MetalTheme theme = getCurrentTheme0();
/*  889 */       if ((theme instanceof PlasticTheme))
/*  890 */         return (PlasticTheme)theme;
/*      */     }
/*  892 */     PlasticTheme uimanagerTheme = (PlasticTheme)UIManager.get(THEME_KEY);
/*  893 */     if (uimanagerTheme != null) {
/*  894 */       return uimanagerTheme;
/*      */     }
/*  896 */     PlasticTheme initialTheme = createMyDefaultTheme();
/*  897 */     setPlasticTheme(initialTheme);
/*  898 */     return initialTheme;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setPlasticTheme(PlasticTheme theme)
/*      */   {
/*  916 */     if (theme == null) {
/*  917 */       throw new NullPointerException("The theme must not be null.");
/*      */     }
/*  919 */     UIManager.put(THEME_KEY, theme);
/*      */     
/*  921 */     setCurrentTheme(theme);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static BorderUIResource getInternalFrameBorder()
/*      */   {
/*  928 */     return new BorderUIResource(PlasticBorders.getInternalFrameBorder());
/*      */   }
/*      */   
/*      */   public static BorderUIResource getPaletteBorder() {
/*  932 */     return new BorderUIResource(PlasticBorders.getPaletteBorder());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ColorUIResource getPrimaryControlDarkShadow()
/*      */   {
/*  941 */     return getPlasticTheme().getPrimaryControlDarkShadow();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getPrimaryControlHighlight() {
/*  945 */     return getPlasticTheme().getPrimaryControlHighlight();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getPrimaryControlInfo() {
/*  949 */     return getPlasticTheme().getPrimaryControlInfo();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getPrimaryControlShadow() {
/*  953 */     return getPlasticTheme().getPrimaryControlShadow();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getPrimaryControl() {
/*  957 */     return getPlasticTheme().getPrimaryControl();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getControlHighlight() {
/*  961 */     return getPlasticTheme().getControlHighlight();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getControlDarkShadow() {
/*  965 */     return getPlasticTheme().getControlDarkShadow();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getControl() {
/*  969 */     return getPlasticTheme().getControl();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getFocusColor() {
/*  973 */     return getPlasticTheme().getFocusColor();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getMenuItemBackground() {
/*  977 */     return getPlasticTheme().getMenuItemBackground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getMenuItemSelectedBackground() {
/*  981 */     return getPlasticTheme().getMenuItemSelectedBackground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getMenuItemSelectedForeground() {
/*  985 */     return getPlasticTheme().getMenuItemSelectedForeground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getWindowTitleBackground() {
/*  989 */     return getPlasticTheme().getWindowTitleBackground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getWindowTitleForeground() {
/*  993 */     return getPlasticTheme().getWindowTitleForeground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getWindowTitleInactiveBackground() {
/*  997 */     return getPlasticTheme().getWindowTitleInactiveBackground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getWindowTitleInactiveForeground() {
/* 1001 */     return getPlasticTheme().getWindowTitleInactiveForeground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getSimpleInternalFrameForeground() {
/* 1005 */     return getPlasticTheme().getSimpleInternalFrameForeground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getSimpleInternalFrameBackground() {
/* 1009 */     return getPlasticTheme().getSimpleInternalFrameBackground();
/*      */   }
/*      */   
/*      */   public static ColorUIResource getTitleTextColor() {
/* 1013 */     return getPlasticTheme().getTitleTextColor();
/*      */   }
/*      */   
/*      */   public static FontUIResource getTitleTextFont() {
/* 1017 */     return getPlasticTheme().getTitleTextFont();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static MetalTheme getCurrentTheme0()
/*      */   {
/* 1024 */     if (getCurrentThemeMethod != null) {
/*      */       try {
/* 1026 */         return (MetalTheme)getCurrentThemeMethod.invoke(null, null);
/*      */       }
/*      */       catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1035 */     return null;
/*      */   }
/*      */   
/*      */   private static Method getMethodGetCurrentTheme()
/*      */   {
/*      */     try {
/* 1041 */       Class clazz = MetalLookAndFeel.class;
/* 1042 */       return clazz.getMethod("getCurrentTheme", new Class[0]);
/*      */     }
/*      */     catch (SecurityException e) {}catch (NoSuchMethodException e) {}
/*      */     
/*      */ 
/*      */ 
/* 1048 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Object[] append(Object[] source, String key, Object value)
/*      */   {
/* 1061 */     int length = source.length;
/* 1062 */     Object[] destination = new Object[length + 2];
/* 1063 */     System.arraycopy(source, 0, destination, 0, length);
/* 1064 */     destination[length] = key;
/* 1065 */     destination[(length + 1)] = value;
/* 1066 */     return destination;
/*      */   }
/*      */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticLookAndFeel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */