package jogo;

import javax.swing.ImageIcon;
import base.Elemento;

public class Peperoni extends Elemento {

    public Peperoni() {
        super(0, 0, 16, 16);
        setImagem(new ImageIcon("imagens/pepperoni.png"));
        setVel(4);
        setAtivo(true);
    }

    public void inicializarPosicao(int largura, int altura) {
        setPx(0);
        setPy((int) (Math.random() * (altura - getAltura())));
    }

    public void mover(int largura) {
        incPx(getVel());
        if (getPx() > largura) {
            setAtivo(false);
        }
    }
}