# MathForKids

A native Android math exercise app for Israeli kids (Hebrew, RTL) built with Jetpack Compose.

## Features
- 6 grade levels (כיתה א׳ – כיתה ו׳)
- Age-appropriate math topics per grade
- Multiple choice + free-form numpad input
- Scoring with 1–3 stars
- Achievements system
- Tablet-adaptive layouts
- Full RTL Hebrew support

## Tech Stack
- Kotlin + Jetpack Compose
- Material 3 with WindowSizeClass (tablet support)
- MVVM + Clean Architecture
- Hilt (DI)
- Room (local persistence)
- Navigation Compose

## Building
Open in Android Studio and run on device/emulator.

```bash
./gradlew assembleDebug
./gradlew test
```
