package tabuleiro;

public abstract class PecaTabuleiro {
	protected PosicaoTabuleiro posicao;
	private Tabuleiro tabuleiro;

	public PecaTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null; // é nulo automaticamente pelo Java, mas é importante enfatizar para fins
						// didáticos
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movimentosPossiveis();

	public boolean movimentoPossivel(PosicaoTabuleiro posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getLinha()];
	}

	public boolean existeMovimentoPossivel(PosicaoTabuleiro posicao) {
		boolean[][] matriz = movimentosPossiveis();
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
