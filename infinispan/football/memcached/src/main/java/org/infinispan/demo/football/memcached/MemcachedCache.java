package org.infinispan.demo.football.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import net.spy.memcached.MemcachedClient;

/**
 * A cache working via Memcached client (only few basic operations)
 *  
 */
class MemcachedCache<K, V> implements ConcurrentMap<K, V> {

    private MemcachedClient client;

    MemcachedCache(String hostname, int port) {
        try {
            this.client = new MemcachedClient(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create a Memcached client", e);
        }
    }

    private String toStringKey(Object key) {
        if (key instanceof String) {
            return (String) key;
        } else {
            throw new UnsupportedOperationException("Memcached cache only allows String keys!");
        }
    }

    private Object decode(String s) {
        if (s == null) {
            return null;
        } else {
            Object o = Base64.decodeToObject(s);
            return o;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        String stringKey = toStringKey(key);
        return (V) client.get(stringKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        String stringKey = toStringKey(key);
        V oldValue = (V) client.get(stringKey);
        try {
            if (!client.set(stringKey, 0, value).get()) {
                throw new RuntimeException("put() failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        String stringKey = toStringKey(key);
        V oldValue = (V) client.get(stringKey);
        try {
            client.delete(stringKey).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public V replace(K key, V value) {
        return put(key, value);
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Unsupported");
    }
}