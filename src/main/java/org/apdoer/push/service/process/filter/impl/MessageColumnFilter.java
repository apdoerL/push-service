package org.apdoer.push.service.process.filter.impl;

import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.process.filter.Filter;
import org.apdoer.push.service.service.PushConfigCenterSerivce;

/**
 * 根据业务实现
 * @author apdoer
 */
public class MessageColumnFilter implements Filter<Payload, Payload> {
	
	private PushConfigCenterSerivce pushConfigCenterSerivce;
	
	public MessageColumnFilter(PushConfigCenterSerivce pushConfigCenterSerivce) {
		this.pushConfigCenterSerivce = pushConfigCenterSerivce;
	}

	@Override
	public Payload filter(Payload data) {
		return data;
	}

}
