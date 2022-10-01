package xadrez;

import tabuleiro.Peca;
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
		PecaXadrez [][] matriz = new PecaXadrez[tabuleiro.getLinhas()] [tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas();i++) {
			for (int j=0; j<tabuleiro.getColunas();j++) {
				matriz [i][j] = (PecaXadrez) tabuleiro.peca(i,j);
			}
		}
		return matriz;
	
	}
	
	private void posicionarNovaPeca (char coluna, int linha, Peca peca) {
		tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	private void configuraçãoInicial() {
		posicionarNovaPeca('e', 8, new Torre(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('a', 5, new Rei(tabuleiro, Cores.PRETO));
	}
	
	
	
}
