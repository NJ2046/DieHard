# Docker
## 常规操作
1. from remote repository by pull image
2. use dockerfile to build UserDefindImage
3. run UDI as container
4. push UDI to remote repository
[Docker development best practices](https://docs.docker.com/develop/dev-best-practices/)
## 任务操作
1. 从生产环境导出镜像: docker export container-name > image.tar
2. 将image.tar导入开发环境: docker load image.tar image-name:image-version
3. 使用dockerfile对导入镜像进行操作: write contnet to dockerfile. [ref](https://vuepress.mirror.docker-practice.com/image/dockerfile/)
4. 生成新的镜像: docker build -t image-name:image-version
5. 推入镜像服务器：docker push username/image-name:image-version
7. 生产环境拉下镜像:docker pull image-name:image-version
# Flask
Flask is a microframework for Python based on Werkzeug, Jinja 2 and good intentions. And before you ask: It's BSD licensed!
## 任务操作
1. python注解@app.route标示请求地址,直接在下面编写实现方法
2. import thrift函数以及函数映射文件
# uWSGI
# Thrift
# Nginx
# LShell
