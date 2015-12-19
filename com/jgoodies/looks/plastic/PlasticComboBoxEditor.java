/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import com.jgoodies.looks.common.ComboBoxEditorTextField;
/*  4:   */ import javax.swing.JTextField;
/*  5:   */ import javax.swing.plaf.UIResource;
/*  6:   */ import javax.swing.plaf.basic.BasicComboBoxEditor;
/*  7:   */ 
/*  8:   */ class PlasticComboBoxEditor
/*  9:   */   extends BasicComboBoxEditor
/* 10:   */ {
/* 11:   */   PlasticComboBoxEditor(boolean isTableCellEditor)
/* 12:   */   {
/* 13:51 */     this.editor = new ComboBoxEditorTextField(isTableCellEditor);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setItem(Object item)
/* 17:   */   {
/* 18:55 */     super.setItem(item);
/* 19:56 */     this.editor.selectAll();
/* 20:   */   }
/* 21:   */   
/* 22:   */   static final class UIResource
/* 23:   */     extends PlasticComboBoxEditor
/* 24:   */     implements UIResource
/* 25:   */   {
/* 26:   */     UIResource(boolean isTableCellEditor)
/* 27:   */     {
/* 28:71 */       super();
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticComboBoxEditor
 * JD-Core Version:    0.7.0.1
 */