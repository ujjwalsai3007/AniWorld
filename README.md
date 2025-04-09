# AniWorld

<p align="center">
  <img src="app/src/main/res/drawable/firstimage.png" width="300" alt="AniWorld Banner">
</p>

## Your Gateway to the World of Anime

AniWorld is a modern Android application that provides a comprehensive anime browsing experience. Built with Jetpack Compose and Material 3, it offers a visually appealing interface with seamless navigation and powerful search capabilities.

## Features

- **Elegant User Interface**: Clean, modern design with a warm color palette and intuitive navigation
- **Browse Top Anime**: Explore a curated selection of top-rated anime from the Jikan API
- **Infinite Scrolling**: Seamlessly load more content as you scroll
- **Detailed Information**: View comprehensive details about each anime including ratings, episodes, and synopsis
- **Advanced Search**: Search for specific anime titles with real-time results
- **Responsive Design**: Full support for all screen sizes and orientations
- **Dark/Light Theme**: Automatic theme adaptation based on system settings
- **Edge-to-Edge Design**: Modern UI with proper handling of system insets (status bar, navigation bar)

## Technical Details

### Architecture

- **MVVM Architecture**: Clean separation of UI, business logic, and data
- **Repository Pattern**: Abstraction layer for data operations
- **Dependency Injection**: Hilt for dependency management
- **Coroutines**: Asynchronous programming for smooth UI experience

### Libraries & Technologies

- **Jetpack Compose**: Modern Android UI toolkit
- **Material 3**: Latest Material Design components
- **Retrofit**: Type-safe HTTP client for API communication
- **Coil**: Image loading library for efficient image handling
- **ViewModel**: Lifecycle-aware data holder
- **Hilt**: Dependency injection
- **Kotlin Coroutines**: Asynchronous programming
- **Navigation Compose**: Handle in-app navigation

### API Integration

AniWorld integrates with the [Jikan API](https://jikan.moe/) (v4), an unofficial MyAnimeList API, to provide:

- Top anime rankings
- Detailed anime information
- Search functionality
- Pagination support

## Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/AniWorld.git
```

2. Open the project in Android Studio
3. Sync Gradle and run the application on an emulator or physical device

## Screenshots

<img src="https://github.com/user-attachments/assets/6912ef5b-980f-43e7-81de-ce9b3576a414" width="200"/>

<img src="https://github.com/user-attachments/assets/b15122ac-9638-4928-819e-d43d62760d8c" width="200"/>

<img src="https://github.com/user-attachments/assets/0931991d-d97a-4087-958a-b912b29190dd" width="200"/>

<img src="https://github.com/user-attachments/assets/066fb542-1cda-4e88-9c76-6fb6204d0072" width="200"/>

<img src="https://github.com/user-attachments/assets/f1e333bc-6c28-43ac-88cc-a08e4734dc50" width="200"/>

<img src="https://github.com/user-attachments/assets/bfe76509-3af6-45c8-b61d-ae0151820f3f" width="200"/>



## App Structure

```
com.example.aniworld/
├── data/
│   ├── api/                 # API service interfaces
│   ├── model/               # Data classes
│   └── repository/          # Repository implementations
├── di/                      # Dependency injection modules
├── presentation/
│   ├── navigation/          # Navigation components
│   ├── screens/             # UI screens
│   │   ├── AnimeListScreen.kt
│   │   ├── AnimeDetailScreen.kt
│   │   ├── SearchScreen.kt
│   │   └── StartingScreen.kt
│   └── viewmodel/           # ViewModels
├── ui/
│   └── theme/               # Theme components
└── MainActivity.kt          # Entry point
```



## Credits

- [Jikan API](https://jikan.moe/) - Unofficial MyAnimeList API
- API Endpoint for Top Anime: https://api.jikan.moe/v4/top/anime
