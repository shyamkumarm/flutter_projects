import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class User {
  final int id;
  final String name;
  final String address;
  final String phoneNumber;
  final String signaturePic;
  final String profilePic;
  User({required this.id, required this.name, required this.address, required this.phoneNumber, required this.profilePic, required this.signaturePic});

  factory User.fromMap(Map<String, dynamic> map) {
    return User(
      id: map['id'],
      name: map['name'],
      address: map['address'],
      phoneNumber: map['phoneNumber'],
      signaturePic: map['signaturePic'],
      profilePic: map['profilePic'],
    );
  }
}