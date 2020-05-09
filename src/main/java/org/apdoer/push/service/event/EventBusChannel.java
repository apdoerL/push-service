package org.apdoer.push.service.event;

/**
 * 内部数据通道接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 14:35
 */
public interface EventBusChannel {
    /**
     * 注册消息监听器
     *
     * @param listener
     */
    void subscribe(SourceEventListener listener);

    /**
     * 移除消息监听器
     *
     * @param listener
     */
    void unSubscribe(SourceEventListener listener);

    /**
     * 发布易感事件
     *
     * @param event
     */
    void publish(SourceEvent event);

    /**
     * 获取数据通道大小
     *
     * @return
     */
    int getQueueSize();

    /**
     * 关闭通道
     */
    void shutdown();

    /**
     * 获取内部通道名称
     *
     * @return
     */
    String getName();
}
