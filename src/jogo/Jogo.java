package jogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jogo.Jogador;
import jogo.Ranking;
import base.CenarioPadrao;


public class Jogo extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int FPS = 1000 / 20;

	private static final int JANELA_ALTURA = 550;

	private static final int JANELA_LARGURA = 448;

	private JPanel tela;

	private Graphics2D g2d;

	private BufferedImage buffer;
	
	private static Ranking ranking;

	private CenarioPadrao cenario;

	public static int nivel;
	
	public static boolean pausado;
	
	public enum Tecla {
		CIMA, BAIXO, ESQUERDA, DIREITA, BA, BB, BC
	}
	
	public static boolean[] controleTecla = new boolean[Tecla.values().length];

	public static void liberaTeclas() {
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}
	
	//Define se a tecla esta pressionado ou nao
	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_UP:
			controleTecla[Tecla.CIMA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[Tecla.BAIXO.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[Tecla.ESQUERDA.ordinal()] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[Tecla.DIREITA.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ESCAPE:
			controleTecla[Tecla.BB.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_SPACE:
			controleTecla[Tecla.BC.ordinal()] = pressionada;
			break;

		case KeyEvent.VK_ENTER:
			controleTecla[Tecla.BA.ordinal()] = pressionada;
		}
	}

	//Construtor que nicializa o jogo
	public Jogo() {
		ranking = new Ranking(); //Inicializa o ranking
		this.addKeyListener(new KeyListener() { //Teclado

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});

		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);

		g2d = buffer.createGraphics();

		tela = new JPanel() { //Configura o painel de exibicao
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(JANELA_LARGURA, JANELA_ALTURA);
			}

			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
		};

		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();

		setVisible(true);
		tela.repaint();

	}

	private void carregarJogo() {
		cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
		cenario.carregar();
	}

	public void iniciarJogo() { //Inicia o loop principal do jogo
		long prxAtualizacao = 0; //tempo 0 para o proximo frame

		while (true) {
			if (System.currentTimeMillis() >= prxAtualizacao) { //Verifica se é para atualizar o frame

				g2d.setColor(Color.BLACK); //Fundo preto
				g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);

				if (controleTecla[Tecla.BA.ordinal()]) {
					// Pressionou espaço ou enter
					if (cenario instanceof InicioCenario) {
						cenario.descarregar();
						cenario = null;
						cenario = new JogoCenario(tela.getWidth(), tela.getHeight());

						g2d.setColor(Color.WHITE);
						g2d.drawString("Carregando...", 20, 20);
						tela.repaint();

						cenario.carregar();

					} else { //Estado do jogo
						Jogo.pausado = !Jogo.pausado;
					}

					liberaTeclas();

				} else if (controleTecla[Tecla.BB.ordinal()]) {
					// Pressionou ESC
					if (!(cenario instanceof InicioCenario)) {
						cenario.descarregar();

						cenario = null;
						cenario = new InicioCenario(tela.getWidth(), tela.getHeight());
						cenario.carregar();
					}

					liberaTeclas();

				}

				if (cenario == null) {
					g2d.setColor(Color.WHITE);
					g2d.drawString("Carregando...", 20, 20);

				} else {
					if (!Jogo.pausado)
						cenario.atualizar();

					cenario.desenhar(g2d);

					if (Jogo.pausado) {
						g2d.setColor(Color.WHITE);
						g2d.drawString("Pausado", tela.getWidth() / 2 - 30, tela.getHeight() / 2);
					}
				}

				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}
	
	 public static void finalizarJogo(int pontuacao) { //Finaliza o jogo e registra a pontuacao no ranking
	        String nome = JOptionPane.showInputDialog(null, "Digite seu nome: ");
	        if (nome != null && !nome.trim().isEmpty()) { //remove espacos em branco do inicio e fim do texto
	            Jogador jogador = new Jogador(nome, pontuacao); //Instancia jogador com nome e pontuacao
	            ranking.adicionarJogador(jogador); //adiciona jogador ao ranking
	        }
	        exibirRanking(); //mostra o ranking
	    }
	 
	 
	 private static void exibirRanking() { //Exibe o ranking
	        StringBuilder rankingStr = new StringBuilder("Ranking:\n");
	        List<Jogador> jogadores = ranking.getJogadores();
	        for (int i = 0; i < jogadores.size(); i++) {
	            Jogador jogador = jogadores.get(i);
	            rankingStr.append(String.format("%d. %s - %d pontos\n", i + 1, jogador.getNome(), jogador.getPontuacao()));
	        }
	        JOptionPane.showMessageDialog(null, rankingStr.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
	    }
	
	

	public static void main(String[] args) { //Método principal que inicia o jogo
		//String nome = JOptionPane.showInputDialog(null, "Digite seu nome: ");
		Jogo jogo = new Jogo();
		jogo.carregarJogo();
		jogo.iniciarJogo();
	}

}

