package com.example.gps_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    CardView cardRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        cardRoutes = findViewById(R.id.cardRoutes);

        mAuth = FirebaseAuth.getInstance();

        cardRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GPSActivity.class);
                startActivity(i);
            }
        });
    }
}