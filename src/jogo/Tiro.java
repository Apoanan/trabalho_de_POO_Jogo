package jogo;

import jplay.Sprite;
import jplay.URL;

public class Tiro extends Sprite {

    public static final int LEFT = 1, RIGHT = 2, STOP = 3, UP = 4, DOWN = 5;

    protected static final double BULLET_SPEED = 20;
    protected int caminho = STOP;
    protected boolean movendo = false;
    protected int direcao = 3;

    public Tiro(double x, double y, int caminho) {
        super(URL.sprite("tiro.png"), 16);
        this.caminho = caminho;
        this.x = x;
        this.y = y;
        this.setTotalDuration(2000);
    }
    public void mover(){
        if(caminho == LEFT){
            this.x -= BULLET_SPEED;
            if(direcao != 1){
                setSequence(4,8);
            }
            movendo = true;
        }
        if(caminho == RIGHT){
            this.x += BULLET_SPEED;
            if(direcao != 2){
                setSequence(8, 12);
            }
            movendo = true;
        }
        if(caminho == UP){
            this.y -= BULLET_SPEED;
            if(direcao != 4){
                setSequence(12,16);
            }
        }
        if(caminho == DOWN || caminho == STOP){
            this.y += BULLET_SPEED;
            if(direcao != 5){
                setSequence(0, 4);
            }
            movendo = true;
        }
        if(movendo){
            update();
            movendo = false;
        }
    }

}

