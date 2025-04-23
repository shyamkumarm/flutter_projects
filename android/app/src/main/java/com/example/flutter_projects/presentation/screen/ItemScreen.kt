package com.example.flutter_projects.presentation.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.flutter_projects.domain.User
import com.example.flutter_projects.presentation.MyUserDataViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File


@Composable
fun UserInputScreen(
    modifier: Modifier,
    viewModel: MyUserDataViewModel = koinViewModel<MyUserDataViewModel>(),
     context: Context = LocalContext.current
) {

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var signature by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var updatedImage by remember { mutableStateOf(false) }


    val viewState = viewModel.userData.collectAsStateWithLifecycle()

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            updatedImage = isSuccess
            Log.d("shyam","isSuccess $isSuccess")
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("User Form", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Signature", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))
        Text("Profile Picture", fontWeight = FontWeight.Medium)

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray)
                .clickable {
                   createImageUri(context).run {
                       photoUri = this
                       cameraLauncher.launch(this)
                    }

                },
            contentAlignment = Alignment.Center
        ) {
            if (updatedImage) {
                AsyncImage(
                    model = photoUri,
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
                User(
                    name = name, address = address, phoneNumber = phone.toLong(),
                    profilePic = photoUri.toString()
                )
            )
        }) {
            Text("Submit")
        }
    }


}


fun createImageUri(context: Context): Uri {
    val imageFile = File(context.filesDir, "profile_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileProvider",
        imageFile
    )
}

