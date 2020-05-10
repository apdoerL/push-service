package org.apdoer.push.service.source.serialization.impl;


import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.source.serialization.Deserializer;

/**
 * zmq 反序列化器
 * @author apdoer
 */
public interface ZmqDeserializer extends Deserializer<String, Payload> {
}
