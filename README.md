Arad
====

##android快速应用开发框架

android rapid application development的简写`arad`

Arad的特性
=========
* MVP设计架构
* 注解开发，参考[Butterknife](https://github.com/JakeWharton/butterknife)，大大简化代码，注重业务的开发
* Http使用[Retrofit](https://square.github.io/retrofit/)，配合RxJava2，okhttp
* 图片异步加载类库[Glide](https://github.com/bumptech/glide)  google官方推荐
* 数据库模块，LiteOrm框架，一行代码就可以进行增删改查。支持一对多，多对一等查询
* Util 常用方法的集合
* 多控件的支持，基础adapter，recycleview上拉下拉，常用页面切换动画，http统一错误处理等
* 内置EventBus 事件总线
* 支持ACC中的Lifecycle,ViewModel架构组件,完美的结合到了MVP上。
* 支持RxLifecycle
* 支持腾讯开源的[MMKV](https://github.com/Tencent/MMKV) 高性能key-value组件

Arad快速上手
===========
  Gradle 引入方式
  ```
  compile 'com.beanu:arad:1.1.1'
  ```
  
  1. 继承AradApplication，配置ApplicationConfig
  
  ```java
  public class MyApplication extends AradApplication {
    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
  }
  ```
  
  2. 开始使用Arad，在全局的任何地方
  
  ```java
  
  //Http 参考rxjava+retrofit+okhttp
  
  //图片下载
    Arad.imageLoader.load(url).into(imageView);

  //数据库访问
    Arad.db.findAll(Classname);
    Arad.db.save(entity);
    Arad.db.update(entity,strWhere);
  
  //数据存储
    Arad.preferences.put(key,value);
    Arad.preferences.flush();
    
  //数据存储mmkv
    Arad.kv.put(key,value);
    
  //发送事件
    Arad.bus.post(event);

  ```


推荐项目
===================
* [PhotoView](https://github.com/chrisbanes/PhotoView) 图片手势缩放库
* [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
* [android-crop](https://github.com/jdamcd/android-crop) 图片剪切类库 (有 OOM bug,所以自写了一个，使用arad-crop)
