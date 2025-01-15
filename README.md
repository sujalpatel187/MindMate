# Mindmate - Mental Health App

Mindmate is an Android application designed to improve mental health and well-being. The app provides a comprehensive set of features including login/signup, real-time data storage, video call connectivity, mental health improvement exercises, and AI-powered support. The user interface is built using XML and the app is developed in Kotlin.

## Features
- **User Authentication**: Secure login and signup using Firebase Authentication.
- **Real-Time Database**: Firebase Realtime Database for storing user data.
- **Video Call Support**: Seamless connection with doctors via ZEGOCLOUD API.
- **Stress Relief Exercises**: AI-suggested breathing and mental stress improvement exercises.
- **News and Podcasts**: Access the latest mental health news and curated podcasts.
- **Conferences and Blogs**: Explore resources and join conversations.
- **Chatbot Support**: Round-the-clock chatbot assistance for mental health guidance.

## Getting Started

### Prerequisites
Before you begin, ensure you have the following:
- Android Studio installed on your system.
- A Firebase account for Authentication and Realtime Database setup.
- ZEGOCLOUD account for video call API integration.
- Internet connectivity for API and Firebase interactions.

### Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/sujalpatel187/Mindmate.git
   ```

2. Open the project in Android Studio.

3. Set up Firebase:
   - Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
   - Add the Android app to your Firebase project.
   - Download the `google-services.json` file and place it in the `app/` directory.

4. Configure ZEGOCLOUD:
   - Sign up or log in to your [ZEGOCLOUD account](https://www.zegocloud.com/).
   - Obtain your App ID and App Sign.
   - Add these credentials in your `strings.xml` file:
     ```xml
     <string name="zego_app_id">YOUR_APP_ID</string>
     <string name="zego_app_sign">YOUR_APP_SIGN</string>
     ```

5. Sync the project with Gradle to download all dependencies.

### Dependencies
Add the following dependencies to your `build.gradle` file:
```gradle
implementation 'com.google.firebase:firebase-auth:21.3.0'
implementation 'com.google.firebase:firebase-database:20.2.2'
implementation 'com.zegocloud:zego-zim-sdk:latest_version'
implementation 'com.squareup.okhttp3:okhttp:4.9.3'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.9.0'
```
Replace `latest_version` with the latest version available on the [ZEGOCLOUD SDK documentation](https://docs.zegocloud.com/).

## Usage
1. Launch the app on an Android device or emulator.
2. Sign up or log in using Firebase Authentication.
3. Access various features like video calls, exercises, news, podcasts, and chatbot support.

## Project Structure
- **UI (XML layouts)**: Intuitive and accessible design for all user needs.
- **Kotlin**: Manages application logic and integrations with Firebase and ZEGOCLOUD.
- **Firebase**: Handles authentication and real-time data storage.
- **ZEGOCLOUD**: Enables video call functionality.

## Contributing
Contributions are welcome! Follow these steps to contribute:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push them to your fork.
4. Submit a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For any queries or suggestions, feel free to reach out:
- GitHub: [Sujal Patel](https://github.com/sujalpatel187)

---

Let Mindmate be your companion in mental health improvement!

