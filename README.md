# Custom Android Keyboard with Auto-Paste

This is a modern Android keyboard application built with Kotlin, following MVVM architecture and using Room database for persistence.

## Features

*   **Custom Comment Management**: Add, edit, and delete messages.
*   **Auto-Paste**: Automatically inserts a random saved comment when a text field is opened.
*   **App Filtering**: Choose which apps the auto-paste feature should work in.
*   **Non-Repeating Random**: Logic to avoid repeating the same comment twice in a row.
*   **Material Design 3**: Modern UI with dark/light mode support.
*   **Floating Quick Buttons**: Includes a "RAND" button on the keyboard for manual insertion.

## Technical Stack

*   **Language**: Kotlin
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Database**: Room
*   **UI**: Material Design 3, XML Layouts
*   **Min SDK**: 26 (Android 8.0)
*   **Target SDK**: 34 (Android 14)

## How to Build

1.  Open the project in **Android Studio** (Hedgehog or newer recommended).
2.  Wait for Gradle sync to complete.
3.  Build and run the app on an emulator or physical device.
4.  Enable the keyboard in **Settings > System > Languages & input > On-screen keyboard > Manage on-screen keyboards**.
5.  Select "Custom Auto Keyboard" as your input method.

## Usage

1.  Open the app to manage your custom comments.
2.  Enable "Auto Paste" in the settings.
3.  Navigate to any text field (e.g., in Instagram or TikTok).
4.  The keyboard will automatically insert one of your saved comments.
5.  Use the "RAND" key on the keyboard to manually trigger a random insertion.
