## 搜房网

### 开发

启动所需中间件（借助 docker 、docker-compose 工具）：

下载ik分词器：
```shell
wget https://kgithub.com/medcl/elasticsearch-analysis-ik/releases/download/v7.17.7/elasticsearch-analysis-ik-7.17.7.zip
```
```shell
docker-compose up --build
```

启动前端 (项目在 frontend 里)：
如果没有安装过依赖，需要安装一下（国内推荐使用 cnpm 代替 npm）:

```shell
cnpm install
```
接下来
```shell
cnpm run dev
```

启动后端：

先
```shell
mvn wrapper:wrapper
```

再
```shell
./mvnw spring-boot:run
```

windows 用户需要将 `./mvnw` 替换为 `./mvnw.cmd`(mvn spring-boot:run)