/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.text.CharacterIterator;
/*     */ import java.text.StringCharacterIterator;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JLabel;
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
/*     */ class MnemonicText
/*     */ {
/*     */   private static final String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
/*     */   
/*     */   public static void configure(Object target, String markedText)
/*     */   {
/*  38 */     String text = markedText;
/*  39 */     int mnemonicIndex = -1;
/*  40 */     int mnemonicKey = 0;
/*     */     
/*  42 */     int markerIndex = mnemonicMarkerIndex(markedText, '&');
/*  43 */     if (markerIndex == -1) {
/*  44 */       markerIndex = mnemonicMarkerIndex(markedText, '_');
/*     */     }
/*  46 */     if (markerIndex != -1) {
/*  47 */       text = text.substring(0, markerIndex) + text.substring(markerIndex + 1);
/*  48 */       mnemonicIndex = markerIndex;
/*  49 */       CharacterIterator sci = new StringCharacterIterator(markedText, markerIndex);
/*  50 */       mnemonicKey = mnemonicKey(sci.next());
/*     */     }
/*  52 */     if ((target instanceof Action)) {
/*  53 */       configureAction((Action)target, text, mnemonicKey, mnemonicIndex);
/*     */     }
/*  55 */     else if ((target instanceof AbstractButton)) {
/*  56 */       configureButton((AbstractButton)target, text, mnemonicKey, mnemonicIndex);
/*     */     }
/*  58 */     else if ((target instanceof JLabel)) {
/*  59 */       configureLabel((JLabel)target, text, mnemonicKey, mnemonicIndex);
/*     */     }
/*     */     else {
/*  62 */       throw new IllegalArgumentException("unrecognized target type " + target);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int mnemonicMarkerIndex(String s, char marker) {
/*  67 */     if ((s == null) || (s.length() < 2)) return -1;
/*  68 */     CharacterIterator sci = new StringCharacterIterator(s);
/*  69 */     int i = 0;
/*  70 */     while (i != -1) {
/*  71 */       i = s.indexOf(marker, i);
/*  72 */       if (i != -1) {
/*  73 */         sci.setIndex(i);
/*  74 */         char c1 = sci.previous();
/*  75 */         sci.setIndex(i);
/*  76 */         char c2 = sci.next();
/*  77 */         boolean isQuote = (c1 == '\'') && (c2 == '\'');
/*  78 */         boolean isSpace = Character.isWhitespace(c2);
/*  79 */         if ((!isQuote) && (!isSpace) && (c2 != 65535)) {
/*  80 */           return i;
/*     */         }
/*     */       }
/*  83 */       if (i != -1) i++;
/*     */     }
/*  85 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int mnemonicKey(char c)
/*     */   {
/*  94 */     int vk = c;
/*  95 */     if ((vk >= 97) && (vk <= 122)) {
/*  96 */       vk -= 32;
/*     */     }
/*  98 */     return vk;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void configureAction(Action target, String text, int key, int index)
/*     */   {
/* 108 */     target.putValue("Name", text);
/* 109 */     if (key != 0) target.putValue("MnemonicKey", Integer.valueOf(key));
/* 110 */     if (index != -1) target.putValue("SwingDisplayedMnemonicIndexKey", Integer.valueOf(index));
/*     */   }
/*     */   
/*     */   private static void configureButton(AbstractButton target, String text, int key, int index) {
/* 114 */     target.setText(text);
/* 115 */     if (key != 0) target.setMnemonic(key);
/* 116 */     if (index != -1) target.setDisplayedMnemonicIndex(index);
/*     */   }
/*     */   
/*     */   private static void configureLabel(JLabel target, String text, int key, int index) {
/* 120 */     target.setText(text);
/* 121 */     if (key != 0) target.setDisplayedMnemonic(key);
/* 122 */     if (index != -1) target.setDisplayedMnemonicIndex(index);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\MnemonicText.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */