package pro.wangji.service;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

/**
 * 分离出 Rest 接口，以利于被代理服务(feign)共享。
 */
public interface CheckConfigController {
    @GetMapping(
            value = "/config/key1",
            produces = MediaType.TEXT_PLAIN_VALUE)
    String getKey1();

    @GetMapping(
            value = "/config/{key_name}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    String getKeyValue(@PathVariable("key_name") String keyName);


    @GetMapping(
            value = "/delay/{seconds}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    String delay(@PathVariable("seconds") int seconds);
}
