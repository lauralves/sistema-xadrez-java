package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		configuraçãoInicial();
	}
	public PecaXadrez [][] getPecas(){
		PecaXadrez [][] matriz = new PecaXadrez[tabuleiro.getLinha()] [tabuleiro.getColuna()];
		for (int i=0; i<tabuleiro.getLinha();i++) {
			for (int j=0; j<tabuleiro.getColuna();j++) {
				matriz [i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return matriz;
	
	}
	
	private void configuraçãoInicial() {
		tabuleiro.posicionarPeca(new Torre(tabuleiro, Cores.BRANCO), new Posicao(2,1));
		tabuleiro.posicionarPeca(new Rei(tabuleiro, Cores.PRETO), new Posicao(0,4));
	}
	
	
	
}
