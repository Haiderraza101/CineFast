package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

  Animation fade;
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
    AnimationSet animationSet = new AnimationSet(true);
    animationSet.setDuration(2500);

    RotateAnimation rotateAnimation = new RotateAnimation(
        0, 360,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);
    rotateAnimation.setDuration(2000);

    AlphaAnimation fadeAnimation = new AlphaAnimation(0.0f, 1.0f);
    fadeAnimation.setDuration(2000);

    TranslateAnimation translateAnimation = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, -0.5f,
        Animation.RELATIVE_TO_SELF, 0);
    translateAnimation.setDuration(2000);

    animationSet.addAnimation(rotateAnimation);
    animationSet.addAnimation(fadeAnimation);
    animationSet.addAnimation(translateAnimation);

    ivLogo.startAnimation(animationSet);
  }
}
