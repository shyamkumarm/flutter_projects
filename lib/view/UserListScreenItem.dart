import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_projects/viewmodel/UserViewModel.dart';
import 'package:provider/provider.dart';

class UserListScreenItem extends StatelessWidget {
  final Map<String, dynamic> user;

  const UserListScreenItem({super.key, required this.user});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Material(
        elevation: 2,
        borderRadius: BorderRadius.circular(12),
        child: Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(12),
            color: Colors.white,
          ),
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    CircleAvatar(
                      radius: 40,
                      backgroundImage: FileImage(File(user["profilePic"])),
                    ),
                    const SizedBox(width: 16.0),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            user["name"],
                            style: const TextStyle(
                              fontWeight: FontWeight.w600,
                              fontSize: 18.0,
                              color: Colors.black87,
                            ),
                            overflow: TextOverflow.ellipsis,
                          ),
                          const SizedBox(height: 4.0),
                          Text(
                            user["address"],
                            style: TextStyle(
                              fontSize: 14.0,
                              color: Colors.black87,
                            ),
                          ),
                        ],
                      ),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete,color: Colors.black38),
                      onPressed: () =>  {
                        Provider.of<UserViewmodel>(
                          context,
                          listen: false,
                        ).deleteUser(user["id"])
                      },
                    ),
                  ],
                ),
                Row(
                  children: [
                    Icon(Icons.phone, color: Colors.black87, size: 16),
                    const SizedBox(width: 4.0),
                    Text(
                      user["phoneNumber"],
                      style: TextStyle(fontSize: 14.0, color: Colors.black54, fontWeight: FontWeight.w600),
                    ),
                    const SizedBox(width:50.0),
                    const Text(
                      'Signature:',
                      style: TextStyle(
                        fontWeight: FontWeight.w500,
                        color: Colors.black87,
                      ),
                    ),
                    const SizedBox(width: 4.0),
                    ClipRRect(
                      borderRadius: BorderRadius.circular(8.0),
                      child: Image.file(
                        File(user["signaturePic"]),
                        height: 50.0,
                        width: 75.0,
                        fit: BoxFit.fitWidth,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}