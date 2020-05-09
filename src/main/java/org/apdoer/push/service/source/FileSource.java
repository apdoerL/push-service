package org.apdoer.push.service.source;

import org.apdoer.push.service.source.breakpoint.BreakPoint;
import org.apdoer.push.service.source.breakpoint.TechSequence;

/**
 * 文件数据源
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 9:53
 */
public interface FileSource<T> extends Source<T>, BreakPoint<TechSequence> {
}
