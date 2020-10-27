# 背景
机器学习方法中使用标注数据训练出解决实际问题的模型</br>
一般的，可以理解人工智能产品的核心，即是模型</br>
人机交互中，使用HTTP协议居多，WEB是交互载体，随即五花八门网络服务器问世</br>
以编程语言为讨论核心，围绕各种语言社区，以解决问题为驱动，每一个语言周围充斥着</br>
各种奇葩的名字的网络服务器，由于问世的时间、地点以及问题领域不同，虽然他们本质没有太大区别</br>
但他们的存在，各有各的理由</br>
本次任务以python编程语言为核心，围绕着python社区的技术或者叫话题，开展一项分离和转移的任务</br>
关键词：python、model、flask、uwisg、thrift、nginx、docker</br>
# 任务
对话服务响应缓慢，客户不满意，需要提高对话速度</br>
## 思考
1. 关机，添加内存或者GPU，成本高，操作复杂。
2. 分析服务架构，了解服务器负载度，设置性能监控。 
## 方案
将模型从flask中分离出来，评估模型负载度，寻找合适负载度且带有GPU的机器，使用thrift进行网络传输
## 环境
A机器: OS:linux, Sortware:python、pip、flask、uwsgi、nginx、js
B机器: OS:linux, Sortware:python、pip、thrift、docker
## 执行
1. 从flask中分离出模型
2. 模型嵌入thrift服务
3. 测试thrift服务器端
4. 编写flask的客户端代码
5. 测试flask服务
6. 嵌入uwsig且测试
7. 编写js代码 
9. nginx反向代理
8. js嵌入到nginx服务中
过程：
|location|action|
|---|---|
|A|A detach model as m|
|A|scp m to B as m|
|B|m nest thrift|
|B|reload thrift| 
|B|test thrift of m|
|A|base on m to code flask client|
|A|test flask client|
|A|client nest uwsig and test|
|A|base on client to code js|
|A|nginx proxy pass client|
|A|js nest nginx and test|

