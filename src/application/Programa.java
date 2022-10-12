package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {
	public static void main (String [] args) {
		Scanner scan = new Scanner(System.in);
	
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	try {
	while(true) {
		UI.limparTela();
		UI.printPartida(partidaXadrez);
		System.out.println();
		System.out.print("Posicao original: ");
		PosicaoXadrez original = UI.lerPosicaoXadrez(scan);
		
		boolean [][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(original);
		UI.limparTela();
		UI.printBoard(partidaXadrez.getPecas(), movimentosPossiveis);
		
		System.out.println();
		System.out.println("Posicao de destino: ");
		PosicaoXadrez destino = UI.lerPosicaoXadrez(scan);
		
		PecaXadrez capturarPeca = partidaXadrez.executarMovimento(original, destino);
	} 
	} catch (XadrezException e ) {
		System.out.println(e.getMessage());
	
	} catch (InputMismatchException e) {
		System.out.println(e.getMessage());
	
	}
	}
}
