package tabuleiro;

public class PecaTabuleiro {
	protected PosicaoTabuleiro posicao;
	private Tabuleiro tabuleiro;
	
	public PecaTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null; //é nulo automaticamente pelo Java, mas é importante enfatizar para fins didáticos
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	
}
