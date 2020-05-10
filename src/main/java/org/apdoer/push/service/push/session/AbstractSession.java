package org.apdoer.push.service.push.session;

import lombok.Data;
import org.apdoer.push.service.push.session.enums.SessionState;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Session 基类
 *
 * @author apdoer
 */
@Data
public abstract class AbstractSession<ID> implements Session<ID> {

    protected SessionState sessionState = SessionState.CONNECTED;

    /**
     * 延时时间
     */
    private long expireTime;

    @Override
    public int compareTo(Delayed o) {
        AbstractSession<ID> session = (AbstractSession<ID>) o;
        return (int) (expireTime - session.getExpireTime());
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return expireTime - System.currentTimeMillis();
    }
}
