package tabuleiro;

public class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null; //é nulo automaticamente pelo Java, mas é importante enfatizar para fins didáticos
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	
}
