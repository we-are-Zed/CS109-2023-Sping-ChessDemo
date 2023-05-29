package view;

import controller.GameController;
import model.Chessboard;
//import model.Difficulty;

import javax.swing.*;
import java.awt.*;

//begin game 界面

public class Begin extends JFrame {
    ChessGameFrame chessGameFrame;

    private final int WIDTH;
    private final int HEIGHT;

    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }


    public Begin() {
        setTitle("Jungle");
        this.WIDTH = 400;
        this.HEIGHT = 500;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        ChessGameFrame chessGameFrame = new ChessGameFrame(1100, 810);
        GameController controller = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard());
        this.chessGameFrame = chessGameFrame;
        chessGameFrame.begin = this;

        addBeginButton();

        Image image = new ImageIcon("resource/bg3.png").getImage();
        image = image.getScaledInstance(400, 500,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);

        JLabel bg = new JLabel(icon);
        bg.setSize(400, 500);
        bg.setLocation(0, 0);
        add(bg);
    }

    private void addBeginButton() {
        JButton button = new JButton("Begin");
        button.addActionListener((e) -> {
            this.setVisible(false);


            chessGameFrame.repaint();
            chessGameFrame.getChessboardComponent().getGameController().restart();
            chessGameFrame.setVisible(true);
        });

        button.setLocation(100, 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
}
