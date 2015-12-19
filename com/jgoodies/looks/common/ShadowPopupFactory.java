/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import com.jgoodies.looks.Options;
/*   5:    */ import java.awt.Component;
/*   6:    */ import javax.swing.Popup;
/*   7:    */ import javax.swing.PopupFactory;
/*   8:    */ 
/*   9:    */ public final class ShadowPopupFactory
/*  10:    */   extends PopupFactory
/*  11:    */ {
/*  12:    */   static final String PROP_HORIZONTAL_BACKGROUND = "jgoodies.hShadowBg";
/*  13:    */   static final String PROP_VERTICAL_BACKGROUND = "jgoodies.vShadowBg";
/*  14:    */   private final PopupFactory storedFactory;
/*  15:    */   
/*  16:    */   private ShadowPopupFactory(PopupFactory storedFactory)
/*  17:    */   {
/*  18: 96 */     this.storedFactory = storedFactory;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void install()
/*  22:    */   {
/*  23:117 */     if (LookUtils.IS_OS_MAC) {
/*  24:118 */       return;
/*  25:    */     }
/*  26:121 */     PopupFactory factory = PopupFactory.getSharedInstance();
/*  27:122 */     if ((factory instanceof ShadowPopupFactory)) {
/*  28:123 */       return;
/*  29:    */     }
/*  30:125 */     PopupFactory.setSharedInstance(new ShadowPopupFactory(factory));
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static void uninstall()
/*  34:    */   {
/*  35:135 */     PopupFactory factory = PopupFactory.getSharedInstance();
/*  36:136 */     if (!(factory instanceof ShadowPopupFactory)) {
/*  37:137 */       return;
/*  38:    */     }
/*  39:139 */     PopupFactory stored = ((ShadowPopupFactory)factory).storedFactory;
/*  40:140 */     PopupFactory.setSharedInstance(stored);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Popup getPopup(Component owner, Component contents, int x, int y)
/*  44:    */     throws IllegalArgumentException
/*  45:    */   {
/*  46:175 */     Popup popup = super.getPopup(owner, contents, x, y);
/*  47:176 */     return Options.isPopupDropShadowActive() ? ShadowPopup.getInstance(owner, contents, x, y, popup) : popup;
/*  48:    */   }
/*  49:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ShadowPopupFactory
 * JD-Core Version:    0.7.0.1
 */