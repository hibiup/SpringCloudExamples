package pro.wangji.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients

public class ServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceFeignApplication.class, args );
    }
}

/**
 * 定义一个 feign 接口，通过 @FeignClient（“服务名”），来指定代理哪个服务。比如 eureka-service 服务的 /hello 接口：
 */
@FeignClient(value = "service-hello", fallback = ServiceHelloDelegateFallback.class)
interface ServiceHelloDelegate {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String sayHiFromClient(@RequestParam(value = "name") String name);
}

@RestController
class HelloController {
    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    ServiceHelloDelegate serviceHelloDelegate;

    @GetMapping(value = "/hello")
    public String sayHi(@RequestParam String name) {
        return serviceHelloDelegate.sayHiFromClient( name );
    }
}

/**
 * 当 ServiceHelloDelegate 失败时断路器 Feign 将访问该类。
 */
@Component
class ServiceHelloDelegateFallback implements ServiceHelloDelegate {
    @Override
    public String sayHiFromClient(String name) {
        return "sorry "+name;
    }
}