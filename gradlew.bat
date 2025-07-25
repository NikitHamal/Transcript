@echo off
set DIR=%~dp0
if exist "%DIR%gradle\wrapper\gradle-wrapper.jar" (
  java -jar "%DIR%gradle\wrapper\gradle-wrapper.jar" %*
) else (
  echo Gradle wrapper JAR not found. Please run on Unix to bootstrap.
)