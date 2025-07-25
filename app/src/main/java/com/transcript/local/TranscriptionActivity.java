package com.transcript.local;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.vosk.Recognizer;
import org.vosk.Model;
import org.vosk.android.StorageService;

import java.io.InputStream;

public class TranscriptionActivity extends AppCompatActivity {

    private TextView tvOutput;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvOutput = new TextView(this);
        progressBar = new ProgressBar(this);
        setContentView(tvOutput);

        Uri audioUri = getIntent().getData();
        if (audioUri == null) {
            Toast.makeText(this, "No audio selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        StorageService.unpack(this, "models", "vosk-model-small-en-in-0.4", (model) -> runTranscription(model, audioUri),
                (e) -> Toast.makeText(this, "Could not load model", Toast.LENGTH_SHORT).show());
    }

    private void runTranscription(Model model, Uri audioUri) {
        tvOutput.setText("Transcribing…\n");
        new Thread(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(audioUri);
                if (inputStream == null) {
                    runOnUiThread(() -> Toast.makeText(this, "Unable to open file", Toast.LENGTH_SHORT).show());
                    return;
                }
                Recognizer recognizer = new Recognizer(model, 16000);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        String result = recognizer.getResult();
                        appendResult(result);
                    } else {
                        String partial = recognizer.getPartialResult();
                        appendPartial(partial);
                    }
                }
                appendResult(recognizer.getFinalResult());
                recognizer.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void appendPartial(String text) {
        runOnUiThread(() -> tvOutput.setText(text));
    }

    private void appendResult(String text) {
        runOnUiThread(() -> tvOutput.append("\n" + text));
    }
}