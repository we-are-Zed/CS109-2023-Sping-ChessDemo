package controller;

import model.*;

import java.util.List;
import java.util.Random;

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
        List<Step> steps = model.getLegalMove(color);
        Random random = new Random();
        Step bestStep = null;
        Step safeStep = null;
        int maxEnemyRank = -1;

        Step randomStep = steps.get(random.nextInt(steps.size()));

        for (Step step : steps) {
            ChessPiece piece = model.getChessPieceAt(step.getDest());
            if (piece != null && piece.getColor() != color) {
                if (piece.getRank() > maxEnemyRank) {
                    maxEnemyRank = piece.getRank();
                    bestStep = step;
                }
            }

            if (model.willBeEaten(step.getFrom()) && safeStep == null) {
                safeStep = step;
            }
        }

        if (bestStep == null) {
            if (safeStep != null) {
                return safeStep;
            }
            return randomStep;
        }
        return bestStep;
    }
    public String toString() {
        return "AI";
    }

}