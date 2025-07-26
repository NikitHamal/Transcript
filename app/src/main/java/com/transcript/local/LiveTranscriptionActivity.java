package com.transcript.local;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;
import org.vosk.android.StorageService;

import java.io.IOException;

public class LiveTranscriptionActivity extends AppCompatActivity implements RecognitionListener {

    private Model model;
    private SpeechService speechService;
    private TextView resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_transcription);
        resultView = findViewById(R.id.live_transcription_text);

        initVosk();

        MaterialButton muteButton = findViewById(R.id.mute_button);
        muteButton.setOnClickListener(view -> {
            if (speechService != null) {
                speechService.setPause(true);
            }
        });

        MaterialButton pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(view -> {
            if (speechService != null) {
                speechService.setPause(!speechService.isPaused());
            }
        });

        MaterialButton stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(view -> {
            if (speechService != null) {
                speechService.stop();
            }
        });
    }

    private void initVosk() {
        StorageService.unpack(this, "vosk-model-small-en-in", "model",
                (model) -> {
                    this.model = model;
                    startListening();
                },
                (exception) -> {
                    // Handle error
                });
    }

    private void startListening() {
        if (speechService != null) {
            speechService.stop();
            speechService = null;
        }

        try {
            Recognizer rec = new Recognizer(model, 16000.0f);
            speechService = new SpeechService(rec, 16000.0f);
            speechService.startListening(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResult(String hypothesis) {
        resultView.append(hypothesis + "\n");
    }

    @Override
    public void onFinalResult(String hypothesis) {
        resultView.append(hypothesis + "\n");
    }

    @Override
    public void onPartialResult(String hypothesis) {
        // Do nothing
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onTimeout() {
        // Do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechService != null) {
            speechService.stop();
            speechService.shutdown();
        }
    }
}
