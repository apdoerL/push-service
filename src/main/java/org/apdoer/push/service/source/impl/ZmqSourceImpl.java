package org.apdoer.push.service.source.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.config.Configuration;
import org.apdoer.push.service.constants.PushConstants;
import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.source.SourceType;
import org.apdoer.push.service.source.ZmqSource;
import org.apdoer.push.service.source.serialization.impl.ZmqDeserializer;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

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
     * 配置
     */
    protected Configuration<String, Object> config;

    /**
     * 反序列化器
     */
    protected ZmqDeserializer deserializer;


    /**
     * 通过反射构造数据源对应的构造器一定要存在
     * @param config
     * @param deserializer
     */
    public ZmqSourceImpl(Configuration<String, Object> config, ZmqDeserializer deserializer) {
        this.config = config;
        this.deserializer = deserializer;
    }

    @Override
    public void run() {
        while (this.runFlag) {
            byte[] recv = socket.recv(0);
            if (null != recv) {
                String json = new String(recv, StandardCharsets.UTF_8);
                if (this.deserializer.isPush(json)) {
                    this.queue.add(json);
                }
            }
        }
    }

    @Override
    public void init() {
        if (!this.checkParamsIsLagel(this.config)) {
            throw new RuntimeException("Zmq Config error");
        }
        //初始化空间 无界队列
        this.queue = new LinkedBlockingDeque<>();
    }

    private boolean checkParamsIsLagel(Configuration<String, Object> config) {
        if (null == config) {
            log.error("Config is null");
            return false;
        }
        if (null == config.get(PushConstants.SOURCE_URL_KEY)) {
            log.error("Config url is null");
            return false;
        }
        return true;
    }

    @Override
    public void open() {
        String url = this.config.get(PushConstants.SOURCE_URL_KEY).toString();
        this.context = ZMQ.context(1);
        this.socket = context.socket(ZMQ.SUB);
        this.socket.setRcvHWM(0);
        this.socket.setReceiveBufferSize(0);
        this.socket.connect(url);
        this.socket.subscribe("".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Payload read() {
        return this.deserializer.deserialize(this.queue.poll());
    }

    @Override
    public void close() {
        if (null != this.socket) {
            this.socket.close();
        }
        this.runFlag = false;
    }

    @Override
    public void cleanUp() {
        this.queue.clear();
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.ZMQ;
    }
}
