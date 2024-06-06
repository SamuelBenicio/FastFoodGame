package jogo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ranking {
    private static final String RANKING_FILE = "ranking.txt"; //nome do arquivo
    private List<Jogador> jogadores;

    public Ranking() { //Construtor
        jogadores = new ArrayList<>(); //Cria uma arraList com o nome de jogadores
        carregarRanking(); //chama esse metodo para carregar os dados
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador); //adiciona a lista
        Collections.sort(jogadores); //ordena em ordem decrescente
        if (jogadores.size() > 10) { //Se for maior que 10
            jogadores = jogadores.subList(0, 10); //Crie uma sub lista jogadores com apenas as 10 maiores pontuacoes
        }
        salvarRanking();
    }

    public List<Jogador> getJogadores() { //Retorna ranking
        return jogadores;
    }

   
	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	private void carregarRanking() {
        try (FileInputStream arquivo = new FileInputStream(RANKING_FILE); //Abre o arquivo ranking.txt 
             Scanner scannerArquivo = new Scanner(arquivo)) { //Cria um scanner para ler o que tem no arquivo

            while (scannerArquivo.hasNextLine()) { //Le cada linhas enquanto tiver linha
                String linha = scannerArquivo.nextLine(); //Le uma linha
                jogadores.add(Jogador.quebraString(linha)); //Converte a linha em um objeto e adiciona a lista
            }
        } 
        catch (IOException e) { //Se der erro cria uma novo lista
            jogadores = new ArrayList<>();
        }
    }

    private void salvarRanking() {
        try (FileOutputStream arquivo = new FileOutputStream(RANKING_FILE);
             PrintWriter writer = new PrintWriter(arquivo)) { //Print writer para escrever no arquivo

            for (Jogador jogador : jogadores) {
                writer.println(jogador.toString()); //Converte o objeto jogador em string e escreve no arquivo
            }
        } catch (IOException e) { //Erro
            
        }
    }
}
