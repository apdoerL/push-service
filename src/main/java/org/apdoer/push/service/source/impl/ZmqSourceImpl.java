package org.apdoer.push.service.source.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.source.SourceType;
import org.apdoer.push.service.source.ZmqSource;
import org.apdoer.push.service.source.serialization.Deserializer;
import org.zeromq.ZMQ;

import java.util.Queue;

/**
 * zmq 数据源,其他数据源类似扩展
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/8 18:55
 */
@Slf4j
public class ZmqSourceImpl implements ZmqSource<Payload> {

    protected boolean runFlag = true;
    protected int queueSize;
    protected int releaseSize;

    protected static final int DEFAULT_CAPACITY = 100000;
    protected static final int DEFAULT_RELEASE_SIZE = 20000;

    /**
     * zmq socket & Context
     */
    protected ZMQ.Socket socket;
    protected ZMQ.Context context;

    /**
     * 队列
     */
    protected Queue<String> queue;

    /**
     * 反序列化器
     */
    protected Deserializer deserializer;


    @Override
    public void run() {

    }

    @Override
    public void init() {

    }

    @Override
    public void open() {

    }

    @Override
    public Payload read() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void cleanUp() {

    }

    @Override
    public SourceType getSourceType() {
        return null;
    }
}
