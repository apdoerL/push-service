package org.apdoer.push.service.event;

import java.util.Map;

/**
 * 内部数据通道管理接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 14:51
 */
public interface EventBusManager {

    /**
     * 注册消息监听器-绑定所有通道
     *
     * @param listener
     */
    void subscribe(SourceEventListener listener);

    /**
     * 注册消息监听器到指定通道
     *
     * @param listener
     * @param systemChannel
     */
    void subscribe(SourceEventListener listener, String systemChannel);

    /**
     * 移除消息监听器-所有通道
     */
    void unSubscribe(SourceEventListener listener);

    /**
     * 移除指定通道的消息监听器
     *
     * @param listener
     * @param systemChannel
     */
    void unSubscribe(SourceEventListener listener, String systemChannel);

    /**
     * 发送易感事件搭配指定通道
     *
     * @param systemChannel
     * @param event
     */
    void publish(String systemChannel, SourceEvent event);

    /**
     * 构建内部通道
     *
     * @param systemChannel
     */
    void buildEventBusChannel(String systemChannel);

    /**
     * 构建内部通道-反压
     *
     * @param systemChannel
     * @param backPressureSize
     */
    void buildEventBusChannel(String systemChannel, int backPressureSize);

    /**
     * 获取通道
     *
     * @param systemChannel
     * @return
     */
    EventBusChannel getEventBusChannel(String systemChannel);

    /**
     * 指定通道数否存在
     *
     * @param systemChannel
     * @return
     */
    boolean containsChannel(String systemChannel);

    /**
     * 获取通道中数据堆积情况
     *
     * @return
     */
    Map<String, Integer> getChannelWaitSize();


}
