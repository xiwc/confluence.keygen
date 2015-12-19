/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import com.jgoodies.looks.MicroLayout;
/*   5:    */ import com.jgoodies.looks.MicroLayoutPolicy;
/*   6:    */ import java.awt.Insets;
/*   7:    */ import javax.swing.UIDefaults;
/*   8:    */ import javax.swing.border.Border;
/*   9:    */ import javax.swing.border.EmptyBorder;
/*  10:    */ import javax.swing.plaf.ColorUIResource;
/*  11:    */ 
/*  12:    */ public class PlasticXPLookAndFeel
/*  13:    */   extends Plastic3DLookAndFeel
/*  14:    */ {
/*  15:    */   public String getID()
/*  16:    */   {
/*  17: 61 */     return "JGoodies Plastic XP";
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getName()
/*  21:    */   {
/*  22: 65 */     return "JGoodies Plastic XP";
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getDescription()
/*  26:    */   {
/*  27: 69 */     return "The JGoodies Plastic XP Look and Feel - © 2001-2009 JGoodies Karsten Lentzsch";
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void initClassDefaults(UIDefaults table)
/*  31:    */   {
/*  32: 80 */     super.initClassDefaults(table);
/*  33:    */     
/*  34: 82 */     String uiClassnamePrefix = "com.jgoodies.looks.plastic.PlasticXP";
/*  35: 83 */     Object[] uiDefaults = { "CheckBoxUI", "com.jgoodies.looks.plastic.PlasticXPCheckBoxUI", "RadioButtonUI", "com.jgoodies.looks.plastic.PlasticXPRadioButtonUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticXPSpinnerUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticXPToolBarUI" };
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 96 */     table.putDefaults(uiDefaults);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void initComponentDefaults(UIDefaults table)
/*  52:    */   {
/*  53:106 */     super.initComponentDefaults(table);
/*  54:    */     
/*  55:108 */     MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
/*  56:109 */     Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
/*  57:    */     
/*  58:111 */     Object buttonBorder = PlasticXPBorders.getButtonBorder(buttonBorderInsets);
/*  59:112 */     Object toggleButtonBorder = PlasticXPBorders.getToggleButtonBorder(buttonBorderInsets);
/*  60:113 */     Object checkBoxIcon = PlasticXPIconFactory.getCheckBoxIcon();
/*  61:114 */     Object comboBoxButtonBorder = PlasticXPBorders.getComboBoxArrowButtonBorder();
/*  62:115 */     Border comboBoxEditorBorder = PlasticXPBorders.getComboBoxEditorBorder();
/*  63:116 */     Object radioButtonIcon = PlasticXPIconFactory.getRadioButtonIcon();
/*  64:117 */     Object scrollPaneBorder = PlasticXPBorders.getScrollPaneBorder();
/*  65:118 */     Object textFieldBorder = PlasticXPBorders.getTextFieldBorder();
/*  66:119 */     Object spinnerBorder = PlasticXPBorders.getSpinnerBorder();
/*  67:    */     
/*  68:121 */     String radioCheckIconName = LookUtils.IS_LOW_RESOLUTION ? "icons/RadioLight5x5.png" : "icons/RadioLight7x7.png";
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:125 */     Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
/*  73:    */     
/*  74:127 */     Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
/*  75:128 */     int comboBorderSize = comboEditorBorderInsets.left;
/*  76:129 */     int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
/*  77:130 */     int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
/*  78:131 */     Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
/*  79:132 */     Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
/*  80:    */     
/*  81:134 */     Object[] defaults = { "Button.border", buttonBorder, "Button.borderPaintsFocus", Boolean.TRUE, "CheckBox.icon", checkBoxIcon, "CheckBox.check", getToggleButtonCheckColor(), "ComboBox.arrowButtonBorder", comboBoxButtonBorder, "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.borderPaintsFocus", Boolean.TRUE, "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, "FormattedTextField.border", textFieldBorder, "PasswordField.border", textFieldBorder, "Spinner.border", spinnerBorder, "ScrollPane.border", scrollPaneBorder, "Table.scrollPaneBorder", scrollPaneBorder, "RadioButton.icon", radioButtonIcon, "RadioButton.check", getToggleButtonCheckColor(), "RadioButton.interiorBackground", getControlHighlight(), "RadioButton.checkIcon", makeIcon(getClass(), radioCheckIconName), "TextField.border", textFieldBorder, "ToggleButton.border", toggleButtonBorder, "ToggleButton.borderPaintsFocus", Boolean.TRUE, "Tree.expandedIcon", makeIcon(getClass(), "icons/TreeExpanded.png"), "Tree.collapsedIcon", makeIcon(getClass(), "icons/TreeCollapsed.png") };
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:168 */     table.putDefaults(defaults);
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected static void installDefaultThemes() {}
/* 119:    */   
/* 120:    */   private ColorUIResource getToggleButtonCheckColor()
/* 121:    */   {
/* 122:174 */     return getPlasticTheme().getToggleButtonCheckColor();
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticXPLookAndFeel
 * JD-Core Version:    0.7.0.1
 */