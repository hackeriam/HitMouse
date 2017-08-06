package com.hitmouse;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActivityEmoh extends Activity {
    private WebView webview;
    //private TextView beginLoading,endLoading,loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //title:当sdk大于等于5.0时
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }




        //webView
        //创建WebView方法一
            //实例化WebView对象
            webview = new WebView(this);

        //创建WebView方法二
//        webview = (WebView) findViewById(R.id.webView1);
//        beginLoading = (TextView) findViewById(R.id.text_beginLoading);
//        endLoading = (TextView) findViewById(R.id.text_endLoading);
//        loading = (TextView) findViewById(R.id.text_Loading);

        //子类设置
                //设置WebSettings类【作用：对WebView进行配置和管理
                    //方法
                        //Javascript脚本
                            //设置WebView属性，能够执行Javascript脚本
                            webview.getSettings().setJavaScriptEnabled(true);
                            // 设置允许JS弹窗
                            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

                        //支持插件
                            //webview.getSettings().setPluginsEnabled(true);

                        //图片
                            //支持自动加载图片
                            webview.getSettings().setLoadsImagesAutomatically(true);
                            //将图片调整到适合webview的大小
                            webview.getSettings().setUseWideViewPort(true);
                            // 缩放至屏幕的大小
                            webview.getSettings().setLoadWithOverviewMode(true);

                        //支持缩放
                            //设置缩放等级
                            webview.setInitialScale(100);//为25%，最小缩放等级 里面的数字代表缩放等级:web.setInitialScale(100);  代表不缩放。

                                //支持缩放，默认为true。是下面那个的前提。
                                    //缩放开关，设置此属性，仅支持双击缩放，不支持触摸缩放
                                webview.getSettings().setSupportZoom(true);
                                //设置内置的缩放控件。若为false，则该WebView不可缩放
                                    //设置是否可缩放，会出现缩放工具（若为true则上面的设值也默认为true）
                                webview.getSettings().setBuiltInZoomControls(true);
                                //隐藏原生的缩放控件
                                webview.getSettings().setDisplayZoomControls(false);
                                //加载的html代码中，必须去掉<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />这一句。这句加上就不支持缩放了。

                        //设置可以访问文件
                        webview.getSettings().setAllowFileAccess(true);


                //设置WebChromeClient类[作用：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
                webview.setWebChromeClient(new WebChromeClient() {

                    //获取加载进度
//                    @Override
//                    public void onProgressChanged(WebView view, int newProgress) {
//                        if (newProgress < 100) {
//                            String progress = newProgress + "%";
//                            loading.setText(progress);
//                        } else if (newProgress == 100) {
//                            String progress = newProgress + "%";
//                            loading.setText(progress);
//                        }
//                    }
                });


                //设置WebViewClient类【作用：处理通知和请求事件
                webview.setWebViewClient(new WebViewClient(){
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
//                            @Override
//                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                                System.out.println("开始加载了");
//                                beginLoading.setText("开始加载了");
//                            }

                            //设置结束加载函数
//                            @Override
//                            public void onPageFinished(WebView view, String url) {
//                                endLoading.setText("结束加载了");
//                            }
                });



        //加载需要显示的网页
        webview.loadUrl("http://cn.bing.com/");

        //创建WebView方法一
            //设置Web视图
            setContentView(webview);

        //创建WebView方法二
            //设置视图
            //setContentView(R.layout.activity_emoh);
    }

            //设置回退:点击返回上一页面而不是退出浏览器
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
                    webview.goBack();
                    return true;
                }
                return false;
            }
}

