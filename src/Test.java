

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Test extends JFrame {
    // 添加 显示时间的JLabel

    public Test() {
        JLabel time = new JLabel();
        add(time);
        this.setTimer(time);
    } // 设置Timer
    // 1000ms实现一次动作
    private void setTimer(JLabel time) {
        final JLabel varTime = time;
        Timer timeAction = new Timer(1000, new ActionListener() {
            long timemillis2 = (System.currentTimeMillis()-1000);
            public void actionPerformed(ActionEvent e) {

                long timemillis1 = System.currentTimeMillis();

                long timemillis = timemillis1-timemillis2;

                // 转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    } // 运行方法

    public static void main(String[] args) {
        Test timeFrame = new Test();
        timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timeFrame.setSize(160, 80);
        timeFrame.setVisible(true);
    }
}

