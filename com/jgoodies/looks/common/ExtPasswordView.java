/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.RenderingHints;
/*   7:    */ import java.awt.Shape;
/*   8:    */ import javax.swing.JPasswordField;
/*   9:    */ import javax.swing.UIManager;
/*  10:    */ import javax.swing.text.BadLocationException;
/*  11:    */ import javax.swing.text.Element;
/*  12:    */ import javax.swing.text.PasswordView;
/*  13:    */ import javax.swing.text.Position.Bias;
/*  14:    */ 
/*  15:    */ public final class ExtPasswordView
/*  16:    */   extends PasswordView
/*  17:    */ {
/*  18:    */   public ExtPasswordView(Element element)
/*  19:    */   {
/*  20: 54 */     super(element);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public float getPreferredSpan(int axis)
/*  24:    */   {
/*  25: 58 */     overrideEchoChar();
/*  26: 59 */     return super.getPreferredSpan(axis);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Shape modelToView(int pos, Shape a, Position.Bias b)
/*  30:    */     throws BadLocationException
/*  31:    */   {
/*  32: 63 */     overrideEchoChar();
/*  33: 64 */     return super.modelToView(pos, a, b);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias)
/*  37:    */   {
/*  38: 69 */     overrideEchoChar();
/*  39: 70 */     return super.viewToModel(fx, fy, a, bias);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected int drawEchoCharacter(Graphics g, int x, int y, char c)
/*  43:    */   {
/*  44: 85 */     Container container = getContainer();
/*  45: 86 */     if (!(container instanceof JPasswordField)) {
/*  46: 87 */       return super.drawEchoCharacter(g, x, y, c);
/*  47:    */     }
/*  48: 89 */     JPasswordField field = (JPasswordField)container;
/*  49: 90 */     if (canOverrideEchoChar(field)) {
/*  50: 91 */       c = getEchoChar();
/*  51:    */     }
/*  52: 94 */     Graphics2D g2 = (Graphics2D)g;
/*  53: 95 */     Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
/*  54: 96 */     Object oldAAHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/*  55: 97 */     if (newAAHint != oldAAHint) {
/*  56: 98 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, newAAHint);
/*  57:    */     } else {
/*  58:100 */       oldAAHint = null;
/*  59:    */     }
/*  60:103 */     int newX = super.drawEchoCharacter(g, x, y, c);
/*  61:105 */     if (oldAAHint != null) {
/*  62:106 */       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
/*  63:    */     }
/*  64:108 */     return newX;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void overrideEchoChar()
/*  68:    */   {
/*  69:113 */     Container container = getContainer();
/*  70:114 */     if (!(container instanceof JPasswordField)) {
/*  71:115 */       return;
/*  72:    */     }
/*  73:117 */     JPasswordField field = (JPasswordField)container;
/*  74:118 */     if (canOverrideEchoChar(field)) {
/*  75:119 */       setFieldEchoChar(field, getEchoChar());
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private boolean canOverrideEchoChar(JPasswordField field)
/*  80:    */   {
/*  81:125 */     return (field.echoCharIsSet()) && (field.getEchoChar() == '*');
/*  82:    */   }
/*  83:    */   
/*  84:    */   private void setFieldEchoChar(JPasswordField field, char newEchoChar)
/*  85:    */   {
/*  86:136 */     char oldEchoChar = field.getEchoChar();
/*  87:137 */     if (oldEchoChar == newEchoChar) {
/*  88:138 */       return;
/*  89:    */     }
/*  90:139 */     field.setEchoChar(newEchoChar);
/*  91:    */   }
/*  92:    */   
/*  93:    */   private static char getEchoChar()
/*  94:    */   {
/*  95:144 */     return ((Character)UIManager.get("PasswordField.echoChar")).charValue();
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtPasswordView
 * JD-Core Version:    0.7.0.1
 */