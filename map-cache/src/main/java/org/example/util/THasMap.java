package org.example.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 这个类使用 ScheduledExecutorService 服务内部的优先队列，使用了线程池的方法，
 * 在设置缓存时创建会创建一个删除 该缓存的定时任务，
 * 当定时任务到达运行时间时，就会删除该对象，但是用户可能会修改超时时间，所以会先检查超时时间，只有超过超时时间才会被删除。
 * @param <K> key
 * @param <V> value
 */
public class THasMap <K, V> {
    static class ExpirableData <V> {
        long endTimestamp;
        V data;
        ExpirableData(V data, long delay){
            this.data = data;
            this.endTimestamp = System.currentTimeMillis() + delay;
        }
    }
    private final ConcurrentHashMap<K, ExpirableData <V>> map = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void put(K key, V value, long expire) {
        map.put(key, new ExpirableData(value, expire));
        if (expire > 0) {
            scheduler.schedule(
                    () -> {
                        if(map.get(key)!=null && map.get(key).endTimestamp <= System.currentTimeMillis()){
                            map.remove(key);
                        }
                    },
                    expire,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    public void setExpire(K key, long expire){
        if(map.contains(key)){
            map.get(key).endTimestamp = System.currentTimeMillis() + expire;
            scheduler.schedule(
                    () -> {
                        if(map.get(key)!=null && map.get(key).endTimestamp <= System.currentTimeMillis()){
                            map.remove(key);
                        }
                    },
                    expire,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    public V get(K key) {
        if(map.get(key) == null){
            return null;
        }
        return map.get(key).data;
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public void stop(){
        scheduler.shutdown();
    }
}
