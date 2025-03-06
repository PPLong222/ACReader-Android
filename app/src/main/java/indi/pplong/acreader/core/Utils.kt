package indi.pplong.acreader.core

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:32 AM
 */

fun unzipFile(zipFileStream: InputStream?, targetDirectory: String) {
    val targetDir = File(targetDirectory)

    if (!targetDir.exists()) {
        targetDir.mkdirs()
    }

    try {
        val zipInputStream = ZipInputStream(zipFileStream)
        var zipEntry: ZipEntry?
        while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
            val filePath = targetDirectory + File.separator + zipEntry!!.name
            val newFile = File(filePath)


            if (zipEntry!!.isDirectory) {
                newFile.mkdirs()
            } else {
                // if has parent dir, make sure it exists
                val parentDir = File(filePath).parentFile
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs()
                }

                val fileOutputStream = FileOutputStream(newFile)
                val buffer = ByteArray(1024)
                var len: Int
                while (zipInputStream.read(buffer).also { len = it } != -1) {
                    fileOutputStream.write(buffer, 0, len)
                }
                fileOutputStream.close()
            }
            zipInputStream.closeEntry()
        }
        zipInputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}