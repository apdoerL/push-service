package org.apdoer.push.service.config;

import java.util.Map;

/**
 * 自定义配置类
 *
 * @author apdoer
 */
public interface Configuration<K, V> {

    /**
     * 根据key获取单个配置
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 加入配置
     *
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * 加入一组配置 根据map
     *
     * @param map
     */
    void setAll(Map<K, V> map);

    /**
     * 加入一组配置 根据配置文件
     *
     * @param configuration
     */
    void setAll(Configuration<K, V> configuration);

    /**
     * 设置所有配置(将所有配置装载进map返回)
     *
     * @return
     */
    Map<K, V> elements();
}
