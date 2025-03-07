package indi.pplong.acreader.core

import android.util.Log
import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.ArchiveInputStream
import org.apache.commons.compress.archivers.ArchiveStreamFactory
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry

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
        ZipArchiveInputStream(zipFileStream, "UTF-8").use { zipInputStream ->
            var zipEntry: ZipEntry?
            while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                val filePath = targetDirectory + File.separator + zipEntry!!.name
                val newFile = File(filePath)
                Log.d("123", "newfile ${newFile.path}")

                if (zipEntry!!.isDirectory) {
                    newFile.mkdirs()
                } else {
                    // if has parent dir, make sure it exists
                    val parentDir = File(filePath).parentFile
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs()
                    }

                    FileOutputStream(newFile).use { fileOutputStream ->
                        val buffer = ByteArray(1024)
                        var len: Int
                        while (zipInputStream.read(buffer).also { len = it } != -1) {
                            fileOutputStream.write(buffer, 0, len)
                        }
                    }
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}