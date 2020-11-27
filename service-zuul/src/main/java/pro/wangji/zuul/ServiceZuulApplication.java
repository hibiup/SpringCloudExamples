package pro.wangji.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableZuulProxy          // 打开 Zuul 路由服务
public class ServiceZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run( ServiceZuulApplication.class, args );
    }
}

/**
 * 添加服务过滤
 */
@Component
class MyFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public String filterType() {
        /**
         *     pre：路由之前
         *     routing：路由之时
         *     post： 路由之后
         *     error：发送错误调用
         */
        return "pre";
    }

    @Override
    public int filterOrder() {
        /**
         * 过滤的顺序
         */
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        /**
         * 过滤条件，true 表示无条件过滤
         */
        return true;
    }

    @Override
    public Object run() {
        /**
         * 过滤器的具体逻辑
         */
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e){}

            return null;
        }

        log.info("ok");
        return null;
    }
}