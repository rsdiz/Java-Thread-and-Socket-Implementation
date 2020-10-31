package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class AppServer
 * Implementasi dari ServerSocket
 *
 * @author Muhammad Rosyid Izzulkhaq (5180411122)
 */
public class AppServer {

    /**
     * fungsi untuk menjalankan ServerSocket
     * Cara Pemakaian:
     *      java AppServer [port]
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        int port = 0;
        Scanner keyboard = new Scanner(System.in);

        if (args.length < 1) {
            do {
                try {
                    System.out.print(
                            "Masukkan port yang akan anda gunakan\n" +
                                    "untuk menjalankan server: ");
                    port = keyboard.nextInt();
                } catch (InputMismatchException exception) {
                    System.out.println(
                            "Port yang dimasukkan tidak valid!\n");
                }
                keyboard.nextLine();
            } while (port <= 0);
        }

        // Implementasi ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server berjalan pada port: " + port);

            // Menjalankan Thread
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client terhubung ke server!");
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            System.out.println(
                    "Terjadi Kesalahan pada I/O: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
