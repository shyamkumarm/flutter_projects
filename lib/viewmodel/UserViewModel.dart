import 'package:flutter/material.dart';
import 'package:flutter_projects/data/User.dart';
import 'package:flutter_projects/message_channel/AndroidNativeMessageChannel.dart';

class UserViewmodel extends ChangeNotifier {
   List<User> _persons = [];

  List<User> get persons => _persons;

  Future<void> load() async {
    _persons = await UserChannel.getUsers();
    notifyListeners();
  }


  void deleteUser(int id) async {
    await UserChannel.deleteUser(id);
    //load(); // reload
  }


}