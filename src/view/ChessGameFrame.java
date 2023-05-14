package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
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
    private JLabel statusLabel;

    private ChessboardComponent chessboardComponent;
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
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


        addChessboard();
        addTurnLabel();
        addTimeLabel();
        addThemeButton();
        addUndoButton();
        addRestartButton();
        addSaveButton();
        addLoadButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }
    /**
     * 在游戏面板中添加背景
     */
//    private void addBackgroundImage() {
//        URL defaultPath = getClass().getResource(bgPaths[0]);
//        mainPanel = new ImagePanel(defaultPath);
//        setContentPane(mainPanel);
//        mainPanel.setLayout(null);
//    }
//    public void setBackgroundImage(int index) {
//        URL path = getClass().getResource(bgPaths[index - 1]);
//        mainPanel.setBackgroundImage(path);
//        mainPanel.repaint();
//    }

    /**
     * 在游戏面板中添加标签
     */
    private void addTurnLabel() {
        JLabel statusLabel = new JLabel("TURN 1: Blue");
        statusLabel.setLocation(1, 1);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addTimeLabel() {
        JLabel timeLabel = new JLabel("TIME: 00:00");
        timeLabel.setLocation(WIDTH - 150, 1);
        timeLabel.setSize(200, 60);
        timeLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(timeLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addThemeButton() {
        JButton undoButton = new JButton("Theme");
        undoButton.addActionListener((e) ->
                JOptionPane.showMessageDialog(this, "THeme"));
//        SettingButton.addActionListener(e -> {
//            System.out.println("Click setting");
//            SwingUtilities.invokeLater(() -> {
//                SettingGameFrame settingFrame = new SettingGameFrame(500, 750, this);
//                settingFrame.setVisible(true);
//            });
//        });
        undoButton.setLocation(HEIGTH, HEIGTH / 10 + 30);
        undoButton.setSize(150, 60);
        undoButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(undoButton);
    }
    private void addUndoButton() {
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener((e) ->
                JOptionPane.showMessageDialog(this, "Undo"));
//        UndoButton.addActionListener(e -> {
//                    System.out.println("Click undo");
//                    chessboardComponent.getGameController().undo();
        undoButton.setLocation(HEIGTH, HEIGTH / 10 + 310);
        undoButton.setSize(150, 60);
        undoButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(undoButton);
    }
    private void addRestartButton() {
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Restart"));
//        RestartButton.addActionListener(e -> {
//                    System.out.println("Click restart");
//                    chessboardComponent.getGameController().restart();
        restartButton.setLocation(HEIGTH, HEIGTH / 10 + 390);
        restartButton.setSize(150, 60);
        restartButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(restartButton);
    }
    private void addSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Save"));
//        SaveButton.addActionListener(e -> {
//            System.out.println("Click save");
//            chessboardComponent.getGameController().save();
        saveButton.setLocation(HEIGTH, HEIGTH / 10 + 470);
        saveButton.setSize(150, 60);
        saveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(saveButton);
    }
    private void addLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Load"));
//        LoadButton.addActionListener(e -> {
//                    System.out.println("Click load");
//                    chessboardComponent.getGameController().load();
        loadButton.setLocation(HEIGTH, HEIGTH / 10 + 550);
        loadButton.setSize(150, 60);
        loadButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(loadButton);
    }

    public void updateStatus(String status) {
        statusLabel.setText(status);
    }


//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
