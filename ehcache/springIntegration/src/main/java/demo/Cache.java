package demo;

import java.util.Collection;
import java.util.Map;

public abstract interface Cache<K, V> extends Map<K, V>
{
  public abstract String getCacheName();

  public abstract Map<Object, Object> getValuesByKeys(Collection<Object> paramCollection);
}

