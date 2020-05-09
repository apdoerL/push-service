package org.apdoer.push.service.job;


/**
 * @author apdoer
 */
public enum JobStatus {
    /**
     * NEW：               创建
     * READY：          准备
     * RUNNING：     运行
     * SLEEP：          休眠
     * STOPED：        停止
     * EXCEPTION：异常
     */
    NEW,
    READY,
    RUNNING,
    SLEEP,
    STOPED,
    EXCEPTION;
}
