/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource;
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
/*     */ final class PlasticComboBoxButton
/*     */   extends JButton
/*     */ {
/*  58 */   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/*  59 */   private static final Border EMPTY_BORDER = new EmptyBorder(EMPTY_INSETS);
/*     */   
/*     */   private static final int LEFT_MARGIN = 2;
/*     */   
/*     */   private static final int RIGHT_MARGIN = 2;
/*     */   private final JList listBox;
/*     */   private final CellRendererPane rendererPane;
/*     */   private JComboBox comboBox;
/*     */   private Icon comboIcon;
/*  68 */   private boolean iconOnly = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean borderPaintsFocus;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   PlasticComboBoxButton(JComboBox comboBox, Icon comboIcon, boolean iconOnly, CellRendererPane rendererPane, JList listBox)
/*     */   {
/*  80 */     super("");
/*  81 */     setModel(new DefaultButtonModel() {
/*     */       public void setArmed(boolean armed) {
/*  83 */         super.setArmed((isPressed()) || (armed));
/*     */       }
/*  85 */     });
/*  86 */     this.comboBox = comboBox;
/*  87 */     this.comboIcon = comboIcon;
/*  88 */     this.iconOnly = iconOnly;
/*  89 */     this.rendererPane = rendererPane;
/*  90 */     this.listBox = listBox;
/*  91 */     setEnabled(comboBox.isEnabled());
/*  92 */     setFocusable(false);
/*  93 */     setRequestFocusEnabled(comboBox.isEnabled());
/*  94 */     setBorder(UIManager.getBorder("ComboBox.arrowButtonBorder"));
/*  95 */     setMargin(new Insets(0, 2, 0, 2));
/*  96 */     this.borderPaintsFocus = UIManager.getBoolean("ComboBox.borderPaintsFocus");
/*     */   }
/*     */   
/*     */   public JComboBox getComboBox() {
/* 100 */     return this.comboBox;
/*     */   }
/*     */   
/*     */   public void setComboBox(JComboBox cb) {
/* 104 */     this.comboBox = cb;
/*     */   }
/*     */   
/*     */   public Icon getComboIcon() {
/* 108 */     return this.comboIcon;
/*     */   }
/*     */   
/*     */   public void setComboIcon(Icon i) {
/* 112 */     this.comboIcon = i;
/*     */   }
/*     */   
/*     */   public boolean isIconOnly() {
/* 116 */     return this.iconOnly;
/*     */   }
/*     */   
/*     */   public void setIconOnly(boolean b) {
/* 120 */     this.iconOnly = b;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 124 */     super.setEnabled(enabled);
/*     */     
/* 126 */     if (enabled) {
/* 127 */       setBackground(this.comboBox.getBackground());
/* 128 */       setForeground(this.comboBox.getForeground());
/*     */     } else {
/* 130 */       setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/* 131 */       setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFocusTraversable()
/*     */   {
/* 143 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paintComponent(Graphics g)
/*     */   {
/* 152 */     super.paintComponent(g);
/* 153 */     boolean leftToRight = PlasticUtils.isLeftToRight(this.comboBox);
/* 154 */     Insets insets = getInsets();
/* 155 */     int width = getWidth() - (insets.left + insets.right);
/* 156 */     int height = getHeight() - (insets.top + insets.bottom);
/* 157 */     if ((height <= 0) || (width <= 0)) {
/* 158 */       return;
/*     */     }
/* 160 */     int left = insets.left;
/* 161 */     int top = insets.top;
/* 162 */     int right = left + (width - 1);
/*     */     
/* 164 */     int iconWidth = 0;
/* 165 */     int iconLeft = leftToRight ? right : left;
/*     */     
/*     */ 
/* 168 */     if (this.comboIcon != null) {
/* 169 */       iconWidth = this.comboIcon.getIconWidth();
/* 170 */       int iconHeight = this.comboIcon.getIconHeight();
/*     */       int iconTop;
/*     */       int iconTop;
/* 173 */       if (this.iconOnly) {
/* 174 */         iconLeft = (getWidth() - iconWidth) / 2;
/* 175 */         iconTop = (getHeight() - iconHeight) / 2;
/*     */       } else {
/* 177 */         iconLeft = leftToRight ? left + (width - 1) - iconWidth : left;
/*     */         
/*     */ 
/* 180 */         iconTop = (getHeight() - iconHeight) / 2;
/*     */       }
/* 182 */       this.comboIcon.paintIcon(this, g, iconLeft, iconTop);
/*     */     }
/*     */     
/*     */ 
/* 186 */     if ((!this.iconOnly) && (this.comboBox != null)) {
/* 187 */       ListCellRenderer renderer = this.comboBox.getRenderer();
/* 188 */       boolean renderPressed = getModel().isPressed();
/* 189 */       Component c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, renderPressed, false);
/*     */       
/*     */ 
/* 192 */       int x = leftToRight ? left : left + iconWidth;
/* 193 */       int y = top;
/* 194 */       int w = getWidth() - left - PlasticComboBoxUI.getEditableButtonWidth();
/* 195 */       int h = height;
/*     */       
/* 197 */       Border oldBorder = null;
/* 198 */       if (((c instanceof JComponent)) && (!isTableCellEditor())) {
/* 199 */         JComponent component = (JComponent)c;
/* 200 */         if ((c instanceof BasicComboBoxRenderer.UIResource)) {
/* 201 */           oldBorder = component.getBorder();
/* 202 */           component.setBorder(EMPTY_BORDER);
/*     */         }
/* 204 */         Insets rendererInsets = component.getInsets();
/* 205 */         Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
/* 206 */         int offsetTop = Math.max(0, editorInsets.top - rendererInsets.top);
/* 207 */         int offsetBottom = Math.max(0, editorInsets.bottom - rendererInsets.bottom);
/* 208 */         y += offsetTop;
/* 209 */         h -= offsetTop + offsetBottom;
/*     */       }
/* 211 */       c.setFont(this.rendererPane.getFont());
/* 212 */       configureColors(c);
/*     */       
/*     */ 
/*     */ 
/* 216 */       boolean shouldValidate = c instanceof JPanel;
/*     */       
/* 218 */       if ((!is3D()) || (!(c instanceof JComponent)) || (!c.isOpaque())) {
/* 219 */         this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 224 */         JComponent component = (JComponent)c;
/* 225 */         boolean oldOpaque = component.isOpaque();
/* 226 */         component.setOpaque(false);
/* 227 */         this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
/* 228 */         component.setOpaque(oldOpaque);
/*     */       }
/* 230 */       if (oldBorder != null) {
/* 231 */         ((JComponent)c).setBorder(oldBorder);
/*     */       }
/*     */     }
/*     */     
/* 235 */     if (this.comboIcon != null)
/*     */     {
/* 237 */       boolean hasFocus = this.comboBox.hasFocus();
/* 238 */       if ((!this.borderPaintsFocus) && (hasFocus)) {
/* 239 */         g.setColor(PlasticLookAndFeel.getFocusColor());
/* 240 */         g.drawRect(2, 2, getWidth() - 6, getHeight() - 6);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void configureColors(Component c)
/*     */   {
/* 247 */     if ((this.model.isArmed()) && (this.model.isPressed())) {
/* 248 */       if (isOpaque()) {
/* 249 */         c.setBackground(UIManager.getColor("Button.select"));
/*     */       }
/* 251 */       c.setForeground(this.comboBox.getForeground());
/* 252 */     } else if (!this.comboBox.isEnabled()) {
/* 253 */       if (isOpaque()) {
/* 254 */         c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/*     */       }
/* 256 */       c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/*     */     } else {
/* 258 */       c.setForeground(this.comboBox.getForeground());
/* 259 */       c.setBackground(this.comboBox.getBackground());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean is3D()
/*     */   {
/* 270 */     if (PlasticUtils.force3D(this.comboBox))
/* 271 */       return true;
/* 272 */     if (PlasticUtils.forceFlat(this.comboBox))
/* 273 */       return false;
/* 274 */     return PlasticUtils.is3D("ComboBox.");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isTableCellEditor()
/*     */   {
/* 286 */     return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticComboBoxButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */