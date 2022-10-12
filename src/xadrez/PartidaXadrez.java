package xadrez;

import java.awt.Color;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cores.BRANCO;
		configuraçãoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	public Cores getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean [][] movimentosPossiveis (PosicaoXadrez posicaoOriginal)  {
		Posicao posicao = posicaoOriginal.toPosicao();
		validarPosicaoOriginal(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
		
	}
	
	public PecaXadrez executarMovimento(PosicaoXadrez posicaoOriginal, PosicaoXadrez posicaoDestino) {
		Posicao original = posicaoOriginal.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOriginal(original);
		validarPosicaoDestino(original, destino);
		Peca capturarPeca = fazerMovimento(original, destino);
		proximoTurno();
		return(PecaXadrez) capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao original, Posicao destino) {
		Peca p = tabuleiro.removerPeca(original);
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.posicionarPeca(p, destino);
		return capturarPeca;
	}
	private void validarPosicaoOriginal(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezException("Não há essa peca no tabuleiro");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida nao pertence a voce");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel(posicao)) {
			throw new XadrezException("Não há movimentos possiveis para essa peca");
		}
	}
	private void validarPosicaoDestino(Posicao original, Posicao destino) {
		if (!tabuleiro.peca(original).movimentoPossivel(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}
	
	private void posicionarNovaPeca (char coluna, int linha, Peca peca) {
		tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	private void configuraçãoInicial() {
		 posicionarNovaPeca('c', 2, new Torre(tabuleiro, Cores.BRANCO));
		 posicionarNovaPeca('d', 2,new Torre(tabuleiro, Cores.BRANCO));
		 posicionarNovaPeca('e', 2, new Torre(tabuleiro, Cores.BRANCO));
		 posicionarNovaPeca('e', 1, new Torre(tabuleiro, Cores.BRANCO));
		 posicionarNovaPeca('d', 1, new Rei(tabuleiro, Cores.BRANCO));

		 posicionarNovaPeca('c', 7, new Torre(tabuleiro, Cores.PRETO));
		 posicionarNovaPeca('c', 8, new Torre(tabuleiro, Cores.PRETO));
		 posicionarNovaPeca('d', 7, new Torre(tabuleiro, Cores.PRETO));
		 posicionarNovaPeca('e', 7, new Torre(tabuleiro, Cores.PRETO));
		 posicionarNovaPeca('e', 8, new Torre(tabuleiro, Cores.PRETO));
		 posicionarNovaPeca('d', 8, new Rei(tabuleiro, Cores.PRETO));
	}
	
	
	
}
