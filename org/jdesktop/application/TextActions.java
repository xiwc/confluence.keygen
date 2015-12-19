/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.AWTEvent;
/*   4:    */ import java.awt.EventQueue;
/*   5:    */ import java.awt.datatransfer.Clipboard;
/*   6:    */ import java.awt.datatransfer.DataFlavor;
/*   7:    */ import java.awt.datatransfer.FlavorEvent;
/*   8:    */ import java.awt.datatransfer.FlavorListener;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.InputEvent;
/*  11:    */ import java.beans.PropertyChangeEvent;
/*  12:    */ import java.beans.PropertyChangeListener;
/*  13:    */ import javax.swing.AbstractAction;
/*  14:    */ import javax.swing.ActionMap;
/*  15:    */ import javax.swing.JComponent;
/*  16:    */ import javax.swing.event.CaretEvent;
/*  17:    */ import javax.swing.event.CaretListener;
/*  18:    */ import javax.swing.text.Caret;
/*  19:    */ import javax.swing.text.JTextComponent;
/*  20:    */ 
/*  21:    */ class TextActions
/*  22:    */   extends AbstractBean
/*  23:    */ {
/*  24:    */   private final ApplicationContext context;
/*  25:    */   private final CaretListener textComponentCaretListener;
/*  26:    */   private final PropertyChangeListener textComponentPCL;
/*  27: 51 */   private final String markerActionKey = "TextActions.markerAction";
/*  28:    */   private final javax.swing.Action markerAction;
/*  29: 53 */   private boolean copyEnabled = false;
/*  30: 54 */   private boolean cutEnabled = false;
/*  31: 55 */   private boolean pasteEnabled = false;
/*  32: 56 */   private boolean deleteEnabled = false;
/*  33:    */   
/*  34:    */   public TextActions(ApplicationContext context)
/*  35:    */   {
/*  36: 59 */     this.context = context;
/*  37: 60 */     this.markerAction = new AbstractAction()
/*  38:    */     {
/*  39:    */       public void actionPerformed(ActionEvent e) {}
/*  40: 62 */     };
/*  41: 63 */     this.textComponentCaretListener = new TextComponentCaretListener(null);
/*  42: 64 */     this.textComponentPCL = new TextComponentPCL(null);
/*  43: 65 */     getClipboard().addFlavorListener(new ClipboardListener(null));
/*  44:    */   }
/*  45:    */   
/*  46:    */   private ApplicationContext getContext()
/*  47:    */   {
/*  48: 69 */     return this.context;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private JComponent getFocusOwner()
/*  52:    */   {
/*  53: 73 */     return getContext().getFocusOwner();
/*  54:    */   }
/*  55:    */   
/*  56:    */   private Clipboard getClipboard()
/*  57:    */   {
/*  58: 77 */     return getContext().getClipboard();
/*  59:    */   }
/*  60:    */   
/*  61:    */   void updateFocusOwner(JComponent oldOwner, JComponent newOwner)
/*  62:    */   {
/*  63: 84 */     if ((oldOwner instanceof JTextComponent))
/*  64:    */     {
/*  65: 85 */       JTextComponent text = (JTextComponent)oldOwner;
/*  66: 86 */       text.removeCaretListener(this.textComponentCaretListener);
/*  67: 87 */       text.removePropertyChangeListener(this.textComponentPCL);
/*  68:    */     }
/*  69: 89 */     if ((newOwner instanceof JTextComponent))
/*  70:    */     {
/*  71: 90 */       JTextComponent text = (JTextComponent)newOwner;
/*  72: 91 */       maybeInstallTextActions(text);
/*  73: 92 */       updateTextActions(text);
/*  74: 93 */       text.addCaretListener(this.textComponentCaretListener);
/*  75: 94 */       text.addPropertyChangeListener(this.textComponentPCL);
/*  76:    */     }
/*  77: 96 */     else if (newOwner == null)
/*  78:    */     {
/*  79: 97 */       setCopyEnabled(false);
/*  80: 98 */       setCutEnabled(false);
/*  81: 99 */       setPasteEnabled(false);
/*  82:100 */       setDeleteEnabled(false);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private final class ClipboardListener
/*  87:    */     implements FlavorListener
/*  88:    */   {
/*  89:    */     private ClipboardListener() {}
/*  90:    */     
/*  91:    */     public void flavorsChanged(FlavorEvent e)
/*  92:    */     {
/*  93:106 */       JComponent c = TextActions.this.getFocusOwner();
/*  94:107 */       if ((c instanceof JTextComponent)) {
/*  95:108 */         TextActions.this.updateTextActions((JTextComponent)c);
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private final class TextComponentCaretListener
/* 101:    */     implements CaretListener
/* 102:    */   {
/* 103:    */     private TextComponentCaretListener() {}
/* 104:    */     
/* 105:    */     public void caretUpdate(CaretEvent e)
/* 106:    */     {
/* 107:115 */       TextActions.this.updateTextActions((JTextComponent)e.getSource());
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private final class TextComponentPCL
/* 112:    */     implements PropertyChangeListener
/* 113:    */   {
/* 114:    */     private TextComponentPCL() {}
/* 115:    */     
/* 116:    */     public void propertyChange(PropertyChangeEvent e)
/* 117:    */     {
/* 118:121 */       String propertyName = e.getPropertyName();
/* 119:122 */       if ((propertyName == null) || ("editable".equals(propertyName))) {
/* 120:123 */         TextActions.this.updateTextActions((JTextComponent)e.getSource());
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void updateTextActions(JTextComponent text)
/* 126:    */   {
/* 127:129 */     Caret caret = text.getCaret();
/* 128:130 */     boolean selection = caret.getDot() != caret.getMark();
/* 129:131 */     boolean editable = text.isEditable();
/* 130:132 */     boolean data = getClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor);
/* 131:133 */     setCopyEnabled(selection);
/* 132:134 */     setCutEnabled((editable) && (selection));
/* 133:135 */     setDeleteEnabled((editable) && (selection));
/* 134:136 */     setPasteEnabled((editable) && (data));
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void maybeInstallTextActions(JTextComponent text)
/* 138:    */   {
/* 139:141 */     ActionMap actionMap = text.getActionMap();
/* 140:142 */     if (actionMap.get("TextActions.markerAction") == null)
/* 141:    */     {
/* 142:143 */       actionMap.put("TextActions.markerAction", this.markerAction);
/* 143:144 */       ActionMap textActions = getContext().getActionMap(getClass(), this);
/* 144:145 */       for (Object key : textActions.keys()) {
/* 145:146 */         actionMap.put(key, textActions.get(key));
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   private int getCurrentEventModifiers()
/* 151:    */   {
/* 152:155 */     int modifiers = 0;
/* 153:156 */     AWTEvent currentEvent = EventQueue.getCurrentEvent();
/* 154:157 */     if ((currentEvent instanceof InputEvent)) {
/* 155:158 */       modifiers = ((InputEvent)currentEvent).getModifiers();
/* 156:160 */     } else if ((currentEvent instanceof ActionEvent)) {
/* 157:161 */       modifiers = ((ActionEvent)currentEvent).getModifiers();
/* 158:    */     }
/* 159:163 */     return modifiers;
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void invokeTextAction(JTextComponent text, String actionName)
/* 163:    */   {
/* 164:167 */     ActionMap actionMap = text.getActionMap().getParent();
/* 165:168 */     long eventTime = EventQueue.getMostRecentEventTime();
/* 166:169 */     int eventMods = getCurrentEventModifiers();
/* 167:170 */     ActionEvent actionEvent = new ActionEvent(text, 1001, actionName, eventTime, eventMods);
/* 168:    */     
/* 169:172 */     actionMap.get(actionName).actionPerformed(actionEvent);
/* 170:    */   }
/* 171:    */   
/* 172:    */   @Action(enabledProperty="cutEnabled")
/* 173:    */   public void cut(ActionEvent e)
/* 174:    */   {
/* 175:177 */     Object src = e.getSource();
/* 176:178 */     if ((src instanceof JTextComponent)) {
/* 177:179 */       invokeTextAction((JTextComponent)src, "cut");
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isCutEnabled()
/* 182:    */   {
/* 183:183 */     return this.cutEnabled;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setCutEnabled(boolean cutEnabled)
/* 187:    */   {
/* 188:186 */     boolean oldValue = this.cutEnabled;
/* 189:187 */     this.cutEnabled = cutEnabled;
/* 190:188 */     firePropertyChange("cutEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.cutEnabled));
/* 191:    */   }
/* 192:    */   
/* 193:    */   @Action(enabledProperty="copyEnabled")
/* 194:    */   public void copy(ActionEvent e)
/* 195:    */   {
/* 196:193 */     Object src = e.getSource();
/* 197:194 */     if ((src instanceof JTextComponent)) {
/* 198:195 */       invokeTextAction((JTextComponent)src, "copy");
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isCopyEnabled()
/* 203:    */   {
/* 204:199 */     return this.copyEnabled;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setCopyEnabled(boolean copyEnabled)
/* 208:    */   {
/* 209:202 */     boolean oldValue = this.copyEnabled;
/* 210:203 */     this.copyEnabled = copyEnabled;
/* 211:204 */     firePropertyChange("copyEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.copyEnabled));
/* 212:    */   }
/* 213:    */   
/* 214:    */   @Action(enabledProperty="pasteEnabled")
/* 215:    */   public void paste(ActionEvent e)
/* 216:    */   {
/* 217:209 */     Object src = e.getSource();
/* 218:210 */     if ((src instanceof JTextComponent)) {
/* 219:211 */       invokeTextAction((JTextComponent)src, "paste");
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isPasteEnabled()
/* 224:    */   {
/* 225:215 */     return this.pasteEnabled;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setPasteEnabled(boolean pasteEnabled)
/* 229:    */   {
/* 230:218 */     boolean oldValue = this.pasteEnabled;
/* 231:219 */     this.pasteEnabled = pasteEnabled;
/* 232:220 */     firePropertyChange("pasteEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.pasteEnabled));
/* 233:    */   }
/* 234:    */   
/* 235:    */   @Action(enabledProperty="deleteEnabled")
/* 236:    */   public void delete(ActionEvent e)
/* 237:    */   {
/* 238:225 */     Object src = e.getSource();
/* 239:226 */     if ((src instanceof JTextComponent)) {
/* 240:234 */       invokeTextAction((JTextComponent)src, "delete-next");
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean isDeleteEnabled()
/* 245:    */   {
/* 246:238 */     return this.deleteEnabled;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setDeleteEnabled(boolean deleteEnabled)
/* 250:    */   {
/* 251:241 */     boolean oldValue = this.deleteEnabled;
/* 252:242 */     this.deleteEnabled = deleteEnabled;
/* 253:243 */     firePropertyChange("deleteEnabled", Boolean.valueOf(oldValue), Boolean.valueOf(this.deleteEnabled));
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.TextActions
 * JD-Core Version:    0.7.0.1
 */