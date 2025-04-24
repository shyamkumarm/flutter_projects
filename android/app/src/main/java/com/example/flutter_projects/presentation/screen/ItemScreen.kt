package com.example.flutter_projects.presentation.screen

import android.content.Context
import android.graphics.Picture
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.presentation.MyUserDataViewModel
import com.example.flutter_projects.presentation.UserUiState
import com.example.flutter_projects.utils.AppUtils
import org.koin.androidx.compose.koinViewModel


var drawScope: DrawScope? = null

@Composable
fun UserInputScreen(
    modifier: Modifier,
    viewModel: MyUserDataViewModel = koinViewModel<MyUserDataViewModel>(),
    context: Context = LocalContext.current
) {

    var user by remember { mutableStateOf(User("", "", "", null, null, null)) }

    var updatedImage by remember { mutableStateOf(false) }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val picture by remember { mutableStateOf(Picture()) }


    val viewState by viewModel.userData.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            updatedImage = isSuccess

        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        when (viewState) {
            is UserUiState.Success -> {
                ShowMessage(context, "saved success")
                Log.d("Data", "${(viewState as UserUiState.Success).savedItem}")
                focusRequester.requestFocus()
            }

            is UserUiState.Error -> {
                ShowMessage(context, (viewState as UserUiState.Error).message)
            }

            is UserUiState.Init -> {}
            else -> {
                ShowLoading()
            }
        }

        Text("User Form", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        OutlinedTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = user.name,
            onValueChange = { user = user.copy(name = it) },
            label = { Text("Name") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = user.address,
            onValueChange = { user = user.copy(address = it) },
            label = { Text("Address") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = user.phoneNumber,
            onValueChange = { user = user.copy(phoneNumber = it) },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Signature", fontWeight = FontWeight.Medium)


        SignatureCanvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(1.dp, Color.Gray), picture = picture
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Profile Picture", fontWeight = FontWeight.Medium)

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray)
                .clickable {
                    AppUtils.createImageUri(context).run {
                        user.profilePic = this
                        cameraLauncher.launch(this)
                    }

                },
            contentAlignment = Alignment.Center
        ) {
            if (updatedImage) {
                AsyncImage(
                    model = user.profilePic,
                    contentDescription = "Captured Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Tap to capture", color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.save(
                user.copy(path = picture)
            )
        }) {
            Text("Submit")
        }
    }


}


@Composable
fun ShowLoading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
fun ShowMessage(context: Context, message: String) {
    LaunchedEffect(true) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


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



