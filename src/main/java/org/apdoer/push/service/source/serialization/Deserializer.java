package org.apdoer.push.service.source.serialization;

/**
 * 反序列化器
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/8 17:06
 */
public interface Deserializer<S, T> {

    /**
     * 反序列化数据
     * s 源数据
     *
     * @return T目标数据
     */
    T deserialize(S source);


    /**
     * 根据业务决定是否需要反序列化
     * @param soure source
     * @return
     */
    boolean isPush(S soure);
}
