package jogo;

import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

import java.awt.*;
import java.util.Random;

public class Cenario {
    // Declaração de variáveis da classe Cenário
    private Window janela;
    private Scene cena;
    private Player player;
    private Keyboard teclado;
    private Enemi[] zumbis;  // Array de zumbis
    private Random random;
    private long tempoInicial = System.currentTimeMillis();
    private long duracao = 40 * 1000;
    private long ultimoSpawn;
    private long intervaloGeracao = 5000;
    private int maxZumbi = 20;
    private int numZumbis = 0; // Controle do número de zumbis gerados

    // Método Construtor
    public Cenario(Window window) {
        // Instância dos objetos
        janela = window;
        random = new Random();
        cena = new Scene();
        cena.loadFromFile(URL.scenario("Cenario.scn"));
        player = new Player(640, 350);
        teclado = janela.getKeyboard();
        zumbis = new Enemi[maxZumbi];  // Array fixo para armazenar os inimigos
        ultimoSpawn = System.currentTimeMillis();

        // Chamamento de métodos da classe no método construtor
        Som.play("gwyn_theme.mid");
        run();
    }

    // Método run, que roda os métodos das outras classes dentro da classe Cenário
    private void run() {
        while (true) {
            long tempoAtual = System.currentTimeMillis();
            long tempoRestante = (duracao - (tempoAtual - tempoInicial)) / 1000;

            Graphics g = janela.getGameGraphics();
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Tempo: " + tempoRestante + "s", 50, 100);
            janela.update();

            if (tempoRestante <= 0) {
                janela.drawText("Tempo esgotado! VITÓRIA", 400, 300, Color.RED);
                janela.update();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                janela.exit();
                System.exit(0);
            }

            // Movimenta o jogador e verifica as teclas pressionadas
            player.controle(janela, teclado);
            player.caminho(cena);
            cena.moveScene(player);
            player.x += cena.getXOffset();
            player.y += cena.getYOffset();
            player.draw();

            player.life(janela);

            // Gera novos zumbis periodicamente (a cada intervalo)
            if (tempoAtual - ultimoSpawn >= intervaloGeracao) {
                gerarZumbis();
                ultimoSpawn = tempoAtual;
            }

            // Atualiza e desenha os zumbis
            for (int i = 0; i < numZumbis; i++) {
                if (zumbis[i] != null) {
                    zumbis[i].caminho(cena);
                    zumbis[i].follow(player.x, player.y);
                    zumbis[i].x += cena.getXOffset();
                    zumbis[i].y += cena.getYOffset();
                    zumbis[i].draw();
                    player.atirar(janela, cena, teclado, zumbis[i]);
                    zumbis[i].atacar(player, janela);
                }
            }

            janela.update();
        }
    }

    // Método para gerar zumbis aleatórios
    private void gerarZumbis() {
        if (numZumbis < maxZumbi) {
            int n = 2;

            // Não gerar mais zumbis se o número total ultrapassar 20
            if (numZumbis + n > maxZumbi) {
                n = maxZumbi - numZumbis;  // Ajusta o número de zumbis a serem gerados
            }

            // Geração de zumbis
            for (int i = 0; i < n; i++) {
                if (numZumbis < maxZumbi) {
                    int xz = random.nextInt((int) janela.getWidth());
                    int yz = random.nextInt((int) janela.getHeight());
                    zumbis[numZumbis] = new Enemi(xz, yz);
                    numZumbis++;  // Incrementa o contador de zumbis
                }
            }
        }
    }
}
