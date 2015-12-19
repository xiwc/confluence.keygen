/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import com.jgoodies.looks.MicroLayout;
/*     */ import com.jgoodies.looks.MicroLayoutPolicy;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlasticXPLookAndFeel
/*     */   extends Plastic3DLookAndFeel
/*     */ {
/*     */   public String getID()
/*     */   {
/*  61 */     return "JGoodies Plastic XP";
/*     */   }
/*     */   
/*     */   public String getName() {
/*  65 */     return "JGoodies Plastic XP";
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  69 */     return "The JGoodies Plastic XP Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initClassDefaults(UIDefaults table)
/*     */   {
/*  80 */     super.initClassDefaults(table);
/*     */     
/*  82 */     String uiClassnamePrefix = "com.jgoodies.looks.plastic.PlasticXP";
/*  83 */     Object[] uiDefaults = { "CheckBoxUI", "com.jgoodies.looks.plastic.PlasticXPCheckBoxUI", "RadioButtonUI", "com.jgoodies.looks.plastic.PlasticXPRadioButtonUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticXPSpinnerUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticXPToolBarUI" };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */     table.putDefaults(uiDefaults);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void initComponentDefaults(UIDefaults table)
/*     */   {
/* 106 */     super.initComponentDefaults(table);
/*     */     
/* 108 */     MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
/* 109 */     Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
/*     */     
/* 111 */     Object buttonBorder = PlasticXPBorders.getButtonBorder(buttonBorderInsets);
/* 112 */     Object toggleButtonBorder = PlasticXPBorders.getToggleButtonBorder(buttonBorderInsets);
/* 113 */     Object checkBoxIcon = PlasticXPIconFactory.getCheckBoxIcon();
/* 114 */     Object comboBoxButtonBorder = PlasticXPBorders.getComboBoxArrowButtonBorder();
/* 115 */     Border comboBoxEditorBorder = PlasticXPBorders.getComboBoxEditorBorder();
/* 116 */     Object radioButtonIcon = PlasticXPIconFactory.getRadioButtonIcon();
/* 117 */     Object scrollPaneBorder = PlasticXPBorders.getScrollPaneBorder();
/* 118 */     Object textFieldBorder = PlasticXPBorders.getTextFieldBorder();
/* 119 */     Object spinnerBorder = PlasticXPBorders.getSpinnerBorder();
/*     */     
/* 121 */     String radioCheckIconName = LookUtils.IS_LOW_RESOLUTION ? "icons/RadioLight5x5.png" : "icons/RadioLight7x7.png";
/*     */     
/*     */ 
/*     */ 
/* 125 */     Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
/*     */     
/* 127 */     Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
/* 128 */     int comboBorderSize = comboEditorBorderInsets.left;
/* 129 */     int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
/* 130 */     int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
/* 131 */     Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
/* 132 */     Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
/*     */     
/* 134 */     Object[] defaults = { "Button.border", buttonBorder, "Button.borderPaintsFocus", Boolean.TRUE, "CheckBox.icon", checkBoxIcon, "CheckBox.check", getToggleButtonCheckColor(), "ComboBox.arrowButtonBorder", comboBoxButtonBorder, "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.borderPaintsFocus", Boolean.TRUE, "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, "FormattedTextField.border", textFieldBorder, "PasswordField.border", textFieldBorder, "Spinner.border", spinnerBorder, "ScrollPane.border", scrollPaneBorder, "Table.scrollPaneBorder", scrollPaneBorder, "RadioButton.icon", radioButtonIcon, "RadioButton.check", getToggleButtonCheckColor(), "RadioButton.interiorBackground", getControlHighlight(), "RadioButton.checkIcon", makeIcon(getClass(), radioCheckIconName), "TextField.border", textFieldBorder, "ToggleButton.border", toggleButtonBorder, "ToggleButton.borderPaintsFocus", Boolean.TRUE, "Tree.expandedIcon", makeIcon(getClass(), "icons/TreeExpanded.png"), "Tree.collapsedIcon", makeIcon(getClass(), "icons/TreeCollapsed.png") };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 168 */     table.putDefaults(defaults);
/*     */   }
/*     */   
/*     */   protected static void installDefaultThemes() {}
/*     */   
/*     */   private ColorUIResource getToggleButtonCheckColor() {
/* 174 */     return getPlasticTheme().getToggleButtonCheckColor();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticXPLookAndFeel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */