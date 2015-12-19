/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.beans.PropertyChangeEvent;
/*   5:    */ import javax.swing.JComponent;
/*   6:    */ import javax.swing.UIManager;
/*   7:    */ import javax.swing.plaf.ComponentUI;
/*   8:    */ import javax.swing.plaf.UIResource;
/*   9:    */ import javax.swing.plaf.basic.BasicTextAreaUI;
/*  10:    */ import javax.swing.text.JTextComponent;
/*  11:    */ 
/*  12:    */ public final class PlasticTextAreaUI
/*  13:    */   extends BasicTextAreaUI
/*  14:    */ {
/*  15:    */   public static ComponentUI createUI(JComponent c)
/*  16:    */   {
/*  17: 61 */     return new PlasticTextAreaUI();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void installUI(JComponent c)
/*  21:    */   {
/*  22: 67 */     super.installUI(c);
/*  23: 68 */     updateBackground((JTextComponent)c);
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void propertyChange(PropertyChangeEvent evt)
/*  27:    */   {
/*  28: 81 */     super.propertyChange(evt);
/*  29: 82 */     String propertyName = evt.getPropertyName();
/*  30: 83 */     if (("editable".equals(propertyName)) || ("enabled".equals(propertyName))) {
/*  31: 85 */       updateBackground((JTextComponent)evt.getSource());
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void updateBackground(JTextComponent c)
/*  36:    */   {
/*  37: 91 */     Color background = c.getBackground();
/*  38: 92 */     if (!(background instanceof UIResource)) {
/*  39: 93 */       return;
/*  40:    */     }
/*  41: 95 */     Color newColor = null;
/*  42: 96 */     if (!c.isEnabled()) {
/*  43: 97 */       newColor = UIManager.getColor("TextArea.disabledBackground");
/*  44:    */     }
/*  45: 99 */     if ((newColor == null) && (!c.isEditable())) {
/*  46:100 */       newColor = UIManager.getColor("TextArea.inactiveBackground");
/*  47:    */     }
/*  48:102 */     if (newColor == null) {
/*  49:103 */       newColor = UIManager.getColor("TextArea.background");
/*  50:    */     }
/*  51:105 */     if ((newColor != null) && (newColor != background)) {
/*  52:106 */       c.setBackground(newColor);
/*  53:    */     }
/*  54:    */   }
/*  55:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticTextAreaUI
 * JD-Core Version:    0.7.0.1
 */