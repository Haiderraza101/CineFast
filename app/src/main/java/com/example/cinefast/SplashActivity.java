package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

  private static final int SPLASH_DURATION = 5000;
  ImageView ivLogo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ivLogo = findViewById(R.id.ivLogo);

    applyLogoAnimations();

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
        startActivity(intent);
        finish();
      }
    }, SPLASH_DURATION);
  }

  private void applyLogoAnimations() {
    if (ivLogo != null) {
      Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_anim);
      ivLogo.startAnimation(splashAnim);
    }
  }
}
