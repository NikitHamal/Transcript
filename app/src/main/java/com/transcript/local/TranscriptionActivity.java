package com.transcript.local;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TranscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcription);

        String transcription = getIntent().getStringExtra("transcription");
        TextView transcriptionText = findViewById(R.id.transcription_text);
        transcriptionText.setText(transcription);
    }
}
