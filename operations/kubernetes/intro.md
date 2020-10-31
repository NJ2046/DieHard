# 概论
基于容器技术的分布式架构领先方案,基于Docker的大规模容器化分布式系统解决方案,实现资源管理的自动化,以及跨多个数据中心的资源利用率的最大化,负载均衡器的选型和部署实施问题,复杂的服务治理框架,服务监控和故障处理模块
### 软件的安装方式
1. 传统安装方式:源码、二进制以及依托于安装工具，包括但不限于yum、apt等
2. 基于容器的安装方式:k8s特有的安装方式，以配置文件(yaml or json)为调度资源的控制台，使用kubectl apply -f .yam 完成软件安装
### 需要用的软件
1. [docker\<18.03](https://kubernetes.io/zh/docs/setup/production-environment/container-runtimes/): CRI,容器运行时的一种软件。
2. apt-get install -y kubelet=1.15.0-00 kubeadm=1.15.0-00 kubectl=1.15.0-00
3. flannel:CNI,容器网络接口的一种软件
4. [systemd](https://www.cnblogs.com/zwcry/p/9602756.html):linux管理的一套命令, systemctl, jorunalctl</br>
其中docker和kubelet、kubeadm、kubectl必须使用传统安装方式, CNI可以使用传统安装方式也可以使用基于容器的安装方式，推荐使用后者。systmed用来调试软件安装出现的问题、监控软件运行的状态。
### 软件依赖
1. docker是k8s所必须的，k8s里的docker是指符合容器运行时的一种软件，当然还有其它容器运行时软件。理解的话，可以用编译器比较，比如C语言会有多种编译器，常用的是GCC，但也有VC、TC等。相同的C语言语法，用不同编译器，都可以正常编译出可运行文件。k8s虽然是一个解决方案、框架或者说是服务，但从编程语言角度理解，k8s的确定义了自己的DSL, 它以资源为基本单元，定义了一套操作资源的领域专用语言。
2. kubelet、kubeadm、kubectl。k8s提供了几种安装方式命令工具安装和二进制文件安装。这里采用第一种方式安装k8s，其中kubeadm用来初始化集群，kubelet用来在集群中的每个节点启动pod和容器等，kubectl用来与集群通信。建议使用yum或apt来安装，工具版本使用1.14.0，kubeflow对k8s的版本1.14.0支持最好，一般的工具版本指定后，如果没有的指定k8s版本，那么k8s的版本会跟随安装工具kubeadm版本。
3. flannel。负责k8s中pod通信的软件。可以通过传统方式安装，也可以通过基于容器的方式安装，推荐后者。安装完成后，集群内节点将处于ready状态，ifconfig的output中会多一个flannel的网卡信息。
4. systemctl,jorunalctl。如果在安装过程中出现错误，可以使用这两个工具来调试错误。systemclt status serverName，查看服务的运行状态。jorunalctl serverName查看服务的日志
# 操作步骤
安装软件包含主节点和子节点，请依照标题的上下顺序进行安装，有一些软件存在依赖性，请严格按照顺序安装，标题的先后顺序是基于软件依赖性进行的。主&子节点是主和子节点都需要进行的操作，主节点和子节点是分别对应的不同操作
## 主&子节点
### 前置操作
关闭防火墙、selinux、swapoff
```
systemctl stop firewalld && \
systemctl disable firewalld && \
sed -i 's/^SELINUX=.*/SELINUX=disabled/' /etc/selinux/config  && \ 
setenforce 0 && \
swapoff -a && \
sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab 
```
### 安装 Docker
### 安装 Kube\* 工具
## 主节点
## 子节点

## 安装
### 前置
#### 硬件&软件
1. cpu和内存：Master:2core&4GB, Node:4core&16GB
2. OS:linux
3. etcd:<3.0(分布式存储数据库)
4. [docker:\<18.03](https://kubernetes.io/zh/docs/setup/production-environment/container-runtimes/)
#### 操作
```
1. systemctl disable firewalld
2. systemctl stop firewaddl
3. setenforce 0 
4. swapoff -a or sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab 
5. apt-get install -y kubelet=1.15.0-00 kubeadm=1.15.0-00 kubectl=1.15.0-00
6. systemctl enadle docker && systemctl start docker
7. systemctl enadle kubelet && systemctl start kubelet
8. 
	kubeadm init \
	--apiserver-advertise-address=172.17.7.152 \
	--image-repository registry.aliyuncs.com/google_containers \
	--service-cidr=10.1.0.0/16 \
	--pod-network-cidr=10.244.0.0/16
9.	node exec setp 1-8 and exce this command
	kubeadm join 172.17.7.152:6443 --token apnko7.myavcusube5jyt3r \
    	--discovery-token-ca-cert-hash sha256:b2618e08860356992f14d58a6b52c5add541d2b22173389a8fa8e1e5e6febcfb
10. 安装网络插件
```
## Kubeflow
