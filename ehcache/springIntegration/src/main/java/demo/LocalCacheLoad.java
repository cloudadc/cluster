 package demo;
  
public class LocalCacheLoad {
   Cache<Object, Object> cacheTemp = CacheManager.getInstance().getCache("00009");
   Cache<Object, Object> cacheMOName = CacheManager.getInstance().getCache("00001");
   Cache<Object, Object> moStateDefineCache = CacheManager.getInstance().getCache("00006");
   Cache<Object, Object> presentationObjectCache = CacheManager.getInstance().getCache("00010");
   Cache<Object, Object> bizParamsCache = CacheManager.getInstance().getCache("BIZ_PARAMS");
   Cache<Object, Object> ompParamsCache = CacheManager.getInstance().getCache("OMP_PARAMS");
   Cache<Object, Object> hostmoduleCache = CacheManager.getInstance().getCache("HOST_MODULE");
 
	public LocalCacheLoad() {
     System.out.print("*************************test*********************************************");
	}
}

