package org.apdoer.push.service.job.factory.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.config.Configuration;
import org.apdoer.push.service.constants.PushConstants;
import org.apdoer.push.service.job.Job;
import org.apdoer.push.service.job.JobDescribution;
import org.apdoer.push.service.job.JobType;
import org.apdoer.push.service.job.factory.JobFactory;
import org.apdoer.push.service.job.impl.PushSourceJob;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.source.Source;
import org.apdoer.push.service.source.SourceType;
import org.apdoer.push.service.source.serialization.impl.ZmqDeserializer;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * job 创建实现
 *
 * @author apdoer
 */
@Slf4j
public class SourceJobFactory implements JobFactory<String, Object> {

    private SourceJobFactory() {
    }

    private static class InnerSourceJobFactory {
        private static final SourceJobFactory INSTANCE = new SourceJobFactory();
    }

    public static final SourceJobFactory instance() {
        return InnerSourceJobFactory.INSTANCE;
    }


    @Override
    public Job getInstance(Configuration<String, Object> configuration, Processor processor) {
        try {
            String sourceFileName = configuration.get(PushConstants.JOB_NAME_PRE_KEY).toString();
            Object sourceType = configuration.get(PushConstants.SOURCE_TYPE_KEY);
            if (Objects.isNull(sourceType) || !SourceType.vaild(sourceType.toString())) {
                log.error("{},{},sourcefile is blank or sourceType invalid", sourceFileName, sourceType);
                return null;
            }
            Object deserializerStr = configuration.get(PushConstants.SOURCE_DESERIALIZER_CLASS_KEY);
            if (Objects.isNull(deserializerStr)) {
                log.error("{},{} is blank", sourceFileName, PushConstants.SOURCE_DESERIALIZER_CLASS_KEY);
                return null;
            }
            if (SourceType.ZMQ.name().equalsIgnoreCase(sourceType.toString())) {
                return this.buildZmqJob(processor, configuration, deserializerStr.toString(), sourceType.toString());
            } else {
                // 自行扩展其他数据源任务
                return null;
            }
        } catch (Exception e) {
            log.error("create job error", e);
            return null;
        }
    }

    private Job buildZmqJob(Processor processor, Configuration<String, Object> configuration,
                            String deserializerClassStr, String sourceType) {
        try {
            ZmqDeserializer zmqDeserializer = this.buildZmqDeserializer(deserializerClassStr);
            Object sourceClassObject = configuration.get(PushConstants.SOURCE_CLASS_KEY);
            if (Objects.isNull(sourceClassObject)) {
                log.error("{} is blank", PushConstants.SOURCE_CLASS_KEY);
                return null;
            }
            Source<Payload> source = this.buildZmqSource(sourceClassObject.toString(), configuration, zmqDeserializer);
            String jobName = sourceType + "_" + configuration.get(PushConstants.JOB_NAME_PRE_KEY).toString();
            JobDescribution jobDescribution = this.bulidJobDesc(jobName, "ZMQ_JOB_GROUP");
            return new PushSourceJob(source, processor, jobDescribution);
        } catch (Exception e) {
            log.error("create zmq job error", e);
            return null;
        }
    }

    private Source<Payload> buildZmqSource(String sourceClassName, Configuration<String, Object> configuration,
                                           ZmqDeserializer zmqDeserializer) throws Exception{
        Class<?> clazz = Class.forName(sourceClassName);
        Class<?>[] paramTpyes = {Configuration.class,ZmqDeserializer.class};
        Object[] initArgs ={configuration,zmqDeserializer};
        Constructor<?> constructor = clazz.getConstructor(paramTpyes);
        return ((Source<Payload>) constructor.newInstance(initArgs));
    }

    private ZmqDeserializer buildZmqDeserializer(String deserializerClassStr) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return ((ZmqDeserializer) Class.forName(deserializerClassStr).newInstance());
    }

    private JobDescribution bulidJobDesc(String jobName, String jobGroup) {
        JobDescribution jobDescribution = new JobDescribution();
        jobDescribution.setJobType(JobType.BUSINESS_JOB);
        jobDescribution.setJobName(jobName);
        jobDescribution.setJobGroup(jobGroup);
        return jobDescribution;
    }

    @Override
    public Job getInstance(Source source, Processor processor) {
        JobDescribution jobDescribution = bulidJobDesc("HTTP_SOURCE_JOB", "HTTP_JOB_GROUP");
        return new PushSourceJob(source, processor, jobDescribution);
    }
}
