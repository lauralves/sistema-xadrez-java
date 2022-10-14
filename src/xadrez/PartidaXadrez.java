package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;

	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOriginal) {
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

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(original, destino, capturarPeca);
			throw new XadrezException("Voce nao pode se colocar em check");
		}
		check = testarCheck(oponente(jogadorAtual)) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {

			proximoTurno();
		}

		return (PecaXadrez) capturarPeca;
	}

	private Peca fazerMovimento(Posicao original, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(original);
		p.incrementarContarMovimento();
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.posicionarPeca(p, destino);

		if (capturarPeca != null) {
			pecasTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		return capturarPeca;
	}

	private void desfazerMovimento(Posicao original, Posicao destino, Peca capturarPeca) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContarMovimento();
		tabuleiro.posicionarPeca(p, original);

		if (capturarPeca != null) {
			tabuleiro.posicionarPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasTabuleiro.add(capturarPeca);
		}
	}

	private void validarPosicaoOriginal(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezException("Não há essa peca no tabuleiro");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida nao pertence a voce");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel(posicao)) {
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

	private Cores oponente(Cores cor) {
		return (cor == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private PecaXadrez rei(Cores cor) {
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não há rei " + cor + " no tabuleiro");
	}

	private boolean testarCheck(Cores cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> oponentePecas = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cores cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao original = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = fazerMovimento(original, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(original, destino, capturarPeca);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void posicionarNovaPeca(char coluna, int linha, Peca peca) {
		tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasTabuleiro.add(peca);
	}

	private void configuraçãoInicial() {
		posicionarNovaPeca('a', 1, new Torre(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('b', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('c', 1, new Bispo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('f', 1, new Bispo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('g', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('h', 1, new Torre(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('a', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('b', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('c', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('d', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('e', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('f', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('g', 2, new Peao(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('h', 2, new Peao(tabuleiro, Cores.BRANCO));
		

		posicionarNovaPeca('a', 8, new Torre(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('b', 8, new Cavalo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('c', 8, new Bispo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('e', 8, new Rei(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('f', 8, new Bispo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('g', 8, new Cavalo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('h', 8, new Torre(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('a', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('b', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('c', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('d', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('e', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('f', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('g', 7, new Peao(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('h', 7, new Peao(tabuleiro, Cores.PRETO));
	

	}

}
