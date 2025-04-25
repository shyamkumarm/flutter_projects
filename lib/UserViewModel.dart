import 'package:flutter/material.dart';
import 'package:flutter_projects/NativeLauncher.dart';

class UserViewmodel extends ChangeNotifier {
  List<Map<String, dynamic>> _persons = [];

  List<Map<String, dynamic>> get persons => _persons;

  Future<void> load() async {
    _persons = await UserChannel.getUsers();
    notifyListeners();
  }


  void deleteUser(int id) async {
    await UserChannel.deleteUser(id);
     load(); // reload
  }


}