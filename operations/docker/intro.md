# Docker简介
虚拟技术产物之一,类似的有vm、virtualbox、docker、rocker等
## docker定义
物理机上虚拟出多台操作系统
## docker术语
镜像仓库(docker hub)、镜像(image)、容器(container)
## 一般操作顺序
1. 从docker hub拉取镜像到本地       docker pull
2. 使用dockerfile指令自定义镜像 	docker build 
3. 操作容器                         docker run 
## 术语关系
image存储在docker hub中，container基于image启动
# DockerHub
镜像远程仓库
## 拉取
docker pull centos
## 推送
docker tag ubuntu:18.04 username/ubuntu:18.04
## 其他
[自动构建镜像](https://vuepress.mirror.docker-practice.com/repository/dockerhub/#%E8%87%AA%E5%8A%A8%E6%9E%84%E5%BB%BA) </br>
[构建私有仓库](https://vuepress.mirror.docker-practice.com/repository/registry/)
# Image
一种特殊的文件系统，除了提供程序除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）</br>
镜像不包含任何动态数据，其内容在构建之后也不会被改变
## Dockerfile
制作镜像使用的指令，根据指令自定义镜像，构建好的文件使用docker build构建镜像
[ref](https://vuepress.mirror.docker-practice.com/image/dockerfile/)
# Container
容器的实质是进程，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的 命名空间 </br>
因此容器可以拥有自己的 root 文件系统、自己的网络配置、自己的进程空间，甚至自己的用户 ID 空间</br>
容器可以被创建、启动、停止、删除、暂停等
[ref](https://vuepress.mirror.docker-practice.com/container/)
