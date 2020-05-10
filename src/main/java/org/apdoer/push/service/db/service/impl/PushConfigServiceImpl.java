package org.apdoer.push.service.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.db.mapper.PushConfigMapper;
import org.apdoer.push.service.db.model.PushConfigPo;
import org.apdoer.push.service.db.service.PushConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author apdoer
 */
@Component
@Slf4j
public class PushConfigServiceImpl implements PushConfigService {

	@Autowired
	private PushConfigMapper pushConfigMapper;

	@Override
	public List<PushConfigPo> queryAllPushConfig() {
		try {
			return this.pushConfigMapper.queryAllPushConfig();
		} catch (Exception e) {
			log.error("Query push config exception!",e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> queryAllSystemChannel() {
		try {
			return this.pushConfigMapper.queryAllSystemChannel();
		} catch (Exception e) {
			log.error("Query push config exception!",e);
			return new ArrayList<>();
		}
	}
}
