package org.apdoer.push.service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 敏感事件
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 14:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceEvent {

    private Object payload;
}
