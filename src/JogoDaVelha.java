import java.util.Scanner;
import java.util.Random;

public class JogoDaVelha {
    public static void main(String[] args) {
        char[][] tabuleiro = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        Scanner leitor = new Scanner(System.in);
        Random bot = new Random();
        char humano = 'X';
        char botChar = 'O';
        int jogadas = 0;

        System.out.println("--- Jogo da Velha: Tu vs IA Defensiva ---");

        while (true) {
            exibirTabuleiro(tabuleiro);

            // --- VEZ DO HUMANO ---
            System.out.println("Tua vez! Escolhe linha e coluna (0-2):");
            int l, c;
            while (true) {
                l = leitor.nextInt(); c = leitor.nextInt();
                if (l >= 0 && l <= 2 && c >= 0 && c <= 2 && tabuleiro[l][c] == ' ') break;
                System.out.println("Inválido! Tenta de novo:");
            }
            tabuleiro[l][c] = humano;
            jogadas++;

            if (verificarVitoria(tabuleiro, humano)) {
                exibirTabuleiro(tabuleiro);
                System.out.println("Incrível! Tu venceste!");
                break;
            }
            if (jogadas == 9) {
                exibirTabuleiro(tabuleiro);
                System.out.println("Empate!");
                break;
            }

            // --- VEZ DO COMPUTADOR (IA) ---
            System.out.println("\nO computador está a analisar as tuas jogadas...");

            // 1. Tentar Bloquear o Humano
            boolean bloqueou = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro[i][j] == ' ') {
                        tabuleiro[i][j] = humano; // "Simula" uma jogada sua
                        if (verificarVitoria(tabuleiro, humano)) {
                            tabuleiro[i][j] = botChar; // Bloqueia aqui!
                            bloqueou = true;
                        } else {
                            tabuleiro[i][j] = ' '; // Limpa a simulação
                        }
                    }
                    if (bloqueou) break;
                }
                if (bloqueou) break;
            }

            // 2. Se não precisou bloquear, joga aleatório
            if (!bloqueou) {
                int lb, cb;
                do {
                    lb = bot.nextInt(3);
                    cb = bot.nextInt(3);
                } while (tabuleiro[lb][cb] != ' ');
                tabuleiro[lb][cb] = botChar;
            }

            jogadas++;
            if (verificarVitoria(tabuleiro, botChar)) {
                exibirTabuleiro(tabuleiro);
                System.out.println("O computador bloqueou-te e venceu!");
                break;
            }
        }
        leitor.close();
    }

    public static void exibirTabuleiro(char[][] t) {
        System.out.println("\n  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(t[i][j] + (j < 2 ? "|" : ""));
            }
            System.out.println(i < 2 ? "\n  -----" : "");
        }
    }

    public static boolean verificarVitoria(char[][] t, char j) {
        for (int i = 0; i < 3; i++) {
            if ((t[i][0] == j && t[i][1] == j && t[i][2] == j) ||
                    (t[0][i] == j && t[1][i] == j && t[2][i] == j)) return true;
        }
        return (t[0][0] == j && t[1][1] == j && t[2][2] == j) ||
                (t[0][2] == j && t[1][1] == j && t[2][0] == j);
    }
}