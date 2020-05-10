package org.apdoer.push.service.push.session.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * websocket client session 状态
 *
 * @author apdoer
 */
@AllArgsConstructor
@Getter
public enum SessionState {


    /**
     * UNCONNECTED  已关闭的链接
     * CONNECTED   新建链接
     * LOGIN   完成登录的链接状态
     * BIND  完成connfig的链接状态
     * ACTIVE  活动的链接状态（有交互）
     * IDEL   发送探测包未回复改为空闲状态
     */
    UNCONNECTED(-1, "连接关闭"),
    CONNECTED(0, "已连接"),
    LOGIN(1, "已鉴权"),
    SUBSCRIBE(2, "已订阅"),
    ACTIVE(3, "活跃种"),
    IDEL(4, "空闲"),
    DISCONNECTING(5, "断开");


    private Integer code;
    private String title;

    public static SessionState getByCode(Integer code) {
        for (SessionState e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static String getTitle(Integer code) {
        SessionState e = getByCode(code);
        return e == null ? null : e.getTitle();
    }

    public static Boolean vaild(Integer field) {
        if (null == getByCode(field)) {
            return false;
        }
        return true;
    }
}
