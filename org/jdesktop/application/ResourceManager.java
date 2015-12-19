/*     */ package org.jdesktop.application;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ResourceManager
/*     */   extends AbstractBean
/*     */ {
/*  56 */   private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());
/*     */   private final Map<String, ResourceMap> resourceMaps;
/*     */   private final ApplicationContext context;
/*  59 */   private List<String> applicationBundleNames = null;
/*  60 */   private ResourceMap appResourceMap = null;
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
/*     */   protected ResourceManager(ApplicationContext context)
/*     */   {
/*  80 */     if (context == null) {
/*  81 */       throw new IllegalArgumentException("null context");
/*     */     }
/*  83 */     this.context = context;
/*  84 */     this.resourceMaps = new ConcurrentHashMap();
/*     */   }
/*     */   
/*     */   protected final ApplicationContext getContext()
/*     */   {
/*  89 */     return this.context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<String> allBundleNames(Class startClass, Class stopClass)
/*     */   {
/* 100 */     List<String> bundleNames = new ArrayList();
/* 101 */     Class limitClass = stopClass.getSuperclass();
/* 102 */     for (Class c = startClass; c != limitClass; c = c.getSuperclass()) {
/* 103 */       bundleNames.addAll(getClassBundleNames(c));
/*     */     }
/* 105 */     return Collections.unmodifiableList(bundleNames);
/*     */   }
/*     */   
/*     */   private String bundlePackageName(String bundleName) {
/* 109 */     int i = bundleName.lastIndexOf(".");
/* 110 */     return i == -1 ? "" : bundleName.substring(0, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ResourceMap createResourceMapChain(ClassLoader cl, ResourceMap root, ListIterator<String> names)
/*     */   {
/* 120 */     if (!names.hasNext()) {
/* 121 */       return root;
/*     */     }
/*     */     
/* 124 */     String bundleName0 = (String)names.next();
/* 125 */     String rmBundlePackage = bundlePackageName(bundleName0);
/* 126 */     List<String> rmNames = new ArrayList();
/* 127 */     rmNames.add(bundleName0);
/* 128 */     while (names.hasNext()) {
/* 129 */       String bundleName = (String)names.next();
/* 130 */       if (rmBundlePackage.equals(bundlePackageName(bundleName))) {
/* 131 */         rmNames.add(bundleName);
/*     */       }
/*     */       else {
/* 134 */         names.previous();
/* 135 */         break;
/*     */       }
/*     */     }
/* 138 */     ResourceMap parent = createResourceMapChain(cl, root, names);
/* 139 */     return createResourceMap(cl, parent, rmNames);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ResourceMap getApplicationResourceMap()
/*     */   {
/* 150 */     if (this.appResourceMap == null) {
/* 151 */       List<String> appBundleNames = getApplicationBundleNames();
/* 152 */       Class appClass = getContext().getApplicationClass();
/* 153 */       if (appClass == null) {
/* 154 */         logger.warning("getApplicationResourceMap(): no Application class");
/* 155 */         appClass = Application.class;
/*     */       }
/* 157 */       ClassLoader classLoader = appClass.getClassLoader();
/* 158 */       this.appResourceMap = createResourceMapChain(classLoader, null, appBundleNames.listIterator());
/*     */     }
/* 160 */     return this.appResourceMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private ResourceMap getClassResourceMap(Class startClass, Class stopClass)
/*     */   {
/* 167 */     String classResourceMapKey = startClass.getName() + stopClass.getName();
/* 168 */     ResourceMap classResourceMap = (ResourceMap)this.resourceMaps.get(classResourceMapKey);
/* 169 */     if (classResourceMap == null) {
/* 170 */       List<String> classBundleNames = allBundleNames(startClass, stopClass);
/* 171 */       ClassLoader classLoader = startClass.getClassLoader();
/* 172 */       ResourceMap appRM = getResourceMap();
/* 173 */       classResourceMap = createResourceMapChain(classLoader, appRM, classBundleNames.listIterator());
/* 174 */       this.resourceMaps.put(classResourceMapKey, classResourceMap);
/*     */     }
/* 176 */     return classResourceMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResourceMap getResourceMap(Class startClass, Class stopClass)
/*     */   {
/* 245 */     if (startClass == null) {
/* 246 */       throw new IllegalArgumentException("null startClass");
/*     */     }
/* 248 */     if (stopClass == null) {
/* 249 */       throw new IllegalArgumentException("null stopClass");
/*     */     }
/* 251 */     if (!stopClass.isAssignableFrom(startClass)) {
/* 252 */       throw new IllegalArgumentException("startClass is not a subclass, or the same as, stopClass");
/*     */     }
/* 254 */     return getClassResourceMap(startClass, stopClass);
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
/*     */   public final ResourceMap getResourceMap(Class cls)
/*     */   {
/* 269 */     if (cls == null) {
/* 270 */       throw new IllegalArgumentException("null class");
/*     */     }
/* 272 */     return getResourceMap(cls, cls);
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
/*     */   public ResourceMap getResourceMap()
/*     */   {
/* 289 */     return getApplicationResourceMap();
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
/*     */   public List<String> getApplicationBundleNames()
/*     */   {
/* 326 */     if (this.applicationBundleNames == null) {
/* 327 */       Class appClass = getContext().getApplicationClass();
/* 328 */       if (appClass == null) {
/* 329 */         return allBundleNames(Application.class, Application.class);
/*     */       }
/*     */       
/* 332 */       this.applicationBundleNames = allBundleNames(appClass, Application.class);
/*     */     }
/*     */     
/* 335 */     return this.applicationBundleNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setApplicationBundleNames(List<String> bundleNames)
/*     */   {
/* 346 */     if (bundleNames != null) {
/* 347 */       for (String bundleName : bundleNames) {
/* 348 */         if ((bundleName == null) || (bundleNames.size() == 0)) {
/* 349 */           throw new IllegalArgumentException("invalid bundle name \"" + bundleName + "\"");
/*     */         }
/*     */       }
/*     */     }
/* 353 */     Object oldValue = this.applicationBundleNames;
/* 354 */     if (bundleNames != null) {
/* 355 */       this.applicationBundleNames = Collections.unmodifiableList(new ArrayList(bundleNames));
/*     */     }
/*     */     else {
/* 358 */       this.applicationBundleNames = null;
/*     */     }
/* 360 */     this.resourceMaps.clear();
/* 361 */     firePropertyChange("applicationBundleNames", oldValue, this.applicationBundleNames);
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
/*     */   private String classBundleBaseName(Class cls)
/*     */   {
/* 376 */     String className = cls.getName();
/* 377 */     StringBuffer sb = new StringBuffer();
/* 378 */     int i = className.lastIndexOf('.');
/* 379 */     if (i > 0) {
/* 380 */       sb.append(className.substring(0, i));
/* 381 */       sb.append(".resources.");
/* 382 */       sb.append(cls.getSimpleName());
/*     */     }
/*     */     else {
/* 385 */       sb.append("resources.");
/* 386 */       sb.append(cls.getSimpleName());
/*     */     }
/* 388 */     return sb.toString();
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
/*     */   protected List<String> getClassBundleNames(Class cls)
/*     */   {
/* 422 */     String bundleName = classBundleBaseName(cls);
/* 423 */     return Collections.singletonList(bundleName);
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
/*     */   protected ResourceMap createResourceMap(ClassLoader classLoader, ResourceMap parent, List<String> bundleNames)
/*     */   {
/* 436 */     return new ResourceMap(parent, classLoader, bundleNames);
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
/*     */   public String getPlatform()
/*     */   {
/* 449 */     return getResourceMap().getString("platform", new Object[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlatform(String platform)
/*     */   {
/* 475 */     if (platform == null) {
/* 476 */       throw new IllegalArgumentException("null platform");
/*     */     }
/* 478 */     getResourceMap().putResource("platform", platform);
/*     */   }
/*     */ }


/* Location:              C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar!\org\jdesktop\application\ResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */