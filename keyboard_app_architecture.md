# Android Keyboard App Architecture Design

This document outlines the architectural design and project structure for the custom Android keyboard application. The app will be developed using Kotlin in Android Studio, adhering to the MVVM (Model-View-ViewModel) architectural pattern, and utilizing Room for local data persistence.

## 1. Core Features

The keyboard app will include the following key features:

*   **Custom Comment Management**: Users can add, edit, and delete custom comments/messages.
*   **Random Comment Insertion**: Automatically inserts a random saved comment when a text/comment field is opened.
*   **Non-Repeating Random Selection**: Ensures that the same comment is not repeated continuously.
*   **Enable/Disable Auto Paste**: User control over the automatic insertion feature.
*   **App Selection Filter**: Allows users to specify which applications the custom keyboard features should be active in (e.g., Facebook, Instagram, TikTok, YouTube, browser).
*   **Dark/Light Mode UI**: Supports both dark and light themes for the keyboard interface.
*   **Material Design 3**: Modern UI/UX implementation following Material Design 3 guidelines.
*   **Floating Quick Buttons**: Quick access buttons for random comment insertion and paste-next actions.
*   **Android 8+ Support**: Compatibility with Android 8 (Oreo) and newer versions.
*   **MVVM Architecture**: Structured codebase for better maintainability and testability.
*   **Room Database**: Persistent local storage for custom comments and app settings.

## 2. Architectural Overview (MVVM)

The application will follow the MVVM architectural pattern, separating the UI from the business logic and data layers. This promotes a clean, testable, and maintainable codebase.

*   **Model**: Represents the data and business logic. This includes the `Comment` entity, `CommentDao`, `CommentDatabase`, and `CommentRepository`.
*   **View**: The UI components, primarily the `InputMethodService` and its associated `KeyboardView` for the keyboard layout, and `Activities`/`Fragments` for settings and comment management.
*   **ViewModel**: Acts as a bridge between the View and the Model, exposing data streams to the View and handling UI-related logic. It will interact with the `Repository`.

```mermaid
graph TD
    A[InputMethodService/Activities/Fragments] --> B(ViewModel)
    B --> C(Repository)
    C --> D{Room Database}
    C --> E[Other Data Sources (e.g., SharedPreferences for settings)]
    D -- CommentDao --> F[Comment Entity]
```

## 3. Project Structure

The project will be organized into several packages, reflecting the MVVM architecture and functional separation.

```
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/customkeyboard/
│       │       ├── keyboard/             // Input Method Service and UI related to the keyboard
│       │       │   ├── CustomKeyboardService.kt
│       │       │   ├── KeyboardView.kt
│       │       │   └── KeyboardViewModel.kt
│       │       ├── data/                 // Data layer: Room database, entities, DAOs, repositories
│       │       │   ├── database/
│       │       │   │   ├── AppDatabase.kt
│       │       │   │   └── CommentDao.kt
│       │       │   ├── model/
│       │       │   │   └── Comment.kt
│       │       │   └── repository/
│       │       │       └── CommentRepository.kt
│       │       ├── domain/               // Business logic, use cases (if complex enough)
│       │       │   └── usecase/
│       │       │       └── GetRandomCommentUseCase.kt
│       │       ├── ui/                   // Activities and Fragments for settings and comment management
│       │       │   ├── settings/
│       │       │   │   ├── SettingsActivity.kt
│       │       │   │   ├── SettingsViewModel.kt
│       │       │   │   └── AppSelectionAdapter.kt
│       │       │   ├── comments/
│       │       │   │   ├── CommentListActivity.kt
│       │       │   │   ├── CommentListViewModel.kt
│       │       │   │   └── CommentAdapter.kt
│       │       │   └── common/
│       │       │       └── ThemeManager.kt
│       │       ├── util/                 // Utility classes, extensions, constants
│       │       │   ├── Constants.kt
│       │       │   └── Extensions.kt
│       │       └── CustomKeyboardApplication.kt // Application class
│       └── res/
│           ├── layout/                 // XML layouts for activities, fragments, and keyboard
│           ├── drawable/               // Drawable resources
│           ├── values/                 // Colors, strings, styles, themes
│           ├── xml/                    // Input method editor (IME) configuration
│           └── mipmap/                 // Launcher icons
└── build.gradle.kts
```

## 4. Key Components and Responsibilities

### 4.1. `CustomKeyboardService.kt` (Input Method Service)

*   Extends `InputMethodService` to provide the core keyboard functionality.
*   Manages the keyboard UI (`KeyboardView`).
*   Handles key presses and input events.
*   Interacts with `KeyboardViewModel` to fetch comments and settings.
*   Implements logic for random comment insertion and app filtering.

### 4.2. `KeyboardView.kt`

*   Custom `View` responsible for drawing the keyboard layout.
*   Receives key events and passes them to `CustomKeyboardService`.
*   Displays floating quick buttons.

### 4.3. `KeyboardViewModel.kt`

*   Provides data to `CustomKeyboardService` (e.g., list of comments, settings).
*   Handles logic for selecting random comments, ensuring non-repetition.
*   Communicates with `CommentRepository`.

### 4.4. `Comment.kt` (Room Entity)

*   Data class representing a single custom comment.
*   Annotated for Room database persistence.

### 4.5. `CommentDao.kt` (Room DAO)

*   Defines methods for database operations on `Comment` entities (insert, update, delete, get all, get random).

### 4.6. `AppDatabase.kt` (Room Database)

*   Abstract class extending `RoomDatabase`.
*   Defines the database and provides access to `CommentDao`.

### 4.7. `CommentRepository.kt`

*   Abstracts the data source (Room database).
*   Provides a clean API for `ViewModel`s to interact with comment data.
*   Handles data conflicts and ensures data integrity.

### 4.8. `SettingsActivity.kt` / `SettingsViewModel.kt`

*   UI and logic for managing app settings, including enable/disable auto-paste, and selecting target applications.

### 4.9. `CommentListActivity.kt` / `CommentListViewModel.kt`

*   UI and logic for displaying, adding, editing, and deleting custom comments.

### 4.10. `ThemeManager.kt`

*   Manages the application's theme (dark/light mode) dynamically.

## 5. Dependencies

Key dependencies will include:

*   **AndroidX Libraries**: Core components, AppCompat, Material Design.
*   **Kotlin Coroutines**: For asynchronous operations and background tasks.
*   **Room Persistence Library**: For local database management.
*   **Lifecycle Components**: ViewModel, LiveData/Flow for observing data changes.
*   **Navigation Component**: For managing UI navigation (if multiple settings screens).
*   **Dagger Hilt / Koin**: For dependency injection (optional, but good practice for larger apps).

## 6. Future Considerations

*   **Unit and Integration Tests**: Implement comprehensive tests for all layers.
*   **Accessibility**: Ensure the keyboard is accessible to users with disabilities.
*   **Performance Optimization**: Optimize drawing and input handling for smooth performance.
*   **Security**: Adhere to Android security best practices, especially concerning user input.

This architecture provides a solid foundation for building a robust, maintainable, and feature-rich Android keyboard application.
