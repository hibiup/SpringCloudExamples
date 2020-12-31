package pro.wangji.cloudconfig.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 依次启动eureka-server、config-server, config-client 和 MQ server. 访问 http://localhost:8771/hi 获得配置值
 *
 * 这时我们去代码仓库修改 foo的值。如果是传统的做法，需要重启服务，才能达到配置文件的更新。此时，我们只需要发送 post
 * 请求：http://localhost:8771/actuator/bus-refresh，config-client会重新读取配置文件
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@RefreshScope    // 刷新访问地址: [POST] http://localhost:8771/actuator/bus-refresh
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/hi")
    public String hi(){
        return foo;
    }
}
