//package com.library.gateway.security;
//
//import com.library.gateway.configs.GatewaySecurityProperties;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
//
//    public static final String USER_ID_HEADER = "X-User-Id";
//    public static final String USERNAME_HEADER = "X-Username";
//    public static final String ROLES_HEADER = "X-User-Roles";
//
//    private static final AntPathMatcher MATCHER = new AntPathMatcher();
//    private static final String BEARER_PREFIX = "Bearer ";
//
//    private final JwtUtils jwtUtils;
//    private final GatewaySecurityProperties props;
//
//    public JwtAuthenticationFilter(JwtUtils jwtUtils, GatewaySecurityProperties props) {
//        this.jwtUtils = jwtUtils;
//        this.props = props;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        ServerHttpRequest.Builder mutated = request.mutate().headers(h -> {
//            h.remove(USER_ID_HEADER);
//            h.remove(USERNAME_HEADER);
//            h.remove(ROLES_HEADER);
//        });
//
//        if (HttpMethod.OPTIONS.equals(request.getMethod()) || isPublic(path)) {
//            return chain.filter(exchange.mutate().request(mutated.build()).build());
//        }
//
//        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
//            return reject(exchange, HttpStatus.UNAUTHORIZED, "Missing bearer token");
//        }
//
//        Claims claims;
//        try {
//            claims = jwtUtils.parseClaims(authHeader.substring(BEARER_PREFIX.length()));
//        } catch (ExpiredJwtException e) {
//            return reject(exchange, HttpStatus.UNAUTHORIZED, "Token expired");
//        } catch (JwtException | IllegalArgumentException e) {
//            return reject(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
//        }
//
//        List<String> roles = jwtUtils.getRoles(claims);
//        if (!hasRequiredRole(path, roles)) {
//            return reject(exchange, HttpStatus.FORBIDDEN, "Insufficient permissions");
//        }
//
//        Object userId = claims.get("userId");
//        if (userId != null) {
//            mutated.header(USER_ID_HEADER, String.valueOf(userId));
//        }
//        mutated.header(USERNAME_HEADER, claims.getSubject());
//        mutated.header(ROLES_HEADER, String.join(",", roles));
//
//        return chain.filter(exchange.mutate().request(mutated.build()).build());
//    }
//
//    private boolean isPublic(String path) {
//        return props.publicPaths().stream().anyMatch(p -> MATCHER.match(p, path));
//    }
//
//    private boolean hasRequiredRole(String path, List<String> userRoles) {
//        return props.roleRules().stream()
//                .filter(rule -> MATCHER.match(rule.path(), path))
//                .allMatch(rule -> rule.roles().stream().anyMatch(userRoles::contains));
//    }
//
//    private Mono<Void> reject(ServerWebExchange exchange, HttpStatus status, String message) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(status);
//        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        if (status == HttpStatus.UNAUTHORIZED) {
//            response.getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
//        }
//        String body = "{\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}"
//                .formatted(status.value(), status.getReasonPhrase(), message);
//        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
//        return response.writeWith(Mono.just(buffer));
//    }
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//}
