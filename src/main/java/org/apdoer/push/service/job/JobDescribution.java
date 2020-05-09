package org.apdoer.push.service.job;


public class JobDescribution {
	
	//job类型
	private JobType jobType;
	
	//属于哪个job组
	private String jobGroup;
	
	//job名称
	private String jobName;
	
	//job状态
	private JobStatus jobStatus = JobStatus.NEW;
	
	//当前已发送异常次数
	private long exceptionContineTimes;
	
	//允许重试次数
	private long allowExceptionContineTimes = Long.MAX_VALUE;
	
	//异常延时休眠时间
	private long exceptionSleepTime = 5*1000;
	
	//启动等待时间
	private long delayTime = 10 * 1000;
	
	//每读取一次休眠时间
	private long workSleepTime = 0;
	
	//无数据时，读取一次源休眠时间
	private long sourceBlankSleepTime = 10L;
	
	
	private long createTime = System.currentTimeMillis();
	
	public boolean isRunnable() {
		return (System.currentTimeMillis() - this.createTime) > this.delayTime;
	}
	
	/**
	 * 异常次数加一
	 */
	public void exceptionContineTimesAdd() {
		this.exceptionContineTimes ++;
	}
	
	/**
	 * 是否达到异常允许次数上线
	 */
	public boolean isAllowException() {
		return this.allowExceptionContineTimes >= this.allowExceptionContineTimes;
	}
	
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public JobStatus getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	public JobType getJobType() {
		return jobType;
	}
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	public long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}
	public long getAllowExceptionContineTimes() {
		return allowExceptionContineTimes;
	}
	public void setAllowExceptionContineTimes(long allowExceptionContineTimes) {
		this.allowExceptionContineTimes = allowExceptionContineTimes;
	}
	public long getExceptionSleepTime() {
		return exceptionSleepTime;
	}
	public void setExceptionSleepTime(long exceptionSleepTime) {
		this.exceptionSleepTime = exceptionSleepTime;
	}
	public long getExceptionContineTimes() {
		return exceptionContineTimes;
	}
	public void setExceptionContineTimes(long exceptionContineTimes) {
		this.exceptionContineTimes = exceptionContineTimes;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getWorkSleepTime() {
		return workSleepTime;
	}
	public void setWorkSleepTime(long workSleepTime) {
		this.workSleepTime = workSleepTime;
	}
	public long getSourceBlankSleepTime() {
		return sourceBlankSleepTime;
	}
	public void setSourceBlankSleepTime(long sourceBlankSleepTime) {
		this.sourceBlankSleepTime = sourceBlankSleepTime;
	}
}
