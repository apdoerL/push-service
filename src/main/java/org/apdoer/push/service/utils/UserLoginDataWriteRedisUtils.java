package org.apdoer.push.service.utils;

import com.corundumstudio.socketio.transport.NamespaceClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apdoer.common.service.redis.RedisOperator;
import org.apdoer.common.service.util.JsonUtils;
import org.apdoer.push.service.enums.TradeType;
import org.apdoer.push.service.push.session.impl.SessionSocketClient;
import org.apdoer.push.service.websocket.manager.WebsocketClientManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户登录信息写入redis,撮合重启后重redis获取
 *
 * @author apdoer
 */
@Slf4j
public class UserLoginDataWriteRedisUtils {
    public static final String USER_LOGIN_DATA_REDIS_KEY = "logins";

    public static RedisOperator redisOperator;

    public static void writeUserLoginData() {
        try {
            if (Objects.nonNull(redisOperator)) {
                List<UserLoginRedisDto> list = new ArrayList<>();
                for (Map.Entry<String, SessionSocketClient> entry : WebsocketClientManager.getInstance().getClientMap().entrySet()) {
                    SessionSocketClient socketClient = entry.getValue();
                    NamespaceClient tClient = (NamespaceClient) socketClient.getClient();
                    if (tClient.getBaseClient().isConnected()) {
                        if (null != socketClient.getClientInfo() && null != socketClient.getClientInfo().getUserPo()) {
                            UserLoginRedisDto dto = new UserLoginRedisDto();
                            dto.setAccountId(socketClient.getClientInfo().getUserPo().getId());
                            dto.setApplId(TradeType.TRADE_FUTURE.getCode());
                            list.add(dto);
                        }
                    }
                }
                UserLoginRedisWrapDto dto = new UserLoginRedisWrapDto();
                dto.setLogins(list);
                String userLoginData = JsonUtils.toJson(dto);
                redisOperator.set(USER_LOGIN_DATA_REDIS_KEY, userLoginData);
                list.clear();
            } else {
                log.error("Write user login data to redis error, redisOperatorService is blank");
            }
        } catch (Exception e) {
            log.error("Write user login data to redis error", e);
        }
    }

    /**
     * 用户登录信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UserLoginRedisDto implements Serializable {
        private static final long serialVersionUID = -1148517087445598709L;
        private Integer applId;
        private Integer accountId;
    }

    /**
     * 用户登录信息，撮合重启时推送服务写入redis
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UserLoginRedisWrapDto {
        private List<UserLoginRedisDto> logins;
    }
}
