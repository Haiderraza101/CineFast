package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OnboardingActivity extends AppCompatActivity {

  Button btnGetStarted;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_onboarding);

    btnGetStarted = findViewById(R.id.btnGetStarted);

    btnGetStarted.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }
}
