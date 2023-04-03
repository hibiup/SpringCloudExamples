package pro.wangji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckConfigController {
    @Autowired
    private Environment env;

    @Value("${key1}")
    private String key1;

    @GetMapping(
            value = "/config/key1",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String getKey1() {
        return String.format("The value of %s is %s...\n", "key1", key1);
    }

    @GetMapping(
            value = "/config/{key_name}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String getKeyValue(@PathVariable("key_name") String keyName) {
        return String.format("The value of %s is %s...\n", keyName, env.getProperty(keyName));
    }
}
