package com.example.tictactoecosmover;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class TicTacToe_Gameplay {
    private final Activity activity;
    private final ImageButton[] buttons;
    private final List<int[]> combination;
    private boolean crossPlayer = true;

    public TicTacToe_Gameplay(Activity activity, ImageButton[] buttons) {
        this.activity = activity;
        this.buttons = buttons;
        this.combination = Arrays.asList(
                new int[]{0, 1, 2},
                new int[]{3, 4, 5},
                new int[]{6, 7, 8},
                new int[]{0, 3, 6},
                new int[]{1, 4, 7},
                new int[]{2, 5, 8},
                new int[]{0, 4, 8},
                new int[]{2, 4, 6}
        );
    }

    public void gameHandler(View view) {
        ImageButton button = (ImageButton) view;
        if (button.getTag() == null || button.getTag().equals("star_image"))
        {
            if (crossPlayer)
            {
                button.setImageResource(R.drawable.star_red_image);
                button.setTag("star_red_image");
            }
            else
            {
                button.setImageResource(R.drawable.star_yellow_image);
                button.setTag("star_yellow_image");
            }
            crossPlayer = !crossPlayer;
            TicTacToe_Animation.animateButton(button);

            ImageView rocket = activity.findViewById(R.id.rocket);
            int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
            TicTacToe_Animation.animateRocket(rocket, screenWidth);

            String winner = checkOnWin();
            if (winner != null)
            {
                Toast.makeText(activity, "Player " + winner + " WIN", Toast.LENGTH_SHORT).show();
                checkOnSideWin(winner);
                resetGame();
            }
            else if (isBoardFull())
            {
                Toast.makeText(activity, "DRAW!", Toast.LENGTH_SHORT).show();
                resetGame();
            }
        }
    }

    private boolean checkRowCol(ImageButton a, ImageButton b, ImageButton c) {
        return a.getTag() != null && a.getTag().equals(b.getTag()) && b.getTag().equals(c.getTag());
    }

    private String checkOnWin() {
        for (int[] combo : combination) {
            ImageButton a = buttons[combo[0]];
            ImageButton b = buttons[combo[1]];
            ImageButton c = buttons[combo[2]];
            if (checkRowCol(a, b, c))
            {
                if (a.getTag().equals("star_red_image"))
                    return "red";
                else if (a.getTag().equals("star_yellow_image"))
                    return "yellow";
            }
        }
        return null;
    }

    private void checkOnSideWin(String side) {
        TextView redSide = activity.findViewById(R.id.score_red);
        TextView yellowSide = activity.findViewById(R.id.score_yellow);
        if (side.equals("red"))
        {
            int redScore = Integer.parseInt(redSide.getText().toString());
            redSide.setText(String.valueOf(redScore + 1));
            TicTacToe_Animation.animateTextViewScore(redSide, "#E991B0");
        }
        else if (side.equals("yellow"))
        {
            int blueScore = Integer.parseInt(yellowSide.getText().toString());
            yellowSide.setText(String.valueOf(blueScore + 1));
            TicTacToe_Animation.animateTextViewScore(yellowSide, "#E2E78F");
        }
    }

    private boolean isBoardFull() {
        for (ImageButton button : buttons) {
            if (button.getTag() == null || button.getTag().equals("star_image"))
                return false;
        }
        return true;
    }

    private void resetGame() {
        for (ImageButton button : buttons) {
            button.setImageResource(R.drawable.star_image);
            button.setTag(null);
        }
        crossPlayer = true;
        ImageView rocket = activity.findViewById(R.id.rocket);
        TicTacToe_Animation.resetRocketPosition(rocket);
    }
}
