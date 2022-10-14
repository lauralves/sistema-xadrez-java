package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
		
	}

	@Override 
	public String toString() {
		return "Q";
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

		//acima 
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		}
		//esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		
		}
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		
		}
		//baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && háUmaPecaOponente(p)) {
			mat [p.getLinha()] [p.getColuna()]= true;
		}
		
		
		return mat;
	}

}