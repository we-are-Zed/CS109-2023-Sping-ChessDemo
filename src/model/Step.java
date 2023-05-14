package model;

import java.io.Serializable;

public class Step implements Serializable, Comparable<Step> {
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece fromChessPiece;
    private ChessPiece toChessPiece;
    private PlayerColor currentPlayer;
    private transient int value;
    private int turnCount;

    public Step(ChessboardPoint from, ChessboardPoint to, ChessPiece fromChessPiece, ChessPiece toChessPiece, PlayerColor currentPlayer, int turnCount) {
        this.from = from;
        this.to = to;
        this.fromChessPiece = fromChessPiece;
        this.toChessPiece = toChessPiece;
        this.currentPlayer = currentPlayer;
        this.turnCount = turnCount;
    }

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
        return "Step{" +
                "from=" + from +
                ", to=" + to +
                ", fromChessPiece=" + fromChessPiece +
                ", toChessPiece=" + toChessPiece +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(Step o) {
        return o.getValue() - this.getValue();
    }
}
