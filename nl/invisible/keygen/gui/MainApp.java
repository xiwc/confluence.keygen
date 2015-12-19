/*    */ package nl.invisible.keygen.gui;
/*    */ 
/*    */ import java.awt.Window;
/*    */ import org.jdesktop.application.Application;
/*    */ import org.jdesktop.application.SingleFrameApplication;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainApp
/*    */   extends SingleFrameApplication
/*    */ {
/*    */   protected void startup()
/*    */   {
/* 19 */     show(new MainWindow(this));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void configureWindow(Window root) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MainApp getApplication()
/*    */   {
/* 35 */     return (MainApp)Application.getInstance(MainApp.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 42 */     launch(MainApp.class, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\nl\invisible\keygen\gui\MainApp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */