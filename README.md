Arad
====

Arad-android rapid application development
android快速应用开发框架

Arad的特性
=========
* BaseActivity支持IOC，通过注解来引用TextView,Button,ImageView等基本控件，不再需要findViewById
* Http的封装
* Imageloader异步加载图片
* 数据库模块，android中的orm框架，一行代码就可以进行增删改查。支持一对多，多对一等查询
* 支持页面的手势滑动检测
* 多个控件的支持（如果控件带有资源文件，则被分配到library-project中，不需要的时候，不用加入依赖工程）

Arad快速上手
===========
  1.继承AradApplication
  ---------------------
  
  2.继承AradActivity

Arad依赖项目
===========
* ActionBarSherlock: https://github.com/JakeWharton/ActionBarSherlock
* Jackson jar解析json文件

感谢以下项目的支持
==================
* afinal: https://github.com/yangfuhai/afinal(再次封装afinal的四个模块)
* akita: https://github.com/xjanker/akita(工具的支持)


TODO
====
* 加入异常处理
* 解决UI事件和数据的分离
