package pro.wangji.service;

//import brave.sampler.Sampler;
//import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.FeignClient;

import java.util.logging.Logger;

/**
 * Eureka Service 1 是注册到 Eureka 上的服务，可以启动多个，随机分配端口。
 *
 * Eureka Service 的 /hello 服务被 Service Ribbon 以负载均衡的方式代理。
 */

@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableFeignClients
//@RestController
public class ConfigServiceApplication {
    private static final Logger logger = Logger.getLogger(ConfigServiceApplication.class.getName());

    @Value("${server.port}")
    int port;

    public static void main(String[] args) {
        /*
        测试多实例，随机分配端口

        final int port = ThreadLocalRandom.current().nextInt(8000, 8200 + 1);
        SpringApplication app = new SpringApplication(EurekaServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", String.valueOf(port)));
        app.run(args);
        */
        SpringApplication.run(ConfigServiceApplication.class, args);
    }

    /**
     * 调用另一个服务，产生 trace record
     *
     * Sampler 是调用跟踪记录采样器。Spring Sleuth 采用低度侵入性的方案来跟踪调用过程，需要插入这个采样器才能工作。
     */
    //@Bean
    /*public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }*/

    /**
     * 定义一个 feign 接口调用 service-hello：
     */
    /*@FeignClient(value = "service-backend", fallback = ServiceBackendDelegateFallback.class)
    interface ServiceBackendDelegate {
        @RequestMapping(value = "/deep_hello",method = RequestMethod.GET)
        String sayHiFromFrontend(@RequestParam(value = "name") String name);
    }*/

    /**
     * 当 ServiceBackendDelegate 失败时断路器 Feign 将访问该类。
     */
    /*@Component
    class ServiceBackendDelegateFallback implements ServiceBackendDelegate {
        @Override
        public String sayHiFromFrontend(String name) {
            return "sorry "+name;
        }
    }*/

    /*@Autowired
    ServiceBackendDelegate serviceBackendDelegate;*/

    /**
     * Frontend 服务入口。调用  backend，会产生调用跟踪日志
     */
    /*@RequestMapping("/bk")
    public String callBackend(){
        logger.log(Level.INFO, "Generate calling trace for backend service  ");
        return serviceBackendDelegate.sayHiFromFrontend( "backend" );
    }*/

    /**
     * 本地服务，不会产生调用跟踪日志
     */
    /*@RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "jason") String name) {
        logger.log(Level.INFO, "Not generate calling trace");
        return "hi " + name + " ,i am from port:" + port;
    }*/

}

