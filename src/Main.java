import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;
import java.net.URL;
import view.Music;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);
        });
        URL musicURL = Main.class.getResource("/music.wav");

        Music musicThread = new Music(musicURL, true);

        Thread music = new Thread(musicThread);
        music.start();

        musicThread.setVolume(0.5f); // 设置音量为一半
        float volume = musicThread.getVolume(); // 获取当前音量
        System.out.println("volume: " + volume);
    }
}
