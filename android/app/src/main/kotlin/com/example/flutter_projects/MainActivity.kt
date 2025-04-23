package com.example.flutter_projects

import android.content.Intent
import com.example.flutter_projects.presentation.UserActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity(){


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

         val CHANNEL = "com.example/native"

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "launchUserActivity") {
                launchUserActivity()
                result.success("launched")
            } else {
                result.notImplemented()
            }
        }
    }



    private fun launchUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
    }
}

