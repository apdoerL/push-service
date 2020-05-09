package org.apdoer.push.service.backpressure;

import java.util.concurrent.TimeUnit;

/**
 * 反压接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 15:37
 */
public interface BackPressure {

    /**
     * 反压
     *
     * @param batch    当前数据
     * @param timeout  反压时间
     * @param timeUnit 反压时间单位
     * @return
     * @throws InterruptedException
     */
    boolean backPress(int batch, long timeout, TimeUnit timeUnit) throws InterruptedException;
}
