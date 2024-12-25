package com.example.tictactoecosmover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class TicTacToe_Animation {
    public static void animateButton(ImageButton imageButton) {
        ObjectAnimator scaleUpAnimator = ObjectAnimator.ofPropertyValuesHolder(
                imageButton,
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.03f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.03f)
        );
        scaleUpAnimator.setDuration(150);

        ObjectAnimator scaleDownAnimator = ObjectAnimator.ofPropertyValuesHolder(
                imageButton,
                PropertyValuesHolder.ofFloat("scaleX", 1.05f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 1.05f, 1.0f)
        );
        scaleDownAnimator.setDuration(150);

        float currentRotation = imageButton.getRotation();
        float newRotation = currentRotation + 10f;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageButton, "rotation", currentRotation, newRotation);
        rotateAnimator.setDuration(300);
        rotateAnimator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());

        scaleUpAnimator.start();
        scaleUpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleDownAnimator.start();
                rotateAnimator.start();
            }
        });
    }

    public static void animateTextViewScore(TextView textView, String hexColor){
        int newTextColor = Color.parseColor(hexColor);
        float startSize = textView.getTextSize() / textView.getResources().getDisplayMetrics().scaledDensity;
        float endSize = startSize + 5;

        int currentTextColor = textView.getCurrentTextColor();

        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(textView, "textSize", startSize, endSize);
        textSizeAnimator.setDuration(250);

        ObjectAnimator resetTextSizeAnimator = ObjectAnimator.ofFloat(textView, "textSize", endSize, startSize);
        resetTextSizeAnimator.setDuration(250);

        ObjectAnimator textColorAnimator = ObjectAnimator.ofInt(textView, "textColor", currentTextColor, newTextColor);
        textColorAnimator.setDuration(250);
        textColorAnimator.setEvaluator(new android.animation.ArgbEvaluator());

        ObjectAnimator textColorRevertAnimator = ObjectAnimator.ofInt(textView, "textColor", newTextColor, currentTextColor);
        textColorRevertAnimator.setDuration(250);
        textColorRevertAnimator.setEvaluator(new android.animation.ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(textSizeAnimator).with(textColorAnimator);
        animatorSet.play(resetTextSizeAnimator).with(textColorRevertAnimator).after(textSizeAnimator);
        animatorSet.start();
    }

    public static void animateRocket(ImageView rocket, int screenWidth) {
        float currentX = rocket.getX();
        float newX = currentX + (screenWidth / 9.0f);

        ObjectAnimator rocketAnimator = ObjectAnimator.ofFloat(rocket, "x", currentX, newX);
        rocketAnimator.setDuration(300);
        rocketAnimator.start();
    }

    public static void resetRocketPosition(ImageView rocket) {
        ObjectAnimator rocketAnimator = ObjectAnimator.ofFloat(rocket, "x", rocket.getX(), 15);
        rocketAnimator.setDuration(300);
        rocketAnimator.start();
    }
}
