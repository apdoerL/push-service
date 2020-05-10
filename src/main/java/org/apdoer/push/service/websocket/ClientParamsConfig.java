package org.apdoer.push.service.websocket;

import org.apdoer.push.service.websocket.message.Topic;

import java.util.HashMap;
import java.util.Map;

/**
 * 订阅参数
 *
 * @author apdoer
 */
public class ClientParamsConfig {

    private Map<String, Topic> paramsMap = new HashMap<>();

    public boolean containsKey(String topic) {
        return this.paramsMap.containsKey(topic);
    }

    public Topic get(String topic) {
        return this.paramsMap.get(topic);
    }

    public void set(String key, Topic value) {
        this.paramsMap.put(key, value);
    }

    public Map<String, Topic> getParams() {
        return this.paramsMap;
    }


    public Map<String, Topic> getNotNullParams() {
        Map<String, Topic> paramsMap = new HashMap<>(this.paramsMap.size());
        for (Map.Entry<String, Topic> entry : this.paramsMap.entrySet()) {
            if (null != entry.getValue()) {
                paramsMap.put(entry.getKey(), entry.getValue());
            }
        }
        return paramsMap;
    }
}
