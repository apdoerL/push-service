package org.apdoer.push.service.source;

import org.apdoer.push.service.source.breakpoint.BreakPoint;
import org.apdoer.push.service.source.breakpoint.TechSequence;

/**
 * 数据库数据源
 *
 * @author apdoer
 * @version 1.0
 * @date 2020/5/9 9:55
 */
public interface DbSource<T> extends Source<T>, BreakPoint<TechSequence> {
}
