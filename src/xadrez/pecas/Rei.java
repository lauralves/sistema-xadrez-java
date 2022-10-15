package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;
	
	public Rei(Tabuleiro tabuleiro, Cores cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testarRoqueTorre(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContarMovimento() == 0;
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		// acima 
		
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//esquerda
		
		p.setValores(posicao.getLinha(), posicao.getColuna() -1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//direita
		
		p.setValores(posicao.getLinha(), posicao.getColuna() +1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//noroeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//nordeste
		
		p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//sudoeste
		
		p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//sudeste
		
		p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
		if (getTabuleiro().posicaoExistente(p)  && podeMover(p)) {
			mat [p.getLinha()] [p.getColuna()] = true;
			
		}
		
		//movimento especial roque
		
		if(getContarMovimento() == 0 && !partidaXadrez.getCheck()) {
			
			//movimento especial roque rei torre
			
			Posicao posicaoT1 = new Posicao(posicao.getLinha(), posicao.getColuna() +3);
			if (testarRoqueTorre(posicaoT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
				if(getTabuleiro().peca(p1)== null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() +2] = true;
				}
			
			}
			
	//movimento especial roque rainha torre
			
			Posicao posicaoT2 = new Posicao(posicao.getLinha(), posicao.getColuna() -4);
			if (testarRoqueTorre(posicaoT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() -2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() -3);
				if(getTabuleiro().peca(p1)== null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			
			}
		}
		
		return mat;
	}

}
