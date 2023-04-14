package pro.wangji.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
public class CheckConfigControllerImpl implements CheckConfigController {
    @Autowired
    private Environment env;

    @Value("${key1}")
    private String key1;

    @Override
    public String getKey1() {
        return //Mono.fromCallable(() ->
            String.format(
                "[Port-%d]: The value of %s is %s...\n",
                Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))),
                "key1",
                key1);
        //);
    }

    @Override
    public String getKeyValue(@PathVariable("key_name") String keyName) {
        return //Mono.fromCallable(() ->
            String.format(
                "[Port-%d]: The value of %s is %s...\n",
                Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))),
                keyName,
                env.getProperty(keyName));
        //);
    }

    @Override
    public String delay(int seconds) {
        LocalDateTime now = LocalDateTime.now();
        try {
            Thread.sleep(seconds * 1000);
            return String.format(
                    "Start at:\t%s\nEnd at:\t\t%s",
                    now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }
}

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e) {
        e.printStackTrace();
        Map<String, String> message = Collections.singletonMap("message", e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
