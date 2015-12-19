/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ComboBoxEditorTextField
/*    */   extends JTextField
/*    */ {
/*    */   public ComboBoxEditorTextField(boolean isTableCellEditor)
/*    */   {
/* 47 */     super("", UIManager.getInt("ComboBox.editorColumns"));
/*    */     
/* 49 */     if (isTableCellEditor) {
/* 50 */       setMargin(UIManager.getInsets("ComboBox.tableEditorInsets"));
/*    */     }
/* 52 */     setBorder(UIManager.getBorder("ComboBox.editorBorder"));
/*    */   }
/*    */   
/*    */   public void setText(String s)
/*    */   {
/* 57 */     if (getText().equals(s)) {
/* 58 */       return;
/*    */     }
/* 60 */     super.setText(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ComboBoxEditorTextField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */