package org.apdoer.push.service.job.manager;

import org.apdoer.push.service.config.Configuration;
import org.apdoer.push.service.job.Job;

import java.util.List;

/**
 * 任务管理器,基类
 *
 * @author apdoer
 */
public abstract class AbstractJobManager {


    protected Configuration<String, Object> configuration;

    /**
     * 构造job
     *
     * @return
     */
    protected abstract List<Job> getJobList();
}
