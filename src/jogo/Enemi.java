package jogo;


import jplay.Sound;
import jplay.URL;
import jplay.Window;


import java.awt.*;


public class Enemi extends Ator{

    int ataque = 1;
    private boolean podeAtacar = true;

    public Enemi (int x, int y) {
        super(URL.sprite("Zumbi 02.png"), 16);
        this.x =x;
        this.y =y;
        this.setTotalDuration(2000);
        this.speed = 0.3;

    }

    public void follow(double    x , double y){
        if(this.x > x && this.y <= y + 50 && this.y >= y - 50){
            moveTo(x, y, speed);
            if (direcao != 1){
                setSequence(4,8);
                direcao = 1;
            }
            movendo = true;
        }
        else if(this.x < x && this.y <= y + 50 && this.y >= y - 50){
            moveTo(x, y, speed);
            if (direcao != 2){
                setSequence(8,12);
                direcao = 2;
            }
            movendo = true;
        }
        else if(this.y < y){
            moveTo(x, y, speed);
            if (direcao != 4){
                setSequence(0,4);
                direcao = 4;
            }
            movendo = true;
        }
        else if (this.y > y) {
            moveTo(x, y, speed);
            if (direcao != 5){
                setSequence(12,16);
                direcao = 5;
            }
            movendo = true;
        }
        if(movendo){
            update();
            movendo = false;
        }

    }

    public void morrer() {
        if(this.vida <=0){
            this.speed = 0;
            this.ataque = 0;
            this.direcao = 0;
            this.movendo = false;
            this.hide();
        }
    }

    public void atacar(Player player, Window window) {
        if (this.collided(player) && podeAtacar) {
            player.vida -= ataque;
            //player.setSequence(,);
            new Sound(URL.audio("hit.wav")).play();  // Som do ataque

            // Se a vida do player for 0 ou menor, game over

            podeAtacar = false;

            // Inicia uma nova thread para permitir o ataque de novo após um tempo
            new Thread(() -> {
                try {
                    Thread.sleep(1000);  // Atraso de 1 segundo para permitir o próximo ataque
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    podeAtacar = true;  // Permite atacar novamente após o tempo de espera
                    // Restaura o sprite original do player após o tempo de 500ms
                    //player.setSequence();// Troca de volta para o sprite original
                }
            }).start();

            if (player.vida <= 0) {
                window.drawText("GAME OVER", 400, 300, Color.WHITE);
                new Sound(URL.audio("GAME_OVER.WAV")).play();  // Som de game over
                player.life(window);

                window.update();
                window.delay(5000);  // Delay antes de sair
                System.exit(0);
            }
        }
    }


}
