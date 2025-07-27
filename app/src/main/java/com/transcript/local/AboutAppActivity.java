package com.transcript.local;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.transcript.local.R;

public class AboutAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        setTitle("About App");
        // Optionally, set up a Material toolbar here
    }
}