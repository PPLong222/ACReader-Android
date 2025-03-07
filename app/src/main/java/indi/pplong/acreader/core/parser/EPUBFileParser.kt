package indi.pplong.acreader.core.parser

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

        val doc = Jsoup.parse(epubFile, "UTF-8", "")
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

}