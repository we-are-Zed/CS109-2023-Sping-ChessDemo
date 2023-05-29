package model;

import java.io.Serializable;

public class Step implements Serializable, Comparable<Step> {
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece fromChessPiece;
    private ChessPiece toChessPiece;
    private PlayerColor currentPlayer;
    private transient int value;
    public ChessPiece beCaptured;
    private int turnCount;

    public Step(ChessboardPoint from, ChessboardPoint to, ChessPiece fromChessPiece, ChessPiece toChessPiece, PlayerColor currentPlayer, int turnCount) {
        this.from = from;
        this.to = to;
        this.fromChessPiece = fromChessPiece;
        this.toChessPiece = toChessPiece;
        this.currentPlayer = currentPlayer;
        this.turnCount = turnCount;
        beCaptured=null;
    }
    public Step(ChessboardPoint from, ChessboardPoint to, ChessPiece fromChessPiece, ChessPiece toChessPiece, PlayerColor currentPlayer, int turnCount,ChessPiece beCaptured) {
        this.from = from;
        this.to = to;
        this.fromChessPiece = fromChessPiece;
        this.toChessPiece = toChessPiece;
        this.currentPlayer = currentPlayer;
        this.turnCount = turnCount;
        this.beCaptured=beCaptured;}

    public ChessboardPoint getFrom() {
        return from;
    }

    public ChessboardPoint getTo() {
        return to;
    }

    public void setFrom(ChessboardPoint from) {
        this.from = from;
    }

    public void setTo(ChessboardPoint to) {
        this.to = to;
    }

    public ChessPiece getFromChessPiece() {
        return fromChessPiece;
    }

    public ChessPiece getToChessPiece() {
        return toChessPiece;
    }

    public void setFromChessPiece(ChessPiece fromChessPiece) {
        this.fromChessPiece = fromChessPiece;
    }

    public void setToChessPiece(ChessPiece toChessPiece) {
        this.toChessPiece = toChessPiece;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (beCaptured == null)
            return (currentPlayer == PlayerColor.BLUE ? "B " : "R ") +
                    "(" + from.getRow() + "," + from.getCol() + ") " +
                    "(" + to.getRow() +"," + to.getCol() + ") " +
                    "null";
        else
            return (currentPlayer == PlayerColor.BLUE ? "B " : "R ") +
                    "(" + from.getRow() + "," + from.getCol() + ") " +
                    "(" + to.getRow() +"," + to.getCol() + ") " +
                    beCaptured.getName();
    }

    @Override
    public int compareTo(Step o) {
        return o.getValue() - this.getValue();
    }

    public ChessboardPoint getDest() {
        return to;
    }
}
