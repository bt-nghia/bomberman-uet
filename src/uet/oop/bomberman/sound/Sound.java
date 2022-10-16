package uet.oop.bomberman.sound;

import uet.oop.bomberman.BombermanGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Sound {
    // play multiple track at same time
    public static synchronized void playSound(String sound, int time) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(BombermanGame.class.getResourceAsStream("/sound/" + sound + ".wav"))
                    );
                    clip.open(inputStream);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-20.0f);
                    clip.start();
                    TimerTask stopSoundTask = new TimerTask() {
                        @Override
                        public void run() {
                            stopSound(clip);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(stopSoundTask, time);
                } catch (Exception e) {
                    System.out.println("sound track error " + e.getMessage());
                }
            }
        }
        ).start();
    }

    public static synchronized void stopSound(Clip clip) {
        clip.stop();
    }
}