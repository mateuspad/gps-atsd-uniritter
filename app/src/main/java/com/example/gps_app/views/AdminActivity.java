package com.example.gps_app.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gps_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button showUsersLocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        showUsersLocs = findViewById(R.id.showLocs1);

        showUsersLocs.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, LastLocsActivity.class);
            startActivity(intent);
        });
    }
}