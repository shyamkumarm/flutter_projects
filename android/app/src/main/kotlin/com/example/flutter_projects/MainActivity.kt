package com.example.flutter_projects

import android.content.Intent
import com.example.flutter_projects.domain.UserDataCase
import com.example.flutter_projects.domain.UserItem
import com.example.flutter_projects.presentation.UserActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : FlutterActivity() {

    private val userChannel = "com.example.users"
    private val launchUserChannel = "com.example/native"
    val userDataCase: UserDataCase by inject()
    val job by lazy { CoroutineScope(Dispatchers.IO) }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)



        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            launchUserChannel
        ).setMethodCallHandler { call, result ->
            if (call.method == "launchUserActivity") {
                launchUserActivity()
                result.success("launched")
            } else {
                result.notImplemented()
            }
        }



        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            userChannel
        ).setMethodCallHandler { call, result ->
            if (call.method == "getUsers") {
                job.launch {
                    userDataCase.getUser().collect { user ->
                        val users = user as UserItem
                        val userList = users.userList.map {
                            mapOf(
                                "id" to it.id,
                                "name" to it.name,
                                "address" to it.address,
                                "phoneNumber" to it.phoneNumber,
                                "signaturePic" to it.signaturePic,
                                "profilePic" to it.profilePic
                            )
                        }
                        result.success(userList)
                    }

                }
            } else {
                result.notImplemented()
            }
        }
    }


    private fun launchUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

