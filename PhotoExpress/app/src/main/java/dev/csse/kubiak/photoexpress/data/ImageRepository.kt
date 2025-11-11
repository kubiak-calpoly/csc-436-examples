package com.zybooks.photoexpress.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

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
    // coming up...
  }
}