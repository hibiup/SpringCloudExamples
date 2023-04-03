package pro.wangji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CheckConfigControllerImpl implements CheckConfigController {
    @Autowired
    private Environment env;

    @Value("${key1}")
    private String key1;

    @Override
    public String getKey1() {
        return String.format(
                "[Port-%d]: The value of %s is %s...\n",
                Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))),
                "key1",
                key1);
    }

    @Override
    public String getKeyValue(@PathVariable("key_name") String keyName) {
        return String.format(
                "[Port-%d]: The value of %s is %s...\n",
                Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))),
                keyName,
                env.getProperty(keyName));
    }
}
