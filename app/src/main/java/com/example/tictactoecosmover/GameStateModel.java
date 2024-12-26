package com.example.tictactoecosmover;

import androidx.lifecycle.ViewModel;
import java.util.Arrays;

public class GameStateModel extends ViewModel {
    private int redScore = 0;
    private int yellowScore = 0;
    private final String[] stateImageButtons = new String[9];

    public int getRedScore() {
        return redScore;
    }

    public void incrementRedScore() {
        redScore++;
    }

    public int getYellowScore() {
        return yellowScore;
    }

    public void incrementYellowScore() {
        yellowScore++;
    }

    public String[] getStateImageButtons() {
        return stateImageButtons;
    }

    public void setStateImageButtons(int index, String value) {
        stateImageButtons[index] = value;
    }

    public void resetStateImageButtons() {
        Arrays.fill(stateImageButtons, null);
    }
}
