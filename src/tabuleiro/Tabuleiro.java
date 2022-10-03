package tabuleiro;

public class Tabuleiro {
	private Integer linhas;
	private Integer colunas;
	private PecaTabuleiro[][] pecas;

	public Tabuleiro(Integer linhas, Integer colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro criando tabuleiro: necessário ter pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new PecaTabuleiro[linhas][colunas];
	}

	public Integer getLinhas() {
		return linhas;
	}
	public Integer getColunas() {
		return colunas;
	}

	public PecaTabuleiro peca(int linha, int coluna) {	
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}

	public PecaTabuleiro peca(PosicaoTabuleiro posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Peça não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public PecaTabuleiro removerPeca (PosicaoTabuleiro posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		} if (peca(posicao) == null) {
			return null;
		}
		PecaTabuleiro aux = peca(posicao);
		aux.posicao = null;
		pecas [posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}

	public void posicionarPeca(PecaTabuleiro peca, PosicaoTabuleiro posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peça na posição " + posicao);
		}
		pecas [posicao.getLinha()] [posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas  && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExistente(PosicaoTabuleiro posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	public boolean temPeca (PosicaoTabuleiro posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		}
		return peca(posicao) != null;
	}
}
