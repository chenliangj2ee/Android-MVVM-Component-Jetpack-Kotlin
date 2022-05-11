package com.chenliang.baselibrary.view

import android.content.Context
import android.net.http.SslError
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import com.chenliang.baselibrary.utils.log

/**
 * author:chenliang
 * date:2022/3/10
 */
class MyWebView : WebView {

    var type = 2

    var error = false
    var success = true

    object Type {
        var CONTENT = 0
        var IMAGE = 1
        var PATH = 2
    }

    var loadFinish: (() -> Unit?)? = null
    var loadError: (() -> Unit?)? = null

    constructor(context: Context?) : super(context!!) {
        initWebView()
    }

    constructor(context: Context?, attributeSet: AttributeSet) : super(context!!, attributeSet) {
        overScrollMode = View.OVER_SCROLL_NEVER;

        initWebView()
    }

    private fun initWebView() {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        webViewClient = MyWebViewClient(this)
        webChromeClient = MyWebChromeClient()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        addJavascriptInterface(this, "JS");
    }

    private var jsBridge: (() -> Unit?)? = null
    private var jsBridges: ((args: String?) -> Unit?)? = null

    fun jsBridgeListener(func: () -> Unit?) {
        jsBridge = func
    }

    fun jsBridgesListener(func: (args: String?) -> Unit?) {
        jsBridges = func
    }

    @JavascriptInterface
    fun jsCallBridge() {
        log("JS jsCallBridge .....")
        jsBridge?.let { it() }
    }

    @JavascriptInterface
    fun jsCallBridge(args: String?) {
        log("JS jsCallBridge args:"+args.toString())
        jsBridges?.let { it(args) }
    }


    fun loadFinish(loadFinish: () -> Unit) {
        this.loadFinish = loadFinish
    }

    fun loadError(loadError: () -> Unit) {
        this.loadError = loadError
    }

    fun load(str: String, type: Int) {
        if (type == Type.IMAGE) {
            loadDataWithBaseURL(
                null,
                "<body style='margin:0;padding:0'><img  src=$str width='100%'></body>",
                "text/html",
                "charset=UTF-8",
                null
            );
        }

        if (type == Type.CONTENT) {
            loadDataWithBaseURL(null, str, "text/html", "charset=UTF-8", null);
        }

        if (type == Type.PATH) {
            loadUrl(str)
        }
    }
}


class MyWebViewClient(myWebView: MyWebView) : WebViewClient() {
    private var myWebView = myWebView
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (!myWebView.error) {
            myWebView.success = true

        }
        myWebView.error = false
        if (myWebView.success) {
            myWebView.loadFinish?.let { it() }
        }

    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {

        log("加载失败......")
        myWebView.error = true
        myWebView.success = false
        if (myWebView.error)
            myWebView.loadError?.let { it() }
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        handler?.proceed()
        super.onReceivedSslError(view, handler, error)
    }


}

class MyWebChromeClient : WebChromeClient() {


}