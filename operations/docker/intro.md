# Docker简介
虚拟技术产物之一,类似的有vm、virtualbox、docker、rocker等
## docker定义
物理机上虚拟出多台操作系统
## docker术语
镜像仓库(docker hub)、镜像(image)、容器(container)
## 术语关系
ops或者dev使用docker的时候内容。
1. 从docker hub拉取镜像到本地       docker pull
2. 使用dockerfile语法自定义镜像 	docker build 
3. 操作容器                         docker run  </br>
image存储在docker hub中，container基于image启动

