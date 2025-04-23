package com.example.flutter_projects.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint.Cap
import android.graphics.Paint.Join
import android.graphics.Paint.Style
import android.graphics.Path
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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


    fun getSignatureBitmap(
        path: Path,
        width: Int,
        height: Int,
        strokeWidths: Float = 4f,
        strokeColor: Int = Color.Black.toArgb(),
        bgColor: Int = Color.White.toArgb()
    ): Bitmap {
        val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)

        // Optional background
        canvas.drawColor(bgColor)

        val paint = android.graphics.Paint().apply {
            color = strokeColor
            style = Style.STROKE
            strokeWidth = strokeWidths
            isAntiAlias = true
            strokeCap = Cap.ROUND
            strokeJoin = Join.ROUND
        }

        canvas.drawPath(path, paint)

        return bitmap
    }


    fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.filesDir, "signature_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        outputStream.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}