/*    */ package org.jdesktop.application;
/*    */ 
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JRootPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameView
/*    */   extends View
/*    */ {
/* 15 */   private JFrame frame = null;
/*    */   
/*    */   public FrameView(Application application) {
/* 18 */     super(application);
/*    */   }
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
/*    */   public JFrame getFrame()
/*    */   {
/* 37 */     if (this.frame == null) {
/* 38 */       String title = getContext().getResourceMap().getString("Application.title", new Object[0]);
/* 39 */       this.frame = new JFrame(title);
/* 40 */       this.frame.setName("mainFrame");
/*    */     }
/* 42 */     return this.frame;
/*    */   }
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
/*    */   public void setFrame(JFrame frame)
/*    */   {
/* 68 */     if (frame == null) {
/* 69 */       throw new IllegalArgumentException("null JFrame");
/*    */     }
/* 71 */     if (this.frame != null) {
/* 72 */       throw new IllegalStateException("frame already set");
/*    */     }
/* 74 */     this.frame = frame;
/* 75 */     firePropertyChange("frame", null, this.frame);
/*    */   }
/*    */   
/*    */   public JRootPane getRootPane()
/*    */   {
/* 80 */     return getFrame().getRootPane();
/*    */   }
/*    */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\FrameView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */