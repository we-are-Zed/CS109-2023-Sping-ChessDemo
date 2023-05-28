package controller;

import model.Chessboard;
import model.PlayerColor;
import model.Step;

import java.util.List;

public class AI {
    private Mode gameMode;
    private Chessboard model;

    public AI(Mode gameMode, Chessboard model) {
        this.gameMode = gameMode;
        this.model = model;
    }

    public Step run(PlayerColor color) {
        if (gameMode == Mode.Easy) {
            return runEasy(color);
        } else {
            return runDifficulty(color);
        }
    }
    public Step runEasy(PlayerColor color) {
        List<Step> steps = model.getLegalMove(color);
        if (steps.size() > 0) {
            return steps.get((int) (Math.random() * steps.size()));
        }
        return null;
    }
    public Step runDifficulty(PlayerColor color) {

        return null;
    }
}