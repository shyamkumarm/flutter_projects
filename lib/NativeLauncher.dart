import 'package:flutter/services.dart';

class NativeLauncher {
  static const platform = MethodChannel('com.example/native');

  static Future<void> launchNativeActivity() async {
    try {
      await platform.invokeMethod('launchUserActivity');
    } catch (e) {
      print("Error launching activity: $e");
    }
  }
}