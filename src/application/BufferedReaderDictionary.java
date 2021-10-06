package application;

import java.io.BufferedReader;
import java.io.FileReader;

public class BufferedReaderDictionary {

	public static void main(String[] args) throws Exception {
		String s = leTexto("teste.txt");
		String v[] = paraArray(s, "teste.txt");
		printOut(v);

	}

	private static boolean buscaBinaria(String v[], String s) {

		int inicio = 0;
		int fim = v.length - 1;
		int meio;

		while (inicio <= fim) {

			meio = (inicio + fim) / 2;

			try {

				if (v[meio].equals(s)) {
					return true;
				}

				if (v[meio].compareTo(s) > 0) {
					fim = meio - 1;
				}

				else {
					inicio = meio + 1;
				}
			}

			// tratamento de excecao caso o vetor em tal indice seja nulo
			catch (NullPointerException e) {
				fim = meio - 1;
			}
		}

		return false;

	}

	private static void ordenacaoInsercao(String v[], String s) {

		for (int i = 0; i < v.length; i++) {
			int j = i;
			String x = v[j];

			try {
				while (j > 0 && x.compareTo(v[j - 1]) < 0) {
					v[j] = v[j - 1];
					j--;

				}

				v[j] = x;

			} catch (NullPointerException e) {

			}

		}

	}

	// metodo para obter o tamanho aproximado do vetor para o texto informado
	private static int tamanhoVetor(String archive) throws Exception {

		FileReader fr = new FileReader(archive);
		BufferedReader br = new BufferedReader(fr);

		String line;
		int size = 0;

		while ((line = br.readLine()) != null) {

			// regex para remover as pontuacoes mais recorrentes em textos, bem como os
			// espacos
			// foi a unica forma que achei de faze-lo.

			String aux[] = line.split("[.!?,-;\\s]+");
			size += aux.length;

		}

		br.close();
		return size;

	}

	// autoexplicativo. Le o texto e o retorna numa string so
	private static String leTexto(String arquivo) throws Exception {

		FileReader fr = new FileReader(arquivo);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();

		String line;

		while ((line = br.readLine()) != null) {

			sb.append(line.toLowerCase()).append(" ");

		}

		br.close();
		return sb.toString();

	}

	// metodo para transferir do stringbuffer para um array
	private static String[] paraArray(String s, String arquivo) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(s);

		// regex novamente
		String aux[] = sb.toString().split("[.!?,-;\\s]+");
		// vetor final
		String v[] = new String[tamanhoVetor(arquivo)];

		for (int i = 0, j = 0; j < aux.length || i < v.length; i++, j++) {

			// o primeiro termo v[0] sera inserido automaticamente
			if (i == 0) {
				v[i] = aux[i];
			} else if (j == aux.length - 1) {
				break;
			}

			// ifs aninhados. Caso a busca binaria retorne falso, quer dizer que nao ha
			// correspondencia
			// logo, sera feita a insercao do termo em sua ordem correta usando insertion
			// sort
			else {
				buscaBinaria(v, aux[j]);

				if (buscaBinaria(v, aux[j]) == false) {
					insereElemento(v, aux[j], i);
					ordenacaoInsercao(v, aux[j]);

				} else {
					i--;
				}

			}
		}

		return v;
	}

	private static void insereElemento(String v[], String s, int pos) {

		for (int i = pos; i < v.length; i++) {
			v[pos] = s;
		}
	}

	private static void printOut(String v[]) {
		int contaPalavras = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i] != null) {
				System.out.println(v[i]);
				contaPalavras++;
			}
		}

		System.out.printf("N° de palavras diferentes no dicionário: %d", contaPalavras);
	}

}
