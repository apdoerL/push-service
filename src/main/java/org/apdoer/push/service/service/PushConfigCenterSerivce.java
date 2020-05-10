package org.apdoer.push.service.service;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.db.model.PushConfigPo;
import org.apdoer.push.service.db.service.PushConfigService;
import org.apdoer.push.service.event.impl.GuavaEventBusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 推送系统 内部配置管理
 *
 * @author apdoer
 */
@Service
@Slf4j
public class PushConfigCenterSerivce {
    @Autowired
    private PushConfigService pushConfigService;

    private Map<String, PushConfigPo> pushConfigMap;

    private ReentrantLock lock = new ReentrantLock();

    public PushConfigPo getConfigByTopic(String topic) {
        try {
            lock.lock();
            PushConfigPo config = null;
            if (null != this.pushConfigMap && this.pushConfigMap.containsKey(topic)) {
                config = this.pushConfigMap.get(topic);
            }
            return config;
        } finally {
            lock.unlock();
        }
    }


    public Map<String, PushConfigPo> getPushConfigMap() {

        try {
            lock.lock();
            if (null != this.pushConfigMap) {
                this.reflushPushTopicConfig();
            }
            return this.pushConfigMap;
        } finally {
            lock.unlock();
        }
    }

    public void reflushPushTopicConfig() {
        Map<String, PushConfigPo> configMap = new ConcurrentHashMap<>();
        List<PushConfigPo> pushConfigList = this.pushConfigService.queryAllPushConfig();
        if (!CollectionUtils.isEmpty(pushConfigList)) {
            for (PushConfigPo temp : pushConfigList) {
                if (!configMap.containsKey(temp.getTopic())) {
                    configMap.put(temp.getTopic(), temp);
                }
            }
        }
        try {
            lock.lock();
            //刷新配置
            this.pushConfigMap = configMap;
            log.info("Reflush TopicConfig success");
        } finally {
            lock.unlock();
        }
    }


    public boolean containsKey(String topic) {
        if (null == this.pushConfigMap) {
            return false;
        }
        try {
            lock.lock();
            return this.pushConfigMap.containsKey(topic);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 定时加载配置,分布式问题不影响最终结果
     */
    @Scheduled(fixedDelay = 600000)
    public void configUpdateScheduleTrigger() {
        this.reflushPushSystemChannelConfig();
        this.reflushPushTopicConfig();
    }

    /**
     * 刷新内部systemChannel
     */
    public void reflushPushSystemChannelConfig() {
        List<String> systemChannels = this.pushConfigService.queryAllSystemChannel();
        if (!CollectionUtils.isEmpty(systemChannels)) {
            for (String systemChannel : systemChannels) {
                if (!GuavaEventBusManager.getInstance().containsChannel(systemChannel)) {
                    GuavaEventBusManager.getInstance().buildEventBusChannel(systemChannel);
                    log.info("ReflushSystemChannel add systemChannel={}", systemChannel);
                }
            }
        }

    }
}

