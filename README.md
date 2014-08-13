Arad
====
##Arad-android rapid application development

###android快速应用开发框架

Arad的特性
=========
* 全注解的开发，参考androidannotations，大大简化代码，注重业务的开发。并且是编译时的注解解析，对运行时速度没有任何影响
* Http使用Volley框架（google I/O 大会上推荐的）一个比喻成快如箭的http访问框架，支持缓存
* Imageloader异步加载图片（Picasso）
* 数据库模块，android中的orm框架，一行代码就可以进行增删改查。支持一对多，多对一等查询
* Util 常用方法的集合
* 多个控件的支持（如果控件带有资源文件，则被分配到library-project中，需要的时候，再加入依赖工程）

Arad快速上手
===========
  1.继承AradApplication，配置ApplicationConfig
  ---------------------
  
  2.继承

Arad使用到的依赖包
===========
* ActionBarCompat: http://android-developers.blogspot.com/2013/08/actionbarcompat-and-io-2013-app-source.html
* androidannotations https://github.com/excilys/androidannotations
* Volley https://android.googlesource.com/platform/frameworks/volley
* Jackson https://github.com/FasterXML
* Picasso https://github.com/square/picasso

感谢以下项目的支持
==================
* afinal: https://github.com/yangfuhai/afinal(afinal的数据库orm)
* akita: https://github.com/xjanker/akita(工具的支持)


常用的依赖包
===================
*ImageViewZoom https://github.com/sephiroth74/ImageViewZoom
*Android-ViewPagerIndicator https://github.com/JakeWharton/Android-ViewPagerIndicator