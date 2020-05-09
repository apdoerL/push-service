package org.apdoer.push.service.process.impl;

import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.payload.ProcessPayload;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.process.cleaner.Cleaner;

import java.util.List;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:43
 */
public class MessageProcessor implements Processor<Payload, ProcessPayload> {

    private List<Cleaner<Payload, Payload>> cleanerList;
    private List<Cleaner<Payload, Payload>> filterList;
    private List<Cleaner<Payload, ProcessPayload>> assembleList;



    @Override
    public ProcessPayload process(Payload source) {
        return null;
    }
}
