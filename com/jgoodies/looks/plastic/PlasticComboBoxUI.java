/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*     */ import javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource;
/*     */ import javax.swing.plaf.basic.BasicComboBoxUI.PropertyChangeHandler;
/*     */ import javax.swing.plaf.basic.BasicComboPopup;
/*     */ import javax.swing.plaf.basic.ComboPopup;
/*     */ import javax.swing.plaf.metal.MetalComboBoxUI;
/*     */ import javax.swing.plaf.metal.MetalComboBoxUI.MetalComboBoxLayoutManager;
/*     */ import javax.swing.plaf.metal.MetalTextFieldUI;
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
/*     */ public class PlasticComboBoxUI
/*     */   extends MetalComboBoxUI
/*     */ {
/*     */   static final String CELL_EDITOR_KEY = "JComboBox.isTableCellEditor";
/*  73 */   private static final JTextField PHANTOM = new JTextField("Phantom");
/*     */   
/*     */ 
/*     */ 
/*     */   private static Class phantomLafClass;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean tableCellEditor;
/*     */   
/*     */ 
/*     */   private PropertyChangeListener propertyChangeListener;
/*     */   
/*     */ 
/*     */ 
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  90 */     ensurePhantomHasPlasticUI();
/*  91 */     return new PlasticComboBoxUI();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void ensurePhantomHasPlasticUI()
/*     */   {
/*  99 */     TextUI ui = PHANTOM.getUI();
/* 100 */     Class lafClass = UIManager.getLookAndFeel().getClass();
/* 101 */     if ((phantomLafClass != lafClass) || (!(ui instanceof MetalTextFieldUI)))
/*     */     {
/* 103 */       phantomLafClass = lafClass;
/* 104 */       PHANTOM.updateUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void installUI(JComponent c)
/*     */   {
/* 112 */     super.installUI(c);
/* 113 */     this.tableCellEditor = isTableCellEditor();
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 117 */     super.installListeners();
/* 118 */     this.propertyChangeListener = new TableCellEditorPropertyChangeHandler(null);
/* 119 */     this.comboBox.addPropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 123 */     super.uninstallListeners();
/* 124 */     this.comboBox.removePropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
/* 125 */     this.propertyChangeListener = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JButton createArrowButton()
/*     */   {
/* 137 */     return new PlasticComboBoxButton(this.comboBox, PlasticIconFactory.getComboBoxButtonIcon(), this.comboBox.isEditable(), this.currentValuePane, this.listBox);
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
/*     */   protected ComboBoxEditor createEditor()
/*     */   {
/* 151 */     return new PlasticComboBoxEditor.UIResource(this.tableCellEditor);
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
/*     */   protected LayoutManager createLayoutManager()
/*     */   {
/* 164 */     return new PlasticComboBoxLayoutManager(null);
/*     */   }
/*     */   
/*     */   protected ComboPopup createPopup()
/*     */   {
/* 169 */     return new PlasticComboPopup(this.comboBox, null);
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
/*     */   protected ListCellRenderer createRenderer()
/*     */   {
/* 186 */     if (this.tableCellEditor) {
/* 187 */       return super.createRenderer();
/*     */     }
/* 189 */     BasicComboBoxRenderer renderer = new BasicComboBoxRenderer.UIResource();
/* 190 */     renderer.setBorder(UIManager.getBorder("ComboBox.rendererBorder"));
/* 191 */     return renderer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getMinimumSize(JComponent c)
/*     */   {
/* 199 */     if (!this.isMinimumSizeDirty) {
/* 200 */       return new Dimension(this.cachedMinimumSize);
/*     */     }
/* 202 */     Dimension size = getDisplaySize();
/* 203 */     Insets insets = getInsets();
/* 204 */     size.height += insets.top + insets.bottom;
/* 205 */     if (this.comboBox.isEditable()) {
/* 206 */       Insets editorBorderInsets = UIManager.getInsets("ComboBox.editorBorderInsets");
/* 207 */       size.width += editorBorderInsets.left + editorBorderInsets.right;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 212 */       size.width += 1;
/* 213 */     } else if (this.arrowButton != null) {
/* 214 */       Insets arrowButtonInsets = this.arrowButton.getInsets();
/* 215 */       size.width += arrowButtonInsets.left;
/*     */     }
/* 217 */     int buttonWidth = getEditableButtonWidth();
/* 218 */     size.width += insets.left + insets.right + buttonWidth;
/*     */     
/*     */ 
/* 221 */     ListCellRenderer renderer = this.comboBox.getRenderer();
/* 222 */     if ((renderer instanceof JComponent)) {
/* 223 */       JComponent component = (JComponent)renderer;
/* 224 */       Insets rendererInsets = component.getInsets();
/* 225 */       Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
/* 226 */       int offsetLeft = Math.max(0, editorInsets.left - rendererInsets.left);
/* 227 */       int offsetRight = Math.max(0, editorInsets.right - rendererInsets.right);
/*     */       
/*     */ 
/* 230 */       size.width += offsetLeft + offsetRight;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 235 */     Dimension textFieldSize = PHANTOM.getMinimumSize();
/* 236 */     size.height = Math.max(textFieldSize.height, size.height);
/*     */     
/* 238 */     this.cachedMinimumSize.setSize(size.width, size.height);
/* 239 */     this.isMinimumSizeDirty = false;
/*     */     
/* 241 */     return new Dimension(size);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getPreferredSize(JComponent c)
/*     */   {
/* 250 */     return getMinimumSize(c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Rectangle rectangleForCurrentValue()
/*     */   {
/* 258 */     int width = this.comboBox.getWidth();
/* 259 */     int height = this.comboBox.getHeight();
/* 260 */     Insets insets = getInsets();
/* 261 */     int buttonWidth = getEditableButtonWidth();
/* 262 */     if (this.arrowButton != null) {
/* 263 */       buttonWidth = this.arrowButton.getWidth();
/*     */     }
/* 265 */     if (this.comboBox.getComponentOrientation().isLeftToRight()) {
/* 266 */       return new Rectangle(insets.left, insets.top, width - (insets.left + insets.right + buttonWidth), height - (insets.top + insets.bottom));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 272 */     return new Rectangle(insets.left + buttonWidth, insets.top, width - (insets.left + insets.right + buttonWidth), height - (insets.top + insets.bottom));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(Graphics g, JComponent c)
/*     */   {
/* 284 */     if (c.isOpaque()) {
/* 285 */       g.setColor(c.getBackground());
/* 286 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 287 */       if (isToolBarComboBox(c))
/* 288 */         c.setOpaque(false);
/*     */     }
/* 290 */     paint(g, c);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isToolBarComboBox(JComponent c)
/*     */   {
/* 301 */     Container parent = c.getParent();
/* 302 */     return (parent != null) && (((parent instanceof JToolBar)) || ((parent.getParent() instanceof JToolBar)));
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
/*     */   static int getEditableButtonWidth()
/*     */   {
/* 319 */     return UIManager.getInt("ScrollBar.width") - 1;
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
/* 331 */     return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final class PlasticComboBoxLayoutManager
/*     */     extends MetalComboBoxUI.MetalComboBoxLayoutManager
/*     */   {
/*     */     private PlasticComboBoxLayoutManager()
/*     */     {
/* 344 */       super();
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container parent) {
/* 348 */       JComboBox cb = (JComboBox)parent;
/*     */       
/*     */ 
/* 351 */       if (!cb.isEditable()) {
/* 352 */         super.layoutContainer(parent);
/* 353 */         return;
/*     */       }
/*     */       
/* 356 */       int width = cb.getWidth();
/* 357 */       int height = cb.getHeight();
/*     */       
/* 359 */       Insets insets = PlasticComboBoxUI.this.getInsets();
/* 360 */       int buttonWidth = PlasticComboBoxUI.getEditableButtonWidth();
/* 361 */       int buttonHeight = height - (insets.top + insets.bottom);
/*     */       
/* 363 */       if (PlasticComboBoxUI.this.arrowButton != null) {
/* 364 */         if (cb.getComponentOrientation().isLeftToRight()) {
/* 365 */           PlasticComboBoxUI.this.arrowButton.setBounds(width - (insets.right + buttonWidth), insets.top, buttonWidth, buttonHeight);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 371 */           PlasticComboBoxUI.this.arrowButton.setBounds(insets.left, insets.top, buttonWidth, buttonHeight);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 378 */       if (PlasticComboBoxUI.this.editor != null) {
/* 379 */         PlasticComboBoxUI.this.editor.setBounds(PlasticComboBoxUI.this.rectangleForCurrentValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertyChangeListener createPropertyChangeListener()
/*     */   {
/* 386 */     return new PlasticPropertyChangeListener(null);
/*     */   }
/*     */   
/*     */   private final class PlasticPropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler
/*     */   {
/*     */     private PlasticPropertyChangeListener()
/*     */     {
/* 393 */       super();
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 397 */       super.propertyChange(e);
/* 398 */       String propertyName = e.getPropertyName();
/*     */       
/* 400 */       if (propertyName.equals("editable")) {
/* 401 */         PlasticComboBoxButton button = (PlasticComboBoxButton)PlasticComboBoxUI.this.arrowButton;
/*     */         
/* 403 */         button.setIconOnly(PlasticComboBoxUI.this.comboBox.isEditable());
/* 404 */         PlasticComboBoxUI.this.comboBox.repaint();
/* 405 */       } else if (propertyName.equals("background")) {
/* 406 */         Color color = (Color)e.getNewValue();
/* 407 */         PlasticComboBoxUI.this.arrowButton.setBackground(color);
/* 408 */         PlasticComboBoxUI.this.listBox.setBackground(color);
/*     */       }
/* 410 */       else if (propertyName.equals("foreground")) {
/* 411 */         Color color = (Color)e.getNewValue();
/* 412 */         PlasticComboBoxUI.this.arrowButton.setForeground(color);
/* 413 */         PlasticComboBoxUI.this.listBox.setForeground(color);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class PlasticComboPopup
/*     */     extends BasicComboPopup
/*     */   {
/*     */     private PlasticComboPopup(JComboBox combo)
/*     */     {
/* 425 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void configureList()
/*     */     {
/* 432 */       super.configureList();
/* 433 */       this.list.setForeground(UIManager.getColor("MenuItem.foreground"));
/* 434 */       this.list.setBackground(UIManager.getColor("MenuItem.background"));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void configureScroller()
/*     */     {
/* 441 */       super.configureScroller();
/* 442 */       this.scroller.getVerticalScrollBar().putClientProperty("JScrollBar.isFreeStanding", Boolean.FALSE);
/*     */     }
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
/*     */     protected Rectangle computePopupBounds(int px, int py, int pw, int ph)
/*     */     {
/* 476 */       Rectangle defaultBounds = super.computePopupBounds(px, py, pw, ph);
/* 477 */       Object popupPrototypeDisplayValue = this.comboBox.getClientProperty("ComboBox.popupPrototypeDisplayValue");
/*     */       
/* 479 */       if (popupPrototypeDisplayValue == null) {
/* 480 */         return defaultBounds;
/*     */       }
/*     */       
/* 483 */       ListCellRenderer renderer = this.list.getCellRenderer();
/* 484 */       Component c = renderer.getListCellRendererComponent(this.list, popupPrototypeDisplayValue, -1, true, true);
/*     */       
/* 486 */       pw = c.getPreferredSize().width;
/* 487 */       boolean hasVerticalScrollBar = this.comboBox.getItemCount() > this.comboBox.getMaximumRowCount();
/*     */       
/* 489 */       if (hasVerticalScrollBar)
/*     */       {
/* 491 */         JScrollBar verticalBar = this.scroller.getVerticalScrollBar();
/* 492 */         pw += verticalBar.getPreferredSize().width;
/*     */       }
/* 494 */       Rectangle prototypeBasedBounds = super.computePopupBounds(px, py, pw, ph);
/* 495 */       return prototypeBasedBounds.width > defaultBounds.width ? prototypeBasedBounds : defaultBounds;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final class TableCellEditorPropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private TableCellEditorPropertyChangeHandler() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent evt)
/*     */     {
/* 512 */       PlasticComboBoxUI.this.tableCellEditor = PlasticComboBoxUI.this.isTableCellEditor();
/* 513 */       if ((PlasticComboBoxUI.this.comboBox.getRenderer() == null) || ((PlasticComboBoxUI.this.comboBox.getRenderer() instanceof UIResource))) {
/* 514 */         PlasticComboBoxUI.this.comboBox.setRenderer(PlasticComboBoxUI.this.createRenderer());
/*     */       }
/* 516 */       if ((PlasticComboBoxUI.this.comboBox.getEditor() == null) || ((PlasticComboBoxUI.this.comboBox.getEditor() instanceof UIResource))) {
/* 517 */         PlasticComboBoxUI.this.comboBox.setEditor(PlasticComboBoxUI.this.createEditor());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticComboBoxUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */