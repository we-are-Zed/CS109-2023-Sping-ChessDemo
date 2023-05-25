package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;
import view.AnimalChessComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


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
    public int turnCount = 1;
    private PlayerColor winner;
    public AI ai;
    public Mode gameMode;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    final String[] s0 = new String[1];
    private List<Step> stepList;
    private List<ChessboardPoint> validMoves;

    public GameController(ChessboardComponent view, Chessboard model) {
        stepList = new LinkedList<>();
        validMoves = new ArrayList<>();

        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
        timer();
        if(gameMode==Mode.Easy||gameMode==Mode.Difficulty){
            ai=new AI(gameMode,model);
        }
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // after a valid move swap the player
    public void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        view.getChessGameFrame().getTurnLabel(currentPlayer, turnCount);
    }

    public void timer(){
        final String[] s0 = new String[1];
        ActionListener play = new ActionListener(){
            long start = (System.currentTimeMillis()-1000);
            public void actionPerformed(ActionEvent e) {

                long now = System.currentTimeMillis();

                long change = now- start;

                // 转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                if(win()){
                    s0[0] = df.format(new Date(change));
                }else{
                    s0[0] = df.format(new Date(change));
                    view.getChessGameFrame().getTime(s0[0]);
                }
            }
        };
        Timer timeAction0= new Timer(1000, play);
        timeAction0.start();
        if(win()){
            timeAction0.stop();
        }

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
        int nestX1 = 0; // 自己巢穴的X坐标red
        int nestY1 = 3; // 自己巢穴的Y坐标
        int nestX2 = 8; // 自己巢穴的X坐标blue
        int nestY2 = 3; // 自己巢穴的Y坐标
        // 检查是否有对方的棋子进入自己的巢穴
        if (model.grid[nestX1][nestY1].getPiece() != null && model.grid[nestX1][nestY1].getPiece().getOwner() != model.grid[nestX1][nestY1].getOwner()) {
            winner = PlayerColor.BLUE;
            return true;
        }
        if (model.grid[nestX2][nestY2].getPiece() != null && model.grid[nestX2][nestY2].getPiece().getOwner() != model.grid[nestX2][nestY2].getOwner()) {
            winner = PlayerColor.RED;
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
    public void gameOver(){
        if (winner == PlayerColor.BLUE) {
            JOptionPane.showMessageDialog(null, "Blue wins!" + s0[0]);
        } else if (winner == PlayerColor.RED) {
            JOptionPane.showMessageDialog(null, "Red wins!" + s0[0]);
        }
        restart();
    }

    // click an empty cell
    // selected point 点的那个点   point ：分成有棋子和没棋子
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {//如果有选中的棋子，且移动合法
            Step step = model.recordStep(selectedPoint, point, currentPlayer, turnCount);
            stepList.add(step);

            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            if(currentPlayer==PlayerColor.RED)
            {
                turnCount++;
            }
            view.repaint();
        }
        //if the chess enter Dens or Traps
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)&&model.grid[point.getRow()][point.getCol()].getType()==GridType.den) {
            winner=currentPlayer;
        }
        if(selectedPoint!=null&&model.grid[selectedPoint.getRow()][selectedPoint.getCol()].getType().equals(GridType.trap)&&model.isValidMove(selectedPoint, point))
        {
            model.exitTrap(selectedPoint);
            System.out.printf("exit trap");
        }
        if(win())
        {
            gameOver();
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

                Step step = model.recordStep(selectedPoint, point, currentPlayer, turnCount);
                stepList.add(step);
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                if(model.grid[point.getRow()][point.getCol()].getType()==GridType.trap)
                {
                    model.getTrapped(point);
                    System.out.println("trap");
                    System.out.printf(model.grid[point.getRow()][point.getCol()].getType().toString());
                }
                swapColor();
                if(currentPlayer==PlayerColor.RED)
                {
                    turnCount++;
                }
                view.repaint();
            }
            // TODO: Implement capture function
        }
        if(win())
        {
            gameOver();
        }
    }
    public boolean Load(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("save"));
        chooser.showDialog(view, "选择");
        File file = chooser.getSelectedFile();
        if(!file.getName().endsWith(".txt"))
        {
            JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始", "后缀错误", JOptionPane.ERROR_MESSAGE);
            restart();
            return false;
        }

        try{
            String temp;
            ArrayList<String> readList = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            while((temp = reader.readLine())!=null&&temp.length()!=0)
            {
                readList.add(temp);
            }
            int num = Integer.parseInt(readList.remove(0));
            for (int i = 0; i <= num; i++) {
                String str = readList.get(i);
                if (i % 2 == 0 && str.charAt(0) != 'B') {
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                            "当前回合方错误", JOptionPane.ERROR_MESSAGE);
                    restart();
                    return false;
                }
                if (i % 2 == 1 && str.charAt(0) != 'R') {
                    //System.out.println(str);
                    JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                            "当前回合方错误", JOptionPane.ERROR_MESSAGE);
                    restart();
                    return false;
                }
            }
            try{
                for (int j = num + 1; j < num + 10; j++) {
                    boolean s = true;
                    String[] chess= readList.get(j).split(" ");
                    if (chess.length != 7){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "棋盘规格错误", JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    if (!checkName(chess)) s = false;
                    if (!s){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "棋子名字错误", JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                        "棋盘规格错误", JOptionPane.ERROR_MESSAGE);
                restart();
                return false;
            }
            restart();
            for(int i=0;i<num;i++)
            {
                String[] step = readList.get(i).split(" ");
                ChessboardPoint point1 = new ChessboardPoint(Integer.parseInt(step[1].charAt(1)+""),Integer.parseInt(step[1].charAt(3)+""));
                ChessboardPoint point2 = new ChessboardPoint(Integer.parseInt(step[2].charAt(1)+""),Integer.parseInt(step[2].charAt(3)+""));
                boolean captureHappen = !step[3].equals("null");
                if(!captureHappen)
                {
                    if (!model.isValidMove(point1, point2)){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "存在非合规移动", JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    restart();
                }
                else {
                    if (!model.isValidCapture(point1, point2)){
                        JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始",
                                "存在非合规捕捉", JOptionPane.ERROR_MESSAGE);
                        restart();
                        return false;
                    }
                    restart();
                }
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "检测到非法修改存档\n已重新开始", "文件错误", JOptionPane.ERROR_MESSAGE);
            restart();
        }
        return true;
    }
    private static boolean checkName(String[] chess){
        for (int i = 0; i < chess.length; i++) {
            if (!chess[i].equals("E") && !chess[i].equals("L") && !chess[i].equals("T") && !chess[i].equals("l")
                    && !chess[i].equals("w") && !chess[i].equals("d") && !chess[i].equals("c") && !chess[i].equals("r")
                    && !chess[i].equals("0")){
                return false;
            }
        }
        return true;
    }

    public void undo()
    {
        if (stepList.isEmpty()) {
            return;
        }

    }
}
