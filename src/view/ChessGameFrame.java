package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;
import model.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
//import view.ImagePanel;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
//    private ImagePanel mainPanel;
//    private String[] bgPaths = {"/gameBg.jpg", "/gameBg2.jpg"};

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public boolean isDay;
    JLabel background;
    JLabel statusLabel ;
    JLabel timeLabel ;
    public Saver saver;
    private String[] bgPaths = {"day.png", "night.png"};
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }
    public JLabel getTimeLabel() {
        return timeLabel;
    }
    public ChessGameFrame(int width, int height) {
        setTitle("Jungle "); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        isDay = true;

        addChessboard();
        addTurnLabel();
        addTimeLabel();
        addThemeButton();
        addUndoButton();
        addRestartButton();
        addSaveButton();
        addLoadButton();
        addBackgroundImage();
        addAiButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE,this);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }
    /**
     * 在游戏面板中添加背景
     */
    private void addBackgroundImage() {
        Image bg1 = new ImageIcon("resource/bg3.png").getImage();
        bg1 = bg1.getScaledInstance(1100, 810,Image.SCALE_DEFAULT);
        ImageIcon icon1 = new ImageIcon(bg1);
        JLabel bg = new JLabel(icon1);
        bg.setSize(1100, 810);
        bg.setLocation(0, 0);

        background = bg;
        add(background);
    }

    /**
     * 在游戏面板中添加标签
     */
    public void addTurnLabel() {
        statusLabel = new JLabel("TURN 1 : BLUE");
        statusLabel.setLocation(2, 2);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.GRAY);
        add(statusLabel);
    }
    public void getTurnLabel(PlayerColor p, int turnCount){
        String s ;
        int turn = turnCount + 1;
        if(p == PlayerColor.BLUE){
            s = "BLUE";
        }else {
            s = "RED";
        }
        statusLabel.setText("TURN " + turnCount + " : " + s);
    }
    //timer
    public void addTimeLabel() {
        timeLabel = new JLabel("00:00");
        timeLabel.setLocation(WIDTH - 150, 1);
        timeLabel.setSize(200, 60);
        timeLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        timeLabel.setForeground(Color.GRAY);
        add(timeLabel);

    }
    public void getTime(String s){
        timeLabel.setText(s);
    }
    //        Timer timeAction = new Timer(1000, new ActionListener() {
//            long timemillis2 = (System.currentTimeMillis()-1000);
//            public void actionPerformed(ActionEvent e) {
//
//                long timemillis1 = System.currentTimeMillis();
//
//                long timemillis = timemillis1-timemillis2;
//
//                // 转换日期显示格式
//                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
//                timeLabel.setText(df.format(new Date(timemillis)));
//            }
//        });
//        timeLabel.setLocation(WIDTH - 150, 1);
//        timeLabel.setSize(200, 60);
//        timeLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
//        timeLabel.setForeground(Color.GRAY);
//
//        timeAction.start();

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addThemeButton() {
        JButton themeButton = new JButton("Theme");
        themeButton.setLocation(HEIGTH, HEIGTH / 10 + 30);
        themeButton.setSize(150, 60);
        themeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(themeButton);

        themeButton.addActionListener(e -> {
            System.out.println("Click to change theme");
            chessboardComponent.changeTheme(isDay);
            if (isDay){
                isDay = false;
            }else{
                isDay = true;
            }
            repaint();
            revalidate();
        });
    }

    private void addUndoButton() {
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener((e) ->
                JOptionPane.showMessageDialog(this, "Undo"));
        undoButton.addActionListener(e -> {
                    System.out.println("Click undo");
                    chessboardComponent.getGameController().undo();});
        undoButton.setLocation(HEIGTH, HEIGTH / 10 + 310);
        undoButton.setSize(150, 60);
        undoButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(undoButton);
    }
    private void addRestartButton() {
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Restart"));
        restartButton.setLocation(HEIGTH, HEIGTH / 10 + 390);
        restartButton.setSize(150, 60);
        restartButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        restartButton.addActionListener(e -> {
            System.out.println("Click restart");
            chessboardComponent.getGameController().restart();});
        add(restartButton);
    }


    private void addSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Save"));
        saveButton.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog("存档名");
            while (path.equals("")){
                JOptionPane.showMessageDialog(null, "存档名不能为空");
                path = JOptionPane.showInputDialog("存档名");
            }
            chessboardComponent.getGameController().Save(path);});
        saveButton.setLocation(HEIGTH, HEIGTH / 10 + 470);
        saveButton.setSize(150, 60);
        saveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(saveButton);
    }

    private void addLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Load"));
        loadButton.addActionListener(e -> {
            System.out.println("Click load");
            chessboardComponent.getGameController().Load();});
        loadButton.setLocation(HEIGTH, HEIGTH / 10 + 550);
        loadButton.setSize(150, 60);
        loadButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(loadButton);
    }

    private void addAiButton() {
        JButton aiButton = new JButton("AI");
        aiButton.setLocation(HEIGTH, HEIGTH / 10 + 180);
        aiButton.setSize(150, 60);
        aiButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(aiButton);

        aiButton.addActionListener(e -> {
            System.out.println("ai");
        });


    }
}
