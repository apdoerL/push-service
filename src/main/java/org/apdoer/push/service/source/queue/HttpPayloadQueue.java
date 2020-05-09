package org.apdoer.push.service.source.queue;

import org.apdoer.push.service.payload.Payload;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author http 数据queue 单例
 * @version 1.0
 * @date 2020/5/9 10:09
 */
public class HttpPayloadQueue {


    private static Queue<Payload> queue = new LinkedBlockingDeque<>();

    private HttpPayloadQueue() {
    }

    private static class InnerHttpPayLoadQueue {
        private static final HttpPayloadQueue INSTANCE = new HttpPayloadQueue();
    }

    public static HttpPayloadQueue getInstance() {
        return InnerHttpPayLoadQueue.INSTANCE;
    }

    public void add(Payload payload) {
        queue.add(payload);
    }

    public Payload poll() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

}
