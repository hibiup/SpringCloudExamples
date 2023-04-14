package pro.wangji.loadbalancer;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


/**
 * Service ribbon 将作为 eureka-service 的服务代理，实现对多个 eureka-service 实例的负载均衡访问。
 */

@SpringBootApplication
@EnableDiscoveryClient
public class LoadBalancerApplication {
    public static void main(String[] args) {
        SpringApplication.run( LoadBalancerApplication.class, args );
    }
}

@Configuration
class LoadBalancerConfig{

    /**
     * 1. 具有负载均衡的客户端生成器。@LoadBalanced 是关键
     */
    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    /**
     * 2. 根据生成器获得负载均衡客户端
     */
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}

@Log4j2
@RestController
class CheckConfigDelegateController {
    @Autowired
    WebClient webClient;


    @GetMapping(
            value = "/config/key1",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getKey1() {
        return webClient.get().uri("http://config-service/config/key1")
                .retrieve().bodyToMono(String.class);
    }

    @GetMapping(
            value = "/config/{key_name}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getKeyValue(@PathVariable("key_name") String keyName){
        return webClient.get().uri(String.format("http://config-service/config/%s", keyName))
                .retrieve().bodyToMono(String.class);
    }

    @GetMapping(
            value = "/delay/{seconds}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> delay(@PathVariable("seconds") int seconds){
        return webClient.get().uri(String.format("http://config-service/delay/%d", seconds))
                .retrieve().bodyToMono(String.class);
    }
}
