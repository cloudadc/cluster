 package demo;
 
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import net.sf.ehcache.Ehcache;
 import net.sf.ehcache.Element;
 
 public class CacheImpl<K, V>
   implements Cache<K, V>
 {
   private Ehcache cache;
 
   public CacheImpl(Ehcache cache)
   {
     this.cache = cache;
   }
 
   public int size()
   {
     return this.cache.getSize();
   }
 
   public boolean isEmpty()
   {
     return size() == 0;
   }
 
   public boolean containsKey(Object key)
   {
    return this.cache.isKeyInCache(key);
   }
 
   public boolean containsValue(Object value)
   {
     return this.cache.isValueInCache(value);
   }
 
	public V get(Object key) {
		Object result = null;
		Element element = null;
		element = this.cache.get(key);
		if (element != null) {
			result = element.getValue();
		}
		return (V) result;
	}
 
	public V put(K key, V value) {
		Element element = new Element(key, value);
		this.cache.put(element);
		return null;
	}

	public V remove(Object key) {
		return (V) new Boolean(this.cache.remove(key));
	}
 
   public void putAll(Map<? extends K, ? extends V> m)
   {
     Collection collection = null;
     if ((m != null) && (!m.isEmpty())) {
       collection = new ArrayList();
       for (Iterator i = m.entrySet().iterator(); i.hasNext(); ) {
         Map.Entry e = (Map.Entry)i.next();
         collection.add(new Element(e.getKey(), e.getValue()));
       }
     }
     if (collection != null)
       this.cache.putAll(collection);
   }
 
   public void clear()
   {
     this.cache.removeAll();
   }
 
   public Set<K> keySet()
   {
     Set result = null;
     List keys = this.cache.getKeys();
     if (keys != null) {
       result = new HashSet(keys);
     }
     return result;
   }
 
   public Collection<V> values()
   {
     Collection collection = null;
     collection = new ArrayList();
     Set keys = keySet();
     if ((keys != null) && (!keys.isEmpty())) {
       Map values = this.cache.getAll(keys);
       for (Iterator i = values.entrySet().iterator(); i.hasNext(); ) {
         Map.Entry e = (Map.Entry)i.next();
         if ((e.getValue() != null) && (((Element)e.getValue()).getValue() != null)) {
           collection.add(((Element)e.getValue()).getValue());
         }
       }
     }
     return collection;
   }
 
   public Set<Map.Entry<K, V>> entrySet()
   {
     return null;
   }
 
   public String getCacheName()
   {
     return this.cache.getName();
   }
 
  public Map<Object, Object> getValuesByKeys(Collection<Object> keys)
  {
     Map map = null;
     map = new HashMap();
     if ((keys != null) && (!keys.isEmpty())) {
       Map values = this.cache.getAll(keys);
       for (Iterator i = values.entrySet().iterator(); i.hasNext(); ) {
         Map.Entry e = (Map.Entry)i.next();
         map.put(e.getKey(), e.getValue() != null ? ((Element)e.getValue()).getValue() : null);
       }
    }
     return map;
   }
 }
