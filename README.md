## 平台简介

FOXUPAY 能量租用平台 是一款开源的能量租用平台，基于区块链技术，采用区块链技术实现能量租赁，降低代币转账成本。

**一、部署说明**

如不需定制功能，可直接使用本目录代码部署，不需要再次编译源代码。

**二、部署教程**

本教程基于 1Panel 面板进行说明，其他方式可自行部署。

1、安装 1Panel 面板

可参考 1Panel 官方文档：https://www.1panel.cn
2、进入面板 > 应用商店 安装软件

    Java
    OpenResty
    Mysql
    Redis
    ActiveMQ

3、进入 网站 > 网站 创建网站 输入主域名 确认即可
4、进入 网站 > 网站 > 点击 域名 > 网站目录 > 主目录 点击右侧文件图标 进入文件管理

将 manage jar index 上传到此目录下

目录结构如下：

    ├── index
    ├── jar
    ├── manage
    ├── ssl

5、进入 网站 > 网站 > 点击 域名 > 伪静态 粘贴内容，保存并重载

将内容中的 e.daguoli.cn 替换为你的域名

注意：需在服务器控制台 安全组 中开放 8080 或 自定义端口 否则无法访问后端数据

```agsl
location ^~/ {
    root /www/sites/e.daguoli.cn/index;
    try_files $uri $uri/ /index.html;
}
location ^~/manage {
    alias /www/sites/e.daguoli.cn/manage/;
    try_files $uri $uri/ /manage/index.html;
}
location ^~ /api/ {
    proxy_pass http://127.0.0.1:8080/;
    proxy_set_header Host $host:$server_port;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header REMOTE-HOST $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
}
```

6、 jar/application.yml 文件配置

只列出需自行配置的参数

```agsl
ruoyi:
  profile: /home/fox/energy/files //文件上传路径，可自行修改

server:
  port: 8051 //服务器的HTTP端口，可自习修改为其他端口，与反向代理端口一致即可
spring:
  datasource:
    druid:
      master:
        //自行修改为 本机 mysql 连接信息
        url: jdbc:mysql://127.0.0.1:3306/energy-agent?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: 
        password: 

  //自行修改为 本机 redis 连接信息
  redis:
    host: 127.0.0.1
    port: 16379
    database: 0
    password: //替换为redis连接密码
  //自行修改为 本机 activemq 连接信息
  activemq:
    user: system
    password: manager
    broker-url: tcp://127.0.0.1:61616
    packages:
      trust-all: true
    #消息不放在内存中
    in-memory: false
    pool:
      # true表示使用连接池
      enabled: true
      # 连接池最大连接数
      max-connections: 10

token:
  secret: //替换密码 //随机生成32为字符串替换即可
```

7、项目启动
启动后端

使用 Supervisor 创建守护进程进行启动及监控

进入 面板 > 工具箱 > 进程守护

若未安装 Supervisor 可参考 1Panel 官方文档：https://1panel.cn/docs/user_manual/toolbox/supervisor/

创建 supervisor 进程

    名称：自定义
    运行目录：/opt/1panel/apps/openresty/openresty/www/sites/e.daguoli.cn/jar
    启动命令：nohup java -jar -Dloader.path=/opt/1panel/apps/openresty/openresty/www/sites/e.daguoli.cn/jar/lib energy.jar --spring.config.location=/opt/1panel/apps/openresty/openresty/www/sites/e.daguoli.cn/jar/application.yml &

以上均需自行修改 e.daguoli.cn 为 你的域名

确认，等待启动成功即可

启动PC、管理端

上传文件到对应 index manage 目录，即可启动

8、设置定时日志切割

进入 面板 > 计划任务 > 创建任务

    任务类型：Shell脚本
    任务名称：自定义
    执行周期：每天 23小时59分钟
    脚本内容：/opt/1panel/apps/openresty/openresty/www/sites/e.daguoli.cn/jar/splitLog.sh

需自行修改 e.daguoli.cn 为 你的域名

可提供软件部署服务，具体请联系

Email:admin@foxupay.com

电报：@foxupay

**三、添加通道**

通道实现类位于：energy-lease/src/main/java/com/fox/energy/lease/channel/service 目录下

只需要创建 通道类 并实现 ChannelService ，设置唯一的 通道代码

方法说明：

1. getChannel() 获取通道代码，需要唯一
2. getPrice()   获取通道价格，需设置且仅支持 1h、24h、72h及其他的价格
3. pay()        通道下单，需要实现具体的下单逻辑并返回 上游订单号 及 代理hash (如果同步返回的话)
4. query()      通道订单查询，通过上游订单号查询订单状态，并按要求设置订单数据

可提供通道对接服务，具体请联系

Email:admin@foxupay.com

电报：@foxupay

tele群：https://t.me/+Ry8pEQ1RIKczNjkx

官网及演示：https://e.foxupay.com
