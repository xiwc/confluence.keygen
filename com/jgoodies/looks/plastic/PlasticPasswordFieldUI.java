/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import com.jgoodies.looks.LookUtils;
/*    */ import com.jgoodies.looks.common.ExtPasswordView;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicPasswordFieldUI;
/*    */ import javax.swing.text.Caret;
/*    */ import javax.swing.text.Element;
/*    */ import javax.swing.text.View;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PlasticPasswordFieldUI
/*    */   extends BasicPasswordFieldUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent c)
/*    */   {
/* 64 */     return new PlasticPasswordFieldUI();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public View create(Element elem)
/*    */   {
/* 74 */     return LookUtils.IS_JAVA_1_4_OR_5 ? new ExtPasswordView(elem) : super.create(elem);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Caret createCaret()
/*    */   {
/* 86 */     return PlasticLookAndFeel.isSelectTextOnKeyboardFocusGained() ? new PlasticFieldCaret() : super.createCaret();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticPasswordFieldUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */