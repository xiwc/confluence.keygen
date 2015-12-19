/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.common.ExtBasicSpinnerLayout;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import java.awt.LayoutManager;
/*   7:    */ import java.awt.event.ActionListener;
/*   8:    */ import java.awt.event.MouseListener;
/*   9:    */ import javax.swing.AbstractButton;
/*  10:    */ import javax.swing.JComponent;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ import javax.swing.JSpinner;
/*  13:    */ import javax.swing.JSpinner.DefaultEditor;
/*  14:    */ import javax.swing.JTextField;
/*  15:    */ import javax.swing.UIManager;
/*  16:    */ import javax.swing.border.EmptyBorder;
/*  17:    */ import javax.swing.plaf.ComponentUI;
/*  18:    */ import javax.swing.plaf.basic.BasicSpinnerUI;
/*  19:    */ 
/*  20:    */ public class PlasticSpinnerUI
/*  21:    */   extends BasicSpinnerUI
/*  22:    */ {
/*  23:    */   public static ComponentUI createUI(JComponent b)
/*  24:    */   {
/*  25: 59 */     return new PlasticSpinnerUI();
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected Component createPreviousButton()
/*  29:    */   {
/*  30: 77 */     Component c = createArrowButton(5);
/*  31: 78 */     installPreviousButtonListenersFromSuper(c);
/*  32: 79 */     return c;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected Component createNextButton()
/*  36:    */   {
/*  37: 97 */     Component c = createArrowButton(1);
/*  38: 98 */     installNextButtonListenersFromSuper(c);
/*  39: 99 */     return c;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected Component createArrowButton(int direction)
/*  43:    */   {
/*  44:104 */     return new SpinnerArrowButton(direction, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void installPreviousButtonListenersFromSuper(Component c)
/*  48:    */   {
/*  49:110 */     AbstractButton sc = (AbstractButton)super.createPreviousButton();
/*  50:111 */     ActionListener[] als = sc.getActionListeners();
/*  51:112 */     MouseListener[] mls = sc.getMouseListeners();
/*  52:113 */     if ((c instanceof AbstractButton)) {
/*  53:114 */       ((AbstractButton)c).addActionListener(als[0]);
/*  54:    */     }
/*  55:116 */     c.addMouseListener(mls[0]);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void installNextButtonListenersFromSuper(Component c)
/*  59:    */   {
/*  60:122 */     AbstractButton sc = (AbstractButton)super.createNextButton();
/*  61:123 */     ActionListener[] als = sc.getActionListeners();
/*  62:124 */     MouseListener[] mls = sc.getMouseListeners();
/*  63:125 */     if ((c instanceof AbstractButton)) {
/*  64:126 */       ((AbstractButton)c).addActionListener(als[0]);
/*  65:    */     }
/*  66:128 */     c.addMouseListener(mls[0]);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected LayoutManager createLayout()
/*  70:    */   {
/*  71:146 */     return new ExtBasicSpinnerLayout();
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected JComponent createEditor()
/*  75:    */   {
/*  76:174 */     JComponent editor = this.spinner.getEditor();
/*  77:175 */     configureEditorBorder(editor);
/*  78:176 */     return editor;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void replaceEditor(JComponent oldEditor, JComponent newEditor)
/*  82:    */   {
/*  83:195 */     this.spinner.remove(oldEditor);
/*  84:196 */     configureEditorBorder(newEditor);
/*  85:197 */     this.spinner.add(newEditor, "Editor");
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void configureEditorBorder(JComponent editor)
/*  89:    */   {
/*  90:205 */     if ((editor instanceof JSpinner.DefaultEditor))
/*  91:    */     {
/*  92:206 */       JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor)editor;
/*  93:207 */       JTextField editorField = defaultEditor.getTextField();
/*  94:208 */       Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
/*  95:209 */       editorField.setBorder(new EmptyBorder(insets));
/*  96:    */     }
/*  97:210 */     else if (((editor instanceof JPanel)) && (editor.getBorder() == null) && (editor.getComponentCount() > 0))
/*  98:    */     {
/*  99:213 */       JComponent editorField = (JComponent)editor.getComponent(0);
/* 100:214 */       Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
/* 101:215 */       editorField.setBorder(new EmptyBorder(insets));
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static final class SpinnerArrowButton
/* 106:    */     extends PlasticArrowButton
/* 107:    */   {
/* 108:    */     private SpinnerArrowButton(int direction)
/* 109:    */     {
/* 110:225 */       super(UIManager.getInt("ScrollBar.width"), true);
/* 111:    */     }
/* 112:    */     
/* 113:    */     protected int calculateArrowHeight(int height, int width)
/* 114:    */     {
/* 115:229 */       int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
/* 116:230 */       return Math.max(arrowHeight, 3);
/* 117:    */     }
/* 118:    */     
/* 119:    */     protected int calculateArrowOffset()
/* 120:    */     {
/* 121:234 */       return 1;
/* 122:    */     }
/* 123:    */     
/* 124:    */     protected boolean isPaintingNorthBottom()
/* 125:    */     {
/* 126:238 */       return true;
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticSpinnerUI
 * JD-Core Version:    0.7.0.1
 */