import 'package:flutter/services.dart';

import '../data/User.dart';

class NativeLauncher {
  static const launch = MethodChannel('com.example.launch');

  static Future<void> launchNativeActivity() async {
    try {
      await launch.invokeMethod('launchUserActivity');
    } catch (e) {
      print("Error launching activity: $e");
    }
  }
}

class UserChannel {
  static const _channel = MethodChannel('com.example.users');

  static Future<List<User>> getUsers() async {
    final List<dynamic> users = await _channel.invokeMethod('getUsers');
    return  users.map((user) {
      return User.fromMap(user.cast<String, dynamic>());
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