package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		if(getCor()== Cores.BRANCO) {
			p.setValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2)  && getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() -1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.setValores(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() +2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2)  && getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() +1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		
		return mat;
	}
	@Override 
	public String toString() {
		return "P";
	}


}
