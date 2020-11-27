远程仓库https://github.com/hibiup/SpringCloudExamples/ 中有个文件 configure/config-client-dev.properties

启动程序：访问 http://localhost:8770/foo/dev

```json
{
   "name":"foo",
   "profiles":[
      "dev"
   ],
   "label":null,
   "version":"792ffc77c03f4b138d28e89b576900ac5e01a44b",
   "state":null,
   "propertySources":[
      
   ]
}
```

此时输入的 `foo/dev` 为任意输入的路径，并不对应任何文件或文件内容，主要查看 `version` 返回的 commit hash是否和服务端一致。

如果希望读取真实配置文件，输入:`http://localhost:8770/config-client/dev`：

```json
{
   "name":"config-client",
   "profiles":[
      "dev"
   ],
   "label":"develop",
   "version":"716fe1e9be8d8dcdee6974be319f99a065663ed2",
   "state":null,
   "propertySources":[
      {
         "name":"https://github.com/hibiup/SpringCloudExamples//configure/config-client-dev.properties",
         "source":{
            "foo":"foo version 21",
            "democonfigclient.message":"hello spring io"
         }
      }
   ]
}
```

url 格式为:`http://<config-server:<port>/<application.name>/<config.profile>/[<config.label>]`

详细参考 `config-client` 项目说明
