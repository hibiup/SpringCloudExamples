package pro.wangji.gw;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    /**
     * 路由可以通过配置文件，也可以通过 RouteLocator Bean 来配置。RouteLocator 相同的 predicate 条件，RouteLocator 优先级高于配置文件。
     */
    @Bean
    public RouteLocator circuitBreakerRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                /**
                 * 测试指令：
                 *   curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8769/delay/3
                 *
                 * host(): Predicate。如果 Host: *.circuitbreaker.com，路由到 http://httpbin.org:80/delay/3。
                 * 如果直接访问 http://httpbin.org:80/delay/3，网站将会在 3 秒后返回 echo 信息。
                 *
                 * 定制一个 circuit breaker 对该访问进行保护，并将这个 circuit breaker 命名为 `myFallbackCircuitBreaker`。
                 * 缺省的 circuit breaker 对请求有时间限制，如果在特定的时长内没有回复会返回或者 504(Timeout) 错误，
                 * 或者 setFallbackUri 的页面。
                 *
                 * 如果存在缺省的过滤器（default-filters），这一条会覆盖缺省的配置。
                 *
                 * "forward" 是预定义的重定向指令。
                 * "/fallback"：Spring Cloud Gateway 预定义了一系列错误页面，其中 /fallback 是预定义的 404 页面。
                 * 可以用自定义的 Controller 覆盖预定义的 url。
                 * */
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f ->
                                f.circuitBreaker(config ->
                                        config
                                                .setName("myFallbackCircuitBreaker")
                                                .setFallbackUri("forward:/fallback")  // 缺省404
                                )
                        )
                        .uri("http://httpbin.org:80"))
                .build();
    }
}
