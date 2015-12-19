/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
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
/*    */ public final class ExtBasicCheckBoxMenuItemUI
/*    */   extends ExtBasicRadioButtonMenuItemUI
/*    */ {
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 45 */     return "CheckBoxMenuItem";
/*    */   }
/*    */   
/*    */   public static ComponentUI createUI(JComponent b) {
/* 49 */     return new ExtBasicCheckBoxMenuItemUI();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicCheckBoxMenuItemUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */