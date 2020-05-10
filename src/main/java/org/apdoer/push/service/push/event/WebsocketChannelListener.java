package org.apdoer.push.service.push.event;

import org.apdoer.push.service.event.SourceEventListener;
import org.apdoer.push.service.push.session.impl.SessionSocketClient;

/**
 * @author apdoer
 */
public interface WebsocketChannelListener extends SourceEventListener {
	
	/**
	 * 获取客户端
	 * @return
	 */
	SessionSocketClient getClient();
	
	/**
	 * 设置客户端
	 * @param client
	 */
	void setClient(SessionSocketClient client);

}
