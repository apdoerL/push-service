package org.apdoer.push.service.job.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apdoer.push.service.event.SourceEvent;
import org.apdoer.push.service.event.impl.EventBusChannelImpl;
import org.apdoer.push.service.event.impl.GuavaEventBusManager;
import org.apdoer.push.service.job.Job;
import org.apdoer.push.service.job.JobDescribution;
import org.apdoer.push.service.job.thread.JobThreadPool;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.payload.ProcessPayload;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.source.Source;
import org.apdoer.push.service.source.SourceType;
import org.apdoer.push.service.source.impl.ZmqSourceImpl;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 12:08
 */
@Slf4j
public class PushSourceJob implements Job {


    private Source<Payload> source;
    private Processor<Payload, ProcessPayload> processor;
    private JobDescribution jobDescribution;

    public PushSourceJob(Source<Payload> source, Processor<Payload, ProcessPayload> processor, JobDescribution jobDescribution) {
        this.source = source;
        this.processor = processor;
        this.jobDescribution = jobDescribution;
    }

    @Override
    public void init() {
        log.info("jobName={}, excute func init start", jobDescribution.getJobName());
        this.source.init();
        log.info("jobName={}, excute func init success", jobDescribution.getJobName());
    }

    @Override
    public void start() throws Exception {
        log.info("jobName={}, excute func start start", this.jobDescribution.getJobName());
        this.source.open();
        if (source.getSourceType().equals(SourceType.ZMQ)) {
            ZmqSourceImpl source = (ZmqSourceImpl) this.source;
            JobThreadPool.getInstance().execute(source);
        }
        //这里可以根据业务自行扩展
        log.info("jobName={}, excute func start success", this.jobDescribution.getJobName());
    }

    @Override
    public void dowork() throws Exception {
        //源端读取数据
        Payload sourcePayload = this.source.read();
        if (Objects.nonNull(sourcePayload)) {
            //数据处理
            ProcessPayload processPayload = processor.process(sourcePayload);
            if (Objects.nonNull(processPayload)
                    && StringUtils.isNotBlank(processPayload.getSystemChannel())
                    && !CollectionUtils.isEmpty(processPayload.getPayload())) {
                //获取数据的内部通道
                EventBusChannelImpl eventBusChannel = (EventBusChannelImpl) GuavaEventBusManager.getInstance().getEventBusChannel(processPayload.getSystemChannel());
                if (Objects.nonNull(eventBusChannel)) {
                    while (eventBusChannel.backPress(1, 3L, TimeUnit.SECONDS)) {
                        //实现反压策略
                    }
                    //数据发送到内部通道
                    eventBusChannel.publish(new SourceEvent(processPayload));
                } else {
                    Thread.sleep(jobDescribution.getSourceBlankSleepTime());
                }

            }
        }

    }

    @Override
    public void shutdown() throws Exception {
        this.source.close();
    }

    @Override
    public void cleanup() {
        this.source.cleanUp();
    }

    @Override
    public void exceptionHandle(Exception e) {
        log.error("job execute error,jobName:{}", this.jobDescribution.getJobName(), e);
    }

    @Override
    public JobDescribution getJobDescribbution() {
        return this.jobDescribution;
    }
}
