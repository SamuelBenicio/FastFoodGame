package jogo;

public class Jogador implements Comparable<Jogador> {
    private String nome;
    private int pontuacao;

    public Jogador(String nome, int pontuacao) { //Construtor do jogador
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public int compareTo(Jogador novo) { //Compara os jogadores com base na pontucao
        return Integer.compare(novo.pontuacao, this.pontuacao); //Ordenar o ranking
    }

    @Override
    public String toString() { //Toda vez que jogador for chamado vai ser printado assim
        return nome + "," + pontuacao;
    }

    public static Jogador quebraString(String linha) {
        String[] partes = linha.split(","); //divide a string em linhas
        return new Jogador(partes[0], Integer.parseInt(partes[1])); //parte [0] nome do jogador
        //parte [1] pontuacao e faz a conversao da String para inteiro
    }
}