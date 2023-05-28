package view;

import javax.swing.*;

public class Time extends Thread{
    JLabel l;
    int time = 0;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public Time(JLabel l) {
        super();
        this.l = l;
    }

    public void run() {
        boolean b = true;
        l.setText("45");
        while(b) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            time--;
            if(time >= 0){
                String sec = String.valueOf(time);
                l.setText(sec);
            }else{
                b = false;
            }
        }
    }



}
