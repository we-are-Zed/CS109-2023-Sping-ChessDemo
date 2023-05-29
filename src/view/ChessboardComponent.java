package view;


import controller.GameController;
import model.*;
import view.Animal.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> denCell = new HashSet<>();

    public Color DayGrass = new Color(238,197,145);
    public Color NightGrass = new Color(205,170,125);
    public Color DayRiver =  new Color(187,255,255);
    public Color NightRiver =  new Color(150,205,205);
    public Color DayTrap =  new Color(238,197,145);
    public Color NightTrap =  new Color(205,170,125);
    public Color DayDen =  new Color(192, 192, 192);
    public Color NightDen =  new Color(100, 100, 100);

    private GameController gameController;
    private ChessGameFrame chessGameFrame;
    private boolean aiPlay=false;

    public GameController getGameController(){return gameController;}

    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }

    public ChessboardComponent(int chessSize, ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();//就是chessboard里的grid，只是不想用get写

        // Clear all the chess components
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    if(grid[i][j].getPiece().getName().equals("Elephant")){
                        gridComponents[i][j].add(new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Lion")){
                        gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Tiger")){
                        gridComponents[i][j].add(new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Leopard")){
                        gridComponents[i][j].add(new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Wolf")){
                        gridComponents[i][j].add(new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Dog")){
                        gridComponents[i][j].add(new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Cat")){
                        gridComponents[i][j].add(new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Rat")){
                        gridComponents[i][j].add(new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }else if(grid[i][j].getPiece().getName().equals("Trap")){
                        gridComponents[i][j].add(new TrapChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                    }

                }
            }
        }

    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));

        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
        trapCell.add(new ChessboardPoint(7,3));

        denCell.add(new ChessboardPoint(0,3));
        denCell.add(new ChessboardPoint(8,3));

        //清空所有的格子
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(DayRiver, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if(trapCell.contains(temp)){
                    cell = new CellComponent(DayTrap, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }else if(denCell.contains(temp)){
                    cell = new CellComponent(DayDen, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }else{
                    cell = new CellComponent(DayGrass, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, AnimalChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public AnimalChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        AnimalChessComponent chess = (AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    public void changeTheme(boolean isDay){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 7; j++){
                if(gridComponents[i][j].background == DayGrass){
                    gridComponents[i][j].background = NightGrass;
                }else if (gridComponents[i][j].background == DayRiver){
                    gridComponents[i][j].background = NightRiver;
                }else if (gridComponents[i][j].background == DayTrap){
                    gridComponents[i][j].background = NightTrap;
                }else if (gridComponents[i][j].background == DayDen){
                    gridComponents[i][j].background = NightDen;
                }else if(gridComponents[i][j].background == NightGrass){
                    gridComponents[i][j].background = DayGrass;
                }else if(gridComponents[i][j].background == NightRiver){
                    gridComponents[i][j].background = DayRiver;
                }else if(gridComponents[i][j].background == NightTrap){
                    gridComponents[i][j].background = DayTrap;
                }else if(gridComponents[i][j].background == NightDen){
                    gridComponents[i][j].background = DayDen;
                }
            }
        }
        repaint();
        revalidate();
    }
    public void Undo(Step step) {
        ChessboardPoint point1 = step.getFrom();
        ChessboardPoint point2 = step.getTo();
        ChessPiece ChessPiece1 = step.getFromChessPiece();
        ChessPiece ChessPiece2 = step.getToChessPiece();
        if (ChessPiece2 != null) {
            removeChessComponentAtGrid(point2);
            if (ChessPiece2.getName().equals("Elephant")){
                setChessComponentAtGrid(point2, new ElephantChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Lion")){
                setChessComponentAtGrid(point2, new LionChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Tiger")){
                setChessComponentAtGrid(point2, new TigerChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Leopard")){
                setChessComponentAtGrid(point2, new LeopardChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Wolf")){
                setChessComponentAtGrid(point2, new WolfChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Dog")){
                setChessComponentAtGrid(point2, new DogChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Cat")){
                setChessComponentAtGrid(point2, new CatChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            } else if (ChessPiece2.getName().equals("Rat")){
                setChessComponentAtGrid(point2, new RatChessComponent(ChessPiece2.getOwner(), CHESS_SIZE));
            }
        } else {
            removeChessComponentAtGrid(point2);
        }
        if (ChessPiece1.getName().equals("Elephant")){
            setChessComponentAtGrid(point1, new ElephantChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Lion")){
            setChessComponentAtGrid(point1, new LionChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Tiger")){
            setChessComponentAtGrid(point1, new TigerChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Leopard")){
            setChessComponentAtGrid(point1, new LeopardChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Wolf")){
            setChessComponentAtGrid(point1, new WolfChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Dog")){
            setChessComponentAtGrid(point1, new DogChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Cat")){
            setChessComponentAtGrid(point1, new CatChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        } else if (ChessPiece1.getName().equals("Rat")){
            setChessComponentAtGrid(point1, new RatChessComponent(ChessPiece1.getOwner(), CHESS_SIZE));
        }
    }
    public void setAiPlay(boolean aiPlay) {
        this.aiPlay = aiPlay;
    }
    public void showValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint validMove : validMoves) {
            CellComponent cellComponent = getGridComponentAt(validMove);
            cellComponent.setValidMove(true);
            paintImmediately(this.getBounds());
        }
    }
    public void hideValidMoves(List<ChessboardPoint> validMoves) {
        for (ChessboardPoint validMove : validMoves) {
            CellComponent cellComponent = getGridComponentAt(validMove);
            cellComponent.setValidMove(false);
            cellComponent.repaint();
        }
    }
}
