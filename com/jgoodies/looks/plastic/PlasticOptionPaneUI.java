/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import com.jgoodies.looks.common.ExtButtonAreaLayout;
/*    */ import java.awt.Container;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicOptionPaneUI;
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
/*    */ public final class PlasticOptionPaneUI
/*    */   extends BasicOptionPaneUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent b)
/*    */   {
/* 55 */     return new PlasticOptionPaneUI();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Container createButtonArea()
/*    */   {
/* 63 */     JPanel bottom = new JPanel(new ExtButtonAreaLayout(true, 6));
/* 64 */     bottom.setBorder(UIManager.getBorder("OptionPane.buttonAreaBorder"));
/* 65 */     addButtonComponents(bottom, getButtons(), getInitialValueIndex());
/* 66 */     return bottom;
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticOptionPaneUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */