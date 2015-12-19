/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.FontPolicy;
/*   4:    */ import com.jgoodies.looks.FontSet;
/*   5:    */ import java.awt.Color;
/*   6:    */ import javax.swing.plaf.ColorUIResource;
/*   7:    */ import javax.swing.plaf.FontUIResource;
/*   8:    */ import javax.swing.plaf.metal.DefaultMetalTheme;
/*   9:    */ 
/*  10:    */ public abstract class PlasticTheme
/*  11:    */   extends DefaultMetalTheme
/*  12:    */ {
/*  13: 52 */   public static final Color DARKEN_START = new Color(0, 0, 0, 0);
/*  14: 53 */   public static final Color DARKEN_STOP = new Color(0, 0, 0, 64);
/*  15: 54 */   public static final Color LT_DARKEN_STOP = new Color(0, 0, 0, 32);
/*  16: 55 */   public static final Color BRIGHTEN_START = new Color(255, 255, 255, 0);
/*  17: 56 */   public static final Color BRIGHTEN_STOP = new Color(255, 255, 255, 128);
/*  18: 57 */   public static final Color LT_BRIGHTEN_STOP = new Color(255, 255, 255, 64);
/*  19: 59 */   protected static final ColorUIResource WHITE = new ColorUIResource(255, 255, 255);
/*  20: 62 */   protected static final ColorUIResource BLACK = new ColorUIResource(0, 0, 0);
/*  21:    */   private FontSet fontSet;
/*  22:    */   
/*  23:    */   protected ColorUIResource getBlack()
/*  24:    */   {
/*  25: 81 */     return BLACK;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected ColorUIResource getWhite()
/*  29:    */   {
/*  30: 85 */     return WHITE;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ColorUIResource getSystemTextColor()
/*  34:    */   {
/*  35: 89 */     return getControlInfo();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ColorUIResource getTitleTextColor()
/*  39:    */   {
/*  40: 93 */     return getPrimary1();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ColorUIResource getMenuForeground()
/*  44:    */   {
/*  45: 97 */     return getControlInfo();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ColorUIResource getMenuItemBackground()
/*  49:    */   {
/*  50:101 */     return getMenuBackground();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public ColorUIResource getMenuItemSelectedBackground()
/*  54:    */   {
/*  55:105 */     return getMenuSelectedBackground();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ColorUIResource getMenuItemSelectedForeground()
/*  59:    */   {
/*  60:109 */     return getMenuSelectedForeground();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ColorUIResource getSimpleInternalFrameForeground()
/*  64:    */   {
/*  65:113 */     return getWhite();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ColorUIResource getSimpleInternalFrameBackground()
/*  69:    */   {
/*  70:117 */     return getPrimary1();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ColorUIResource getToggleButtonCheckColor()
/*  74:    */   {
/*  75:121 */     return getPrimary1();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public FontUIResource getTitleTextFont()
/*  79:    */   {
/*  80:127 */     return getFontSet().getTitleFont();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public FontUIResource getControlTextFont()
/*  84:    */   {
/*  85:131 */     return getFontSet().getControlFont();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public FontUIResource getMenuTextFont()
/*  89:    */   {
/*  90:135 */     return getFontSet().getMenuFont();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public FontUIResource getSubTextFont()
/*  94:    */   {
/*  95:139 */     return getFontSet().getSmallFont();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public FontUIResource getSystemTextFont()
/*  99:    */   {
/* 100:143 */     return getFontSet().getControlFont();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public FontUIResource getUserTextFont()
/* 104:    */   {
/* 105:147 */     return getFontSet().getControlFont();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public FontUIResource getWindowTitleFont()
/* 109:    */   {
/* 110:151 */     return getFontSet().getWindowTitleFont();
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected FontSet getFontSet()
/* 114:    */   {
/* 115:155 */     if (this.fontSet == null)
/* 116:    */     {
/* 117:156 */       FontPolicy policy = PlasticLookAndFeel.getFontPolicy();
/* 118:157 */       this.fontSet = policy.getFontSet("Plastic", null);
/* 119:    */     }
/* 120:159 */     return this.fontSet;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean equals(Object o)
/* 124:    */   {
/* 125:171 */     if (this == o) {
/* 126:172 */       return true;
/* 127:    */     }
/* 128:173 */     if (o == null) {
/* 129:174 */       return false;
/* 130:    */     }
/* 131:175 */     return getClass().equals(o.getClass());
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int hashCode()
/* 135:    */   {
/* 136:185 */     return getClass().hashCode();
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticTheme
 * JD-Core Version:    0.7.0.1
 */