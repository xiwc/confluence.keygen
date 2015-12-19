/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.common.PopupMenuLayout;
/*   4:    */ import java.beans.PropertyChangeEvent;
/*   5:    */ import java.beans.PropertyChangeListener;
/*   6:    */ import javax.swing.JComponent;
/*   7:    */ import javax.swing.JPopupMenu;
/*   8:    */ import javax.swing.LookAndFeel;
/*   9:    */ import javax.swing.plaf.ComponentUI;
/*  10:    */ import javax.swing.plaf.UIResource;
/*  11:    */ import javax.swing.plaf.basic.BasicPopupMenuUI;
/*  12:    */ 
/*  13:    */ public final class PlasticPopupMenuUI
/*  14:    */   extends BasicPopupMenuUI
/*  15:    */ {
/*  16:    */   private PropertyChangeListener borderListener;
/*  17:    */   
/*  18:    */   public static ComponentUI createUI(JComponent b)
/*  19:    */   {
/*  20: 65 */     return new PlasticPopupMenuUI();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void installDefaults()
/*  24:    */   {
/*  25: 70 */     super.installDefaults();
/*  26: 71 */     installBorder();
/*  27: 72 */     if ((this.popupMenu.getLayout() == null) || ((this.popupMenu.getLayout() instanceof UIResource))) {
/*  28: 74 */       this.popupMenu.setLayout(new PopupMenuLayout(this.popupMenu, 1));
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void installListeners()
/*  33:    */   {
/*  34: 79 */     super.installListeners();
/*  35: 80 */     this.borderListener = new BorderStyleChangeHandler(null);
/*  36: 81 */     this.popupMenu.addPropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void uninstallListeners()
/*  40:    */   {
/*  41: 85 */     this.popupMenu.removePropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
/*  42: 86 */     super.uninstallListeners();
/*  43:    */   }
/*  44:    */   
/*  45:    */   private final class BorderStyleChangeHandler
/*  46:    */     implements PropertyChangeListener
/*  47:    */   {
/*  48:    */     private BorderStyleChangeHandler() {}
/*  49:    */     
/*  50:    */     public void propertyChange(PropertyChangeEvent e)
/*  51:    */     {
/*  52: 94 */       PlasticPopupMenuUI.this.installBorder();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void installBorder()
/*  57:    */   {
/*  58:104 */     boolean useNarrowBorder = Boolean.TRUE.equals(this.popupMenu.getClientProperty("JPopupMenu.noMargin"));
/*  59:    */     
/*  60:106 */     String suffix = useNarrowBorder ? "noMarginBorder" : "border";
/*  61:107 */     LookAndFeel.installBorder(this.popupMenu, "PopupMenu." + suffix);
/*  62:    */   }
/*  63:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticPopupMenuUI
 * JD-Core Version:    0.7.0.1
 */