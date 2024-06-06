package jogo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.ImageIcon;

import base.CenarioPadrao;
import base.Texto;
import base.Util;

public class JogoCenario extends CenarioPadrao {
	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}

	/*public enum Direcao {
		NORTE, SUL, OESTE, LESTE;
	}
	*/
	
	private Pizza pizza;
	
	private Legume[] legumes;
    
	private Peperoni[] peperonis;
	
	private Texto texto = new Texto();
	
	private Random rand = new Random();
	
	private Estado estado = Estado.JOGANDO;

	public static final int ESPACO_TOPO = 25; // Espacamento topo
	
	private static final int NUM_LEGUMES = 6;
	
    private static final int NUM_PEPPERONIS = 5;

	
    //private final int numLegumes = 4;
    private int vidas = 6;
	
	private int pontuacao =0;
	
	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {
		

		texto.setCor(Color.WHITE);

		pizza = new Pizza();
		pizza.setVel(6);
        pizza.setAtivo(true);
        //pizza.setPx(largura / 2);
        //pizza.setPy(altura - 50);
        Util.centraliza(pizza, largura, altura);
        
        legumes = new Legume[NUM_LEGUMES];
        for (int i = 0; i < legumes.length; i++) {
            legumes[i] = new Legume();
            legumes[i].inicializarPosicao(largura, altura);
        }

        peperonis = new Peperoni[NUM_PEPPERONIS];
        for (int i = 0; i < peperonis.length; i++) {
            peperonis[i] = new Peperoni();
            peperonis[i].inicializarPosicao(largura, altura);
        }
       }


	
	@Override
	public void descarregar() {
		pizza.setAtivo(false);
		for (Legume legume : legumes) {
            legume.setAtivo(false);
        }
        for (Peperoni pepperoni : peperonis) {
            pepperoni.setAtivo(false);
        }
        Jogo.finalizarJogo(pontuacao);
        estado = Estado.PERDEU;
    }


	@Override
    public void atualizar() {
		if (estado == Estado.JOGANDO) {
            if (Jogo.controleTecla[Jogo.Tecla.CIMA.ordinal()]) {
                pizza.moverParaCima();
            }
            if (Jogo.controleTecla[Jogo.Tecla.BAIXO.ordinal()]) {
                pizza.moverParaBaixo(altura);
            }
            if (Jogo.controleTecla[Jogo.Tecla.ESQUERDA.ordinal()]) {
                pizza.moverParaEsquerda();
            }
            if (Jogo.controleTecla[Jogo.Tecla.DIREITA.ordinal()]) {
                pizza.moverParaDireita(largura);
            }

            // Movimentação e colisão com Legumes
            for (Legume legume : legumes) {
                if (legume.isAtivo()) {
                    legume.mover(largura);
                    if (Util.colide(pizza, legume)) {
                        vidas--;
                        legume.setAtivo(false); // Legume desaparece
                        if (vidas <= 0) {
                            estado = Estado.PERDEU;
                        }
                    }
                } else {
                    legume.setAtivo(true);
                    legume.inicializarPosicao(largura, altura);
                }
            }

            // Movimentação e colisão com Pepperonis
            for (Peperoni peperoni : peperonis) {
                if (peperoni.isAtivo()) {
                    peperoni.mover(largura);
                    if (Util.colide(pizza, peperoni)) {
                        pontuacao++;
                        peperoni.setAtivo(false); // Pepperoni desaparece
                    }
                } else {
                    peperoni.setAtivo(true);
                    peperoni.inicializarPosicao(largura, altura);
                }
            }
        }
    }
	@Override
    public void desenhar(Graphics2D g) {
		pizza.desenha(g);
		for (Legume legume : legumes) {
            if (legume.isAtivo()) {
                legume.desenha(g);
            }
        }
        for (Peperoni peperoni : peperonis) {
            if (peperoni.isAtivo()) {
                peperoni.desenha(g);
            }
        }
        texto.desenha(g, "Vidas: " + vidas, 10, 20);
        texto.desenha(g, "Pontuação: " + pontuacao, 10, 40);
        if (estado == Estado.PERDEU) {
            texto.desenha(g, "Game Over", largura / 2 - 50, altura / 2);
        }
    }
}