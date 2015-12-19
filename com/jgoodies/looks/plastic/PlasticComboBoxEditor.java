/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import com.jgoodies.looks.common.ComboBoxEditorTextField;
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.plaf.UIResource;
/*    */ import javax.swing.plaf.basic.BasicComboBoxEditor;
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
/*    */ 
/*    */ 
/*    */ class PlasticComboBoxEditor
/*    */   extends BasicComboBoxEditor
/*    */ {
/*    */   PlasticComboBoxEditor(boolean isTableCellEditor)
/*    */   {
/* 51 */     this.editor = new ComboBoxEditorTextField(isTableCellEditor);
/*    */   }
/*    */   
/*    */   public void setItem(Object item) {
/* 55 */     super.setItem(item);
/* 56 */     this.editor.selectAll();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static final class UIResource
/*    */     extends PlasticComboBoxEditor
/*    */     implements UIResource
/*    */   {
/*    */     UIResource(boolean isTableCellEditor)
/*    */     {
/* 71 */       super();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticComboBoxEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */