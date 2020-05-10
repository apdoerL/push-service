package org.apdoer.push.service.process.assemble.impl;


import io.micrometer.core.instrument.util.StringUtils;
import org.apdoer.push.service.db.model.PushConfigPo;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.payload.ProcessPayload;
import org.apdoer.push.service.process.assemble.Assembler;
import org.apdoer.push.service.service.PushConfigCenterSerivce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据组装实现
 *
 * @author apdoer
 */
public class MessageAssembler implements Assembler<Payload, ProcessPayload> {

    private PushConfigCenterSerivce pushConfigCenterSerivce;

    public MessageAssembler(PushConfigCenterSerivce pushConfigCenterSerivce) {
        this.pushConfigCenterSerivce = pushConfigCenterSerivce;
    }

    @Override
    public ProcessPayload assemble(Payload data) {
        return this.buildProcessPayload(data);
    }

    private ProcessPayload buildProcessPayload(Payload data) {
        ProcessPayload payload = null;
        if (null == data) {
            return null;
        }
        PushConfigPo configPo = pushConfigCenterSerivce.getConfigByTopic(data.getTopic());
        if (null != configPo) {
            payload = new ProcessPayload();
            payload.setTopic(configPo.getTopic());
            payload.setPushChannels(this.buildChannelList(configPo.getPushChannels()));
            payload.setSystemChannel(configPo.getSystemChannel());
            payload.setIsAuth(configPo.getIsAuth());
            payload.setPushWay(configPo.getPushWay());
            payload.setPriority(configPo.getPriority());
            payload.setPayload(data.getData());
        }
        return payload;
    }

    private List<String> buildChannelList(String pushChannels) {
        if (StringUtils.isBlank(pushChannels)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(pushChannels.split("[,]"));
    }

}
