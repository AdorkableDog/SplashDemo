# 安卓客户端网络加载启动页和活动页



## 介绍

根据需求，首次加载启动页面使用本地（apk内部的Splash 图片），请求接口获取启动页面和活动页面的随机图片后Picasso缓存到本地，第二次打开app，启动页面使用缓存图片
活动页面也使用缓存中的。此处要做的有无网络和缓存，是否为第一次打开app,相应的逻辑以无网络，显示本地图片，
有网的情况下，获取接口中的URL,关闭网络，加载缓存中的图片。

## 架构

两种架构：MVP 和 Google 推出的 LiveData。


- [master 分支](https://github.com/Bakumon/UGank/tree/master) 采用 MVP 架构。参考：[android-architecture#todo-mvp-rxjava](https://github.com/googlesamples/android-architecture/tree/todo-mvp-rxjava)

- [livedata 分支](https://github.com/Bakumon/UGank/tree/livedata) 采用 LiveData 。参考：[android-architecture-components#BasicSample](https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample)


## License

[GNU General Public License, version 3](https://github.com/Bakumon/UGank/blob/master/LICENSE)