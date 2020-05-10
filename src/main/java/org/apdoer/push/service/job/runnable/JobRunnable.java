package org.apdoer.push.service.job.runnable;


import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.job.Job;
import org.apdoer.push.service.job.JobStatus;

import java.util.concurrent.CountDownLatch;

/**
 * Job runnable
 *
 * @author apdoer
 */
@AllArgsConstructor
@Slf4j
@Setter
public class JobRunnable implements Runnable {
    private Job job;
    private CountDownLatch countDownLatch;

    public JobRunnable(Job job) {
        this.job = job;
    }

    @Override
    public void run() {
        try {
            while (this.job.getJobDescribbution().isRunnable()) {
                Thread.sleep(this.job.getJobDescribbution().getDelayTime());
            }
            this.job.init();
            this.job.start();
            while (this.job.getJobDescribbution().getJobStatus() != JobStatus.STOPED) {
                try {
                    job.dowork();
                    Thread.sleep(this.job.getJobDescribbution().getWorkSleepTime());
                } catch (Exception e) {
                    log.error("Job work exception!", e);
                    this.job.getJobDescribbution().exceptionContineTimesAdd();
                    if (this.job.getJobDescribbution().isAllowException()) {
                        Thread.sleep(this.job.getJobDescribbution().getExceptionSleepTime());
                    } else {
                        this.job.exceptionHandle(e);
                        this.job.getJobDescribbution().setJobStatus(JobStatus.STOPED);
                    }
                }
            }
        } catch (Exception e) {
            this.job.exceptionHandle(e);
        } finally {
            try {
                this.job.shutdown();
                this.job.cleanup();
            } catch (Exception e) {
                log.error("Job shutdown exception!", e);
            }
            if (null != countDownLatch) {
                this.countDownLatch.countDown();
            }
        }
    }
}
