/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.common.PopupMenuLayout;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicPopupMenuUI;
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
/*     */ public final class PlasticPopupMenuUI
/*     */   extends BasicPopupMenuUI
/*     */ {
/*     */   private PropertyChangeListener borderListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  65 */     return new PlasticPopupMenuUI();
/*     */   }
/*     */   
/*     */   public void installDefaults()
/*     */   {
/*  70 */     super.installDefaults();
/*  71 */     installBorder();
/*  72 */     if ((this.popupMenu.getLayout() == null) || ((this.popupMenu.getLayout() instanceof UIResource)))
/*     */     {
/*  74 */       this.popupMenu.setLayout(new PopupMenuLayout(this.popupMenu, 1));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  79 */     super.installListeners();
/*  80 */     this.borderListener = new BorderStyleChangeHandler(null);
/*  81 */     this.popupMenu.addPropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/*  85 */     this.popupMenu.removePropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
/*  86 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   private final class BorderStyleChangeHandler implements PropertyChangeListener
/*     */   {
/*     */     private BorderStyleChangeHandler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/*  94 */       PlasticPopupMenuUI.this.installBorder();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void installBorder()
/*     */   {
/* 104 */     boolean useNarrowBorder = Boolean.TRUE.equals(this.popupMenu.getClientProperty("JPopupMenu.noMargin"));
/*     */     
/* 106 */     String suffix = useNarrowBorder ? "noMarginBorder" : "border";
/* 107 */     LookAndFeel.installBorder(this.popupMenu, "PopupMenu." + suffix);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticPopupMenuUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */