package indi.pplong.acreader.feature.view

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader
import ptq.mpga.ptqbookpageview.widget.PTQBookPageView
import ptq.mpga.ptqbookpageview.widget.rememberPTQBookPageViewState
import java.io.File

/**
 * Description:
 * @author PPLong
 * @date 3/7/25 9:21 AM
 */

@Composable
fun ReadPage(modifier: Modifier = Modifier) {
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
    val context = LocalContext.current
    var state by rememberPTQBookPageViewState(pageCount = 10, currentPage = 0)
    // 动态注入 CSS
    val css = """
    body {
        margin: 0;
        padding: 0;
        overflow: hidden;
        column-width: 100vw;
        column-gap: 0;
        height: 100vh;
    }
    .page {
        break-inside: avoid;
        page-break-inside: avoid;
    }
"""

// 动态注入 JavaScript
    val js = """
    let currentPage = 0;
    const pages = document.querySelectorAll('.page');

    function showPage(index) {
        if (index >= 0 && index < pages.length) {
            pages.forEach((page, i) => {
                page.style.display = i === index ? 'block' : 'none';
            });
            currentPage = index;
        }
    }

    showPage(0);

    let startX = 0;
    document.addEventListener('touchstart', (e) => {
        startX = e.touches[0].clientX;
    });
    document.addEventListener('touchend', (e) => {
        const endX = e.changedTouches[0].clientX;
        if (startX - endX > 50) {
            showPage(currentPage + 1);
        } else if (endX - startX > 50) {
            showPage(currentPage - 1);
        }
    });
"""


    PTQBookPageView(state = state) {
        onTurnPageRequest { currentPage, isNextOrPrevious, success ->
            if (success) {
                state =
                    state.copy(currentPage = if (isNextOrPrevious) currentPage + 1 else currentPage - 1)
            }
        }
        contents { currentPage, refresh ->
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true

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

                                // 执行 JavaScript，让所有图片适应 WebView
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
        body {
            overflow: hidden !important;
            margin: 0;
            padding: 0;
        }
    """.trimIndent()

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
    window.scrollTo(0, pageNumber * visibleHeight);
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

                                // 注入 JavaScript
                                view?.evaluateJavascript(js, null)
                                view?.evaluateJavascript("scrollToPage(2)", null)
                            }
                        }

                        isVerticalScrollBarEnabled = false
                        isHorizontalScrollBarEnabled = false
                        loadUrl("https://appassets.androidplatform.net/books/OEBPS/html/app01.html")

                    }
                },
                update = { webView ->
                    webView.reload()
                },
                modifier = Modifier.fillMaxSize()
            )

            refresh()
        }
    }

}