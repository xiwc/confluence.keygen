/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.text.CharacterIterator;
/*   4:    */ import java.text.StringCharacterIterator;
/*   5:    */ import javax.swing.AbstractButton;
/*   6:    */ import javax.swing.Action;
/*   7:    */ import javax.swing.JLabel;
/*   8:    */ 
/*   9:    */ class MnemonicText
/*  10:    */ {
/*  11:    */   private static final String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
/*  12:    */   
/*  13:    */   public static void configure(Object target, String markedText)
/*  14:    */   {
/*  15: 38 */     String text = markedText;
/*  16: 39 */     int mnemonicIndex = -1;
/*  17: 40 */     int mnemonicKey = 0;
/*  18:    */     
/*  19: 42 */     int markerIndex = mnemonicMarkerIndex(markedText, '&');
/*  20: 43 */     if (markerIndex == -1) {
/*  21: 44 */       markerIndex = mnemonicMarkerIndex(markedText, '_');
/*  22:    */     }
/*  23: 46 */     if (markerIndex != -1)
/*  24:    */     {
/*  25: 47 */       text = text.substring(0, markerIndex) + text.substring(markerIndex + 1);
/*  26: 48 */       mnemonicIndex = markerIndex;
/*  27: 49 */       CharacterIterator sci = new StringCharacterIterator(markedText, markerIndex);
/*  28: 50 */       mnemonicKey = mnemonicKey(sci.next());
/*  29:    */     }
/*  30: 52 */     if ((target instanceof Action)) {
/*  31: 53 */       configureAction((Action)target, text, mnemonicKey, mnemonicIndex);
/*  32: 55 */     } else if ((target instanceof AbstractButton)) {
/*  33: 56 */       configureButton((AbstractButton)target, text, mnemonicKey, mnemonicIndex);
/*  34: 58 */     } else if ((target instanceof JLabel)) {
/*  35: 59 */       configureLabel((JLabel)target, text, mnemonicKey, mnemonicIndex);
/*  36:    */     } else {
/*  37: 62 */       throw new IllegalArgumentException("unrecognized target type " + target);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   private static int mnemonicMarkerIndex(String s, char marker)
/*  42:    */   {
/*  43: 67 */     if ((s == null) || (s.length() < 2)) {
/*  44: 67 */       return -1;
/*  45:    */     }
/*  46: 68 */     CharacterIterator sci = new StringCharacterIterator(s);
/*  47: 69 */     int i = 0;
/*  48: 70 */     while (i != -1)
/*  49:    */     {
/*  50: 71 */       i = s.indexOf(marker, i);
/*  51: 72 */       if (i != -1)
/*  52:    */       {
/*  53: 73 */         sci.setIndex(i);
/*  54: 74 */         char c1 = sci.previous();
/*  55: 75 */         sci.setIndex(i);
/*  56: 76 */         char c2 = sci.next();
/*  57: 77 */         boolean isQuote = (c1 == '\'') && (c2 == '\'');
/*  58: 78 */         boolean isSpace = Character.isWhitespace(c2);
/*  59: 79 */         if ((!isQuote) && (!isSpace) && (c2 != 65535)) {
/*  60: 80 */           return i;
/*  61:    */         }
/*  62:    */       }
/*  63: 83 */       if (i != -1) {
/*  64: 83 */         i++;
/*  65:    */       }
/*  66:    */     }
/*  67: 85 */     return -1;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static int mnemonicKey(char c)
/*  71:    */   {
/*  72: 94 */     int vk = c;
/*  73: 95 */     if ((vk >= 97) && (vk <= 122)) {
/*  74: 96 */       vk -= 32;
/*  75:    */     }
/*  76: 98 */     return vk;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static void configureAction(Action target, String text, int key, int index)
/*  80:    */   {
/*  81:108 */     target.putValue("Name", text);
/*  82:109 */     if (key != 0) {
/*  83:109 */       target.putValue("MnemonicKey", Integer.valueOf(key));
/*  84:    */     }
/*  85:110 */     if (index != -1) {
/*  86:110 */       target.putValue("SwingDisplayedMnemonicIndexKey", Integer.valueOf(index));
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static void configureButton(AbstractButton target, String text, int key, int index)
/*  91:    */   {
/*  92:114 */     target.setText(text);
/*  93:115 */     if (key != 0) {
/*  94:115 */       target.setMnemonic(key);
/*  95:    */     }
/*  96:116 */     if (index != -1) {
/*  97:116 */       target.setDisplayedMnemonicIndex(index);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static void configureLabel(JLabel target, String text, int key, int index)
/* 102:    */   {
/* 103:120 */     target.setText(text);
/* 104:121 */     if (key != 0) {
/* 105:121 */       target.setDisplayedMnemonic(key);
/* 106:    */     }
/* 107:122 */     if (index != -1) {
/* 108:122 */       target.setDisplayedMnemonicIndex(index);
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.MnemonicText
 * JD-Core Version:    0.7.0.1
 */