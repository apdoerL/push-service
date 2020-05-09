package org.apdoer.push.service.source.breakpoint;

/**
 * 断点续传 断点接口
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 9:47
 */
public interface BreakPoint<SEQ> {

    /**
     * 获取断点位置
     *
     * @return 断点位置
     */
    SEQ position();


    /**
     * 设置断点位置
     *
     * @param seq 断点位置
     */
    void setPosition(SEQ seq);


    /**
     * 重置断点
     */
    void reset();

    /**
     * 断点存储,持久化
     */
    void mark();
}
