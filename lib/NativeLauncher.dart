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

class UserChannel {
  static const _channel = MethodChannel('com.example.users');

  static Future<List<Map<String, dynamic>>> getUsers() async {
    final List<dynamic> users = await _channel.invokeMethod('getUsers');
    return  users.map((user) {
      return Map<String, dynamic>.from(user);
    }).toList();
  }

  static Future<void> deleteUser(int id) async {
    try {
       await _channel.invokeMethod('deleteUser',{"id":id});
    } catch (e) {
      print("Error db query: $e");
    }
  }
}