package pro.wangji.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import pro.wangji.service.CheckConfigController;

@SpringBootApplication
@EnableFeignClients
public class ServiceFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceFeignApplication.class, args );
    }
}

/**
 * 定义一个 feign 接口，通过 @FeignClient（“服务名”），来指定代理哪个服务。比如 config-service 服务的各项接口。
 * 注意：代理的目标服务通过 interface 与 Feign 共享了接口。
 */
@FeignClient(value = "config-service", fallback = ConfigServiceDelegateFallback.class)
interface ConfigServiceDelegate extends CheckConfigController{ }

@RestController
class CheckConfigDelegateController implements CheckConfigController {
    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    ConfigServiceDelegate configServiceDelegate;

    @Override
    public String getKey1() {
        return configServiceDelegate.getKey1();
    }

    @Override
    public String getKeyValue(String keyName) {
        return configServiceDelegate.getKeyValue( keyName );
    }
}

/**
 * 当 ConfigServiceDelegate 失败时断路器 Feign 将访问该类。
 */
@Component
class ConfigServiceDelegateFallback implements ConfigServiceDelegate {
    @Override
    public String getKey1() {
        return "404";
    }

    @Override
    public String getKeyValue(String name) {
        return "404";
    }
}