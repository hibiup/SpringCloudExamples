配置文件必须在程序启动之前载入，因此必须配置在 `bootstrap.properties` 中：

    ...
    spring.application.name=config-client
    spring.cloud.config.profile=dev
    spring.cloud.config.label=master
    ...

表示读取 `master` branch 下的 `<spring.application.name>-<spring.cloud.config.profile>.properties` 文件

http 请求地址

    http://<config-server>:<port>/applicaiton-name/profile/[label]

label 为可选，值可能是目录名称，也可能是 branch 名称，因为配置文件的命名规则可以有多种形式，甚至可以是默认(`master` branch):

    /{application-name}-{profile}.yml
    /{application-name}/{profile}/{label}
    /{label}/{application-name}-{profile}.yml
    /{application-name}-{profile}.properties
    /{label}/{application-name}-{profile}.properties


依次启动 eureka-server，config-server和 config-client，然后访问: `http://localhost:8771/hi` 返回 `foo version 21`
