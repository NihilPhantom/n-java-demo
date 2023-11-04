package org.example.util;

import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class THasMap2 <K, V> {
    private final int TimeoutTaskThreshold = 10;

    private final int CheckPeriod = 5 * 1000;

    static class ExpirableKey <K> implements Comparable<ExpirableKey <K>>{
        long endTimestamp;
        K key;

        ExpirableKey(K k, long delay){
            this.key = k;
            this.endTimestamp = System.currentTimeMillis() + delay;
        }

        @Override
        public int compareTo(ExpirableKey<K> o) {
            return Long.compare(this.endTimestamp, o.endTimestamp);
        }
    }

    static class ExpirableData <V> {
        long endTimestamp;
        V data;
        ExpirableData(V data, long delay){
            this.data = data;
            this.endTimestamp = System.currentTimeMillis() + delay;
        }
    }

    private final ConcurrentHashMap<K, ExpirableData<V>> map = new ConcurrentHashMap<>();
    private final Timer timer = new Timer();

    private TimerTask task;

    PriorityQueue<ExpirableKey <K>> minHeap = new PriorityQueue<>();

    public THasMap2(){
        start();
    }

    public void start(){
        // 创建定时任务
        task = new TimerTask() {
            @Override
            public void run() {
                // 定时任务要执行的操作
                if(minHeap.size() >= TimeoutTaskThreshold){
                    int s = minHeap.size();
                    while(minHeap.peek().endTimestamp <= System.currentTimeMillis()){
                        ExpirableKey<K> expirableKey = minHeap.poll();
                        if(map.get(expirableKey.key)!=null && map.get(expirableKey.key).endTimestamp <= System.currentTimeMillis()){
                            map.remove(expirableKey.key);
                        }
                    }
                    int e = minHeap.size();
                    System.out.println("【触发清理方法】清理前:"+ s +"个 清理后" + e + "个");
                }
            }
        };
        timer.schedule(task, 0, CheckPeriod);
    }


    public void put(K key, V value, long expire) {
        map.put(key, new THasMap2.ExpirableData(value, expire));
        minHeap.add(new ExpirableKey<>(key, expire));
    }

    public void setExpire(K key, long expire){
        if(map.contains(key)){
            map.get(key).endTimestamp = System.currentTimeMillis() + expire;
        }
        minHeap.add(new ExpirableKey<>(key, expire));
    }

    public V get(K key) {
        if(map.get(key) == null){
            return null;
        }
        if(map.get(key).endTimestamp < System.currentTimeMillis() ){
            map.remove(key);
            return null;
        }
        return map.get(key).data;
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public void stop(){
        task.cancel();
        timer.cancel();
    }
}
