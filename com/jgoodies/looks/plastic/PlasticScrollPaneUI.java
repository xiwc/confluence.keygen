/*  1:   */ package com.jgoodies.looks.plastic;
/*  2:   */ 
/*  3:   */ import java.beans.PropertyChangeEvent;
/*  4:   */ import java.beans.PropertyChangeListener;
/*  5:   */ import javax.swing.JComponent;
/*  6:   */ import javax.swing.JScrollPane;
/*  7:   */ import javax.swing.LookAndFeel;
/*  8:   */ import javax.swing.plaf.ComponentUI;
/*  9:   */ import javax.swing.plaf.metal.MetalScrollPaneUI;
/* 10:   */ 
/* 11:   */ public final class PlasticScrollPaneUI
/* 12:   */   extends MetalScrollPaneUI
/* 13:   */ {
/* 14:   */   private PropertyChangeListener borderStyleChangeHandler;
/* 15:   */   
/* 16:   */   public static ComponentUI createUI(JComponent b)
/* 17:   */   {
/* 18:62 */     return new PlasticScrollPaneUI();
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void installDefaults(JScrollPane scrollPane)
/* 22:   */   {
/* 23:66 */     super.installDefaults(scrollPane);
/* 24:67 */     installEtchedBorder(scrollPane);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void installListeners(JScrollPane scrollPane)
/* 28:   */   {
/* 29:74 */     super.installListeners(scrollPane);
/* 30:75 */     this.borderStyleChangeHandler = new BorderStyleChangeHandler(null);
/* 31:76 */     scrollPane.addPropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected void uninstallListeners(JComponent c)
/* 35:   */   {
/* 36:80 */     ((JScrollPane)c).removePropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
/* 37:   */     
/* 38:82 */     super.uninstallListeners(c);
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected void installEtchedBorder(JScrollPane scrollPane)
/* 42:   */   {
/* 43:86 */     Object value = scrollPane.getClientProperty("jgoodies.isEtched");
/* 44:87 */     boolean hasEtchedBorder = Boolean.TRUE.equals(value);
/* 45:88 */     LookAndFeel.installBorder(scrollPane, hasEtchedBorder ? "ScrollPane.etchedBorder" : "ScrollPane.border");
/* 46:   */   }
/* 47:   */   
/* 48:   */   private class BorderStyleChangeHandler
/* 49:   */     implements PropertyChangeListener
/* 50:   */   {
/* 51:   */     private BorderStyleChangeHandler() {}
/* 52:   */     
/* 53:   */     public void propertyChange(PropertyChangeEvent evt)
/* 54:   */     {
/* 55:97 */       JScrollPane scrollPane = (JScrollPane)evt.getSource();
/* 56:98 */       PlasticScrollPaneUI.this.installEtchedBorder(scrollPane);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticScrollPaneUI
 * JD-Core Version:    0.7.0.1
 */