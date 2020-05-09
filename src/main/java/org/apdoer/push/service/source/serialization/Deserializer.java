package org.apdoer.push.service.source.serialization;

/**
 * 反序列化器
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/8 17:06
 */
public interface Deserializer<DES, REQ,IP> {

    /**
     * 反序列化数据
     * s 源数据
     * @return  T目标数据
     */
    DES deserialize(REQ data);


    boolean isPush(IP data);
}
