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
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promocao;

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

	public PecaXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}

	public PecaXadrez getPromocao() {
		return promocao;
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

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

		// movimento especial promocao

		promocao = null;

		if (pecaMovida instanceof Peao) {
			if (pecaMovida.getCor() == Cores.BRANCO && destino.getLinha() == 0
					|| pecaMovida.getCor() == Cores.PRETO && destino.getLinha() == 7) {
				promocao = (PecaXadrez) tabuleiro.peca(destino);
				promocao = trocarPecaPromovida("Q");
			}
		}

		check = testarCheck(oponente(jogadorAtual)) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {

			proximoTurno();
		}
		// movimento especial enPassant

		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == original.getLinha() - 2 || destino.getLinha() == original.getLinha() + 2)) {
			enPassantVulneravel = pecaMovida;
		} else {
			enPassantVulneravel = null;
		}

		return (PecaXadrez) capturarPeca;
	}

	public PecaXadrez trocarPecaPromovida(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Não ha peca para ser promovida");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
			return promocao;
		}
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasTabuleiro.remove(p);

		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.posicionarPeca(novaPeca, pos);
		pecasTabuleiro.add(novaPeca);

		return novaPeca;
	}

	private PecaXadrez novaPeca(String tipo, Cores cor) {
		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if (tipo.equals("Q"))
			return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca fazerMovimento(Posicao original, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(original);
		p.incrementarContarMovimento();
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.posicionarPeca(p, destino);

		if (capturarPeca != null) {
			pecasTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		// movimento especial roque rei torre

		if (p instanceof Rei && destino.getColuna() == original.getColuna() + 2) {
			Posicao originalT = new Posicao(original.getLinha(), original.getColuna() + 3);
			Posicao destinoT = new Posicao(original.getLinha(), original.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(originalT);
			tabuleiro.posicionarPeca(torre, destinoT);
			torre.incrementarContarMovimento();
		}

		// movimento especial roque rainha torre

		if (p instanceof Rei && destino.getColuna() == original.getColuna() - 2) {
			Posicao originalT = new Posicao(original.getLinha(), original.getColuna() - 4);
			Posicao destinoT = new Posicao(original.getLinha(), original.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(originalT);
			tabuleiro.posicionarPeca(torre, destinoT);
			torre.incrementarContarMovimento();
		}

		// movimento especial en passant

		if (p instanceof Peao) {
			if (original.getColuna() != destino.getColuna() && capturarPeca == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cores.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());

				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturarPeca = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(capturarPeca);
				pecasTabuleiro.remove(capturarPeca);
			}
		}

		return capturarPeca;
	}

	private void desfazerMovimento(Posicao original, Posicao destino, Peca capturarPeca) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarContarMovimento();
		tabuleiro.posicionarPeca(p, original);

		if (capturarPeca != null) {
			tabuleiro.posicionarPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasTabuleiro.add(capturarPeca);
		}

		// movimento especial roque rei torre

		if (p instanceof Rei && destino.getColuna() == original.getColuna() + 2) {
			Posicao originalT = new Posicao(original.getLinha(), original.getColuna() + 3);
			Posicao destinoT = new Posicao(original.getLinha(), original.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.posicionarPeca(torre, originalT);
			torre.decrementarContarMovimento();
		}

		// movimento especial roque rainha torre

		if (p instanceof Rei && destino.getColuna() == original.getColuna() - 2) {
			Posicao originalT = new Posicao(original.getLinha(), original.getColuna() - 4);
			Posicao destinoT = new Posicao(original.getLinha(), original.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.posicionarPeca(torre, originalT);
			torre.decrementarContarMovimento();
		}
		// movimento especial en passant

		if (p instanceof Peao) {
			if (original.getColuna() != destino.getColuna() && capturarPeca == enPassantVulneravel) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cores.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());

				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.posicionarPeca(peao, posicaoPeao);
			}
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
		posicionarNovaPeca('d', 1, new Rainha(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('f', 1, new Bispo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('g', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('h', 1, new Torre(tabuleiro, Cores.BRANCO));
		posicionarNovaPeca('a', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('b', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('c', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('d', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('e', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('f', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('g', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		posicionarNovaPeca('h', 2, new Peao(tabuleiro, Cores.BRANCO, this));

		posicionarNovaPeca('a', 8, new Torre(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('b', 8, new Cavalo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('c', 8, new Bispo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('d', 8, new Rainha(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('e', 8, new Rei(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('f', 8, new Bispo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('g', 8, new Cavalo(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('h', 8, new Torre(tabuleiro, Cores.PRETO));
		posicionarNovaPeca('a', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('b', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('c', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('d', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('e', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('f', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('g', 7, new Peao(tabuleiro, Cores.PRETO, this));
		posicionarNovaPeca('h', 7, new Peao(tabuleiro, Cores.PRETO, this));

	}

}
