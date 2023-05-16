package controller;


import listener.GameListener;
import model.*;
import view.AnimalChessComponent;
import view.CellComponent;
import view.ChessboardComponent;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    public PlayerColor currentPlayer;
    private int turnCount = 1;
    private PlayerColor winner;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean win() {
        if (model.isAllCaptured(PlayerColor.RED)) {
            winner = PlayerColor.BLUE;
            return true;
        }
        if (model.isAllCaptured(PlayerColor.BLUE)) {
            winner = PlayerColor.RED;
            return true;
        }
        int nestX1 = 0; // 自己巢穴的X坐标
        int nestY1 = 3; // 自己巢穴的Y坐标
        int nestX2 = 8; // 自己巢穴的X坐标
        int nestY2 = 3; // 自己巢穴的Y坐标
        // 检查是否有对方的棋子进入自己的巢穴
        if (model.grid[nestX1][nestY1].getPiece() != null && model.grid[nestX1][nestY1].getPiece().getOwner() != model.grid[nestX1][nestY1].getOwner()) {
            return true;
        }
        if (model.grid[nestX2][nestY2].getPiece() != null && model.grid[nestX2][nestY2].getPiece().getOwner() != model.grid[nestX2][nestY2].getOwner()) {
            return true;
        }
        return false;
        // TODO: Check the board if there is a winner
    }

    public void restart() {
        model.initPieces();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        view.repaint();
        currentPlayer = PlayerColor.BLUE;
        winner = null;
        selectedPoint = null;
        turnCount = 1;
    }


    // click an empty cell
    // selected point 点的那个点   point ：分成有棋子和没棋子
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {//如果有选中的棋子，且移动合法
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
        }
            //if the chess enter Dens or Traps
            if (selectedPoint != null && model.isValidMove(selectedPoint, point)&&model.grid[point.getRow()][point.getCol()].getType()==GridType.den) {

            }
            if (selectedPoint != null && model.isValidMove(selectedPoint, point)&&model.grid[point.getRow()][point.getCol()].getType().equals(GridType.trap)) {
                model.getTrapped(point);
            }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            }
            // TODO: Implement capture function
        }
    }
}