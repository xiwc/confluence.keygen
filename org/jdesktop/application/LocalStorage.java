/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.DefaultPersistenceDelegate;
/*     */ import java.beans.Encoder;
/*     */ import java.beans.ExceptionListener;
/*     */ import java.beans.Expression;
/*     */ import java.beans.XMLDecoder;
/*     */ import java.beans.XMLEncoder;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class LocalStorage
/*     */   extends AbstractBean
/*     */ {
/*  46 */   private static Logger logger = Logger.getLogger(LocalStorage.class.getName());
/*     */   private final ApplicationContext context;
/*  48 */   private long storageLimit = -1L;
/*  49 */   private LocalIO localIO = null;
/*  50 */   private final File unspecifiedFile = new File("unspecified");
/*  51 */   private File directory = this.unspecifiedFile;
/*     */   
/*     */   protected LocalStorage(ApplicationContext context) {
/*  54 */     if (context == null) {
/*  55 */       throw new IllegalArgumentException("null context");
/*     */     }
/*  57 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected final ApplicationContext getContext()
/*     */   {
/*  62 */     return this.context;
/*     */   }
/*     */   
/*     */   private void checkFileName(String fileName) {
/*  66 */     if (fileName == null) {
/*  67 */       throw new IllegalArgumentException("null fileName");
/*     */     }
/*     */   }
/*     */   
/*     */   public InputStream openInputFile(String fileName) throws IOException {
/*  72 */     checkFileName(fileName);
/*  73 */     return getLocalIO().openInputFile(fileName);
/*     */   }
/*     */   
/*     */   public OutputStream openOutputFile(String fileName) throws IOException {
/*  77 */     checkFileName(fileName);
/*  78 */     return getLocalIO().openOutputFile(fileName);
/*     */   }
/*     */   
/*     */   public boolean deleteFile(String fileName) throws IOException {
/*  82 */     checkFileName(fileName);
/*  83 */     return getLocalIO().deleteFile(fileName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class AbortExceptionListener
/*     */     implements ExceptionListener
/*     */   {
/*  92 */     public Exception exception = null;
/*     */     
/*  94 */     public void exceptionThrown(Exception e) { if (this.exception == null) this.exception = e;
/*     */     }
/*     */   }
/*     */   
/*  98 */   private static boolean persistenceDelegatesInitialized = false;
/*     */   
/*     */   public void save(Object bean, String fileName) throws IOException {
/* 101 */     AbortExceptionListener el = new AbortExceptionListener(null);
/* 102 */     XMLEncoder e = null;
/*     */     
/*     */ 
/*     */ 
/* 106 */     ByteArrayOutputStream bst = new ByteArrayOutputStream();
/*     */     try {
/* 108 */       e = new XMLEncoder(bst);
/* 109 */       if (!persistenceDelegatesInitialized) {
/* 110 */         e.setPersistenceDelegate(Rectangle.class, new RectanglePD());
/* 111 */         persistenceDelegatesInitialized = true;
/*     */       }
/* 113 */       e.setExceptionListener(el);
/* 114 */       e.writeObject(bean);
/*     */     }
/*     */     finally {
/* 117 */       if (e != null) e.close();
/*     */     }
/* 119 */     if (el.exception != null) {
/* 120 */       throw new LSException("save failed \"" + fileName + "\"", el.exception);
/*     */     }
/* 122 */     OutputStream ost = null;
/*     */     try {
/* 124 */       ost = openOutputFile(fileName);
/* 125 */       ost.write(bst.toByteArray());
/*     */     }
/*     */     finally {
/* 128 */       if (ost != null) ost.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object load(String fileName) throws IOException {
/* 133 */     InputStream ist = null;
/*     */     try {
/* 135 */       ist = openInputFile(fileName);
/*     */     }
/*     */     catch (IOException e) {
/* 138 */       return null;
/*     */     }
/* 140 */     AbortExceptionListener el = new AbortExceptionListener(null);
/* 141 */     XMLDecoder d = null;
/*     */     try {
/* 143 */       d = new XMLDecoder(ist);
/* 144 */       d.setExceptionListener(el);
/* 145 */       Object bean = d.readObject();
/* 146 */       if (el.exception != null) {
/* 147 */         throw new LSException("load failed \"" + fileName + "\"", el.exception);
/*     */       }
/* 149 */       return bean;
/*     */     }
/*     */     finally {
/* 152 */       if (d != null) d.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeStream(Closeable st, String fileName) throws IOException {
/* 157 */     if (st != null) {
/* 158 */       try { st.close();
/*     */       } catch (IOException e) {
/* 160 */         throw new LSException("close failed \"" + fileName + "\"", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public long getStorageLimit() {
/* 166 */     return this.storageLimit;
/*     */   }
/*     */   
/*     */   public void setStorageLimit(long storageLimit) {
/* 170 */     if (storageLimit < -1L) {
/* 171 */       throw new IllegalArgumentException("invalid storageLimit");
/*     */     }
/* 173 */     long oldValue = this.storageLimit;
/* 174 */     this.storageLimit = storageLimit;
/* 175 */     firePropertyChange("storageLimit", Long.valueOf(oldValue), Long.valueOf(this.storageLimit));
/*     */   }
/*     */   
/*     */   private String getId(String key, String def) {
/* 179 */     ResourceMap appResourceMap = getContext().getResourceMap();
/* 180 */     String id = appResourceMap.getString(key, new Object[0]);
/* 181 */     if (id == null) {
/* 182 */       logger.log(Level.WARNING, "unspecified resource " + key + " using " + def);
/* 183 */       id = def;
/*     */     }
/* 185 */     else if (id.trim().length() == 0) {
/* 186 */       logger.log(Level.WARNING, "empty resource " + key + " using " + def);
/* 187 */       id = def;
/*     */     }
/* 189 */     return id;
/*     */   }
/*     */   
/* 192 */   private String getApplicationId() { return getId("Application.id", getContext().getApplicationClass().getSimpleName()); }
/*     */   
/*     */   private String getVendorId() {
/* 195 */     return getId("Application.vendorId", "UnknownApplicationVendor");
/*     */   }
/*     */   
/*     */ 
/*     */   private static enum OSId
/*     */   {
/* 201 */     WINDOWS,  OSX,  UNIX;
/*     */     private OSId() {} }
/* 203 */   private OSId getOSId() { PrivilegedAction<String> doGetOSName = new PrivilegedAction() {
/*     */       public String run() {
/* 205 */         return System.getProperty("os.name");
/*     */       }
/* 207 */     };
/* 208 */     OSId id = OSId.UNIX;
/* 209 */     String osName = (String)AccessController.doPrivileged(doGetOSName);
/* 210 */     if (osName != null) {
/* 211 */       if (osName.toLowerCase().startsWith("mac os x")) {
/* 212 */         id = OSId.OSX;
/*     */       }
/* 214 */       else if (osName.contains("Windows")) {
/* 215 */         id = OSId.WINDOWS;
/*     */       }
/*     */     }
/* 218 */     return id;
/*     */   }
/*     */   
/*     */   public File getDirectory() {
/* 222 */     if (this.directory == this.unspecifiedFile) {
/* 223 */       this.directory = null;
/* 224 */       String userHome = null;
/*     */       try {
/* 226 */         userHome = System.getProperty("user.home");
/*     */       }
/*     */       catch (SecurityException ignore) {}
/*     */       
/* 230 */       if (userHome != null) {
/* 231 */         String applicationId = getApplicationId();
/* 232 */         OSId osId = getOSId();
/* 233 */         if (osId == OSId.WINDOWS) {
/* 234 */           File appDataDir = null;
/*     */           try {
/* 236 */             String appDataEV = System.getenv("APPDATA");
/* 237 */             if ((appDataEV != null) && (appDataEV.length() > 0)) {
/* 238 */               appDataDir = new File(appDataEV);
/*     */             }
/*     */           }
/*     */           catch (SecurityException ignore) {}
/*     */           
/* 243 */           String vendorId = getVendorId();
/* 244 */           if ((appDataDir != null) && (appDataDir.isDirectory()))
/*     */           {
/* 246 */             String path = vendorId + "\\" + applicationId + "\\";
/* 247 */             this.directory = new File(appDataDir, path);
/*     */           }
/*     */           else
/*     */           {
/* 251 */             String path = "Application Data\\" + vendorId + "\\" + applicationId + "\\";
/* 252 */             this.directory = new File(userHome, path);
/*     */           }
/*     */         }
/* 255 */         else if (osId == OSId.OSX)
/*     */         {
/* 257 */           String path = "Library/Application Support/" + applicationId + "/";
/* 258 */           this.directory = new File(userHome, path);
/*     */         }
/*     */         else
/*     */         {
/* 262 */           String path = "." + applicationId + "/";
/* 263 */           this.directory = new File(userHome, path);
/*     */         }
/*     */       }
/*     */     }
/* 267 */     return this.directory;
/*     */   }
/*     */   
/*     */   public void setDirectory(File directory) {
/* 271 */     File oldValue = this.directory;
/* 272 */     this.directory = directory;
/* 273 */     firePropertyChange("directory", oldValue, this.directory);
/*     */   }
/*     */   
/*     */   private static class LSException
/*     */     extends IOException
/*     */   {
/*     */     public LSException(String s, Throwable e)
/*     */     {
/* 281 */       super();
/* 282 */       initCause(e);
/*     */     }
/*     */     
/* 285 */     public LSException(String s) { super(); }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class RectanglePD
/*     */     extends DefaultPersistenceDelegate
/*     */   {
/*     */     public RectanglePD()
/*     */     {
/* 298 */       super();
/*     */     }
/*     */     
/*     */     protected Expression instantiate(Object oldInstance, Encoder out) {
/* 302 */       Rectangle oldR = (Rectangle)oldInstance;
/* 303 */       Object[] constructorArgs = { Integer.valueOf(oldR.x), Integer.valueOf(oldR.y), Integer.valueOf(oldR.width), Integer.valueOf(oldR.height) };
/*     */       
/*     */ 
/* 306 */       return new Expression(oldInstance, oldInstance.getClass(), "new", constructorArgs); } }
/*     */   private abstract class LocalIO { private LocalIO() {}
/*     */     public abstract InputStream openInputFile(String paramString) throws IOException;
/*     */     public abstract OutputStream openOutputFile(String paramString) throws IOException;
/*     */     public abstract boolean deleteFile(String paramString) throws IOException; }
/* 311 */   private synchronized LocalIO getLocalIO() { if (this.localIO == null) {
/* 312 */       this.localIO = getPersistenceServiceIO();
/* 313 */       if (this.localIO == null) {
/* 314 */         this.localIO = new LocalFileIO(null);
/*     */       }
/*     */     }
/* 317 */     return this.localIO;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class LocalFileIO
/*     */     extends LocalStorage.LocalIO
/*     */   {
/* 326 */     private LocalFileIO() { super(null); }
/*     */     
/* 328 */     public InputStream openInputFile(String fileName) throws IOException { File path = new File(LocalStorage.this.getDirectory(), fileName);
/*     */       try {
/* 330 */         return new BufferedInputStream(new FileInputStream(path));
/*     */       }
/*     */       catch (IOException e) {
/* 333 */         throw new LocalStorage.LSException("couldn't open input file \"" + fileName + "\"", e);
/*     */       }
/*     */     }
/*     */     
/* 337 */     public OutputStream openOutputFile(String fileName) throws IOException { File dir = LocalStorage.this.getDirectory();
/* 338 */       if ((!dir.isDirectory()) && 
/* 339 */         (!dir.mkdirs())) {
/* 340 */         throw new LocalStorage.LSException("couldn't create directory " + dir);
/*     */       }
/*     */       
/* 343 */       File path = new File(dir, fileName);
/*     */       try {
/* 345 */         return new BufferedOutputStream(new FileOutputStream(path));
/*     */       }
/*     */       catch (IOException e) {
/* 348 */         throw new LocalStorage.LSException("couldn't open output file \"" + fileName + "\"", e);
/*     */       }
/*     */     }
/*     */     
/* 352 */     public boolean deleteFile(String fileName) throws IOException { File path = new File(LocalStorage.this.getDirectory(), fileName);
/* 353 */       return path.delete();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private LocalIO getPersistenceServiceIO()
/*     */   {
/*     */     try
/*     */     {
/* 364 */       Class smClass = Class.forName("javax.jnlp.ServiceManager");
/* 365 */       Method getServiceNamesMethod = smClass.getMethod("getServiceNames", new Class[0]);
/* 366 */       String[] serviceNames = (String[])getServiceNamesMethod.invoke(null, new Object[0]);
/* 367 */       boolean psFound = false;
/* 368 */       boolean bsFound = false;
/* 369 */       for (String serviceName : serviceNames) {
/* 370 */         if (serviceName.equals("javax.jnlp.BasicService")) {
/* 371 */           bsFound = true;
/*     */         }
/* 373 */         else if (serviceName.equals("javax.jnlp.PersistenceService")) {
/* 374 */           psFound = true;
/*     */         }
/*     */       }
/* 377 */       if ((bsFound) && (psFound)) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 384 */       return null;
/*     */     }
/*     */     catch (Exception ignore) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\LocalStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */