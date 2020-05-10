package org.apdoer.push.service.job.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apdoer.common.service.redis.RedisOperator;
import org.apdoer.common.service.util.SpringBeanUtils;
import org.apdoer.push.service.config.Configuration;
import org.apdoer.push.service.config.impl.PropertiesConfiguration;
import org.apdoer.push.service.constants.PushConstants;
import org.apdoer.push.service.job.Job;
import org.apdoer.push.service.job.factory.impl.SourceJobFactory;
import org.apdoer.push.service.job.runnable.JobRunnable;
import org.apdoer.push.service.job.thread.JobThreadPool;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.payload.ProcessPayload;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.process.impl.MessageProcessor;
import org.apdoer.push.service.service.PushConfigCenterSerivce;
import org.apdoer.push.service.source.Source;
import org.apdoer.push.service.source.impl.HttpSourceImpl;
import org.apdoer.push.service.utils.UserLoginDataWriteRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * job管理类
 *
 * @author apdoer
 */
@Component
@Slf4j
public class SourceJobManager extends AbstractJobManager {

    @Autowired
    private SpringBeanUtils springBeanUtils;

    @Autowired
    private PushConfigCenterSerivce pushConfigCenterSerivce;
    //    public static FutureContractService futureContractService;
//    public static TickService tickService;
//    public static CommonService commonService;
    @Autowired
    private RedisOperator redisOperator;

    private Map<String, Job> jobMap = new ConcurrentHashMap<>();
    private Map<String, Runnable> runnableMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void init() {
        this.restartHandle();
        this.pushConfigCenterSerivce.reflushPushSystemChannelConfig();
        this.pushConfigCenterSerivce.reflushPushTopicConfig();
        for (Job job : this.getJobList()) {
            jobMap.put(job.getJobDescribbution().getJobName(), job);
            Runnable runnable = new JobRunnable(job);
            runnableMap.put(job.getJobDescribbution().getJobName(), runnable);
            JobThreadPool.getInstance().execute(runnable);
        }
    }

    private void restartHandle() {
        UserLoginDataWriteRedisUtils.redisOperator = redisOperator;
//        TickBufferUtils.futureContractService = this.futureContractService;
//        TickBufferUtils.tickService = this.tickService;
//        TickBufferUtils.commonService = commonService;
    }


    @Override
    protected List<Job> getJobList() {
        log.info("build job start");
        List<Job> jobList = new ArrayList<>();
        String sourcePath = System.getProperty(PushConstants.SOURCE_PATH_KEY);
        List<Configuration<String, Object>> configurationList = this.getSourceConfiguration(sourcePath);
        if (!CollectionUtils.isEmpty(configurationList)) {
            for (Configuration<String, Object> configuration : configurationList) {
                Processor<Payload, ProcessPayload> processor = new MessageProcessor(pushConfigCenterSerivce);
                Job job = SourceJobFactory.instance().getInstance(configuration, processor);
                if (null != job) {
                    jobList.add(job);
                    log.info("build job success,{}", job.getJobDescribbution().getJobName());
                }
            }
        }
        jobList.add(this.buildHttpSourceJob());
        log.info("build job end jobSize={}", jobList.size());
        return jobList;
    }

    private List<Configuration<String, Object>> getSourceConfiguration(String sourcePath) {
        if (StringUtils.isBlank(sourcePath)) {
            log.error("");
            return null;
        }
        List<Configuration<String, Object>> configurationList = new ArrayList<>();
        File sourceFileRoot = new File(sourcePath);
        if (sourceFileRoot.exists() && sourceFileRoot.isDirectory()) {
            File[] files = sourceFileRoot.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()) {
                    Configuration<String, Object> config = new PropertiesConfiguration(file.getAbsolutePath());
                    config.set(PushConstants.JOB_NAME_PRE_KEY, file.getName());
                    configurationList.add(config);
                }
            }
        }
        return configurationList;
    }

    private Job buildHttpSourceJob() {
        Source<Payload> httpSource = new HttpSourceImpl();
        Processor<Payload, ProcessPayload> processor = new MessageProcessor(pushConfigCenterSerivce);
        return SourceJobFactory.instance().getInstance(httpSource, processor);
    }
}
