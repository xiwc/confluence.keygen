/*   1:    */ package com.jgoodies.looks.plastic;
/*   2:    */ 
/*   3:    */ import com.jgoodies.looks.BorderStyle;
/*   4:    */ import com.jgoodies.looks.HeaderStyle;
/*   5:    */ import java.awt.Component;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Rectangle;
/*   9:    */ import java.beans.PropertyChangeEvent;
/*  10:    */ import java.beans.PropertyChangeListener;
/*  11:    */ import javax.swing.AbstractButton;
/*  12:    */ import javax.swing.JComponent;
/*  13:    */ import javax.swing.JToolBar;
/*  14:    */ import javax.swing.LookAndFeel;
/*  15:    */ import javax.swing.border.Border;
/*  16:    */ import javax.swing.plaf.ComponentUI;
/*  17:    */ import javax.swing.plaf.metal.MetalToolBarUI;
/*  18:    */ 
/*  19:    */ public class PlasticToolBarUI
/*  20:    */   extends MetalToolBarUI
/*  21:    */ {
/*  22:    */   private static final String PROPERTY_PREFIX = "ToolBar.";
/*  23:    */   private PropertyChangeListener listener;
/*  24:    */   
/*  25:    */   public static ComponentUI createUI(JComponent b)
/*  26:    */   {
/*  27: 67 */     return new PlasticToolBarUI();
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected Border createRolloverBorder()
/*  31:    */   {
/*  32: 73 */     return PlasticBorders.getRolloverButtonBorder();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void setBorderToRollover(Component c)
/*  36:    */   {
/*  37: 77 */     if ((c instanceof AbstractButton))
/*  38:    */     {
/*  39: 78 */       super.setBorderToRollover(c);
/*  40:    */     }
/*  41: 79 */     else if ((c instanceof Container))
/*  42:    */     {
/*  43: 80 */       Container cont = (Container)c;
/*  44: 81 */       for (int i = 0; i < cont.getComponentCount(); i++) {
/*  45: 82 */         super.setBorderToRollover(cont.getComponent(i));
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void installDefaults()
/*  51:    */   {
/*  52: 92 */     super.installDefaults();
/*  53: 93 */     installSpecialBorder();
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected void installListeners()
/*  57:    */   {
/*  58: 97 */     super.installListeners();
/*  59: 98 */     this.listener = createBorderStyleListener();
/*  60: 99 */     this.toolBar.addPropertyChangeListener(this.listener);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void uninstallListeners()
/*  64:    */   {
/*  65:103 */     this.toolBar.removePropertyChangeListener(this.listener);
/*  66:104 */     super.uninstallListeners();
/*  67:    */   }
/*  68:    */   
/*  69:    */   private PropertyChangeListener createBorderStyleListener()
/*  70:    */   {
/*  71:108 */     new PropertyChangeListener()
/*  72:    */     {
/*  73:    */       public void propertyChange(PropertyChangeEvent e)
/*  74:    */       {
/*  75:111 */         String prop = e.getPropertyName();
/*  76:112 */         if ((prop.equals("jgoodies.headerStyle")) || (prop.equals("Plastic.borderStyle"))) {
/*  77:114 */           PlasticToolBarUI.this.installSpecialBorder();
/*  78:    */         }
/*  79:    */       }
/*  80:    */     };
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void installSpecialBorder()
/*  84:    */   {
/*  85:132 */     BorderStyle borderStyle = BorderStyle.from(this.toolBar, "Plastic.borderStyle");
/*  86:    */     String suffix;
/*  87:    */     String suffix;
/*  88:134 */     if (borderStyle == BorderStyle.EMPTY)
/*  89:    */     {
/*  90:135 */       suffix = "emptyBorder";
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:    */       String suffix;
/*  95:136 */       if (borderStyle == BorderStyle.ETCHED)
/*  96:    */       {
/*  97:137 */         suffix = "etchedBorder";
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101:    */         String suffix;
/* 102:138 */         if (borderStyle == BorderStyle.SEPARATOR)
/* 103:    */         {
/* 104:139 */           suffix = "separatorBorder";
/* 105:    */         }
/* 106:    */         else
/* 107:    */         {
/* 108:141 */           HeaderStyle headerStyle = HeaderStyle.from(this.toolBar);
/* 109:    */           String suffix;
/* 110:142 */           if (headerStyle == HeaderStyle.BOTH)
/* 111:    */           {
/* 112:143 */             suffix = "headerBorder";
/* 113:    */           }
/* 114:    */           else
/* 115:    */           {
/* 116:    */             String suffix;
/* 117:144 */             if ((headerStyle == HeaderStyle.SINGLE) && (is3D())) {
/* 118:145 */               suffix = "etchedBorder";
/* 119:    */             } else {
/* 120:147 */               suffix = "border";
/* 121:    */             }
/* 122:    */           }
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:149 */     LookAndFeel.installBorder(this.toolBar, "ToolBar." + suffix);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void update(Graphics g, JComponent c)
/* 130:    */   {
/* 131:155 */     if (c.isOpaque())
/* 132:    */     {
/* 133:156 */       g.setColor(c.getBackground());
/* 134:157 */       g.fillRect(0, 0, c.getWidth(), c.getHeight());
/* 135:158 */       if (is3D())
/* 136:    */       {
/* 137:159 */         Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
/* 138:    */         
/* 139:161 */         boolean isHorizontal = ((JToolBar)c).getOrientation() == 0;
/* 140:    */         
/* 141:163 */         PlasticUtils.addLight3DEffekt(g, bounds, isHorizontal);
/* 142:    */       }
/* 143:    */     }
/* 144:166 */     paint(g, c);
/* 145:    */   }
/* 146:    */   
/* 147:    */   private boolean is3D()
/* 148:    */   {
/* 149:173 */     if (PlasticUtils.force3D(this.toolBar)) {
/* 150:174 */       return true;
/* 151:    */     }
/* 152:175 */     if (PlasticUtils.forceFlat(this.toolBar)) {
/* 153:176 */       return false;
/* 154:    */     }
/* 155:177 */     return (PlasticUtils.is3D("ToolBar.")) && (HeaderStyle.from(this.toolBar) != null) && (BorderStyle.from(this.toolBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     com.jgoodies.looks.plastic.PlasticToolBarUI
 * JD-Core Version:    0.7.0.1
 */