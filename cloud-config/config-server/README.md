远程仓库https://github.com/hibiup/SpringCloudExamples/ 中有个文件 configure/config-client-dev.properties 文件中有一个属性：

    foo = foo version 3

启动程序：访问 http://localhost:8770/foo/dev

```json
{
   "name":"foo",
   "profiles":[
      "dev"
   ],
   "label":"master",
   "version":"792ffc77c03f4b138d28e89b576900ac5e01a44b",
   "state":null,
   "propertySources":[
      
   ]
}
```

http 请求地址

    http://url/applicaiton-name/profile/label

资源文件映射如下:

    /{application-name}/{profile}[/{label}]
    /{application-name}-{profile}.yml
    /{label}/{application-name}-{profile}.yml
    /{application-name}-{profile}.properties
    /{label}/{application-name}-{profile}.properties