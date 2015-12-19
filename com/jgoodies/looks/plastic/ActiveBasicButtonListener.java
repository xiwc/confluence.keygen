/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import java.awt.event.MouseEvent;
/*  4:   */ import javax.swing.AbstractButton;
/*  5:   */ import javax.swing.ButtonModel;
/*  6:   */ import javax.swing.plaf.basic.BasicButtonListener;
/*  7:   */ 
/*  8:   */ final class ActiveBasicButtonListener
/*  9:   */   extends BasicButtonListener
/* 10:   */ {
/* 11:   */   private boolean mouseOver;
/* 12:   */   
/* 13:   */   ActiveBasicButtonListener(AbstractButton b)
/* 14:   */   {
/* 15:52 */     super(b);
/* 16:53 */     this.mouseOver = false;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void mouseEntered(MouseEvent e)
/* 20:   */   {
/* 21:57 */     super.mouseEntered(e);
/* 22:58 */     AbstractButton button = (AbstractButton)e.getSource();
/* 23:59 */     button.getModel().setArmed(this.mouseOver = 1);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void mouseExited(MouseEvent e)
/* 27:   */   {
/* 28:63 */     super.mouseExited(e);
/* 29:64 */     AbstractButton button = (AbstractButton)e.getSource();
/* 30:65 */     button.getModel().setArmed(this.mouseOver = 0);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void mouseReleased(MouseEvent e)
/* 34:   */   {
/* 35:69 */     super.mouseReleased(e);
/* 36:70 */     AbstractButton button = (AbstractButton)e.getSource();
/* 37:71 */     button.getModel().setArmed(this.mouseOver);
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.ActiveBasicButtonListener
 * JD-Core Version:    0.7.0.1
 */