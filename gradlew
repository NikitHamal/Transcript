#!/usr/bin/env sh

set -e

DIR="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  GRADLE_VERSION="8.2.1"
  ZIP_URI="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"
  TMP_ZIP="$(mktemp)"
  echo "Gradle wrapper JAR missing. Downloading ${ZIP_URI} …"
  curl -sL "$ZIP_URI" -o "$TMP_ZIP"
  mkdir -p "$DIR/gradle/wrapper"
  # Extract only the wrapper jar from the distribution zip
  unzip -p "$TMP_ZIP" "*/gradle-wrapper.jar" > "$WRAPPER_JAR"
  rm "$TMP_ZIP"
fi

exec java -jar "$WRAPPER_JAR" "$@"