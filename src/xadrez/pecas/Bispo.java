package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
		
	}

	@Override 
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		//noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna()-1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		}
		//nordeste
		p.setValores(posicao.getLinha()-1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setValores(p.getLinha()-1, p.getColuna()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		
		}
		//sudeste
		p.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setValores(p.getLinha()+1, p.getColuna()+1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		
		}
		//sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna()-1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setValores(p.getLinha()+1, p.getColuna()-1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		}
		
		return mat;
	}

}
