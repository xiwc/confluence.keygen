/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.BorderStyle;
/*   4:    */ import com.jgoodies.looks.HeaderStyle;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Rectangle;
/*   7:    */ import java.beans.PropertyChangeEvent;
/*   8:    */ import java.beans.PropertyChangeListener;
/*   9:    */ import javax.swing.JComponent;
/*  10:    */ import javax.swing.JMenuBar;
/*  11:    */ import javax.swing.LookAndFeel;
/*  12:    */ import javax.swing.plaf.ComponentUI;
/*  13:    */ import javax.swing.plaf.basic.BasicMenuBarUI;
/*  14:    */ 
/*  15:    */ public final class PlasticMenuBarUI
/*  16:    */   extends BasicMenuBarUI
/*  17:    */ {
/*  18:    */   private PropertyChangeListener listener;
/*  19:    */   
/*  20:    */   public static ComponentUI createUI(JComponent b)
/*  21:    */   {
/*  22: 63 */     return new PlasticMenuBarUI();
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected void installDefaults()
/*  26:    */   {
/*  27: 70 */     super.installDefaults();
/*  28: 71 */     installSpecialBorder();
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void installListeners()
/*  32:    */   {
/*  33: 76 */     super.installListeners();
/*  34: 77 */     this.listener = createBorderStyleListener();
/*  35: 78 */     this.menuBar.addPropertyChangeListener(this.listener);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void uninstallListeners()
/*  39:    */   {
/*  40: 83 */     this.menuBar.removePropertyChangeListener(this.listener);
/*  41: 84 */     super.uninstallListeners();
/*  42:    */   }
/*  43:    */   
/*  44:    */   private PropertyChangeListener createBorderStyleListener()
/*  45:    */   {
/*  46: 89 */     new PropertyChangeListener()
/*  47:    */     {
/*  48:    */       public void propertyChange(PropertyChangeEvent e)
/*  49:    */       {
/*  50: 92 */         String prop = e.getPropertyName();
/*  51: 93 */         if ((prop.equals("jgoodies.headerStyle")) || (prop.equals("Plastic.borderStyle"))) {
/*  52: 95 */           PlasticMenuBarUI.this.installSpecialBorder();
/*  53:    */         }
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void installSpecialBorder()
/*  59:    */   {
/*  60:112 */     BorderStyle borderStyle = BorderStyle.from(this.menuBar, "Plastic.borderStyle");
/*  61:    */     String suffix;
/*  62:114 */     if (borderStyle == BorderStyle.EMPTY)
/*  63:    */     {
/*  64:115 */       suffix = "emptyBorder";
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68:    */       String suffix;
/*  69:116 */       if (borderStyle == BorderStyle.ETCHED)
/*  70:    */       {
/*  71:117 */         suffix = "etchedBorder";
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75:    */         String suffix;
/*  76:118 */         if (borderStyle == BorderStyle.SEPARATOR)
/*  77:    */         {
/*  78:119 */           suffix = "separatorBorder";
/*  79:    */         }
/*  80:    */         else
/*  81:    */         {
/*  82:121 */           HeaderStyle headerStyle = HeaderStyle.from(this.menuBar);
/*  83:    */           String suffix;
/*  84:122 */           if (headerStyle == HeaderStyle.BOTH)
/*  85:    */           {
/*  86:123 */             suffix = "headerBorder";
/*  87:    */           }
/*  88:    */           else
/*  89:    */           {
/*  90:    */             String suffix;
/*  91:124 */             if ((headerStyle == HeaderStyle.SINGLE) && (is3D())) {
/*  92:125 */               suffix = "etchedBorder";
/*  93:    */             } else {
/*  94:    */               return;
/*  95:    */             }
/*  96:    */           }
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:    */     String suffix;
/* 101:130 */     LookAndFeel.installBorder(this.menuBar, "MenuBar." + suffix);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void update(Graphics g, JComponent c)
/* 105:    */   {
/* 106:137 */     if (c.isOpaque())
/* 107:    */     {
/* 108:138 */       g.setColor(c.getBackground());
/* 109:139 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 110:140 */       if (is3D())
/* 111:    */       {
/* 112:141 */         Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/* 113:    */         
/* 114:143 */         PlasticUtils.addLight3DEffekt(g, bounds, true);
/* 115:    */       }
/* 116:    */     }
/* 117:147 */     paint(g, c);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private boolean is3D()
/* 121:    */   {
/* 122:155 */     if (PlasticUtils.force3D(this.menuBar)) {
/* 123:156 */       return true;
/* 124:    */     }
/* 125:157 */     if (PlasticUtils.forceFlat(this.menuBar)) {
/* 126:158 */       return false;
/* 127:    */     }
/* 128:159 */     return (PlasticUtils.is3D("MenuBar.")) && (HeaderStyle.from(this.menuBar) != null) && (BorderStyle.from(this.menuBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticMenuBarUI
 * JD-Core Version:    0.7.0.1
 */