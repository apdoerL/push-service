package org.apdoer.push.service.process;

/**
 * 数据处理接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:36
 */
public interface Processor<S, T> {

    /**
     * 数据处理
     *
     * @param source 源数据
     * @return 目标数据
     */
    T process(S source);
}
