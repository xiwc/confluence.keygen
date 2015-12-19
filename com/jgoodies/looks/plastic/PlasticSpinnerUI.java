/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import com.jgoodies.looks.common.ExtBasicSpinnerLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JSpinner.DefaultEditor;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSpinnerUI;
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
/*     */ public class PlasticSpinnerUI
/*     */   extends BasicSpinnerUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent b)
/*     */   {
/*  59 */     return new PlasticSpinnerUI();
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
/*     */ 
/*     */   protected Component createPreviousButton()
/*     */   {
/*  77 */     Component c = createArrowButton(5);
/*  78 */     installPreviousButtonListenersFromSuper(c);
/*  79 */     return c;
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
/*     */ 
/*     */   protected Component createNextButton()
/*     */   {
/*  97 */     Component c = createArrowButton(1);
/*  98 */     installNextButtonListenersFromSuper(c);
/*  99 */     return c;
/*     */   }
/*     */   
/*     */   protected Component createArrowButton(int direction)
/*     */   {
/* 104 */     return new SpinnerArrowButton(direction, null);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void installPreviousButtonListenersFromSuper(Component c)
/*     */   {
/* 110 */     AbstractButton sc = (AbstractButton)super.createPreviousButton();
/* 111 */     ActionListener[] als = sc.getActionListeners();
/* 112 */     MouseListener[] mls = sc.getMouseListeners();
/* 113 */     if ((c instanceof AbstractButton)) {
/* 114 */       ((AbstractButton)c).addActionListener(als[0]);
/*     */     }
/* 116 */     c.addMouseListener(mls[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void installNextButtonListenersFromSuper(Component c)
/*     */   {
/* 122 */     AbstractButton sc = (AbstractButton)super.createNextButton();
/* 123 */     ActionListener[] als = sc.getActionListeners();
/* 124 */     MouseListener[] mls = sc.getMouseListeners();
/* 125 */     if ((c instanceof AbstractButton)) {
/* 126 */       ((AbstractButton)c).addActionListener(als[0]);
/*     */     }
/* 128 */     c.addMouseListener(mls[0]);
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
/*     */ 
/*     */   protected LayoutManager createLayout()
/*     */   {
/* 146 */     return new ExtBasicSpinnerLayout();
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
/*     */   protected JComponent createEditor()
/*     */   {
/* 174 */     JComponent editor = this.spinner.getEditor();
/* 175 */     configureEditorBorder(editor);
/* 176 */     return editor;
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
/*     */ 
/*     */ 
/*     */   protected void replaceEditor(JComponent oldEditor, JComponent newEditor)
/*     */   {
/* 195 */     this.spinner.remove(oldEditor);
/* 196 */     configureEditorBorder(newEditor);
/* 197 */     this.spinner.add(newEditor, "Editor");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void configureEditorBorder(JComponent editor)
/*     */   {
/* 205 */     if ((editor instanceof JSpinner.DefaultEditor)) {
/* 206 */       JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor)editor;
/* 207 */       JTextField editorField = defaultEditor.getTextField();
/* 208 */       Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
/* 209 */       editorField.setBorder(new EmptyBorder(insets));
/* 210 */     } else if (((editor instanceof JPanel)) && (editor.getBorder() == null) && (editor.getComponentCount() > 0))
/*     */     {
/*     */ 
/* 213 */       JComponent editorField = (JComponent)editor.getComponent(0);
/* 214 */       Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
/* 215 */       editorField.setBorder(new EmptyBorder(insets));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class SpinnerArrowButton
/*     */     extends PlasticArrowButton
/*     */   {
/*     */     private SpinnerArrowButton(int direction)
/*     */     {
/* 225 */       super(UIManager.getInt("ScrollBar.width"), true);
/*     */     }
/*     */     
/*     */     protected int calculateArrowHeight(int height, int width) {
/* 229 */       int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
/* 230 */       return Math.max(arrowHeight, 3);
/*     */     }
/*     */     
/*     */     protected int calculateArrowOffset() {
/* 234 */       return 1;
/*     */     }
/*     */     
/*     */     protected boolean isPaintingNorthBottom() {
/* 238 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticSpinnerUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */