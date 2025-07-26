#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Gradle wrapper JAR missing. Downloading..."
  mkdir -p "$DIR/gradle/wrapper"
  curl -sLo "$WRAPPER_JAR" https://github.com/gradle/gradle/raw/v8.2.1/gradle/wrapper/gradle-wrapper.jar
fi

exec java -jar "$WRAPPER_JAR" "$@"