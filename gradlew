#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Gradle wrapper JAR missing. Downloading..."
  mkdir -p "$DIR/gradle/wrapper"
  curl -sLo "$WRAPPER_JAR" https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/8.2/gradle-wrapper-8.2.jar
fi

exec java -jar "$WRAPPER_JAR" "$@"