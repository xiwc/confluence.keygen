/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import com.jgoodies.looks.Options;
/*     */ import java.awt.Component;
/*     */ import javax.swing.Popup;
/*     */ import javax.swing.PopupFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShadowPopupFactory
/*     */   extends PopupFactory
/*     */ {
/*     */   static final String PROP_HORIZONTAL_BACKGROUND = "jgoodies.hShadowBg";
/*     */   static final String PROP_VERTICAL_BACKGROUND = "jgoodies.vShadowBg";
/*     */   private final PopupFactory storedFactory;
/*     */   
/*     */   private ShadowPopupFactory(PopupFactory storedFactory)
/*     */   {
/*  96 */     this.storedFactory = storedFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void install()
/*     */   {
/* 117 */     if (LookUtils.IS_OS_MAC) {
/* 118 */       return;
/*     */     }
/*     */     
/* 121 */     PopupFactory factory = PopupFactory.getSharedInstance();
/* 122 */     if ((factory instanceof ShadowPopupFactory)) {
/* 123 */       return;
/*     */     }
/* 125 */     PopupFactory.setSharedInstance(new ShadowPopupFactory(factory));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void uninstall()
/*     */   {
/* 135 */     PopupFactory factory = PopupFactory.getSharedInstance();
/* 136 */     if (!(factory instanceof ShadowPopupFactory)) {
/* 137 */       return;
/*     */     }
/* 139 */     PopupFactory stored = ((ShadowPopupFactory)factory).storedFactory;
/* 140 */     PopupFactory.setSharedInstance(stored);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Popup getPopup(Component owner, Component contents, int x, int y)
/*     */     throws IllegalArgumentException
/*     */   {
/* 175 */     Popup popup = super.getPopup(owner, contents, x, y);
/* 176 */     return Options.isPopupDropShadowActive() ? ShadowPopup.getInstance(owner, contents, x, y, popup) : popup;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ShadowPopupFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */