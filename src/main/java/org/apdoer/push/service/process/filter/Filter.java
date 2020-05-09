package org.apdoer.push.service.process.filter;

/**
 * @author Li
 * @version 1.0
 * @date 2020/5/9 11:40
 */
public interface Filter<S,T> {

    /**
     * 数据过滤
     * @param source 源数据
     * @return 目标数据
     */
    T filter(S source);
}
