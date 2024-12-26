package com.example.tictactoecosmover;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private TicTacToe_Gameplay gameplay;
    private GameStateModel gameStateModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStateModel = new ViewModelProvider(this).get(GameStateModel.class);
        gameplay = new TicTacToe_Gameplay(this, getAllImageButtons(), gameStateModel);

        setBarColor();
        startGifAnimation();
        setContentMetrics();
        setTextScore();
        restoreStateImageButtons();
    }

    public void fieldsClick(View view) {
        gameplay.gameHandler(view);
    }

    private void startGifAnimation(){
        ImageView gifImageView = findViewById(R.id.imageViewGif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.cosmo_animation_slow)
                .into(gifImageView);

    }

    private ImageButton[] getAllImageButtons(){
        int[] imageButtons = {
                R.id.field1, R.id.field2, R.id.field3,
                R.id.field4, R.id.field5, R.id.field6,
                R.id.field7, R.id.field8, R.id.field9
        };

        ImageButton[] buttons = new ImageButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = findViewById(imageButtons[i]);
        }
        return buttons;
    }

    private void setBarColor(){
        int color = R.color.application_background_color;
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, color));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, color));
    }

    private void setContentMetrics() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        addGlobalLayoutEvent(tableLayout, () -> setTableLayoutSize(tableLayout));

        ImageView rocket = findViewById(R.id.rocket);
        addGlobalLayoutEvent(rocket, () -> setImageHeightSize(rocket));
    }

    private void addGlobalLayoutEvent(View view, Runnable action) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                action.run();
            }
        });
    }

    private void setTableLayoutSize(TableLayout tableLayout) {
        int min = Math.min(tableLayout.getWidth(), tableLayout.getHeight());

        ViewGroup.LayoutParams params = tableLayout.getLayoutParams();
        params.width = min;
        params.height = min;
        tableLayout.setLayoutParams(params);
    }

    private void setImageHeightSize(ImageView rocket){
        int heightImage = rocket.getRootView().getHeight() / 10;

        ViewGroup.LayoutParams params = rocket.getLayoutParams();
        params.height = heightImage;
        rocket.setLayoutParams(params);
    }

    private void setTextScore() {
        TextView redSide = findViewById(R.id.score_red);
        TextView yellowSide = findViewById(R.id.score_yellow);
        TextView separator = findViewById(R.id.score_separator);

        float textSize = getResources().getDisplayMetrics().heightPixels / 15.1f;

        redSide.setTextSize(textSize);
        yellowSide.setTextSize(textSize);
        separator.setTextSize(textSize);

        redSide.setText(String.valueOf(gameStateModel.getRedScore()));
        yellowSide.setText(String.valueOf(gameStateModel.getYellowScore()));
    }

    private void restoreStateImageButtons() {
        ImageButton[] buttons = getAllImageButtons();
        String[] stateImageButtons = gameStateModel.getStateImageButtons();
        for (int i = 0; i < stateImageButtons.length; i++)
        {
            if (stateImageButtons[i] != null)
            {
                buttons[i].setTag(stateImageButtons[i]);
                if (stateImageButtons[i].equals("star_red_image"))
                    buttons[i].setImageResource(R.drawable.star_red_image);
                else if (stateImageButtons[i].equals("star_yellow_image"))
                    buttons[i].setImageResource(R.drawable.star_yellow_image);
            }
        }
    }

}