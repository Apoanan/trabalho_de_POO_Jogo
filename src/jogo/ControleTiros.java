package jogo;

import jplay.Scene;
import jplay.Sound;
import jplay.URL;

import java.util.LinkedList;
import java.util.List;

public class ControleTiros {
    LinkedList<Tiro> tiros = new LinkedList<>();

    public void adicionarTiro(double x, double y, int caminho, Scene cena) {
        Tiro tiro = new Tiro(x,y,caminho);
        tiros.addFirst(tiro);
        cena.addOverlay(tiro);
        bulletSound();
    }

    public void run(Enemi inimigo){
        for(int i = 0; i < tiros.size(); i++){
            Tiro tiro = tiros.removeFirst();
            tiro.mover();
            tiros.addLast(tiro);

            if (tiro.collided(inimigo)){
                inimigo.vida = 0;
                inimigo.morrer();
            }
        }

    }

    private void bulletSound(){
        new Sound(URL.audio("tiro.WAV")).play();
    }
}
