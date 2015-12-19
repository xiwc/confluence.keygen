/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.GraphicsConfiguration;
/*   7:    */ import java.awt.GraphicsDevice;
/*   8:    */ import java.awt.PrintGraphics;
/*   9:    */ import java.awt.RenderingHints;
/*  10:    */ import java.awt.RenderingHints.Key;
/*  11:    */ import java.awt.Toolkit;
/*  12:    */ import java.awt.print.PrinterGraphics;
/*  13:    */ import java.lang.reflect.InvocationTargetException;
/*  14:    */ import java.lang.reflect.Method;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Set;
/*  19:    */ import javax.swing.JComponent;
/*  20:    */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*  21:    */ 
/*  22:    */ public final class RenderingUtils
/*  23:    */ {
/*  24:    */   private static final String PROP_DESKTOPHINTS = "awt.font.desktophints";
/*  25: 64 */   private static final String SWING_UTILITIES2_NAME = LookUtils.IS_JAVA_6_OR_LATER ? "sun.swing.SwingUtilities2" : "com.sun.java.swing.SwingUtilities2";
/*  26: 74 */   private static Method drawStringUnderlineCharAtMethod = null;
/*  27:    */   
/*  28:    */   static
/*  29:    */   {
/*  30: 77 */     if (LookUtils.IS_JAVA_5_OR_LATER) {
/*  31: 78 */       drawStringUnderlineCharAtMethod = getMethodDrawStringUnderlineCharAt();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y)
/*  36:    */   {
/*  37:100 */     if (LookUtils.IS_JAVA_5_OR_LATER)
/*  38:    */     {
/*  39:101 */       if (drawStringUnderlineCharAtMethod != null) {
/*  40:    */         try
/*  41:    */         {
/*  42:103 */           drawStringUnderlineCharAtMethod.invoke(null, new Object[] { c, g, text, new Integer(underlinedIndex), new Integer(x), new Integer(y) });
/*  43:    */           
/*  44:    */ 
/*  45:106 */           return;
/*  46:    */         }
/*  47:    */         catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*  48:    */       }
/*  49:115 */       Graphics2D g2 = (Graphics2D)g;
/*  50:116 */       Map oldRenderingHints = installDesktopHints(g2);
/*  51:117 */       BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
/*  52:118 */       if (oldRenderingHints != null) {
/*  53:119 */         g2.addRenderingHints(oldRenderingHints);
/*  54:    */       }
/*  55:121 */       return;
/*  56:    */     }
/*  57:123 */     BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static Method getMethodDrawStringUnderlineCharAt()
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64:132 */       Class clazz = Class.forName(SWING_UTILITIES2_NAME);
/*  65:133 */       return clazz.getMethod("drawStringUnderlineCharAt", new Class[] { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE });
/*  66:    */     }
/*  67:    */     catch (ClassNotFoundException e) {}catch (SecurityException e) {}catch (NoSuchMethodException e) {}
/*  68:144 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static Map installDesktopHints(Graphics2D g2)
/*  72:    */   {
/*  73:149 */     Map oldRenderingHints = null;
/*  74:150 */     if (LookUtils.IS_JAVA_6_OR_LATER)
/*  75:    */     {
/*  76:151 */       Map desktopHints = desktopHints(g2);
/*  77:152 */       if ((desktopHints != null) && (!desktopHints.isEmpty()))
/*  78:    */       {
/*  79:153 */         oldRenderingHints = new HashMap(desktopHints.size());
/*  80:155 */         for (Iterator i = desktopHints.keySet().iterator(); i.hasNext();)
/*  81:    */         {
/*  82:156 */           RenderingHints.Key key = (RenderingHints.Key)i.next();
/*  83:157 */           oldRenderingHints.put(key, g2.getRenderingHint(key));
/*  84:    */         }
/*  85:159 */         g2.addRenderingHints(desktopHints);
/*  86:    */       }
/*  87:    */     }
/*  88:162 */     return oldRenderingHints;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static Map desktopHints(Graphics2D g2)
/*  92:    */   {
/*  93:167 */     if (isPrinting(g2)) {
/*  94:168 */       return null;
/*  95:    */     }
/*  96:170 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  97:171 */     GraphicsDevice device = g2.getDeviceConfiguration().getDevice();
/*  98:172 */     Map desktopHints = (Map)toolkit.getDesktopProperty("awt.font.desktophints." + device.getIDstring());
/*  99:174 */     if (desktopHints == null) {
/* 100:175 */       desktopHints = (Map)toolkit.getDesktopProperty("awt.font.desktophints");
/* 101:    */     }
/* 102:178 */     if (desktopHints != null)
/* 103:    */     {
/* 104:179 */       Object aaHint = desktopHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
/* 105:180 */       if ((aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) || (aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)) {
/* 106:182 */         desktopHints = null;
/* 107:    */       }
/* 108:    */     }
/* 109:185 */     return desktopHints;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static boolean isPrinting(Graphics g)
/* 113:    */   {
/* 114:190 */     return ((g instanceof PrintGraphics)) || ((g instanceof PrinterGraphics));
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.RenderingUtils
 * JD-Core Version:    0.7.0.1
 */