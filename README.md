# Working Buddy

Working Buddy is a native Android application built with Kotlin and Jetpack Compose designed for service providers to track their daily work entries, earnings, and service breakdowns.

## Features
- **Daily Work Entries**: Track services like PAN Card, Voter ID, Aadhaar, and more.
- **Payment Tracking**: Mark entries as Paid or Unpaid with real-time summary updates.
- **Analytics**: Visual breakdown of earnings (Paid vs Pending) and service types.
- **Calculator**: Built-in floating calculator accessible from any screen.
- **Offline First**: Uses Room database for local storage, ensuring privacy and speed.
- **Backup & Restore**: Export and import data as JSON files.
- **Theme Support**: Light and Dark mode toggle saved via DataStore.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM
- **Database**: Room
- **Preferences**: DataStore
- **JSON**: Gson

## Build Instructions
1. Clone the repository.
2. Open in Android Studio (Iguana or later recommended).
3. Build and run on a device with API 26+.

## CI/CD
The project includes a GitHub Actions workflow (`.github/workflows/build.yml`) that automatically builds the debug APK on every push to the `main` branch.
