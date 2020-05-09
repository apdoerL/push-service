package org.apdoer.push.service.source.impl;

import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.source.HttpSource;
import org.apdoer.push.service.source.SourceType;
import org.apdoer.push.service.source.queue.HttpPayloadQueue;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 10:19
 */
public class HttpSourceImpl implements HttpSource<Payload> {

    private HttpPayloadQueue queue;

    @Override
    public void init() {
        this.queue = HttpPayloadQueue.getInstance();
    }

    @Override
    public void open() {

    }

    @Override
    public Payload read() {
        return this.queue.poll();
    }

    @Override
    public void close() {

    }

    @Override
    public void cleanUp() {

    }

    @Override
    public SourceType getSourceType() {
        return SourceType.HTTP;
    }
}
