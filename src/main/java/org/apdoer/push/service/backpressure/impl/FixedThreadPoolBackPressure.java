package org.apdoer.push.service.backpressure.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.backpressure.BackPressure;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * fix 线程池的反压实现
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 15:42
 */
@Slf4j
public class FixedThreadPoolBackPressure implements BackPressure {

    /**
     * 默认反压阈值
     */
    private final static int DEFAULT_BACKPRESSURE_SIZE = 300000;

    private int backPressureSize;
    private ThreadPoolExecutor threadPoolExecutor;

    public FixedThreadPoolBackPressure(int backPressureSize, ThreadPoolExecutor threadPoolExecutor) {
        this.backPressureSize = backPressureSize <= 0 ? DEFAULT_BACKPRESSURE_SIZE : backPressureSize;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public boolean backPress(int batch, long timeout, TimeUnit timeUnit) throws InterruptedException {
        if (threadPoolExecutor.getQueue().size() + batch > backPressureSize) {
            //休眠
            Thread.sleep(timeUnit.toMillis(timeout));
            log.info("back pressing ");
            return true;
        }
        return false;
    }
}
