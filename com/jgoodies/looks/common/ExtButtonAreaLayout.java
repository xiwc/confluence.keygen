/*   1:    */ package com.jgoodies.looks.common;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.LookUtils;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.ComponentOrientation;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.Insets;
/*   9:    */ import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;
/*  10:    */ 
/*  11:    */ public final class ExtButtonAreaLayout
/*  12:    */   extends BasicOptionPaneUI.ButtonAreaLayout
/*  13:    */ {
/*  14:    */   public ExtButtonAreaLayout(boolean syncAllWidths, int padding)
/*  15:    */   {
/*  16: 60 */     super(syncAllWidths, padding);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void layoutContainer(Container container)
/*  20:    */   {
/*  21: 64 */     Component[] children = container.getComponents();
/*  22: 66 */     if ((children != null) && (children.length > 0))
/*  23:    */     {
/*  24: 67 */       int numChildren = children.length;
/*  25: 68 */       Dimension[] sizes = new Dimension[numChildren];
/*  26:    */       
/*  27: 70 */       int yLocation = container.getInsets().top;
/*  28: 72 */       if (this.syncAllWidths)
/*  29:    */       {
/*  30: 73 */         int maxWidth = getMinimumButtonWidth();
/*  31: 75 */         for (int counter = 0; counter < numChildren; counter++)
/*  32:    */         {
/*  33: 76 */           sizes[counter] = children[counter].getPreferredSize();
/*  34: 77 */           maxWidth = Math.max(maxWidth, sizes[counter].width);
/*  35:    */         }
/*  36:    */         int xOffset;
/*  37:    */         int xLocation;
/*  38:    */         int xOffset;
/*  39: 83 */         if (getCentersChildren())
/*  40:    */         {
/*  41: 84 */           int xLocation = (container.getSize().width - (maxWidth * numChildren + (numChildren - 1) * this.padding)) / 2;
/*  42:    */           
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 89 */           xOffset = this.padding + maxWidth;
/*  47:    */         }
/*  48:    */         else
/*  49:    */         {
/*  50:    */           int xOffset;
/*  51: 91 */           if (numChildren > 1)
/*  52:    */           {
/*  53: 92 */             int xLocation = 0;
/*  54: 93 */             xOffset = (container.getSize().width - maxWidth * numChildren) / (numChildren - 1) + maxWidth;
/*  55:    */           }
/*  56:    */           else
/*  57:    */           {
/*  58: 99 */             xLocation = (container.getSize().width - maxWidth) / 2;
/*  59:100 */             xOffset = 0;
/*  60:    */           }
/*  61:    */         }
/*  62:103 */         boolean ltr = container.getComponentOrientation().isLeftToRight();
/*  63:105 */         for (counter = 0; counter < numChildren; counter++)
/*  64:    */         {
/*  65:106 */           int index = ltr ? counter : numChildren - counter - 1;
/*  66:    */           
/*  67:    */ 
/*  68:109 */           children[index].setBounds(xLocation, yLocation, maxWidth, sizes[index].height);
/*  69:    */           
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:114 */           xLocation += xOffset;
/*  74:    */         }
/*  75:    */       }
/*  76:    */       else
/*  77:    */       {
/*  78:117 */         int totalWidth = 0;
/*  79:119 */         for (int counter = 0; counter < numChildren; counter++)
/*  80:    */         {
/*  81:120 */           sizes[counter] = children[counter].getPreferredSize();
/*  82:121 */           totalWidth += sizes[counter].width;
/*  83:    */         }
/*  84:123 */         totalWidth += (numChildren - 1) * this.padding;
/*  85:    */         
/*  86:125 */         boolean cc = getCentersChildren();
/*  87:    */         int xOffset;
/*  88:    */         int xLocation;
/*  89:    */         int xOffset;
/*  90:129 */         if (cc)
/*  91:    */         {
/*  92:130 */           int xLocation = (container.getSize().width - totalWidth) / 2;
/*  93:131 */           xOffset = this.padding;
/*  94:    */         }
/*  95:    */         else
/*  96:    */         {
/*  97:    */           int xLocation;
/*  98:133 */           if (numChildren > 1)
/*  99:    */           {
/* 100:134 */             int xOffset = (container.getSize().width - totalWidth) / (numChildren - 1);
/* 101:    */             
/* 102:    */ 
/* 103:137 */             xLocation = 0;
/* 104:    */           }
/* 105:    */           else
/* 106:    */           {
/* 107:139 */             xLocation = (container.getSize().width - totalWidth) / 2;
/* 108:    */             
/* 109:141 */             xOffset = 0;
/* 110:    */           }
/* 111:    */         }
/* 112:145 */         boolean ltr = container.getComponentOrientation().isLeftToRight();
/* 113:147 */         for (counter = 0; counter < numChildren; counter++)
/* 114:    */         {
/* 115:148 */           int index = ltr ? counter : numChildren - counter - 1;
/* 116:    */           
/* 117:    */ 
/* 118:151 */           children[index].setBounds(xLocation, yLocation, sizes[index].width, sizes[index].height);
/* 119:    */           
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:156 */           xLocation += xOffset + sizes[index].width;
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Dimension minimumLayoutSize(Container c)
/* 130:    */   {
/* 131:163 */     if (c != null)
/* 132:    */     {
/* 133:164 */       Component[] children = c.getComponents();
/* 134:166 */       if ((children != null) && (children.length > 0))
/* 135:    */       {
/* 136:168 */         int numChildren = children.length;
/* 137:169 */         int height = 0;
/* 138:170 */         Insets cInsets = c.getInsets();
/* 139:171 */         int extraHeight = cInsets.top + cInsets.bottom;
/* 140:173 */         if (this.syncAllWidths)
/* 141:    */         {
/* 142:174 */           int maxWidth = getMinimumButtonWidth();
/* 143:176 */           for (int counter = 0; counter < numChildren; counter++)
/* 144:    */           {
/* 145:177 */             Dimension aSize = children[counter].getPreferredSize();
/* 146:178 */             height = Math.max(height, aSize.height);
/* 147:179 */             maxWidth = Math.max(maxWidth, aSize.width);
/* 148:    */           }
/* 149:181 */           return new Dimension(maxWidth * numChildren + (numChildren - 1) * this.padding, extraHeight + height);
/* 150:    */         }
/* 151:185 */         int totalWidth = 0;
/* 152:187 */         for (int counter = 0; counter < numChildren; counter++)
/* 153:    */         {
/* 154:188 */           Dimension aSize = children[counter].getPreferredSize();
/* 155:189 */           height = Math.max(height, aSize.height);
/* 156:190 */           totalWidth += aSize.width;
/* 157:    */         }
/* 158:192 */         totalWidth += (numChildren - 1) * this.padding;
/* 159:193 */         return new Dimension(totalWidth, extraHeight + height);
/* 160:    */       }
/* 161:    */     }
/* 162:196 */     return new Dimension(0, 0);
/* 163:    */   }
/* 164:    */   
/* 165:    */   private int getMinimumButtonWidth()
/* 166:    */   {
/* 167:217 */     return LookUtils.IS_LOW_RESOLUTION ? 75 : 100;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.common.ExtButtonAreaLayout
 * JD-Core Version:    0.7.0.1
 */