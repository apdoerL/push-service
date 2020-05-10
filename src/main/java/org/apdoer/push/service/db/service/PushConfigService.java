package org.apdoer.push.service.db.service;

import org.apdoer.push.service.db.model.PushConfigPo;

import java.util.List;


/**
 * @author apdoer
 */
public interface PushConfigService {
	
	/**
	 * 获取所有push 配置
	 * @return
	 */
	List<PushConfigPo> queryAllPushConfig();
	
	/**
	 * 获取所有通道
	 * @return
	 */
	List<String> queryAllSystemChannel();

}
