package org.apdoer.push.service.push.session.impl;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.push.event.WebsocketChannelListener;
import org.apdoer.push.service.push.session.AbstractSession;
import org.apdoer.push.service.push.session.enums.SessionState;
import org.apdoer.push.service.websocket.ClientInfo;
import org.apdoer.push.service.websocket.ClientParamsConfig;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author apdoer
 */
@Slf4j
@Data
public class SessionSocketClient extends AbstractSession<String> {
    private SocketIOClient client;

    private AtomicInteger al = new AtomicInteger(0);

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 数据监听器
     */
    private WebsocketChannelListener sourceEventListener;

    /**
     * client 身份信息
     */
    private ClientInfo clientInfo;

    /**
     * 配置信息
     */
    private ClientParamsConfig clientParamsConfig = new ClientParamsConfig();

    @Override
    public String getId() {
        return this.client.getSessionId().toString();
    }

    @Override
    public SessionState getState() {
        return this.sessionState;
    }

    @Override
    public void setState(SessionState state) {
        this.sessionState = state;
    }

    @Override
    public boolean isOpen() {
        return client.isChannelOpen();
    }

    @Override
    public long getLong() {
        return this.al.incrementAndGet();
    }

    @Override
    public Map<String, Object> getUserProperties() {
        return null;
    }

    @Override
    public void sendMessage(Object message, String topic, boolean isMonitor) {
        try {
            if (isValidSession()) {
                this.client.sendEvent(topic, message);
            }
        } catch (Exception e) {
            log.error("send message error", e);
        }
    }

    private boolean isValidSession() {
        if (this.isOpen() && this.getState() != SessionState.DISCONNECTING) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public long getMessageId() {
        return this.al.get();
    }

    @Override
    public void close() throws IOException {
        try {
            lock.lock();
            if (this.isOpen()) {
                this.setExpireTime(0);
                this.close();
            }
        } catch (Exception e) {
            log.error("web socket close error", e);
        } finally {
            lock.unlock();
        }
    }
}
