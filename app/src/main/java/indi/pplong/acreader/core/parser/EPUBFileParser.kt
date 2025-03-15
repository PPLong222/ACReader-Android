package indi.pplong.acreader.core.parser

import indi.pplong.acreader.core.read.model.book.BookChapter
import indi.pplong.acreader.feature.shelf.model.EBookParseEntry
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.select.NodeVisitor
import java.io.File

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 6:04 PM
 */
class EPUBFileParser(filePath: String) : AbstractEBookFileParser(filePath) {
    companion object {
        const val META_INF_DIR_NAME = "META-INF"
        const val CONTAINER_FILE_NAME = "container.xml"
    }

    private lateinit var epubFilePath: String
    private lateinit var epubDirPath: String
    val chapterList = mutableListOf<EBookParseEntry>()

    init {
        locateContentPath()
    }

    private fun locateContentPath() {
        val metaInfoFile =
            File(basicPath + File.separator + META_INF_DIR_NAME + File.separator + CONTAINER_FILE_NAME)
        val doc = Jsoup.parse(metaInfoFile, "UTF-8", "")
        val rootFile = doc.select("rootfile").first()
        if (rootFile == null) {
            return
        }
        epubFilePath = basicPath + File.separator + rootFile.attr("full-path")
        epubDirPath = File(epubFilePath).parent ?: return
    }

    override fun parseFile() {
        val epubFile = File(epubFilePath)

        val doc = Jsoup.parse(epubFile, "UTF-8")
        title = doc.select("metadata > dc|title").first()?.text()

        val coverMeta = doc.select("metadata > meta[name=cover]").first()
        if (coverMeta != null) {
            // 先读取标签里的content attr
            val coverContent = coverMeta.attr("content")
            /*
               如果该值只是一个索引的key值, 则manifest看是否有额外声明该资源, 有则使用
               如果没有, 则说明已是资源地址
             */
            val coverItem = doc.select("manifest > item[id=$coverContent]").first()
            if (coverItem != null) {
                coverPath = epubDirPath + File.separator + coverItem.attr("href")
            } else {
                coverPath = epubDirPath + File.separator + coverContent
            }
        }
    }

    override fun parseTocInfo() {
        val epubFile = File(epubFilePath)
        val tocFile = File(epubFile.parentFile, "toc.ncx")
        val doc = Jsoup.parse(tocFile, "UTF-8")

        // 不使用直接元素, 而是所有的, 因为navPoint可能嵌套
        val navPoints = doc.select("navMap navPoint")

        navPoints.forEach { navPoint ->
            val chapterName = navPoint.select("navLabel > text").first()?.text() ?: ""
            val order = navPoint.attr("playOrder").toInt()
            var src = navPoint.select("content").attr("src")
            // 这里暂时先把.xhtml后的#xxxx索引去掉
            if (src.contains(".xhtml#")) {
                src = src.substringBeforeLast("#")
            }
            val path = File(epubFile.parentFile, src).path

            chapterList.add(EBookParseEntry(-1, order, chapterName, path))
        }
    }

    fun parseChapter(chapterUri: String): BookChapter {
        val doc = Jsoup.parse(File(chapterUri), "UTF-8")
        // TODO: 不同的epub这里title不一定在h1中

        val title = doc.selectFirst("h1")?.text() ?: ""
        val list = arrayListOf<String>()
        doc.body().traverse(object : NodeVisitor {
            override fun head(node: Node, depth: Int) {
                if (node is Element) {
                    if (node.tagName().equals("p")) {
                        list.add(node.text())
                        return
                    } else if (node.tagName().equals("img")) {
                        list.add(node.outerHtml())
                        return
                    }
                }
            }

        })
        return  BookChapter(title, list)
    }

}