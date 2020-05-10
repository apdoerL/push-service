package org.apdoer.push.service.db.mapper;


import org.apdoer.common.service.common.BaseMapper;
import org.apdoer.push.service.db.model.PushConfigPo;

import java.util.List;

/**
 * @author apdoer
 */
public interface PushConfigMapper extends BaseMapper<PushConfigPo> {
	
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