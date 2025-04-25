# Flutter-Native Hybrid Integration: User Profile Capture & Listing

## Objective

Build a user entry system using Flutter and Native Android (Kotlin + Compose) with:

* MVVM architecture on both ends
* Bidirectional communication between Flutter and native Android
* Reuse of native data models in Flutter
* Data persistence using a local database
* A basic UI in both layers for entry and listing

## 📂 Project Structure (Android)
```
app/
 ├── data/               # Data layer (UserRepo, UserData sources)
 ├── di/                 # Dependency Injection (Koin Modules)
 ├── domain/             # Domain layer (UseDataCase, data classes, IUserData interfaces, database entites),
 ├── presentation/       # Presentation layer (Screens, Activities,ViewModels)
 ├── utils/              # Utility classes (filepath creations )
 └── MyApplication.kt     # Entry point of the app
```

## 📂 Project Structure (Flutter)
```
app/
 ├── message_channel/      # Data layer - Communicate to native models (AndroidNativeMessageChannel - Manages channels)   
 ├── viewmodel/            # UserViewmodel
 ├── view/                 # UserListScreenItem.dart - Screen components
 └── main.dart             #  Entry point of the flutter app
```

## Functional Requirements:

### 1. User Entry Flow (Native Android - Kotlin)

* Build a form using native Android (Kotlin) that includes:
    * Name (EditText)
    * Address (EditText)
    * Phone number (EditText)
    * Signature – captured via a Canvas view (custom view, avoid private library)
    * Profile Picture – captured using Camera
* On submission, save the data to a local database (Room)
* Model: `User`(name, address, phone, signature (file path), profilePic (file path))

### 2. Show User List (Flutter)

* Create a Flutter screen that:
    * Retrieves all user entries from the native database
    * Displays them in a list view (showing Name, Phone, and Profile Picture)
    * Allows deletion of individual entries, which should trigger deletion from the native DB

## Non-Functional Requirements
* **User Interface:** Implement a basic, user-friendly UI for both the native Android form and the Flutter user list screen.
* **Performance** Ensure smooth and responsive performance in both the native Android and Flutter components.
* **Error Handling:** Implement robust error handling for all operations, including data input, database operations, and communication between Flutter and native.
* **Testing:** Include basic unit tests for the native Android (Kotlin) code, especially for the data access layer (Room).  Consider widget tests for the Flutter UI.
* **Security & Encryptions:** Handle user data, especially the signature and profile picture, securely.Consider storage permissions and data privacy.
* **Device Compatibility & Landscape:** The application should function correctly on a range of Android devices, also should supports (tablets) landscape mode .

## Out of Scope
* **Advanced UI/UX:**  Focus is on basic functionality, not on creating a highly polished or complex UI.  Advanced animations, custom themes, and intricate design are not required.
* **Comprehensive Testing:**  While basic unit testing for the native Android (Kotlin) code is expected, full test coverage (e.g., UI testing, integration testing) is not required.
* **Image Editing & Other Markup:**  Basic camera capture and display are sufficient.
* **Server Synchronization:**  Data synchronization with a remote server is not within the scope of this project.  Data should be stored only locally using the Room database.
* **Signature Editing:** The signature capture should be a simple capture. Editing the signature after it has been captured is out of scope.


## 📝 License
This project is licensed under the MIT License.

---
Made with ❤️ using Jetpack Compose and Flutter dart
