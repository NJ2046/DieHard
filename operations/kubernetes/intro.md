## 概论
基于容器技术的分布式架构领先方案,基于Docker的大规模容器化分布式系统解决方案,实现资源管理的自动化,以及跨多个数据中心的资源利用率的最大化,负载均衡器的选型和部署实施问题,复杂的服务治理框架,服务监控和故障处理模块
## 写在前面
仅仅梳理软件安装问题，仅限于K8S。
### 软件的安装方式
1. 传统安装方式:源码、二进制以及依托于安装工具，包括但不限于yum、apt等
2. 基于容器的安装方式:k8s特有的安装方式，以配置文件(yaml or json)为调度资源的控制台，使用kubectl apply -f .yam 完成软件安装
### 需要用的软件
1. [docker:\<18.03](https://kubernetes.io/zh/docs/setup/production-environment/container-runtimes/), CRI,容器运行接口的一种软件
2. apt-get install -y kubelet=1.15.0-00 kubeadm=1.15.0-00 kubectl=1.15.0-00
3. flannel:CNI,容器网络接口的一种软件
4. [systemd](https://www.cnblogs.com/zwcry/p/9602756.html):linux管理的一套命令, systemctl, jorunalctl
其中docker和kubelet、kubeadm、kubectl必须使用传统安装方式, CNI可以使用传统安装方式也可以使用基于容器的安装方式，推荐使用后者。systmed用来调试软件安装出现的问题、监控软件运行的状态。


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
