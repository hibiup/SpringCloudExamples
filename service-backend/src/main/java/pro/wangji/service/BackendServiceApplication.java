package pro.wangji.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class BackendServiceApplication {
    private static final Logger logger = Logger.getLogger(BackendServiceApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(BackendServiceApplication.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    /**
     * 定义一个 feign 接口调用 service-hello：
     */
    @FeignClient(value = "service-hello", fallback = ServiceHelloDelegateFallback.class)
    interface ServiceHelloDelegate {
        @RequestMapping(value = "/hello",method = RequestMethod.GET)
        String sayHiFromBackend(@RequestParam(value = "name") String name);
    }

    /**
     * 当 ServiceHelloDelegate 失败时断路器 Feign 将访问该类。
     */
    @Component
    class ServiceHelloDelegateFallback implements ServiceHelloDelegate {
        @Override
        public String sayHiFromBackend(String name) {
            return "sorry "+name;
        }
    }

    /**
     * Backend 服务入口
     */
    @Autowired
    ServiceHelloDelegate serviceHelloDelegate;

    @RequestMapping("/deep_hello")
    public String deep_hello(@RequestParam(value = "name") String name){
        logger.log(Level.INFO, "Backend service is being called from "+ name + ".(Call back to front end...)");
        return serviceHelloDelegate.sayHiFromBackend( "backend" );
    }

}
