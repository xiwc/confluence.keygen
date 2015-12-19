/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.ComponentOrientation;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Insets;
/*  10:    */ import java.awt.LayoutManager;
/*  11:    */ import java.awt.Rectangle;
/*  12:    */ import java.beans.PropertyChangeEvent;
/*  13:    */ import java.beans.PropertyChangeListener;
/*  14:    */ import javax.swing.ComboBoxEditor;
/*  15:    */ import javax.swing.JButton;
/*  16:    */ import javax.swing.JComboBox;
/*  17:    */ import javax.swing.JComponent;
/*  18:    */ import javax.swing.JList;
/*  19:    */ import javax.swing.JScrollBar;
/*  20:    */ import javax.swing.JScrollPane;
/*  21:    */ import javax.swing.JTextField;
/*  22:    */ import javax.swing.JToolBar;
/*  23:    */ import javax.swing.ListCellRenderer;
/*  24:    */ import javax.swing.UIManager;
/*  25:    */ import javax.swing.plaf.ComponentUI;
/*  26:    */ import javax.swing.plaf.TextUI;
/*  27:    */ import javax.swing.plaf.UIResource;
/*  28:    */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*  29:    */ import javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource;
/*  30:    */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*  31:    */ import javax.swing.plaf.basic.BasicComboBoxUI.PropertyChangeHandler;
/*  32:    */ import javax.swing.plaf.basic.BasicComboPopup;
/*  33:    */ import javax.swing.plaf.basic.ComboPopup;
/*  34:    */ import javax.swing.plaf.metal.MetalComboBoxUI;
/*  35:    */ import javax.swing.plaf.metal.MetalComboBoxUI.MetalComboBoxLayoutManager;
/*  36:    */ import javax.swing.plaf.metal.MetalTextFieldUI;
/*  37:    */ 
/*  38:    */ public class PlasticComboBoxUI
/*  39:    */   extends MetalComboBoxUI
/*  40:    */ {
/*  41:    */   static final String CELL_EDITOR_KEY = "JComboBox.isTableCellEditor";
/*  42: 73 */   private static final JTextField PHANTOM = new JTextField("Phantom");
/*  43:    */   private static Class phantomLafClass;
/*  44:    */   private boolean tableCellEditor;
/*  45:    */   private PropertyChangeListener propertyChangeListener;
/*  46:    */   
/*  47:    */   public static ComponentUI createUI(JComponent b)
/*  48:    */   {
/*  49: 90 */     ensurePhantomHasPlasticUI();
/*  50: 91 */     return new PlasticComboBoxUI();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static void ensurePhantomHasPlasticUI()
/*  54:    */   {
/*  55: 99 */     TextUI ui = PHANTOM.getUI();
/*  56:100 */     Class lafClass = UIManager.getLookAndFeel().getClass();
/*  57:101 */     if ((phantomLafClass != lafClass) || (!(ui instanceof MetalTextFieldUI)))
/*  58:    */     {
/*  59:103 */       phantomLafClass = lafClass;
/*  60:104 */       PHANTOM.updateUI();
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void installUI(JComponent c)
/*  65:    */   {
/*  66:112 */     super.installUI(c);
/*  67:113 */     this.tableCellEditor = isTableCellEditor();
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void installListeners()
/*  71:    */   {
/*  72:117 */     super.installListeners();
/*  73:118 */     this.propertyChangeListener = new TableCellEditorPropertyChangeHandler(null);
/*  74:119 */     this.comboBox.addPropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void uninstallListeners()
/*  78:    */   {
/*  79:123 */     super.uninstallListeners();
/*  80:124 */     this.comboBox.removePropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
/*  81:125 */     this.propertyChangeListener = null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected JButton createArrowButton()
/*  85:    */   {
/*  86:137 */     return new PlasticComboBoxButton(this.comboBox, PlasticIconFactory.getComboBoxButtonIcon(), this.comboBox.isEditable(), this.currentValuePane, this.listBox);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected ComboBoxEditor createEditor()
/*  90:    */   {
/*  91:151 */     return new PlasticComboBoxEditor.UIResource(this.tableCellEditor);
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected LayoutManager createLayoutManager()
/*  95:    */   {
/*  96:164 */     return new PlasticComboBoxLayoutManager(null);
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected ComboPopup createPopup()
/* 100:    */   {
/* 101:169 */     return new PlasticComboPopup(this.comboBox, null);
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected ListCellRenderer createRenderer()
/* 105:    */   {
/* 106:186 */     if (this.tableCellEditor) {
/* 107:187 */       return super.createRenderer();
/* 108:    */     }
/* 109:189 */     BasicComboBoxRenderer renderer = new BasicComboBoxRenderer.UIResource();
/* 110:190 */     renderer.setBorder(UIManager.getBorder("ComboBox.rendererBorder"));
/* 111:191 */     return renderer;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Dimension getMinimumSize(JComponent c)
/* 115:    */   {
/* 116:199 */     if (!this.isMinimumSizeDirty) {
/* 117:200 */       return new Dimension(this.cachedMinimumSize);
/* 118:    */     }
/* 119:202 */     Dimension size = getDisplaySize();
/* 120:203 */     Insets insets = getInsets();
/* 121:204 */     size.height += insets.top + insets.bottom;
/* 122:205 */     if (this.comboBox.isEditable())
/* 123:    */     {
/* 124:206 */       Insets editorBorderInsets = UIManager.getInsets("ComboBox.editorBorderInsets");
/* 125:207 */       size.width += editorBorderInsets.left + editorBorderInsets.right;
/* 126:    */       
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:212 */       size.width += 1;
/* 131:    */     }
/* 132:213 */     else if (this.arrowButton != null)
/* 133:    */     {
/* 134:214 */       Insets arrowButtonInsets = this.arrowButton.getInsets();
/* 135:215 */       size.width += arrowButtonInsets.left;
/* 136:    */     }
/* 137:217 */     int buttonWidth = getEditableButtonWidth();
/* 138:218 */     size.width += insets.left + insets.right + buttonWidth;
/* 139:    */     
/* 140:    */ 
/* 141:221 */     ListCellRenderer renderer = this.comboBox.getRenderer();
/* 142:222 */     if ((renderer instanceof JComponent))
/* 143:    */     {
/* 144:223 */       JComponent component = (JComponent)renderer;
/* 145:224 */       Insets rendererInsets = component.getInsets();
/* 146:225 */       Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
/* 147:226 */       int offsetLeft = Math.max(0, editorInsets.left - rendererInsets.left);
/* 148:227 */       int offsetRight = Math.max(0, editorInsets.right - rendererInsets.right);
/* 149:    */       
/* 150:    */ 
/* 151:230 */       size.width += offsetLeft + offsetRight;
/* 152:    */     }
/* 153:235 */     Dimension textFieldSize = PHANTOM.getMinimumSize();
/* 154:236 */     size.height = Math.max(textFieldSize.height, size.height);
/* 155:    */     
/* 156:238 */     this.cachedMinimumSize.setSize(size.width, size.height);
/* 157:239 */     this.isMinimumSizeDirty = false;
/* 158:    */     
/* 159:241 */     return new Dimension(size);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Dimension getPreferredSize(JComponent c)
/* 163:    */   {
/* 164:250 */     return getMinimumSize(c);
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected Rectangle rectangleForCurrentValue()
/* 168:    */   {
/* 169:258 */     int width = this.comboBox.getWidth();
/* 170:259 */     int height = this.comboBox.getHeight();
/* 171:260 */     Insets insets = getInsets();
/* 172:261 */     int buttonWidth = getEditableButtonWidth();
/* 173:262 */     if (this.arrowButton != null) {
/* 174:263 */       buttonWidth = this.arrowButton.getWidth();
/* 175:    */     }
/* 176:265 */     if (this.comboBox.getComponentOrientation().isLeftToRight()) {
/* 177:266 */       return new Rectangle(insets.left, insets.top, width - (insets.left + insets.right + buttonWidth), height - (insets.top + insets.bottom));
/* 178:    */     }
/* 179:272 */     return new Rectangle(insets.left + buttonWidth, insets.top, width - (insets.left + insets.right + buttonWidth), height - (insets.top + insets.bottom));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void update(Graphics g, JComponent c)
/* 183:    */   {
/* 184:284 */     if (c.isOpaque())
/* 185:    */     {
/* 186:285 */       g.setColor(c.getBackground());
/* 187:286 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 188:287 */       if (isToolBarComboBox(c)) {
/* 189:288 */         c.setOpaque(false);
/* 190:    */       }
/* 191:    */     }
/* 192:290 */     paint(g, c);
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected boolean isToolBarComboBox(JComponent c)
/* 196:    */   {
/* 197:301 */     Container parent = c.getParent();
/* 198:302 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
/* 199:    */   }
/* 200:    */   
/* 201:    */   static int getEditableButtonWidth()
/* 202:    */   {
/* 203:319 */     return UIManager.getInt("ScrollBar.width") - 1;
/* 204:    */   }
/* 205:    */   
/* 206:    */   private boolean isTableCellEditor()
/* 207:    */   {
/* 208:331 */     return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
/* 209:    */   }
/* 210:    */   
/* 211:    */   private final class PlasticComboBoxLayoutManager
/* 212:    */     extends MetalComboBoxUI.MetalComboBoxLayoutManager
/* 213:    */   {
/* 214:    */     private PlasticComboBoxLayoutManager()
/* 215:    */     {
/* 216:344 */       super();
/* 217:    */     }
/* 218:    */     
/* 219:    */     public void layoutContainer(Container parent)
/* 220:    */     {
/* 221:348 */       JComboBox cb = (JComboBox)parent;
/* 222:351 */       if (!cb.isEditable())
/* 223:    */       {
/* 224:352 */         super.layoutContainer(parent);
/* 225:353 */         return;
/* 226:    */       }
/* 227:356 */       int width = cb.getWidth();
/* 228:357 */       int height = cb.getHeight();
/* 229:    */       
/* 230:359 */       Insets insets = PlasticComboBoxUI.this.getInsets();
/* 231:360 */       int buttonWidth = PlasticComboBoxUI.getEditableButtonWidth();
/* 232:361 */       int buttonHeight = height - (insets.top + insets.bottom);
/* 233:363 */       if (PlasticComboBoxUI.this.arrowButton != null) {
/* 234:364 */         if (cb.getComponentOrientation().isLeftToRight()) {
/* 235:365 */           PlasticComboBoxUI.this.arrowButton.setBounds(width - (insets.right + buttonWidth), insets.top, buttonWidth, buttonHeight);
/* 236:    */         } else {
/* 237:371 */           PlasticComboBoxUI.this.arrowButton.setBounds(insets.left, insets.top, buttonWidth, buttonHeight);
/* 238:    */         }
/* 239:    */       }
/* 240:378 */       if (PlasticComboBoxUI.this.editor != null) {
/* 241:379 */         PlasticComboBoxUI.this.editor.setBounds(PlasticComboBoxUI.this.rectangleForCurrentValue());
/* 242:    */       }
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public PropertyChangeListener createPropertyChangeListener()
/* 247:    */   {
/* 248:386 */     return new PlasticPropertyChangeListener(null);
/* 249:    */   }
/* 250:    */   
/* 251:    */   private final class PlasticPropertyChangeListener
/* 252:    */     extends BasicComboBoxUI.PropertyChangeHandler
/* 253:    */   {
/* 254:    */     private PlasticPropertyChangeListener()
/* 255:    */     {
/* 256:393 */       super();
/* 257:    */     }
/* 258:    */     
/* 259:    */     public void propertyChange(PropertyChangeEvent e)
/* 260:    */     {
/* 261:397 */       super.propertyChange(e);
/* 262:398 */       String propertyName = e.getPropertyName();
/* 263:400 */       if (propertyName.equals("editable"))
/* 264:    */       {
/* 265:401 */         PlasticComboBoxButton button = (PlasticComboBoxButton)PlasticComboBoxUI.this.arrowButton;
/* 266:    */         
/* 267:403 */         button.setIconOnly(PlasticComboBoxUI.this.comboBox.isEditable());
/* 268:404 */         PlasticComboBoxUI.this.comboBox.repaint();
/* 269:    */       }
/* 270:405 */       else if (propertyName.equals("background"))
/* 271:    */       {
/* 272:406 */         Color color = (Color)e.getNewValue();
/* 273:407 */         PlasticComboBoxUI.this.arrowButton.setBackground(color);
/* 274:408 */         PlasticComboBoxUI.this.listBox.setBackground(color);
/* 275:    */       }
/* 276:410 */       else if (propertyName.equals("foreground"))
/* 277:    */       {
/* 278:411 */         Color color = (Color)e.getNewValue();
/* 279:412 */         PlasticComboBoxUI.this.arrowButton.setForeground(color);
/* 280:413 */         PlasticComboBoxUI.this.listBox.setForeground(color);
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   private static final class PlasticComboPopup
/* 286:    */     extends BasicComboPopup
/* 287:    */   {
/* 288:    */     private PlasticComboPopup(JComboBox combo)
/* 289:    */     {
/* 290:425 */       super();
/* 291:    */     }
/* 292:    */     
/* 293:    */     protected void configureList()
/* 294:    */     {
/* 295:432 */       super.configureList();
/* 296:433 */       this.list.setForeground(UIManager.getColor("MenuItem.foreground"));
/* 297:434 */       this.list.setBackground(UIManager.getColor("MenuItem.background"));
/* 298:    */     }
/* 299:    */     
/* 300:    */     protected void configureScroller()
/* 301:    */     {
/* 302:441 */       super.configureScroller();
/* 303:442 */       this.scroller.getVerticalScrollBar().putClientProperty("JScrollBar.isFreeStanding", Boolean.FALSE);
/* 304:    */     }
/* 305:    */     
/* 306:    */     protected Rectangle computePopupBounds(int px, int py, int pw, int ph)
/* 307:    */     {
/* 308:476 */       Rectangle defaultBounds = super.computePopupBounds(px, py, pw, ph);
/* 309:477 */       Object popupPrototypeDisplayValue = this.comboBox.getClientProperty("ComboBox.popupPrototypeDisplayValue");
/* 310:479 */       if (popupPrototypeDisplayValue == null) {
/* 311:480 */         return defaultBounds;
/* 312:    */       }
/* 313:483 */       ListCellRenderer renderer = this.list.getCellRenderer();
/* 314:484 */       Component c = renderer.getListCellRendererComponent(this.list, popupPrototypeDisplayValue, -1, true, true);
/* 315:    */       
/* 316:486 */       pw = c.getPreferredSize().width;
/* 317:487 */       boolean hasVerticalScrollBar = this.comboBox.getItemCount() > this.comboBox.getMaximumRowCount();
/* 318:489 */       if (hasVerticalScrollBar)
/* 319:    */       {
/* 320:491 */         JScrollBar verticalBar = this.scroller.getVerticalScrollBar();
/* 321:492 */         pw += verticalBar.getPreferredSize().width;
/* 322:    */       }
/* 323:494 */       Rectangle prototypeBasedBounds = super.computePopupBounds(px, py, pw, ph);
/* 324:495 */       return prototypeBasedBounds.width > defaultBounds.width ? prototypeBasedBounds : defaultBounds;
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   private final class TableCellEditorPropertyChangeHandler
/* 329:    */     implements PropertyChangeListener
/* 330:    */   {
/* 331:    */     private TableCellEditorPropertyChangeHandler() {}
/* 332:    */     
/* 333:    */     public void propertyChange(PropertyChangeEvent evt)
/* 334:    */     {
/* 335:512 */       PlasticComboBoxUI.this.tableCellEditor = PlasticComboBoxUI.this.isTableCellEditor();
/* 336:513 */       if ((PlasticComboBoxUI.this.comboBox.getRenderer() == null) || ((PlasticComboBoxUI.this.comboBox.getRenderer() instanceof UIResource))) {
/* 337:514 */         PlasticComboBoxUI.this.comboBox.setRenderer(PlasticComboBoxUI.this.createRenderer());
/* 338:    */       }
/* 339:516 */       if ((PlasticComboBoxUI.this.comboBox.getEditor() == null) || ((PlasticComboBoxUI.this.comboBox.getEditor() instanceof UIResource))) {
/* 340:517 */         PlasticComboBoxUI.this.comboBox.setEditor(PlasticComboBoxUI.this.createEditor());
/* 341:    */       }
/* 342:    */     }
/* 343:    */   }
/* 344:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticComboBoxUI
 * JD-Core Version:    0.7.0.1
 */