/*  1:   */ package nl.invisible.keygen.gui;
/*  2:   */ 
/*  3:   */ import java.awt.Window;
/*  4:   */ import org.jdesktop.application.Application;
/*  5:   */ import org.jdesktop.application.SingleFrameApplication;
/*  6:   */ 
/*  7:   */ public class MainApp
/*  8:   */   extends SingleFrameApplication
/*  9:   */ {
/* 10:   */   protected void startup()
/* 11:   */   {
/* 12:19 */     show(new MainWindow(this));
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected void configureWindow(Window root) {}
/* 16:   */   
/* 17:   */   public static MainApp getApplication()
/* 18:   */   {
/* 19:35 */     return (MainApp)Application.getInstance(MainApp.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void main(String[] args)
/* 23:   */   {
/* 24:42 */     launch(MainApp.class, args);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     nl.invisible.keygen.gui.MainApp
 * JD-Core Version:    0.7.0.1
 */