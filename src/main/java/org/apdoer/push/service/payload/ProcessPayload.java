package org.apdoer.push.service.payload;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 数据处理负载
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:24
 */
@Data
public class ProcessPayload {

    private String topic;

    /**
     * 发送渠道 phone email  ws
     */
    private List<String> pushChannels;

    /**
     * 内部数据通道
     */
    private String systemChannel;

    /**
     * 是否需要认证
     */
    private Integer isAuth;

    /**
     * 推送方式,0广播,1点对点
     */
    private Integer pushWay;

    /**
     * 数据优先级
     */
    private Integer priority;
    /**
     * 数据负载
     */
    private List<Map<String, Object>> payload;

    private long timestamp = System.currentTimeMillis();
}
