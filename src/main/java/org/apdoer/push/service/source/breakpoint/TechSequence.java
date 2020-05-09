package org.apdoer.push.service.source.breakpoint;

import lombok.Data;

/**
 * 断点类
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 9:50
 */
@Data
public class TechSequence {
    private String host;
    private String topic;
    private int partition;
    private long offset;

}
