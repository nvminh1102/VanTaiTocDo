/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 *
 * @author Vu Van Lich
 */
public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String value = super.resolvePlaceholder(placeholder, props);
        return value == null ? null : value.trim();
    }

    /**
     * Map that hold all the properties.
     */
    private Map<String, String> propertiesMap;

    /**
     * Iterate through all the Propery keys and build a Map, resolve all the
     * nested values before beuilding the map.
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);

        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String valueStr = props.getProperty(keyStr);
            propertiesMap.put(keyStr.trim(), valueStr.trim());
        }
    }

    public String getProperty(String name) {
        return propertiesMap.get(name).toString();
    }

}
