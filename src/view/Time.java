package view;

import controller.GameController;

import javax.swing.*;

public class Time {
    private JLabel label;
    public int time;
    public Timer timer;
    public GameController controller;

    public Time(JLabel label,GameController controller) {
        this.label = label;
        this.controller=controller;
        this.timer = new Timer(1000, e -> tick());
    }


    public void start(int time) {
        this.time = time;
        updateLabel();
        timer.start();
    }

    public void reset(int time) {
        this.time = time;
        updateLabel();
    }

    private void tick() {
        time--;
        updateLabel();
    }

    private void updateLabel() {
        if(time >=0){
            label.setText(String.valueOf(time));
        }
    }

    public boolean checkAI(){
        if(time <= 0){
            return true;
        }
        return false;
    }

}