# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\sdt1\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-----------------混淆配置设定------------------------------------------------------------------------
#-optimizationpasses 5                                                       #指定代码压缩级别
#-dontusemixedcaseclassnames                                                 #混淆时不会产生形形色色的类名
#-dontskipnonpubliclibraryclasses                                            #指定不忽略非公共类库
#-dontpreverify                                                              #不预校验，如果需要预校验，是-dontoptimize
#-ignorewarnings                                                             #屏蔽警告
#-verbose                                                                    #混淆时记录日志
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*    #优化，混淆算法

#-----------------导入第三方包,但是在当前版本中使用会报 input jar file is specified twice 错误，所以注释掉
#-libraryjars libs/android.support.v4.jar
#-libraryjars libs/BaiduLBS_Android.jar
#-libraryjars libs/commons-httpclient-3.1.jar
#-libraryjars libs/jackson-annotations-2.1.4.jar
#-libraryjars libs/jackson-core-2.1.4.jar
#-libraryjars libs/jackson-databind-2.1.4.jar
#-libraryjars libs/xUtils-2.6.14.jar

#-----------------不需要混淆第三方类库------------------------------------------------------------------
# 过滤第三方包的混淆：其中packagename为第三方包的包名
# -keep class packagename.** {*;}
#-keep class com.squareup.okhttp3.** {*;}
#-keep class com.github.bumptech.glide.** {*;}
#-keep class com.lovedise.** {*;}
#-keep class com.github.chrisbanes.** {*;}
#-keep class de.hdodenhof.** {*;}
#-keep class cn.jiguang.sdk.** {*;}
#-keep class com.android.support.** {*;}
#-keep class com.youth.banner.** {*;}
#-keep class com.jcodecraeer.** {*;}
#-keep class com.github.jdsjlzx.** {*;}
#-keep class com.zhy.** {*;}
#
#-libraryjars libs/wechat-sdk-android-with-mta-1.0.2.jar
#-libraryjars libs/httpclient-4.2.5.jar
#-libraryjars libs/httpcore-4.2.4.jar



##保持greenDao的方法不被混淆
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
##用来保持生成的表名不被混淆
#    public static java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
##-----------------不需要混淆系统组件等-------------------------------------------------------------------
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.preference.Preference
#-keep public class com.android.vending.licensing.ILicensingService
#
##过滤library项目（）
#
#-keep class org.apache.**{*;}                                               #过滤commons-httpclient-3.1.jar
#-keep class com.fasterxml.jackson.**{*;}                                    #过滤jackson-core-2.1.4.jar等
#-dontwarn com.baidu.**                                                      #去掉警告
#-dontwarn com.baidu.mapapi.**
#-keep class com.baidu.** {*;}                                               #过滤BaiduLBS_Android.jar
#-keep class vi.com.gdi.bgl.android.**{*;}
#-keep class com.baidu.platform.**{*;}
#-keep class com.baidu.location.**{*;}
#-keep class com.baidu.vi.**{*;}
#
#
#-keep public class javax.**
#
#-keep class de.greenrobot.event.** {*;}
#-keep class de.greenrobot.** {*;}
#
#
#-dontoptimize
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#
#
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#    @butterknife.* <fields>;
#}
#-keepclasseswithmembernames class * {
#    @butterknife.* <methods>;
#}
#-dontwarn cn.jiguang.**
#-keep class cn.jiguang.** { *; }
#
#-keepattributes *Annotation*
#
#-keepclassmembers class ** {
#    @de.greenrobot.event.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum de.greenrobot.event.ThreadMode { *; }
##----------------保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在------------------------------------
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#
#-keep class com.alibaba.fastjson.** { *; }
#-dontwarn com.alibaba.fastjson.**
#-keep class com.readystatesoftware.systembartint.** { *; }
#-dontwarn com.readystatesoftware.systembartint.**
#
#-dontwarn com.squareup.okhttp3.**
#-keep class com.squareup.okhttp3.** { *;}
#-dontwarn okio.**
#
#
#
#
#-dontwarn android.support.v4.**                                             #去掉警告
#-keep class android.support.v4.** { *; }                                    #过滤android.support.v4
#-keep interface android.support.v4.app.** { *; }
#-keep public class * extends android.support.v4.**
#
#-dontwarn android.support.v7.**                                             #去掉警告
#-keep class android.support.v7.** { *; }                                    #过滤android.support.v7
#-keep interface android.support.v7.app.** { *; }
#-keep public class * extends android.support.v7.**
#
#
#-keep class com.yonyou.framework.library.** {*;}
#-keep public class [com.yonyou.framework.library].R$*{
#    public static final int *;
#}
#
#-libraryjars libs/fastjson-1.1.31.jar
#-libraryjars libs/badgeview.jar
#-libraryjars libs/systembartint-1.0.4.jar
#-libraryjars libs/nineoldandroids-2.4.0.jar
#-libraryjars libs/httpclient-4.5.1.jar
#-libraryjars libs/httpcore-4.4.3.jar