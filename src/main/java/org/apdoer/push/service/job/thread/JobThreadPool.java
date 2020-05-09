package org.apdoer.push.service.job.thread;

import org.apdoer.common.service.thread.NameableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author apdoer 单例
 * @version 1.0
 * @date 2020/5/9 12:22
 */
public class JobThreadPool {

    private static final int CORE_POOLSIZE = 5;
    private static final int MAX_POOLSIZE = 10;
    private static final int INIT_CAPACITY = 500;
    private static final long KEEP_ALIVE = 20L;

    private static ThreadPoolExecutor executor;


    private JobThreadPool() {
        executor = new ThreadPoolExecutor(
                CORE_POOLSIZE,
                MAX_POOLSIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(INIT_CAPACITY),
                new NameableThreadFactory("push-job-pool"));
    }

    private static class InnerJobThreadPool {
        private static final JobThreadPool INSTANCE = new JobThreadPool();
    }

    public static final JobThreadPool getInstance() {
        return InnerJobThreadPool.INSTANCE;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public int size() {
        return executor.getQueue().size();
    }

    public void shutdown(){
        executor.shutdown();
    }

}
