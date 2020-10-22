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
# 镜像仓库
## 拉取
docker pull centos
## 推送
docker tag ubuntu:18.04 username/ubuntu:18.04
## 其他
[自动构建镜像](https://vuepress.mirror.docker-practice.com/repository/dockerhub/#%E8%87%AA%E5%8A%A8%E6%9E%84%E5%BB%BA)
[构建私有仓库](https://vuepress.mirror.docker-practice.com/repository/registry/)

