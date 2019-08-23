# freeWithWxJumpGame
For Jump_Game in We-Chat, there is an easy way to arriving.

##关于这个java小程序

微信跳一跳在2018年初风靡一时，本代码仓库开源一种基于adb工具和javax.swing界面的跳一跳辅助软件

* javax.swing包写的图形界面是本程序的显著特征
* 需要用户点击“Pull SC”按钮将手机截图显示到图形用户界面上
* 注册鼠标事件监听器以在点击截图时获得起跳点和落脚点的位置
* 计算按压时间为pressTime后exec("adb shell input swipe 600 1000 600 1000 "+pressTime)完成跳跃

本程序仅供学习和娱乐，请不要用作其他用途

##代码获取及环境安装

首先来安装Git

* https://git-scm.com/downloads
* 从上面的地址下载并安装后打开控制台,通过cd d:命令进入D盘
* 往控制台输入命令git clone git@github.com:lu1198373615/freeWithWxJumpGame.git
* 短暂等待后，就可以在D盘根目录下找到本代码仓库所有代码及说明文档了

接下来配置jdk和jre环境

* jdk/jre链接:https://pan.baidu.com/s/1nmQnKJhE4ZCA-ZEdD9t1vA 提取码:439h
* 下载并安装后添加环境变量，变量值为jdk安装后javac.exe的所在路径

还需要配置adb环境

* 从链接`http://adbshell.com/downloads`下载`ADB Kits(525kb)`,解压到某一路径(免安装)并将这一路径配置到环境变量中

以上3步遇上困难可将问题发送至邮箱1198373615@qq.com，或添加QQ：1198373615与我联系

##如何运行程序

对于我试验使用OPPO A59s手机来说，截图保存路径："/sdcard/DCIM/Screenshots"；截图像素大小720*1280
但你的手机却不一定和我有相同的参数，截图像素大小我通过程序优化已解决不兼容的问题
但截图保存路径仍需你手动修改：

* 修改文件WxJumpGame.java第138行private final String screenshotPathOfPhone = "/sdcard/DCIM/Screenshots";
* 把路径改为您手机截图存放的地址,并保存文件。

代码获取时我们把代码文件放在了d盘根目录，那么以下将演示如何编译和运行程序

* cd freeWithWxJumpGame
* javac WxJumpGame.java
* java WxJumpGame

程序便开始运行了。遇上困难可将问题发送至邮箱1198373615@qq.com，或添加QQ：1198373615与我联系

##程序使用方法

* 安卓手机打开手机的“开发者模式”，选择“允许USB调试”，允许通过adb shell来进行手机操作
* 点击左上角的`Pull SC`按钮，将手机当前页面截图并显示到右侧的界面上
* 依次用鼠标点击截图中起跳点和落脚点，可以看到左下角已经计算出了跳跃的距离
* 大胆点击`JUMP`按钮，进行跳跃吧！！！

使用过程中极有可能出现跳跃距离不准确的问题，这也是由手机差异造成的

* 修改文件WxJumpGame.java第162行final double factor = 2.05;
* 这个值越大，跳跃距离越长，这个值越小，跳跃距离越短
* 请你多次修改并编译以得到适合你手机的值

微信的监管系统趋于完善，使用辅助软件获得的排名已经无法显示在好友可见排行榜上