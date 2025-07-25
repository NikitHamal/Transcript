package com.transcript.local;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.transcript.local.databinding.ActivityLiveTranscriptionBinding;

import org.vosk.Model;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;
import org.vosk.android.StorageService;

import java.io.IOException;

public class LiveTranscriptionActivity extends AppCompatActivity implements RecognitionListener {

    private ActivityLiveTranscriptionBinding binding;

    private SpeechService speechService;
    private Model model;

    private final ActivityResultLauncher<String> micPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    startListening();
                } else {
                    Toast.makeText(this, "Mic permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLiveTranscriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStop.setOnClickListener(v -> stopListening());
        binding.btnPauseResume.setOnClickListener(v -> togglePauseResume());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED) {
            startListening();
        } else {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }

    private void startListening() {
        if (speechService != null) return;
        StorageService.unpack(this, "models", "vosk-model-small-en-in-0.4", (model) -> {
            this.model = model;
            try {
                speechService = new SpeechService(new org.vosk.Recognizer(model, 16000.0f), 16000.0f);
                speechService.startListening(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, (exception) -> Toast.makeText(this, "Failed to load model", Toast.LENGTH_SHORT).show());
    }

    private void stopListening() {
        if (speechService != null) {
            speechService.stop();
            speechService = null;
        }
        finish();
    }

    private void togglePauseResume() {
        if (speechService == null) return;
        if (speechService.isPaused()) {
            speechService.resume();
            binding.btnPauseResume.setText("Pause");
        } else {
            speechService.pause();
            binding.btnPauseResume.setText("Resume");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechService != null) {
            speechService.shutdown();
        }
    }

    /* RecognitionListener callbacks */
    @Override
    public void onPartialResult(String hypothesis) {
        binding.tvLiveTranscript.setText(hypothesis);
    }

    @Override
    public void onResult(String hypothesis) {
        binding.tvLiveTranscript.append("\n" + hypothesis);
    }

    @Override
    public void onFinalResult(String hypothesis) {
        binding.tvLiveTranscript.append("\n" + hypothesis);
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {
        Toast.makeText(this, "Timeout", Toast.LENGTH_SHORT).show();
    }
}