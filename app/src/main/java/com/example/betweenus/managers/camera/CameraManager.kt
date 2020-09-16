package com.example.betweenus.managers.camera

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@Suppress("DEPRECATION")
class CameraManager {
    private val context by inject(Context::class.java)

    var currentPhotoPath: String? = null
        private set

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
    }

    fun getCameraIntent(): Intent? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(context.packageManager)?.also {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI = FileProvider.getUriForFile(
                    context,
                    "com.example.betweenus.fileprovider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
        }
        return intent
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    suspend fun getTakenPic(): Bitmap? {
        return withContext(Dispatchers.IO) {
            currentPhotoPath ?: return@withContext null
            return@withContext BitmapFactory.decodeFile(currentPhotoPath)
        }
    }

    suspend fun addPicToGallery() {
        withContext(Dispatchers.IO) {
            currentPhotoPath ?: return@withContext
            val file = File(currentPhotoPath!!)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + "/${context.applicationInfo.loadLabel(
                            context.packageManager
                        )}"
                    )
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
                val collection =
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val uri = context.contentResolver.insert(collection, values)
                uri?.let {
                    context.contentResolver.openOutputStream(it)?.use { outStream ->
                        FileUtils.copy(FileInputStream(file), outStream)
                    }
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    context.contentResolver.update(uri, values, null, null)
                }
            } else {
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    file.absolutePath, file.name, null
                )
            }
        }
    }
}
