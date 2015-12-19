/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import java.awt.Insets;
/*   4:    */ import javax.swing.plaf.InsetsUIResource;
/*   5:    */ 
/*   6:    */ public final class MicroLayout
/*   7:    */ {
/*   8:    */   private final InsetsUIResource textInsets;
/*   9:    */   private final InsetsUIResource wrappedTextInsets;
/*  10:    */   private final InsetsUIResource comboBoxEditorInsets;
/*  11:    */   private final Insets buttonBorderInsets;
/*  12:    */   private final InsetsUIResource buttonMargin;
/*  13:    */   private final InsetsUIResource commitButtonMargin;
/*  14:    */   private final int comboBorderSize;
/*  15:    */   private final int comboPopupBorderSize;
/*  16:    */   private final InsetsUIResource checkBoxMargin;
/*  17:    */   private final InsetsUIResource menuItemMargin;
/*  18:    */   private final InsetsUIResource menuMargin;
/*  19:    */   private final InsetsUIResource popupMenuSeparatorMargin;
/*  20:    */   
/*  21:    */   public MicroLayout(InsetsUIResource textInsets, InsetsUIResource wrappedTextInsets, InsetsUIResource comboBoxEditorInsets, int comboBorderSize, int comboPopupBorderSize, Insets buttonBorderInsets, InsetsUIResource buttonMargin, InsetsUIResource commitButtonMargin, InsetsUIResource checkBoxMargin, InsetsUIResource menuItemMargin, InsetsUIResource menuMargin, InsetsUIResource popupMenuSeparatorMargin)
/*  22:    */   {
/*  23: 76 */     this.textInsets = textInsets;
/*  24: 77 */     this.wrappedTextInsets = wrappedTextInsets;
/*  25: 78 */     this.comboBoxEditorInsets = comboBoxEditorInsets;
/*  26: 79 */     this.buttonBorderInsets = buttonBorderInsets;
/*  27: 80 */     this.buttonMargin = buttonMargin;
/*  28: 81 */     this.commitButtonMargin = commitButtonMargin;
/*  29: 82 */     this.comboBorderSize = comboBorderSize;
/*  30: 83 */     this.comboPopupBorderSize = comboPopupBorderSize;
/*  31: 84 */     this.checkBoxMargin = checkBoxMargin;
/*  32: 85 */     this.menuItemMargin = menuItemMargin;
/*  33: 86 */     this.menuMargin = menuMargin;
/*  34: 87 */     this.popupMenuSeparatorMargin = popupMenuSeparatorMargin;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Insets getButtonBorderInsets()
/*  38:    */   {
/*  39: 99 */     return this.buttonBorderInsets;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public InsetsUIResource getButtonMargin()
/*  43:    */   {
/*  44:119 */     return this.buttonMargin;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public InsetsUIResource getCommitButtonMargin()
/*  48:    */   {
/*  49:135 */     return this.commitButtonMargin;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getComboBorderSize()
/*  53:    */   {
/*  54:139 */     return this.comboBorderSize;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getComboPopupBorderSize()
/*  58:    */   {
/*  59:143 */     return this.comboPopupBorderSize;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public InsetsUIResource getComboBoxEditorInsets()
/*  63:    */   {
/*  64:147 */     return this.comboBoxEditorInsets;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public InsetsUIResource getCheckBoxMargin()
/*  68:    */   {
/*  69:151 */     return this.checkBoxMargin;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public InsetsUIResource getMenuItemMargin()
/*  73:    */   {
/*  74:156 */     return this.menuItemMargin;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public InsetsUIResource getMenuMargin()
/*  78:    */   {
/*  79:160 */     return this.menuMargin;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public InsetsUIResource getPopupMenuSeparatorMargin()
/*  83:    */   {
/*  84:164 */     return this.popupMenuSeparatorMargin;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public InsetsUIResource getTextInsets()
/*  88:    */   {
/*  89:169 */     return this.textInsets;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public InsetsUIResource getWrappedTextInsets()
/*  93:    */   {
/*  94:174 */     return this.wrappedTextInsets;
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.MicroLayout
 * JD-Core Version:    0.7.0.1
 */