#分布式图片存储系统（demo版）


##服务端启动流程：

1.启动nameserver： 运行ndfs.nameserver.NameServerMainClass的main方法。相应的端口和参数配置在src/main/resources/conf.properties文件中。

2.启动dataserver： 运行ndfs.dataserver.DataServerMainClass的main方法。为减少文件碎片，第一次启动时，系统会在D盘下建立400多个block文件，用于后续的图片存储。端口和参数配置在src/main/resources/conf.properties文件中。


##客户端使用方式：

1.按照上面步骤启动服务端程序。

2.上传文件：运行ndfs.client.netty.MainClass的upload方法，以操作系统的文件路径作为参数，完成上传。上传成功后，方法返回该文件的URL地址。

  读取文件：运行ndfs.client.netty.MainClass的read方法，以文件URL地址为参数，该方法会将读取到的文件存放在D盘下的new.jpg中。


##实现原理 
参考淘宝图片文件存储系统。http://code.taobao.org/p/tfs/wiki/intro/
