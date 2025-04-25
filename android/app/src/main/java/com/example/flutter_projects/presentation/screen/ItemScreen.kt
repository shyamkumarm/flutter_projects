package com.example.flutter_projects.presentation.screen

import android.app.Activity
import android.content.Context
import android.graphics.Picture
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
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


@Composable
fun UserInputScreen(
    modifier: Modifier,
    viewModel: MyUserDataViewModel = koinViewModel<MyUserDataViewModel>(),
    context: Context = LocalContext.current
) {

    var user by remember { mutableStateOf(User("", "", "", null, null, null)) }

    var updatedImage by remember { mutableStateOf(false) }
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

            }

            is UserUiState.Error -> {
                ShowMessage(context, (viewState as UserUiState.Error).message)
            }

            is UserUiState.Init -> {}
            else -> {
                ShowLoading()
            }
        }
        val uiModifier = Modifier
            .fillMaxWidth()

        TextButton(onClick = { /* just for decoration*/ }) {
            Text(
                text = "User Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        OutlinedTextField(
            modifier = uiModifier,
            value = user.name,
            onValueChange = { user = user.copy(name = it) },
            label = { Text("Name") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = uiModifier,
            value = user.address,
            onValueChange = { user = user.copy(address = it) },
            label = { Text("Address") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = uiModifier,
            value = user.phoneNumber,
            onValueChange = { user = user.copy(phoneNumber = it) },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = uiModifier, verticalAlignment = Alignment.CenterVertically) {
            Text("Profile Picture", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray)
                    .clickable {
                        AppUtils.createImageUri(context).run {
                            user.profilePic = this.first
                            cameraLauncher.launch(this.second)
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(modifier = uiModifier, text = "Signature", fontWeight = FontWeight.Medium)
        Spacer(
            modifier = Modifier
                .height(5.dp)
        )
        SignatureCanvas(
            modifier = uiModifier
                .height(100.dp)
                .border(1.dp, Color.Gray), picture = picture
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = uiModifier, onClick = {
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
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    if (context is Activity) {
        context.finish()
    }
}




