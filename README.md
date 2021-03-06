华为云加速 DBank JAVA SDK 重构版本
===============================


当我发现华为网盘支持云加速来定制开发后，我就开始研究华为提供的二次开发的SDK，发现SDK对REST接口的封装不是很合理，所以我花了两天的时间对其做了一个深度的重构方便日后开发更加负责的业务。


华为云加速管理主页： [http://cloud.dbank.com/manager.html?v=2.9.18b1399537812208](http://cloud.dbank.com/manager.html?v=2.9.18b1399537812208)

华为云加速SDK帮助文档： [http://cloud.dbank.com/help.html?v=2.9.18b1399537812208](http://cloud.dbank.com/help.html?v=2.9.18b1399537812208)

-------------------------
以前代码存在的问题
---------------
1. 很多方法都放在Utils中，导致Utils实现了很多的功能，非常臃肿，不利于代码维护。
2. 使用 `Callback` 回调函数来负责处理发送消息和接受消息的流，导致写的代码很丑，不利于代码复用。
3. 没有将最核心的REST连接抽象出来，不利于快速开发其它类似的服务。
4. 没有用到单元测试。
5. 缺少业务配置文件和日志文件配置文件。

重构策略
-------
1. 重新封装Utils里面的不同的方法到不同的类里面，遵守单一职责的原则。
2. 将REST消息封装到 `RestConnector` 类里面，使用一个 `DBankRestHandler` 来初始化和收发消息。
2. 使用 `DBankRestHandler` 接口来负责处理发送消息和接受消息的流，所有的服务只需要实现这个接口，就可以被 `RestConnector` 中使用了。
3. 编写单元测试用例，采用测试驱动的方法来重构和开发新功能。
4. 增加 `dbank.properties` 和 `log4j.properties` 等配置文件。

----------------
使用注意事项
----------
 ** 使用IDE导入工程后，需要修改 `dbank.properties` 里面的 `app.name, app.id,app.secret` 等为你自己申请的id，然后修改测试用例，这样之后才能运行起来。 **


---------
联系我
-----

作者： 平江

邮箱： pingjiang1989@gmail.com

主页： [http://pingjiang.github.io](http://pingjiang.github.io)







