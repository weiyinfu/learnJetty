* jetty是Java中的一种服务器，它的优点在于可以以代码的形式启动server。  
* jetty支持servlet。  
* jetty提供了httpclient可以用于写爬虫。

* 此博客将jetty官方文档翻译完成，是很好地中文jetty资料：http://www.cnblogs.com/yiwangzhibujian/p/5832294.html
* jetty官方文档地址：http://www.eclipse.org/jetty/documentation/

jetty中的设计思想就是：一切皆Handler，各种Handler的花式嵌套。
* HandlerCollection：handler集合，按顺序遍历handler
* HandlerList：按顺序遍历handler直到handled
* HandlerWrapper：包裹Handler，像面向切片编程一样
