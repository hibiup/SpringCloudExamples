package pro.wangji.ribbon;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Service ribbon 将作为 eureka-service 的服务代理，实现对多个 eureka-service 实例的负载均衡访问。
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix     // 启用 Hystrix 断路器
public class ServiceRibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceRibbonApplication.class, args );
    }

    @Bean
    @LoadBalanced  // @LoadBalanced 注解表明这个 restRemplate 开启负载均衡的功能。
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
class HelloService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")  // 如果服务不可到达则调用 hiError。
    public String hiService(String name) {
        /**
         * 对于开启负载均衡的 restTemplate，接受直接以服务名称（SERVICE-HELLO）作为域名。
         * */
        return restTemplate.getForObject("http://SERVICE-HELLO/hello?name="+name, String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}

@RestController
class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hello")
    public String hi(@RequestParam String name) {
        return helloService.hiService( name );
    }
}