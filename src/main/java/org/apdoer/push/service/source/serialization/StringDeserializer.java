package org.apdoer.push.service.source.serialization;


import org.apache.commons.lang.StringUtils;
import org.apdoer.common.service.util.JacksonUtil;
import org.apdoer.push.service.payload.Payload;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 9:58
 */
public abstract class StringDeserializer implements Deserializer<Payload, String, String> {
    @Override
    public Payload deserialize(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        return this.handle(data);
    }

    private Payload handle(String data){
        Map<String, Object> dataMap = JacksonUtil.jsonToObj(data.trim(), Map.class);
        dataMap = this.handle(dataMap);
        if (!CollectionUtils.isEmpty(dataMap)){
            Payload payload = new Payload();
            List<Map<String, Object>> dataList = new ArrayList<>(1);
            dataList.add(dataMap);
            payload.setData(dataList);
            payload.setTopic(this.getTopic(dataMap));
        }
        return null;
    }


    /**
     * 处理数据
     * @param dataMap
     * @return
     */
    protected abstract  Map<String, Object> handle(Map<String, Object> dataMap);

    /**
     * 获取当前数据的topic
     * @param dataMap
     * @return
     */
    protected abstract String getTopic(Map<String, Object> dataMap);
}
