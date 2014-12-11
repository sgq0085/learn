实践代码记录
===================================


learn-jta-jpa
-----------------------------------
基于spring-data-jpa和atomikos的一个JTA演示，并配置jdbcdslog打印sql<br />
1.[spring-data-jpa通过Atomikos实现JTA事务](http://sgq0085.iteye.com/blog/2001918)<br />
2.[Atomikos+jdbcdslog——分布式事务管理输出实际log日志](http://sgq0085.iteye.com/blog/2039534)<br />


learn-unit-test
-----------------------------------
关于单元测试的一些研究学习，包括JUnit、Mockito、AssertJ<br />
1.[JUnit注解整理](http://sgq0085.iteye.com/blog/2029388)<br />
2.[Mockito 初探](http://sgq0085.iteye.com/blog/2031319)<br />
3.[JUnit 单元测试断言推荐 AssertJ](http://sgq0085.iteye.com/blog/2030609)<br />


learn-sitemesh
-----------------------------------
SiteMesh配置实例<br />
[SiteMesh2简单研究笔记](http://sgq0085.iteye.com/blog/2072882)<br />
[SiteMesh3简单研究笔记](http://sgq0085.iteye.com/blog/2103870)<br />

learn-web
-----------------------------------
学习研究JQuery、Bootstrap等


learn-core-java
-----------------------------------
一些简单案例的研究学习<br>
Redis和Memcached简单访问DEMO<br />
1.[Redis安装](http://sgq0085.iteye.com/blog/2087750)<br />
2.[Centos6.5安装Memcached](http://sgq0085.iteye.com/blog/2088440)<br />


learn-msm
-----------------------------------
1.通过maven-dependency-plugin实现导出，基于memcached-session-manager实现负载均衡时需要放到Tomcat/lib目录下的依赖<br />
> 注意：为了和其他依赖做区分，msm所需的依赖scope设置为test

MVN命令

    mvn dependency:copy-dependencies  -DexcludeScope=compile  -DoutputDirectory=./target/lib

或可以直接使用 learn\learn-msm\bin\msm_dependency.bat 自动下载<br />
2.一个负载均衡测试页面<br />
3.一个测试通过的Nginx配置文件<br />
[基于memcached-session-manager的负载均衡](http://sgq0085.iteye.com/blog/2089298)<br />


learn-cas-client  learn-cas-server
-----------------------------------
CAS服务器端和客户端的研究<br />
1.[keytool+tomcat 单向/双向认证的配置](http://sgq0085.iteye.com/blog/1767923)<br />
2.[yale-cas服务器端深度定制](http://sgq0085.iteye.com/blog/2003190)<br />
3.[cas server 4.0深度研究](http://sgq0085.iteye.com/blog/2099196)<br />
4.[yale-cas 与 shiro进行整合](http://sgq0085.iteye.com/blog/2003783)<br />


learn-shiro
-----------------------------------
shiro的研究<br />
1.[简单的Spring整合Shiro](http://sgq0085.iteye.com/blog/1983832)<br />
2.[Shiro一些补充](http://sgq0085.iteye.com/blog/2163641)<br />
