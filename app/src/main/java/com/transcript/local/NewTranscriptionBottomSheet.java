package com.transcript.local;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.SpeechStreamService;
import java.io.IOException;
import java.io.InputStream;

public class NewTranscriptionBottomSheet extends BottomSheetDialogFragment {

    private static final int PICK_FILE_REQUEST_CODE = 101;
    private Model model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_new_transcription, container, false);

        model = ((MainActivity) requireActivity()).model;

        view.findViewById(R.id.from_device_button).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        });

        view.findViewById(R.id.live_button).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LiveTranscriptionActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            recognizeFile(uri);
        }
    }

    private void recognizeFile(Uri uri) {
        if (model == null) {
            // Model not loaded yet
            return;
        }

        try (InputStream is = requireContext().getContentResolver().openInputStream(uri)) {
            if (is == null) return;
            Recognizer rec = new Recognizer(model, 16000.f);
            int nread;
            byte[] b = new byte[4096];
            while ((nread = is.read(b)) >= 0) {
                rec.acceptWaveForm(b, nread);
            }
            String result = rec.getFinalResult();
            Intent intent = new Intent(requireActivity(), TranscriptionActivity.class);
            intent.putExtra("transcription", result);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
