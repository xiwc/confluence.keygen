/*    */ package com.jgoodies.looks.plastic.theme;
/*    */ 
/*    */ import com.jgoodies.looks.plastic.PlasticLookAndFeel;
/*    */ import javax.swing.UIDefaults;
/*    */ import javax.swing.plaf.ColorUIResource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DesertBlue
/*    */   extends DesertBluer
/*    */ {
/*    */   public String getName()
/*    */   {
/* 49 */     return "Desert Blue";
/*    */   }
/*    */   
/* 52 */   private static final ColorUIResource SECONDARY2 = new ColorUIResource(148, 144, 140);
/* 53 */   private static final ColorUIResource SECONDARY3 = new ColorUIResource(211, 210, 204);
/*    */   
/*    */ 
/*    */ 
/* 57 */   protected ColorUIResource getPrimary1() { return Colors.GRAY_DARK; }
/* 58 */   protected ColorUIResource getPrimary2() { return Colors.BLUE_LOW_MEDIUM; }
/* 59 */   protected ColorUIResource getPrimary3() { return Colors.BLUE_LOW_LIGHTEST; }
/*    */   
/* 61 */   protected ColorUIResource getSecondary1() { return Colors.GRAY_MEDIUM; }
/* 62 */   protected ColorUIResource getSecondary2() { return SECONDARY2; }
/* 63 */   protected ColorUIResource getSecondary3() { return SECONDARY3; }
/*    */   
/* 65 */   public ColorUIResource getTitleTextColor() { return Colors.BLUE_MEDIUM_DARKEST; }
/*    */   
/*    */   public ColorUIResource getFocusColor() {
/* 68 */     return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : Colors.BLUE_MEDIUM_DARK;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 73 */   public ColorUIResource getPrimaryControlShadow() { return getPrimary3(); }
/* 74 */   public ColorUIResource getMenuItemSelectedBackground() { return getPrimary1(); }
/*    */   
/*    */   public void addCustomEntriesToTable(UIDefaults table) {
/* 77 */     super.addCustomEntriesToTable(table);
/* 78 */     Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.FALSE, "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", null };
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 83 */     table.putDefaults(uiDefaults);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\com\jgoodies\looks\plastic\theme\DesertBlue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */