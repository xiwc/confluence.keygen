/*   1:    */ package org.jdesktop.application;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.ListIterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.concurrent.ConcurrentHashMap;
/*   9:    */ import java.util.logging.Logger;
/*  10:    */ 
/*  11:    */ public class ResourceManager
/*  12:    */   extends AbstractBean
/*  13:    */ {
/*  14: 56 */   private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());
/*  15:    */   private final Map<String, ResourceMap> resourceMaps;
/*  16:    */   private final ApplicationContext context;
/*  17: 59 */   private List<String> applicationBundleNames = null;
/*  18: 60 */   private ResourceMap appResourceMap = null;
/*  19:    */   
/*  20:    */   protected ResourceManager(ApplicationContext context)
/*  21:    */   {
/*  22: 80 */     if (context == null) {
/*  23: 81 */       throw new IllegalArgumentException("null context");
/*  24:    */     }
/*  25: 83 */     this.context = context;
/*  26: 84 */     this.resourceMaps = new ConcurrentHashMap();
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected final ApplicationContext getContext()
/*  30:    */   {
/*  31: 89 */     return this.context;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private List<String> allBundleNames(Class startClass, Class stopClass)
/*  35:    */   {
/*  36:100 */     List<String> bundleNames = new ArrayList();
/*  37:101 */     Class limitClass = stopClass.getSuperclass();
/*  38:102 */     for (Class c = startClass; c != limitClass; c = c.getSuperclass()) {
/*  39:103 */       bundleNames.addAll(getClassBundleNames(c));
/*  40:    */     }
/*  41:105 */     return Collections.unmodifiableList(bundleNames);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private String bundlePackageName(String bundleName)
/*  45:    */   {
/*  46:109 */     int i = bundleName.lastIndexOf(".");
/*  47:110 */     return i == -1 ? "" : bundleName.substring(0, i);
/*  48:    */   }
/*  49:    */   
/*  50:    */   private ResourceMap createResourceMapChain(ClassLoader cl, ResourceMap root, ListIterator<String> names)
/*  51:    */   {
/*  52:120 */     if (!names.hasNext()) {
/*  53:121 */       return root;
/*  54:    */     }
/*  55:124 */     String bundleName0 = (String)names.next();
/*  56:125 */     String rmBundlePackage = bundlePackageName(bundleName0);
/*  57:126 */     List<String> rmNames = new ArrayList();
/*  58:127 */     rmNames.add(bundleName0);
/*  59:128 */     while (names.hasNext())
/*  60:    */     {
/*  61:129 */       String bundleName = (String)names.next();
/*  62:130 */       if (rmBundlePackage.equals(bundlePackageName(bundleName)))
/*  63:    */       {
/*  64:131 */         rmNames.add(bundleName);
/*  65:    */       }
/*  66:    */       else
/*  67:    */       {
/*  68:134 */         names.previous();
/*  69:135 */         break;
/*  70:    */       }
/*  71:    */     }
/*  72:138 */     ResourceMap parent = createResourceMapChain(cl, root, names);
/*  73:139 */     return createResourceMap(cl, parent, rmNames);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private ResourceMap getApplicationResourceMap()
/*  77:    */   {
/*  78:150 */     if (this.appResourceMap == null)
/*  79:    */     {
/*  80:151 */       List<String> appBundleNames = getApplicationBundleNames();
/*  81:152 */       Class appClass = getContext().getApplicationClass();
/*  82:153 */       if (appClass == null)
/*  83:    */       {
/*  84:154 */         logger.warning("getApplicationResourceMap(): no Application class");
/*  85:155 */         appClass = Application.class;
/*  86:    */       }
/*  87:157 */       ClassLoader classLoader = appClass.getClassLoader();
/*  88:158 */       this.appResourceMap = createResourceMapChain(classLoader, null, appBundleNames.listIterator());
/*  89:    */     }
/*  90:160 */     return this.appResourceMap;
/*  91:    */   }
/*  92:    */   
/*  93:    */   private ResourceMap getClassResourceMap(Class startClass, Class stopClass)
/*  94:    */   {
/*  95:167 */     String classResourceMapKey = startClass.getName() + stopClass.getName();
/*  96:168 */     ResourceMap classResourceMap = (ResourceMap)this.resourceMaps.get(classResourceMapKey);
/*  97:169 */     if (classResourceMap == null)
/*  98:    */     {
/*  99:170 */       List<String> classBundleNames = allBundleNames(startClass, stopClass);
/* 100:171 */       ClassLoader classLoader = startClass.getClassLoader();
/* 101:172 */       ResourceMap appRM = getResourceMap();
/* 102:173 */       classResourceMap = createResourceMapChain(classLoader, appRM, classBundleNames.listIterator());
/* 103:174 */       this.resourceMaps.put(classResourceMapKey, classResourceMap);
/* 104:    */     }
/* 105:176 */     return classResourceMap;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public ResourceMap getResourceMap(Class startClass, Class stopClass)
/* 109:    */   {
/* 110:245 */     if (startClass == null) {
/* 111:246 */       throw new IllegalArgumentException("null startClass");
/* 112:    */     }
/* 113:248 */     if (stopClass == null) {
/* 114:249 */       throw new IllegalArgumentException("null stopClass");
/* 115:    */     }
/* 116:251 */     if (!stopClass.isAssignableFrom(startClass)) {
/* 117:252 */       throw new IllegalArgumentException("startClass is not a subclass, or the same as, stopClass");
/* 118:    */     }
/* 119:254 */     return getClassResourceMap(startClass, stopClass);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final ResourceMap getResourceMap(Class cls)
/* 123:    */   {
/* 124:269 */     if (cls == null) {
/* 125:270 */       throw new IllegalArgumentException("null class");
/* 126:    */     }
/* 127:272 */     return getResourceMap(cls, cls);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ResourceMap getResourceMap()
/* 131:    */   {
/* 132:289 */     return getApplicationResourceMap();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public List<String> getApplicationBundleNames()
/* 136:    */   {
/* 137:326 */     if (this.applicationBundleNames == null)
/* 138:    */     {
/* 139:327 */       Class appClass = getContext().getApplicationClass();
/* 140:328 */       if (appClass == null) {
/* 141:329 */         return allBundleNames(Application.class, Application.class);
/* 142:    */       }
/* 143:332 */       this.applicationBundleNames = allBundleNames(appClass, Application.class);
/* 144:    */     }
/* 145:335 */     return this.applicationBundleNames;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setApplicationBundleNames(List<String> bundleNames)
/* 149:    */   {
/* 150:346 */     if (bundleNames != null) {
/* 151:347 */       for (String bundleName : bundleNames) {
/* 152:348 */         if ((bundleName == null) || (bundleNames.size() == 0)) {
/* 153:349 */           throw new IllegalArgumentException("invalid bundle name \"" + bundleName + "\"");
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:353 */     Object oldValue = this.applicationBundleNames;
/* 158:354 */     if (bundleNames != null) {
/* 159:355 */       this.applicationBundleNames = Collections.unmodifiableList(new ArrayList(bundleNames));
/* 160:    */     } else {
/* 161:358 */       this.applicationBundleNames = null;
/* 162:    */     }
/* 163:360 */     this.resourceMaps.clear();
/* 164:361 */     firePropertyChange("applicationBundleNames", oldValue, this.applicationBundleNames);
/* 165:    */   }
/* 166:    */   
/* 167:    */   private String classBundleBaseName(Class cls)
/* 168:    */   {
/* 169:376 */     String className = cls.getName();
/* 170:377 */     StringBuffer sb = new StringBuffer();
/* 171:378 */     int i = className.lastIndexOf('.');
/* 172:379 */     if (i > 0)
/* 173:    */     {
/* 174:380 */       sb.append(className.substring(0, i));
/* 175:381 */       sb.append(".resources.");
/* 176:382 */       sb.append(cls.getSimpleName());
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:385 */       sb.append("resources.");
/* 181:386 */       sb.append(cls.getSimpleName());
/* 182:    */     }
/* 183:388 */     return sb.toString();
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected List<String> getClassBundleNames(Class cls)
/* 187:    */   {
/* 188:422 */     String bundleName = classBundleBaseName(cls);
/* 189:423 */     return Collections.singletonList(bundleName);
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected ResourceMap createResourceMap(ClassLoader classLoader, ResourceMap parent, List<String> bundleNames)
/* 193:    */   {
/* 194:436 */     return new ResourceMap(parent, classLoader, bundleNames);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getPlatform()
/* 198:    */   {
/* 199:449 */     return getResourceMap().getString("platform", new Object[0]);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setPlatform(String platform)
/* 203:    */   {
/* 204:475 */     if (platform == null) {
/* 205:476 */       throw new IllegalArgumentException("null platform");
/* 206:    */     }
/* 207:478 */     getResourceMap().putResource("platform", platform);
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\Users\xi\Desktop\confluence_keygen\confluence_keygen.jar
 * Qualified Name:     org.jdesktop.application.ResourceManager
 * JD-Core Version:    0.7.0.1
 */