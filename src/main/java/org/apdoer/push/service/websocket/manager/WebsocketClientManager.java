package org.apdoer.push.service.websocket.manager;

import org.apdoer.push.service.push.session.impl.SessionSocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket client管理类 单例
 *
 * @author apdoer
 */
public class WebsocketClientManager {

    private Map<String, SessionSocketClient> clientMap = new ConcurrentHashMap<>();

    private WebsocketClientManager() {
    }

    private static class InnerWebsocketClientManager {
        private static final WebsocketClientManager INSTANCE = new WebsocketClientManager();
    }

    public static final WebsocketClientManager getInstance() {
        return InnerWebsocketClientManager.INSTANCE;
    }

    public Map<String, SessionSocketClient> getClientMap() {
        return this.clientMap;
    }

    public boolean containsKey(String id) {
        return this.clientMap.containsKey(id);
    }

    public void add(String id, SessionSocketClient client) {
        this.clientMap.put(id, client);
    }

    public SessionSocketClient get(String id) {
        return this.clientMap.get(id);
    }

    public void remove(String id) {
        this.clientMap.remove(id);
    }

    public int size() {
        return this.clientMap.size();
    }
}
