package org.apdoer.push.service.websocket;


import lombok.Data;
import org.apdoer.common.service.model.po.UserPo;
import org.apdoer.push.service.enums.AuthType;

/**
 * client 身份信息
 * @author apdoer
 */
@Data
public class ClientInfo {

    private UserPo userPo;
    private AuthType type;

}
