package Tetris.resource;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {
    
    private Clip soundClip;
    private AudioInputStream soundStream;

    public SoundEffect(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream soundStream = AudioSystem.getAudioInputStream(soundFile);
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(soundStream);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        /*
         * Play the sound clip
         */
        soundClip.start();
    }

    public void stop() throws IOException {
        /*
         * Stop the clip
         */
        soundStream.close();
        soundClip.close();
        soundClip.stop();
    } 

}
