package Tetris.resource;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {
    
    private Clip soundClip;
    private AudioInputStream soundStream;

    public SoundEffect(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);

            // Check if the file exists or not
            if(!soundFile.exists()) {
                System.out.println("The file path \"" + soundFileName + "\" doesn't exist");
                System.out.println("CWD: " + System.getProperty("user.dir"));
                return;
            }

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
        soundClip.setFramePosition(0);
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
