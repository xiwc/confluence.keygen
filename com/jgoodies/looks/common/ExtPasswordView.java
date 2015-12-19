/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.PasswordView;
/*     */ import javax.swing.text.Position.Bias;
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
/*     */ public final class ExtPasswordView
/*     */   extends PasswordView
/*     */ {
/*     */   public ExtPasswordView(Element element)
/*     */   {
/*  54 */     super(element);
/*     */   }
/*     */   
/*     */   public float getPreferredSpan(int axis) {
/*  58 */     overrideEchoChar();
/*  59 */     return super.getPreferredSpan(axis);
/*     */   }
/*     */   
/*     */   public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
/*  63 */     overrideEchoChar();
/*  64 */     return super.modelToView(pos, a, b);
/*     */   }
/*     */   
/*     */   public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias)
/*     */   {
/*  69 */     overrideEchoChar();
/*  70 */     return super.viewToModel(fx, fy, a, bias);
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
/*     */   protected int drawEchoCharacter(Graphics g, int x, int y, char c)
/*     */   {
/*  85 */     Container container = getContainer();
/*  86 */     if (!(container instanceof JPasswordField)) {
/*  87 */       return super.drawEchoCharacter(g, x, y, c);
/*     */     }
/*  89 */     JPasswordField field = (JPasswordField)container;
/*  90 */     if (canOverrideEchoChar(field)) {
/*  91 */       c = getEchoChar();
/*     */     }
/*     */     
/*  94 */     Graphics2D g2 = (Graphics2D)g;
/*  95 */     Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/*  96 */     Object oldAAHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/*  97 */     if (newAAHint != oldAAHint) {
/*  98 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, newAAHint);
/*     */     } else {
/* 100 */       oldAAHint = null;
/*     */     }
/*     */     
/* 103 */     int newX = super.drawEchoCharacter(g, x, y, c);
/*     */     
/* 105 */     if (oldAAHint != null) {
/* 106 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
/*     */     }
/* 108 */     return newX;
/*     */   }
/*     */   
/*     */   private void overrideEchoChar()
/*     */   {
/* 113 */     Container container = getContainer();
/* 114 */     if (!(container instanceof JPasswordField)) {
/* 115 */       return;
/*     */     }
/* 117 */     JPasswordField field = (JPasswordField)container;
/* 118 */     if (canOverrideEchoChar(field)) {
/* 119 */       setFieldEchoChar(field, getEchoChar());
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canOverrideEchoChar(JPasswordField field)
/*     */   {
/* 125 */     return (field.echoCharIsSet()) && (field.getEchoChar() == '*');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setFieldEchoChar(JPasswordField field, char newEchoChar)
/*     */   {
/* 136 */     char oldEchoChar = field.getEchoChar();
/* 137 */     if (oldEchoChar == newEchoChar)
/* 138 */       return;
/* 139 */     field.setEchoChar(newEchoChar);
/*     */   }
/*     */   
/*     */   private static char getEchoChar()
/*     */   {
/* 144 */     return ((Character)UIManager.get("PasswordField.echoChar")).charValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtPasswordView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */