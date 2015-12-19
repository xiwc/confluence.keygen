/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTextAreaUI;
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
/*     */ 
/*     */ 
/*     */ public final class PlasticTextAreaUI
/*     */   extends BasicTextAreaUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  61 */     return new PlasticTextAreaUI();
/*     */   }
/*     */   
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/*  67 */     super.installUI(c);
/*  68 */     updateBackground((JTextComponent)c);
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
/*     */   protected void propertyChange(PropertyChangeEvent evt)
/*     */   {
/*  81 */     super.propertyChange(evt);
/*  82 */     String propertyName = evt.getPropertyName();
/*  83 */     if (("editable".equals(propertyName)) || ("enabled".equals(propertyName)))
/*     */     {
/*  85 */       updateBackground((JTextComponent)evt.getSource());
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateBackground(JTextComponent c)
/*     */   {
/*  91 */     Color background = c.getBackground();
/*  92 */     if (!(background instanceof UIResource)) {
/*  93 */       return;
/*     */     }
/*  95 */     Color newColor = null;
/*  96 */     if (!c.isEnabled()) {
/*  97 */       newColor = UIManager.getColor("TextArea.disabledBackground");
/*     */     }
/*  99 */     if ((newColor == null) && (!c.isEditable())) {
/* 100 */       newColor = UIManager.getColor("TextArea.inactiveBackground");
/*     */     }
/* 102 */     if (newColor == null) {
/* 103 */       newColor = UIManager.getColor("TextArea.background");
/*     */     }
/* 105 */     if ((newColor != null) && (newColor != background)) {
/* 106 */       c.setBackground(newColor);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticTextAreaUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */