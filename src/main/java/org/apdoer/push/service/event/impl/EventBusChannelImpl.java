package org.apdoer.push.service.event.impl;

import com.google.common.eventbus.AsyncEventBus;
import org.apdoer.common.service.thread.NameableThreadFactory;
import org.apdoer.push.service.backpressure.BackPressure;
import org.apdoer.push.service.backpressure.impl.FixedThreadPoolBackPressure;
import org.apdoer.push.service.event.EventBusChannel;
import org.apdoer.push.service.event.SourceEvent;
import org.apdoer.push.service.event.SourceEventListener;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 内部数据通道实现
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 15:36
 */
public class EventBusChannelImpl implements EventBusChannel {
    /**
     * 无界队列,max无意义
     */
    private final static int DEFAULT_CORE_POOLSIZE = 50;
    private final static int DEFAULT_MAX_POOLSIZE = 50;
    private final static int INITIAL_CAPACITY = 350000;
    // 默认线程池线程空闲时间
    private final static long DEFAULT_KEEP_ALIVE_TIME = 60L;

    private String name;
    private final AsyncEventBus eventBus;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final BackPressure backPressure;


    public EventBusChannelImpl(String name) {
        this(0, name);
    }

    public EventBusChannelImpl(int backpressureSize, String name) {
        this.threadPoolExecutor = new ThreadPoolExecutor(DEFAULT_CORE_POOLSIZE, DEFAULT_MAX_POOLSIZE,
                DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(INITIAL_CAPACITY),
                new NameableThreadFactory("push-systemChannel"));
        this.eventBus = new AsyncEventBus(threadPoolExecutor);
        this.backPressure = new FixedThreadPoolBackPressure(backpressureSize,threadPoolExecutor);
        this.name = name;
    }

    @Override
    public void subscribe(SourceEventListener listener) {

    }

    @Override
    public void unSubscribe(SourceEventListener listener) {

    }

    @Override
    public void publish(SourceEvent event) {

    }

    @Override
    public int getQueueSize() {
        return 0;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getName() {
        return null;
    }
}
