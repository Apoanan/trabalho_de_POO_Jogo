package jogo;

import jplay.*;
import jplay.Window;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Ator{

    Keyboard teclado;
    Window window;
    private String spriteOriginal;
    private boolean isChangingSprite;
    protected String fileName;

    public Player(int x, int y) {
        super(URL.sprite("Zumbi_03.png"), 16);
        this.x = x;
        this.y = y;
        this.setTotalDuration(2000);
        this.speed = 1;
        this.fileName = "Zumbi_03.png";
        this.isChangingSprite = false;
    }

    ControleTiros tiros = new ControleTiros();

    public void atirar(Window janela, Scene cena, Keyboard teclado, Enemi inimigo) {

        if (teclado.keyDown(KeyEvent.VK_A)){
            tiros.adicionarTiro(x, y, direcao, cena);
            tiros.run(inimigo);
        } else {
            tiros.run(inimigo);
        }
    }

    public void changeSprite(String newSprite, int duration) {
        if (!isChangingSprite) {
            isChangingSprite = true;
            this.fileName = newSprite;
            this.loadImage(URL.sprite(newSprite));


            new Thread(() -> {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.fileName = spriteOriginal;
                this.loadImage(URL.sprite(spriteOriginal));
                isChangingSprite = false;
            }).start();
        }
    }

    public void controle(Window janela, Keyboard teclado) {
        this.window = janela;
        this.teclado = teclado;

        if (teclado.keyDown(Keyboard.LEFT_KEY)) {
                if (this.x > 0)
                    this.x -= speed;
                if (direcao != 1){
                    setSequence(4,8);
                    direcao = 1;
                }movendo = true;
        }else if (teclado.keyDown(Keyboard.RIGHT_KEY)) {
            if (this.x < window.getWidth() - 60)
                this.x += speed;
            if (direcao != 2){
                setSequence(8,12);
                direcao = 2;
            }movendo = true;
        }else if (teclado.keyDown(Keyboard.UP_KEY)) {
            if (this.y > 0)
                this.y -= speed;
            if (direcao != 4){
                setSequence(12,16);
                direcao = 4;
            }movendo = true;
        }else if (teclado.keyDown(Keyboard.DOWN_KEY)) {
            if (this.y < window.getHeight() - 60)
                this.y += speed;
            if (direcao != 5){
                setSequence(0,4);
                direcao = 5;
            }movendo = true;
        }
        if(movendo){
            update();
            movendo = false;
        }
    }


    public void life( Window janela) {
        Font f = new Font("Arial", Font.BOLD, 30);
        janela.drawText("Vida: " + (this.vida), 60, 60, Color.RED, f);
    }

}

