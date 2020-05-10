package org.apdoer.push.service.push.session;



import org.apdoer.push.service.push.session.enums.SessionState;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Delayed;

/**
 * websocket session
 *
 * @author apdoer
 */
public interface Session<ID> extends Closeable, Delayed {

    /**
     * 获取客户端id
     *
     * @return
     */
    ID getId();

    /**
     * 获取客户端session状态
     *
     * @return
     */
    SessionState getState();

    /**
     * 设置客户端session状态
     *
     * @param state
     */
    void setState(SessionState state);

    /**
     * 判断客户端session open
     *
     * @return
     */
    boolean isOpen();

    long getLong();

    Map<String, Object> getUserProperties();

    /**
     * 发送消息
     *
     * @param message
     * @param topic
     * @param isMonitor 是否需要监听
     */
    void sendMessage(Object message, String topic, boolean isMonitor);

    long getMessageId();
}

