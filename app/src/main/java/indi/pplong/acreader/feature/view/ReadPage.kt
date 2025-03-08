package indi.pplong.acreader.feature.view

import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader
import java.io.File

/**
 * Description:
 * @author PPLong
 * @date 3/7/25 9:21 AM
 */

@Composable
fun ReadPage(modifier: Modifier = Modifier, id: Int) {
    val file =
        File("/data/user/0/indi.pplong.acreader/files/books/msf:1000018671/OEBPS/html/app01.html")
    var htmlContent by remember { mutableStateOf("") }
    WebView.setWebContentsDebuggingEnabled(true)
    LaunchedEffect(Unit) {
        file.bufferedReader().use {
            htmlContent = it.readText()
            Log.d("123", "1111")
        }
    }
    val list = mutableListOf<Int>()
    list.add(Color.Red.toArgb())
    list.add(Color.Green.toArgb())
    list.add(Color.Blue.toArgb())
    list.add(Color.Gray.toArgb())
    list.add(Color.White.toArgb())
    val context = LocalContext.current
    // 动态注入 CSS

    val pageState = rememberPagerState(
        pageCount = { 8 }
    )

    HorizontalPager(
        state = pageState,
        beyondViewportPageCount = 1,
        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
    ) { page ->
        val webView = remember(key1 = page) {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                val assetLoader = WebViewAssetLoader.Builder()
                    .addPathHandler(
                        "/books/",
                        WebViewAssetLoader.InternalStoragePathHandler(
                            context,
                            File(context.filesDir.path + "/books/msf:1000018671") // 指定 EPUB 存储目录
                        )
                    )
                    .build()

                webViewClient = object : WebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): WebResourceResponse? {
                        return assetLoader.shouldInterceptRequest(request!!.url)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

//                                view?.evaluateJavascript(
//                                    """
//            (function() {
//                var images = document.getElementsByTagName('img');
//                for (var i = 0; i < images.length; i++) {
//                    images[i].style.maxWidth = '100%';
//                    images[i].style.height = 'auto';
//                }
//                document.body.style.overflowX = 'hidden';
//            })();
//            """.trimIndent(), null
//                                )

                        val css = """
        .page {
            height: 100%; /* 每页高度为屏幕高度 */
            width: 100%; /* 每页宽度为屏幕宽度 */
            overflow: hidden; /* 防止内容溢出 */
            page-break-inside: avoid; /* 避免内容被分割 */
            page-break-after: always; /* 每页结束后强制分页 */
        }
        /* 防止图片和文字被分割 */
        img, p {
            page-break-inside: avoid;
        }
    """.trimIndent()
                        val injectCss = """
                    var style = document.createElement('style');
                    style.type = 'text/css';
                    style.innerHTML = `$css`;
                    document.head.appendChild(style);
                """
                        val js = """
document.body.style.overflow = 'hidden';
document.body.style.touchAction = 'none';

// 获取可见高度（WebView 的高度）
function getVisibleHeight() {
    return window.innerHeight;
}

// 获取内容高度（整个 HTML 内容的高度）
function getContentHeight() {
    return document.documentElement.scrollHeight;
}

// 计算总页数
function getTotalPages() {
    var visibleHeight = getVisibleHeight();
    var contentHeight = getContentHeight();
    return Math.ceil(contentHeight / visibleHeight);
}

// 翻页逻辑
function scrollToPage(pageNumber) {
    var visibleHeight = getVisibleHeight();
    var totalPages = getTotalPages();
    var targetScrollY = pageNumber * visibleHeight;

    // 确保目标滚动位置不超过最大滚动高度
    targetScrollY = Math.min(targetScrollY, (totalPages - 1) * visibleHeight);
    
    window.scrollTo(0, targetScrollY);
}

// 初始化分页
function initPaging() {
    var totalPages = getTotalPages();
    console.log("总页数: " + totalPages);
    scrollToPage(0); // 默认滚动到第一页
}

// 初始化
initPaging();
    """.trimIndent()
                        view?.evaluateJavascript(injectCss, null)
                        view?.evaluateJavascript(js, null)
                        val jsGetTotalPages = """
        (function() {
            return getTotalPages();
        })();
    """.trimIndent()
                        view?.evaluateJavascript(jsGetTotalPages) { totalPages ->
                            Log.d("123", "Total Pages from JS: $totalPages")
                        }
                        view?.evaluateJavascript("scrollToPage(${page})", null)
                        Log.d("123", "Current Page:$page.")
                        Log.d(
                            "123",
                            "webview heigh: ${this@apply.height} width: ${this@apply.width}"
                        )
                    }
                }

                loadUrl("https://appassets.androidplatform.net/books/OEBPS/html/app01.html")
                Log.d("123", "build html")
            }
        }
        AndroidView(

            factory = { context ->
                webView
            },
            update = { webView ->

            },
            modifier = Modifier.fillMaxSize(),
        )
    }

}