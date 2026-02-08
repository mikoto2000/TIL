package dev.mikoto2000.study.springboot.logging.firststep.filter;

import static net.logstash.logback.argument.StructuredArguments.*;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class AccessLogFilter extends OncePerRequestFilter {

    private static final Logger accessLogger = LoggerFactory.getLogger("ACCESS_LOG");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        // traceId 発行
        String traceId = UUID.randomUUID().toString();

        // user（Security連携）
        // String user = getUser();

        // IP
        String ip = request.getRemoteAddr();

        // MDCセット
        MDC.put("traceId", traceId);
        // MDC.put("user", user);
        MDC.put("ip", ip);

        boolean success = false;

        try {
            chain.doFilter(request, response);
            success = true;
        } finally {

            long time = System.currentTimeMillis() - start;

            accessLogger.info("ACCESS",
                kv("method", request.getMethod()),
                kv("request_uri", request.getRequestURI()),
                kv("status", response.getStatus()),
                kv("success", success ? "SUCCESS" : "FAIL"),
                kv("time", time),
                value("event", "access")
                );

            // ★必須
            MDC.clear();
        }
    }

//    private String getUser() {
//        Authentication auth =
//            SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || !auth.isAuthenticated()
//            || "anonymousUser".equals(auth.getName())) {
//            return "ANONYMOUS";
//        }
//        return auth.getName();
//    }
}

