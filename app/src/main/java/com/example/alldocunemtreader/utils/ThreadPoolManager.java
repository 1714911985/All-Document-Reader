package com.example.alldocunemtreader.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author: Eccentric
 * Created on 2024/6/24 09:54.
 * Description: com.example.alldocunemtreader.utils.ThreadPoolManager
 */
public class ThreadPoolManager {

    // 单例模式，确保只有一个实例
    private static ThreadPoolManager instance;

    // 定义线程池
    private ExecutorService singleExecutor;
    private ExecutorService fixedExecutor;
    private ExecutorService cachedExecutor;
    private ScheduledExecutorService scheduledExecutor;

    // 私有构造函数，防止外部实例化
    private ThreadPoolManager() {
        singleExecutor = Executors.newSingleThreadExecutor();
        fixedExecutor = Executors.newFixedThreadPool(4); // 可以根据需要调整线程池大小
        cachedExecutor = Executors.newCachedThreadPool();
        scheduledExecutor = Executors.newScheduledThreadPool(2); // 可以根据需要调整线程池大小
    }

    // 获取单例实例
    public static synchronized ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    // 提交单线程任务
    public void executeSingle(Runnable task) {
        singleExecutor.execute(task);
    }

    // 提交固定线程池任务
    public void executeFixed(Runnable task) {
        fixedExecutor.execute(task);
    }

    // 提交缓存线程池任务
    public void executeCached(Runnable task) {
        cachedExecutor.execute(task);
    }

    // 提交定时任务
    public void schedule(Runnable task, long delay, TimeUnit unit) {
        scheduledExecutor.schedule(task, delay, unit);
    }

    // 提交周期性任务
    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    // 关闭所有线程池
    public void shutdown() {
        singleExecutor.shutdown();
        fixedExecutor.shutdown();
        cachedExecutor.shutdown();
        scheduledExecutor.shutdown();
    }
}