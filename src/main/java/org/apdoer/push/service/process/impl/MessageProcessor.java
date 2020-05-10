package org.apdoer.push.service.process.impl;

import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.payload.ProcessPayload;
import org.apdoer.push.service.process.Processor;
import org.apdoer.push.service.process.assemble.Assembler;
import org.apdoer.push.service.process.assemble.impl.MessageAssembler;
import org.apdoer.push.service.process.cleaner.Cleaner;
import org.apdoer.push.service.process.cleaner.impl.MessageTopicCleaner;
import org.apdoer.push.service.process.filter.Filter;
import org.apdoer.push.service.process.filter.impl.MessageColumnFilter;
import org.apdoer.push.service.process.filter.impl.MessageTimeFilter;
import org.apdoer.push.service.service.PushConfigCenterSerivce;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 11:43
 */
public class MessageProcessor implements Processor<Payload, ProcessPayload> {

    private List<Cleaner<Payload, Payload>> cleanerList;
    private List<Filter<Payload, Payload>> filterList;
    private Assembler<Payload, ProcessPayload> assembler;


    public MessageProcessor(PushConfigCenterSerivce pushConfigCenterSerivce) {
        this.cleanerList = new ArrayList<>();
        Cleaner<Payload, Payload> messageTopicCleaner = new MessageTopicCleaner(pushConfigCenterSerivce);
        this.cleanerList.add(messageTopicCleaner);

        this.filterList = new ArrayList<>();
        Filter<Payload, Payload> columnFilter = new MessageColumnFilter(pushConfigCenterSerivce);
        Filter<Payload, Payload> timeFilter = new MessageTimeFilter(pushConfigCenterSerivce);
        filterList.add(columnFilter);

        this.assembler = new MessageAssembler(pushConfigCenterSerivce);

    }


    @Override
    public ProcessPayload process(Payload source) {
        Payload payload = source;
        for (Cleaner<Payload, Payload> cleaner : this.cleanerList) {
            payload = cleaner.clean(source);
        }
        for (Filter<Payload, Payload> filter : this.filterList) {
            payload = filter.filter(payload);
        }
        return this.assembler.assemble(payload);
    }
}
