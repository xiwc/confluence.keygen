/*  1:   */ package com.jgoodies.looks.common;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import javax.swing.BoxLayout;
/*  5:   */ import javax.swing.JPopupMenu;
/*  6:   */ import javax.swing.plaf.UIResource;
/*  7:   */ 
/*  8:   */ public final class PopupMenuLayout
/*  9:   */   extends BoxLayout
/* 10:   */   implements UIResource
/* 11:   */ {
/* 12:   */   public PopupMenuLayout(Container target, int axis)
/* 13:   */   {
/* 14:61 */     super(target, axis);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public synchronized void invalidateLayout(Container target)
/* 18:   */   {
/* 19:75 */     if ((target instanceof JPopupMenu))
/* 20:   */     {
/* 21:76 */       JPopupMenu menu = (JPopupMenu)target;
/* 22:77 */       menu.putClientProperty("maxTextWidth", null);
/* 23:78 */       menu.putClientProperty("maxAccWidth", null);
/* 24:   */     }
/* 25:80 */     super.invalidateLayout(target);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.PopupMenuLayout
 * JD-Core Version:    0.7.0.1
 */