package org.apdoer.push.service.job.factory;

import org.apdoer.push.service.config.Configuration;
import org.apdoer.push.service.job.Job;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.source.Source;

/**
 * job任务创建接口
 *
 * @author apdoer
 */
public interface JobFactory<K, V> {

    /**
     * 根据 配置文件,和处理器构建job,数据源由配置文件通过反射生成
     *
     * @param configuration
     * @param processor
     * @return
     */
    Job getInstance(Configuration<K, V> configuration, Processor processor);

    /**
     * 通过数据源和数据处理器构建job
     *
     * @param source    数据源
     * @param processor 数据处理器
     * @return
     */
    Job getInstance(Source source, Processor processor);

}
