#!/usr/bin/env sh

# Gradle wrapper bootstrap (trimmed)
DIR="$(cd "$(dirname "$0")" && pwd)"

if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  echo "Gradle wrapper JAR missing. Downloading..."
  wget -q https://services.gradle.org/distributions/gradle-8.2-bin.zip -O /tmp/gradle.zip && unzip -q /tmp/gradle.zip -d "$DIR/gradle/wrapper" && rm /tmp/gradle.zip
  java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
fi