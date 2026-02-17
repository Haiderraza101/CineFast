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

  // Splash screen duration in milliseconds
  private static final int SPLASH_DURATION = 5000; // 5 seconds

  ImageView ivLogo;
  TextView tvCine, tvFast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    // Initialize views
    ivLogo = findViewById(R.id.ivLogo);
    tvCine = findViewById(R.id.tvCine);
    tvFast = findViewById(R.id.tvFast);

    // Apply animations
    applyLogoAnimations();
    applyTextAnimations();

    // Navigate to OnboardingActivity after 5 seconds
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
    // Create animation set combining rotate, fade, and translate
    AnimationSet animationSet = new AnimationSet(true);
    animationSet.setDuration(2000);

    // Rotate animation (360 degrees)
    RotateAnimation rotateAnimation = new RotateAnimation(
        0, 360,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f);
    rotateAnimation.setDuration(2000);

    // Fade animation (from invisible to visible)
    AlphaAnimation fadeAnimation = new AlphaAnimation(0.0f, 1.0f);
    fadeAnimation.setDuration(2000);

    // Translate animation (slide from top)
    TranslateAnimation translateAnimation = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, -1.0f,
        Animation.RELATIVE_TO_SELF, 0);
    translateAnimation.setDuration(2000);

    // Add all animations to the set
    animationSet.addAnimation(rotateAnimation);
    animationSet.addAnimation(fadeAnimation);
    animationSet.addAnimation(translateAnimation);

    // Start the animation
    ivLogo.startAnimation(animationSet);
  }

  private void applyTextAnimations() {
    // Fade in animation for text
    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    fadeIn.setDuration(2000);
    fadeIn.setStartOffset(1000); // Start after 1 second

    // Translate animation for text (slide from bottom)
    TranslateAnimation slideUp = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0);
    slideUp.setDuration(1500);
    slideUp.setStartOffset(1000);

    // Create animation set for text
    AnimationSet textAnimationSet = new AnimationSet(true);
    textAnimationSet.addAnimation(fadeIn);
    textAnimationSet.addAnimation(slideUp);

    // Apply to both text views
    tvCine.startAnimation(textAnimationSet);
    tvFast.startAnimation(textAnimationSet);
  }
}
