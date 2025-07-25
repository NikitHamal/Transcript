# Transcript – Offline Speech Transcription

Transcript is an Android application written in Java that provides completely offline speech-to-text transcription using the Vosk `vosk-model-small-en-in-0.4` model.  
The UI follows Material 3 (Material You) guidelines and uses the Poppins font.

## Features
• Transcribe audio **from files** or **live** through the microphone.  
• Works entirely offline – internet not required after the model is available.  
• Modern Material design with dynamic colour on Android 12+.  
• GitHub Action automatically builds release APKs on every push.

## Building locally
1. Install **Android Studio Giraffe/Flamingo** or newer (AGP 8+).
2. Clone the repo and open it in Android Studio; let Gradle sync.
3. Download the Vosk Indian-English small model (≈ 32 MB) and unzip it to  
   `app/src/main/assets/models/vosk-model-small-en-in-0.4/`.  A helper menu action will prompt the download at first run when missing.
4. Create a signing keystore at `keystore/transcript.jks` (update passwords through environment variables or replace them in `app/build.gradle`).

## Continuous Integration
GitHub Action defined in `.github/workflows/android.yml` builds a release APK for every push and attaches it as an artefact tagged with the commit short-SHA.

---
© 2024 Transcript
