/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.FlavorEvent;
/*     */ import java.awt.datatransfer.FlavorListener;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.CaretEvent;
/*     */ import javax.swing.event.CaretListener;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.JTextComponent;
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
/*     */ class TextActions
/*     */   extends AbstractBean
/*     */ {
/*     */   private final ApplicationContext context;
/*     */   private final CaretListener textComponentCaretListener;
/*     */   private final PropertyChangeListener textComponentPCL;
/*  51 */   private final String markerActionKey = "TextActions.markerAction";
/*     */   private final javax.swing.Action markerAction;
/*  53 */   private boolean copyEnabled = false;
/*  54 */   private boolean cutEnabled = false;
/*  55 */   private boolean pasteEnabled = false;
/*  56 */   private boolean deleteEnabled = false;
/*     */   
/*     */   public TextActions(ApplicationContext context) {
/*  59 */     this.context = context;
/*  60 */     this.markerAction = new AbstractAction() {
/*     */       public void actionPerformed(ActionEvent e) {}
/*  62 */     };
/*  63 */     this.textComponentCaretListener = new TextComponentCaretListener(null);
/*  64 */     this.textComponentPCL = new TextComponentPCL(null);
/*  65 */     getClipboard().addFlavorListener(new ClipboardListener(null));
/*     */   }
/*     */   
/*     */   private ApplicationContext getContext() {
/*  69 */     return this.context;
/*     */   }
/*     */   
/*     */   private JComponent getFocusOwner() {
/*  73 */     return getContext().getFocusOwner();
/*     */   }
/*     */   
/*     */   private Clipboard getClipboard() {
/*  77 */     return getContext().getClipboard();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void updateFocusOwner(JComponent oldOwner, JComponent newOwner)
/*     */   {
/*  84 */     if ((oldOwner instanceof JTextComponent)) {
/*  85 */       JTextComponent text = (JTextComponent)oldOwner;
/*  86 */       text.removeCaretListener(this.textComponentCaretListener);
/*  87 */       text.removePropertyChangeListener(this.textComponentPCL);
/*     */     }
/*  89 */     if ((newOwner instanceof JTextComponent)) {
/*  90 */       JTextComponent text = (JTextComponent)newOwner;
/*  91 */       maybeInstallTextActions(text);
/*  92 */       updateTextActions(text);
/*  93 */       text.addCaretListener(this.textComponentCaretListener);
/*  94 */       text.addPropertyChangeListener(this.textComponentPCL);
/*     */     }
/*  96 */     else if (newOwner == null) {
/*  97 */       setCopyEnabled(false);
/*  98 */       setCutEnabled(false);
/*  99 */       setPasteEnabled(false);
/* 100 */       setDeleteEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ClipboardListener implements FlavorListener { private ClipboardListener() {}
/*     */     
/* 106 */     public void flavorsChanged(FlavorEvent e) { JComponent c = TextActions.this.getFocusOwner();
/* 107 */       if ((c instanceof JTextComponent))
/* 108 */         TextActions.this.updateTextActions((JTextComponent)c);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class TextComponentCaretListener implements CaretListener {
/*     */     private TextComponentCaretListener() {}
/*     */     
/* 115 */     public void caretUpdate(CaretEvent e) { TextActions.this.updateTextActions((JTextComponent)e.getSource()); }
/*     */   }
/*     */   
/*     */   private final class TextComponentPCL implements PropertyChangeListener {
/*     */     private TextComponentPCL() {}
/*     */     
/* 121 */     public void propertyChange(PropertyChangeEvent e) { String propertyName = e.getPropertyName();
/* 122 */       if ((propertyName == null) || ("editable".equals(propertyName))) {
/* 123 */         TextActions.this.updateTextActions((JTextComponent)e.getSource());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateTextActions(JTextComponent text) {
/* 129 */     Caret caret = text.getCaret();
/* 130 */     boolean selection = caret.getDot() != caret.getMark();
/* 131 */     boolean editable = text.isEditable();
/* 132 */     boolean data = getClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor);
/* 133 */     setCopyEnabled(selection);
/* 134 */     setCutEnabled((editable) && (selection));
/* 135 */     setDeleteEnabled((editable) && (selection));
/* 136 */     setPasteEnabled((editable) && (data));
/*     */   }
/*     */   
/*     */   private void maybeInstallTextActions(JTextComponent text)
/*     */   {
/* 141 */     ActionMap actionMap = text.getActionMap();
/* 142 */     if (actionMap.get("TextActions.markerAction") == null) {
/* 143 */       actionMap.put("TextActions.markerAction", this.markerAction);
/* 144 */       ActionMap textActions = getContext().getActionMap(getClass(), this);
/* 145 */       for (Object key : textActions.keys()) {
/* 146 */         actionMap.put(key, textActions.get(key));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getCurrentEventModifiers()
/*     */   {
/* 155 */     int modifiers = 0;
/* 156 */     AWTEvent currentEvent = EventQueue.getCurrentEvent();
/* 157 */     if ((currentEvent instanceof InputEvent)) {
/* 158 */       modifiers = ((InputEvent)currentEvent).getModifiers();
/*     */     }
/* 160 */     else if ((currentEvent instanceof ActionEvent)) {
/* 161 */       modifiers = ((ActionEvent)currentEvent).getModifiers();
/*     */     }
/* 163 */     return modifiers;
/*     */   }
/*     */   
/*     */   private void invokeTextAction(JTextComponent text, String actionName) {
/* 167 */     ActionMap actionMap = text.getActionMap().getParent();
/* 168 */     long eventTime = EventQueue.getMostRecentEventTime();
/* 169 */     int eventMods = getCurrentEventModifiers();
/* 170 */     ActionEvent actionEvent = new ActionEvent(text, 1001, actionName, eventTime, eventMods);
/*     */     
/* 172 */     actionMap.get(actionName).actionPerformed(actionEvent);
/*     */   }
/*     */   
/*     */   @Action(enabledProperty="cutEnabled")
/*     */   public void cut(ActionEvent e) {
/* 177 */     Object src = e.getSource();
/* 178 */     if ((src instanceof JTextComponent)) {
/* 179 */       invokeTextAction((JTextComponent)src, "cut");
/*     */     }
/*     */   }
/*     */   
/* 183 */   public boolean isCutEnabled() { return this.cutEnabled; }
/*     */   
/*     */   public void setCutEnabled(boolean cutEnabled) {
/* 186 */     boolean oldValue = this.cutEnabled;
/* 187 */     this.cutEnabled = cutEnabled;
/* 188 */     firePropertyChange("cutEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.cutEnabled));
/*     */   }
/*     */   
/*     */   @Action(enabledProperty="copyEnabled")
/*     */   public void copy(ActionEvent e) {
/* 193 */     Object src = e.getSource();
/* 194 */     if ((src instanceof JTextComponent)) {
/* 195 */       invokeTextAction((JTextComponent)src, "copy");
/*     */     }
/*     */   }
/*     */   
/* 199 */   public boolean isCopyEnabled() { return this.copyEnabled; }
/*     */   
/*     */   public void setCopyEnabled(boolean copyEnabled) {
/* 202 */     boolean oldValue = this.copyEnabled;
/* 203 */     this.copyEnabled = copyEnabled;
/* 204 */     firePropertyChange("copyEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.copyEnabled));
/*     */   }
/*     */   
/*     */   @Action(enabledProperty="pasteEnabled")
/*     */   public void paste(ActionEvent e) {
/* 209 */     Object src = e.getSource();
/* 210 */     if ((src instanceof JTextComponent)) {
/* 211 */       invokeTextAction((JTextComponent)src, "paste");
/*     */     }
/*     */   }
/*     */   
/* 215 */   public boolean isPasteEnabled() { return this.pasteEnabled; }
/*     */   
/*     */   public void setPasteEnabled(boolean pasteEnabled) {
/* 218 */     boolean oldValue = this.pasteEnabled;
/* 219 */     this.pasteEnabled = pasteEnabled;
/* 220 */     firePropertyChange("pasteEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.pasteEnabled));
/*     */   }
/*     */   
/*     */   @Action(enabledProperty="deleteEnabled")
/*     */   public void delete(ActionEvent e) {
/* 225 */     Object src = e.getSource();
/* 226 */     if ((src instanceof JTextComponent))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 234 */       invokeTextAction((JTextComponent)src, "delete-next");
/*     */     }
/*     */   }
/*     */   
/* 238 */   public boolean isDeleteEnabled() { return this.deleteEnabled; }
/*     */   
/*     */   public void setDeleteEnabled(boolean deleteEnabled) {
/* 241 */     boolean oldValue = this.deleteEnabled;
/* 242 */     this.deleteEnabled = deleteEnabled;
/* 243 */     firePropertyChange("deleteEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.deleteEnabled));
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\TextActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */