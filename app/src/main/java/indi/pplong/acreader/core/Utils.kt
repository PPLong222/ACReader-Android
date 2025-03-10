package indi.pplong.acreader.core

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
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
    } else {
        return // Already unzipped before
    }

    try {
        ZipArchiveInputStream(zipFileStream, "UTF-8").use { zipInputStream ->
            var zipEntry: ZipEntry?
            while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                val filePath = targetDirectory + File.separator + zipEntry!!.name
                val newFile = File(filePath)
                // Log.d("Hust1037", "newfile ${newFile.path}")
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

/**
 * Get real file name(without suffix) form Uri
 */
fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var fileName: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0) {
                fileName = cursor.getString(nameIndex)
            }
        }
    }
    // Remove suffix
    fileName = fileName?.substringBeforeLast(".")
    // Generate a default name if unable to get the real name
    return fileName ?: "file_${System.currentTimeMillis()}"
}

/**
 * Copy original file to app's directory
 */
fun copyFileFromUri(context: Context, uri: Uri, destinationPath: String): File? {
    try {
        // Get real file name
        val fileName = getFileNameFromUri(context, uri) ?: return null
        val destinationFile = File(destinationPath, fileName)

        // Copy file
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return destinationFile
    } catch (e: Exception) {
        Log.e("FileCopy", "Failed to copy: ${e.message}")
        return null
    }
}
