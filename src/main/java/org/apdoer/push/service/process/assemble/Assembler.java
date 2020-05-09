package org.apdoer.push.service.process.assemble;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:40
 */
public interface Assembler<S, T> {


    /**
     * 数据组装
     * @param source 源数据
     * @return 目标数据
     */
    T assemble(S source);
}

