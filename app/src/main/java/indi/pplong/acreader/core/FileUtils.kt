package indi.pplong.acreader.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 10:42 AM
 */

fun openFile(context: Activity, pickerInitialUri: Uri, resultCode: Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
    }
    startActivityForResult(context, intent, resultCode, null)
}