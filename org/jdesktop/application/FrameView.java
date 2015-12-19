/*  1:   */ package org.jdesktop.application;
/*  2:   */ 
/*  3:   */ import javax.swing.JFrame;
/*  4:   */ import javax.swing.JRootPane;
/*  5:   */ 
/*  6:   */ public class FrameView
/*  7:   */   extends View
/*  8:   */ {
/*  9:15 */   private JFrame frame = null;
/* 10:   */   
/* 11:   */   public FrameView(Application application)
/* 12:   */   {
/* 13:18 */     super(application);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public JFrame getFrame()
/* 17:   */   {
/* 18:37 */     if (this.frame == null)
/* 19:   */     {
/* 20:38 */       String title = getContext().getResourceMap().getString("Application.title", new Object[0]);
/* 21:39 */       this.frame = new JFrame(title);
/* 22:40 */       this.frame.setName("mainFrame");
/* 23:   */     }
/* 24:42 */     return this.frame;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setFrame(JFrame frame)
/* 28:   */   {
/* 29:68 */     if (frame == null) {
/* 30:69 */       throw new IllegalArgumentException("null JFrame");
/* 31:   */     }
/* 32:71 */     if (this.frame != null) {
/* 33:72 */       throw new IllegalStateException("frame already set");
/* 34:   */     }
/* 35:74 */     this.frame = frame;
/* 36:75 */     firePropertyChange("frame", null, this.frame);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public JRootPane getRootPane()
/* 40:   */   {
/* 41:80 */     return getFrame().getRootPane();
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.FrameView
 * JD-Core Version:    0.7.0.1
 */