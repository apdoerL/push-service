package org.apdoer.push.service.process.cleaner.impl;

import org.apdoer.push.service.payload.Payload;
import org.apdoer.push.service.process.cleaner.Cleaner;

import java.util.Objects;

/**
 * @author Li
 * @version 1.0
 * @date 2020/5/9 11:46
 */
public class MessageTopicCleaner implements Cleaner<Payload,Payload> {


    @Override
    public Payload clean(Payload source) {
        if (Objects.nonNull(source)){

        }
        return null;
    }
}
