package tabuleiro;

public class PosicaoTabuleiro {
	private Integer linha;
	private Integer coluna;
	
	public PosicaoTabuleiro() {
		
	}

	public PosicaoTabuleiro(Integer linha, Integer coluna) {
		super();
		this.linha = linha;
		this.coluna = coluna;
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public Integer getColuna() {
		return coluna;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}
	
	@Override 
	public String toString () {
		return linha + "," + coluna;
	}
}
