Arad
====

##android快速应用开发框架

android rapid application development的简写`arad`

Arad的特性
=========
* 注解开发，参考[Butterknife](https://github.com/JakeWharton/butterknife)，大大简化代码，注重业务的开发
* Http使用[android-async-http](https://github.com/loopj/android-async-http)，标杆应用，异步http请求，多媒体文件上传下载，request线程池，大小只有60KB
* Imageloader异步加载图片（Picasso）
* 数据库模块，android中的orm框架，一行代码就可以进行增删改查。支持一对多，多对一等查询
* Util 常用方法的集合
* 多控件的支持（如果控件带有资源文件，则被分配到library-project中，需要的时候，再加入依赖工程）

Arad快速上手
===========
  
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
  //http请求

  //图片下载

  //数据库访问

  //view注解开发

  ```

感谢以下项目的支持
==================
* ActionBarCompat: http://android-developers.blogspot.com/2013/08/actionbarcompat-and-io-2013-app-source.html
* Jackson https://github.com/FasterXML
* Picasso https://github.com/square/picasso
* afinal: https://github.com/yangfuhai/afinal(afinal的数据库orm)
* akita: https://github.com/xjanker/akita(工具的支持)


推荐项目
===================
* [PhotoView](https://github.com/chrisbanes/PhotoView) 图片手势缩放库
* [Android-ViewPagerIndicator](https://github.com/JakeWharton/Android-ViewPagerIndicator)
* [android-crop](https://github.com/jdamcd/android-crop) 图片剪切类库 (有 OOM bug,所以自写了一个，使用arad-crop)
