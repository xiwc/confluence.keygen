/*    */ package com.jgoodies.looks.plastic;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.ButtonModel;
/*    */ import javax.swing.plaf.basic.BasicButtonListener;
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
/*    */ final class ActiveBasicButtonListener
/*    */   extends BasicButtonListener
/*    */ {
/*    */   private boolean mouseOver;
/*    */   
/*    */   ActiveBasicButtonListener(AbstractButton b)
/*    */   {
/* 52 */     super(b);
/* 53 */     this.mouseOver = false;
/*    */   }
/*    */   
/*    */   public void mouseEntered(MouseEvent e) {
/* 57 */     super.mouseEntered(e);
/* 58 */     AbstractButton button = (AbstractButton)e.getSource();
/* 59 */     button.getModel().setArmed(this.mouseOver = 1);
/*    */   }
/*    */   
/*    */   public void mouseExited(MouseEvent e) {
/* 63 */     super.mouseExited(e);
/* 64 */     AbstractButton button = (AbstractButton)e.getSource();
/* 65 */     button.getModel().setArmed(this.mouseOver = 0);
/*    */   }
/*    */   
/*    */   public void mouseReleased(MouseEvent e) {
/* 69 */     super.mouseReleased(e);
/* 70 */     AbstractButton button = (AbstractButton)e.getSource();
/* 71 */     button.getModel().setArmed(this.mouseOver);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\ActiveBasicButtonListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */