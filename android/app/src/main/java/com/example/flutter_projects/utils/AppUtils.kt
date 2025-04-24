package com.example.flutter_projects.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import java.io.File
import java.io.FileOutputStream


object AppUtils {

    fun createImageUri(context: Context): Uri {
        val imageFile = File(context.filesDir, "profile_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileProvider",
            imageFile
        )
    }


    fun uriBitmap(context: Context, uri: Uri):Bitmap{
        return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }

    fun saveBitmapToFile(context: Context, path: Picture): Uri {
        val file = File(context.filesDir, "signature_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        outputStream.use {
            createBitmapFromPicture(path).run {
                compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                 recycle()
            }
            it.flush()
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileProvider", file)
    }


    private fun createBitmapFromPicture(picture: Picture): Bitmap {
        val bitmap = createBitmap(picture.width, picture.height)
        val canvas = Canvas(bitmap)
        canvas.drawColor(android.graphics.Color.WHITE)
        canvas.drawPicture(picture)
        return bitmap
    }
}
