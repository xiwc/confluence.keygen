/*     */ package com.jgoodies.looks;
/*     */ 
/*     */ import java.awt.Insets;
/*     */ import javax.swing.plaf.InsetsUIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MicroLayout
/*     */ {
/*     */   private final InsetsUIResource textInsets;
/*     */   private final InsetsUIResource wrappedTextInsets;
/*     */   private final InsetsUIResource comboBoxEditorInsets;
/*     */   private final Insets buttonBorderInsets;
/*     */   private final InsetsUIResource buttonMargin;
/*     */   private final InsetsUIResource commitButtonMargin;
/*     */   private final int comboBorderSize;
/*     */   private final int comboPopupBorderSize;
/*     */   private final InsetsUIResource checkBoxMargin;
/*     */   private final InsetsUIResource menuItemMargin;
/*     */   private final InsetsUIResource menuMargin;
/*     */   private final InsetsUIResource popupMenuSeparatorMargin;
/*     */   
/*     */   public MicroLayout(InsetsUIResource textInsets, InsetsUIResource wrappedTextInsets, InsetsUIResource comboBoxEditorInsets, int comboBorderSize, int comboPopupBorderSize, Insets buttonBorderInsets, InsetsUIResource buttonMargin, InsetsUIResource commitButtonMargin, InsetsUIResource checkBoxMargin, InsetsUIResource menuItemMargin, InsetsUIResource menuMargin, InsetsUIResource popupMenuSeparatorMargin)
/*     */   {
/*  76 */     this.textInsets = textInsets;
/*  77 */     this.wrappedTextInsets = wrappedTextInsets;
/*  78 */     this.comboBoxEditorInsets = comboBoxEditorInsets;
/*  79 */     this.buttonBorderInsets = buttonBorderInsets;
/*  80 */     this.buttonMargin = buttonMargin;
/*  81 */     this.commitButtonMargin = commitButtonMargin;
/*  82 */     this.comboBorderSize = comboBorderSize;
/*  83 */     this.comboPopupBorderSize = comboPopupBorderSize;
/*  84 */     this.checkBoxMargin = checkBoxMargin;
/*  85 */     this.menuItemMargin = menuItemMargin;
/*  86 */     this.menuMargin = menuMargin;
/*  87 */     this.popupMenuSeparatorMargin = popupMenuSeparatorMargin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Insets getButtonBorderInsets()
/*     */   {
/*  99 */     return this.buttonBorderInsets;
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
/*     */   public InsetsUIResource getButtonMargin()
/*     */   {
/* 119 */     return this.buttonMargin;
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
/*     */   public InsetsUIResource getCommitButtonMargin()
/*     */   {
/* 135 */     return this.commitButtonMargin;
/*     */   }
/*     */   
/*     */   public int getComboBorderSize() {
/* 139 */     return this.comboBorderSize;
/*     */   }
/*     */   
/*     */   public int getComboPopupBorderSize() {
/* 143 */     return this.comboPopupBorderSize;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getComboBoxEditorInsets() {
/* 147 */     return this.comboBoxEditorInsets;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getCheckBoxMargin() {
/* 151 */     return this.checkBoxMargin;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getMenuItemMargin()
/*     */   {
/* 156 */     return this.menuItemMargin;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getMenuMargin() {
/* 160 */     return this.menuMargin;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getPopupMenuSeparatorMargin() {
/* 164 */     return this.popupMenuSeparatorMargin;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getTextInsets()
/*     */   {
/* 169 */     return this.textInsets;
/*     */   }
/*     */   
/*     */   public InsetsUIResource getWrappedTextInsets()
/*     */   {
/* 174 */     return this.wrappedTextInsets;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\MicroLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */