package jogo;

import jplay.Sound;
import jplay.URL;

public class Som {

    private static Sound sound;

    public static void play(String audio){
        sound = new Sound(URL.audio(audio));
        Som.sound.play();
        Som.sound.setRepeat(true);
    }

    public static void stop(){
        if(Som.sound != null){
            sound.stop();
        }
    }
}
