![app icon 240_240](https://github.com/user-attachments/assets/1f0dae10-bccb-4c87-8746-54ad2c847ea1)

![Android](https://img.shields.io/badge/Android-%2334A853?style=for-the-badge&logo=Android&logoColor=white)
![kotlin](https://img.shields.io/badge/Kotlin-%237F52FF?style=for-the-badge&logo=Kotlin&logoColor=white)

## 프로젝트 소개
카메라로 바코드를 스캔해서 코드를 추출해서 활용할 수 있는 앱입니다.

## 🚀 주요 기능

- 📷 1D/2D 바코드(예: QR 코드) 스캔
- 🔗 바코드 내 URL 자동 추출 및 열기
- 🕓 스캔 기록 확인
- 📋 스캔된 정보 복사 기능
- 📦 바코드로 Google sheet에서 데이터 불러오기

## 📸 스크린샷
| 스캔 화면 | sheet 화면 | 기록 목록 |
|-----------|------------|-------------|
| ![screenshot-barcode](https://github.com/user-attachments/assets/90e78030-4b38-4f33-8718-a98d19586928) | ![screenshot-sheet](https://github.com/user-attachments/assets/935fbb7c-b978-46a8-911e-67d0c208a257)| ![screenshot-history](https://github.com/user-attachments/assets/06a04aaf-4343-4d2c-865a-dc44888dd0b2)|



## 🛠 사용 기술

- Kotlin
- CameraX
- ML Kit Barcode Scanning API
- MVVM 아키텍처
- Hilt (의존성 주입)
- Jetpack Navigation
- Room (로컬 DB)
- DataStore (앱 설정 저장)

## ✅ 최소 요구사항

- Android 7.0 (API 24) 이상
- 카메라 권한 허용

## 🧾 Open Source Licenses
This project is licensed under the [Apache License 2.0](LICENSE).

It also uses the following open source libraries:
- [Retrofit](https://square.github.io/retrofit/) — Apache License 2.0
- [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning) — Apache License 2.0
- [CameraX](https://developer.android.com/training/camerax) — Apache License 2.0
- [Hilt](https://dagger.dev/hilt/) — Apache License 2.0
- [AppAuth for Android](https://github.com/openid/AppAuth-Android) — Apache License 2.0
- [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) — Apache License 2.0
- [Room](https://developer.android.com/training/data-storage/room) — Apache License 2.0


