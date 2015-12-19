/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.beans.PropertyChangeEvent;
/*   6:    */ import java.beans.PropertyChangeListener;
/*   7:    */ import javax.swing.JComponent;
/*   8:    */ import javax.swing.JInternalFrame;
/*   9:    */ import javax.swing.LookAndFeel;
/*  10:    */ import javax.swing.UIDefaults;
/*  11:    */ import javax.swing.UIManager;
/*  12:    */ import javax.swing.border.Border;
/*  13:    */ import javax.swing.border.EmptyBorder;
/*  14:    */ import javax.swing.plaf.ComponentUI;
/*  15:    */ import javax.swing.plaf.UIResource;
/*  16:    */ import javax.swing.plaf.basic.BasicInternalFrameUI;
/*  17:    */ 
/*  18:    */ public class PlasticInternalFrameUI
/*  19:    */   extends BasicInternalFrameUI
/*  20:    */ {
/*  21:    */   private static final String FRAME_TYPE = "JInternalFrame.frameType";
/*  22:    */   public static final String IS_PALETTE = "JInternalFrame.isPalette";
/*  23:    */   private static final String PALETTE_FRAME = "palette";
/*  24:    */   private static final String OPTION_DIALOG = "optionDialog";
/*  25: 69 */   private static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
/*  26:    */   private PlasticInternalFrameTitlePane titlePane;
/*  27:    */   private PropertyChangeListener paletteListener;
/*  28:    */   private PropertyChangeListener contentPaneListener;
/*  29:    */   
/*  30:    */   public PlasticInternalFrameUI(JInternalFrame b)
/*  31:    */   {
/*  32: 78 */     super(b);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static ComponentUI createUI(JComponent c)
/*  36:    */   {
/*  37: 83 */     return new PlasticInternalFrameUI((JInternalFrame)c);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void installUI(JComponent c)
/*  41:    */   {
/*  42: 88 */     this.frame = ((JInternalFrame)c);
/*  43:    */     
/*  44: 90 */     this.paletteListener = new PaletteListener(this, null);
/*  45: 91 */     this.contentPaneListener = new ContentPaneListener(this, null);
/*  46: 92 */     c.addPropertyChangeListener(this.paletteListener);
/*  47: 93 */     c.addPropertyChangeListener(this.contentPaneListener);
/*  48:    */     
/*  49: 95 */     super.installUI(c);
/*  50:    */     
/*  51: 97 */     Object paletteProp = c.getClientProperty("JInternalFrame.isPalette");
/*  52: 98 */     if (paletteProp != null) {
/*  53: 99 */       setPalette(((Boolean)paletteProp).booleanValue());
/*  54:    */     }
/*  55:102 */     Container content = this.frame.getContentPane();
/*  56:103 */     stripContentBorder(content);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void uninstallUI(JComponent c)
/*  60:    */   {
/*  61:108 */     this.frame = ((JInternalFrame)c);
/*  62:    */     
/*  63:110 */     c.removePropertyChangeListener(this.paletteListener);
/*  64:111 */     c.removePropertyChangeListener(this.contentPaneListener);
/*  65:    */     
/*  66:113 */     Container cont = ((JInternalFrame)c).getContentPane();
/*  67:114 */     if ((cont instanceof JComponent))
/*  68:    */     {
/*  69:115 */       JComponent content = (JComponent)cont;
/*  70:116 */       if (content.getBorder() == EMPTY_BORDER) {
/*  71:117 */         content.setBorder(null);
/*  72:    */       }
/*  73:    */     }
/*  74:120 */     super.uninstallUI(c);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void installDefaults()
/*  78:    */   {
/*  79:125 */     super.installDefaults();
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:130 */     JComponent contentPane = (JComponent)this.frame.getContentPane();
/*  85:131 */     if (contentPane != null)
/*  86:    */     {
/*  87:132 */       Color bg = contentPane.getBackground();
/*  88:133 */       if ((bg instanceof UIResource)) {
/*  89:134 */         contentPane.setBackground(null);
/*  90:    */       }
/*  91:    */     }
/*  92:136 */     this.frame.setBackground(UIManager.getLookAndFeelDefaults().getColor("control"));
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void installKeyboardActions() {}
/*  96:    */   
/*  97:    */   protected void uninstallKeyboardActions() {}
/*  98:    */   
/*  99:    */   private void stripContentBorder(Object c)
/* 100:    */   {
/* 101:148 */     if ((c instanceof JComponent))
/* 102:    */     {
/* 103:149 */       JComponent contentComp = (JComponent)c;
/* 104:150 */       Border contentBorder = contentComp.getBorder();
/* 105:151 */       if ((contentBorder == null) || ((contentBorder instanceof UIResource))) {
/* 106:152 */         contentComp.setBorder(EMPTY_BORDER);
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected JComponent createNorthPane(JInternalFrame w)
/* 112:    */   {
/* 113:159 */     this.titlePane = new PlasticInternalFrameTitlePane(w);
/* 114:160 */     return this.titlePane;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setPalette(boolean isPalette)
/* 118:    */   {
/* 119:165 */     String key = isPalette ? "InternalFrame.paletteBorder" : "InternalFrame.border";
/* 120:166 */     LookAndFeel.installBorder(this.frame, key);
/* 121:167 */     this.titlePane.setPalette(isPalette);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void setFrameType(String frameType)
/* 125:    */   {
/* 126:173 */     boolean hasPalette = frameType.equals("palette");
/* 127:    */     String key;
/* 128:    */     String key;
/* 129:174 */     if (frameType.equals("optionDialog"))
/* 130:    */     {
/* 131:175 */       key = "InternalFrame.optionDialogBorder";
/* 132:    */     }
/* 133:    */     else
/* 134:    */     {
/* 135:    */       String key;
/* 136:176 */       if (hasPalette) {
/* 137:177 */         key = "InternalFrame.paletteBorder";
/* 138:    */       } else {
/* 139:179 */         key = "InternalFrame.border";
/* 140:    */       }
/* 141:    */     }
/* 142:181 */     LookAndFeel.installBorder(this.frame, key);
/* 143:182 */     this.titlePane.setPalette(hasPalette);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static final class PaletteListener
/* 147:    */     implements PropertyChangeListener
/* 148:    */   {
/* 149:    */     private final PlasticInternalFrameUI ui;
/* 150:    */     
/* 151:    */     private PaletteListener(PlasticInternalFrameUI ui)
/* 152:    */     {
/* 153:190 */       this.ui = ui;
/* 154:    */     }
/* 155:    */     
/* 156:    */     public void propertyChange(PropertyChangeEvent e)
/* 157:    */     {
/* 158:193 */       String name = e.getPropertyName();
/* 159:194 */       Object value = e.getNewValue();
/* 160:195 */       if (name.equals("JInternalFrame.frameType"))
/* 161:    */       {
/* 162:196 */         if ((value instanceof String)) {
/* 163:197 */           this.ui.setFrameType((String)value);
/* 164:    */         }
/* 165:    */       }
/* 166:199 */       else if (name.equals("JInternalFrame.isPalette")) {
/* 167:200 */         this.ui.setPalette(Boolean.TRUE.equals(value));
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static final class ContentPaneListener
/* 173:    */     implements PropertyChangeListener
/* 174:    */   {
/* 175:    */     private final PlasticInternalFrameUI ui;
/* 176:    */     
/* 177:    */     private ContentPaneListener(PlasticInternalFrameUI ui)
/* 178:    */     {
/* 179:209 */       this.ui = ui;
/* 180:    */     }
/* 181:    */     
/* 182:    */     public void propertyChange(PropertyChangeEvent e)
/* 183:    */     {
/* 184:212 */       String name = e.getPropertyName();
/* 185:213 */       if (name.equals("contentPane")) {
/* 186:214 */         this.ui.stripContentBorder(e.getNewValue());
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticInternalFrameUI
 * JD-Core Version:    0.7.0.1
 */