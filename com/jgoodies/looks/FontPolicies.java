/*   1:    */ package com.jgoodies.looks;
/*   2:    */ 
/*   3:    */ import java.awt.Font;
/*   4:    */ import javax.swing.UIDefaults;
/*   5:    */ 
/*   6:    */ public final class FontPolicies
/*   7:    */ {
/*   8:    */   public static FontPolicy createFixedPolicy(FontSet fontSet)
/*   9:    */   {
/*  10: 81 */     return new FixedPolicy(fontSet);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static FontPolicy customSettingsPolicy(FontPolicy defaultPolicy)
/*  14:    */   {
/*  15: 96 */     return new CustomSettingsPolicy(defaultPolicy);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static FontPolicy getDefaultPlasticOnWindowsPolicy()
/*  19:    */   {
/*  20:122 */     return new DefaultPlasticOnWindowsPolicy(null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static FontPolicy getDefaultPlasticPolicy()
/*  24:    */   {
/*  25:138 */     if (LookUtils.IS_OS_WINDOWS) {
/*  26:139 */       return getDefaultPlasticOnWindowsPolicy();
/*  27:    */     }
/*  28:141 */     return getLogicalFontsPolicy();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static FontPolicy getDefaultWindowsPolicy()
/*  32:    */   {
/*  33:158 */     return new DefaultWindowsPolicy(null);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static FontPolicy getLogicalFontsPolicy()
/*  37:    */   {
/*  38:169 */     return createFixedPolicy(FontSets.getLogicalFontSet());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static FontPolicy getLooks1xPlasticPolicy()
/*  42:    */   {
/*  43:183 */     Font controlFont = Fonts.getDefaultGUIFontWesternModernWindowsNormal();
/*  44:184 */     Font menuFont = controlFont;
/*  45:185 */     Font titleFont = controlFont.deriveFont(1);
/*  46:186 */     FontSet fontSet = FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
/*  47:187 */     return createFixedPolicy(fontSet);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static FontPolicy getLooks1xWindowsPolicy()
/*  51:    */   {
/*  52:201 */     return new Looks1xWindowsPolicy(null);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static FontPolicy getTransitionalPlasticPolicy()
/*  56:    */   {
/*  57:215 */     return LookUtils.IS_OS_WINDOWS ? getDefaultPlasticOnWindowsPolicy() : getLooks1xPlasticPolicy();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static FontSet getCustomFontSet(String lafName)
/*  61:    */   {
/*  62:233 */     String controlFontKey = lafName + ".controlFont";
/*  63:234 */     String menuFontKey = lafName + ".menuFont";
/*  64:235 */     String decodedControlFont = LookUtils.getSystemProperty(controlFontKey);
/*  65:236 */     if (decodedControlFont == null) {
/*  66:237 */       return null;
/*  67:    */     }
/*  68:238 */     Font controlFont = Font.decode(decodedControlFont);
/*  69:239 */     String decodedMenuFont = LookUtils.getSystemProperty(menuFontKey);
/*  70:240 */     Font menuFont = decodedMenuFont != null ? Font.decode(decodedMenuFont) : null;
/*  71:    */     
/*  72:    */ 
/*  73:243 */     Font titleFont = "Plastic".equals(lafName) ? controlFont.deriveFont(1) : controlFont;
/*  74:    */     
/*  75:    */ 
/*  76:246 */     return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static FontPolicy getCustomPolicy(String lafName)
/*  80:    */   {
/*  81:261 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static final class CustomSettingsPolicy
/*  85:    */     implements FontPolicy
/*  86:    */   {
/*  87:    */     private final FontPolicy wrappedPolicy;
/*  88:    */     
/*  89:    */     CustomSettingsPolicy(FontPolicy wrappedPolicy)
/*  90:    */     {
/*  91:270 */       this.wrappedPolicy = wrappedPolicy;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public FontSet getFontSet(String lafName, UIDefaults table)
/*  95:    */     {
/*  96:274 */       FontPolicy customPolicy = FontPolicies.getCustomPolicy(lafName);
/*  97:275 */       if (customPolicy != null) {
/*  98:276 */         return customPolicy.getFontSet(null, table);
/*  99:    */       }
/* 100:278 */       FontSet customFontSet = FontPolicies.getCustomFontSet(lafName);
/* 101:279 */       if (customFontSet != null) {
/* 102:280 */         return customFontSet;
/* 103:    */       }
/* 104:282 */       return this.wrappedPolicy.getFontSet(lafName, table);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private static final class DefaultPlasticOnWindowsPolicy
/* 109:    */     implements FontPolicy
/* 110:    */   {
/* 111:    */     public FontSet getFontSet(String lafName, UIDefaults table)
/* 112:    */     {
/* 113:305 */       Font windowsControlFont = Fonts.getWindowsControlFont();
/* 114:    */       Font controlFont;
/* 115:    */       Font controlFont;
/* 116:307 */       if (windowsControlFont != null)
/* 117:    */       {
/* 118:308 */         controlFont = windowsControlFont;
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:    */         Font controlFont;
/* 123:309 */         if (table != null) {
/* 124:310 */           controlFont = table.getFont("Button.font");
/* 125:    */         } else {
/* 126:312 */           controlFont = new Font("Dialog", 0, 12);
/* 127:    */         }
/* 128:    */       }
/* 129:315 */       Font menuFont = table == null ? controlFont : table.getFont("Menu.font");
/* 130:    */       
/* 131:    */ 
/* 132:318 */       Font titleFont = controlFont.deriveFont(1);
/* 133:    */       
/* 134:320 */       return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   private static final class DefaultWindowsPolicy
/* 139:    */     implements FontPolicy
/* 140:    */   {
/* 141:    */     public FontSet getFontSet(String lafName, UIDefaults table)
/* 142:    */     {
/* 143:331 */       Font windowsControlFont = Fonts.getWindowsControlFont();
/* 144:    */       Font controlFont;
/* 145:    */       Font controlFont;
/* 146:333 */       if (windowsControlFont != null)
/* 147:    */       {
/* 148:334 */         controlFont = windowsControlFont;
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:    */         Font controlFont;
/* 153:335 */         if (table != null) {
/* 154:336 */           controlFont = table.getFont("Button.font");
/* 155:    */         } else {
/* 156:338 */           controlFont = new Font("Dialog", 0, 12);
/* 157:    */         }
/* 158:    */       }
/* 159:340 */       Font menuFont = table == null ? controlFont : table.getFont("Menu.font");
/* 160:    */       
/* 161:    */ 
/* 162:343 */       Font titleFont = controlFont;
/* 163:344 */       Font messageFont = table == null ? controlFont : table.getFont("OptionPane.font");
/* 164:    */       
/* 165:    */ 
/* 166:347 */       Font smallFont = table == null ? controlFont.deriveFont(controlFont.getSize2D() - 2.0F) : table.getFont("ToolTip.font");
/* 167:    */       
/* 168:    */ 
/* 169:350 */       Font windowTitleFont = table == null ? controlFont : table.getFont("InternalFrame.titleFont");
/* 170:    */       
/* 171:    */ 
/* 172:353 */       return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static final class FixedPolicy
/* 177:    */     implements FontPolicy
/* 178:    */   {
/* 179:    */     private final FontSet fontSet;
/* 180:    */     
/* 181:    */     FixedPolicy(FontSet fontSet)
/* 182:    */     {
/* 183:373 */       this.fontSet = fontSet;
/* 184:    */     }
/* 185:    */     
/* 186:    */     public FontSet getFontSet(String lafName, UIDefaults table)
/* 187:    */     {
/* 188:377 */       return this.fontSet;
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   private static final class Looks1xWindowsPolicy
/* 193:    */     implements FontPolicy
/* 194:    */   {
/* 195:    */     public FontSet getFontSet(String lafName, UIDefaults table)
/* 196:    */     {
/* 197:388 */       Font windowsControlFont = Fonts.getLooks1xWindowsControlFont();
/* 198:    */       Font controlFont;
/* 199:    */       Font controlFont;
/* 200:390 */       if (windowsControlFont != null)
/* 201:    */       {
/* 202:391 */         controlFont = windowsControlFont;
/* 203:    */       }
/* 204:    */       else
/* 205:    */       {
/* 206:    */         Font controlFont;
/* 207:392 */         if (table != null) {
/* 208:393 */           controlFont = table.getFont("Button.font");
/* 209:    */         } else {
/* 210:395 */           controlFont = new Font("Dialog", 0, 12);
/* 211:    */         }
/* 212:    */       }
/* 213:397 */       return FontSets.createDefaultFontSet(controlFont);
/* 214:    */     }
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.FontPolicies
 * JD-Core Version:    0.7.0.1
 */