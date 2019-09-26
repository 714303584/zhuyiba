# 猪尾(yi)巴

一个JAVA编写的短网址生成服务端, 实现了基本的短网址生成以及302跳转的功能。

猪尾巴采用的短网址生成策略是： 将长网址进行保存,根据数据库中的自增ID通过invitation-code中的生成算法,生成一个唯一的短网址编码，从而进行短网址的生成。


# 软件环境

软件使用JAVA8进行编写,后台采用mysql数据库进行存储生成的短网址。

编写语言：采用JAVA
采用框架：springboot2.0和其内置的hibernate
数据存储：mysql数据库+redis缓存

# 运行环境搭建

请自行百度搜索 JAVA环境搭建 + Mysql环境搭建 + redis安装

后台编译时请修改对应的软件端口

src\main\resources下的application.properties

server.port=自己的端口

project.domain=http://自己的域名/

注意请定制自己的主页。

