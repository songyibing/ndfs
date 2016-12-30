
/*
 * Copyright (c) 2016 Sohu. All Rights Reserved
 */
package ndfs.core.cache;

/**
 * <p>
 * Description: 缓存接口
 * </p>
 * 
 * @author yibingsong
 * @Date 2016年7月18日 下午3:11:37
 */
public interface LocalCache<K, V> {
    
    public void put(K key, V value);

    // 如果缓存中没有，返回null； 如果key=null，抛出空指针异常。
    public V get(K key);

    public boolean contains(K key);

    public void remove(K key);

    public boolean removeValue(V value);
    
    public long size();
}
