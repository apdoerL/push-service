package org.apdoer.push.service.process.cleaner;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:38
 */
public interface Cleaner<S,T> {

    /**
     * 数据清洗
     * @param source 源数据
     * @return 目标数据
     */
    T clean(S source);
}
