package com.example.flutter_projects.presentation.screen

import android.graphics.Picture
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun SignatureCanvas(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 4f,
    strokeColor: Color = Color.Blue,
    picture: Picture
) {
    var lastPoint by remember { mutableStateOf<Offset?>(null) }
    val path by remember { mutableStateOf(Path()) }
    Canvas(
        modifier = modifier
            .clipToBounds()
            .drawWithCache {
                // Example that shows how to redirect rendering to an Android Picture and then
                // draw the picture into the original destination
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()
                onDrawWithContent {
                    val pictureCanvas =
                        androidx.compose.ui.graphics.Canvas(
                            picture.beginRecording(
                                width,
                                height
                            )
                        )
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()
                    drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        path.moveTo(offset.x, offset.y)
                        lastPoint = offset
                    },
                    onDrag = { change, _ ->
                        val point = change.position
                        lastPoint?.let {
                            path.lineTo(point.x, point.y)
                        }
                        lastPoint = point
                    },
                    onDragEnd = {
                        lastPoint = null
                    },
                    onDragCancel = {
                        lastPoint = null
                    }
                )
            }
    ) {
        lastPoint
        drawPath(
            path = path,
            color = strokeColor,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
    Button(onClick = {
        path.reset()
        lastPoint = Offset(0f, 0f)
    }) {
        Text("Clear")
    }
}