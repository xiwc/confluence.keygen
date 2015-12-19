/*    */ package com.jgoodies.looks.common;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.plaf.UIResource;
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
/*    */ public final class PopupMenuLayout
/*    */   extends BoxLayout
/*    */   implements UIResource
/*    */ {
/*    */   public PopupMenuLayout(Container target, int axis)
/*    */   {
/* 61 */     super(target, axis);
/*    */   }
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
/*    */   public synchronized void invalidateLayout(Container target)
/*    */   {
/* 75 */     if ((target instanceof JPopupMenu)) {
/* 76 */       JPopupMenu menu = (JPopupMenu)target;
/* 77 */       menu.putClientProperty("maxTextWidth", null);
/* 78 */       menu.putClientProperty("maxAccWidth", null);
/*    */     }
/* 80 */     super.invalidateLayout(target);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\PopupMenuLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */