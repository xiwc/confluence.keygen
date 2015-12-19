/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.Options;
/*   4:    */ import java.awt.EventQueue;
/*   5:    */ import java.awt.event.FocusEvent;
/*   6:    */ import java.awt.event.MouseEvent;
/*   7:    */ import javax.swing.JFormattedTextField;
/*   8:    */ import javax.swing.SwingUtilities;
/*   9:    */ import javax.swing.plaf.UIResource;
/*  10:    */ import javax.swing.text.DefaultCaret;
/*  11:    */ import javax.swing.text.Document;
/*  12:    */ import javax.swing.text.JTextComponent;
/*  13:    */ 
/*  14:    */ final class PlasticFieldCaret
/*  15:    */   extends DefaultCaret
/*  16:    */   implements UIResource
/*  17:    */ {
/*  18: 60 */   private boolean isKeyboardFocusEvent = true;
/*  19:    */   
/*  20:    */   public void focusGained(FocusEvent e)
/*  21:    */   {
/*  22: 64 */     JTextComponent c = getComponent();
/*  23: 65 */     if (c.isEnabled())
/*  24:    */     {
/*  25: 66 */       setVisible(true);
/*  26: 67 */       setSelectionVisible(true);
/*  27:    */     }
/*  28: 69 */     if ((!c.isEnabled()) || (!this.isKeyboardFocusEvent) || (!Options.isSelectOnFocusGainActive(c))) {
/*  29: 72 */       return;
/*  30:    */     }
/*  31: 74 */     if ((c instanceof JFormattedTextField)) {
/*  32: 75 */       EventQueue.invokeLater(new Runnable()
/*  33:    */       {
/*  34:    */         public void run()
/*  35:    */         {
/*  36: 77 */           PlasticFieldCaret.this.selectAll();
/*  37:    */         }
/*  38:    */       });
/*  39:    */     } else {
/*  40: 81 */       selectAll();
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void selectAll()
/*  45:    */   {
/*  46: 87 */     JTextComponent c = getComponent();
/*  47: 88 */     boolean backward = Boolean.TRUE.equals(c.getClientProperty("JGoodies.invertSelection"));
/*  48: 89 */     if (backward)
/*  49:    */     {
/*  50: 90 */       setDot(c.getDocument().getLength());
/*  51: 91 */       moveDot(0);
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 93 */       setDot(0);
/*  56: 94 */       moveDot(c.getDocument().getLength());
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void focusLost(FocusEvent e)
/*  61:    */   {
/*  62:100 */     super.focusLost(e);
/*  63:101 */     if (!e.isTemporary())
/*  64:    */     {
/*  65:102 */       this.isKeyboardFocusEvent = true;
/*  66:103 */       if (Boolean.TRUE.equals(getComponent().getClientProperty("JGoodies.setCaretToStartOnFocusLost"))) {
/*  67:104 */         setDot(0);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void mousePressed(MouseEvent e)
/*  73:    */   {
/*  74:111 */     if ((SwingUtilities.isLeftMouseButton(e)) || (e.isPopupTrigger())) {
/*  75:112 */       this.isKeyboardFocusEvent = false;
/*  76:    */     }
/*  77:114 */     super.mousePressed(e);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void mouseReleased(MouseEvent e)
/*  81:    */   {
/*  82:120 */     super.mouseReleased(e);
/*  83:121 */     if (e.isPopupTrigger())
/*  84:    */     {
/*  85:122 */       this.isKeyboardFocusEvent = false;
/*  86:123 */       if ((getComponent() != null) && (getComponent().isEnabled()) && (getComponent().isRequestFocusEnabled())) {
/*  87:125 */         getComponent().requestFocus();
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticFieldCaret
 * JD-Core Version:    0.7.0.1
 */