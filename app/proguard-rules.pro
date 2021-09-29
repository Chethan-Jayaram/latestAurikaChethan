-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keep public class com.mobisprint.aurika.coorg.pojo.**
-keep public class com.mobisprint.aurika.coorg.fragments.**


-keepclasseswithmembers class * {
  public void onPayment*(...);
}