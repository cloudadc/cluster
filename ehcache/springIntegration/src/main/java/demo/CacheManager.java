 package demo;
 
 import java.util.Collection;
 import java.util.Map;
 import java.util.Set;
 import java.util.concurrent.locks.Lock;
 import java.util.concurrent.locks.ReentrantLock;
 import net.sf.ehcache.Ehcache;
 
public class CacheManager {
   private static Lock lock = new ReentrantLock();
 
   private static CacheManager instance = null;
 
   private CacheManager() {
     CacheManager.getInstance();
   }
 
   public static CacheManager getInstance() {
     lock.lock();
     if (instance == null) {
       instance = new CacheManager();
     }
     lock.unlock();
     return instance;
   }
 
   public String[] getCacheNames() {
     return CacheManager.getInstance().getCacheNames();
   }
 
   public void addCache(String cacheName) {
     CacheManager.getInstance().addCache(cacheName);
   }
 
   public <K, V> Cache<K, V> getCache(String cacheName) {
     Cache cache = null;
     Ehcache ehcache = null;
     ehcache = (Ehcache) CacheManager.getInstance().getCache(cacheName);
     if (ehcache == null) {
       addCache(cacheName);
       ehcache = (Ehcache) CacheManager.getInstance().getCache(cacheName);
     }
     cache = new CacheImpl(ehcache);
     return cache;
   }
 
   public Collection<?> getCacheValues(Cache<?, ?> cache) {
     if (cache == null) {
       return null;
     }
     return cache.values();
   }
 
   public Object getCacheValue(Object key, Cache<?, ?> cache) {
     if ((key == null) || (cache == null)) {
       return null;
     }
     Object result = null;
     try {
       result = cache.get(key);
     } catch (Exception e) {
      result = null;
       e.printStackTrace();
     }
     return result;
   }
 
   public Set<?> getCacheKeySet(Cache<?, ?> cache) {
     if (cache != null) {
       return cache.keySet();
     }
     return null;
   }
 
   public void putCacheValue(Object key, Object value, Cache<Object, Object> cache) {
     if ((key == null) || (cache == null)) {
       return;
     }
     cache.put(key, value);
   }
 
   public void putAll(Map<? extends Object, ? extends Object> map, Cache<Object, Object> cache) {
     if (cache == null) {
       return;
     }
     cache.putAll(map);
   }
 
   public void removeCacheValue(Object key, Cache<?, ?> cache) {
     if ((key == null) || (cache == null)) {
       return;
     }
     cache.remove(key);
   }
 
   public boolean isEmptyCache(Cache<?, ?> cache) {
     if (cache != null) {
       return cache.isEmpty();
     }
     return false;
   }
 
   public boolean isContainsKey(Object key, Cache<?, ?> cache) {
     if ((key == null) || (cache == null)) {
       return false;
     }
     return cache.containsKey(key);
   }
 
   public boolean isContainsValue(Object value, Cache<?, ?> cache) {
     if (cache == null) {
       return false;
     }
     return cache.containsValue(value);
   }
 
   public void clearCache(Cache<?, ?> cache) {
     if (cache != null)
       cache.clear();
   }
 
   public void destroyCache(Cache<?, ?> cache)
   {
     if (cache != null) {
       cache.clear();
       CacheManager.getInstance().removeCacheValue(cache.getCacheName(), cache);
     }
   }
 
   public void shutdown() {
     CacheManager.getInstance().shutdown();
   }
 
   public Map<Object, Object> getCacheValuesByKeys(Collection<Object> keys, Cache<?, ?> cache) {
     return cache.getValuesByKeys(keys);
   }
 }
