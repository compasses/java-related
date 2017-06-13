# ES dump tool
解压elastic-client.zip后会有个bin目录，里面有可执行文件elastic-client和配置文件config.properties，通过修改这两个文件实现导出、导入功能。

## 导出
1. 默认的功能，只需要修改config.properties的ES_HOST即可，其指向ES的ip:port地址
2. 导出的数据存在FILE指定的文件中，在当前bin目录下，默认文件名export.json
3. TENANTID指定导出的tenant ID，-1就是全部导出
4. 执行./elastic-client即可

## 导入
1. 导入需要修改执行文件里面的环境变量：vi elastic-client中DEFAULT_JVM_OPTS='"-Dconfig=./config.properties" "-Dexport=true"' 把-Dexport=true换成false，即为：DEFAULT_JVM_OPTS='"-Dconfig=./config.properties" "-Dexport=false"'
2. 同样修改config.properties中的ES_HOST，修改为要导入的ES节点ip和port，重新执行./elastic-client


## 日志

导出的日志，看到data exported count即为导出成功：

```
2017-06-13 12:18:15.770  INFO 39464 --- [           main] e.MainCtrl                               : Use user config ./config.properties
2017-06-13 12:18:15.772  INFO 39464 --- [           main] e.MainCtrl                               : going to export data fileName=export.json ESHOST=localhost:9200 tenantId=-1
2017-06-13 12:18:16.088  INFO 39464 --- [           main] e.LoadData                               : Start to export data
2017-06-13 12:18:17.330  INFO 39464 --- [           main] e.LoadData                               : data exported count = 3633
```

导入的日志，看到total send即为导入成功：

```
2017-06-13 12:20:19.367  INFO 46264 --- [           main] e.LoadData                               : left .66
2017-06-13 12:20:19.418  INFO 46264 --- [           main] e.LoadData                               : total send 3633
```
