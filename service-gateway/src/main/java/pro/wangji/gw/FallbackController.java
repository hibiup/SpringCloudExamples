package pro.wangji.gw;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    /**
     * 覆盖预定义的 /fallback，返回字符串"fallback"
     */
    @RequestMapping("/inCaseOfFailureUseThis")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
