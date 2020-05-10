package org.apdoer.push.service.event.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.event.EventBusChannel;
import org.apdoer.push.service.event.EventBusManager;
import org.apdoer.push.service.event.SourceEvent;
import org.apdoer.push.service.event.SourceEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内部数据通道管理器
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 15:06
 */
@Slf4j
public class GuavaEventBusManager implements EventBusManager {

    private ReentrantLock lock = new ReentrantLock();
    private Map<String, EventBusChannel> eventBusChannelMap = new ConcurrentHashMap<>();


    private GuavaEventBusManager() {
    }

    private static class InnerGuavaEventBusManager {
        private static final GuavaEventBusManager INSTANCE = new GuavaEventBusManager();
    }

    public static final GuavaEventBusManager getInstance() {
        return InnerGuavaEventBusManager.INSTANCE;
    }


    @Override
    public void subscribe(SourceEventListener listener) {
        for (Map.Entry<String, EventBusChannel> eventBusChannelEntry : eventBusChannelMap.entrySet()) {
            eventBusChannelEntry.getValue().subscribe(listener);
        }
    }

    @Override
    public void subscribe(SourceEventListener listener, String systemChannel) {
        if (eventBusChannelMap.containsKey(systemChannel)) {
            eventBusChannelMap.get(systemChannel).subscribe(listener);
        }
    }

    @Override
    public void unSubscribe(SourceEventListener listener) {
        for (Map.Entry<String, EventBusChannel> eventBusChannelEntry : eventBusChannelMap.entrySet()) {
            eventBusChannelEntry.getValue().unSubscribe(listener);
        }
    }

    @Override
    public void unSubscribe(SourceEventListener listener, String systemChannel) {
        try {
            if (eventBusChannelMap.containsKey(systemChannel)) {
                eventBusChannelMap.get(systemChannel).unSubscribe(listener);
            }
        } catch (Exception e) {
            log.error("unsubscribe error,systemChannel:{},", systemChannel);
        }
    }

    @Override
    public void publish(String systemChannel, SourceEvent event) {
        if (eventBusChannelMap.containsKey(systemChannel)) {
            eventBusChannelMap.get(systemChannel).publish(event);
        }
    }

    @Override
    public void buildEventBusChannel(String systemChannel) {
        try {
            lock.lock();
            if (!eventBusChannelMap.containsKey(systemChannel)) {
                new EventBusChannelImpl(systemChannel);
            }
        } catch (Exception e) {
            log.error("build eventbusChannel error", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void buildEventBusChannel(String systemChannel, int backPressureSize) {
        try {
            lock.lock();
            if (!eventBusChannelMap.containsKey(systemChannel)) {
                EventBusChannel channel = new EventBusChannelImpl(backPressureSize, systemChannel);
                eventBusChannelMap.put(systemChannel, channel);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public EventBusChannel getEventBusChannel(String systemChannel) {
        if (eventBusChannelMap.containsKey(systemChannel)) {
            return eventBusChannelMap.get(systemChannel);
        }
        return null;
    }

    @Override
    public boolean containsChannel(String systemChannel) {
        return eventBusChannelMap.containsKey(systemChannel);
    }

    @Override
    public Map<String, Integer> getChannelWaitSize() {
        Map<String, Integer> map = new HashMap<>(eventBusChannelMap.size());
        for (Map.Entry<String, EventBusChannel> entry : eventBusChannelMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getQueueSize());
        }
        return map;
    }

    @Override
    public void shutdown() {
        for (Map.Entry<String, EventBusChannel> entry : eventBusChannelMap.entrySet()) {
            entry.getValue().shutdown();
        }
    }
}
