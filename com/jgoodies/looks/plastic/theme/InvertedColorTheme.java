/*   1:    */ package com.jgoodies.looks.plastic.theme;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.plastic.PlasticTheme;
/*   4:    */ import java.awt.Color;
/*   5:    */ import javax.swing.UIDefaults;
/*   6:    */ import javax.swing.plaf.ColorUIResource;
/*   7:    */ 
/*   8:    */ public abstract class InvertedColorTheme
/*   9:    */   extends PlasticTheme
/*  10:    */ {
/*  11: 49 */   private final ColorUIResource softWhite = new ColorUIResource(154, 154, 154);
/*  12: 52 */   private final ColorUIResource primary1 = new ColorUIResource(83, 83, 61);
/*  13: 55 */   private final ColorUIResource primary2 = new ColorUIResource(115, 107, 82);
/*  14: 58 */   private final ColorUIResource primary3 = new ColorUIResource(156, 156, 123);
/*  15: 61 */   private final ColorUIResource secondary1 = new ColorUIResource(32, 32, 32);
/*  16: 64 */   private final ColorUIResource secondary2 = new ColorUIResource(96, 96, 96);
/*  17: 67 */   private final ColorUIResource secondary3 = new ColorUIResource(84, 84, 84);
/*  18:    */   
/*  19:    */   public ColorUIResource getSimpleInternalFrameBackground()
/*  20:    */   {
/*  21: 71 */     return getWhite();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void addCustomEntriesToTable(UIDefaults table)
/*  25:    */   {
/*  26: 75 */     super.addCustomEntriesToTable(table);
/*  27: 76 */     Object[] uiDefaults = { "TextField.ineditableForeground", getSoftWhite(), "Plastic.brightenStop", new Color(255, 255, 255, 20), "Plastic.ltBrightenStop", new Color(255, 255, 255, 16), "SimpleInternalFrame.activeTitleBackground", getPrimary2() };
/*  28:    */     
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 86 */     table.putDefaults(uiDefaults);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ColorUIResource getControlDisabled()
/*  41:    */   {
/*  42: 90 */     return getSoftWhite();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ColorUIResource getControlHighlight()
/*  46:    */   {
/*  47: 94 */     return getSoftWhite();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ColorUIResource getControlInfo()
/*  51:    */   {
/*  52: 98 */     return getWhite();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ColorUIResource getInactiveSystemTextColor()
/*  56:    */   {
/*  57:102 */     return getSoftWhite();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ColorUIResource getMenuDisabledForeground()
/*  61:    */   {
/*  62:106 */     return getSoftWhite();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ColorUIResource getMenuItemSelectedBackground()
/*  66:    */   {
/*  67:110 */     return getPrimary3();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public ColorUIResource getMenuItemSelectedForeground()
/*  71:    */   {
/*  72:114 */     return getBlack();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public ColorUIResource getMenuSelectedBackground()
/*  76:    */   {
/*  77:118 */     return getPrimary2();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ColorUIResource getMenuSelectedForeground()
/*  81:    */   {
/*  82:122 */     return getWhite();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected ColorUIResource getPrimary1()
/*  86:    */   {
/*  87:126 */     return this.primary1;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected ColorUIResource getPrimary2()
/*  91:    */   {
/*  92:130 */     return this.primary2;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected ColorUIResource getPrimary3()
/*  96:    */   {
/*  97:134 */     return this.primary3;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ColorUIResource getPrimaryControlHighlight()
/* 101:    */   {
/* 102:137 */     return getSoftWhite();
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected ColorUIResource getSecondary1()
/* 106:    */   {
/* 107:141 */     return this.secondary1;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected ColorUIResource getSecondary2()
/* 111:    */   {
/* 112:145 */     return this.secondary2;
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected ColorUIResource getSecondary3()
/* 116:    */   {
/* 117:149 */     return this.secondary3;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ColorUIResource getSeparatorBackground()
/* 121:    */   {
/* 122:153 */     return getSoftWhite();
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected ColorUIResource getSoftWhite()
/* 126:    */   {
/* 127:157 */     return this.softWhite;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ColorUIResource getTitleTextColor()
/* 131:    */   {
/* 132:161 */     return getControlInfo();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public ColorUIResource getToggleButtonCheckColor()
/* 136:    */   {
/* 137:165 */     return getWhite();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ColorUIResource getFocusColor()
/* 141:    */   {
/* 142:169 */     return Colors.GRAY_FOCUS;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.theme.InvertedColorTheme
 * JD-Core Version:    0.7.0.1
 */