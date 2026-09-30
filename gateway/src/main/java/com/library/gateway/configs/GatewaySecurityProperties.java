package com.library.gateway.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "gateway.security")
public record GatewaySecurityProperties(
        List<String> publicPaths,
        List<RoleRule> roleRules
) {
    public GatewaySecurityProperties {
        publicPaths = publicPaths == null ? List.of() : List.copyOf(publicPaths);
        roleRules = roleRules == null ? List.of() : List.copyOf(roleRules);
    }

    public record RoleRule(String path, List<String> roles) {
        public RoleRule {
            roles = roles == null ? List.of() : List.copyOf(roles);
        }
    }
}
