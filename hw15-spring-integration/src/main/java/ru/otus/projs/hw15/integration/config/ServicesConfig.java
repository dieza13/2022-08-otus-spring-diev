package ru.otus.projs.hw15.integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "service")
public class ServicesConfig {
    private final Map<String, String> hosts = new HashMap<>();

    public Map<String, String> getHosts() {
        return hosts;
    }

    public String getHost(String service) {
        return hosts.get(service);
    }
}
