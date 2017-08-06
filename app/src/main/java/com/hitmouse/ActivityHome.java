package com.hitmouse;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;//全屏
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class ActivityHome extends Activity {
    WebView mWebview;
    WebSettings mWebSettings;
    TextView loading;
    //TextView beginLoading,endLoading,loading,mtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //title:当sdk大于等于5.0时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }





        //webView
            //创建WebView方法二
            mWebview = (WebView) findViewById(R.id.webView1);
//            beginLoading = (TextView) findViewById(R.id.text_beginLoading);
//            endLoading = (TextView) findViewById(R.id.text_endLoading);
            loading = (TextView) findViewById(R.id.text_Loading);


        //子类设置
            //设置WebSettings类【作用：对WebView进行配置和管理
                //初始化
        mWebSettings = mWebview.getSettings();



        //加载需要显示的网页
        mWebview.loadUrl("http://cn.bing.com/");



                //设置WebSettings类【作用：对WebView进行配置和管理
                    //方法
                        //Javascript脚本
                            //设置WebView属性，能够执行Javascript脚本
                            mWebSettings.setJavaScriptEnabled(true);
                            // 设置允许JS弹窗
                            mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

                        //支持插件
                            //mWebSettings.setPluginsEnabled(true);

                        //图片
                            //支持自动加载图片
                            mWebSettings.setLoadsImagesAutomatically(true);
                            //将图片调整到适合webview的大小
                            mWebSettings.setUseWideViewPort(true);
                            // 缩放至屏幕的大小
                            mWebSettings.setLoadWithOverviewMode(true);

                        //支持缩放
                            //设置缩放等级
                            mWebview.setInitialScale(100);//为25%，最小缩放等级 里面的数字代表缩放等级:web.setInitialScale(100);  代表不缩放。

                                //支持缩放，默认为true。是下面那个的前提。
                                    //缩放开关，设置此属性，仅支持双击缩放，不支持触摸缩放
                                mWebSettings.setSupportZoom(true);
                                //设置内置的缩放控件。若为false，则该WebView不可缩放
                                    //设置是否可缩放，会出现缩放工具（若为true则上面的设值也默认为true）
                                mWebSettings.setBuiltInZoomControls(true);
                                //隐藏原生的缩放控件
                                mWebSettings.setDisplayZoomControls(false);
                                //加载的html代码中，必须去掉<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />这一句。这句加上就不支持缩放了。

                        //设置可以访问文件
                       mWebSettings.setAllowFileAccess(true);



        //设置WebViewClient类【作用：处理通知和请求事件
            //设置不用系统浏览器打开,直接显示在当前Webview
//            mWebview.setWebViewClient(new WebViewClient() {
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    //方法
//                    view.loadUrl(url);
//                    return true;
//                }
//            });



        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                System.out.println("标题在这里");
//                mtitle.setText(title);
//            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //方法
                //重写此方法可以让webview处理链接的跳转方式：即不调用系统浏览器只在当前的app webView中跳转
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                //重写此方法才能够处理在浏览器中的按键事件。
                @Override
                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                    return super.shouldOverrideKeyEvent(view, event);
                }

                //加载
                    //设置加载前的函数
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        //System.out.println("开始加载了");
                        //beginLoading.setText("开始加载了");
                        loading.setVisibility(View.VISIBLE);
                    }

                    //设置结束加载函数
//                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //endLoading.setText("结束加载了");
                        loading.setVisibility(View.INVISIBLE);
                    }

            // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    return;
                }
                // 在这里显示自定义错误页
                mWebview.loadUrl("file:///android_asset/home/index.html");
            }

            // 新版本，只会在Android6及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()){ // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    // 在这里显示自定义错误页
                    mWebview.loadUrl("file:///android_asset/home/index.html");
                }
            }

        });
    }

    //设置回退:点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return false;
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}
