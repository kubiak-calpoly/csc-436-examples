package dev.csse.kubiak.photoexpress.data

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageRepository(private val context: Context) {
  var photoFile: File? = null

  fun createPhotoFile(): Uri {
    val file = createEmptyImageFile()
    photoFile = file
    val photoUri =
      FileProvider.getUriForFile(
        context,
        "dev.csse.kubiak.photoexpress.fileprovider",
        file
      )
    return photoUri
  }

  fun createEmptyImageFile(): File {
    val timeStamp =
      SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
      .format(Date())
    val imageFilename = "photo_$timeStamp.jpg"

    // Create the file in scoped Pictures directory on external storage
    val storageDir = context.getExternalFilesDir(
      Environment.DIRECTORY_PICTURES
    )

    return File(storageDir, imageFilename)
  }
}