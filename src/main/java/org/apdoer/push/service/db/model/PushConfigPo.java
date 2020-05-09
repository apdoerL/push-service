package org.apdoer.push.service.db.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 推送配置
 * @author apdoer
 */
@Table(name = "web_push_config")
public class PushConfigPo implements Serializable {
	
	private static final long serialVersionUID = 3139778626974246718L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 主题名称
     */
    private String topic;

    /**
     * 发送消息渠道列表，目前支持：phone,email,websocket
     */
    private String pushChannels;

    /**
     * 内部传输通道名称
     */
    private String systemChannel;

    /**
     * 是否需要鉴权，0：需要鉴权，2：不需要
     */
    private Integer isAuth;

    /**
     * 优先等级，值越大，越优先
     */
    private Integer priority;

    /**
     * 推送方式，0：广播，1：单播放
     */
    private Integer pushWay;
    
    /**
     * 是否启用，0：启用，1：不启用
     */
    private Integer enabled;
    
    /**
     * 创建时间
     */
    private Date createTime;


}