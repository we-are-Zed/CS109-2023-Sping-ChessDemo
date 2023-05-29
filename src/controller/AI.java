package controller;

import model.*;

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
        List<Step> steps = model.getLegalMove(color);
        Step bestStep = null;
        int maxEnemyRank = -1;

        // Select the step that can attack the highest rank enemy
        for (Step step : steps) {
            ChessPiece piece = model.getChessPieceAt(step.getDest());
            if (piece != null && piece.getColor() != color) {
                if (piece.getRank() > maxEnemyRank) {
                    maxEnemyRank = piece.getRank();
                    bestStep = step;
                }
            }
        }

        if (bestStep == null) {
            int minDistanceToBase = Integer.MAX_VALUE;
            for (Step step : steps) {
                int distanceToBase = computeDistanceToEnemyBase(step.getDest());
                if (distanceToBase < minDistanceToBase) {
                    minDistanceToBase = distanceToBase;
                    bestStep = step;
                }
            }
        }

        return bestStep;
    }

    private int computeDistanceToEnemyBase(ChessboardPoint point) {
        return point.getRow();
    }
    public String toString() {
        return "AI";
    }

}