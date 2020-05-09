package org.apdoer.push.service.event;

import java.util.Set;

/**
 * eventbus 消息监听接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 14:36
 */
public interface SourceEventListener {

    /**
     * 监听感兴趣的事件
     *
     * @param event
     */
    void listen(SourceEvent event);

    /**
     * 获取当前已注册的内部通道
     *
     * @return
     */
    Set<String> getSubscribedSystemChannels();

    /**
     * 是否注册到指定内部通道
     *
     * @param systemChannel
     * @return
     */
    boolean isSubscribedSystemChannel(String systemChannel);

    /**
     * 注册到内部通道
     *
     * @param systemChannel
     */
    void subscribeSystemChannel(String systemChannel);

    /**
     * 获取当前监听器绑定的topic
     *
     * @return
     */
    Set<String> getSubscribedTopics();

    /**
     * 是否监听指定topic
     *
     * @param topic
     * @return
     */
    boolean isSubscribedTopic(String topic);

    /**
     * 监听指定topic
     *
     * @param topic
     */
    void subscribeTopic(String topic);
}
