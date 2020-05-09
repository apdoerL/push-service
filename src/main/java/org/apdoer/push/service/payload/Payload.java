package org.apdoer.push.service.payload;

import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 *  默认数据负载
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/8 17:21
 */
@Data
public class Payload {

    private String topic;

    private List<Map<String, Object>> data;
}
