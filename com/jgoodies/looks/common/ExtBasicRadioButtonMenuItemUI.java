/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.ButtonModel;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.MenuElement;
/*    */ import javax.swing.MenuSelectionManager;
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
/*    */ public class ExtBasicRadioButtonMenuItemUI
/*    */   extends ExtBasicMenuItemUI
/*    */ {
/*    */   protected String getPropertyPrefix()
/*    */   {
/* 51 */     return "RadioButtonMenuItem";
/*    */   }
/*    */   
/*    */   public static ComponentUI createUI(JComponent b) {
/* 55 */     return new ExtBasicRadioButtonMenuItemUI();
/*    */   }
/*    */   
/*    */   protected boolean iconBorderEnabled()
/*    */   {
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public void processMouseEvent(JMenuItem item, MouseEvent e, MenuElement[] path, MenuSelectionManager manager)
/*    */   {
/* 65 */     Point p = e.getPoint();
/* 66 */     if ((p.x >= 0) && (p.x < item.getWidth()) && (p.y >= 0) && (p.y < item.getHeight()))
/*    */     {
/* 68 */       if (e.getID() == 502) {
/* 69 */         manager.clearSelectedPath();
/* 70 */         item.doClick(0);
/* 71 */         item.setArmed(false);
/*    */       } else {
/* 73 */         manager.setSelectedPath(path);
/* 74 */       } } else if (item.getModel().isArmed()) {
/* 75 */       MenuElement[] newPath = new MenuElement[path.length - 1];
/*    */       
/* 77 */       int i = 0; for (int c = path.length - 1; i < c; i++)
/* 78 */         newPath[i] = path[i];
/* 79 */       manager.setSelectedPath(newPath);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtBasicRadioButtonMenuItemUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */