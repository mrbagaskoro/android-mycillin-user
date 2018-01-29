# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\16003041\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# GreenDAO
-keep class org.greenrobot.greendao.**
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.**
-dontwarn org.greenrobot.greendao.database.**

# Retrofit
-keep class com.squareup.retrofit2.**
-keep class okio.**
-keep class retrofit2.**
-keep class android.databinding.**
-keep class org.apache.http.**
-keep class android.net.http.**
-dontwarn com.squareup.retrofit2.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn android.databinding.**
-keepattributes *Annotation*

# CarouselView
-keep class com.synnapps.carouselview.** { *; }
-dontwarn com.synnapps.carouselview.**

# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**