/*     */ package com.jgoodies.looks.plastic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameUI;
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
/*     */ public class PlasticInternalFrameUI
/*     */   extends BasicInternalFrameUI
/*     */ {
/*     */   private static final String FRAME_TYPE = "JInternalFrame.frameType";
/*     */   public static final String IS_PALETTE = "JInternalFrame.isPalette";
/*     */   private static final String PALETTE_FRAME = "palette";
/*     */   private static final String OPTION_DIALOG = "optionDialog";
/*  69 */   private static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
/*     */   
/*     */   private PlasticInternalFrameTitlePane titlePane;
/*     */   
/*     */   private PropertyChangeListener paletteListener;
/*     */   private PropertyChangeListener contentPaneListener;
/*     */   
/*     */   public PlasticInternalFrameUI(JInternalFrame b)
/*     */   {
/*  78 */     super(b);
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent c)
/*     */   {
/*  83 */     return new PlasticInternalFrameUI((JInternalFrame)c);
/*     */   }
/*     */   
/*     */   public void installUI(JComponent c)
/*     */   {
/*  88 */     this.frame = ((JInternalFrame)c);
/*     */     
/*  90 */     this.paletteListener = new PaletteListener(this, null);
/*  91 */     this.contentPaneListener = new ContentPaneListener(this, null);
/*  92 */     c.addPropertyChangeListener(this.paletteListener);
/*  93 */     c.addPropertyChangeListener(this.contentPaneListener);
/*     */     
/*  95 */     super.installUI(c);
/*     */     
/*  97 */     Object paletteProp = c.getClientProperty("JInternalFrame.isPalette");
/*  98 */     if (paletteProp != null) {
/*  99 */       setPalette(((Boolean)paletteProp).booleanValue());
/*     */     }
/*     */     
/* 102 */     Container content = this.frame.getContentPane();
/* 103 */     stripContentBorder(content);
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent c)
/*     */   {
/* 108 */     this.frame = ((JInternalFrame)c);
/*     */     
/* 110 */     c.removePropertyChangeListener(this.paletteListener);
/* 111 */     c.removePropertyChangeListener(this.contentPaneListener);
/*     */     
/* 113 */     Container cont = ((JInternalFrame)c).getContentPane();
/* 114 */     if ((cont instanceof JComponent)) {
/* 115 */       JComponent content = (JComponent)cont;
/* 116 */       if (content.getBorder() == EMPTY_BORDER) {
/* 117 */         content.setBorder(null);
/*     */       }
/*     */     }
/* 120 */     super.uninstallUI(c);
/*     */   }
/*     */   
/*     */   protected void installDefaults()
/*     */   {
/* 125 */     super.installDefaults();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 130 */     JComponent contentPane = (JComponent)this.frame.getContentPane();
/* 131 */     if (contentPane != null) {
/* 132 */       Color bg = contentPane.getBackground();
/* 133 */       if ((bg instanceof UIResource))
/* 134 */         contentPane.setBackground(null);
/*     */     }
/* 136 */     this.frame.setBackground(UIManager.getLookAndFeelDefaults().getColor("control"));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void installKeyboardActions() {}
/*     */   
/*     */ 
/*     */   protected void uninstallKeyboardActions() {}
/*     */   
/*     */ 
/*     */   private void stripContentBorder(Object c)
/*     */   {
/* 148 */     if ((c instanceof JComponent)) {
/* 149 */       JComponent contentComp = (JComponent)c;
/* 150 */       Border contentBorder = contentComp.getBorder();
/* 151 */       if ((contentBorder == null) || ((contentBorder instanceof UIResource))) {
/* 152 */         contentComp.setBorder(EMPTY_BORDER);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected JComponent createNorthPane(JInternalFrame w)
/*     */   {
/* 159 */     this.titlePane = new PlasticInternalFrameTitlePane(w);
/* 160 */     return this.titlePane;
/*     */   }
/*     */   
/*     */   public void setPalette(boolean isPalette)
/*     */   {
/* 165 */     String key = isPalette ? "InternalFrame.paletteBorder" : "InternalFrame.border";
/* 166 */     LookAndFeel.installBorder(this.frame, key);
/* 167 */     this.titlePane.setPalette(isPalette);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setFrameType(String frameType)
/*     */   {
/* 173 */     boolean hasPalette = frameType.equals("palette");
/* 174 */     String key; String key; if (frameType.equals("optionDialog")) {
/* 175 */       key = "InternalFrame.optionDialogBorder"; } else { String key;
/* 176 */       if (hasPalette) {
/* 177 */         key = "InternalFrame.paletteBorder";
/*     */       } else
/* 179 */         key = "InternalFrame.border";
/*     */     }
/* 181 */     LookAndFeel.installBorder(this.frame, key);
/* 182 */     this.titlePane.setPalette(hasPalette);
/*     */   }
/*     */   
/*     */   private static final class PaletteListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private final PlasticInternalFrameUI ui;
/*     */     
/* 190 */     private PaletteListener(PlasticInternalFrameUI ui) { this.ui = ui; }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 193 */       String name = e.getPropertyName();
/* 194 */       Object value = e.getNewValue();
/* 195 */       if (name.equals("JInternalFrame.frameType")) {
/* 196 */         if ((value instanceof String)) {
/* 197 */           this.ui.setFrameType((String)value);
/*     */         }
/* 199 */       } else if (name.equals("JInternalFrame.isPalette")) {
/* 200 */         this.ui.setPalette(Boolean.TRUE.equals(value));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ContentPaneListener
/*     */     implements PropertyChangeListener {
/*     */     private final PlasticInternalFrameUI ui;
/*     */     
/* 209 */     private ContentPaneListener(PlasticInternalFrameUI ui) { this.ui = ui; }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent e) {
/* 212 */       String name = e.getPropertyName();
/* 213 */       if (name.equals("contentPane")) {
/* 214 */         this.ui.stripContentBorder(e.getNewValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\PlasticInternalFrameUI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */