package model;

import model.GridType;

import java.util.*;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;


/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public Cell[][] grid;
    public ArrayList<Step> steps;
    public final Set<ChessboardPoint> riverCell = new HashSet<>();
    public final Set<ChessboardPoint> denCell = new HashSet<>();
    public final Set<ChessboardPoint> trapCell = new HashSet<>();

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initSets();
        initPieces();
    }
    public void initPieces() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }

        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));

        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));

        grid[0][2].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));
        grid[0][4].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));
        grid[8][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
        grid[8][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
        grid[1][3].setPiece(new ChessPiece(PlayerColor.RED, "Trap", 0));
        grid[7][3].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", 0));
    }
    private void initSets() {
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));


        denCell.add(new ChessboardPoint(8, 3));
        denCell.add(new ChessboardPoint(0, 3));

        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell(GridType.grass);
            }
        }
        grid[3][1].setType(GridType.river);
        grid[3][2].setType(GridType.river);
        grid[3][4].setType(GridType.river);
        grid[3][5].setType(GridType.river);
        grid[4][1].setType(GridType.river);
        grid[4][2].setType(GridType.river);
        grid[4][4].setType(GridType.river);
        grid[4][5].setType(GridType.river);
        grid[5][1].setType(GridType.river);
        grid[5][2].setType(GridType.river);
        grid[5][4].setType(GridType.river);
        grid[5][5].setType(GridType.river);

        grid[8][3].setType(GridType.den);
        grid[0][3].setType(GridType.den);

        grid[7][3].setType(GridType.trap);
        grid[0][2].setType(GridType.trap);
        grid[0][4].setType(GridType.trap);
        grid[1][3].setType(GridType.trap);
        grid[8][2].setType(GridType.trap);
        grid[8][4].setType(GridType.trap);

        grid[0][3].setOwner(PlayerColor.RED);
        grid[8][3].setOwner(PlayerColor.BLUE);

        grid[0][2].setOwner(PlayerColor.RED);
        grid[0][4].setOwner(PlayerColor.RED);
        grid[1][3].setOwner(PlayerColor.RED);
        grid[8][2].setOwner(PlayerColor.BLUE);
        grid[8][4].setOwner(PlayerColor.BLUE);
        grid[7][3].setOwner(PlayerColor.BLUE);
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        } else {
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
        }
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if(grid[src.getRow()][src.getCol()].getType().equals(GridType.trap)){
            return false;
        }
        if (riverCell.contains(dest)) {
            if (!Objects.equals(getChessPieceAt(src).getName(), "Rat")) {
                return false;
            } else {
                return calculateDistance(src, dest) == 1;
            }
        }
        if (calculateDistance(src, dest) > 1 && (getChessPieceAt(src).getName().equals("Lion") || getChessPieceAt(src).getName().equals("Tiger"))) {
            if (src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) {
                return false;
            }
            if (src.getCol() == dest.getCol()) {
                if (src.getRow() > dest.getRow()) {
                    for (int i = dest.getRow() + 1; i < src.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getRow() < dest.getRow()) {
                    for (int i = src.getRow() + 1; i < dest.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getType() != GridType.river) {
                            return false;
                        }

                    }
                }
                if (src.getRow() < dest.getRow()) {
                    for (int i = src.getRow() + 1; i < dest.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                            return false;
                        }
                    }
                }
                if (src.getRow() > dest.getRow()) {
                    for (int i = dest.getRow() + 1; i < src.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                            return false;
                        }
                    }
                }
                return true;
            }
            if (src.getRow() == dest.getRow()) {
                if (src.getCol() > dest.getCol()) {
                    for (int i = dest.getCol() + 1; i < src.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getCol() < dest.getCol()) {
                    for (int i = src.getCol() + 1; i < dest.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getCol() < dest.getCol()) {
                    for (int i = src.getCol() + 1; i < dest.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                            return false;
                        }
                    }
                }
                if (src.getCol() > dest.getCol()) {
                    for (int i = dest.getCol() + 1; i < src.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }

        if (getGridAt(dest).getType() == GridType.den && getGridAt(dest).getOwner() ==getChessPieceAt(src).getOwner() ) {
            return false;
        }
        else if(getGridAt(dest).getType() == GridType.den && getGridAt(dest).getOwner() !=getChessPieceAt(src).getOwner())
        {
            return true;
        }
        if (getGridAt(dest).getType() == GridType.trap && getGridAt(dest).getOwner() ==getChessPieceAt(src).getOwner() ) {
            return false;
        }
        else if(getGridAt(dest).getType() == GridType.trap && getGridAt(dest).getOwner() !=getChessPieceAt(src).getOwner())
        {
            return true;
        }
        return calculateDistance(src, dest) == 1;
    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (srcPiece == null || destPiece == null) {
            return false;
        }
        if (srcPiece.getOwner() == destPiece.getOwner()) {
            return false;
        }
        if (getGridAt(dest).getType() == GridType.river || getGridAt(src).getType() == GridType.river) {
            return false;
        }
        if (calculateDistance(src, dest) > 1 && (getChessPieceAt(src).getName().equals("Lion") || getChessPieceAt(src).getName().equals("Tiger"))) {
            if (src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) {
                return false;
            }
            if (src.getCol() == dest.getCol()) {
                if (src.getRow() > dest.getRow()) {
                    for (int i = dest.getRow() + 1; i < src.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getRow() < dest.getRow()) {
                    for (int i = src.getRow() + 1; i < dest.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getRow() < dest.getRow()) {
                    for (int i = src.getRow() + 1; i < dest.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                            return false;
                        }
                    }
                }
                if (src.getRow() > dest.getRow()) {
                    for (int i = dest.getRow() + 1; i < src.getRow(); i++) {
                        if (getGridAt(new ChessboardPoint(i, src.getCol())).getPiece() != null) {
                            return false;
                        }
                    }
                }
                return srcPiece.canCapture(destPiece);
            }
            if (src.getRow() == dest.getRow()) {
                if (src.getCol() > dest.getCol()) {
                    for (int i = dest.getCol() + 1; i < src.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getCol() < dest.getCol()) {
                    for (int i = src.getCol() + 1; i < dest.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getType() != GridType.river) {
                            return false;
                        }
                    }
                }
                if (src.getCol() < dest.getCol()) {
                    for (int i = src.getCol() + 1; i < dest.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                            return false;
                        }
                    }
                }
                if (src.getCol() > dest.getCol()) {
                    for (int i = dest.getCol() + 1; i < src.getCol(); i++) {
                        if (getGridAt(new ChessboardPoint(src.getRow(), i)).getPiece() != null) {
                            return false;
                        }
                    }
                }
                return srcPiece.canCapture(destPiece);
            }
        }
        return calculateDistance(src, dest) == 1 && srcPiece.canCapture(destPiece);
    }

    public void getTrapped(ChessboardPoint point) {
        getGridAt(point).getPiece().setRank(0);
    }

    public void solveTrap(ChessboardPoint selectedPoint, ChessboardPoint destPoint) {
        if (getGridAt(destPoint).getType() == GridType.trap && getGridAt(destPoint).getOwner() != getChessPieceAt(selectedPoint).getOwner()) {
            getTrapped(selectedPoint);
        } else if (getGridAt(selectedPoint).getType() == GridType.trap && getGridAt(selectedPoint).getOwner() != getChessPieceAt(selectedPoint).getOwner()) {
            exitTrap(selectedPoint);
        }
    }

    public void exitTrap(ChessboardPoint point) {
        switch (getGridAt(point).getPiece().getName()) {
            case "Rat":
                getGridAt(point).getPiece().setRank(1);
                break;
            case "Cat":
                getGridAt(point).getPiece().setRank(2);
                break;
            case "Dog":
                getGridAt(point).getPiece().setRank(3);
                break;
            case "Wolf":
                getGridAt(point).getPiece().setRank(4);
                break;
            case "Leopard":
                getGridAt(point).getPiece().setRank(5);
                break;
            case "Tiger":
                getGridAt(point).getPiece().setRank(6);
                break;
            case "Lion":
                getGridAt(point).getPiece().setRank(7);
                break;
            case "Elephant":
                getGridAt(point).getPiece().setRank(8);
                break;
        }
    }

    public void runStep(Step step) {
        ChessboardPoint fromPoint = step.getFrom();
        ChessboardPoint toPoint = step.getTo();
        ChessPiece fromPiece = step.getFromChessPiece();
        setChessPiece(fromPoint, null);
        setChessPiece(toPoint, fromPiece);
    }

    public Step recordStep(ChessboardPoint fromPoint, ChessboardPoint toPoint, PlayerColor currentPlayer, int turn) {
        ChessPiece fromPiece = getChessPieceAt(fromPoint);
        ChessPiece toPiece = getChessPieceAt(toPoint);
        Step step = new Step(fromPoint, toPoint, fromPiece, toPiece, currentPlayer, turn);
//        System.out.println(step);
        return step;
    }

    public boolean solveDens(ChessboardPoint destPoint) {
        if (getGridAt(destPoint).getType() == GridType.den) {
            return true;
        }
        return false;
    }

    public boolean isAllCaptured(PlayerColor playerColor) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (getChessPieceAt(new ChessboardPoint(i, j)) != null && getChessPieceAt(new ChessboardPoint(i, j)).getOwner() == playerColor) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Step> getLegalMove(PlayerColor color)
    {
        List<ChessboardPoint> legalMove = getValidPoints(color);
        List<Step> steps = new ArrayList<>();
        for(ChessboardPoint point: legalMove)
        {
            List<ChessboardPoint> validMoves = getValidMoves(point);
            for(ChessboardPoint destPoint: validMoves)
            {
                steps.add(recordStep(point, destPoint, color, 0));
            }
        }
        return steps;
    }
    public List<ChessboardPoint> getValidMoves(ChessboardPoint point) {
        List<ChessboardPoint> availablePoints = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint destPoint = new ChessboardPoint(i, j);
                if (isValidMove(point, destPoint) || isValidCapture(point, destPoint)) {
                    availablePoints.add(destPoint);
                }
            }
        }
        return availablePoints;
    }
    public List<ChessboardPoint> getValidPoints(PlayerColor color){
        List<ChessboardPoint> availablePoints = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (getChessPieceAt(point) != null && getChessPieceAt(point).getOwner() == color) {
                    availablePoints.add(point);
                }
            }
        }
        return availablePoints;
    }
}