/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import java.awt.Point;
/*  4:   */ import java.awt.event.MouseEvent;
/*  5:   */ import javax.swing.ButtonModel;
/*  6:   */ import javax.swing.JComponent;
/*  7:   */ import javax.swing.JMenuItem;
/*  8:   */ import javax.swing.MenuElement;
/*  9:   */ import javax.swing.MenuSelectionManager;
/* 10:   */ import javax.swing.plaf.ComponentUI;
/* 11:   */ 
/* 12:   */ public class ExtBasicRadioButtonMenuItemUI
/* 13:   */   extends ExtBasicMenuItemUI
/* 14:   */ {
/* 15:   */   protected String getPropertyPrefix()
/* 16:   */   {
/* 17:51 */     return "RadioButtonMenuItem";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static ComponentUI createUI(JComponent b)
/* 21:   */   {
/* 22:55 */     return new ExtBasicRadioButtonMenuItemUI();
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected boolean iconBorderEnabled()
/* 26:   */   {
/* 27:60 */     return true;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void processMouseEvent(JMenuItem item, MouseEvent e, MenuElement[] path, MenuSelectionManager manager)
/* 31:   */   {
/* 32:65 */     Point p = e.getPoint();
/* 33:66 */     if ((p.x >= 0) && (p.x < item.getWidth()) && (p.y >= 0) && (p.y < item.getHeight()))
/* 34:   */     {
/* 35:68 */       if (e.getID() == 502)
/* 36:   */       {
/* 37:69 */         manager.clearSelectedPath();
/* 38:70 */         item.doClick(0);
/* 39:71 */         item.setArmed(false);
/* 40:   */       }
/* 41:   */       else
/* 42:   */       {
/* 43:73 */         manager.setSelectedPath(path);
/* 44:   */       }
/* 45:   */     }
/* 46:74 */     else if (item.getModel().isArmed())
/* 47:   */     {
/* 48:75 */       MenuElement[] newPath = new MenuElement[path.length - 1];
/* 49:   */       
/* 50:77 */       int i = 0;
/* 51:77 */       for (int c = path.length - 1; i < c; i++) {
/* 52:78 */         newPath[i] = path[i];
/* 53:   */       }
/* 54:79 */       manager.setSelectedPath(newPath);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtBasicRadioButtonMenuItemUI
 * JD-Core Version:    0.7.0.1
 */