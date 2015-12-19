/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import javax.swing.JTextField;
/*  4:   */ import javax.swing.UIManager;
/*  5:   */ 
/*  6:   */ public final class ComboBoxEditorTextField
/*  7:   */   extends JTextField
/*  8:   */ {
/*  9:   */   public ComboBoxEditorTextField(boolean isTableCellEditor)
/* 10:   */   {
/* 11:47 */     super("", UIManager.getInt("ComboBox.editorColumns"));
/* 12:49 */     if (isTableCellEditor) {
/* 13:50 */       setMargin(UIManager.getInsets("ComboBox.tableEditorInsets"));
/* 14:   */     }
/* 15:52 */     setBorder(UIManager.getBorder("ComboBox.editorBorder"));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setText(String s)
/* 19:   */   {
/* 20:57 */     if (getText().equals(s)) {
/* 21:58 */       return;
/* 22:   */     }
/* 23:60 */     super.setText(s);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ComboBoxEditorTextField
 * JD-Core Version:    0.7.0.1
 */