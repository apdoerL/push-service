package org.apdoer.push.service.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * websocket client 订阅topci 参数
 *
 * @author apdoer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    /**
     * 订阅的主题
     */
    private String topic;

    /**
     * depth：深度，gears：档位
     */
    private Map<String, Object> params;

}
