import 'package:device_preview/device_preview.dart';
import 'package:flutter/material.dart';
import 'package:flutter_projects/message_channel/AndroidNativeMessageChannel.dart';
import 'package:flutter_projects/view/UserListScreenItem.dart';
import 'package:flutter_projects/viewmodel/UserViewModel.dart';
import 'package:provider/provider.dart';

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

  void launchAndroidActivity() {
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
        body: UserListScreen(),
        floatingActionButton: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () => launchAndroidActivity(),
        ),
      ),
    );
  }
}


class UserListScreen extends StatefulWidget {
  const UserListScreen({super.key});

  @override
  _UserListScreenState createState() => _UserListScreenState();
}

class _UserListScreenState extends State<UserListScreen> {
  bool _initialized = false;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (!_initialized) {
      Provider.of<UserViewmodel>(context, listen: false).load();
      _initialized = true;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserViewmodel>(
      builder: (context, viewModel, child) {
        return RefreshIndicator(
          onRefresh: () => viewModel.load(),
          child: ListView.builder(
            itemCount: viewModel.persons.length,
            itemBuilder: (context, index) {
              final person = viewModel.persons[index];
              return UserListScreenItem(user: person);
            },
          ),
        );
      },
    );
  }
}

