package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();

		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Posicao original: ");
				PosicaoXadrez original = UI.lerPosicaoXadrez(scan);

				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(original);
				UI.limparTela();
				UI.printBoard(partidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.println("Posicao de destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(scan);

				PecaXadrez capturarPeca = partidaXadrez.executarMovimento(original, destino);

				if (capturarPeca != null) {
					capturada.add(capturarPeca);
				}
				if(partidaXadrez.getPromocao() != null) {
					System.out.println("Entre com a peca a ser promovida (B/C/T/Q): ");
					String tipo = scan.nextLine();
					partidaXadrez.trocarPecaPromovida(tipo);
				}

			} catch (XadrezException e) {
				System.out.println(e.getMessage());

			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());

			}
		}
		UI.limparTela();
		UI.printPartida(partidaXadrez, capturada);
	}
}
