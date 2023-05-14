package controller;


import listener.GameListener;
import model.*;
import view.Animal.LionChessComponent;
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

    private ChessboardPoint selectedPoint;
    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private int turnCount=1;
    private PlayerColor winner;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
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
        view.getChessGameFrame().updateStatus(String.format("Turn %d: %s's turn", turnCount, currentPlayer));
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }
    private void densWin() {
        winner = currentPlayer;
        System.out.println("Winner is " + winner);
        view.showWinDialog(String.valueOf(winner));
    }
    // after a valid move swap the player
    private void swapColor(boolean isUndo) {
        if (!isUndo) {
            turnCount++;
        } else {
            turnCount--;
        }
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        view.getChessGameFrame().updateStatus(String.format("Turn %d: %s's turn", turnCount, currentPlayer));
    }

    private boolean win() {
        // TODO: Check the board if there is a winner
        return false;
    }



    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {

            //如果是进入陷阱格子，降低棋子的等级至0
            model.solveTrap(selectedPoint, point);


            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));

            //如果是进入巢穴格子，结束游戏
            if (model.solveDens(point)) {
                densWin();
            }

            selectedPoint = null;
            swapColor(false);
            view.repaint();
        }
    }



    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
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
            if (!model.isValidCapture(selectedPoint, point)) {
//                throw new IllegalArgumentException("Illegal chess capture!");
                System.out.println("Illegal chess capture!");
                return;
            }

            Step step = model.recordStep(selectedPoint, point, currentPlayer, turnCount);


            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            ;

            selectedPoint = null;
            swapColor(false);
            view.repaint();

        }
    }
}
