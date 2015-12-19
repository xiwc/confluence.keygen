/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import javax.swing.ButtonModel;
/*   7:    */ import javax.swing.CellRendererPane;
/*   8:    */ import javax.swing.DefaultButtonModel;
/*   9:    */ import javax.swing.Icon;
/*  10:    */ import javax.swing.JButton;
/*  11:    */ import javax.swing.JComboBox;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JList;
/*  14:    */ import javax.swing.JPanel;
/*  15:    */ import javax.swing.ListCellRenderer;
/*  16:    */ import javax.swing.UIManager;
/*  17:    */ import javax.swing.border.Border;
/*  18:    */ import javax.swing.border.EmptyBorder;
/*  19:    */ import javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource;
/*  20:    */ 
/*  21:    */ final class PlasticComboBoxButton
/*  22:    */   extends JButton
/*  23:    */ {
/*  24: 58 */   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/*  25: 59 */   private static final Border EMPTY_BORDER = new EmptyBorder(EMPTY_INSETS);
/*  26:    */   private static final int LEFT_MARGIN = 2;
/*  27:    */   private static final int RIGHT_MARGIN = 2;
/*  28:    */   private final JList listBox;
/*  29:    */   private final CellRendererPane rendererPane;
/*  30:    */   private JComboBox comboBox;
/*  31:    */   private Icon comboIcon;
/*  32: 68 */   private boolean iconOnly = false;
/*  33:    */   private final boolean borderPaintsFocus;
/*  34:    */   
/*  35:    */   PlasticComboBoxButton(JComboBox comboBox, Icon comboIcon, boolean iconOnly, CellRendererPane rendererPane, JList listBox)
/*  36:    */   {
/*  37: 80 */     super("");
/*  38: 81 */     setModel(new DefaultButtonModel()
/*  39:    */     {
/*  40:    */       public void setArmed(boolean armed)
/*  41:    */       {
/*  42: 83 */         super.setArmed((isPressed()) || (armed));
/*  43:    */       }
/*  44: 85 */     });
/*  45: 86 */     this.comboBox = comboBox;
/*  46: 87 */     this.comboIcon = comboIcon;
/*  47: 88 */     this.iconOnly = iconOnly;
/*  48: 89 */     this.rendererPane = rendererPane;
/*  49: 90 */     this.listBox = listBox;
/*  50: 91 */     setEnabled(comboBox.isEnabled());
/*  51: 92 */     setFocusable(false);
/*  52: 93 */     setRequestFocusEnabled(comboBox.isEnabled());
/*  53: 94 */     setBorder(UIManager.getBorder("ComboBox.arrowButtonBorder"));
/*  54: 95 */     setMargin(new Insets(0, 2, 0, 2));
/*  55: 96 */     this.borderPaintsFocus = UIManager.getBoolean("ComboBox.borderPaintsFocus");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public JComboBox getComboBox()
/*  59:    */   {
/*  60:100 */     return this.comboBox;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setComboBox(JComboBox cb)
/*  64:    */   {
/*  65:104 */     this.comboBox = cb;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Icon getComboIcon()
/*  69:    */   {
/*  70:108 */     return this.comboIcon;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setComboIcon(Icon i)
/*  74:    */   {
/*  75:112 */     this.comboIcon = i;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isIconOnly()
/*  79:    */   {
/*  80:116 */     return this.iconOnly;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIconOnly(boolean b)
/*  84:    */   {
/*  85:120 */     this.iconOnly = b;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setEnabled(boolean enabled)
/*  89:    */   {
/*  90:124 */     super.setEnabled(enabled);
/*  91:126 */     if (enabled)
/*  92:    */     {
/*  93:127 */       setBackground(this.comboBox.getBackground());
/*  94:128 */       setForeground(this.comboBox.getForeground());
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:130 */       setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/*  99:131 */       setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isFocusTraversable()
/* 104:    */   {
/* 105:143 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void paintComponent(Graphics g)
/* 109:    */   {
/* 110:152 */     super.paintComponent(g);
/* 111:153 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.comboBox);
/* 112:154 */     Insets insets = getInsets();
/* 113:155 */     int width = getWidth() - (insets.left + insets.right);
/* 114:156 */     int height = getHeight() - (insets.top + insets.bottom);
/* 115:157 */     if ((height <= 0) || (width <= 0)) {
/* 116:158 */       return;
/* 117:    */     }
/* 118:160 */     int left = insets.left;
/* 119:161 */     int top = insets.top;
/* 120:162 */     int right = left + (width - 1);
/* 121:    */     
/* 122:164 */     int iconWidth = 0;
/* 123:165 */     int iconLeft = leftToRight ? right : left;
/* 124:168 */     if (this.comboIcon != null)
/* 125:    */     {
/* 126:169 */       iconWidth = this.comboIcon.getIconWidth();
/* 127:170 */       int iconHeight = this.comboIcon.getIconHeight();
/* 128:    */       int iconTop;
/* 129:    */       int iconTop;
/* 130:173 */       if (this.iconOnly)
/* 131:    */       {
/* 132:174 */         iconLeft = (getWidth() - iconWidth) / 2;
/* 133:175 */         iconTop = (getHeight() - iconHeight) / 2;
/* 134:    */       }
/* 135:    */       else
/* 136:    */       {
/* 137:177 */         iconLeft = leftToRight ? left + (width - 1) - iconWidth : left;
/* 138:    */         
/* 139:    */ 
/* 140:180 */         iconTop = (getHeight() - iconHeight) / 2;
/* 141:    */       }
/* 142:182 */       this.comboIcon.paintIcon(this, g, iconLeft, iconTop);
/* 143:    */     }
/* 144:186 */     if ((!this.iconOnly) && (this.comboBox != null))
/* 145:    */     {
/* 146:187 */       ListCellRenderer renderer = this.comboBox.getRenderer();
/* 147:188 */       boolean renderPressed = getModel().isPressed();
/* 148:189 */       Component c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, renderPressed, false);
/* 149:    */       
/* 150:    */ 
/* 151:192 */       int x = leftToRight ? left : left + iconWidth;
/* 152:193 */       int y = top;
/* 153:194 */       int w = getWidth() - left - PlasticComboBoxUI.getEditableButtonWidth();
/* 154:195 */       int h = height;
/* 155:    */       
/* 156:197 */       Border oldBorder = null;
/* 157:198 */       if (((c instanceof JComponent)) && (!isTableCellEditor()))
/* 158:    */       {
/* 159:199 */         JComponent component = (JComponent)c;
/* 160:200 */         if ((c instanceof BasicComboBoxRenderer.UIResource))
/* 161:    */         {
/* 162:201 */           oldBorder = component.getBorder();
/* 163:202 */           component.setBorder(EMPTY_BORDER);
/* 164:    */         }
/* 165:204 */         Insets rendererInsets = component.getInsets();
/* 166:205 */         Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
/* 167:206 */         int offsetTop = Math.max(0, editorInsets.top - rendererInsets.top);
/* 168:207 */         int offsetBottom = Math.max(0, editorInsets.bottom - rendererInsets.bottom);
/* 169:208 */         y += offsetTop;
/* 170:209 */         h -= offsetTop + offsetBottom;
/* 171:    */       }
/* 172:211 */       c.setFont(this.rendererPane.getFont());
/* 173:212 */       configureColors(c);
/* 174:    */       
/* 175:    */ 
/* 176:    */ 
/* 177:216 */       boolean shouldValidate = c instanceof JPanel;
/* 178:218 */       if ((!is3D()) || (!(c instanceof JComponent)) || (!c.isOpaque()))
/* 179:    */       {
/* 180:219 */         this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:224 */         JComponent component = (JComponent)c;
/* 185:225 */         boolean oldOpaque = component.isOpaque();
/* 186:226 */         component.setOpaque(false);
/* 187:227 */         this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
/* 188:228 */         component.setOpaque(oldOpaque);
/* 189:    */       }
/* 190:230 */       if (oldBorder != null) {
/* 191:231 */         ((JComponent)c).setBorder(oldBorder);
/* 192:    */       }
/* 193:    */     }
/* 194:235 */     if (this.comboIcon != null)
/* 195:    */     {
/* 196:237 */       boolean hasFocus = this.comboBox.hasFocus();
/* 197:238 */       if ((!this.borderPaintsFocus) && (hasFocus))
/* 198:    */       {
/* 199:239 */         g.setColor(PlasticLookAndFeel.getFocusColor());
/* 200:240 */         g.drawRect(2, 2, getWidth() - 6, getHeight() - 6);
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   private void configureColors(Component c)
/* 206:    */   {
/* 207:247 */     if ((this.model.isArmed()) && (this.model.isPressed()))
/* 208:    */     {
/* 209:248 */       if (isOpaque()) {
/* 210:249 */         c.setBackground(UIManager.getColor("Button.select"));
/* 211:    */       }
/* 212:251 */       c.setForeground(this.comboBox.getForeground());
/* 213:    */     }
/* 214:252 */     else if (!this.comboBox.isEnabled())
/* 215:    */     {
/* 216:253 */       if (isOpaque()) {
/* 217:254 */         c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/* 218:    */       }
/* 219:256 */       c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/* 220:    */     }
/* 221:    */     else
/* 222:    */     {
/* 223:258 */       c.setForeground(this.comboBox.getForeground());
/* 224:259 */       c.setBackground(this.comboBox.getBackground());
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   private boolean is3D()
/* 229:    */   {
/* 230:270 */     if (PlasticUtils.force3D(this.comboBox)) {
/* 231:271 */       return true;
/* 232:    */     }
/* 233:272 */     if (PlasticUtils.forceFlat(this.comboBox)) {
/* 234:273 */       return false;
/* 235:    */     }
/* 236:274 */     return PlasticUtils.is3D("ComboBox.");
/* 237:    */   }
/* 238:    */   
/* 239:    */   private boolean isTableCellEditor()
/* 240:    */   {
/* 241:286 */     return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticComboBoxButton
 * JD-Core Version:    0.7.0.1
 */