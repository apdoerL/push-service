package org.apdoer.push.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * zmq配置
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 10:33
 */
@Component
@ConfigurationProperties(prefix = "zmq.")
public class ZmqProperties {
}
