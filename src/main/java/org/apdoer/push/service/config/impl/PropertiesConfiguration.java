package org.apdoer.push.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.apdoer.push.service.config.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 配置实现
 *
 * @author apdoer
 */
@Slf4j
public class PropertiesConfiguration implements Configuration<String, Object> {
    private final Properties properties;

    public PropertiesConfiguration() {
        this.properties = new Properties();
    }

    public PropertiesConfiguration(Properties properties) {
        this.properties = properties;
    }

    public PropertiesConfiguration(String pathname) {
        this.properties = this.loadconf(pathname);

    }


    private Properties loadconf(String pathname) {
        Properties properties = new Properties();
        File file = new File(pathname);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("File not exists!");
        }
        try {
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            log.error("Properties config load error", e);
        }
        return properties;
    }

    @Override
    public Object get(String key) {
        return this.properties.get(key);
    }

    @Override
    public void set(String key, Object value) {
        this.properties.setProperty(key, String.valueOf(value));
    }

    @Override
    public void setAll(Map<String, Object> map) {
        this.properties.putAll(map);
    }

    @Override
    public void setAll(Configuration<String, Object> configuration) {
        this.properties.putAll(configuration.elements());
    }

    @Override
    public Map<String, Object> elements() {
        Map<String, Object> map = new HashMap<>();
        Iterator<Object> iterator = this.properties.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            map.put(key, this.properties.get(key));
        }
        return map;
    }
}
