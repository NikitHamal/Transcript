package org.vosk.android;

import org.vosk.Recognizer;

public class SpeechService {
    private boolean paused = false;
    
    public SpeechService(Recognizer recognizer, float sampleRate) {
        // Stub implementation
    }
    
    public void startListening() {
        // Stub implementation
    }
    
    public void startListening(RecognitionListener listener) {
        // Stub implementation
    }
    
    public void stop() {
        // Stub implementation
    }
    
    public void pause() {
        // Stub implementation
    }
    
    public void resume() {
        // Stub implementation
    }
    
    public void setPause(boolean pause) {
        this.paused = pause;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void shutdown() {
        // Stub implementation
    }
}