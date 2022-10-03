package xadrez;

import tabuleiro.PecaTabuleiro;
import tabuleiro.Tabuleiro;

public class PecaXadrez extends PecaTabuleiro {
	private Cores cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cores getCor() {
		return cor;
	}
	
}
