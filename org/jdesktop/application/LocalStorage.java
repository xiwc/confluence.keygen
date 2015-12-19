/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.awt.Rectangle;
/*   4:    */ import java.beans.DefaultPersistenceDelegate;
/*   5:    */ import java.beans.Encoder;
/*   6:    */ import java.beans.ExceptionListener;
/*   7:    */ import java.beans.Expression;
/*   8:    */ import java.beans.XMLDecoder;
/*   9:    */ import java.beans.XMLEncoder;
/*  10:    */ import java.io.BufferedInputStream;
/*  11:    */ import java.io.BufferedOutputStream;
/*  12:    */ import java.io.ByteArrayOutputStream;
/*  13:    */ import java.io.Closeable;
/*  14:    */ import java.io.File;
/*  15:    */ import java.io.FileInputStream;
/*  16:    */ import java.io.FileOutputStream;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.io.InputStream;
/*  19:    */ import java.io.OutputStream;
/*  20:    */ import java.lang.reflect.Method;
/*  21:    */ import java.security.AccessController;
/*  22:    */ import java.security.PrivilegedAction;
/*  23:    */ import java.util.logging.Level;
/*  24:    */ import java.util.logging.Logger;
/*  25:    */ 
/*  26:    */ public class LocalStorage
/*  27:    */   extends AbstractBean
/*  28:    */ {
/*  29: 46 */   private static Logger logger = Logger.getLogger(LocalStorage.class.getName());
/*  30:    */   private final ApplicationContext context;
/*  31: 48 */   private long storageLimit = -1L;
/*  32: 49 */   private LocalIO localIO = null;
/*  33: 50 */   private final File unspecifiedFile = new File("unspecified");
/*  34: 51 */   private File directory = this.unspecifiedFile;
/*  35:    */   
/*  36:    */   protected LocalStorage(ApplicationContext context)
/*  37:    */   {
/*  38: 54 */     if (context == null) {
/*  39: 55 */       throw new IllegalArgumentException("null context");
/*  40:    */     }
/*  41: 57 */     this.context = context;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected final ApplicationContext getContext()
/*  45:    */   {
/*  46: 62 */     return this.context;
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void checkFileName(String fileName)
/*  50:    */   {
/*  51: 66 */     if (fileName == null) {
/*  52: 67 */       throw new IllegalArgumentException("null fileName");
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public InputStream openInputFile(String fileName)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 72 */     checkFileName(fileName);
/*  60: 73 */     return getLocalIO().openInputFile(fileName);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public OutputStream openOutputFile(String fileName)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66: 77 */     checkFileName(fileName);
/*  67: 78 */     return getLocalIO().openOutputFile(fileName);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean deleteFile(String fileName)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73: 82 */     checkFileName(fileName);
/*  74: 83 */     return getLocalIO().deleteFile(fileName);
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static class AbortExceptionListener
/*  78:    */     implements ExceptionListener
/*  79:    */   {
/*  80: 92 */     public Exception exception = null;
/*  81:    */     
/*  82:    */     public void exceptionThrown(Exception e)
/*  83:    */     {
/*  84: 94 */       if (this.exception == null) {
/*  85: 94 */         this.exception = e;
/*  86:    */       }
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90: 98 */   private static boolean persistenceDelegatesInitialized = false;
/*  91:    */   
/*  92:    */   public void save(Object bean, String fileName)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:101 */     AbortExceptionListener el = new AbortExceptionListener(null);
/*  96:102 */     XMLEncoder e = null;
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:106 */     ByteArrayOutputStream bst = new ByteArrayOutputStream();
/* 101:    */     try
/* 102:    */     {
/* 103:108 */       e = new XMLEncoder(bst);
/* 104:109 */       if (!persistenceDelegatesInitialized)
/* 105:    */       {
/* 106:110 */         e.setPersistenceDelegate(Rectangle.class, new RectanglePD());
/* 107:111 */         persistenceDelegatesInitialized = true;
/* 108:    */       }
/* 109:113 */       e.setExceptionListener(el);
/* 110:114 */       e.writeObject(bean);
/* 111:    */     }
/* 112:    */     finally
/* 113:    */     {
/* 114:117 */       if (e != null) {
/* 115:117 */         e.close();
/* 116:    */       }
/* 117:    */     }
/* 118:119 */     if (el.exception != null) {
/* 119:120 */       throw new LSException("save failed \"" + fileName + "\"", el.exception);
/* 120:    */     }
/* 121:122 */     OutputStream ost = null;
/* 122:    */     try
/* 123:    */     {
/* 124:124 */       ost = openOutputFile(fileName);
/* 125:125 */       ost.write(bst.toByteArray());
/* 126:    */     }
/* 127:    */     finally
/* 128:    */     {
/* 129:128 */       if (ost != null) {
/* 130:128 */         ost.close();
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Object load(String fileName)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:133 */     InputStream ist = null;
/* 139:    */     try
/* 140:    */     {
/* 141:135 */       ist = openInputFile(fileName);
/* 142:    */     }
/* 143:    */     catch (IOException e)
/* 144:    */     {
/* 145:138 */       return null;
/* 146:    */     }
/* 147:140 */     AbortExceptionListener el = new AbortExceptionListener(null);
/* 148:141 */     XMLDecoder d = null;
/* 149:    */     try
/* 150:    */     {
/* 151:143 */       d = new XMLDecoder(ist);
/* 152:144 */       d.setExceptionListener(el);
/* 153:145 */       Object bean = d.readObject();
/* 154:146 */       if (el.exception != null) {
/* 155:147 */         throw new LSException("load failed \"" + fileName + "\"", el.exception);
/* 156:    */       }
/* 157:149 */       return bean;
/* 158:    */     }
/* 159:    */     finally
/* 160:    */     {
/* 161:152 */       if (d != null) {
/* 162:152 */         d.close();
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void closeStream(Closeable st, String fileName)
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:157 */     if (st != null) {
/* 171:    */       try
/* 172:    */       {
/* 173:158 */         st.close();
/* 174:    */       }
/* 175:    */       catch (IOException e)
/* 176:    */       {
/* 177:160 */         throw new LSException("close failed \"" + fileName + "\"", e);
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public long getStorageLimit()
/* 183:    */   {
/* 184:166 */     return this.storageLimit;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setStorageLimit(long storageLimit)
/* 188:    */   {
/* 189:170 */     if (storageLimit < -1L) {
/* 190:171 */       throw new IllegalArgumentException("invalid storageLimit");
/* 191:    */     }
/* 192:173 */     long oldValue = this.storageLimit;
/* 193:174 */     this.storageLimit = storageLimit;
/* 194:175 */     firePropertyChange("storageLimit", Long.valueOf(oldValue), Long.valueOf(this.storageLimit));
/* 195:    */   }
/* 196:    */   
/* 197:    */   private String getId(String key, String def)
/* 198:    */   {
/* 199:179 */     ResourceMap appResourceMap = getContext().getResourceMap();
/* 200:180 */     String id = appResourceMap.getString(key, new Object[0]);
/* 201:181 */     if (id == null)
/* 202:    */     {
/* 203:182 */       logger.log(Level.WARNING, "unspecified resource " + key + " using " + def);
/* 204:183 */       id = def;
/* 205:    */     }
/* 206:185 */     else if (id.trim().length() == 0)
/* 207:    */     {
/* 208:186 */       logger.log(Level.WARNING, "empty resource " + key + " using " + def);
/* 209:187 */       id = def;
/* 210:    */     }
/* 211:189 */     return id;
/* 212:    */   }
/* 213:    */   
/* 214:    */   private String getApplicationId()
/* 215:    */   {
/* 216:192 */     return getId("Application.id", getContext().getApplicationClass().getSimpleName());
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String getVendorId()
/* 220:    */   {
/* 221:195 */     return getId("Application.vendorId", "UnknownApplicationVendor");
/* 222:    */   }
/* 223:    */   
/* 224:    */   private static enum OSId
/* 225:    */   {
/* 226:201 */     WINDOWS,  OSX,  UNIX;
/* 227:    */     
/* 228:    */     private OSId() {}
/* 229:    */   }
/* 230:    */   
/* 231:    */   private OSId getOSId()
/* 232:    */   {
/* 233:203 */     PrivilegedAction<String> doGetOSName = new PrivilegedAction()
/* 234:    */     {
/* 235:    */       public String run()
/* 236:    */       {
/* 237:205 */         return System.getProperty("os.name");
/* 238:    */       }
/* 239:207 */     };
/* 240:208 */     OSId id = OSId.UNIX;
/* 241:209 */     String osName = (String)AccessController.doPrivileged(doGetOSName);
/* 242:210 */     if (osName != null) {
/* 243:211 */       if (osName.toLowerCase().startsWith("mac os x")) {
/* 244:212 */         id = OSId.OSX;
/* 245:214 */       } else if (osName.contains("Windows")) {
/* 246:215 */         id = OSId.WINDOWS;
/* 247:    */       }
/* 248:    */     }
/* 249:218 */     return id;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public File getDirectory()
/* 253:    */   {
/* 254:222 */     if (this.directory == this.unspecifiedFile)
/* 255:    */     {
/* 256:223 */       this.directory = null;
/* 257:224 */       String userHome = null;
/* 258:    */       try
/* 259:    */       {
/* 260:226 */         userHome = System.getProperty("user.home");
/* 261:    */       }
/* 262:    */       catch (SecurityException ignore) {}
/* 263:230 */       if (userHome != null)
/* 264:    */       {
/* 265:231 */         String applicationId = getApplicationId();
/* 266:232 */         OSId osId = getOSId();
/* 267:233 */         if (osId == OSId.WINDOWS)
/* 268:    */         {
/* 269:234 */           File appDataDir = null;
/* 270:    */           try
/* 271:    */           {
/* 272:236 */             String appDataEV = System.getenv("APPDATA");
/* 273:237 */             if ((appDataEV != null) && (appDataEV.length() > 0)) {
/* 274:238 */               appDataDir = new File(appDataEV);
/* 275:    */             }
/* 276:    */           }
/* 277:    */           catch (SecurityException ignore) {}
/* 278:243 */           String vendorId = getVendorId();
/* 279:244 */           if ((appDataDir != null) && (appDataDir.isDirectory()))
/* 280:    */           {
/* 281:246 */             String path = vendorId + "\\" + applicationId + "\\";
/* 282:247 */             this.directory = new File(appDataDir, path);
/* 283:    */           }
/* 284:    */           else
/* 285:    */           {
/* 286:251 */             String path = "Application Data\\" + vendorId + "\\" + applicationId + "\\";
/* 287:252 */             this.directory = new File(userHome, path);
/* 288:    */           }
/* 289:    */         }
/* 290:255 */         else if (osId == OSId.OSX)
/* 291:    */         {
/* 292:257 */           String path = "Library/Application Support/" + applicationId + "/";
/* 293:258 */           this.directory = new File(userHome, path);
/* 294:    */         }
/* 295:    */         else
/* 296:    */         {
/* 297:262 */           String path = "." + applicationId + "/";
/* 298:263 */           this.directory = new File(userHome, path);
/* 299:    */         }
/* 300:    */       }
/* 301:    */     }
/* 302:267 */     return this.directory;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setDirectory(File directory)
/* 306:    */   {
/* 307:271 */     File oldValue = this.directory;
/* 308:272 */     this.directory = directory;
/* 309:273 */     firePropertyChange("directory", oldValue, this.directory);
/* 310:    */   }
/* 311:    */   
/* 312:    */   private static class LSException
/* 313:    */     extends IOException
/* 314:    */   {
/* 315:    */     public LSException(String s, Throwable e)
/* 316:    */     {
/* 317:281 */       super();
/* 318:282 */       initCause(e);
/* 319:    */     }
/* 320:    */     
/* 321:    */     public LSException(String s)
/* 322:    */     {
/* 323:285 */       super();
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private static class RectanglePD
/* 328:    */     extends DefaultPersistenceDelegate
/* 329:    */   {
/* 330:    */     public RectanglePD()
/* 331:    */     {
/* 332:298 */       super();
/* 333:    */     }
/* 334:    */     
/* 335:    */     protected Expression instantiate(Object oldInstance, Encoder out)
/* 336:    */     {
/* 337:302 */       Rectangle oldR = (Rectangle)oldInstance;
/* 338:303 */       Object[] constructorArgs = { Integer.valueOf(oldR.x), Integer.valueOf(oldR.y), Integer.valueOf(oldR.width), Integer.valueOf(oldR.height) };
/* 339:    */       
/* 340:    */ 
/* 341:306 */       return new Expression(oldInstance, oldInstance.getClass(), "new", constructorArgs);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   private synchronized LocalIO getLocalIO()
/* 346:    */   {
/* 347:311 */     if (this.localIO == null)
/* 348:    */     {
/* 349:312 */       this.localIO = getPersistenceServiceIO();
/* 350:313 */       if (this.localIO == null) {
/* 351:314 */         this.localIO = new LocalFileIO(null);
/* 352:    */       }
/* 353:    */     }
/* 354:317 */     return this.localIO;
/* 355:    */   }
/* 356:    */   
/* 357:    */   private abstract class LocalIO
/* 358:    */   {
/* 359:    */     private LocalIO() {}
/* 360:    */     
/* 361:    */     public abstract InputStream openInputFile(String paramString)
/* 362:    */       throws IOException;
/* 363:    */     
/* 364:    */     public abstract OutputStream openOutputFile(String paramString)
/* 365:    */       throws IOException;
/* 366:    */     
/* 367:    */     public abstract boolean deleteFile(String paramString)
/* 368:    */       throws IOException;
/* 369:    */   }
/* 370:    */   
/* 371:    */   private class LocalFileIO
/* 372:    */     extends LocalStorage.LocalIO
/* 373:    */   {
/* 374:    */     private LocalFileIO()
/* 375:    */     {
/* 376:326 */       super(null);
/* 377:    */     }
/* 378:    */     
/* 379:    */     public InputStream openInputFile(String fileName)
/* 380:    */       throws IOException
/* 381:    */     {
/* 382:328 */       File path = new File(LocalStorage.this.getDirectory(), fileName);
/* 383:    */       try
/* 384:    */       {
/* 385:330 */         return new BufferedInputStream(new FileInputStream(path));
/* 386:    */       }
/* 387:    */       catch (IOException e)
/* 388:    */       {
/* 389:333 */         throw new LocalStorage.LSException("couldn't open input file \"" + fileName + "\"", e);
/* 390:    */       }
/* 391:    */     }
/* 392:    */     
/* 393:    */     public OutputStream openOutputFile(String fileName)
/* 394:    */       throws IOException
/* 395:    */     {
/* 396:337 */       File dir = LocalStorage.this.getDirectory();
/* 397:338 */       if ((!dir.isDirectory()) && 
/* 398:339 */         (!dir.mkdirs())) {
/* 399:340 */         throw new LocalStorage.LSException("couldn't create directory " + dir);
/* 400:    */       }
/* 401:343 */       File path = new File(dir, fileName);
/* 402:    */       try
/* 403:    */       {
/* 404:345 */         return new BufferedOutputStream(new FileOutputStream(path));
/* 405:    */       }
/* 406:    */       catch (IOException e)
/* 407:    */       {
/* 408:348 */         throw new LocalStorage.LSException("couldn't open output file \"" + fileName + "\"", e);
/* 409:    */       }
/* 410:    */     }
/* 411:    */     
/* 412:    */     public boolean deleteFile(String fileName)
/* 413:    */       throws IOException
/* 414:    */     {
/* 415:352 */       File path = new File(LocalStorage.this.getDirectory(), fileName);
/* 416:353 */       return path.delete();
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   private LocalIO getPersistenceServiceIO()
/* 421:    */   {
/* 422:    */     try
/* 423:    */     {
/* 424:364 */       Class smClass = Class.forName("javax.jnlp.ServiceManager");
/* 425:365 */       Method getServiceNamesMethod = smClass.getMethod("getServiceNames", new Class[0]);
/* 426:366 */       String[] serviceNames = (String[])getServiceNamesMethod.invoke(null, new Object[0]);
/* 427:367 */       boolean psFound = false;
/* 428:368 */       boolean bsFound = false;
/* 429:369 */       for (String serviceName : serviceNames) {
/* 430:370 */         if (serviceName.equals("javax.jnlp.BasicService")) {
/* 431:371 */           bsFound = true;
/* 432:373 */         } else if (serviceName.equals("javax.jnlp.PersistenceService")) {
/* 433:374 */           psFound = true;
/* 434:    */         }
/* 435:    */       }
/* 436:377 */       if ((bsFound) && (psFound)) {}
/* 437:384 */       return null;
/* 438:    */     }
/* 439:    */     catch (Exception ignore) {}
/* 440:    */   }
/* 441:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.LocalStorage
 * JD-Core Version:    0.7.0.1
 */