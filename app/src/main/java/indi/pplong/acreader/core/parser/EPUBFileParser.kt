package indi.pplong.acreader.core.parser

import indi.pplong.acreader.feature.shelf.model.EBookParseEntry
import org.jsoup.Jsoup
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
            val coverId = coverMeta.attr("content")
            val coverItem = doc.select("manifest > item[id=$coverId]").first()
            if (coverItem != null) {
                coverPath = epubDirPath + File.separator + coverItem.attr("href")
            }
        }
    }

    override fun parseTocInfo() {
        val epubFile = File(epubFilePath)
        val tocFile = File(epubFile.parentFile, "toc.ncx")
        val doc = Jsoup.parse(tocFile, "UTF-8")

        val navPoints = doc.select("navMap > navPoint")

        navPoints.forEach { navPoint ->
            val chapterName = navPoint.select("navLabel > text").first()?.text() ?: ""
            val order = navPoint.attr("playOrder").toInt()
            val src = navPoint.select("content").attr("src")
            val path = File(epubFile.parentFile, src).path

            chapterList.add(EBookParseEntry(-1, order, chapterName, path))
        }
    }

}