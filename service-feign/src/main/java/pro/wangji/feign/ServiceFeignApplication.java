package pro.wangji.feign;

import feign.Feign;
import feign.Target;
/*import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.springboot3.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;*/

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import pro.wangji.service.CheckConfigController;
//import reactivefeign.spring.config.EnableReactiveFeignClients;
//import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
//@EnableReactiveFeignClients
public class ServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceFeignApplication.class, args );
    }
}

/**
 * 1. 定义一个 feign 接口，通过 @FeignClient（“服务名”）来指定代理哪个服务。比如 config-service 服务的各项接口。
 *
 * 注意：代理的目标服务通过 interface 与 Feign 共享接口，虽然这并非必须的，FeignClient 是由 @FeignClient 自动申告并生成的代理类。
 * 它本身不是 controller，但是会（在第3步）被注入代理 controller 用于与代理目标通讯。它的 method 也并非必须与代理目标一摸一样，但是通过
 * 共享可以让代理的接口与目标接口保持一致。
 */
@Component
//@ReactiveFeignClient(value = "config-service", fallback = ConfigServiceDelegateFallback.class)
@FeignClient(name="config-service" /*, fallback = ConfigServiceDelegateFallback.class*/)
interface ConfigServiceDelegate extends CheckConfigController{ }

/**
 * 2. 生成代理 Controller。同样我们通过和代理目标共享接口来保持接口的一致性。
 */
@RestController
class CheckConfigDelegateController implements CheckConfigController {
    /**
     * 3. 注入 feign 代理实例，这个代理实例是由第 1 步动态生成的。
     * 编译器可能会报错，无视之。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
     */
    @Autowired
    ConfigServiceDelegate configServiceDelegate;

    /**
     * 4. 实现代理接口，每个接口方法的 body 只是简单地回调第三步注入的 delegate 实例的相应同名 method。
     */
    @Override
    public String getKey1() {
        return configServiceDelegate.getKey1();
    }

    @Override
    public String getKeyValue(String keyName) {
        return configServiceDelegate.getKeyValue( keyName );
    }

    @Override
    public String delay(int seconds) {
        return configServiceDelegate.delay(seconds);
    }
}
