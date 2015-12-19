/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.PrintGraphics;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.RenderingHints.Key;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.print.PrinterGraphics;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
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
/*     */ public final class RenderingUtils
/*     */ {
/*     */   private static final String PROP_DESKTOPHINTS = "awt.font.desktophints";
/*  64 */   private static final String SWING_UTILITIES2_NAME = LookUtils.IS_JAVA_6_OR_LATER ? "sun.swing.SwingUtilities2" : "com.sun.java.swing.SwingUtilities2";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  74 */   private static Method drawStringUnderlineCharAtMethod = null;
/*     */   
/*     */   static {
/*  77 */     if (LookUtils.IS_JAVA_5_OR_LATER) {
/*  78 */       drawStringUnderlineCharAtMethod = getMethodDrawStringUnderlineCharAt();
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y)
/*     */   {
/* 100 */     if (LookUtils.IS_JAVA_5_OR_LATER) {
/* 101 */       if (drawStringUnderlineCharAtMethod != null) {
/*     */         try {
/* 103 */           drawStringUnderlineCharAtMethod.invoke(null, new Object[] { c, g, text, new Integer(underlinedIndex), new Integer(x), new Integer(y) });
/*     */           
/*     */ 
/* 106 */           return;
/*     */         }
/*     */         catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 115 */       Graphics2D g2 = (Graphics2D)g;
/* 116 */       Map oldRenderingHints = installDesktopHints(g2);
/* 117 */       BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
/* 118 */       if (oldRenderingHints != null) {
/* 119 */         g2.addRenderingHints(oldRenderingHints);
/*     */       }
/* 121 */       return;
/*     */     }
/* 123 */     BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Method getMethodDrawStringUnderlineCharAt()
/*     */   {
/*     */     try
/*     */     {
/* 132 */       Class clazz = Class.forName(SWING_UTILITIES2_NAME);
/* 133 */       return clazz.getMethod("drawStringUnderlineCharAt", new Class[] { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE });
/*     */     }
/*     */     catch (ClassNotFoundException e) {}catch (SecurityException e) {}catch (NoSuchMethodException e) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   private static Map installDesktopHints(Graphics2D g2)
/*     */   {
/* 149 */     Map oldRenderingHints = null;
/* 150 */     if (LookUtils.IS_JAVA_6_OR_LATER) {
/* 151 */       Map desktopHints = desktopHints(g2);
/* 152 */       if ((desktopHints != null) && (!desktopHints.isEmpty())) {
/* 153 */         oldRenderingHints = new HashMap(desktopHints.size());
/*     */         
/* 155 */         for (Iterator i = desktopHints.keySet().iterator(); i.hasNext();) {
/* 156 */           RenderingHints.Key key = (RenderingHints.Key)i.next();
/* 157 */           oldRenderingHints.put(key, g2.getRenderingHint(key));
/*     */         }
/* 159 */         g2.addRenderingHints(desktopHints);
/*     */       }
/*     */     }
/* 162 */     return oldRenderingHints;
/*     */   }
/*     */   
/*     */   private static Map desktopHints(Graphics2D g2)
/*     */   {
/* 167 */     if (isPrinting(g2)) {
/* 168 */       return null;
/*     */     }
/* 170 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 171 */     GraphicsDevice device = g2.getDeviceConfiguration().getDevice();
/* 172 */     Map desktopHints = (Map)toolkit.getDesktopProperty("awt.font.desktophints." + device.getIDstring());
/*     */     
/* 174 */     if (desktopHints == null) {
/* 175 */       desktopHints = (Map)toolkit.getDesktopProperty("awt.font.desktophints");
/*     */     }
/*     */     
/* 178 */     if (desktopHints != null) {
/* 179 */       Object aaHint = desktopHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
/* 180 */       if ((aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) || (aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT))
/*     */       {
/* 182 */         desktopHints = null;
/*     */       }
/*     */     }
/* 185 */     return desktopHints;
/*     */   }
/*     */   
/*     */   private static boolean isPrinting(Graphics g)
/*     */   {
/* 190 */     return ((g instanceof PrintGraphics)) || ((g instanceof PrinterGraphics));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\RenderingUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */