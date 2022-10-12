package tabuleiro;

public class Tabuleiro {
	private Integer linhas;
	private Integer colunas;
	private Peca[][] pecas;

	public Tabuleiro(Integer linhas, Integer colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro criando tabuleiro: necessário ter pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public Integer getLinhas() {
		return linhas;
	}
	public Integer getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {	
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Peça não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void posicionarPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peça na posição " + posicao);
		}
		pecas [posicao.getLinha()] [posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca (Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		} 
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas [posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas  && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	public boolean temPeca (Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posicao não existe no tabuleiro");
		}
		return peca(posicao) != null;
	}
}
