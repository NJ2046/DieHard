# 背景
机器学习方法中使用标注数据训练出解决实际问题的模型</br>
一般的，可以理解人工智能产品的核心，即模型</br>
人机交互中，使用HTTP协议居多，WEB是交互载体，随即五花八门网络服务器问世</br>
以编程语言为讨论核心，围绕各种语言社区，以解决问题为驱动，每一个语言周围充斥着</br>
各种奇葩名字的网络服务器，由于问世的时间、地点以及问题领域不同，虽然他们本质没有太大区别</br>
但他们的存在，各有各的理由</br>
本次任务以python编程语言为核心，围绕着python社区的技术或者叫话题，开展一项分离和转移的任务</br>
关键词：python、model、flask、uwisg、thrift、nginx、docker</br>
# 任务
对话服务响应缓慢，客户不满意，需要提高对话响应速度</br>
## 思考
1. 关机，添加内存或者GPU，成本高，操作复杂。
2. 分析对话服务架构，了解服务器负载度，设置性能监控(没做)。 
## 方案
将模型从flask中分离出来，评估模型负载度，寻找合适负载度且带有GPU的机器，使用thrift进行网络传输
## 环境
A: OS:linux, Sortware:python、pip、flask、uwsgi、nginx、js</br>
B: OS:linux, Sortware:python、pip、thrift、docker
## 过程
|setp|location|action|
|---|---|---|
|1|A|detach model as m|
|2|A|scp m to B as m|
|3|B|m nest thrift|
|4|B|reload thrift| 
|5|B|test m of thrift|
|6|A|base on m to code flask client|
|7|A|test flask client|
|8|A|client nest uwsig and test|
|9|A|base on client to code js|
|10|A|nginx proxy pass client|
|11|A|js nest nginx and test|
## 耗时
起2020-10-27止2020-10-26 总计: 3d
# 感与悟
上周四中午接到任务，对任务中使用的环境不甚了解，现在不熟练</br>
## 技术简介
python：一种编程语言，与我而言就是写脚本及其方便，掌握程度一般。流畅的python可以提升</br>
pip:管理python包的一个工具，自动解决包与包之前的依赖问题，掌握程度熟练。</br>
flask:基于python语言的web服务，掌握程度了解。</br>
uwsgi:web服务，掌握程度不了解。</br>
nginx:web服务，主要特性是反向代理和基于反向代理实现的负载均衡，掌握程度了解。</br>
docker:一种容器技术，解决环境一致，迁移方便，掌握程度了解。</br>
thrift:网络传输服务,一种基于RPC(远程过程调用)方法论的产物，可以通过编译原理中的PC切入讲解</br>
实现主要是三个步骤：构建函数和ID映射表、网络传输中序列话反序列、使用TCP的socket传输</br>
与之对应的是REST,资源在网络中的状态转移，一个更玄乎且枯燥的学究名词，来自于某论文，简单特性，URL定位资源，HTTP(GET、POST)动词描述操作</br>
RPC和REST可以说是方法轮，可以说是软件构架方案，更深层次的区别也不知道，表面上是PRC在传输层，一般使用TCP，REST在应用层，一般使用HTTP</br>
RPC:thrift</br>
HTTP:spring家族桶、django、flask、tomcat等</br>
## 操作详情
1. 分离模型的时候要大致了解一下flask中，调用模型的切入点，了解模型之间的代码依赖(包括包依赖)。收获不大，主要解决包依赖，以及代码报错问题，要有点代码调试能力。使用工具是vim编辑器，print看看接口的输入输出类型等。
3. 已经搭建了thrift服务，任务是嵌入。在未得到嵌入任务前，我是自建了这个服务，使用的python中的thrift2。备注一下，嵌入前的thrift1相当混乱且不利于维护，并且丢失了关键的函数映射表，也就是.thrift的配置文件。后续考虑迁移到thrift2，因为简洁方便。
10. 出现一个低级却被忽略的问题，nginx.conf配置错误，第一次操作，没有入门，是看着原有的配置，照着葫芦画瓢，并没有理解配置含义，所以后续还是要先简单入门，再入手。
## 后续
1. docker容器的持久化以及迁移,熟练这些操作。
2. 将了解程度的转化为熟悉程度。

