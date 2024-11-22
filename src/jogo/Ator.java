package jogo;

import jplay.*;

import java.awt.*;
import java.util.Vector;

public class Ator extends Sprite{

    String fileName;
    public int vida = 3;
    double speed = 1;
    int direcao = 3;
    boolean movendo = false;

    Controle controle = new Controle();

    public Ator(String fileName, int numFrames) {
        super(fileName, numFrames);
    }


    public void caminho(Scene cena) {

        Point min = new Point((int)this.x, (int)this.y);
        Point max = new Point((int)this.x + this.width, (int)this.y + this.height);

        Vector<?> tiles = cena.getTilesFromPosition(min, max);

        for (int i = 0; i < tiles.size(); i++) {
            TileInfo tile = (TileInfo) tiles.get(i);

            if(controle.colisao(this, tile)==true){
                if(colisaoVertical(this, tile)) {
                    if (tile.y + tile.height - 2 < this.y) {
                        this.y = tile.y + tile.height;
                    } else if (tile.y > this.y + this.height - 2) {
                        this.y = tile.y - this.height;
                    }
                }
                if(colisaoHorizontal(this, tile)){
                    if (tile.x > this.x + this.width - 2) {
                        this.x = tile.x - this.width;
                    }else{
                        this.x = tile.x + this.width;
                    }
                }

            }
        }

    }


    public boolean colisaoVertical(GameObject obj, GameObject obj2) {
        if(obj2.x + obj2.width <= obj.x)
            return false;
        if(obj.x + obj.width <= obj2.x)
            return false;
        return true;
    }

    public boolean colisaoHorizontal(GameObject obj, GameObject obj2) {
        if(obj2.y + obj2.height <= obj.y)
            return false;
        if(obj.y + obj.height <= obj2.y)
            return false;
        return true;
    }
}
