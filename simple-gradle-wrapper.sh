#!/bin/bash

# Simple gradle wrapper that downloads gradle 8.0 if needed
GRADLE_VERSION=8.0
GRADLE_USER_HOME=${GRADLE_USER_HOME:-$HOME/.gradle}
GRADLE_CACHE_DIR="$GRADLE_USER_HOME/wrapper/dists/gradle-$GRADLE_VERSION-bin"

# Create cache dir
mkdir -p "$GRADLE_CACHE_DIR"

# Check if gradle is already downloaded
if [ ! -d "$GRADLE_CACHE_DIR/gradle-$GRADLE_VERSION" ]; then
    echo "Downloading Gradle $GRADLE_VERSION..."
    cd "$GRADLE_CACHE_DIR"
    curl -L "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "gradle-$GRADLE_VERSION-bin.zip"
    unzip -q "gradle-$GRADLE_VERSION-bin.zip"
fi

# Run gradle
exec "$GRADLE_CACHE_DIR/gradle-$GRADLE_VERSION/bin/gradle" "$@"
