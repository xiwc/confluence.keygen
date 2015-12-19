/*     */ package com.jgoodies.looks.common;
/*     */ 
/*     */ import com.jgoodies.looks.LookUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;
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
/*     */ public final class ExtButtonAreaLayout
/*     */   extends BasicOptionPaneUI.ButtonAreaLayout
/*     */ {
/*     */   public ExtButtonAreaLayout(boolean syncAllWidths, int padding)
/*     */   {
/*  60 */     super(syncAllWidths, padding);
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container container) {
/*  64 */     Component[] children = container.getComponents();
/*     */     
/*  66 */     if ((children != null) && (children.length > 0)) {
/*  67 */       int numChildren = children.length;
/*  68 */       Dimension[] sizes = new Dimension[numChildren];
/*     */       
/*  70 */       int yLocation = container.getInsets().top;
/*     */       
/*  72 */       if (this.syncAllWidths) {
/*  73 */         int maxWidth = getMinimumButtonWidth();
/*     */         
/*  75 */         for (int counter = 0; counter < numChildren; counter++) {
/*  76 */           sizes[counter] = children[counter].getPreferredSize();
/*  77 */           maxWidth = Math.max(maxWidth, sizes[counter].width);
/*     */         }
/*     */         
/*     */         int xOffset;
/*     */         int xLocation;
/*     */         int xOffset;
/*  83 */         if (getCentersChildren()) {
/*  84 */           int xLocation = (container.getSize().width - (maxWidth * numChildren + (numChildren - 1) * this.padding)) / 2;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*  89 */           xOffset = this.padding + maxWidth;
/*     */         } else { int xOffset;
/*  91 */           if (numChildren > 1) {
/*  92 */             int xLocation = 0;
/*  93 */             xOffset = (container.getSize().width - maxWidth * numChildren) / (numChildren - 1) + maxWidth;
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/*  99 */             xLocation = (container.getSize().width - maxWidth) / 2;
/* 100 */             xOffset = 0;
/*     */           }
/*     */         }
/* 103 */         boolean ltr = container.getComponentOrientation().isLeftToRight();
/*     */         
/* 105 */         for (counter = 0; counter < numChildren; counter++) {
/* 106 */           int index = ltr ? counter : numChildren - counter - 1;
/*     */           
/*     */ 
/* 109 */           children[index].setBounds(xLocation, yLocation, maxWidth, sizes[index].height);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 114 */           xLocation += xOffset;
/*     */         }
/*     */       } else {
/* 117 */         int totalWidth = 0;
/*     */         
/* 119 */         for (int counter = 0; counter < numChildren; counter++) {
/* 120 */           sizes[counter] = children[counter].getPreferredSize();
/* 121 */           totalWidth += sizes[counter].width;
/*     */         }
/* 123 */         totalWidth += (numChildren - 1) * this.padding;
/*     */         
/* 125 */         boolean cc = getCentersChildren();
/*     */         int xOffset;
/*     */         int xLocation;
/*     */         int xOffset;
/* 129 */         if (cc) {
/* 130 */           int xLocation = (container.getSize().width - totalWidth) / 2;
/* 131 */           xOffset = this.padding;
/*     */         } else { int xLocation;
/* 133 */           if (numChildren > 1) {
/* 134 */             int xOffset = (container.getSize().width - totalWidth) / (numChildren - 1);
/*     */             
/*     */ 
/* 137 */             xLocation = 0;
/*     */           } else {
/* 139 */             xLocation = (container.getSize().width - totalWidth) / 2;
/*     */             
/* 141 */             xOffset = 0;
/*     */           }
/*     */         }
/*     */         
/* 145 */         boolean ltr = container.getComponentOrientation().isLeftToRight();
/*     */         
/* 147 */         for (counter = 0; counter < numChildren; counter++) {
/* 148 */           int index = ltr ? counter : numChildren - counter - 1;
/*     */           
/*     */ 
/* 151 */           children[index].setBounds(xLocation, yLocation, sizes[index].width, sizes[index].height);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 156 */           xLocation += xOffset + sizes[index].width;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container c) {
/* 163 */     if (c != null) {
/* 164 */       Component[] children = c.getComponents();
/*     */       
/* 166 */       if ((children != null) && (children.length > 0))
/*     */       {
/* 168 */         int numChildren = children.length;
/* 169 */         int height = 0;
/* 170 */         Insets cInsets = c.getInsets();
/* 171 */         int extraHeight = cInsets.top + cInsets.bottom;
/*     */         
/* 173 */         if (this.syncAllWidths) {
/* 174 */           int maxWidth = getMinimumButtonWidth();
/*     */           
/* 176 */           for (int counter = 0; counter < numChildren; counter++) {
/* 177 */             Dimension aSize = children[counter].getPreferredSize();
/* 178 */             height = Math.max(height, aSize.height);
/* 179 */             maxWidth = Math.max(maxWidth, aSize.width);
/*     */           }
/* 181 */           return new Dimension(maxWidth * numChildren + (numChildren - 1) * this.padding, extraHeight + height);
/*     */         }
/*     */         
/*     */ 
/* 185 */         int totalWidth = 0;
/*     */         
/* 187 */         for (int counter = 0; counter < numChildren; counter++) {
/* 188 */           Dimension aSize = children[counter].getPreferredSize();
/* 189 */           height = Math.max(height, aSize.height);
/* 190 */           totalWidth += aSize.width;
/*     */         }
/* 192 */         totalWidth += (numChildren - 1) * this.padding;
/* 193 */         return new Dimension(totalWidth, extraHeight + height);
/*     */       }
/*     */     }
/* 196 */     return new Dimension(0, 0);
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
/*     */   private int getMinimumButtonWidth()
/*     */   {
/* 217 */     return LookUtils.IS_LOW_RESOLUTION ? 75 : 100;
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\common\ExtButtonAreaLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */