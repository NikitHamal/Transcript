package com.transcript.local;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import com.transcript.local.databinding.ActivityMainBinding;
import com.transcript.local.databinding.BottomSheetNewTranscriptionBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                // No-op: handled per permission.
            });

    private final ActivityResultLauncher<String[]> openDocumentLauncher = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(), this::handlePickedFile);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabNew.setOnClickListener(v -> showNewTranscriptionSheet());

        requestNeededPermissions();
    }

    private void requestNeededPermissions() {
        if (!hasAudioPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_AUDIO});
            } else {
                permissionLauncher.launch(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        }
    }

    private boolean hasAudioPermissions() {
        boolean mic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED;
        boolean read;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PermissionChecker.PERMISSION_GRANTED;
        } else {
            read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED;
        }
        return mic && read;
    }

    private void showNewTranscriptionSheet() {
        BottomSheetNewTranscriptionBinding sheetBinding = BottomSheetNewTranscriptionBinding.inflate(LayoutInflater.from(this));
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(sheetBinding.getRoot());

        sheetBinding.optionFromDevice.setOnClickListener(v -> {
            dialog.dismiss();
            openDocumentLauncher.launch(new String[]{"audio/*"});
        });

        sheetBinding.optionLive.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(this, LiveTranscriptionActivity.class));
        });

        dialog.show();
    }

    private void handlePickedFile(Uri uri) {
        if (uri == null) return;
        Toast.makeText(this, "Starting transcription…", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TranscriptionActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }
}