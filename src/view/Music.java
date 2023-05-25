package view;

import javax.sound.sampled.*;
import java.net.URL;

public class Music implements Runnable {
    private URL musicPath;
    private boolean isLoop;
    private FloatControl gainControl;

    public Music(URL musicPath, boolean isLoop) {
        this.musicPath = musicPath;
        this.isLoop = isLoop;
    }

    @Override
    public void run() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicPath);
            clip.open(ais);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (gainControl != null) {
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public float getVolume() {
        if (gainControl != null) {
            return (float) Math.pow(10.0, gainControl.getValue() / 20.0);
        }
        return 0.0f;
    }
}
