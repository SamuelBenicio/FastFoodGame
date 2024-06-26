package jogo;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import base.Elemento;

public class Pizza extends Elemento {
	
	private int linha;
	private int coluna;
	
	public Pizza() {
		super(0, 0, 16, 16);
		setImagem(new ImageIcon("imagens/sprite_pizza.png"));
		
	}
	
	public void moverParaCima() { 
        if (getPy() - getVel() >= 0) { //Verifica o limite superior para nao ultrapssar
            incPy(-getVel()); // Se o if for verdadeiro, a posicao Y vai sendo diminuida pelo valor da velocidade da pizza que é 5
        }
    }

    public void moverParaBaixo(int altura) { //Mesma logica
        if (getPy() + getAltura() + getVel() <= altura) {
            incPy(getVel());
        }
    }

    public void moverParaEsquerda() { //Mesma logica
        if (getPx() - getVel() >= 0) {
            incPx(-getVel());
        }
    }

    public void moverParaDireita(int largura) { //Mesma logica
        if (getPx() + getLargura() + getVel() <= largura) {
            incPx(getVel());
        }
    }

    
    @Override
	public void desenha(Graphics2D g) {

		int pX = getPx() - 6;
		int pY = getPy() + JogoCenario.ESPACO_TOPO - 6;

		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth() / 4;
		int altMoldura = getImagem().getIconHeight() / 4;

		// Largura e altura do recorte da imagem
		int largImg = largMoldura * coluna;
		int altImg = altMoldura * linha;

		g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);

	}
}
