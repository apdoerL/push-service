package org.apdoer.push.service.constants;

/**
 * @author apdoer
 */
public class PushConstants {
    public static final String ZH_CN = "zh_CN";// 默认语言

    //用于获取消息中的用户Id
    public static final String ACCOUNT_ID_KEY = "account_id";
    //用于获取消息中的id
    public static final String ID_KEY = "id";
    //用于获取消息中邮箱地址
    public static final String EMAIL_KEY = "email"; // 邮箱
    //用于获取消息中手机号
    public static final String PHONE_KEY = "phone";    //手机号码

    public static final String JOB_NAME_PRE_KEY = "source_jobname_pre_key";
    //数据源路径
    public static final String SOURCE_PATH_KEY = "sourcePath";
    //数据源类型
    public static final String SOURCE_TYPE_KEY = "source.type";
    //url
    public static final String SOURCE_URL_KEY = "source.url";
    //数据主题
    public static final String SOURCE_TOPIC_KEY = "source.topic";
    //数据源class
    public static final String SOURCE_CLASS_KEY = "source.class";
    //数据反反序列class
    public static final String SOURCE_DESERIALIZER_CLASS_KEY = "source.deserializer.class";

}
