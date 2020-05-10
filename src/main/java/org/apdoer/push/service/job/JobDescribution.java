package org.apdoer.push.service.job;


import lombok.Data;

/**
 * 任务描述
 *
 * @author apdoer
 */
@Data
public class JobDescribution {

    /**
     * job类型
     */
    private JobType jobType;

    /**
     * 属于哪个job组
     */
    private String jobGroup;

    /**
     * job名称
     */
    private String jobName;

    /**
     * job状态
     */
    private JobStatus jobStatus = JobStatus.NEW;

    /**
     * 当前已发送异常次数
     */
    private long exceptionContineTimes;

    /**
     * 允许重试次数
     */
    private long allowExceptionContineTimes = Long.MAX_VALUE;

    /**
     * 异常延时休眠时间
     */
    private long exceptionSleepTime = 5 * 1000;

    /**
     * 启动等待时间
     */
    private long delayTime = 10 * 1000;

    /**
     * 每读取一次休眠时间
     */
    private long workSleepTime = 0;

    /**
     * 无数据时，读取一次源休眠时间
     */
    private long sourceBlankSleepTime = 10L;


    private long createTime = System.currentTimeMillis();

    public boolean isRunnable() {
        return (System.currentTimeMillis() - this.createTime) > this.delayTime;
    }

    /**
     * 异常次数加一
     */
    public void exceptionContineTimesAdd() {
        this.exceptionContineTimes++;
    }

    /**
     * 是否达到异常允许次数上线
     */
    public boolean isAllowException() {
        return this.allowExceptionContineTimes >= this.allowExceptionContineTimes;
    }

}
