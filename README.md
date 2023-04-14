参考：
* https://blog.csdn.net/forezp/article/details/70148833
* https://www.fangzhipeng.com/spring-cloud.html

# Basic

## 1. eureka-server

服务注册与发现（eureka）通常是 Cloud 的第一个要实现的服务，其他服务，包括配置管理（Config）服务也需要注册到该服务上。

## 2. config-server

配置管理通常是第二个要实现的服务

在分布式系统中，服务数量可能众多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。

## 3. config-service

配置查询服务（随机端口，可以启动多个以支持负载均衡）。作为启动的第三个服务，它提供了一些用户功能，由 cloud-config 提供配置支持。

## 4.1 service-feign

feign 是客户端负载均衡（通过生成一个伪 http 客户端实现和 ribbon 类似的功能但调用界面更友好），用户通过 feign 访问 config-service

* Feign 采用的是基于接口的注解
* Feign 具有负载均衡的能力
* Feign 可以集成断路器，参考：https://medium.com/@fazazulfikapp/spring-cloud-circuit-breaker-implementation-using-resilience4j-and-spring-open-feign-734d0fd34e37

## 4.2 service-loadbalancer

和 Feign 类似的客户端负载均衡解决方案，参考：https://spring.io/guides/gs/spring-cloud-loadbalancer/

## 5. gateway 路由器（取代 zuul）

Feign 和 Ribbon （通过伪 http client）实现了服务内部 api 的调用路由，并暴露出外部 api，多个外部 api 可以通过网关合集成在一个
域名下以形成更大的应用。例如可以实现 /user 路径路由到A服务集群，/admin 路径路由到B服务集群。同时网关还可以实现统一认证。

参考：
* https://cloud.spring.io/spring-cloud-gateway/reference/html/
* https://spring.io/guides/gs/gateway/



