/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import javax.swing.plaf.FontUIResource;
/*   5:    */ 
/*   6:    */ public final class FontSets
/*   7:    */ {
/*   8:    */   private static FontSet logicalFontSet;
/*   9:    */   
/*  10:    */   public static FontSet createDefaultFontSet(Font controlFont)
/*  11:    */   {
/*  12: 75 */     return createDefaultFontSet(controlFont, null);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont)
/*  16:    */   {
/*  17: 92 */     return createDefaultFontSet(controlFont, menuFont, null, null, null, null);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont)
/*  21:    */   {
/*  22:110 */     return createDefaultFontSet(controlFont, menuFont, titleFont, null, null, null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont)
/*  26:    */   {
/*  27:138 */     return new DefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static FontSet getLogicalFontSet()
/*  31:    */   {
/*  32:152 */     if (logicalFontSet == null) {
/*  33:153 */       logicalFontSet = new LogicalFontSet(null);
/*  34:    */     }
/*  35:155 */     return logicalFontSet;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static final class DefaultFontSet
/*  39:    */     implements FontSet
/*  40:    */   {
/*  41:    */     private final FontUIResource controlFont;
/*  42:    */     private final FontUIResource menuFont;
/*  43:    */     private final FontUIResource titleFont;
/*  44:    */     private final FontUIResource messageFont;
/*  45:    */     private final FontUIResource smallFont;
/*  46:    */     private final FontUIResource windowTitleFont;
/*  47:    */     
/*  48:    */     public DefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont)
/*  49:    */     {
/*  50:195 */       this.controlFont = new FontUIResource(controlFont);
/*  51:196 */       this.menuFont = (menuFont != null ? new FontUIResource(menuFont) : this.controlFont);
/*  52:    */       
/*  53:    */ 
/*  54:199 */       this.titleFont = (titleFont != null ? new FontUIResource(titleFont) : this.controlFont);
/*  55:    */       
/*  56:    */ 
/*  57:202 */       this.messageFont = (messageFont != null ? new FontUIResource(messageFont) : this.controlFont);
/*  58:    */       
/*  59:    */ 
/*  60:205 */       this.smallFont = new FontUIResource(smallFont != null ? smallFont : controlFont.deriveFont(controlFont.getSize2D() - 2.0F));
/*  61:    */       
/*  62:    */ 
/*  63:208 */       this.windowTitleFont = (windowTitleFont != null ? new FontUIResource(windowTitleFont) : this.titleFont);
/*  64:    */     }
/*  65:    */     
/*  66:    */     public FontUIResource getControlFont()
/*  67:    */     {
/*  68:217 */       return this.controlFont;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public FontUIResource getMenuFont()
/*  72:    */     {
/*  73:221 */       return this.menuFont;
/*  74:    */     }
/*  75:    */     
/*  76:    */     public FontUIResource getTitleFont()
/*  77:    */     {
/*  78:225 */       return this.titleFont;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public FontUIResource getWindowTitleFont()
/*  82:    */     {
/*  83:229 */       return this.windowTitleFont;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public FontUIResource getSmallFont()
/*  87:    */     {
/*  88:233 */       return this.smallFont;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public FontUIResource getMessageFont()
/*  92:    */     {
/*  93:237 */       return this.messageFont;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private static final class LogicalFontSet
/*  98:    */     implements FontSet
/*  99:    */   {
/* 100:    */     private FontUIResource controlFont;
/* 101:    */     private FontUIResource titleFont;
/* 102:    */     private FontUIResource systemFont;
/* 103:    */     private FontUIResource smallFont;
/* 104:    */     
/* 105:    */     public FontUIResource getControlFont()
/* 106:    */     {
/* 107:256 */       if (this.controlFont == null) {
/* 108:257 */         this.controlFont = new FontUIResource(Font.getFont("swing.plaf.metal.controlFont", new Font("Dialog", 0, 12)));
/* 109:    */       }
/* 110:263 */       return this.controlFont;
/* 111:    */     }
/* 112:    */     
/* 113:    */     public FontUIResource getMenuFont()
/* 114:    */     {
/* 115:267 */       return getControlFont();
/* 116:    */     }
/* 117:    */     
/* 118:    */     public FontUIResource getTitleFont()
/* 119:    */     {
/* 120:271 */       if (this.titleFont == null) {
/* 121:272 */         this.titleFont = new FontUIResource(getControlFont().deriveFont(1));
/* 122:    */       }
/* 123:276 */       return this.titleFont;
/* 124:    */     }
/* 125:    */     
/* 126:    */     public FontUIResource getSmallFont()
/* 127:    */     {
/* 128:280 */       if (this.smallFont == null) {
/* 129:281 */         this.smallFont = new FontUIResource(Font.getFont("swing.plaf.metal.smallFont", new Font("Dialog", 0, 10)));
/* 130:    */       }
/* 131:287 */       return this.smallFont;
/* 132:    */     }
/* 133:    */     
/* 134:    */     public FontUIResource getMessageFont()
/* 135:    */     {
/* 136:291 */       if (this.systemFont == null) {
/* 137:292 */         this.systemFont = new FontUIResource(Font.getFont("swing.plaf.metal.systemFont", new Font("Dialog", 0, 12)));
/* 138:    */       }
/* 139:298 */       return this.systemFont;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public FontUIResource getWindowTitleFont()
/* 143:    */     {
/* 144:302 */       return getTitleFont();
/* 145:    */     }
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.FontSets
 * JD-Core Version:    0.7.0.1
 */