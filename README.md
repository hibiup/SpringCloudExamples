参考：
* https://blog.csdn.net/forezp/article/details/70148833
* https://www.fangzhipeng.com/spring-cloud.html

# Basic

## 1. eureka-server

服务注册与发现（eureka）通常是 Cloud 的第一个要实现的服务，其他服务，包括配置管理（Config）服务也需要注册到该服务上。

## 2. cloud-config

配置管理通常是第二个要实现的服务

在分布式系统中，服务数量可能众多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。spring cloud config 组件分两个角色:

    * config-server
    * config-client

## 3. eureka-service

  服务（随机端口，可以启动多个以支持负载均衡）

# Advance

## Gateway

## OAuth2

## service-ribbon

  具有负载均衡的服务客户端

## service-feign

  * Feign 采用的是基于接口的注解
  * Feign 整合了ribbon，具有负载均衡的能力
  * 整合了 Hystrix，具有熔断的能力

## service-zuul

  * 在 ribbon 的基础上进一步提供了路由功能。
  * 支持过滤器：通过 http://localhost:8769/api-r/hello?name=forezp&token=22 或 http://localhost:8769/api-f/hello?name=forezp&token=22 访问。
  没有 `token` 参数返回 `token is empty`
  

