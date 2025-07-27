package org.vosk.android;

public interface RecognitionListener {
    void onPartialResult(String hypothesis);
    void onResult(String hypothesis);
    void onFinalResult(String hypothesis);
    void onError(Exception e);
    void onTimeout();
}