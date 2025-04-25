import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_projects/UserListScreenItem.dart';
import 'package:flutter_projects/UserViewModel.dart';

import 'package:path/path.dart' as path;
import 'package:image/image.dart' as img; // Import the image package
import 'package:flutter/foundation.dart'; // For ChangeNotifier
import 'package:flutter_projects/NativeLauncher.dart';
import 'package:provider/provider.dart';
import 'package:device_preview/device_preview.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [ChangeNotifierProvider(create: (_) => UserViewmodel())],
      child: const UserDataApp(),
    ),
  );
}

class UserDataApp extends StatelessWidget {
  const UserDataApp({super.key});

  void _incrementCounter() {
    NativeLauncher.launchNativeActivity();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'User Data App',
      theme: ThemeData(primarySwatch: Colors.blue, fontFamily: 'Inter'),
      locale: DevicePreview.locale(context),
      builder: DevicePreview.appBuilder,
      darkTheme: ThemeData.dark(),
      home: Scaffold(
        appBar: AppBar(title: Text('User List')),
        body: Consumer<UserViewmodel>(
          builder: (context, viewModel, child) {
            return RefreshIndicator(
              onRefresh: () => viewModel.load(),
              child: ListView.builder(
                itemCount: viewModel.persons.length,
                itemBuilder: (context, index) {
                  final person = viewModel.persons[index];
                  return UserListScreenItem(user: person);
                },
              )
            );
          },
        ),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () => _incrementCounter(),
        ),
      ),
    );
  }
}

