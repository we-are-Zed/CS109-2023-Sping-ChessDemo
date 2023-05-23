package controller;

import model.Chessboard;
import model.Step;

public class AI {
    private Mode gameMode;
    private Chessboard model;

    public AI(Mode gameMode, Chessboard model) {
        this.gameMode = gameMode;
        this.model = model;
    }

    public Step run() {
        if (gameMode == Mode.Easy) {
            return runEasy();
        } else {
            return runDifficulty();
        }
    }
    public Step runEasy() {
        return null;
    }
    public Step runDifficulty() {

        return null;
    }
}