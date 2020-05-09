package org.apdoer.push.service.source;

/**
 * 数据源接口
 *
 * @author apdoe
 * @version 1.0
 * @date 2020/5/8 16:56
 */
public interface Source<T> {

    /**
     * 初始化
     */
    void init();


    /**
     * 打开连接
     */
    void open();

    /**
     * 读取数据
     * @return T
     */
    T read();

    /**
     * 关闭连接
     */
    void close();

    /**
     * 数据清除
     */
    void cleanUp();


    SourceType getSourceType();
}
