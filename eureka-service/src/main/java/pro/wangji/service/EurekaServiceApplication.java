package pro.wangji.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Eureka Service 是注册到 Eureka 上的服务，可以启动多个，随机分配端口。
 *
 * Eureka Service 的 /hello 服务被 Service Ribbon 以负载均衡的方式代理。
 */

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaServiceApplication {
    // @Value("${server.port}")
    protected static final int port = ThreadLocalRandom.current().nextInt(8000, 8200 + 1);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EurekaServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", String.valueOf(port)));
        app.run(args);
    }

    @RequestMapping("/hello")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
