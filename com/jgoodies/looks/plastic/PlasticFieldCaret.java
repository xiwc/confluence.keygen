/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.Options;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.DefaultCaret;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.JTextComponent;
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
/*     */ final class PlasticFieldCaret
/*     */   extends DefaultCaret
/*     */   implements UIResource
/*     */ {
/*  60 */   private boolean isKeyboardFocusEvent = true;
/*     */   
/*     */   public void focusGained(FocusEvent e)
/*     */   {
/*  64 */     JTextComponent c = getComponent();
/*  65 */     if (c.isEnabled()) {
/*  66 */       setVisible(true);
/*  67 */       setSelectionVisible(true);
/*     */     }
/*  69 */     if ((!c.isEnabled()) || (!this.isKeyboardFocusEvent) || (!Options.isSelectOnFocusGainActive(c)))
/*     */     {
/*     */ 
/*  72 */       return;
/*     */     }
/*  74 */     if ((c instanceof JFormattedTextField)) {
/*  75 */       EventQueue.invokeLater(new Runnable() {
/*     */         public void run() {
/*  77 */           PlasticFieldCaret.this.selectAll();
/*     */         }
/*     */       });
/*     */     } else {
/*  81 */       selectAll();
/*     */     }
/*     */   }
/*     */   
/*     */   private void selectAll()
/*     */   {
/*  87 */     JTextComponent c = getComponent();
/*  88 */     boolean backward = Boolean.TRUE.equals(c.getClientProperty("JGoodies.invertSelection"));
/*  89 */     if (backward) {
/*  90 */       setDot(c.getDocument().getLength());
/*  91 */       moveDot(0);
/*     */     } else {
/*  93 */       setDot(0);
/*  94 */       moveDot(c.getDocument().getLength());
/*     */     }
/*     */   }
/*     */   
/*     */   public void focusLost(FocusEvent e)
/*     */   {
/* 100 */     super.focusLost(e);
/* 101 */     if (!e.isTemporary()) {
/* 102 */       this.isKeyboardFocusEvent = true;
/* 103 */       if (Boolean.TRUE.equals(getComponent().getClientProperty("JGoodies.setCaretToStartOnFocusLost"))) {
/* 104 */         setDot(0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent e)
/*     */   {
/* 111 */     if ((SwingUtilities.isLeftMouseButton(e)) || (e.isPopupTrigger())) {
/* 112 */       this.isKeyboardFocusEvent = false;
/*     */     }
/* 114 */     super.mousePressed(e);
/*     */   }
/*     */   
/*     */ 
/*     */   public void mouseReleased(MouseEvent e)
/*     */   {
/* 120 */     super.mouseReleased(e);
/* 121 */     if (e.isPopupTrigger()) {
/* 122 */       this.isKeyboardFocusEvent = false;
/* 123 */       if ((getComponent() != null) && (getComponent().isEnabled()) && (getComponent().isRequestFocusEnabled()))
/*     */       {
/* 125 */         getComponent().requestFocus();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticFieldCaret.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */