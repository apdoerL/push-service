package org.apdoer.push.service.source;

import org.apache.commons.lang3.StringUtils;

/**
 * @author apdoer
 * @version 1.0
 * @date 2020/5/8 17:03
 */
public enum SourceType {

    /**
     * 数据源类型
     */
    KAFKA,
    ZMQ,
    HTTP,
    DB,
    REDIS,
    CACHE;

    public static Boolean vaild(String o) {
        if (StringUtils.isBlank(o)) {
            return false;
        }
        for (SourceType type : SourceType.values()) {
            if (type.name().equalsIgnoreCase(o)) {
                return true;
            }
        }
        return false;
    }
}
